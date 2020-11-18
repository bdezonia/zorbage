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
package nom.bdezonia.zorbage.type.integer.int128;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.integer.int128.UnsignedInt128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt128 {

	final BigInteger two = BigInteger.valueOf(2);
	final BigInteger two128 = two.pow(128);
	final BigInteger two128minus1 = two128.subtract(BigInteger.ONE);
	
	@Test
	public void testStorageMethods() {

		IndexedDataSource<UnsignedInt128Member> data =
				ArrayStorage.allocate(new UnsignedInt128Member(), 6000);
		UnsignedInt128Member in = new UnsignedInt128Member();
		UnsignedInt128Member out = new UnsignedInt128Member();
		in.setV(BigInteger.ZERO);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(BigInteger.ZERO, out.v());
		}
		for (long i = 0; i < data.size(); i++) {
			in.setV(BigInteger.valueOf(i));
			data.set(i, in);
			out.setV(in.v().subtract(BigInteger.ONE));
			data.get(i, out);
			assertEquals(in.v(), out.v());
		}
		in.setV(BigInteger.ZERO);
		for (long i = 0; i < data.size(); i++) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(BigInteger.ZERO, out.v());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setV(BigInteger.valueOf(i));
			data.set(i, in);
			out.setV(in.v().subtract(BigInteger.ONE));
			data.get(i, out);
			assertEquals(in.v(), out.v());
		}
		in.setV(BigInteger.ZERO);
		for (long i = data.size()-1; i >= 0; i--) {
			data.set(i, in);
			data.get(i, out);
			assertEquals(BigInteger.ZERO, out.v());
		}
		for (long i = data.size()-1; i >= 0; i--) {
			in.setV(BigInteger.valueOf(i));
			data.set(i, in);
			out.setV(in.v().subtract(BigInteger.ONE));
			data.get(i, out);
			assertEquals(in.v(), out.v());
		}
	}

	@Test
	public void testMathematicalMethods() {
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();

		G.UINT128.minBound().call(a);
		G.UINT128.maxBound().call(b);
		
		ArrayList<UnsignedInt128Member> numsg = new ArrayList<>();
		numsg.add(new UnsignedInt128Member(a));
		numsg.add(new UnsignedInt128Member(a.v().add(BigInteger.ONE)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(-111111111111111L)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(-2048)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(-1024)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(-512)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(-256)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(-1)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(0)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(1)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(256)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(512)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(1024)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(2048)));
		numsg.add(new UnsignedInt128Member(BigInteger.valueOf(111111111111111L)));
		numsg.add(new UnsignedInt128Member(b.v().subtract(BigInteger.ONE)));
		numsg.add(new UnsignedInt128Member(b));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt128Member num = G.UINT128.construct();
			G.UINT128.random().call(num);
			numsg.add(num);
		}
		
		ArrayList<UnsignedInt128Member> numsh = new ArrayList<>();
		numsh.add(new UnsignedInt128Member(a));
		numsh.add(new UnsignedInt128Member(a.v().add(BigInteger.ONE)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(-111111111111111L)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(-2048)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(-1024)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(-512)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(-256)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(-1)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(0)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(1)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(256)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(512)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(1024)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(2048)));
		numsh.add(new UnsignedInt128Member(BigInteger.valueOf(111111111111111L)));
		numsh.add(new UnsignedInt128Member(b.v().subtract(BigInteger.ONE)));
		numsh.add(new UnsignedInt128Member(b));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt128Member num = G.UINT128.construct();
			G.UINT128.random().call(num);
			numsh.add(num);
		}
		
		for (int i = 0; i < numsg.size(); i++) {

			if (i % 100 == 0)
				System.out.println(i);
			
			a.set(numsg.get(i));

			BigInteger bigA = a.v();
			
			for (int j = 0; j < numsh.size(); j++) {

				b.set(numsh.get(i));

				BigInteger bigB = b.v();

				c.set(b);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.abs().call(b, c);
				assertEquals(b.v(),c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.add().call(a, b, c);
				assertEquals(bigA.add(bigB).and(two128minus1), c.v());
				
				c.set(b);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.assign().call(b, c);
				assertEquals(b.v(),c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.bitAnd().call(a, b, c);
				assertEquals(bigA.and(bigB).and(two128minus1), c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.bitOr().call(a, b, c);
				assertEquals(bigA.or(bigB).and(two128minus1), c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.bitShiftLeft().call(j, a, c);
				assertEquals(bigA.shiftLeft(j%128).and(two128minus1), c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.bitShiftRight().call(j, a, c);
				assertEquals(bigA.shiftRight(j).and(two128minus1), c.v());

				/*
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.bitShiftRightFillZero().call(j, a, c);
				assertEquals((i >>> j)&0xfff, c.v);
				*/
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.bitXor().call(a, b, c);
				assertEquals(bigA.xor(bigB).and(two128minus1), c.v());
				
				assertEquals(bigA.compareTo(bigB),(long) G.UINT128.compare().call(a, b));
				
				UnsignedInt128Member v = G.UINT128.construct();
				assertEquals(BigInteger.ZERO, v.v());
				
				v = G.UINT128.construct((bigA.add(bigB).and(two128minus1)).toString());
				assertEquals(bigA.add(bigB).and(two128minus1), v.v());
				
				v = G.UINT128.construct(a);
				assertEquals(bigA,v.v());
				
				if (bigB.signum() != 0) {
					c.set(a);
					G.UINT128.pred().call(c, c);
					G.UINT128.pred().call(c, c);
					G.UINT128.div().call(a, b, c);
					assertEquals(bigA.divide(bigB), c.v());
				}
				
				// TODO: gcd()
				// TODO: lcm()
				
				assertEquals((bigB.and(BigInteger.ONE).equals(BigInteger.ZERO)), G.UINT128.isEven().call(b));
				assertEquals((bigB.and(BigInteger.ONE).equals(BigInteger.ONE)), G.UINT128.isOdd().call(b));
				
				assertEquals(bigA.compareTo(bigB) == 0, G.UINT128.isEqual().call(a, b));
				assertEquals(bigA.compareTo(bigB) != 0, G.UINT128.isNotEqual().call(a, b));
				assertEquals(bigA.compareTo(bigB) < 0, G.UINT128.isLess().call(a, b));
				assertEquals(bigA.compareTo(bigB) <= 0, G.UINT128.isLessEqual().call(a, b));
				assertEquals(bigA.compareTo(bigB) > 0, G.UINT128.isGreater().call(a, b));
				assertEquals(bigA.compareTo(bigB) >= 0, G.UINT128.isGreaterEqual().call(a, b));
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.max().call(a, b, c);
				assertEquals(bigA.max(bigB), c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.min().call(a, b, c);
				assertEquals(bigA.min(bigB), c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.maxBound().call(c);
				assertEquals(two128minus1, c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.minBound().call(c);
				assertEquals(BigInteger.ZERO, c.v());
				
				if (bigB.signum() != 0) {
					c.set(a);
					G.UINT128.pred().call(c, c);
					G.UINT128.pred().call(c, c);
					G.UINT128.mod().call(a, b, c);
					assertEquals(bigA.mod(bigB), c.v());
				}
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.multiply().call(a, b, c);
				assertEquals(bigA.multiply(bigB).and(two128minus1),c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.norm().call(a, c);
				assertEquals(bigA, c.v());

				// pow() and power() lightly tested elsewhere
				
				c.set(b);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(b, c);
				BigInteger expected = (bigB.equals(BigInteger.ZERO) ? two128minus1 : bigB.subtract(BigInteger.ONE));
				assertEquals(expected, c.v());
				
				c.set(b);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.succ().call(b, c);
				expected = (bigB.equals(two128minus1)) ? BigInteger.ZERO : bigB.add(BigInteger.ONE);
				assertEquals(expected, c.v());
				
				assertEquals(bigB.signum(), (int) G.UINT128.signum().call(b));
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.subtract().call(a, b, c);
				assertEquals(bigA.subtract(bigB).add(two128).and(two128minus1), c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.unity().call(c);
				assertEquals(BigInteger.ONE, c.v());
				
				c.set(a);
				G.UINT128.pred().call(c, c);
				G.UINT128.pred().call(c, c);
				G.UINT128.zero().call(c);
				assertEquals(BigInteger.ZERO, c.v());
			}
		}
	}
	
	@Test
	public void miscNumerics() {
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		
		a.setV(BigInteger.valueOf(23));
		b.setV(BigInteger.valueOf(4));
		G.UINT128.pow().call(a, b, c);
		assertEquals(BigInteger.valueOf(23*23*23*23), c.v());
		
		a.setV(BigInteger.valueOf(9));
		G.UINT128.power().call(7,a,b);
		assertEquals(BigInteger.valueOf(9*9*9*9*9*9*9), b.v());
		
		// test something bigger than 64 bits
		
		a.setV(BigInteger.valueOf(2));
		G.UINT128.power().call(127,a,b);
		assertEquals(two.pow(127), b.v());
		
		// value confirmed by https://www.symbolab.com/solver/gcd-calculator
		a.setV(new BigInteger("4500000"));
		b.setV(new BigInteger("260000"));
		G.UINT128.gcd().call(a, b, c);
		assertEquals(new BigInteger("20000"), c.v());
		
		// value confirmed by https://www.symbolab.com/solver/lcm-calculator
		a.setV(new BigInteger("60491000"));
		b.setV(new BigInteger("20000"));
		G.UINT128.lcm().call(a, b, c);
		assertEquals(new BigInteger("1209820000"), c.v());
	}
}
