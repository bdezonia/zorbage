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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TransformedDataSource<U,W>
	implements
		IndexedDataSource<W>
{
	private final IndexedDataSource<U> uCollection;
	private final Algebra<?,U> uAlg;
	private final Procedure2<W,U> wToU;
	private final Procedure2<U,W> uToW;
	private final ThreadLocal<U> tmpU = new ThreadLocal<U>() {
		@Override
		protected U initialValue() {
			return uAlg.construct();
		}
	};
	
	/**
	 * 
	 * @param uCollection
	 * @param uAlg
	 * @param uToW
	 * @param wToU
	 */
	public TransformedDataSource(Algebra<?,U> uAlg, IndexedDataSource<U> uCollection, Procedure2<U,W> uToW, Procedure2<W,U> wToU) {
		this.uAlg = uAlg;
		this.uCollection = uCollection;
		this.uToW = uToW;
		this.wToU = wToU;
	}

	@Override
	public TransformedDataSource<U,W> duplicate() {
		// shallow copy
		return new TransformedDataSource<U,W>(uAlg, uCollection, uToW, wToU);
	}

	@Override
	public void set(long index, W value) {
		if (wToU == null)
			throw new IllegalArgumentException("TransformedDataSource cannot write: it's missing a transform");
		U tmp = tmpU.get();
		wToU.call(value, tmp);
		uCollection.set(index, tmp);
	}

	@Override
	public void get(long index, W value) {
		if (uToW == null)
			throw new IllegalArgumentException("TransformedDataSource cannot read: it's missing a transform");
		U tmp = tmpU.get();
		uCollection.get(index, tmp);
		uToW.call(tmp, value);
	}

	@Override
	public long size() {
		return uCollection.size();
	}

	@Override
	public StorageConstruction storageType() {
		return uCollection.storageType();
	}

	@Override
	public boolean accessWithOneThread() {
		return uCollection.accessWithOneThread();
	}
}
