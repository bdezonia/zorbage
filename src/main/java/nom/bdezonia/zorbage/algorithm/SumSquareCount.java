/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SumSquareCount {

	// TODO: maybe based upon data characteristics we can choose between impl 1 and impl 3
	
	private SumSquareCount() {}
	
	/**
	 * 
	 * @param alg
	 * @param storage
	 * @param avg
	 * @param sumSqDevs
	 * @param count
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Unity<U> & Ordered<U> & Invertible<U>,U>
		void compute(T alg, IndexedDataSource<?,U> storage, U avg, U sumSqDevs, U count)
	{
		/* original: impl 1: naive but quick
		
		U tmp = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		alg.zero().call(sumSqDevs);
		alg.zero().call(count);
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			alg.subtract().call(tmp, avg, tmp);
			alg.multiply().call(tmp, tmp, tmp);
			alg.add().call(sumSqDevs, tmp, sumSqDevs);
			alg.add().call(count, one, count);
		}

		 */
		
		/* first optimization: not fully working: impl 2
		
		U minDev = alg.construct();
		U maxDev = alg.construct();
		U val = alg.construct();
		U cnt = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, val);
			alg.subtract().call(val, avg, val);
			if (alg.isLess().call(val,minDev))
				alg.assign().call(val, minDev);
			if (alg.isGreater().call(val, maxDev))
				alg.assign().call(val, maxDev);
			alg.add().call(cnt, one, cnt);
		}
		if (alg.isEqual().call(minDev, maxDev)) {
			alg.zero().call(sumSqDevs);
			alg.assign().call(cnt, count);
			return;
		}
		U range = alg.construct();
		U factor = alg.construct();
		alg.subtract().call(maxDev, minDev, range);
		U newRange = alg.construct("128");
		U newOrigin = alg.construct("-64");
		alg.divide().call(newRange, range, factor);
		U sum = alg.construct();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, val);
			alg.subtract().call(val, avg, val);
			alg.subtract().call(val, minDev, val);
			alg.multiply().call(val, factor, val);
			alg.add().call(val, newOrigin, val);
			alg.multiply().call(val, val, val);
			alg.add().call(sum, val, sum);
		}
		alg.divide().call(sum, factor, sum);
		//alg.multiply().call(minDev, count, val);
		//alg.add().call(sum, val, sum);
		alg.divide().call(sum, factor, sum);
		alg.assign().call(sum, sumSqDevs);
		alg.assign().call(cnt, count);
		 */
		
		/* second optimization: based on some Algebra I did: impl 3
		 * The idea behind it is to scale big numbers into manageable range to avoid overflows
		 * if possible. Might have an accuracy cost.
		 */

		long storageSize = storage.size();
		if (storageSize == 0)
			throw new IllegalArgumentException("cannot compute values for empty list");
		
		U tmp = alg.construct();
		U y = alg.construct();
		U cnt = alg.construct();
		U one = alg.construct();
		U two = alg.construct();
		alg.unity().call(one);
		alg.add().call(one, one, two);

		U val = alg.construct();
		U a = alg.construct();
		U m = alg.construct();
		U b = alg.construct("-128");
		U range = alg.construct("256");
		U sumY = alg.construct();
		U sumYsq = alg.construct();
		U min = alg.construct();
		U max = alg.construct();
		Average.compute(alg, storage, a);
		
		alg.add().call(cnt, one, cnt);
		storage.get(0, val);
		alg.assign().call(val, min);
		alg.assign().call(val, max);
		for (long i = 1; i < storageSize; i++) {
			alg.add().call(cnt, one, cnt);
			storage.get(i, val);
			//alg.subtract().call(val, a, val);
			if (alg.isLess().call(val, min))
				alg.assign().call(val, min);
			if (alg.isGreater().call(val, max))
				alg.assign().call(val, max);
		}

		if (alg.isGreaterEqual().call(min, max)) {
			alg.assign().call(cnt, count);
			alg.zero().call(sumSqDevs);
			return;
		}

		// TODO I am scaling min/max to -128/128. I used to do so with deviations but here I am
		// doing with raw values. If dataset is not very symmetric this might be wrong. Maybe I
		// need to go back to doing deviations. Investigate.
		
		alg.subtract().call(max, min, tmp);
		alg.divide().call(tmp, range, m);

		for (long i = 0; i < storageSize; i++) {
			storage.get(i, val);
			alg.subtract().call(val, b, val);
			alg.divide().call(val, m, y);
			alg.add().call(sumY, y, sumY);
			alg.multiply().call(y, y, y);
			alg.add().call(sumYsq, y, sumYsq);
		}
		
		U term = alg.construct();
		
		alg.zero().call(tmp);
		
		alg.multiply().call(a, a, term);
		alg.multiply().call(term, cnt, term);
		alg.add().call(tmp, term, tmp);
		
		alg.multiply().call(b, b, term);
		alg.multiply().call(term, cnt, term);
		alg.add().call(tmp, term, tmp);
		
		alg.multiply().call(two, a, term);
		alg.multiply().call(term, b, term);
		alg.multiply().call(term, cnt, term);
		alg.subtract().call(tmp, term, tmp);
		
		alg.multiply().call(m, m, term);
		alg.multiply().call(term, sumYsq, term);
		alg.add().call(tmp, term, tmp);
		
		alg.multiply().call(two, m, term);
		alg.multiply().call(term, b, term);
		alg.multiply().call(term, sumY, term);
		alg.add().call(tmp, term, tmp);
		
		alg.multiply().call(two, m, term);
		alg.multiply().call(term, a, term);
		alg.multiply().call(term, sumY, term);
		alg.subtract().call(tmp, term, tmp);
		
		alg.assign().call(cnt, count);
		alg.assign().call(tmp, sumSqDevs);
	}
	
}
