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
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.Multiplication;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixMultiply {

	// do not instantiate
	
	private MatrixMultiply() {}
	
	/**
	 * Multiply matrices: c = a x b
	 * 
	 * @param algebra
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U>,U>
		void compute(T algebra, MatrixMember<U> a, MatrixMember<U> b, MatrixMember<U> c)
	{
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		long rows = a.rows();
		long cols = b.cols();
		long common = a.cols(); 
		c.alloc(rows, cols);
		U sum = algebra.construct();
		U atmp = algebra.construct();
		U btmp = algebra.construct();
		U term = algebra.construct();
		for (long row = 0; row < rows; row++) {
			for (long col = 0; col < cols; col++) {
				algebra.zero().call(sum);
				for (long i = 0; i < common; i++) {
					a.getV(row, i, atmp);
					b.getV(i, col, btmp);
					algebra.multiply().call(atmp, btmp, term);
					algebra.add().call(sum, term, sum);
				}
				c.setV(row, col, sum);
			}
		}
	}
}
