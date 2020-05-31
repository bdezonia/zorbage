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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixDiagonalRModuleBridge<U> implements RModuleMember<U> {

	public enum Origin {UpperLeft, UpperRight, LowerRight, LowerLeft};
	
	private final U zero;
	private final MatrixMember<U> mat;
	private Origin origin;
	private long diagNumber;
	
	public MatrixDiagonalRModuleBridge(Algebra<?,U> algebra, MatrixMember<U> mat) {
		this.zero = algebra.construct();
		this.mat = mat;
		this.origin = Origin.UpperLeft;
		this.diagNumber = 0;
	}
	
	public void setDiagonal(Origin origin, long diagNumber) {
		this.origin = origin;
		this.diagNumber = diagNumber;
		if (length() <= 0)
			throw new IllegalArgumentException("diagonal number is outside allowed range");
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension query");
		if (d == 0)
			return length();
		return 1;
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public long length() {
		return Math.min(mat.rows(), mat.cols()) - Math.abs(diagNumber);
	}

	@Override
	public boolean alloc(long len) {
		if (len == length())
			return false;
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long len) {
		if (len == length()) {
			for (long i = 0; i < len; i++) {
				setV(i, zero);
			}
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long len) {
		if (len != length())
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(long i, U value) {
		if (i < 0 || i >= length())
			throw new IllegalArgumentException("diagonal indexed out of bounds");
		long r = row(i);
		long c = col(i);
		mat.getV(r, c, value);
	}

	@Override
	public void setV(long i, U value) {
		if (i < 0 || i >= length())
			throw new IllegalArgumentException("diagonal indexed out of bounds");
		long r = row(i);
		long c = col(i);
		mat.setV(r, c, value);
	}
	
	long row(long i) {
		switch (origin) {
		case UpperLeft:
			if (diagNumber < 0)
				return i - diagNumber;
			else
				return i;
		case LowerRight:
			if (diagNumber < 0)
				return mat.rows() - 1 - i + diagNumber;
			else
				return mat.rows() - 1 - i;
		case UpperRight:			
			if (diagNumber < 0)
				return i;
			else
				return i + diagNumber;
		default: // LowerLeft
			if (diagNumber < 0)
				return mat.rows() - 1 - i;
			else
				return mat.rows() - 1 - i - diagNumber;
		}
	}

	long col(long i) {
		switch (origin) {
		case UpperLeft:
			if (diagNumber < 0)
				return i;
			else
				return i + diagNumber;
		case LowerRight:
			if (diagNumber < 0)
				return mat.cols() - 1 - i;
			else
				return mat.cols() - 1 - i - diagNumber;
		case UpperRight:			
			if (diagNumber < 0)
				return mat.cols() - 1 - i + diagNumber;
			else
				return mat.cols() - 1 - i;
		default: // LowerLeft
			if (diagNumber < 0)
				return i - diagNumber;
			else
				return i;
		}
	}

	@Override
	public StorageConstruction storageType() {
		return mat.storageType();
	}
}
