/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
public class SequencedDataSource<T extends Algebra<?,U>,U>
	implements IndexedDataSource<SequencedDataSource<T,U>, U>
{
	private final IndexedDataSource<?,U> data;
	private final long start;
	private final long stride;
	private final long count;

	/**
	 * 
	 * @param data
	 * @param start
	 * @param stride
	 * @param count
	 */
	public SequencedDataSource(IndexedDataSource<?,U> data,long start, long stride, long count) {
		this.data = data;
		this.start = start;
		this.stride = stride;
		this.count = count;
		if (count < 0)
			throw new IllegalArgumentException("count must be >= 1");
		if (start < 0)
			throw new IllegalArgumentException("start cannot be negative");
		if ((start + stride*(count-1)) >= data.size())
			throw new IllegalArgumentException("the specified sequence reaches beyond the end of the dataset");
	}
	
	@Override
	public SequencedDataSource<T, U> duplicate() {
		// shallow copy
		return new SequencedDataSource<>(data, start, stride, count);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= count)
			throw new IllegalArgumentException("index out of bounds");
		data.set(start + stride*index, value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= count)
			throw new IllegalArgumentException("index out of bounds");
		data.get(start + stride*index, value);
	}

	@Override
	public long size() {
		return count;
	}
}
