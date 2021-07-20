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

import nom.bdezonia.zorbage.algebra.DimensionCount;
import nom.bdezonia.zorbage.data.DimensionedDataSource;

/**
 * WindowView is used to grab data from a DimensionedDataSource where all the view
 * coords are integers. The WindowView can pan or scroll over a large data source
 * whose coords are longs. The use of integers in WindowView allows one to write
 * graphics UI code (already generally based on int sizes) that can view long based
 * data.
 * 
 * @author Barry DeZonia
 *
 */
public class WindowView<U> implements DimensionCount {
	
	private final PlaneView<U> dataView;
	private final int d0;
	private final int d1;
	private long origin0;
	private long origin1;

	/**
	 * 
	 * @param dataSource
	 * @param d0
	 * @param d1
	 */
	public WindowView(DimensionedDataSource<U> dataSource, int d0, int d1) {
		this.dataView = new PlaneView<>(dataSource, 0, 1);
		if (d0 > dataSource.dimension(0))
			this.d0 = (int) dataSource.dimension(0);
		else
			this.d0 = d0;
		if (d1 > dataSource.dimension(1))
			this.d1 = (int) dataSource.dimension(1);
		else
			this.d1 = d1;
		this.origin0 = 0;
		this.origin1 = 0;
	}

	/**
	 * 
	 */
	@Override
	public int numDimensions() {
		return 2;
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public int dimension(int d) {
		if (d == 0) return d0;
		if (d == 1) return d1;
		throw new IllegalArgumentException("dimension out of bounds");
	}
	
	/**
	 * 
	 * @return
	 */
	public int d0() {
		return d0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int d1() {
		return d1;
	}

	/**
	 * 
	 * @return
	 */
	public DimensionedDataSource<U> getDataSource() {
		return dataView.getDataSource();
	}

	/**
	 * 
	 * @return
	 */
	public int getExtraDimsCount() {
		return dataView.getExtraDimsCount();
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public long getExtraDimValue(int i) {
		return dataView.getExtraDimValue(i);
	}

	/**
	 * 
	 * @param i
	 * @param v
	 */
	public void setExtraDimValue(int i, long v) {
		dataView.setExtraDimValue(i, v);
	}

	/**
	 * 
	 * @return
	 */
	public long origin0() {
		return origin0;
	}

	/**
	 * 
	 * @return
	 */
	public long origin1() {
		return origin1;
	}
	
	/**
	 * 
	 * @param numCols
	 */
	public void moveWindowLeft(long numCols) {
		origin0 -= numCols;
		if (origin0 < 0)
			origin0 = 0;
	}
	
	/**
	 * 
	 * @param numCols
	 */
	public void moveWindowRight(long numCols) {
		origin0 += numCols;
		if (origin0 > (dataView.d0() - this.d0))
			origin0 = dataView.d0() - this.d0;
	}
	
	// TODO: do we want origin at LL or UL?
	
	/**
	 * 
	 * @param numRows
	 */
	public void moveWindowUp(long numRows) {
		origin1 += numRows;
		if (origin1 > (dataView.d1() - this.d1))
			origin1 = dataView.d1() - this.d1;
	}
	
	/**
	 * 
	 * @param numRows
	 */
	public void moveWindowDown(long numRows) {
		origin1 -= numRows;
		if (origin1 < 0)
			origin1 = 0;
	}
	
	/**
	 * 
	 * @param i0
	 * @param i1
	 * @param value
	 */
	public void get(int i0, int i1, U value) {
		dataView.get((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
	
	/**
	 * 
	 * @param i0
	 * @param i1
	 * @param value
	 */
	public void set(int i0, int i1, U value) {
		dataView.set((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
	
	/**
	 * 
	 * @param i0
	 * @param i1
	 * @param value
	 */
	public void safeGet(int i0, int i1, U value) {
		dataView.safeGet((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
	
	/**
	 * 
	 * @param i0
	 * @param i1
	 * @param value
	 */
	public void safeSet(int i0, int i1, U value) {
		dataView.safeSet((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
}
