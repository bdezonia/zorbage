/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.universal;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.bigint.UnboundedIntMember;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64VectorMember;
import nom.bdezonia.zorbage.type.data.int8.UnsignedInt8Member;

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
		
	}
	
	@Test
	public void nD() {
		
	}
}
