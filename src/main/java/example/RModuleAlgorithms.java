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
package example;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDefaultNorm;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleHermitianProduct;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleReshape;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByDouble;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByRational;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleSum;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64VectorMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class RModuleAlgorithms {

	// Zorbage has many basic vector/rmodule algorithms. The examples below are mostly using
	// doubles but note that any precision of reals, complexes, quaternions, and octonions
	// can be substituted instead as needed.
	
	void example1() {
		
		Float64VectorMember a = new Float64VectorMember(new double[] {1,2,3});

		Float64VectorMember b = new Float64VectorMember(new double[] {5,7,1});
		
		Float64VectorMember sum = new Float64VectorMember();

		RModuleAdd.compute(G.DBL, a, b, sum);
		
		// sum = [6, 9, 4]
	}

	void example2() {
		
		Float64VectorMember a = new Float64VectorMember(new double[] {1,2,3});

		Float64VectorMember result = new Float64VectorMember();

		RModuleAssign.compute(G.DBL, a, result);
		
		// result = [1, 2, 3]
	}

	void example3() {

		ComplexFloat64VectorMember a =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		ComplexFloat64VectorMember result = new ComplexFloat64VectorMember();
		
		RModuleConjugate.compute(G.CDBL, a, result);
		
		// result = [(1, 1), (2, 0), (3, 4)]
	}

	void example4() {
		
		ComplexFloat64VectorMember a =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64Member result = new Float64Member();

		RModuleDefaultNorm.compute(G.CDBL, G.DBL, a, result);
	}

	void example5() {
		
		Float64VectorMember a = new Float64VectorMember(new double[] {1,2,3});
		
		Float64VectorMember b = new Float64VectorMember(new double[] {5,3,1});
		
		Float64MatrixMember result = G.DBL_MAT.construct();
		
		RModuleDirectProduct.compute(G.DBL, a, b, result);
		
		// result = [[5, 3, 1]
		//           [10, 6, 2]
		//           [15, 9, 3]]
	}

	@SuppressWarnings("unused")
	void example6() {

		ComplexFloat64VectorMember a =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		ComplexFloat64VectorMember b =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		ComplexFloat64VectorMember c =
				new ComplexFloat64VectorMember(new double[] {1, 1, 1, 1, 1, 1});

		boolean res1 = RModuleEqual.compute(G.CDBL, a, b);

		// res1 = true

		boolean res2 = RModuleEqual.compute(G.CDBL, a, c);
		
		// res2 = false
	}

	void example7() {
		
		ComplexFloat64VectorMember a =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		ComplexFloat64VectorMember b =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		ComplexFloat64Member result = G.CDBL.construct();
		
		RModuleHermitianProduct.compute(G.CDBL, a, b, result);
		
		// result contains the hermitian product of the two vectors
	}

	void example8() {
		
		ComplexFloat64VectorMember a =
				new ComplexFloat64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		RModuleNegate.compute(G.CDBL, a, a);
		
		// a contains [(-1,1), (-2,0), (-3,4)]
	}

	void example9() {
		
		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		RModuleReshape.compute(G.DBL_VEC, G.DBL, 8, a);

		// a = [1, -1, 2, 0, 3, -4, 0, 0]
		
		RModuleReshape.compute(G.DBL_VEC, G.DBL, 4, a);

		// a = [1, -1, 2, 0]
	}

	void example10() {

		Float64VectorMember a =
				new Float64VectorMember(new double[] {1.4, -1.2, 2.3, 0, 3.6, -4.1});

		Float64VectorMember b = G.DBL_VEC.construct();
		
		Float64Member delta = new Float64Member(1);
		
		RModuleRound.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		
		// b = [1.0, -1.0, 2.0, 0, 4.0, -4.0]
	}

	void example11() {
		
		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64VectorMember b = G.DBL_VEC.construct();
		
		Float64Member scale = new Float64Member(17);
		
		RModuleScale.compute(G.DBL, scale, a, b);
		
		// b = [17, -17, 34, 0, 51, -68]
	}

	void example12() {
		
		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64VectorMember b = G.DBL_VEC.construct();
		
		double scale = 17.0;
		
		RModuleScaleByDouble.compute(G.DBL, scale, a, b);
		
		// b = [17, -17, 34, 0, 51, -68]
	}

	void example13() {
		
		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64VectorMember b = G.DBL_VEC.construct();
		
		HighPrecisionMember scale = new HighPrecisionMember(BigDecimal.valueOf(17));
		
		RModuleScaleByHighPrec.compute(G.DBL, scale, a, b);
		
		// b = [17, -17, 34, 0, 51, -68]
	}

	void example14() {
		
		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64VectorMember b = G.DBL_VEC.construct();
		
		RationalMember scale = new RationalMember(34,2);
		
		RModuleScaleByRational.compute(G.DBL, scale, a, b);
		
		// b = [17, -17, 34, 0, 51, -68]
	}

	void example15() {

		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64VectorMember b =
				new Float64VectorMember(new double[] {3, -1, -3, 5, 2, -4});

		Float64VectorMember c = G.DBL_VEC.construct();
		
		RModuleSubtract.compute(G.DBL, a, b, c);
		
		// c = [-2, 0, 5, -5, 1, 0]
	}

	void example16() {
		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		Float64Member result = G.DBL.construct();
		
		RModuleSum.compute(G.DBL, a, result);
		
		// result = 1
	}

	void example17() {

		Float64VectorMember a =
				new Float64VectorMember(new double[] {1, -1, 2, 0, 3, -4});

		RModuleZero.compute(a);
		
		// a = [0, 0, 0, 0, 0, 0]
	}

}
