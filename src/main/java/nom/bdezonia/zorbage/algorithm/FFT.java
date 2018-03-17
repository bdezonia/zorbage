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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConverter;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.PaddedIndexedDataSource;
import nom.bdezonia.zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FFT {

	// don't instantiate
	
	private FFT() {}

	/**
	 * 
	 * @param group
	 * @param a
	 * @param b
	 */
	public static <T extends Group<T,U>, U extends PrimitiveConversion>
		void compute(T group, IndexedDataSource<?,U> a, IndexedDataSource<?,ComplexFloat64Member> b)
	{
		ComplexFloat64Member wm = new ComplexFloat64Member();
		ComplexFloat64Member w = new ComplexFloat64Member();
		ComplexFloat64Member u = new ComplexFloat64Member();
		ComplexFloat64Member t = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member diff = new ComplexFloat64Member();

		long n = enclosingPowerOf2(a.size());
		if (b.size() != n)
			throw new IllegalArgumentException("output is not the correct size: expected "+n+" and given "+b.size());
		IndexedDataSource<?, ComplexFloat64Member> A = Storage.allocate(n, new ComplexFloat64Member());
		PaddedIndexedDataSource<?, U> paddedA = new PaddedIndexedDataSource<T,U>(group, a);
		bitReverseCopy(paddedA, A, group.construct());
		int maxS = lg(n);
		for (int s = 1; s <= maxS; s++) {
			long m = 1 << s;
			PrincipalComplexRootOfUnity.compute(m, wm);
			w.setR(1);
			w.setI(0);
			long halfM = m / 2;
			for (long j = 0; j < halfM - 1; j++) {
				for (long k = j; k < n; k += m) {
					A.get(k + halfM, t);
					G.CDBL.multiply(w, t, t);
					A.get(k, u);
					G.CDBL.add(u, t, sum);
					G.CDBL.subtract(u, t, diff);
					A.set(k, sum);
					A.set(k + halfM, diff);
				}
				G.CDBL.multiply(w, wm, w);
			}
		}
		CopyN.compute(G.CDBL, 0, 0, n, A, b);
	}

	private static long rev(long index) {
		long mask = 0x8000000000000000L;
		long inc = 1;
		long reversedValue = 0;
		while (mask != 0) {
			if ((index & mask) != 0)
				reversedValue += inc;
			inc <<= 1;
			mask >>= 1;
		}
		return reversedValue;
	}
	
	private static <U extends PrimitiveConversion>
		void bitReverseCopy(IndexedDataSource<?, U> paddedA, IndexedDataSource<?, ComplexFloat64Member> A, U value)
	{
		int numDims = Math.max(0, value.numDimensions());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		IntegerIndex tmp1 = new IntegerIndex(numDims);
		IntegerIndex tmp2 = new IntegerIndex(numDims);
		IntegerIndex tmp3 = new IntegerIndex(numDims);
		long n = A.size();
		for (long i = 0; i < n; i++) {
			paddedA.get(i, value);
			long index = rev(i);
			PrimitiveConverter.convert(tmp1, tmp2, tmp3, value, tmp);
			A.set(index, tmp);
		}
	}
	
	private static int lg(long num) {
		if (num <= 0 || num >= (1L << 63))
			throw new IllegalArgumentException("num too big");
		long mask = 0x8000000000000000L;
		int log = 63;
		while ((num & mask) == 0) {
			mask >>= 1;
			log--;
		}
		return log;
	}
	
	public static long enclosingPowerOf2(long num) {
		if (num <= 0) throw new IllegalArgumentException("num too small");
		long max = 1;
		for (int i = 0; i < 63; i++) {
			if (num <= max) return max;
			max <<= 1;
		}
		throw new IllegalArgumentException("num too big");
	}
}
