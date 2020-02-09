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

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMinMax {

	@Test
	public void test() {
		SignedInt64Member a = G.INT64.construct();
		SignedInt64Member b = G.INT64.construct();
		SignedInt64Member min = G.INT64.construct();
		SignedInt64Member max = G.INT64.construct();
		
		a.setV(17);
		b.setV(17);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(17,  min.v());
		assertEquals(17,  max.v());
		
		a.setV(43);
		b.setV(99);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(43,  min.v());
		assertEquals(99,  max.v());
		
		b.setV(43);
		a.setV(99);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(43,  min.v());
		assertEquals(99,  max.v());
		
		a.setV(-2);
		b.setV(-7);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(-7,  min.v());
		assertEquals(-2,  max.v());
		
		b.setV(-2);
		a.setV(-7);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(-7,  min.v());
		assertEquals(-2,  max.v());
		
		a.setV(-2);
		b.setV(2);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(-2,  min.v());
		assertEquals(2,  max.v());
		
		b.setV(-2);
		a.setV(2);
		MinMax.compute(G.INT64, a, b, min, max);
		assertEquals(-2,  min.v());
		assertEquals(2,  max.v());
	}
}
