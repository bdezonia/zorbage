/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import java.util.List;

import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ListDataSource<T extends Algebra<T,U>,U> implements IndexedDataSource<U> {

	private final T algebra;
	private final List<U> list;
	
	public ListDataSource(T algebra, List<U> list) {
		this.algebra = algebra;
		this.list = list;
	}
	
	@Override
	public ListDataSource<T,U> duplicate() {
		return new ListDataSource<>(algebra, list);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= list.size())
			throw new IllegalArgumentException("index oob for ListDataSource");
		U tmp = list.get((int) index);
		algebra.assign().call(value, tmp);
		list.set((int) index, tmp);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= list.size())
			throw new IllegalArgumentException("index oob for ListDataSource");
		U tmp = list.get((int) index);
		algebra.assign().call(tmp, value);
	}

	@Override
	public long size() {
		return list.size();
	}

}
