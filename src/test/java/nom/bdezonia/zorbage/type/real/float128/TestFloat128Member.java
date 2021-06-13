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
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

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
		byte[] arr = new byte[16];
		
		Float128Member val = G.QUAD.construct();
		
		val.setNan();
		
		val.encode(arr, 0); // 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF

		assertEquals(0x7f, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
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
		assertEquals(1, arr[0]);

		val.setNegInf();
		
		val.encode(arr, 0);  // 0xFFFF0000000000000000000000000000

		assertEquals(0xff, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
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
		assertEquals(0, arr[0]);

		val.setPosInf();
		
		val.encode(arr, 0);  // 0x7FFF0000000000000000000000000000

		assertEquals(0x7f, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
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
		assertEquals(0, arr[0]);

		val.setNegZero();
		
		val.encode(arr, 0);  // 0x80000000000000000000000000000000

		assertEquals(0x80, arr[15] & 0xff);
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
		assertEquals(0, arr[0]);

		val.setPosZero();
		
		val.encode(arr, 0);  // 0x00000000000000000000000000000000

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
		assertEquals(0, arr[0]);
		
		val.setV(BigDecimal.ONE);

		val.encode(arr, 0);  // 0x3FFF0000000000000000000000000000

		assertEquals(0x3f, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
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
		assertEquals(0, arr[0]);
		
		val.setV(BigDecimal.ONE.negate());

		val.encode(arr, 0);

		assertEquals(0xbf, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
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
		assertEquals(0, arr[0]);
		
		val.setV(BigDecimal.valueOf(1.5));

		val.encode(arr, 0);  // 0x3FFF8000000000000000000000000000

		assertEquals(0x3f, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
		assertEquals(0x80, arr[13] & 0xff);
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
		assertEquals(0, arr[0]);
		
		val.setV(BigDecimal.valueOf(-1.5));

		val.encode(arr, 0);  // 0xBFFF8000000000000000000000000000

		assertEquals(0xbf, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
		assertEquals(0x80, arr[13] & 0xff);
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
		assertEquals(0, arr[0]);
		
		val.setV(BigDecimal.valueOf(1.75));

		val.encode(arr, 0);  // 0x3FFFC000000000000000000000000000

		assertEquals(0x3f, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
		assertEquals(0xc0, arr[13] & 0xff);
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
		assertEquals(0, arr[0]);
		
		val.setV(BigDecimal.valueOf(-1.75));

		val.encode(arr, 0);  // 0xBFFFC000000000000000000000000000

		assertEquals(0xbf, arr[15] & 0xff);
		assertEquals(0xff, arr[14] & 0xff);
		assertEquals(0xc0, arr[13] & 0xff);
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
		assertEquals(0, arr[0]);

		val.setV(BigDecimal.valueOf(28.6333302));

		val.encode(arr, 0);  // 0x4003CA221ED9091B31B4ADF21632716C

		assertEquals(0x40, arr[15] & 0xff);
		assertEquals(0x03, arr[14] & 0xff);
		assertEquals(0xca, arr[13] & 0xff);
		assertEquals(0x22, arr[12] & 0xff);
		assertEquals(0x1e, arr[11] & 0xff);
		assertEquals(0xd9, arr[10] & 0xff);
		assertEquals(0x09, arr[9] & 0xff);
		assertEquals(0x1b, arr[8] & 0xff);
		assertEquals(0x31, arr[7] & 0xff);
		assertEquals(0xb4, arr[6] & 0xff);
		assertEquals(0xad, arr[5] & 0xff);
		assertEquals(0xf2, arr[4] & 0xff);
		assertEquals(0x16, arr[3] & 0xff);
		assertEquals(0x32, arr[2] & 0xff);
		assertEquals(0x71, arr[1] & 0xff);
		assertEquals(0x6c, arr[0] & 0xff);

		val.setV(BigDecimal.valueOf(-28.6333302));

		val.encode(arr, 0);  // 0xC003CA221ED9091B31B4ADF21632716C

		assertEquals(0xc0, arr[15] & 0xff);
		assertEquals(0x03, arr[14] & 0xff);
		assertEquals(0xca, arr[13] & 0xff);
		assertEquals(0x22, arr[12] & 0xff);
		assertEquals(0x1e, arr[11] & 0xff);
		assertEquals(0xd9, arr[10] & 0xff);
		assertEquals(0x09, arr[9] & 0xff);
		assertEquals(0x1b, arr[8] & 0xff);
		assertEquals(0x31, arr[7] & 0xff);
		assertEquals(0xb4, arr[6] & 0xff);
		assertEquals(0xad, arr[5] & 0xff);
		assertEquals(0xf2, arr[4] & 0xff);
		assertEquals(0x16, arr[3] & 0xff);
		assertEquals(0x32, arr[2] & 0xff);
		assertEquals(0x71, arr[1] & 0xff);
		assertEquals(0x6c, arr[0] & 0xff);
		
		IndexedDataSource<Float128Member> storage = Storage.allocate(val, 1);
		
		double crazyConstant = 4213.19308583;
		
		val.setV(BigDecimal.valueOf(crazyConstant));

		val.encode(arr, 0);  // 0x400B075316E12AD2BC7AF00B404FFC42

		assertEquals(0x40, arr[15] & 0xff);
		assertEquals(0x0b, arr[14] & 0xff);
		assertEquals(0x07, arr[13] & 0xff);
		assertEquals(0x53, arr[12] & 0xff);
		assertEquals(0x16, arr[11] & 0xff);
		assertEquals(0xe1, arr[10] & 0xff);
		assertEquals(0x2a, arr[9] & 0xff);
		assertEquals(0xd2, arr[8] & 0xff);
		assertEquals(0xbc, arr[7] & 0xff);
		assertEquals(0x7a, arr[6] & 0xff);
		assertEquals(0xf0, arr[5] & 0xff);
		assertEquals(0x0b, arr[4] & 0xff);
		assertEquals(0x40, arr[3] & 0xff);
		assertEquals(0x4f, arr[2] & 0xff);
		assertEquals(0xfc, arr[1] & 0xff);
		assertEquals(0x42, arr[0] & 0xff);
		
		for (long i = 0; i < storage.size(); i++) {
			storage.set(i, val);
		}
		
		for (long i = 0; i < storage.size(); i++) {
			val.setPosZero();
			storage.get(i, val);
			assertEquals(crazyConstant, val.v().doubleValue(), 0.000000001);
		}
		
	}
	
	@Test
	public void testPossibleFailures() {

		Float128Member tol = new Float128Member(BigDecimal.valueOf(0.00000000000001));
		
		Float128Member num = new Float128Member();
		
		int[] ints = new int[]{1,7,4,1,2,4,8,3,3};

		byte[] arr = new byte[16];
		for (int i = 0; i < ints.length; i++) {
			
			BigDecimal input = BigDecimal.valueOf(ints[i]); 
			
			num.setV(input);
			
			num.encode(arr, 0);
			
			num.decode(arr, 0);
			
			System.out.println(input);
			
			assertTrue(num.v().subtract(input).abs().compareTo(tol.v()) <= 0);
		}
	}
}
