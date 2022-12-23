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
package nom.bdezonia.zorbage.storage.array;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.StringCoder;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageString<U extends StringCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<ArrayStorageString<U>>
{
	private final U type;
	private final String[] data;
	
	public ArrayStorageString(U type, long size) {
		if (size < 0)
			throw new NegativeArraySizeException();
		if (size > (Integer.MAX_VALUE / type.stringCount()))
			throw new IllegalArgumentException("ArrayStorageString can handle at most " + (Integer.MAX_VALUE / type.stringCount()) + " of the type of requested String based entities");
		this.type = type.allocate();
		this.data = new String[(int)size * type.stringCount()];
	}

	@Override
	public void set(long index, U value) {
		value.toStringArray(data, (int)(index * type.stringCount()));
	}

	@Override
	public void get(long index, U value) {
		value.fromStringArray(data, (int)(index * type.stringCount()));
	}
	
	@Override
	public long size() {
		return data.length / type.stringCount();
	}

	@Override
	public ArrayStorageString<U> duplicate() {
		ArrayStorageString<U> s = new ArrayStorageString<U>(type, size());
		// TODO this maybe should dupicate / clone all string values instead of sharing references.
		System.arraycopy(data, 0, s.data, 0, data.length);
		return s;
	}

	@Override
	public ArrayStorageString<U> allocate() {
		return new ArrayStorageString<U>(type, size());
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}

	@Override
	public boolean accessWithOneThread() {
		return false;
	}
}
