/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Multiplication;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixDirectProduct {

	private MatrixDirectProduct() { }
	
	/**
	 * 
	 * @param Algebra
	 * @param in1
	 * @param in2
	 * @param out
	 */
	public static <T extends Algebra<T,U> & Multiplication<U>,U,W extends MatrixMember<U>>
		void compute(T Algebra, W in1, W in2, W out)
	{
		U tmp1 = Algebra.construct();
		U tmp2 = Algebra.construct();
		U tmp3 = Algebra.construct();
		if (out == in1 || out == in2)
			throw new IllegalArgumentException("output matrix must not be one of input matrices");
		out.alloc(in1.rows() * in2.rows(), in1.cols() * in2.cols());
		for (long r1 = 0; r1 < in1.rows(); r1++) {
			for (long c1 = 0; c1 < in1.cols(); c1++) {
				in1.v(r1, c1, tmp1);
				for (long r2 = 0; r2 < in2.rows(); r2++) {
					for (long c2 = 0; c2 < in2.cols(); c2++) {
						in2.v(r2, c2, tmp2);
						Algebra.multiply().call(tmp1, tmp2, tmp3);
						out.setV(r1*in2.rows()+r2, c1*in2.cols()+c2, tmp3);
					}
				}
			}
		}
	}
}
