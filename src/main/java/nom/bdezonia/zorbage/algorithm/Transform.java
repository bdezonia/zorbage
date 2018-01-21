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

import nom.bdezonia.zorbage.basic.procedure.Procedure2;
import nom.bdezonia.zorbage.basic.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Transform {

	// do not instantiate
	
	private Transform() {}

	/**
	 * Transform the data of one complete list in place.
	 * 
	 * @param grpU
	 * @param a
	 * @param proc
	 */
	public static <T extends Group<T,U>, U>
		void compute(T grpU, Procedure2<U,U> proc, IndexedDataSource<?,U> a)
	{
		compute(grpU, proc, 0, a.size(), a);
	}

	/**
	 * Transform a portion of the data of one list in place
	 * 
	 * @param grpU
	 * @param proc
	 * @param a
	 * @param start
	 * @param count
	 */
	public static <T extends Group<T,U>, U>
		void compute(T grpU, Procedure2<U,U> proc, long start, long count, IndexedDataSource<?,U> a)
	{
		compute(grpU, proc, start, start, count, a, a);
	}

	/**
	 * Transform the data of one complete list into the data of another
	 * list.
	 * 
	 * @param grpU
	 * @param a
	 * @param b
	 * @param proc
	 */
	public static <T extends Group<T,U>, U>
		void compute(T grpU, Procedure2<U,U> proc, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
	{
		compute(grpU, proc, 0, 0, a.size(), a, b);
	}

	/**
	 * Transform the data of a portion of one list into the data of
	 * another list.
	 * 
	 * @param grpU
	 * @param proc
	 * @param aStart
	 * @param bStart
	 * @param count
	 * @param a
	 * @param b
	 */
	public static <T extends Group<T,U>, U>
		void compute(T grpU, Procedure2<U,U> proc, long aStart, long bStart, long count, IndexedDataSource<?,U> a, IndexedDataSource<?,U> b)
	{
		U tmpU = grpU.construct();
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmpU);
			proc.call(tmpU, tmpU);
			b.set(bStart+i, tmpU);
		}
	}

	/**
	 * Transform the data from one complete list to another list. Lists
	 * can be of differing types.
	 * 
	 * @param grpU
	 * @param grpW
	 * @param a
	 * @param b
	 * @param proc
	 */
	public static <T extends Group<T,U>, U extends PrimitiveConversion, V extends Group<V,W>, W extends PrimitiveConversion>
		void compute(T grpU, V grpW, Procedure2<U, W> proc, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b)
	{
		compute(grpU, grpW, proc, 0, 0, a.size(), a, b);
	}
	
	/**
	 * Transform the data from a subrange of one list to a subrange
	 * of another list. Lists can be of differing types.
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
	public static <T extends Group<T,U>, U extends PrimitiveConversion, V extends Group<V,W>, W extends PrimitiveConversion>
		void compute(T grpU, V grpW, Procedure2<U, W> proc, long aStart, long bStart, long count, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b)
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
	 * Transform the data from two complete lists to another list.
	 * Lists can be of differing types.
	 * 
	 * @param grpU
	 * @param grpW
	 * @param grpY
	 * @param a
	 * @param b
	 * @param c
	 * @param proc
	 */
	public static <T extends Group<T,U>, U extends PrimitiveConversion, V extends Group<V,W>, W extends PrimitiveConversion, X extends Group<X,Y>, Y extends PrimitiveConversion>
		void compute(T grpU, V grpW, X grpY, Procedure3<U, W, Y> proc, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b, IndexedDataSource<?,Y> c)
	{
		compute(grpU, grpW, grpY, proc, 0, 0, 0, a.size(), a, b, c);
	}

	/**
	 * Transform the data from subranges of two lists to a subrange
	 * of another list. Lists can be of differing types.
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
	public static <T extends Group<T,U>, U extends PrimitiveConversion, V extends Group<V,W>, W extends PrimitiveConversion, X extends Group<X,Y>, Y extends PrimitiveConversion>
		void compute(T grpU, V grpW, X grpY, Procedure3<U,W,Y> proc, long aStart, long bStart, long cStart, long count, IndexedDataSource<?,U> a, IndexedDataSource<?,W> b, IndexedDataSource<?,Y> c)
	{
		U tmpU = grpU.construct();
		W tmpW = grpW.construct();
		Y tmpY = grpY.construct();
		for (long i = 0; i < count; i++) {
			a.get(aStart+i, tmpU);
			b.get(bStart+i, tmpW);
			proc.call(tmpU, tmpW, tmpY);
			c.set(cStart+i, tmpY);
		}
	}
}
