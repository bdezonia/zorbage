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
package nom.bdezonia.zorbage.type.quaternion.float64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestQuaternionFloat64CartesianTensor {

	@Test
	public void test1() {
		QuaternionFloat64Member value = G.QDBL.construct();
		QuaternionFloat64CartesianTensorProductMember a =
				G.QDBL_TEN.construct("[[[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]]]");
		assertEquals(3, a.rank());
		assertEquals(3, a.dimension(0));
		assertEquals(3, a.dimension(1));
		assertEquals(3, a.dimension(2));

		G.QDBL_TEN.unity().call(a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) { // 1st, halfway, last
				assertEquals(1, value.r(), 0);
			}
			else {
				assertEquals(0, value.r(), 0);
			}
		}

		QuaternionFloat64Member scale = new QuaternionFloat64Member(14,0,0,0);
		
		G.QDBL_TEN.scale().call(scale, a, a);
		
		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(14, value.r(), 0);
			else
				assertEquals(0, value.r(), 0);
		}

		G.QDBL_TEN.power().call(3, a, a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(14*14*14, value.r(), 0);
			else
				assertEquals(0, value.r(), 0);
		}

		QuaternionFloat64CartesianTensorProductMember x =
				new QuaternionFloat64CartesianTensorProductMember(2, 2, 1,2,3,4, 0,0,0,0, 0,0,0,0, 0,0,0,0);
		QuaternionFloat64CartesianTensorProductMember y =
				new QuaternionFloat64CartesianTensorProductMember(2, 2, 5,6,7,8, 0,0,0,0, 0,0,0,0, 0,0,0,0);
		QuaternionFloat64CartesianTensorProductMember z =
				new QuaternionFloat64CartesianTensorProductMember();

		G.QDBL_TEN.multiply().call(x, y, z);
		
		assertEquals(4, z.rank());
		assertEquals(2, z.dimension());

		// TODO I only support "square" tensors. Make a non square example from
		//   https://www.tensorflow.org/api_docs/python/tf/linalg/matmul
		//   and modify my code to work with it.
	}
	
	@Test
	public void test2() {

		int rank = 2;
		int dimension = 3;
		
		QuaternionFloat64Member tmp1 = G.QDBL.construct();
		QuaternionFloat64Member tmp2 = G.QDBL.construct();
		Float64Member tmp1v = G.DBL.construct();
		Float64Member tmp2v = G.DBL.construct();
		
		QuaternionFloat64CartesianTensorProductMember value1 =
				new QuaternionFloat64CartesianTensorProductMember(rank, dimension);
		QuaternionFloat64CartesianTensorProductMember value2 =
				new QuaternionFloat64CartesianTensorProductMember(rank, dimension);

		assertNotNull(G.QDBL_TEN.construct());
		QuaternionFloat64CartesianTensorProductMember junk1 = G.QDBL_TEN.construct("[1,2,3][4,5,6][7,8,9]");
		assertEquals(2, junk1.rank());
		assertEquals(3, junk1.dimension());
		junk1.v(1, tmp1);
		assertEquals(2, tmp1.r(), 0);
		QuaternionFloat64CartesianTensorProductMember junk2 = G.QDBL_TEN.construct(junk1);
		assertTrue(G.QDBL_TEN.isEqual().call(junk1, junk2));
		assertFalse(G.QDBL_TEN.isNotEqual().call(junk1, junk2));
		
		assertTrue(G.QDBL_TEN.isZero().call(value1));
		G.QDBL_TEN.unity().call(value1);
		assertFalse(G.QDBL_TEN.isZero().call(value1));
		G.QDBL_TEN.zero().call(value1);
		assertTrue(G.QDBL_TEN.isZero().call(value1));

		tmp1.setR(44);
		G.QDBL_TEN.addScalar().call(tmp1, value1, value2);
		assertFalse(G.QDBL_TEN.isEqual().call(value1, value2));
		assertTrue(G.QDBL_TEN.isNotEqual().call(value1, value2));
		value2.v(0, tmp2);
		assertEquals(tmp1.r(), tmp2.r(), 0);
		
		tmp2.setR(4);
		G.QDBL_TEN.multiplyByScalar().call(tmp2, value2, value2);
		value2.v(0, tmp2);
		assertEquals(44*4, tmp2.r(), 0);

		tmp2.setR(4);
		G.QDBL_TEN.divideByScalar().call(tmp2, value2, value2);
		value2.v(0, tmp2);
		assertEquals(44, tmp2.r(), 0);

		G.QDBL_TEN.subtractScalar().call(tmp1, value2, value1);
		assertTrue(G.QDBL_TEN.isZero().call(value1));

		G.QDBL_TEN.assign().call(value2, value1);
		assertFalse(G.QDBL_TEN.isZero().call(value1));
		assertTrue(G.QDBL_TEN.isEqual().call(value1, value2));

		G.QDBL_TEN.unity().call(value1);
		value1.v(0, tmp1);
		assertEquals(1, tmp1.r(), 0);
		G.QDBL_TEN.add().call(value1, value1, value2);
		value2.v(0, tmp1);
		assertEquals(2, tmp1.r(), 0);

		G.QDBL_TEN.multiplyElements().call(value2, value2, value1);
		value1.v(0, tmp1);
		assertEquals(4, tmp1.r(), 0);
		value1.v(1, tmp1);
		assertEquals(0, tmp1.r(), 0);

		assertTrue(value1.numElems() > 0);
		assertTrue(value2.numElems() > 0);
		for (long i = 0; i < value1.numElems(); i++) {
			tmp1.setR(i);
			tmp2.setR(43);
			value1.setV(i, tmp1);
			value2.setV(i, tmp2);
		}
		G.QDBL_TEN.divideElements().call(value1, value2, value1);
		assertTrue(value1.numElems() > 0);
		assertTrue(value1.numElems() == value2.numElems());
		for (long i = 0; i < value1.numElems(); i++) {
			value1.v(i, tmp1);
			assertEquals(1.0*i/43, tmp1.r(), 0.000000000000001);
		}

		G.QDBL_TEN.subtract().call(value1, value1, value2);
		assertTrue(G.QDBL_TEN.isZero().call(value2));
		
		value1 = new QuaternionFloat64CartesianTensorProductMember("[1,2][3,4]");
		assertEquals(2, value1.rank());
		assertEquals(2, value1.dimension());
		G.QDBL_TEN.multiply().call(value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			assertEquals(((i % 4)+1) * ((i / 4) + 1), tmp2.r(), 0);
		}
		
		G.QDBL_TEN.unity().call(value1);
		assertFalse(G.QDBL_TEN.isNaN().call(value1));
		G.QDBL_TEN.nan().call(value1);
		assertTrue(G.QDBL_TEN.isNaN().call(value1));

		G.QDBL_TEN.unity().call(value1);
		assertFalse(G.QDBL_TEN.isInfinite().call(value1));
		G.QDBL_TEN.infinite().call(value1);
		assertTrue(G.QDBL_TEN.isInfinite().call(value1));

		value1 = new QuaternionFloat64CartesianTensorProductMember("[1,2][3,4]");
		G.QDBL_TEN.negate().call(value1, value2);
		assertFalse(G.QDBL_TEN.isEqual().call(value1, value2));
		value2.v(2, tmp2);
		assertEquals(-3, tmp2.r(), 0);

		G.QDBL_TEN.commaDerivative().call(0, value1, value2);
		value2.v(0, tmp2);
		assertEquals(1, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(2, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(0, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(0, tmp2.r(), 0);

		G.QDBL_TEN.commaDerivative().call(1, value1, value2);
		value2.v(0, tmp2);
		assertEquals(0, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(0, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(3, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(4, tmp2.r(), 0);
		
		G.QDBL_TEN.semicolonDerivative().call(0, value1, value2);
		value2.v(0, tmp2);
		assertEquals(1, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(2, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(0, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(0, tmp2.r(), 0);

		G.QDBL_TEN.semicolonDerivative().call(1, value1, value2);
		value2.v(0, tmp2);
		assertEquals(0, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(0, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(3, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(4, tmp2.r(), 0);
		
		value1 = new QuaternionFloat64CartesianTensorProductMember("[1,2][3,4]");
		G.QDBL_TEN.norm().call(value1, tmp1v);
		assertEquals(Math.sqrt(30), tmp1v.v(), 0);
		
		value1 = new QuaternionFloat64CartesianTensorProductMember("[1,2][3,4]");
		assertEquals(2, value1.rank());
		assertEquals(2, value1.dimension());
		G.QDBL_TEN.outerProduct().call(value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			assertEquals(((i % 4)+1) * ((i / 4) + 1), tmp2.r(), 0);
		}

		try {
			G.QDBL_TEN.lowerIndex().call(0, value1, value2);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			G.QDBL_TEN.raiseIndex().call(0, value1, value2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		value1 = new QuaternionFloat64CartesianTensorProductMember(3,2);
		assertEquals(3, value1.rank());
		assertEquals(2, value1.dimension());
		assertEquals(8, value1.numElems());
		for (int i = 0; i < 8; i++) {
			tmp1.setR(i+1);
			value1.setV(i, tmp1);
		}
		G.QDBL_TEN.contract().call(0, 1, value1, value2);
		value2.v(0, tmp2);
		assertEquals(5, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(13, tmp2.r(), 0);

		G.QDBL_TEN.innerProduct().call(0, 1, value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			// TODO do something. Note that the impl does a simple approach
			// that matches theory very closely. I think we can get by not
			// testing this a lot yet.
			//
			// System.out.println(tmp2.r());
			//assertEquals(some func val, tmp2.r(), 0);
		}

		// tested in test1() above
		//G.QDBL_TEN.power();
		
		value1 = new QuaternionFloat64CartesianTensorProductMember(2,2);
		tmp1.setR(3);
		value1.setV(0, tmp1);
		tmp1.setR(6);
		value1.setV(1, tmp1);
		tmp1.setR(9);
		value1.setV(2, tmp1);
		tmp1.setR(12);
		value1.setV(3, tmp1);
		tmp2.setR(5);
		G.QDBL_TEN.scale().call(tmp2, value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.r(), 0);

		value1 = new QuaternionFloat64CartesianTensorProductMember(2,2);
		tmp1.setR(3);
		value1.setV(0, tmp1);
		tmp1.setR(6);
		value1.setV(1, tmp1);
		tmp1.setR(9);
		value1.setV(2, tmp1);
		tmp1.setR(12);
		value1.setV(3, tmp1);
		G.QDBL_TEN.scaleByDouble().call(5.0, value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.r(), 0);

		value1 = new QuaternionFloat64CartesianTensorProductMember(2,2);
		tmp1.setR(3);
		value1.setV(0, tmp1);
		tmp1.setR(6);
		value1.setV(1, tmp1);
		tmp1.setR(9);
		value1.setV(2, tmp1);
		tmp1.setR(12);
		value1.setV(3, tmp1);
		G.QDBL_TEN.scaleByHighPrec().call(new HighPrecisionMember(BigDecimal.valueOf(5.0)), value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.r(), 0);

		value1 = new QuaternionFloat64CartesianTensorProductMember(2,2);
		tmp1.setR(3);
		value1.setV(0, tmp1);
		tmp1.setR(6);
		value1.setV(1, tmp1);
		tmp1.setR(9);
		value1.setV(2, tmp1);
		tmp1.setR(12);
		value1.setV(3, tmp1);
		G.QDBL_TEN.scaleByRational().call(new RationalMember(100,20), value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.r(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.r(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.r(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.r(), 0);

		value1 = new QuaternionFloat64CartesianTensorProductMember("[1.25,2][3,4]");
		value2 = new QuaternionFloat64CartesianTensorProductMember("[1,2][3,4]");
		tmp1v.setV(0.24);
		assertFalse(G.QDBL_TEN.within().call(tmp1v, value1, value2));
		tmp1v.setV(0.26);
		assertTrue(G.QDBL_TEN.within().call(tmp1v, value1, value2));
		tmp1v.setV(1);
		G.QDBL_TEN.round().call(Mode.HALF_EVEN, tmp1v, value1, value2);
		assertTrue(G.QDBL_TEN.isEqual().call(value1, value2));

		// test with complex numbers where it makes sense
		
		IntegerIndex index = new IntegerIndex(2);
		ComplexFloat64Member ctmp1 = G.CDBL.construct();
		ComplexFloat64Member ctmp2 = G.CDBL.construct();
		ComplexFloat64CartesianTensorProductMember cvalue1 =
				new ComplexFloat64CartesianTensorProductMember(2,2);
		index.set(0, 0);
		index.set(1, 0);
		ctmp1.setR(1);
		ctmp1.setI(2);
		cvalue1.setV(index, ctmp1);
		index.set(0, 1);
		index.set(1, 0);
		ctmp1.setR(3);
		ctmp1.setI(4);
		cvalue1.setV(index, ctmp1);
		index.set(0, 0);
		index.set(1, 1);
		ctmp1.setR(5);
		ctmp1.setI(6);
		cvalue1.setV(index, ctmp1);
		index.set(0, 1);
		index.set(1, 0);
		ctmp1.setR(7);
		ctmp1.setI(8);
		cvalue1.setV(index, ctmp1);
		ComplexFloat64CartesianTensorProductMember cvalue2 =
				new ComplexFloat64CartesianTensorProductMember();
		
		G.CDBL_TEN.conjugate().call(cvalue1, cvalue2);
		assertEquals(cvalue1.rank(), cvalue2.rank());
		assertEquals(cvalue1.dimension(), cvalue2.dimension());
		index.set(0, 0);
		index.set(1, 0);
		cvalue1.getV(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1v);
		ctmp2.getR(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		ctmp1.getI(tmp1v);
		ctmp2.getI(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		index.set(0, 1);
		index.set(1, 0);
		cvalue1.getV(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1v);
		ctmp2.getR(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		ctmp1.getI(tmp1v);
		ctmp2.getI(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		index.set(0, 0);
		index.set(1, 1);
		cvalue1.getV(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1v);
		ctmp2.getR(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		ctmp1.getI(tmp1v);
		ctmp2.getI(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		index.set(0, 1);
		index.set(1, 1);
		cvalue1.getV(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1v);
		ctmp2.getR(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		ctmp1.getI(tmp1v);
		ctmp2.getI(tmp2v);
		assertEquals(tmp1v.v(), tmp2v.v(), 0);
		
		// a test to make sure rank 0 tensors can be accessed
		IntegerIndex idx = new IntegerIndex(0);
		value1 = new QuaternionFloat64CartesianTensorProductMember(0,3);
		tmp1.setR(53.9);
		value1.setV(idx, tmp1);
		tmp2.setR(-99999);
		value1.getV(idx, tmp2);
		assertEquals(53.9, tmp2.r(), 0);
	}
}
