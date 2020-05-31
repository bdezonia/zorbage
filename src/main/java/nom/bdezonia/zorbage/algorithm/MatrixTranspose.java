/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.MatrixMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixTranspose {

	// do not instantiate
	
	private MatrixTranspose() {}
	
	/**
	 * 
	 * @param algebra
	 * @param a
	 * @param b
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T algebra, MatrixMember<U> a, MatrixMember<U> b)
	{
		U value1 = algebra.construct();
		U value2 = algebra.construct();
		if (a == b) {
			if (a.rows() != a.cols())
				throw new IllegalArgumentException("in place transpose only works for square matrices");
			// else can transpose square mats in place
			for (long d = 1; d < a.rows(); d++) {
				for (long c = 1; c <= d; c++) {
					a.getV(d-c, d, value1);
					a.getV(d, d-c, value2);
					a.setV(d-c, d, value2);
					a.setV(d, d-c, value1);
				}
			}
		}
		else { // the output b is different from the input a
			b.alloc(a.cols(), a.rows());
			for (long r = 0; r < a.rows(); r++) {
				for (long c = 0; c < a.cols(); c++) {
					a.getV(r, c, value1);
					b.setV(c, r, value1);
				}
			}
		}
	}
}
