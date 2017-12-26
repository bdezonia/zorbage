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
package nom.bdezonia.zorbage.type.storage.linear.array;

import nom.bdezonia.zorbage.type.storage.coder.IntCoder;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArrayStorageSignedInt32<U extends IntCoder<U>>
	implements LinearStorage<ArrayStorageSignedInt32<U>,U>
{

	private final U type;
	private final int[] data;
	
	public ArrayStorageSignedInt32(long size, U type) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageSignedInt32 cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / type.intCount()))
			throw new IllegalArgumentException("ArrayStorageSignedInt32 can handle at most " + (Integer.MAX_VALUE / type.intCount()) + " int based entities");
		this.type = type;
		this.data = new int[(int)size * type.intCount()];
	}

	@Override
	public void set(long index, U value) {
		value.toArray(data, (int)(index * type.intCount()));
	}

	@Override
	public void get(long index, U value) {
		value.toValue(data, (int)(index * type.intCount()));
	}
	
	@Override
	public long size() {
		return data.length / type.intCount();
	}

	@Override
	public ArrayStorageSignedInt32<U> duplicate() {
		ArrayStorageSignedInt32<U> s = new ArrayStorageSignedInt32<U>(size(), type);
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

}
