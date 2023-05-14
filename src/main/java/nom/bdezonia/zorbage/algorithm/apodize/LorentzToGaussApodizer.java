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
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.procedure.Procedure2;

// Source: NMR Data Processing, Hoch and Stern, 1996, p. 47

// ak = e ^ (pi * we * k * dt) * e ^ (-wg * (k * dt) ^ 2))

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LorentzToGaussApodizer<CA extends Algebra<CA,C> & RealConstants<C> &
												Exponential<C> & Multiplication<C> &
												Addition<C>,
										C extends SetFromLongs>
	implements Procedure2<Long,C>
{
	private final CA alg;
	private final C t1;
	private final C t2;
	private final ThreadLocal<C> k;
	private final ThreadLocal<C> t1k;
	private final ThreadLocal<C> t2k;

	/**
	 * 
	 * @param alg
	 * @param we
	 * @param wg
	 * @param dt
	 */
	public LorentzToGaussApodizer(CA alg, C we, C wg, C dt) {
		this.alg = alg;
		this.t1 = alg.construct();
		this.t2 = alg.construct();
		this.k = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
		this.t1k = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
		this.t2k = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
		alg.PI().call(t1);
		alg.multiply().call(t1, we, t1);
		alg.multiply().call(t1, dt, t1);
		alg.assign().call(wg, t2);
		alg.negate().call(t2, t2);
		alg.multiply().call(t2, dt, t2);
		alg.multiply().call(t2, dt, t2);
	}
	
	@Override
	public void call(Long k, C ak)
	{
		C tk = this.k.get();
		C t1k = this.t1k.get();
		C t2k = this.t2k.get();
		tk.setFromLongs(k);
		alg.multiply().call(t1, tk, t1k);
		alg.exp().call(t1k, t1k);
		alg.multiply().call(t2, tk, t2k);
		alg.multiply().call(t2k, tk, t2k);
		alg.exp().call(t2k, t2k);
		alg.multiply().call(t1k, t2k, ak);
	}
}
