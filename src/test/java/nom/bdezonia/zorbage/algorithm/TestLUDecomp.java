/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import nom.bdezonia.zorbage.algorithm.LUDecomp;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64VectorMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestLUDecomp {

	@Test
	public void run() {
		
		Float64Member val = G.DBL.construct();

		Float64MatrixMember a = G.DBL_MAT.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, 3, 3);
		G.DBL.unity(val);
		a.setV(0, 0, val);
		a.setV(0, 1, val);
		a.setV(1, 1, val);
		a.setV(1, 2, val);
		a.setV(2, 0, val);
		a.setV(2, 2, val);
		
		Float64VectorMember b = G.DBL_VEC.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, 3);
		val.setV(4);
		b.setV(0, val);
		val.setV(10);
		b.setV(1, val);
		val.setV(20);
		b.setV(2, val);
		
		Float64VectorMember x = G.DBL_VEC.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, 3);

		LUDecomp.compute(G.DBL, G.DBL_VEC, G.DBL_MAT, a,b,x);

		Float64Member value = new Float64Member();
		
		assertEquals(3, x.length());
		
		x.v(0, value );
		assertEquals(7, value.v(), 0.000000000001);
		
		x.v(1, value );
		assertEquals(-3, value.v(), 0.000000000001);
		
		x.v(2, value );
		assertEquals(13, value.v(), 0.000000000001);
	}
	
	@Test
	public void testFloat() {
		Float64MatrixMember a;
		Float64VectorMember b;
		Float64VectorMember x;
		LUDecomp.compute(G.DBL, G.DBL_VEC, G.DBL_MAT, a, b, x);
	}
	
	@Test
	public void testComplexFloat() {
		ComplexFloat64MatrixMember a;
		ComplexFloat64VectorMember b;
		ComplexFloat64VectorMember x;
		LUDecomp.compute(G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b, x);
	}
	
	@Test
	public void testQuaternionFloat() {
		QuaternionFloat64MatrixMember a;
		QuaternionFloat64RModuleMember b;
		QuaternionFloat64RModuleMember x;
		LUDecomp.compute(G.QDBL, G.QDBL_MOD, G.QDBL_MAT, a, b, x);
	}

	@Test
	public void testOctonionFloat() {
		OctonionFloat64MatrixMember a;
		OctonionFloat64RModuleMember b;
		OctonionFloat64RModuleMember x;
		LUDecomp.compute(G.ODBL, G.ODBL_MOD, G.ODBL_MAT, a, b, x);
	}

}
