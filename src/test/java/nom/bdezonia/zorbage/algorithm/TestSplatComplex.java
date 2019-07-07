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
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.data.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSplatComplex {

	@Test
	public void test1() {
		ComplexFloat32Member value = G.CFLT.construct("{1.2,2.4}");
		Tuple2<Float32Member,Float32Member> tuple = new Tuple2<>(G.FLT.construct(),G.FLT.construct());
		SplatComplex.toTuple(value, tuple);
		assertEquals(1.2f, tuple.a().v(), 0);
		assertEquals(2.4f, tuple.b().v(), 0);
	}

	@Test
	public void test2() {
		Tuple2<Float32Member,Float32Member> tuple = new Tuple2<>(G.FLT.construct("1.2"),G.FLT.construct("2.4"));
		ComplexFloat32Member value = G.CFLT.construct();
		SplatComplex.toValue(tuple, value);
		assertEquals(1.2f, value.r(), 0);
		assertEquals(2.4f, value.i(), 0);
	}

}
