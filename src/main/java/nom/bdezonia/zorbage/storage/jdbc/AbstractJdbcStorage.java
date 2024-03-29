/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Allocatable;

/**
 * 
 * @author Barry DeZonia
 *
 */
abstract class AbstractJdbcStorage<U extends Allocatable<U>>
{
	protected final U type;
	protected final long size;
	protected final String tableName;
	protected final Connection conn;
	
	abstract String zeroValueAsString();  // some types can represent zero as "0" while chars represent 0 as a blank.
	
	protected AbstractJdbcStorage(long size, U type, Connection conn) {
		if (size < 0)
			throw new NegativeArraySizeException();
		this.size = size;
		this.type = type.allocate();
		this.conn = conn;
		this.tableName = newTableName(conn);
	}
	
	public long size() {
		return size;
	}

	public void cleanup() {
		Statement statement;
		try {
			statement = conn.createStatement();
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE ");
			sb.append(tableName);
			sb.append(';');
			String sql = sb.toString();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	protected void createTable(Connection conn, String tableName, String sqlType, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(tableName);
		sb.append("(ID bigint NOT NULL, ");
		for (int i = 0; i < count; i++) {
			sb.append(" v");
			sb.append(i);
			sb.append(' ');
			sb.append(sqlType);
			sb.append(" DEFAULT ");
			sb.append(zeroValueAsString());
			sb.append(" NOT NULL,");
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
	}

	protected void zeroFill(int valueCount) {
		
		final long MAX = 500;
		
		try {
			Statement statement = conn.createStatement();
			long i = 0;
			while (i < size) {
				long num;
				if (size - i < MAX) {
					num = size - i;
				}
				else {
					num = MAX;
				}
				String sql = makeInsertZeroesStatement(i, num, valueCount);
				i += num;
				statement.execute(sql);
			}
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	
	protected String makeInsertZeroesStatement(long offset, long count, int valueCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(tableName);
		sb.append(" (ID) VALUES ");
		for (long c = 0; c < count; c++) {
			sb.append('(');
			sb.append(offset+c);
			sb.append(')');
			if (c != count-1)
				sb.append(',');
		}
		sb.append(';');
		return sb.toString();
	}

	protected void copyTableToTable(Connection conn, String fromTable, String toTable) {
		StringBuilder sb2 = new StringBuilder();
		sb2.append("TRUNCATE TABLE ");
		sb2.append(toTable);
		sb2.append(';');
		String sql = sb2.toString();
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(toTable);
		sb.append(" SELECT * FROM ");
		sb.append(fromTable);
		sb.append(" WHERE 1 = 1;");
		sql = sb.toString();
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	protected String newTableName(Connection conn) {
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
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				;
			}
			if (name != null)
				return name;
		}
	}

	protected Tuple2<Statement,ResultSet> getHelper(long index, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (int i = 0; i < count; i++) {
			sb.append('v');
			sb.append(i);
			if (i != count-1)
				sb.append(',');
		}
		sb.append(" FROM ");
		sb.append(tableName);
		sb.append(" WHERE id = ");
		sb.append(index);
		sb.append(';');
		String sql = sb.toString();
		Statement statement = null;
		try {
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			return new Tuple2<Statement,ResultSet>(statement,result);
		} catch (SQLException e) {
			return new Tuple2<Statement,ResultSet>(null,null);
		}
	}
	
	protected String setHelper(long index, int fieldCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(tableName);
		sb.append(" SET ");
		for (int i = 0; i < fieldCount; i++) {
			sb.append('v');
			sb.append(i);
			sb.append(" = ");
			sb.append(" ? ");
			if (i != fieldCount-1)
				sb.append(", ");
		}
		sb.append(" WHERE id = ");
		sb.append(index);
		sb.append(';');
		return sb.toString();
	}
	
	private char randomChar(Random rng) {
		return (char)('a' + rng.nextInt(26));
	}
	
}
