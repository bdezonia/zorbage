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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt32Algebra {

	@Test
	public void testInts() {
		  
		SignedInt32Member a = G.INT32.construct("1");
		SignedInt32Member b = G.INT32.construct("4");
		SignedInt32Member sum = G.INT32.construct("99");

		G.INT32.add().call(a, b, sum);
		
		assertEquals(5, sum.v());
	}
	
	@Test
	public void testPower() {
		SignedInt32Member a = G.INT32.construct();
		SignedInt32Member b = G.INT32.construct();
		
		a.setV(2);
		
		try {
			G.INT32.power().call(-1, a, b);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		for (int i = 0; i < 100; i++) {
			G.INT32.power().call(i, a, b);
			assertEquals(pow(a.v(), i), b.v());
		}

	}
	
	private int pow(int base, int power) {
		int total = 1;
		for (int i = 0; i < power; i++) {
			total *= base;
		}
		return total;
	}
	
	@Test
	public void mathematicalMethods() {
		SignedInt32Member x = G.INT32.construct();
		assertEquals(0, x.v());
	
		SignedInt32Member y = G.INT32.construct("4431");
		assertEquals(4431, y.v());
		
		SignedInt32Member z = G.INT32.construct(y);
		assertEquals(4431, z.v());
		
		G.INT32.unity().call(z);
		assertEquals(1, z.v());
		
		G.INT32.zero().call(z);
		assertEquals(0, z.v());

		G.INT32.maxBound().call(x);
		assertEquals(Integer.MAX_VALUE, x.v());
		
		G.INT32.minBound().call(x);
		assertEquals(Integer.MIN_VALUE, x.v());

		SignedInt32Member a = G.INT32.construct();
		SignedInt32Member b = G.INT32.construct();
		SignedInt32Member c = G.INT32.construct();
		SignedInt32Member d = G.INT32.construct();
		
		ArrayList<SignedInt32Member> numsh = new ArrayList<>();
		numsh.add(new SignedInt32Member(Integer.MIN_VALUE));
		numsh.add(new SignedInt32Member(Integer.MIN_VALUE+1));
		numsh.add(new SignedInt32Member(Integer.MIN_VALUE+2));
		numsh.add(new SignedInt32Member(-2));
		numsh.add(new SignedInt32Member(-1));
		numsh.add(new SignedInt32Member(-0));
		numsh.add(new SignedInt32Member(0));
		numsh.add(new SignedInt32Member(1));
		numsh.add(new SignedInt32Member(2));
		numsh.add(new SignedInt32Member(-100));
		numsh.add(new SignedInt32Member(100));
		numsh.add(new SignedInt32Member(-22717));
		numsh.add(new SignedInt32Member(22717));
		numsh.add(new SignedInt32Member(Integer.MAX_VALUE-2));
		numsh.add(new SignedInt32Member(Integer.MAX_VALUE-1));
		numsh.add(new SignedInt32Member(Integer.MAX_VALUE));
		for (int i = 0; i < 4000; i++) {
			SignedInt32Member num = G.INT32.construct();
			G.INT32.random().call(num);
			numsh.add(num);
		}
		
		ArrayList<SignedInt32Member> numsg = new ArrayList<>();
		numsg.add(new SignedInt32Member(Integer.MIN_VALUE));
		numsg.add(new SignedInt32Member(Integer.MIN_VALUE+1));
		numsg.add(new SignedInt32Member(Integer.MIN_VALUE+2));
		numsg.add(new SignedInt32Member(-2));
		numsg.add(new SignedInt32Member(-1));
		numsg.add(new SignedInt32Member(-0));
		numsg.add(new SignedInt32Member(0));
		numsg.add(new SignedInt32Member(1));
		numsg.add(new SignedInt32Member(2));
		numsg.add(new SignedInt32Member(-100));
		numsg.add(new SignedInt32Member(100));
		numsg.add(new SignedInt32Member(-22717));
		numsg.add(new SignedInt32Member(22717));
		numsg.add(new SignedInt32Member(Integer.MAX_VALUE-2));
		numsg.add(new SignedInt32Member(Integer.MAX_VALUE-1));
		numsg.add(new SignedInt32Member(Integer.MAX_VALUE));
		for (int i = 0; i < 4000; i++) {
			SignedInt32Member num = G.INT32.construct();
			G.INT32.random().call(num);
			numsg.add(num);
		}
		
		for (int g = 0; g < numsg.size(); g++) {
			
			a.set(numsg.get(g));
			
			if (a.v() != Integer.MIN_VALUE) {
				G.INT32.abs().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			G.INT32.assign().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.INT32.bitNot().call(a, c);
			assertEquals(~a.v(), c.v());
			
			for (int p = 0; p < 32; p++) {
				
				G.INT32.bitShiftLeft().call(p, a, c);
				assertEquals((int)(a.v() << p), c.v());
				
				G.INT32.bitShiftRight().call(p, a, c);
				assertEquals((int)(a.v() >> p), c.v());
				
				G.INT32.bitShiftRightFillZero().call(p, a, c);
				assertEquals((int)(a.v() >>> p), c.v());

				if (a.v() != 0 || p != 0) {
					int t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.INT32.power().call(p, a, c);
					assertEquals(t, c.v());
					b.setV(p);
					G.INT32.pow().call(a, b, c);
					assertEquals(t, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.INT32.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.INT32.isOdd().call(a));
			
			assertEquals(a.v()==0, G.INT32.isZero().call(a));

			if (a.v() != Integer.MIN_VALUE) {
				G.INT32.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
			
			if (a.v() != Integer.MIN_VALUE) {
				G.INT32.norm().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			if (a.v() > Integer.MIN_VALUE) {
				G.INT32.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < Integer.MAX_VALUE) {
				G.INT32.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.INT32.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < numsh.size(); h++) {

				b.set(numsg.get(h));
				
				G.INT32.add().call(a, b, c);
				assertEquals((int)(a.v()+b.v()), c.v());

				G.INT32.bitAnd().call(a, b, c);
				assertEquals(a.v() & b.v(), c.v());

				G.INT32.bitAndNot().call(a, b, c);
				assertEquals(a.v() & ~b.v(), c.v());

				G.INT32.bitOr().call(a, b, c);
				assertEquals(a.v() | b.v(), c.v());

				G.INT32.bitXor().call(a, b, c);
				assertEquals(a.v() ^ b.v(), c.v());

				if (a.v() < b.v())
					assertEquals(-1, (int) G.INT32.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.INT32.compare().call(a, b));
				else
					assertEquals(0, (int) G.INT32.compare().call(a, b));

				if ((b.v() != 0) && !(a.v() == Integer.MIN_VALUE && b.v() == -1)) {
					
					G.INT32.div().call(a, b, x);
					assertEquals(a.v()/b.v(), x.v());

					G.INT32.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.INT32.divMod().call(a, b, c, d);
					assertEquals(x.v(), c.v());
					assertEquals(y.v(), d.v());
				}
				
				assertEquals(a.v()==b.v(), G.INT32.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.INT32.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.INT32.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.INT32.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.INT32.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.INT32.isGreaterEqual().call(a, b));

				G.INT32.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.INT32.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.INT32.multiply().call(a, b, c);
				assertEquals((int)(a.v()*b.v()), c.v());

				G.INT32.scale().call(a, b, c);
				assertEquals((int)(a.v()*b.v()), c.v());

				G.INT32.subtract().call(a, b, c);
				assertEquals((int)(a.v()-b.v()), c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.INT32.gcd();
		G.INT32.lcm();
	}
}
