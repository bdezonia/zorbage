/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRound {

	@Test
	public void testNegativeFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(5.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(-2, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(-3.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.NEGATIVE, delta, a, b);
		assertEquals(-6.0, b.v(), 0);
	}

	@Test
	public void testPositiveFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(6.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(3.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(-1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.POSITIVE, delta, a, b);
		assertEquals(-5.0, b.v(), 0);
	}

	@Test
	public void testAwayFromOriginFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(6.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(3.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(-2, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(-3.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.AWAY_FROM_ORIGIN, delta, a, b);
		assertEquals(-6.0, b.v(), 0);
	}

	@Test
	public void testTowardsOriginFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(5.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(-1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.TOWARDS_ORIGIN, delta, a, b);
		assertEquals(-5.0, b.v(), 0);
	}

	@Test
	public void testHalfUpFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(6.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(3.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(-1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(-3.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.HALF_UP, delta, a, b);
		assertEquals(-6.0, b.v(), 0);
	}
	
	@Test
	public void testHalfDownFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(5.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(-1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.HALF_DOWN, delta, a, b);
		assertEquals(-5.0, b.v(), 0);
	}
	
	@Test
	public void testHalfEvenFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(6.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(-1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.HALF_EVEN, delta, a, b);
		assertEquals(-6.0, b.v(), 0);
	}
	
	@Test
	public void testHalfOddFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(5.0, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(3.0, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(2.0, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(-1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(-2.0, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(-3.0, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.HALF_ODD, delta, a, b);
		assertEquals(-5.0, b.v(), 0);
	}
	
	@Test
	public void testExactFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		try {
			Round.compute(G.DBL, Round.Mode.EXACT, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testNoneFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(5.5, b.v(), 0);

		a.setV(2.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(2.5, b.v(), 0);

		a.setV(1.6);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(1.6, b.v(), 0);

		a.setV(1.1);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(1.1, b.v(), 0);

		a.setV(1.0);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(1.0, b.v(), 0);

		a.setV(-1.0);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(-1.0, b.v(), 0);

		a.setV(-1.1);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(-1.1, b.v(), 0);

		a.setV(-1.6);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(-1.6, b.v(), 0);

		a.setV(-2.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(-2.5, b.v(), 0);

		a.setV(-5.5);
		Round.compute(G.DBL, Round.Mode.NONE, delta, a, b);
		assertEquals(-5.5, b.v(), 0);
	}
}
