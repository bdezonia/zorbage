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
import static org.junit.Assert.assertFalse;

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Algebra;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestDerivative {

	@Test
	public void test1() {
		Float64Member delta = G.DBL.construct("0.00001");
		Float64Member point = G.DBL.construct("4");
		Float64Member result = G.DBL.construct();
		Derivative<Float64Algebra,Float64Member> deriv = new Derivative<Float64Algebra, Float64Member>(G.DBL, eqn, delta);
		deriv.call(point, result);
		assertEquals(8, result.v(), 0.00001);
	}
	
	private final Procedure2<Float64Member,Float64Member> eqn = new Procedure2<Float64Member, Float64Member>() {
		
		@Override
		public void call(Float64Member a, Float64Member b) {
			double v = a.v();
			v = v*v;
			b.setV(v);
		}
	};

	@Test
	public void test2() {
		OctonionFloat64Member delta = G.ODBL.construct("{0.00001,0.00001,0.00001,0.00001,0.00001,0.00001,0.00001,0.00001}");
		OctonionFloat64Member point = G.ODBL.construct("{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8}");
		OctonionFloat64Member result = G.ODBL.construct();
		Derivative<OctonionFloat64Algebra,OctonionFloat64Member> deriv = new Derivative<OctonionFloat64Algebra, OctonionFloat64Member>(G.ODBL, G.ODBL.sin(), delta);
		deriv.call(point, result);
		assertFalse(G.ODBL.isInfinite().call(result));
		assertFalse(G.ODBL.isNaN().call(result));
		assertFalse(G.ODBL.isZero().call(result));
		System.out.println(result);
	}
}
