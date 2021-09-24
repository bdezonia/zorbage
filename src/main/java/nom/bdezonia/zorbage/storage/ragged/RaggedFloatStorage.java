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
public class RaggedFloatStorage<U extends ByteCoder>
	implements IndexedDataSource<U>
{
	private final long numElements;
	private final long numFloats;
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
	public RaggedFloatStorage(long numElements, long totalFloats) {

		System.out.println("numElems  = "+numElements);
		System.out.println("totFloats = "+totalFloats);
		
		// TODO:
		//   take an IndexedDataSource<Float32> instead of totalFloats ???
		//   build the internal index from the data source
		//   make an example that reads data and builds the index
		
		this.numElements = numElements;
		this.numFloats = totalFloats;
		elementByteOffsets = Storage.allocate(G.INT64.construct(), numElements);
		System.out.println("  allocated longs");
		elementData = Storage.allocate(G.UINT8.construct(), totalFloats * 4);
		System.out.println("  allocated bytes");
		uBuffer = new byte[0];
		tmpInt64 = G.INT64.construct();
		tmpUInt8 = G.UINT8.construct();
		System.out.println("  done allocating");
	}

	/**
	 * 
	 * @param other
	 */
	public RaggedFloatStorage(RaggedFloatStorage<U> other) {
		
		numElements = other.numElements;
		numFloats = other.numFloats;
		elementByteOffsets = other.elementByteOffsets.duplicate();
		elementData = other.elementData.duplicate();
		uBuffer = other.uBuffer.clone();
		tmpInt64 = G.INT64.construct();
		tmpUInt8 = G.UINT8.construct();
	}

	@Override
	public RaggedFloatStorage<U> duplicate() {
		
		return new RaggedFloatStorage<>(this);
	}

	@Override
	public StorageConstruction storageType() {

		return elementData.storageType();
	}

	@Override
	public void set(long index, U value) {

		long currBytesIndex;
		long prevBytesIndex;
		if (index < 0) {
			throw new IllegalArgumentException("negative index exception");
		}

		SignedInt64Member idx = tmpInt64;
		if (index == 0) {
			prevBytesIndex = 0;
		}
		else {
			elementByteOffsets.get(index-1, idx);
			prevBytesIndex = idx.v();
		}
		elementByteOffsets.get(index, idx);
		currBytesIndex = idx.v();
		
		int diff = (int) (currBytesIndex - prevBytesIndex);

		// make sure our uBuffer is big enough
		
		if (uBuffer.length < diff) {
			uBuffer = new byte[diff];
		}
		value.toByteArray(uBuffer, 0);
		UnsignedInt8Member b = tmpUInt8;
		for (long i = 0; i < diff; i++) {
			b.setV(uBuffer[(int) i]);
			elementData.set(prevBytesIndex+i, b);
		}
	}

	@Override
	public void get(long index, U value) {

		long currBytesIndex;
		long prevBytesIndex;
		if (index < 0) {
			throw new IllegalArgumentException("negative index exception");
		}

		SignedInt64Member idx = tmpInt64;
		if (index == 0) {
			prevBytesIndex = 0;
		}
		else {
			elementByteOffsets.get(index-1, idx);
			prevBytesIndex = idx.v();
		}
		elementByteOffsets.get(index, idx);
		currBytesIndex = idx.v();

		int diff = (int) (currBytesIndex - prevBytesIndex);

		// make sure out ubuffer is big enough
		
		if (uBuffer.length < diff) {
			uBuffer = new byte[diff];
		}
		UnsignedInt8Member b = tmpUInt8;
		for (int i = 0; i < diff; i++) {
			elementData.get(prevBytesIndex+i, b);
			uBuffer[i] = (byte) b.v();
		}
		value.fromByteArray(uBuffer, 0);
	}

	public void setByteOffset(long i, SignedInt64Member offset) {
		elementByteOffsets.set(i, offset);
	}

	public void getByteOffset(long i, SignedInt64Member offset) {
		elementByteOffsets.get(i, offset);
	}
	
	@Override
	public long size() {

		return numElements;
	}

	@Override
	public boolean accessWithOneThread() {
		
		// we reuse local vars such as uBuffer, tmpUInt8, tmpInt64.
		//   multithreaded stuff could break accessing these

		return true;
	}
}
