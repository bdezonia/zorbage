/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Invertible;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NewtonsMethod<T extends Algebra<T,U> & Addition<U> & Invertible<U>, U>
	implements Function2<Boolean,U,U>
{
	// x1 = x0 - f(x0) / f'(x0)
	// iterate

	private final T alg;
	private final Procedure2<U,U> f;
	private final Derivative<T,U> fPrime;
	private final long maxIters;

	/**
	 * 
	 * @param alg
	 * @param f
	 * @param delta
	 * @param maxIters
	 */
	public NewtonsMethod(T alg, Procedure2<U,U> f, U delta, long maxIters) {
		this.alg = alg;
		this.f = f;
		this.fPrime = new Derivative<T,U>(alg, f, delta);
		this.maxIters = maxIters;
		if (maxIters < 1)
			throw new IllegalArgumentException("number of iterations must be > 0");
	}
	
	// TODO: detect convergence status. return true if converged and false if not.

	@Override
	public Boolean call(U guess, U result) {
		U tmp = alg.construct(guess);
		U n = alg.construct();
		U d = alg.construct();
		U correction = alg.construct();
		for (long i = 0; i < maxIters; i++) {
			f.call(tmp, n);
			fPrime.call(tmp, d);
			alg.divide().call(n, d, correction);
			alg.subtract().call(tmp, correction, tmp);
		}
		alg.assign().call(tmp, result);
		return true;
	}
}
