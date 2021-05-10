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
public class NineDView<U> implements Dimensioned {

	private final long d0;
	private final long d1;
	private final long d2;
	private final long d3;
	private final long d4;
	private final long d5;
	private final long d6;
	private final long d7;
	private final long d8;
	private final IndexedDataSource<U> list;
	
	public NineDView(long d0, long d1, long d2, long d3, long d4, long d5, long d6, long d7, long d8, IndexedDataSource<U> data) {
		if (d0*d1*d2*d3*d4*d5*d6*d7*d8 != data.size())
			throw new IllegalArgumentException("view dimensions do not match underlying data source dimensions");
		this.d0 = d0;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.d4 = d4;
		this.d5 = d5;
		this.d6 = d6;
		this.d7 = d7;
		this.d8 = d8;
		this.list = data;
	}
	
	public NineDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 9)
			throw new IllegalArgumentException("9-d view passed a data source that is "+ds.numDimensions()+"-d");
		d0 = ds.dimension(0);
		d1 = ds.dimension(1);
		d2 = ds.dimension(2);
		d3 = ds.dimension(3);
		d4 = ds.dimension(4);
		d5 = ds.dimension(5);
		d6 = ds.dimension(6);
		d7 = ds.dimension(7);
		d8 = ds.dimension(8);
		list = ds.rawData();
	}
	
	public long d0() { return d0; }
	
	public long d1() { return d1; }
	
	public long d2() { return d2; }
	
	public long d3() { return d3; }
	
	public long d4() { return d4; }
	
	public long d5() { return d5; }
	
	public long d6() { return d6; }
	
	public long d7() { return d7; }
	
	public long d8() { return d8; }
	
	public void get(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, U val) {
		long index = i8;
		index = index*d7 + i7;
		index = index*d6 + i6;
		index = index*d5 + i5;
		index = index*d4 + i4;
		index = index*d3 + i3;
		index = index*d2 + i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.get(index, val);
	}
	
	public void set(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, U val) {
		long index = i8;
		index = index*d7 + i7;
		index = index*d6 + i6;
		index = index*d5 + i5;
		index = index*d4 + i4;
		index = index*d3 + i3;
		index = index*d2 + i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.set(index, val);
	}
	
	public void safeGet(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, U val) {
		if (outOfBounds(i0,i1,i2,i3,i4,i5,i6,i7,i8))
			throw new IllegalArgumentException("view index out of bounds");
		get(i0,i1,i2,i3,i4,i5,i6,i7,i8,val);
	}
	
	public void safeSet(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, U val) {
		if (outOfBounds(i0,i1,i2,i3,i4,i5,i6,i7,i8))
			throw new IllegalArgumentException("view index out of bounds");
		set(i0,i1,i2,i3,i4,i5,i6,i7,i8,val);
	}
	
	private boolean outOfBounds(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		if (i2 < 0 || i2 >= d2) return true;
		if (i3 < 0 || i3 >= d3) return true;
		if (i4 < 0 || i4 >= d4) return true;
		if (i5 < 0 || i5 >= d5) return true;
		if (i6 < 0 || i6 >= d6) return true;
		if (i7 < 0 || i7 >= d7) return true;
		if (i8 < 0 || i8 >= d8) return true;
		return false;
	}

	@Override
	public int numDimensions() {
		return 9;
	}

	@Override
	public long dimension(int d) {
		if (d == 0) return d0;
		if (d == 1) return d1;
		if (d == 2) return d2;
		if (d == 3) return d3;
		if (d == 4) return d4;
		if (d == 5) return d5;
		if (d == 6) return d6;
		if (d == 7) return d7;
		if (d == 8) return d8;
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
