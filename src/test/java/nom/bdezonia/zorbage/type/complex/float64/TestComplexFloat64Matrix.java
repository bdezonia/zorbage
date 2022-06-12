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
package nom.bdezonia.zorbage.type.complex.float64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestComplexFloat64Matrix {

	final double tol = 0.00000000000001;
	
	@Test
	public void test1() {
		
		ComplexFloat64Member value = G.CDBL.construct();
		Float64Member num = G.DBL.construct();
		
		ComplexFloat64MatrixMember a,b,c;

		// general ctor
		
		a = G.CDBL_MAT.construct();
		b = G.CDBL_MAT.construct();
		c = G.CDBL_MAT.construct();
		assertTrue(a != null && b != null && c != null);

		// construct from other
		
		a = new ComplexFloat64MatrixMember(3, 1, 1,4, 2,3, 1,3);
		b = G.CDBL_MAT.construct(a);
		assertEquals(a.rows(), b.rows());
		assertEquals(a.cols(), b.cols());
		assertTrue(G.CDBL_MAT.isEqual().call(a, b));
		
		// construct from string
		
		c = G.CDBL_MAT.construct("[[{1,2},{3,4}][{5,6},{7,8}]]");
		assertEquals(2, c.rows());
		assertEquals(2, c.cols());
		c.getV(0, 0, value);
		assertEquals(1, value.r(), 0);
		assertEquals(2, value.i(), 0);
		c.getV(0, 1, value);
		assertEquals(3, value.r(), 0);
		assertEquals(4, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(5, value.r(), 0);
		assertEquals(6, value.i(), 0);
		c.getV(1, 1, value);
		assertEquals(7, value.r(), 0);
		assertEquals(8, value.i(), 0);
		
		// construct with storage construction param
		
		c = G.CDBL_MAT.construct(StorageConstruction.MEM_SPARSE, 25, 25);
		assertTrue(c != null);

		// add
		
		a = new ComplexFloat64MatrixMember(3, 1, 1,4, 2,3, 1,3);
		b = new ComplexFloat64MatrixMember(3, 1, 2,1, 0,9, -2,-5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.add().call(a, b, c);
		assertEquals(3, c.rows());
		assertEquals(1, c.cols());
		c.getV(0, 0, value);
		assertEquals(3, value.r(), 0);
		assertEquals(5, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(2, value.r(), 0);
		assertEquals(12, value.i(), 0);
		c.getV(2, 0, value);
		assertEquals(-1, value.r(), 0);
		assertEquals(-2, value.i(), 0);
	
		// subtract
		
		a = new ComplexFloat64MatrixMember(3, 1, 1,4, 2,3, 1,3);
		b = new ComplexFloat64MatrixMember(3, 1, 2,1, 0,9, -2,-5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.subtract().call(a, b, c);
		assertEquals(3, c.rows());
		assertEquals(1, c.cols());
		c.getV(0, 0, value);
		assertEquals(-1, value.r(), 0);
		assertEquals(3, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(2, value.r(), 0);
		assertEquals(-6, value.i(), 0);
		c.getV(2, 0, value);
		assertEquals(3, value.r(), 0);
		assertEquals(8, value.i(), 0);

		// addScalar()
		
		value.setFromDouble(2,1);
		b = new ComplexFloat64MatrixMember(3, 1, 2,1, 0,9, -2,-5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.addScalar().call(value, b, c);
		assertEquals(3, c.rows());
		assertEquals(1, c.cols());
		c.getV(0, 0, value);
		assertEquals(4, value.r(), 0);
		assertEquals(2, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(2, value.r(), 0);
		assertEquals(10, value.i(), 0);
		c.getV(2, 0, value);
		assertEquals(0, value.r(), 0);
		assertEquals(-4, value.i(), 0);
		
		// subtractScalar()
		
		value.setFromDouble(2,1);
		b = new ComplexFloat64MatrixMember(3, 1, 2,1, 0,9, -2,-5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.subtractScalar().call(value, b, c);
		assertEquals(3, c.rows());
		assertEquals(1, c.cols());
		c.getV(0, 0, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(-2, value.r(), 0);
		assertEquals(8, value.i(), 0);
		c.getV(2, 0, value);
		assertEquals(-4, value.r(), 0);
		assertEquals(-6, value.i(), 0);
		
		// assign
		
		a = new ComplexFloat64MatrixMember(1,2, 3,4, 4,5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.assign().call(a, c);
		assertTrue(G.CDBL_MAT.isEqual().call(a, c));
		
		// conjugate
		
		a = new ComplexFloat64MatrixMember(1,2, 3,4, 4,5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.conjugate().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		c.getV(0, 0, value);
		assertEquals(3, value.r(), 0);
		assertEquals(-4, value.i(), 0);
		c.getV(0, 1, value);
		assertEquals(4, value.r(), 0);
		assertEquals(-5, value.i(), 0);
		
		// conjugateTranspose()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.conjugateTranspose().call(a, c);
		assertEquals(a.rows(), c.cols());
		assertEquals(a.cols(), c.rows());
		c.getV(0, 0, value);
		assertEquals(3, value.r(), 0);
		assertEquals(-4, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(4, value.r(), 0);
		assertEquals(-5, value.i(), 0);

		// sin()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		b = new ComplexFloat64MatrixMember();
		try {
			// try to calc on a non-swaure matrix
			G.CDBL_MAT.sin().call(a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sin().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// cos()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		b = new ComplexFloat64MatrixMember();
		try {
			// try to calc on a non-swaure matrix
			G.CDBL_MAT.cos().call(a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.cos().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// sinAndCos()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		b = new ComplexFloat64MatrixMember();
		c = new ComplexFloat64MatrixMember();
		try {
			// try to calc on a non-swaure matrix
			G.CDBL_MAT.sinAndCos().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		b = new ComplexFloat64MatrixMember();
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sinAndCos().call(a, b, c);
		assertEquals(a.rows(), b.rows());
		assertEquals(a.cols(), b.cols());
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());

		// sinh()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		c = new ComplexFloat64MatrixMember();
		try {
			// try to calc on a non-swaure matrix
			G.CDBL_MAT.sinh().call(a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sinh().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// cosh()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		b = new ComplexFloat64MatrixMember();
		try {
			// try to calc on a non-swaure matrix
			G.CDBL_MAT.cosh().call(a, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.cosh().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// sinhAndCosh()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		b = new ComplexFloat64MatrixMember();
		c = new ComplexFloat64MatrixMember();
		try {
			// try to calc on a non-swaure matrix
			G.CDBL_MAT.sinhAndCosh().call(a, b, c);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		b = new ComplexFloat64MatrixMember();
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sinhAndCosh().call(a, b, c);
		assertEquals(a.rows(), b.rows());
		assertEquals(a.cols(), b.cols());
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		
		// tan()
		
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.tan().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// tanh()
		
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.tanh().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// exp()
		
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.exp().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy
		
		// log()
		
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.log().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		// TODO: test values for accuracy (and if it even converges)
		
		// det()
		
		ComplexFloat64Member value2 = G.CDBL.construct();
		ComplexFloat64Member value3 = G.CDBL.construct();
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		// 2x2 det = ad - bc
		num.setV(0.00000000000001);
		a.getV(0, 0, value2); // a
		a.getV(1, 1, value3); // d
		G.CDBL.multiply().call(value2, value3, value);
		a.getV(0, 1, value2); // b
		a.getV(1, 0, value3); // c
		G.CDBL.multiply().call(value2, value3, value2);
		G.CDBL.subtract().call(value, value2, value3);
		G.CDBL_MAT.det().call(a, value);
		assertTrue(G.CDBL.within().call(num, value3, value));
		
		// directProduct()
		
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		b = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.directProduct().call(a, b, c);
		assertEquals(4, c.rows());
		assertEquals(4, c.cols());
		// TODO: test values for accuracy

		// divide()
		
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		b = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.divide().call(a, b, c);
		assertEquals(2, c.rows());
		assertEquals(2, c.cols());
		// TODO: test values for accuracy

		// divideByScalar()
		
		value.setR(2);
		value.setI(0);
		a = new ComplexFloat64MatrixMember(2, 2, 3,4, 4,5, -4,11, 2,8);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.divideByScalar().call(value, a, c);
		assertEquals(2, c.rows());
		assertEquals(2, c.cols());
		c.getV(0, 0, value);
		assertEquals(1.5, value.r(), 0.0000000000001);
		assertEquals(2, value.i(), 0.0000000000001);
		c.getV(0, 1, value);
		assertEquals(2, value.r(), 0.0000000000001);
		assertEquals(2.5, value.i(), 0.0000000000001);
		c.getV(1, 0, value);
		assertEquals(-2, value.r(), 0.0000000000001);
		assertEquals(5.5, value.i(), 0.0000000000001);
		c.getV(1, 1, value);
		assertEquals(1, value.r(), 0.0000000000001);
		assertEquals(4, value.i(), 0.0000000000001);
		
		// divideElements()
		
		a = new ComplexFloat64MatrixMember(2, 2, 1,2, 3,4, 5,6, 7,8);
		b = new ComplexFloat64MatrixMember(2, 2, 8,7, 6,5, 4,3, 2,1);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.divideElements().call(a, b, c);
		assertEquals(2, c.rows());
		assertEquals(2, c.cols());
		a.getV(0, 0, value2);
		b.getV(0, 0, value3);
		G.CDBL.divide().call(value2, value3, value3);
		c.getV(0, 0, value);
		assertEquals(value3.r(), value.r(), 0.0000000000001);
		assertEquals(value3.i(), value.i(), 0.0000000000001);
		a.getV(0, 1, value2);
		b.getV(0, 1, value3);
		G.CDBL.divide().call(value2, value3, value3);
		c.getV(0, 1, value);
		assertEquals(value3.r(), value.r(), 0.0000000000001);
		assertEquals(value3.i(), value.i(), 0.0000000000001);
		a.getV(1, 0, value2);
		b.getV(1, 0, value3);
		G.CDBL.divide().call(value2, value3, value3);
		c.getV(1, 0, value);
		assertEquals(value3.r(), value.r(), 0.0000000000001);
		assertEquals(value3.i(), value.i(), 0.0000000000001);
		a.getV(1, 1, value2);
		b.getV(1, 1, value3);
		G.CDBL.divide().call(value2, value3, value3);
		c.getV(1, 1, value);
		assertEquals(value3.r(), value.r(), 0.0000000000001);
		assertEquals(value3.i(), value.i(), 0.0000000000001);

		// invert() : tested elsewhere
		
		a = new ComplexFloat64MatrixMember(2, 2, 1,7, -2,5, 0,3, 8,6);
		b.alloc(a.rows(), a.cols());
		G.CDBL_MAT.invert().call(a, b);
		assertEquals(a.rows(), b.rows());
		assertEquals(a.cols(), b.cols());
		// TODO: test values for accuracy? is tested elsewhere in zorbage

		// E()
		
		a = new ComplexFloat64MatrixMember(2, 2, 0,0, 0,0, 0,0, 0,0);
		G.CDBL_MAT.E().call(a);
		assertEquals(2, a.rows());
		assertEquals(2, a.cols());
		a.getV(0, 0, value);
		assertEquals(Math.E, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 1, value);
		assertEquals(Math.E, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 0, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);
		a.getV(0, 1, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);

		// GAMMA
		
		a = new ComplexFloat64MatrixMember(2, 2, 0,0, 0,0, 0,0, 0,0);
		G.CDBL_MAT.GAMMA().call(a);
		assertEquals(2, a.rows());
		assertEquals(2, a.cols());
		a.getV(0, 0, value);
		assertEquals(0.57721566490153286060, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 1, value);
		assertEquals(0.57721566490153286060, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 0, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);
		a.getV(0, 1, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);
		
		// PHI
		
		a = new ComplexFloat64MatrixMember(2, 2, 0,0, 0,0, 0,0, 0,0);
		G.CDBL_MAT.PHI().call(a);
		assertEquals(2, a.rows());
		assertEquals(2, a.cols());
		a.getV(0, 0, value);
		assertEquals(1.6180339887498948482045868, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 1, value);
		assertEquals(1.6180339887498948482045868, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 0, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);
		a.getV(0, 1, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);

		// PI
		
		a = new ComplexFloat64MatrixMember(2, 2, 0,0, 0,0, 0,0, 0,0);
		G.CDBL_MAT.PI().call(a);
		assertEquals(2, a.rows());
		assertEquals(2, a.cols());
		a.getV(0, 0, value);
		assertEquals(Math.PI, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 1, value);
		assertEquals(Math.PI, value.r(), 0.00000000000001);
		assertEquals(0, value.i(), 0);
		a.getV(1, 0, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);
		a.getV(0, 1, value);
		assertEquals(0, value.r(), 0);
		assertEquals(0, value.i(), 0);

		// isEqual() / isNotEqual()
		
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		b = new ComplexFloat64MatrixMember(3, 2, 2,0, 0,9, -2,-5, 1,2, 5,4, 6,0);
		assertFalse(G.CDBL_MAT.isEqual().call(a, b));
		assertTrue(G.CDBL_MAT.isNotEqual().call(a, b));
		b.getV(0, 0, value);
		value.setI(1);
		b.setV(0, 0, value);
		assertTrue(G.CDBL_MAT.isEqual().call(a, b));
		assertFalse(G.CDBL_MAT.isNotEqual().call(a, b));

		// infinite() / isInfinite()
		
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		assertFalse(G.CDBL_MAT.isInfinite().call(a));
		G.CDBL_MAT.infinite().call(a);
		assertTrue(G.CDBL_MAT.isInfinite().call(a));
		assertEquals(3, a.rows());
		assertEquals(2, a.cols());

		// nan() / isNaN()
		
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		assertFalse(G.CDBL_MAT.isNaN().call(a));
		G.CDBL_MAT.nan().call(a);
		assertTrue(G.CDBL_MAT.isNaN().call(a));
		assertEquals(3, a.rows());
		assertEquals(2, a.cols());

		// unity() / isUnity()
		
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		assertFalse(G.CDBL_MAT.isUnity().call(a));
		G.CDBL_MAT.unity().call(a);
		assertTrue(G.CDBL_MAT.isUnity().call(a));
		assertEquals(3, a.rows());
		assertEquals(2, a.cols());
		
		a = new ComplexFloat64MatrixMember(3, 3, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0, 0,0, 1,2, 4,4);
		assertFalse(G.CDBL_MAT.isUnity().call(a));
		G.CDBL_MAT.unity().call(a);
		assertTrue(G.CDBL_MAT.isUnity().call(a));
		assertEquals(3, a.rows());
		assertEquals(3, a.cols());
		
		// zero() / isZero()
		
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		assertFalse(G.CDBL_MAT.isZero().call(a));
		G.CDBL_MAT.zero().call(a);
		assertTrue(G.CDBL_MAT.isZero().call(a));
		assertEquals(3, a.rows());
		assertEquals(2, a.cols());

		// multiply()
		
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		b = new ComplexFloat64MatrixMember(2, 2, 1,7, 4,0, -1,11, -4,1);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.multiply().call(a, b, c);
		assertEquals(3, c.rows());
		assertEquals(2, c.cols());
		// TODO: test values for accuracy

		// multiplyByScalar()
		
		value.setR(2);
		value.setI(0);
		a = new ComplexFloat64MatrixMember(3, 2, 2,1, 0,9, -2,-5, 1,2, 5,4, 6,0);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.multiplyByScalar().call(value, a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.getV(row, col, value);
				c.getV(row, col, value2);
				assertEquals(value.r()*2, value2.r(), num.v());
				assertEquals(value.i()*2, value2.i(), num.v());
			}
		}
		
		// multiplyElements()
		
		num.setV(0.00000000000001);
		a = new ComplexFloat64MatrixMember(2, 2, 2,1, 0,9, -2,-5, 1,2);
		b = new ComplexFloat64MatrixMember(2, 2, 3,7, 2,1, 8,-3, 4,7);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.multiplyElements().call(a, b, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.getV(row,  col, value2);
				b.getV(row,  col, value3);
				G.CDBL.multiply().call(value2, value3, value3);
				c.getV(row,  col, value);
				assertTrue(G.CDBL.within().call(num, value3, value));
			}
		}
		
		// negate()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.negate().call(a, c);
		assertEquals(a.rows(), c.rows());
		assertEquals(a.cols(), c.cols());
		c.getV(0, 0, value);
		assertEquals(-3, value.r(), 0);
		assertEquals(-4, value.i(), 0);
		c.getV(0, 1, value);
		assertEquals(-4, value.r(), 0);
		assertEquals(-5, value.i(), 0);
		
		// norm()
		
		try {
			G.CDBL_MAT.norm().call(a, num);
			fail("must write an actual test for matrix norm() when the algorithm is written");
		} catch (Exception e) {
			assertTrue(true);
		}

		// power()
		
		a = new ComplexFloat64MatrixMember(1,1, 13,0);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.power().call(4, a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value);
		assertEquals(13*13*13*13, value.r(), 0);
		assertEquals(0, value.i(), 0);
		
		// round()
		
		num.setV(1);
		a = new ComplexFloat64MatrixMember(1,1, 13.6,10.9999);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.round().call(Round.Mode.NEGATIVE, num, a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value);
		assertEquals(13, value.r(), 0);
		assertEquals(10, value.i(), 0);
		
		// scale()
		
		value.setR(3);
		value.setI(0);
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.scale().call(value, a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value2);
		assertEquals(12, value2.r(), tol);
		assertEquals(21, value2.i(), tol);
		
		// scaleByDouble()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.scaleByDouble().call(4.0, a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value2);
		assertEquals(16, value2.r(), 0);
		assertEquals(28, value2.i(), 0);

		// scaleByHighPrec()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.scaleByHighPrec().call(new HighPrecisionMember(1.5), a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value2);
		assertEquals(6, value2.r(), 0);
		assertEquals(10.5, value2.i(), 0);

		// scaleByOneHalf()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.scaleByOneHalf().call(2, a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value2);
		assertEquals(1, value2.r(), 0);
		assertEquals(1.75, value2.i(), 0);

		// scaleByTwo()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.scaleByTwo().call(2, a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value2);
		assertEquals(16, value2.r(), 0);
		assertEquals(28, value2.i(), 0);

		// scaleByTwo()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.scaleByRational().call(new RationalMember(8, 5), a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		b.getV(0, 0, value2);
		assertEquals(4.0*8/5, value2.r(), 0);
		assertEquals(7.0*8/5, value2.i(), 0);

		// sinc()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sinc().call(a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		// TODO: test values for accuracy

		// sinch()
		
		a = new ComplexFloat64MatrixMember(1,1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sinch().call(a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		// TODO: test values for accuracy

		// sinchpi()
		
		a = new ComplexFloat64MatrixMember(1, 1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sinchpi().call(a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		// TODO: test values for accuracy

		// sincpi()
		
		a = new ComplexFloat64MatrixMember(1, 1, 4,7);
		b = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.sincpi().call(a, b);
		assertEquals(1, b.rows());
		assertEquals(1, b.cols());
		// TODO: test values for accuracy

		// transpose()
		
		a = new ComplexFloat64MatrixMember(1, 2, 3,4, 4,5);
		c = new ComplexFloat64MatrixMember();
		G.CDBL_MAT.transpose().call(a, c);
		assertEquals(a.rows(), c.cols());
		assertEquals(a.cols(), c.rows());
		c.getV(0, 0, value);
		assertEquals(3, value.r(), 0);
		assertEquals(4, value.i(), 0);
		c.getV(1, 0, value);
		assertEquals(4, value.r(), 0);
		assertEquals(5, value.i(), 0);

		// within

		num.setV(0.2499999999999999);
		a = new ComplexFloat64MatrixMember(1, 2, 3,   2,    3.7, -4.4);
		b = new ComplexFloat64MatrixMember(1, 2, 3.2, 2.25, 3.5, -4.5);
		assertFalse(G.CDBL_MAT.within().call(num, a, b));
		num.setV(0.25);
		assertTrue(G.CDBL_MAT.within().call(num, a, b));
	}

}
