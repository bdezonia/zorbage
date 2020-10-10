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
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.storage.coder.IntCoder;
import nom.bdezonia.zorbage.storage.coder.LongCoder;
import nom.bdezonia.zorbage.storage.coder.ShortCoder;

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
			
			class Bytes implements ByteCoder, Allocatable<Bytes> {

				byte a, b, c;
				
				@Override
				public int byteCount() {
					return 3;
				}

				@Override
				public void fromByteArray(byte[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toByteArray(byte[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Bytes allocate() {
					return new Bytes();
				}
				
			}
			
			JdbcStorageSignedInt8<Bytes> storage = new JdbcStorageSignedInt8<Bytes>(conn, new Bytes(), 50);
			
			assertEquals(50, storage.size());
			
			Bytes value = new Bytes();
			for (long i = 0; i < storage.size(); i++) {
				value.a = (byte) -1;
				value.b = (byte) -1;
				value.c = (byte) -1;
				storage.get(i, value);
				assertEquals(0, value.a);
				assertEquals(0, value.b);
				assertEquals(0, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = (byte) (i+0);
				value.b = (byte) (i+1);
				value.c = (byte) (i+2);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = (byte) 0;
				value.b = (byte) 0;
				value.c = (byte) 0;
				storage.get(i, value);
				assertEquals((byte) (i+0), value.a);
				assertEquals((byte) (i+1), value.b);
				assertEquals((byte) (i+2), value.c);
			}
			
			JdbcStorageSignedInt8<Bytes> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = (byte) 0;
				value.b = (byte) 0;
				value.c = (byte) 0;
				storage2.get(i, value);
				assertEquals((byte) (i+0), value.a);
				assertEquals((byte) (i+1), value.b);
				assertEquals((byte) (i+2), value.c);
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
			
			class Shorts implements ShortCoder, Allocatable<Shorts> {

				short a, b, c;
				
				@Override
				public int shortCount() {
					return 3;
				}

				@Override
				public void fromShortArray(short[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toShortArray(short[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Shorts allocate() {
					return new Shorts();
				}
				
			}
			
			JdbcStorageSignedInt16<Shorts> storage = new JdbcStorageSignedInt16<Shorts>(conn, new Shorts(), 50);
			
			assertEquals(50, storage.size());
			
			Shorts value = new Shorts();
			for (long i = 0; i < storage.size(); i++) {
				value.a = -1;
				value.b = -1;
				value.c = -1;
				storage.get(i, value);
				assertEquals(0, value.a);
				assertEquals(0, value.b);
				assertEquals(0, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = (short) (i+0);
				value.b = (short) (i+1);
				value.c = (short) (i+2);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage.get(i, value);
				assertEquals(i+0, value.a);
				assertEquals(i+1, value.b);
				assertEquals(i+2, value.c);
			}
			
			JdbcStorageSignedInt16<Shorts> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage2.get(i, value);
				assertEquals(i+0, value.a);
				assertEquals(i+1, value.b);
				assertEquals(i+2, value.c);
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
			
			class Ints implements IntCoder, Allocatable<Ints> {

				int a, b, c;
				
				@Override
				public int intCount() {
					return 3;
				}

				@Override
				public void fromIntArray(int[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toIntArray(int[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Ints allocate() {
					return new Ints();
				}
				
			}
			
			JdbcStorageSignedInt32<Ints> storage = new JdbcStorageSignedInt32<Ints>(conn, new Ints(), 50);
			
			assertEquals(50, storage.size());
			
			Ints value = new Ints();
			for (long i = 0; i < storage.size(); i++) {
				value.a = -1;
				value.b = -1;
				value.c = -1;
				storage.get(i, value);
				assertEquals(0, value.a);
				assertEquals(0, value.b);
				assertEquals(0, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = (int) (i+0);
				value.b = (int) (i+1);
				value.c = (int) (i+2);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage.get(i, value);
				assertEquals(i+0, value.a);
				assertEquals(i+1, value.b);
				assertEquals(i+2, value.c);
			}
			
			JdbcStorageSignedInt32<Ints> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage2.get(i, value);
				assertEquals(i+0, value.a);
				assertEquals(i+1, value.b);
				assertEquals(i+2, value.c);
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
			
			class Longs implements LongCoder, Allocatable<Longs> {

				long a, b, c;
				
				@Override
				public int longCount() {
					return 3;
				}

				@Override
				public void fromLongArray(long[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toLongArray(long[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Longs allocate() {
					return new Longs();
				}
				
			}
			
			JdbcStorageSignedInt64<Longs> storage = new JdbcStorageSignedInt64<Longs>(conn, new Longs(), 50);
			
			assertEquals(50, storage.size());
			
			Longs value = new Longs();
			for (long i = 0; i < storage.size(); i++) {
				value.a = -1;
				value.b = -1;
				value.c = -1;
				storage.get(i, value);
				assertEquals(0, value.a);
				assertEquals(0, value.b);
				assertEquals(0, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = i+0;
				value.b = i+1;
				value.c = i+2;
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage.get(i, value);
				assertEquals(i+0, value.a);
				assertEquals(i+1, value.b);
				assertEquals(i+2, value.c);
			}
			
			JdbcStorageSignedInt64<Longs> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage2.get(i, value);
				assertEquals(i+0, value.a);
				assertEquals(i+1, value.b);
				assertEquals(i+2, value.c);
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
			
			class Floats implements FloatCoder, Allocatable<Floats> {

				float a, b, c;
				
				@Override
				public int floatCount() {
					return 3;
				}

				@Override
				public void fromFloatArray(float[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toFloatArray(float[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Floats allocate() {
					return new Floats();
				}
				
			}
			
			JdbcStorageFloat32<Floats> storage = new JdbcStorageFloat32<Floats>(conn, new Floats(), 50);
			
			assertEquals(50, storage.size());
			
			Floats value = new Floats();
			for (long i = 0; i < storage.size(); i++) {
				value.a = -1;
				value.b = -1;
				value.c = -1;
				storage.get(i, value);
				assertEquals(0, value.a, 0);
				assertEquals(0, value.b, 0);
				assertEquals(0, value.c, 0);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = i+0+0.25f;
				value.b = i+1+0.5f;
				value.c = i+2+0.75f;
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage.get(i, value);
				assertEquals(i+0+0.25f, value.a, 0);
				assertEquals(i+1+0.5f, value.b, 0);
				assertEquals(i+2+0.75f, value.c, 0);
			}
			
			JdbcStorageFloat32<Floats> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage2.get(i, value);
				assertEquals(i+0+0.25f, value.a, 0);
				assertEquals(i+1+0.5f, value.b, 0);
				assertEquals(i+2+0.75f, value.c, 0);
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
			
			class Doubles implements DoubleCoder, Allocatable<Doubles> {

				double a, b, c;
				
				@Override
				public int doubleCount() {
					return 3;
				}

				@Override
				public void fromDoubleArray(double[] arr, int index) {
					a = arr[index+0];
					b = arr[index+1];
					c = arr[index+2];
				}

				@Override
				public void toDoubleArray(double[] arr, int index) {
					arr[index+0] = a;
					arr[index+1] = b;
					arr[index+2] = c;
				}

				@Override
				public Doubles allocate() {
					return new Doubles();
				}
				
			}
			
			JdbcStorageFloat64<Doubles> storage = new JdbcStorageFloat64<Doubles>(conn, new Doubles(), 50);
			
			assertEquals(50, storage.size());
			
			Doubles value = new Doubles();
			for (long i = 0; i < storage.size(); i++) {
				value.a = -1;
				value.b = -1;
				value.c = -1;
				storage.get(i, value);
				assertEquals(0, value.a, 0);
				assertEquals(0, value.b, 0);
				assertEquals(0, value.c, 0);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = i+0+0.25;
				value.b = i+1+0.5;
				value.c = i+2+0.75;
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage.get(i, value);
				assertEquals(i+0+0.25, value.a, 0);
				assertEquals(i+1+0.5, value.b, 0);
				assertEquals(i+2+0.75, value.c, 0);
			}
			
			JdbcStorageFloat64<Doubles> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = 0;
				value.b = 0;
				value.c = 0;
				storage2.get(i, value);
				assertEquals(i+0.25, value.a, 0);
				assertEquals(i+1.5, value.b, 0);
				assertEquals(i+2.75, value.c, 0);
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void test7() {
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
			
			JdbcStorageBoolean<Bools> storage = new JdbcStorageBoolean<Bools>(conn, new Bools(), 50);
			
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
	public void test8() {
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
			
			JdbcStorageBigDecimal<BigDs> storage = new JdbcStorageBigDecimal<BigDs>(conn, new BigDs(), 50);
			
			assertEquals(50, storage.size());
			
			BigDecimal offset = new BigDecimal("12345678901234567890123456789012345.00000000000025");
			
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
				value.a = offset.add(BigDecimal.valueOf(i+0+0.25));
				value.b = offset.add(BigDecimal.valueOf(i+1+0.5));
				value.c = offset.add(BigDecimal.valueOf(i+2+0.8));
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigDecimal.ZERO;
				value.b = BigDecimal.ZERO;
				value.c = BigDecimal.ZERO;
				storage.get(i, value);
				assertEquals(0, offset.add(BigDecimal.valueOf(i+0+0.25)).compareTo(value.a));
				assertEquals(0, offset.add(BigDecimal.valueOf(i+1+0.5)).compareTo(value.b));
				assertEquals(0, offset.add(BigDecimal.valueOf(i+2+0.8)).compareTo(value.c));
			}
			
			JdbcStorageBigDecimal<BigDs> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = BigDecimal.ZERO;
				value.b = BigDecimal.ZERO;
				value.c = BigDecimal.ZERO;
				storage2.get(i, value);
				assertEquals(0, offset.add(BigDecimal.valueOf(i+0+0.25)).compareTo(value.a));
				assertEquals(0, offset.add(BigDecimal.valueOf(i+1+0.5)).compareTo(value.b));
				assertEquals(0, offset.add(BigDecimal.valueOf(i+2+0.8)).compareTo(value.c));
			}
			
			storage.cleanup();
			storage2.cleanup();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test
	public void test9() {
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
			
			JdbcStorageBigInteger<BigIs> storage = new JdbcStorageBigInteger<BigIs>(conn, new BigIs(), 50);
			
			assertEquals(50, storage.size());
			
			BigInteger offset = new BigInteger("1234567890123456789012345678901234567890");
			
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
				value.a = offset.add(BigInteger.valueOf(i+0));
				value.b = offset.add(BigInteger.valueOf(i+1));
				value.c = offset.add(BigInteger.valueOf(i+2));
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				value.a = BigInteger.ZERO;
				value.b = BigInteger.ZERO;
				value.c = BigInteger.ZERO;
				storage.get(i, value);
				assertEquals(offset.add(BigInteger.valueOf(i+0)), value.a);
				assertEquals(offset.add(BigInteger.valueOf(i+1)), value.b);
				assertEquals(offset.add(BigInteger.valueOf(i+2)), value.c);
			}
			
			JdbcStorageBigInteger<BigIs> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				value.a = BigInteger.ZERO;
				value.b = BigInteger.ZERO;
				value.c = BigInteger.ZERO;
				storage2.get(i, value);
				assertEquals(offset.add(BigInteger.valueOf(i+0)), value.a);
				assertEquals(offset.add(BigInteger.valueOf(i+1)), value.b);
				assertEquals(offset.add(BigInteger.valueOf(i+2)), value.c);
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
