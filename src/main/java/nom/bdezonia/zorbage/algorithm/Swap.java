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
public class Swap {

	// do not instantiate
	
	private Swap() {}

	/**
	 * 
	 * @param group
	 * @param a
	 * @param b
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, LinearStorage<?,U> a, LinearStorage<?,U> b)
	{
		compute(group, a, b, 0, 0, a.size());
	}
	
	/**
	 * 
	 * @param group
	 * @param a
	 * @param b
	 * @param aStart
	 * @param bStart
	 * @param count
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, LinearStorage<?,U> a, LinearStorage<?,U> b, long aStart, long bStart, long count)
	{
		U tmp1 = group.construct();
		U tmp2 = group.construct();
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmp1);
			b.get(bStart+i, tmp2);
			a.set(aStart+i, tmp2);
			b.set(bStart+i, tmp1);
		}
	}

}
