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
	 * Allocate an array based storage structure for storing data
	 * elements. These storage types live in ram and are the
	 * fastest storage types to access. One cannot allocate more
	 * than Integer.MAX_VALUE elements using this allocator. It
	 * is possible to fail to allocate lists if your system does
	 * not have enough free ram.
	 * 
	 * @param type
	 * @param numElements
	 * @param <U>
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U extends Allocatable<U>>
		IndexedDataSource<U> allocate(U type, long numElements)
	{
		if (type instanceof DoubleCoder) {
			return new ArrayStorageFloat64((DoubleCoder)type, numElements);
		}
		if (type instanceof FloatCoder) {
			return new ArrayStorageFloat32((FloatCoder)type, numElements);
		}
		if (type instanceof LongCoder) {
			return new ArrayStorageSignedInt64((LongCoder)type, numElements);
		}
		if (type instanceof IntCoder) {
			return new ArrayStorageSignedInt32((IntCoder)type, numElements);
		}
		if (type instanceof ShortCoder) {
			return new ArrayStorageSignedInt16((ShortCoder)type, numElements);
		}
		if (type instanceof BooleanCoder) {
			return new ArrayStorageBoolean((BooleanCoder)type, numElements);
		}
		if (type instanceof BigIntegerCoder) {
			return new ArrayStorageBigInteger((BigIntegerCoder)type, numElements);
		}
		if (type instanceof BigDecimalCoder) {
			return new ArrayStorageBigDecimal((BigDecimalCoder)type, numElements);
		}
		if (type instanceof StringCoder) {
			return new ArrayStorageString((StringCoder)type, numElements);
		}
		if (type instanceof CharCoder) {
			return new ArrayStorageChar((CharCoder)type, numElements);
		}
		// Best if one of last as many types might support Bytes by default but prefer
		// other types for speed
		if (type instanceof ByteCoder) {
			return new ArrayStorageSignedInt8((ByteCoder)type, numElements);
		}
		
		// Best if last: since bit types are slow
		if (type instanceof BitCoder) {
			return new ArrayStorageBit((BitCoder)type, numElements);
		}

		throw new IllegalArgumentException("Unsupported type in ArrayStorage");
	}
	
	// do not instaniate
	
	private ArrayStorage() { }
}
