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
package nom.bdezonia.zorbage.type.universal;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.float64.real.Float64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64VectorMember;
import nom.bdezonia.zorbage.type.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestPrimitiveConversions {

	@Test
	public void zeroD() {
		IntegerIndex tmp1 = new IntegerIndex(0);
		IntegerIndex tmp2 = new IntegerIndex(0);
		IntegerIndex tmp3 = new IntegerIndex(0);

		UnsignedInt8Member uint8 = new UnsignedInt8Member();
		Float64Member dbl = new Float64Member();
		
		uint8.setV(222);
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, uint8, dbl);
		assertEquals(222, dbl.v(), 0);
		
		dbl.setV(14.7);
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, dbl, uint8);
		assertEquals(14, uint8.v());
	}
	
	@Test
	public void oneD() {
		IntegerIndex tmp1 = new IntegerIndex(1);
		IntegerIndex tmp2 = new IntegerIndex(1);
		IntegerIndex tmp3 = new IntegerIndex(1);
		
		Float64VectorMember fvec = new Float64VectorMember(new double[] {-7,12,Math.PI});
		ComplexFloat64VectorMember cvec = new ComplexFloat64VectorMember(new double[] {-1,-1,-1,-1,-1,-1});
		
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, fvec, cvec);
		ComplexFloat64Member ctmp = new ComplexFloat64Member();
		assertEquals(3, cvec.length());
		cvec.v(0, ctmp);
		assertEquals(-7, ctmp.r(), 0);
		assertEquals(0, ctmp.i(), 0);
		cvec.v(1, ctmp);
		assertEquals(12, ctmp.r(), 0);
		assertEquals(0, ctmp.i(), 0);
		cvec.v(2, ctmp);
		assertEquals(Math.PI, ctmp.r(), 0);
		assertEquals(0, ctmp.i(), 0);
		
		UnboundedIntMember bigint = new UnboundedIntMember();
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, fvec, bigint);
		assertEquals(BigInteger.valueOf(-7), bigint.v());
		
		Float64VectorMember fvec2 = new Float64VectorMember(new double[] {55,44,33,22,11,-11});
		Float64Member ftmp = new Float64Member();
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, fvec, fvec2);
		assertEquals(3, fvec.length());
		assertEquals(6, fvec2.length());
		fvec2.v(0, ftmp);
		assertEquals(-7,ftmp.v(),0);
		fvec2.v(1, ftmp);
		assertEquals(12,ftmp.v(),0);
		fvec2.v(2, ftmp);
		assertEquals(Math.PI,ftmp.v(),0);
		fvec2.v(3, ftmp);
		assertEquals(0,ftmp.v(),0);
		fvec2.v(4, ftmp);
		assertEquals(0,ftmp.v(),0);
		fvec2.v(5, ftmp);
		assertEquals(0,ftmp.v(),0);

		fvec2 = new Float64VectorMember(new double[] {101,102,103,104,105});
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, fvec2, fvec);
		assertEquals(3, fvec.length());
		assertEquals(5, fvec2.length());
		fvec.v(0, ftmp);
		assertEquals(101,ftmp.v(),0);
		fvec.v(1, ftmp);
		assertEquals(102,ftmp.v(),0);
		fvec.v(2, ftmp);
		assertEquals(103,ftmp.v(),0);
	}
	
	@Test
	public void twoD() {
		
		IntegerIndex tmp1 = new IntegerIndex(2);
		IntegerIndex tmp2 = new IntegerIndex(2);
		IntegerIndex tmp3 = new IntegerIndex(2);

		Float64Member tmp = new Float64Member();

		Float64MatrixMember m = new Float64MatrixMember(2, 4, new double[] {1,2,3,4,5,6,7,8});

		UnboundedIntMember t = new UnboundedIntMember(BigInteger.TEN);
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, m, t);
		assertEquals(BigInteger.ONE, t.v());
		
		Float64VectorMember v = new Float64VectorMember(new double[] {9,9,9});
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, m, v);
		assertEquals(3, v.length());
		v.v(0, tmp);
		assertEquals(1, tmp.v(), 0);
		v.v(1, tmp);
		assertEquals(2, tmp.v(), 0);
		v.v(2, tmp);
		assertEquals(3, tmp.v(), 0);

		v = new Float64VectorMember(new double[] {9,9,9,9,9});

		PrimitiveConverter.convert(tmp1, tmp2, tmp3, m, v);
		assertEquals(5, v.length());
		v.v(0, tmp);
		assertEquals(1, tmp.v(), 0);
		v.v(1, tmp);
		assertEquals(2, tmp.v(), 0);
		v.v(2, tmp);
		assertEquals(3, tmp.v(), 0);
		v.v(3, tmp);
		assertEquals(4, tmp.v(), 0);
		v.v(4, tmp);
		assertEquals(0, tmp.v(), 0);

		Float64MatrixMember m2 = new Float64MatrixMember(3, 3, new double[] {99,99,99,99,99,99,99,99,99});
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, m, m2);
		assertEquals(3, m2.rows());
		assertEquals(3, m2.cols());
		m2.v(0, 0, tmp);
		assertEquals(1,tmp.v(),0);
		m2.v(0, 1, tmp);
		assertEquals(2,tmp.v(),0);
		m2.v(0, 2, tmp);
		assertEquals(3,tmp.v(),0);
		m2.v(1, 0, tmp);
		assertEquals(5,tmp.v(),0);
		m2.v(1, 1, tmp);
		assertEquals(6,tmp.v(),0);
		m2.v(1, 2, tmp);
		assertEquals(7,tmp.v(),0);
		m2.v(2, 0, tmp);
		assertEquals(0,tmp.v(),0);
		m2.v(2, 1, tmp);
		assertEquals(0,tmp.v(),0);
		m2.v(2, 2, tmp);
		assertEquals(0,tmp.v(),0);
	}
	
	@Test
	public void nD() {

		IntegerIndex tmp1 = new IntegerIndex(3);
		IntegerIndex tmp2 = new IntegerIndex(3);
		IntegerIndex tmp3 = new IntegerIndex(3);

		double[] vals = new double[]{1,2,3,4,5,6,7,8};
		
		Float64CartesianTensorProductMember t =
				new Float64CartesianTensorProductMember(3,2,vals);

		Float64Member tmp = new Float64Member();
		
		UnsignedInt8Member uint8 = new UnsignedInt8Member();
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, t, uint8);
		assertEquals(1, uint8.v());
		
		Float64VectorMember v = new Float64VectorMember(StorageConstruction.MEM_ARRAY, 5);
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, t, v);
		assertEquals(5, v.length());
		v.v(0, tmp);
		assertEquals(1, tmp.v(), 0);
		v.v(1, tmp);
		assertEquals(2, tmp.v(), 0);
		v.v(2, tmp);
		assertEquals(0, tmp.v(), 0);
		v.v(3, tmp);
		assertEquals(0, tmp.v(), 0);
		v.v(4, tmp);
		assertEquals(0, tmp.v(), 0);

		Float64MatrixMember m = new Float64MatrixMember(StorageConstruction.MEM_ARRAY, 3, 2);
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, t, m);
		assertEquals(2, m.rows());
		assertEquals(3, m.cols());
		m.v(0, 0, tmp);
		assertEquals(1, tmp.v(), 0);
		m.v(0, 1, tmp);
		assertEquals(2, tmp.v(), 0);
		m.v(0, 2, tmp);
		assertEquals(0, tmp.v(), 0);
		m.v(1, 0, tmp);
		assertEquals(3, tmp.v(), 0);
		m.v(1, 1, tmp);
		assertEquals(4, tmp.v(), 0);
		m.v(1, 2, tmp);
		assertEquals(0, tmp.v(), 0);
		
		tmp1 = new IntegerIndex(4);
		tmp2 = new IntegerIndex(4);
		tmp3 = new IntegerIndex(4);
		
		Float64CartesianTensorProductMember t2 = new Float64CartesianTensorProductMember(4,3,
				new double[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,
								36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,
								67,68,69,70,71,72,73,74,75,76,77,78,79,80,81});
		
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, t, t2);
		
		assertEquals(4, t2.numDimensions());
		assertEquals(3, t2.dimension(0));
		assertEquals(3, t2.dimension(1));
		assertEquals(3, t2.dimension(2));
		assertEquals(3, t2.dimension(3));
		
		IntegerIndex index = new IntegerIndex(4);

		index.set(0,0);
		index.set(1,0);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(1, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(2, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(3, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(4, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,0);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);
		
		index.set(0,0);
		index.set(1,0);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(5, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(6, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(7, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(8, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,1);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,0);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,2);
		index.set(3,0);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,0);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);
		
		index.set(0,1);
		index.set(1,0);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,0);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);
		
		index.set(0,0);
		index.set(1,0);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,1);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,0);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,2);
		index.set(3,1);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,0);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);
		
		index.set(0,1);
		index.set(1,0);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,0);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);
		
		index.set(0,0);
		index.set(1,0);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,1);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,0);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,0);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,0);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,1);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,1);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,1);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,0);
		index.set(1,2);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,1);
		index.set(1,2);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);

		index.set(0,2);
		index.set(1,2);
		index.set(2,2);
		index.set(3,2);
		
		t2.v(index, tmp);
		assertEquals(0, tmp.v(), 0);
		
		Float64CartesianTensorProductMember t3 = new Float64CartesianTensorProductMember(2,1, new double[] {77});
		
		tmp1 = new IntegerIndex(3);
		tmp2 = new IntegerIndex(3);
		tmp3 = new IntegerIndex(3);
		
		PrimitiveConverter.convert(tmp1, tmp2, tmp3, t, t3);
		
		assertEquals(2, t3.numDimensions());
		assertEquals(1, t3.dimension(0));
		assertEquals(1, t3.dimension(1));
		
		index = new IntegerIndex(2);

		index.set(0,0);
		index.set(1,0);

		t3.v(index, tmp);
		assertEquals(1, tmp.v(), 0);
		
	}
}
