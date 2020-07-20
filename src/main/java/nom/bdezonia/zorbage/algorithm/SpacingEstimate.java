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

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SpacingEstimate {

	// do not instantiate
	
	private SpacingEstimate() { }

	/**
	 * Note: for linear axis equations this spacing estimate is exact.
	 * 
	 * @param data
	 * @param result
	 */
	public static
		void compute(DimensionedDataSource<?> data, RModuleMember<HighPrecisionMember> result)
	{
		RModuleMember<HighPrecisionMember> min = G.HP_VEC.construct();
		RModuleMember<HighPrecisionMember> max = G.HP_VEC.construct();

		BoundingBox.compute(data, min, max);
		
		result.alloc(min.length());
		
		HighPrecisionMember avgSpacing = G.HP.construct();
		HighPrecisionMember numer = G.HP.construct();
		HighPrecisionMember denom = G.HP.construct();
		HighPrecisionMember left = G.HP.construct();
		HighPrecisionMember right = G.HP.construct();
		for (int i = 0; i < data.numDimensions(); i++) {
			max.getV(i, right);
			min.getV(i, left);
			G.HP.subtract().call(right, left, numer);
			denom.setV(BigDecimal.valueOf(data.dimension(i)));
			G.HP.divide().call(numer, denom, avgSpacing);
			result.setV(i, avgSpacing);
		}
	}
}
