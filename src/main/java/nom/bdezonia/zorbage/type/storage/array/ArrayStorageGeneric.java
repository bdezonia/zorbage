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

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageGeneric<T extends Algebra<T,U>,U>
	implements IndexedDataSource<ArrayStorageGeneric<T,U>,U>, Allocatable<ArrayStorageGeneric<T,U>>
{

	private final T grp;
	private final Object[] data;
	
	public ArrayStorageGeneric(long size, T grp) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageGeneric cannot handle a negative request");
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorageGeneric can handle at most " + Integer.MAX_VALUE + " objects");
		this.grp = grp;
		this.data = new Object[(int)size];
		for (int i = 0; i < size; i++) {
			this.data[i] = grp.construct();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(long index, U value) {
		grp.assign().call(value, (U)data[(int)index]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void get(long index, U value) {
		grp.assign().call((U)data[(int)index], value);
	}
	
	@Override
	public long size() {
		return data.length;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayStorageGeneric<T,U> duplicate() {
		ArrayStorageGeneric<T,U> s = new ArrayStorageGeneric<T,U>(size(),grp);
		for (int i = 0; i < data.length; i++)
			grp.assign().call((U)data[i], (U)s.data[i]);
		return s;
	}

	@Override
	public ArrayStorageGeneric<T,U> allocate() {
		return new ArrayStorageGeneric<T,U>(size(), grp);
	}

}
