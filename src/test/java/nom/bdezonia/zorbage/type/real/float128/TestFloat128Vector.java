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
package nom.bdezonia.zorbage.type.real.float128;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat128Vector {

	private final BigDecimal TOL = BigDecimal.valueOf(0.00000000000001);

	@Test
	public void test1() {
		
		Float128Member value = G.QUAD.construct();

		// test default ctor
		
		Float128VectorMember a = G.QUAD_VEC.construct();
		Float128VectorMember b = G.QUAD_VEC.construct();
		Float128VectorMember c = G.QUAD_VEC.construct();
		
		// bigd ctor
		
		c = G.QUAD_VEC.construct(BigDecimal.ONE, BigDecimal.TEN);
		assertEquals(2, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(10, value.v(), BigDecimal.ZERO));
		
		// bigi ctor
		
		c = G.QUAD_VEC.construct(BigInteger.TWO, BigInteger.TEN, BigInteger.ZERO, BigInteger.ONE);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(10, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), BigDecimal.ZERO));

		// double ctor
		
		c = G.QUAD_VEC.construct(14.3, 18.0, -7);
		assertEquals(3, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(14.3, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(18, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(-7, value.v(), BigDecimal.ZERO));
		
		// long ctor
		
		c = G.QUAD_VEC.construct(-33L, -14L, 20, 19, 18);
		assertEquals(5, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(-33, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(-14, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(20, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(19, value.v(), BigDecimal.ZERO));
		c.getV(4, value);
		assertTrue(BigDecimalUtils.isNear(18, value.v(), BigDecimal.ZERO));

		// construct from other
		
		c = G.QUAD_VEC.construct(a);
		assertTrue(G.QUAD_VEC.isEqual().call(a, c));
		
		// construct from string
		
		c = G.QUAD_VEC.construct("[1,2,3,4,5]");
		assertEquals(5, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));
		c.getV(4, value);
		assertTrue(BigDecimalUtils.isNear(5, value.v(), BigDecimal.ZERO));
		
		// construct using specified storage type

		c = G.QUAD_VEC.construct(StorageConstruction.MEM_SPARSE, 13);
		assertEquals(13, c.length());
		for (int i = 0; i < c.length(); i++) { 
			c.getV(i, value);
			assertTrue(BigDecimalUtils.isNear(0, value.v(), BigDecimal.ZERO));
		}
		
		// add()
		
		a = G.QUAD_VEC.construct(3,5,7,9);
		b = G.QUAD_VEC.construct(1,2);
		G.QUAD_VEC.add().call(a, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(7, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(7, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(9, value.v(), BigDecimal.ZERO));
		
		b = G.QUAD_VEC.construct(1,2,3,4);
		G.QUAD_VEC.add().call(a, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(7, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(10, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(13, value.v(), BigDecimal.ZERO));

		// addScalar()
		
		value.setFromDouble(44);
		G.QUAD_VEC.addScalar().call(value, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(45, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(46, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(47, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(48, value.v(), BigDecimal.ZERO));
		
		// assign()

		G.QUAD_VEC.assign().call(b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));
		
		// conjugate() : for reals this does nothing much
		
		c = G.QUAD_VEC.construct();
		G.QUAD_VEC.conjugate().call(b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));

		// crossProduct()   TODO
		
		G.QUAD_VEC.crossProduct();
		
		// directProduct()
		
		Float128MatrixMember mat = G.QUAD_MAT.construct();
		a = G.QUAD_VEC.construct(1, 5);
		b = G.QUAD_VEC.construct(2, 3);
		G.QUAD_VEC.directProduct().call(a, b, mat);
		assertEquals(2, mat.rows());
		assertEquals(2, mat.cols());
		mat.getV(0, 0, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		mat.getV(0, 1, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		mat.getV(1, 0, value);
		assertTrue(BigDecimalUtils.isNear(10, value.v(), BigDecimal.ZERO));
		mat.getV(1, 1, value);
		assertTrue(BigDecimalUtils.isNear(15, value.v(), BigDecimal.ZERO));
		
		// divideByScalar()

		value.setFromDouble(1.5);
		a = G.QUAD_VEC.construct(1,2,3,4);
		G.QUAD_VEC.divideByScalar().call(value, a, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(1.0/1.5, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(2.0/1.5, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(3.0/1.5, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4.0/1.5, value.v(), TOL));
	
		// divideElements()
		
		a = G.QUAD_VEC.construct(1,2,3,4);
		b = G.QUAD_VEC.construct(2,-1,5,0);
		G.QUAD_VEC.divideElements().call(a, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(0.5, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(-2, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(0.6, value.v(), TOL));
		c.getV(3, value);
		assertTrue(G.QUAD.isInfinite().call(value));
		assertTrue(G.QUAD.signum().call(value) > 0);

		// dotProduct()
		
		a = G.QUAD_VEC.construct(3,5,7,9);
		G.QUAD_VEC.dotProduct().call(a, a, value);
		assertTrue(BigDecimalUtils.isNear(3*3+5*5+7*7+9*9, value.v(), TOL));

		// infinite() and isInfinite()
		
		c = G.QUAD_VEC.construct(1,2);
		assertEquals(2, c.length());
		assertFalse(G.QUAD_VEC.isInfinite().call(c));
		G.QUAD_VEC.infinite().call(c);
		assertTrue(G.QUAD_VEC.isInfinite().call(c));
		assertEquals(2, c.length());

		// isEqual()
		
		c = G.QUAD_VEC.construct(-1, 21);
		assertFalse(G.QUAD_VEC.isEqual().call(a, c));
		c = G.QUAD_VEC.construct(-1, 21, 5, 3);
		assertFalse(G.QUAD_VEC.isEqual().call(a, c));
		G.QUAD_VEC.assign().call(a, c);
		assertTrue(G.QUAD_VEC.isEqual().call(a, c));

		// isNotEqual()
		
		c = G.QUAD_VEC.construct(-1, 21);
		assertTrue(G.QUAD_VEC.isNotEqual().call(a, c));
		c = G.QUAD_VEC.construct(-1, 21, 5, 3);
		assertTrue(G.QUAD_VEC.isNotEqual().call(a, c));
		G.QUAD_VEC.assign().call(a, c);
		assertFalse(G.QUAD_VEC.isNotEqual().call(a, c));

		// nan() and isNaN()
		
		c = G.QUAD_VEC.construct(1,2);
		assertEquals(2, c.length());
		assertFalse(G.QUAD_VEC.isNaN().call(c));
		G.QUAD_VEC.nan().call(c);
		assertTrue(G.QUAD_VEC.isNaN().call(c));
		assertEquals(2, c.length());
		
		// multiplyByScalar()

		a = G.QUAD_VEC.construct(1,2,3,4);
		c = new Float128VectorMember(a.length());
		value.setFromDouble(-2);
		G.QUAD_VEC.multiplyByScalar().call(value, a, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(-2, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(-4, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(-6, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(-8, value.v(), BigDecimal.ZERO));
		

		a = G.QUAD_VEC.construct(1,2,3,4);
		b = G.QUAD_VEC.construct(-3,14,2,-100);
		G.QUAD_VEC.multiplyElements().call(a, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(-3, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(28, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(6, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(-400, value.v(), BigDecimal.ZERO));
		
		// negate()
		
		G.QUAD_VEC.negate().call(a, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(-1, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(-2, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(-3, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(-4, value.v(), BigDecimal.ZERO));
		
		// norm()
		
		G.QUAD_VEC.norm().call(a, value);
		assertTrue(BigDecimalUtils.isNear(Math.sqrt(1+4+9+16), value.v(), TOL));
		
		// perpDotPoduct()   TODO
		
		G.QUAD_VEC.perpDotProduct();
		
		// round()
		
		b = G.QUAD_VEC.construct(1.00001, 2, 2.4, -3.6);
		value.setFromDouble(1);
		G.QUAD_VEC.round().call(Round.Mode.AWAY_FROM_ORIGIN, value, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(-4, value.v(), BigDecimal.ZERO));
		
		G.QUAD_VEC.round();
		
		// scalarTripleProduct()   TODO
		
		G.QUAD_VEC.scalarTripleProduct();
		
		// scale()
		
		value.setFromLong(14);
		b = G.QUAD_VEC.construct(1,2,3,4);
		G.QUAD_VEC.scale().call(value, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(14, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(28, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(42, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(56, value.v(), TOL));

		// scaleByDouble()

		b = G.QUAD_VEC.construct(3,1,7,4);
		G.QUAD_VEC.scaleByDouble().call(14.0, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(3*14, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(1*14, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(7*14, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4*14, value.v(), TOL));

		// scaleByHighPrec()

		b = G.QUAD_VEC.construct(3,1,7,4);
		G.QUAD_VEC.scaleByHighPrec().call(new HighPrecisionMember(11), b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(3*11, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(1*11, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(7*11, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4*11, value.v(), TOL));
		
		// scaleByOneHalf()
		
		b = G.QUAD_VEC.construct(1,2,3,4);
		G.QUAD_VEC.scaleByOneHalf().call(1, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(0.5, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(1.5, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), TOL));
		
		G.QUAD_VEC.scaleByOneHalf().call(3, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(1/8.0, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(2/8.0, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(3/8.0, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4/8.0, value.v(), TOL));

		// scaleByRational()

		b = G.QUAD_VEC.construct(3,1,7,4);
		G.QUAD_VEC.scaleByRational().call(G.RAT.construct(7,5), b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(3.0*7/5, value.v(), TOL));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(1.0*7/5, value.v(), TOL));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(7.0*7/5, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(4.0*7/5, value.v(), TOL));

		// scaleByTwo()
		
		b = G.QUAD_VEC.construct(1,2,3,4);
		G.QUAD_VEC.scaleByTwo().call(1, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(6, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(8, value.v(), BigDecimal.ZERO));
		
		G.QUAD_VEC.scaleByTwo().call(3, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(8, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(16, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(24, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(32, value.v(), BigDecimal.ZERO));

		// subtract()
		
		a = G.QUAD_VEC.construct(3,5,7,9);
		b = G.QUAD_VEC.construct(1,2);
		G.QUAD_VEC.subtract().call(a, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(7, value.v(), TOL));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(9, value.v(), TOL));
		
		G.QUAD_VEC.subtract().call(b, a, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(-2, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(-3, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(-7, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(-9, value.v(), BigDecimal.ZERO));
		
		b = G.QUAD_VEC.construct(1,2,3,4);
		G.QUAD_VEC.add().call(a, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(4, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(7, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(10, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(13, value.v(), BigDecimal.ZERO));

		// subtractScalar()

		value.setFromDouble(44);
		G.QUAD_VEC.subtractScalar().call(value, b, c);
		assertEquals(4, c.length());
		c.getV(0, value);
		assertTrue(BigDecimalUtils.isNear(-43, value.v(), BigDecimal.ZERO));
		c.getV(1, value);
		assertTrue(BigDecimalUtils.isNear(-42, value.v(), BigDecimal.ZERO));
		c.getV(2, value);
		assertTrue(BigDecimalUtils.isNear(-41, value.v(), BigDecimal.ZERO));
		c.getV(3, value);
		assertTrue(BigDecimalUtils.isNear(-40, value.v(), BigDecimal.ZERO));
		
		// vectorDirectProduct()
		
		a = G.QUAD_VEC.construct(1, 5);
		b = G.QUAD_VEC.construct(2, 3);
		G.QUAD_VEC.vectorDirectProduct().call(a, b, mat);
		assertEquals(2, mat.rows());
		assertEquals(2, mat.cols());
		mat.getV(0, 0, value);
		assertTrue(BigDecimalUtils.isNear(2, value.v(), BigDecimal.ZERO));
		mat.getV(0, 1, value);
		assertTrue(BigDecimalUtils.isNear(3, value.v(), BigDecimal.ZERO));
		mat.getV(1, 0, value);
		assertTrue(BigDecimalUtils.isNear(10, value.v(), BigDecimal.ZERO));
		mat.getV(1, 1, value);
		assertTrue(BigDecimalUtils.isNear(15, value.v(), BigDecimal.ZERO));
		
		// vectorTripleProduct()   TODO
		
		G.QUAD_VEC.vectorTripleProduct();
		
		// within()
		
		value.setFromDouble(0);
		a = G.QUAD_VEC.construct(1,2,3,4);
		b = G.QUAD_VEC.construct(1,2,3,4);
		assertTrue(G.QUAD_VEC.within().call(value, a, b));
		b = G.QUAD_VEC.construct(1.1, 2.2, 3.0, 4.1);
		value.setFromDouble(0.1);
		assertFalse(G.QUAD_VEC.within().call(value, a, b));
		value.setFromDouble(0.1999999999999999999999);
		assertFalse(G.QUAD_VEC.within().call(value, a, b));
		value.setFromDouble(0.2000000000000001);  // TODO: 0.2 doesn'tt work. This code might point out a bug.
		assertTrue(G.QUAD_VEC.within().call(value, a, b));

		// zero(), isZero()
		
		assertEquals(4, b.length());
		assertFalse(G.QUAD_VEC.isZero().call(b));
		G.QUAD_VEC.zero().call(b);
		assertTrue(G.QUAD_VEC.isZero().call(b));
		assertEquals(4, b.length());
	}
	
	// https://en.wikipedia.org/?title=3300&redirect=no
}
