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
package nom.bdezonia.zorbage.multidim;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Dimensioned;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ProcedurePaddedMultiDimDataSource<T extends Algebra<T,U>,U>
	implements Dimensioned
{
	private final T algebra;
	private final MultiDimDataSource<?,U> md;
	private final Procedure2<long[],U> proc;

	/**
	 * 
	 * @param alg
	 * @param md
	 * @param proc
	 */
	public ProcedurePaddedMultiDimDataSource(T alg, MultiDimDataSource<?,U> md, Procedure2<long[],U> proc) {
		this.algebra = alg;
		this.md = md;
		this.proc = proc;
	}

	public IndexedDataSource<?,U> rawData() {
		return md.rawData();
	}

	@Override
	public int numDimensions() {
		return md.numDimensions();
	}

	@Override
	public long dimension(int d) {
		return md.dimension(d);
	}
	
	public long numElements() {
		return md.numElements();
	}

	public Function1<BigDecimal,Long> getAxis(int i) {
		return md.getAxis(i);
	}
	
	public void setAxis(int i, Function1<BigDecimal,Long> func) {
		md.setAxis(i, func);
	}
	
	public IndexedDataSource<?,U> piped(int dim, long[] coord) {
		return md.piped(dim, coord);
	}
	
	public void set(long[] index, U v) {
		if (md.oob(index)) {
			if (!algebra.isZero().call(v))
				throw new IllegalArgumentException("Cannot set out of bounds value as nonzero");
		}
		else {
			md.set(index, v);
		}
	}
	
	public void get(long[] index, U v) {
		if (md.oob(index)) {
			proc.call(index, v);
		}
		else {
			md.get(index, v);
		}
	}
	
}
