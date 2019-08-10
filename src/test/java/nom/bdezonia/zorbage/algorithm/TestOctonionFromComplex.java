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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestOctonionFromComplex {

	@Test
	public void test1() {
		
		ComplexFloat64Member c1 = G.CDBL.construct("{1,4}");
		ComplexFloat64Member c2 = G.CDBL.construct("{9,2}");
		ComplexFloat64Member c3 = G.CDBL.construct("{7,3}");
		ComplexFloat64Member c4 = G.CDBL.construct("{6,5}");
		OctonionFloat64Member out = G.ODBL.construct();
		OctonionFromComplex.compute(G.DBL, c1, c2, c3, c4, out);
		assertEquals(1, out.r(), 0);
		assertEquals(4, out.i(), 0);
		assertEquals(9, out.j(), 0);
		assertEquals(2, out.k(), 0);
		assertEquals(7, out.l(), 0);
		assertEquals(3, out.i0(), 0);
		assertEquals(6, out.j0(), 0);
		assertEquals(5, out.k0(), 0);
	}
}
