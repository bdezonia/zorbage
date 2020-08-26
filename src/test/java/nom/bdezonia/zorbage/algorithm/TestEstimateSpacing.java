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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.axis.StringDefinedAxisEquation;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionVectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestEstimateSpacing {

	@Test
	public void test1() {
		
		HighPrecisionVectorMember spacings = G.HP_VEC.construct();
		
		long[] dims = new long[] {5,6,7,8,9};
		
		DimensionedDataSource<Float64Member> data = DimensionedStorage.allocate(dims, G.DBL.construct());
		
		data.setAxisEquation(0, new StringDefinedAxisEquation("0.5 + 2*$0"));
		data.setAxisEquation(1, new StringDefinedAxisEquation("0.6 + 3*$0"));
		data.setAxisEquation(2, new StringDefinedAxisEquation("0.7 + 4*$0"));
		data.setAxisEquation(3, new StringDefinedAxisEquation("0.8 + 5*$0"));
		data.setAxisEquation(4, new StringDefinedAxisEquation("0.9 + 6*$0"));

		EstimateSpacing.compute(data, spacings);

		HighPrecisionMember value = G.HP.construct();
		
		assertEquals(dims.length, spacings.length());
		
		spacings.getV(0, value);
		assertEquals(BigDecimal.valueOf(2.0), value.v());
		spacings.getV(1, value);
		assertEquals(BigDecimal.valueOf(3.0), value.v());
		spacings.getV(2, value);
		assertEquals(BigDecimal.valueOf(4.0), value.v());
		spacings.getV(3, value);
		assertEquals(BigDecimal.valueOf(5.0), value.v());
		spacings.getV(4, value);
		assertEquals(BigDecimal.valueOf(6.0), value.v());
		
	}
}
