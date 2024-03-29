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
public class SixDView<U> implements Dimensioned {

	private final long d0;
	private final long d1;
	private final long d2;
	private final long d3;
	private final long d4;
	private final long d5;
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
	 * @param data The 1-d list the view is being built around.
	 */
	public SixDView(long d0, long d1, long d2, long d3, long d4, long d5, IndexedDataSource<U> data) {
		DViewUtils.checkDims(data.size(),d0,d1,d2,d3,d4,d5);
		this.d0 = d0;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.d4 = d4;
		this.d5 = d5;
		this.list = data;
	}

	/**
	 * Construct a view from a {@link DimensionedDataSource}.
	 * 
	 * @param ds The n-d data set that the view is being built around.
	 */
	public SixDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 6)
			throw new IllegalArgumentException("6-d view passed a data source that is "+ds.numDimensions()+"-d");
		this.d0 = ds.dimension(0);
		this.d1 = ds.dimension(1);
		this.d2 = ds.dimension(2);
		this.d3 = ds.dimension(3);
		this.d4 = ds.dimension(4);
		this.d5 = ds.dimension(5);
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
	 * A view.get() call will pull the value at the view input coordinates from the data set into val.
	 * No index out of bounds checking is done.
	 * 
	 * @param i0 0th view input coord
	 * @param i1 1th view input coord
	 * @param i2 2th view input coord
	 * @param i3 3th view input coord
	 * @param i4 4th view input coord
	 * @param i5 5th view input coord
	 * @param val The output where the result is placed
	 */
	public void get(long i0, long i1, long i2, long i3, long i4, long i5, U val) {
		long index = i5;
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
	 * @param val The input that is stored in the underlying data set
	 */
	public void set(long i0, long i1, long i2, long i3, long i4, long i5, U val) {
		long index = i5;
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
	public void safeGet(long i0, long i1, long i2, long i3, long i4, long i5, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4, i5)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			get(i0, i1, i2, i3, i4, i5, val);
	}

	/**
	 * A view.safeSet() call will do a set() call provided the passed index coordinate values
	 * fit within the view's dimensions. If not an exception is thrown instead.
	 */
	public void safeSet(long i0, long i1, long i2, long i3, long i4, long i5, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4, i5)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			set(i0, i1, i2, i3, i4, i5, val);
	}

	private boolean outOfBounds(long i0, long i1, long i2, long i3, long i4, long i5) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		if (i2 < 0 || i2 >= d2) return true;
		if (i3 < 0 || i3 >= d3) return true;
		if (i4 < 0 || i4 >= d4) return true;
		if (i5 < 0 || i5 >= d5) return true;
		return false;
	}

	/**
	 * Return the number of dimensions in the view.
	 */
	@Override
	public int numDimensions() {
		return 6;
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
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
