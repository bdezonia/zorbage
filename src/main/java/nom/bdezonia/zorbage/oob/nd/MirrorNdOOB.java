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
public class MirrorNdOOB<U> implements Procedure2<IntegerIndex,U> {

	private final DimensionedDataSource<U> ds;
	private final ThreadLocal<IntegerIndex> coord;
	
	/**
	 * 
	 * @param d
	 */
	public MirrorNdOOB(DimensionedDataSource<U> d) {
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
		long idx;
		long offset;
		IntegerIndex tmp = coord.get();
		boolean oob = false;
		for (int i = 0; i < ds.numDimensions(); i++) {
			long val = index.get(i);
			if (val < 0) {
				idx = ((-val) - 1) / ds.dimension(i);
				offset = ((-val) - 1) % ds.dimension(i);
				if (idx % 2 == 0)
					tmp.set(i, offset);
				else
					tmp.set(i, ds.dimension(i) - 1 - offset);
				oob = true;
			}
			else if (val >= ds.dimension(i)) {
				idx = val / ds.dimension(i);
				offset = val % ds.dimension(i);
				if (idx % 2 == 0)
					tmp.set(i, offset);
				else
					tmp.set(i, ds.dimension(i) - 1 - offset);
				oob = true;
			}
			else {
				tmp.set(i, val);
			}
		}
		if (oob) {
			ds.get(tmp, value);
		}
		else
			throw new IllegalArgumentException("OOB method called with in bounds index");
	}

}
