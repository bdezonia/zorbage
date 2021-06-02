/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.real.float128;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat128Member {
	
	// This site is invaluable for making these tests;
	//   http://weitz.de/ieee/
	
	@Test
	public void test() {
/*		
		System.out.println(Math.pow(1.1, Double.POSITIVE_INFINITY));
		
		Float128Member a = G.QUAD.construct();
		Float128Member b = G.QUAD.construct();
		Float128Member c = G.QUAD.construct();

		byte[] classes = new byte[] {Float128Member.NEGINF, Float128Member.NEGZERO, Float128Member.NORMAL, Float128Member.POSZERO, Float128Member.POSINF, Float128Member.NAN};
		BigDecimal[] nums = new BigDecimal[] {BigDecimal.valueOf(-25), BigDecimal.valueOf(-1.1), BigDecimal.valueOf(-1), BigDecimal.valueOf(-0.9), BigDecimal.valueOf(0.9), BigDecimal.valueOf(1), BigDecimal.valueOf(1.1), BigDecimal.valueOf(25)};
		
		for (int c1 = 0; c1 < classes.length; c1++) {
			a.num = BigDecimal.ZERO;
			a.classification = classes[c1];
			if (!a.isNormal()) {
				for (int c2 = 0; c2 < classes.length; c2++) {
					b.num = BigDecimal.ZERO;
					b.classification = classes[c2];
					if (!b.isNormal()) {
						System.out.println(" 1) ac bc "+a.classification+" "+b.classification);
						G.QUAD.pow().call(a, b, c);
					}
					else { // b is normal
						for (int w = 0; w < nums.length; w++) {
							b.num = nums[w];
							System.out.println(" 2) ac bc bnum "+a.classification+" "+b.classification+" "+b.num);
							G.QUAD.pow().call(a, b, c);
						}
					}
				}
			}
			else { // a is normal
				for (int v = 0; v < nums.length; v++) {
					a.num = nums[v];
					for (int c2 = 0; c2 < classes.length; c2++) {
						b.num = BigDecimal.ZERO;
						b.classification = classes[c2];
						if (!b.isNormal()) {
								System.out.println(" 3) ac bc anum "+a.classification+" "+b.classification+" "+a.num);
								G.QUAD.pow().call(a, b, c);
						}
						else { // b is normal
							for (int w = 0; w < nums.length; w++) {
								b.num = nums[w];
								System.out.println(" 4) ac bc anum bnum "+a.classification+" "+b.classification+" "+a.num+" "+b.num);
								G.QUAD.pow().call(a, b, c);
							}
						}
					}
				}
			}
		}
		
*/		
		byte[] arr = new byte[17];
		
		Float128Member val = G.QUAD.construct();
		
		val.setNan();
		
		val.encode(arr, 0);

		assertEquals(3, arr[0]);
		assertEquals(0x7f, arr[16] & 0xff);
		assertEquals(0xff, arr[15] & 0xff);
		assertEquals(1, arr[14]);
		assertEquals(1, arr[13]);
		assertEquals(1, arr[12]);
		assertEquals(1, arr[11]);
		assertEquals(1, arr[10]);
		assertEquals(1, arr[9]);
		assertEquals(1, arr[8]);
		assertEquals(1, arr[7]);
		assertEquals(1, arr[6]);
		assertEquals(1, arr[5]);
		assertEquals(1, arr[4]);
		assertEquals(1, arr[3]);
		assertEquals(1, arr[2]);
		assertEquals(1, arr[1]);

		val.setNegInf();
		
		val.encode(arr, 0);

		assertEquals(-2, arr[0]);
		assertEquals(0xff, arr[16] & 0xff);
		assertEquals(0xff, arr[15] & 0xff);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(0, arr[1]);

		val.setPosInf();
		
		val.encode(arr, 0);

		assertEquals(2, arr[0]);
		assertEquals(0x7f, arr[16] & 0xff);
		assertEquals(0xff, arr[15] & 0xff);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(0, arr[1]);

		// TODO does my encode/decode turn neg zeroes into pos zeroes?
		
		val.setNegZero();
		
		val.encode(arr, 0);

		assertEquals(-1, arr[0]);
		assertEquals(0x80, arr[16] & 0xff);
		assertEquals(0, arr[15]);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(0, arr[1]);

		val.setPosZero();
		
		val.encode(arr, 0);

		assertEquals(1, arr[0]);
		assertEquals(0, arr[16]);
		assertEquals(0, arr[15]);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(0, arr[1]);
		
		val.setV(BigDecimal.ONE);

		val.encode(arr, 0);

		assertEquals(0, arr[0]);
		assertEquals(0x3f, arr[16] & 0xff);
		assertEquals(0xf0, arr[15] & 0xff);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(0, arr[1]);
		
		val.setV(BigDecimal.ONE.negate());

		val.encode(arr, 0);

		assertEquals(0, arr[0]);
		assertEquals(0xbf, arr[16] & 0xff);
		assertEquals(0xf0, arr[15] & 0xff);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(0, arr[1]);
		
		val.setV(BigDecimal.valueOf(1.5));

		val.encode(arr, 0);

		assertEquals(0, arr[0]);
		assertEquals(0x3f, arr[16] & 0xff);
		assertEquals(0xf8, arr[15] & 0xff);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(1, arr[1]);
		
		val.setV(BigDecimal.valueOf(-1.5));

		val.encode(arr, 0);

		assertEquals(0, arr[0]);
		assertEquals(0xbf, arr[16] & 0xff);
		assertEquals(0xff, arr[15] & 0xff);
		assertEquals(0, arr[14]);
		assertEquals(0, arr[13]);
		assertEquals(0, arr[12]);
		assertEquals(0, arr[11]);
		assertEquals(0, arr[10]);
		assertEquals(0, arr[9]);
		assertEquals(0, arr[8]);
		assertEquals(0, arr[7]);
		assertEquals(0, arr[6]);
		assertEquals(0, arr[5]);
		assertEquals(0, arr[4]);
		assertEquals(0, arr[3]);
		assertEquals(0, arr[2]);
		assertEquals(1, arr[1]);
		
		/*
		IndexedDataSource<Float128Member> storage = Storage.allocate(val, 1);
		
		for (long i = 0; i < storage.size(); i++) {
			storage.set(i, val);
		}
		
		for (long i = 0; i < storage.size(); i++) {
			val.setPosZero();
			storage.get(i, val);
			assertEquals(1.5, val.v().doubleValue(), 0);
		}
		*/
		
	}

}
