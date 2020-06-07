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
import nom.bdezonia.zorbage.algebra.GetOctonion;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16Member;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.octonion.OctonionHighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;

// What are Octonions? See https://en.wikipedia.org/wiki/Octonion

//TODO - discuss the Oct constructor helpers

/**
 * @author Barry DeZonia
 */
class Octonions {

	// Zorbage supports Octonion numbers in a variety precisions
	
	@SuppressWarnings("unused")
	void example1() {
		
		// 16-bit float based
		
		OctonionFloat16Member o16 = new OctonionFloat16Member(1.3f, -6.2f, 0, 0, 6, 3, 1, 7);
		
		// 32-bit float based
		
		OctonionFloat32Member o32 = new OctonionFloat32Member(99f, 44f, 1, 2, 1, 1, 1, 1);
		
		// 64-bit float based
		
		OctonionFloat64Member o64 = new OctonionFloat64Member(Math.E, Math.PI, -3, -7, 0, 0, 1, 2);
		
		// unbounded high precision float based
		
		OctonionHighPrecisionMember oBig =
				new OctonionHighPrecisionMember(BigDecimal.valueOf(-6712),BigDecimal.valueOf(1033),
													BigDecimal.TEN, BigDecimal.ONE,
													BigDecimal.TEN, BigDecimal.ONE,
													BigDecimal.TEN, BigDecimal.ONE);
		
		// use code (defined below) that can work with any of them
		//   and use it to extract values from all kinds of Octonion values
		
		Float16Member r16 = getK0(G.HLF, o16);
	
		Float32Member r32 = getK0(G.FLT, o32);
		
		Float64Member r64 = getK0(G.DBL, o64);
		
		HighPrecisionMember rBig = getK0(G.HP, oBig);
		
		// an alternative way to get this data without a reusable approach

		o16.getK0(r16);
		o32.getK0(r32);
		o64.getK0(r64);
		oBig.getK0(rBig);
	}
	
	<NUMBER extends GetOctonion<COMPONENT>,
		COMPONENT_ALGEBRA extends nom.bdezonia.zorbage.algebra.Algebra<COMPONENT_ALGEBRA,COMPONENT>,
		COMPONENT>
	COMPONENT getK0(COMPONENT_ALGEBRA componentAlgebra, NUMBER OctonionNumber)
	{
		COMPONENT component = componentAlgebra.construct();
		OctonionNumber.getK0(component);
		return component;
	}
	
	// Zorbage can manipulate Octonion numbers in many ways
	
	void example2() {
		
		// Any of the Octonion algebras (G.QHLF, G.QFLT, G.ODBL, and G.QHP) implement all of
		// the following methods.
		
		OctonionFloat64Member num = G.ODBL.construct();
		OctonionFloat64Member other = G.ODBL.construct();
		Float64Member val = G.DBL.construct();
		
		// basic java support
		
		num.equals(num);
		num.hashCode();
		num.toString();

		// setters and getters

		num.r();
		num.i();
		num.get(other);
		num.set(other);
		num.getV(other);
		num.setV(other);
		num.getR(val);
		num.getI(val);
		num.getJ(val);
		num.getK(val);
		num.getL(val);
		num.getI0(val);
		num.getJ0(val);
		num.getK0(val);
		num.setR(val);
		num.setI(val);
		num.setJ(val);
		num.setK(val);
		num.setL(val);
		num.setI0(val);
		num.setJ0(val);
		num.setK0(val);
		
		// construction
		
		num.allocate();  // return a new Octonion number initialized to zero
		num.duplicate();  // return a new Octonion number initialized to my own values
		
		// more construction
		
		G.ODBL.construct();  // construct a zero value
		G.ODBL.construct(num);  // construct a copy of a number
		G.ODBL.construct("{44,77,3,2,7,5,3,1}");  // construct a Octonion number from a string

		// comparisons
		
		G.ODBL.isEqual();
		G.ODBL.isNotEqual();
		G.ODBL.isInfinite();
		G.ODBL.isNaN();
		G.ODBL.isZero();

		// set to one or inf or nan or zero or random value
		
		G.ODBL.unity();
		G.ODBL.infinite();
		G.ODBL.nan();
		G.ODBL.zero();
		G.ODBL.random();

		// get/use Octonion constants
		
		G.ODBL.E();
		G.ODBL.GAMMA();
		G.ODBL.PI();
		G.ODBL.PHI();
		G.ODBL.I();
		G.ODBL.J();
		G.ODBL.K();
		G.ODBL.L();
		G.ODBL.I0();
		G.ODBL.J0();
		G.ODBL.K0();

		// basic operations
		
		G.ODBL.assign();
		G.ODBL.add();
		G.ODBL.subtract();
		G.ODBL.multiply();
		G.ODBL.divide();
		G.ODBL.negate();
		G.ODBL.invert();
		G.ODBL.pow();
		G.ODBL.power();
		G.ODBL.conjugate();
		G.ODBL.norm();
		G.ODBL.round();

		// query operations

		G.ODBL.real();  // get a real number with real equal my r
		G.ODBL.unreal(); // get a Octonion number with real 0 and unreal equal my 0,i,j,k,l,i0,j0,k0

		// scaling
		
		G.ODBL.scale();
		G.ODBL.scaleByDouble();
		G.ODBL.scaleByHighPrec();
		G.ODBL.scaleByRational();
		G.ODBL.scaleComponents();

		// roots
		
		G.ODBL.sqrt();
		G.ODBL.cbrt();

		// exp / log
		
		G.ODBL.exp();
		G.ODBL.log();

		// transcendentals (regular and hyperbolic)
		
		G.ODBL.cos();
		G.ODBL.cosh();
		G.ODBL.sin();
		G.ODBL.sinh();
		G.ODBL.tan();
		G.ODBL.tanh();

		// various sinc functions
		
		G.ODBL.sinc();
		G.ODBL.sinch();
		G.ODBL.sincpi();
		G.ODBL.sinchpi();
		
	}
}
