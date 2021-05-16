/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import java.math.BigInteger;

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CartesianProduct {

	// do not instantiate
	
	private CartesianProduct() { }
	
	/**
	 * Calculates the cartesian product between two input lists and places
	 * the values into an output list. The inputs are transformed by a
	 * function before being placed in the output list.
	 * 
	 * An example might make things self evident.
	 *   a = [s,t,u]
	 *   b = [1,2,3]
	 *   result = [f(s,1), f(s,2), f(s,3), f(t,1), f(t,2), f(t,3), f(u,1), f(u,2), f(u,3)]
	 *   
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param proc
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U>,
					U,
					V extends Algebra<V,W>,
					W,
					X extends Algebra<X,Y>,
					Y>
		void compute(T algU, V algW, X algY, Procedure3<U,W,Y> proc,
						IndexedDataSource<U> a, IndexedDataSource<W> b, IndexedDataSource<Y> result)
	{
		if (a.size() <= 0 || b.size() <= 0 || result.size() <= 0)
			throw new IllegalArgumentException("input arrays must all be length >= 1");
		if (!BigInteger.valueOf(result.size()).equals(BigInteger.valueOf(a.size()).multiply(BigInteger.valueOf(b.size()))))
			throw new IllegalArgumentException("input arrays are not shape compatible");
		U u = algU.construct();
		W w = algW.construct();
		Y y = algY.construct();
		long i = 0;
		for (long r = 0; r < a.size(); r++) {
			a.get(r, u);
			for (long c = 0; c < b.size(); c++) {
				b.get(c, w);
				proc.call(u, w, y);
				result.set(i++, y);
			}
		}
	}

	/**
	 * Calculates the cartesian product between two input vectors and places
	 * the values into an output matrix. The inputs are transformed by a
	 * function before being placed in the output matrix.
	 * 
	 * An example might make things self evident.
	 *   a = [s,t,u]
	 *   b = [1,2,3]
	 *   result = [
	 *     [f(s,1), f(s,2), f(s,3)]
	 *     [f(t,1), f(t,2), f(t,3)]
	 *     [f(u,1), f(u,2), f(u,3)]
	 *   ]
	 *   
	 * @param algU
	 * @param algW
	 * @param algY
	 * @param proc
	 * @param a
	 * @param b
	 * @param result
	 */
	public static <T extends Algebra<T,U>,
					U,
					V extends Algebra<V,W>,
					W,
					X extends Algebra<X,Y>,
					Y>
		void compute(T algU, V algW, X algY, Procedure3<U,W,Y> proc,
				RModuleMember<U> a, RModuleMember<W> b, MatrixMember<Y> result)
	{
		if (a.length() != result.rows() || b.length() != result.cols())
			throw new IllegalArgumentException("input array sizes do not match");
		U u = algU.construct();
		W w = algW.construct();
		Y y = algY.construct();
		for (long r = 0; r < a.length(); r++) {
			a.getV(r, u);
			for (long c = 0; c < b.length(); c++) {
				b.getV(c, w);
				proc.call(u, w, y);
				result.setV(r, c, y);
			}
		}
	}
}
