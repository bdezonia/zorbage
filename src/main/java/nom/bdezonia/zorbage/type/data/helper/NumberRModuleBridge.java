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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.NumberMember;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NumberRModuleBridge<U> implements RModuleMember<U>{

	private final U zero;
	private final Algebra<?,U> group;
	private NumberMember<U> num;
	
	public NumberRModuleBridge(Algebra<?,U> group, NumberMember<U> num) {
		this.zero = group.construct();
		this.group = group;
		this.num = num;
	}
	
	public void setNum(NumberMember<U> num) {
		this.num = num;
	}
	
	@Override
	public long dimension(int d) {
		return 1;
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public long length() {
		return 1;
	}

	@Override
	public boolean alloc(long len) {
		if (len == 1)
			return false;
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long len) {
		if (len == 1)
			num.setV(zero);
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long len) {
		if (len != 1)
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(long i, U value) {
		if (i == 0)
			num.v(value);
		else
			group.assign().call(zero, value);
	}

	@Override
	public void setV(long i, U value) {
		if (i == 0)
			num.setV(value);
		else if (group.isNotEqual().call(zero, value))
			throw new IllegalArgumentException("out of bounds nonzero write");

	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}
}
