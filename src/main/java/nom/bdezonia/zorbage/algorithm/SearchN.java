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

import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SearchN {

	/**
	 * 
	 * @param group
	 * @param n
	 * @param value
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, long n, U value, LinearStorage<?,U> a)
	{
		return compute(group, n, value, 0, a.size(), a);
	}

	/**
	 * 
	 * @param group
	 * @param n
	 * @param value
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, long n, U value, long start, long count, LinearStorage<?,U> a)
	{
		U tmpA = group.construct();
	    for (long i = 0; i < count-n; i++) {
	    	for (int j = 0; j < n; j++) {
		    	a.get(start+i+j, tmpA);
	            if (group.isNotEqual(tmpA, value))
	                break;
	            if (j == n-1)
	                return start+i;
	        }
	    }
	    return start+count;
	}

	/**
	 * 
	 * @param group
	 * @param n
	 * @param cond
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, long n, Condition<U> cond, LinearStorage<?,U> a)
	{
		return compute(group, n, cond, 0, a.size(), a);
	}

	/**
	 * 
	 * @param group
	 * @param n
	 * @param cond
	 * @param start
	 * @param count
	 * @param a
	 * @return
	 */
	public static <T extends Group<T,U>, U>
		long compute(T group, long n, Condition<U> cond, long start, long count, LinearStorage<?,U> a)
	{
		U tmpA = group.construct();
	    for (long i = 0; i < count-n; i++) {
	    	for (int j = 0; j < n; j++) {
		    	a.get(start+i+j, tmpA);
	            if (!cond.isTrue(tmpA))
	                break;
	            if (j == n-1)
	                return start+i;
	        }
	    }
	    return start+count;
	}
}
