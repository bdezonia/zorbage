/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Fill {

	// do not instantiate
	
	private Fill() {}
	
	/**
	 * 
	 * @param Algebra
	 * @param value
	 * @param storage
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T algebra, U value, IndexedDataSource<?,U> storage)
	{
		compute(algebra, value, 0, storage.size(), storage);
	}

	/**
	 * 
	 * @param Algebra
	 * @param storage
	 * @param proc
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T algebra, Procedure1<U> proc, IndexedDataSource<?,U> storage)
	{
		compute(algebra, proc, 0, storage.size(), storage);
	}

	/**
	 * 
	 * @param Algebra
	 * @param storage
	 * @param value
	 * @param start
	 * @param count
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T algebra, U value, long start, long count, IndexedDataSource<?,U> storage)
	{
		for (long i = 0; i < count; i++) {
			storage.set(start+i, value);
		}
	}
	
	/**
	 * 
	 * @param Algebra
	 * @param storage
	 * @param proc
	 * @param start
	 * @param count
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T algebra, Procedure1<U> proc, long start, long count, IndexedDataSource<?,U> storage)
	{
		U tmp = algebra.construct();
		for (long i = 0; i < count; i++) {
			proc.call(tmp);
			storage.set(start+i, tmp);
		}
	}

}
