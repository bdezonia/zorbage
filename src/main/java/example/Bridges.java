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
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.helper.NumberRModuleBridge;

/**
 * @author Barry DeZonia
 */
public class Bridges {

	/*
	 * Zorbage delineates many data types walled off in designations like NumberMember or
	 * Vector/RModuleMember, MatrixMember, and TensorProductMember. However in mathematical
	 * terms each smaller type (say Number) can be treated as a special case of a higher
	 * level type. For instance a Number is the same as a 1-element vector or the same as
	 * a 1x1 shaped matrix or a 1x1x1... shaped tensor. Given these relationships we define
	 * bridge classes that allow elements of one type (vector for instance) to be passed
	 * as another (a rank 1 tensor for instance). There are numerous bridge classes and
	 * they will be detailed below.
	 */
	
	void example1() {
		Float64Member num = new Float64Member(191.6);
		NumberRModuleBridge<Float64Member> bridge = new NumberRModuleBridge<>(G.DBL, num);
		bridge.setV(0, G.DBL.construct());
		System.out.println(num); // prints 0
	}
}
