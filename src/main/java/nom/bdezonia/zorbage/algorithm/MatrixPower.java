/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixPower {

	private MatrixPower() { }

	/**
	 * 
	 * @param power
	 * @param numAlgebra
	 * @param rmodAlgebra
	 * @param matAlgebra
	 * @param a
	 * @param b
	 */
	public static
	<BASETYPE, // the base type like Float64Member or Octonion etc.
	BASETYPE_ALGEBRA extends RingWithUnity<BASETYPE_ALGEBRA,BASETYPE> & Invertible<BASETYPE>,
	RMODULE_MEMBER extends RModuleMember<BASETYPE>,
	RMODULE_ALGEBRA extends RModule<RMODULE_ALGEBRA,RMODULE_MEMBER,BASETYPE_ALGEBRA,BASETYPE> & Constructible1dLong<RMODULE_MEMBER>,
	MATRIX_MEMBER extends MatrixMember<BASETYPE>,
	MATRIX_ALGEBRA extends Algebra<MATRIX_ALGEBRA,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
		void compute(int power, BASETYPE_ALGEBRA numAlgebra, RMODULE_ALGEBRA rmodAlgebra, MATRIX_ALGEBRA matAlgebra, MATRIX_MEMBER a, MATRIX_MEMBER b)
	{
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0) {
			MATRIX_MEMBER aInv = matAlgebra.construct();
			MatrixInvert.compute(numAlgebra, rmodAlgebra, matAlgebra, a, aInv);
			MatrixPower.compute(-power, numAlgebra, rmodAlgebra, matAlgebra, aInv, b);
		}
		else if (power == 0) {
			b.alloc(a.rows(), a.cols());
			if (matAlgebra.isZero().call(a)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			else {
				MatrixUnity.compute(numAlgebra, b);
			}
		}
		else if (power == 1)
			MatrixAssign.compute(numAlgebra, a, b);
		else { // power >= 2
			// Higham, Functions of Matrices, page 72
			//   my impl is not completely speed optimized
			MATRIX_MEMBER p = matAlgebra.construct(a);
			MATRIX_MEMBER tmp = matAlgebra.construct();
			int i = 0;
			while ((power & (1 << i)) == 0) {
				MatrixMultiply.compute(numAlgebra, p, p, tmp);
				MatrixAssign.compute(numAlgebra, tmp, p);
				i = i + 1;
			}
			MATRIX_MEMBER x = matAlgebra.construct(p); 
			int maxBit = Integer.highestOneBit(power);
			for (int j = i + 1; j <= maxBit; j++) {
				MatrixMultiply.compute(numAlgebra, p, p, tmp);
				MatrixAssign.compute(numAlgebra, tmp, p);
				if ((power & (1 << j)) > 0) {
					MatrixMultiply.compute(numAlgebra, x, p, tmp);
					MatrixAssign.compute(numAlgebra, tmp, x);
				}
			}
			MatrixAssign.compute(numAlgebra, x, b);
		}
	}
}
