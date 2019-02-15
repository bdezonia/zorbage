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
import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FindFixedPoint {

	/**
	 * 
	 * @param alg
	 * @param proc
	 * @param closeEnough
	 * @param firstGuess
	 * @param maxIters
	 * @param result
	 * @return
	 */
	public static <T extends Algebra<T,U>, U>
		long compute(T alg, Procedure2<U,U> proc, Function2<Boolean,U,U> closeEnough, U firstGuess, long maxIters, U result)
	{
		if (maxIters < 0)
			throw new IllegalArgumentException("maxIters must be >= 0");
		U guess = alg.construct(firstGuess);
		U tmp = alg.construct();
		return iterate(alg, proc, closeEnough, guess, tmp, maxIters, result);
	}
	
	private static <T extends Algebra<T,U>, U>
		long iterate(T alg, Procedure2<U,U> proc, Function2<Boolean,U,U> closeEnough, U guess, U tmp, long iters, U result)
	{
		if (iters == 0)
			return -1;
		proc.call(guess, tmp);
		if (closeEnough.call(guess, tmp)) {
			alg.assign().call(guess, result);
			return iters;
		}
		else {
			alg.assign().call(tmp, guess);
			return iterate(alg, proc, closeEnough, guess, tmp, iters-1, result);
		}			
	}
}
