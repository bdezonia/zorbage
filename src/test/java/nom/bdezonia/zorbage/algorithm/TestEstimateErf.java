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
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

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
		
		input.setV(0);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0, result.v(), 0.000001);
		
		input.setV(0.1);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.112463, result.v(), 0.000001);
		
		input.setV(0.3);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.328627, result.v(), 0.000001);
		
		input.setV(0.5);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.520500, result.v(), 0.000001);
		
		input.setV(0.7);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.677801, result.v(), 0.000001);
		
		input.setV(0.9);
		EstimateErf.compute(G.DBL, 8, input, result);
		assertEquals(0.796908, result.v(), 0.000001);
		
		input.setV(3);
		EstimateErf.compute(G.DBL, 32, input, result);
		assertEquals(0.999977, result.v(), 0.000001);
		
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
		assertEquals(-0.999978, result.v(), 0.000001);
	}
}
