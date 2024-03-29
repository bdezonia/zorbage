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
public class TensorRModuleBridge<U> implements RModuleMember<U> {

	// Note - the handling of the index for this class is not threadsafe
	
	private final U zero;
	private final TensorMember<U> tensor;
	private final IntegerIndex fixedDims;
	private int rangingDim;

	public TensorRModuleBridge(Algebra<?,U> algebra, TensorMember<U> tensor) {
		this.zero = algebra.construct();
		this.tensor = tensor;
		this.fixedDims = new IntegerIndex(tensor.numDimensions());
		this.rangingDim = 0;
	}
	
	public void setRmodule(int rangeDim, IntegerIndex fixed) {
		if (fixed.numDimensions() + 1 != fixedDims.numDimensions())
			throw new IllegalArgumentException("mismatched dimensions");
		if (rangeDim < 0 || rangeDim >= fixedDims.numDimensions())
			throw new IllegalArgumentException("dim out of range");
		this.rangingDim = rangeDim;
		int tmp = 0;
		for (int i = 0; i < fixedDims.numDimensions(); i++) {
			if (i != rangeDim) {
				fixedDims.set(i, fixed.get(tmp++));
			}
		}
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
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
		return tensor.dimension(rangingDim);
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
			for (long i = 0; i < len; i++)
				setV(i, zero);
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
		if (i < 0 || i >= length()) {
			throw new IllegalArgumentException("out of bounds read");
		}
		else {
			fixedDims.set(rangingDim, i);
			tensor.getV(fixedDims, value);
		}
	}

	@Override
	public void setV(long i, U value) {
		if (i < 0 || i >= length()) {
			throw new IllegalArgumentException("out of bounds write");
		}
		else {
			fixedDims.set(rangingDim, i);
			tensor.setV(fixedDims, value);
		}
	}

	@Override
	public StorageConstruction storageType() {
		return tensor.storageType();
	}
	
	@Override
	public boolean accessWithOneThread() {
		return tensor.accessWithOneThread();
	}
}
