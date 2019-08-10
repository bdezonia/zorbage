/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.GetComplex;
import nom.bdezonia.zorbage.type.algebra.SetOctonion;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFromComplex {

	private OctonionFromComplex() { }
	
	/**
	 * 
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 * @param out
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, GetComplex<U> c1, GetComplex<U> c2, GetComplex<U> c3, GetComplex<U> c4, SetOctonion<U> out)
	{
		U tmp = alg.construct();
		c1.getR(tmp);
		out.setR(tmp);
		c1.getI(tmp);
		out.setI(tmp);
		c2.getR(tmp);
		out.setJ(tmp);
		c2.getI(tmp);
		out.setK(tmp);
		c3.getR(tmp);
		out.setL(tmp);
		c3.getI(tmp);
		out.setI0(tmp);
		c4.getR(tmp);
		out.setJ0(tmp);
		c4.getI(tmp);
		out.setK0(tmp);

	}
	
}
