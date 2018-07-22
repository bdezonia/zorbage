/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestQuaternionFloat64Group {

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
}
