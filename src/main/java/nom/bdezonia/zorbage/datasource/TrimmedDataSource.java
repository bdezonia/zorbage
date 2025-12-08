/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.algebra.StorageConstruction;

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
				first >= listSize || count > listSize)
			throw new IllegalArgumentException("poor definition of first/count/list size");
		if (first + count > listSize) {
			count = listSize - first;
		}
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

	@Override
	public boolean accessWithOneThread() {
		return list.accessWithOneThread();
	}
}
