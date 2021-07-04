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
package nom.bdezonia.zorbage.type.real.float128;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.LUDecomp;
import nom.bdezonia.zorbage.algorithm.LUSolve;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat128Matrix {

	@Test
	public void testMatrixInverse() {
		
		BigDecimal tol = BigDecimal.valueOf(0.00000000000000001);
		
		int[] ints = new int[]{1,7,4,1,2,4,8,3,3};
		
		BigDecimal[] values = new BigDecimal[ints.length];
		
		for (int i = 0; i < ints.length; i++) {
			values[i] = BigDecimal.valueOf(ints[i]);
		}
		
		// Used wolfram alpha to come up with this example
		
		Float128MatrixMember mat = new Float128MatrixMember(3, 3, values);
		
		Float128MatrixMember invMat = new Float128MatrixMember(3, 3);
		
		Float128MatrixMember ident = new Float128MatrixMember(3, 3);
		
		G.QUAD_MAT.invert().call(mat, invMat);
		
		Float128Member value = new Float128Member();

		invMat.getV(0, 0, value);
		assertTrue(BigDecimalUtils.isNear(-6.0/145, value.v(), tol));
		
		invMat.getV(0, 1, value);
		assertTrue(BigDecimalUtils.isNear(-9.0/145, value.v(), tol));
		
		invMat.getV(0, 2, value);
		assertTrue(BigDecimalUtils.isNear(20.0/145, value.v(), tol));
		
		invMat.getV(1, 0, value);
		assertTrue(BigDecimalUtils.isNear(29.0/145, value.v(), tol));
		
		invMat.getV(1, 1, value);
		assertTrue(BigDecimalUtils.isNear(-29.0/145, value.v(), tol));
		
		invMat.getV(1, 2, value);
		assertTrue(BigDecimalUtils.isNear(0.0/145, value.v(), tol));
		
		invMat.getV(2, 0, value);
		assertTrue(BigDecimalUtils.isNear(-13.0/145, value.v(), tol));
		
		invMat.getV(2, 1, value);
		assertTrue(BigDecimalUtils.isNear(53.0/145, value.v(), tol));
		
		invMat.getV(2, 2, value);
		assertTrue(BigDecimalUtils.isNear(-5.0/145, value.v(), tol));
		
		G.QUAD_MAT.multiply().call(mat, invMat, ident);
		
		ident.getV(0, 0, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), tol));
		
		ident.getV(0, 1, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), tol));
		
		ident.getV(0, 2, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), tol));
		
		ident.getV(1, 0, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), tol));
		
		ident.getV(1, 1, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), tol));
		
		ident.getV(1, 2, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), tol));
		
		ident.getV(2, 0, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), tol));
		
		ident.getV(2, 1, value);
		assertTrue(BigDecimalUtils.isNear(0, value.v(), tol));
		
		ident.getV(2, 2, value);
		assertTrue(BigDecimalUtils.isNear(1, value.v(), tol));
	}
	
	@Test
	public void testSingular() {
		BigDecimal[] matVals = new BigDecimal[] {BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		BigDecimal[] vecVals = new BigDecimal[] {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)};
		Float128MatrixMember a = new Float128MatrixMember(3, 3, matVals);
		Float128VectorMember b = new Float128VectorMember(vecVals);
		Float128VectorMember x = new Float128VectorMember(3);
		LUDecomp.compute(G.QUAD, G.QUAD_MAT, a);
		LUSolve.compute(G.QUAD, G.QUAD_VEC, a, b, x);
		Float128Member v = new Float128Member();
		x.getV(0, v);
		//System.out.println(v.v());
		x.getV(1, v);
		//System.out.println(v.v());
		x.getV(2, v);
		//System.out.println(v.v());
	}
}