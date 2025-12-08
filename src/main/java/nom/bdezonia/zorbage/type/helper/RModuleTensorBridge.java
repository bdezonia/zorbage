/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RModuleTensorBridge<U> implements TensorMember<U> {

	private final U zero;
	private final RModuleMember<U> rmod;
	
	public RModuleTensorBridge(Algebra<?,U> algebra, RModuleMember<U> rmod) {
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
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long[] dims) {
		if (dimsCompatible(dims)) {
			for (long i = 0; i < rmod.length(); i++) {
				rmod.setV(i, zero);
			}
		}
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long[] dims) {
		if (!dimsCompatible(dims))
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void getV(IntegerIndex index, U value) {
		for (int i = 1; i < index.numDimensions(); i++) {
			if (index.get(i) != 0)
				throw new IllegalArgumentException("out of bounds read");
		}
		long i = index.get(0);
		rmod.getV(i, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		for (int i = 1; i < index.numDimensions(); i++) {
			if (index.get(i) != 0)
				throw new IllegalArgumentException("out of bounds write");
		}
		long i = index.get(0);
		rmod.setV(i, value);
	}

	@Override
	public StorageConstruction storageType() {
		return rmod.storageType();
	}

	@Override
	public int rank() { return lowerRank() + upperRank(); }
	
	@Override
	public int lowerRank() {
		return 1;
	}
	
	@Override
	public int upperRank() {
		return 0;
	}
	
	@Override
	public boolean indexIsLower(int index) {
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return true;
	}
	
	@Override
	public boolean indexIsUpper(int index) {
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return false;
	}

	@Override
	public long dimension() { return rmod.length(); }
	
	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length < 1) return false;
		for (int i = 1; i < newDims.length; i++) {
			if (newDims[i] != 1) return false;
		}
		if (newDims[0] != rmod.length()) return false;
		return true;
	}
	
	@Override
	public boolean accessWithOneThread() {
		return rmod.accessWithOneThread();
	}
}
