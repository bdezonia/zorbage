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

import java.math.BigDecimal;
import java.math.MathContext;

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionAlgebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ClassicRungeKutta {

	/**
	 * 
	 * @param alg
	 * @param wAlg
	 * @param proc
	 * @param t0
	 * @param y0
	 * @param numSteps
	 * @param dt
	 * @param result
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Scale<U,W>,
					U,
					V extends Algebra<V,W> & Addition<W> & Scale<W,W>,
					W>
		void compute(T alg, V wAlg, Procedure3<W,U,U> proc, W t0, U y0, int numSteps, W dt, U result)
	{
		if (numSteps <= 0)
			throw new IllegalArgumentException("numSteps must be greater than 0");
		if (wAlg.isZero().call(dt))
			throw new IllegalArgumentException("spatial increment h must not be 0");
		W t = wAlg.construct(t0);
		U y = alg.construct(y0);
		U k1 = alg.construct();
		U k2 = alg.construct();
		U k3 = alg.construct();
		U k4 = alg.construct();
		U dy = alg.construct();
		W one_half = wAlg.construct("0.5");
		W one_sixth = wAlg.construct(""+(BigDecimal.ONE.divide(BigDecimal.valueOf(6),new MathContext(HighPrecisionAlgebra.getPrecision()))));
		U tmp = alg.construct();
		W tt = wAlg.construct();
		U ty = alg.construct();
		W dt_over_two = wAlg.construct();
		wAlg.scale().call(one_half, dt, dt_over_two);
		for (int i = 0; i < numSteps; i++) {
		
			// calc k1
			if (i == 0)
				alg.assign().call(y, tmp);
			else
				proc.call(t, y, tmp);
			alg.scale().call(dt, tmp, k1);
			
			// calc k2
			wAlg.add().call(t, dt_over_two, tt);
			alg.scale().call(one_half, k1, ty);
			alg.add().call(y, ty, ty);
			proc.call(tt, ty, tmp);
			alg.scale().call(dt, tmp, k2);
			
			// calc k3
			alg.scale().call(one_half, k2, ty);
			alg.add().call(y, ty, ty);
			proc.call(tt, ty, tmp);
			alg.scale().call(dt, tmp, k3);
			
			// calc k4
			wAlg.add().call(t, dt, tt);
			alg.add().call(y, k3, ty);
			proc.call(tt, ty, tmp);
			alg.scale().call(dt, tmp, k4);
			
			// update y
			alg.add().call(k1, k2, dy);
			alg.add().call(dy, k2, dy);
			alg.add().call(dy, k3, dy);
			alg.add().call(dy, k3, dy);
			alg.add().call(dy, k4, dy);
			alg.scale().call(one_sixth, dy, dy);
			alg.add().call(y, dy, y);
			
			// update t
			wAlg.add().call(t, dt, t);
		}
		
		// assign result
		alg.assign().call(y, result);
	}
}
