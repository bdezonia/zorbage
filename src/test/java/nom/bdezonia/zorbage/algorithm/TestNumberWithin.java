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

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.integer.int4.UnsignedInt4Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNumberWithin {

	@Test
	public void signedInteger() {

		SignedInt4Member elemA = G.INT4.construct();
		SignedInt4Member elemB = G.INT4.construct();
		SignedInt4Member elemTol = G.INT4.construct();
		for (int a = -8; a < 8; a++) {
			elemA.setV(a);
			for (int b = -8; b < 8; b++) {
				elemB.setV(b);
				for (int tol = 0; tol < 8; tol++) {
					elemTol.setV(tol);
					boolean algo = NumberWithin.compute(G.INT4, elemTol, elemA, elemB);
					boolean test = Math.abs(a-b) <= tol;
					assertEquals(test, algo);
				}
			}
		}
	}

	@Test
	public void unsignedInteger() {

		UnsignedInt4Member elemA = G.UINT4.construct();
		UnsignedInt4Member elemB = G.UINT4.construct();
		UnsignedInt4Member elemTol = G.UINT4.construct();
		for (int a = 0; a < 16; a++) {
			elemA.setV(a);
			for (int b = 0; b < 16; b++) {
				elemB.setV(b);
				for (int tol = 0; tol < 16; tol++) {
					elemTol.setV(tol);
					boolean algo = NumberWithin.compute(G.UINT4, elemTol, elemA, elemB);
					boolean test = Math.abs(a-b) <= tol;
					assertEquals(test, algo);
				}
			}
		}
	}

	@Test
	public void signedFloat() {

		Float64Member elemA = G.DBL.construct();
		Float64Member elemB = G.DBL.construct();
		Float64Member elemTol = G.DBL.construct();
		for (double a = -8; a < 8; a += 0.25) {
			elemA.setV(a);
			for (double b = -8; b < 8; b += 0.25) {
				elemB.setV(b);
				for (double tol = 0; tol < 8; tol += 0.25) {
					elemTol.setV(tol);
					boolean algo = NumberWithin.compute(G.DBL, elemTol, elemA, elemB);
					boolean test = Math.abs(a-b) <= tol;
					assertEquals(test, algo);
				}
			}
		}
	}

	@Test
	public void unsignedFloat() {

		Float64Member elemA = G.DBL.construct();
		Float64Member elemB = G.DBL.construct();
		Float64Member elemTol = G.DBL.construct();
		for (double a = 0; a < 16; a += 0.25) {
			elemA.setV(a);
			for (double b = 0; b < 16; b += 0.25) {
				elemB.setV(b);
				for (double tol = 0; tol < 16; tol += 0.25) {
					elemTol.setV(tol);
					boolean algo = NumberWithin.compute(G.DBL, elemTol, elemA, elemB);
					boolean test = Math.abs(a-b) <= tol;
					assertEquals(test, algo);
				}
			}
		}
	}
}
