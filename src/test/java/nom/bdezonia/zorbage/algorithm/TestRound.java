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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

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

		Round.Mode mode = Mode.NEGATIVE;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(5.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-3.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-6.0, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}

	@Test
	public void testPositiveFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.POSITIVE;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(6.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(3.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-5.0, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}

	@Test
	public void testAwayFromOriginFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.AWAY_FROM_ORIGIN;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(6.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(3.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-3.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-6.0, b.v(), 0);
		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}

	@Test
	public void testTowardsOriginFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.TOWARDS_ORIGIN;
		
		delta.setV(1);
		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(5.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-5.0, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}

	@Test
	public void testHalfUpFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.HALF_UP;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(6.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(3.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-3.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-6.0, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		// halfways
		a.setV(1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		// halfways
		a.setV(1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		// halfways
		a.setV(0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		// halfways
		a.setV(0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		// halfways
		a.setV(-0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		// halfways
		a.setV(-0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		// halfways
		a.setV(-1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		// halfways
		a.setV(-1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}
	
	@Test
	public void testHalfDownFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.HALF_DOWN;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(5.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-5.0, b.v(), 0);
	
		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		// halfways
		a.setV(1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		// halfways
		a.setV(1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		// halfways
		a.setV(0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		// halfways
		a.setV(0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		// halfways
		a.setV(-0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		// halfways
		a.setV(-0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		// halfways
		a.setV(-1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		// halfways
		a.setV(-1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}
	
	@Test
	public void testHalfEvenFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.HALF_EVEN;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(6.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-6.0, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		// halfways
		a.setV(1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		// halfways
		a.setV(1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		// halfways
		a.setV(0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		// halfways
		a.setV(0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		// halfways
		a.setV(-0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		// halfways
		a.setV(-0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		// halfways
		a.setV(-1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		// halfways
		a.setV(-1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}
	
	@Test
	public void testHalfOddFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.HALF_ODD;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(5.0, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(3.0, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-3.0, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-5.0, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(2.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		a.setV(1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.0, b.v(), 0);
		// halfways
		a.setV(1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		// halfways
		a.setV(1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.5, b.v(), 0);
		a.setV(1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		// halfways
		a.setV(0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		// halfways
		a.setV(0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0.5, b.v(), 0);
		a.setV(0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		a.setV(-0.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(0, b.v(), 0);
		// halfways
		a.setV(-0.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		// halfways
		a.setV(-0.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-0.5, b.v(), 0);
		a.setV(-0.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-0.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		// halfways
		a.setV(-1.25);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.3);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.4);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.7);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		// halfways
		a.setV(-1.75);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.5, b.v(), 0);
		a.setV(-1.8);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-1.9);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
		a.setV(-2);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.0, b.v(), 0);
	}
	
	@Test
	public void testExactFloat() {
		Float64Member delta = new Float64Member();
		Float64Member a = new Float64Member();
		Float64Member b = new Float64Member();

		Round.Mode mode = Mode.EXACT;
		
		delta.setV(1);

		a.setV(5.5);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(2.5);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(1.6);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(1.1);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(-1.6);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(-2.5);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
			fail();
		} catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(-5.5);
		try {
			Round.compute(G.DBL, mode, delta, a, b);
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

		Round.Mode mode = Mode.NONE;
		
		delta.setV(1);

		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(5.5, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.6, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.1, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.6, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.5, b.v(), 0);

		delta.setV(0.5);
		
		a.setV(5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(5.5, b.v(), 0);
		a.setV(2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(2.5, b.v(), 0);
		a.setV(1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.6, b.v(), 0);
		a.setV(1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.1, b.v(), 0);
		a.setV(1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(1.0, b.v(), 0);
		a.setV(-1.0);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.0, b.v(), 0);
		a.setV(-1.1);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.1, b.v(), 0);
		a.setV(-1.6);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-1.6, b.v(), 0);
		a.setV(-2.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-2.5, b.v(), 0);
		a.setV(-5.5);
		Round.compute(G.DBL, mode, delta, a, b);
		assertEquals(-5.5, b.v(), 0);
	}
	
	@Test
	public void testNegativeInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.NEGATIVE;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(3);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-6, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-6, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
	}
	
	@Test
	public void testPostiveInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.POSITIVE;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(3);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(6, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(6, b.v());
	}
	
	@Test
	public void testAwayFromOriginInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.AWAY_FROM_ORIGIN;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(3);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-6, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-6, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(6, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(6, b.v());
	}
	
	@Test
	public void testTowardsOriginInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.TOWARDS_ORIGIN;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(3);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
	}
	
	@Test
	public void testHalfUpInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.HALF_UP;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(4);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
	}
	
	@Test
	public void testHalfDownInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.HALF_DOWN;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(4);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
	}
	
	@Test
	public void testHalfEvenInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.HALF_EVEN;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(4);

		a.setV(-8);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-8, b.v());
		a.setV(-7);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-8, b.v());
		a.setV(-6);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-8, b.v());
		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(6);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(8, b.v());
		a.setV(7);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(8, b.v());
		a.setV(8);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(8, b.v());
	}
	
	@Test
	public void testHalfOddInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.HALF_ODD;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(4);

		a.setV(-8);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-8, b.v());
		a.setV(-7);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-8, b.v());
		a.setV(-6);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(6);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(7);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(8, b.v());
		a.setV(8);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(8, b.v());
	}
	
	@Test
	public void testExactInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.EXACT;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(4);

		try {
			a.setV(-5);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		try {
			a.setV(-3);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		try {
			a.setV(-2);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		try {
			a.setV(-1);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		try {
			a.setV(1);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		try {
			a.setV(2);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		try {
			a.setV(3);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		try {
			a.setV(5);
			Round.compute(G.INT32, mode, delta, a, b);
			fail();
		}
		catch (ArithmeticException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testNoneInts() {
		SignedInt32Member delta = new SignedInt32Member();
		SignedInt32Member a = new SignedInt32Member();
		SignedInt32Member b = new SignedInt32Member();

		Round.Mode mode = Mode.NONE;
		
		delta.setV(1);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());

		delta.setV(4);

		a.setV(-5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-5, b.v());
		a.setV(-4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-4, b.v());
		a.setV(-3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-3, b.v());
		a.setV(-2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-2, b.v());
		a.setV(-1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(-1, b.v());
		a.setV(0);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(0, b.v());
		a.setV(1);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(1, b.v());
		a.setV(2);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(2, b.v());
		a.setV(3);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(3, b.v());
		a.setV(4);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(4, b.v());
		a.setV(5);
		Round.compute(G.INT32, mode, delta, a, b);
		assertEquals(5, b.v());
	}
}
