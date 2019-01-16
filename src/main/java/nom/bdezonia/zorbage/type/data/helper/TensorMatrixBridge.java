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
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorMatrixBridge<U> implements MatrixMember<U> {

	private final Algebra<?,U> group;
	private final U zero;
	private final TensorMember<U> tensor;
	private final IntegerIndex fixedDims;
	private int rangingDimR;
	private int rangingDimC;

	public TensorMatrixBridge(Algebra<?,U> group, TensorMember<U> tensor) {
		if (tensor.numDimensions() < 2)
			throw new IllegalArgumentException();
		this.group = group;
		this.zero = group.construct();
		this.tensor = tensor;
		this.fixedDims = new IntegerIndex(tensor.numDimensions());
		this.rangingDimC = 0;
		this.rangingDimR = 0;
	}
	
	public void setMatrix(IntegerIndex idx, int rDim, int cDim) {
		if (idx.numDimensions() != fixedDims.numDimensions())
			throw new IllegalArgumentException("mismatched dimensions");
		if (rDim < 0 || rDim >= fixedDims.numDimensions())
			throw new IllegalArgumentException("row dim out of range");
		if (cDim < 0 || cDim >= fixedDims.numDimensions())
			throw new IllegalArgumentException("col dim out of range");
		if (rDim == cDim)
			throw new IllegalArgumentException("cannot specify same dim twice");
		rangingDimR = rDim;
		rangingDimC = cDim;
		for (int i = 0; i < idx.numDimensions(); i++)
			fixedDims.set(i, idx.get(i));
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		if (d == 0) return cols();
		if (d == 1) return rows();
		return 1;
	}

	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public long rows() {
		return tensor.dimension(rangingDimR);
	}

	@Override
	public long cols() {
		return tensor.dimension(rangingDimC);
	}

	@Override
	public boolean alloc(long rows, long cols) {
		if (rows == rows() && cols == cols())
			return false;
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long rows, long cols) {
		if (rows == rows() && cols == cols()) {
			for (long r = 0; r < rows; r++) {
				for (long c = 0; c < cols; c++) {
					setV(r,c,zero);
				}
			}
		}
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long rows, long cols) {
		if (rows != rows() && cols != cols())
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(long r, long c, U value) {
		if (r < 0 || r >= rows() || c < 0 || c >= cols())
			group.assign().call(zero, value);
		else {
			fixedDims.set(rangingDimR, r);
			fixedDims.set(rangingDimC, c);
			tensor.v(fixedDims, value);
		}
	}

	@Override
	public void setV(long r, long c, U value) {
		if (r < 0 || r >= rows() || c < 0 || c >= cols()) {
			if (group.isNotEqual().call(zero, value))
				throw new IllegalArgumentException("out of bounds nonzero write");
		}
		else {
			fixedDims.set(rangingDimR, r);
			fixedDims.set(rangingDimC, c);
			tensor.setV(fixedDims, value);
		}
	}

	@Override
	public StorageConstruction storageType() {
		return tensor.storageType();
	}

}
