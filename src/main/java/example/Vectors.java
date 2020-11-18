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
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32VectorMember;

/**
 * @author Barry DeZonia
 */
class Vectors {

	/*
	 * Zorbage supports Vectors and RModules. An RModule is a list of elements
	 * that do not support all the operations that Vector elements do. So reals and
	 * complexes can be organized into Vectors. Quaternions and octonions can only
	 * be organized into RModules. This is because quaternions and octonions do not
	 * support all the math operations that reals and complexes can. The key thing
	 * to realize is that RModules and Vectors are functionally equivalent and you
	 * can ignore the naming convention. It is named such to be mathematically
	 * correct.
	 */

	/*
	 * Zorbage supports various flavors of Vectors/RModules:
	 * 
	 * Float16VectorMember - 16 bit reals
	 * Float32VectorMember - 32 bit reals
	 * Float64VectorMember - 64 bit reals
	 * HighPrecisionVectorMember - high precision reals
	 * 
	 * ComplexFloat16VectorMember - 16 bit complexes
	 * ComplexFloat32VectorMember - 32 bit complexes
	 * ComplexFloat64VectorMember - 64 bit complexes
	 * ComplexHighPrecisionVectorMember - high precision complexes
	 * 
	 * QuaternionFloat16RModuleMember - 16 bit quaternions
	 * QuaternionFloat32RModuleMember - 32 bit quaternions
	 * QuaternionFloat64RModuleMember - 64 bit quaternions
	 * QuaternionHighPrecisionRModuleMember - high precision quaternions
	 * 
	 * OctonionFloat16RModuleMember - 16 bit octonions
	 * OctonionFloat32RModuleMember - 32 bit octonions
	 * OctonionFloat64RModuleMember - 64 bit octonions
	 * OctonionHighPrecisionRModuleMember - high precision octonions
	 * 
	 */
	
	// Here are some example calls supported by Vectors/RModules. Float32 reals are shown
	// in the examples below but all the same calls exist for the type combinations:
	// (Precision: 16/32/64/HighPrec Type:real/complex/quaternion/octonion)
	
	@SuppressWarnings("unused")
	void example1() {
		
		Float32Member value = G.FLT.construct();
		
		// construction
		
		Float32VectorMember a = new Float32VectorMember();
		Float32VectorMember b = new Float32VectorMember(new float[] {1,2,3,4});
		Float32VectorMember c = G.FLT_VEC.construct();
		Float32VectorMember d = G.FLT_VEC.construct(b);
		Float32VectorMember e = G.FLT_VEC.construct("[1,2,3,4,5,6]");
		Float32VectorMember f = G.FLT_VEC.construct(StorageConstruction.MEM_SPARSE, 1000);

		// java support
		
		a.equals(b);
		a.hashCode();
		a.toString();

		// basic functions
		
		b.length();
		b.get(e);
		b.set(f);
		b.getV(2, value);
		b.setV(1, value);

		// basic operations
		
		G.FLT_VEC.assign();
		G.FLT_VEC.add();  // add two vectors
		G.FLT_VEC.addScalar();  // add a scalar to all elements of a vector
		G.FLT_VEC.multiplyByScalar();  // multiply all elements of a vector by a scalar 
		G.FLT_VEC.multiplyElements(); // do element wise multiplication of two vectors
		G.FLT_VEC.subtract(); // subtract one vector from another
		G.FLT_VEC.subtractScalar(); // add a scalar from all elements of a vector
		G.FLT_VEC.divideByScalar();  // divide all elements of a vector by a scalar
		G.FLT_VEC.divideElements(); // do element wise division of two vectors
		G.FLT_VEC.conjugate();  // conjugate all the elements of a vector
		G.FLT_VEC.negate();  // flip the sign of all elements of a vector
		G.FLT_VEC.norm();  // calculate the magnitude of a vector
		G.FLT_VEC.round();  // round all elements of a vector to specified precision
		G.FLT_VEC.within();  // test that two vectors are within a tolerance of each other

		// test values

		G.FLT_VEC.isEqual();
		G.FLT_VEC.isNotEqual();
		G.FLT_VEC.isInfinite();
		G.FLT_VEC.isNaN();
		G.FLT_VEC.isZero();

		// set values
		
		G.FLT_VEC.infinite();
		G.FLT_VEC.nan();
		G.FLT_VEC.zero();
		
		// scaling
		
		G.FLT_VEC.scale();
		G.FLT_VEC.scaleByDouble();
		G.FLT_VEC.scaleByHighPrec();
		G.FLT_VEC.scaleByRational();
		
		// vector operations
		
		G.FLT_VEC.crossProduct();
		G.FLT_VEC.directProduct();
		G.FLT_VEC.dotProduct();
		G.FLT_VEC.perpDotProduct();
		G.FLT_VEC.scalarTripleProduct();
		G.FLT_VEC.vectorDirectProduct();
		G.FLT_VEC.vectorTripleProduct();
	}
}