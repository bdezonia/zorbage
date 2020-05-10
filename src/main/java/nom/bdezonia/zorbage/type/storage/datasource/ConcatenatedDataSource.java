/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage.datasource;

import java.math.BigInteger;

import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ConcatenatedDataSource<U>
	implements IndexedDataSource<U>
{
	private final IndexedDataSource<U> first;
	private final IndexedDataSource<U> second;
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public ConcatenatedDataSource(IndexedDataSource<U> a, IndexedDataSource<U> b) {
		if (BigInteger.valueOf(a.size()).add(BigInteger.valueOf(b.size())).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
			throw new IllegalArgumentException("the two input lists are too long to add together");
		this.first = a;
		this.second = b;
	}
	
	@Override
	public ConcatenatedDataSource<U> duplicate() {
		// shallow copy
		return new ConcatenatedDataSource<>(first, second);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index < first.size())
			first.set(index, value);
		else
			second.set(index-first.size(), value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index < first.size())
			first.get(index, value);
		else
			second.get(index-first.size(), value);
	}

	@Override
	public long size() {
		return first.size() + second.size();
	}

	@Override
	public StorageConstruction storageType() {
		// TODO - Maybe the order of these if statements should change. Or maybe more testing of the size()'s
		//   should factor in.
		if ((first.storageType() == StorageConstruction.MEM_SPARSE) &&
				(second.storageType() == StorageConstruction.MEM_SPARSE))
			return StorageConstruction.MEM_SPARSE;
		if ((first.storageType() == StorageConstruction.MEM_VIRTUAL) ||
				(second.storageType() == StorageConstruction.MEM_VIRTUAL))
			return StorageConstruction.MEM_VIRTUAL;
		if (size() <= Integer.MAX_VALUE)
			return StorageConstruction.MEM_ARRAY;
		return StorageConstruction.MEM_VIRTUAL;
	}
}
