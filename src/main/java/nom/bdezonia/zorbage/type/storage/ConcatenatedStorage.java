/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ConcatenatedStorage<T extends IndexedDataSource<T,U>,U>
	implements IndexedDataSource<T,U>
{
	private final IndexedDataSource<T,U> first;
	private final IndexedDataSource<T,U> second;

	/**
	 * 
	 * @param a
	 * @param b
	 */
	public ConcatenatedStorage(IndexedDataSource<T,U> a, IndexedDataSource<T,U> b) {
		if (BigInteger.valueOf(a.size()).add(BigInteger.valueOf(b.size())).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
			throw new IllegalArgumentException("the two input lists are too long to add together");
		this.first = a;
		this.second = b;
	}
	
	@Override
	public T duplicate() {
		// shallow copy
		// TODO: WTH? Why does this warning keep cropping up?
		return (T) new ConcatenatedStorage<>(first, second);
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
		return first.size() + second.size();
	}

}
