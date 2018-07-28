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
	}
	
}
