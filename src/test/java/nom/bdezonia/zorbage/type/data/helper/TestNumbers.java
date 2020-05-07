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
package nom.bdezonia.zorbage.type.data.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float32.complex.ComplexFloat32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNumbers {

	@Test
	public void testNumberToRmodule() {

		ComplexFloat32Member c = new ComplexFloat32Member(1, 2);

		ComplexFloat32Member tmp = new ComplexFloat32Member();
		
		NumberRModuleBridge<ComplexFloat32Member> bridge = new NumberRModuleBridge<ComplexFloat32Member>(G.CFLT, c);
		
		assertEquals(1, bridge.length());
		
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		for (int i = 0; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
		bridge.v(0, tmp);
		assertTrue(G.CFLT.isEqual().call(c, tmp));
		
		try {
			bridge.alloc(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(1));
		try {
			bridge.alloc(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.init(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(1);
		assertTrue(G.CFLT.isZero().call(c));
		try {
			bridge.init(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.reshape(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(1);
		try {
			bridge.reshape(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		assertEquals(1, bridge.numDimensions());

		tmp.setR(3);
		tmp.setI(7);

		bridge.setV(0, tmp);
		bridge.v(0, c);
		
		assertEquals(3, c.r(), 0);
		assertEquals(7, c.i(), 0);
		
		assertEquals(StorageConstruction.MEM_ARRAY, bridge.storageType());
	}

	@Test
	public void testNumberToMatrix() {
		
		ComplexFloat32Member c = new ComplexFloat32Member(1, 2);

		ComplexFloat32Member tmp = new ComplexFloat32Member();
		
		NumberMatrixBridge<ComplexFloat32Member> bridge = new NumberMatrixBridge<ComplexFloat32Member>(G.CFLT, c);

		assertEquals(1, bridge.rows());
		assertEquals(1, bridge.cols());
		
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		for (int i = 0; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
		bridge.v(0, 0, tmp);
		assertTrue(G.CFLT.isEqual().call(c, tmp));
		
		try {
			bridge.alloc(-1,-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(0,0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(1,1));
		try {
			bridge.alloc(1,2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(2,1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.init(-1,-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(0,0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(1,1);
		assertTrue(G.CFLT.isZero().call(c));
		try {
			bridge.init(1,2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(2,1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.reshape(-1,-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(0,0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(1,1);
		try {
			bridge.reshape(1,2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(2,1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		assertEquals(2, bridge.numDimensions());
		
		tmp.setR(3);
		tmp.setI(7);

		bridge.setV(0, 0, tmp);
		
		bridge.v(0, 0, c);
		
		assertEquals(3, c.r(), 0);
		assertEquals(7, c.i(), 0);

		assertEquals(StorageConstruction.MEM_ARRAY, bridge.storageType());
	}
	
	@Test
	public void testNumberToTensor() {
		
		ComplexFloat32Member c = new ComplexFloat32Member(1, 2);

		ComplexFloat32Member tmp = new ComplexFloat32Member();
		
		NumberTensorBridge<ComplexFloat32Member> bridge = new NumberTensorBridge<ComplexFloat32Member>(G.CFLT, c, 3);
		
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		for (int i = 0; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
		IntegerIndex idx = new IntegerIndex(2);
		idx.set(0, 0);
		idx.set(1, 0);

		bridge.v(idx, tmp);
		assertTrue(G.CFLT.isEqual().call(c, tmp));
		
		try {
			bridge.alloc(new long[] {-1,-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {0,0});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(new long[] {1,1}));
		try {
			bridge.alloc(new long[] {1,2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {2,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.init(new long[] {-1,-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(new long[] {0,0});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(new long[] {1,1});
		assertTrue(G.CFLT.isZero().call(c));
		try {
			bridge.init(new long[] {1,2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(new long[] {2,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.reshape(new long[] {-1,-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(new long[] {0,0});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(new long[] {1,1});
		try {
			bridge.reshape(new long[] {1,2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(new long[] {2,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		assertEquals(0, bridge.numDimensions());
		
		tmp.setR(3);
		tmp.setI(7);

		bridge.setV(idx, tmp);
		
		bridge.v(idx, c);
		
		assertEquals(3, c.r(), 0);
		assertEquals(7, c.i(), 0);

		assertEquals(StorageConstruction.MEM_ARRAY, bridge.storageType());

		try {
			bridge.indexIsLower(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.indexIsUpper(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertEquals(0, bridge.lowerRank());
		assertEquals(0, bridge.upperRank());
		assertEquals(0, bridge.rank());
		
		assertEquals(3, bridge.dimension());
		bridge.setDimension(99);
		assertEquals(99, bridge.dimension());
	}
}
