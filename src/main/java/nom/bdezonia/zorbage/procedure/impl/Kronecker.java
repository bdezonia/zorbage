/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.procedure.impl;

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Kronecker<T extends Algebra<T,U> & Unity<U>,U>
	implements Procedure3<long[],long[],U>
{
	private final T algebra;
	private final U zero;
	private final U one;
	
	public Kronecker(T algebra) {
		this.algebra = algebra;
		this.zero = algebra.construct();
		this.one = algebra.construct();
		algebra.unity().call(this.one);
	}
	
	@Override
	public void call(long[] a, long[] b, U c) {
		if (a.length != b.length)
			throw new IllegalArgumentException("index length mismatch");
		if (a.length == 0) {
			algebra.assign().call(zero, c);
			return;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				algebra.assign().call(zero, c);
				return;
			}
		}
		algebra.assign().call(one, c);
	}

}
