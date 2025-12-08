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

import nom.bdezonia.zorbage.tuple.Tuple10;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSplat2 {

	@Test
	public void test1() {
		ComplexFloat32Member value = G.CFLT.construct("{1.2,2.4}");
		Tuple2<Float32Member,Float32Member> tuple = new Tuple2<>(G.FLT.construct(), G.FLT.construct());
		Splat2.toTuple(value, tuple);
		assertEquals(1.2f, tuple.a().v(), 0);
		assertEquals(2.4f, tuple.b().v(), 0);
	}

	@Test
	public void test2() {
		Tuple2<Float32Member,Float32Member> tuple = new Tuple2<>(G.FLT.construct("1.2"), G.FLT.construct("2.4"));
		ComplexFloat32Member value = G.CFLT.construct();
		Splat2.toValue(tuple, value);
		assertEquals(1.2f, value.r(), 0);
		assertEquals(2.4f, value.i(), 0);
	}

	@Test
	public void test3() {
		Tuple10<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,
			Float32Member,Float32Member,Float32Member,Float32Member,Float32Member>
			tuple = new Tuple10<>(G.FLT.construct("1"), G.FLT.construct("2"),
					G.FLT.construct("3"), G.FLT.construct("4"), G.FLT.construct("5"),
					G.FLT.construct("6"), G.FLT.construct("7"), G.FLT.construct("8"),
					G.FLT.construct("9"), G.FLT.construct("10"));
			ComplexFloat32Member value = G.CFLT.construct();
			Splat2.toValue(tuple, value);
			assertEquals(1, value.r(), 0);
			assertEquals(2, value.i(), 0);
	}

	@Test
	public void test4() {
		ComplexFloat32Member value = G.CFLT.construct("{14,15}");
		Tuple10<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,
			Float32Member,Float32Member,Float32Member,Float32Member,Float32Member>
			tuple = new Tuple10<>(G.FLT.construct("1"), G.FLT.construct("2"),
					G.FLT.construct("3"), G.FLT.construct("4"), G.FLT.construct("5"),
					G.FLT.construct("6"), G.FLT.construct("7"), G.FLT.construct("8"),
					G.FLT.construct("9"), G.FLT.construct("10"));
			Splat2.toTuple(value, tuple);
			assertEquals(14, tuple.a().v(), 0);
			assertEquals(15, tuple.b().v(), 0);
			assertEquals(3, tuple.c().v(), 0);
			assertEquals(4, tuple.d().v(), 0);
			assertEquals(5, tuple.e().v(), 0);
			assertEquals(6, tuple.f().v(), 0);
			assertEquals(7, tuple.g().v(), 0);
			assertEquals(8, tuple.h().v(), 0);
			assertEquals(9, tuple.i().v(), 0);
			assertEquals(10, tuple.j().v(), 0);
	}

}
