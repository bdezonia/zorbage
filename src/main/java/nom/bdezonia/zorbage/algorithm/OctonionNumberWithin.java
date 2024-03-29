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
import nom.bdezonia.zorbage.algebra.GetOctonion;
import nom.bdezonia.zorbage.algebra.Ordered;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionNumberWithin {

	// do not instantiate
	
	private OctonionNumberWithin() { }
		
	/**
	 * 
	 * @param singleAlg
	 * @param a
	 * @param b
	 * @param tol
	 * @return
	 */
	public static <U extends GetOctonion<W>,
					V extends Algebra<V,W> & Ordered<W> & Addition<W>,
					W>
		boolean compute(V singleAlg, W tol, U a, U b)
	{
		W elemA = singleAlg.construct();
		W elemB = singleAlg.construct();
		a.getR(elemA);
		b.getR(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getI(elemA);
		b.getI(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getJ(elemA);
		b.getJ(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getK(elemA);
		b.getK(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getL(elemA);
		b.getL(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getI0(elemA);
		b.getI0(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getJ0(elemA);
		b.getJ0(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		a.getK0(elemA);
		b.getK0(elemB);
		if (!NumberWithin.compute(singleAlg, tol, elemA, elemB))
			return false;
		return true;
	}

}
