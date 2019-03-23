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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ConditionalDataSource<T extends Algebra<T,U>, U>
	implements IndexedDataSource<U>
{
	private static final ThreadLocal<SignedInt64Member> tmp64 =
		new ThreadLocal<SignedInt64Member>() {
			@Override
			protected SignedInt64Member initialValue() {
				return G.INT64.construct();
			}
		};
	private final T algebra;
	private final IndexedDataSource<U> source;
	private final Predicate<U> condition;
	private final IndexedDataSource<SignedInt64Member> indexList;
	private final long sz;

	/**
	 * 
	 * @param algebra
	 * @param source
	 * @param condition
	 */
	public ConditionalDataSource(T algebra, IndexedDataSource<U> source, Predicate<U> condition) {
		this.algebra = algebra;
		this.source = source;
		this.condition = condition;
		U tmp = algebra.construct();
		long count = 0;
		for (long i = 0; i < source.size(); i++) {
			source.get(i, tmp);
			if (condition.isTrue(tmp)) {
				count++;
			}
		}
		this.sz = count;
		SignedInt64Member val = tmp64.get();
		this.indexList = Storage.allocate(sz, val);
		count = 0;
		for (long i = 0; i < source.size(); i++) {
			source.get(i, tmp);
			if (condition.isTrue(tmp)) {
				val.setV(i);
				indexList.set(count, val);
				count++;
			}
		}
	}
	
	@Override
	public ConditionalDataSource<T, U> duplicate() {
		// shallow copy
		return new ConditionalDataSource<T,U>(algebra, source, condition);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= sz)
			throw new IllegalArgumentException("index out of bounds for conditional data source");
		// the size of the list is an invariant!
		if (!condition.isTrue(value))
			throw new IllegalArgumentException("inserted values must conform to the condition");
		SignedInt64Member tmp = tmp64.get();
		indexList.get(index, tmp);
		source.set(tmp.v(), value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= sz)
			throw new IllegalArgumentException("index out of bounds for conditional data source");
		SignedInt64Member tmp = tmp64.get();
		indexList.get(index, tmp);
		source.get(tmp.v(), value);
	}

	@Override
	public long size() {
		return sz;
	}

}
