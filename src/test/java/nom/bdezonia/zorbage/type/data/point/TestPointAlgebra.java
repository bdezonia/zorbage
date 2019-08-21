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
package nom.bdezonia.zorbage.type.data.point;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;
import nom.bdezonia.zorbage.type.data.unbounded.UnboundedIntMember;

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
		
		G.POINT.scale().call(new Float64Member(1.5), p, p3);
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
		G.POINT.scaleByRational().call(new RationalMember(new UnboundedIntMember(14),new UnboundedIntMember(10)), p, p3);;
		assertEquals(3, p3.numDimensions());
		assertEquals(1.4*p.component(0), p3.component(0), 0);
		assertEquals(1.4*p.component(1), p3.component(1), 0);
		assertEquals(1.4*p.component(2), p3.component(2), 0);
	
		G.POINT.random().call(p3);
	}

}
