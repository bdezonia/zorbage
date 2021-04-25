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
package nom.bdezonia.zorbage.type.tuple;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.tuple.Tuple4;
import nom.bdezonia.zorbage.type.bool.BooleanAlgebra;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.integer.int128.UnsignedInt128Algebra;
import nom.bdezonia.zorbage.type.integer.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.integer.int4.SignedInt4Algebra;
import nom.bdezonia.zorbage.type.integer.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTuple {

	@Test
	public void test1() {
		
		Tuple4Algebra<Float64Algebra,Float64Member,SignedInt4Algebra,SignedInt4Member,BooleanAlgebra,BooleanMember,UnsignedInt128Algebra,UnsignedInt128Member>
			alg = new Tuple4Algebra<>(G.DBL,G.INT4,G.BOOL,G.UINT128);
		
		Tuple4<Float64Member,SignedInt4Member,BooleanMember,UnsignedInt128Member> tuple = alg.construct("14.2:-2:true");
		
		assertEquals(14.2, tuple.a().v(), 0);
		assertEquals(-2, tuple.b().v());
		assertEquals(true, tuple.c().v());
		assertEquals(BigInteger.ZERO, tuple.d().v());
	}
}
