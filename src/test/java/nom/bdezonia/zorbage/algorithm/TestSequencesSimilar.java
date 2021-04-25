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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.integer.int3.UnsignedInt3Member;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32Member;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSequencesSimilar {

	@Test
	public void test1() {
		UnsignedInt3Member value = G.UINT3.construct();
		UnsignedInt3Member tol = G.UINT3.construct();
		
		IndexedDataSource<UnsignedInt3Member>  l1 = Storage.allocate(value, 4);
		IndexedDataSource<UnsignedInt3Member>  l2 = Storage.allocate(value, 4);
		
		assertTrue(SequencesSimilar.compute(G.UINT3, tol, l1, l2));
		
		tol.setV(1);
		assertTrue(SequencesSimilar.compute(G.UINT3, tol, l1, l2));
		
		value.setV(2);
		l1.set(l1.size()-1, value);
		assertFalse(SequencesSimilar.compute(G.UINT3, tol, l1, l2));
		
		l1 = Storage.allocate(value, l2.size()+1);
		assertFalse(SequencesSimilar.compute(G.UINT3, tol, l1, l2));
		
		l1 = Storage.allocate(value, 0);
		l2 = Storage.allocate(value, 0);
		assertTrue(SequencesSimilar.compute(G.UINT3, tol, l1, l2));
	}
	
	@Test
	public void test2() {
		Float32Member value = G.FLT.construct();
		Float32Member tol = G.FLT.construct();
		
		IndexedDataSource<Float32Member>  l1 = Storage.allocate(value, 4);
		IndexedDataSource<Float32Member>  l2 = Storage.allocate(value, 4);
		
		tol.setV(0.1f);
		
		assertTrue(SequencesSimilar.compute(G.FLT, tol, l1, l2));
		
		value.setV(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.FLT, tol, l1, l2));
		
		value.setV(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.FLT, tol, l1, l2));
	}
	
	@Test
	public void test3() {
		ComplexFloat32Member value = G.CFLT.construct();
		Float32Member tol = G.FLT.construct();
		
		IndexedDataSource<ComplexFloat32Member>  l1 = Storage.allocate(value, 4);
		IndexedDataSource<ComplexFloat32Member>  l2 = Storage.allocate(value, 4);
		
		tol.setV(0.1f);
		
		assertTrue(SequencesSimilar.compute(G.CFLT, tol, l1, l2));
		
		value.setR(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.CFLT, tol, l1, l2));
		
		value.setR(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.CFLT, tol, l1, l2));

		value.setR(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.CFLT, tol, l1, l2));

		value.setI(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.CFLT, tol, l1, l2));

		value.setI(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.CFLT, tol, l1, l2));
	}
	
	@Test
	public void test4() {
		QuaternionFloat32Member value = G.QFLT.construct();
		Float32Member tol = G.FLT.construct();
		
		IndexedDataSource<QuaternionFloat32Member>  l1 = Storage.allocate(value, 4);
		IndexedDataSource<QuaternionFloat32Member>  l2 = Storage.allocate(value, 4);
		
		tol.setV(0.1f);
		
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));
		
		value.setR(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));
		
		value.setR(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setR(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setI(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setI(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.QFLT, tol, l1, l2));
		
		value.setI(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));
		
		value.setJ(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));
		
		value.setJ(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setJ(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setK(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setK(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.QFLT, tol, l1, l2));

		value.setK(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.QFLT, tol, l1, l2));
	}
	
	@Test
	public void test5() {
		OctonionFloat32Member value = G.OFLT.construct();
		Float32Member tol = G.FLT.construct();
		
		IndexedDataSource<OctonionFloat32Member>  l1 = Storage.allocate(value, 4);
		IndexedDataSource<OctonionFloat32Member>  l2 = Storage.allocate(value, 4);
		
		tol.setV(0.1f);
		
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setR(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setR(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setR(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setI(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setI(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setI(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setJ(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setJ(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setJ(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setK(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setK(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setK(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setL(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setL(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setL(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setI0(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setI0(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setI0(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setJ0(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
		value.setJ0(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setJ0(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setK0(0.1f);
		l2.set(2, value);
		assertTrue(SequencesSimilar.compute(G.OFLT, tol, l1, l2));

		value.setK0(0.100001f);
		l2.set(2, value);
		assertFalse(SequencesSimilar.compute(G.OFLT, tol, l1, l2));
		
	}
}
