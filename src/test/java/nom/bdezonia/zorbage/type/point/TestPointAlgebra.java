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
package nom.bdezonia.zorbage.type.point;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestPointAlgebra {

	@Test
	public void test() {
		Point p = G.POINT.construct();
		assertEquals(0, p.numDimensions());
		
		p = new Point(3);
		assertEquals(3, p.numDimensions());
		
		p.setComponent(0, 4);
		p.setComponent(1, 9);
		p.setComponent(2, -1);
		Point p2 = G.POINT.construct(p);
		assertEquals(3, p2.numDimensions());
		assertEquals(4, p2.component(0), 0);
		assertEquals(9, p2.component(1), 0);
		assertEquals(-1, p2.component(2), 0);

		// TODO unsupported I think
		//G.POINT.construct(str);

		G.POINT.zero().call(p2);
		assertEquals(3, p2.numDimensions());
		assertEquals(0, p2.component(0), 0);
		assertEquals(0, p2.component(1), 0);
		assertEquals(0, p2.component(2), 0);

		assertFalse(G.POINT.isZero().call(p));
		assertTrue(G.POINT.isZero().call(p2));

		assertTrue(G.POINT.isEqual().call(p, p));
		assertFalse(G.POINT.isEqual().call(p, p2));
		assertTrue(G.POINT.isEqual().call(p2, p2));

		assertFalse(G.POINT.isNotEqual().call(p, p));
		assertTrue(G.POINT.isNotEqual().call(p, p2));
		assertFalse(G.POINT.isNotEqual().call(p2, p2));

		Point p3 = new Point();
		
		G.POINT.assign().call(p, p3);
		assertTrue(G.POINT.isEqual().call(p, p3));

		G.POINT.add().call(p, p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(2*p.component(0), p3.component(0), 0);
		assertEquals(2*p.component(1), p3.component(1), 0);
		assertEquals(2*p.component(2), p3.component(2), 0);

		G.POINT.negate().call(p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(-1*p.component(0), p3.component(0), 0);
		assertEquals(-1*p.component(1), p3.component(1), 0);
		assertEquals(-1*p.component(2), p3.component(2), 0);
		
		G.POINT.subtract().call(p, p, p3);
		assertTrue(G.POINT.isZero().call(p3));
		
		G.POINT.scale().call(1.5, p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(1.5*p.component(0), p3.component(0), 0);
		assertEquals(1.5*p.component(1), p3.component(1), 0);
		assertEquals(1.5*p.component(2), p3.component(2), 0);

		G.POINT.zero().call(p3);
		G.POINT.scaleByHighPrec().call(new HighPrecisionMember(BigDecimal.valueOf(1.4)), p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(1.4*p.component(0), p3.component(0), 0);
		assertEquals(1.4*p.component(1), p3.component(1), 0);
		assertEquals(1.4*p.component(2), p3.component(2), 0);
		
		G.POINT.zero().call(p3);
		G.POINT.scaleByRational().call(new RationalMember(18,10), p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(1.8*p.component(0), p3.component(0), 0);
		assertEquals(1.8*p.component(1), p3.component(1), 0);
		assertEquals(1.8*p.component(2), p3.component(2), 0);
		
		G.POINT.zero().call(p3);
		G.POINT.scaleByDouble().call(6.7, p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(6.7*p.component(0), p3.component(0), 0);
		assertEquals(6.7*p.component(1), p3.component(1), 0);
		assertEquals(6.7*p.component(2), p3.component(2), 0);
		
		G.POINT.zero().call(p3);
		G.POINT.scaleByOneHalf().call(1, p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(0.5*p.component(0), p3.component(0), 0);
		assertEquals(0.5*p.component(1), p3.component(1), 0);
		assertEquals(0.5*p.component(2), p3.component(2), 0);
		
		G.POINT.zero().call(p3);
		G.POINT.scaleByTwo().call(1, p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(2.0*p.component(0), p3.component(0), 0);
		assertEquals(2.0*p.component(1), p3.component(1), 0);
		assertEquals(2.0*p.component(2), p3.component(2), 0);
		
		G.POINT.zero().call(p3);
		G.POINT.scaleComponents().call(14.7, p, p3);
		assertEquals(3, p3.numDimensions());
		assertEquals(14.7*p.component(0), p3.component(0), 0);
		assertEquals(14.7*p.component(1), p3.component(1), 0);
		assertEquals(14.7*p.component(2), p3.component(2), 0);

		G.POINT.random().call(p3);
		
		double tol;

		// within negative tol
		
		tol = -0.000000001;
		try {
			G.POINT.within().call(tol, p, p3);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		// within 0 tol

		tol = 0;
		assertFalse(G.POINT.within().call(tol, p, p3));
		assertTrue(G.POINT.within().call(tol, p3, p3));
		
		// within a small positive tol
		
		G.POINT.assign().call(p, p3);
		p3.setComponent(2, p.component(2)+0.0005);
		tol = 0.0005;
		assertTrue(G.POINT.within().call(tol, p, p3));
		tol = 0.000499999;
		assertFalse(G.POINT.within().call(tol, p, p3));
		
		G.POINT.assign().call(p, p3);
		p3.setComponent(0, -1);
		assertFalse(G.POINT.isInfinite().call(p3));
		G.POINT.infinite().call(p3);
		assertTrue(p3.component(0) > 0);
		assertTrue(G.POINT.isInfinite().call(p3));
		
		G.POINT.assign().call(p, p3);
		p3.setComponent(0, 1);
		assertFalse(G.POINT.isInfinite().call(p3));
		G.POINT.negInfinite().call(p3);
		assertTrue(p3.component(0) < 0);
		assertTrue(G.POINT.isInfinite().call(p3));
		
		G.POINT.assign().call(p, p3);
		assertFalse(G.POINT.isNaN().call(p3));
		G.POINT.nan().call(p3);
		assertFalse(p3.component(0) > 0);
		assertFalse(p3.component(0) == 0);
		assertFalse(p3.component(0) < 0);
		assertTrue(G.POINT.isNaN().call(p3));
	}

}
