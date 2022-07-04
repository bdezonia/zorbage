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
import nom.bdezonia.zorbage.algebra.SetFromLong;
import nom.bdezonia.zorbage.procedure.Procedure2;

// Source: NMR Data Processing, Hoch and Stern, 1996, p. 47

// ak = e ^ (-pi * w1 * k * dt) - scale * (e ^ (-pi * w2 * k * dt))

/**
 * 
 * @author Barry DeZonia
 *
 * @param <CA>
 * @param <C>
 */
public class ConvolutionDifferenceApodizer<CA extends Algebra<CA,C> & RealConstants<C> &
												Addition<C> & Multiplication<C> &
												Exponential<C>,
									C extends SetFromLong>
	implements Procedure2<Long,C>
{
	private final CA alg;
	private final C base1;
	private final C base2;
	private final ThreadLocal<C> termK;
	private final ThreadLocal<C> temp1;
	private final ThreadLocal<C> temp2;

	/**
	 * 
	 * @param algebra
	 * @param scale
	 * @param w1
	 * @param w2
	 * @param dt
	 */
	public ConvolutionDifferenceApodizer(CA algebra, C scale, C w1, C w2, C dt) {
		
		this.alg = algebra;
		this.base1 = alg.construct();
		this.base2 = alg.construct();
		this.termK = new ThreadLocal<C>() {

			@Override
			protected C initialValue() {
				return alg.construct();
			}
		
		};
		this.temp1 = new ThreadLocal<C>() {

			@Override
			protected C initialValue() {
				return alg.construct();
			}
		
		};
		this.temp2 = new ThreadLocal<C>() {

			@Override
			protected C initialValue() {
				return alg.construct();
			}
		
		};
		
		alg.PI().call(base1);
		alg.negate().call(base1, base1);
		alg.multiply().call(base1, w1, base1);
		alg.multiply().call(base1, dt, base1);
		alg.exp().call(base1, base1);
		
		alg.PI().call(base2);
		alg.negate().call(base2, base2);
		alg.multiply().call(base2, w2, base2);
		alg.multiply().call(base2,dt,base2);
		alg.exp().call(base2, base2);
		alg.multiply().call(scale, base2, base2);
	}

	@Override
	public void call(Long k, C ak) {
		C tk = termK.get();
		C tmp1 = temp1.get();
		C tmp2 = temp2.get();
		tk.setFromLong(k);
		alg.exp().call(tk, tk);
		alg.multiply().call(base1, tk, tmp1);
		alg.multiply().call(base2, tk, tmp2);
		alg.subtract().call(tmp1, tmp2, ak);
	}
}
