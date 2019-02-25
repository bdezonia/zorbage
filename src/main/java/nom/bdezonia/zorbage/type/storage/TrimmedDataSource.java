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
package nom.bdezonia.zorbage.type.storage;

import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TrimmedDataSource<T extends Algebra<T,U>, U>
	implements
		IndexedDataSource<TrimmedDataSource<T,U>,U>
{
	private final IndexedDataSource<?,U> list;
	private final long first;
	private final long last;
	
	/**
	 * 
	 * @param list
	 * @param first
	 * @param last
	 */
	public TrimmedDataSource(IndexedDataSource<?,U> list, long first, long last) {
		long listSize = list.size();
		if (first < 0 || last < 0 || first > last || 
				first >= listSize || last >= listSize ||
				(last - first) >= listSize)
			throw new IllegalArgumentException("poor definition of first/last/list size");
		this.list = list;
		this.first = first;
		this.last = last;
	}
	
	@Override
	public TrimmedDataSource<T,U> duplicate() {
		// shallow copy
		return new TrimmedDataSource<>(list, first, last);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index >= size())
			throw new IllegalArgumentException("out of bounds index exception");
		list.set(index+first, value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index >= size())
			throw new IllegalArgumentException("out of bounds index exception");
		list.get(index+first, value);
	}

	@Override
	public long size() {
		return last - first + 1;
	}

}
