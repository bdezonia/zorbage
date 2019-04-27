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
package nom.bdezonia.zorbage.type.data.intunlim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MinMaxElement;
import nom.bdezonia.zorbage.algorithm.Shuffle;
import nom.bdezonia.zorbage.type.data.intunlim.UnboundedIntAlgebra;
import nom.bdezonia.zorbage.type.data.intunlim.UnboundedIntMember;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageGeneric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnboundedIntAlgebra {

	@Test
	public void testHugeNumbers() {
		UnboundedIntMember a = new UnboundedIntMember(Long.MAX_VALUE);
		UnboundedIntMember b = new UnboundedIntMember(44);
		UnboundedIntMember product = new UnboundedIntMember();

		G.INT_UNLIM.multiply().call(a,b,product);
		
		assertTrue(new BigInteger("405828369621610135508").equals(product.v()));
	}
	
	@Test
	public void minmax() {
		IndexedDataSource<UnboundedIntMember> list =
				new ArrayStorageGeneric<UnboundedIntAlgebra,UnboundedIntMember>(5000, G.INT_UNLIM);
		UnboundedIntMember a = G.INT_UNLIM.construct("1234567890");
		UnboundedIntMember b = G.INT_UNLIM.construct("55");
		for (long i = 0, j = list.size()-1; i < list.size()/2; i++,j--) {
			list.set(i, a);
			list.set(j, b);
			G.INT_UNLIM.pred().call(a, a);
			G.INT_UNLIM.succ().call(b, b);
		}
		Shuffle.compute(G.INT_UNLIM, list);
		UnboundedIntMember min = G.INT_UNLIM.construct();
		UnboundedIntMember max = G.INT_UNLIM.construct();
		MinMaxElement.compute(G.INT_UNLIM, list, min, max);
		assertEquals(BigInteger.valueOf(55), min.v());
		assertEquals(BigInteger.valueOf(1234567890), max.v());
	}
	
	@Test
	public void bigpower() {
		UnboundedIntMember a = G.INT_UNLIM.construct("3");
		UnboundedIntMember b = G.INT_UNLIM.construct("100");
		UnboundedIntMember c = G.INT_UNLIM.construct();
		
		G.INT_UNLIM.pow().call(a, b, c);
		
		assertEquals(new BigInteger("515377520732011331036461129765621272702107522001"), c.v());
	}
	
	@Test
	public void mathematicalMethods() {
		
		UnboundedIntMember a = G.INT_UNLIM.construct();
		UnboundedIntMember b = G.INT_UNLIM.construct();
		UnboundedIntMember c = G.INT_UNLIM.construct();
		
		// abs
		
		a.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.abs().call(a, b);
		assertEquals(BigInteger.ONE, b.v());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.abs().call(a, b);
		assertEquals(BigInteger.ZERO, b.v());
		
		a.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.abs().call(a, b);
		assertEquals(BigInteger.ONE, b.v());

		// add
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				G.INT_UNLIM.add().call(a, b, c);
				assertEquals(i+j, c.v().intValue());
			}
		}
		
		// andNot
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				G.INT_UNLIM.andNot().call(a, b, c);
				G.INT_UNLIM.bitAndNot().call(a, b, b);
				assertEquals(b.v(), c.v());
			}
		}
		
		// assign
		
		a.setV(BigInteger.valueOf(-43));
		b.setV(BigInteger.valueOf(1000));
		G.INT_UNLIM.assign().call(a, b);
		assertEquals(BigInteger.valueOf(-43), b.v());
		
		// bitAnd
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).and(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).and(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).and(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).and(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).and(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).and(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).and(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).and(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitAnd().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).and(BigInteger.valueOf(1)), c.v());
		
		// bitAndNot
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).andNot(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).andNot(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).andNot(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).andNot(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).andNot(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).andNot(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).andNot(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).andNot(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitAndNot().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).andNot(BigInteger.valueOf(1)), c.v());
		
		// bitCount

		for (int i = -65536; i <= 65536; i++) {
			a.setV(BigInteger.valueOf(i));
			assertEquals((int) BigInteger.valueOf(i).bitCount(), (int) G.INT_UNLIM.bitCount().call(a));
		}
		
		// bitLength

		for (int i = -65536; i <= 65536; i++) {
			a.setV(BigInteger.valueOf(i));
			assertEquals((int) BigInteger.valueOf(i).bitLength(), (int) G.INT_UNLIM.bitLength().call(a));
		}
		
		// bitNot
		
		a.setV(BigInteger.valueOf(-255));
		G.INT_UNLIM.bitNot().call(a, b);
		assertEquals(BigInteger.valueOf(-255).not(),b.v());
		
		a.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitNot().call(a, b);
		assertEquals(BigInteger.valueOf(-1).not(),b.v());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitNot().call(a, b);
		assertEquals(BigInteger.valueOf(0).not(),b.v());
		
		a.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitNot().call(a, b);
		assertEquals(BigInteger.valueOf(1).not(),b.v());
		
		a.setV(BigInteger.valueOf(255));
		G.INT_UNLIM.bitNot().call(a, b);
		assertEquals(BigInteger.valueOf(255).not(),b.v());
		
		// bitOr
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).or(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).or(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).or(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).or(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).or(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).or(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).or(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).or(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitOr().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).or(BigInteger.valueOf(1)), c.v());
		
		// bitShiftLeft
		
		a.setV(BigInteger.valueOf(-10));
		G.INT_UNLIM.bitShiftLeft().call(-1,a,b);
		assertEquals(BigInteger.valueOf(-5), b.v());
		
		a.setV(BigInteger.valueOf(-10));
		G.INT_UNLIM.bitShiftLeft().call(0,a,b);
		assertEquals(BigInteger.valueOf(-10), b.v());
		
		a.setV(BigInteger.valueOf(-10));
		G.INT_UNLIM.bitShiftLeft().call(1,a,b);
		assertEquals(BigInteger.valueOf(-20), b.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.bitShiftLeft().call(-1,a,b);
		assertEquals(BigInteger.valueOf(5), b.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.bitShiftLeft().call(0,a,b);
		assertEquals(BigInteger.valueOf(10), b.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.bitShiftLeft().call(1,a,b);
		assertEquals(BigInteger.valueOf(20), b.v());

		// bitShiftRight
		
		a.setV(BigInteger.valueOf(-10));
		G.INT_UNLIM.bitShiftRight().call(-1,a,b);
		assertEquals(BigInteger.valueOf(-20), b.v());
		
		a.setV(BigInteger.valueOf(-10));
		G.INT_UNLIM.bitShiftRight().call(0,a,b);
		assertEquals(BigInteger.valueOf(-10), b.v());
		
		a.setV(BigInteger.valueOf(-10));
		G.INT_UNLIM.bitShiftRight().call(1,a,b);
		assertEquals(BigInteger.valueOf(-5), b.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.bitShiftRight().call(-1,a,b);
		assertEquals(BigInteger.valueOf(20), b.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.bitShiftRight().call(0,a,b);
		assertEquals(BigInteger.valueOf(10), b.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.bitShiftRight().call(1,a,b);
		assertEquals(BigInteger.valueOf(5), b.v());

		// bitXor
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).xor(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).xor(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(-1).xor(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).xor(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).xor(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(0));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(0).xor(BigInteger.valueOf(1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(-1));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).xor(BigInteger.valueOf(-1)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).xor(BigInteger.valueOf(0)), c.v());
		
		a.setV(BigInteger.valueOf(1));
		b.setV(BigInteger.valueOf(1));
		G.INT_UNLIM.bitXor().call(a, b, c);
		assertEquals(BigInteger.valueOf(1).xor(BigInteger.valueOf(1)), c.v());
		
		// clearBit
		
		a.setV(BigInteger.ONE);
		b.setV(BigInteger.TEN);
		G.INT_UNLIM.clearBit().call(0, a, b);
		assertEquals(BigInteger.ZERO, b.v());
		
		// compare
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(0,(int)G.INT_UNLIM.compare().call(a, a));
		assertEquals(-1,(int)G.INT_UNLIM.compare().call(a, b));
		assertEquals(-1,(int)G.INT_UNLIM.compare().call(a, c));
		assertEquals(1,(int)G.INT_UNLIM.compare().call(b, a));
		assertEquals(0,(int)G.INT_UNLIM.compare().call(b, b));
		assertEquals(-1,(int)G.INT_UNLIM.compare().call(b, c));
		assertEquals(1,(int)G.INT_UNLIM.compare().call(c, a));
		assertEquals(1,(int)G.INT_UNLIM.compare().call(c, b));
		assertEquals(0,(int)G.INT_UNLIM.compare().call(c, c));
		
		// div
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				if (j != 0) {
					G.INT_UNLIM.div().call(a, b, c);
					assertEquals(BigInteger.valueOf(i/j), c.v());
				}
			}
		}
		
		// divMod
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				if (j != 0) {
					G.INT_UNLIM.divMod().call(a, b, b, c);
					assertEquals(BigInteger.valueOf(i/j), b.v());
					assertEquals(BigInteger.valueOf(i%j), c.v());
				}
			}
		}
		
		// flipBit
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.flipBit().call(0, a, b);
		assertEquals(BigInteger.valueOf(1), b.v());
		G.INT_UNLIM.flipBit().call(0, b, c);
		assertEquals(BigInteger.valueOf(0), c.v());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.flipBit().call(3, a, b);
		assertEquals(BigInteger.valueOf(8), b.v());
		G.INT_UNLIM.flipBit().call(3, b, c);
		assertEquals(BigInteger.valueOf(0), c.v());
		
		// gcd: tested as an algorithm elsewhere

		// getLowestBitSet

		a.setV(BigInteger.valueOf(0));
		assertEquals(-1, (int) G.INT_UNLIM.getLowestSetBit().call(a));

		a.setV(BigInteger.valueOf(1));
		assertEquals(0, (int) G.INT_UNLIM.getLowestSetBit().call(a));

		a.setV(BigInteger.valueOf(2));
		assertEquals(1, (int) G.INT_UNLIM.getLowestSetBit().call(a));

		a.setV(BigInteger.valueOf(4));
		assertEquals(2, (int) G.INT_UNLIM.getLowestSetBit().call(a));

		a.setV(BigInteger.valueOf(8));
		assertEquals(3, (int) G.INT_UNLIM.getLowestSetBit().call(a));
		
		// isEqual
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(true, G.INT_UNLIM.isEqual().call(a, a));
		assertEquals(false, G.INT_UNLIM.isEqual().call(a, b));
		assertEquals(false, G.INT_UNLIM.isEqual().call(a, c));
		assertEquals(false, G.INT_UNLIM.isEqual().call(b, a));
		assertEquals(true, G.INT_UNLIM.isEqual().call(b, b));
		assertEquals(false, G.INT_UNLIM.isEqual().call(b, c));
		assertEquals(false, G.INT_UNLIM.isEqual().call(c, a));
		assertEquals(false, G.INT_UNLIM.isEqual().call(c, b));
		assertEquals(true, G.INT_UNLIM.isEqual().call(c, c));

		// isNotEqual
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(false, G.INT_UNLIM.isNotEqual().call(a, a));
		assertEquals(true, G.INT_UNLIM.isNotEqual().call(a, b));
		assertEquals(true, G.INT_UNLIM.isNotEqual().call(a, c));
		assertEquals(true, G.INT_UNLIM.isNotEqual().call(b, a));
		assertEquals(false, G.INT_UNLIM.isNotEqual().call(b, b));
		assertEquals(true, G.INT_UNLIM.isNotEqual().call(b, c));
		assertEquals(true, G.INT_UNLIM.isNotEqual().call(c, a));
		assertEquals(true, G.INT_UNLIM.isNotEqual().call(c, b));
		assertEquals(false, G.INT_UNLIM.isNotEqual().call(c, c));

		// isLess
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(false, G.INT_UNLIM.isLess().call(a, a));
		assertEquals(true, G.INT_UNLIM.isLess().call(a, b));
		assertEquals(true, G.INT_UNLIM.isLess().call(a, c));
		assertEquals(false, G.INT_UNLIM.isLess().call(b, a));
		assertEquals(false, G.INT_UNLIM.isLess().call(b, b));
		assertEquals(true, G.INT_UNLIM.isLess().call(b, c));
		assertEquals(false, G.INT_UNLIM.isLess().call(c, a));
		assertEquals(false, G.INT_UNLIM.isLess().call(c, b));
		assertEquals(false, G.INT_UNLIM.isLess().call(c, c));

		// isLessEqual
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(true, G.INT_UNLIM.isLessEqual().call(a, a));
		assertEquals(true, G.INT_UNLIM.isLessEqual().call(a, b));
		assertEquals(true, G.INT_UNLIM.isLessEqual().call(a, c));
		assertEquals(false, G.INT_UNLIM.isLessEqual().call(b, a));
		assertEquals(true, G.INT_UNLIM.isLessEqual().call(b, b));
		assertEquals(true, G.INT_UNLIM.isLessEqual().call(b, c));
		assertEquals(false, G.INT_UNLIM.isLessEqual().call(c, a));
		assertEquals(false, G.INT_UNLIM.isLessEqual().call(c, b));
		assertEquals(true, G.INT_UNLIM.isLessEqual().call(c, c));

		// isGreater
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(false, G.INT_UNLIM.isGreater().call(a, a));
		assertEquals(false, G.INT_UNLIM.isGreater().call(a, b));
		assertEquals(false, G.INT_UNLIM.isGreater().call(a, c));
		assertEquals(true, G.INT_UNLIM.isGreater().call(b, a));
		assertEquals(false, G.INT_UNLIM.isGreater().call(b, b));
		assertEquals(false, G.INT_UNLIM.isGreater().call(b, c));
		assertEquals(true, G.INT_UNLIM.isGreater().call(c, a));
		assertEquals(true, G.INT_UNLIM.isGreater().call(c, b));
		assertEquals(false, G.INT_UNLIM.isGreater().call(c, c));

		// isGreaterEqual
		
		a.setV(BigInteger.valueOf(-1));
		b.setV(BigInteger.valueOf(0));
		c.setV(BigInteger.valueOf(1));
		assertEquals(true, G.INT_UNLIM.isGreaterEqual().call(a, a));
		assertEquals(false, G.INT_UNLIM.isGreaterEqual().call(a, b));
		assertEquals(false, G.INT_UNLIM.isGreaterEqual().call(a, c));
		assertEquals(true, G.INT_UNLIM.isGreaterEqual().call(b, a));
		assertEquals(true, G.INT_UNLIM.isGreaterEqual().call(b, b));
		assertEquals(false, G.INT_UNLIM.isGreaterEqual().call(b, c));
		assertEquals(true, G.INT_UNLIM.isGreaterEqual().call(c, a));
		assertEquals(true, G.INT_UNLIM.isGreaterEqual().call(c, b));
		assertEquals(true, G.INT_UNLIM.isGreaterEqual().call(c, c));

		// isEven
		
		a.setV(BigInteger.valueOf(2));
		b.setV(BigInteger.valueOf(1));
		assertEquals(true, G.INT_UNLIM.isEven().call(a));
		assertEquals(false, G.INT_UNLIM.isEven().call(b));
		
		// isOdd
		
		a.setV(BigInteger.valueOf(2));
		b.setV(BigInteger.valueOf(1));
		assertEquals(false, G.INT_UNLIM.isOdd().call(a));
		assertEquals(true, G.INT_UNLIM.isOdd().call(b));
		
		// isProbablePrime
		
		int certainty = 1000000;
		for (int i = -1024; i <= 1024; i++) {
			BigInteger v = BigInteger.valueOf(i);
			a.setV(v);
			assertEquals(v.isProbablePrime(certainty), G.INT_UNLIM.isProbablePrime().call(certainty, a));
		}
		
		// isZero

		a.setV(BigInteger.ZERO);
		assertEquals(true, G.INT_UNLIM.isZero().call(a));

		a.setV(BigInteger.ONE);
		assertEquals(false, G.INT_UNLIM.isZero().call(a));
		
		// lcm: tested as an algorithm elsewhere
		
		// max

		a.setV(BigInteger.ONE);
		a.setV(BigInteger.ZERO);
		G.INT_UNLIM.max().call(a, b, c);
		assertEquals(BigInteger.ONE, c.v());
		
		a.setV(BigInteger.ONE);
		a.setV(BigInteger.TEN);
		G.INT_UNLIM.max().call(a, b, c);
		assertEquals(BigInteger.TEN, c.v());
		
		// min

		a.setV(BigInteger.ONE);
		a.setV(BigInteger.ZERO);
		G.INT_UNLIM.min().call(a, b, c);
		assertEquals(BigInteger.ZERO, c.v());
		
		a.setV(BigInteger.ONE);
		a.setV(BigInteger.TEN);
		G.INT_UNLIM.min().call(a, b, c);
		assertEquals(BigInteger.ONE, c.v());
		
		// mod
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				if (j != 0) {
					G.INT_UNLIM.mod().call(a, b, c);
					assertEquals(i%j, c.v().intValue());
				}
			}
		}

		// modInverse
		
		a.setV(BigInteger.valueOf(20));
		b.setV(BigInteger.valueOf(7));
		G.INT_UNLIM.modInverse().call(a, b, c);
		assertEquals(a.v().modInverse(b.v()), c.v());

		// modPow
		
		a.setV(BigInteger.valueOf(20));
		b.setV(BigInteger.valueOf(7));
		c.setV(BigInteger.valueOf(3));
		G.INT_UNLIM.modPow().call(a, b, c, c);
		assertEquals((20*20*20*20*20*20*20)%3, c.v().intValue());

		// multiply
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				G.INT_UNLIM.multiply().call(a, b, c);
				assertEquals(i*j, c.v().intValue());
			}
		}

		// negate
		
		a.setV(BigInteger.valueOf(-4));
		G.INT_UNLIM.negate().call(a, b);
		assertEquals(4, b.v().intValue());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.negate().call(a, b);
		assertEquals(0, b.v().intValue());
		
		a.setV(BigInteger.valueOf(4));
		G.INT_UNLIM.negate().call(a, b);
		assertEquals(-4, b.v().intValue());
		
		// nextProbablePrime
		
		for (int i = 0; i <= 1024; i++) {
			a.setV(BigInteger.valueOf(i));
			G.INT_UNLIM.nextProbablePrime().call(a, b);
			assertEquals(a.v().nextProbablePrime(), b.v());
		}
		
		// norm
	
		a.setV(BigInteger.valueOf(-55));
		G.INT_UNLIM.norm().call(a, b);
		assertEquals(55, b.v().intValue());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.norm().call(a, b);
		assertEquals(0, b.v().intValue());
		
		a.setV(BigInteger.valueOf(55));
		G.INT_UNLIM.norm().call(a, b);
		assertEquals(55, b.v().intValue());
		
		// pow
		
		for (int i = -10; i <= 10; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = 0; j <= 10; j++) {
				b.setV(BigInteger.valueOf(j));
				if (i == 0 && j == 0) continue;
				G.INT_UNLIM.pow().call(a, b, c);
				assertEquals(a.v().pow(j), c.v());
			}
		}
		
		// power
		
		for (int i = -10; i <= 10; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = 0; j <= 10; j++) {
				if (i == 0 && j == 0) continue;
				G.INT_UNLIM.power().call(j, a, b);
				assertEquals(a.v().pow(j), b.v());
			}
		}

		// pred
		
		a.setV(BigInteger.valueOf(-4));
		G.INT_UNLIM.pred().call(a, a);
		assertEquals(BigInteger.valueOf(-5), a.v());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.pred().call(a, a);
		assertEquals(BigInteger.valueOf(-1), a.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.pred().call(a, a);
		assertEquals(BigInteger.valueOf(9), a.v());
		
		// setBit
		
		a.setV(BigInteger.ZERO);
		G.INT_UNLIM.setBit().call(0, a, b);
		assertEquals(1, b.v().intValue());
		
		G.INT_UNLIM.setBit().call(1, a, b);
		assertEquals(2, b.v().intValue());

		G.INT_UNLIM.setBit().call(2, a, b);
		assertEquals(4, b.v().intValue());
		
		G.INT_UNLIM.setBit().call(3, a, b);
		assertEquals(8, b.v().intValue());

		// signum
		
		a.setV(BigInteger.valueOf(-5));
		assertEquals(-1,(int)G.INT_UNLIM.signum().call(a));
		
		a.setV(BigInteger.valueOf(-1));
		assertEquals(-1,(int)G.INT_UNLIM.signum().call(a));
		
		a.setV(BigInteger.valueOf(0));
		assertEquals(0,(int)G.INT_UNLIM.signum().call(a));
		
		a.setV(BigInteger.valueOf(1));
		assertEquals(1,(int)G.INT_UNLIM.signum().call(a));
		
		a.setV(BigInteger.valueOf(5));
		assertEquals(1,(int)G.INT_UNLIM.signum().call(a));
		
		// subtract
		
		for (int i = -50; i <= 50; i++) {
			a.setV(BigInteger.valueOf(i));
			for (int j = -50; j <= 50; j++) {
				b.setV(BigInteger.valueOf(j));
				G.INT_UNLIM.subtract().call(a, b, c);
				assertEquals(i-j, c.v().intValue());
			}
		}
		
		// succ
		
		a.setV(BigInteger.valueOf(-4));
		G.INT_UNLIM.succ().call(a, a);
		assertEquals(BigInteger.valueOf(-3), a.v());
		
		a.setV(BigInteger.valueOf(0));
		G.INT_UNLIM.succ().call(a, a);
		assertEquals(BigInteger.valueOf(1), a.v());
		
		a.setV(BigInteger.valueOf(10));
		G.INT_UNLIM.succ().call(a, a);
		assertEquals(BigInteger.valueOf(11), a.v());
		
		// testBit

		a.setV(BigInteger.valueOf(13));
		assertEquals(true, G.INT_UNLIM.testBit().call(0, a));
		assertEquals(false, G.INT_UNLIM.testBit().call(1, a));
		assertEquals(true, G.INT_UNLIM.testBit().call(2, a));
		assertEquals(true, G.INT_UNLIM.testBit().call(3, a));
		assertEquals(false, G.INT_UNLIM.testBit().call(4, a));
		assertEquals(false, G.INT_UNLIM.testBit().call(5, a));
		
		// unity
		
		a.setV(BigInteger.valueOf(199));
		assertEquals(false, a.v().equals(BigInteger.ONE));
		G.INT_UNLIM.unity().call(a);
		assertEquals(true, a.v().equals(BigInteger.ONE));

		// zero
		
		a.setV(BigInteger.valueOf(199));
		assertEquals(false, a.v().equals(BigInteger.ZERO));
		G.INT_UNLIM.zero().call(a);
		assertEquals(true, a.v().equals(BigInteger.ZERO));
	}
}
