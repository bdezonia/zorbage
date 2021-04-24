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
package example;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algorithm.Eye;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
import nom.bdezonia.zorbage.algorithm.MatrixConjugateTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixConstantDiagonal;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixMaximumAbsoluteColumnSumNorm;
import nom.bdezonia.zorbage.algorithm.MatrixMaximumAbsoluteRowSumNorm;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixReshape;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixScale;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByDouble;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByRational;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixSum;
import nom.bdezonia.zorbage.algorithm.MatrixTrace;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Ones;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Zeroes;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class MatrixAlgorithms {

	// Zorbage has many basic matrix algorithms. The examples below are mostly using
	// doubles but note that any precision of reals, complexes, quaternions, and octonions
	// can be substituted instead as needed.
	
	void example1() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = new Float64MatrixMember(2, 2, new double[] {5,6,7,8});
		
		Float64MatrixMember c = new Float64MatrixMember();

		MatrixAddition.compute(G.DBL, a, b, c);
		
		// c == [[6,8]
		//       [10,12]]
	}

	void example2() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = new Float64MatrixMember();

		MatrixAssign.compute(G.DBL, a, b);
		
		// b = [[1,2]
		//      [3,4]]
	}

	void example3() {
		
		ComplexFloat64MatrixMember a =
				new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,-5,7,-8});
		
		ComplexFloat64MatrixMember b = new ComplexFloat64MatrixMember();

		// a = [[(1,2), (3,4)]
		//      [(5,-6), (7,-8)]]
		
		MatrixConjugate.compute(G.CDBL, a, b);

		// b = [[(1,-2), (3,-4)]
		//      [(5,6), (7,8)]]
	}
	
	void example4() {
		
		ComplexFloat64MatrixMember a =
				new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,-5,7,-8});

		ComplexFloat64MatrixMember b = new ComplexFloat64MatrixMember();

		// a = [[(1,2), (3,4)]
		//      [(5,-6), (7,-8)]]
		
		MatrixConjugateTranspose.compute(G.CDBL, a, b);

		// b = [[(1,-2), (5,6)]
		//      [(3,-4), (7,8)]]
	}
	
	void example5() {
		
		Float64Member constant = new Float64Member(74);
		
		Float64MatrixMember mat = new Float64MatrixMember(3, 3);
		
		MatrixConstantDiagonal.compute(G.DBL, constant, mat);
		
		// mat = [[74,0,0]
		//        [0,74,0]
		//        [0,0,74]]
	}

	void example6() {
		
		Float64MatrixMember mat = new Float64MatrixMember(3, 3, new double[] {1,2,3,4,5,6,7,8,9});
		
		Float64Member det = G.DBL.construct();
		
		MatrixDeterminant.compute(G.DBL_MAT, G.DBL, mat, det);
		
		// det = the determinant of the mat
	}

	void example7() {
		
		Float64MatrixMember in1 = new Float64MatrixMember(2, 2, new double[] {-2,0,1,6});
		
		Float64MatrixMember in2 = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember out = G.DBL_MAT.construct();
		
		MatrixDirectProduct.compute(G.DBL, in1, in2, out);
		
		// out =  [[-2, -4, 0, 0]
		//         [-6, -8, 0, 0]
		//         [1, 2, 6, 12]
		//         [3, 4, 18, 24]]
	}

	@SuppressWarnings("unused")
	void example8() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64MatrixMember b = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember c = new Float64MatrixMember(2, 2, new double[] {5,6,3,2});
		
		boolean res1 = MatrixEqual.compute(G.DBL, a, b);
		// res1 = true
		
		boolean res2 = MatrixEqual.compute(G.DBL, a, c);
		// res2 = false
	}

	void example9() {
		
		Float64MatrixMember in = new Float64MatrixMember(2, 2, new double[] {5,6,3,2});
		
		Float64MatrixMember out = new Float64MatrixMember();

		MatrixInvert.compute(G.DBL, G.DBL_VEC, G.DBL_MAT, in, out);
		
		// out contains the inverse of the in matrix
	}

	void example10() {
		
		Float64MatrixMember matrix = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64Member result = G.DBL.construct();
		
		MatrixMaximumAbsoluteColumnSumNorm.compute(G.DBL, G.DBL, matrix, result);
		
		// result = 6
	}

	void example11() {
		
		Float64MatrixMember matrix = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64Member result = G.DBL.construct();
		
		MatrixMaximumAbsoluteRowSumNorm.compute(G.DBL, G.DBL, matrix, result);
		
		// result = 7
	}

	void example12() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64MatrixMember b = new Float64MatrixMember(2, 1, new double[] {3,5});

		Float64MatrixMember c = new Float64MatrixMember();

		MatrixMultiply.compute(G.DBL, a, b, c);
		
		// c = [[13]
		//      [29]]
	}

	void example13() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64MatrixMember b = new Float64MatrixMember();

		MatrixNegate.compute(G.DBL, a, b);
		
		// b = [[-1,-2]
		//      [-3,-4]]
	}

	void example14() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64MatrixMember b = new Float64MatrixMember();

		MatrixPower.compute(4, G.DBL, G.DBL_VEC, G.DBL_MAT, a, b);
		
		// b = a ^ 4
	}

	void example15() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		MatrixReshape.compute(G.DBL_MAT, G.DBL, 3, 3, a);
		
		// a = [[1, 2, 0]
		//      [3, 4, 0]
		//      [0, 0, 0]]
	}

	void example16() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1.1, 2.2, 3.3, 4.4});
		
		Float64MatrixMember b = G.DBL_MAT.construct();
		
		Float64Member delta = new Float64Member(1);

		MatrixRound.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		
		// b = [[1,2]
		//      [3,4]]
	}

	void example17() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = G.DBL_MAT.construct();

		Float64Member scale = new Float64Member(1.3333333333);

		MatrixScale.compute(G.DBL, scale, a, b);
		
		// b = [[1.33, 2.67]
		//      [3.99, 5.33]]
	}

	void example18() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = G.DBL_MAT.construct();

		double scale = 1.3333333333;

		MatrixScaleByDouble.compute(G.DBL, scale, a, b);
		
		// b = [[1.33, 2.67]
		//      [3.99, 5.33]]
	}

	void example19() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = G.DBL_MAT.construct();

		HighPrecisionMember scale = new HighPrecisionMember(BigDecimal.valueOf(1.3333333333));

		MatrixScaleByHighPrec.compute(G.DBL, scale, a, b);
		
		// b = [[1.33, 2.67]
		//      [3.99, 5.33]]
	}
	
	void example20() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = G.DBL_MAT.construct();

		RationalMember rational = new RationalMember(4,3);

		MatrixScaleByRational.compute(G.DBL, rational, a, b);
		
		// b = [[1.33, 2.67]
		//      [3.99, 5.33]]
	}
	
	void example22() {
		
		Float64MatrixMember a = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		Float64MatrixMember b = new Float64MatrixMember(2, 2, new double[] {5,6,7,8});
		
		Float64MatrixMember c = new Float64MatrixMember();

		MatrixSubtraction.compute(G.DBL, a, b, c);
		
		// c == [[-4,-4]
		//       [-4,-4]]
	}
	
	void example23() {
		
		Float64MatrixMember mat = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64Member result = G.DBL.construct();
		
		MatrixSum.compute(G.DBL, mat, result);
		
		// result = 10
	}
	
	void example24() {
		
		Float64MatrixMember mat = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		Float64Member result = G.DBL.construct();
		
		MatrixTrace.compute(G.DBL, mat, result);
		
		// result = 5
	}
	
	void example25() {
		
		Float64MatrixMember mat = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		// mat is [[1,2]
		//         [3,4]]
		
		MatrixTranspose.compute(G.DBL, mat, mat);

		// mat is [[1,3]
		//         [2,4]]
	}
	
	void example26() {
		
		Float64MatrixMember mat = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});

		// mat is [[1,2]
		//         [3,4]]
		
		MatrixUnity.compute(G.DBL, mat);
		
		// mat is [[1,0]
		//         [0,1]]
	}
	
	void example27() {
		
		Float64MatrixMember mat = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		// mat is [[1,2]
		//         [3,4]]
		
		MatrixZero.compute(mat);
		
		// mat is [[0,0]
		//         [0,0]]
	}
	
	void example28() {
		
		Float64MatrixMember mat = new Float64MatrixMember(25, 25);

		Ones.compute(G.DBL, mat);
		
		// mat is filled with ones
	}
	
	void example29() {
		
		Float64MatrixMember mat = new Float64MatrixMember(2, 2, new double[] {1,2,3,4});
		
		// mat is [[1,2]
		//         [3,4]]
		
		Zeroes.compute(G.DBL, mat);
		
		// mat is [[0,0]
		//         [0,0]]
		
	}
	
	@SuppressWarnings("unused")
	void example30() {
		
		MatrixMember<Float64Member> mat = Eye.compute(G.DBL_MAT, 4, 4);
		
		// mat is [[1,0,0,0]
		//         [0,1,0,0]
		//         [0,0,1,0]
		//         [0,0,0,1]]
	}
}
