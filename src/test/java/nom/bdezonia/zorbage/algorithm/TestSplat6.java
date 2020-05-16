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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.tuple.Tuple10;
import nom.bdezonia.zorbage.tuple.Tuple6;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSplat6 {

	@Test
	public void test1() {
		OctonionFloat32Member value = G.OFLT.construct("{1,2,3,4,5,6,7,8}");
		Tuple6<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,Float32Member>
			tuple =
				new Tuple6<>(G.FLT.construct(), G.FLT.construct(), G.FLT.construct(), G.FLT.construct(), G.FLT.construct(), G.FLT.construct());
		Splat6.toTuple(value, tuple);
		assertEquals(1, tuple.a().v(), 0);
		assertEquals(2, tuple.b().v(), 0);
		assertEquals(3, tuple.c().v(), 0);
		assertEquals(4, tuple.d().v(), 0);
		assertEquals(5, tuple.e().v(), 0);
		assertEquals(6, tuple.f().v(), 0);
	}

	@Test
	public void test2() {
		Tuple6<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,Float32Member>
			tuple =
				new Tuple6<>(G.FLT.construct("1"), G.FLT.construct("2"), G.FLT.construct("3"), G.FLT.construct("4"), G.FLT.construct("5"), G.FLT.construct("6"));
		OctonionFloat32Member value = G.OFLT.construct();
		Splat6.toValue(tuple, value);
		assertEquals(1, value.r(), 0);
		assertEquals(2, value.i(), 0);
		assertEquals(3, value.j(), 0);
		assertEquals(4, value.k(), 0);
		assertEquals(5, value.l(), 0);
		assertEquals(6, value.i0(), 0);
		assertEquals(0, value.j0(), 0);
		assertEquals(0, value.k0(), 0);
	}

	@Test
	public void test3() {
		Tuple10<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,
			Float32Member,Float32Member,Float32Member,Float32Member,Float32Member>
			tuple = new Tuple10<>(G.FLT.construct("1"), G.FLT.construct("2"),
					G.FLT.construct("3"), G.FLT.construct("4"), G.FLT.construct("5"),
					G.FLT.construct("6"), G.FLT.construct("7"), G.FLT.construct("8"),
					G.FLT.construct("9"), G.FLT.construct("10"));
			OctonionFloat32Member value = G.OFLT.construct();
			Splat6.toValue(tuple, value);
			assertEquals(1, value.r(), 0);
			assertEquals(2, value.i(), 0);
			assertEquals(3, value.j(), 0);
			assertEquals(4, value.k(), 0);
			assertEquals(5, value.l(), 0);
			assertEquals(6, value.i0(), 0);
			assertEquals(0, value.j0(), 0);
			assertEquals(0, value.k0(), 0);
	}

	@Test
	public void test4() {
		OctonionFloat32Member value = G.OFLT.construct("{14,15,16,17,18,19,20,21}");
		Tuple10<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,
			Float32Member,Float32Member,Float32Member,Float32Member,Float32Member>
			tuple = new Tuple10<>(G.FLT.construct("1"), G.FLT.construct("2"),
					G.FLT.construct("3"), G.FLT.construct("4"), G.FLT.construct("5"),
					G.FLT.construct("6"), G.FLT.construct("7"), G.FLT.construct("8"),
					G.FLT.construct("9"), G.FLT.construct("10"));
			Splat6.toTuple(value, tuple);
			assertEquals(14, tuple.a().v(), 0);
			assertEquals(15, tuple.b().v(), 0);
			assertEquals(16, tuple.c().v(), 0);
			assertEquals(17, tuple.d().v(), 0);
			assertEquals(18, tuple.e().v(), 0);
			assertEquals(19, tuple.f().v(), 0);
			assertEquals(7, tuple.g().v(), 0);
			assertEquals(8, tuple.h().v(), 0);
			assertEquals(9, tuple.i().v(), 0);
			assertEquals(10, tuple.j().v(), 0);
	}

}
