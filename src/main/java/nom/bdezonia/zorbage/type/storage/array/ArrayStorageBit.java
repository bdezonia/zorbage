/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.storage.array;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.coder.BitCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

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
	
	public ArrayStorageBit(long size, U type) {
	
		final long maxElements = 64l * Integer.MAX_VALUE / type.bitCount();
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
		synchronized (data) {
			value.toBitArray(data, bucketStart, bucketOffset);
		}
	}

	@Override
	public void get(long index, U value) {
		final long bitIndex = index * type.bitCount();
		final int bucketStart = (int)(bitIndex / 64);
		final int bucketOffset = (int)(bitIndex % 64);
		synchronized (data) {
			value.fromBitArray(data, bucketStart, bucketOffset);
		}
	}
	
	@Override
	public long size() {
		return size;
	}

	@Override
	public ArrayStorageBit<U> duplicate() {
		ArrayStorageBit<U> s = new ArrayStorageBit<U>(size(), type);
		synchronized (data) {
			for (int i = 0; i < data.length; i++)
				s.data[i] = data[i];
		}
		return s;
	}

	@Override
	public ArrayStorageBit<U> allocate() {
		return new ArrayStorageBit<U>(size(), type);
	}
	
}
