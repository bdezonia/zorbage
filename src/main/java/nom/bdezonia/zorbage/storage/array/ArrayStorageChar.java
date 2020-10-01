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
package nom.bdezonia.zorbage.storage.array;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.CharCoder;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageChar<U extends CharCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<ArrayStorageChar<U>>
{
	private final U type;
	private final char[] data;
	
	public ArrayStorageChar(long size, U type) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageChar cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / type.charCount()))
			throw new IllegalArgumentException("ArrayStorageChar can handle at most " + (Integer.MAX_VALUE / type.charCount()) + " of the type of requested boolean based entities");
		this.type = type.allocate();
		this.data = new char[(int)size * type.charCount()];
	}

	@Override
	public void set(long index, U value) {
		value.toCharArray(data, (int)(index * type.charCount()));
	}

	@Override
	public void get(long index, U value) {
		value.fromCharArray(data, (int)(index * type.charCount()));
	}
	
	@Override
	public long size() {
		return data.length / type.charCount();
	}

	@Override
	public ArrayStorageChar<U> duplicate() {
		ArrayStorageChar<U> s = new ArrayStorageChar<U>(size(), type);
		System.arraycopy(data, 0, s.data, 0, data.length);
		return s;
	}

	@Override
	public ArrayStorageChar<U> allocate() {
		return new ArrayStorageChar<U>(size(), type);
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}
}