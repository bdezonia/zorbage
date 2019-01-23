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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FilteredSource<T extends IndexedDataSource<T,U>, U, V extends IndexedDataSource<V,W>, W>
	implements IndexedDataSource<V,W>
{
	private final IndexedDataSource<T,U> source;
	private final Procedure2<U,W> dProc;
	private final Procedure2<W,U> sProc;
	private final Algebra<?, U> sAlg;
	private final Algebra<?, W> dAlg;
	
	public FilteredSource(IndexedDataSource<T,U> source, Procedure2<W,U> sProc, Algebra<?,U> sAlg, Procedure2<U,W> dProc, Algebra<?,W> dAlg) {
		this.source = source;
		this.sProc = sProc;
		this.dProc = dProc;
		this.sAlg = sAlg;
		this.dAlg = dAlg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V duplicate() {
		// TODO: this cast might be broken. I am supressing warnings. Must test.
		return (V) new FilteredSource<T,U,V,W>(source, sProc, sAlg, dProc, dAlg);
	}

	@Override
	public void set(long index, W value) {
		U tmp = sAlg.construct();
		sProc.call(value, tmp);
		source.set(index, tmp);
	}

	@Override
	public void get(long index, W value) {
		U tmp = sAlg.construct();
		source.get(index, tmp);
		dProc.call(tmp, value);
	}

	@Override
	public long size() {
		return source.size();
	}
}