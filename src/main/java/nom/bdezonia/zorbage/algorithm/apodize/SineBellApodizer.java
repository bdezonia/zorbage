/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.procedure.Procedure2;

// Source: NMR Data Processing, Hoch and Stern, 1996, p. 47

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SineBellApodizer<CA extends Algebra<CA,C> & Trigonometric<C> &
											RealConstants<C> & Unity<C> & Addition<C> &
											Invertible<C> & Multiplication<C>,
								C extends SetFromLongs>
	implements Procedure2<Long,C>
{
	private final CA alg;
	private final C phase;
	private final C t1;
	private final ThreadLocal<C> tk;
	private final ThreadLocal<C> k;

	/**
	 * 
	 * @param alg Algebra
	 * @param phase An angle between 0 and pi/2 radians inclusive 
	 * @param signalLen Total length of the signal you are apodizing.
	 */
	public SineBellApodizer(CA alg, C phase, long signalLen) {
		this.alg = alg;
		this.phase = alg.construct(phase);
		C len = alg.construct();
		C oneEighty = alg.construct();
		this.t1 = alg.construct();
		this.k = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
		this.tk = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
		C pi = alg.construct();
		C one = alg.construct();
		C two = alg.construct();
		alg.PI().call(pi);
		alg.unity().call(one);
		alg.add().call(one, one, two);
		alg.divide().call(oneEighty, two, oneEighty);
		len.setFromLongs(signalLen);
		alg.assign().call(oneEighty, t1);
		alg.subtract().call(t1, phase, t1);
		alg.divide().call(t1, len, t1);
	}

	@Override
	public void call(Long k, C ak) {
		C tmp = this.k.get();
		C tk = this.tk.get();
		tmp.setFromLongs(k);
		alg.multiply().call(t1, tmp, tk);
		alg.add().call(tk, phase, ak);
		alg.add().call(tk, phase, tk);
		alg.sin().call(tk, ak);
	}

}
