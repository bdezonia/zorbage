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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.SequencedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 * A {@link DataSource} that is a one dimensional column within a {@link MultiDimDataSource}.
 * Pronounced as "pie" "ped".
 * 
 */
public class PipedDataSource <T extends Algebra<T,U>,U> implements IndexedDataSource<PipedDataSource<T,U>,U> {

	private MultiDimDataSource<T,U> d;
	private int dim;
	private long[] parentDims;
	private long[] coords;
	private IndexedDataSource<?,U> data;
	
	/**
	 * 
	 * @param d
	 * @param dim
	 * @param coords
	 */
	public PipedDataSource(MultiDimDataSource<T,U> d, int dim, long[] coords) {
		this.d = d;
		this.dim = dim;
		this.coords = coords;
		if (coords.length != d.numDimensions())
			throw new IllegalArgumentException("coordinate does not match dimensionality of multidim data");
		if (dim < 0 || dim >= coords.length)
			throw new IllegalArgumentException("ranging dim is not within dimensionality of multidim data");
		for (int i = 0; i < coords.length; i++) {
			if (i != dim) {
				if (coords[i] < 0 || coords[i] >= d.dimension(i))
					throw new IllegalArgumentException("coordinate is outside bounds of multidim data");
			}
		}
		this.parentDims = new long[d.numDimensions()];
		for (int i = 0; i < d.numDimensions(); i++) {
			this.parentDims[i] = d.dimension(i);
		}
		this.data = findSubset();
	}
	
	@Override
	public PipedDataSource<T,U> duplicate() {
		// shallow copy
		return new PipedDataSource<T,U>(d,dim,coords);
	}

	@Override
	public void set(long index, U value) {
		data.set(index, value);
	}

	@Override
	public void get(long index, U value) {
		data.get(index, value);
	}

	@Override
	public long size() {
		return data.size();
	}

	private IndexedDataSource<?,U> findSubset() {
		long[] start = coords.clone();
		long[] stop = coords.clone();
		start[dim] = 0;
		stop[dim] = d.dimension(dim)-1;
		long count = d.dimension(dim);
		long offset = IndexUtils.indexToLong(parentDims,start);
		long stride;
		if (count <= 1) {
			stride = 1;
		}
		else {
			stride = (IndexUtils.indexToLong(parentDims,stop) - offset) / (count - 1);
		}
		return new SequencedDataSource<>(d.rawData(), offset, stride, count);
	}
	
}
