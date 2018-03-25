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

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SubMatrixBridge<U> implements MatrixMember<U>{

	private final Group<?,U> group;
	private final MatrixMember<U> mat;
	private long startRow, countRows, startCol, countCols;
	
	public SubMatrixBridge(Group<?,U> group, MatrixMember<U> mat) {
		this.group = group;
		this.mat = mat;
		this.startRow = 0;
		this.startCol = 0;
		this.countRows = mat.rows();
		this.countCols = mat.cols();
	}
	
	public void setSubrow(long matrixStartRow, long matrixCountRows) {
		if (matrixStartRow < 0)
			throw new IllegalArgumentException("negative start row");
		if (matrixCountRows < 1)
			throw new IllegalArgumentException("non-positive count rows");
		if (matrixStartRow + matrixCountRows > mat.rows())
			throw new IllegalArgumentException("subrow beyond end of matrix row");
		this.startRow = matrixStartRow;
		this.countRows = matrixCountRows;
	}
	
	public void setSubcol(long matrixStartCol, long matrixCountCols) {
		if (matrixStartCol < 0)
			throw new IllegalArgumentException("negative start col");
		if (matrixCountCols < 1)
			throw new IllegalArgumentException("non-positive count cols");
		if (matrixStartCol + matrixCountCols > mat.cols())
			throw new IllegalArgumentException("subcol beyond end of matrix row");
		this.startCol = matrixStartCol;
		this.countCols = matrixCountCols;
	}
	
	@Override
	public long dimension(int d) {
		if (d == 0) return countCols;
		if (d == 1) return countRows;
		return mat.dimension(d);
	}

	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public long rows() {
		return countRows;
	}

	@Override
	public long cols() {
		return countCols;
	}

	@Override
	public boolean alloc(long rows, long cols) {
		if (rows == countRows && cols == countCols)
			return false;
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long rows, long cols) {
		if (rows == countRows && cols == countCols) {
			U zero = group.construct();
			for (long r = 0; r < countRows; r++) {
				for (long c = 0; c < countCols; c++) {
					setV(r, c, zero);
				}
			}
		}
		else {
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
		}
	}

	@Override
	public void reshape(long rows, long cols) {
		if (rows != countRows || cols != countCols) {
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
		}
	}

	@Override
	public void v(long r, long c, U value) {
		mat.v(startRow+r, startCol+c, value);
	}

	@Override
	public void setV(long r, long c, U value) {
		mat.setV(startRow+r, startCol+c, value);
	}

}
