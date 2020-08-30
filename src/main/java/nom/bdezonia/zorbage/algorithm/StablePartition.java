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
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class StablePartition {

	private StablePartition() { }
	
	/**
	 * 
	 * @param alg
	 * @param cond
	 * @param storage
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Function1<Boolean,U> cond, IndexedDataSource<U> storage)
	{
		partition(alg, cond, 0, storage.size()-1, storage);
	}

	private static <T extends Algebra<T,U> ,U>
		long partition(T alg, Function1<Boolean,U> cond, long left, long right, IndexedDataSource<U> storage)
	{
		U value = alg.construct();
		long firstTrue, numTrue, endFalse;
		while (true)
		{
			firstTrue = -1;
			numTrue = 0;
			endFalse = -1;
			boolean stop = true;
			for (long i = left; i <= right; i++) // TODO - is this now off by one?
			{
				storage.get(i, value);
				if (!cond.call(value))
				{
					//hasMetTrue = true;
					if (endFalse == -1)
					{
						firstTrue = firstTrue == -1 ? i : firstTrue;
						numTrue++;
					}
					else
					{
						// TODO: do I have a bunch of off by ones here?
						reverse(alg, firstTrue, endFalse, storage);
						reverse(alg, firstTrue, endFalse - numTrue, storage);
						reverse(alg, endFalse - numTrue + 1, endFalse, storage);
						firstTrue = -1;
						numTrue = 0;
						endFalse = -1;
					}
				}
				else if (firstTrue > -1)
				{
					stop = false;
					endFalse = i;
				}
			}
			//to handle the case where the end of the array is false
			if (firstTrue > 0)
			{
				// TODO: do I have a bunch of off by ones here?
				reverse(alg, firstTrue, right, storage);
				reverse(alg, firstTrue, right - numTrue, storage);
				reverse(alg, right - numTrue + 1, right, storage);
			}
			if (stop)
				break;
		}
		return firstTrue + numTrue - 1; // TODO: my guess at a correct partition point
	}

	private static <T extends Algebra<T,U>, U>
		void reverse(T alg, long start, long end, IndexedDataSource<U> storage)
	{
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		while(start < end)
		{
			storage.get(start, tmp1);
			storage.get(end, tmp2);
			storage.set(start, tmp2);
			storage.set(end, tmp1);
			start++;
			end--;
		}
	}
}
