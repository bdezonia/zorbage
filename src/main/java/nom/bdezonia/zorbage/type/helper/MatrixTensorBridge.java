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
public class MatrixTensorBridge<U> implements TensorMember<U> {

	private final U zero;
	private MatrixMember<U> mat;
	private long startRow, startCol, size;
	
	public MatrixTensorBridge(Algebra<?,U> algebra, MatrixMember<U> mat) {
		this.mat = mat;
		this.zero = algebra.construct();
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
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		if (d == 0 || d == 1)
			return size;
		return 1;
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
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
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
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long[] dims) {
		if (!dimsCompatible(dims))
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(IntegerIndex index, U value) {
		for (int i = 2; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				throw new IllegalArgumentException("out of bounds read");
			}
		}
		long c = index.get(0);
		long r = index.get(1);
		mat.getV(startRow + r, startCol + c, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		for (int i = 2; i < index.numDimensions(); i++) {
			if (index.get(i) != 0)
				throw new IllegalArgumentException("out of bounds write");
		}
		long c = index.get(0);
		long r = index.get(1);
		mat.setV(startRow + r, startCol + c, value);
	}

	@Override
	public StorageConstruction storageType() {
		return mat.storageType();
	}

	@Override
	public int rank() { return lowerRank() + upperRank(); }
	
	@Override
	public int lowerRank() { return 2; }
	
	@Override
	public int upperRank() { return 0; }
	
	@Override
	public boolean indexIsLower(int index) {
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return true;
	}
	
	@Override
	public boolean indexIsUpper(int index) {
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return false;
	}

	@Override
	public long dimension() { return size; }
	
	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length < 2) return false;
		for (int i = 2; i < newDims.length; i++) {
			if (newDims[i] != 1) return false;
		}
		if (newDims[0] != size) return false;
		if (newDims[1] != size) return false;
		return true;
	}
	
	@Override
	public boolean accessWithOneThread() {
		return mat.accessWithOneThread();
	}
}
