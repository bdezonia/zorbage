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
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestExponentialCalculation {
	
	@Test
	public void test1() {
		
		Float64Member v = G.DBL.construct();
		Float64Member fraction = G.DBL.construct("0.5");
		Float64Member base = G.DBL.construct("4");
		Float64Member power = G.DBL.construct("3");
		
		ExponentialCalculation.compute(G.DBL, fraction, base, power, v);
		
		assertEquals(0.5*4*4*4, v.v(), 0.0000000000001);
	}

	@Test
	public void test2() {
		
		SignedInt16Member v = G.INT16.construct();
		SignedInt16Member fraction = G.INT16.construct("7");
		SignedInt16Member base = G.INT16.construct("2");
		SignedInt16Member power = G.INT16.construct("5");
		
		ExponentialCalculation.compute(G.INT16, fraction, base, power, v);
		
		assertEquals(7*2*2*2*2*2, v.v(), 0);
	}
}
