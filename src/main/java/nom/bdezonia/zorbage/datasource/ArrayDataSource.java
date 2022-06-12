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
package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArrayDataSource<T extends Algebra<T,U>,U> implements IndexedDataSource<U> {

	private final T algebra;
	private final U[] data;
	
	/**
	 * Contract: the U[] must not contain null elements.
	 * 
	 * @param algebra
	 * @param data
	 */
	public ArrayDataSource(T algebra, U[] data) {
		this.algebra = algebra;
		this.data = data;
	}
	
	@Override
	public ArrayDataSource<T,U> duplicate() {
		// shallow copy
		return new ArrayDataSource<T,U>(algebra, data);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= data.length)
			throw new IllegalArgumentException("index oob");
		algebra.assign().call(value, data[(int) index]);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= data.length)
			throw new IllegalArgumentException("index oob");
		algebra.assign().call(data[(int) index], value);
	}

	@Override
	public long size() {
		return data.length;
	}

	/**
	 * 
	 * @param algebra
	 * @param numElems
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Algebra<T,U>, U>
		ArrayDataSource<T, U> construct(T algebra, int numElems)
	{
		Object[] array = new Object[numElems];
		for (int i = 0; i < numElems; i++) {
			array[i] = algebra.construct();
		}
		return new ArrayDataSource<T,U>(algebra, (U[]) array); // magic
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
