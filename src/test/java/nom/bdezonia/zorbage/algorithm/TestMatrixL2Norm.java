/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixL2Norm {

	@Test
	public void testReal() {
		Float64Member result = G.DBL.construct();
		Float64MatrixMember mat = new Float64MatrixMember(2, 3, new double[] {0,-2,4,-6,8,-10});
		SequenceL2Norm.compute(G.DBL, G.DBL, mat.rawData(), result);
		assertEquals(Math.sqrt(220), result.v(), 0);
	}

	@Test
	public void testComplex() {
		Float64Member result = G.DBL.construct();
		ComplexFloat64MatrixMember mat = new ComplexFloat64MatrixMember(2, 3, new double[] {0, 0, -2, 0, 4, 0, -6, 0, 8, 0, -10, 0});
		SequenceL2Norm.compute(G.CDBL, G.DBL, mat.rawData(), result);
		assertEquals(Math.sqrt(220), result.v(), 0);
	}

	@Test
	public void testQuat() {
		Float64Member result = G.DBL.construct();
		QuaternionFloat64MatrixMember mat = new QuaternionFloat64MatrixMember(2, 3, new double[] {0, 0, 0, 0, -2, 0, 0, 0, 4, 0, 0, 0, -6, 0, 0, 0, 8, 0, 0, 0, -10, 0, 0, 0});
		SequenceL2Norm.compute(G.QDBL, G.DBL, mat.rawData(), result);
		assertEquals(Math.sqrt(220), result.v(), 0);
	}

	@Test
	public void testOct() {
		Float64Member result = G.DBL.construct();
		OctonionFloat64MatrixMember mat = new OctonionFloat64MatrixMember(2, 3, new double[] {0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, -6, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, -10, 0, 0, 0, 0, 0, 0, 0});
		SequenceL2Norm.compute(G.ODBL, G.DBL, mat.rawData(), result);
		assertEquals(Math.sqrt(220), result.v(), 0);
	}
}
