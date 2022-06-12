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
package nom.bdezonia.zorbage.type.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */

public class TestTensors {

	@Test
	public void testTensorToNumber() {
		
		ComplexFloat64CartesianTensorProductMember t = new ComplexFloat64CartesianTensorProductMember(2, 2, 1,2, 3,4, 5,6, 7,8);
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		TensorElementNumberBridge<ComplexFloat64Member> bridge = new TensorElementNumberBridge<ComplexFloat64Member>(t);
		
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
		
		assertEquals(0, bridge.numDimensions());
		
		IntegerIndex idx = new IntegerIndex(2);
		
		idx.set(0,0);
		idx.set(1,0);
		bridge.setIndex(idx);
		bridge.getV(tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);

		idx.set(0,1);
		idx.set(1,0);
		bridge.setIndex(idx);
		bridge.getV(tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);

		idx.set(0,0);
		idx.set(1,1);
		bridge.setIndex(idx);
		bridge.getV(tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);

		idx.set(0,1);
		idx.set(1,1);
		bridge.setIndex(idx);
		bridge.getV(tmp);
		assertEquals(7, tmp.r(), 0);
		assertEquals(8, tmp.i(), 0);

		tmp.setR(1000);
		tmp.setI(2000);
		bridge.setV(tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.getV(tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);
	}

	@Test
	public void testTensorToRModule() {
		
		ComplexFloat64CartesianTensorProductMember t = new ComplexFloat64CartesianTensorProductMember(2, 2, 1,2, 3,4, 5,6, 7,8);
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		TensorRModuleBridge<ComplexFloat64Member> bridge = new TensorRModuleBridge<ComplexFloat64Member>(G.CDBL, t);

		// test a 1xc matrix
		
		IntegerIndex idx = new IntegerIndex(1);
		
		idx.set(0, 0);
		bridge.setRmodule(0, idx);

		bridge.getV(0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.getV(1, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);

		idx.set(0, 1);
		bridge.setRmodule(0, idx);

		bridge.getV(0, tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);
		
		bridge.getV(1, tmp);
		assertEquals(7, tmp.r(), 0);
		assertEquals(8, tmp.i(), 0);

		idx.set(0, 0);
		bridge.setRmodule(1, idx);
		
		bridge.getV(0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.getV(1, tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);

		idx.set(0, 1);
		bridge.setRmodule(1, idx);

		bridge.getV(0, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);
		
		bridge.getV(1, tmp);
		assertEquals(7, tmp.r(), 0);
		assertEquals(8, tmp.i(), 0);

		assertEquals(2, bridge.length());
		
		assertEquals(1, bridge.numDimensions());

		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(2, bridge.dimension(0));
		for (int i = 1; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
		}
		
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
		assertFalse(bridge.alloc(2));
		try {
			bridge.alloc(10);
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
		try {
			bridge.init(1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(2);
		for (int i = 0; i < 2; i++) {
			tmp.setR(102);
			tmp.setI(105);
			bridge.getV(i, tmp);
			assertTrue(G.CDBL.isZero().call(tmp));
		}
		try {
			bridge.init(10);
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
		try {
			bridge.reshape(1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(2);
		try {
			bridge.reshape(10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		tmp.setR(1000);
		tmp.setI(2000);
		bridge.setV(0, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.getV(0, tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);

		tmp.setR(100);
		tmp.setI(200);
		bridge.setV(1, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.getV(1, tmp);
		assertEquals(100, tmp.r(), 0);
		assertEquals(200, tmp.i(), 0);

		assertEquals(t.storageType(), bridge.storageType());
	}

	@Test
	public void testTensorToMatrix() {

		ComplexFloat64CartesianTensorProductMember t = new ComplexFloat64CartesianTensorProductMember(2, 2, 1,2, 3,4, 5,6, 7,8);
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		TensorMatrixBridge<ComplexFloat64Member> bridge = new TensorMatrixBridge<ComplexFloat64Member>(G.CDBL, t);

		assertEquals(2, bridge.numDimensions());
		
		assertEquals(2, bridge.cols());
		assertEquals(2, bridge.rows());

		bridge.setMatrix(1, 0, new IntegerIndex(0));

		bridge.getV(0, 0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.getV(0, 1, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);
		
		bridge.getV(1, 0, tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);
		
		bridge.getV(1, 1, tmp);
		assertEquals(7, tmp.r(), 0);
		assertEquals(8, tmp.i(), 0);
		
		bridge.setMatrix(0, 1, new IntegerIndex(0));

		bridge.getV(0, 0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.getV(0, 1, tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);
		
		bridge.getV(1, 0, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);
		
		bridge.getV(1, 1, tmp);
		assertEquals(7, tmp.r(), 0);
		assertEquals(8, tmp.i(), 0);
		
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(2, bridge.dimension(0));
		assertEquals(2, bridge.dimension(1));
		for (int i = 2; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
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
		try {
			bridge.alloc(1,1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(2,2));
		try {
			bridge.alloc(5,5);
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
		try {
			bridge.init(1,1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(2,2);
		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 2; c++) {
				tmp.setR(123);
				tmp.setI(456);
				bridge.getV(r, c, tmp);
				assertTrue(G.CDBL.isZero().call(tmp));
			}
		}
		try {
			bridge.init(5,5);
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
		try {
			bridge.reshape(1,1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(2,2);
		try {
			bridge.reshape(3,3);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		assertEquals(t.storageType(), bridge.storageType());
		
		tmp.setR(103);
		tmp.setI(109);
		bridge.setV(0, 0, tmp);
		tmp.setR(0);
		tmp.setI(0);
		bridge.getV(0, 0, tmp);
		assertEquals(103,  tmp.r(), 0);
		assertEquals(109,  tmp.i(), 0);
	}

	@Test
	public void testTensorToSubTensor() {

		ComplexFloat64CartesianTensorProductMember t = new ComplexFloat64CartesianTensorProductMember(2, 2, 1,2, 3,4, 5,6, 7,8);
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		ComplexFloat64Member tmp2 = new ComplexFloat64Member();
		
		SubTensorBridge<ComplexFloat64Member> bridge = new SubTensorBridge<ComplexFloat64Member>(G.CDBL, t, new int[] {0}, new long[] {1});

		IntegerIndex idx = new IntegerIndex(1);
		
		IntegerIndex idx2 = new IntegerIndex(2);

		assertEquals(2, bridge.dimension());
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		// this seems nonintuitive
		assertEquals(2, bridge.dimension(0));
		// this exceptions out which also seems nonintuitive
		for (int i = 1; i < 10; i++) {
			try {
				bridge.dimension(1);
				fail();
			} catch (IllegalArgumentException e) {
				assertTrue(true);
			}
		}
		
		idx2.set(0, 0);
		idx2.set(1, 0);
		bridge.setDims(new int[] {0}, new long[] {0}, new long[] {0});
		idx.set(0,0);

		bridge.getV(idx, tmp);
		t.getV(idx2, tmp2);
		
		assertTrue(G.CDBL.isEqual().call(tmp, tmp2));
		
		idx2.set(0, 1);
		idx2.set(1, 0);
		bridge.setDims(new int[] {0}, new long[] {1}, new long[] {0});
		idx.set(0,0);
		
		bridge.getV(idx, tmp);
		t.getV(idx2, tmp2);
		
		assertTrue(G.CDBL.isEqual().call(tmp, tmp2));
		
		idx2.set(0, 0);
		idx2.set(1, 1);
		bridge.setDims(new int[] {1}, new long[] {1}, new long[] {0});
		idx.set(0,0);
		
		bridge.getV(idx, tmp);
		t.getV(idx2, tmp2);
		
		assertTrue(G.CDBL.isEqual().call(tmp, tmp2));
		
		idx2.set(0, 1);
		idx2.set(1, 1);
		bridge.setDims(new int[] {1}, new long[] {1}, new long[] {1});
		idx.set(0,0);  // 0,1 works but is nonsensical
		
		bridge.getV(idx, tmp);
		t.getV(idx2, tmp2);
		
		assertTrue(G.CDBL.isEqual().call(tmp, tmp2));

		assertEquals(1, bridge.numDimensions());
		
		try {
			bridge.alloc(new long[] {-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {0});
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(new long[] {1}));
		try {
			bridge.alloc(new long[] {2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {4});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {1,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.init(new long[] {-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(new long[] {0});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(new long[] {1});
		idx.set(0, 0);
		tmp.setR(104);
		tmp.setI(105);
		bridge.getV(idx, tmp);
		assertTrue(G.CDBL.isZero().call(tmp));
		try {
			bridge.init(new long[] {2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(new long[] {4});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(new long[] {2, 2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.reshape(new long[] {-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(new long[] {0});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(new long[] {1});
		idx.set(0,0);
		tmp.setR(104);
		tmp.setI(105);
		bridge.getV(idx, tmp);
		assertTrue(G.CDBL.isZero().call(tmp));
		try {
			bridge.reshape(new long[] {2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(new long[] {4});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(new long[] {2, 2});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		idx.set(0, 0);
		tmp.setR(101);
		tmp.setI(102);
		bridge.setV(idx, tmp);
		tmp.setR(-1);
		tmp.setI(-1);
		bridge.getV(idx, tmp);
		assertEquals(101, tmp.r(), 0);
		assertEquals(102, tmp.i(), 0);
		
		assertEquals(1, bridge.rank());
		assertEquals(0, bridge.upperRank());
		assertEquals(1, bridge.lowerRank());
		assertFalse(bridge.indexIsUpper(0));
		assertTrue(bridge.indexIsLower(0));
		try {
			assertFalse(bridge.indexIsUpper(1));
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			assertFalse(bridge.indexIsLower(1));
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertEquals(t.storageType(), bridge.storageType());
	}
}
