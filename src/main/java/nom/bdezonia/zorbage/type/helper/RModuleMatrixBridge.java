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
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleMatrixBridge<U> implements MatrixMember<U> {

	private final U zero;
	private final RModuleMember<U> rmod;
	private boolean isColumn;
	
	public RModuleMatrixBridge(Algebra<?,U> algebra, RModuleMember<U> rmod) {
		this.zero = algebra.construct();
		this.rmod = rmod;
		this.isColumn = true;
	}

	public void setIsColumn(boolean b) {
		this.isColumn = b;
	}
	
	public boolean isColumn() {
		return isColumn;
	}

	public boolean isRow() {
		return !isColumn;
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		else if (d == 0)
			return cols();
		else if (d == 1)
			return rows();
		else
			return 1;
	}

	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public long rows() {
		return isColumn ? rmod.length() : 1;
	}

	@Override
	public long cols() {
		return isColumn ? 1 : rmod.length();
	}

	@Override
	public boolean alloc(long rows, long cols) {
		if (isColumn && rows == rmod.length() && cols == 1)
			return false;
		else if (!isColumn && rows == 1 && cols == rmod.length())
			return false;
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long rows, long cols) {
		if (isColumn && rows == rmod.length() && cols == 1) {
			for (long i = 0; i < rmod.length(); i++)
				rmod.setV(i, zero);
		}
		else if (!isColumn && rows == 1 && cols == rmod.length()) {
			for (long i = 0; i < rmod.length(); i++)
				rmod.setV(i, zero);
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long rows, long cols) {
		if (isColumn && rows == rmod.length() && cols == 1) {
			;
		}
		else if (!isColumn && rows == 1 && cols == rmod.length()) {
			;
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(long r, long c, U value) {
		if (isColumn) {
			if (c != 0) {
				throw new IllegalArgumentException("out of bounds read");
			}
			else
				rmod.getV(r, value);
		}
		else {
			if (r != 0) {
				throw new IllegalArgumentException("out of bounds read");
			}
			else
				rmod.getV(c, value);
		}
	}

	@Override
	public void setV(long r, long c, U value) {
		if (isColumn) {
			if (c != 0) {
				throw new IllegalArgumentException("out of bounds write");
			}
			else
				rmod.setV(r, value);
		}
		else {
			if (r != 0) {
				throw new IllegalArgumentException("out of bounds write");
			}
			else
				rmod.setV(c, value);
		}
	}

	@Override
	public StorageConstruction storageType() {
		return rmod.storageType();
	}

}
