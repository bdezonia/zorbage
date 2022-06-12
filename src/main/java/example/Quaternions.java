/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.GetQuaternion;
import nom.bdezonia.zorbage.type.quaternion.float128.QuaternionFloat128Member;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16Member;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32Member;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionMember;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// What are Quaternions? See https://en.wikipedia.org/wiki/Quaternion

//TODO - discuss the Quat constructor helpers

/**
 * @author Barry DeZonia
 */
class Quaternions {

	// Zorbage supports Quaternion numbers in a variety precisions
	
	void example1() {
		
		// 16-bit float based
		
		QuaternionFloat16Member q16 = new QuaternionFloat16Member(1.3f, -6.2f, 0, 0);
		
		// 32-bit float based
		
		QuaternionFloat32Member q32 = new QuaternionFloat32Member(99f, 44f, 1, 2);
		
		// 64-bit float based
		
		QuaternionFloat64Member q64 = new QuaternionFloat64Member(Math.E, Math.PI, -3, -7);
		
		// 128-bit float based
		
		QuaternionFloat128Member q128 = new QuaternionFloat128Member(BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN.negate());
		
		// unbounded high precision float based
		
		QuaternionHighPrecisionMember qBig =
				new QuaternionHighPrecisionMember(BigDecimal.valueOf(-6712),BigDecimal.valueOf(1033),
													BigDecimal.TEN, BigDecimal.ONE);
		
		// use code (defined below) that can work with any of them
		//   and use it to extract values from all kinds of Quaternion values
		
		Float16Member r16 = getK(G.HLF, q16);
	
		Float32Member r32 = getK(G.FLT, q32);
		
		Float64Member r64 = getK(G.DBL, q64);
		
		Float128Member r128 = getK(G.QUAD, q128);
		
		HighPrecisionMember rBig = getK(G.HP, qBig);
		
		// an alternative way to get this data without a reusable approach

		q16.getK(r16);
		q32.getK(r32);
		q64.getK(r64);
		q128.getK(r128);
		qBig.getK(rBig);
	}
	
	<NUMBER extends GetQuaternion<COMPONENT>,
		COMPONENT_ALGEBRA extends nom.bdezonia.zorbage.algebra.Algebra<COMPONENT_ALGEBRA,COMPONENT>,
		COMPONENT>
	COMPONENT getK(COMPONENT_ALGEBRA componentAlgebra, NUMBER QuaternionNumber)
	{
		COMPONENT component = componentAlgebra.construct();
		QuaternionNumber.getK(component);
		return component;
	}
	
	// Zorbage can manipulate Quaternion numbers in many ways
	
	void example2() {
		
		// Any of the Quaternion algebras (G.QHLF, G.QFLT, G.QDBL, and G.QHP) implement all of
		// the following methods.
		
		QuaternionFloat64Member num = G.QDBL.construct();
		QuaternionFloat64Member other = G.QDBL.construct();
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
		num.setR(val);
		num.setI(val);
		num.setJ(val);
		num.setK(val);
		
		// construction
		
		num.allocate();  // return a new Quaternion number initialized to zero
		num.duplicate();  // return a new Quaternion number initialized to my own values
		
		// more construction
		
		G.QDBL.construct();  // construct a zero value
		G.QDBL.construct(num);  // construct a copy of a number
		G.QDBL.construct("{44,77,3,2}");  // construct a Quaternion number from a string

		// comparisons
		
		G.QDBL.isEqual();
		G.QDBL.isNotEqual();
		G.QDBL.isInfinite();
		G.QDBL.isNaN();
		G.QDBL.isUnity();
		G.QDBL.isZero();

		// set to one or inf or nan or zero or random value
		
		G.QDBL.unity();
		G.QDBL.infinite();
		G.QDBL.nan();
		G.QDBL.zero();
		G.QDBL.random();

		// get/use Quaternion constants
		
		G.QDBL.E();
		G.QDBL.GAMMA();
		G.QDBL.PI();
		G.QDBL.PHI();
		G.QDBL.I();
		G.QDBL.J();
		G.QDBL.K();

		// basic operations
		
		G.QDBL.assign();
		G.QDBL.add();
		G.QDBL.subtract();
		G.QDBL.multiply();
		G.QDBL.divide();
		G.QDBL.negate();
		G.QDBL.invert();
		G.QDBL.pow();
		G.QDBL.power();
		G.QDBL.conjugate();
		G.QDBL.norm();
		G.QDBL.round();

		// query operations

		G.QDBL.real();  // get a real number with real equal my r
		G.QDBL.unreal(); // get a Quaternion number with real 0 and unreal equal my 0,i,j,k

		// scaling
		
		G.QDBL.scale();
		G.QDBL.scaleByDouble();
		G.QDBL.scaleByHighPrec();
		G.QDBL.scaleByRational();
		G.QDBL.scaleComponents();

		// roots
		
		G.QDBL.sqrt();
		G.QDBL.cbrt();

		// exp / log
		
		G.QDBL.exp();
		G.QDBL.log();

		// transcendentals (regular and hyperbolic)
		
		G.QDBL.cos();
		G.QDBL.cosh();
		G.QDBL.sin();
		G.QDBL.sinh();
		G.QDBL.tan();
		G.QDBL.tanh();

		// various sinc functions
		
		G.QDBL.sinc();
		G.QDBL.sinch();
		G.QDBL.sincpi();
		G.QDBL.sinchpi();
		
	}
}
