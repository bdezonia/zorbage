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
package nom.bdezonia.zorbage.storage.jdbc;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.storage.coder.CharCoder;
import nom.bdezonia.zorbage.type.float64.quaternion.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;
import nom.bdezonia.zorbage.storage.jdbc.JdbcStorageCharacter;
/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestJdbc {

	@Test
	public void test1() {
		
		try {
			Connection conn = getConnection();

			JdbcStorageFloat64<QuaternionFloat64Member> storage = new JdbcStorageFloat64<QuaternionFloat64Member>(50, G.QDBL.construct(), conn);
			
			assertEquals(50, storage.size());
			
			QuaternionFloat64Member value = G.QDBL.construct();

			for (long i = 0; i < storage.size(); i++) {
				value.setR(-1000);
				value.setI(-1000);
				value.setJ(-1000);
				value.setK(-1000);
				storage.get(i, value);
				assertEquals(0, value.r(), 0);
				assertEquals(0, value.i(), 0);
				assertEquals(0, value.j(), 0);
				assertEquals(0, value.k(), 0);
			}

			for (long i = 0; i < storage.size(); i++) {
				value.setR(i+4);
				value.setI(i+5);
				value.setJ(i+6);
				value.setK(i+7);
				storage.set(i, value);
			}

			for (long i = 0; i < storage.size(); i++) {
				value.setR(0);
				value.setI(0);
				value.setJ(0);
				value.setK(0);
				storage.get(i, value);
				assertEquals(i+4, value.r(), 0);
				assertEquals(i+5, value.i(), 0);
				assertEquals(i+6, value.j(), 0);
				assertEquals(i+7, value.k(), 0);
			}
			
			JdbcStorageFloat64<QuaternionFloat64Member> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.setR(0);
				value.setI(0);
				value.setJ(0);
				value.setK(0);
				storage2.get(i, value);
				assertEquals(i+4, value.r(), 0);
				assertEquals(i+5, value.i(), 0);
				assertEquals(i+6, value.j(), 0);
				assertEquals(i+7, value.k(), 0);
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void test2() {
		
		try {
			Connection conn = getConnection();
			
			JdbcStorageSignedInt16<SignedInt16Member> storage = new JdbcStorageSignedInt16<SignedInt16Member>(50, G.INT16.construct(), conn);
			
			assertEquals(50, storage.size());
			
			SignedInt16Member value = G.INT16.construct();
			for (long i = 0; i < storage.size(); i++) {
				value.setV(-1);
				storage.get(i, value);
				assertEquals(0, value.v());
			}
			for (long i = 0; i < storage.size(); i++) {
				value.setV((short) i);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.setV(-1);
				storage.get(i, value);
				assertEquals(i, value.v());
			}
			
			JdbcStorageSignedInt16<SignedInt16Member> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.setV(-1);
				storage2.get(i, value);
				assertEquals(i, value.v());
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void test3() {
		try {
			Connection conn = getConnection();
			
			class Chars implements CharCoder, Allocatable<Chars> {

				char a = ' ', b = ' ', c = ' ';
				
				@Override
				public int charCount() {
					return 3;
				}

				@Override
				public void fromCharArray(char[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toCharArray(char[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Chars allocate() {
					return new Chars();
				}
				
			}
			
			JdbcStorageCharacter<Chars> storage = new JdbcStorageCharacter<Chars>(50, new Chars(), conn);
			
			assertEquals(50, storage.size());
			
			Chars value = new Chars();
			for (long i = 0; i < storage.size(); i++) {
				value.a = 'a';
				value.b = 'b';
				value.c = 'c';
				storage.get(i, value);
				assertEquals(' ', value.a);
				assertEquals(' ', value.b);
				assertEquals(' ', value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = (char) (32+i+0);
				value.b = (char) (32+i+1);
				value.c = (char) (32+i+2);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = ' ';
				value.b = ' ';
				value.c = ' ';
				storage.get(i, value);
				assertEquals((char)(32+i+0), value.a);
				assertEquals((char)(32+i+1), value.b);
				assertEquals((char)(32+i+2), value.c);
			}
			
			JdbcStorageCharacter<Chars> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = ' ';
				value.b = ' ';
				value.c = ' ';
				storage2.get(i, value);
				assertEquals((char)(32+i+0), value.a);
				assertEquals((char)(32+i+1), value.b);
				assertEquals((char)(32+i+2), value.c);
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test
	public void test4() {
		try {
			Connection conn = getConnection();
			
			class Bools implements BooleanCoder, Allocatable<Bools> {

				boolean a, b, c;
				
				@Override
				public int booleanCount() {
					return 3;
				}

				@Override
				public void fromBooleanArray(boolean[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toBooleanArray(boolean[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Bools allocate() {
					return new Bools();
				}
				
			}
			
			JdbcStorageBoolean<Bools> storage = new JdbcStorageBoolean<Bools>(50, new Bools(), conn);
			
			assertEquals(50, storage.size());
			
			Bools value = new Bools();
			for (long i = 0; i < storage.size(); i++) {
				value.a = true;
				value.b = true;
				value.c = true;
				storage.get(i, value);
				assertEquals(false, value.a);
				assertEquals(false, value.b);
				assertEquals(false, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = false;
				value.b = true;
				value.c = true;
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = true;
				value.b = false;
				value.c = false;
				storage.get(i, value);
				assertEquals(false, value.a);
				assertEquals(true, value.b);
				assertEquals(true, value.c);
			}
			
			JdbcStorageBoolean<Bools> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = true;
				value.b = false;
				value.c = false;
				storage2.get(i, value);
				assertEquals(false, value.a);
				assertEquals(true, value.b);
				assertEquals(true, value.c);
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test
	public void test5() {
		try {
			Connection conn = getConnection();
			
			class BigDs implements BigDecimalCoder, Allocatable<BigDs> {

				BigDecimal a = BigDecimal.ZERO, b = BigDecimal.ZERO, c = BigDecimal.ZERO;
				
				@Override
				public int bigDecimalCount() {
					return 3;
				}

				@Override
				public void fromBigDecimalArray(BigDecimal[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toBigDecimalArray(BigDecimal[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public BigDs allocate() {
					return new BigDs();
				}
				
			}
			
			JdbcStorageBigDecimal<BigDs> storage = new JdbcStorageBigDecimal<BigDs>(50, new BigDs(), conn);
			
			assertEquals(50, storage.size());
			
			BigDs value = new BigDs();
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigDecimal.valueOf(-1);
				value.b = BigDecimal.valueOf(-1);
				value.c = BigDecimal.valueOf(-1);
				storage.get(i, value);
				assertEquals(0, BigDecimal.ZERO.compareTo(value.a));
				assertEquals(0, BigDecimal.ZERO.compareTo(value.b));
				assertEquals(0, BigDecimal.ZERO.compareTo(value.c));
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigDecimal.valueOf(i+0+0.25);
				value.b = BigDecimal.valueOf(i+1+0.5);
				value.c = BigDecimal.valueOf(i+2+0.8);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigDecimal.valueOf(-1);
				value.b = BigDecimal.valueOf(-1);
				value.c = BigDecimal.valueOf(-1);
				storage.get(i, value);
				assertEquals(0, BigDecimal.valueOf(i+0+0.25).compareTo(value.a));
				assertEquals(0, BigDecimal.valueOf(i+1+0.5).compareTo(value.b));
				assertEquals(0, BigDecimal.valueOf(i+2+0.8).compareTo(value.c));
			}
			
			JdbcStorageBigDecimal<BigDs> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = BigDecimal.valueOf(-1);
				value.b = BigDecimal.valueOf(-1);
				value.c = BigDecimal.valueOf(-1);
				storage2.get(i, value);
				assertEquals(0, BigDecimal.valueOf(i+0+0.25).compareTo(value.a));
				assertEquals(0, BigDecimal.valueOf(i+1+0.5).compareTo(value.b));
				assertEquals(0, BigDecimal.valueOf(i+2+0.8).compareTo(value.c));
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test
	public void test6() {
		try {
			Connection conn = getConnection();
			
			class BigIs implements BigIntegerCoder, Allocatable<BigIs> {

				BigInteger a = BigInteger.ZERO, b = BigInteger.ZERO, c = BigInteger.ZERO;
				
				@Override
				public int bigIntegerCount() {
					return 3;
				}

				@Override
				public void fromBigIntegerArray(BigInteger[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toBigIntegerArray(BigInteger[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public BigIs allocate() {
					return new BigIs();
				}
				
			}
			
			JdbcStorageBigInteger<BigIs> storage = new JdbcStorageBigInteger<BigIs>(50, new BigIs(), conn);
			
			assertEquals(50, storage.size());
			
			BigIs value = new BigIs();
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigInteger.valueOf(-1);
				value.b = BigInteger.valueOf(-1);
				value.c = BigInteger.valueOf(-1);
				storage.get(i, value);
				assertEquals(BigInteger.ZERO, value.a);
				assertEquals(BigInteger.ZERO, value.b);
				assertEquals(BigInteger.ZERO, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+0));
				value.b = new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+1));
				value.c = new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+2));
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigInteger.valueOf(-1);
				value.b = BigInteger.valueOf(-1);
				value.c = BigInteger.valueOf(-1);
				storage.get(i, value);
				assertEquals(new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+0)), value.a);
				assertEquals(new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+1)), value.b);
				assertEquals(new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+2)), value.c);
			}
			
			JdbcStorageBigInteger<BigIs> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = BigInteger.valueOf(-1);
				value.b = BigInteger.valueOf(-1);
				value.c = BigInteger.valueOf(-1);
				storage2.get(i, value);
				assertEquals(new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+0)), value.a);
				assertEquals(new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+1)), value.b);
				assertEquals(new BigInteger("1234567890123456789012345678901234567890").add(BigInteger.valueOf(i+2)), value.c);
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private Connection getConnection() throws Exception {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/zorbage?serverTimezone=UTC&user=zorbage&password=zorbage");

	}
	
}
