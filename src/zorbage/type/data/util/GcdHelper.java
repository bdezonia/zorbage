/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.data.util;

import zorbage.type.algebra.AbsoluteValue;
import zorbage.type.algebra.Group;
import zorbage.type.algebra.IntegralDivision;
import zorbage.type.algebra.Multiplication;
import zorbage.type.algebra.Ordered;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GcdHelper {
	
	private GcdHelper() {}
	
	public static <T extends Group<T,U> & IntegralDivision<U> & Ordered<U>, U>
		void findGcd(T group, U zero, U a, U b, U result)
	{
		U aTmp = group.construct(a);
		U bTmp = group.construct(b);
		U t = group.construct();
		while (group.compare(bTmp, zero) != 0) {
			group.assign(bTmp, t);
			group.mod(aTmp, bTmp, bTmp);
			group.assign(t, aTmp);
		}
		group.assign(aTmp, result);
	}

	public static <T extends Group<T,U> & AbsoluteValue<U> & IntegralDivision<U> & Multiplication<U> & Ordered<U>, U>
		void findLcm(T group, U zero, U a, U b, U result)
	{
		U n = group.construct();
		U d = group.construct();
		group.multiply(a,b,n);
		group.abs(n,n);
		GcdHelper.findGcd(group, zero, a, b, d);
		group.div(n,d,result);
	}
}
