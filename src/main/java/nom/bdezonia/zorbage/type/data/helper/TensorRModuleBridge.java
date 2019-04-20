/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
public class TensorRModuleBridge<U> implements RModuleMember<U> {

	private final Algebra<?,U> algebra;
	private final U zero;
	private final TensorMember<U> tensor;
	private final IntegerIndex fixedDims;
	private int rangingDim;

	public TensorRModuleBridge(Algebra<?,U> algebra, TensorMember<U> tensor) {
		this.algebra = algebra;
		this.zero = algebra.construct();
		this.tensor = tensor;
		this.fixedDims = new IntegerIndex(tensor.numDimensions());
		this.rangingDim = 0;
	}
	
	public void setRmodule(IntegerIndex idx, int dim) {
		if (idx.numDimensions() != fixedDims.numDimensions())
			throw new IllegalArgumentException("mismatched dimensions");
		if (dim < 0 || dim >= fixedDims.numDimensions())
			throw new IllegalArgumentException("dim out of range");
		this.rangingDim = dim;
		for (int i = 0; i < idx.numDimensions(); i++)
			fixedDims.set(i, idx.get(i));
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
		throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long len) {
		if (len == length()) {
			for (long i = 0; i < len; i++)
				setV(i, zero);
		}
		else
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long len) {
		if (len != length())
			throw new UnsupportedOperationException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(long i, U value) {
		if (i < 0 || i >= length())
			algebra.assign().call(zero, value);
		else {
			fixedDims.set(rangingDim, i);
			tensor.v(fixedDims, value);
		}
	}

	@Override
	public void setV(long i, U value) {
		if (i < 0 || i >= length()) {
			if (algebra.isNotEqual().call(zero, value))
				throw new IllegalArgumentException("out of bounds nonzero write");
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

}
