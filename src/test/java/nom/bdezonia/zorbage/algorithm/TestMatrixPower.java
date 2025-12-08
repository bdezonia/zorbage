/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixPower {

	@Test
	public void test() {
		Float64Member vb = G.DBL.construct();
		Float64Member vc = G.DBL.construct();
		Float64MatrixMember a = new Float64MatrixMember(2,2,new double[] {7,-21,44,13});
		Float64MatrixMember b = G.DBL_MAT.construct();
		for (int i = -11; i <= 11; i++) {
			G.DBL_MAT.power().call(i, a, b);
			if (i < 0) {
				Float64MatrixMember invA = G.DBL_MAT.construct();
				G.DBL_MAT.invert().call(a, invA);
				Float64MatrixMember c = G.DBL_MAT.construct(invA);
				Float64MatrixMember d = G.DBL_MAT.construct();
				for (int j = 1; j < -i; j++) {
					G.DBL_MAT.multiply().call(c, invA, d);
					G.DBL_MAT.assign().call(d, c);
				}
				for (int rr = 0; rr < b.rows(); rr++) {
					for (int cc = 0; cc < b.cols(); cc++) {
						b.getV(rr, cc, vb);
						c.getV(rr, cc, vc);
						assertEquals(vb.v(), vc.v(), 0.0000000000001);
					}
				}
			}
			else if (i > 0) {
				Float64MatrixMember c = G.DBL_MAT.construct(a);
				Float64MatrixMember d = G.DBL_MAT.construct();
				for (int j = 1; j < i; j++) {
					G.DBL_MAT.multiply().call(c, a, d);
					G.DBL_MAT.assign().call(d, c);
				}
				for (int rr = 0; rr < b.rows(); rr++) {
					for (int cc = 0; cc < b.cols(); cc++) {
						b.getV(rr, cc, vb);
						c.getV(rr, cc, vc);
						assertEquals(vb.v(), vc.v(), 0.0000000000001);
					}
				}
			}
			else {
				// i == 0
				assertEquals(2, b.rows());
				assertEquals(2, b.cols());
				b.getV(0, 0, vb);
				assertEquals(1, vb.v(), 0);
				b.getV(0, 1, vb);
				assertEquals(0, vb.v(), 0);
				b.getV(1, 0, vb);
				assertEquals(0, vb.v(), 0);
				b.getV(1, 1, vb);
				assertEquals(1, vb.v(), 0);
			}
		}
	}
}
