/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ClassicRungeKutta {

	/**
	 * 
	 * @param alg
	 * @param proc
	 * @param t0
	 * @param y0
	 * @param numSteps
	 * @param dt
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Multiplication<U> & Unity<U> & Invertible<U>, U>
		void compute(T alg, Procedure3<U,U,U> proc, U t0, U y0, int numSteps, U dt, U result)
	{
		if (numSteps <= 0)
			throw new IllegalArgumentException("numSteps must be greater than 0");
		if (alg.isZero().call(dt))
			throw new IllegalArgumentException("spatial increment h must not be 0");
		U t = alg.construct(t0);
		U y = alg.construct(y0);
		U k1 = alg.construct();
		U k2 = alg.construct();
		U k3 = alg.construct();
		U k4 = alg.construct();
		U dy = alg.construct();
		U two = alg.construct("2");
		U six = alg.construct("6");
		U tmp = alg.construct();
		U tt = alg.construct();
		U ty = alg.construct();
		U dt_over_two = alg.construct();
		alg.divide().call(dt, two, dt_over_two);
		for (int i = 0; i < numSteps; i++) {
		
			// calc k1
			if (i == 0)
				alg.assign().call(y, tmp);
			else
				proc.call(t, y, tmp);
			alg.multiply().call(dt, tmp, k1);
			
			// calc k2
			alg.add().call(t, dt_over_two, tt);
			alg.divide().call(k1, two, ty);
			alg.add().call(y, ty, ty);
			proc.call(tt, ty, tmp);
			alg.multiply().call(dt, tmp, k2);
			
			// calc k3
			alg.divide().call(k2, two, ty);
			alg.add().call(y, ty, ty);
			proc.call(tt, ty, tmp);
			alg.multiply().call(dt, tmp, k3);
			
			// calc k4
			alg.add().call(t, dt, tt);
			alg.add().call(y, k3, ty);
			proc.call(tt, ty, tmp);
			alg.multiply().call(dt, tmp, k4);
			
			// update y
			alg.add().call(k1, k2, dy);
			alg.add().call(dy, k2, dy);
			alg.add().call(dy, k3, dy);
			alg.add().call(dy, k3, dy);
			alg.add().call(dy, k4, dy);
			alg.divide().call(dy, six, dy);
			alg.add().call(y, dy, y);
			
			// update t
			alg.add().call(t, dt, t);
		}
		
		// assign result
		alg.assign().call(y, result);
	}
}
