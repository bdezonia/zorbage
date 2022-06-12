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

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RampFill {

	// do not instantiate
	
	private RampFill() { }
	
	/**
	 * Fill a list with a steadily changing set of values. Start
	 * at "startVal" and increment by "incBy". If "incBy" is positive
	 * the ramp will be increasing and if "incBy" is negative the
	 * ramp will be decreasing.
	 * 
	 * @param alg
	 * @param startVal
	 * @param incBy
	 * @param data
	 */
	public static <T extends Algebra<T,U> & Addition<U>, U>
		void compute(T alg, U startVal, U incBy, IndexedDataSource<U> data)
	{
		if (alg.isZero().call(incBy))
			throw new IllegalArgumentException("FillRamp expects nonzero increment");
		U tmp = alg.construct(startVal);
		long sz = data.size();
		for (long i = 0; i < sz; i++) {
			data.set(i, tmp);
			alg.add().call(tmp, incBy, tmp);
		}
	}

	/**
	 * Fill a list with a steadily increasing set of values. Start at 0 and increment by one.
	 * 
	 * @param alg
	 * @param data
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U>, U>
		void compute(T alg, IndexedDataSource<U> data)
	{
		U zero = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		compute(alg, zero, one, data);
	}
}
