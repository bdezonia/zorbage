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
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure3;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Reduce {

	// do not instantiate
	
	private Reduce() { }
	
	/**
	 * 
	 * @param alg
	 * @param proc
	 * @param data
	 * @param reduction
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Procedure3<U,U,U> proc, IndexedDataSource<U> data, U reduction)
	{
		U partial = alg.construct();
		U tmp0 = alg.construct();
		U tmp1 = alg.construct();
		long sz = data.size();
		if (sz < 2)
			throw new IllegalArgumentException("must have two or more values before you can reduce");
		data.get(0, tmp0);
		data.get(1, tmp1);
		proc.call(tmp0, tmp1, partial);
		for (int i = 2; i < sz; i++) {
			data.get(i,  tmp0);
			proc.call(partial, tmp0, partial);
		}
		alg.assign().call(partial, reduction);
	}
}
