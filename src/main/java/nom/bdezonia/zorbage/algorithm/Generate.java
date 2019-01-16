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

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

public class Generate {

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param storage
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T group, Procedure1<U> proc, IndexedDataSource<?,U> storage)
	{
		compute(group, proc, 0, storage.size(), 1, storage);
	}

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param storage
	 * @param inputs
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Algebra<T,U>,U>
		void compute(T group, Procedure<U> proc, IndexedDataSource<?,U> storage, U... inputs)
	{
		compute(group, proc, 0, storage.size(), 1, storage, inputs);
	}

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param start
	 * @param count
	 * @param stride
	 * @param storage
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T group, Procedure1<U> proc, long start, long count, long stride, IndexedDataSource<?,U> storage)
	{
		U value = group.construct();
		for (long i = start, c = 0; c < count; c++) {
			proc.call(value);
			storage.set(i, value);
			i += stride;
		}
	}

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param start
	 * @param count
	 * @param stride
	 * @param storage
	 * @param inputs
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Algebra<T,U>,U>
		void compute(T group, Procedure<U> proc, long start, long count, long stride, IndexedDataSource<?,U> storage, U... inputs)
	{
		U value = group.construct();
		for (long i = start, c = 0; c < count; c++) {
			proc.call(value, inputs);
			storage.set(i, value);
			i += stride;
		}
	}

}
