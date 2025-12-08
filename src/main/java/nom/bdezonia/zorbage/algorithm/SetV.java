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
public class SetV {

	// do not instantiate

	private SetV() { }

	/**
	 * Set the last value of a list.
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
			throw new IllegalArgumentException("cannot set last element of empty list");
		data.set(sz-1, value);
	}

	/**
	 * Set the first value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void first(IndexedDataSource<U> data, U value)
	{
		data.set(0, value);
	}

	/**
	 * Set the second value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void second(IndexedDataSource<U> data, U value)
	{
		data.set(1, value);
	}

	/**
	 * Set the third value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void third(IndexedDataSource<U> data, U value)
	{
		data.set(2, value);
	}

	/**
	 * Set the fourth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fourth(IndexedDataSource<U> data, U value)
	{
		data.set(3, value);
	}

	/**
	 * Set the fifth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fifth(IndexedDataSource<U> data, U value)
	{
		data.set(4, value);
	}

	/**
	 * Set the sixth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void sixth(IndexedDataSource<U> data, U value)
	{
		data.set(5, value);
	}

	/**
	 * Set the seventh value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void seventh(IndexedDataSource<U> data, U value)
	{
		data.set(6, value);
	}

	/**
	 * Set the eighth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eighth(IndexedDataSource<U> data, U value)
	{
		data.set(7, value);
	}

	/**
	 * Set the ninth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void ninth(IndexedDataSource<U> data, U value)
	{
		data.set(8, value);
	}

	/**
	 * Set the tenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void tenth(IndexedDataSource<U> data, U value)
	{
		data.set(9, value);
	}

	/**
	 * Set the eleventh value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eleventh(IndexedDataSource<U> data, U value)
	{
		data.set(10, value);
	}

	/**
	 * Set the twelfth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twelfth(IndexedDataSource<U> data, U value)
	{
		data.set(11, value);
	}

	/**
	 * Set the thirteenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void thirteenth(IndexedDataSource<U> data, U value)
	{
		data.set(12, value);
	}

	/**
	 * Set the fourteenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fourteenth(IndexedDataSource<U> data, U value)
	{
		data.set(13, value);
	}

	/**
	 * Set the fifteenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fifteenth(IndexedDataSource<U> data, U value)
	{
		data.set(14, value);
	}

	/**
	 * Set the sixteenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void sixteenth(IndexedDataSource<U> data, U value)
	{
		data.set(15, value);
	}

	/**
	 * Set the seventeenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void seventeenth(IndexedDataSource<U> data, U value)
	{
		data.set(16, value);
	}

	/**
	 * Set the eighteenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eighteenth(IndexedDataSource<U> data, U value)
	{
		data.set(17, value);
	}

	/**
	 * Set the nineteenth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void nineteenth(IndexedDataSource<U> data, U value)
	{
		data.set(18, value);
	}

	/**
	 * Set the twentieth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentieth(IndexedDataSource<U> data, U value)
	{
		data.set(19, value);
	}

	/**
	 * Set the twenty first value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentyfirst(IndexedDataSource<U> data, U value)
	{
		data.set(20, value);
	}

	/**
	 * Set the twenty second value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentysecond(IndexedDataSource<U> data, U value)
	{
		data.set(21, value);
	}

	/**
	 * Set the twenty third value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentythird(IndexedDataSource<U> data, U value)
	{
		data.set(22, value);
	}

	/**
	 * Set the twenty fourth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentyfourth(IndexedDataSource<U> data, U value)
	{
		data.set(23, value);
	}

	/**
	 * Set the twenty fifth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentyfifth(IndexedDataSource<U> data, U value)
	{
		data.set(24, value);
	}

	/**
	 * Set the twenty sixth value of a list.
	 * 
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentysixth(IndexedDataSource<U> data, U value)
	{
		data.set(25, value);
	}

	/**
	 * Set the first value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void first(T alg, AccessorA<U> data, U value)
	{
		alg.assign().call(value, data.a());
	}

	/**
	 * Set the second value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void second(T alg, AccessorB<U> data, U value)
	{
		alg.assign().call(value, data.b());
	}

	/**
	 * Set the third value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void third(T alg, AccessorC<U> data, U value)
	{
		alg.assign().call(value, data.c());
	}

	/**
	 * Set the fourth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fourth(T alg, AccessorD<U> data, U value)
	{
		alg.assign().call(value, data.d());
	}

	/**
	 * Set the fifth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fifth(T alg, AccessorE<U> data, U value)
	{
		alg.assign().call(value, data.e());
	}

	/**
	 * Set the sixth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void sixth(T alg, AccessorF<U> data, U value)
	{
		alg.assign().call(value, data.f());
	}

	/**
	 * Set the seventh value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void seventh(T alg, AccessorG<U> data, U value)
	{
		alg.assign().call(value, data.g());
	}

	/**
	 * Set the eighth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void eighth(T alg, AccessorH<U> data, U value)
	{
		alg.assign().call(value, data.h());
	}

	/**
	 * Set the ninth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void ninth(T alg, AccessorI<U> data, U value)
	{
		alg.assign().call(value, data.i());
	}

	/**
	 * Set the tenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void tenth(T alg, AccessorJ<U> data, U value)
	{
		alg.assign().call(value, data.j());
	}

	/**
	 * Set the eleventh value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void eleventh(T alg, AccessorK<U> data, U value)
	{
		alg.assign().call(value, data.k());
	}

	/**
	 * Set the twelfth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twelfth(T alg, AccessorL<U> data, U value)
	{
		alg.assign().call(value, data.l());
	}

	/**
	 * Set the thirteenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void thirteenth(T alg, AccessorM<U> data, U value)
	{
		alg.assign().call(value, data.m());
	}

	/**
	 * Set the fourteenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fourteenth(T alg, AccessorN<U> data, U value)
	{
		alg.assign().call(value, data.n());
	}

	/**
	 * Set the fifteenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void fifteenth(T alg, AccessorO<U> data, U value)
	{
		alg.assign().call(value, data.o());
	}

	/**
	 * Set the sixteenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void sixteenth(T alg, AccessorP<U> data, U value)
	{
		alg.assign().call(value, data.p());
	}

	/**
	 * Set the seventeenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void seventeenth(T alg, AccessorQ<U> data, U value)
	{
		alg.assign().call(value, data.q());
	}

	/**
	 * Set the eighteenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void eighteenth(T alg, AccessorR<U> data, U value)
	{
		alg.assign().call(value, data.r());
	}

	/**
	 * Set the nineteenth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void nineteenth(T alg, AccessorS<U> data, U value)
	{
		alg.assign().call(value, data.s());
	}

	/**
	 * Set the twentieth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentieth(T alg, AccessorT<U> data, U value)
	{
		alg.assign().call(value, data.t());
	}

	/**
	 * Set the twenty first value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentyfirst(T alg, AccessorU<U> data, U value)
	{
		alg.assign().call(value, data.u());
	}

	/**
	 * Set the twenty second value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentysecond(T alg, AccessorV<U> data, U value)
	{
		alg.assign().call(value, data.v());
	}

	/**
	 * Set the twenty third value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentythird(T alg, AccessorW<U> data, U value)
	{
		alg.assign().call(value, data.w());
	}

	/**
	 * Set the twenty fourth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentyfourth(T alg, AccessorX<U> data, U value)
	{
		alg.assign().call(value, data.x());
	}

	/**
	 * Set the twenty fifth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentyfifth(T alg, AccessorY<U> data, U value)
	{
		alg.assign().call(value, data.y());
	}

	/**
	 * Set the twenty sixth value of a tuple.
	 * 
	 * @param alg
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <T extends Algebra<T,U>, U>
	void twentysixth(T alg, AccessorZ<U> data, U value)
	{
		alg.assign().call(value, data.z());
	}

}
