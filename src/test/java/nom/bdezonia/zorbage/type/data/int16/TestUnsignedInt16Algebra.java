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
public class TestUnsignedInt16Algebra {

	@Test
	public void testPred() {
		UnsignedInt16Member v = new UnsignedInt16Member();
		
		v.setV(3);
		assertEquals(3, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(2, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(1, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0xffff, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0xfffe, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0xfffd, v.v());

		v.setV(0x8002);
		assertEquals(0x8002, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x8001, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x8000, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x7fff, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x7ffe, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x7ffd, v.v());
	}
	
	@Test
	public void testSucc() {
		UnsignedInt16Member v = new UnsignedInt16Member();
		
		v.setV(0xfffd);
		assertEquals(0xfffd, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0xfffe, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0xffff, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(1, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(2, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(3, v.v());

		v.setV(0x7ffd);
		assertEquals(0x7ffd, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x7ffe, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x7fff, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x8000, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x8001, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x8002, v.v());
	}

	@Test
	public void mathematicalMethods() {
		UnsignedInt16Member x = G.UINT16.construct();
		assertEquals(0, x.v);
	
		UnsignedInt16Member y = G.UINT16.construct("4431");
		assertEquals(4431, y.v);
		
		UnsignedInt16Member z = G.UINT16.construct(y);
		assertEquals(4431, z.v);
		
		G.UINT16.unity().call(z);
		assertEquals(1, z.v);
		
		G.UINT16.zero().call(z);
		assertEquals(0, z.v);

		G.UINT16.maxBound().call(x);
		assertEquals(0xffff, x.v());
		
		G.UINT16.minBound().call(x);
		assertEquals(0, x.v());

		UnsignedInt16Member a = G.UINT16.construct();
		UnsignedInt16Member b = G.UINT16.construct();
		UnsignedInt16Member c = G.UINT16.construct();
		UnsignedInt16Member d = G.UINT16.construct();
		
		ArrayList<UnsignedInt16Member> numsh = new ArrayList<>();
		numsh.add(new UnsignedInt16Member(0));
		numsh.add(new UnsignedInt16Member(1));
		numsh.add(new UnsignedInt16Member(0x32));
		numsh.add(new UnsignedInt16Member(0x64));
		numsh.add(new UnsignedInt16Member(0x128));
		numsh.add(new UnsignedInt16Member(0x519));
		numsh.add(new UnsignedInt16Member(0x1001));
		numsh.add(new UnsignedInt16Member(0x2000));
		numsh.add(new UnsignedInt16Member(0x3166));
		numsh.add(new UnsignedInt16Member(0x4163));
		numsh.add(new UnsignedInt16Member(0x5112));
		numsh.add(new UnsignedInt16Member(0x6944));
		numsh.add(new UnsignedInt16Member(0x7357));
		numsh.add(new UnsignedInt16Member(0x8166));
		numsh.add(new UnsignedInt16Member(0x9112));
		numsh.add(new UnsignedInt16Member(0xaf01));
		numsh.add(new UnsignedInt16Member(0xffff));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt16Member num = G.UINT16.construct();
			G.UINT16.random().call(num);
			numsh.add(num);
		}
		
		ArrayList<UnsignedInt16Member> numsg = new ArrayList<>();
		numsg.add(new UnsignedInt16Member(0));
		numsg.add(new UnsignedInt16Member(1));
		numsg.add(new UnsignedInt16Member(0x32));
		numsg.add(new UnsignedInt16Member(0x64));
		numsg.add(new UnsignedInt16Member(0x128));
		numsg.add(new UnsignedInt16Member(0x519));
		numsg.add(new UnsignedInt16Member(0x1001));
		numsg.add(new UnsignedInt16Member(0x2000));
		numsg.add(new UnsignedInt16Member(0x3166));
		numsg.add(new UnsignedInt16Member(0x4163));
		numsg.add(new UnsignedInt16Member(0x5112));
		numsg.add(new UnsignedInt16Member(0x6944));
		numsg.add(new UnsignedInt16Member(0x7357));
		numsg.add(new UnsignedInt16Member(0x8166));
		numsg.add(new UnsignedInt16Member(0x9112));
		numsg.add(new UnsignedInt16Member(0xaf01));
		numsg.add(new UnsignedInt16Member(0xffff));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt16Member num = G.UINT16.construct();
			G.UINT16.random().call(num);
			numsg.add(num);
		}
		
		for (int g = 0; g < numsg.size(); g++) {
			
			a.set(numsg.get(g));
			
			G.UINT16.abs().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT16.assign().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT16.bitNot().call(a, c);
			assertEquals(~a.v, c.v);
			
			for (int p = 0; p < 16; p++) {
				
				G.UINT16.bitShiftLeft().call(p, a, c);
				assertEquals((a.v() << p) & 0xffff, c.v());
				
				G.UINT16.bitShiftRight().call(p, a, c);
				assertEquals(a.v() >> p, c.v());
				
				G.UINT16.bitShiftRightFillZero().call(p, a, c);
				assertEquals(a.v() >>> p, c.v());

				if (a.v() != 0 || p != 0) {
					int t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.UINT16.power().call(p, a, c);
					assertEquals(t & 0xffff, c.v());
					b.setV(p);
					G.UINT16.pow().call(a, b, c);
					assertEquals(t & 0xffff, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.UINT16.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.UINT16.isOdd().call(a));
			
			assertEquals(a.v()==0, G.UINT16.isZero().call(a));

			G.UINT16.negate().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.UINT16.norm().call(a, c);
			assertEquals(a.v(), c.v());
			
			if (a.v() > 0) {
				G.UINT16.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < 0xffff) {
				G.UINT16.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.UINT16.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < numsh.size(); h++) {
				
				b.set(numsh.get(h));
				
				G.UINT16.add().call(a, b, c);
				assertEquals((a.v()+b.v()) & 0xffff, c.v());

				G.UINT16.bitAnd().call(a, b, c);
				assertEquals(a.v & b.v, c.v);

				G.UINT16.bitAndNot().call(a, b, c);
				assertEquals(a.v & ~b.v, c.v);

				G.UINT16.bitOr().call(a, b, c);
				assertEquals(a.v | b.v, c.v);

				G.UINT16.bitXor().call(a, b, c);
				assertEquals(a.v ^ b.v, c.v);

				if (a.v() < b.v())
					assertEquals(-1, (int) G.UINT16.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.UINT16.compare().call(a, b));
				else
					assertEquals(0, (int) G.UINT16.compare().call(a, b));

				if (b.v() != 0) {
					
					G.UINT16.div().call(a, b, x);
					assertEquals(a.v()/b.v(), x.v());

					G.UINT16.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.UINT16.divMod().call(a, b, c, d);
					assertEquals(x.v, c.v);
					assertEquals(y.v, d.v);
				}
				
				assertEquals(a.v()==b.v(), G.UINT16.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.UINT16.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.UINT16.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.UINT16.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.UINT16.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.UINT16.isGreaterEqual().call(a, b));

				G.UINT16.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.UINT16.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.UINT16.multiply().call(a, b, c);
				assertEquals((a.v()*b.v())&0xffff, c.v());

				G.UINT16.scale().call(a, b, c);
				assertEquals((a.v()*b.v())&0xffff, c.v());

				G.UINT16.subtract().call(a, b, c);
				assertEquals((a.v()-b.v())&0xffff, c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.UINT16.gcd();
		G.UINT16.lcm();
	}
	
	@Test
	public void rollover() {
		UnsignedInt16Member num = G.UINT16.construct();
		for (int offset : new int[] {0, 65536, 131072, 196608, -65536, -131072, -196608}) {
			for (int i = 0; i < 65536; i++) {
				num.setV(offset + i);
				if (i < 32768)
					assertEquals(i, num.v);
				else
					assertEquals(-(65536-i), num.v);
			}
		}
	}
}
