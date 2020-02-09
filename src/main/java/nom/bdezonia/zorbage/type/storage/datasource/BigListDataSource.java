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
package nom.bdezonia.zorbage.type.storage.datasource;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BigListDataSource<T extends Algebra<T,U>,U>
	implements IndexedDataSource<U>
{
	public static long MAX_ITEMS = BigList.MAX_ITEMS;
	
	private final T algebra;
	private final long size;
	private final BigList<U> data;
	
	public BigListDataSource(T algebra, long numItems) {
		this.algebra = algebra;
		this.size = numItems;
		this.data = new BigList<U>(numItems);
		for (long i = 0; i < numItems; i++) {
			this.data.set(i, algebra.construct());
		}
	}

	@Override
	public BigListDataSource<T,U> duplicate() {
		BigListDataSource<T,U> ds = new BigListDataSource<T,U>(algebra, size);
		for (long i = 0; i < size; i++) {
			U v = this.data.get(i);
			ds.set(i, v);
		}
		return ds;
	}

	@Override
	public void set(long index, U value) {
		U v = data.get(index);
		algebra.assign().call(value, v);
	}

	@Override
	public void get(long index, U value) {
		U v = data.get(index);
		algebra.assign().call(v, value);
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}
}
