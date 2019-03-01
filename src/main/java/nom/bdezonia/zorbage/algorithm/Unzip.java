/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.tuple.Tuple1;
import nom.bdezonia.zorbage.tuple.Tuple10;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.tuple.Tuple3;
import nom.bdezonia.zorbage.tuple.Tuple4;
import nom.bdezonia.zorbage.tuple.Tuple5;
import nom.bdezonia.zorbage.tuple.Tuple6;
import nom.bdezonia.zorbage.tuple.Tuple7;
import nom.bdezonia.zorbage.tuple.Tuple8;
import nom.bdezonia.zorbage.tuple.Tuple9;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Unzip {

	/**
	 * 
	 * @param alg1
	 * @param zipped
	 * @param unzip1
	 */
	public static <A extends Algebra<A,B>, B>
		void one(A alg1,
			IndexedDataSource<?,Tuple1<B>> zipped,
			IndexedDataSource<?,B> unzip1)
	{
		Tuple1<B> tuple = new Tuple1<B>(null);
		tuple.setA(alg1.construct());
		long zippedSize = zipped.size();
		if (unzip1.size() != zippedSize)
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
		}
	}
		
	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 */
	public static <A extends Algebra<A,B>, B,
					C extends Algebra<C,D>, D>
		void two(A alg1, C alg2,
					IndexedDataSource<?,Tuple2<B,D>> zipped,
					IndexedDataSource<?,B> unzip1,
					IndexedDataSource<?,D> unzip2)
	{
		Tuple2<B,D> tuple = new Tuple2<B,D>(null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) || (unzip2.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
		}
	}

	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 */
	public static <A extends Algebra<A,B>, B,
					C extends Algebra<C,D>, D,
					E extends Algebra<E,F>, F>
		void three(A alg1, C alg2, E alg3,
					IndexedDataSource<?,Tuple3<B,D,F>> zipped,
					IndexedDataSource<?,B> unzip1,
					IndexedDataSource<?,D> unzip2,
					IndexedDataSource<?,F> unzip3)
	{
		Tuple3<B,D,F> tuple = new Tuple3<B,D,F>(null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
		}
	}

	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 */
	public static <A extends Algebra<A,B>, B,
					C extends Algebra<C,D>, D,
					E extends Algebra<E,F>, F,
					G extends Algebra<G,H>, H>
		void four(A alg1, C alg2, E alg3, G alg4,
					IndexedDataSource<?,Tuple4<B,D,F,H>> zipped,
					IndexedDataSource<?,B> unzip1,
					IndexedDataSource<?,D> unzip2,
					IndexedDataSource<?,F> unzip3,
					IndexedDataSource<?,H> unzip4)
	{
		Tuple4<B,D,F,H> tuple = new Tuple4<B,D,F,H>(null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
		}
	}
	
	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param alg5
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 * @param unzip5
	 */
	public static <A extends Algebra<A,B>, B,
					C extends Algebra<C,D>, D,
					E extends Algebra<E,F>, F,
					G extends Algebra<G,H>, H,
					I extends Algebra<I,J>, J>
		void five(A alg1, C alg2, E alg3, G alg4, I alg5,
					IndexedDataSource<?,Tuple5<B,D,F,H,J>> zipped,
					IndexedDataSource<?,B> unzip1,
					IndexedDataSource<?,D> unzip2,
					IndexedDataSource<?,F> unzip3,
					IndexedDataSource<?,H> unzip4,
					IndexedDataSource<?,J> unzip5)
	{
		Tuple5<B,D,F,H,J> tuple = new Tuple5<B,D,F,H,J>(null, null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		tuple.setE(alg5.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize) ||
				(unzip5.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
			unzip5.set(i, tuple.e());
		}
	}

	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param alg5
	 * @param alg6
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 * @param unzip5
	 * @param unzip6
	 */
	public static <A extends Algebra<A,B>, B,
		C extends Algebra<C,D>, D,
		E extends Algebra<E,F>, F,
		G extends Algebra<G,H>, H,
		I extends Algebra<I,J>, J,
		K extends Algebra<K,L>, L>
	void six(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6,
		IndexedDataSource<?,Tuple6<B,D,F,H,J,L>> zipped,
		IndexedDataSource<?,B> unzip1,
		IndexedDataSource<?,D> unzip2,
		IndexedDataSource<?,F> unzip3,
		IndexedDataSource<?,H> unzip4,
		IndexedDataSource<?,J> unzip5,
		IndexedDataSource<?,L> unzip6)
	{
		Tuple6<B,D,F,H,J,L> tuple = new Tuple6<B,D,F,H,J,L>(null, null, null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		tuple.setE(alg5.construct());
		tuple.setF(alg6.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize) ||
				(unzip5.size() != zippedSize) ||
				(unzip6.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
			unzip5.set(i, tuple.e());
			unzip6.set(i, tuple.f());
		}
	}

	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param alg5
	 * @param alg6
	 * @param alg7
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 * @param unzip5
	 * @param unzip6
	 * @param unzip7
	 */
	public static <A extends Algebra<A,B>, B,
		C extends Algebra<C,D>, D,
		E extends Algebra<E,F>, F,
		G extends Algebra<G,H>, H,
		I extends Algebra<I,J>, J,
		K extends Algebra<K,L>, L,
		M extends Algebra<M,N>, N>
	void seven(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6, M alg7,
		IndexedDataSource<?,Tuple7<B,D,F,H,J,L,N>> zipped,
		IndexedDataSource<?,B> unzip1,
		IndexedDataSource<?,D> unzip2,
		IndexedDataSource<?,F> unzip3,
		IndexedDataSource<?,H> unzip4,
		IndexedDataSource<?,J> unzip5,
		IndexedDataSource<?,L> unzip6,
		IndexedDataSource<?,N> unzip7)
	{
		Tuple7<B,D,F,H,J,L,N> tuple = new Tuple7<B,D,F,H,J,L,N>(null, null, null, null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		tuple.setE(alg5.construct());
		tuple.setF(alg6.construct());
		tuple.setG(alg7.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize) ||
				(unzip5.size() != zippedSize) ||
				(unzip6.size() != zippedSize) ||
				(unzip7.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
			unzip5.set(i, tuple.e());
			unzip6.set(i, tuple.f());
			unzip7.set(i, tuple.g());
		}
	}

	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param alg5
	 * @param alg6
	 * @param alg7
	 * @param alg8
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 * @param unzip5
	 * @param unzip6
	 * @param unzip7
	 * @param unzip8
	 */
	public static <A extends Algebra<A,B>, B,
		C extends Algebra<C,D>, D,
		E extends Algebra<E,F>, F,
		G extends Algebra<G,H>, H,
		I extends Algebra<I,J>, J,
		K extends Algebra<K,L>, L,
		M extends Algebra<M,N>, N,
		O extends Algebra<O,P>, P>
	void eight(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6, M alg7, O alg8,
		IndexedDataSource<?,Tuple8<B,D,F,H,J,L,N,P>> zipped,
		IndexedDataSource<?,B> unzip1,
		IndexedDataSource<?,D> unzip2,
		IndexedDataSource<?,F> unzip3,
		IndexedDataSource<?,H> unzip4,
		IndexedDataSource<?,J> unzip5,
		IndexedDataSource<?,L> unzip6,
		IndexedDataSource<?,N> unzip7,
		IndexedDataSource<?,P> unzip8)
	{
		Tuple8<B,D,F,H,J,L,N,P> tuple = new Tuple8<B,D,F,H,J,L,N,P>(null,null, null, null, null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		tuple.setE(alg5.construct());
		tuple.setF(alg6.construct());
		tuple.setG(alg7.construct());
		tuple.setH(alg8.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize) ||
				(unzip5.size() != zippedSize) ||
				(unzip6.size() != zippedSize) ||
				(unzip7.size() != zippedSize) ||
				(unzip8.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
			unzip5.set(i, tuple.e());
			unzip6.set(i, tuple.f());
			unzip7.set(i, tuple.g());
			unzip8.set(i, tuple.h());
		}
	}

	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param alg5
	 * @param alg6
	 * @param alg7
	 * @param alg8
	 * @param alg9
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 * @param unzip5
	 * @param unzip6
	 * @param unzip7
	 * @param unzip8
	 * @param unzip9
	 */
	public static <A extends Algebra<A,B>, B,
		C extends Algebra<C,D>, D,
		E extends Algebra<E,F>, F,
		G extends Algebra<G,H>, H,
		I extends Algebra<I,J>, J,
		K extends Algebra<K,L>, L,
		M extends Algebra<M,N>, N,
		O extends Algebra<O,P>, P,
		Q extends Algebra<Q,R>, R>
	void nine(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6, M alg7, O alg8, Q alg9,
		IndexedDataSource<?,Tuple9<B,D,F,H,J,L,N,P,R>> zipped,
		IndexedDataSource<?,B> unzip1,
		IndexedDataSource<?,D> unzip2,
		IndexedDataSource<?,F> unzip3,
		IndexedDataSource<?,H> unzip4,
		IndexedDataSource<?,J> unzip5,
		IndexedDataSource<?,L> unzip6,
		IndexedDataSource<?,N> unzip7,
		IndexedDataSource<?,P> unzip8,
		IndexedDataSource<?,R> unzip9)
	{
		Tuple9<B,D,F,H,J,L,N,P,R> tuple = new Tuple9<B,D,F,H,J,L,N,P,R>(null,null,null, null, null, null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		tuple.setE(alg5.construct());
		tuple.setF(alg6.construct());
		tuple.setG(alg7.construct());
		tuple.setH(alg8.construct());
		tuple.setI(alg9.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize) ||
				(unzip5.size() != zippedSize) ||
				(unzip6.size() != zippedSize) ||
				(unzip7.size() != zippedSize) ||
				(unzip8.size() != zippedSize) ||
				(unzip9.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
			unzip5.set(i, tuple.e());
			unzip6.set(i, tuple.f());
			unzip7.set(i, tuple.g());
			unzip8.set(i, tuple.h());
			unzip9.set(i, tuple.i());
		}
	}
	
	/**
	 * 
	 * @param alg1
	 * @param alg2
	 * @param alg3
	 * @param alg4
	 * @param alg5
	 * @param alg6
	 * @param alg7
	 * @param alg8
	 * @param alg9
	 * @param alg10
	 * @param zipped
	 * @param unzip1
	 * @param unzip2
	 * @param unzip3
	 * @param unzip4
	 * @param unzip5
	 * @param unzip6
	 * @param unzip7
	 * @param unzip8
	 * @param unzip9
	 * @param unzip10
	 */
	public static <A extends Algebra<A,B>, B,
		C extends Algebra<C,D>, D,
		E extends Algebra<E,F>, F,
		G extends Algebra<G,H>, H,
		I extends Algebra<I,J>, J,
		K extends Algebra<K,L>, L,
		M extends Algebra<M,N>, N,
		O extends Algebra<O,P>, P,
		Q extends Algebra<Q,R>, R,
		S extends Algebra<S,T>, T>
	void ten(A alg1, C alg2, E alg3, G alg4, I alg5, K alg6, M alg7, O alg8, Q alg9, S alg10,
		IndexedDataSource<?,Tuple10<B,D,F,H,J,L,N,P,R,T>> zipped,
		IndexedDataSource<?,B> unzip1,
		IndexedDataSource<?,D> unzip2,
		IndexedDataSource<?,F> unzip3,
		IndexedDataSource<?,H> unzip4,
		IndexedDataSource<?,J> unzip5,
		IndexedDataSource<?,L> unzip6,
		IndexedDataSource<?,N> unzip7,
		IndexedDataSource<?,P> unzip8,
		IndexedDataSource<?,R> unzip9,
		IndexedDataSource<?,T> unzip10)
	{
		Tuple10<B,D,F,H,J,L,N,P,R,T> tuple = new Tuple10<B,D,F,H,J,L,N,P,R,T>(null,null,null,null, null, null, null, null, null, null);
		tuple.setA(alg1.construct());
		tuple.setB(alg2.construct());
		tuple.setC(alg3.construct());
		tuple.setD(alg4.construct());
		tuple.setE(alg5.construct());
		tuple.setF(alg6.construct());
		tuple.setG(alg7.construct());
		tuple.setH(alg8.construct());
		tuple.setI(alg9.construct());
		tuple.setJ(alg10.construct());
		long zippedSize = zipped.size();
		if ((unzip1.size() != zippedSize) ||
				(unzip2.size() != zippedSize) ||
				(unzip3.size() != zippedSize) ||
				(unzip4.size() != zippedSize) ||
				(unzip5.size() != zippedSize) ||
				(unzip6.size() != zippedSize) ||
				(unzip7.size() != zippedSize) ||
				(unzip8.size() != zippedSize) ||
				(unzip9.size() != zippedSize) ||
				(unzip10.size() != zippedSize))
			throw new IllegalArgumentException("mismatched list sizes");
		for (long i = 0; i < zippedSize; i++) {
			zipped.get(i, tuple);
			unzip1.set(i, tuple.a());
			unzip2.set(i, tuple.b());
			unzip3.set(i, tuple.c());
			unzip4.set(i, tuple.d());
			unzip5.set(i, tuple.e());
			unzip6.set(i, tuple.f());
			unzip7.set(i, tuple.g());
			unzip8.set(i, tuple.h());
			unzip9.set(i, tuple.i());
			unzip10.set(i, tuple.j());
		}
	}
}
