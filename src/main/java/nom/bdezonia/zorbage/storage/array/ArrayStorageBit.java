/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.storage.array;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.BitCoder;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageBit<U extends BitCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<ArrayStorageBit<U>>
{
	private final U type;
	private final long[] data;
	private final long size;
	
	public ArrayStorageBit(U type, long size) {
	
		final long maxElements = 64L * Integer.MAX_VALUE / type.bitCount();
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageBit cannot handle a negative request");
		if (size > maxElements)
			throw new IllegalArgumentException("ArrayStorageBit can handle at most " + maxElements + " elements of the given type.");
		int count = (int)((size * type.bitCount()) / 64);
		if ((size * type.bitCount()) % 64 > 0) count += 1;
		this.type = type.allocate();
		this.data = new long[count];
		this.size = size;
	}

	@Override
	public void set(long index, U value) {
		final long bitIndex = index * type.bitCount();
		final int bucketStart = (int)(bitIndex / 64);
		final int bucketOffset = (int)(bitIndex % 64);
		value.toBitArray(data, bucketStart, bucketOffset);
	}

	@Override
	public void get(long index, U value) {
		final long bitIndex = index * type.bitCount();
		final int bucketStart = (int)(bitIndex / 64);
		final int bucketOffset = (int)(bitIndex % 64);
		value.fromBitArray(data, bucketStart, bucketOffset);
	}
	
	@Override
	public long size() {
		return size;
	}

	@Override
	public ArrayStorageBit<U> duplicate() {
		ArrayStorageBit<U> s = new ArrayStorageBit<U>(type, size());
		System.arraycopy(data, 0, s.data, 0, data.length);
		return s;
	}

	@Override
	public ArrayStorageBit<U> allocate() {
		return new ArrayStorageBit<U>(type, size());
	}
	
	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}

	@Override
	public boolean accessWithOneThread() {
		return true;
	}
}
