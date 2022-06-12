/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSinc {

	@Test
	public void test() {
		Float64Member x = G.DBL.construct("111");
		Float64Member result = G.DBL.construct("999");
		
		x.setV(0);
		Sinc.compute(G.DBL, x, result);
		assertEquals(1, result.v(), 0);
		
		x.setV(Math.PI/4);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(Math.PI/4)/(Math.PI/4), result.v(), 0);
		
		x.setV(Math.PI/2);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(Math.PI/2)/(Math.PI/2), result.v(), 0);
		
		x.setV(3*Math.PI/4);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(3*Math.PI/4)/(3*Math.PI/4), result.v(), 0);
		
		x.setV(Math.PI);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(Math.PI)/(Math.PI), result.v(), 0);

		x.setV(5*Math.PI/4);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(5*Math.PI/4)/(5*Math.PI/4), result.v(), 0);
		
		x.setV(3*Math.PI/2);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(3*Math.PI/2)/(3*Math.PI/2), result.v(), 0);

		x.setV(7*Math.PI/4);
		Sinc.compute(G.DBL, x, result);
		assertEquals(Math.sin(7*Math.PI/4)/(7*Math.PI/4), result.v(), 0);
		
	}
}
