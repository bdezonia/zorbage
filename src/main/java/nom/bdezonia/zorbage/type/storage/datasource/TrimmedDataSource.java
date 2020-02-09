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

import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TrimmedDataSource<U>
	implements
		IndexedDataSource<U>
{
	private final IndexedDataSource<U> list;
	private final long first;
	private final long count;
	
	/**
	 * 
	 * @param list
	 * @param first
	 * @param count
	 */
	public TrimmedDataSource(IndexedDataSource<U> list, long first, long count) {
		long listSize = list.size();
		if (first < 0 || count < 0 || 
				first >= listSize || count > listSize ||
				(first + count) > listSize)
			throw new IllegalArgumentException("poor definition of first/count/list size");
		this.list = list;
		this.first = first;
		this.count = count;
	}
	
	@Override
	public TrimmedDataSource<U> duplicate() {
		// shallow copy
		return new TrimmedDataSource<>(list, first, count);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index >= size())
			throw new IllegalArgumentException("out of bounds index exception");
		list.set(first+index, value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index >= size())
			throw new IllegalArgumentException("out of bounds index exception");
		list.get(first+index, value);
	}

	@Override
	public long size() {
		return count;
	}

	@Override
	public StorageConstruction storageType() {
		return list.storageType();
	}
}
