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
	public void testEncodeInts() {

		Float128Member tol = new Float128Member(new BigDecimal("0.00000000000000000000000000000000000001"));
		
		Float128Member num = new Float128Member();
		
		byte[] arr = new byte[16];
		for (int i = -2000; i <= 2000; i++) {
			
			BigDecimal input = BigDecimal.valueOf(i); 
			
			num.setV(input);
			
			num.encode(arr, 0);
			
			num.decode(arr, 0);
			
			// System.out.println(input + " " + num);
			
			assertTrue(num.v().subtract(input).abs().compareTo(tol.v()) <= 0);
		}
	}
	
	@Test
	public void testEncodeFractions() {

		Float128Member tol = new Float128Member(new BigDecimal("0.00000000000000000000000000000000000001"));
		
		Float128Member num = new Float128Member();

		byte[] arr = new byte[16];
		for (int i = 0; i < 1000; i++) {
			
			G.QUAD.random().call(num);

			// test half the numbers as negative
			
			if ((i & 1) == 1)
				G.QUAD.negate().call(num, num);

			BigDecimal before = num.v();
			
			num.encode(arr, 0);
			
			num.decode(arr, 0);
			
			// System.out.println(before + " " + num);
			
			assertTrue(num.v().subtract(before).abs().compareTo(tol.v()) <= 0);
		}
	}
	
	@Test
	public void testEncodeSubnormal() {

		// TODO : write example code around this number
		
		//BigDecimal tol = new BigDecimal("0.00000000000000000000000000000000000001");

		BigDecimal v = new BigDecimal("2.3476754706213756379937664069229480929E-4933");
		
		assertTrue(v.compareTo(Float128Member.MIN_SUBNORMAL) >= 0);
		assertTrue(v.compareTo(Float128Member.MAX_SUBNORMAL) <= 0);
	}
	
	@Test
	public void testPowersOf2() {
		
		Float128Member num = new Float128Member();
		
		BigDecimal start;
		
		int count = 55; // NOTE 56 or higher fails in the divide case. Due to 40 digit roundoff?
		
		byte[] arr1 = new byte[16];
		
		byte[] arr2 = new byte[16];

		// positive num > 1
		
		start = BigDecimal.ONE;

		for (int i = 0; i < count; i++) {
			
			start = start.multiply(BigDecimal.valueOf(2));
			
			num.setV(start);
			
			num.encode(arr1, 0);
			
			num.decode(arr1, 0);
			
			num.encode(arr2, 0);
			
			for (int k = 0; k < 16; k++) {
				assertEquals(arr1[k], arr2[k]);
			}
		}
		
		// positive 0 < num <= 1
		
		start = BigDecimal.ONE;

		for (int i = 0; i < count; i++) {
			
			start = start.divide(BigDecimal.valueOf(2));
			
			num.setV(start);
			
			num.encode(arr1, 0);
			
			num.decode(arr1, 0);
			
			num.encode(arr2, 0);
			
			for (int k = 0; k < 16; k++) {
				assertEquals(arr1[k], arr2[k]);
			}
		}

		// negative num < -1
		
		start = BigDecimal.ONE.negate();

		for (int i = 0; i < count; i++) {
			
			start = start.multiply(BigDecimal.valueOf(2));
			
			num.setV(start);
			
			num.encode(arr1, 0);
			
			num.decode(arr1, 0);
			
			num.encode(arr2, 0);
			
			for (int k = 0; k < 16; k++) {
				assertEquals(arr1[k], arr2[k]);
			}
		}
		
		// negative 0 < num <= 1
		
		start = BigDecimal.ONE.negate();

		for (int i = 0; i < count; i++) {
			
			start = start.divide(BigDecimal.valueOf(2));
			
			num.setV(start);
			
			num.encode(arr1, 0);
			
			num.decode(arr1, 0);
			
			num.encode(arr2, 0);
			
			for (int k = 0; k < 16; k++) {
				assertEquals(arr1[k], arr2[k]);
			}
		}

	}
	
	@Test
	public void testOneCase() {
		
		Float128Member num = new Float128Member();
		
		BigDecimal start = new BigDecimal("-0.9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999983189484284439532468686610913391236987");
		
		byte[] arr1 = new byte[16];
		
		byte[] arr2 = new byte[16];

		num.setV(start);
		
		//System.out.println(num);
		
		num.encode(arr1, 0);
		
		num.decode(arr1, 0);
		
		//System.out.println(num);
		
		num.encode(arr2, 0);
		
		for (int k = 0; k < 16; k++) {
			assertEquals(arr1[k], arr2[k]);
		}
	}
}
