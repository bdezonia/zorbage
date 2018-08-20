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
package nom.bdezonia.zorbage.procedure.impl;

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Step<T extends Group<T,U> & Ordered<U> & Unity<U>, U>
	implements Procedure2<U,U>
{
	private final T group;
	private final U zero;
	private final U h0;
	
	/**
	 * Heaviside step function. 
	 * 
	 * @param group
	 * @param h0 The value to return if the input is 0. Common values
	 *   are 0, 1/2, and 1.
	 */
	public Step(T group, U h0) {
		this.group = group;
		this.zero = group.construct();
		this.h0 = group.construct();
		group.assign().call(h0, this.h0);
		U one = group.construct();
		group.unity().call(one);
		if (group.isLess().call(h0, zero) || group.isGreater().call(h0, one))
			throw new IllegalArgumentException("step: h0 out of sensible range");
	}
	
	@Override
	public void call(U a, U b) {
		int s = group.signum().call(a);
		if (s < 0)
			group.assign().call(zero, b);
		else if (s > 0)
			group.unity().call(b);
		else
			group.assign().call(h0, b);
	}

}
