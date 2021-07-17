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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class JdbcStorage {

	/**
	 * 
	 * @param conn
	 * @param size
	 * @param type
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U extends Allocatable<U>> IndexedDataSource<U> allocate(Connection conn, U type, long size) {
		if (type instanceof DoubleCoder) {
			return (IndexedDataSource<U>) new JdbcStorageFloat64(conn, (DoubleCoder)type, size);
		}
		if (type instanceof FloatCoder) {
			return (IndexedDataSource<U>) new JdbcStorageFloat32(conn, (FloatCoder)type, size);
		}
		if (type instanceof LongCoder) {
			return (IndexedDataSource<U>) new JdbcStorageSignedInt64(conn, (LongCoder)type, size);
		}
		if (type instanceof IntCoder) {
			return (IndexedDataSource<U>) new JdbcStorageSignedInt32(conn, (IntCoder)type, size);
		}
		if (type instanceof ShortCoder) {
			return (IndexedDataSource<U>) new JdbcStorageSignedInt16(conn, (ShortCoder)type, size);
		}
		if (type instanceof BooleanCoder) {
			return (IndexedDataSource<U>) new JdbcStorageBoolean(conn, (BooleanCoder)type, size);
		}
		if (type instanceof CharCoder) {
			return (IndexedDataSource<U>) new JdbcStorageChar(conn, (CharCoder)type, size);
		}
		if (type instanceof BigIntegerCoder) {
			return (IndexedDataSource<U>) new JdbcStorageBigInteger(conn, (BigIntegerCoder)type, size);
		}
		if (type instanceof BigDecimalCoder) {
			return (IndexedDataSource<U>) new JdbcStorageBigDecimal(conn, (BigDecimalCoder)type, size);
		}
		// Best if close to last as types may define Bytes as a last ditch approach
		if (type instanceof ByteCoder) {
			return (IndexedDataSource<U>) new JdbcStorageSignedInt8(conn, (ByteCoder)type, size);
		}
		
		// TODO: add bitCoder when it is done. It should certainly be last as it will
		//   do files reads as well as writes and thus will be slowest.
		
		throw new IllegalArgumentException("Unsupported type in JdbcStorage");
	}

}
