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
package nom.bdezonia.zorbage.multidim;

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.axis.IdentityAxis;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdData<U>
	implements MultiDimDataSource<U>
{
	private final List<Procedure2<Long,HighPrecisionMember>> axes;
	private final IndexedDataSource<U> data;
	private final long[] dims;
	
	/**
	 * 
	 * @param dims
	 * @param data
	 */
	public NdData(long[] dims, IndexedDataSource<U> data) {
		if (dims.length == 0)
			throw new IllegalArgumentException("multidim data source must have 1 or more dimensions");
		if (LongUtils.numElements(dims) != data.size())
			throw new IllegalArgumentException("num elements within stated dimensions do not match size of given data source");
		this.dims = dims;
		this.data = data;
		this.axes = new ArrayList<Procedure2<Long,HighPrecisionMember>>();
		for (int i = 0; i < dims.length; i++)
			this.axes.add(new IdentityAxis());
	}
	
	@Override
	public IndexedDataSource<U> rawData() {
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
	
	public long numElements() {
		return data.size();
	}

	public Procedure2<Long,HighPrecisionMember> getAxis(int i) {
		return this.axes.get(i);
	}
	
	public void setAxis(int i, Procedure2<Long,HighPrecisionMember> proc) {
		this.axes.set(i, proc);
	}
	
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {
		return new PipedDataSource<U>(this, dim, coord);
	}
	
	public void set(IntegerIndex index, U v) {
		long idx = IndexUtils.indexToLong(dims, index);
		data.set(idx, v);
	}
	
	public void setSafe(IntegerIndex index, U v) {
		if (oob(index))
			throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		set(index, v);
	}
	
	public void get(IntegerIndex index, U v) {
		long idx = IndexUtils.indexToLong(dims, index);
		data.get(idx, v);
	}
	
	public void getSafe(IntegerIndex index, U v) {
		if (oob(index))
			throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		get(index, v);
	}
	
	public boolean oob(IntegerIndex index) {
		if (index.numDimensions() != numDimensions())
			throw new IllegalArgumentException("index dimensionality not the same as multidim dimensions");
		for (int i = 0; i < index.numDimensions(); i++) {
			if (index.get(i) < 0 || index.get(i) >= dimension(i))
				return true;
		}
		return false;
	}
}
