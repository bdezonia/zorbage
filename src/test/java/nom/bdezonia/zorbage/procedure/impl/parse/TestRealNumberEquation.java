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
package nom.bdezonia.zorbage.procedure.impl.parse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure;
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
				parser.parse(G.DBL, "1.4-2.6");
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

	@Test
	public void test5() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "$0+$1");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp,G.DBL.construct("7"),G.DBL.construct("9"));
		assertEquals(16, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test6() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "1.2e2-1.1");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(120-1.1, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test7() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "1--4");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(5, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test8() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "1-+4");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(-3, tmp.v(), 0.00000000000001);
	}

	@Test
	public void test9() {
		
		EquationParser<Float64Algebra,Float64Member> parser =
				new EquationParser<Float64Algebra,Float64Member>();
		Tuple2<String, Procedure<Float64Member>> result =
				parser.parse(G.DBL, "5-+1.4e-03");
		assertEquals(null, result.a());
		Float64Member tmp = G.DBL.construct();
		result.b().call(tmp);
		assertEquals(5-0.0014, tmp.v(), 0.00000000000001);
	}

	/* TODO BROKEN
	@Test
	public void test10() {
		
		EquationParser<SignedInt16Algebra,SignedInt16Member> parser =
				new EquationParser<SignedInt16Algebra,SignedInt16Member>();
		Tuple2<String, Procedure<SignedInt16Member>> result =
				parser.parse(G.INT16, "-32768");
		assertEquals(null, result.a());
		SignedInt16Member tmp = G.INT16.construct();
		result.b().call(tmp);
		assertEquals(-32768, tmp.v());
	}
	*/

	// Verify that I can parse a bunch of things by using a benchmark some other parsers are tested with.
	// from https://raw.githubusercontent.com/ArashPartow/math-parser-benchmark-project/master/bench_expr.txt 4/4/2020
	
	@Test
	public void monsterTest() {

		String[] eqns = {
				
			// trivial expressions and expressions eliminated by
			// constant folding
			"1",
			"$0",
			"2/abs(3*4/5)",
			"sin((1+2/2*3)*4^5)+cos(6*PI)",
	
			//
			// Variablen und Variablenzusammenfassung
			//
			// direkte Rckgabe von Variablen Zusammenfassung von Variablen
			// Alle gleichungen hier knnen auf eine Variable oder einen Term
			//  der Form a*x+b mit a,b konstant zurckgefhrt werden
			"$0+1",
			"$0*2",
			"2*$0+1",
			"(2*$0+1)*3",
	
			//
			// Polynome / Optimierung von Potenzfunktionen testen
			//
			// Polynomials with integer exponents
			"$0^2+1",
			"1+$0^2",
			"1.1*$0^2",
			"1.1*$0^2 + 2.2*$1^3",
			"1.1*$0^2 + 2.2*$1^3 + 3.3*$2^4",
	
			// Polynome mit floating point exponenten
			"1.1*$0^2.01",
			"1.1*$0^2.01 + 2.2*$1^3.01",
			"1.1*$0^2.01 + 2.2*$1^3.01 + 3.3*$2^4.01",
	
			//physikalisch relevante gleichungen (variablen sind willkrlich gesetzt)
			// gausskurve
			"1/($0*sqrt(2*PI))*E^(-0.5*(($1-$0)/$0)^2)",
	
			// hornerschema:
			"(((((((7*$0+6)*$0+5)*$0+4)*$0+3)*$0+2)*$0+1)*$0+0.1)",
			"7*$0^7+6*$0^6+5*$0^5+4*$0^4+3*$0^3+2*$0^2+1*$0^1+0.1",
	
			// distance calculation
			"sqrt($0^2+$1^2)",
	
			// Overhead of basic function calls
			"sin($0)",
			"sqrt($0)",
			"abs($0)",
	
			// Simple expressions without functions
			"$0+$1",
			"$0+$1-$2",
			"$0*$1*$2",
			"$0/$1/$2",
			"$0+$1*$2",
			"$0*$1+$2",
			"$0/(($0+$1)*($0-$1))/$1",
			"1-(($0*$1)+($0/$1))-3",
			"($0+$1)*3",
			"-($1^1.1)",
			"$0+$1*($0+$1)",
			"(1+$1)*(-3)",
			"$0+$1-E*PI/5^6",
			"$0^$1/E*PI-5+6",
			"$0*2+2*$1",
			"2*($0+$1)",
			"($0+$1)*2",
			"-$0+-$1",
			"-$0--$1",
			"-$0*-$1",
			"-$0/-$1",
			"-$0*$1",
			"-$0/$1",
			"$0*-$1",
			"$0/-$1",
			"$0+1-2/3",
			"1+$0-2/3",
			"(1+$0)-2/3",
			"1+($0-2/3)",
			"($0/(((($1+(((E*(((((PI*((((3.45*((PI+$0)+PI))+$1)+$1)*$0))+0.68)+E)+$0)/$0))+$0)+$1))+$1)*$0)-PI))",
			"(5.5+$0)+(2*$0-2/3*$1)*($0/3+$1/4)+($2+7.7)",
			"(((($0)-($1))+(($2)-($3)))/((($4)-($5))+(($6)-($0))))*(((($1)-($2))+(($3)-($4)))/((($5)-($6))+(($0)-($1))))",
			"((((2*$0)-($1*3))+((4*$2)-($3*5)))/((($4/6)-(7/$5))+(($6/8)-($0^2))))*(((($1^3)-($2/4))+((5/$3)-(6/$4)))/((($5/7)-(8/$6))+(($0)-($1))))",
	
			// expressions with functions
			"$0+(cos($1-sin(2/$0*PI))-sin($0-cos(2*$1/PI)))-$1",
			"sin($0)+sin($1)",
			"abs(sin(sqrt($0^2+$1^2))*255)",
			"-abs($0+1)*-sin(2-$1)",
			"sqrt($0)*sin(8)", // slightly modified by BDZ
			"(10+sqrt($0))*(sin(8)^2)", // slightly modified by BDZ
			"($1+$0/$1) * ($0-$1/$0)",
			"(0.1*$0+1)*$0+1.1-sin($0)-log($0)/$0*3/4",
			"sin(2 * $0) + cos(PI / $1)",
			"1 - sin(2 * $0) + cos(PI / $1)",
			"sqrt(1 - sin(2 * $0) + cos(PI / $1) / 3)",
			"($0^2 / sin(2 * PI / $1)) -$0 / 2",
			"1-($0/$1*0.5)",
			"E^log(7*$0)",
			"10^log(3+$1)",
			"(cos(2.41)/$1)",
			"-(sin(PI+$0)+1)",
			"$0-(E^(log(7+$1)))"
		};
		
		EquationParser<Float64Algebra,Float64Member> parser =
			new EquationParser<Float64Algebra,Float64Member>();

		String eqn = null;
		for (int i = 0; i < eqns.length; i++) {
			eqn = eqns[i];
			Tuple2<String, Procedure<Float64Member>> result = parser.parse(G.DBL, eqn);
			assertEquals(null, result.a());
		}
	}
}
