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
package nom.bdezonia.zorbage.type.helper;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SubTensorBridge<U> implements TensorMember<U> {

	private final TensorMember<U> tensor;
	private final int[] rangingDims;
	private final long[] rangingOffsets;
	private final U zero;
	private final IntegerIndex index;

	public SubTensorBridge(Algebra<?,U> algebra, TensorMember<U> tensor, int[] rangingDims, long[] fixedDimValues) {
		this.tensor = tensor;
		this.zero = algebra.construct();
		this.index = new IntegerIndex(tensor.rank());
		this.rangingDims = new int[rangingDims.length];
		this.rangingOffsets = new long[rangingDims.length];
		setDims(rangingDims, rangingOffsets, fixedDimValues);
	}
	
	// checks subdims fit inside given tensor

	private void checkDims(int[] rangingDims, long[] rangingOffsets, long[] fixedDimValues) {
		if (rangingDims.length != rangingOffsets.length)
			throw new IllegalArgumentException("ranging parameters do not match in length");
		if (rangingDims.length + fixedDimValues.length != tensor.rank())
			throw new IllegalArgumentException("subtensor dims don't fit within parent tensor");
		for (int i = 0; i < rangingDims.length; i++) {
			if (rangingDims[i] < 0 || rangingDims[i] >= tensor.rank())
				throw new IllegalArgumentException("rangingDim["+i+"] is out of parent tensor's bounds");
			for (int j = 0; j < i; j++) {
				if (rangingDims[i] == rangingDims[j])
					throw new IllegalArgumentException("subtensor repeats a ranging dimension of the parent tensor");
			}
		}
		for (int i = 0; i < fixedDimValues.length; i++) {
			if (fixedDimValues[i] < 0 || fixedDimValues[i] >= tensor.dimension())
				throw new IllegalArgumentException("fixedDim["+i+"] is out of parent tensor's bounds");
		}
	}
	
	public void setDims(int[] rangingDims, long[] rangingOffsets, long[] fixedDimValues) {
		checkDims(rangingDims, rangingOffsets, fixedDimValues);
		for (int i = 0; i < rangingDims.length; i++) {
			this.rangingDims[i] = rangingDims[i];
			this.rangingOffsets[i] = rangingOffsets[i];
		}
		int fIdx = 0;
		int found = 0;
		for (int i = 0; i < rangingDims.length; i++) {
			int v = rangingDims[i];
			while (fIdx < v) {
				index.set(fIdx, fixedDimValues[found]);
				found++;
				fIdx++;
			}
			index.set(fIdx, rangingOffsets[i]);
			fIdx++;
		}
		for (int i = fIdx; i < tensor.rank(); i++) {
			index.set(i, fixedDimValues[found]);
			found++;
		}
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		if (d >= rangingDims.length)
			throw new IllegalArgumentException("dimension out of bounds exception");
		return tensor.dimension();
	}

	@Override
	public int numDimensions() {
		return rangingDims.length;
	}

	@Override
	public boolean alloc(long[] dims) {
		if (dimsCompatible(dims))
			return false;
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long[] dims) {
		if (dimsCompatible(dims)) {
			IntegerIndex idx = new IntegerIndex(dims.length);
			long[] minPt = new long[dims.length];
			long[] maxPt = new long[dims.length];
			for (int i = 0; i < dims.length; i++) {
				maxPt[i] = dims[i]-1;
			}
			SamplingCartesianIntegerGrid sampling =
					new SamplingCartesianIntegerGrid(minPt,maxPt);
			SamplingIterator<IntegerIndex> iter = sampling.iterator();
			while (iter.hasNext()) {
				iter.next(idx);
				setV(idx, zero);
			}
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long[] dims) {
		if (!dimsCompatible(dims))
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(IntegerIndex index, U value) {
		if (index.numDimensions() != rangingDims.length)
			throw new IllegalArgumentException("mismatched dims exception");
		for (int i = 0; i < rangingDims.length; i++) {
			this.index.set(rangingDims[i], index.get(i) + rangingOffsets[i]);
		}
		if (oob())
			throw new IllegalArgumentException("out of bounds read");
		else
			tensor.getV(this.index, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		if (index.numDimensions() != rangingDims.length)
			throw new IllegalArgumentException("mismatched dims exception");
		for (int i = 0; i < rangingDims.length; i++) {
			this.index.set(rangingDims[i], index.get(i) + rangingOffsets[i]);
		}
		if (oob())
			throw new IllegalArgumentException("out of bounds write");
		else
			tensor.setV(this.index, value);
	}

	@Override
	public StorageConstruction storageType() {
		return tensor.storageType();
	}

	@Override
	public int rank() { return rangingDims.length; }
	
	@Override
	public int lowerRank() {
		int count = 0;
		for (int i = 0; i < rank(); i++) {
			if (indexIsLower(i))
				count++;
		}
		return count;
	}
	
	@Override
	public int upperRank() {
		int count = 0;
		for (int i = 0; i < rank(); i++) {
			if (indexIsUpper(i))
				count++;
		}
		return count;
	}
	
	@Override
	public boolean indexIsLower(int index) {
		if (index < 0 || index >= rangingDims.length)
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return tensor.indexIsLower(rangingDims[index]);
	}
	
	@Override
	public boolean indexIsUpper(int index) {
		if (index < 0 || index >= rangingDims.length)
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return tensor.indexIsUpper(rangingDims[index]);
	}

	@Override
	public long dimension() {
		return tensor.dimension();
	}
	
	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length != rangingDims.length)
			return false;
		for (int i = 0; i < rangingDims.length; i++) {
			if (newDims[i] != rangingDims.length)
				return false;
		}
		return true;
	}
	
	private boolean oob() {
		for (int i = 0; i < index.numDimensions(); i++) {
			long v = index.get(i);
			if (v < 0 || v >= tensor.dimension())
				return true;
		}
		return false;
	}
}
