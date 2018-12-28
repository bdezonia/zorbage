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

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorageFloat64<U extends DoubleCoder & Allocatable<U>>
	extends AbstractJdbcStorage<U>
	implements IndexedDataSource<JdbcStorageFloat64<U>, U>
{
	public JdbcStorageFloat64(long size, U type, Connection conn, String dbName) {
		super(size, type, conn, dbName);
		// TODO the string representing the type in the following call may not be portable.
		// It is entirely possible that I need to look at the Connection's metadata and
		// determine the best string to pass based on installed db.
		createTable(conn, dbName, tableName, "double", type.doubleCount(), size);
	}

	@Override
	public JdbcStorageFloat64<U> duplicate() {
		JdbcStorageFloat64<U> newContainer = new JdbcStorageFloat64<U>(size, type, conn, dbName);
		copyTableToTable(conn, dbName, tableName, newContainer.tableName);
		return newContainer;
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("index out of bounds");
		double[] arr = tmpSpace.get();
		value.toDoubleArray(arr, 0);
		setHelper(index, arr);
	}

	@Override
	public void get(long index, U value) {
		ResultSet result = getHelper(index, type.doubleCount());
		double[] arr = tmpSpace.get();
		try {
			for (int i = 0; i < arr.length; i++) {
				arr[i] = result.getDouble(i);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		value.fromDoubleArray(arr, 0);
	}

	// Thread local variable containing each thread's temp array
    private final ThreadLocal<double[]> tmpSpace =
        new ThreadLocal<double[]>() {
            @Override protected double[] initialValue() {
                return new double[type.doubleCount()];
        }
    };
    
}
