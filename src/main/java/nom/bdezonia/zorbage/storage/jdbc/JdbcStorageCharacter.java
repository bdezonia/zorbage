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
package nom.bdezonia.zorbage.storage.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.CharCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorageCharacter<U extends CharCoder & Allocatable<U>>
	extends AbstractJdbcStorage<U>
	implements IndexedDataSource<U>, Allocatable<JdbcStorageCharacter<U>>
{
	// string passed to createTable based on info from:
	//   https://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
	
	public JdbcStorageCharacter(Connection conn, U type, long size) {
		super(size, type, conn);
		createTable(conn, tableName, "CHAR", type.charCount(), size);
		zeroFill(type.charCount());
	}

	public JdbcStorageCharacter(JdbcStorageCharacter<U> other) {
		super(other.size, other.type, other.conn);
		createTable(conn, tableName, "CHAR", type.charCount(), size);
		copyTableToTable(conn, other.tableName, tableName);
	}

	@Override
	public JdbcStorageCharacter<U> duplicate() {
		return new JdbcStorageCharacter<U>(this);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		char[] arr = tmpSpace.get();
		value.toCharArray(arr, 0);
		String sql = setHelper(index, arr.length);
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			for (int i = 0; i < arr.length; i++) {
				statement.setString(i+1, Character.toString(arr[i]) );
			}
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		Tuple2<Statement,ResultSet> result = getHelper(index, type.charCount());
		char[] arr = tmpSpace.get();
		try {
			result.b().next();
			for (int i = 0; i < arr.length; i++) {
				String st = result.b().getString(i+1);
				if (st == null || st.length() == 0)
					arr[i] = ' ';
				else
					arr[i] = st.charAt(0);
			}
			value.fromCharArray(arr, 0);
			result.a().close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Thread local variable containing each thread's temp array
	private final ThreadLocal<char[]> tmpSpace =
		new ThreadLocal<char[]>() {
			@Override protected char[] initialValue() {
				return new char[type.charCount()];
			}
		};

	@Override
	public JdbcStorageCharacter<U> allocate() {
		return new JdbcStorageCharacter<U>(conn, type, size());
	}

	@Override
	public StorageConstruction storageType() {
		// There is no exact match for our type. But if someone has a large JDBC backed structure
		// that we want to derive an allocation from then a VIRTUAL structure might be best. This
		// return value reflects that idea and kind of generalizes the idea of how the data is
		// stored in actuality.
		return StorageConstruction.MEM_VIRTUAL;
	}

	@Override
	String zeroValueAsString() {
		return "' '";  // For a character we will treat a space as the zero value. Maybe /NUL would be better
					   //   but experiments seemed to uncover problems doing this. 
	}
	
}
