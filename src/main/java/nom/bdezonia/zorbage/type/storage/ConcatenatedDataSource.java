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

import java.math.BigInteger;

import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ConcatenatedDataSource<T extends Algebra<T,U>,U>
	implements IndexedDataSource<ConcatenatedDataSource<T,U>,U>
{
	private final IndexedDataSource<?,U> first;
	private final IndexedDataSource<?,U> second;
	private final long sz;
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public ConcatenatedDataSource(IndexedDataSource<?,U> a, IndexedDataSource<?,U> b) {
		if (BigInteger.valueOf(a.size()).add(BigInteger.valueOf(b.size())).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
			throw new IllegalArgumentException("the two input lists are too long to add together");
		this.first = a;
		this.second = b;
		this.sz = a.size() + b.size();
	}
	
	@Override
	public ConcatenatedDataSource<T,U> duplicate() {
		// shallow copy
		return new ConcatenatedDataSource<>(first, second);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		long firstSize = first.size();
		if (index < firstSize)
			first.set(index, value);
		else
			second.set(index-firstSize, value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		long firstSize = first.size();
		if (index < firstSize)
			first.get(index, value);
		else
			second.get(index-firstSize, value);
	}

	@Override
	public long size() {
		return sz;
	}

}
