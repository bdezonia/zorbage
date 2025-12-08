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
import nom.bdezonia.zorbage.algorithm.StableSort;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * @author Barry DeZonia
 */
class ProceduralProgramming {
	
	// Zorbage is designed to mix a few programming styles so that it is convenient
	// to compute what you need when you need it. Well known programming models
	// include object oriented programming, functional programming, and procedural
	// (also known as imperative) programming. In general, most numeric algorithms
	// are naturally defined as procedural or functional solutions. However
	// functional approaches can be significantly slower or use significantly more
	// resources. That is one reason why Zorbage prefers procedural code when
	// possible.
	
	// Zorbage thinks of procedural code as something similar to Pascal's approach.
	// Code can be organized in blocks of code called Procedures (or Functions).
	// Procedures and Functions can take inputs by reference. A Procedure or
	// Function can change a variable from a long distance if it desires. This
	// makes for efficient code in terms of speed and space. Java was designed with
	// some incomplete pass by reference capabilities (for instance, Java cannot
	// externally modify a single primitive value such as byte, short, int, long,
	// float, double, etc.). Zorbage can modify primitives externally.
	
	// An example to show how pass by reference works in Java and in Zorbage
	
	// typical Java
	
	void inc(int num) {
		num = num + 1;
	}
	
	void typicalJava() {
		int i = 7;
		inc(i);
		System.out.println(i);

		// Note here it prints 7 : external variable was not changed
	}
	
	// typical Zorbage
	
	Procedure1<SignedInt32Member> inc =
			new Procedure1<SignedInt32Member>()
	{
		@Override
		public void call(SignedInt32Member a) {
			// assign to a the successor of a
			G.INT32.succ().call(a, a);
		}
	};
	
	void typicalZorbage() {
		SignedInt32Member i = new SignedInt32Member(7);
		inc.call(i);
		System.out.println(i);

		// Note here it prints 8 : external variable was changed
	}

	// Zorbage users should follow a couple conventions when designing their own
	// Procedures. One convention is that in a call() definition for a Procedure
	// you want data to flow from left to right. Think that defining
	// proc.call(a,b,c) might be assigning c = a + b. Basically the methods you
	// design should have inputs on the left and outputs on the right. If you
	// desire you can treat a parameter as both an input and an output. One
	// example is sorting. You pass a list to the Sort code and the data is
	// changed. There is no 2nd list to contain the results or to get returned.
	// Another trick is that unlike Functions that return one variable, Procedures
	// can modify multiple output variables at one time.
	
	void example1() {
		
		// make a list
		
		IndexedDataSource<Float32Member> data = null;
		
		// elsewhere fill the data list
		
		// now sort it. notice that data is passed in and changed.
		
		StableSort.compute(G.FLT, data);
	}
	
	void example2() {
	
		// an example that shows multiple outputs
		
		Float64Member angle = new Float64Member(Math.PI / 2);
		
		Float64Member sin = G.DBL.construct();

		Float64Member cos = G.DBL.construct();

		G.DBL.sinAndCos().call(angle, sin, cos);
		
		// after this call sin = angle's sine value and cos = angle's cosine value
	}
	
	// One can imagine that this multiple output capability could come in handy
	// in a number of situations. It can reduce the number of computations you
	// make. For instance the sinAndCos() example above can be faster than
	// separately calling sin() and cos(). These two calcs use a lot of the
	// same intermediate data. A combined method can reuse information to speed
	// up the overall calculation. Another example where this feature might be
	// useful is the SummaryStats algorithm. It makes minimal passes over data
	// while calculating many statistics of interest.
	//   e.g.
	//
	//   SummaryStats.compute(list, min, q1, median, mean, q3, max);
	
	// Most of Zorbage's algorithms are static classes with a compute() method.
	// Procedures and Functions have a call() method. Algorithms and Procedures
	// and Functions are similarly designed and one can be used to wrap the
	// other when necessary. Zorbage chooses the static algorithm approach to
	// improve code readability. What Mean.compute() is doing is pretty easy
	// to understand.
	
	// Since Zorbage algorithms can modify their inputs, and one input can be
	// repeatedly used in a call the design of the procedure must follow
	// some rules.
	
	// One such example is some of the complex math code:
	
	@SuppressWarnings("unused")
	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> MULTIPLY =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
			
			// Current code:
			
			// for safety must use tmps
			double r = a.r()*b.r() - a.i()*b.i();
			double i = a.i()*b.r() + a.r()*b.i();
			c.setR( r );
			c.setI( i );

			// Code comment for this topic:
			
			// see here that C ends up being calculated from A and B
			// If we did not use temporary variables and simply did this instead:

			c.setR(a.r()*b.r() - a.i()*b.i());
			c.setI(a.i()*b.r() + a.r()*b.i());
			
			// then if we did proc.call(a,b,a); with "a" being an output target then the 1st
			// line would calc fine but the second line would fail because a's r-value had
			// changed on the previous line and would now be used in the second line's
			// calculation.
			
			// Anyhow the upshot is that procedure definitions have to consider that every
			// input might be a duplicate use of one of the other inputs. Procedures
			// need to copy data to local variables for calculations and assign outputs
			// from local variables when this is necessary so that variable overwrites don't
			// happen during partial computations. You usually do not need to worry about
			// this much in practice but you should be aware of the possibility your
			// Procedure definition might need to use this approach.
		}
	};
}
