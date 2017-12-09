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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Matrix;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LUDecomp {

	/**
	 * Do not instantiate. Private constructor for utility class.
	 */
	private LUDecomp() {}
	
	/**
	 * LU Decomposition. Sets the solution vector x from the equation Ax = b.
	 * @param a
	 * @param b
	 * @param x
	 */
	public static <N extends Group<N,U> & Addition<U> & Multiplication<U> & Invertible<U> & Unity<U>,
					R extends Group<R,RModuleMember<U>> & Constructible1dLong<RModuleMember<U>>,
					M extends Group<M,MatrixMember<U>> & Constructible2dLong<MatrixMember<U>>,
					U>
		void compute(N numGroup, R vecGroup, M matGroup, MatrixMember<U> a, RModuleMember<U> b, RModuleMember<U> x)
	{
		final long n = x.length();
		
		// decomposition of matrix

		MatrixMember<U> lu = matGroup.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, n, n);
		U sum = numGroup.construct();
		U value1 = numGroup.construct();
		U value2 = numGroup.construct();
		U term = numGroup.construct();
		U tmp = numGroup.construct();
		for (long i = 0; i < n; i++)
		{
			for (long j = i; j < n; j++)
			{
				numGroup.zero(sum);
				for (long k = 0; k < i; k++) {
					lu.v(i, k, value1);
					lu.v(k, j, value2);
					numGroup.multiply(value1, value2, term);
					numGroup.add(sum, term, sum);
				}
				a.v(i, j, term);
				numGroup.subtract(term, sum, term);
				lu.setV(i, j, term);
			}
			for (long j = i + 1; j < n; j++)
			{
				numGroup.zero(sum);
				for (long k = 0; k < i; k++) {
					lu.v(j, k, value1);
					lu.v(k, i, value2);
					numGroup.multiply(value1, value2, term);
					numGroup.add(sum, term, sum);
				}
				numGroup.unity(value1);
				lu.v(i, i, tmp);
				numGroup.divide(value1, tmp, value1);
				a.v(j, i, tmp);
				numGroup.subtract(tmp, sum, value2);
				numGroup.multiply(value1, value2, term);
				lu.setV(j, i, term);
			}
		}

		// find solution of Ly = b
		RModuleMember<U> y = vecGroup.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, n);
		for (long i = 0; i < n; i++)
		{
			numGroup.zero(sum);
			for (long k = 0; k < i; k++) {
				lu.v(i, k, value1);
				y.v(k, value2);
				numGroup.multiply(value1, value2, term);
				numGroup.add(sum, term, sum);
			}
			b.v(i, value1);
			numGroup.subtract(value1, sum, term);
			y.setV(i, term);
		}

		// find solution of Ux = y
		for (long i = n - 1; i >= 0; i--)
		{
			numGroup.zero(sum);
			for (long k = i + 1; k < n; k++) {
				lu.v(i, k, value1);
				x.v(k, value2);
				numGroup.multiply(value1, value2, term);
				numGroup.add(sum, term, sum);
			}
			numGroup.unity(tmp);
			lu.v(i, i, value1);
			numGroup.divide(tmp, value1, value1);
			y.v(i, value2);
			numGroup.subtract(value2, sum, value2);
			numGroup.multiply(value1, value2, term);
			x.setV(i, term);
		}
	}
	
}
