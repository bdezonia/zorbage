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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.SetFromLong;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.procedure.Procedure2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TrapezoidalApodizer<CA extends Algebra<CA,C> & Unity<C> & Invertible<C>,
									C extends SetFromLong>
	implements Procedure2<Long, C>
{
	private final CA alg;
	private final ThreadLocal<C> numer;
	private final ThreadLocal<C> denom;
	private final long leftPoint;
	private final long rightPoint;
	private final long size;

	/**
	 * 
	 * @param alg
	 * @param leftPoint
	 * @param rightPoint
	 * @param size
	 */
	public TrapezoidalApodizer(CA alg, long leftPoint, long rightPoint, long size) {
		this.alg = alg;
		this.leftPoint = leftPoint;
		this.rightPoint = rightPoint;
		this.size = size;
		this.numer = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
		this.denom = new ThreadLocal<C>() {
			@Override
			protected C initialValue() {
				return alg.construct();
			}
		};
	}

	@Override
	public void call(Long index, C value) {
		if (index < leftPoint) {
			C n = numer.get();
			C d = denom.get();
			n.setFromLong(index);
			d.setFromLong(leftPoint);
			alg.divide().call(n, d, value);
		}
		else if (index > rightPoint) {
			C n = numer.get();
			C d = denom.get();
			n.setFromLong(size-1-index);
			d.setFromLong(size-1-rightPoint);
			alg.divide().call(n, d, value);
		}
		else {
			alg.unity().call(value);
		}
	}

}
