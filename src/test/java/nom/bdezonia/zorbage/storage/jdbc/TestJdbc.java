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

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.G;
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

			JdbcStorageFloat64<QuaternionFloat64Member> storage = new JdbcStorageFloat64<QuaternionFloat64Member>(19, G.QDBL.construct(), conn);
			
			QuaternionFloat64Member value = G.QDBL.construct();

			for (long i = 0; i < storage.size(); i++) {
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
				storage.get(i, value);
				assertEquals(i+4, value.r(), 0);
				assertEquals(i+5, value.i(), 0);
				assertEquals(i+6, value.j(), 0);
				assertEquals(i+7, value.k(), 0);
			}
			
			storage.cleanup();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void test2() {
		
		try {
			Connection conn = getConnection();
			
			JdbcStorageSignedInt16<SignedInt16Member> storage = new JdbcStorageSignedInt16<SignedInt16Member>(19, G.INT16.construct(), conn);
			
			SignedInt16Member value = G.INT16.construct();
			for (long i = 0; i < storage.size(); i++) {
				storage.get(i, value);
				assertEquals(0, value.v());
			}
			for (long i = 0; i < storage.size(); i++) {
				value.setV((short) i);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				storage.get(i, value);
				assertEquals(i, value.v());
			}
			
			JdbcStorageSignedInt16<SignedInt16Member> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
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

				char a, b, c;
				
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
			
			JdbcStorageCharacter<Chars> storage = new JdbcStorageCharacter<Chars>(512, new Chars(), conn);
			
			Chars value = new Chars();
			for (long i = 0; i < storage.size(); i++) {
				storage.get(i, value);
				assertEquals(0, value.a);
				assertEquals(0, value.b);
				assertEquals(0, value.c);
			}
			for (long i = 0; i < storage.size(); i++) {
				value.a = (char) (i+0);
				value.b = (char) (i+1);
				value.c = (char) (i+2);
				storage.set(i, value);
			}
			
			for (long i = 0; i < storage.size(); i++) {
				storage.get(i, value);
				assertEquals((char)(i+0), value.a);
				assertEquals((char)(i+1), value.b);
				assertEquals((char)(i+2), value.c);
			}
			
			JdbcStorageCharacter<Chars> storage2 = storage.duplicate();
			
			assertEquals(storage.size(), storage2.size());
			
			for (long i = 0; i < storage2.size(); i++) {
				storage2.get(i, value);
				assertEquals((char)(i+0), value.a);
				assertEquals((char)(i+1), value.b);
				assertEquals((char)(i+2), value.c);
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
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/zorbage?user=root&password=root");

	}
	
}
