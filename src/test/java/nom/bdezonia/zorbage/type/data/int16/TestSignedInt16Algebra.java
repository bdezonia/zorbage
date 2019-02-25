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
package nom.bdezonia.zorbage.type.data.int16;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt16Algebra {

	@Test
	public void testPred() {
		SignedInt16Member v = new SignedInt16Member();
		
		v.setV(-32765);
		assertEquals(-32765, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(-32766, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(-32767, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(-32768, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(32767, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(32766, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(32765, v.v());

		v.setV(3);
		assertEquals(3, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(2, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(1, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(0, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(-1, v.v());
		G.INT16.pred().call(v, v);
		assertEquals(-2, v.v());
	}
	
	@Test
	public void testSucc() {
		SignedInt16Member v = new SignedInt16Member();
		
		v.setV(32765);
		assertEquals(32765, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(32766, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(32767, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(-32768, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(-32767, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(-32766, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(-32765, v.v());

		v.setV(-2);
		assertEquals(-2, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(-1, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(0, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(1, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(2, v.v());
		G.INT16.succ().call(v, v);
		assertEquals(3, v.v());
	}

	@Test
	public void mathematicalMethods() {
		SignedInt16Member x = G.INT16.construct();
		assertEquals(0, x.v());
	
		SignedInt16Member y = G.INT16.construct("4431");
		assertEquals(4431, y.v());
		
		SignedInt16Member z = G.INT16.construct(y);
		assertEquals(4431, z.v());
		
		G.INT16.unity().call(z);
		assertEquals(1, z.v());
		
		G.INT16.zero().call(z);
		assertEquals(0, z.v());

		G.INT16.maxBound().call(x);
		assertEquals(Short.MAX_VALUE, x.v());
		
		G.INT16.minBound().call(x);
		assertEquals(Short.MIN_VALUE, x.v());

		SignedInt16Member a = G.INT16.construct();
		SignedInt16Member b = G.INT16.construct();
		SignedInt16Member c = G.INT16.construct();
		SignedInt16Member d = G.INT16.construct();
		
		ArrayList<SignedInt16Member> numsg = new ArrayList<>();
		numsg.add(new SignedInt16Member(-32678));
		numsg.add(new SignedInt16Member(-32767));
		numsg.add(new SignedInt16Member(-32765));
		numsg.add(new SignedInt16Member(-2));
		numsg.add(new SignedInt16Member(-5));
		numsg.add(new SignedInt16Member(-11));
		numsg.add(new SignedInt16Member(-23));
		numsg.add(new SignedInt16Member(-47));
		numsg.add(new SignedInt16Member(-95));
		numsg.add(new SignedInt16Member(-1));
		numsg.add(new SignedInt16Member(0));
		numsg.add(new SignedInt16Member(1));
		numsg.add(new SignedInt16Member(2));
		numsg.add(new SignedInt16Member(5));
		numsg.add(new SignedInt16Member(11));
		numsg.add(new SignedInt16Member(23));
		numsg.add(new SignedInt16Member(47));
		numsg.add(new SignedInt16Member(95));
		numsg.add(new SignedInt16Member(32765));
		numsg.add(new SignedInt16Member(32767));
		for (int i = 0; i < 4000; i++) {
			SignedInt16Member num = G.INT16.construct();
			G.INT16.random().call(num);
			numsg.add(num);
		}
		
		ArrayList<SignedInt16Member> numsh = new ArrayList<>();
		numsh.add(new SignedInt16Member(-32678));
		numsh.add(new SignedInt16Member(-32767));
		numsh.add(new SignedInt16Member(-32765));
		numsh.add(new SignedInt16Member(-2));
		numsh.add(new SignedInt16Member(-5));
		numsh.add(new SignedInt16Member(-11));
		numsh.add(new SignedInt16Member(-23));
		numsh.add(new SignedInt16Member(-47));
		numsh.add(new SignedInt16Member(-95));
		numsh.add(new SignedInt16Member(-1));
		numsh.add(new SignedInt16Member(0));
		numsh.add(new SignedInt16Member(1));
		numsh.add(new SignedInt16Member(2));
		numsh.add(new SignedInt16Member(5));
		numsh.add(new SignedInt16Member(11));
		numsh.add(new SignedInt16Member(23));
		numsh.add(new SignedInt16Member(47));
		numsh.add(new SignedInt16Member(95));
		numsh.add(new SignedInt16Member(32765));
		numsh.add(new SignedInt16Member(32767));
		for (int i = 0; i < 4000; i++) {
			SignedInt16Member num = G.INT16.construct();
			G.INT16.random().call(num);
			numsh.add(num);
		}
		
		for (int g = 0; g < numsg.size(); g++) {
			
			a.set(numsg.get(g));

			if (a.v() != Short.MIN_VALUE) {
				G.INT16.abs().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			G.INT16.assign().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.INT16.bitNot().call(a, c);
			assertEquals(~a.v(), c.v());
			
			for (int p = 0; p < 16; p++) {
				
				G.INT16.bitShiftLeft().call(p, a, c);
				assertEquals((short)(a.v() << p), c.v());
				
				G.INT16.bitShiftRight().call(p, a, c);
				assertEquals((short)(a.v() >> p), c.v());
				
				G.INT16.bitShiftRightFillZero().call(p, a, c);
				assertEquals((short)(a.v() >>> p), c.v());

				if (a.v() != 0 || p != 0) {
					short t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.INT16.power().call(p, a, c);
					assertEquals(t, c.v());
					b.setV(p);
					G.INT16.pow().call(a, b, c);
					assertEquals(t, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.INT16.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.INT16.isOdd().call(a));
			
			assertEquals(a.v()==0, G.INT16.isZero().call(a));

			if (a.v() != Short.MIN_VALUE) {
				G.INT16.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
			
			if (a.v() != Short.MIN_VALUE) {
				G.INT16.norm().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			if (a.v() > Short.MIN_VALUE) {
				G.INT16.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < Short.MAX_VALUE) {
				G.INT16.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.INT16.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < numsh.size(); h++) {
				
				b.set(numsh.get(h));
				
				G.INT16.add().call(a, b, c);
				assertEquals((short)(a.v()+b.v()), c.v());

				G.INT16.bitAnd().call(a, b, c);
				assertEquals(a.v() & b.v(), c.v());

				G.INT16.bitAndNot().call(a, b, c);
				assertEquals(a.v() & ~b.v(), c.v());

				G.INT16.bitOr().call(a, b, c);
				assertEquals(a.v() | b.v(), c.v());

				G.INT16.bitXor().call(a, b, c);
				assertEquals(a.v() ^ b.v(), c.v());

				if (a.v() < b.v())
					assertEquals(-1, (int) G.INT16.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.INT16.compare().call(a, b));
				else
					assertEquals(0, (int) G.INT16.compare().call(a, b));

				if ((b.v() != 0) && !(a.v() == Short.MIN_VALUE && b.v() == -1)) {
					
					G.INT16.div().call(a, b, x);
					assertEquals(a.v()/b.v(), x.v());

					G.INT16.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.INT16.divMod().call(a, b, c, d);
					assertEquals(x.v(), c.v());
					assertEquals(y.v(), d.v());
				}
				
				assertEquals(a.v()==b.v(), G.INT16.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.INT16.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.INT16.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.INT16.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.INT16.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.INT16.isGreaterEqual().call(a, b));

				G.INT16.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.INT16.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.INT16.multiply().call(a, b, c);
				assertEquals((short)(a.v()*b.v()), c.v());

				G.INT16.scale().call(a, b, c);
				assertEquals((short)(a.v()*b.v()), c.v());

				G.INT16.subtract().call(a, b, c);
				assertEquals((short)(a.v()-b.v()), c.v());

			}
			if (a.v() != Short.MIN_VALUE) {
				G.INT16.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
		}

		// tested as an algorithm elsewhere
		G.INT16.gcd();
		G.INT16.lcm();
	}
	
	@Test
	public void rollover() {
		SignedInt16Member num = G.INT16.construct();
		for (int offset : new int[] {0, 65536, 131072, 196608, -65536, -131072, -196608}) {
			for (int i = Short.MIN_VALUE; i < Short.MAX_VALUE; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v());
			}
		}
	}
}
