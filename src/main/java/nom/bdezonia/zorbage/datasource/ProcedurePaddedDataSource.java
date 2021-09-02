/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 * @param <U>
 */
public class ProcedurePaddedDataSource<T extends Algebra<T,U>,U>
	implements IndexedDataSource<U>
{
	private final T algebra;
	private final IndexedDataSource<U> storage;
	private final Procedure2<Long,U> proc;
	private final ThreadLocal<U> tmp;

	/**
	 * 
	 * @param alg
	 * @param storage
	 * @param proc
	 */
	public ProcedurePaddedDataSource(T alg, IndexedDataSource<U> storage, Procedure2<Long,U> proc) {
		this.algebra = alg;
		this.storage = storage;
		this.proc = proc;
		this.tmp = new ThreadLocal<U>() {
			@Override
			protected U initialValue() {
				return algebra.construct();
			}
		};
	}

	@Override
	public ProcedurePaddedDataSource<T,U> duplicate() {
		// shallow copy
		return new ProcedurePaddedDataSource<T,U>(algebra, storage, proc);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= storage.size()) {
			U t = tmp.get();
			proc.call(index, t);
			if (algebra.isNotEqual().call(t, value))
				throw new IllegalArgumentException("Out of bounds value does not match out of bounds procedure constraint");
		}
		else {
			storage.set(index, value);
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= storage.size()) {
			proc.call(index, value);
		}
		else {
			storage.get(index, value);
		}
	}

	@Override
	public long size() {
		return storage.size();
	}

	@Override
	public StorageConstruction storageType() {
		return storage.storageType();
	}

	@Override
	public boolean accessWithOneThread() {
		return storage.accessWithOneThread();
	}
}
