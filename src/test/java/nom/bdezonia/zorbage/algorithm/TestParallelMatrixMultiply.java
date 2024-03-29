/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
public class TestParallelMatrixMultiply {

	@Test
	public void test1() {
		Float64MatrixMember a = new Float64MatrixMember(117, 41);
		Float64MatrixMember b = new Float64MatrixMember(41, 79);
		Float64MatrixMember c1 = G.DBL_MAT.construct();
		Float64MatrixMember c2 = G.DBL_MAT.construct();
		Fill.compute(G.DBL, G.DBL.random(), a.rawData());
		Fill.compute(G.DBL, G.DBL.random(), b.rawData());
		MatrixMultiply.compute(G.DBL, a, b, c1);
		MatrixMultiply.compute(G.DBL, a, b, c2);
		assertEquals(c1.rows(), c2.rows());
		assertEquals(c1.cols(), c2.cols());
		Float64Member t1 = G.DBL.construct();
		Float64Member t2 = G.DBL.construct();
		for (long r = 0; r < c1.rows(); r++) {
			for (long c = 0; c < c1.cols(); c++) {
				c1.getV(r, c, t1);
				c2.getV(r, c, t2);
				assertEquals(t1.v(), t2.v(), 0);
			}			
		}
	}

	@Test
	public void test2() {
		Float64MatrixMember a = new Float64MatrixMember(117, 41);
		Float64MatrixMember b = new Float64MatrixMember(41, 79);
		Float64MatrixMember c1 = G.DBL_MAT.construct();
		Float64MatrixMember c2 = G.DBL_MAT.construct();
		Fill.compute(G.DBL, G.DBL.random(), a.rawData());
		Fill.compute(G.DBL, G.DBL.random(), b.rawData());
		int numTrials = 20;
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < numTrials; i++) {
			MatrixMultiply.compute(G.DBL, a, b, c1);
		}
		long t2 = System.currentTimeMillis();
		for (int i = 0; i < numTrials; i++) {
			MatrixMultiply.compute(G.DBL, a, b, c2);
		}
		long t3 = System.currentTimeMillis();
		
		System.out.println("Regular  multiply : "+(t2-t1));
		System.out.println("Parallel multiply : "+(t3-t2));
	}
}
