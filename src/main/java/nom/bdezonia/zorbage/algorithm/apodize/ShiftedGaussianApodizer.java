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
package nom.bdezonia.zorbage.algorithm.apodize;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Exponential;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetFromLong;
import nom.bdezonia.zorbage.procedure.Procedure2;

// Source: NMR Data Processing, Hoch and Stern, 1996, p. 43

// ak = e ^ (-w * (k * dt - dt*numElems/totElems)^2)

/**
 * 
 * @author Barry DeZonia
 *
 * @param <CA>
 * @param <C>
 */
public class ShiftedGaussianApodizer<CA extends Algebra<CA,C> & RealConstants<C> &
												Addition<C> & Multiplication<C> &
												Invertible<C> & Exponential<C>,
									C extends SetFromLong>
	implements Procedure2<Long,C>
{
	private final CA alg;
	private final C base;
	private final C dt;
	private final C shift;
	private final ThreadLocal<C> termK;

	/**
	 * 
	 * @param algebra
	 * @param w
	 * @param dt
	 */
	public ShiftedGaussianApodizer(CA algebra, C w, C dt, long numElems, long totElems) {
		
		this.alg = algebra;
		this.base = alg.construct();
		this.dt = alg.construct(dt);
		this.shift = alg.construct();
		this.termK = new ThreadLocal<C>() {

			@Override
			protected C initialValue() {
				return alg.construct();
			}
		
		};
		alg.assign().call(w, base);
		alg.negate().call(base, base);
		alg.exp().call(base, base);
		C numEls = alg.construct();
		numEls.setFromLong(numElems);
		C totEls = alg.construct();
		totEls.setFromLong(totElems);
		alg.multiply().call(dt, numEls, shift);
		alg.divide().call(shift, totEls, shift);
	}

	@Override
	public void call(Long k, C ak) {
		C tk = termK.get();
		tk.setFromLong(k);
		alg.multiply().call(tk, dt, tk);
		alg.subtract().call(tk, shift, tk);
		alg.multiply().call(tk, tk, tk);
		alg.exp().call(tk, tk);
		alg.multiply().call(base, tk, ak);
	}
}
