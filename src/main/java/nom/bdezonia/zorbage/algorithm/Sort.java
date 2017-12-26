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
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Sort {

	private Sort() {}
	
	/**
	 * 
	 * @param grp
	 * @param storage
	 */
	public static <T extends Group<T,U> & Ordered<U> ,U>
		void compute(T grp, LinearStorage<?,U> storage)
	{
		qsort(grp, storage, 0, storage.size() -1);
	}
	
	private static <T extends Group<T,U> & Ordered<U> ,U>
		void qsort(T grp, LinearStorage<?,U> storage, long left, long right)
	{
		if (left < right) {
			long pivotPoint = partition(grp, storage,left,right);
			qsort(grp, storage,left,pivotPoint-1);
			qsort(grp, storage,pivotPoint+1,right);
		}
	}


	private static <T extends Group<T,U> & Ordered<U> ,U>
		long partition(T grp, LinearStorage<?,U> storage, long left, long right)
	{
		U tmp1 = grp.construct();
		U tmp2 = grp.construct();
		
		U pivotValue = grp.construct();
		storage.get(left, pivotValue);

		long leftmark = left+1;
		long rightmark = right;
	
		boolean done = false;
		while (!done) {
	
			while (true) {
				if (leftmark > rightmark) break;
				storage.get(leftmark, tmp1);
				if (grp.isGreater(tmp1, pivotValue)) break;
				leftmark++;
			}
	
			while (true) {
				storage.get(rightmark, tmp1);
				if (grp.isLess(tmp1, pivotValue)) break;
				if (rightmark < leftmark) break;
				rightmark--;
			}
	
			if (rightmark < leftmark)
				done = true;
			else {
				storage.get(leftmark, tmp1);
				storage.get(rightmark, tmp2);
				storage.set(leftmark,tmp2);
				storage.set(rightmark, tmp1);
			}
		}
		storage.get(left, tmp1);
		storage.get(rightmark, tmp2);
		storage.set(left,tmp2);
		storage.set(rightmark, tmp1);

		return rightmark;
	}
}
