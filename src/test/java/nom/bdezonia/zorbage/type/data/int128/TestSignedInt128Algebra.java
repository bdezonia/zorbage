/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.int128;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int128.SignedInt128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt128Algebra {

	BigInteger RANGE = BigInteger.ONE.add(BigInteger.ONE).pow(15);
	BigInteger LORANGE = RANGE.negate();
	BigInteger DOUBLE_RANGE = RANGE.add(RANGE);
	BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	BigInteger MIN = TWO.pow(15).negate();
	BigInteger MAX = TWO.pow(15).subtract(BigInteger.ONE);
	
	@Test
	public void mathematicalMethods() {
		
		SignedInt128Member x = G.INT128.construct();
		assertEquals(BigInteger.ZERO, x.v());
	
		SignedInt128Member y = G.INT128.construct("-4431");
		assertEquals(BigInteger.valueOf(-4431), y.v());
		
		SignedInt128Member y2 = G.INT128.construct("4431");
		assertEquals(BigInteger.valueOf(4431), y2.v());
		
		SignedInt128Member z = G.INT128.construct(y2);
		assertEquals(BigInteger.valueOf(4431), z.v());
		
		G.INT128.unity().call(z);
		assertEquals(BigInteger.ONE, z.v());
		
		G.INT128.zero().call(z);
		assertEquals(BigInteger.ZERO, z.v());

		G.INT128.maxBound().call(x);
		assertEquals(MAX, x.v());
		
		G.INT128.minBound().call(x);
		assertEquals(MIN, x.v());

		SignedInt128Member a = G.INT128.construct();
		SignedInt128Member b = G.INT128.construct();
		SignedInt128Member c = G.INT128.construct();
		SignedInt128Member d = G.INT128.construct();
		
		for (int g = -32768; g < 32768; g += 1) {
			System.out.println(g);
			
			BigInteger bg = BigInteger.valueOf(g);
			d.setV(bg);
			assertEquals(bg, d.v());
			
			a.set(d);
			
			//G.INT128.random().call(a);

			if (a.v().compareTo(MIN) != 0) {
				G.INT128.abs().call(a, c);
				assertEquals(a.v().abs(), c.v());
			}
			
			G.INT128.assign().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.INT128.bitNot().call(a, c);
			assertEquals(a.v().not(), c.v());
			
//			for (int p = 0; p < 128; p++) {
//				
//				G.INT128.bitShiftLeft().call(p, a, c);
////TODO				assertEquals(a.v().shiftLeft(p).remainder(RANGE), c.v());
//				
//				G.INT128.bitShiftRight().call(p, a, c);
////TODO				assertEquals(a.v().shiftRight(p), c.v());
//				
//				G.INT128.bitShiftRightFillZero().call(p, a, c);
////TODO				assertEquals(a.v().shiftRight(p), c.v());
//
//				if (a.v() != BigInteger.ZERO || p != 0) {
//					BigInteger t = (p == 0) ? BigInteger.ONE : a.v();
//					for (int i = 1; i < p; i++) {
//						t = t.multiply(a.v());
//					}
////					G.INT128.power().call(p, a, c);
//// TODO					assertEquals(t, c.v());
//					b.setV(BigInteger.valueOf(p));
////					G.INT128.pow().call(a, b, c);
//// TODO					assertEquals(t, c.v());
//				}
//			}
//			
			assertEquals((a.v().and(BigInteger.ONE).compareTo(BigInteger.ZERO) == 0), G.INT128.isEven().call(a));
			
			assertEquals((a.v().and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0), G.INT128.isOdd().call(a));
			
			assertEquals(a.v().compareTo(BigInteger.ZERO) == 0, G.INT128.isZero().call(a));

			if (a.v().compareTo(MIN) != 0) {
				G.INT128.negate().call(a, c);
				assertEquals(a.v().negate(), c.v());
			}
			
			if (a.v().compareTo(MIN) != 0) {
				G.INT128.norm().call(a, c);
				assertEquals(a.v().abs(), c.v());
			}
			
			if (a.v().compareTo(MIN) > 0) {
				G.INT128.pred().call(a, c);
				assertEquals(a.v().subtract(BigInteger.ONE), c.v());
			}
			
			if (a.v().compareTo(MAX) < 0) {
				G.INT128.succ().call(a, c);
				assertEquals(a.v().add(BigInteger.ONE), c.v());
			}

			int v = G.INT128.signum().call(a);
			if (a.v().compareTo(BigInteger.ZERO) < 0)
				assertEquals(-1, v);
			else if (a.v().compareTo(BigInteger.ZERO) > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = -32768; h < 32768; h += 1) {
				
				b.setV(BigInteger.valueOf(h));
				
//				// G.INT128.random().call(b);
//				
//				G.INT128.add().call(a, b, c);
//				testIt(g+h, c.v());
//				testIt(a.v().add(b.v()), c.v());
//
				G.INT128.bitAnd().call(a, b, c);
				testIt(g&h, c.v());
				assertEquals(a.v().and(b.v()), c.v());

				G.INT128.bitAndNot().call(a, b, c);
				testIt(g&~h, c.v());
				assertEquals(a.v().andNot(b.v()), c.v());

				G.INT128.bitOr().call(a, b, c);
				testIt(g|h, c.v());
				assertEquals(a.v().or(b.v()), c.v());

				G.INT128.bitXor().call(a, b, c);
				testIt(g^h, c.v());
				assertEquals(a.v().xor(b.v()), c.v());

				if (g < h)
//				if (a.v().compareTo(b.v()) < 0)
					assertEquals(-1, (int) G.INT128.compare().call(a, b));
				else if (g > h)
//				else if (a.v().compareTo(b.v()) > 0)
					assertEquals(1, (int) G.INT128.compare().call(a, b));
				else
					assertEquals(0, (int) G.INT128.compare().call(a, b));

//				if ((b.v().signum() != 0) && !(a.v().compareTo(MIN) == 0 && b.v().compareTo(BigInteger.ONE.negate()) == 0)) {
//					
//					G.INT128.div().call(a, b, x);
//					testIt(g/h, c.v());
//					assertEquals(a.v().divide(b.v()), x.v());
//
//					G.INT128.mod().call(a, b, y);
//					testIt(g%h, c.v());
//					assertEquals(a.v().remainder(b.v()), y.v());
//					
//					G.INT128.divMod().call(a, b, c, d);
//					testIt(g/h, c.v());
//					assertEquals(x.v(), c.v());
//					testIt(g%h, d.v());
//					assertEquals(y.v(), d.v());
//				}
//				
				assertEquals((g == h), G.INT128.isEqual().call(a, b));
				assertEquals(a.v().compareTo(b.v()) == 0, G.INT128.isEqual().call(a, b));
				
				assertEquals((g != h), G.INT128.isNotEqual().call(a, b));
				assertEquals(a.v().compareTo(b.v()) != 0, G.INT128.isNotEqual().call(a, b));
				
				assertEquals((g < h), G.INT128.isLess().call(a, b));
				assertEquals(a.v().compareTo(b.v()) < 0, G.INT128.isLess().call(a, b));
				
				assertEquals((g <= h), G.INT128.isLessEqual().call(a, b));
				assertEquals(a.v().compareTo(b.v()) <= 0, G.INT128.isLessEqual().call(a, b));
				
				assertEquals((g > h), G.INT128.isGreater().call(a, b));
				assertEquals(a.v().compareTo(b.v()) > 0, G.INT128.isGreater().call(a, b));
				
				assertEquals((g >= h), G.INT128.isGreaterEqual().call(a, b));
				assertEquals(a.v().compareTo(b.v()) >= 0, G.INT128.isGreaterEqual().call(a, b));

				G.INT128.max().call(a, b, c);
				assertEquals((g > h) ? a.v() : b.v(), c.v());
				assertEquals((a.v().compareTo(b.v()) > 0 ? a.v() : b.v()), c.v());
				
				G.INT128.min().call(a, b, c);
				assertEquals((g < h) ? a.v() : b.v(), c.v());
				assertEquals((a.v().compareTo(b.v()) < 0 ? a.v() : b.v()), c.v());


//WORKING				G.INT128.multiply().call(a, b, c);
//WORKING				testIt(g*h,c.v());
//WORKING				//testIt(a.v().multiply(b.v()),c.v());

// will not test: scale delegates to multiply
//				G.INT128.scale().call(a, b, c);

//				G.INT128.subtract().call(a, b, c);
//				testIt(g-h, c.v());
//				testIt(a.v().subtract(b.v()), c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.INT128.gcd();
		G.INT128.lcm();
	}
	
	private void testIt(int raw, BigInteger int128) {
		assertEquals((short) raw, int128.shortValue());
	}
	
	private void testIt(BigInteger raw, BigInteger int128) {
		raw = raw.mod(DOUBLE_RANGE);
		if (raw.compareTo(RANGE) >= 0)
			raw = raw.subtract(DOUBLE_RANGE);
		else if (raw.compareTo(LORANGE) < 0)
			raw = raw.add(DOUBLE_RANGE);
		assertEquals(raw, int128);
	}
}