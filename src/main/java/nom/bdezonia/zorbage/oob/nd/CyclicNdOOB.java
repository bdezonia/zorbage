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
package nom.bdezonia.zorbage.oob.nd;

import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CyclicNdOOB<U> implements Procedure2<IntegerIndex,U> {

	private final DimensionedDataSource<U> ds;
	private final ThreadLocal<IntegerIndex> coord;

	/**
	 * 
	 * @param d
	 */
	public CyclicNdOOB(DimensionedDataSource<U> d) {
		this.ds = d;
		this.coord = new ThreadLocal<IntegerIndex>() {
			@Override
			protected IntegerIndex initialValue() {
				return new IntegerIndex(ds.numDimensions());
			}
		};
	}

	@Override
	public void call(IntegerIndex index, U value) {
		if (index.numDimensions() != ds.numDimensions())
			throw new IllegalArgumentException("index does not have same num dims as dataset");
		IntegerIndex tmp = coord.get();
		boolean oob = false;
		for (int i = 0; i < ds.numDimensions(); i++) {
			long val = index.get(i);
			if (val < 0) {
				long idx = ds.dimension(i) - 1 - (((-val) - 1) % ds.dimension(i));
				tmp.set(i, idx);
				oob = true;
			}
			else if (val >= ds.dimension(i)) {
				tmp.set(i, val % ds.dimension(i));
				oob = true;
			}
			else {
				tmp.set(i, val);
			}
		}
		if (oob)
			ds.get(tmp, value);
		else
			throw new IllegalArgumentException("OOB method called with in bounds index");
	}

}
