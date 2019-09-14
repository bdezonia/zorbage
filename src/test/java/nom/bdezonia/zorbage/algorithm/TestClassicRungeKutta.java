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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestClassicRungeKutta {

	// Test data derived from http://www2.hawaii.edu/~jmcfatri/math407/RungeKuttaTest.html

	@Test
	public void test1() {
		
		double h = 0.0125;
		int numSteps = 500;
		double range = h * numSteps;
		// true analytic solution: 12*e^t/(e^t+1)^2
		double expected = 12 * Math.exp(range)/((Math.exp(range)+1)*(Math.exp(range)+1));
	
		Float64Member t0 = new Float64Member(0);
		Float64Member y0 = new Float64Member(3); // true analytic y(0) when t(0) = 0
		Float64Member dy = new Float64Member(h);
		Float64Member result = G.DBL.construct();
		
		ClassicRungeKutta.compute(G.DBL, myProc, t0, y0, numSteps, dy, result);
		
		assertEquals(expected, result.v(), 0.0001);
	}

	private static Procedure3<Float64Member,Float64Member,Float64Member> myProc =
			new Procedure3<Float64Member, Float64Member, Float64Member>()
	{
		@Override
		public void call(Float64Member t, Float64Member y, Float64Member result) {
			double val = y.v() * ((2/(Math.exp(t.v())+1))-1);
			result.setV(val);
		}
	};
}
