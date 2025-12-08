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
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorIsUnity {

	// do not instantiate
	
	private TensorIsUnity() { }
	
	/**
	 * Returns true if a given tensor has all ones along it's super diagonal
	 * 
	 * @param alg
	 * @param a
	 * @return
	 */
	public static <T extends Algebra<T,U> & Unity<U>, U>
		boolean compute(T alg, TensorMember<U> a)
	{
		int numD = a.numDimensions();
		IntegerIndex idx = new IntegerIndex(numD);
		U value = alg.construct();
		if (numD == 0) {
			// a number
			a.getV(idx, value);
			return alg.isUnity().call(value);
		}
		if (numD == 1) {
			// a vector
			if (a.dimension(0) != 1)
				return false;
			// a number
			a.getV(idx, value);
			return alg.isUnity().call(value);
		}
		// if here than numD is 2 or more
		for (int i = 0; i < numD; i++) {
			if (a.dimension(i) < 1)
				return false;
		}
		// note that we do not require perfectly shaped tensors. many things can be unity-like.
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(a);
		while (iter.hasNext()) {
			iter.next(idx);
			a.getV(idx, value);
			boolean onDiag = true;
			long tmp = idx.get(0);
			for (int i = 1; i < idx.numDimensions(); i++) {
				if (idx.get(i) != tmp)
					onDiag = false;
			}
			if (onDiag) {
				if (!alg.isUnity().call(value))
					return false;
			}
			else {
				if (!alg.isZero().call(value))
					return false;
			}
		}
		return true;
	}
}
