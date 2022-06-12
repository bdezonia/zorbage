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
package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Member;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.function.Function1;

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
	private final Function1<Boolean,U> condition;
	private final IndexedDataSource<SignedInt64Member> indexList;
	private final long sz;

	/**
	 * 
	 * @param algebra
	 * @param source
	 * @param condition
	 */
	public ConditionalDataSource(T algebra, IndexedDataSource<U> source, Function1<Boolean,U> condition) {
		this.algebra = algebra;
		this.source = source;
		this.condition = condition;
		U tmp = algebra.construct();
		long count = 0;
		long srcSize = source.size();
		for (long i = 0; i < srcSize; i++) {
			source.get(i, tmp);
			if (condition.call(tmp)) {
				count++;
			}
		}
		this.sz = count;
		SignedInt64Member val = tmp64.get();
		this.indexList = Storage.allocate(val, sz);
		count = 0;
		for (long i = 0; i < srcSize; i++) {
			source.get(i, tmp);
			if (condition.call(tmp)) {
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
		if (!condition.call(value))
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

	@Override
	public StorageConstruction storageType() {
		return source.storageType();
	}

	@Override
	public boolean accessWithOneThread() {
		return source.accessWithOneThread();
	}
}
