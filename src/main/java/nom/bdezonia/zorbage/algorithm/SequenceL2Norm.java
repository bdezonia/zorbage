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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SequenceL2Norm {

	private SequenceL2Norm() { }
	
	/**
	 * 
	 * @param normAlgebra
	 * @param numAlgebra
	 * @param seq
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Norm<U,W>, U, V extends Algebra<V,W> & Addition<W> & Multiplication<W> & Roots<W> & Ordered<W>& Invertible<W>, W>
		void compute(T normAlgebra, V numAlgebra, IndexedDataSource<U> seq, W result)
	{
		U value = normAlgebra.construct();
		W sum = numAlgebra.construct();
		W tmp = numAlgebra.construct();
		W max = numAlgebra.construct();
		for (long i = 0; i < seq.size(); i++) {
			seq.get(i, value);
			normAlgebra.norm().call(value, tmp);
			Max.compute(numAlgebra, max, tmp, max);
		}
		if (numAlgebra.isZero().call(max)) {
			numAlgebra.zero().call(result);
			return;
		}
		for (long i = 0; i < seq.size(); i++) {
			seq.get(i, value);
			normAlgebra.norm().call(value, tmp);
			numAlgebra.divide().call(tmp, max, tmp);
			numAlgebra.multiply().call(tmp, tmp, tmp);
			numAlgebra.add().call(sum, tmp, sum);
		}
		numAlgebra.sqrt().call(sum, sum);
		numAlgebra.multiply().call(sum, max, sum);
		numAlgebra.assign().call(sum, result);
	}
}
