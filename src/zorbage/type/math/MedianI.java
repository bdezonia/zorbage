/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.math;

import zorbage.type.algebra.Ordered;
import zorbage.type.algebra.Unity;
import zorbage.type.algebra.AdditiveGroup;
import zorbage.type.algebra.IntegralDivision;
import zorbage.type.storage.ArrayStorageGeneric;
import zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 * @param <U>
 */
public class MedianI<T extends AdditiveGroup<T,U> & IntegralDivision<U> & Ordered<U> & Unity<U>, U> {

	private T g;
	private ArrayStorageGeneric<T,U> localStorage; // Note: limited to Integer.MAX_VALUE entries

	public MedianI(T g) {
		this.g = g;
	}
	
	public void calculate(Storage<U> storage, U result) {
		localStorage = new ArrayStorageGeneric<T,U>(storage.size(), g);
		U tmp = g.construct();
		U tmp2 = g.construct();
		U tmp3 = g.construct();
		U one = g.construct();
		g.unity(one);
		for (long l = 0; l < storage.size(); l++) {
			storage.get(l, tmp);
			localStorage.put(l, tmp);
		}
		// TODO: change from bubble sort to quick sort
		for (long i = 0; i < localStorage.size()-1; i++) {
			localStorage.get(i, tmp);
			for (long j = i+1; j < localStorage.size(); j++) {
				localStorage.get(j, tmp2);
				if (g.isLess(tmp2, tmp)) {
					g.assign(tmp, tmp3);
					localStorage.put(i, tmp2);
					localStorage.put(j, tmp3);
				}
			}
			
		}
		if (storage.size() % 2 == 0) {
			U sum = g.construct();
			localStorage.get(storage.size()/2 - 1, tmp);
			g.add(sum, tmp, sum);
			localStorage.get(storage.size()/2, tmp);
			g.add(sum, tmp, sum);
			U count = g.construct();
			g.add(one, one, count);
			g.div(sum, count, result);
		}
		else {
			localStorage.get(storage.size()/2, result);
		}
	}
}
