/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.procedure.impl.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Algebra;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Matrix;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Algebra;
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestParseLanguageExamples {

	// Some examples: taken from EQUATION_LANGUAGE docs in root of zorbage directory
	 
	@Test
	public void test1() {

		/*

		   4 * $0 + 7
		   
		     Scale input[0] by 4 and add 7
		 */
		
		SignedInt32Member value = G.INT32.construct();
		SignedInt32Member input = G.INT32.construct();
		EquationParser<SignedInt32Algebra, SignedInt32Member> parser = new EquationParser<>();
		Tuple2<String,Procedure<SignedInt32Member>> result = parser.parse(G.INT32, "4 * $0 + 7");
		assertNull(result.a());
		
		input.setV(0);
		result.b().call(value, input);
		assertEquals(7, value.v());
		
		input.setV(1);
		result.b().call(value, input);
		assertEquals(11, value.v());
		
		input.setV(2);
		result.b().call(value, input);
		assertEquals(15, value.v());
		
		input.setV(3);
		result.b().call(value, input);
		assertEquals(19, value.v());
		
		input.setV(-1);
		result.b().call(value, input);
		assertEquals(3, value.v());
	}
	
	@Test
	public void test2() {

		/*
		   {1,2}^{3,4}
		   
		     Raise complex number 1+2i to the 3+4i power
		 */

		ComplexFloat64Member value = G.CDBL.construct();
		EquationParser<ComplexFloat64Algebra, ComplexFloat64Member> parser = new EquationParser<>();
		Tuple2<String,Procedure<ComplexFloat64Member>> result = parser.parse(G.CDBL, "{1,2}^{3,4}");
		assertNull(result.a());
		
		result.b().call(value);
		
		ComplexFloat64Member a = G.CDBL.construct("{1,2}");
		ComplexFloat64Member b = G.CDBL.construct("{3,4}");
		ComplexFloat64Member expected = G.CDBL.construct();
		G.CDBL.pow().call(a, b, expected);
		
		assertTrue(G.CDBL.isEqual().call(expected, value));
	}
	
	@Test
	public void test3() {

		/*
		   (tmin + tmax) / 2
		   
		     Produce a number at the midpt of a range. 0 for signed numbers. max/2 for unsigned numbers.
		 */
		
		SignedInt16Member value = G.INT16.construct();
		EquationParser<SignedInt16Algebra, SignedInt16Member> parser = new EquationParser<>();
		Tuple2<String,Procedure<SignedInt16Member>> result = parser.parse(G.INT16, "(tmin + tmax) / 2");
		assertNull(result.a());

		result.b().call(value);
		
		assertEquals(0, value.v());
	}
	
	@Test
	public void test4() {

		/*
		   (tmin + tmax) / 2
		   
		     Produce a number at the midpt of a range. 0 for signed numbers. max/2 for unsigned numbers.
		 */
		
		UnsignedInt16Member value = G.UINT16.construct();
		EquationParser<UnsignedInt16Algebra, UnsignedInt16Member> parser = new EquationParser<>();
		Tuple2<String,Procedure<UnsignedInt16Member>> result = parser.parse(G.UINT16, "(tmin + tmax) / 2");
		assertNull(result.a());

		result.b().call(value);
		
		assertEquals(65535/2, value.v());
	}
	
	@Test
	public void test5() {

		/*
		   (2 * sin($0)) + (3 * cos($1))
		   
		     Add two times the sine of input[0] with three times the cosine of
		     input[1]. The inputs can be numbers or matrices.
		 */

		Float64Member value = G.DBL.construct();
		Float64Member input1 = G.DBL.construct();
		Float64Member input2 = G.DBL.construct();
		EquationParser<Float64Algebra, Float64Member> parser = new EquationParser<>();
		Tuple2<String,Procedure<Float64Member>> result = parser.parse(G.DBL, "(2 * sin($0)) + (3 * cos($1))");
		assertNull(result.a());

		input1.setV(0.45);
		input2.setV(1.1);
		result.b().call(value, input1, input2);
		
		assertEquals(2*Math.sin(input1.v()) + 3*Math.cos(input2.v()), value.v(), 0.000000000000001);
	}
	
	@Test
	public void test6() {

		/*
		   exp( [[1,2][3,4]] )
		   
		     Take the exponential of the specified 2x2 matrix. The result is a
		     matrix too.
		 */

		Float64MatrixMember value = G.DBL_MAT.construct();
		EquationParser<Float64Matrix, Float64MatrixMember> parser = new EquationParser<>();
		Tuple2<String,Procedure<Float64MatrixMember>> result = parser.parse(G.DBL_MAT, "exp( [[1,2][3,4]] )");
		assertNull(result.a());

		result.b().call(value);
		
		Float64MatrixMember a = G.DBL_MAT.construct("[[1,2][3,4]]");
		Float64MatrixMember expected = G.DBL_MAT.construct();
		G.DBL_MAT.exp().call(a, expected);
		assertTrue(G.DBL_MAT.isEqual().call(expected, value));
	}
	
	@Test
	public void test7() {

		/*
		   PI + rand
		   
		     Return a random number between PI and PI+1.0
		
		 */

		Float64Member value = G.DBL.construct();
		EquationParser<Float64Algebra, Float64Member> parser = new EquationParser<>();
		Tuple2<String,Procedure<Float64Member>> result = parser.parse(G.DBL, "PI + rand");
		assertNull(result.a());

		Float64Member pi = G.DBL.construct();
		G.DBL.PI().call(pi);
		Float64Member one = G.DBL.construct();
		G.DBL.unity().call(one);
		Float64Member pi_plus_one = G.DBL.construct();
		G.DBL.add().call(pi, one, pi_plus_one);

		int extremalValueCount = 0;
				
		int numIters = 100;
		for (int i = 0; i < numIters; i++) {

			result.b().call(value);
			
			assertTrue(G.DBL.isGreaterEqual().call(value, pi));
			assertTrue(G.DBL.isLess().call(value, pi_plus_one));
			
			if (G.DBL.isEqual().call(value, pi) || G.DBL.isEqual().call(value, pi_plus_one))
				extremalValueCount++;
		}
		
		if (extremalValueCount > numIters/10)
			fail("computed sequence does not seem random");
	}
}
