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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;

/**
 * @author Barry DeZonia
 */
public class TensorEqual {

	// do not instantiate
	
	private  TensorEqual() { }

	/**
	 * 
	 * @param <CC>
	 * @param <COMPONENT>
	 * @param <TEN>
	 * @param alg
	 * @param a
	 * @param b
	 */
	public static <TEN extends TensorMember<COMPONENT> & RawData<COMPONENT>,
					CC extends Algebra<CC,COMPONENT>,
					COMPONENT>
	
		boolean compute(CC alg, TEN a, TEN b)
	
	{
		if (a == b)
			return true;
		
		if (!ShapesMatch.compute(a, b))
			return false;
		
		if (!IndicesMatch.compute(a, b))
			return false;
		
		IndexedDataSource<COMPONENT> rawA = a.rawData();
		IndexedDataSource<COMPONENT> rawB = b.rawData();
		
		COMPONENT aTmp = alg.construct();
		COMPONENT bTmp = alg.construct();

		for (long i = 0; i < rawA.size(); i++) {
		
			rawA.get(i, aTmp);
			rawB.get(i, bTmp);

			if (alg.isNotEqual().call(aTmp, bTmp))
				return false;
		}

		return true;
	}
}
