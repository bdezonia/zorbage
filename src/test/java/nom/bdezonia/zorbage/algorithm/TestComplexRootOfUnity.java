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
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestComplexRootOfUnity {

	@Test
	public void test() {
		
		double tol = 0.00000000000001; 
		
		ComplexFloat64Member root = G.CDBL.construct();
		
		ComplexRootOfUnity.compute(1, 1, root);
		assertEquals(1, root.r(), tol);
		assertEquals(0, root.i(), tol);
		
		ComplexRootOfUnity.compute(2, 1, root);
		assertEquals(-1, root.r(), tol);
		assertEquals(0, root.i(), tol);
		
		ComplexRootOfUnity.compute(2, 2, root);
		assertEquals(1, root.r(), tol);
		assertEquals(0, root.i(), tol);
		
		ComplexRootOfUnity.compute(3, 1, root);
		assertEquals(-0.5, root.r(), tol);
		assertEquals(Math.sqrt(3)/2, root.i(), tol);
		
		ComplexRootOfUnity.compute(3, 2, root);
		assertEquals(-0.5, root.r(), tol);
		assertEquals(-Math.sqrt(3)/2, root.i(), tol);
		
		ComplexRootOfUnity.compute(3, 3, root);
		assertEquals(1, root.r(), tol);
		assertEquals(0, root.i(), tol);

		ComplexRootOfUnity.compute(4, 1, root);
		assertEquals(0, root.r(), tol);
		assertEquals(1, root.i(), tol);

		ComplexRootOfUnity.compute(4, 2, root);
		assertEquals(-1, root.r(), tol);
		assertEquals(0, root.i(), tol);

		ComplexRootOfUnity.compute(4, 3, root);
		assertEquals(0, root.r(), tol);
		assertEquals(-1, root.i(), tol);

		ComplexRootOfUnity.compute(4, 4, root);
		assertEquals(1, root.r(), tol);
		assertEquals(0, root.i(), tol);
		
	}
}
