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
package nom.bdezonia.zorbage.storage.extmem;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.*;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ExtMemStorage {

	/**
	 * Allocate a list of elements that resides completely in memory and can
	 * contain up to 128 gig of elements and each element can have multiple
	 * components. It is possible to fail to allocate lists of any size
	 * if your system does not have enough free ram.
	 *  
	 * @param type
	 * @param numElements
	 * @param <U>
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U> IndexedDataSource<U> allocate(U type, long numElements) {

		if (numElements < 0)
			throw new IllegalArgumentException("num elements must be >= 0");

		if (type instanceof DoubleCoder) {
			return new ExtMemStorageFloat64((DoubleCoder) type, numElements);
		}
		if (type instanceof FloatCoder) {
			return new ExtMemStorageFloat32((FloatCoder) type, numElements);
		}
		if (type instanceof LongCoder) {
			return new ExtMemStorageSignedInt64((LongCoder) type, numElements);
		}
		if (type instanceof IntCoder) {
			return new ExtMemStorageSignedInt32((IntCoder) type, numElements);
		}
		if (type instanceof ShortCoder) {
			return new ExtMemStorageSignedInt16((ShortCoder) type, numElements);
		}
		if (type instanceof BigIntegerCoder) {
			return new ExtMemStorageBigInteger((BigIntegerCoder) type, numElements);
		}
		if (type instanceof BigDecimalCoder) {
			return new ExtMemStorageBigDecimal((BigDecimalCoder) type, numElements);
		}
		if (type instanceof StringCoder) {
			return new ExtMemStorageString((StringCoder) type, numElements);
		}
		if (type instanceof CharCoder) {
			return new ExtMemStorageChar((CharCoder) type, numElements);
		}
		if (type instanceof BooleanCoder) {
			return new ExtMemStorageBoolean((BooleanCoder) type, numElements);
		}
		if (type instanceof ByteCoder) {
			return new ExtMemStorageSignedInt8((ByteCoder) type, numElements);
		}
		// someday?
		//
		// if (type instanceof BitCoder) {
		// }

		throw new IllegalArgumentException("Unsupported type in HyperBoxStorage");
	}

	// do not instantiate
	
	private ExtMemStorage() { }
	
}
