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
package nom.bdezonia.zorbage.oob.nd;

import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EdgeNdOOB<U> implements Procedure2<IntegerIndex,U> {

	private final DimensionedDataSource<U> ds;
	private final ThreadLocal<IntegerIndex> coord;

	/**
	 * 
	 * @param d
	 */
	public EdgeNdOOB(DimensionedDataSource<U> d) {
		if (d.numElements() < 1)
			throw new IllegalArgumentException("datset size must be positive");
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
				tmp.set(i, 0);
				oob = true;
			}
			else if (val >= ds.dimension(i)) {
				tmp.set(i, ds.dimension(i) - 1);
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
