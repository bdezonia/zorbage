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

import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.FloatCoder;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageFloat32<U extends FloatCoder<U>>
	implements IndexedDataSource<ArrayStorageFloat32<U>, U>
{
	private final U type;
	private final float[] data;
	
	public ArrayStorageFloat32(long size, U type) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageFloat32 cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / type.floatCount()))
			throw new IllegalArgumentException("ArrayStorageFloat32 can handle at most " + (Integer.MAX_VALUE / type.floatCount()) + " float based entities");
		this.type = type;
		this.data = new float[(int)size * type.floatCount()];
	}

	@Override
	public void set(long index, U value) {
		value.toFloatArray(data, (int)(index * type.floatCount()));
	}

	@Override
	public void get(long index, U value) {
		value.fromFloatArray(data, (int)(index * type.floatCount()));
	}
	
	@Override
	public long size() {
		return data.length / type.floatCount();
	}

	@Override
	public ArrayStorageFloat32<U> duplicate() {
		ArrayStorageFloat32<U> s = new ArrayStorageFloat32<U>(size(), type);
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

}
