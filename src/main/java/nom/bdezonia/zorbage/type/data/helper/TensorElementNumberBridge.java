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
import nom.bdezonia.zorbage.type.algebra.NumberMember;
import nom.bdezonia.zorbage.type.algebra.TensorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorElementNumberBridge<U> implements NumberMember<U> {

	private final TensorMember<U> tensor;
	private final IntegerIndex index;
	
	public TensorElementNumberBridge(TensorMember<U> tensor) {
		this.tensor = tensor;
		this.index = new IntegerIndex(tensor.numDimensions());
	}
	
	public void setIndex(IntegerIndex idx) {
		for (int i = 0; i < index.numDimensions(); i++) {
			index.set(i, 0);
		}
		for (int i = 0; i < idx.numDimensions(); i++) {
			if (i < index.numDimensions())
				index.set(i, idx.get(i));
			else if (idx.get(i) > 0)
				throw new IllegalArgumentException("out of bounds index");
		}
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		return 1;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void v(U value) {
		tensor.v(index, value);
	}

	@Override
	public void setV(U value) {
		tensor.setV(index, value);
	}

}
