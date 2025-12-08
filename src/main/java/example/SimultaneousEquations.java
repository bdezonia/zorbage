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
import nom.bdezonia.zorbage.algorithm.LUDecomp;
import nom.bdezonia.zorbage.algorithm.LUSolve;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64VectorMember;

/**
 * @author Barry DeZonia
 */
class SimultaneousEquations {

	// Zorbage can solve systems of equations by applying the LU style algorithms 
	
	void example1() {

		// Given a system of equations that satisfy A * x = b then given a and b we can
		// find the real points x that solve the equation
		
		Float64MatrixMember a = new Float64MatrixMember(3, 3,
				new double[] {1, 0, 1,
								1, 1, 0,
								3, 2, 1});
		
		LUDecomp.compute(G.DBL, G.DBL_MAT, a);

		Float64VectorMember b = new Float64VectorMember(new double[] {4, 7, 3});
		
		Float64VectorMember x = G.DBL_VEC.construct();
		
		LUSolve.compute(G.DBL, G.DBL_VEC, a, b, x);
	}
	
	// Note that this approach works for complex numbers and quaternions and octonions as well.
	// Below we line out the same simple real example but working in more complex spaces.
	
	void example2() {

		// Given a system of equations that satisfy A * x = b then given a and b we can
		// find the complex points x that solve the equation
		
		ComplexFloat64MatrixMember a = new ComplexFloat64MatrixMember(3, 3,
				new double[] {1, 0, 0, 0, 1, 0,
								1, 0, 1, 0, 0, 0,
								3, 0, 2, 0, 1, 0});
		
		LUDecomp.compute(G.CDBL, G.CDBL_MAT, a);

		ComplexFloat64VectorMember b =
				new ComplexFloat64VectorMember(new double[] {4, 0, 7, 0, 3, 0});
		
		ComplexFloat64VectorMember x = G.CDBL_VEC.construct();
		
		LUSolve.compute(G.CDBL, G.CDBL_VEC, a, b, x);
	}
	
	void example3() {

		// Given a system of equations that satisfy A * x = b then given a and b we can
		// find the quaternion points x that solve the equation
		
		QuaternionFloat64MatrixMember a = new QuaternionFloat64MatrixMember(3, 3,
				new double[] {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
								1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
								3, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0});
		
		LUDecomp.compute(G.QDBL, G.QDBL_MAT, a);

		QuaternionFloat64RModuleMember b =
				new QuaternionFloat64RModuleMember(new double[] {4, 0, 0, 0, 7, 0, 0, 0, 0, 3, 0, 0, 0});
		
		QuaternionFloat64RModuleMember x = G.QDBL_RMOD.construct();
		
		LUSolve.compute(G.QDBL, G.QDBL_RMOD, a, b, x);
	}
	
	void example4() {

		// Given a system of equations that satisfy A * x = b then given a and b we can
		// find the octonion points x that solve the equation
		
		OctonionFloat64MatrixMember a = new OctonionFloat64MatrixMember(3, 3,
				new double[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 
								1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
								3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
		
		LUDecomp.compute(G.ODBL, G.ODBL_MAT, a);

		OctonionFloat64RModuleMember b = new OctonionFloat64RModuleMember(new double[] {4, 7, 3});
		
		OctonionFloat64RModuleMember x = G.ODBL_RMOD.construct();
		
		LUSolve.compute(G.ODBL, G.ODBL_RMOD, a, b, x);
	}
	
	
}