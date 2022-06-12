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
public class TwelveDView<U> implements Dimensioned {

	private final long d0;
	private final long d1;
	private final long d2;
	private final long d3;
	private final long d4;
	private final long d5;
	private final long d6;
	private final long d7;
	private final long d8;
	private final long d9;
	private final long d10;
	private final long d11;
	private final IndexedDataSource<U> list;

	/**
	 * Construct a view from an {@link IndexedDataSource} and some dimensions.
	 * 
	 * @param d0 0th dimension in the view.
	 * @param d1 1th dimension in the view.
	 * @param d2 2th dimension in the view.
	 * @param d3 3th dimension in the view.
	 * @param d4 4th dimension in the view.
	 * @param d5 5th dimension in the view.
	 * @param d6 6th dimension in the view.
	 * @param d7 7th dimension in the view.
	 * @param d8 8th dimension in the view.
	 * @param d9 9th dimension in the view.
	 * @param d10 10th dimension in the view.
	 * @param d11 11th dimension in the view.
	 * @param data The 1-d list the view is being built around.
	 */
	public TwelveDView(long d0, long d1, long d2, long d3, long d4, long d5, long d6, long d7, long d8, long d9, long d10, long d11, IndexedDataSource<U> data) {
		DViewUtils.checkDims(data.size(),d0,d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11);
		this.d0 = d0;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.d4 = d4;
		this.d5 = d5;
		this.d6 = d6;
		this.d7 = d7;
		this.d8 = d8;
		this.d9 = d9;
		this.d10 = d10;
		this.d11 = d11;
		this.list = data;
	}

	/**
	 * Construct a view from a {@link DimensionedDataSource}.
	 * 
	 * @param ds The n-d data set that the view is being built around.
	 */
	public TwelveDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 12)
			throw new IllegalArgumentException("12-d view passed a data source that is "+ds.numDimensions()+"-d");
		this.d0 = ds.dimension(0);
		this.d1 = ds.dimension(1);
		this.d2 = ds.dimension(2);
		this.d3 = ds.dimension(3);
		this.d4 = ds.dimension(4);
		this.d5 = ds.dimension(5);
		this.d6 = ds.dimension(6);
		this.d7 = ds.dimension(7);
		this.d8 = ds.dimension(8);
		this.d9 = ds.dimension(9);
		this.d10 = ds.dimension(10);
		this.d11 = ds.dimension(11);
		this.list = ds.rawData();
	}

	/**
	 * Returns the 0th dimension of the view.
	 */
	public long d0() { return d0; }

	/**
	 * Returns the 1th dimension of the view.
	 */
	public long d1() { return d1; }

	/**
	 * Returns the 2th dimension of the view.
	 */
	public long d2() { return d2; }

	/**
	 * Returns the 3th dimension of the view.
	 */
	public long d3() { return d3; }

	/**
	 * Returns the 4th dimension of the view.
	 */
	public long d4() { return d4; }

	/**
	 * Returns the 5th dimension of the view.
	 */
	public long d5() { return d5; }

	/**
	 * Returns the 6th dimension of the view.
	 */
	public long d6() { return d6; }

	/**
	 * Returns the 7th dimension of the view.
	 */
	public long d7() { return d7; }

	/**
	 * Returns the 8th dimension of the view.
	 */
	public long d8() { return d8; }

	/**
	 * Returns the 9th dimension of the view.
	 */
	public long d9() { return d9; }

	/**
	 * Returns the 10th dimension of the view.
	 */
	public long d10() { return d10; }

	/**
	 * Returns the 11th dimension of the view.
	 */
	public long d11() { return d11; }

	/**
	 * A view.get() call will pull the value at the view input coordinates from the data set into val.
	 * No index out of bounds checking is done.
	 * 
	 * @param i0 0th view input coord
	 * @param i1 1th view input coord
	 * @param i2 2th view input coord
	 * @param i3 3th view input coord
	 * @param i4 4th view input coord
	 * @param i5 5th view input coord
	 * @param i6 6th view input coord
	 * @param i7 7th view input coord
	 * @param i8 8th view input coord
	 * @param i9 9th view input coord
	 * @param i10 10th view input coord
	 * @param i11 11th view input coord
	 * @param val The output where the result is placed
	 */
	public void get(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, U val) {
		long index = i11;
		index = index*d10 + i10;
		index = index*d9 + i9;
		index = index*d8 + i8;
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

	/**
	 * A view.set() call will push the value at the view input coordinates into the data set.
	 * No index out of bounds checking is done.
	 * 
	 * @param i0 0th view input coord
	 * @param i1 1th view input coord
	 * @param i2 2th view input coord
	 * @param i3 3th view input coord
	 * @param i4 4th view input coord
	 * @param i5 5th view input coord
	 * @param i6 6th view input coord
	 * @param i7 7th view input coord
	 * @param i8 8th view input coord
	 * @param i9 9th view input coord
	 * @param i10 10th view input coord
	 * @param i11 11th view input coord
	 * @param val The input that is stored in the underlying data set
	 */
	public void set(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, U val) {
		long index = i11;
		index = index*d10 + i10;
		index = index*d9 + i9;
		index = index*d8 + i8;
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

	/**
	 * A view.safeGet() call will do a get() call provided the passed index coordinate values
	 * fit within the view's dimensions. If not an exception is thrown instead.
	 */
	public void safeGet(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			get(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, val);
	}

	/**
	 * A view.safeSet() call will do a set() call provided the passed index coordinate values
	 * fit within the view's dimensions. If not an exception is thrown instead.
	 */
	public void safeSet(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			set(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, val);
	}

	private boolean outOfBounds(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		if (i2 < 0 || i2 >= d2) return true;
		if (i3 < 0 || i3 >= d3) return true;
		if (i4 < 0 || i4 >= d4) return true;
		if (i5 < 0 || i5 >= d5) return true;
		if (i6 < 0 || i6 >= d6) return true;
		if (i7 < 0 || i7 >= d7) return true;
		if (i8 < 0 || i8 >= d8) return true;
		if (i9 < 0 || i9 >= d9) return true;
		if (i10 < 0 || i10 >= d10) return true;
		if (i11 < 0 || i11 >= d11) return true;
		return false;
	}

	/**
	 * Return the number of dimensions in the view.
	 */
	@Override
	public int numDimensions() {
		return 12;
	}

	/**
	 * Retrieve each view dimension by index. Throws an exception if
	 * the dimension index number is outside the view dimensions.
	 */
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
		if (d == 9) return d9;
		if (d == 10) return d10;
		if (d == 11) return d11;
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
