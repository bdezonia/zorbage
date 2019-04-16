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
package nom.bdezonia.zorbage.procedure.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.impl.parse.EquationParser;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRealNumberEquation {

	@Test
	public void test1() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "4.7315");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(4.7315, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test2() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "1.4 + 2.6");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(4, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test3() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "1.4 - 2.6");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(-1.2, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test4() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "sin(PI/4)");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(Math.sqrt(2)/2, tmp.v(), 0.00000000000001);
	}
}
