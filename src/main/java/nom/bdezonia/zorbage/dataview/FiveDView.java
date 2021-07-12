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

/**
 * 
 * @author Barry DeZonia
 * 
 * @param <U>
 */
public class FiveDView<U> implements Dimensioned {

	private final long d0;
	private final long d1;
	private final long d2;
	private final long d3;
	private final long d4;
	private final IndexedDataSource<U> list;

	public FiveDView(long d0, long d1, long d2, long d3, long d4, IndexedDataSource<U> data) {
		DViewUtils.checkDims(data.size(),d0,d1,d2,d3,d4);
		this.d0 = d0;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.d4 = d4;
		this.list = data;
	}

	public FiveDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 5)
			throw new IllegalArgumentException("5-d view passed a data source that is "+ds.numDimensions()+"-d");
		this.d0 = ds.dimension(0);
		this.d1 = ds.dimension(1);
		this.d2 = ds.dimension(2);
		this.d3 = ds.dimension(3);
		this.d4 = ds.dimension(4);
		this.list = ds.rawData();
	}

	public long d0() { return d0; }

	public long d1() { return d1; }

	public long d2() { return d2; }

	public long d3() { return d3; }

	public long d4() { return d4; }

	public void get(long i0, long i1, long i2, long i3, long i4, U val) {
		long index = i4;
		index = index*d3 + i3;
		index = index*d2 + i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.get(index, val);
	}

	public void set(long i0, long i1, long i2, long i3, long i4, U val) {
		long index = i4;
		index = index*d3 + i3;
		index = index*d2 + i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.set(index, val);
	}

	public void safeGet(long i0, long i1, long i2, long i3, long i4, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			get(i0, i1, i2, i3, i4, val);
	}

	public void safeSet(long i0, long i1, long i2, long i3, long i4, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			set(i0, i1, i2, i3, i4, val);
	}

	private boolean outOfBounds(long i0, long i1, long i2, long i3, long i4) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		if (i2 < 0 || i2 >= d2) return true;
		if (i3 < 0 || i3 >= d3) return true;
		if (i4 < 0 || i4 >= d4) return true;
		return false;
	}

	@Override
	public int numDimensions() {
		return 5;
	}

	@Override
	public long dimension(int d) {
		if (d == 0) return d0;
		if (d == 1) return d1;
		if (d == 2) return d2;
		if (d == 3) return d3;
		if (d == 4) return d4;
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
