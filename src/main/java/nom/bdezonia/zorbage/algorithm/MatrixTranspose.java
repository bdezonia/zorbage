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

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixTranspose {

	// do not instantiate
	
	private MatrixTranspose() {}
	
	public static <T extends Group<T,U>,U>
		void compute(T group, MatrixMember<U> a, MatrixMember<U> b)
	{
		U value = group.construct();
		U value2 = group.construct();
		if (a == b) {
			// can transpose square mats in place
			if (a.rows() == a.cols()) {
				for (long d = 1; d < a.rows(); d++) {
					for (long c = 1; c <= d; c++) {
						a.v(d-c, d, value);
						a.v(d, d-c, value2);
						a.setV(d-c, d, value2);
						a.setV(d, d-c, value);
					}
				}
				return;
			}
			throw new IllegalArgumentException("in place transpose only works for square matrices");
		}
		b.alloc(a.cols(), a.rows());
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r,  c, value);
				b.setV(c, r, value);
			}
		}
	}
}
