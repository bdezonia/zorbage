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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.Multiplication;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixDirectProduct {

	// do not instantiate
	
	private MatrixDirectProduct() { }
	
	/**
	 * Calculate the direct product between two matrices and put in an output matrix.
	 * 
	 * @param algebra
	 * @param in1
	 * @param in2
	 * @param out
	 */
	public static <T extends Algebra<T,U> & Multiplication<U>, U, W extends MatrixMember<U>>
		void compute(T algebra, W in1, W in2, W out)
	{
		U tmp1 = algebra.construct();
		U tmp2 = algebra.construct();
		U tmp3 = algebra.construct();
		if (out == in1 || out == in2)
			throw new IllegalArgumentException("output matrix must not be one of input matrices");
		out.alloc(in1.rows() * in2.rows(), in1.cols() * in2.cols());
		for (long r1 = 0; r1 < in1.rows(); r1++) {
			for (long c1 = 0; c1 < in1.cols(); c1++) {
				in1.getV(r1, c1, tmp1);
				for (long r2 = 0; r2 < in2.rows(); r2++) {
					for (long c2 = 0; c2 < in2.cols(); c2++) {
						in2.getV(r2, c2, tmp2);
						algebra.multiply().call(tmp1, tmp2, tmp3);
						out.setV(r1*in2.rows()+r2, c1*in2.cols()+c2, tmp3);
					}
				}
			}
		}
	}
}
