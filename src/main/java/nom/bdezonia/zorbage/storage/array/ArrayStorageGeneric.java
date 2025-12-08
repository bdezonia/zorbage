/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageGeneric<T extends Algebra<T,U>,U>
	implements IndexedDataSource<U>, Allocatable<ArrayStorageGeneric<T,U>>
{

	private final T alg;
	private final Object[] data;
	
	public ArrayStorageGeneric(T alg, long size) {
		if (size < 0)
			throw new NegativeArraySizeException();
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorageGeneric can handle at most " + Integer.MAX_VALUE + " objects");
		this.alg = alg;
		this.data = new Object[(int)size];
		for (int i = 0; i < size; i++) {
			this.data[i] = alg.construct();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(long index, U value) {
		alg.assign().call(value, (U)data[(int)index]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void get(long index, U value) {
		alg.assign().call((U)data[(int)index], value);
	}
	
	@Override
	public long size() {
		return data.length;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayStorageGeneric<T,U> duplicate() {
		ArrayStorageGeneric<T,U> s = new ArrayStorageGeneric<T,U>(alg, size());
		for (int i = 0; i < data.length; i++)
			alg.assign().call((U)data[i], (U)s.data[i]);
		return s;
	}

	@Override
	public ArrayStorageGeneric<T,U> allocate() {
		return new ArrayStorageGeneric<T,U>(alg, size());
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
