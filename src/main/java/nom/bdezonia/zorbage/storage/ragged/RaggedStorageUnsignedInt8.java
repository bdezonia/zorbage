/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.storage.ragged;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;

/**
 * @author Barry DeZonia
 */
public class RaggedStorageUnsignedInt8<U extends ByteCoder>
	implements IndexedDataSource<U>
{
	private final IndexedDataSource<SignedInt64Member> elementByteOffsets;
	private final IndexedDataSource<UnsignedInt8Member> elementData;
	private final SignedInt64Member tmpInt64;
	private final UnsignedInt8Member tmpUInt8;
	private byte[] uBuffer;
	
	/**
	 * 
	 * @param numElements
	 * @param totalFloats
	 */
	public RaggedStorageUnsignedInt8(long numElements, long totalBytes) {

		this.elementByteOffsets = Storage.allocate(G.INT64.construct(), numElements);
		this.elementData = Storage.allocate(G.UINT8.construct(), totalBytes);
		this.uBuffer = new byte[0];
		this.tmpInt64 = G.INT64.construct();
		this.tmpUInt8 = G.UINT8.construct();
	}

	/**
	 * 
	 * @param other
	 */
	public RaggedStorageUnsignedInt8(RaggedStorageUnsignedInt8<U> other) {
		
		this.elementByteOffsets = other.elementByteOffsets.duplicate();
		this.elementData = other.elementData.duplicate();
		this.uBuffer = other.uBuffer.clone();
		this.tmpInt64 = G.INT64.construct();
		this.tmpUInt8 = G.UINT8.construct();
	}

	@Override
	public RaggedStorageUnsignedInt8<U> duplicate() {
		
		return new RaggedStorageUnsignedInt8<>(this);
	}

	@Override
	public StorageConstruction storageType() {

		return elementData.storageType();
	}

	@Override
	public void set(long index, U value) {

		if (index < 0) {
			throw new IllegalArgumentException("negative index exception");
		}

		final long startByte;
		if (index == 0) {
			startByte = 0;
		}
		else {
			elementByteOffsets.get(index-1, tmpInt64);
			startByte = tmpInt64.v();
		}
		elementByteOffsets.get(index, tmpInt64);
		final long byteSize = tmpInt64.v() - startByte;

		// now is numbytes stored at startbyte == this value's numbytes?
		
		if (byteSize != value.byteCount()) {
			throw new IllegalArgumentException(
				"Ragged storage set(index,value) may not change stored element size");
		}
		
		// make sure our uBuffer is big enough
		
		if (uBuffer.length < byteSize) {
			uBuffer = new byte[(int) byteSize];
		}
		value.toByteArray(uBuffer, 0);
		UnsignedInt8Member b = tmpUInt8;
		for (int i = 0; i < byteSize; i++) {
			b.setV(uBuffer[i]);
			elementData.set(startByte + i, b);
		}
	}

	@Override
	public void get(long index, U value) {
		
		if (index < 0) {
			throw new IllegalArgumentException("negative index exception");
		}

		SignedInt64Member idx = tmpInt64;

		final long startByte;
		if (index == 0) {
			startByte = 0;
		}
		else {
			elementByteOffsets.get(index-1, idx);
			startByte = idx.v();
		}
		elementByteOffsets.get(index, tmpInt64);
		final long byteSize = tmpInt64.v() - startByte;

		// make sure our uBuffer is big enough
		
		if (uBuffer.length < byteSize) {
			uBuffer = new byte[(int) byteSize];
		}
		UnsignedInt8Member b = tmpUInt8;
		for (int i = 0; i < byteSize; i++) {
			elementData.get(startByte + i, b);
			uBuffer[i] = (byte) b.v();
		}
		value.fromByteArray(uBuffer, 0);
	}

	// Note: assumes every entity up to index-1 has been placed correctly
	
	public void place(long index, U value) {

		if (index < 0) {
			throw new IllegalArgumentException("negative index exception");
		}

		SignedInt64Member idx = tmpInt64;
		
		final long startByte;
		if (index == 0) {
			startByte = 0;
		}
		else {
			elementByteOffsets.get(index-1, idx);
			startByte = idx.v();
		}
		
		// make sure our uBuffer is big enough
		
		int byteCount = value.byteCount();
		
		if (uBuffer.length < byteCount) {
			uBuffer = new byte[byteCount];
		}
		value.toByteArray(uBuffer, 0);
		UnsignedInt8Member b = tmpUInt8;
		for (int i = 0; i < byteCount; i++) {
			b.setV(uBuffer[i]);
			elementData.set(startByte + i, b);
		}
		idx.setV(startByte + byteCount);
		elementByteOffsets.set(index, idx);
	}
	
	@Override
	public long size() {

		return elementByteOffsets.size();
	}

	public long byteCount() {

		return elementData.size();
	}

	@Override
	public boolean accessWithOneThread() {
		
		// we reuse local vars such as uBuffer, tmpUInt8, tmpInt64.
		//   multithreaded stuff could break accessing these

		return true;
	}
}
