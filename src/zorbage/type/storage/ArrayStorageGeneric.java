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
package zorbage.type.storage;

import zorbage.type.algebra.Group;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageGeneric<T extends Group<T,U>,U>
	implements Storage<ArrayStorageGeneric<T,U>,U>
{

	private final Object[] data;
	private final T g;
	
	public ArrayStorageGeneric(long size, T g) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageGeneric cannot handle a negative request");
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorageGeneric cannot handle such a large request");
		this.data = new Object[(int)size];
		for (int i = 0; i < size; i++) {
			this.data[i] = g.construct();
		}
		this.g = g;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(long index, U value) {
		g.assign(value, (U)data[(int)index]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void get(long index, U value) {
		g.assign((U)data[(int)index], value);
	}
	
	@Override
	public long size() {
		return data.length;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayStorageGeneric<T,U> duplicate() {
		ArrayStorageGeneric<T,U> s = new ArrayStorageGeneric<T,U>(size(),g);
		for (int i = 0; i < data.length; i++)
			g.assign((U)data[i], (U)s.data[i]);
		return s;
	}

}
