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
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixTensorBridge<U> implements TensorMember<U> {

	private final Group<?,U> group;
	private final U zero;
	private MatrixMember<U> mat;
	private long startRow, startCol, size;
	
	public MatrixTensorBridge(Group<?,U> group, MatrixMember<U> mat) {
		this.mat = mat;
		this.group = group;
		this.zero = group.construct();
		this.startRow = 0;
		this.startCol = 0;
		this.size = Math.min(mat.rows(), mat.cols());
	}
	
	public void setMat(MatrixMember<U> mat, long startRow, long startCol, long size) {
		if (startRow < 0 || startRow >= mat.rows())
			throw new IllegalArgumentException("startRow is of bounds");
		if (startCol < 0 || startCol >= mat.cols())
			throw new IllegalArgumentException("startCol is of bounds");
		if (size < 1)
			throw new IllegalArgumentException("size must be positive");
		if ((startRow + size) >= mat.rows() || (startCol + size) >= mat.cols())
			throw new IllegalArgumentException("dimensions out of bounds");
		this.mat = mat;
		this.startRow = startRow;
		this.startCol = startCol;
		this.size = size;
	}
	
	@Override
	public long dimension(int d) {
		if (d == 0 || d == 1)
			return size;
		// TODO: should I be returning 1 for d > 1?
		// that is not a tensor shape.
		throw new IllegalArgumentException("dimension out of bounds exception");
	}

	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public boolean alloc(long[] dims) {
		if (dimsCompatible(dims)) {
			return false;
		}
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long[] dims) {
		if (dimsCompatible(dims)) {
			for (long r = startRow; r < startRow + size; r++) {
				for (long c = startCol; c < startCol + size; c++) {
					mat.setV(r, c, zero);
				}
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
		for (int i = 2; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				group.assign(zero, value);
				return;
			}
		}
		long c = index.get(0);
		long r = index.get(1);
		mat.v(startRow + r, startCol + c, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		for (int i = 2; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				if (group.isNotEqual(zero, value))
					throw new IllegalArgumentException("out of bounds nonzero write");
			}
		}
		long c = index.get(0);
		long r = index.get(1);
		mat.setV(startRow + r, startCol + c, value);
	}

	@Override
	public StorageConstruction storageType() {
		return mat.storageType();
	}

	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length < 2) return false;
		for (int i = 2; i < newDims.length; i++) {
			if (newDims[i] != 1) return false;
		}
		if (newDims[0] != size) return false;
		if (newDims[1] != size) return false;
		return true;
	}
}
