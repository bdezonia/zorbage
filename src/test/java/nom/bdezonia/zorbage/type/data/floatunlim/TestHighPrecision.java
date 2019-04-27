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
package nom.bdezonia.zorbage.type.data.floatunlim;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.floatunlim.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.data.floatunlim.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestHighPrecision {

	@Test
	public void test() {
		HighPrecisionMember a = G.BIGDEC.construct();
		try {
			HighPrecisionAlgebra.setPrecision(0);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		try {
			HighPrecisionAlgebra.setPrecision(2001);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		HighPrecisionAlgebra.setPrecision(1);
		G.BIGDEC.E().call(a);
		assertTrue(BigDecimal.valueOf(2.7).equals(a.v()));
		G.BIGDEC.PI().call(a);
		assertTrue(BigDecimal.valueOf(3.1).equals(a.v()));
		HighPrecisionAlgebra.setPrecision(10);
		G.BIGDEC.E().call(a);
		assertTrue(BigDecimal.valueOf(2.7182818284).equals(a.v()));
		G.BIGDEC.PI().call(a);
		assertTrue(BigDecimal.valueOf(3.1415926535).equals(a.v()));
		// test that the max limit code will actually run
		HighPrecisionAlgebra.setPrecision(2000);
		G.BIGDEC.E().call(a);
		G.BIGDEC.PI().call(a);
		assertTrue(true);
	}
}
