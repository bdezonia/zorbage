/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.storage.coder.CharCoder;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.storage.coder.IntCoder;
import nom.bdezonia.zorbage.storage.coder.LongCoder;
import nom.bdezonia.zorbage.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.storage.coder.StringCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorage {

	/**
	 * Allocate a database friendly storage structure for storing
	 * data. These storage types live in rows of database tables.
	 * It is a slow storage type to access but one can allocate a
	 * huge number of elements and external programs can query
	 * your data. It is possible to fail to allocate lists when
	 * your RDBMS system does not have enough resources.
	 * 
	 * @param conn
	 * @param type
	 * @param numElements
 	 * @param <U>
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U extends Allocatable<U>> IndexedDataSource<U>
		allocate(Connection conn, U type, long numElements)
	{
		if (type instanceof DoubleCoder) {
			return new JdbcStorageFloat64(conn, (DoubleCoder)type, numElements);
		}
		if (type instanceof FloatCoder) {
			return new JdbcStorageFloat32(conn, (FloatCoder)type, numElements);
		}
		if (type instanceof LongCoder) {
			return new JdbcStorageSignedInt64(conn, (LongCoder)type, numElements);
		}
		if (type instanceof IntCoder) {
			return new JdbcStorageSignedInt32(conn, (IntCoder)type, numElements);
		}
		if (type instanceof ShortCoder) {
			return new JdbcStorageSignedInt16(conn, (ShortCoder)type, numElements);
		}
		if (type instanceof BooleanCoder) {
			return new JdbcStorageBoolean(conn, (BooleanCoder)type, numElements);
		}
		if (type instanceof StringCoder) {
			return new JdbcStorageString(conn, (StringCoder)type, numElements);
		}
		if (type instanceof CharCoder) {
			return new JdbcStorageChar(conn, (CharCoder)type, numElements);
		}
		if (type instanceof BigIntegerCoder) {
			return new JdbcStorageBigInteger(conn, (BigIntegerCoder)type, numElements);
		}
		if (type instanceof BigDecimalCoder) {
			return new JdbcStorageBigDecimal(conn, (BigDecimalCoder)type, numElements);
		}
		// Best if close to last as types may define Bytes as a last ditch approach
		if (type instanceof ByteCoder) {
			return new JdbcStorageSignedInt8(conn, (ByteCoder)type, numElements);
		}
		
		throw new IllegalArgumentException("Unsupported type in JdbcStorage");
	}

	// do not instantiate
	
	private JdbcStorage() { }
	
}
