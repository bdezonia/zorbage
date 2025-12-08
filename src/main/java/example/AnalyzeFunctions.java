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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Derivative;
import nom.bdezonia.zorbage.algorithm.FindFixedPoint;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.NewtonRaphson;
import nom.bdezonia.zorbage.datasource.ProcedureDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Algebra;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Algebra;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * @author Barry DeZonia
 */
class AnalyzeFunctions {

	// This is an example showing some of the things you can do with functions and procedures.

	// A function takes input values and returns a computed value. A procedure is very similar.
	// It is a function that takes input values and rather than returning an output it places
	// its output value in the last inputs passed to the procedure. The procedure reserves
	// space for its outputs.

	void example1() {

		// Let's define a procedure that squares an input floating point number

		Procedure2<Float64Member, Float64Member> squarer =
				new Procedure2<Float64Member, Float64Member>()
				{
					public void call(Float64Member in, Float64Member out) {
						out.setV(in.v() * in.v());
					}
				};

		// Now let's exercise it

		Float64Member in = G.DBL.construct("4");

		Float64Member out = G.DBL.construct();

		squarer.call(in, out);

		System.out.println(out); // prints 16.0
	}

	void example2() {

		// Let's define a procedure that cubes an input integer

		Procedure2<Long, Float64Member> cuber =
				new Procedure2<Long, Float64Member>()
				{
					public void call(Long in, Float64Member out) {
						out.setV(in * in * in);
					}
				};

		// Let's treat this procedure as a source of data

		ProcedureDataSource<Float64Member> source = new ProcedureDataSource<Float64Member>(cuber);

		// And specify that we want to look at the 10 cubes starting at 5^3

		TrimmedDataSource<Float64Member> trimmed =
				new TrimmedDataSource<Float64Member>(source, 5, 10);

		// So no we can calc the mean of 5^3 + 6^3 + 7^3 + 8^3 + 9^3 + 10^3 + 11^3 + 12^3 + 13^3 + 14^3

		Float64Member mean = G.DBL.construct();

		Mean.compute(G.DBL, trimmed, mean);

		System.out.println(mean);
	}

	void example3() {

		// Let's define a procedure that computes the numbers on the line 4*x + 7

		Procedure2<Float64Member, Float64Member> line =
				new Procedure2<Float64Member, Float64Member>()
				{
					public void call(Float64Member in, Float64Member out) {
						out.setV(4 * in.v() + 7);
					}
				};

		// now let's calculate it's derivative at the point 14.0

		Float64Member point = new Float64Member(14.0);

		// compute the derivative from a point on the curve and a point 0.001 away

		Float64Member delta = new Float64Member(0.001);

		// set aside space for the result

		Float64Member result = new Float64Member();

		// setup the derivative calculator

		Derivative<Float64Algebra,Float64Member> deriv =
				new Derivative<Float64Algebra,Float64Member>(G.DBL, line, delta);

		// compute the derivative at the point

		deriv.call(point, result);

		System.out.println(result);  // prints 4.0
	}

	void example4() {

		// let's calculate a root of an equation near a point

		// define a quadratic formula as a procedure

		Procedure2<Float32Member,Float32Member> proc =
				new Procedure2<Float32Member, Float32Member>()
				{
					public void call(Float32Member in, Float32Member out) {
						// y = 2x^2 -13x + 3
						out.setV(2f*in.v()*in.v() - 13f*in.v() + 3f);
					}
				};

		// set the granularity of the search

		Float32Member delta = G.FLT.construct("0.1");

		// guess a point we think is near the root

		Float32Member guess = G.FLT.construct("1.5");

		// set aside space for the result

		Float32Member result = G.FLT.construct();

		// set up the most iterations we'll try

		long maxIters = 100;

		NewtonRaphson<Float32Algebra,Float32Member> nr =
				new NewtonRaphson<Float32Algebra,Float32Member>(G.FLT, proc, delta, maxIters);

		// do the search and respond

		if (nr.call(guess, result)) {
			System.out.println("root found at " + result.v());
		}
		else {
			System.out.println("no root found near " + guess.v() + " after " + maxIters + " iterations");
		}
	}

	void example5() {

		// let's find a fixed point of a procedure

		// we're going to look at the sine function that applies to Float64Members (doubles).

		Procedure2<Float64Member,Float64Member> sine = G.DBL.sin();

		// we define a tolerancing function that tells us what is closeEnough when evaluating roots

		Function2<Boolean,Float64Member,Float64Member> closeEnough =
				new Function2<Boolean, Float64Member, Float64Member>() {
					public Boolean call(Float64Member a, Float64Member b) {
						return Math.abs(a.v()-b.v()) < 0.000001;
					}
				};

		// guess a point we think is near the fixed point

		Float64Member guess = new Float64Member(0.1);

		// set aside space for the result

		Float64Member result = G.DBL.construct();

		// set up the most iterations we'll try

		long maxIters = 100;

		// we setup the solver

		FindFixedPoint<Float64Algebra,Float64Member> ffp =
				new FindFixedPoint<Float64Algebra,Float64Member>(G.DBL, sine, closeEnough, maxIters);

		// and we invoke it and react

		long foundIndex = ffp.call(guess, result);
		if (foundIndex < 0) {
			System.out.println("no root found near " + guess.v() + " after " + maxIters + " iterations");
		}
		else {
			System.out.println("fixed point found at " + result.v() + " after " + foundIndex + " iterations");
		}

		// if you didn't find it you can try again

		ffp.setMaxIters(10000);
		foundIndex = ffp.call(guess, result);
		// etc.
	}

	void example6() {

		// let's show that you can work with complex valued functions

		// Let's define a procedure in complex plane that computes y = (x + (1.4,3.2))^2

		Procedure2<ComplexFloat64Member, ComplexFloat64Member> func =

				new Procedure2<ComplexFloat64Member, ComplexFloat64Member>() {

					// define the constant that later we will use repeatedly

					private final ComplexFloat64Member constant = new ComplexFloat64Member(1.4, 3.2);

					// define the procedure's transformation code here

					public void call(ComplexFloat64Member in, ComplexFloat64Member out) {

						// G.CDBL is the Algebra that holds all the operations you can do with
						//   complex float 64 numbers.

						// allocate a temporary variable

						ComplexFloat64Member tmp = G.CDBL.construct();

						// add the constant to the input and store in tmp

						G.CDBL.add().call(in, constant, tmp);

						// multiply tmp by itself and store in out

						G.CDBL.multiply().call(tmp, tmp, out);

					}

				};

		// now let's calculate it's derivative at the point 5, -3

		ComplexFloat64Member point = new ComplexFloat64Member(5, -3);

		// compute the derivative from a point on the curve and a point (0.001,0.002) away

		ComplexFloat64Member delta = new ComplexFloat64Member(0.001, 0.002);

		// set aside space for the result

		ComplexFloat64Member result = new ComplexFloat64Member();

		// setup the derivative calculator

		Derivative<ComplexFloat64Algebra,ComplexFloat64Member> deriv =
				new Derivative<ComplexFloat64Algebra, ComplexFloat64Member>(G.CDBL, func, delta);

		// compute the derivative at the point

		deriv.call(point, result);

		System.out.println(result);
	}
}
