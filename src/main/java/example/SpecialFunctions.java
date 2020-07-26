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
import nom.bdezonia.zorbage.algorithm.Combinations;
import nom.bdezonia.zorbage.algorithm.EstimateErf;
import nom.bdezonia.zorbage.algorithm.EstimateErfc;
import nom.bdezonia.zorbage.algorithm.ExponentialCalculation;
import nom.bdezonia.zorbage.algorithm.Factorial;
import nom.bdezonia.zorbage.algorithm.Fibonacci;
import nom.bdezonia.zorbage.algorithm.Gaussian;
import nom.bdezonia.zorbage.algorithm.Gcd;
import nom.bdezonia.zorbage.algorithm.Lcm;
import nom.bdezonia.zorbage.algorithm.Permutations;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.ScientificNotation;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCos;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCosh;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateExp;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateLog;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSin;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSinh;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateTan;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateTanh;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * @author Barry DeZonia
 */
class SpecialFunctions {
	
	// TODO expose a kronecker algorithm? 

	// TODO binomial coeffs: not written yet?


	// Zorbage supports a number of special functions out of the box. You can compute values
	// of these functions in various numeric types.
	
	// the error function

	void example1() {
		
		HighPrecisionMember input = new HighPrecisionMember(BigDecimal.valueOf(1.2345678));
		HighPrecisionMember result = G.HP.construct();
		
		EstimateErf.compute(G.HP, 23, input, result);
	}
	
	// the complementary error function

	void example2() {
		
		HighPrecisionMember input = new HighPrecisionMember(BigDecimal.valueOf(1.2345678));
		HighPrecisionMember result = G.HP.construct();
		
		EstimateErfc.compute(G.HP, 23, input, result);
	}

	// the fibonacci numbers
	
	void example3() {
		
		// fib(25) as an unbound integer
		
		UnboundedIntMember resultA = G.UNBOUND.construct();
		
		Fibonacci.compute(G.UNBOUND, 25, resultA);

		// fib(12) as a 32-bit precision complex float
		
		ComplexFloat32Member resultB = G.CFLT.construct();
		
		Fibonacci.compute(G.CFLT, 12, resultB);
	}
	
	// the factorial function
	
	void example4() {
		
		// 100! as an unbound integer
		
		UnboundedIntMember resultA = G.UNBOUND.construct();
		
		Factorial.compute(G.UNBOUND, 100, resultA);
		
		// 12! as a 64-bit precision octonion float
		
		OctonionFloat64Member resultB = G.ODBL.construct();
		
		Factorial.compute(G.ODBL, 12, resultB);
	}
	
	// number of permutations of n elements
	
	void example5() {
	
		UnsignedInt64Member resultA = G.UINT64.construct();
	
		// the number of permutations of 20 elements
		
		Permutations.compute(G.UINT64, 20, resultA);
	}
	
	// number of combinations of n elements chosen k at a time
	
	void example6() {
	
		UnsignedInt64Member resultA = G.UINT64.construct();
		
		// the number of ways of taking 10 elements from a universe of 20 elements
		
		Combinations.compute(G.UINT64, 20, 10, resultA);
	}

	// you can calc numbers in zorbage using exponential notation
	
	void example7() {
		
		// result = 0.6 * (5 ^ 2.7)
		
		Float64Member fraction = G.DBL.construct("0.6");
		Float64Member base = G.DBL.construct("5");
		Float64Member power = G.DBL.construct("2.7");
		Float64Member result = G.DBL.construct();

		ExponentialCalculation.compute(G.DBL, fraction, base, power, result);
	}

	// you can calc numbers in zorbage using scientific notation
	
	void example8() {
		
		// result = 0.125 * (6 ^ 3)
		
		BigDecimal fraction = BigDecimal.valueOf(0.125);
		int base = 6;
		int power = 3;
		Float64Member result = G.DBL.construct();
		
		ScientificNotation.compute(fraction, base, power, result);
	}
	
	// greatest common divisor
	
	void example9() {
		
		SignedInt64Member a = G.INT64.construct("1230588303029");
		SignedInt64Member b = G.INT64.construct("404223");
		SignedInt64Member result = G.INT64.construct();
		
		Gcd.compute(G.INT64, a, b, result);
	}
	
	// least common multiple
	
	void example10() {
		
		SignedInt64Member a = G.INT64.construct("1230588303029");
		SignedInt64Member b = G.INT64.construct("404223");
		SignedInt64Member result = G.INT64.construct();
		
		Lcm.compute(G.INT64, a, b, result);
	}
	
	// zorbage has a built in flexible rounding algorithm
	
	void example11() {
		
		Float16Member a = new Float16Member(44.160f);
		Float16Member deltaA = new Float16Member(0.125f);
		Float16Member resultA = G.HLF.construct();
		
		Round.compute(G.HLF, Round.Mode.HALF_EVEN, deltaA, a, resultA);
		
		Float32Member b = new Float32Member(44.50683f);
		Float32Member deltaB = new Float32Member(0.125f);
		Float32Member resultB = G.FLT.construct();
		
		Round.compute(G.FLT, Round.Mode.POSITIVE, deltaB, b, resultB);
	
		Float64Member c = new Float64Member(106.1603174);
		Float64Member deltaC = new Float64Member(0.125);
		Float64Member resultC = G.DBL.construct();
		
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, deltaC, c, resultC);
	}

	// zorbage supports a number of sinc functions
	
	void example12() {

		Float64Member x = new Float64Member(44.2);
		Float64Member result = G.DBL.construct();
		
		Sinc.compute(G.DBL, x, result);  // sin(x) / x
		Sinch.compute(G.DBL, x, result);  // sinh(x) / x
		Sincpi.compute(G.DBL, x, result);  // sin(x*pi) / (x*pi)
		Sinchpi.compute(G.DBL, x, result);  // sinh(x*pi) / (x*pi)
	}
	
	// zorbage has a number of taylor estimate formulas. you can estimate a number of functions.
	// zorbage uses such a facility to calculate the sin or exp of a matrix. they can be used
	// for regular numbers as shown below too.
	
	void example13() {
	
		Float64Member x = new Float64Member(4.2);
		Float64Member result = G.DBL.construct();
		
		TaylorEstimateCos.compute(23, G.DBL, G.DBL, x, result);
		TaylorEstimateCosh.compute(14, G.DBL, G.DBL, x, result);
		TaylorEstimateSin.compute(9, G.DBL, G.DBL, x, result);
		TaylorEstimateSinh.compute(17, G.DBL, G.DBL, x, result);
		TaylorEstimateTan.compute(5, G.DBL, G.DBL, x, result);
		TaylorEstimateTanh.compute(22, G.DBL, G.DBL, x, result);
		TaylorEstimateExp.compute(16, G.DBL, G.DBL, x, result);
		TaylorEstimateLog.compute(11, G.DBL, G.DBL, x, result);
	}
	
	// gaussian
	
	void example14() {
	
		Float64Member mu = new Float64Member(4.2);
		Float64Member sigma = new Float64Member(1.4);
		Float64Member x = new Float64Member();
		Float64Member result = new Float64Member();
		
		x.setV(14);
		Gaussian.compute(G.DBL, mu, sigma, x, result);
		
		x.setV(-2);
		Gaussian.compute(G.DBL, mu, sigma, x, result);
	}
}
