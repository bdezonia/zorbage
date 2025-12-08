/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.misc;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRealUtils {

	@Test
	public void test1() {
		assertEquals(4, RealUtils.distance1d(3.0, 7.0), 0);
	}

	@Test
	public void test2() {
		assertEquals(5, RealUtils.distance2d(3.0, 7.0, 6.0, 11.0), 0);
	}

	@Test
	public void test3() {
		double x = Math.sqrt((6.0-3)*(6-3) + (11.0-7)*(11-7) + (5.0-1)*(5-1));
		assertEquals(x, RealUtils.distance3d(3.0, 7.0, 1.0, 6.0, 11.0, 5.0), 0.000000000000001);
	}
	
	@Test
	public void test4() {
		double[] p1 = new double[] {3,1,11};
		double[] p2 = new double[] {7,6,5};
		double[] scratch = new double[p1.length];
		double x = Math.sqrt((7.0-3)*(7-3) + (6.0-1)*(6-1) + (11.0-5)*(11-5));
		assertEquals(x, RealUtils.distanceNd(p1, p2, scratch), 0.000000000000001);
		try {
			RealUtils.distanceNd(p1, p2, new double[] {1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			RealUtils.distanceNd(p1, new double[] {1}, scratch);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test5() {
		assertFalse(RealUtils.near(1.0, 1.1, 0));
		assertFalse(RealUtils.near(1.0, 1.1, 0.0125));
		assertFalse(RealUtils.near(1.0, 1.1, 0.025));
		assertFalse(RealUtils.near(1.0, 1.1, 0.05));
		assertFalse(RealUtils.near(1.0, 1.1, 0.1)); // rounding issues with this number rep
		assertTrue(RealUtils.near(1.0, 1.1, 0.1000000000000001));
	}
	
	@Test
	public void test6() {
		assertFalse(RealUtils.near(1.0f, 1.1f, 0f));
		assertFalse(RealUtils.near(1.0f, 1.1f, 0.0125f));
		assertFalse(RealUtils.near(1.0f, 1.1f, 0.025f));
		assertFalse(RealUtils.near(1.0f, 1.1f, 0.05f));
		assertFalse(RealUtils.near(1.0f, 1.1f, 0.1f)); // rounding issues with this number rep
		assertTrue(RealUtils.near(1.0f, 1.1f, 0.1000001f));
	}
}
