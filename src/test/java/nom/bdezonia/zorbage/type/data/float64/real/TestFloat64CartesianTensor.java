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
package nom.bdezonia.zorbage.type.data.float64.real;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64CartesianTensor {

	@Test
	public void test1() {
		Float64Member value = G.DBL.construct();
		Float64CartesianTensorProductMember a = G.DBL_TEN.construct("[[[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]]]");
		assertEquals(3, a.rank());
		assertEquals(3, a.dimension(0));
		assertEquals(3, a.dimension(1));
		assertEquals(3, a.dimension(2));

		G.DBL_TEN.unity().call(a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(1, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}

		Float64Member scale = new Float64Member(14);
		
		G.DBL_TEN.scale().call(scale, a, a);
		
		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(14, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}

		G.DBL_TEN.power().call(3, a, a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertEquals(14*14*14, value.v(), 0);
			else
				assertEquals(0, value.v(), 0);
		}

		Float64CartesianTensorProductMember x = new Float64CartesianTensorProductMember(
				new long[] {3,2,2},
				new double[]{1,2,3,
								4,5,6,
								7,8,9,
								10,11,12});
		Float64CartesianTensorProductMember y = new Float64CartesianTensorProductMember(
				new long[] {2,3,2},
				new double[]{13,14,
								15,16,
								17,18,
								19,20,
								21,22,
								23,24
								});
		Float64CartesianTensorProductMember z = new Float64CartesianTensorProductMember();

		G.DBL_TEN.multiply().call(x, y, z);

		// from example at 
		//   https://www.tensorflow.org/api_docs/python/tf/linalg/matmul
		// which assumes multiply is a generalized mat mul. my code might not do this.
		//assertEquals(94, value.v(), 0);
		//assertEquals(100, value.v(), 0);
		//assertEquals(229, value.v(), 0);
		//assertEquals(244, value.v(), 0);
		//assertEquals(508, value.v(), 0);
		//assertEquals(532, value.v(), 0);
		//assertEquals(697, value.v(), 0);
		//assertEquals(732, value.v(), 0);
	}
	
	@Test
	public void test2() {

		int rank = 2;
		int dimension = 3;
		
		Float64Member tmp1 = G.DBL.construct();
		Float64Member tmp2 = G.DBL.construct();
		
		Float64CartesianTensorProductMember value1 = new Float64CartesianTensorProductMember(rank, dimension);
		Float64CartesianTensorProductMember value2 = new Float64CartesianTensorProductMember(rank, dimension);

		assertNotNull(G.DBL_TEN.construct());
		Float64CartesianTensorProductMember junk1 = G.DBL_TEN.construct("[1,2,3][4,5,6][7,8,9]");
		assertEquals(2, junk1.rank());
		assertEquals(3, junk1.dimension());
		junk1.v(1, tmp1);
		assertEquals(2, tmp1.v(), 0);
		Float64CartesianTensorProductMember junk2 = G.DBL_TEN.construct(junk1);
		assertTrue(G.DBL_TEN.isEqual().call(junk1, junk2));
		assertFalse(G.DBL_TEN.isNotEqual().call(junk1, junk2));
		
		assertTrue(G.DBL_TEN.isZero().call(value1));
		G.DBL_TEN.unity().call(value1);
		assertFalse(G.DBL_TEN.isZero().call(value1));
		G.DBL_TEN.zero().call(value1);
		assertTrue(G.DBL_TEN.isZero().call(value1));

		tmp1.setV(44);
		G.DBL_TEN.addScalar().call(tmp1, value1, value2);
		assertFalse(G.DBL_TEN.isEqual().call(value1, value2));
		assertTrue(G.DBL_TEN.isNotEqual().call(value1, value2));
		value2.v(0, tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		
		tmp2.setV(4);
		G.DBL_TEN.multiplyByScalar().call(tmp2, value2, value2);
		value2.v(0, tmp2);
		assertEquals(44*4, tmp2.v(), 0);

		tmp2.setV(4);
		G.DBL_TEN.divideByScalar().call(tmp2, value2, value2);
		value2.v(0, tmp2);
		assertEquals(44, tmp2.v(), 0);

		G.DBL_TEN.subtractScalar().call(tmp1, value2, value1);
		assertTrue(G.DBL_TEN.isZero().call(value1));

		G.DBL_TEN.assign().call(value2, value1);
		assertFalse(G.DBL_TEN.isZero().call(value1));
		assertTrue(G.DBL_TEN.isEqual().call(value1, value2));

		G.DBL_TEN.unity().call(value1);
		value1.v(0, tmp1);
		assertEquals(1, tmp1.v(), 0);
		G.DBL_TEN.add().call(value1, value1, value2);
		value2.v(0, tmp1);
		assertEquals(2, tmp1.v(), 0);

		G.DBL_TEN.multiplyElements().call(value2, value2, value1);
		value1.v(0, tmp1);
		assertEquals(4, tmp1.v(), 0);
		value1.v(1, tmp1);
		assertEquals(0, tmp1.v(), 0);

		assertTrue(value1.numElems() > 0);
		assertTrue(value2.numElems() > 0);
		for (long i = 0; i < value1.numElems(); i++) {
			tmp1.setV(i);
			tmp2.setV(43);
			value1.setV(i, tmp1);
			value2.setV(i, tmp2);
		}
		G.DBL_TEN.divideElements().call(value1, value2, value1);
		assertTrue(value1.numElems() > 0);
		assertTrue(value1.numElems() == value2.numElems());
		for (long i = 0; i < value1.numElems(); i++) {
			value1.v(i, tmp1);
			assertEquals(1.0*i/43, tmp1.v(), 0.000000000000001);
		}

		G.DBL_TEN.subtract().call(value1, value1, value2);
		assertTrue(G.DBL_TEN.isZero().call(value2));
		
		value1 = new Float64CartesianTensorProductMember("[1,2][3,4]");
		assertEquals(2, value1.rank());
		assertEquals(2, value1.dimension());
		G.DBL_TEN.multiply().call(value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			assertEquals(((i % 4)+1) * ((i / 4) + 1), tmp2.v(), 0);
		}
		
		G.DBL_TEN.unity().call(value1);
		assertFalse(G.DBL_TEN.isNaN().call(value1));
		G.DBL_TEN.nan().call(value1);
		assertTrue(G.DBL_TEN.isNaN().call(value1));

		G.DBL_TEN.unity().call(value1);
		assertFalse(G.DBL_TEN.isInfinite().call(value1));
		G.DBL_TEN.infinite().call(value1);
		assertTrue(G.DBL_TEN.isInfinite().call(value1));

		value1 = new Float64CartesianTensorProductMember("[1,2][3,4]");
		G.DBL_TEN.negate().call(value1, value2);
		assertFalse(G.DBL_TEN.isEqual().call(value1, value2));
		value2.v(2, tmp2);
		assertEquals(-3, tmp2.v(), 0);

		G.DBL_TEN.commaDerivative().call(0, value1, value2);
		value2.v(0, tmp2);
		assertEquals(1, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(2, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(0, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(0, tmp2.v(), 0);

		G.DBL_TEN.commaDerivative().call(1, value1, value2);
		value2.v(0, tmp2);
		assertEquals(0, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(0, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(3, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(4, tmp2.v(), 0);
		
		G.DBL_TEN.semicolonDerivative().call(0, value1, value2);
		value2.v(0, tmp2);
		assertEquals(1, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(2, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(0, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(0, tmp2.v(), 0);

		G.DBL_TEN.semicolonDerivative().call(1, value1, value2);
		value2.v(0, tmp2);
		assertEquals(0, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(0, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(3, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(4, tmp2.v(), 0);
		
		value1 = new Float64CartesianTensorProductMember("[1,2][3,4]");
		G.DBL_TEN.norm().call(value1, tmp1);
		assertEquals(Math.sqrt(30), tmp1.v(), 0);
		
		value1 = new Float64CartesianTensorProductMember("[1,2][3,4]");
		assertEquals(2, value1.rank());
		assertEquals(2, value1.dimension());
		G.DBL_TEN.outerProduct().call(value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			assertEquals(((i % 4)+1) * ((i / 4) + 1), tmp2.v(), 0);
		}

		try {
			G.DBL_TEN.lowerIndex().call(0, value1, value2);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			G.DBL_TEN.raiseIndex().call(0, value1, value2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		value1 = new Float64CartesianTensorProductMember(3,2);
		assertEquals(3, value1.rank());
		assertEquals(2, value1.dimension());
		assertEquals(8, value1.numElems());
		for (int i = 0; i < 8; i++) {
			tmp1.setV(i+1);
			value1.setV(i, tmp1);
		}
		G.DBL_TEN.contract().call(0, 1, value1, value2);
		value2.v(0, tmp2);
		assertEquals(5, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(13, tmp2.v(), 0);

		G.DBL_TEN.innerProduct().call(0, 1, value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			// TODO do something. Note that the impl does a simple approach that matches theory very closely. I think we can
			//   get by not testing this a lot yet.
			// System.out.println(tmp2.v());
			//assertEquals(some func val, tmp2.v(), 0);
		}

		// tested in test1() above
		//G.DBL_TEN.power();
		
		value1 = new Float64CartesianTensorProductMember(2,2);
		tmp1.setV(3);
		value1.setV(0, tmp1);
		tmp1.setV(6);
		value1.setV(1, tmp1);
		tmp1.setV(9);
		value1.setV(2, tmp1);
		tmp1.setV(12);
		value1.setV(3, tmp1);
		tmp2.setV(5);
		G.DBL_TEN.scale().call(tmp2, value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.v(), 0);

		value1 = new Float64CartesianTensorProductMember(2,2);
		tmp1.setV(3);
		value1.setV(0, tmp1);
		tmp1.setV(6);
		value1.setV(1, tmp1);
		tmp1.setV(9);
		value1.setV(2, tmp1);
		tmp1.setV(12);
		value1.setV(3, tmp1);
		G.DBL_TEN.scaleByDouble().call(5.0, value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.v(), 0);

		value1 = new Float64CartesianTensorProductMember(2,2);
		tmp1.setV(3);
		value1.setV(0, tmp1);
		tmp1.setV(6);
		value1.setV(1, tmp1);
		tmp1.setV(9);
		value1.setV(2, tmp1);
		tmp1.setV(12);
		value1.setV(3, tmp1);
		G.DBL_TEN.scaleByHighPrec().call(new HighPrecisionMember(BigDecimal.valueOf(5.0)), value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.v(), 0);

		value1 = new Float64CartesianTensorProductMember(2,2);
		tmp1.setV(3);
		value1.setV(0, tmp1);
		tmp1.setV(6);
		value1.setV(1, tmp1);
		tmp1.setV(9);
		value1.setV(2, tmp1);
		tmp1.setV(12);
		value1.setV(3, tmp1);
		G.DBL_TEN.scaleByRational().call(new RationalMember(100,20), value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertEquals(15, tmp2.v(), 0);
		value2.v(1, tmp2);
		assertEquals(30, tmp2.v(), 0);
		value2.v(2, tmp2);
		assertEquals(45, tmp2.v(), 0);
		value2.v(3, tmp2);
		assertEquals(60, tmp2.v(), 0);

		value1 = new Float64CartesianTensorProductMember("[1.25,2][3,4]");
		value2 = new Float64CartesianTensorProductMember("[1,2][3,4]");
		tmp1.setV(0.24);
		assertFalse(G.DBL_TEN.within().call(tmp1, value1, value2));
		tmp1.setV(0.26);
		assertTrue(G.DBL_TEN.within().call(tmp1, value1, value2));
		tmp1.setV(1);
		G.DBL_TEN.round().call(Mode.HALF_EVEN, tmp1, value1, value2);
		assertTrue(G.DBL_TEN.isEqual().call(value1, value2));

		// test with complex numbers where it makes sense
		
		IntegerIndex index = new IntegerIndex(2);
		ComplexFloat64Member ctmp1 = G.CDBL.construct();
		ComplexFloat64Member ctmp2 = G.CDBL.construct();
		ComplexFloat64CartesianTensorProductMember cvalue1 = new ComplexFloat64CartesianTensorProductMember(2,2);
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
		ComplexFloat64CartesianTensorProductMember cvalue2 = new ComplexFloat64CartesianTensorProductMember();
		
		G.CDBL_TEN.conjugate().call(cvalue1, cvalue2);
		assertEquals(cvalue1.rank(), cvalue2.rank());
		assertEquals(cvalue1.dimension(), cvalue2.dimension());
		index.set(0, 0);
		index.set(1, 0);
		cvalue1.v(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.v(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		index.set(0, 1);
		index.set(1, 0);
		cvalue1.v(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.v(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		index.set(0, 0);
		index.set(1, 1);
		cvalue1.v(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.v(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		index.set(0, 1);
		index.set(1, 1);
		cvalue1.v(index, ctmp1);
		G.CDBL.conjugate().call(ctmp1, ctmp1);
		cvalue2.v(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertEquals(tmp1.v(), tmp2.v(), 0);
		
		// a test to make sure rank 0 tensors can be accessed
		IntegerIndex idx = new IntegerIndex(0);
		value1 = new Float64CartesianTensorProductMember(0,3);
		value1.v(idx, tmp1);
	}
}
