/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.dataview;

import nom.bdezonia.zorbage.algebra.Dimensioned;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class OneDView<U> implements Dimensioned {

	private final long d0;
	private final IndexedDataSource<U> list;
	private final DimensionedDataSource<U> ds;
	private final ThreadLocal<IntegerIndex> idx;

	
	public OneDView(IndexedDataSource<U> data) {
		this.list = data;
		this.d0 = data.size();
		this.ds = null;
		this.idx = null;
	}
	
	public OneDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 1)
			throw new IllegalArgumentException("1-d view passed a data source that is "+ds.numDimensions()+"-d");
		this.list = ds.rawData();
		this.d0 = list.size();
		this.ds = ds;
		this.idx = new ThreadLocal<IntegerIndex>() {
			@Override
			protected IntegerIndex initialValue() {
				return new IntegerIndex(1);
			}
		};
	}
	
	public long d0() { return d0; }
	
	public void get(long i0, U val) {
		list.get(i0, val);
	}
	
	public void set(long i0, U val) {
		list.set(i0, val);
	}
	
	public void safeGet(long i0, U val) {
		if (outOfBounds(i0)) {
			if (ds == null)
				throw new IllegalArgumentException("view index out of bounds");
			IntegerIndex index = idx.get();
			index.set(0, i0);
			ds.safeGet(index, val);
		}
		else
			get(i0, val);
	}
	
	public void safeSet(long i0, U val) {
		if (outOfBounds(i0)) {
			if (ds == null)
				throw new IllegalArgumentException("view index out of bounds");
			IntegerIndex index = idx.get();
			index.set(0, i0);
			ds.safeSet(index, val);
		}
		else
			set(i0, val);
	}
	
	private boolean outOfBounds(long i0) {
		if (i0 < 0 || i0 >= d0) return true;
		return false;
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public long dimension(int d) {
		if (d == 0) return d0;
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
