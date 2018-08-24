/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestRModuleConjugate {
	
	@Test
	public void test() {
		//TODO: fix this crash:
		// ComplexFloat64VectorMember a = G.CDBL_VEC.construct("[{1,0},{2,-7},{3,5}]");
		ComplexFloat64Member value = G.CDBL.construct();
		ComplexFloat64VectorMember a = G.CDBL_VEC.construct(StorageConstruction.MEM_ARRAY,3);
		value.setR(1);
		value.setI(0);
		a.setV(0, value);
		value.setR(2);
		value.setI(-7);
		a.setV(1, value);
		value.setR(3);
		value.setI(5);
		a.setV(2, value);
		ComplexFloat64VectorMember b = G.CDBL_VEC.construct();
		RModuleConjugate.compute(G.CDBL, a, b);
		b.v(0, value);
		assertEquals(1, value.r(), 0);
		assertEquals(0, value.i(), 0);
		b.v(1, value);
		assertEquals(2, value.r(), 0);
		assertEquals(7, value.i(), 0);
		b.v(2, value);
		assertEquals(3, value.r(), 0);
		assertEquals(-5, value.i(), 0);
	}
}
