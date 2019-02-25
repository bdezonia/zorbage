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
import java.math.RoundingMode;

import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ScientificNotation {

	private static final MathContext context = new MathContext(100, RoundingMode.HALF_EVEN);

	/**
	 * Create a numeric value from an scientific notation description.
	 * Works in BigDecimals for up to 100 decimal places of accuracy.
	 * Can only create real values using this method. Result must
	 * support UniversalRepresentation.
	 * 
	 * @param fraction
	 * @param base
	 * @param power
	 * @param result
	 */
	public static <U extends UniversalRepresentation>
		void compute(BigDecimal fraction, int base, int power, U result)
	{
		BigDecimal b = BigDecimal.valueOf(base);
		BigDecimal tmp = b.pow(power, context);
		BigDecimal value = tmp.multiply(fraction, context);
		OctonionRepresentation v = new OctonionRepresentation();
		v.setR(value);
		TensorOctonionRepresentation rep = new TensorOctonionRepresentation();
		rep.setValue(v);
		result.fromRep(rep);
	}
}
