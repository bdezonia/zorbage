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
package nom.bdezonia.zorbage.type.data.int32;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt32Algebra {

	@Test
	public void testPred() {
		UnsignedInt32Member v = new UnsignedInt32Member();
		
		v.setV(3);
		assertEquals(3, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(2, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(1, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0xffffffffL, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0xfffffffeL, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0xfffffffdL, v.v());

		v.setV(0x80000002L);
		assertEquals(0x80000002L, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0x80000001L, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0x80000000L, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0x7fffffffL, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0x7ffffffeL, v.v());
		G.UINT32.pred().call(v, v);
		assertEquals(0x7ffffffdL, v.v());
	}
	
	@Test
	public void testSucc() {
		UnsignedInt32Member v = new UnsignedInt32Member();
		
		v.setV(0xfffffffdL);
		assertEquals(0xfffffffdL, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0xfffffffeL, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0xffffffffL, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(1, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(2, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(3, v.v());

		v.setV(0x7ffffffdL);
		assertEquals(0x7ffffffdL, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0x7ffffffeL, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0x7fffffffL, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0x80000000L, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0x80000001L, v.v());
		G.UINT32.succ().call(v, v);
		assertEquals(0x80000002L, v.v());
	}


	@Test
	public void mathematicalMethods() {
		UnsignedInt32Member x = G.UINT32.construct();
		assertEquals(0, x.v);
	
		UnsignedInt32Member y = G.UINT32.construct("4431");
		assertEquals(4431, y.v);
		
		UnsignedInt32Member z = G.UINT32.construct(y);
		assertEquals(4431, z.v);
		
		G.UINT32.unity().call(z);
		assertEquals(1, z.v);
		
		G.UINT32.zero().call(z);
		assertEquals(0, z.v);

		G.UINT32.maxBound().call(x);
		assertEquals(0xffffffffL, x.v());
		
		G.UINT32.minBound().call(x);
		assertEquals(0, x.v());

		UnsignedInt32Member a = G.UINT32.construct();
		UnsignedInt32Member b = G.UINT32.construct();
		UnsignedInt32Member c = G.UINT32.construct();
		UnsignedInt32Member d = G.UINT32.construct();
		
		ArrayList<UnsignedInt32Member> numsg = new ArrayList<>();
		numsg.add(new UnsignedInt32Member(Integer.MIN_VALUE));
		numsg.add(new UnsignedInt32Member(Integer.MIN_VALUE+1));
		numsg.add(new UnsignedInt32Member(Integer.MIN_VALUE+2));
		numsg.add(new UnsignedInt32Member(-2));
		numsg.add(new UnsignedInt32Member(-1));
		numsg.add(new UnsignedInt32Member(-0));
		numsg.add(new UnsignedInt32Member(0));
		numsg.add(new UnsignedInt32Member(1));
		numsg.add(new UnsignedInt32Member(2));
		numsg.add(new UnsignedInt32Member(-100));
		numsg.add(new UnsignedInt32Member(100));
		numsg.add(new UnsignedInt32Member(-22717));
		numsg.add(new UnsignedInt32Member(22717));
		numsg.add(new UnsignedInt32Member(Integer.MAX_VALUE-2));
		numsg.add(new UnsignedInt32Member(Integer.MAX_VALUE-1));
		numsg.add(new UnsignedInt32Member(Integer.MAX_VALUE));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt32Member num = G.UINT32.construct();
			G.UINT32.random().call(num);
			numsg.add(num);
		}
		
		ArrayList<UnsignedInt32Member> numsh = new ArrayList<>();
		numsh.add(new UnsignedInt32Member(Integer.MIN_VALUE));
		numsh.add(new UnsignedInt32Member(Integer.MIN_VALUE+1));
		numsh.add(new UnsignedInt32Member(Integer.MIN_VALUE+2));
		numsh.add(new UnsignedInt32Member(-2));
		numsh.add(new UnsignedInt32Member(-1));
		numsh.add(new UnsignedInt32Member(-0));
		numsh.add(new UnsignedInt32Member(0));
		numsh.add(new UnsignedInt32Member(1));
		numsh.add(new UnsignedInt32Member(2));
		numsh.add(new UnsignedInt32Member(-100));
		numsh.add(new UnsignedInt32Member(100));
		numsh.add(new UnsignedInt32Member(-22717));
		numsh.add(new UnsignedInt32Member(22717));
		numsh.add(new UnsignedInt32Member(Integer.MAX_VALUE-2));
		numsh.add(new UnsignedInt32Member(Integer.MAX_VALUE-1));
		numsh.add(new UnsignedInt32Member(Integer.MAX_VALUE));
		for (int i = 0; i < 4000; i++) {
			UnsignedInt32Member num = G.UINT32.construct();
			G.UINT32.random().call(num);
			numsh.add(num);
		}
		
		for (int g = 0; g < numsg.size(); g++) {
			
			a.set(numsg.get(g));
			
			G.UINT32.abs().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT32.assign().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT32.bitNot().call(a, c);
			assertEquals(~a.v, c.v);
			
			for (int p = 0; p < 16; p++) {
				
				G.UINT32.bitShiftLeft().call(p, a, c);
				assertEquals((a.v() << p) & 0xffffffffL, c.v());
				
				G.UINT32.bitShiftRight().call(p, a, c);
				assertEquals(a.v() >> p, c.v());
				
				G.UINT32.bitShiftRightFillZero().call(p, a, c);
				assertEquals(a.v() >>> p, c.v());

				if (a.v() != 0 || p != 0) {
					long t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.UINT32.power().call(p, a, c);
					assertEquals(t & 0xffffffffL, c.v());
					b.setV(p);
					G.UINT32.pow().call(a, b, c);
					assertEquals(t & 0xffffffffL, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.UINT32.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.UINT32.isOdd().call(a));
			
			assertEquals(a.v()==0, G.UINT32.isZero().call(a));

			G.UINT32.negate().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.UINT32.norm().call(a, c);
			assertEquals(a.v(), c.v());
			
			if (a.v() > 0) {
				G.UINT32.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < 0xffff) {
				G.UINT32.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.UINT32.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < numsh.size(); h++) {
				
				b.set(numsh.get(h));
				
				G.UINT32.add().call(a, b, c);
				assertEquals((a.v()+b.v()) & 0xffffffffL, c.v());

				G.UINT32.bitAnd().call(a, b, c);
				assertEquals(a.v & b.v, c.v);

				G.UINT32.bitAndNot().call(a, b, c);
				assertEquals(a.v & ~b.v, c.v);

				G.UINT32.bitOr().call(a, b, c);
				assertEquals(a.v | b.v, c.v);

				G.UINT32.bitXor().call(a, b, c);
				assertEquals(a.v ^ b.v, c.v);

				if (a.v() < b.v())
					assertEquals(-1, (int) G.UINT32.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.UINT32.compare().call(a, b));
				else
					assertEquals(0, (int) G.UINT32.compare().call(a, b));

				if (b.v() != 0) {
					
					G.UINT32.div().call(a, b, x);
					assertEquals(a.v()/b.v(), x.v());

					G.UINT32.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.UINT32.divMod().call(a, b, c, d);
					assertEquals(x.v, c.v);
					assertEquals(y.v, d.v);
				}
				
				assertEquals(a.v()==b.v(), G.UINT32.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.UINT32.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.UINT32.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.UINT32.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.UINT32.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.UINT32.isGreaterEqual().call(a, b));

				G.UINT32.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.UINT32.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.UINT32.multiply().call(a, b, c);
				assertEquals((a.v()*b.v())&0xffffffffL, c.v());

				G.UINT32.scale().call(a, b, c);
				assertEquals((a.v()*b.v())&0xffffffffL, c.v());

				G.UINT32.subtract().call(a, b, c);
				assertEquals((a.v()-b.v())&0xffffffffL, c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.UINT32.gcd();
		G.UINT32.lcm();
	}
}
