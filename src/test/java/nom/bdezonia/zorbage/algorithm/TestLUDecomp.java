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

import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestLUDecomp {

	@Test
	public void testDouble() {
		
		Float64Member val = G.DBL.construct();

		Float64MatrixMember a = G.DBL_MAT.construct(StorageConstruction.MEM_ARRAY, 3, 3);
		G.DBL.unity().call(val);
		a.setV(0, 0, val);
		a.setV(0, 1, val);
		a.setV(1, 1, val);
		a.setV(1, 2, val);
		a.setV(2, 0, val);
		a.setV(2, 2, val);
		
		Float64VectorMember b = G.DBL_VEC.construct(StorageConstruction.MEM_ARRAY, 3);
		val = G.DBL.construct("4");
		b.setV(0, val);
		val = G.DBL.construct("10");
		b.setV(1, val);
		val = G.DBL.construct("20");
		b.setV(2, val);
		
		Float64VectorMember x = G.DBL_VEC.construct(StorageConstruction.MEM_ARRAY, 3);

		LUDecomp.compute(G.DBL, G.DBL_MAT, a);

		LUSolve.compute(G.DBL, G.DBL_VEC, a, b, x);

		Float64Member value = new Float64Member();
		
		assertEquals(3, x.length());
		
		x.getV(0, value );
		assertEquals(7, value.v(), 0.000000000001);
		
		x.getV(1, value );
		assertEquals(-3, value.v(), 0.000000000001);
		
		x.getV(2, value );
		assertEquals(13, value.v(), 0.000000000001);
	}
	
	@Test
	public void testComplexDouble() {
		
		ComplexFloat64Member val = G.CDBL.construct();

		ComplexFloat64MatrixMember a = G.CDBL_MAT.construct(StorageConstruction.MEM_ARRAY, 3, 3);
		G.CDBL.unity().call(val);
		a.setV(0, 0, val);
		a.setV(0, 1, val);
		a.setV(1, 1, val);
		a.setV(1, 2, val);
		a.setV(2, 0, val);
		a.setV(2, 2, val);
		
		ComplexFloat64VectorMember b = G.CDBL_VEC.construct(StorageConstruction.MEM_ARRAY, 3);
		val = G.CDBL.construct("{4,0}");
		b.setV(0, val);
		val = G.CDBL.construct("{10,0}");
		b.setV(1, val);
		val = G.CDBL.construct("{20,0}");
		b.setV(2, val);
		
		ComplexFloat64VectorMember x = G.CDBL_VEC.construct(StorageConstruction.MEM_ARRAY, 3);

		LUDecomp.compute(G.CDBL, G.CDBL_MAT, a);

		LUSolve.compute(G.CDBL, G.CDBL_VEC, a, b, x);

		ComplexFloat64Member value = new ComplexFloat64Member();
		
		assertEquals(3, x.length());
		
		x.getV(0, value );
		assertEquals(7, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		
		x.getV(1, value );
		assertEquals(-3, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		
		x.getV(2, value );
		assertEquals(13, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
	}
	
	@Test
	public void testQuaternionDouble() {
		
		QuaternionFloat64Member val = G.QDBL.construct();

		QuaternionFloat64MatrixMember a = G.QDBL_MAT.construct(StorageConstruction.MEM_ARRAY, 3, 3);
		G.QDBL.unity().call(val);
		a.setV(0, 0, val);
		a.setV(0, 1, val);
		a.setV(1, 1, val);
		a.setV(1, 2, val);
		a.setV(2, 0, val);
		a.setV(2, 2, val);
		
		QuaternionFloat64RModuleMember b = G.QDBL_RMOD.construct(StorageConstruction.MEM_ARRAY, 3);
		val = G.QDBL.construct("{4,0,0,0}");
		b.setV(0, val);
		val = G.QDBL.construct("{10,0,0,0}");
		b.setV(1, val);
		val = G.QDBL.construct("{20,0,0,0}");
		b.setV(2, val);
		
		QuaternionFloat64RModuleMember x = G.QDBL_RMOD.construct(StorageConstruction.MEM_ARRAY, 3);

		LUDecomp.compute(G.QDBL, G.QDBL_MAT, a);

		LUSolve.compute(G.QDBL, G.QDBL_RMOD, a, b, x);

		QuaternionFloat64Member value = new QuaternionFloat64Member();
		
		assertEquals(3, x.length());
		
		x.getV(0, value );
		assertEquals(7, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		assertEquals(0, value.j(), 0.000000000001);
		assertEquals(0, value.k(), 0.000000000001);
		
		x.getV(1, value );
		assertEquals(-3, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		assertEquals(0, value.j(), 0.000000000001);
		assertEquals(0, value.k(), 0.000000000001);
		
		x.getV(2, value );
		assertEquals(13, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		assertEquals(0, value.j(), 0.000000000001);
		assertEquals(0, value.k(), 0.000000000001);
	}

	@Test
	public void testOctonionDouble() {
		
		OctonionFloat64Member val = G.ODBL.construct();

		OctonionFloat64MatrixMember a = G.ODBL_MAT.construct(StorageConstruction.MEM_ARRAY, 3, 3);
		G.ODBL.unity().call(val);
		a.setV(0, 0, val);
		a.setV(0, 1, val);
		a.setV(1, 1, val);
		a.setV(1, 2, val);
		a.setV(2, 0, val);
		a.setV(2, 2, val);
		
		OctonionFloat64RModuleMember b = G.ODBL_RMOD.construct(StorageConstruction.MEM_ARRAY, 3);
		val = G.ODBL.construct("{4,0,0,0,0,0,0,0}");
		b.setV(0, val);
		val = G.ODBL.construct("{10,0,0,0,0,0,0,0}");
		b.setV(1, val);
		val = G.ODBL.construct("{20,0,0,0,0,0,0,0}");
		b.setV(2, val);
		
		OctonionFloat64RModuleMember x = G.ODBL_RMOD.construct(StorageConstruction.MEM_ARRAY, 3);

		LUDecomp.compute(G.ODBL, G.ODBL_MAT, a);

		LUSolve.compute(G.ODBL, G.ODBL_RMOD, a, b, x);

		OctonionFloat64Member value = new OctonionFloat64Member();
		
		assertEquals(3, x.length());
		
		x.getV(0, value );
		assertEquals(7, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		assertEquals(0, value.j(), 0.000000000001);
		assertEquals(0, value.k(), 0.000000000001);
		assertEquals(0, value.l(), 0.000000000001);
		assertEquals(0, value.i0(), 0.000000000001);
		assertEquals(0, value.j0(), 0.000000000001);
		assertEquals(0, value.k0(), 0.000000000001);
		
		x.getV(1, value );
		assertEquals(-3, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		assertEquals(0, value.j(), 0.000000000001);
		assertEquals(0, value.k(), 0.000000000001);
		assertEquals(0, value.l(), 0.000000000001);
		assertEquals(0, value.i0(), 0.000000000001);
		assertEquals(0, value.j0(), 0.000000000001);
		assertEquals(0, value.k0(), 0.000000000001);
		
		x.getV(2, value );
		assertEquals(13, value.r(), 0.000000000001);
		assertEquals(0, value.i(), 0.000000000001);
		assertEquals(0, value.j(), 0.000000000001);
		assertEquals(0, value.k(), 0.000000000001);
		assertEquals(0, value.l(), 0.000000000001);
		assertEquals(0, value.i0(), 0.000000000001);
		assertEquals(0, value.j0(), 0.000000000001);
		assertEquals(0, value.k0(), 0.000000000001);
	}
}
