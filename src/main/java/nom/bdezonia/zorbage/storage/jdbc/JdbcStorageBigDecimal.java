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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorageBigDecimal<U extends BigDecimalCoder & Allocatable<U>>
    extends AbstractJdbcStorage<U>
    implements IndexedDataSource<U>, Allocatable<JdbcStorageBigDecimal<U>>
{
    // string passed to createTable based on info from:
    //   https://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
    
    public JdbcStorageBigDecimal(long size, U type, Connection conn) {
        super(size, type, conn);
        createTable(conn, tableName, "NUMERIC", type.bigDecimalCount(), size);
        zeroFill(type.bigDecimalCount());
    }

    public JdbcStorageBigDecimal(JdbcStorageBigDecimal<U> other) {
        super(other.size, other.type, other.conn);
        createTable(conn, tableName, "NUMERIC", type.bigDecimalCount(), size);
        copyTableToTable(conn, other.tableName, tableName);
    }

    @Override
    public JdbcStorageBigDecimal<U> duplicate() {
        return new JdbcStorageBigDecimal<U>(this);
    }

    @Override
    public void set(long index, U value) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("index out of bounds");
        BigDecimal[] arr = tmpSpace.get();
        value.toBigDecimalArray(arr, 0);
        setHelper(index, arr);
    }

    @Override
    public void get(long index, U value) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("index out of bounds");
        Tuple2<Statement,ResultSet> result = getHelper(index, type.bigDecimalCount());
        BigDecimal[] arr = tmpSpace.get();
        try {
            result.b().next();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = result.b().getBigDecimal(i+1);
            }
            value.fromBigDecimalArray(arr, 0);
            result.a().close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Thread local variable containing each thread's temp array
    private final ThreadLocal<BigDecimal[]> tmpSpace =
        new ThreadLocal<BigDecimal[]>() {
            @Override protected BigDecimal[] initialValue() {
                return new BigDecimal[type.bigDecimalCount()];
            }
        };

    @Override
    public JdbcStorageBigDecimal<U> allocate() {
        return new JdbcStorageBigDecimal<U>(size(), type, conn);
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
	String value(Object o) {
		return o.toString();
	}

	@Override
	String zero() {
		return "0";
	}
    
}
