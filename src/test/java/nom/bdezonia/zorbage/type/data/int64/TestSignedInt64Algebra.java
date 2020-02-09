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

import java.util.ArrayList;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt64Algebra {

	@Test
	public void mathematicalMethods() {
		SignedInt64Member x = G.INT64.construct();
		assertEquals(0, x.v());
	
		SignedInt64Member y = G.INT64.construct("4431");
		assertEquals(4431, y.v());
		
		SignedInt64Member z = G.INT64.construct(y);
		assertEquals(4431, z.v());
		
		G.INT64.unity().call(z);
		assertEquals(1, z.v());
		
		G.INT64.zero().call(z);
		assertEquals(0, z.v());

		G.INT64.maxBound().call(x);
		assertEquals(Long.MAX_VALUE, x.v());
		
		G.INT64.minBound().call(x);
		assertEquals(Long.MIN_VALUE, x.v());

		SignedInt64Member a = G.INT64.construct();
		SignedInt64Member b = G.INT64.construct();
		SignedInt64Member c = G.INT64.construct();
		SignedInt64Member d = G.INT64.construct();
		
		ArrayList<SignedInt64Member> numsg = new ArrayList<>();
		numsg.add(new SignedInt64Member(Long.MIN_VALUE));
		numsg.add(new SignedInt64Member(Long.MIN_VALUE+1));
		numsg.add(new SignedInt64Member(Long.MIN_VALUE+2));
		numsg.add(new SignedInt64Member(Long.MAX_VALUE));
		numsg.add(new SignedInt64Member(Long.MAX_VALUE-1));
		numsg.add(new SignedInt64Member(Long.MAX_VALUE-2));
		numsg.add(new SignedInt64Member(-1));
		numsg.add(new SignedInt64Member(0));
		numsg.add(new SignedInt64Member(1));
		numsg.add(new SignedInt64Member(-2));
		numsg.add(new SignedInt64Member(2));
		numsg.add(new SignedInt64Member(3));
		numsg.add(new SignedInt64Member(-3));
		numsg.add(new SignedInt64Member(0xaf8304));
		numsg.add(new SignedInt64Member(-0xaf8304));
		numsg.add(new SignedInt64Member(-1111111));
		numsg.add(new SignedInt64Member(1111111));
		for (int i = 0; i < 4000; i++) {
			SignedInt64Member num = G.INT64.construct();
			G.INT64.random().call(num);
			numsg.add(num);
		}
		
		ArrayList<SignedInt64Member> numsh = new ArrayList<>();
		numsh.add(new SignedInt64Member(Long.MIN_VALUE));
		numsh.add(new SignedInt64Member(Long.MIN_VALUE+1));
		numsh.add(new SignedInt64Member(Long.MIN_VALUE+2));
		numsh.add(new SignedInt64Member(Long.MAX_VALUE-2));
		numsh.add(new SignedInt64Member(Long.MAX_VALUE-1));
		numsh.add(new SignedInt64Member(Long.MAX_VALUE-2));
		numsh.add(new SignedInt64Member(-1));
		numsh.add(new SignedInt64Member(0));
		numsh.add(new SignedInt64Member(1));
		numsh.add(new SignedInt64Member(-2));
		numsh.add(new SignedInt64Member(2));
		numsh.add(new SignedInt64Member(3));
		numsh.add(new SignedInt64Member(-3));
		numsh.add(new SignedInt64Member(0xaf8304));
		numsh.add(new SignedInt64Member(-0xaf8304));
		numsh.add(new SignedInt64Member(-1111111));
		numsh.add(new SignedInt64Member(1111111));
		for (int i = 0; i < 4000; i++) {
			SignedInt64Member num = G.INT64.construct();
			G.INT64.random().call(num);
			numsh.add(num);
		}
		
		for (int g = 0; g < numsg.size(); g++) {
			
			a.set(numsg.get(g));
			
			if (a.v() != Long.MIN_VALUE) {
				G.INT64.abs().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			G.INT64.assign().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.INT64.bitNot().call(a, c);
			assertEquals(~a.v(), c.v());
			
			for (int p = 0; p < 64; p++) {
				
				G.INT64.bitShiftLeft().call(p, a, c);
				assertEquals((long)(a.v() << p), c.v());
				
				G.INT64.bitShiftRight().call(p, a, c);
				assertEquals((long)(a.v() >> p), c.v());
				
				G.INT64.bitShiftRightFillZero().call(p, a, c);
				assertEquals((long)(a.v() >>> p), c.v());

				if (a.v() != 0 || p != 0) {
					long t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.INT64.power().call(p, a, c);
					assertEquals(t, c.v());
					b.setV(p);
					G.INT64.pow().call(a, b, c);
					assertEquals(t, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.INT64.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.INT64.isOdd().call(a));
			
			assertEquals(a.v()==0, G.INT64.isZero().call(a));

			if (a.v() != Long.MIN_VALUE) {
				G.INT64.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
			
			if (a.v() != Long.MIN_VALUE) {
				G.INT64.norm().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			if (a.v() > Long.MIN_VALUE) {
				G.INT64.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < Long.MAX_VALUE) {
				G.INT64.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.INT64.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < numsh.size(); h++) {
				
				b.set(numsh.get(h));
				
				G.INT64.random().call(b);
				
				G.INT64.add().call(a, b, c);
				assertEquals((long)(a.v()+b.v()), c.v());

				G.INT64.bitAnd().call(a, b, c);
				assertEquals(a.v() & b.v(), c.v());

				G.INT64.bitAndNot().call(a, b, c);
				assertEquals(a.v() & ~b.v(), c.v());

				G.INT64.bitOr().call(a, b, c);
				assertEquals(a.v() | b.v(), c.v());

				G.INT64.bitXor().call(a, b, c);
				assertEquals(a.v() ^ b.v(), c.v());

				if (a.v() < b.v())
					assertEquals(-1, (int) G.INT64.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.INT64.compare().call(a, b));
				else
					assertEquals(0, (int) G.INT64.compare().call(a, b));

				if ((b.v() != 0) && !(a.v() == Long.MIN_VALUE && b.v() == -1)) {
					
					G.INT64.div().call(a, b, x);
					assertEquals(a.v()/b.v(), x.v());

					G.INT64.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.INT64.divMod().call(a, b, c, d);
					assertEquals(x.v(), c.v());
					assertEquals(y.v(), d.v());
				}
				
				assertEquals(a.v()==b.v(), G.INT64.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.INT64.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.INT64.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.INT64.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.INT64.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.INT64.isGreaterEqual().call(a, b));

				G.INT64.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.INT64.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.INT64.multiply().call(a, b, c);
				assertEquals((long)(a.v()*b.v()), c.v());

				G.INT64.scale().call(a, b, c);
				assertEquals((long)(a.v()*b.v()), c.v());

				G.INT64.subtract().call(a, b, c);
				assertEquals((long)(a.v()-b.v()), c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.INT64.gcd();
		G.INT64.lcm();
	}
}
