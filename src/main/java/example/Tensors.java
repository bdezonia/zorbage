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

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.real.float64.Float64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;


// What are Tensors? See https://en.wikipedia.org/wiki/Tensor

/**
 * @author Barry DeZonia
 */
class Tensors {
	
	/*
	 * Zorbage supports various flavors of Cartesian Tensors. Cartesian tensors do not have upper indices
	 * (unlike general tensors which do). All of Zorbage's tensors are perfectly shaped (2x2 or 5x5x5 etc.)
	 * rather than raggedly shaped (2x1 or 5x3x4 etc.).
	 * 
	 * Float16CartesianTensorProductMember - 16 bit reals
	 * Float32CartesianTensorProductMember - 32 bit reals
	 * Float64CartesianTensorProductMember - 64 bit reals
	 * Float64CartesianTensorProductMember - high precision reals
	 * 
	 * ComplexFloat16CartesianTensorProductMember - 16 bit complexes
	 * ComplexFloat32CartesianTensorProductMember - 32 bit complexes
	 * ComplexFloat64CartesianTensorProductMember - 64 bit complexes
	 * ComplexFloat64CartesianTensorProductMember - high precision complexes
	 * 
	 * QuaternionFloat16CartesianTensorProductMember - 16 bit quaternions
	 * QuaternionFloat32CartesianTensorProductMember - 32 bit quaternions
	 * QuaternionFloat64CartesianTensorProductMember - 64 bit quaternions
	 * QuaternionFloat64CartesianTensorProductMember - high precision quaternions
	 * 
	 * OctonionFloat16CartesianTensorProductMember - 16 bit octonions
	 * OctonionFloat32CartesianTensorProductMember - 32 bit octonions
	 * OctonionFloat64CartesianTensorProductMember - 64 bit octonions
	 * OctonionFloat64CartesianTensorProductMember - high precision octonions
	 * 
	 */
	
	// Here are some example calls supported by tensors. 64-bit reals are shown
	// in the examples below but all the same calls exist for the type combinations:
	// (Precision: 16/32/64/HighPrec Type:real/complex/quaternion/octonion)
	
	@SuppressWarnings("unused")
	void example1() {
		
		Float64Member value = G.DBL.construct();
		
		// construction
		
		Float64CartesianTensorProductMember a = new Float64CartesianTensorProductMember();
		Float64CartesianTensorProductMember b = new Float64CartesianTensorProductMember(2, 3,
																new double[] {2,4,6,
																				3,5,7,
																				10,100,1000});
		Float64CartesianTensorProductMember c = G.DBL_TEN.construct();
		Float64CartesianTensorProductMember d = G.DBL_TEN.construct(b);
		Float64CartesianTensorProductMember e = G.DBL_TEN.construct("[[1,2,3][4,5,6][7,8,9]]");

		// java support
		
		a.equals(b);
		a.hashCode();
		a.toString();

		// basic functions
		
		IntegerIndex idx = new IntegerIndex(b.rank());
		
		b.rank();
		b.dimension();
		b.get(e);
		b.set(c);
		b.getV(idx, value);
		b.setV(idx, value);

		// basic operations
		
		G.DBL_TEN.assign();
		G.DBL_TEN.add();  // add two matrices
		G.DBL_TEN.addScalar();  // add a scalar to all elements of a matrix
		G.DBL_TEN.multiplyByScalar();  // multiply all elements of a matrix by a scalar 
		G.DBL_TEN.multiplyElements(); // do element wise multiplication of two matrices
		G.DBL_TEN.subtract(); // subtract one matrix from another
		G.DBL_TEN.subtractScalar(); // add a scalar from all elements of a matrix
		G.DBL_TEN.divideByScalar();  // divide all elements of a matrix by a scalar
		G.DBL_TEN.divideElements(); // do element wise division of two matrices
		G.DBL_TEN.conjugate();  // conjugate all the elements of a matrix
		G.DBL_TEN.negate();  // flip the sign of all elements of a matrix
		G.DBL_TEN.norm();  // calculate the magnitude of a matrix
		G.DBL_TEN.power();  // calc a nth-power of a matrix
		G.DBL_TEN.round();  // round all elements of a matrix to specified precision
		G.DBL_TEN.within();  // test that two matrices are within a tolerance of each other

		// test values

		G.DBL_TEN.isEqual();
		G.DBL_TEN.isNotEqual();
		G.DBL_TEN.isInfinite();
		G.DBL_TEN.isNaN();
		G.DBL_TEN.isUnity();
		G.DBL_TEN.isZero();

		// set values: infinity, nan, identity, zero
		
		G.DBL_TEN.infinite();
		G.DBL_TEN.nan();
		G.DBL_TEN.unity();
		G.DBL_TEN.zero();

		// scaling
		
		G.DBL_TEN.scale();
		G.DBL_TEN.scaleByDouble();
		G.DBL_TEN.scaleByHighPrec();
		G.DBL_TEN.scaleByRational();
		
		// tensor methods
		
		G.DBL_TEN.lowerIndex();
		G.DBL_TEN.raiseIndex();
		G.DBL_TEN.contract();
		G.DBL_TEN.innerProduct();
		G.DBL_TEN.outerProduct();
		G.DBL_TEN.commaDerivative();
		G.DBL_TEN.semicolonDerivative();

	}

	
}