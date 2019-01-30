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
package nom.bdezonia.zorbage.multidim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.axis.IdentityAxis;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Dimensioned;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MultiDimDataSource<T extends Algebra<T,U>,U>
	implements Dimensioned
{

	private List<Function1<BigDecimal,Long>> axes;
	private IndexedDataSource<?,U> data;
	private long[] dims;
	
	/**
	 * 
	 * @param dims
	 * @param data
	 */
	public MultiDimDataSource(T alg, long[] dims, IndexedDataSource<?,U> data) {
		if (LongUtils.numElements(dims) != data.size())
			throw new IllegalArgumentException("num elements within stated dimensions do not match size of given data source");
		this.dims = dims;
		this.data = data;
		this.axes = new ArrayList<Function1<BigDecimal,Long>>();
		for (int i = 0; i < dims.length; i++)
			this.axes.add(new IdentityAxis());
	}
	
	public IndexedDataSource<?,U> rawData() {
		return data;
	}


	@Override
	public int numDimensions() {
		return dims.length;
	}

	@Override
	public long dimension(int d) {
		return dims[d];
	}
	
	public Function1<BigDecimal,Long> getAxis(int i) {
		return this.axes.get(i);
	}
	
	public void setAxis(int i, Function1<BigDecimal,Long> func) {
		this.axes.set(i, func);
	}
	
	public IndexedDataSource<?,U> piped(int dim, long[] coord) {
		return new PipedDataSource<>(this, dim, coord);
	}

}