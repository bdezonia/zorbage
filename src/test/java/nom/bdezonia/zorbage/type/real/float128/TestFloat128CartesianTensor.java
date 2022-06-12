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
package nom.bdezonia.zorbage.type.real.float128;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat128CartesianTensor {

	private final BigDecimal TOL = BigDecimal.valueOf(0.000000000000001);

	@Test
	public void test1() {
		Float128Member value = G.QUAD.construct();
		Float128CartesianTensorProductMember a = G.QUAD_TEN.construct("[[[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]][[0,0,0][0,0,0][0,0,0]]]");
		assertEquals(3, a.rank());
		assertEquals(3, a.dimension(0));
		assertEquals(3, a.dimension(1));
		assertEquals(3, a.dimension(2));

		G.QUAD_TEN.unity().call(a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertTrue(BigDecimalUtils.isNear(1, value.v(), TOL));
			else
				assertTrue(BigDecimalUtils.isNear(0, value.v(), TOL));
		}

		Float128Member scale = new Float128Member(BigDecimal.valueOf(14));
		
		G.QUAD_TEN.scale().call(scale, a, a);
		
		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertTrue(BigDecimalUtils.isNear(14, value.v(), TOL));
			else
				assertTrue(BigDecimalUtils.isNear(0, value.v(), TOL));
		}

		G.QUAD_TEN.power().call(3, a, a);

		for (int i = 0; i < 27; i++) {
			a.v(i, value);
			if (i == 0 || i == 13 || i == 26) // 1st, halfway, last
				assertTrue(BigDecimalUtils.isNear(14*14*14, value.v(), TOL));
			else
				assertTrue(BigDecimalUtils.isNear(0, value.v(), TOL));
		}

		Float128CartesianTensorProductMember x = new Float128CartesianTensorProductMember(2, 2, new BigDecimal[] {BigDecimal.valueOf(1),BigDecimal.valueOf(2),BigDecimal.valueOf(3),BigDecimal.valueOf(4)});
		Float128CartesianTensorProductMember y = new Float128CartesianTensorProductMember(2, 2, new BigDecimal[] {BigDecimal.valueOf(5),BigDecimal.valueOf(6),BigDecimal.valueOf(7),BigDecimal.valueOf(8)});
		Float128CartesianTensorProductMember z = new Float128CartesianTensorProductMember();

		G.QUAD_TEN.multiply().call(x, y, z);
		
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
		
		Float128Member tmp1 = G.QUAD.construct();
		Float128Member tmp2 = G.QUAD.construct();
		
		Float128CartesianTensorProductMember value1 = new Float128CartesianTensorProductMember(rank, dimension);
		Float128CartesianTensorProductMember value2 = new Float128CartesianTensorProductMember(rank, dimension);

		assertNotNull(G.QUAD_TEN.construct());
		Float128CartesianTensorProductMember junk1 = G.QUAD_TEN.construct("[1,2,3][4,5,6][7,8,9]");
		assertEquals(2, junk1.rank());
		assertEquals(3, junk1.dimension());
		junk1.v(1, tmp1);
		assertTrue(BigDecimalUtils.isNear(2, tmp1.v(), TOL));
		Float128CartesianTensorProductMember junk2 = G.QUAD_TEN.construct(junk1);
		assertTrue(G.QUAD_TEN.isEqual().call(junk1, junk2));
		assertFalse(G.QUAD_TEN.isNotEqual().call(junk1, junk2));
		
		assertTrue(G.QUAD_TEN.isZero().call(value1));
		G.QUAD_TEN.unity().call(value1);
		assertFalse(G.QUAD_TEN.isZero().call(value1));
		G.QUAD_TEN.zero().call(value1);
		assertTrue(G.QUAD_TEN.isZero().call(value1));

		tmp1.setV(BigDecimal.valueOf(44));
		G.QUAD_TEN.addScalar().call(tmp1, value1, value2);
		assertFalse(G.QUAD_TEN.isEqual().call(value1, value2));
		assertTrue(G.QUAD_TEN.isNotEqual().call(value1, value2));
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), TOL));
		
		tmp2.setV(BigDecimal.valueOf(4));
		G.QUAD_TEN.multiplyByScalar().call(tmp2, value2, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(44*4, tmp2.v(), TOL));

		tmp2.setV(BigDecimal.valueOf(4));
		G.QUAD_TEN.divideByScalar().call(tmp2, value2, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(44, tmp2.v(), TOL));

		G.QUAD_TEN.subtractScalar().call(tmp1, value2, value1);
		assertTrue(G.QUAD_TEN.isZero().call(value1));

		G.QUAD_TEN.assign().call(value2, value1);
		assertFalse(G.QUAD_TEN.isZero().call(value1));
		assertTrue(G.QUAD_TEN.isEqual().call(value1, value2));

		G.QUAD_TEN.unity().call(value1);
		value1.v(0, tmp1);
		assertTrue(BigDecimalUtils.isNear(1, tmp1.v(), TOL));
		G.QUAD_TEN.add().call(value1, value1, value2);
		value2.v(0, tmp1);
		assertTrue(BigDecimalUtils.isNear(2, tmp1.v(), TOL));

		G.QUAD_TEN.multiplyElements().call(value2, value2, value1);
		value1.v(0, tmp1);
		assertTrue(BigDecimalUtils.isNear(4, tmp1.v(), TOL));
		value1.v(1, tmp1);
		assertTrue(BigDecimalUtils.isNear(0, tmp1.v(), TOL));

		assertTrue(value1.numElems() > 0);
		assertTrue(value2.numElems() > 0);
		for (long i = 0; i < value1.numElems(); i++) {
			tmp1.setV(BigDecimal.valueOf(i));
			tmp2.setV(BigDecimal.valueOf(43));
			value1.setV(i, tmp1);
			value2.setV(i, tmp2);
		}
		G.QUAD_TEN.divideElements().call(value1, value2, value1);
		assertTrue(value1.numElems() > 0);
		assertTrue(value1.numElems() == value2.numElems());
		for (long i = 0; i < value1.numElems(); i++) {
			value1.v(i, tmp1);
			assertTrue(BigDecimalUtils.isNear(1.0*i/43, tmp1.v(), TOL));
		}

		G.QUAD_TEN.subtract().call(value1, value1, value2);
		assertTrue(G.QUAD_TEN.isZero().call(value2));
		
		value1 = new Float128CartesianTensorProductMember("[1,2][3,4]");
		assertEquals(2, value1.rank());
		assertEquals(2, value1.dimension());
		G.QUAD_TEN.multiply().call(value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			assertTrue(BigDecimalUtils.isNear(((i % 4)+1) * ((i / 4) + 1), tmp2.v(), BigDecimal.ZERO));
		}
		
		G.QUAD_TEN.unity().call(value1);
		assertFalse(G.QUAD_TEN.isNaN().call(value1));
		G.QUAD_TEN.nan().call(value1);
		assertTrue(G.QUAD_TEN.isNaN().call(value1));

		G.QUAD_TEN.unity().call(value1);
		assertFalse(G.QUAD_TEN.isInfinite().call(value1));
		G.QUAD_TEN.infinite().call(value1);
		assertTrue(G.QUAD_TEN.isInfinite().call(value1));

		value1 = new Float128CartesianTensorProductMember("[1,2][3,4]");
		G.QUAD_TEN.negate().call(value1, value2);
		assertFalse(G.QUAD_TEN.isEqual().call(value1, value2));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(-3, tmp2.v(), BigDecimal.ZERO));

		G.QUAD_TEN.commaDerivative().call(0, value1, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(1, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(2, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));

		G.QUAD_TEN.commaDerivative().call(1, value1, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(3, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(4, tmp2.v(), BigDecimal.ZERO));
		
		G.QUAD_TEN.semicolonDerivative().call(0, value1, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(1, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(2, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));

		G.QUAD_TEN.semicolonDerivative().call(1, value1, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(0, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(3, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(4, tmp2.v(), BigDecimal.ZERO));
		
		value1 = new Float128CartesianTensorProductMember("[1,2][3,4]");
		G.QUAD_TEN.norm().call(value1, tmp1);
		assertTrue(BigDecimalUtils.isNear(Math.sqrt(30), tmp1.v(), TOL));
		
		value1 = new Float128CartesianTensorProductMember("[1,2][3,4]");
		assertEquals(2, value1.rank());
		assertEquals(2, value1.dimension());
		G.QUAD_TEN.outerProduct().call(value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			assertTrue(BigDecimalUtils.isNear(((i % 4)+1) * ((i / 4) + 1), tmp2.v(), BigDecimal.ZERO));
		}

		try {
			G.QUAD_TEN.lowerIndex().call(0, value1, value2);
			assertTrue(true);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			G.QUAD_TEN.raiseIndex().call(0, value1, value2);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		value1 = new Float128CartesianTensorProductMember(3,2);
		assertEquals(3, value1.rank());
		assertEquals(2, value1.dimension());
		assertEquals(8, value1.numElems());
		for (int i = 0; i < 8; i++) {
			tmp1.setV(BigDecimal.valueOf(i+1));
			value1.setV(i, tmp1);
		}
		G.QUAD_TEN.contract().call(0, 1, value1, value2);
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(5, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(13, tmp2.v(), BigDecimal.ZERO));

		G.QUAD_TEN.innerProduct().call(0, 1, value1, value1, value2);
		assertEquals(4, value2.rank());
		assertEquals(2, value2.dimension());
		assertEquals(16, value2.numElems());
		for (int i = 0; i < 16; i++) {
			value2.v(i, tmp2);
			// TODO do something. Note that the impl does a simple approach that matches theory very closely. I think we can
			//   get by not testing this a lot yet.
			// System.out.println(tmp2.v());
			//assertTrue(BigDecimalUtils.isNear(some func val, tmp2.v(), BigDecimal.ZERO));
		}

		// tested in test1() above
		//G.QUAD_TEN.power();
		
		value1 = new Float128CartesianTensorProductMember(2,2);
		tmp1.setV(BigDecimal.valueOf(3));
		value1.setV(0, tmp1);
		tmp1.setV(BigDecimal.valueOf(6));
		value1.setV(1, tmp1);
		tmp1.setV(BigDecimal.valueOf(9));
		value1.setV(2, tmp1);
		tmp1.setV(BigDecimal.valueOf(12));
		value1.setV(3, tmp1);
		tmp2.setV(BigDecimal.valueOf(5));
		G.QUAD_TEN.scale().call(tmp2, value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(15, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(30, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(45, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(60, tmp2.v(), BigDecimal.ZERO));

		value1 = new Float128CartesianTensorProductMember(2,2);
		tmp1.setV(BigDecimal.valueOf(3));
		value1.setV(0, tmp1);
		tmp1.setV(BigDecimal.valueOf(6));
		value1.setV(1, tmp1);
		tmp1.setV(BigDecimal.valueOf(9));
		value1.setV(2, tmp1);
		tmp1.setV(BigDecimal.valueOf(12));
		value1.setV(3, tmp1);
		G.QUAD_TEN.scaleByDouble().call(5.0, value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(15, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(30, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(45, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(60, tmp2.v(), BigDecimal.ZERO));

		value1 = new Float128CartesianTensorProductMember(2,2);
		tmp1.setV(BigDecimal.valueOf(3));
		value1.setV(0, tmp1);
		tmp1.setV(BigDecimal.valueOf(6));
		value1.setV(1, tmp1);
		tmp1.setV(BigDecimal.valueOf(9));
		value1.setV(2, tmp1);
		tmp1.setV(BigDecimal.valueOf(12));
		value1.setV(3, tmp1);
		G.QUAD_TEN.scaleByHighPrec().call(new HighPrecisionMember(BigDecimal.valueOf(5.0)), value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(15, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(30, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(45, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(60, tmp2.v(), BigDecimal.ZERO));

		value1 = new Float128CartesianTensorProductMember(2,2);
		tmp1.setV(BigDecimal.valueOf(3));
		value1.setV(0, tmp1);
		tmp1.setV(BigDecimal.valueOf(6));
		value1.setV(1, tmp1);
		tmp1.setV(BigDecimal.valueOf(9));
		value1.setV(2, tmp1);
		tmp1.setV(BigDecimal.valueOf(12));
		value1.setV(3, tmp1);
		G.QUAD_TEN.scaleByRational().call(new RationalMember(100,20), value1, value2);
		assertEquals(value1.rank(), value2.rank());
		assertEquals(value1.dimension(), value2.dimension());
		value2.v(0, tmp2);
		assertTrue(BigDecimalUtils.isNear(15, tmp2.v(), BigDecimal.ZERO));
		value2.v(1, tmp2);
		assertTrue(BigDecimalUtils.isNear(30, tmp2.v(), BigDecimal.ZERO));
		value2.v(2, tmp2);
		assertTrue(BigDecimalUtils.isNear(45, tmp2.v(), BigDecimal.ZERO));
		value2.v(3, tmp2);
		assertTrue(BigDecimalUtils.isNear(60, tmp2.v(), BigDecimal.ZERO));

		value1 = new Float128CartesianTensorProductMember("[1.25,2][3,4]");
		value2 = new Float128CartesianTensorProductMember("[1,2][3,4]");
		tmp1.setV(BigDecimal.valueOf(0.24));
		assertFalse(G.QUAD_TEN.within().call(tmp1, value1, value2));
		tmp1.setV(BigDecimal.valueOf(0.26));
		assertTrue(G.QUAD_TEN.within().call(tmp1, value1, value2));
		tmp1.setV(BigDecimal.valueOf(1));
		G.QUAD_TEN.round().call(Mode.HALF_EVEN, tmp1, value1, value2);
		assertTrue(G.QUAD_TEN.isEqual().call(value1, value2));

		// test with complex numbers where it makes sense
		
		IntegerIndex index = new IntegerIndex(2);
		ComplexFloat128Member ctmp1 = G.CQUAD.construct();
		ComplexFloat128Member ctmp2 = G.CQUAD.construct();
		ComplexFloat128CartesianTensorProductMember cvalue1 = new ComplexFloat128CartesianTensorProductMember(2,2);
		index.set(0, 0);
		index.set(1, 0);
		ctmp1.setR(BigDecimal.valueOf(1));
		ctmp1.setI(BigDecimal.valueOf(2));
		cvalue1.setV(index, ctmp1);
		index.set(0, 1);
		index.set(1, 0);
		ctmp1.setR(BigDecimal.valueOf(3));
		ctmp1.setI(BigDecimal.valueOf(4));
		cvalue1.setV(index, ctmp1);
		index.set(0, 0);
		index.set(1, 1);
		ctmp1.setR(BigDecimal.valueOf(5));
		ctmp1.setI(BigDecimal.valueOf(6));
		cvalue1.setV(index, ctmp1);
		index.set(0, 1);
		index.set(1, 0);
		ctmp1.setR(BigDecimal.valueOf(7));
		ctmp1.setI(BigDecimal.valueOf(8));
		cvalue1.setV(index, ctmp1);
		ComplexFloat128CartesianTensorProductMember cvalue2 =
				new ComplexFloat128CartesianTensorProductMember();
		
		G.CQUAD_TEN.conjugate().call(cvalue1, cvalue2);
		assertEquals(cvalue1.rank(), cvalue2.rank());
		assertEquals(cvalue1.dimension(), cvalue2.dimension());
		index.set(0, 0);
		index.set(1, 0);
		cvalue1.getV(index, ctmp1);
		G.CQUAD.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		index.set(0, 1);
		index.set(1, 0);
		cvalue1.getV(index, ctmp1);
		G.CQUAD.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		index.set(0, 0);
		index.set(1, 1);
		cvalue1.getV(index, ctmp1);
		G.CQUAD.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		index.set(0, 1);
		index.set(1, 1);
		cvalue1.getV(index, ctmp1);
		G.CQUAD.conjugate().call(ctmp1, ctmp1);
		cvalue2.getV(index, ctmp2);
		ctmp1.getR(tmp1);
		ctmp2.getR(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));
		ctmp1.getI(tmp1);
		ctmp2.getI(tmp2);
		assertTrue(BigDecimalUtils.isNear(tmp1.v(), tmp2.v(), BigDecimal.ZERO));

		// a test to make sure rank 0 tensors can be accessed
		IntegerIndex idx = new IntegerIndex(0);
		value1 = new Float128CartesianTensorProductMember(0,3);
		value1.getV(idx, tmp1);
	}

	@Test
	public void test4() {
		
		Float128Member before = new Float128Member();
		
		assertTrue(G.QUAD.isZero().call(before));
		
		IndexedDataSource<Float128Member> array = Storage.allocate(G.QUAD.construct(), 1);
		
		Float128Member after = new Float128Member();
		
		assertTrue(G.QUAD.isZero().call(after));
		
		array.set(0, before);
		
		array.get(0, after);

		assertTrue(G.QUAD.isZero().call(after));
	}
}
