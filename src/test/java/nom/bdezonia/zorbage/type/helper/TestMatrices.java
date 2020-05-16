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
package nom.bdezonia.zorbage.type.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */

public class TestMatrices {

	@Test
	public void testMatrixToNumber() {
		
		ComplexFloat64MatrixMember m = new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		MatrixElementNumberBridge<ComplexFloat64Member> bridge = new MatrixElementNumberBridge<ComplexFloat64Member>(m);
		
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
		
		bridge.setRowCol(0, 0);
		bridge.v(tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);

		bridge.setRowCol(0, 1);
		bridge.v(tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);

		bridge.setRowCol(1, 0);
		bridge.v(tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);

		bridge.setRowCol(1, 1);
		bridge.v(tmp);
		assertEquals(7, tmp.r(), 0);
		assertEquals(8, tmp.i(), 0);

		tmp.setR(1000);
		tmp.setI(2000);
		bridge.setV(tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.v(tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);
	}

	@Test
	public void testMatrixColumnToRModule() {
		
		ComplexFloat64MatrixMember m = new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		MatrixColumnRModuleBridge<ComplexFloat64Member> bridge = new MatrixColumnRModuleBridge<ComplexFloat64Member>(G.CDBL,m);

		// test a 1xc matrix
		
		bridge.setCol(0);
		
		bridge.v(0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.v(1, tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);

		bridge.setCol(1);
		
		bridge.v(0, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);
		
		bridge.v(1, tmp);
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
			bridge.v(i, tmp);
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
		bridge.v(0, tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);

		tmp.setR(100);
		tmp.setI(200);
		bridge.setV(1, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.v(1, tmp);
		assertEquals(100, tmp.r(), 0);
		assertEquals(200, tmp.i(), 0);

		assertEquals(m.storageType(), bridge.storageType());
	}

	@Test
	public void testMatrixRowToRModule() {
		
		ComplexFloat64MatrixMember m = new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		MatrixRowRModuleBridge<ComplexFloat64Member> bridge = new MatrixRowRModuleBridge<ComplexFloat64Member>(G.CDBL,m);

		// test a rx1 matrix
		
		bridge.setRow(0);
		
		bridge.v(0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.v(1, tmp);
		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);

		bridge.setRow(1);
		
		bridge.v(0, tmp);
		assertEquals(5, tmp.r(), 0);
		assertEquals(6, tmp.i(), 0);
		
		bridge.v(1, tmp);
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
			bridge.v(i, tmp);
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
		bridge.v(0, tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);

		tmp.setR(100);
		tmp.setI(200);
		bridge.setV(1, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.v(1, tmp);
		assertEquals(100, tmp.r(), 0);
		assertEquals(200, tmp.i(), 0);

		assertEquals(m.storageType(), bridge.storageType());
	}

	@Test
	public void testMatrixDiagonalToRModule() {
		
		ComplexFloat64MatrixMember m = new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		MatrixDiagonalRModuleBridge<ComplexFloat64Member> bridge = new MatrixDiagonalRModuleBridge<ComplexFloat64Member>(G.CDBL,m);

		// test a rx1 matrix
		
		bridge.v(0, tmp);
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.v(1, tmp);
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
			bridge.v(i, tmp);
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
		bridge.v(0, tmp);
		assertEquals(1000, tmp.r(), 0);
		assertEquals(2000, tmp.i(), 0);

		tmp.setR(100);
		tmp.setI(200);
		bridge.setV(1, tmp);
		tmp.setR(1);
		tmp.setI(2);
		bridge.v(1, tmp);
		assertEquals(100, tmp.r(), 0);
		assertEquals(200, tmp.i(), 0);

		assertEquals(m.storageType(), bridge.storageType());
	}

	@Test
	public void testMatrixToSubMatrix() {

		ComplexFloat64MatrixMember m = new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		SubMatrixBridge<ComplexFloat64Member> bridge = new SubMatrixBridge<ComplexFloat64Member>(G.CDBL, m);
		
		bridge.setSubcol(0, 1);
		bridge.setSubrow(0, 2);

		assertEquals(1, bridge.cols());
		assertEquals(2, bridge.rows());

		bridge.v(0, 0, tmp);
		
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.v(0, 1, tmp);

		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);
		
		bridge.setSubcol(0, 2);
		bridge.setSubrow(0, 1);

		assertEquals(2, bridge.cols());
		assertEquals(1, bridge.rows());

		bridge.v(0, 0, tmp);
		
		assertEquals(1, tmp.r(), 0);
		assertEquals(2, tmp.i(), 0);
		
		bridge.v(0, 1, tmp);

		assertEquals(3, tmp.r(), 0);
		assertEquals(4, tmp.i(), 0);

		bridge.setSubcol(0, 2);
		bridge.setSubrow(1, 1);

		try {
			bridge.dimension(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertEquals(2, bridge.dimension(0));
		for (int i = 1; i < 10; i++) {
			assertEquals(1, bridge.dimension(i));
			assertTrue(true);
		}
		
		assertEquals(2, bridge.numDimensions());
		
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
			bridge.alloc(1, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(1, 2));
		try {
			bridge.alloc(2, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.alloc(4,4);
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
			bridge.init(1, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(1, 2);
		tmp.setR(104);
		tmp.setI(105);
		bridge.v(0, 0, tmp);
		assertTrue(G.CDBL.isZero().call(tmp));
		tmp.setR(104);
		tmp.setI(105);
		bridge.v(0, 1, tmp);
		assertTrue(G.CDBL.isZero().call(tmp));
		try {
			bridge.init(2, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.init(4, 4);
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
			bridge.reshape(1, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(1, 2);
		tmp.setR(104);
		tmp.setI(105);
		bridge.v(0, 0, tmp);
		assertTrue(G.CDBL.isZero().call(tmp));
		tmp.setR(104);
		tmp.setI(105);
		bridge.v(0, 1, tmp);
		assertTrue(G.CDBL.isZero().call(tmp));
		try {
			bridge.reshape(2, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			bridge.reshape(4, 4);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertEquals(m.storageType(), bridge.storageType());
	}

	@Test
	public void testMatrixToTensor() {

		ComplexFloat64MatrixMember m = new ComplexFloat64MatrixMember(2, 2, new double[] {1,2,3,4,5,6,7,8});
		
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		
		MatrixTensorBridge<ComplexFloat64Member> bridge = new MatrixTensorBridge<ComplexFloat64Member>(G.CDBL, m);
		
		assertEquals(2, bridge.dimension());
		
		assertEquals(2, bridge.numDimensions());
		
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
		try {
			bridge.alloc(new long[] {1,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		assertFalse(bridge.alloc(new long[] {2,2}));
		try {
			bridge.alloc(new long[] {5,5});
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
		try {
			bridge.init(new long[] {1,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.init(new long[] {2,2});
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < 2; r++) {
			idx.set(1, r);
			for (int c = 0; c < 2; c++) {
				idx.set(0, c);
				tmp.setR(123);
				tmp.setI(456);
				bridge.v(idx, tmp);
				assertTrue(G.CDBL.isZero().call(tmp));
			}
		}
		try {
			bridge.init(new long[] {5,5});
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
		try {
			bridge.reshape(new long[] {1,1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		bridge.reshape(new long[] {2,2});
		try {
			bridge.reshape(new long[] {3,3});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertTrue(bridge.indexIsLower(0));
		assertFalse(bridge.indexIsUpper(0));
		
		assertTrue(bridge.indexIsLower(1));
		assertFalse(bridge.indexIsUpper(1));
		
		assertEquals(0, bridge.upperRank());
		assertEquals(2, bridge.lowerRank());
		assertEquals(2, bridge.rank());
		
		assertEquals(m.storageType(), bridge.storageType());
		
		tmp.setR(103);
		tmp.setI(109);
		bridge.setV(idx, tmp);
		tmp.setR(0);
		tmp.setI(0);
		bridge.v(idx, tmp);
		assertEquals(103,  tmp.r(), 0);
		assertEquals(109,  tmp.i(), 0);
	}
}
