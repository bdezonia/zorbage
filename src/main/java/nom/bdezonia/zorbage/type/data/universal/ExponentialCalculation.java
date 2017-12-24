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
package nom.bdezonia.zorbage.type.data.universal;

import java.math.BigDecimal;
import java.math.MathContext;

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Power;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ExponentialCalculation {

	private static final MathContext context = new MathContext(100);

	/**
	 * Create a numeric value from an exponential description. Works in
	 * BigDecimals for accuracy and rounds to 100 decimal places. Passed
	 * in type assigned from universal string parsing representation.
	 * 
	 * @param group
	 * @param fraction
	 * @param base
	 * @param power
	 * @param result
	 */
	public static <T extends Group<T,U> & Power<U>, U>
		void compute(T group, BigDecimal fraction, int base, int power, U result)
	{
		if (base < 0)
			throw new IllegalArgumentException("negative bases not allowed");
		if (base == 0 && power == 0)
			throw new IllegalArgumentException("0^0 is not a number");
		BigDecimal b = BigDecimal.valueOf(base);
		BigDecimal exponentiation = b.pow(power, context);
		BigDecimal x = fraction.multiply(exponentiation);
		x = x.round(context);
		U tmp = group.construct(x.toString());
		group.assign(tmp, result);
	}
}
