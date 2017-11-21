/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.algorithm;

import zorbage.groups.G;
import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;
import zorbage.type.data.float64.real.Float64MatrixMember;
import zorbage.type.data.float64.real.Float64Member;
import zorbage.type.data.float64.real.Float64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LUDecomp {

	// TODO - generalize to more floating types
	
	/**
	 * Do not instantiate. Private constructor for utility class.
	 */
	private LUDecomp() {}
	
	/**
	 * LU Decomposition. Sets the solution vector x to the equation Ax = b.
	 * @param a
	 * @param b
	 * @param x
	 */
	public static void compute(Float64MatrixMember a, Float64VectorMember b, Float64VectorMember x) {
		
		final long n = x.length();
		
		// decomposition of matrix

		Float64MatrixMember lu = G.DBL_MAT.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, n, n);
		Float64Member sum = G.DBL.construct();
		Float64Member value1 = G.DBL.construct();
		Float64Member value2 = G.DBL.construct();
		Float64Member term = G.DBL.construct();
		Float64Member tmp = G.DBL.construct();
		for (long i = 0; i < n; i++)
		{
			for (long j = i; j < n; j++)
			{
				sum.setV(0);
				for (long k = 0; k < i; k++) {
					lu.v(i, k, value1);
					lu.v(k, j, value2);
					G.DBL.multiply(value1, value2, term);
					G.DBL.add(sum, term, sum);
				}
				a.v(i, j, term);
				G.DBL.subtract(term, sum, term);
				lu.setV(i, j, term);
			}
			for (long j = i + 1; j < n; j++)
			{
				sum.setV(0);
				for (long k = 0; k < i; k++) {
					lu.v(j, k, value1);
					lu.v(k, i, value2);
					G.DBL.multiply(value1, value2, term);
					G.DBL.add(sum, term, sum);
				}
				G.DBL.unity(value1);
				lu.v(i, i, tmp);
				G.DBL.divide(value1, tmp, value1);
				a.v(j, i, tmp);
				G.DBL.subtract(tmp, sum, value2);
				G.DBL.multiply(value1, value2, term);
				lu.setV(j, i, term);
			}
		}

		// find solution of Ly = b
		Float64VectorMember y = G.DBL_VEC.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, n);
		for (long i = 0; i < n; i++)
		{
			sum.setV(0);
			for (long k = 0; k < i; k++) {
				lu.v(i, k, value1);
				y.v(k, value2);
				G.DBL.multiply(value1, value2, term);
				G.DBL.add(sum, term, sum);
			}
			b.v(i, value1);
			G.DBL.subtract(value1, sum, term);
			y.setV(i, term);
		}

		// find solution of Ux = y
		for (long i = n - 1; i >= 0; i--)
		{
			sum.setV(0);
			for (long k = i + 1; k < n; k++) {
				lu.v(i, k, value1);
				x.v(k, value2);
				G.DBL.multiply(value1, value2, term);
				G.DBL.add(sum, term, sum);
			}
			G.DBL.unity(tmp);
			lu.v(i, i, value1);
			G.DBL.divide(tmp, value1, value1);
			y.v(i, value2);
			G.DBL.subtract(value2, sum, value2);
			G.DBL.multiply(value1, value2, term);
			x.setV(i, term);
		}
	}

}
