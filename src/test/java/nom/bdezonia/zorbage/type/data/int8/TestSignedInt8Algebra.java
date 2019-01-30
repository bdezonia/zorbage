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
package nom.bdezonia.zorbage.type.data.int8;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSignedInt8Algebra {

	@Test
	public void mathematicalMethods() {
		SignedInt8Member x = G.INT8.construct();
		assertEquals(0, x.v());
	
		SignedInt8Member y = G.INT8.construct("99");
		assertEquals(99, y.v());
		
		SignedInt8Member z = G.INT8.construct(y);
		assertEquals(99, z.v());
		
		G.INT8.unity().call(z);
		assertEquals(1, z.v());
		
		G.INT8.zero().call(z);
		assertEquals(0, z.v());

		G.INT8.maxBound().call(x);
		assertEquals(Byte.MAX_VALUE, x.v());
		
		G.INT8.minBound().call(x);
		assertEquals(Byte.MIN_VALUE, x.v());

		SignedInt8Member a = G.INT8.construct();
		SignedInt8Member b = G.INT8.construct();
		SignedInt8Member c = G.INT8.construct();
		SignedInt8Member d = G.INT8.construct();
		
		for (int g = 0; g < 1000; g++) {
			G.INT8.random().call(a);
			
			if (a.v() != Byte.MIN_VALUE) {
				G.INT8.abs().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			G.INT8.assign().call(a, c);
			assertEquals(a.v(), c.v());
			
			G.INT8.bitNot().call(a, c);
			assertEquals(~a.v(), c.v());
			
			for (int p = 0; p < 8; p++) {
				
				G.INT8.bitShiftLeft().call(p, a, c);
				assertEquals((byte)(a.v() << p), c.v());
				
				G.INT8.bitShiftRight().call(p, a, c);
				assertEquals((byte)(a.v() >> p), c.v());
				
				G.INT8.bitShiftRightFillZero().call(p, a, c);
				assertEquals((byte)(a.v() >>> p), c.v());

				if (a.v() != 0 || p != 0) {
					byte t = (p == 0) ? 1 : a.v();
					for (int i = 1; i < p; i++) {
						t *= a.v();
					}
					G.INT8.power().call(p, a, c);
					assertEquals(t, c.v());
					b.setV((byte)p);
					G.INT8.pow().call(a, b, c);
					assertEquals(t, c.v());
				}
			}
			
			assertEquals((a.v() & 1) == 0, G.INT8.isEven().call(a));
			
			assertEquals((a.v() & 1) == 1, G.INT8.isOdd().call(a));
			
			assertEquals(a.v()==0, G.INT8.isZero().call(a));

			if (a.v() != Byte.MIN_VALUE) {
				G.INT8.negate().call(a, c);
				assertEquals(-a.v(), c.v());
			}
			
			if (a.v() != Byte.MIN_VALUE) {
				G.INT8.norm().call(a, c);
				assertEquals(Math.abs(a.v()), c.v());
			}
			
			if (a.v() > Byte.MIN_VALUE) {
				G.INT8.pred().call(a, c);
				assertEquals(a.v()-1, c.v());
			}
			
			if (a.v() < Byte.MAX_VALUE) {
				G.INT8.succ().call(a, c);
				assertEquals(a.v()+1, c.v());
			}

			int v = G.INT8.signum().call(a);
			if (a.v() < 0)
				assertEquals(-1, v);
			else if (a.v() > 0)
				assertEquals(1, v);
			else
				assertEquals(0, v);
				
			for (int h = 0; h < 1000; h++) {
				G.INT8.random().call(b);
				
				G.INT8.add().call(a, b, c);
				assertEquals((byte)(a.v()+b.v()), c.v());

				G.INT8.bitAnd().call(a, b, c);
				assertEquals(a.v() & b.v(), c.v());

				G.INT8.bitAndNot().call(a, b, c);
				assertEquals(a.v() & ~b.v(), c.v());

				G.INT8.bitOr().call(a, b, c);
				assertEquals(a.v() | b.v(), c.v());

				G.INT8.bitXor().call(a, b, c);
				assertEquals(a.v() ^ b.v(), c.v());

				if (a.v() < b.v())
					assertEquals(-1, (int) G.INT8.compare().call(a, b));
				else if (a.v() > b.v())
					assertEquals(1, (int) G.INT8.compare().call(a, b));
				else
					assertEquals(0, (int) G.INT8.compare().call(a, b));

				if ((b.v() != 0) && !(a.v() == Byte.MIN_VALUE && b.v() == -1)) {
					
					G.INT8.div().call(a, b, x);
					assertEquals((byte)(a.v()/b.v()), x.v());

					G.INT8.mod().call(a, b, y);
					assertEquals(a.v()%b.v(), y.v());
					
					G.INT8.divMod().call(a, b, c, d);
					assertEquals(x.v(), c.v());
					assertEquals(y.v(), d.v());
				}
				
				assertEquals(a.v()==b.v(), G.INT8.isEqual().call(a, b));
				
				assertEquals(a.v()!=b.v(), G.INT8.isNotEqual().call(a, b));
				
				assertEquals(a.v()<b.v(), G.INT8.isLess().call(a, b));
				
				assertEquals(a.v()<=b.v(), G.INT8.isLessEqual().call(a, b));
				
				assertEquals(a.v()>b.v(), G.INT8.isGreater().call(a, b));
				
				assertEquals(a.v()>=b.v(), G.INT8.isGreaterEqual().call(a, b));

				G.INT8.max().call(a, b, c);
				assertEquals((a.v()>b.v() ? a.v() : b.v()), c.v());
				
				G.INT8.min().call(a, b, c);
				assertEquals((a.v()<b.v() ? a.v() : b.v()), c.v());

				G.INT8.multiply().call(a, b, c);
				assertEquals((byte)(a.v()*b.v()), c.v());

				if (a.v() != 0 && b.v() > 0) {
					G.INT8.pow().call(a, b, c);
					byte t = a.v();
					for (int i = 1; i < b.v(); i++) {
						t *= a.v();
					}
					assertEquals(t,c.v());
				}

				G.INT8.scale().call(a, b, c);
				assertEquals((byte)(a.v()*b.v()), c.v());

				G.INT8.subtract().call(a, b, c);
				assertEquals((byte)(a.v()-b.v()), c.v());

			}
		}

		// tested as an algorithm elsewhere
		G.INT8.gcd();
		G.INT8.lcm();
	}
	
	@Test
	public void rollover() {
		SignedInt8Member num = G.INT8.construct();
		for (int offset : new int[] {0, 256, 512, 768, 1024, 1280, -256, -512, -768, -1024, -1280}) {
			for (int i = -128; i < 128; i++) {
				num.setV(offset + i);
				assertEquals(i, num.v());
			}
		}
	}
}
