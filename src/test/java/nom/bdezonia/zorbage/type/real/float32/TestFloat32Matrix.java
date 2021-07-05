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
package nom.bdezonia.zorbage.type.real.float32;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.LUDecomp;
import nom.bdezonia.zorbage.algorithm.LUSolve;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat32Matrix {

	@Test
	public void testMatrixInverse() {
		double tol = 0.000001;
		
		Float32MatrixMember mat =
				new Float32MatrixMember(3, 3, 1,7,4,1,2,4,8,3,3);
		
		Float32MatrixMember invMat =
				new Float32MatrixMember(3, 3, new float[9]);
		
		Float32MatrixMember ident =
				new Float32MatrixMember(3, 3, new float[9]);
		
		G.FLT_MAT.invert().call(mat, invMat);
		
		Float32Member value = new Float32Member();

		invMat.getV(0, 0, value);
		assertEquals(-6.0/145, value.v(), tol);
		invMat.getV(0, 1, value);
		assertEquals(-9.0/145, value.v(), tol);
		invMat.getV(0, 2, value);
		assertEquals(20.0/145, value.v(), tol);
		invMat.getV(1, 0, value);
		assertEquals(29.0/145, value.v(), tol);
		invMat.getV(1, 1, value);
		assertEquals(-29.0/145, value.v(), tol);
		invMat.getV(1, 2, value);
		assertEquals(0.0/145, value.v(), tol);
		invMat.getV(2, 0, value);
		assertEquals(-13.0/145, value.v(), tol);
		invMat.getV(2, 1, value);
		assertEquals(53.0/145, value.v(), tol);
		invMat.getV(2, 2, value);
		assertEquals(-5.0/145, value.v(), tol);
		
		G.FLT_MAT.multiply().call(mat, invMat, ident);

		ident.getV(0, 0, value);
		assertEquals(1, value.v(), tol);
		ident.getV(0, 1, value);
		assertEquals(0, value.v(), tol);
		ident.getV(0, 2, value);
		assertEquals(0, value.v(), tol);
		ident.getV(1, 0, value);
		assertEquals(0, value.v(), tol);
		ident.getV(1, 1, value);
		assertEquals(1, value.v(), tol);
		ident.getV(1, 2, value);
		assertEquals(0, value.v(), tol);
		ident.getV(2, 0, value);
		assertEquals(0, value.v(), tol);
		ident.getV(2, 1, value);
		assertEquals(0, value.v(), tol);
		ident.getV(2, 2, value);
		assertEquals(1, value.v(), tol);
	}
	
	@Test
	public void testSingular() {
		Float32MatrixMember a = new Float32MatrixMember(3, 3, 1,0,0,0,1,0,0,0,0);
		Float32VectorMember b = new Float32VectorMember(1,2,3);
		Float32VectorMember x = new Float32VectorMember(new float[3]);
		LUDecomp.compute(G.FLT, G.FLT_MAT, a);
		LUSolve.compute(G.FLT, G.FLT_VEC, a, b, x);
		Float32Member v = new Float32Member();
		x.getV(0, v);
		//System.out.println(v.v());
		x.getV(1, v);
		//System.out.println(v.v());
		x.getV(2, v);
		//System.out.println(v.v());
	}
}