/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class InvFFT {

	// do not instantiate
	
	private InvFFT() {}

	/**
	 * 
	 * @param a
	 * @param b
	 */
	public static
		void compute(IndexedDataSource<?,ComplexFloat64Member> a,IndexedDataSource<?,ComplexFloat64Member> b)
	{
		if (a.size() != FFT.enclosingPowerOf2(a.size()))
			throw new IllegalArgumentException("input size is not a power of 2");
		if (a.size() != b.size())
			throw new IllegalArgumentException("output size does not match input size");
		ComplexFloat64Member one_over_n =
			new ComplexFloat64Member(1.0/a.size(),0);
		Conjugate.compute(G.CDBL, a, b);
		FFT.compute(G.CDBL, b, b); // TODO: does this work in place?
		Conjugate.compute(G.CDBL, b, b);
		Scale.compute(G.CDBL, one_over_n, b, b);
	}
}
