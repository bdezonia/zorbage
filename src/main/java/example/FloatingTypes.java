/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;

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
	
	// Here are the supported methods for float16s
	
	@SuppressWarnings("unused")
	void example2() {
		
		Float16Member a = G.HLF.construct();
		Float16Member b = G.HLF.construct("53.777");
		Float16Member c = G.HLF.construct(b);
		Float16Member d = new Float16Member(101.321f);
		
		G.HLF.maxBound();
		G.HLF.minBound();

		G.HLF.compare();
		G.HLF.signum();
		G.HLF.isEqual();
		G.HLF.isNotEqual();
		G.HLF.isGreater();
		G.HLF.isGreaterEqual();
		G.HLF.isLess();
		G.HLF.isLessEqual();
		G.HLF.isNaN();
		G.HLF.isInfinite();
		G.HLF.isUnity();
		G.HLF.isZero();
		G.HLF.max();
		G.HLF.min();
		
		G.HLF.acos();
		G.HLF.acosh();
		G.HLF.acot();
		G.HLF.acoth();
		G.HLF.acsc();
		G.HLF.acsch();
		G.HLF.asec();
		G.HLF.asech();
		G.HLF.asin();
		G.HLF.asinh();
		G.HLF.atan();
		G.HLF.atanh();
		G.HLF.atan2();
		G.HLF.cos();
		G.HLF.sin();
		G.HLF.sinAndCos();
		G.HLF.cosh();
		G.HLF.sinh();
		G.HLF.sinhAndCosh();
		G.HLF.cot();
		G.HLF.coth();
		G.HLF.csc();
		G.HLF.csch();
		G.HLF.toDegrees();
		G.HLF.toRadians();

		G.HLF.sinc();
		G.HLF.sinch();
		G.HLF.sincpi();
		G.HLF.sinchpi();
		
		G.HLF.assign();
		G.HLF.unity();
		G.HLF.zero();
		G.HLF.infinite();
		G.HLF.negInfinite();
		G.HLF.nan();
		G.HLF.add();
		G.HLF.subtract();
		G.HLF.multiply();
		G.HLF.divide();
		G.HLF.div();
		G.HLF.mod();
		G.HLF.divMod();
		G.HLF.pow();
		G.HLF.power();
		G.HLF.invert();
		G.HLF.negate();
		G.HLF.abs();
		G.HLF.norm();
		G.HLF.cbrt();
		G.HLF.sqrt();
		G.HLF.conjugate();
		G.HLF.within();

		G.HLF.copySign();
		G.HLF.getExponent();
		G.HLF.round();
		G.HLF.scalb();
		G.HLF.ulp();

		G.HLF.E();
		G.HLF.GAMMA();
		G.HLF.PHI();
		G.HLF.PI();

		G.HLF.exp();
		G.HLF.expm1();
		G.HLF.log();
		G.HLF.log10();
		G.HLF.log1p();

		G.HLF.scale();
		G.HLF.scaleByDouble();
		G.HLF.scaleByHighPrec();
		G.HLF.scaleByRational();
		G.HLF.scaleByOneHalf();
		G.HLF.scaleByTwo();
		G.HLF.scaleComponents();

		G.HLF.pred();
		G.HLF.succ();

		G.HLF.random();
		
		G.HLF.real();
		G.HLF.unreal();
	}
	
	// Here are the supported methods for float32s
	
	@SuppressWarnings("unused")
	void example3() {
		
		Float32Member a = G.FLT.construct();
		Float32Member b = G.FLT.construct("53.777");
		Float32Member c = G.FLT.construct(b);
		Float32Member d = new Float32Member(101.321f);
		
		G.FLT.maxBound();
		G.FLT.minBound();

		G.FLT.compare();
		G.FLT.signum();
		G.FLT.isEqual();
		G.FLT.isNotEqual();
		G.FLT.isGreater();
		G.FLT.isGreaterEqual();
		G.FLT.isLess();
		G.FLT.isLessEqual();
		G.FLT.isNaN();
		G.FLT.isInfinite();
		G.FLT.isUnity();
		G.FLT.isZero();
		G.FLT.max();
		G.FLT.min();
		
		G.FLT.acos();
		G.FLT.acosh();
		G.FLT.acot();
		G.FLT.acoth();
		G.FLT.acsc();
		G.FLT.acsch();
		G.FLT.asec();
		G.FLT.asech();
		G.FLT.asin();
		G.FLT.asinh();
		G.FLT.atan();
		G.FLT.atanh();
		G.FLT.atan2();
		G.FLT.cos();
		G.FLT.sin();
		G.FLT.sinAndCos();
		G.FLT.cosh();
		G.FLT.sinh();
		G.FLT.sinhAndCosh();
		G.FLT.cot();
		G.FLT.coth();
		G.FLT.csc();
		G.FLT.csch();
		G.FLT.toDegrees();
		G.FLT.toRadians();

		G.FLT.sinc();
		G.FLT.sinch();
		G.FLT.sincpi();
		G.FLT.sinchpi();
		
		G.FLT.assign();
		G.FLT.unity();
		G.FLT.zero();
		G.FLT.infinite();
		G.FLT.negInfinite();
		G.FLT.nan();
		G.FLT.add();
		G.FLT.subtract();
		G.FLT.multiply();
		G.FLT.divide();
		G.FLT.div();
		G.FLT.mod();
		G.FLT.divMod();
		G.FLT.pow();
		G.FLT.power();
		G.FLT.invert();
		G.FLT.negate();
		G.FLT.abs();
		G.FLT.norm();
		G.FLT.cbrt();
		G.FLT.sqrt();
		G.FLT.conjugate();
		G.FLT.within();

		G.FLT.copySign();
		G.FLT.getExponent();
		G.FLT.round();
		G.FLT.scalb();
		G.FLT.ulp();

		G.FLT.E();
		G.FLT.GAMMA();
		G.FLT.PHI();
		G.FLT.PI();

		G.FLT.exp();
		G.FLT.expm1();
		G.FLT.log();
		G.FLT.log10();
		G.FLT.log1p();

		G.FLT.scale();
		G.FLT.scaleByDouble();
		G.FLT.scaleByHighPrec();
		G.FLT.scaleByRational();
		G.FLT.scaleByOneHalf();
		G.FLT.scaleByTwo();
		G.FLT.scaleComponents();

		G.FLT.pred();
		G.FLT.succ();

		G.FLT.random();
		
		G.FLT.real();
		G.FLT.unreal();
	}
	
	// Here are the supported methods for float64s
	
	@SuppressWarnings("unused")
	void example4() {
		
		Float64Member a = G.DBL.construct();
		Float64Member b = G.DBL.construct("53.777");
		Float64Member c = G.DBL.construct(b);
		Float64Member d = new Float64Member(101.321);
		
		G.DBL.maxBound();
		G.DBL.minBound();

		G.DBL.compare();
		G.DBL.signum();
		G.DBL.isEqual();
		G.DBL.isNotEqual();
		G.DBL.isGreater();
		G.DBL.isGreaterEqual();
		G.DBL.isLess();
		G.DBL.isLessEqual();
		G.DBL.isNaN();
		G.DBL.isInfinite();
		G.DBL.isUnity();
		G.DBL.isZero();
		G.DBL.max();
		G.DBL.min();
		
		G.DBL.acos();
		G.DBL.acosh();
		G.DBL.acot();
		G.DBL.acoth();
		G.DBL.acsc();
		G.DBL.acsch();
		G.DBL.asec();
		G.DBL.asech();
		G.DBL.asin();
		G.DBL.asinh();
		G.DBL.atan();
		G.DBL.atanh();
		G.DBL.atan2();
		G.DBL.cos();
		G.DBL.sin();
		G.DBL.sinAndCos();
		G.DBL.cosh();
		G.DBL.sinh();
		G.DBL.sinhAndCosh();
		G.DBL.cot();
		G.DBL.coth();
		G.DBL.csc();
		G.DBL.csch();
		G.DBL.toDegrees();
		G.DBL.toRadians();

		G.DBL.sinc();
		G.DBL.sinch();
		G.DBL.sincpi();
		G.DBL.sinchpi();
		
		G.DBL.assign();
		G.DBL.unity();
		G.DBL.zero();
		G.DBL.infinite();
		G.DBL.negInfinite();
		G.DBL.nan();
		G.DBL.add();
		G.DBL.subtract();
		G.DBL.multiply();
		G.DBL.divide();
		G.DBL.div();
		G.DBL.mod();
		G.DBL.divMod();
		G.DBL.pow();
		G.DBL.power();
		G.DBL.invert();
		G.DBL.negate();
		G.DBL.abs();
		G.DBL.norm();
		G.DBL.cbrt();
		G.DBL.sqrt();
		G.DBL.conjugate();
		G.DBL.within();
		
		G.DBL.copySign();
		G.DBL.getExponent();
		G.DBL.round();
		G.DBL.scalb();
		G.DBL.ulp();

		G.DBL.E();
		G.DBL.GAMMA();
		G.DBL.PHI();
		G.DBL.PI();

		G.DBL.exp();
		G.DBL.expm1();
		G.DBL.log();
		G.DBL.log10();
		G.DBL.log1p();

		G.DBL.scale();
		G.DBL.scaleByDouble();
		G.DBL.scaleByHighPrec();
		G.DBL.scaleByRational();
		G.DBL.scaleByOneHalf();
		G.DBL.scaleByTwo();
		G.DBL.scaleComponents();

		G.DBL.pred();
		G.DBL.succ();

		G.DBL.random();
		
		G.DBL.real();
		G.DBL.unreal();
	}
	
	// Here are the supported methods for highprecs
	
	@SuppressWarnings("unused")
	void example5() {
		
		HighPrecisionMember a = G.HP.construct();
		HighPrecisionMember b = G.HP.construct("53.777");
		HighPrecisionMember c = G.HP.construct(b);
		HighPrecisionMember d = new HighPrecisionMember(BigDecimal.valueOf(101.321));
		
		G.HP.compare();
		G.HP.signum();
		G.HP.isEqual();
		G.HP.isNotEqual();
		G.HP.isGreater();
		G.HP.isGreaterEqual();
		G.HP.isLess();
		G.HP.isLessEqual();
		G.HP.isUnity();
		G.HP.isZero();
		G.HP.max();
		G.HP.min();
		
		G.HP.acos();
		G.HP.acosh();
		G.HP.asin();
		G.HP.asinh();
		G.HP.atan();
		G.HP.atanh();
		G.HP.cos();
		G.HP.sin();
		G.HP.sinAndCos();
		G.HP.cosh();
		G.HP.sinh();
		G.HP.sinhAndCosh();

		G.HP.sinc();
		G.HP.sinch();
		G.HP.sincpi();
		G.HP.sinchpi();
		
		G.HP.assign();
		G.HP.unity();
		G.HP.zero();
		G.HP.add();
		G.HP.subtract();
		G.HP.multiply();
		G.HP.divide();
		G.HP.pow();
		G.HP.power();
		G.HP.invert();
		G.HP.negate();
		G.HP.abs();
		G.HP.norm();
		G.HP.cbrt();
		G.HP.sqrt();
		G.HP.conjugate();
		G.HP.within();

		G.HP.copySign();
		G.HP.getExponent();
		G.HP.scalb();
		G.HP.ulp();

		G.HP.E();
		G.HP.GAMMA();
		G.HP.PHI();
		G.HP.PI();

		G.HP.exp();
		G.HP.log();

		G.HP.scale();
		G.HP.scaleByDouble();
		G.HP.scaleByHighPrec();
		G.HP.scaleByRational();
		G.HP.scaleByOneHalf();
		G.HP.scaleByTwo();
		G.HP.scaleComponents();
		
		G.HP.real();
		G.HP.unreal();
	}
}
