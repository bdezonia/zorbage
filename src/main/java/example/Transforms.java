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
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
import nom.bdezonia.zorbage.algorithm.FixedTransform2b;
import nom.bdezonia.zorbage.algorithm.Map;
import nom.bdezonia.zorbage.algorithm.ParallelTransform4;
import nom.bdezonia.zorbage.algorithm.Reduce;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.storage.sparse.SparseStorage;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.integer.int6.SignedInt6Member;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * @author Barry DeZonia
 */
class Transforms {

	// Zorbage has a handful of transformation classes that sets destination data to
	// arbitrary combinations of input data.
	
	// One thing to be on the lookout for: many transforms can take multiple lists
	// of different data types and combine them into results. The combination is
	// done by a Procedure defined by you and the sky is the limit here. Below are
	// a couple such examples. Another nice part of this different data types design
	// is that user defined types beyond what Zorbage provides can use this api.
	
	// Basic transforms: Transform1, Trandform2, Transform3, Transform4
	//
	//   y = f(a,b,...)
	//
	//   Example with lists of the same type
	
	void example1() {
		
		// make a list of 1000 half precision numbers
		
		IndexedDataSource<Float16Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.HLF.construct(), 1000);
		
		// set them to random values
		
		Fill.compute(G.HLF, G.HLF.random(), a);
		
		// make another list of the same size
		
		IndexedDataSource<Float16Member> b =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.HLF.construct(), a.size());
		
		// set all of them to the mathematical constant E
		
		Fill.compute(G.HLF, G.HLF.E(), b);
		
		// allocate space for the results
		
		IndexedDataSource<Float16Member> results =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.HLF.construct(), a.size());
		
		// set the results to the elementwise multiplication of a and b
		
		Transform3.compute(G.HLF, G.HLF.multiply(), a, b, results);
	}
	
	// Basic transforms: Transform1, Trandform2, Transform3, Transform4
	//
	//   Example with lists of different types
	
	void example2() {

		// create a list of complex numbers
		
		IndexedDataSource<ComplexFloat16Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.CHLF.construct(), 1000);

		// fill it with random values
		
		Fill.compute(G.CHLF, G.CHLF.random(), a);
		
		// create a similarly sized list of real values
		
		IndexedDataSource<Float16Member> results =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.HLF.construct(), a.size());
		
		// copy the real values of the inputs to the results
		
		Procedure2<ComplexFloat16Member, Float16Member> grabReals =
				new Procedure2<ComplexFloat16Member, Float16Member>()
		{
			@Override
			public void call(ComplexFloat16Member a, Float16Member b) {
				a.getR(b);
			}
		};

		Transform2.compute(G.CHLF, G.HLF, grabReals, a, results);

		// or
		
		// copy the imaginary values of the inputs to the results

		Procedure2<ComplexFloat16Member, Float16Member> grabImags =
				new Procedure2<ComplexFloat16Member, Float16Member>()
		{
			@Override
			public void call(ComplexFloat16Member a, Float16Member b) {
				a.getI(b);
			}
		};

		Transform2.compute(G.CHLF, G.HLF, grabImags, a, results);
		
	}
	
	// Parallel transforms: ParallelTransform1, ParallelTransform2, ParallelTransform3, etc.
	//
	//   Use multiple threads to transform data quickly
	
	void example3() {

		// allocate a list
		
		IndexedDataSource<Float64Member> a =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), 250000);
		
		// set all values in it to the mathematical constant PI
		
		Fill.compute(G.DBL, G.DBL.PI(), a);
		
		// allocate a second list
		
		IndexedDataSource<Float64Member> b =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), a.size());
		
		// set all values in it to the mathematical constant E
		
		Fill.compute(G.DBL, G.DBL.E(), b);
		
		// allocate a third list
		
		IndexedDataSource<Float64Member> c =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), a.size());
		
		// set all the values in it to random numbers between 0 and 1
		
		Fill.compute(G.DBL, G.DBL.random(), c);

		// allocate a list to hold the results
		
		IndexedDataSource<Float64Member> results =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), a.size());
	
		// define a procedure that combines 3 data elements in a nonlinear fashion
		//
		//   d = 3.0*a^3 - 1.5*b^2 - 5.0*c + 17.0
		
		Procedure4<Float64Member,Float64Member,Float64Member,Float64Member> proc =
				new Procedure4<Float64Member, Float64Member, Float64Member, Float64Member>()
		{
			@Override
			public void call(Float64Member a, Float64Member b, Float64Member c, Float64Member d) {
				double value = 3.0*a.v()*a.v()*a.v() - 1.5*b.v()*b.v() - 5.0*c.v() + 17;
				d.setV(value);
			}
		};
		
		// combine the data into results using as many threads as can be allocated to the app
		
		ParallelTransform4.compute(G.DBL, proc, a, b, c, results);
	}
	
	// Fixed transforms
	//
	//   Sometimes you want to transform data by passing in a list and a single value that
	//   can be thought of as a parameter. The parameter is combined with every element in
	//   the input data list and populates the output list. Zorbage has a couple of these:
	//   FixedTransform2a and FixedTransform2b. They differ only in whether the passed value
	//   is considered the left input argument or the right input argument. For most cases
	//   it does not make a difference which you choose. But imagine you want to subtract
	//   a value from a list of values. In this case it is easiest to treat the value as the
	//   second parameter and pass the already defined subtract() method of the appropriate
	//   algebra.
	
	void example5() {
		
		// create a list
		
		IndexedDataSource<Float32Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.FLT.construct(), new float[27000]);

		// elsewhere : fill it with values
		
		// create a results list
		
		IndexedDataSource<Float32Member> results =
				ArrayStorage.allocate(G.FLT.construct(), data.size());

		// set results to the scaling by 4.5 of the input data
		
		// 2a
		
		FixedTransform2a.compute(G.FLT, new Float32Member(4.5f), G.FLT.multiply(), data, results);
		
		// 2b: exact same results with this approach
		
		FixedTransform2b.compute(G.FLT, new Float32Member(4.5f), G.FLT.multiply(), data, results);
		
		// now here is a case where the order matters: subtract 4.5

		// 2a : result = 4.5 - input
		
		FixedTransform2a.compute(G.FLT, new Float32Member(4.5f), G.FLT.subtract(), data, results);

		// 2b : result = input - 4.5
		
		FixedTransform2b.compute(G.FLT, new Float32Member(4.5f), G.FLT.subtract(), data, results);
		
	}
	
	// Map
	//
	//   The Map algorithm is not exactly a Transform but it is similar enough that
	//   explaining it here makes sense. A map operation is common to many functional
	//   programming languages. You take an input list and create an output list
	//   by applying a transformation from the inputs to the outputs. Zorbage allows
	//   you to define transformations easily and in quite an open ended fashion.

	@SuppressWarnings("unused")
	void example6() {
		
		// define some input data
		
		IndexedDataSource<SignedInt6Member> input = SparseStorage.allocate(G.INT6.construct(), 1000);
		
		// elsewhere fill it with data
		
		// define a procedure that transforms INT6 data to FLT data
		
		Procedure2<SignedInt6Member, Float32Member> proc =
				new Procedure2<SignedInt6Member, Float32Member>()
		{
			@Override
			public void call(SignedInt6Member a, Float32Member b) {

				b.setV(3f * a.v()); // scale by 3: not the most realistic example
			}
		};
		
		// get a resulting list which is the application of the procedure to every
		// element of the input list
		
		IndexedDataSource<Float32Member> result = Map.compute(G.INT6, G.FLT, proc, input);
	}
	
	// Reduce
	//
	//   The Reduce algorithm is also not exactly a Transform. But it works hand in hand with
	//   Map and it does transform a list of values to a single value. This is another algo
	//   that is common in functional programming.
	
	void example7() {
		
		// define some input data
		
		IndexedDataSource<SignedInt16Member> input =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.INT16.construct(), 
				new short[] {1,3,5,8,12,55,101}
				);
		
		SignedInt16Member reduction = G.INT16.construct();

		// reduce by summing values
		
		Reduce.compute(G.INT16, G.INT16.add(), input, reduction);  // reduction = 1+3+5+8+12+55+101
		
		// reduce by multiplying values
		
		Reduce.compute(G.INT16, G.INT16.multiply(), input, reduction);  // reduction = 1*3*5*8*12*55*101

		// Let's do a compound interest problem by defining our own code. Imagine money compounds at an
		// 8% annual interest rate. And we are going to deposit funds once every month and compound
		// monthly. How much money will we have at the end?
		
		IndexedDataSource<Float64Member> monthlyDeposits =
				nom.bdezonia.zorbage.storage.Storage.allocate(
						G.DBL.construct(), 
						new double[] {1000,1250,500,300,0,475,950,200}
				);
		
		Procedure3<Float64Member, Float64Member, Float64Member> grow =
				new Procedure3<Float64Member, Float64Member, Float64Member>()
		{
			@Override
			public void call(Float64Member a, Float64Member b, Float64Member c) {
				// calc the growth
				double newValue = (1 + 0.08/12)*a.v() + b.v();
				// now round to nearest cent
				newValue = Math.round(newValue * 100);
				newValue = newValue / 100;
				// and set output value
				c.setV(newValue);
			}
		};
		
		Float64Member balance = G.DBL.construct();
		
		Reduce.compute(G.DBL, grow, monthlyDeposits, balance);
	}
}
