/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.procedure.impl;

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.IntegralDivision;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ModL<T extends Algebra<T,U> & IntegralDivision<U>, U>
	implements Procedure<U>
{
	private final Procedure<U> ancestor1;
	private final Procedure<U> ancestor2;
	private final Mod<T,U> lowerProc;
	private final T algebra;
	private final ThreadLocal<U> tmp1;
	private final ThreadLocal<U> tmp2;
	
	public ModL(T alg, Procedure<U> ancestor1, Procedure<U> ancestor2) {
		this.algebra = alg;
		this.ancestor1 = ancestor1;
		this.ancestor2 = ancestor2;
		this.lowerProc = new Mod<T,U>(algebra);
		this.tmp1 = new ThreadLocal<U>() {
			@Override
			protected U initialValue() {
				return algebra.construct();
			}
		};
		this.tmp2 = new ThreadLocal<U>() {
			@Override
			protected U initialValue() {
				return algebra.construct();
			}
		};
	}

	@Override
	@SuppressWarnings("unchecked")
	public void call(U result, U... inputs) {
		U u1 = tmp1.get();
		U u2 = tmp2.get();
		ancestor1.call(u1, inputs);
		ancestor2.call(u2, inputs);
		lowerProc.call(u1, u2, result);
	}

}
