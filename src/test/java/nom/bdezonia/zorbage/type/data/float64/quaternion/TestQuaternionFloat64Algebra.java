/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestQuaternionFloat64Algebra {

	private static final double TOL = 0.0000000001;
	
	@Test
	public void run() {
		QuaternionFloat64Member q1 = G.QDBL.construct();
		
		// TODO define a ctor that takes four doubles
		q1.setR(1);
		q1.setI(-2);
		q1.setJ(3);
		q1.setK(2);
		
		Float64Member tmp = new Float64Member();
		
		G.QDBL.norm().call(q1, tmp);

		assertEquals(3*Math.sqrt(2),tmp.v(),TOL);
		
		QuaternionFloat64Member q2 = G.QDBL.construct();

		q2.setR(11);
		q2.setI(-2);
		q2.setJ(0);
		q2.setK(-2);

		G.QDBL.norm().call(q2, tmp);

		assertEquals(Math.sqrt(129),tmp.v(),TOL);
		
		QuaternionFloat64Member q3 = G.QDBL.construct();

		G.QDBL.add().call(q1, q2, q3);

		assertEquals(q1.r()+q2.r(), q3.r(), 0);
		assertEquals(q1.i()+q2.i(), q3.i(), 0);
		assertEquals(q1.j()+q2.j(), q3.j(), 0);
		assertEquals(q1.k()+q2.k(), q3.k(), 0);
		
		G.QDBL.subtract().call(q1, q2, q3);

		assertEquals(q1.r()-q2.r(), q3.r(), 0);
		assertEquals(q1.i()-q2.i(), q3.i(), 0);
		assertEquals(q1.j()-q2.j(), q3.j(), 0);
		assertEquals(q1.k()-q2.k(), q3.k(), 0);

		G.QDBL.multiply().call(q1, q2, q3);

		assertEquals(11, q3.r(), TOL);
		assertEquals(-30, q3.i(), TOL);
		assertEquals(25, q3.j(), TOL);
		assertEquals(26, q3.k(), TOL);

		G.QDBL.divide().call(q3, q2, q1);

		assertEquals(1, q1.r(), TOL);
		assertEquals(-2, q1.i(), TOL);
		assertEquals(3, q1.j(), TOL);
		assertEquals(2, q1.k(), TOL);
		
	}
	
	@Test
	public void testConjugate() {
		QuaternionFloat64Member a = new QuaternionFloat64Member(5,3,-1,-7);
		QuaternionFloat64Member b = new QuaternionFloat64Member();
		QuaternionFloat64Member c = new QuaternionFloat64Member();
		
		G.QDBL.conjugate().call(a, b);
		G.QDBL.multiply().call(a, b, c);
		assertEquals(84,c.r(), TOL);
		assertEquals(0,c.i(), TOL);
		assertEquals(0,c.j(), TOL);
		assertEquals(0,c.k(), TOL);
	}
	
	@Test
	public void mathematicalMethods() {
		
		double tol = 0.00000000001;
		
		QuaternionFloat64Member a = G.QDBL.construct();
		QuaternionFloat64Member b = G.QDBL.construct();
		QuaternionFloat64Member c = G.QDBL.construct();
		Float64Member t = G.DBL.construct();
		
		// G.ODBL.add();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		G.QDBL.add().call(a, b, c);
		assertEquals(6, c.r(), 0);
		assertEquals(9, c.i(), 0);
		assertEquals(12, c.j(), 0);
		assertEquals(15, c.k(), 0);
		
		// G.ODBL.assign();
		G.QDBL.assign().call(a, b);
		assertEquals(a.r(), b.r(), 0);
		assertEquals(a.i(), b.i(), 0);
		assertEquals(a.j(), b.j(), 0);
		assertEquals(a.k(), b.k(), 0);
		
		// G.ODBL.cbrt();
		a.setR(27);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.cbrt().call(a, b);
		assertEquals(3, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.ODBL.conjugate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.conjugate().call(a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.ODBL.construct();
		a = G.QDBL.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.ODBL.construct("");
		a = G.QDBL.construct("{3,-1,0,6}");
		assertEquals(3, a.r(), 0);
		assertEquals(-1, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(6, a.k(), 0);
		
		// G.ODBL.construct(other);
		b = G.QDBL.construct(a);
		assertEquals(3, b.r(), 0);
		assertEquals(-1, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(6, b.k(), 0);
		
		// G.ODBL.cos();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.cos().call(a, b);
		assertEquals(FastMath.cos(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.cosh();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.cosh().call(a, b);
		assertEquals(FastMath.cosh(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.divide();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(0.5);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QDBL.divide().call(a, b, c);
		assertEquals(2, c.r(), 0);
		assertEquals(4, c.i(), 0);
		assertEquals(6, c.j(), 0);
		assertEquals(8, c.k(), 0);
		
		// G.ODBL.E();
		G.QDBL.E().call(a);
		assertEquals(Math.E, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.ODBL.exp();
		a.setR(2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.exp().call(a,b);
		assertEquals(FastMath.exp(2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.infinite();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QDBL.isInfinite().call(a));
		G.QDBL.infinite().call(a);
		assertTrue(G.QDBL.isInfinite().call(a));
		
		// G.ODBL.invert();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.invert().call(a, b);
		G.QDBL.unity().call(c);
		G.QDBL.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), 0);
		assertEquals(c.i(), b.i(), 0);
		assertEquals(c.j(), b.j(), 0);
		assertEquals(c.k(), b.k(), 0);

		// G.ODBL.isEqual();
		G.QDBL.zero().call(b);
		assertFalse(G.QDBL.isEqual().call(a, b));
		G.QDBL.assign().call(a, b);
		assertTrue(G.QDBL.isEqual().call(a, b));
		
		// G.ODBL.isInfinite();
		//   tested by infinite() test above
		
		// G.ODBL.isNaN();
		//   tested by nan() test below
		
		// G.ODBL.isNotEqual();
		G.QDBL.zero().call(b);
		assertTrue(G.QDBL.isNotEqual().call(a, b));
		G.QDBL.assign().call(a, b);
		assertFalse(G.QDBL.isNotEqual().call(a, b));
		
		// G.ODBL.isZero();
		//   tested by zero() test below
		
		// G.ODBL.log();
		a.setR(4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.log().call(a, b);
		assertEquals(Math.log(4), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.multiply();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(7);
		b.setI(-4);
		b.setJ(8);
		b.setK(3);
		G.QDBL.multiply().call(a, b, c);
		G.QDBL.divide().call(c, b, b);
		assertEquals(a.r(), b.r(), tol);
		assertEquals(a.i(), b.i(), tol);
		assertEquals(a.j(), b.j(), tol);
		assertEquals(a.k(), b.k(), tol);
		
		// G.ODBL.nan();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QDBL.isNaN().call(a));
		G.QDBL.nan().call(a);
		assertTrue(G.QDBL.isNaN().call(a));
		
		// G.ODBL.negate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.negate().call(a, b);
		assertEquals(-1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.ODBL.norm();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.norm().call(a, t);
		assertEquals(Math.sqrt(1*1 + 2*2 + 3*3 + 4*4), t.v(), 0);
		
		// G.ODBL.PI();
		G.QDBL.PI().call(a);
		assertEquals(Math.PI, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.ODBL.pow();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		b.setR(4);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QDBL.pow().call(a, b, c);
		assertEquals(7*7*7*7, c.r(), tol);
		assertEquals(0, c.i(), tol);
		assertEquals(0, c.j(), tol);
		assertEquals(0, c.k(), tol);
		
		// G.ODBL.power();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.power().call(4, a, b);
		assertEquals(7*7*7*7, b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.random();
		// TODO - how to test
		
		// G.ODBL.real();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.real().call(a, t);
		assertEquals(1, t.v(), 0);
		
		// G.ODBL.round();
		a.setR(1.1);
		a.setI(-2.2);
		a.setJ(3.3);
		a.setK(-4.4);
		t.setV(1);
		G.QDBL.round().call(Mode.HALF_EVEN, t, a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.ODBL.scale();
		a.setR(1.1);
		a.setI(-2.2);
		a.setJ(3.3);
		a.setK(-4.4);
		b.setR(3);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QDBL.scale().call(b, a, c);
		assertEquals(3.3, c.r(), tol);
		assertEquals(-6.6, c.i(), tol);
		assertEquals(9.9, c.j(), tol);
		assertEquals(-13.2, c.k(), tol);
		
		// G.ODBL.sin();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.sin().call(a, b);
		assertEquals(FastMath.sin(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.sinAndCos();
		
		// G.ODBL.sinc();
		
		// G.ODBL.sinch();
		
		// G.ODBL.sinchpi();
		
		// G.ODBL.sincpi();
		
		// G.ODBL.sinh();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.sinh().call(a, b);
		assertEquals(FastMath.sinh(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.sinhAndCosh();
		
		// G.ODBL.sqrt();
		a.setR(25);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.sqrt().call(a, b);
		assertEquals(5, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.ODBL.subtract();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		G.QDBL.subtract().call(a, b, c);
		assertEquals(-4, c.r(), 0);
		assertEquals(-5, c.i(), 0);
		assertEquals(-6, c.j(), 0);
		assertEquals(-7, c.k(), 0);
		
		// G.ODBL.tan();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.tan().call(a, b);
		assertEquals(FastMath.tan(Math.PI/2), b.r(), 0);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.ODBL.tanh();
		a.setR(Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QDBL.tanh().call(a, b);
		assertEquals(FastMath.tanh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.ODBL.unity();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.ODBL.unreal();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QDBL.unreal().call(a, b);
		assertEquals(0, b.r(), 0);
		assertEquals(2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(4, b.k(), 0);
		
		// G.ODBL.zero();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QDBL.isZero().call(a));
		G.QDBL.zero().call(a);
		assertTrue(G.QDBL.isZero().call(a));
	}
}
