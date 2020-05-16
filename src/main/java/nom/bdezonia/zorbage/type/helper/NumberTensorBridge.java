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
package nom.bdezonia.zorbage.type.helper;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NumberTensorBridge<U> implements TensorMember<U> {

	private final U zero;
	private NumberMember<U> num;
	private long dimension;
	
	public NumberTensorBridge(Algebra<?,U> algebra, NumberMember<U> num, long dimension) {
		this.zero = algebra.construct();
		this.num = num;
		this.dimension = dimension;
	}
	
	public void setDimension(long dimension) {
		this.dimension = dimension;
	}

	@Override
	public long dimension() { return dimension; }
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		return 1;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public boolean alloc(long[] dims) {
		if (dimsCompatible(dims))
			return false;
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long[] dims) {
		if (dimsCompatible(dims))
			num.setV(zero);
		else
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void reshape(long[] dims) {
		if (!dimsCompatible(dims))
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void v(IntegerIndex index, U value) {
		for (int i = 0; i < index.numDimensions(); i++) {
			if (index.get(i) != 0)
				throw new IllegalArgumentException("out of bounds read");
		}
		num.v(value);
	}

	@Override
	public void setV(IntegerIndex index, U value) {
		for (int i = 0; i < index.numDimensions(); i++) {
			if (index.get(i) != 0)
				throw new IllegalArgumentException("out of bounds write");
		}
		num.setV(value);
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}

	@Override
	public int rank() { return lowerRank() + upperRank(); }
	
	@Override
	public int lowerRank() { return 0; }
	
	@Override
	public int upperRank() { return 0; }
	
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

	private boolean dimsCompatible(long[] newDims) {
		for (int i = 0; i < newDims.length; i++) {
			if (newDims[i] != 1)
				return false;
		}
		return true;
	}
}
