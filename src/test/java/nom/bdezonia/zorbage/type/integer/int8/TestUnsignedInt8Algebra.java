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
package nom.bdezonia.zorbage.type.integer.int8;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt8Algebra {

	@Test
	public void testPred() {
		UnsignedInt8Member v = new UnsignedInt8Member();
		
		v.setV(3);
		assertEquals(3, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(2, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(1, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0xff, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0xfe, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0xfd, v.v());

		v.setV(0x82);
		assertEquals(0x82, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0x81, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0x80, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0x7f, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0x7e, v.v());
		G.UINT8.pred().call(v, v);
		assertEquals(0x7d, v.v());
	}
	
	@Test
	public void testSucc() {
		UnsignedInt8Member v = new UnsignedInt8Member();
		
		v.setV(0xfd);
		assertEquals(0xfd, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0xfe, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0xff, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(1, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(2, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(3, v.v());

		v.setV(0x7d);
		assertEquals(0x7d, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0x7e, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0x7f, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0x80, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0x81, v.v());
		G.UINT8.succ().call(v, v);
		assertEquals(0x82, v.v());
	}


	@Test
	public void mathematicalMethods() {
		UnsignedInt8Member x = G.UINT8.construct();
		assertEquals(0, x.v);
	
		UnsignedInt8Member y = G.UINT8.construct("99");
		assertEquals(99, y.v);
		
		UnsignedInt8Member z = G.UINT8.construct(y);
		assertEquals(99, z.v);
		
		G.UINT8.unity().call(z);
		assertEquals(1, z.v);
		
		G.UINT8.zero().call(z);
		assertEquals(0, z.v);

		G.UINT8.maxBound().call(x);
		assertEquals(0xff, x.v());
		
		G.UINT8.minBound().call(x);
		assertEquals(0, x.v());

		UnsignedInt8Member a = G.UINT8.construct();
		UnsignedInt8Member b = G.UINT8.construct();
		UnsignedInt8Member c = G.UINT8.construct();
		UnsignedInt8Member d = G.UINT8.construct();
		
		for (int g = 0; g < 1000; g++) {
			G.UINT8.random().call(a);
			
			G.UINT8.abs().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT8.assign().call(a, c);
			assertEquals(a.v, c.v);
			
			G.UINT8.bitNot().call(a, c);
			assertEquals(~a.v, c.v);
			
			for (int p = 0; p < 8; p++) {
				
				G.UINT8.bitShiftLeft().call(p, a, c);
				assertEquals((a.v() << p) & 0xff, c.v());
				
				G.UINT8.bitShiftRight().call(p, a, c);
				assertEquals(a.v() >> p, c.v());
				
				G.UINT8.bitShiftRightFillZero().call(p, a, c);
				assertEquals(a.v() >>> p, c.v());

				if (a.v() != 0 || p != 0) {
					int t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.UINT8.power().call(p, a, c);
					assertEquals(t & 0xff, c.v());
					b.setV(p);
					G.UINT8.pow().call(a, b, c);
					assertEquals(t & 0xff, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.UINT8.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.UINT8.isOdd().call(a));
			
			assertEquals(a.v()==0, G.UINT8.isZero().call(a));

			G.UINT8.negate().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.UINT8.norm().call(a, c);
			assertEquals(a.v(), c.v());
			
			if (a.v() > 0) {
				G.UINT8.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < 0xff) {
				G.UINT8.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.UINT8.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < 1000; h++) {
				G.UINT8.random().call(b);
				
				G.UINT8.add().call(a, b, c);
				assertEquals((a.v()+b.v()) & 0xff, c.v());

				G.UINT8.bitAnd().call(a, b, c);
				assertEquals(a.v & b.v, c.v);

				G.UINT8.bitAndNot().call(a, b, c);
				assertEquals(a.v & ~b.v, c.v);

				G.UINT8.bitOr().call(a, b, c);
				assertEquals(a.v | b.v, c.v);

				G.UINT8.bitXor().call(a, b, c);
				assertEquals(a.v ^ b.v, c.v);

				if (a.v() < b.v())
					assertEquals(-1, (int) G.UINT8.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.UINT8.compare().call(a, b));
				else
					assertEquals(0, (int) G.UINT8.compare().call(a, b));

				if (b.v() != 0) {
					
					G.UINT8.div().call(a, b, x);
					assertEquals(a.v()/b.v(), x.v());

					G.UINT8.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.UINT8.divMod().call(a, b, c, d);
					assertEquals(x.v, c.v);
					assertEquals(y.v, d.v);
				}
				
				assertEquals(a.v()==b.v(), G.UINT8.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.UINT8.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.UINT8.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.UINT8.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.UINT8.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.UINT8.isGreaterEqual().call(a, b));

				G.UINT8.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.UINT8.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.UINT8.multiply().call(a, b, c);
				assertEquals((a.v()*b.v())&0xff, c.v());

				G.UINT8.scale().call(a, b, c);
				assertEquals((a.v()*b.v())&0xff, c.v());

				G.UINT8.subtract().call(a, b, c);
				assertEquals((a.v()-b.v())&0xff, c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.UINT8.gcd();
		G.UINT8.lcm();
	}
	
	@Test
	public void rollover() {
		UnsignedInt8Member num = G.UINT8.construct();
		for (int offset : new int[] {0, 256, 512, 768, 1024, 1280, -256, -512, -768, -1024, -1280}) {
			for (int i = 0; i < 256; i++) {
				num.setV(offset + i);
				if (i < 128)
					assertEquals(i, num.v);
				else
					assertEquals(-(256-i), num.v);
			}
		}
	}
}
