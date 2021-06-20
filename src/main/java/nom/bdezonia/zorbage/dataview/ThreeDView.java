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
public class ThreeDView<U> implements Dimensioned {

	private final long d0;
	private final long d1;
	private final long d2;
	private final IndexedDataSource<U> list;
	private final DimensionedDataSource<U> ds;
	private final ThreadLocal<IntegerIndex> idx;
	
	public ThreeDView(long d0, long d1, long d2, IndexedDataSource<U> data) {
		if (d0*d1*d2 != data.size())
			throw new IllegalArgumentException("view dimensions do not match underlying data source dimensions");
		this.d0 = d0;
		this.d1 = d1;
		this.d2 = d2;
		this.list = data;
		this.ds = null;
		this.idx = null;
	}
	
	public ThreeDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 3)
			throw new IllegalArgumentException("3-d view passed a data source that is "+ds.numDimensions()+"-d");
		this.d0 = ds.dimension(0);
		this.d1 = ds.dimension(1);
		this.d2 = ds.dimension(2);
		this.list = ds.rawData();
		this.ds = ds;
		this.idx = new ThreadLocal<IntegerIndex>() {
			@Override
			protected IntegerIndex initialValue() {
				return new IntegerIndex(ds.numDimensions());
			}
		};
	}
	
	public long d0() { return d0; }
	
	public long d1() { return d1; }
	
	public long d2() { return d2; }
	
	public void get(long i0, long i1, long i2, U val) {
		long index = i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.get(index, val);
	}
	
	public void set(long i0, long i1, long i2, U val) {
		long index = i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.set(index, val);
	}
	
	public void safeGet(long i0, long i1, long i2, U val) {
		if (outOfBounds(i0,i1,i2)) {
			if (ds == null)
				throw new IllegalArgumentException("view index out of bounds");
			IntegerIndex index = idx.get();
			index.set(0, i0);
			index.set(1, i1);
			index.set(2, i2);
			ds.safeGet(index, val);
		}
		else
			get(i0,i1,i2,val);
	}
	
	public void safeSet(long i0, long i1, long i2, U val) {
		if (outOfBounds(i0,i1,i2)) {
			if (ds == null)
				throw new IllegalArgumentException("view index out of bounds");
			IntegerIndex index = idx.get();
			index.set(0, i0);
			index.set(1, i1);
			index.set(2, i2);
			ds.safeSet(index, val);
		}
		else
			set(i0,i1,i2,val);
	}
	
	private boolean outOfBounds(long i0, long i1, long i2) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		if (i2 < 0 || i2 >= d2) return true;
		return false;
	}

	@Override
	public int numDimensions() {
		return 3;
	}

	@Override
	public long dimension(int d) {
		if (d == 0) return d0;
		if (d == 1) return d1;
		if (d == 2) return d2;
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
