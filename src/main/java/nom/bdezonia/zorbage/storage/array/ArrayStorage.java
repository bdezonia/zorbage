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
package nom.bdezonia.zorbage.storage.array;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.storage.coder.BitCoder;
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
public class ArrayStorage {
	
	/**
	 * 
	 * @param size
	 * @param type
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U extends Allocatable<U>>
		IndexedDataSource<U> allocate(U type, long size)
	{
		if (type instanceof DoubleCoder) {
			return (IndexedDataSource<U>) new ArrayStorageFloat64((DoubleCoder)type, size);
		}
		if (type instanceof FloatCoder) {
			return (IndexedDataSource<U>) new ArrayStorageFloat32((FloatCoder)type, size);
		}
		if (type instanceof LongCoder) {
			return (IndexedDataSource<U>) new ArrayStorageSignedInt64((LongCoder)type, size);
		}
		if (type instanceof IntCoder) {
			return (IndexedDataSource<U>) new ArrayStorageSignedInt32((IntCoder)type, size);
		}
		if (type instanceof ShortCoder) {
			return (IndexedDataSource<U>) new ArrayStorageSignedInt16((ShortCoder)type, size);
		}
		if (type instanceof BooleanCoder) {
			return (IndexedDataSource<U>) new ArrayStorageBoolean((BooleanCoder)type, size);
		}
		if (type instanceof BigIntegerCoder) {
			return (IndexedDataSource<U>) new ArrayStorageBigInteger((BigIntegerCoder)type, size);
		}
		if (type instanceof BigDecimalCoder) {
			return (IndexedDataSource<U>) new ArrayStorageBigDecimal((BigDecimalCoder)type, size);
		}
		if (type instanceof CharCoder) {
			return (IndexedDataSource<U>) new ArrayStorageChar((CharCoder)type, size);
		}
		if (type instanceof StringCoder) {
			return (IndexedDataSource<U>) new ArrayStorageString((StringCoder)type, size);
		}
		// Best if one of last as many types might support Bytes by default but prefer
		// other types for speed
		if (type instanceof ByteCoder) {
			return (IndexedDataSource<U>) new ArrayStorageSignedInt8((ByteCoder)type, size);
		}
		
		// Best if last: since bit types are slow
		if (type instanceof BitCoder) {
			return (IndexedDataSource<U>) new ArrayStorageBit((BitCoder)type, size);
		}

		throw new IllegalArgumentException("Unsupported type in ArrayStorage");
	}
	
}
