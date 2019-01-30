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
		if (d < 0) throw new IllegalArgumentException("negative index exception");
		if (d < dims.length) return dims[d];
		return 1;
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
	
	public void set(long[] index, U v) {
		long idx = indexToLong(index);
		data.set(idx, v);
	}
	
	public void setSafe(long[] index, U v) {
		boundsCheck(index);
		set(index, v);
	}
	
	public void get(long[] index, U v) {
		long idx = indexToLong(index);
		data.get(idx, v);
	}
	
	public void getSafe(long[] index, U v) {
		boundsCheck(index);
		get(index, v);
	}
	
	private void boundsCheck(long[] index) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("index dimensionality not the same as multidim dimensions");
		for (int i = 0; i < index.length; i++) {
			if (index[i] < 0 || index[i] >= this.dims[i])
				throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		}
	}
	
	/*
	 * dims = [4,5,6]
	 * idx = [1,2,3]
	 * long = 3*5*4 + 2*4 + 1;
	 */
	private long indexToLong(long[] idx) {
		if (idx.length == 0) return 0;
		long index = 0;
		long mult = 1;
		for (int i = 0; i < idx.length; i++) {
			index += mult * idx[i];
			mult *= dims[i];
		}
		return index;
	}
}
