/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.octonion.float128;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestOctonionFloat128Algebra {

	private static final BigDecimal TOL = BigDecimal.valueOf(0.0000000001);
	
	@Test
	public void testConjugate() {
		// Question: is multiplying an oct by its conjugate always a purely real result?
		// This test proves the answer is yes. This will be a useful fact when we decide
		// to calculate matrix norms.
		
		OctonionFloat128Member a = new OctonionFloat128Member(BigDecimal.valueOf(-1),BigDecimal.valueOf(5),BigDecimal.valueOf(-2),BigDecimal.valueOf(7),BigDecimal.valueOf(-1),BigDecimal.valueOf(-2),BigDecimal.valueOf(-3),BigDecimal.valueOf(-4));
		OctonionFloat128Member b = new OctonionFloat128Member();
		OctonionFloat128Member c = new OctonionFloat128Member();
		
		G.OQUAD.conjugate().call(a, b);
		G.OQUAD.multiply().call(a, b, c);
		assertTrue(BigDecimalUtils.isNear(109,c.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0,c.k0().v(), TOL));
	}
	
	@Test
	public void mathematicalMethods() {
		
		OctonionFloat128Member a = G.OQUAD.construct();
		OctonionFloat128Member b = G.OQUAD.construct();
		OctonionFloat128Member c = G.OQUAD.construct();
		Float128Member t = G.QUAD.construct();
		
		// G.OQUAD.add();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		b.setR(BigDecimal.valueOf(5));
		b.setI(BigDecimal.valueOf(7));
		b.setJ(BigDecimal.valueOf(9));
		b.setK(BigDecimal.valueOf(11));
		b.setL(BigDecimal.valueOf(13));
		b.setI0(BigDecimal.valueOf(15));
		b.setJ0(BigDecimal.valueOf(17));
		b.setK0(BigDecimal.valueOf(19));
		G.OQUAD.add().call(a, b, c);
		assertTrue(BigDecimalUtils.isNear(6, c.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(9, c.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(12, c.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(15, c.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(18, c.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(21, c.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(24, c.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(27, c.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.assign();
		G.OQUAD.assign().call(a, b);
		assertTrue(G.OQUAD.isEqual().call(a, b));
		
		// G.OQUAD.cbrt();
		a.setR(BigDecimal.valueOf(27));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.cbrt().call(a, b);
		assertTrue(BigDecimalUtils.isNear(3, b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), TOL));
		
		// G.OQUAD.conjugate();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.conjugate().call(a, b);
		assertTrue(BigDecimalUtils.isNear(1, b.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-2, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-3, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-4, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-5, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-6, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-7, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-8, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.construct();
		a = G.OQUAD.construct();
		assertTrue(BigDecimalUtils.isNear(0, a.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.construct("");
		a = G.OQUAD.construct("{3,-1,0,6,7,-9,0.1,-0.3}");
		assertTrue(BigDecimalUtils.isNear(3, a.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-1, a.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(6, a.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(7, a.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-9, a.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0.1, a.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-0.3, a.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.construct(other);
		b = G.OQUAD.construct(a);
		assertTrue(BigDecimalUtils.isNear(3, b.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-1, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(6, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(7, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-9, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0.1, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-0.3, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.cos();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.cos().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.cos(Math.PI/2), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.cosh();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.cosh().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.cosh(Math.PI/2), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.divide();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		b.setR(BigDecimal.valueOf(0.5));
		b.setI(BigDecimal.valueOf(0));
		b.setJ(BigDecimal.valueOf(0));
		b.setK(BigDecimal.valueOf(0));
		b.setL(BigDecimal.valueOf(0));
		b.setI0(BigDecimal.valueOf(0));
		b.setJ0(BigDecimal.valueOf(0));
		b.setK0(BigDecimal.valueOf(0));
		G.OQUAD.divide().call(a, b, c);
		assertTrue(BigDecimalUtils.isNear(2, c.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(4, c.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(6, c.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(8, c.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(10, c.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(12, c.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(14, c.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(16, c.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.E();
		G.OQUAD.E().call(a);
		assertTrue(BigDecimalUtils.isNear(Math.E, a.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.exp();
		a.setR(BigDecimal.valueOf(2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.exp().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.exp(2), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.infinite();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		assertFalse(G.OQUAD.isInfinite().call(a));
		G.OQUAD.infinite().call(a);
		assertTrue(G.OQUAD.isInfinite().call(a));
		
		// G.OQUAD.invert();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.invert().call(a, b);
		G.OQUAD.unity().call(c);
		G.OQUAD.divide().call(c, a, c);
		assertTrue(G.OQUAD.isEqual().call(b, c));

		// G.OQUAD.isEqual();
		G.OQUAD.zero().call(b);
		assertFalse(G.OQUAD.isEqual().call(a, b));
		G.OQUAD.assign().call(a, b);
		assertTrue(G.OQUAD.isEqual().call(a, b));
		
		// G.OQUAD.isInfinite();
		//   tested by infinite() test above
		
		// G.OQUAD.isNaN();
		//   tested by nan() test below
		
		// G.OQUAD.isNotEqual();
		G.OQUAD.zero().call(b);
		assertTrue(G.OQUAD.isNotEqual().call(a, b));
		G.OQUAD.assign().call(a, b);
		assertFalse(G.OQUAD.isNotEqual().call(a, b));
		
		// G.OQUAD.isZero();
		//   tested by zero() test below
		
		// G.OQUAD.log();
		a.setR(BigDecimal.valueOf(4));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.log().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.log(4), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.multiply();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		b.setR(BigDecimal.valueOf(7));
		b.setI(BigDecimal.valueOf(-4));
		b.setJ(BigDecimal.valueOf(8));
		b.setK(BigDecimal.valueOf(3));
		b.setL(BigDecimal.valueOf(0));
		b.setI0(BigDecimal.valueOf(-3));
		b.setJ0(BigDecimal.valueOf(1));
		b.setK0(BigDecimal.valueOf(9));
		G.OQUAD.multiply().call(a, b, c);
		G.OQUAD.divide().call(c, b, b);
		assertTrue(BigDecimalUtils.isNear(a.r().v(), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.i().v(), b.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.j().v(), b.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.k().v(), b.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.l().v(), b.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.i0().v(), b.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.j0().v(), b.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(a.k0().v(), b.k0().v(), TOL));
		
		// G.OQUAD.nan();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		assertFalse(G.OQUAD.isNaN().call(a));
		G.OQUAD.nan().call(a);
		assertTrue(G.OQUAD.isNaN().call(a));
		
		// G.OQUAD.negate();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.negate().call(a, b);
		assertTrue(BigDecimalUtils.isNear(-1, b.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-2, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-3, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-4, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-5, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-6, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-7, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-8, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.norm();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.norm().call(a, t);
		assertTrue(BigDecimalUtils.isNear(Math.sqrt(1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6 + 7*7 + 8*8), t.v(), TOL));
		
		// G.OQUAD.PI();
		G.OQUAD.PI().call(a);
		assertTrue(BigDecimalUtils.isNear(Math.PI, a.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.pow();
		a.setR(BigDecimal.valueOf(7));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		b.setR(BigDecimal.valueOf(4));
		b.setI(BigDecimal.valueOf(0));
		b.setJ(BigDecimal.valueOf(0));
		b.setK(BigDecimal.valueOf(0));
		b.setL(BigDecimal.valueOf(0));
		b.setI0(BigDecimal.valueOf(0));
		b.setJ0(BigDecimal.valueOf(0));
		b.setK0(BigDecimal.valueOf(0));
		G.OQUAD.pow().call(a, b, c);
		assertTrue(BigDecimalUtils.isNear(7*7*7*7, c.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, c.k0().v(), TOL));
		
		// G.OQUAD.power();
		a.setR(BigDecimal.valueOf(7));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.power().call(4, a, b);
		assertTrue(BigDecimalUtils.isNear(7*7*7*7, b.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.random();
		G.OQUAD.random().call(a);
		assertTrue(a.r().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.i().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.j().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.k().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.l().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.i0().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.j0().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.k0().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.r().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.i().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.j().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.k().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.l().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.i0().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.j0().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.k0().v().compareTo(BigDecimal.ONE) < 0);
		
		// G.OQUAD.real();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.real().call(a, t);
		assertTrue(BigDecimalUtils.isNear(1, t.v(), BigDecimal.ZERO));
		
		// G.OQUAD.round();
		a.setR(BigDecimal.valueOf(1.1));
		a.setI(BigDecimal.valueOf(-2.2));
		a.setJ(BigDecimal.valueOf(3.3));
		a.setK(BigDecimal.valueOf(-4.4));
		a.setL(BigDecimal.valueOf(5.5));
		a.setI0(BigDecimal.valueOf(-6.6));
		a.setJ0(BigDecimal.valueOf(7.7));
		a.setK0(BigDecimal.valueOf(-8.8));
		t.setV(BigDecimal.valueOf(1));
		G.OQUAD.round().call(Mode.HALF_EVEN, t, a, b);
		assertTrue(BigDecimalUtils.isNear(1, b.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-2, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(3, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-4, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(6, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-7, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(8, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-9, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.scale();
		a.setR(BigDecimal.valueOf(1.1));
		a.setI(BigDecimal.valueOf(-2.2));
		a.setJ(BigDecimal.valueOf(3.3));
		a.setK(BigDecimal.valueOf(-4.4));
		a.setL(BigDecimal.valueOf(5.5));
		a.setI0(BigDecimal.valueOf(-6.6));
		a.setJ0(BigDecimal.valueOf(7.7));
		a.setK0(BigDecimal.valueOf(-8.8));
		b.setR(BigDecimal.valueOf(3));
		b.setI(BigDecimal.valueOf(0));
		b.setJ(BigDecimal.valueOf(0));
		b.setK(BigDecimal.valueOf(0));
		b.setL(BigDecimal.valueOf(0));
		b.setI0(BigDecimal.valueOf(0));
		b.setJ0(BigDecimal.valueOf(0));
		b.setK0(BigDecimal.valueOf(0));
		G.OQUAD.scale().call(b, a, c);
		assertTrue(BigDecimalUtils.isNear(3.3, c.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(-6.6, c.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(9.9, c.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(-13.2, c.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(16.5, c.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(-19.8, c.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(23.1, c.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(-26.4, c.k0().v(), TOL));
		
		// G.OQUAD.sin();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.sin().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.sin(Math.PI/2), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.sinAndCos();
		
		// G.OQUAD.sinc();
		
		// G.OQUAD.sinch();
		
		// G.OQUAD.sinchpi();
		
		// G.OQUAD.sincpi();
		
		// G.OQUAD.sinh();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.sinh().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.sinh(Math.PI/2), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.sinhAndCosh();
		
		// G.OQUAD.sqrt();
		a.setR(BigDecimal.valueOf(25));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.sqrt().call(a, b);
		assertTrue(BigDecimalUtils.isNear(5, b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), TOL));
		
		// G.OQUAD.subtract();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		b.setR(BigDecimal.valueOf(5));
		b.setI(BigDecimal.valueOf(7));
		b.setJ(BigDecimal.valueOf(9));
		b.setK(BigDecimal.valueOf(11));
		b.setL(BigDecimal.valueOf(13));
		b.setI0(BigDecimal.valueOf(15));
		b.setJ0(BigDecimal.valueOf(17));
		b.setK0(BigDecimal.valueOf(19));
		G.OQUAD.subtract().call(a, b, c);
		assertTrue(BigDecimalUtils.isNear(-4, c.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-5, c.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-6, c.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-7, c.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-8, c.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-9, c.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-10, c.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(-11, c.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.tan();
		a.setR(BigDecimal.valueOf(Math.PI/16));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.tan().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.tan(Math.PI/16), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.tanh();
		a.setR(BigDecimal.valueOf(Math.PI/16));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		a.setL(BigDecimal.valueOf(0));
		a.setI0(BigDecimal.valueOf(0));
		a.setJ0(BigDecimal.valueOf(0));
		a.setK0(BigDecimal.valueOf(0));
		G.OQUAD.tanh().call(a, b);
		assertTrue(BigDecimalUtils.isNear(Math.tanh(Math.PI/16), b.r().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.j().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.k().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.l().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.i0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.j0().v(), TOL));
		assertTrue(BigDecimalUtils.isNear(0, b.k0().v(), TOL));
		
		// G.OQUAD.unity();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.unity().call(a);
		assertTrue(BigDecimalUtils.isNear(1, a.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(0, a.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.unreal();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		G.OQUAD.unreal().call(a, b);
		assertTrue(BigDecimalUtils.isNear(0, b.r().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(2, b.i().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(3, b.j().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(4, b.k().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(5, b.l().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(6, b.i0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(7, b.j0().v(), BigDecimal.ZERO));
		assertTrue(BigDecimalUtils.isNear(8, b.k0().v(), BigDecimal.ZERO));
		
		// G.OQUAD.zero();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		a.setL(BigDecimal.valueOf(5));
		a.setI0(BigDecimal.valueOf(6));
		a.setJ0(BigDecimal.valueOf(7));
		a.setK0(BigDecimal.valueOf(8));
		assertFalse(G.OQUAD.isZero().call(a));
		G.OQUAD.zero().call(a);
		assertTrue(G.OQUAD.isZero().call(a));
	}
}
