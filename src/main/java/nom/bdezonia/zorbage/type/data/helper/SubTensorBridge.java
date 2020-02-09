/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.helper;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SubTensorBridge<U> implements TensorMember<U> {

	private final Algebra<?,U> algebra;
	private final U zero;
	private final TensorMember<U> tensor;
	private IntegerIndex index;
	private int[] rangingDims;
	private long dim;

	public SubTensorBridge(Algebra<?,U> algebra, TensorMember<U> tensor) {
		if (tensor.numDimensions() < 2)
			throw new IllegalArgumentException("subtensor can only be constructed on tensor with 2 or more dimensions");
		this.algebra = algebra;
		this.zero = algebra.construct();
		this.tensor = tensor;
		this.index = new IntegerIndex(tensor.numDimensions());
		this.rangingDims = new int[tensor.numDimensions()];
		for (int i = 0; i < tensor.numDimensions(); i++) {
			this.rangingDims[i] = i;
		}
		this.dim = tensor.dimension(0);
	}

	public void setSubrange(int rank, int dim, int[] rangingDims, long[] fixedDimValues) {
		if (rank < 2 || rank > tensor.numDimensions())
			throw new IllegalArgumentException("rank of subtensor outside legal range");
		if (dim < 1 || dim > tensor.dimension(0))
			throw new IllegalArgumentException("dimension of subtensor outside legal range");
		if (rank != rangingDims.length)
			throw new IllegalArgumentException("count of ranging dimensions does not match given rank");
		if (rangingDims.length + fixedDimValues.length != tensor.numDimensions())
			throw new IllegalArgumentException("combined dims are incompatible with given tensor");
		for (int i = 0; i < rangingDims.length; i++) {
			int v = rangingDims[i];
			if (v < 0 || v >= tensor.numDimensions())
				throw new IllegalArgumentException("");
			for (int j = i+1; j < rangingDims.length; j++) {
				if (rangingDims[j] == v)
					throw new IllegalArgumentException("repeated ranging dim not allowed");
				if (rangingDims[j] < v)
					throw new IllegalArgumentException("ranging dims must be specified as increasing values");
			}
		}
		for (int i = 0; i < fixedDimValues.length; i++) {
			long v = fixedDimValues[i];
			if (v < 0 || v >= tensor.dimension(0))
				throw new IllegalArgumentException("fixed dimensions out of range");
		}
		this.rangingDims = rangingDims.clone();
		this.dim = dim;
		int f = 0;
		for (int i = 0; i < rangingDims.length; i++) {
			int v = rangingDims[i];
			while (f < v) {
				index.set(f, fixedDimValues[f]);
				f++;
			}
			index.set(f, 0);
			f++;
		}
		for (int i = rangingDims[rangingDims.length-1]+1; i < tensor.numDimensions(); i++) {
			index.set(i, fixedDimValues[f]);
			f++;
		}
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		if (d >= rangingDims.length)
			throw new IllegalArgumentException("dimension out of bounds exception");
		return dim;
	}

	@Override
	public int numDimensions() {
		return rangingDims.length;
	}

	@Override
	public boolean alloc(long[] dims) {
		if (dimsCompatible(dims))
			return false;
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long[] dims) {
		if (dimsCompatible(dims)) {
			IntegerIndex idx = new IntegerIndex(dims.length);
			SamplingCartesianIntegerGrid sampling =
					new SamplingCartesianIntegerGrid(new long[dims.length], dims);
			SamplingIterator<IntegerIndex> iter = sampling.iterator();
			while (iter.hasNext()) {
				iter.next(idx);
				setV(idx, zero);
			}
		}
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long[] dims) {
		if (!dimsCompatible(dims))
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(IntegerIndex index, U value) {
		if (index.numDimensions() != rangingDims.length)
			throw new IllegalArgumentException("mismatched dims exception");
		for (int i = 0; i < rangingDims.length; i++) {
			index.set(rangingDims[i], index.get(i));
		}
		if (oob())
			algebra.assign().call(zero, value);
		else
			tensor.v(index, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		if (index.numDimensions() != rangingDims.length)
			throw new IllegalArgumentException("mismatched dims exception");
		for (int i = 0; i < rangingDims.length; i++) {
			index.set(rangingDims[i], index.get(i));
		}
		if (oob()) {
			if (algebra.isNotEqual().call(zero, value))
				throw new IllegalArgumentException("out of bounds nonzero write");
		}
		else
			tensor.setV(index, value);
	}

	@Override
	public StorageConstruction storageType() {
		return tensor.storageType();
	}

	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length != rangingDims.length)
			return false;
		for (int i = 0; i < rangingDims.length; i++) {
			if (newDims[i] != dim)
				return false;
		}
		return true;
	}
	
	private boolean oob() {
		for (int i = 0; i < index.numDimensions(); i++) {
			long v = index.get(i);
			if (v < 0 || v >= tensor.dimension(i))
				return true;
		}
		return false;
	}
}
