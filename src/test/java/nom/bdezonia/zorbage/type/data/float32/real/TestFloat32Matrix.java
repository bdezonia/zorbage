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
package nom.bdezonia.zorbage.type.data.float32.real;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.LUDecomp;
import nom.bdezonia.zorbage.algorithm.LUSolve;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat32Matrix {

	// toggle true/false if want to run this big slow test
	private static final boolean RUN = false;
	
	@Test
	public void testHugeMatrix() {
		if (RUN) {
			System.out.println("Making a huge virtual matrix > 2 gig entries");
			Float32MatrixMember m = G.FLT_MAT.construct(StorageConstruction.MEM_VIRTUAL, 50000, 50000);
			System.out.println("Setting it's values to an identity matrix");
			G.FLT_MAT.unity().call(m);
			System.out.println("Walking through it's values and asserting they are correct");
			Float32Member value = G.FLT.construct();
			Float32Member zero = G.FLT.construct();
			Float32Member one = G.FLT.construct();
			G.FLT.unity().call(one);
			for (long r = 0; r < m.rows(); r++) {
				for (long c = 0; c < m.cols(); c++) {
					m.v(r, c, value);
					if (r == c) {
						assertTrue(G.FLT.isEqual().call(value, one));
					}
					else {
						assertTrue(G.FLT.isEqual().call(value, zero));
					}
				}
			}
		}
	}
	
	@Test
	public void testMatrixInverse() {
		double tol = 0.000001;
		
		Float32MatrixMember mat =
				new Float32MatrixMember(3, 3,
						new float[] {1,7,4,1,2,4,8,3,3});
		
		Float32MatrixMember invMat =
				new Float32MatrixMember(3, 3, new float[9]);
		
		Float32MatrixMember ident =
				new Float32MatrixMember(3, 3, new float[9]);
		
		G.FLT_MAT.invert().call(mat, invMat);
		
		Float32Member value = new Float32Member();

		invMat.v(0, 0, value);
		assertEquals(-6.0/145, value.v(), tol);
		invMat.v(0, 1, value);
		assertEquals(-9.0/145, value.v(), tol);
		invMat.v(0, 2, value);
		assertEquals(20.0/145, value.v(), tol);
		invMat.v(1, 0, value);
		assertEquals(29.0/145, value.v(), tol);
		invMat.v(1, 1, value);
		assertEquals(-29.0/145, value.v(), tol);
		invMat.v(1, 2, value);
		assertEquals(0.0/145, value.v(), tol);
		invMat.v(2, 0, value);
		assertEquals(-13.0/145, value.v(), tol);
		invMat.v(2, 1, value);
		assertEquals(53.0/145, value.v(), tol);
		invMat.v(2, 2, value);
		assertEquals(-5.0/145, value.v(), tol);
		
		G.FLT_MAT.multiply().call(mat, invMat, ident);

		ident.v(0, 0, value);
		assertEquals(1, value.v(), tol);
		ident.v(0, 1, value);
		assertEquals(0, value.v(), tol);
		ident.v(0, 2, value);
		assertEquals(0, value.v(), tol);
		ident.v(1, 0, value);
		assertEquals(0, value.v(), tol);
		ident.v(1, 1, value);
		assertEquals(1, value.v(), tol);
		ident.v(1, 2, value);
		assertEquals(0, value.v(), tol);
		ident.v(2, 0, value);
		assertEquals(0, value.v(), tol);
		ident.v(2, 1, value);
		assertEquals(0, value.v(), tol);
		ident.v(2, 2, value);
		assertEquals(1, value.v(), tol);
	}
	
	@Test
	public void testSingular() {
		Float32MatrixMember a = new Float32MatrixMember(3, 3, new float[] {1,0,0,0,1,0,0,0,0});
		Float32VectorMember b = new Float32VectorMember(new float[] {1,2,3});
		Float32VectorMember x = new Float32VectorMember(new float[3]);
		LUDecomp.compute(G.FLT, G.FLT_MAT, a);
		LUSolve.compute(G.FLT, G.FLT_VEC, a, b, x);
		Float32Member v = new Float32Member();
		x.v(0, v);
		//System.out.println(v.v());
		x.v(1, v);
		//System.out.println(v.v());
		x.v(2, v);
		//System.out.println(v.v());
	}
}