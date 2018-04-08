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


import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixTensorBridge<U> implements TensorMember<U> {

	private final MatrixMember<U> mat;
	private final Group<?,U> group;
	private final U zero;
	
	public MatrixTensorBridge(Group<?,U> group, MatrixMember<U> mat) {
		this.mat = mat;
		this.group = group;
		this.zero = group.construct();
	}
	
	@Override
	public long dimension(int d) {
		return mat.dimension(d);
	}

	@Override
	public int numDimensions() {
		return 2;
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
			for (long r = 0; r < mat.rows(); r++) {
				for (long c = 0; c < mat.cols(); c++) {
					mat.setV(r, c, zero);
				}
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
		for (int i = 2; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				group.assign(zero, value);
				return;
			}
		}
		long c = index.get(0);
		long r = index.get(1);
		mat.v(r, c, value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		for (int i = 2; i < index.numDimensions(); i++) {
			if (index.get(i) != 0) {
				if (group.isNotEqual(zero, value))
					throw new IllegalArgumentException("out of bounds nonzero write");
			}
		}
		long c = index.get(0);
		long r = index.get(1);
		mat.setV(r, c, value);
	}

	private boolean dimsCompatible(long[] newDims) {
		if (newDims.length < 2) return false;
		for (int i = 2; i < newDims.length; i++) {
			if (newDims[i] != 1) return false;
		}
		if (newDims[0] != mat.cols()) return false;
		if (newDims[1] != mat.rows()) return false;
		return true;
	}
}
