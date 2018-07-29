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

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
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
	 * @param grp
	 * @param storage
	 * @param avg
	 * @param sumSqDevs
	 * @param count
	 */
	public static <T extends Group<T,U> & Addition<U> & Multiplication<U> & Unity<U> & Ordered<U> & Invertible<U>,U>
		void compute(T grp, IndexedDataSource<?,U> storage, U avg, U sumSqDevs, U count)
	{
		/* original: impl 1: naive but quick
		
		U tmp = grp.construct();
		U one = grp.construct();
		grp.unity().call(one);
		grp.zero().call(sumSqDevs);
		grp.zero().call(count);
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			grp.subtract().call(tmp, avg, tmp);
			grp.multiply().call(tmp, tmp, tmp);
			grp.add().call(sumSqDevs, tmp, sumSqDevs);
			grp.add().call(count, one, count);
		}

		 */
		
		/* first optimization: not fully working: impl 2
		
		U minDev = grp.construct();
		U maxDev = grp.construct();
		U val = grp.construct();
		U cnt = grp.construct();
		U one = grp.construct();
		grp.unity().call(one);
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, val);
			grp.subtract().call(val, avg, val);
			if (grp.isLess().call(val,minDev))
				grp.assign().call(val, minDev);
			if (grp.isGreater().call(val, maxDev))
				grp.assign().call(val, maxDev);
			grp.add().call(cnt, one, cnt);
		}
		if (grp.isEqual().call(minDev, maxDev)) {
			grp.zero().call(sumSqDevs);
			grp.assign().call(cnt, count);
			return;
		}
		U range = grp.construct();
		U factor = grp.construct();
		grp.subtract().call(maxDev, minDev, range);
		U newRange = grp.construct("128");
		U newOrigin = grp.construct("-64");
		grp.divide().call(newRange, range, factor);
		U sum = grp.construct();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, val);
			grp.subtract().call(val, avg, val);
			grp.subtract().call(val, minDev, val);
			grp.multiply().call(val, factor, val);
			grp.add().call(val, newOrigin, val);
			grp.multiply().call(val, val, val);
			grp.add().call(sum, val, sum);
		}
		grp.divide().call(sum, factor, sum);
		//grp.multiply().call(minDev, count, val);
		//grp.add().call(sum, val, sum);
		grp.divide().call(sum, factor, sum);
		grp.assign().call(sum, sumSqDevs);
		grp.assign().call(cnt, count);
		 */
		
		/* second optimization: based on some algebra I did: impl 3
		 * The idea behind it is to scale big numbers into manageable range to avoid overflows
		 * if possible. Might have an accuracy cost.
		 */

		if (storage.size() == 0)
			throw new IllegalArgumentException("cannot compute values for empty list");
		
		U tmp = grp.construct();
		U y = grp.construct();
		U cnt = grp.construct();
		U one = grp.construct();
		U two = grp.construct();
		grp.unity().call(one);
		grp.add().call(one, one, two);

		U val = grp.construct();
		U a = grp.construct();
		U m = grp.construct();
		U b = grp.construct("-128");
		U range = grp.construct("256");
		U sumY = grp.construct();
		U sumYsq = grp.construct();
		U min = grp.construct();
		U max = grp.construct();
		Average.compute(grp, storage, a);
		
		grp.add().call(cnt, one, cnt);
		storage.get(0, val);
		grp.assign().call(val, min);
		grp.assign().call(val, max);
		for (long i = 1; i < storage.size(); i++) {
			grp.add().call(cnt, one, cnt);
			storage.get(i, val);
			//grp.subtract().call(val, a, val);
			if (grp.isLess().call(val, min))
				grp.assign().call(val, min);
			if (grp.isGreater().call(val, max))
				grp.assign().call(val, max);
		}

		if (grp.isGreaterEqual().call(min, max)) {
			grp.assign().call(cnt, count);
			grp.zero().call(sumSqDevs);
			return;
		}

		grp.subtract().call(max, min, tmp);
		grp.divide().call(tmp, range, m);

		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, val);
			grp.subtract().call(val, b, val);
			grp.divide().call(val, m, y);
			grp.add().call(sumY, y, sumY);
			grp.multiply().call(y, y, y);
			grp.add().call(sumYsq, y, sumYsq);
		}
		
		U term = grp.construct();
		
		grp.zero().call(tmp);
		
		grp.multiply().call(a, a, term);
		grp.multiply().call(term, cnt, term);
		grp.add().call(tmp, term, tmp);
		
		grp.multiply().call(b, b, term);
		grp.multiply().call(term, cnt, term);
		grp.add().call(tmp, term, tmp);
		
		grp.multiply().call(two, a, term);
		grp.multiply().call(term, b, term);
		grp.multiply().call(term, cnt, term);
		grp.subtract().call(tmp, term, tmp);
		
		grp.multiply().call(m, m, term);
		grp.multiply().call(term, sumYsq, term);
		grp.add().call(tmp, term, tmp);
		
		grp.multiply().call(two, m, term);
		grp.multiply().call(term, b, term);
		grp.multiply().call(term, sumY, term);
		grp.add().call(tmp, term, tmp);
		
		grp.multiply().call(two, m, term);
		grp.multiply().call(term, a, term);
		grp.multiply().call(term, sumY, term);
		grp.subtract().call(tmp, term, tmp);
		
		grp.assign().call(cnt, count);
		grp.assign().call(tmp, sumSqDevs);
	}
	
}
