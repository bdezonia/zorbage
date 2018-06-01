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
import nom.bdezonia.zorbage.type.algebra.NumberMember;
import nom.bdezonia.zorbage.type.algebra.Rounding;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixRound {

	private MatrixRound() { }
	
	public static <T extends Group<T,U>, U, V extends Group<V,W> & Rounding<U,W>, W extends NumberMember<W>>
		void compute(T numGroup, V entityGroup, Round.Mode mode, U delta, MatrixMember<W> a, MatrixMember<W> b)
	{
		if (a != b)
			b.alloc(a.rows(), a.cols());
		W tmp = entityGroup.construct();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				entityGroup.round(mode, delta, tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}
}
