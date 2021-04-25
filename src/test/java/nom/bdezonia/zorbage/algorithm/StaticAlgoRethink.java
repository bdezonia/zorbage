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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class StaticAlgoRethink {

	private static <T extends Algebra<T,U> & Addition<U>, U>
		void compute(T algebra, U result)
	{
		algebra.add().call(result, result, result);
	}
	
	private class ClassAlgoRethink<T extends Algebra<T,U> & Addition<U>, U>
		implements Procedure2<T,U>
	{
		@Override
		public void call(T algebra, U result) {
			
			algebra.add().call(result, result, result);
		}
		
	}
	
	public void test() {
		
		Float64Member result = G.DBL.construct();
		
		// THIS ?
		
		StaticAlgoRethink.compute(G.DBL, result);
		
		// OR THIS?
		
		new ClassAlgoRethink<Float64Algebra,Float64Member>().call(G.DBL, result);
		
		// I really don't like the second. It requires an object allocation and it requires
		// the specifying of the template parameter types.
		
		// The second has the good effect that algorithms could be passed around as procedures
		// and invoked. Imagine a class that works for any possible norm. You could pass in a
		// norm procedure and get different behavior. Otherwise you need one implementation of
		// the algorithm for each norm type you're interested in. Later edit: not entirely
		// true. A static algorithm could be passed a norm procedure as well. But note that
		// all the norm algorithms that exist when this note was entered are static algos and
		// can't be passed.
		
		// Later edit: I worked a while on replacing all static code with class based code.
		// Frankly it was ugly and thus likely wrong. If someone writes an algorithm that takes
		// a Procedure as a parameter there is nothing stopping someone from wrapping a static
		// algorithm in a Procedure as a way of passing it. This one off solution is likely
		// fine. Just because it would in theory be nice to pass algorithms around it does not
		// mean the need arises often enough that ALL algorithms must not be static. The static
		// algorithms have their own elegance. I will abandon the ALL Procedures idea.
	}
}