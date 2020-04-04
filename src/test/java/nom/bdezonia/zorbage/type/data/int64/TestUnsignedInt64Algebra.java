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
package nom.bdezonia.zorbage.type.data.int64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt64Algebra {

	@Test
	public void testPred() {
		UnsignedInt64Member a = G.UINT64.construct();
		a.setV(BigInteger.valueOf(4));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("3",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("2",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("1",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(BigInteger.ZERO));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("ffffffffffffffff",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("fffffffffffffffe",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("fffffffffffffffd",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("fffffffffffffffc",16)));
		a.setV(new BigInteger("8000000000000003",16));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("8000000000000002",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("8000000000000001",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("8000000000000000",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("7fffffffffffffff",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("7ffffffffffffffe",16)));
		G.UINT64.pred().call(a, a);
		assertTrue(a.v().equals(new BigInteger("7ffffffffffffffd",16)));
	}

	@Test
	public void testSucc() {
		UnsignedInt64Member a = G.UINT64.construct();
		assertTrue(a.v().equals(BigInteger.ZERO));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("1",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("2",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("3",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("4",16)));
		a.setV(new BigInteger("7ffffffffffffffd",16));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("7ffffffffffffffe",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("7fffffffffffffff",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("8000000000000000",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("8000000000000001",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("8000000000000002",16)));
		a.setV(new BigInteger("fffffffffffffffc",16));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("fffffffffffffffd",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("fffffffffffffffe",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("ffffffffffffffff",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(BigInteger.ZERO));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("1",16)));
		G.UINT64.succ().call(a, a);
		assertTrue(a.v().equals(new BigInteger("2",16)));
	}

	@Test
	public void mathematicalMethods() {
		
		BigInteger two64minus1 = BigInteger.ONE.add(BigInteger.ONE).pow(64).subtract(BigInteger.ONE);
		
		UnsignedInt64Member x = G.UINT64.construct();
		assertEquals(0, x.v);
	
		UnsignedInt64Member y = G.UINT64.construct("4431");
		assertEquals(4431, y.v);
		
		UnsignedInt64Member z = G.UINT64.construct(y);
		assertEquals(4431, z.v);
		
		G.UINT64.unity().call(z);
		assertEquals(1, z.v);
		
		G.UINT64.zero().call(z);
		assertEquals(0, z.v);

		G.UINT64.maxBound().call(x);
		assertEquals(two64minus1, x.v());
		
		G.UINT64.minBound().call(x);
		assertEquals(BigInteger.ZERO, x.v());

		UnsignedInt64Member a = G.UINT64.construct();
		UnsignedInt64Member b = G.UINT64.construct();
		UnsignedInt64Member c = G.UINT64.construct();
		UnsignedInt64Member d = G.UINT64.construct();
		
		ArrayList<UnsignedInt64Member> numsg = new ArrayList<>();
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0xffffffffffffffffL)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0xfffffffffffffffeL)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0xfffffffffffffffdL)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0x7fffffffffffffffL)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0x7ffffffffffffffeL)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0x7ffffffffffffffdL)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0x8000000000000000L)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0x8000000000000001L)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(0x8000000000000002L)));
		numsg.add(new UnsignedInt64Member(BigInteger.ZERO));
		numsg.add(new UnsignedInt64Member(BigInteger.ONE));
		numsg.add(new UnsignedInt64Member(BigInteger.TEN));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(10303)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(103031)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(1030399)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(1030344)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(10303948282L)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(1030294836L)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(22222)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(33333)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(77777)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(191919191)));
		numsg.add(new UnsignedInt64Member(BigInteger.valueOf(804872527)));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt64Member num = G.UINT64.construct();
			G.UINT64.random().call(num);
			numsg.add(num);
		}
		
		ArrayList<UnsignedInt64Member> numsh = new ArrayList<>();
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0xffffffffffffffffL)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0xfffffffffffffffeL)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0xfffffffffffffffdL)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0x7fffffffffffffffL)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0x7ffffffffffffffeL)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0x7ffffffffffffffdL)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0x8000000000000000L)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0x8000000000000001L)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(0x8000000000000002L)));
		numsh.add(new UnsignedInt64Member(BigInteger.ZERO));
		numsh.add(new UnsignedInt64Member(BigInteger.ONE));
		numsh.add(new UnsignedInt64Member(BigInteger.TEN));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(10303)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(103031)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(1030399)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(1030344)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(10303948282L)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(1030294836L)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(22222)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(33333)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(77777)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(191919191)));
		numsh.add(new UnsignedInt64Member(BigInteger.valueOf(804872527)));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt64Member num = G.UINT64.construct();
			G.UINT64.random().call(num);
			numsh.add(num);
		}
		
		for (int g = 0; g < numsg.size(); g++) {
			
			a.set(numsg.get(g));
			
			G.UINT64.abs().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT64.assign().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT64.bitNot().call(a, c);
			assertEquals(~a.v, c.v);
			
			for (int p = 0; p < 64; p++) {
				
				G.UINT64.bitShiftLeft().call(p, a, c);
				assertEquals(a.v().shiftLeft(p).and(two64minus1), c.v());
				
				G.UINT64.bitShiftRight().call(p, a, c);
				assertEquals(a.v().shiftRight(p), c.v());
				
				if (!a.v().equals(BigInteger.ZERO) || p != 0) {
					BigInteger t = (p == 0) ? BigInteger.ONE : a.v();
					for (int i = 1; i < p; i++) {
						t = t.multiply(a.v());
					}
					t = t.and(two64minus1);
					G.UINT64.power().call(p, a, c);
					assertEquals(t, c.v());
					b.setV(BigInteger.valueOf(p));
					G.UINT64.pow().call(a, b, c);
					assertEquals(t, c.v());
				}
			}
			
			assertEquals((a.v().and(BigInteger.ONE)).equals(BigInteger.ZERO), G.UINT64.isEven().call(a));
			
			assertEquals((a.v().and(BigInteger.ONE)).equals(BigInteger.ONE), G.UINT64.isOdd().call(a));
			
			assertEquals(a.v().equals(BigInteger.ZERO), G.UINT64.isZero().call(a));

			G.UINT64.negate().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.UINT64.norm().call(a, c);
			assertEquals(a.v(), c.v());
			
			if (a.v().compareTo(BigInteger.ZERO) > 0) {
				G.UINT64.pred().call(a, c);
				assertEquals(a.v().subtract(BigInteger.ONE), c.v());
			}
			
			if (a.v().compareTo(BigInteger.valueOf(0xffffffffL)) < 0) {
				G.UINT64.succ().call(a, c);
				assertEquals(a.v().add(BigInteger.ONE), c.v());
			}

			int v = G.UINT64.signum().call(a);
			assertEquals(a.v().signum(), v);
				
			for (int h = 0; h < numsh.size(); h++) {
				
				b.set(numsh.get(h));
				
				G.UINT64.add().call(a, b, c);
				assertEquals(a.v().add(b.v()).and(two64minus1), c.v());

				G.UINT64.bitAnd().call(a, b, c);
				assertEquals(a.v().and(b.v()), c.v());

				G.UINT64.bitAndNot().call(a, b, c);
				assertEquals(a.v().and(b.v().not()), c.v());

				G.UINT64.bitOr().call(a, b, c);
				assertEquals(a.v().or(b.v()), c.v());

				G.UINT64.bitXor().call(a, b, c);
				assertEquals(a.v().xor(b.v()), c.v());

				assertEquals(a.v().compareTo(b.v()), (int) G.UINT64.compare().call(a, b));

				if (!b.v().equals(BigInteger.ZERO)) {
					
					G.UINT64.div().call(a, b, x);
					assertEquals(a.v().divide(b.v()), x.v());

					G.UINT64.mod().call(a, b, y);
					assertEquals(a.v().remainder(b.v()), y.v());
					
					G.UINT64.divMod().call(a, b, c, d);
					assertEquals(x.v, c.v);
					assertEquals(y.v, d.v);
				}
				
				assertEquals(a.v().equals(b.v()), G.UINT64.isEqual().call(a, b));
				
				assertEquals(!a.v().equals(b.v()), G.UINT64.isNotEqual().call(a, b));
				
				assertEquals(a.v().compareTo(b.v()) < 0, G.UINT64.isLess().call(a, b));
				
				assertEquals(a.v().compareTo(b.v()) <= 0, G.UINT64.isLessEqual().call(a, b));
				
				assertEquals(a.v().compareTo(b.v()) > 0, G.UINT64.isGreater().call(a, b));
				
				assertEquals(a.v().compareTo(b.v()) >= 0, G.UINT64.isGreaterEqual().call(a, b));

				G.UINT64.max().call(a, b, c);
				assertEquals(a.v().max(b.v()), c.v());
				
				G.UINT64.min().call(a, b, c);
				assertEquals(a.v().min(b.v()), c.v());

				G.UINT64.multiply().call(a, b, c);
				assertEquals(a.v().multiply(b.v()).and(two64minus1), c.v());

				G.UINT64.scale().call(a, b, c);
				assertEquals(a.v().multiply(b.v()).and(two64minus1), c.v());

				G.UINT64.subtract().call(a, b, c);
				assertEquals(a.v().subtract(b.v()).and(two64minus1), c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.UINT64.gcd();
		G.UINT64.lcm();
	}
}
