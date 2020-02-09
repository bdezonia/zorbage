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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.data.int10.UnsignedInt10Member;
import nom.bdezonia.zorbage.type.data.int11.SignedInt11Member;
import nom.bdezonia.zorbage.type.data.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.data.int13.UnsignedInt13Member;
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.data.int2.UnsignedInt2Member;
import nom.bdezonia.zorbage.type.data.int3.UnsignedInt3Member;
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.data.int5.UnsignedInt5Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.data.int7.UnsignedInt7Member;
import nom.bdezonia.zorbage.type.data.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFactorial {

	@Test
	public void ints() {
		SignedInt11Member b = G.INT11.construct();
		
		try {
			Factorial.compute(G.INT11, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.INT11, 0, b);
		assertEquals(1, b.v());
		
		Factorial.compute(G.INT11, 1, b);
		assertEquals(1, b.v());

		Factorial.compute(G.INT11, 2, b);
		assertEquals(2, b.v());

		Factorial.compute(G.INT11, 3, b);
		assertEquals(6, b.v());

		Factorial.compute(G.INT11, 6, b);
		assertEquals(720, b.v());
	}
	
	@Test
	public void longs() {
		SignedInt64Member b = G.INT64.construct();
		
		try {
			Factorial.compute(G.INT64, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.INT64, 0, b);
		assertEquals(1, b.v());
		
		Factorial.compute(G.INT64, 1, b);
		assertEquals(1, b.v());

		Factorial.compute(G.INT64, 2, b);
		assertEquals(2, b.v());

		Factorial.compute(G.INT64, 3, b);
		assertEquals(6, b.v());

		Factorial.compute(G.INT64, 6, b);
		assertEquals(720, b.v());
	}
	
	@Test
	public void unboundInts() {
		UnboundedIntMember b = G.UNBOUND.construct();
		
		try {
			Factorial.compute(G.UNBOUND, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.UNBOUND, 0, b);
		assertEquals(BigInteger.valueOf(1), b.v());
		
		Factorial.compute(G.UNBOUND, 1, b);
		assertEquals(BigInteger.valueOf(1), b.v());

		Factorial.compute(G.UNBOUND, 2, b);
		assertEquals(BigInteger.valueOf(2), b.v());

		Factorial.compute(G.UNBOUND, 3, b);
		assertEquals(BigInteger.valueOf(6), b.v());

		Factorial.compute(G.UNBOUND, 6, b);
		assertEquals(BigInteger.valueOf(720), b.v());

		Factorial.compute(G.UNBOUND, 45, b);
		assertEquals(new BigInteger("119622220865480194561963161495657715064383733760000000000"), b.v());
	}
	
	@Test
	public void doubles() {
		Float64Member b = G.DBL.construct();
		
		try {
			Factorial.compute(G.DBL, -1, b);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		Factorial.compute(G.DBL, 0, b);
		assertEquals(1, b.v(), 0);
		
		Factorial.compute(G.DBL, 1, b);
		assertEquals(1, b.v(), 0);

		Factorial.compute(G.DBL, 2, b);
		assertEquals(2, b.v(), 0);

		Factorial.compute(G.DBL, 3, b);
		assertEquals(6, b.v(), 0);

		Factorial.compute(G.DBL, 6, b);
		assertEquals(720, b.v(), 0);
	}
	
	@Test
	public void testLots() {
		UnsignedInt1Member a1 = G.UINT1.construct();

		Factorial.compute(G.UINT1, 0, a1);
		assertEquals(1, a1.v());
		
		Factorial.compute(G.UINT1, 1, a1);
		assertEquals(1, a1.v());
		
		UnsignedInt2Member a2 = G.UINT2.construct();

		Factorial.compute(G.UINT2, 2, a2);
		assertEquals(2, a2.v());

		UnsignedInt3Member a3 = G.UINT3.construct();

		Factorial.compute(G.UINT3, 3, a3);
		assertEquals(6, a3.v());

		UnsignedInt5Member a4 = G.UINT5.construct();

		Factorial.compute(G.UINT5, 4, a4);
		assertEquals(24, a4.v());

		UnsignedInt7Member a5 = G.UINT7.construct();

		Factorial.compute(G.UINT7, 5, a5);
		assertEquals(120, a5.v());

		UnsignedInt10Member a6 = G.UINT10.construct();

		Factorial.compute(G.UINT10, 6, a6);
		assertEquals(720, a6.v());

		UnsignedInt13Member a7 = G.UINT13.construct();

		Factorial.compute(G.UINT13, 7, a7);
		assertEquals(5040, a7.v());

		UnsignedInt16Member a8 = G.UINT16.construct();

		Factorial.compute(G.UINT16, 8, a8);
		assertEquals(40320, a8.v());

		UnsignedInt32Member a9 = G.UINT32.construct();

		Factorial.compute(G.UINT32, 9, a9);
		assertEquals(362880, a9.v());

		UnsignedInt32Member a10 = G.UINT32.construct();

		Factorial.compute(G.UINT32, 10, a10);
		assertEquals(3628800, a10.v());

		UnsignedInt32Member a11 = G.UINT32.construct();

		Factorial.compute(G.UINT32, 11, a11);
		assertEquals(39916800, a11.v());

		UnsignedInt32Member a12 = G.UINT32.construct();

		Factorial.compute(G.UINT32, 12, a12);
		assertEquals(479001600, a12.v());

		UnsignedInt32Member a13 = G.UINT32.construct();

		Factorial.compute(G.UINT32, 13, a13);
		assertEquals(1932053504, a13.v());

		UnsignedInt64Member a14 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 14, a14);
		assertEquals(BigInteger.valueOf(87178291200L), a14.v());

		UnsignedInt64Member a15 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 15, a15);
		assertEquals(BigInteger.valueOf(1307674368000L), a15.v());

		UnsignedInt64Member a16 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 16, a16);
		assertEquals(BigInteger.valueOf(20922789888000L), a16.v());

		UnsignedInt64Member a17 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 17, a17);
		assertEquals(BigInteger.valueOf(355687428096000L), a17.v());

		UnsignedInt64Member a18 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 18, a18);
		assertEquals(BigInteger.valueOf(6402373705728000L), a18.v());

		UnsignedInt64Member a19 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 19, a19);
		assertEquals(BigInteger.valueOf(121645100408832000L), a19.v());

		UnsignedInt64Member a20 = G.UINT64.construct();

		Factorial.compute(G.UINT64, 20, a20);
		assertEquals(BigInteger.valueOf(2432902008176640000L), a20.v());

		UnsignedInt128Member a21 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 21, a21);
		assertEquals(new BigInteger("51090942171709440000"), a21.v());

		UnsignedInt128Member a22 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 22, a22);
		assertEquals(new BigInteger("1124000727777607680000"), a22.v());

		UnsignedInt128Member a23 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 23, a23);
		assertEquals(new BigInteger("25852016738884976640000"), a23.v());

		UnsignedInt128Member a24 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 24, a24);
		assertEquals(new BigInteger("620448401733239439360000"), a24.v());

		UnsignedInt128Member a25 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 25, a25);
		assertEquals(new BigInteger("15511210043330985984000000"), a25.v());

		UnsignedInt128Member a26 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 26, a26);
		assertEquals(new BigInteger("403291461126605635584000000"), a26.v());

		UnsignedInt128Member a27 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 27, a27);
		assertEquals(new BigInteger("10888869450418352160768000000"), a27.v());

		UnsignedInt128Member a28 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 28, a28);
		assertEquals(new BigInteger("304888344611713860501504000000"), a28.v());

		UnsignedInt128Member a29 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 29, a29);
		assertEquals(new BigInteger("8841761993739701954543616000000"), a29.v());

		UnsignedInt128Member a30 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 30, a30);
		assertEquals(new BigInteger("265252859812191058636308480000000"), a30.v());

		UnsignedInt128Member a31 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 31, a31);
		assertEquals(new BigInteger("8222838654177922817725562880000000"), a31.v());

		UnsignedInt128Member a32 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 32, a32);
		assertEquals(new BigInteger("263130836933693530167218012160000000"), a32.v());

		UnsignedInt128Member a33 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 33, a33);
		assertEquals(new BigInteger("8683317618811886495518194401280000000"), a33.v());

		UnsignedInt128Member a34 = G.UINT128.construct();

		Factorial.compute(G.UINT128, 34, a34);
		assertEquals(new BigInteger("295232799039604140847618609643520000000"), a34.v());

		UnboundedIntMember a35 = G.UNBOUND.construct();

		Factorial.compute(G.UNBOUND, 35, a35);
		assertEquals(new BigInteger("10333147966386144929666651337523200000000"), a35.v());
	}
}
