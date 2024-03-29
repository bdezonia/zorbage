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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FindFixedPoint<T extends Algebra<T,U>, U>
	implements Function2<Long,U,U>
{
	private final Algebra<T,U> alg;
	private final Procedure2<U,U> proc;
	private final Function2<Boolean,U,U> closeEnough;
	private long maxIters;
	
	/**
	 * 
	 * @param alg
	 * @param proc
	 * @param closeEnough
	 * @param maxIters
	 */
	public FindFixedPoint(T alg, Procedure2<U,U> proc, Function2<Boolean,U,U> closeEnough, long maxIters) {
		this.alg = alg;
		this.proc = proc;
		this.closeEnough = closeEnough;
		this.maxIters = maxIters;
		if (maxIters < 1)
			throw new IllegalArgumentException("maxIters must be > 0");
	}
	
	public long getMaxIters() {
		return maxIters;
	}
	
	public void setMaxIters(long maxIters) {
		if (maxIters < 1)
			throw new IllegalArgumentException("maxIters must be > 0");
		this.maxIters = maxIters;
	}

	@Override
	public Long call(U firstGuess, U result)
	{
		U guess = alg.construct(firstGuess);
		U tmp = alg.construct();
		return iterate(guess, tmp, maxIters, result);
	}
	
	private long iterate(U guess, U tmp, long iters, U result)
	{
		for (long i = 0; i < iters; i++) {
			proc.call(guess, tmp);
			if (closeEnough.call(guess, tmp)) {
				alg.assign().call(guess, result);
				return i;
			}
			else {
				alg.assign().call(tmp, guess);
			}			
		}
		return -1;
	}
}
