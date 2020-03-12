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
package nom.bdezonia.zorbage.type.data.helper;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleTensorBridge<U> implements TensorMember<U> {

	private final Algebra<?,U> algebra;
	private final U zero;
	private final RModuleMember<U> rmod;
	
	public RModuleTensorBridge(Algebra<?,U> algebra, RModuleMember<U> rmod) {
		this.algebra = algebra;
		this.zero = algebra.construct();
		this.rmod = rmod;
	}
	
	@Override
	public long dimension(int d) {
		return rmod.dimension(d);
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public boolean alloc(long[] dims) {
		if (dimsCompatible(dims)) {
			return false;
		}
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long[] dims) {
		if (dimsCompatible(dims)) {
			for (long i = 0; i < rmod.length(); i++) {
				rmod.setV(i, zero);
			}
		}
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long[] dims) {
		if (!dimsCompatible(dims))
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(IntegerIndex index, U value) {
		for (int i = 1; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				algebra.assign().call(zero, value);
				return;
			}
		}
		long i = index.get(0);
		rmod.v(i, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		for (int i = 1; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				if (algebra.isNotEqual().call(zero, value))
					throw new IllegalArgumentException("out of bounds nonzero write");
			}
		}
		long i = index.get(0);
		rmod.setV(i, value);
	}

	@Override
	public StorageConstruction storageType() {
		return rmod.storageType();
	}

	@Override
	public int rank() {
		return 1;
	}
	
	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length < 1) return false;
		for (int i = 1; i < newDims.length; i++) {
			if (newDims[i] != 1) return false;
		}
		if (newDims[0] != rmod.length()) return false;
		return true;
	}
}
