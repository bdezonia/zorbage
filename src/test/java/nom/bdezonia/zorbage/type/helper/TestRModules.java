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
package nom.bdezonia.zorbage.type.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */

public class TestRModules {

	@Test
	public void testRModuleToNumber() {
		
		ComplexFloat64VectorMember v = new ComplexFloat64VectorMember(new double[] {1,2,3,4,5,6,7,8,9,10});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		RModuleElementNumberBridge<ComplexFloat64Member> bridge = new RModuleElementNumberBridge<ComplexFloat64Member>(v);
		
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
		
		for (int i = 0; i < v.length(); i++) {
			bridge.setIndex(i);
			bridge.getV(tmp);
			assertEquals(2*i+1, tmp.r(), 0);
			assertEquals(2*i+2, tmp.i(), 0);
		}

		bridge.setIndex(0);
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
	public void testRModuleToSubRModule() {
		
		ComplexFloat64VectorMember v = new ComplexFloat64VectorMember(new double[] {1,2,3,4,5,6,7,8,9,10});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		SubRModuleBridge<ComplexFloat64Member> bridge = new SubRModuleBridge<ComplexFloat64Member>(G.CDBL, v);
		bridge.setSubrange(2, 3);

		assertEquals(3, bridge.length());
		
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(3, bridge.dimension(0));
		for (int i = 1; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
		assertEquals(1, bridge.numDimensions());
		
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
		try {
			bridge.alloc(1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(3));
		try {
			bridge.alloc(4);
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
		try {
			bridge.init(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(3);
		for (int i = 0; i < 3; i++) {
			tmp.setR(104);
			tmp.setI(105);
			bridge.getV(i, tmp);
			assertTrue(G.CDBL.isZero().call(tmp));
		}
		try {
			bridge.init(4);
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
		try {
			bridge.reshape(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(3);
		for (int i = 0; i < 3; i++) {
			tmp.setR(104);
			tmp.setI(105);
			bridge.getV(i, tmp);
			assertTrue(G.CDBL.isZero().call(tmp));
		}
		v.getV(0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		v.getV(1, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);
		try {
			bridge.reshape(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertEquals(v.storageType(), bridge.storageType());
	}

	@Test
	public void testRModuleToMatrix() {
		
		ComplexFloat64VectorMember v = new ComplexFloat64VectorMember(new double[] {1,2,3,4,5,6,7,8,9,10});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		RModuleMatrixBridge<ComplexFloat64Member> bridge = new RModuleMatrixBridge<ComplexFloat64Member>(G.CDBL, v);

		// test a 1xc matrix
		
		bridge.setIsColumn(true);
		
		assertTrue(bridge.isColumn());
		assertFalse(bridge.isRow());
		
		assertEquals(5, bridge.rows());
		assertEquals(1, bridge.cols());
		
		assertEquals(2, bridge.numDimensions());

		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(1, bridge.dimension(0));
		assertEquals(5, bridge.dimension(1));
		for (int i = 2; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
		}
		
		try {
			bridge.alloc(-1, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(0, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(1, 5);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(5, 1));
		try {
			bridge.alloc(10, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.init(-1, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(0, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(1, 5);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(5, 1);
		for (int i = 0; i < 5; i++) {
			tmp.setR(102);
			tmp.setI(105);
			bridge.getV(i, 0, tmp);
			assertTrue(G.CDBL.isZero().call(tmp));
		}
		try {
			bridge.init(10, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.reshape(-1, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(0, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(1, 5);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(5, 1);
		try {
			bridge.reshape(10, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		tmp.setR(1000);
		tmp.setI(2000);
		bridge.setV(1, 0, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.getV(1, 0, tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);

		assertEquals(v.storageType(), bridge.storageType());
		
		// test a rx1 matrix

		bridge.setIsColumn(false);
		
		assertFalse(bridge.isColumn());
		assertTrue(bridge.isRow());
		
		assertEquals(1, bridge.rows());
		assertEquals(5, bridge.cols());
		
		assertEquals(2, bridge.numDimensions());

		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(5, bridge.dimension(0));
		assertEquals(1, bridge.dimension(1));
		for (int i = 2; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
		}
		
		try {
			bridge.alloc(-1, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(0, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(1, 5));
		try {
			bridge.alloc(5, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(10, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.init(-1, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(0, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(1, 5);
		for (int i = 0; i < 5; i++) {
			tmp.setR(102);
			tmp.setI(105);
			bridge.getV(0, i, tmp);
			assertTrue(G.CDBL.isZero().call(tmp));
		}
		try {
			bridge.init(5, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(10, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		try {
			bridge.reshape(-1, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(0, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(1, 5);
		try {
			bridge.reshape(5, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(10, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		tmp.setR(1000);
		tmp.setI(2000);
		bridge.setV(0, 1, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.getV(0, 1, tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);

		assertEquals(v.storageType(), bridge.storageType());
		
	}

	@Test
	public void testRModuleToTensor() {
		
		ComplexFloat64VectorMember v = new ComplexFloat64VectorMember(new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		RModuleTensorBridge<ComplexFloat64Member> bridge = new RModuleTensorBridge<ComplexFloat64Member>(G.CDBL, v);
		
		assertEquals(4, bridge.dimension());
		
		assertEquals(1, bridge.numDimensions());
		
		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(4, bridge.dimension(0));
		for (int i = 1; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
		try {
			bridge.alloc(new long[] {-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {0});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(new long[] {1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(new long[] {4}));
		try {
			bridge.alloc(new long[] {5});
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
		try {
			bridge.init(new long[] {1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(new long[] {4});
		IntegerIndex idx = new IntegerIndex(1);
		for (int i = 0; i < 4; i++) {
			idx.set(0, i);
			tmp.setR(123);
			tmp.setI(456);
			bridge.getV(idx, tmp);
			assertTrue(G.CDBL.isZero().call(tmp));
		}
		try {
			bridge.init(new long[] {5});
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
		try {
			bridge.reshape(new long[] {1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(new long[] {4});
		try {
			bridge.reshape(new long[] {5});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertTrue(bridge.indexIsLower(0));
		assertFalse(bridge.indexIsUpper(0));
		
		assertEquals(0, bridge.upperRank());
		assertEquals(1, bridge.lowerRank());
		assertEquals(1, bridge.rank());
		
		assertEquals(v.storageType(), bridge.storageType());
		
		tmp.setR(103);
		tmp.setI(109);
		bridge.setV(idx, tmp);
		tmp.setR(0);
		tmp.setI(0);
		bridge.getV(idx, tmp);
		assertEquals(103,  tmp.r(), 0);
		assertEquals(109,  tmp.i(), 0);
	}
}
