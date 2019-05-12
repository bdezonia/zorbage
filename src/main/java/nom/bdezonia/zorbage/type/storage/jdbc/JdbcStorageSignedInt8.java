/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.storage.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorageSignedInt8<U extends ByteCoder & Allocatable<U>>
	extends AbstractJdbcStorage<U>
	implements IndexedDataSource<U>, Allocatable<JdbcStorageSignedInt8<U>>
{
	// string passed to createTable based on info from:
	//   https://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
	
	public JdbcStorageSignedInt8(long size, U type, Connection conn) {
		super(size, type, conn);
		createTable(conn, tableName, "TINYINT", type.byteCount(), size);
		zeroFill(type.byteCount());
	}

	public JdbcStorageSignedInt8(JdbcStorageSignedInt8<U> other) {
		super(other.size, other.type, other.conn);
		createTable(conn, tableName, "TINYINT", type.byteCount(), size);
		copyTableToTable(conn, other.tableName, tableName);
	}

	@Override
	public JdbcStorageSignedInt8<U> duplicate() {
		return new JdbcStorageSignedInt8<U>(this);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		byte[] arr = tmpSpace.get();
		value.toByteArray(arr, 0);
		setHelper(index, arr);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		Tuple2<Statement,ResultSet> result = getHelper(index, type.byteCount());
		byte[] arr = tmpSpace.get();
		try {
			result.b().next();
			for (int i = 0; i < arr.length; i++) {
				arr[i] = result.b().getByte(i+1);
			}
			value.fromByteArray(arr, 0);
			result.a().close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Thread local variable containing each thread's temp array
    private final ThreadLocal<byte[]> tmpSpace =
        new ThreadLocal<byte[]>() {
            @Override protected byte[] initialValue() {
                return new byte[type.byteCount()];
        }
    };

	@Override
	public JdbcStorageSignedInt8<U> allocate() {
		return new JdbcStorageSignedInt8<U>(size(), type, conn);
	}
    
}
