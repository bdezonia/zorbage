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
package nom.bdezonia.zorbage.type.storage.array;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageBoolean<U extends BooleanCoder & Allocatable<U>>
	implements IndexedDataSource<ArrayStorageBoolean<U>, U>
{
	private final U type;
	private final boolean[] data;
	
	public ArrayStorageBoolean(long size, U type) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageBoolean cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / type.booleanCount()))
			throw new IllegalArgumentException("ArrayStorageBoolean can handle at most " + (Integer.MAX_VALUE / type.booleanCount()) + " boolean based entities");
		this.type = type.allocate();
		this.data = new boolean[(int)size * type.booleanCount()];
	}

	@Override
	public void set(long index, U value) {
		value.toBooleanArray(data, (int)(index * type.booleanCount()));
	}

	@Override
	public void get(long index, U value) {
		value.fromBooleanArray(data, (int)(index * type.booleanCount()));
	}
	
	@Override
	public long size() {
		return data.length / type.booleanCount();
	}

	@Override
	public ArrayStorageBoolean<U> duplicate() {
		ArrayStorageBoolean<U> s = new ArrayStorageBoolean<U>(size(), type);
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

}
