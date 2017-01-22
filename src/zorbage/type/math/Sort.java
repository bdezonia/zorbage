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

import zorbage.type.algebra.Group;
import zorbage.type.algebra.Ordered;
import zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Sort<T extends Group<T,U> & Ordered<U> ,U> {

	private T g;
	
	public Sort(T g) {
		this.g = g;
	}
	
	public void calculate(Storage<?,U> storage) {
		U tmp = g.construct();
		U iVal = g.construct();
		U jVal = g.construct();
		// TODO: change from bubble sort to quick sort
		for (long i = 0; i < storage.size()-1; i++) {
			storage.get(i, iVal);
			for (long j = i+1; j < storage.size(); j++) {
				storage.get(j, jVal);
				if (g.isLess(jVal, iVal)) {
					g.assign(iVal, tmp);
					storage.set(i, jVal);
					storage.set(j, tmp);
				}
			}
			
		}
	}
}
