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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.axis.StringDefinedAxisEquation;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.impl.parse.EquationParser;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class EquationParsing {

	/*
	 * Zorbage has an EquationParser class. You can specify equations with
	 * strings and get back a function that you can use to compute values
	 * from. You plug optional inputs into it and consume the output values
	 * as you desire.
	 * 
	 * The class is defined here:
	 * 
	 *   https://github.com/bdezonia/zorbage/blob/master/src/main/java/nom/bdezonia/zorbage/procedure/impl/parse/EquationParser.java
	 * 
	 * The supported language is detailed here:
	 *   
	 *   https://github.com/bdezonia/zorbage/blob/master/EQUATION_LANGUAGE
	 * 
	 * One of the powers of the EquationParser class is that it can return values
	 * that are numbers or vectors or matrices or tensors. The components of these
	 * can be reals or complex numbers or quaternions or octoniions. What you get
	 * back from the EquationParser depends upon which algebra you feed it. If
	 * a complex vector algebra then you get back complex vector values from the
	 * values you specify in the string.
	 */
	
	/*
	 * You can use the equation parser as you see fit. There are two common usages
	 * found in Zorbage right now which are detailed next.
	 */
	
	// Here is an example of filling a dataset with computed values

	void example1() {
		
		long[] dims = new long[] {50, 50};
		
		Float64Member value = G.DBL.construct();
		
		Float64Member valueX = G.DBL.construct();
		
		Float64Member valueY = G.DBL.construct();

		DimensionedDataSource<Float64Member> data =
				DimensionedStorage.allocate(G.DBL.construct(), dims);
		
		EquationParser<Float64Algebra, Float64Member> parser = new EquationParser<>();
		
		// set every pixel of a data source to 57.3
		
		Tuple2<String,Procedure<Float64Member>> result = parser.parse(G.DBL, "57.3");
		
		if (result.a() == null)
			System.out.println("parse error: "+result.a());
		else {
			result.b().call(value);
			Fill.compute(G.DBL, value, data.rawData());
		}
		
		// set every pixels to 17.0 * y + x where x and y are passed in arguments
		
		result = parser.parse(G.DBL, "17.0 * $1 + %0");
		
		if (result.a() == null)
			System.out.println("parse error: "+result.a());
		else {
			IntegerIndex idx = new IntegerIndex(dims.length);
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(dims);
			while (iter.hasNext()) {
				iter.next(idx);
				valueX.setV(idx.get(0));
				valueY.setV(idx.get(1));
				result.b().call(value, valueX, valueY);
				data.set(idx, value);
			}
		}
		
	}
	
	// Here is another common use case: pulling calibrated values out of
	// a DimensionedDataSource. The DimensionedDataSource has a set of axis
	// equations which tells how to interpret each equally spaced point.
	// Each axis has a scaling equation (that just defaults to 1 normally).
	// One can specify the axis equation using the parser (indirectly) to
	// calibrate with.
	
	void example2() {
		
		StringDefinedAxisEquation a = 
				new StringDefinedAxisEquation(
					"56.30417 * $0 + 13.201"  // a linear scaling
				);

		StringDefinedAxisEquation b = 
				new StringDefinedAxisEquation(
						"12.4367 * log($0) + 0.003"  // a log scaling
				);
		
		HighPrecisionMember calibratedValueX = G.HP.construct();
		
		HighPrecisionMember calibratedValueY = G.HP.construct();
		
		// we're going to look at the calibrated values for point (8, 14)
		
		a.call(8L, calibratedValueX);  // calibrated value for x = 8
		
		b.call(14L, calibratedValueY);  // calibrated value for y = 14
	}
}

// TODO ???
//
// just some examples of valid functions that EquationParser can be define by strings
//
// Maybe also explain the universal string representation
//
// explain the coord system of the string rep too? like how matrices are filled, etc.
