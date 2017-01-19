/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import zorbage.type.algebra.Invertible;
import zorbage.type.algebra.Ordered;
import zorbage.type.algebra.Unity;
import zorbage.type.algebra.AdditiveGroup;
import zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 * @param <U>
 */
public class Median<T extends AdditiveGroup<T,U> & Invertible<U> & Ordered<U> & Unity<U>, U> {

	private T g;
	private Storage<?,U> localStorage;

	public Median(T g) {
		this.g = g;
	}
	
	public void calculate(Storage<?,U> storage, U result) {
		U iVal = g.construct();
		U jVal = g.construct();
		U tmp = g.construct();
		U one = g.construct();
		U sum = g.construct();
		g.unity(one);
		localStorage = storage.duplicate();
		// TODO: change from bubble sort to quick sort
		for (long i = 0; i < localStorage.size()-1; i++) {
			localStorage.get(i, iVal);
			for (long j = i+1; j < localStorage.size(); j++) {
				localStorage.get(j, jVal);
				if (g.isLess(jVal, iVal)) {
					g.assign(iVal, tmp);
					localStorage.set(i, jVal);
					localStorage.set(j, tmp);
				}
			}
			
		}
		if (localStorage.size() % 2 == 0) {
			localStorage.get(localStorage.size()/2 - 1, tmp);
			g.add(sum, tmp, sum);
			localStorage.get(localStorage.size()/2, tmp);
			g.add(sum, tmp, sum);
			U count = g.construct();
			g.add(one, one, count);
			g.divide(sum, count, result);
		}
		else {
			localStorage.get(localStorage.size()/2, result);
		}
	}
}
