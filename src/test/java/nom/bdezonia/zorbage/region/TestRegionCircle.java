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
package nom.bdezonia.zorbage.region;

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.sampling.RealIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRegionCircle {

	@Test
	public void test() {
		RegionCircle rc = new RegionCircle(1, 3, 2);
		assertEquals(2, rc.numDimensions());
		RealIndex idx = new RealIndex(2);
		rc.maxBound().call(idx);
		assertEquals(1+2, idx.get(0), 0);
		assertEquals(3+2, idx.get(1), 0);
		rc.minBound().call(idx);
		assertEquals(1-2, idx.get(0), 0);
		assertEquals(3-2, idx.get(1), 0);
		idx.set(0, 1);
		idx.set(1, 3);
		assertTrue(rc.contains(idx));
		idx.set(0, 1+2);
		idx.set(1, 3);
		assertTrue(rc.contains(idx));
		idx.set(0, 1);
		idx.set(1, 3+2);
		assertTrue(rc.contains(idx));
		idx.set(0, 1-2);
		idx.set(1, 3);
		assertTrue(rc.contains(idx));
		idx.set(0, 1);
		idx.set(1, 3-2);
		assertTrue(rc.contains(idx));
		idx.set(0, 1+2+0.00000001);
		idx.set(1, 3);
		assertFalse(rc.contains(idx));
		idx.set(0, 1);
		idx.set(1, 3+2+0.00000001);
		assertFalse(rc.contains(idx));
		idx.set(0, 1-2-0.00000001);
		idx.set(1, 3);
		assertFalse(rc.contains(idx));
		idx.set(0, 1);
		idx.set(1, 3-2-0.00000001);
		assertFalse(rc.contains(idx));
	}

}
