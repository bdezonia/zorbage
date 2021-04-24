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
package nom.bdezonia.zorbage.data;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.SequencedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 * A {@link IndexedDataSource} that is a one dimensional column within a
 * {@link DimensionedDataSource}. Pronounced as "pie" "ped".
 * 
 */
public class PipedDataSource<U> implements IndexedDataSource<U> {

	private final DimensionedDataSource<U> d;
	private final int dim;
	private final long[] parentDims;
	private final IntegerIndex coords;
	private final IndexedDataSource<U> data;
	
	/**
	 * 
	 * @param d
	 * @param dim
	 * @param coords
	 */
	public PipedDataSource(DimensionedDataSource<U> d, int dim, IntegerIndex coords) {
		if (coords.numDimensions() != d.numDimensions())
			throw new IllegalArgumentException("coordinate does not match dimensionality of multidim data");
		if (dim < 0 || dim >= coords.numDimensions())
			throw new IllegalArgumentException("ranging dim is not within dimensionality of multidim data");
		for (int i = 0; i < coords.numDimensions(); i++) {
			if (i != dim) {
				if (coords.get(i) < 0 || coords.get(i) >= d.dimension(i))
					throw new IllegalArgumentException("coordinate is outside bounds of multidim data");
			}
		}
		this.d = d;
		this.dim = dim;
		this.coords = new IntegerIndex(coords.numDimensions());
		for (int i = 0; i < coords.numDimensions(); i++) {
			this.coords.set(i, coords.get(i));
		}
		this.parentDims = new long[d.numDimensions()];
		for (int i = 0; i < d.numDimensions(); i++) {
			this.parentDims[i] = d.dimension(i);
		}
		this.data = findSubset();
	}
	
	@Override
	public PipedDataSource<U> duplicate() {
		// shallow copy
		return new PipedDataSource<U>(d,dim,coords);
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

	private IndexedDataSource<U> findSubset() {
		IntegerIndex start = new IntegerIndex(coords.numDimensions());
		IntegerIndex stop = new IntegerIndex(coords.numDimensions());
		for (int i = 0; i < coords.numDimensions(); i++) {
			start.set(i, coords.get(i));
			stop.set(i, coords.get(i));
		}
		start.set(dim, 0);
		stop.set(dim, d.dimension(dim)-1);
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

	@Override
	public StorageConstruction storageType() {
		return d.storageType();
	}
	
}
