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

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class IsPartitioned {

	/**
	 * 
	 * @param group
	 * @param cond
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		boolean compute(T group, Condition<U> cond, IndexedDataSource<?,U> a)
	{
		return compute(group, cond, 0, a.size(), a);
	}

	/**
	 * 
	 * @param group
	 * @param cond
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		boolean compute(T group, Condition<U> cond, long start, long count, IndexedDataSource<?,U> a)
	{
		U tmp = group.construct();
		long i = 0;
		while (i < count) {
			a.get(start+i, tmp);
			if (!cond.isTrue(tmp)) break;
			i++;
		}
		while (i < count) {
			a.get(start+i, tmp);
			i++;
			if (cond.isTrue(tmp)) return false;
		}
		return true;
	}
}
