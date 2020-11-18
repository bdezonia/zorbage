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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetComplex;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionMember;
import nom.bdezonia.zorbage.type.gaussian.int64.GaussianInt64Member;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// TODO - discuss the Complex constructor helpers

/**
 * @author Barry DeZonia
 */
class Complexes {

	// Zorbage supports complex numbers in a variety precisions
	
	void example1() {
		
		// 16-bit float based
		
		ComplexFloat16Member c16 = new ComplexFloat16Member(1.3f, -6.2f);
		
		// 32-bit float based
		
		ComplexFloat32Member c32 = new ComplexFloat32Member(99f, 44f);
		
		// 64-bit float based
		
		ComplexFloat64Member c64 = new ComplexFloat64Member(Math.E, Math.PI);
		
		// unbounded high precision float based
		
		ComplexHighPrecisionMember cBig =
				new ComplexHighPrecisionMember(BigDecimal.valueOf(-6712),BigDecimal.valueOf(1033));
		
		// use code (defined below) that can work with any of them
		//   and use it to extract values from all kinds of complex values
		
		Float16Member r16 = getImaginary(G.HLF, c16);
	
		Float32Member r32 = getImaginary(G.FLT, c32);
		
		Float64Member r64 = getImaginary(G.DBL, c64);
		
		HighPrecisionMember rBig = getImaginary(G.HP, cBig);
		
		// an alternative way to get this data without a reusable approach

		c16.getI(r16);
		c32.getI(r32);
		c64.getI(r64);
		cBig.getI(rBig);
	}
	
	<NUMBER extends GetComplex<COMPONENT>,
		COMPONENT_ALGEBRA extends nom.bdezonia.zorbage.algebra.Algebra<COMPONENT_ALGEBRA,COMPONENT>,
		COMPONENT>
	COMPONENT getImaginary(COMPONENT_ALGEBRA componentAlgebra, NUMBER complexNumber)
	{
		COMPONENT component = componentAlgebra.construct();
		complexNumber.getI(component);
		return component;
	}
	
	// Zorbage can manipulate complex numbers in many ways
	
	void example2() {
		
		// Any of the complex algebras (G.CHLF, G.CFLT, G.CDBL, and G.CHP) implement all of
		// the following methods.
		
		ComplexFloat64Member num = G.CDBL.construct();
		ComplexFloat64Member other = G.CDBL.construct();
		Float64Member val = G.DBL.construct();
		
		// basic java support
		
		num.equals(num);
		num.hashCode();
		num.toString();

		// setters and getters

		num.r();
		num.i();
		num.set(other);
		num.get(other);
		num.getV(other);
		num.setV(other);
		num.getR(val);
		num.getI(val);
		num.setR(val);
		num.setI(val);
		
		// construction
		
		num.allocate();  // return a new complex number initialized to zero
		num.duplicate();  // return a new complex number initialized to my own values
		
		// more construction
		
		G.CDBL.construct();  // construct a zero value
		G.CDBL.construct(num);  // construct a copy of a number
		G.CDBL.construct("{44,77}");  // construct a complex number from a string

		// comparisons
		
		G.CDBL.isEqual();
		G.CDBL.isNotEqual();
		G.CDBL.isInfinite();
		G.CDBL.isNaN();
		G.CDBL.isZero();

		// set to one or inf or nan or zero or random value
		
		G.CDBL.unity();
		G.CDBL.infinite();
		G.CDBL.nan();
		G.CDBL.zero();
		G.CDBL.random();

		// get/use complex constants
		
		G.CDBL.E();
		G.CDBL.GAMMA();
		G.CDBL.I();
		G.CDBL.PI();
		G.CDBL.PHI();

		// basic operations
		
		G.CDBL.assign();
		G.CDBL.add();
		G.CDBL.subtract();
		G.CDBL.multiply();
		G.CDBL.divide();
		G.CDBL.negate();
		G.CDBL.invert();
		G.CDBL.pow();
		G.CDBL.power();
		G.CDBL.conjugate();
		G.CDBL.norm();
		G.CDBL.round();

		// query operations

		G.CDBL.real();  // get a real number with real equal my r
		G.CDBL.unreal(); // get a complex number equal to my 0,i

		// scaling
		
		G.CDBL.scale();
		G.CDBL.scaleByDouble();
		G.CDBL.scaleByHighPrec();
		G.CDBL.scaleByRational();
		G.CDBL.scaleComponents();

		// roots
		
		G.CDBL.sqrt();
		G.CDBL.cbrt();

		// exp / log
		
		G.CDBL.exp();
		G.CDBL.expm1();
		G.CDBL.log();
		G.CDBL.log1p();

		// transcendentals (regular and hyperbolic)
		
		G.CDBL.cos();
		G.CDBL.cosh();
		G.CDBL.cot();
		G.CDBL.coth();
		G.CDBL.csc();
		G.CDBL.csch();
		G.CDBL.sec();
		G.CDBL.sech();
		G.CDBL.sin();
		G.CDBL.sinh();
		G.CDBL.tan();
		G.CDBL.tanh();

		// inverse transcendentals (regular and hyperbolic)

		G.CDBL.acos();
		G.CDBL.acosh();
		G.CDBL.acot();
		G.CDBL.acoth();
		G.CDBL.acsc();
		G.CDBL.acsch();
		G.CDBL.asec();
		G.CDBL.asech();
		G.CDBL.asin();
		G.CDBL.asinh();
		G.CDBL.atan();
		G.CDBL.atanh();

		// various sinc functions
		
		G.CDBL.sinc();
		G.CDBL.sinch();
		G.CDBL.sincpi();
		G.CDBL.sinchpi();
		
	}
	
	// Gaussian integers are a special kind of complex number
	
	void example3() {
	
		// constructors
		
		GaussianInt64Member a = G.GAUSS64.construct();
		GaussianInt64Member b = G.GAUSS64.construct("{4,9}");
		GaussianInt64Member c = G.GAUSS64.construct(b);
		
		// setting values
		
		G.GAUSS64.assign().call(c, a);
		G.GAUSS64.unity().call(a);
		G.GAUSS64.zero().call(c);

		// comparing values
		
		G.GAUSS64.isEqual();
		G.GAUSS64.isNotEqual();
		G.GAUSS64.isEven();
		G.GAUSS64.isOdd();
		G.GAUSS64.isZero();

		// basic mathematical operations
		
		G.GAUSS64.add();
		G.GAUSS64.subtract();
		G.GAUSS64.multiply();
		G.GAUSS64.div();
		G.GAUSS64.mod();
		G.GAUSS64.divMod();
		G.GAUSS64.power();

		// other operations
		
		G.GAUSS64.abs();
		G.GAUSS64.conjugate();
		G.GAUSS64.gcd();
		G.GAUSS64.lcm();
		G.GAUSS64.negate();
		G.GAUSS64.norm();
		G.GAUSS64.random();
		G.GAUSS64.within();

		// scaling methods
		
		G.GAUSS64.scale();
		G.GAUSS64.scaleByDouble();
		G.GAUSS64.scaleByDoubleAndRound();
		G.GAUSS64.scaleByHighPrec();
		G.GAUSS64.scaleByHighPrecAndRound();
		G.GAUSS64.scaleByOneHalf();
		G.GAUSS64.scaleByTwo();
		G.GAUSS64.scaleByRational();
	}
}
