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

import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.NumberMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixElementNumberBridge<U> implements NumberMember<U> {

	private final MatrixMember<U> mat;
	private long row;
	private long col;
	
	public MatrixElementNumberBridge(MatrixMember<U> mat) {
		this.mat = mat;
		this.row = 0;
		this.col = 0;
	}

	public void setRowCol(long r, long c) {
		if (r < 0 || r >= mat.rows() || c < 0 || c >= mat.cols())
			throw new IllegalArgumentException("number bridge set outside matrix bounds");
		row = r;
		col = c;
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension query");
		return 1;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(U value) {
		mat.getV(row, col, value);
	}

	@Override
	public void setV(U value) {
		mat.setV(row, col, value);
	}
}
