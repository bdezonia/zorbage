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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestEstimateErf {

	@Test
	public void test1() {
		
		// Note: all decimal numbers below taken from wolframalpha.com on 11-28-19
		
		Float64Member input = G.DBL.construct();
		Float64Member result = G.DBL.construct();
		
		input.setV(3);
		EstimateErf.compute(G.DBL, 32, input, result);
		assertEquals(0.999977, result.v(), 0.000001);
		
		input.setV(0.9);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.796908, result.v(), 0.000001);
		
		input.setV(0.7);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.677801, result.v(), 0.000001);
		
		input.setV(0.5);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.520500, result.v(), 0.000001);
		
		input.setV(0.3);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.328627, result.v(), 0.000001);
		
		input.setV(0.1);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.112463, result.v(), 0.000001);
		
		input.setV(0);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0, result.v(), 0.000001);
		
		input.setV(-0.1);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(-0.112463, result.v(), 0.000001);
		
		input.setV(-0.3);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(-0.328627, result.v(), 0.000001);
		
		input.setV(-0.5);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(-0.520500, result.v(), 0.000001);
		
		input.setV(-0.7);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(-0.677801, result.v(), 0.000001);
		
		input.setV(-0.9);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(-0.796908, result.v(), 0.000001);
		
		input.setV(-3);
		EstimateErf.compute(G.DBL, 32, input, result);
		assertEquals(-0.999977, result.v(), 0.000001);
		
		// Do a complex valued erf() test
		
		ComplexFloat64Member x = G.CDBL.construct();
		ComplexFloat64Member res = G.CDBL.construct();
		x.setR(1);
		x.setI(2);
		EstimateErf.compute(G.CDBL, 21, x, res);
		assertEquals(-0.53664356577, res.r(), 0.000001);
		assertEquals(-5.04914370344, res.i(), 0.000001);
	}
	
	// Note: I tried estimating the erf() of x = 10 with this algorithm and no number of terms
	// could converge to an accurate estimate. See below. This implies the code might be wrong.
	// However I have gone over the definitions in the code over and over and can see no flaw.
	// It's also possible one of the subroutines that the algorithm calls is broken. Must
	// investigate. Later: I wrote simple java code to check term by term and for input values
	// somewhat greater than 3 the terms seem to diverge. I need to check what the math theory
	// says about this series. MathWorld mentioned nothing about radius of convergence.
	// Even later edit: you can see that it converges if decimal places of accuracy set to
	// 150 and iterations at least 365. It's possible for the old x = 1000 input we'd need
	// hundreds of decimal places of accuracy and thousands of iterations. Apparently the
	// series does converge but sometimes very slowly.

	@Test
	public void test2() {
		// Force convergence
		//HighPrecisionAlgebra.setPrecision(150);
		
		HighPrecisionMember input = G.HP.construct();
		HighPrecisionMember result = G.HP.construct();
		
		input.setV(BigDecimal.valueOf(10));
		for (int i = 1; i <= 365; i++) {
			EstimateErf.compute(G.HP, i, input, result);
			//System.out.println(result.v());
		}
	}
}
