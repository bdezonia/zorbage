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
import nom.bdezonia.zorbage.type.algebra.RModuleMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixColumnRModuleBridge<U> implements RModuleMember<U> {

	private final Group<?,U> group;
	private final MatrixMember<U> mat;
	private long col;
	
	public MatrixColumnRModuleBridge(Group<?,U> numGroup, MatrixMember<U> mat, long col) {
		this.group = numGroup;
		this.mat = mat;
		this.col = col;
		if (col < 0 || col >= mat.cols())
			throw new IllegalArgumentException("specified column outside bounds of matrix");
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
		return mat.rows();
	}

	@Override
	public boolean alloc(long len) {
		if (len == mat.rows()) {
			return false;
		}
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long len) {
		if (len == mat.rows()) {
			U zero = group.construct();
			for (long i = 0; i < length(); i++)
				setV(i, zero);
		}
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long len) {
		if (len != mat.rows())
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(long i, U value) {
		mat.v(i, col, value);
	}

	@Override
	public void setV(long i, U value) {
		mat.v(i, col, value);
	}

	
}
