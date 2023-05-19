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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform {

	// do not instantiate
	
	private Transform() { }
	
	/**
	 * 
	 * @param <AA>
	 * @param <A>
	 * @param <BB>
	 * @param <B>
	 * @param aAlg
	 * @param bAlg
	 * @param proc
	 * @param a
	 * @param b
	 */
	public static <AA extends Algebra<AA,A>, A, BB extends Algebra<BB,B>, B>
		void compute(AA aAlg, BB bAlg, Procedure2<A,B> proc, IndexedDataSource<A> a, IndexedDataSource<B> b)
	{
		
		// TODO: parallelize this code. Copy basics from Transform1 or Transform2.
		
		A tmpA = aAlg.construct();
		
		B tmpB = bAlg.construct();
		
		for (long i = 0; i < a.size(); i++) {
			
			a.get(i, tmpA);
			
			proc.call(tmpA, tmpB);

			b.set(i, tmpB);
		}
		
	}

}
