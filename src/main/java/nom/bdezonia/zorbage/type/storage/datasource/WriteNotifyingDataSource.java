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

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class WriteNotifyingDataSource<T extends Algebra<T,U>, U>
	implements IndexedDataSource<U>
{
	private final T algebra;
	private final IndexedDataSource<U> source;
	private final List<DataSourceListener<T,U>> listeners;
	
	/**
	 * 
	 * @param alg
	 * @param src
	 */
	public WriteNotifyingDataSource(T alg, IndexedDataSource<U> src) {
		this.algebra = alg;
		this.source = src;
		this.listeners = new ArrayList<DataSourceListener<T,U>>();
	}
	
	public void subscribe(DataSourceListener<T,U> listener) {
		for (int i = 0; i < listeners.size(); i++) {
			if (listeners.get(i) == listener)
				return;
		}
		listeners.add(listener);
	}
	
	public void unsubscribe(DataSourceListener<T,U> listener) {
		listeners.remove(listener);
	}
	
	@Override
	public WriteNotifyingDataSource<T,U> duplicate() {
		return new WriteNotifyingDataSource<T,U>(algebra, source);
	}

	@Override
	public void set(long index, U value) {
		source.set(index, value);
		for (int i = 0; i < listeners.size(); i++) {
			// NOTE
			// I am passing "this" as the source list to the listeners. Imagine you have one
			// listener listening to two different WriteNotifiers. If I passed "source" the
			// listener would not know which list changed. If I pass "this" the listener
			// can compare object ids to its known WriteNotifiers to know who changed.
			// Also if we pass "source" then a listener can write to the source list without
			// causing write notifications to other listeners. That would be bad.
			listeners.get(i).notify(algebra, this, index);
		}
	}

	@Override
	public void get(long index, U value) {
		source.get(index, value);
	}

	@Override
	public long size() {
		return source.size();
	}

	@Override
	public StorageConstruction storageType() {
		return source.storageType();
	}
}
