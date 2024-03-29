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
import nom.bdezonia.zorbage.type.real.float64.Float64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRModuleRound {

	@Test
	public void test() {
		Float64VectorMember a = new Float64VectorMember(
				new double[] {
						0, 1.2, 1.6,
						-2.7, -2.4, -2.1,
						66.6, -44.4, -20
				});
		RModuleRound.compute(G.DBL, Round.Mode.HALF_EVEN, new Float64Member(1), a, a);
		Float64Member value = G.DBL.construct();
		a.getV(0, value);
		assertEquals(0, value.v(), 0);
		a.getV(1, value);
		assertEquals(1, value.v(), 0);
		a.getV(2, value);
		assertEquals(2, value.v(), 0);
		a.getV(3, value);
		assertEquals(-3, value.v(), 0);
		a.getV(4, value);
		assertEquals(-2, value.v(), 0);
		a.getV(5, value);
		assertEquals(-2, value.v(), 0);
		a.getV(6, value);
		assertEquals(67, value.v(), 0);
		a.getV(7, value);
		assertEquals(-44, value.v(), 0);
		a.getV(8, value);
		assertEquals(-20, value.v(), 0);
	}
}
