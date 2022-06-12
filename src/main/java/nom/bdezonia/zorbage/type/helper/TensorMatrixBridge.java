/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.helper;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorMatrixBridge<U> implements MatrixMember<U> {

	// Note - the handling of the index for this class is not threadsafe
	
	private final U zero;
	private final TensorMember<U> tensor;
	private final IntegerIndex fixedDims;
	private int rangingDimR;
	private int rangingDimC;

	public TensorMatrixBridge(Algebra<?,U> algebra, TensorMember<U> tensor) {
		if (tensor.numDimensions() < 2)
			throw new IllegalArgumentException();
		this.zero = algebra.construct();
		this.tensor = tensor;
		this.fixedDims = new IntegerIndex(tensor.numDimensions());
		this.rangingDimC = 0;
		this.rangingDimR = 0;
	}
	
	public void setMatrix(int rangeR, int rangeC, IntegerIndex fixed) {
		if (fixed.numDimensions() + 2 != fixedDims.numDimensions())
			throw new IllegalArgumentException("mismatched dimensions");
		if (rangeR < 0 || rangeR >= fixedDims.numDimensions())
			throw new IllegalArgumentException("row dim out of range");
		if (rangeC < 0 || rangeC >= fixedDims.numDimensions())
			throw new IllegalArgumentException("col dim out of range");
		if (rangeR == rangeC)
			throw new IllegalArgumentException("cannot specify same dim twice");
		rangingDimR = rangeR;
		rangingDimC = rangeC;
		int tmp = 0;
		for (int i = 0; i < fixedDims.numDimensions(); i++) {
			if (i != rangeR && i != rangeC) {
				fixedDims.set(i, fixed.get(tmp++));
			}
		}
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
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long rows, long cols) {
		if (rows == rows() && cols == cols()) {
			for (long r = 0; r < rows; r++) {
				for (long c = 0; c < cols; c++) {
					setV(r, c, zero);
				}
			}
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long rows, long cols) {
		if (rows != rows() && cols != cols())
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(long r, long c, U value) {
		if (r < 0 || r >= rows() || c < 0 || c >= cols())
			throw new IllegalArgumentException("out of bounds read");
		else {
			fixedDims.set(rangingDimR, r);
			fixedDims.set(rangingDimC, c);
			tensor.getV(fixedDims, value);
		}
	}

	@Override
	public void setV(long r, long c, U value) {
		if (r < 0 || r >= rows() || c < 0 || c >= cols()) {
			throw new IllegalArgumentException("out of bounds write");
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
