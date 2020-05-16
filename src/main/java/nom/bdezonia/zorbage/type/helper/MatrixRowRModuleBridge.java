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
public class MatrixRowRModuleBridge<U> implements RModuleMember<U> {

	private final U zero;
	private final MatrixMember<U> mat;
	private long row;
	
	public MatrixRowRModuleBridge(Algebra<?,U> algebra, MatrixMember<U> mat) {
		this.zero = algebra.construct();
		this.mat = mat;
		this.row = 0;
	}

	public void setRow(long row) {
		if (row < 0 || row >= mat.rows())
			throw new IllegalArgumentException("specified row outside bounds of matrix");
		this.row = row;
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension query");
		if (d == 0) return length();
		return 1;
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public long length() {
		return mat.cols();
	}

	@Override
	public boolean alloc(long len) {
		if (len == mat.cols()) {
			return false;
		}
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long len) {
		if (len == mat.cols()) {
			for (long i = 0; i < length(); i++)
				setV(i, zero);
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long len) {
		if (len != mat.cols())
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(long i, U value) {
		mat.v(row, i, value);
	}

	@Override
	public void setV(long i, U value) {
		mat.setV(row, i, value);
	}

	@Override
	public StorageConstruction storageType() {
		return mat.storageType();
	}
}
