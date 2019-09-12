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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Algebra;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFindFixedPoint {

	@Test
	public void test1() {
		Float64Member firstGuess = G.DBL.construct("1");
		Float64Member result = G.DBL.construct();
		
		FindFixedPoint<Float64Algebra, Float64Member> fp = new FindFixedPoint<>(G.DBL, G.DBL.cos(), closeEnough, 50);
		if (fp.call(firstGuess, result) >= 0) {
			assertEquals(0.73908, result.v(), 0.00001);
		}
		else
			fail();
	}
	
	private Function2<Boolean, Float64Member, Float64Member> closeEnough = new Function2<Boolean, Float64Member, Float64Member>() {
		
		@Override
		public Boolean call(Float64Member a, Float64Member b) {
			return Math.abs(a.v()-b.v()) < 0.00001;
		}
	};
	
	@Test
	public void test2() {
		Function2<Boolean,OctonionFloat64Member,OctonionFloat64Member> closeEnough =
				new WithinTolerance<OctonionFloat64Algebra, OctonionFloat64Member, Float64Algebra, Float64Member>(
						G.ODBL, G.DBL, new Float64Member(0.00001));
		FindFixedPoint<OctonionFloat64Algebra, OctonionFloat64Member> ffp =
				new FindFixedPoint<>(G.ODBL, G.ODBL.sin(), closeEnough, 10000);
		OctonionFloat64Member firstGuess = new OctonionFloat64Member(0.5,0.3,0.3,0.4,0.5,0.6,0.7,0.8);
		OctonionFloat64Member result = G.ODBL.construct();
		long iters = ffp.call(firstGuess, result);
		assertTrue(iters > 0);
		assertFalse(G.ODBL.isNaN().call(result));
		assertFalse(G.ODBL.isInfinite().call(result));
		assertFalse(G.ODBL.isZero().call(result));
		System.out.println(result);
	}
}
