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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.datasource.RawData;

/**
 * @author Barry DeZonia
 */
public class TensorAdd {

	// do not instantiate
	
	private TensorAdd() { }
	
	/**
	 * Add two tensors and store the result in a third.
	 * 
	 * @param alg The algebra that defines the addition operator for the tensors.
	 * @param a Source 1 tensor
	 * @param b Source 2 tensor
	 * @param c Destination tensor that will contain the sum
	 */
	public static <TEN extends TensorMember<COMPONENT> & RawData<COMPONENT>,
					CC extends Algebra<CC,COMPONENT> & Addition<COMPONENT>,
					COMPONENT>
		void compute(CC alg, TEN a, TEN b, TEN c)
	{
		
		if (!ShapesMatch.compute(a, b))
			throw new IllegalArgumentException("tensor add issue: shape mismatch");
		if (!IndicesMatch.compute(a, b))
			throw new IllegalArgumentException("tensor add issue: index distribution mismatch");
		TensorShape.compute(a, c);
		Transform3.compute(alg, alg.add(), a.rawData(), b.rawData(), c.rawData());
	}

}
