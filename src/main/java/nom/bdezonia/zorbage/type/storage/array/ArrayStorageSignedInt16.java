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
package nom.bdezonia.zorbage.type.storage.array;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArrayStorageSignedInt16<U extends ShortCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<ArrayStorageSignedInt16<U>>
{
	private final U type;
	private final short[] data;
	
	public ArrayStorageSignedInt16(long size, U type) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageSignedInt16 cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / type.shortCount()))
			throw new IllegalArgumentException("ArrayStorageSignedInt16 can handle at most " + (Integer.MAX_VALUE / type.shortCount()) + " of the type of requested short based entities");
		this.type = type.allocate();
		this.data = new short[(int)size * type.shortCount()];
	}

	@Override
	public void set(long index, U value) {
		value.toShortArray(data, (int)index * type.shortCount());
	}

	@Override
	public void get(long index, U value) {
		value.fromShortArray(data, (int)index * type.shortCount());
	}
	
	@Override
	public long size() {
		return data.length / type.shortCount();
	}

	@Override
	public ArrayStorageSignedInt16<U> duplicate() {
		ArrayStorageSignedInt16<U> s = new ArrayStorageSignedInt16<U>(size(),type);
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

	@Override
	public ArrayStorageSignedInt16<U> allocate() {
		return new ArrayStorageSignedInt16<U>(size(), type);
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}

}
