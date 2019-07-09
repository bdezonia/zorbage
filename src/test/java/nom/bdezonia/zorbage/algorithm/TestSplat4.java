/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.tuple.Tuple4;
import nom.bdezonia.zorbage.type.data.float32.quaternion.QuaternionFloat32Member;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSplat4 {

	@Test
	public void test1() {
		QuaternionFloat32Member value = G.QFLT.construct("{1,2,3,4}");
		Tuple4<Float32Member,Float32Member,Float32Member,Float32Member> tuple =
				new Tuple4<>(G.FLT.construct(),G.FLT.construct(),G.FLT.construct(),G.FLT.construct());
		Splat4.toTuple(value, tuple);
		assertEquals(1, tuple.a().v(), 0);
		assertEquals(2, tuple.b().v(), 0);
		assertEquals(3, tuple.c().v(), 0);
		assertEquals(4, tuple.d().v(), 0);
	}

	@Test
	public void test2() {
		Tuple4<Float32Member,Float32Member,Float32Member,Float32Member> tuple =
				new Tuple4<>(G.FLT.construct("1"),G.FLT.construct("2"),G.FLT.construct("3"),G.FLT.construct("4"));
		QuaternionFloat32Member value = G.QFLT.construct();
		Splat4.toValue(tuple, value);
		assertEquals(1, value.r(), 0);
		assertEquals(2, value.i(), 0);
		assertEquals(3, value.j(), 0);
		assertEquals(4, value.k(), 0);
	}

}
