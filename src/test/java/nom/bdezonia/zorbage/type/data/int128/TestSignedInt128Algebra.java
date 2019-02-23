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

	BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	BigInteger TWO15 = TWO.pow(15);
	BigInteger TWO16 = TWO.pow(16);
	BigInteger MIN = TWO15.negate();
	BigInteger MAX = TWO15.subtract(BigInteger.ONE);
	
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
			if (g % 100 == 0)
				System.out.println(g);
			
			BigInteger bg = BigInteger.valueOf(g);

//WORKS			d.setV(bg);
//WORKS			assertEquals(bg, d.v());
//WORKS			
//WORKS			a.set(d);
			
			a.setV(bg);
			
			//G.INT128.random().call(a);

			//WORKS if (a.v().compareTo(MIN) != 0) {
			//WORKS	G.INT128.abs().call(a, c);
			//WORKS	assertEquals(a.v().abs(), c.v());
			//WORKS}
			
			//WORKS G.INT128.assign().call(a, c);
			//WORKS assertEquals(a.v(), c.v());
			
			//WORKS G.INT128.bitNot().call(a, c);
			//WORKS assertEquals(a.v().not(), c.v());
			
			for (int p = 0; p < 16; p++) {
				
			//WORKS 	G.INT128.bitShiftLeft().call(p, a, c);
			//WORKS 	testIt(a.v().shiftLeft(p), c.v());
				
			//WORKS 	G.INT128.bitShiftRight().call(p, a, c);
			//WORKS 	testIt(a.v().shiftRight(p), c.v());
				
			//WORKS 	G.INT128.bitShiftRightFillZero().call(p, a, c);
			//WORKS 	testIt(a.v().shiftRight(p), c.v());

				//WORKS 	if (a.v() != BigInteger.ZERO || p != 0) {
				//WORKS 		BigInteger t = (p == 0) ? BigInteger.ONE : a.v();
				//WORKS 		for (int i = 1; i < p; i++) {
				//WORKS 		t = t.multiply(a.v());
				//WORKS 	}

				//WORKS 	G.INT128.power().call(p, a, c);
				//WORKS 	testIt(t, c.v());

				//WORKS 	b.setV(BigInteger.valueOf(p));

				//WORKS 	G.INT128.pow().call(a, b, c);
				//WORKS 	testIt(t, c.v());
				//WORKS }
			}
			
			//WORKS assertEquals((a.v().and(BigInteger.ONE).compareTo(BigInteger.ZERO) == 0), G.INT128.isEven().call(a));
			
			//WORKS assertEquals((a.v().and(BigInteger.ONE).compareTo(BigInteger.ONE) == 0), G.INT128.isOdd().call(a));
			
			//WORKS assertEquals(a.v().compareTo(BigInteger.ZERO) == 0, G.INT128.isZero().call(a));

			//WORKS if (a.v().compareTo(MIN) != 0) {
			//WORKS 	G.INT128.negate().call(a, c);
			//WORKS 	assertEquals(a.v().negate(), c.v());
			//WORKS }
			
			//WORKS if (a.v().compareTo(MIN) != 0) {
			//WORKS 	G.INT128.norm().call(a, c);
			//WORKS 	assertEquals(a.v().abs(), c.v());
			//WORKS }
			
			//WORKS if (a.v().compareTo(MIN) > 0) {
			//WORKS 	G.INT128.pred().call(a, c);
			//WORKS 	assertEquals(a.v().subtract(BigInteger.ONE), c.v());
			//WORKS }
			
			//WORKS if (a.v().compareTo(MAX) < 0) {
			//WORKS 	G.INT128.succ().call(a, c);
			//WORKS 	assertEquals(a.v().add(BigInteger.ONE), c.v());
			//WORKS }

			//WORKS int v = G.INT128.signum().call(a);
			//WORKS if (a.v().compareTo(BigInteger.ZERO) < 0)
			//WORKS 	assertEquals(-1, v);
			//WORKS else if (a.v().compareTo(BigInteger.ZERO) > 0)
			//WORKS 	assertEquals(1, v);
			//WORKS else
			//WORKS 	assertEquals(0, v);
			
			//int[] nums = new int[] {-32768, -32767, -4000, -1, 0, 1, 3113, 32767};
			//for (int q = 0; q < nums.length; q += 1) {
				
			//	int h = nums[q];
				
			for (int h = -32768; h < 32768; h+=1) {
				
				//System.out.println("  "+h);
				
				b.setV(BigInteger.valueOf(h));
				
//				// G.INT128.random().call(b);
				
				//WORKS G.INT128.add().call(a, b, c);
				//WORKS testIt(g+h, c.v());
				//WORKS testIt(a.v().add(b.v()), c.v());

				//WORKS G.INT128.bitAnd().call(a, b, c);
				//WORKS testIt(g&h, c.v());
				//WORKS assertEquals(a.v().and(b.v()), c.v());

				//WORKS G.INT128.bitAndNot().call(a, b, c);
				//WORKS testIt(g&~h, c.v());
				//WORKS assertEquals(a.v().andNot(b.v()), c.v());

				//WORKS G.INT128.bitOr().call(a, b, c);
				//WORKS testIt(g|h, c.v());
				//WORKS assertEquals(a.v().or(b.v()), c.v());

				//WORKS G.INT128.bitXor().call(a, b, c);
				//WORKS testIt(g^h, c.v());
				//WORKS assertEquals(a.v().xor(b.v()), c.v());

				//WORKS if (g < h)
//				if (a.v().compareTo(b.v()) < 0)
				//WORKS 	assertEquals(-1, (int) G.INT128.compare().call(a, b));
				//WORKS else if (g > h)
//				else if (a.v().compareTo(b.v()) > 0)
				//WORKS 	assertEquals(1, (int) G.INT128.compare().call(a, b));
				//WORKS else
				//WORKS 	assertEquals(0, (int) G.INT128.compare().call(a, b));

				if ((b.v().signum() != 0) && !(a.v().compareTo(MIN) == 0 && b.v().compareTo(BigInteger.ONE.negate()) == 0)) {
					
					//G.INT128.div().call(a, b, x);
					// TODO testIt(g/h, x.v());
					//assertEquals(a.v().divide(b.v()), x.v());

					//G.INT128.mod().call(a, b, y);
					// TODO testIt(g%h, y.v());
					//assertEquals(a.v().remainder(b.v()), y.v());
					
					G.INT128.divMod().call(a, b, c, d);
					testIt(g/h, c.v());
					//assertEquals(x.v(), c.v());
					testIt(g%h, d.v());
					//assertEquals(y.v(), d.v());
				}

				//WORKS assertEquals((g == h), G.INT128.isEqual().call(a, b));
				//WORKS assertEquals(a.v().compareTo(b.v()) == 0, G.INT128.isEqual().call(a, b));
				
				//WORKS assertEquals((g != h), G.INT128.isNotEqual().call(a, b));
				//WORKS assertEquals(a.v().compareTo(b.v()) != 0, G.INT128.isNotEqual().call(a, b));
				
				//WORKS assertEquals((g < h), G.INT128.isLess().call(a, b));
				//WORKS assertEquals(a.v().compareTo(b.v()) < 0, G.INT128.isLess().call(a, b));
				
				//WORKS assertEquals((g <= h), G.INT128.isLessEqual().call(a, b));
				//WORKS assertEquals(a.v().compareTo(b.v()) <= 0, G.INT128.isLessEqual().call(a, b));
				
				//WORKS assertEquals((g > h), G.INT128.isGreater().call(a, b));
				//WORKS assertEquals(a.v().compareTo(b.v()) > 0, G.INT128.isGreater().call(a, b));
				
				//WORKS assertEquals((g >= h), G.INT128.isGreaterEqual().call(a, b));
				//WORKS assertEquals(a.v().compareTo(b.v()) >= 0, G.INT128.isGreaterEqual().call(a, b));

				//WORKS G.INT128.max().call(a, b, c);
				//WORKS assertEquals((g > h) ? a.v() : b.v(), c.v());
				//WORKS assertEquals((a.v().compareTo(b.v()) > 0 ? a.v() : b.v()), c.v());
				
				//WORKS G.INT128.min().call(a, b, c);
				//WORKS assertEquals((g < h) ? a.v() : b.v(), c.v());
				//WORKS assertEquals((a.v().compareTo(b.v()) < 0 ? a.v() : b.v()), c.v());


//WORKING				G.INT128.multiply().call(a, b, c);
//WORKING				testIt(g*h,c.v());
//WORKING				//testIt(a.v().multiply(b.v()),c.v());

// will not test: scale delegates to multiply
//				G.INT128.scale().call(a, b, c);

				//WORKS G.INT128.subtract().call(a, b, c);
				//WORKS testIt(g-h, c.v());
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
		raw = raw.mod(TWO16);
		if (raw.compareTo(MAX) > 0)
			raw = raw.subtract(TWO16);
		else if (raw.compareTo(MIN) < 0)
			raw = raw.add(TWO16);
		assertEquals(raw, int128);
	}
}
