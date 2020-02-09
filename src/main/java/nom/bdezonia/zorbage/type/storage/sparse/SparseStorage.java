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
package nom.bdezonia.zorbage.type.storage.sparse;

import nom.bdezonia.zorbage.type.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.type.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.type.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.type.storage.coder.IntCoder;
import nom.bdezonia.zorbage.type.storage.coder.LongCoder;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SparseStorage {
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U> IndexedDataSource<U> allocate(long size, U type) {
		if (type instanceof DoubleCoder) {
			return (IndexedDataSource<U>) new SparseStorageFloat64(size, (DoubleCoder)type);
		}
		if (type instanceof FloatCoder) {
			return (IndexedDataSource<U>) new SparseStorageFloat32(size, (FloatCoder)type);
		}
		if (type instanceof LongCoder) {
			return (IndexedDataSource<U>) new SparseStorageSignedInt64(size, (LongCoder)type);
		}
		if (type instanceof IntCoder) {
			return (IndexedDataSource<U>) new SparseStorageSignedInt32(size, (IntCoder)type);
		}
		if (type instanceof ShortCoder) {
			return (IndexedDataSource<U>) new SparseStorageSignedInt16(size, (ShortCoder)type);
		}
		if (type instanceof BooleanCoder) {
			return (IndexedDataSource<U>) new SparseStorageBoolean(size, (BooleanCoder)type);
		}
		if (type instanceof BigIntegerCoder) {
			return (IndexedDataSource<U>) new SparseStorageBigInteger(size, (BigIntegerCoder)type);
		}
		if (type instanceof BigDecimalCoder) {
			return (IndexedDataSource<U>) new SparseStorageBigDecimal(size, (BigDecimalCoder)type);
		}
		// Best if close to last as types may define Bytes as a last ditch approach
		if (type instanceof ByteCoder) {
			return (IndexedDataSource<U>) new SparseStorageSignedInt8(size, (ByteCoder)type);
		}
		
		// TODO: add bitCoder when it is done.
		
		throw new IllegalArgumentException("Unsupported type in SparseStorage");
	}

}
