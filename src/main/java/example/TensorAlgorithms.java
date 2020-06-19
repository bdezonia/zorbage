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

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
import nom.bdezonia.zorbage.algorithm.TensorShape;
import nom.bdezonia.zorbage.algorithm.TensorUnity;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.float64.real.Float64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;

/**
 * @author Barry DeZonia
 */
class TensorAlgorithms {

	// Zorbage has a number of basic tensor algorithms. The examples below are mostly using
	// doubles but note that any precision of reals, complexes, quaternions, and octonions
	// can be substituted instead as needed.
	
	void example1() {
		
		Float64CartesianTensorProductMember a = new Float64CartesianTensorProductMember(3, 4);
		
		Float64CartesianTensorProductMember b = G.DBL_TEN.construct();
		
		TensorCommaDerivative.compute(G.DBL_TEN, G.DBL, 1, a, b);
		
		// b contains the comma derivative of a
	}

	void example2() {
		
		ComplexFloat64CartesianTensorProductMember a =
				new ComplexFloat64CartesianTensorProductMember(3, 4);
		
		Float64Member b = G.DBL.construct();
		
		TensorNorm.compute(G.CDBL, G.DBL, a.rawData(), b);
		
		// b contains the norm of a
	}

	void example3() {

		ComplexFloat64CartesianTensorProductMember a =
				new ComplexFloat64CartesianTensorProductMember(3, 4);
		
		ComplexFloat64CartesianTensorProductMember b =
				new ComplexFloat64CartesianTensorProductMember(3, 4);
		
		ComplexFloat64CartesianTensorProductMember c = G.CDBL_TEN.construct();
		
		TensorOuterProduct.compute(G.CDBL_TEN, G.CDBL, a, b, c);
		
		// c contains the outer prodcut of a and b
	}

	void example4() {
		
		Float64CartesianTensorProductMember a =
				new Float64CartesianTensorProductMember(3, 4);

		Float64CartesianTensorProductMember b = G.DBL_TEN.construct();

		TensorPower.compute(G.DBL_TEN, 4, a, b);
		
		// b = a ^ 4
	}

	void example5() {
		
		Float64CartesianTensorProductMember a =
				new Float64CartesianTensorProductMember(3, 4);

		Float64CartesianTensorProductMember b = G.DBL_TEN.construct();

		Float64Member delta = new Float64Member(1);
		
		TensorRound.compute(G.DBL_TEN, G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		
		// b contains the rounded version of a
	}

	void example6() {
		
		Float64CartesianTensorProductMember a =
				new Float64CartesianTensorProductMember(3, 4);

		Float64CartesianTensorProductMember b = G.DBL_TEN.construct();

		TensorSemicolonDerivative.compute(G.DBL_TEN, G.DBL, 2, a, b);
		
		// b contains the semicolon derivative of a
	}

	void example7() {
		
		ComplexFloat64CartesianTensorProductMember a =
				new ComplexFloat64CartesianTensorProductMember(3, 4);
		
		ComplexFloat64CartesianTensorProductMember b = G.CDBL_TEN.construct();

		TensorShape.compute(a, b);
		
		// b now has rank of 3 and dimCount of 4
	}

	void example8() {

		ComplexFloat64CartesianTensorProductMember a =
				new ComplexFloat64CartesianTensorProductMember(3, 4);
		
		TensorUnity.compute(G.CDBL_TEN, G.CDBL, a);
		
		// a contains a tensor that is zero most places but is one on the super diagonal
	}

}
