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
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class FloatingTypes
{
	/*
	 * Currently Zorbage supports four different floating number types:
	 * 
	 *   16-bit floating point numbers (supported in software): the half type in the IEEE-754 standard
	 *   
	 *     Float16Member: approximately 3 decimal places of precision
	 * 
	 *   32-bit floating point numbers (supported in hardware): the float type in the IEEE-754 standard
	 *     
	 *     Float32Member: approximately 7 decimal places of precision
	 *   
	 *   64-bit floating point numbers (supported in hardware): the double type in the IEEE-754 standard
	 *     
	 *     Float64Member: approximately 16 decimal places of precision
	 *   
	 *   High precision floating point numbers (supported in software):
	 *     
	 *     HighPrecisionMember: 1 to 4000 decimal places of precision (user configurable)
	 *   
	 * With Zorbage you can calculate floating point values at any of these precisions. You can choose to trade
	 * off memory use versus speed versus accuracy as you see fit.
	 * 
	 */
	
	// Now let's define a method that can work at any precision
	
	<T extends nom.bdezonia.zorbage.algebra.Algebra<T,U> & RealConstants<U> & Multiplication<U>,
					U>
		U calcConstant(T algebra)
	{
		// construct some working variables on the stack
		U result = algebra.construct();
		U tmp = algebra.construct();
		U e = algebra.construct();
		U pi = algebra.construct();
		U gamma = algebra.construct();
		U phi = algebra.construct();
		
		// initialize the constants
		algebra.E().call(e);  // get the E constant
		algebra.PI().call(pi);  // get the PI constant
		algebra.PHI().call(phi);  // get the PHI constant
		algebra.GAMMA().call(gamma); // get the GAMMA constant
		
		// multiply them all together
		algebra.multiply().call(e, pi, tmp);
		algebra.multiply().call(tmp, phi, tmp);
		algebra.multiply().call(tmp, gamma, result);
		
		// return the result
		return result;
	}
	
	// Now I am going to show how you can use it
	
	void example1() {
		
		// Calculate e * pi * phi * gamma for four different accuracies using one algorithm
		
		// Let's push the high precision accuracy to a 100 decimal places. Ideally this method is called
		// once at your program's startup.
		
		HighPrecisionAlgebra.setPrecision(100);

		// Calculate the constant in 16 bit float precision
		
		Float16Member hlfVal = calcConstant(G.HLF);

		// Calculate the constant in 32 bit float precision
		
		Float32Member fltVal = calcConstant(G.FLT);
		
		// Calculate the constant in 64 bit float precision
		
		Float64Member dblVal = calcConstant(G.DBL);
		
		// Calculate the constant in 100 place float precision
		
		HighPrecisionMember hpVal = calcConstant(G.HP);
		
		// Now print out and compare the results
		
		System.out.println(hlfVal);
		System.out.println(fltVal);
		System.out.println(dblVal);
		System.out.println(hpVal);
	}
	
	/*
	 * The floating types support many basic operations you'd expect:
	 *   sin(), cos(), tan()
	 *   sinh(), cosh(), tanh()
	 *   exp(), log()
	 *   asin(), acos, atan()
	 *   asinh(), acosh(), atanh()
	 *   And many many more. The list of methods is comparable to floating point functions supported by other
	 *   programming languages.
	 */
	
	/*
	 * Calculating other things with floating point: note that each supported floating type represents Real
	 * values. Zorbage also supports composite values like Complex values, Quaternion values, and Octonion
	 * values. Each of these composite types of numbers use the above floating types to support multiple
	 * precision code for their implementations. Finally there are other composite types (Vectors, Matrices,
	 * and Tensors). Again the basic Real floating types are used to calculate operations on Vectors, Matrices,
	 * and Tensors made up of Real, Complex, Quaternion, and Octonion values. All at the various precisions you
	 * choose. You can read more about these composite types and how to work with them in their own example
	 * descriptions in this same directory.
	 */
}
