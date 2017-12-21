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

import nom.bdezonia.zorbage.basic.procedure.Procedure2;
import nom.bdezonia.zorbage.basic.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform {

	// do not instantiate
	
	private Transform() {}

	/**
	 * Transform the data from one complete list to another complete list.
	 * Source and destination lists can be the same.
	 * 
	 * @param grpU
	 * @param grpW
	 * @param a
	 * @param b
	 * @param proc
	 */
	public static <T extends Group<T,U>, U, V extends Group<V,W>, W>
		void compute(T grpU, V grpW, LinearStorage<?,U> a, LinearStorage<?,W> b, Procedure2<U, W> proc)
	{
		compute(grpU, grpW, a, b, proc, 0, 0, a.size());
	}
	
	/**
	 * Transform the data from two complete lists to another complete list.
	 * Source and destination lists can be the same.
	 * 
	 * @param grpU
	 * @param grpW
	 * @param grpY
	 * @param a
	 * @param b
	 * @param c
	 * @param proc
	 */
	public static <T extends Group<T,U>, U, V extends Group<V,W>, W, X extends Group<X,Y>, Y>
		void compute(T grpU, V grpW, X grpY, LinearStorage<?,U> a, LinearStorage<?,W> b, LinearStorage<?,Y> c, Procedure3<U, W, Y> proc)
	{
		compute(grpU, grpW, grpY, a, b, c, proc, 0, 0, 0, a.size());
	}

	/**
	 * Transform the data from a subrange of one list to another subrange
	 * of another list. Source and destination lists can be the same.
	 * 
	 * @param grpU
	 * @param grpW
	 * @param a
	 * @param b
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 */
	public static <T extends Group<T,U>,U,V extends Group<V,W>,W>
		void compute(T grpU, V grpW, LinearStorage<?,U> a, LinearStorage<?,W> b, Procedure2<U, W> proc, long aStart, long bStart, long count)
	{
		U tmpU = grpU.construct();
		W tmpW = grpW.construct();
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmpU);
			proc.call(tmpU, tmpW);
			b.set(bStart+i, tmpW);
		}
	}

	/**
	 * Transform the data from subranges of two lists to another subrange
	 * of another list. Source and destination lists can be the same.
	 * 
	 * @param grpU
	 * @param grpW
	 * @param grpX
	 * @param a
	 * @param b
	 * @param c
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param cStart
	 * @param count
	 */
	public static <T extends Group<T,U>, U, V extends Group<V,W>, W, X extends Group<X,Y>, Y>
		void compute(T grpU, V grpW, X grpX, LinearStorage<?,U> a, LinearStorage<?,W> b, LinearStorage<?,Y> c, Procedure3<U,W,Y> proc, long aStart, long bStart, long cStart, long count)
	{
		U tmpU = grpU.construct();
		W tmpW = grpW.construct();
		Y tmpY = grpX.construct();
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmpU);
			b.get(bStart+i, tmpW);
			proc.call(tmpU, tmpW, tmpY);
			c.set(cStart+i, tmpY);
		}
	}
}
