/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class AdjacentFind {

	private AdjacentFind() {}

	/**
	 * 
	 * @param group
	 * @param a
	 * @param value
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, LinearStorage<?,U> a)
	{
		return compute(group, a, 0, a.size());
	}
	
	/**
	 * 
	 * @param group
	 * @param a
	 * @param value
	 * @param start
	 * @param count
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, LinearStorage<?,U> a, long start, long count)
	{
		if (start+count < 2) return start+count;
		U tmp1 = group.construct();
		U tmp2 = group.construct();
		a.get(start, tmp1);
		for (long i = 1; i < count; i++) {
			a.get(start+i, tmp2);
			if (group.isEqual(tmp1, tmp2))
				return start + i - 1;
			group.assign(tmp2, tmp1);
		}
		return start + count;
	}
}
