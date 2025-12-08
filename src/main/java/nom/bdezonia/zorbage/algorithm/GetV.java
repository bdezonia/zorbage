/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.accessor.*;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * @author Barry DeZonia
 */
public class GetV {

	// do not instantiate

	private GetV() { }

	/**
	 * Get the last value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void last(IndexedDataSource<U> data, U value)
	{
		long sz = data.size();
		if (sz == 0)
			throw new IllegalArgumentException("cannot get last element from empty list");
		data.get(sz-1, value);
	}

	/**
	 * Get the first value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void first(IndexedDataSource<U> data, U value)
	{
		data.get(0, value);
	}

	/**
	 * Get the second value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void second(IndexedDataSource<U> data, U value)
	{
		data.get(1, value);
	}

	/**
	 * Get the third value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void third(IndexedDataSource<U> data, U value)
	{
		data.get(2, value);
	}

	/**
	 * Get the fourth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fourth(IndexedDataSource<U> data, U value)
	{
		data.get(3, value);
	}

	/**
	 * Get the fifth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fifth(IndexedDataSource<U> data, U value)
	{
		data.get(4, value);
	}

	/**
	 * Get the sixth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void sixth(IndexedDataSource<U> data, U value)
	{
		data.get(5, value);
	}

	/**
	 * Get the seventh value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void seventh(IndexedDataSource<U> data, U value)
	{
		data.get(6, value);
	}

	/**
	 * Get the eighth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eighth(IndexedDataSource<U> data, U value)
	{
		data.get(7, value);
	}

	/**
	 * Get the ninth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void ninth(IndexedDataSource<U> data, U value)
	{
		data.get(8, value);
	}

	/**
	 * Get the tenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void tenth(IndexedDataSource<U> data, U value)
	{
		data.get(9, value);
	}

	/**
	 * Get the eleventh value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eleventh(IndexedDataSource<U> data, U value)
	{
		data.get(10, value);
	}

	/**
	 * Get the twelfth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twelfth(IndexedDataSource<U> data, U value)
	{
		data.get(11, value);
	}

	/**
	 * Get the thirteen value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void thirteenth(IndexedDataSource<U> data, U value)
	{
		data.get(12, value);
	}

	/**
	 * Get the fourteenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fourteenth(IndexedDataSource<U> data, U value)
	{
		data.get(13, value);
	}

	/**
	 * Get the fifteenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fifteenth(IndexedDataSource<U> data, U value)
	{
		data.get(14, value);
	}

	/**
	 * Get the sixteenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void sixteenth(IndexedDataSource<U> data, U value)
	{
		data.get(15, value);
	}

	/**
	 * Get the seventeenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void seventeenth(IndexedDataSource<U> data, U value)
	{
		data.get(16, value);
	}

	/**
	 * Get the eighteenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eighteenth(IndexedDataSource<U> data, U value)
	{
		data.get(17, value);
	}

	/**
	 * Get the nineteenth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void nineteenth(IndexedDataSource<U> data, U value)
	{
		data.get(18, value);
	}

	/**
	 * Get the twentieth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentieth(IndexedDataSource<U> data, U value)
	{
		data.get(19, value);
	}

	/**
	 * Get the twenty first value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentyfirst(IndexedDataSource<U> data, U value)
	{
		data.get(20, value);
	}

	/**
	 * Get the twenty second value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentysecond(IndexedDataSource<U> data, U value)
	{
		data.get(21, value);
	}

	/**
	 * Get the twenty third value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentythird(IndexedDataSource<U> data, U value)
	{
		data.get(22, value);
	}

	/**
	 * Get the twenty fourth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentyfourth(IndexedDataSource<U> data, U value)
	{
		data.get(23, value);
	}

	/**
	 * Get the twenty fifth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentyfifth(IndexedDataSource<U> data, U value)
	{
		data.get(24, value);
	}

	/**
	 * Get the twenty sixth value from a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentysixth(IndexedDataSource<U> data, U value)
	{
		data.get(25, value);
	}

	/**
	 * Get the first value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void first(T alg, AccessorA<U> data, U value)
	{
		alg.assign().call(data.a(), value);
	}

	/**
	 * Get the second value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void second(T alg, AccessorB<U> data, U value)
	{
		alg.assign().call(data.b(), value);
	}

	/**
	 * Get the third value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void third(T alg, AccessorC<U> data, U value)
	{
		alg.assign().call(data.c(), value);
	}

	/**
	 * Get the fourth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fourth(T alg, AccessorD<U> data, U value)
	{
		alg.assign().call(data.d(), value);
	}

	/**
	 * Get the fifth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fifth(T alg, AccessorE<U> data, U value)
	{
		alg.assign().call(data.e(), value);
	}

	/**
	 * Get the sixth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void sixth(T alg, AccessorF<U> data, U value)
	{
		alg.assign().call(data.f(), value);
	}

	/**
	 * Get the seventh value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void seventh(T alg, AccessorG<U> data, U value)
	{
		alg.assign().call(data.g(), value);
	}

	/**
	 * Get the eighth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void eighth(T alg, AccessorH<U> data, U value)
	{
		alg.assign().call(data.h(), value);
	}

	/**
	 * Get the ninth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void ninth(T alg, AccessorI<U> data, U value)
	{
		alg.assign().call(data.i(), value);
	}

	/**
	 * Get the tenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void tenth(T alg, AccessorJ<U> data, U value)
	{
		alg.assign().call(data.j(), value);
	}

	/**
	 * Get the eleventh value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void eleventh(T alg, AccessorK<U> data, U value)
	{
		alg.assign().call(data.k(), value);
	}

	/**
	 * Get the twelfth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twelfth(T alg, AccessorL<U> data, U value)
	{
		alg.assign().call(data.l(), value);
	}

	/**
	 * Get the thirteenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void thirteenth(T alg, AccessorM<U> data, U value)
	{
		alg.assign().call(data.m(), value);
	}

	/**
	 * Get the fourteenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fourteenth(T alg, AccessorN<U> data, U value)
	{
		alg.assign().call(data.n(), value);
	}

	/**
	 * Get the fifteenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fifteenth(T alg, AccessorO<U> data, U value)
	{
		alg.assign().call(data.o(), value);
	}

	/**
	 * Get the sixteenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void sixteenth(T alg, AccessorP<U> data, U value)
	{
		alg.assign().call(data.p(), value);
	}

	/**
	 * Get the seventeenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void seventeenth(T alg, AccessorQ<U> data, U value)
	{
		alg.assign().call(data.q(), value);
	}

	/**
	 * Get the eighteenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void eighteenth(T alg, AccessorR<U> data, U value)
	{
		alg.assign().call(data.r(), value);
	}

	/**
	 * Get the nineteenth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void nineteenth(T alg, AccessorS<U> data, U value)
	{
		alg.assign().call(data.s(), value);
	}

	/**
	 * Get the twentieth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentieth(T alg, AccessorT<U> data, U value)
	{
		alg.assign().call(data.t(), value);
	}

	/**
	 * Get the twenty first value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentyfirst(T alg, AccessorU<U> data, U value)
	{
		alg.assign().call(data.u(), value);
	}

	/**
	 * Get the twenty second value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentysecond(T alg, AccessorV<U> data, U value)
	{
		alg.assign().call(data.v(), value);
	}

	/**
	 * Get the twenty third value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentythird(T alg, AccessorW<U> data, U value)
	{
		alg.assign().call(data.w(), value);
	}

	/**
	 * Get the twenty fourth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentyfourth(T alg, AccessorX<U> data, U value)
	{
		alg.assign().call(data.x(), value);
	}

	/**
	 * Get the twenty fifth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentyfifth(T alg, AccessorY<U> data, U value)
	{
		alg.assign().call(data.y(), value);
	}

	/**
	 * Get the twenty sixth value from a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentysixth(T alg, AccessorZ<U> data, U value)
	{
		alg.assign().call(data.z(), value);
	}

}
