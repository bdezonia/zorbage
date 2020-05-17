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
package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BigListDataSource<T extends Algebra<T,U>, U extends Allocatable<U>>
	implements IndexedDataSource<U>
{
	public static long MAX_ITEMS = BigList.MAX_ITEMS;
	
	private final T algebra;
	private final BigList<U> data;
	
	public BigListDataSource(T algebra, BigList<U> list) {
		this.algebra = algebra;
		this.data = list;
	}

	public BigListDataSource(T algebra, long numItems) {
		this(algebra, new BigList<U>(numItems, algebra.construct()));
	}

	@Override
	public BigListDataSource<T,U> duplicate() {

		// Deep copy needed!
		//
		//   This is the only DataSource that actually holds elements. Subtle bugs will happen if duplicate will only
		//   return a shallow copy.

		BigList<U> newData = new BigList<>(data.size(), algebra.construct());
		for (long i = 0; i < data.size(); i++) {
			U oldVal = data.get(i);
			U newVal = newData.get(i);
			algebra.assign().call(oldVal, newVal);
		}
		return new BigListDataSource<T,U>(algebra, newData);
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
		return data.size();
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}
}
