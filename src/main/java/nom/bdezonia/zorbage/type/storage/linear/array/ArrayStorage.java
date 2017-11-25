/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.storage.linear.array;

import zorbage.type.storage.coder.BitCoder;
import zorbage.type.storage.coder.BooleanCoder;
import zorbage.type.storage.coder.ByteCoder;
import zorbage.type.storage.coder.DoubleCoder;
import zorbage.type.storage.coder.FloatCoder;
import zorbage.type.storage.coder.IntCoder;
import zorbage.type.storage.coder.LongCoder;
import zorbage.type.storage.coder.ShortCoder;
import zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArrayStorage {
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U> LinearStorage<?,U> allocate(long size, U type) {

		// Best if first: maybe preferred by bit types over other encodings for
		//   space reasons
		if (type instanceof BitCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageBit(size, (BitCoder<U>)type);
		}
		if (type instanceof DoubleCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageFloat64(size, (DoubleCoder<U>)type);
		}
		if (type instanceof FloatCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageFloat32(size, (FloatCoder<U>)type);
		}
		if (type instanceof LongCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageSignedInt64(size, (LongCoder<U>)type);
		}
		if (type instanceof IntCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageSignedInt32(size, (IntCoder<U>)type);
		}
		if (type instanceof ShortCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageSignedInt16(size, (ShortCoder<U>)type);
		}
		if (type instanceof BooleanCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageBoolean(size, (BooleanCoder<U>)type);
		}
		// Best if last as many types might support Bytes by default but prefer
		// other types for speed
		if (type instanceof ByteCoder<?>) {
			return (LinearStorage<?,U>) new ArrayStorageSignedInt8(size, (ByteCoder<U>)type);
		}
		
		throw new IllegalArgumentException("Unsupported type in ArrayStorage");
	}

}
