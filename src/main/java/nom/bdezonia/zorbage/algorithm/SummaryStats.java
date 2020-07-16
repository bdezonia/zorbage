/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SummaryStats {

	// do not instantiate
	
	private SummaryStats() { }
	
	/**
	 * 
	 * @param alg
	 * @param data
	 * @param min
	 * @param q1
	 * @param median
	 * @param q3
	 * @param max
	 * @param noDataCount
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & Ordered<U> & NaN<U>,
					U extends Allocatable<U>>
		void computeSafe(T alg, IndexedDataSource<U> data, U min, U q1, U median, U q3, U max, SignedInt64Member noDataCount)
	{
		IndexedDataSource<U> trimmed = NonNanValues.compute(alg, data);
		noDataCount.setV(data.size() - trimmed.size());
		compute(alg, trimmed, min, q1, median, q3, max);
	}
	
	/**
	 * 
	 * @param alg
	 * @param data
	 * @param min
	 * @param q1
	 * @param median
	 * @param q3
	 * @param max
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Algebra<T,U> & Addition<U> & Unity<U> & Ordered<U>,
					U extends Allocatable<U>>
		void compute(T alg, IndexedDataSource<U> data, U min, U q1, U median, U q3, U max)
	{
		IndexedDataSource<U> copy = DeepCopy.compute(alg, data);
		
		StableSort.compute(alg, copy);
		
		long sz = copy.size();
		
		U med1 = alg.construct();
		U med2 = alg.construct();
		
		if (sz == 0) {
			throw new IllegalArgumentException("cannot extract measures from empty list");
		}
		else if (sz == 1) {
			copy.get(0, min);
			alg.assign().call(min, q1);
			alg.assign().call(min, median);
			alg.assign().call(min, q3);
			alg.assign().call(min, max);
			return;
		}
		else if (sz == 2) {
			copy.get(0, min);
			copy.get(1, max);
			alg.assign().call(min, q1);
			alg.assign().call(max, q3);
			alg.assign().call(min, med1);
			alg.assign().call(max, med2);
		}
		else if (sz == 3) {
			copy.get(0, min);
			copy.get(1, median);
			copy.get(2, max);
			alg.assign().call(min, q1);
			alg.assign().call(max, q3);
			return;
		}
		else if (sz == 4) {
			copy.get(0, min);
			copy.get(1, med1);
			copy.get(2, med2);
			copy.get(3, max);
			alg.assign().call(med1, q1);
			alg.assign().call(med2, q3);
		}
		else { //sz >= 5
			copy.get(0, min);
			copy.get(sz-1, max);
			copy.get(sz/4, q1);
			BigDecimal idx =
					BigDecimal.valueOf(3).multiply(BigDecimal.valueOf(sz)).divide(BigDecimal.valueOf(4), HighPrecisionAlgebra.getContext());
			copy.get(idx.longValue(), q3);
			if (sz % 2 == 1) {
				copy.get(sz/2, median);
				return;
			}
			else {
				copy.get(sz/2-1, med1);
				copy.get(sz/2, med2);
			}
		}
		U numer = alg.construct();
		U denom = alg.construct();
		alg.add().call(med1, med2, numer);
		alg.unity().call(denom);
		alg.add().call(denom, denom, denom);
		if (alg instanceof Invertible) {
			Invertible<U> iAlg = (Invertible<U>) alg;
			iAlg.divide().call(numer, denom, median);
		}
		else if (alg instanceof ModularDivision) {
			ModularDivision<U> mdAlg = (ModularDivision<U>) alg;
			mdAlg.div().call(numer, denom, median);
		}
		else {
			throw new IllegalArgumentException("given algebra must support some kind of division operation");
		}
	}
}
