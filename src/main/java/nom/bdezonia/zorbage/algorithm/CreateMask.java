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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.NdData;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.misc.DataSourceUtils;
import nom.bdezonia.zorbage.procedure.Procedure2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CreateMask {

	// do not instantiate
	
	private CreateMask() { }
	
	/**
	 * Make and return a one dimensional mask by applying a boolean mapping function
	 * across a list of values.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param condition A function that maps values to booleans
	 * @param a The input list
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		IndexedDataSource<UnsignedInt1Member> compute(T alg, Function1<Boolean,U> condition, IndexedDataSource<U> a)
	{
		UnsignedInt1Member zero = G.UINT1.construct();
		
		UnsignedInt1Member one = G.UINT1.construct();
		
		G.UINT1.unity().call(one);
		
		Procedure2<U,UnsignedInt1Member> proc = new Procedure2<U, UnsignedInt1Member>() {
			
			@Override
			public void call(U a, UnsignedInt1Member b) {
			
				if (condition.call(a))
					G.UINT1.assign().call(one, b);
				else
					G.UINT1.assign().call(zero, b);
			}
		};
		
		return Map.compute(alg, G.UINT1, proc, a);
	}

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param condition
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		DimensionedDataSource<UnsignedInt1Member> compute(T alg, Function1<Boolean,U> condition, DimensionedDataSource<U> a)
	{
		long[] dims = DataSourceUtils.dimensions(a);
		IndexedDataSource<UnsignedInt1Member> data =
				compute(alg, condition, a.rawData());
		return new NdData<>(dims, data);
	}
}
