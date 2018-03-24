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
package nom.bdezonia.zorbage.type.data.float64.real;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64Matrix {

	// toggle true/false if want to run this big slow test
	private static final boolean RUN = false;
	
	@Test
	public void run() {
		if (RUN) {
			System.out.println("Making a huge virtual matrix > 2 gig entries");
			Float64MatrixMember m = G.DBL_MAT.construct(MemoryConstruction.DENSE, StorageConstruction.FILE, 50000, 50000);
			G.DBL_MAT.unity(m);
			Float64Member value = G.DBL.construct();
			Float64Member zero = G.DBL.construct();
			Float64Member one = G.DBL.construct();
			G.DBL.unity(one);
			for (long r = 0; r < m.rows(); r++) {
				for (long c = 0; c < m.cols(); c++) {
					m.v(r, c, value);
					if (r == c) {
						assertTrue(G.DBL.isEqual(value, one));
					}
					else {
						assertTrue(G.DBL.isEqual(value, zero));
					}
				}
			}
		}
	}
	
	@Test
	public void test() {
		Float64MatrixMember mat =
				new Float64MatrixMember(3, 3,
						new double[] {1,7,4,1,2,4,8,3,3});
		Float64MatrixMember invMat =
				new Float64MatrixMember(3, 3, new double[9]);
		G.DBL_MAT.invert(mat, invMat);
		Float64Member value = new Float64Member();
		invMat.v(0, 0, value);
		assertEquals(-6.0/145, value.v(), 0.000000000001);
		invMat.v(0, 1, value);
		assertEquals(-9.0/145, value.v(), 0.000000000001);
		invMat.v(0, 2, value);
		assertEquals(20.0/145, value.v(), 0.000000000001);
		invMat.v(1, 0, value);
		assertEquals(29.0/145, value.v(), 0.000000000001);
		invMat.v(1, 1, value);
		assertEquals(-29.0/145, value.v(), 0.000000000001);
		invMat.v(1, 2, value);
		assertEquals(0.0/145, value.v(), 0.000000000001);
		invMat.v(2, 0, value);
		assertEquals(-13.0/145, value.v(), 0.000000000001);
		invMat.v(2, 1, value);
		assertEquals(53.0/145, value.v(), 0.000000000001);
		invMat.v(2, 2, value);
		assertEquals(-5.0/145, value.v(), 0.000000000001);
	}
}