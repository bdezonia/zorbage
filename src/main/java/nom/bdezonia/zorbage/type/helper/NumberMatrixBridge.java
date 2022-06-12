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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NumberMatrixBridge<U> implements MatrixMember<U> {

	private final U zero;
	private final NumberMember<U> num;
	
	public NumberMatrixBridge(Algebra<?,U> algebra, NumberMember<U> num) {
		this.zero = algebra.construct();
		this.num = num;
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		return 1;
	}

	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public long rows() {
		return 1;
	}

	@Override
	public long cols() {
		return 1;
	}

	@Override
	public boolean alloc(long rows, long cols) {
		if (rows == 1 && cols == 1)
			return false;
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long rows, long cols) {
		if (rows == 1 && cols == 1)
			num.setV(zero);
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long rows, long cols) {
		if (rows != 1 || cols != 1)
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(long r, long c, U value) {
		if (r == 0 && c == 0)
			num.getV(value);
		else
			throw new IllegalArgumentException("out of bounds read");
	}

	@Override
	public void setV(long r, long c, U value) {
		if (r == 0 && c == 0)
			num.setV(value);
		else
			throw new IllegalArgumentException("out of bounds write");
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}

}
