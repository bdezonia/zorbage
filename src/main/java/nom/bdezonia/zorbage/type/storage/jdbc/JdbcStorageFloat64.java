/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorageFloat64<U extends DoubleCoder & Allocatable<U>>
	implements IndexedDataSource<JdbcStorageFloat64<U>, U>
{
	private final U type;
	private final long size;
	private final String dbName;
	private final String tableName;
	private final Connection conn;
	
	public JdbcStorageFloat64(long size, U type, Connection conn, String dbName) {
		if (size < 0)
			throw new IllegalArgumentException("jdbc list size must be >= 0");
		this.size = size;
		this.type = type.allocate();
		this.conn = conn;
		this.dbName = dbName;
		this.tableName = newTableName();
		createTable();
	}

	@Override
	public JdbcStorageFloat64<U> duplicate() {
		JdbcStorageFloat64<U> newContainer = new JdbcStorageFloat64<U>(size, type, conn, dbName);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * INTO ");
		sb.append(dbName);
		sb.append(".");
		sb.append(newContainer.tableName);
		sb.append(" FROM ");
		sb.append(dbName);
		sb.append(".");
		sb.append(tableName);
		sb.append(" WHERE 1 = 1;");
		String sql = sb.toString();
		try {
			Statement statement = conn.createStatement();
			statement.executeQuery(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return newContainer;
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		double[] arr = tmpSpace.get();
		value.toDoubleArray(arr, 0);
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(dbName);
		sb.append(".");
		sb.append(tableName);
		sb.append(" SET ");
		for (int i = 0; i < value.doubleCount(); i++) {
			sb.append("v");
			sb.append(i);
			sb.append(" = ");
			sb.append(arr[i]);
			if (i != value.doubleCount()-1)
				sb.append(",");
		}
		sb.append(" WHERE id = ");
		sb.append(index);
		sb.append(";");
		String sql = sb.toString();
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (int i = 0; i < value.doubleCount(); i++) {
			sb.append("v");
			sb.append(i);
			if (i != value.doubleCount()-1)
				sb.append(",");
		}
		sb.append(" FROM ");
		sb.append(dbName);
		sb.append(".");
		sb.append(tableName);
		sb.append(" WHERE id = ");
		sb.append(index);
		sb.append(";");
		String sql = sb.toString();
		double[] arr = tmpSpace.get();
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			for (int i = 0; i < arr.length; i++) {
				arr[i] = result.getDouble(i);
			}
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		value.fromDoubleArray(arr, 0);
	}

	@Override
	public long size() {
		return size;
	}

	public void cleanup() {
		Statement statement;
		try {
			statement = conn.createStatement();
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE ");
			sb.append(dbName);
			sb.append(".");
			sb.append(tableName);
			sb.append(";");
			String sql = sb.toString();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private char randomChar(Random rng) {
		return (char)(rng.nextInt('z' - 'a') + 'a');
	}
	
	private String newTableName() {
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		while (true) {
			StringBuilder sb = new StringBuilder();
			sb.append("t_");
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			sb.append(randomChar(rng));
			StringBuilder sb2 = new StringBuilder();
			sb2.append("SELECT 1 FROM ");
			sb2.append(dbName);
			sb2.append(".");
			sb2.append(sb.toString());
			sb2.append(" LIMIT 1;");
			String sql = sb2.toString();
			Statement statement = null;
			String name = null;
			try {
				statement = conn.createStatement();
				statement.executeQuery(sql);
				// If reach here the table selection succeeded and the table exists
				// So loop and try a new filename
			} catch (SQLException e) {
				// If here then assume the table did not exist. Use the generated filename.
				//
				// TODO: test the actual exception to make sure its about the missing table
				//   with given name.
				name = sb.toString();
			}
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				;
			}
			if (name != null)
				return name;
		}
	}
	
	private void createTable() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(dbName);
		sb.append(".");
		sb.append(tableName);
		sb.append("(ID bigint NOT NULL, ");
		for (int i = 0; i < type.doubleCount(); i++) {
			sb.append(" v");
			sb.append(i);
			sb.append(" double NOT NULL,");
		}
		sb.append(" PRIMARY KEY (ID));");
		String sql = sb.toString();
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		// then fill with SELECT INTO FROM 0,0,0,0 for some n. research.
		// TODO: just to get working insert rows iteratively : very inefficient
		try {
			statement = conn.createStatement();
			StringBuilder sb2 = new StringBuilder();
			sb2.append("INSERT INTO ");
			sb2.append(dbName);
			sb2.append(".");
			sb2.append(tableName);
			sb2.append(" VALUES;");
			sql = sb2.toString();
			for (long i = 0; i < size; i++) {
				statement.execute(sql);
			}
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

		
	}

	// Thread local variable containing each thread's temp array
    private final ThreadLocal<double[]> tmpSpace =
        new ThreadLocal<double[]>() {
            @Override protected double[] initialValue() {
                return new double[type.doubleCount()];
        }
    };
    
}
