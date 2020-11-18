/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Scale;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OdeSolveRK4 {

	/**
	 * Solves ODEs (1-d or n-d) using a classic fourth order Runge Kutta algorithm.
	 * @param uAlg
	 * @param wAlg
	 * @param proc
	 * @param t0
	 * @param y0
	 * @param numSteps
	 * @param dt
	 * @param results
	 */
	public static <T extends Algebra<T,U> & Addition<U> & Scale<U,W>,
					U,
					V extends Algebra<V,W> & Addition<W> & Scale<W,W>,
					W>
		void compute(T uAlg, V wAlg, Procedure3<W,U,U> proc, W t0, U y0, long numSteps, W dt, IndexedDataSource<U> results)
	{
		if (numSteps <= 0)
			throw new IllegalArgumentException("numSteps must be greater than 0");
		if (wAlg.isZero().call(dt))
			throw new IllegalArgumentException("time increment dt must not be 0");
		if (results.size() != numSteps)
			throw new IllegalArgumentException("output array size does not match numSteps");
		W t = wAlg.construct(t0);
		U y = uAlg.construct(y0);
		U k1 = uAlg.construct();
		U k2 = uAlg.construct();
		U k3 = uAlg.construct();
		U k4 = uAlg.construct();
		U dy = uAlg.construct();
		W one_half = wAlg.construct("0.5");
		W one_sixth = wAlg.construct((BigDecimal.ONE.divide(BigDecimal.valueOf(6),HighPrecisionAlgebra.getContext())).toString());
		U tmp = uAlg.construct();
		W tt = wAlg.construct();
		U ty = uAlg.construct();
		W dt_over_two = wAlg.construct();
		wAlg.scale().call(one_half, dt, dt_over_two);
		results.set(0, y);
		for (int i = 1; i < numSteps; i++) {
		
			// calc k1
			proc.call(t, y, tmp);
			uAlg.scale().call(dt, tmp, k1);
			
			// calc k2
			wAlg.add().call(t, dt_over_two, tt);
			uAlg.scale().call(one_half, k1, ty);
			uAlg.add().call(y, ty, ty);
			proc.call(tt, ty, tmp);
			uAlg.scale().call(dt, tmp, k2);
			
			// calc k3
			uAlg.scale().call(one_half, k2, ty);
			uAlg.add().call(y, ty, ty);
			proc.call(tt, ty, tmp);
			uAlg.scale().call(dt, tmp, k3);
			
			// calc k4
			wAlg.add().call(t, dt, tt);
			uAlg.add().call(y, k3, ty);
			proc.call(tt, ty, tmp);
			uAlg.scale().call(dt, tmp, k4);
			
			// update y
			uAlg.add().call(k1, k2, dy);
			uAlg.add().call(dy, k2, dy);
			uAlg.add().call(dy, k3, dy);
			uAlg.add().call(dy, k3, dy);
			uAlg.add().call(dy, k4, dy);
			uAlg.scale().call(one_sixth, dy, dy);
			uAlg.add().call(y, dy, y);

			// update t
			wAlg.add().call(t, dt, t);

			// record result
			results.set(i, y);
		}
	}
}
