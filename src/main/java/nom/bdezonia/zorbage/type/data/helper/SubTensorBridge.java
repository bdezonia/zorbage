/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.helper;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.TensorMember;

//TODO: support a subrange of tensor block as a tensor
//  Not necessary for matrices and rmods because we have sub bridges for
//  those. But the subtensor bridge will be only way to get smaller.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SubTensorBridge<U> implements TensorMember<U> {

	private final Group<?,U> group;
	private final U zero;
	private final TensorMember<U> tensor;
	private IntegerIndex fixedDims;
	private int[] rangingDims;

	public SubTensorBridge(Group<?,U> group, TensorMember<U> tensor) {
		if (tensor.numDimensions() < 2)
			throw new IllegalArgumentException("subtensor can only be constructed on tensor with 2 or more dimensions");
		this.group = group;
		this.zero = group.construct();
		this.tensor = tensor;
		this.fixedDims = new IntegerIndex(tensor.numDimensions());
		for (int i = 0; i < tensor.numDimensions(); i++) {
			fixedDims.set(i, tensor.dimension(i));
		}
		this.rangingDims = new int[1];
	}

	public void setSubtensor(int[] rangingDims) {
		if ((rangingDims.length < 1) || (rangingDims.length >= fixedDims.numDimensions()))
			throw new IllegalArgumentException("subtensor is not a subset of parent tensor");
		for (int i = 0; i < rangingDims.length; i++) {
			int v = rangingDims[i];
			if (v < 0 || v >= fixedDims.numDimensions())
				throw new IllegalArgumentException("subdimension out of bounds");
			for (int j = i+1; j < rangingDims.length; j++) {
				if (rangingDims[j] == v)
					throw new IllegalArgumentException("dim "+v+" specified more than once");
			}
		}
		this.rangingDims = rangingDims.clone();
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		if (d >= rangingDims.length)
			throw new IllegalArgumentException("dimension out of bounds exception");
		return fixedDims.get(rangingDims[d]);
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
			fixedDims.set(rangingDims[i], index.get(i));
		}
		if (oob())
			group.assign(zero, value);
		else
			tensor.v(fixedDims, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		if (index.numDimensions() != rangingDims.length)
			throw new IllegalArgumentException("mismatched dims exception");
		for (int i = 0; i < rangingDims.length; i++) {
			fixedDims.set(rangingDims[i], index.get(i));
		}
		if (oob()) {
			if (group.isNotEqual(zero, value))
				throw new IllegalArgumentException("out of bounds nonzero write");
		}
		else
			tensor.setV(fixedDims, value);
	}

	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length != rangingDims.length) return false;
		for (int i = 0; i < rangingDims.length; i++) {
			if (newDims[i] != fixedDims.get(rangingDims[i]))
				return false;
		}
		for (int i = rangingDims.length; i < newDims.length; i++) {
			if (newDims[i] != 1)
				return false;
		}
		return true;
	}
	
	private boolean oob() {
		for (int i = 0; i < fixedDims.numDimensions(); i++) {
			long v = fixedDims.get(i);
			if (v < 0 || v >= tensor.dimension(i))
				return true;
		}
		return false;
	}
}
