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
package nom.bdezonia.zorbage.type.data.float16.quaternion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestQuaternionFloat16Algebra {

	private static final double TOL = 0.01;
	
	@Test
	public void run() {
		QuaternionFloat16Member q1 = G.QHLF.construct();
		
		// TODO define a ctor that takes four doubles
		q1.setR(1);
		q1.setI(-2);
		q1.setJ(3);
		q1.setK(2);
		
		Float16Member tmp = new Float16Member();
		
		G.QHLF.norm().call(q1, tmp);

		assertEquals(3*Math.sqrt(2),tmp.v(),TOL);
		
		QuaternionFloat16Member q2 = G.QHLF.construct();

		q2.setR(11);
		q2.setI(-2);
		q2.setJ(0);
		q2.setK(-2);

		G.QHLF.norm().call(q2, tmp);

		assertEquals(Math.sqrt(129),tmp.v(),TOL);
		
		QuaternionFloat16Member q3 = G.QHLF.construct();

		G.QHLF.add().call(q1, q2, q3);

		assertEquals(q1.r()+q2.r(), q3.r(), 0);
		assertEquals(q1.i()+q2.i(), q3.i(), 0);
		assertEquals(q1.j()+q2.j(), q3.j(), 0);
		assertEquals(q1.k()+q2.k(), q3.k(), 0);
		
		G.QHLF.subtract().call(q1, q2, q3);

		assertEquals(q1.r()-q2.r(), q3.r(), 0);
		assertEquals(q1.i()-q2.i(), q3.i(), 0);
		assertEquals(q1.j()-q2.j(), q3.j(), 0);
		assertEquals(q1.k()-q2.k(), q3.k(), 0);

		G.QHLF.multiply().call(q1, q2, q3);

		assertEquals(11, q3.r(), TOL);
		assertEquals(-30, q3.i(), TOL);
		assertEquals(25, q3.j(), TOL);
		assertEquals(26, q3.k(), TOL);

		G.QHLF.divide().call(q3, q2, q1);

		assertEquals(1, q1.r(), TOL);
		assertEquals(-2, q1.i(), TOL);
		assertEquals(3, q1.j(), TOL);
		assertEquals(2, q1.k(), TOL);
		
	}
	
	@Test
	public void testConjugate() {
		QuaternionFloat16Member a = new QuaternionFloat16Member(5,3,-1,-7);
		QuaternionFloat16Member b = new QuaternionFloat16Member();
		QuaternionFloat16Member c = new QuaternionFloat16Member();
		
		G.QHLF.conjugate().call(a, b);
		G.QHLF.multiply().call(a, b, c);
		assertEquals(84,c.r(), TOL);
		assertEquals(0,c.i(), TOL);
		assertEquals(0,c.j(), TOL);
		assertEquals(0,c.k(), TOL);
	}
	
	@Test
	public void mathematicalMethods() {
		
		double tol = 0.01;
		
		QuaternionFloat16Member a = G.QHLF.construct();
		QuaternionFloat16Member b = G.QHLF.construct();
		QuaternionFloat16Member c = G.QHLF.construct();
		Float16Member t = G.HLF.construct();
		
		// G.OHLF.add();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		G.QHLF.add().call(a, b, c);
		assertEquals(6, c.r(), 0);
		assertEquals(9, c.i(), 0);
		assertEquals(12, c.j(), 0);
		assertEquals(15, c.k(), 0);
		
		// G.OHLF.assign();
		G.QHLF.assign().call(a, b);
		assertEquals(a.r(), b.r(), 0);
		assertEquals(a.i(), b.i(), 0);
		assertEquals(a.j(), b.j(), 0);
		assertEquals(a.k(), b.k(), 0);
		
		// G.OHLF.cbrt();
		a.setR(27);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.cbrt().call(a, b);
		assertEquals(3, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.OHLF.conjugate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.conjugate().call(a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.OHLF.construct();
		a = G.QHLF.construct();
		assertEquals(0, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OHLF.construct("");
		a = G.QHLF.construct("{3,-1,0,6}");
		assertEquals(3, a.r(), 0);
		assertEquals(-1, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(6, a.k(), 0);
		
		// G.OHLF.construct(other);
		b = G.QHLF.construct(a);
		assertEquals(3, b.r(), 0);
		assertEquals(-1, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(6, b.k(), 0);
		
		// G.OHLF.cos();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.cos().call(a, b);
		assertEquals(FastMath.cos(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.cosh();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.cosh().call(a, b);
		assertEquals(FastMath.cosh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.divide();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(0.5f);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QHLF.divide().call(a, b, c);
		assertEquals(2, c.r(), 0);
		assertEquals(4, c.i(), 0);
		assertEquals(6, c.j(), 0);
		assertEquals(8, c.k(), 0);
		
		// G.OHLF.E();
		G.QHLF.E().call(a);
		assertEquals(Math.E, a.r(), tol);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OHLF.exp();
		a.setR(2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.exp().call(a,b);
		assertEquals(FastMath.exp(2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.infinite();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QHLF.isInfinite().call(a));
		G.QHLF.infinite().call(a);
		assertTrue(G.QHLF.isInfinite().call(a));
		
		// G.OHLF.invert();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.invert().call(a, b);
		G.QHLF.unity().call(c);
		G.QHLF.divide().call(c, a, c);
		assertEquals(c.r(), b.r(), 0);
		assertEquals(c.i(), b.i(), 0);
		assertEquals(c.j(), b.j(), 0);
		assertEquals(c.k(), b.k(), 0);

		// G.OHLF.isEqual();
		G.QHLF.zero().call(b);
		assertFalse(G.QHLF.isEqual().call(a, b));
		G.QHLF.assign().call(a, b);
		assertTrue(G.QHLF.isEqual().call(a, b));
		
		// G.OHLF.isInfinite();
		//   tested by infinite() test above
		
		// G.OHLF.isNaN();
		//   tested by nan() test below
		
		// G.OHLF.isNotEqual();
		G.QHLF.zero().call(b);
		assertTrue(G.QHLF.isNotEqual().call(a, b));
		G.QHLF.assign().call(a, b);
		assertFalse(G.QHLF.isNotEqual().call(a, b));
		
		// G.OHLF.isZero();
		//   tested by zero() test below
		
		// G.OHLF.log();
		a.setR(4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.log().call(a, b);
		assertEquals(Math.log(4), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.multiply();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(7);
		b.setI(-4);
		b.setJ(8);
		b.setK(3);
		G.QHLF.multiply().call(a, b, c);
		G.QHLF.divide().call(c, b, b);
		assertEquals(a.r(), b.r(), tol);
		assertEquals(a.i(), b.i(), tol);
		assertEquals(a.j(), b.j(), tol);
		assertEquals(a.k(), b.k(), tol);
		
		// G.OHLF.nan();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QHLF.isNaN().call(a));
		G.QHLF.nan().call(a);
		assertTrue(G.QHLF.isNaN().call(a));
		
		// G.OHLF.negate();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.negate().call(a, b);
		assertEquals(-1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(-3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.OHLF.norm();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.norm().call(a, t);
		assertEquals(Math.sqrt(1*1 + 2*2 + 3*3 + 4*4), t.v(), tol);
		
		// G.OHLF.PI();
		G.QHLF.PI().call(a);
		assertEquals(Math.PI, a.r(), tol);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OHLF.pow();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		b.setR(4);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QHLF.pow().call(a, b, c);
		assertEquals(7*7*7*7, c.r(), 3);
		assertEquals(0, c.i(), tol);
		assertEquals(0, c.j(), tol);
		assertEquals(0, c.k(), tol);
		
		// G.OHLF.power();
		a.setR(7);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.power().call(4, a, b);
		assertEquals(7*7*7*7, b.r(), 1);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.random();
		// TODO - how to test
		
		// G.OHLF.real();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.real().call(a, t);
		assertEquals(1, t.v(), 0);
		
		// G.OHLF.round();
		a.setR(1.1f);
		a.setI(-2.2f);
		a.setJ(3.3f);
		a.setK(-4.4f);
		t.setV(1);
		G.QHLF.round().call(Mode.HALF_EVEN, t, a, b);
		assertEquals(1, b.r(), 0);
		assertEquals(-2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(-4, b.k(), 0);
		
		// G.OHLF.scale();
		a.setR(1.1f);
		a.setI(-2.2f);
		a.setJ(3.3f);
		a.setK(-4.4f);
		b.setR(3);
		b.setI(0);
		b.setJ(0);
		b.setK(0);
		G.QHLF.scale().call(b, a, c);
		assertEquals(3.3, c.r(), tol);
		assertEquals(-6.6, c.i(), tol);
		assertEquals(9.9, c.j(), tol);
		assertEquals(-13.2, c.k(), tol);
		
		// G.OHLF.sin();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.sin().call(a, b);
		assertEquals(FastMath.sin(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.sinAndCos();
		
		// G.OHLF.sinc();
		
		// G.OHLF.sinch();
		
		// G.OHLF.sinchpi();
		
		// G.OHLF.sincpi();
		
		// G.OHLF.sinh();
		a.setR((float)Math.PI/2);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.sinh().call(a, b);
		assertEquals(FastMath.sinh(Math.PI/2), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.sinhAndCosh();
		
		// G.OHLF.sqrt();
		a.setR(25);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.sqrt().call(a, b);
		assertEquals(5, b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.OHLF.subtract();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		b.setR(5);
		b.setI(7);
		b.setJ(9);
		b.setK(11);
		G.QHLF.subtract().call(a, b, c);
		assertEquals(-4, c.r(), 0);
		assertEquals(-5, c.i(), 0);
		assertEquals(-6, c.j(), 0);
		assertEquals(-7, c.k(), 0);
		
		// G.OHLF.tan();
		a.setR((float)Math.PI/4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.tan().call(a, b);
		assertEquals(FastMath.tan(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), 0);
		assertEquals(0, b.j(), 0);
		assertEquals(0, b.k(), 0);
		
		// G.OHLF.tanh();
		a.setR((float)Math.PI/4);
		a.setI(0);
		a.setJ(0);
		a.setK(0);
		G.QHLF.tanh().call(a, b);
		assertEquals(FastMath.tanh(Math.PI/4), b.r(), tol);
		assertEquals(0, b.i(), tol);
		assertEquals(0, b.j(), tol);
		assertEquals(0, b.k(), tol);
		
		// G.OHLF.unity();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.unity().call(a);
		assertEquals(1, a.r(), 0);
		assertEquals(0, a.i(), 0);
		assertEquals(0, a.j(), 0);
		assertEquals(0, a.k(), 0);
		
		// G.OHLF.unreal();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		G.QHLF.unreal().call(a, b);
		assertEquals(0, b.r(), 0);
		assertEquals(2, b.i(), 0);
		assertEquals(3, b.j(), 0);
		assertEquals(4, b.k(), 0);
		
		// G.OHLF.zero();
		a.setR(1);
		a.setI(2);
		a.setJ(3);
		a.setK(4);
		assertFalse(G.QHLF.isZero().call(a));
		G.QHLF.zero().call(a);
		assertTrue(G.QHLF.isZero().call(a));
	}
}
