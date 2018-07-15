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
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleMatrixBridge<U> implements MatrixMember<U> {

	private final Group<?,U> group;
	private final U zero;
	private final RModuleMember<U> rmod;
	private boolean isColumn;
	
	public RModuleMatrixBridge(Group<?,U> group, RModuleMember<U> rmod) {
		this.group = group;
		this.zero = group.construct();
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
		return isColumn ? 1 : rmod.length();
	}

	@Override
	public long cols() {
		return isColumn ? rmod.length() : 1;
	}

	@Override
	public boolean alloc(long rows, long cols) {
		if (isColumn && rows == 1 && cols == rmod.length())
			return false;
		else if (!isColumn && rows == rmod.length() && cols == 1)
			return false;
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long rows, long cols) {
		if (isColumn && rows == 1 && cols == rmod.length()) {
			for (long i = 0; i < rmod.length(); i++)
				rmod.setV(i, zero);
		}
		else if (!isColumn && rows == rmod.length() && cols == 1) {
			for (long i = 0; i < rmod.length(); i++)
				rmod.setV(i, zero);
		}
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long rows, long cols) {
		if (isColumn && rows == 1 && cols == rmod.length()) {
			;
		}
		else if (!isColumn && rows == rmod.length() && cols == 1) {
			;
		}
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(long r, long c, U value) {
		if (isColumn) {
			if (r != 0)
				group.assign().call(zero, value);
			else
				rmod.v(c, value);
		}
		else {
			if (c != 0)
				group.assign().call(zero, value);
			else
				rmod.v(r, value);
		}
	}

	@Override
	public void setV(long r, long c, U value) {
		if (isColumn) {
			if (r != 0 && group.isNotEqual().call(zero, value))
				throw new IllegalArgumentException("out of bounds nonzero write");
			else
				rmod.setV(c, value);
		}
		else {
			if (c != 0 && group.isNotEqual().call(zero, value))
				throw new IllegalArgumentException("out of bounds nonzero write");
			else
				rmod.setV(r, value);
		}
	}

	@Override
	public StorageConstruction storageType() {
		return rmod.storageType();
	}

}
