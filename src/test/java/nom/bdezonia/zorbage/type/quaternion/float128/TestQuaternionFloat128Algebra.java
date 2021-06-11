/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.quaternion.float128;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestQuaternionFloat128Algebra {

	private static final BigDecimal TOL = BigDecimal.valueOf(0.000000000000001);
	
	@Test
	public void run() {
		QuaternionFloat128Member q1 = G.QQUAD.construct();
		
		q1.setR(BigDecimal.valueOf(1));
		q1.setI(BigDecimal.valueOf(-2));
		q1.setJ(BigDecimal.valueOf(3));
		q1.setK(BigDecimal.valueOf(2));
		
		Float128Member tmp = new Float128Member();
		
		G.QQUAD.norm().call(q1, tmp);

		assertTrue(isNear(3*Math.sqrt(2),tmp.v(),TOL));
		
		QuaternionFloat128Member q2 = G.QQUAD.construct();

		q2.setR(BigDecimal.valueOf(11));
		q2.setI(BigDecimal.valueOf(-2));
		q2.setJ(BigDecimal.valueOf(0));
		q2.setK(BigDecimal.valueOf(-2));

		G.QQUAD.norm().call(q2, tmp);

		assertTrue(isNear(Math.sqrt(129),tmp.v(),TOL));
		
		QuaternionFloat128Member q3 = G.QQUAD.construct();

		G.QQUAD.add().call(q1, q2, q3);

		assertTrue(isNear(q1.r().v().doubleValue()+q2.r().v().doubleValue(), q3.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(q1.i().v().doubleValue()+q2.i().v().doubleValue(), q3.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(q1.j().v().doubleValue()+q2.j().v().doubleValue(), q3.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(q1.k().v().doubleValue()+q2.k().v().doubleValue(), q3.k().v(), BigDecimal.ZERO));
		
		G.QQUAD.subtract().call(q1, q2, q3);

		assertTrue(isNear(q1.r().v().doubleValue()-q2.r().v().doubleValue(), q3.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(q1.i().v().doubleValue()-q2.i().v().doubleValue(), q3.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(q1.j().v().doubleValue()-q2.j().v().doubleValue(), q3.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(q1.k().v().doubleValue()-q2.k().v().doubleValue(), q3.k().v(), BigDecimal.ZERO));
		
		G.QQUAD.multiply().call(q1, q2, q3);

		assertTrue(isNear(11, q3.r().v(), TOL));
		assertTrue(isNear(-30, q3.i().v(), TOL));
		assertTrue(isNear(25, q3.j().v(), TOL));
		assertTrue(isNear(26, q3.k().v(), TOL));

		G.QQUAD.divide().call(q3, q2, q1);

		assertTrue(isNear(1, q1.r().v(), TOL));
		assertTrue(isNear(-2, q1.i().v(), TOL));
		assertTrue(isNear(3, q1.j().v(), TOL));
		assertTrue(isNear(2, q1.k().v(), TOL));
		
	}
	
	@Test
	public void testConjugate() {
		QuaternionFloat128Member a = new QuaternionFloat128Member(BigDecimal.valueOf(5),BigDecimal.valueOf(3),BigDecimal.valueOf(-1),BigDecimal.valueOf(-7));
		QuaternionFloat128Member b = new QuaternionFloat128Member();
		QuaternionFloat128Member c = new QuaternionFloat128Member();
		
		G.QQUAD.conjugate().call(a, b);
		G.QQUAD.multiply().call(a, b, c);
		assertTrue(isNear(84,c.r().v(), TOL));
		assertTrue(isNear(0,c.i().v(), TOL));
		assertTrue(isNear(0,c.j().v(), TOL));
		assertTrue(isNear(0,c.k().v(), TOL));
	}
	
	@Test
	public void mathematicalMethods() {
		
		QuaternionFloat128Member a = G.QQUAD.construct();
		QuaternionFloat128Member b = G.QQUAD.construct();
		QuaternionFloat128Member c = G.QQUAD.construct();
		Float128Member t = G.QUAD.construct();
		
		// G.QQUAD.add();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		b.setR(BigDecimal.valueOf(5));
		b.setI(BigDecimal.valueOf(7));
		b.setJ(BigDecimal.valueOf(9));
		b.setK(BigDecimal.valueOf(11));
		G.QQUAD.add().call(a, b, c);
		assertTrue(isNear(6, c.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(9, c.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(12, c.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(15, c.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.assign();
		G.QQUAD.assign().call(a, b);
		assertTrue(G.QUAD.isEqual().call(a.r(), b.r()));
		assertTrue(G.QUAD.isEqual().call(a.i(), b.i()));
		assertTrue(G.QUAD.isEqual().call(a.j(), b.j()));
		assertTrue(G.QUAD.isEqual().call(a.k(), b.k()));
		
		// G.QQUAD.cbrt();
		a.setR(BigDecimal.valueOf(27));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.cbrt().call(a, b);
		assertTrue(isNear(3, b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), TOL));
		assertTrue(isNear(0, b.j().v(), TOL));
		assertTrue(isNear(0, b.k().v(), TOL));
		
		// G.QQUAD.conjugate();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		G.QQUAD.conjugate().call(a, b);
		assertTrue(isNear(1, b.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(-2, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(-3, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(-4, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.construct();
		a = G.QQUAD.construct();
		assertTrue(isNear(0, a.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.construct("");
		a = G.QQUAD.construct("{3,-1,0,6}");
		assertTrue(isNear(3, a.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(-1, a.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(6, a.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.construct(other);
		b = G.QQUAD.construct(a);
		assertTrue(isNear(3, b.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(-1, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(6, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.cos();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.cos().call(a, b);
		assertTrue(isNear(Math.cos(Math.PI/2), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.cosh();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.cosh().call(a, b);
		assertTrue(isNear(Math.cosh(Math.PI/2), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.divide();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		b.setR(BigDecimal.valueOf(0.5));
		b.setI(BigDecimal.valueOf(0));
		b.setJ(BigDecimal.valueOf(0));
		b.setK(BigDecimal.valueOf(0));
		G.QQUAD.divide().call(a, b, c);
		assertTrue(isNear(2, c.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(4, c.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(6, c.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(8, c.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.E();
		G.QQUAD.E().call(a);
		assertTrue(isNear(Math.E, a.r().v(), TOL));
		assertTrue(isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.exp();
		a.setR(BigDecimal.valueOf(2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.exp().call(a, b);
		assertTrue(isNear(Math.exp(2), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.infinite();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		assertFalse(G.QQUAD.isInfinite().call(a));
		G.QQUAD.infinite().call(a);
		assertTrue(G.QQUAD.isInfinite().call(a));
		
		// G.QQUAD.invert();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		// wolfram alpha: confirmed this is right
		G.QQUAD.invert().call(a, b);
		assertTrue(isNear( 0.033333333333333333, b.r().v(), TOL));
		assertTrue(isNear(-0.066666666666666666, b.i().v(), TOL));
		assertTrue(isNear(-0.100000000000000000, b.j().v(), TOL));
		assertTrue(isNear(-0.133333333333333333, b.k().v(), TOL));
		// wolfram alpha: confirmed this is right
		G.QQUAD.unity().call(c);
		G.QQUAD.divide().call(c, a, c);
		assertTrue(isNear( 0.033333333333333333, c.r().v(), TOL));
		assertTrue(isNear(-0.066666666666666666, c.i().v(), TOL));
		assertTrue(isNear(-0.100000000000000000, c.j().v(), TOL));
		assertTrue(isNear(-0.133333333333333333, c.k().v(), TOL));

		// G.QQUAD.isEqual();
		G.QQUAD.zero().call(b);
		assertFalse(G.QQUAD.isEqual().call(a, b));
		G.QQUAD.assign().call(a, b);
		assertTrue(G.QQUAD.isEqual().call(a, b));
		
		// G.QQUAD.isInfinite();
		//   tested by infinite() test above
		
		// G.QQUAD.isNaN();
		//   tested by nan() test below
		
		// G.QQUAD.isNotEqual();
		G.QQUAD.zero().call(b);
		assertTrue(G.QQUAD.isNotEqual().call(a, b));
		G.QQUAD.assign().call(a, b);
		assertFalse(G.QQUAD.isNotEqual().call(a, b));
		
		// G.QQUAD.isZero();
		//   tested by zero() test below
		
		// G.QQUAD.log();
		a.setR(BigDecimal.valueOf(4));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.log().call(a, b);
		assertTrue(isNear(Math.log(4), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.multiply();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		b.setR(BigDecimal.valueOf(7));
		b.setI(BigDecimal.valueOf(-4));
		b.setJ(BigDecimal.valueOf(8));
		b.setK(BigDecimal.valueOf(3));
		G.QQUAD.multiply().call(a, b, c);
		G.QQUAD.divide().call(c, b, b);
		assertTrue(G.QUAD.within().call(new Float128Member(TOL), a.r(), b.r()));
		assertTrue(G.QUAD.within().call(new Float128Member(TOL), a.i(), b.i()));
		assertTrue(G.QUAD.within().call(new Float128Member(TOL), a.j(), b.j()));
		assertTrue(G.QUAD.within().call(new Float128Member(TOL), a.k(), b.k()));
		
		// G.QQUAD.nan();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		assertFalse(G.QQUAD.isNaN().call(a));
		G.QQUAD.nan().call(a);
		assertTrue(G.QQUAD.isNaN().call(a));
		
		// G.QQUAD.negate();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		G.QQUAD.negate().call(a, b);
		assertTrue(isNear(-1, b.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(-2, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(-3, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(-4, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.norm();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		G.QQUAD.norm().call(a, t);
		assertTrue(isNear(Math.sqrt(1*1 + 2*2 + 3*3 + 4*4), t.v(), TOL));
		
		// G.QQUAD.PI();
		G.QQUAD.PI().call(a);
		assertTrue(isNear(Math.PI, a.r().v(), TOL));
		assertTrue(isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.pow();
		a.setR(BigDecimal.valueOf(7));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		b.setR(BigDecimal.valueOf(4));
		b.setI(BigDecimal.valueOf(0));
		b.setJ(BigDecimal.valueOf(0));
		b.setK(BigDecimal.valueOf(0));
		G.QQUAD.pow().call(a, b, c);
		assertTrue(isNear(7*7*7*7, c.r().v(), TOL));
		assertTrue(isNear(0, c.i().v(), TOL));
		assertTrue(isNear(0, c.j().v(), TOL));
		assertTrue(isNear(0, c.k().v(), TOL));
		
		// G.QQUAD.power();
		a.setR(BigDecimal.valueOf(7));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.power().call(4, a, b);
		assertTrue(isNear(7*7*7*7, b.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.random();
		G.QQUAD.random().call(a);
		assertTrue(a.r().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.r().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.i().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.i().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.j().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.j().v().compareTo(BigDecimal.ONE) < 0);
		assertTrue(a.k().v().compareTo(BigDecimal.ZERO) >= 0);
		assertTrue(a.k().v().compareTo(BigDecimal.ONE) < 0);
		
		// G.QQUAD.real();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		G.QQUAD.real().call(a, t);
		assertTrue(isNear(1, t.v(), BigDecimal.ZERO));
		
		// G.QQUAD.round();
		a.setR(BigDecimal.valueOf(1.1));
		a.setI(BigDecimal.valueOf(-2.2));
		a.setJ(BigDecimal.valueOf(3.3));
		a.setK(BigDecimal.valueOf(-4.4));
		t.setV(BigDecimal.valueOf(1));
		G.QQUAD.round().call(Mode.HALF_EVEN, t, a, b);
		assertTrue(isNear(1, b.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(-2, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(3, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(-4, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.scale();
		a.setR(BigDecimal.valueOf(1.1));
		a.setI(BigDecimal.valueOf(-2.2));
		a.setJ(BigDecimal.valueOf(3.3));
		a.setK(BigDecimal.valueOf(-4.4));
		b.setR(BigDecimal.valueOf(3));
		b.setI(BigDecimal.valueOf(0));
		b.setJ(BigDecimal.valueOf(0));
		b.setK(BigDecimal.valueOf(0));
		G.QQUAD.scale().call(b, a, c);
		assertTrue(isNear(3.3, c.r().v(), TOL));
		assertTrue(isNear(-6.6, c.i().v(), TOL));
		assertTrue(isNear(9.9, c.j().v(), TOL));
		assertTrue(isNear(-13.2, c.k().v(), TOL));
		
		// G.QQUAD.sin();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.sin().call(a, b);
		assertTrue(isNear(Math.sin(Math.PI/2), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.sinAndCos();
		
		// G.QQUAD.sinc();
		
		// G.QQUAD.sinch();
		
		// G.QQUAD.sinchpi();
		
		// G.QQUAD.sincpi();
		
		// G.QQUAD.sinh();
		a.setR(BigDecimal.valueOf(Math.PI/2));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.sinh().call(a, b);
		assertTrue(isNear(Math.sinh(Math.PI/2), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.sinhAndCosh();
		
		// G.QQUAD.sqrt();
		a.setR(BigDecimal.valueOf(25));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.sqrt().call(a, b);
		assertTrue(isNear(5, b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), TOL));
		assertTrue(isNear(0, b.j().v(), TOL));
		assertTrue(isNear(0, b.k().v(), TOL));
		
		// G.QQUAD.subtract();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		b.setR(BigDecimal.valueOf(5));
		b.setI(BigDecimal.valueOf(7));
		b.setJ(BigDecimal.valueOf(9));
		b.setK(BigDecimal.valueOf(11));
		G.QQUAD.subtract().call(a, b, c);
		assertTrue(isNear(-4, c.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(-5, c.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(-6, c.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(-7, c.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.tan();
		a.setR(BigDecimal.valueOf(Math.PI/16));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.tan().call(a, b);
		assertTrue(isNear(Math.tan(Math.PI/16), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.tanh();
		a.setR(BigDecimal.valueOf(Math.PI/16));
		a.setI(BigDecimal.valueOf(0));
		a.setJ(BigDecimal.valueOf(0));
		a.setK(BigDecimal.valueOf(0));
		G.QQUAD.tanh().call(a, b);
		assertTrue(isNear(Math.tanh(Math.PI/16), b.r().v(), TOL));
		assertTrue(isNear(0, b.i().v(), TOL));
		assertTrue(isNear(0, b.j().v(), TOL));
		assertTrue(isNear(0, b.k().v(), TOL));
		
		// G.QQUAD.unity();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		G.QQUAD.unity().call(a);
		assertTrue(isNear(1, a.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(0, a.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.unreal();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		G.QQUAD.unreal().call(a, b);
		assertTrue(isNear(0, b.r().v(), BigDecimal.ZERO));
		assertTrue(isNear(2, b.i().v(), BigDecimal.ZERO));
		assertTrue(isNear(3, b.j().v(), BigDecimal.ZERO));
		assertTrue(isNear(4, b.k().v(), BigDecimal.ZERO));
		
		// G.QQUAD.zero();
		a.setR(BigDecimal.valueOf(1));
		a.setI(BigDecimal.valueOf(2));
		a.setJ(BigDecimal.valueOf(3));
		a.setK(BigDecimal.valueOf(4));
		assertFalse(G.QQUAD.isZero().call(a));
		G.QQUAD.zero().call(a);
		assertTrue(G.QQUAD.isZero().call(a));
	}

	private boolean isNear(double a, BigDecimal b, BigDecimal tol) {
		return BigDecimal.valueOf(a).subtract(b).abs().compareTo(tol) <= 0;
	}
}
