/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.DimensionCount;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.coordinates.LinearNdCoordinateSpace;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;

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
	private final int width;
	private final int height;
	private long origin0;
	private long origin1;

	/**
	 * Create a WindowView by specifying the data source to zoom upon
	 * and the two dimensions of the size of the viewport. The dimensions
	 * are integers rather than longs so they work well with Java's
	 * display classes.
	 * 
	 * @param dataSource The possibly large data set to view
	 * @param width The width of viewport on the data
	 * @param height The height of the viewport on the data
	 * @param axisNumber0 The 0th axis of the view (x, y, z, time, ...)
	 * @param axisNumber1 The 1th axis of the view (x, y, z, time, ...)
	 */
	public WindowView(DimensionedDataSource<U> dataSource, int width, int height, int axisNumber0, int axisNumber1) {
		this.dataView = new PlaneView<>(dataSource, axisNumber0, axisNumber1);
		this.width = (int) Math.min(width, dataView.dimension(0));
		this.height = (int) Math.min(height, dataView.dimension(1));
		this.origin0 = 0;
		this.origin1 = 0;
	}

	/**
	 * The number of dimensions in this view
	 */
	@Override
	public int numDimensions() {
		return 2;
	}
	
	/**
	 * Returns each of the view dimensions
	 * 
	 * @param d Dimension number
	 * @return
	 */
	public int dimension(int d) {
		if (d == 0) return width;
		if (d == 1) return height;
		throw new IllegalArgumentException("dimension out of bounds");
	}
	
	/**
	 * Return the 0th (width) dimension of the view (as an int)
	 * 
	 * @return
	 */
	public int d0() {
		return width;
	}
	
	/**
	 * Return the 1th (height) dimension of the view (as an int)
	 * 
	 * @return
	 */
	public int d1() {
		return height;
	}

	/**
	 * Returns the original data set we are viewing
	 * 
	 * @return
	 */
	public DimensionedDataSource<U> getDataSource() {
		return dataView.getDataSource();
	}

	/**
	 * Returns the PlaneView we are wrapping
	 * 
	 * @return
	 */
	public PlaneView<U> getPlaneView() {
		return dataView;
	}

	/**
	 * Return the number of dimensions of the data set beyond 2
	 * 
	 * @return
	 */
	public int getPositionsCount() {
		return dataView.getPositionsCount();
	}
	
	/**
	 * Return the value of the position of extra dimension i of this view.
	 * 
	 * @param i
	 * @return
	 */
	public long getPositionValue(int i) {
		return dataView.getPositionValue(i);
	}

	/**
	 * Set the value of the position of extra dimension i of this view.
	 * This affects which plane of the data source is returned from this view.
	 * 
	 * @param i
	 * @param value
	 */
	public void setPositionValue(int i, long value) {
		dataView.setPositionValue(i, value);
	}

	/**
	 * Return the 0th dimension origin point of this view in model coords.
	 * The origin is used to locate the window in real world space so that
	 * local data can be pulled out of the data source. As you move the
	 * WindowView around on the data source the origin is updated. One
	 * should never store the value of the origin point since it could
	 * change out from under you.
	 * 
	 * @return
	 */
	public long origin0() {
		return origin0;
	}

	/**
	 * Return the 1th dimension origin point of this view in model coords.
	 * The origin is used to locate the window in real world space so that
	 * local data can be pulled out of the data source. As you move the
	 * WindowView around on the data source the origin is updated. One
	 * should never store the value of the origin point since it could
	 * change out from under you.
	 * 
	 * @return
	 */
	public long origin1() {
		return origin1;
	}
	
	/**
	 * Move the view window to the left relative to the data source.
	 * This changes the value of the internally stored origin point.
	 * 
	 * @param numCols
	 */
	public void moveWindowLeft(long numCols) {
		if (numCols < 0)
			throw new IllegalArgumentException("unexpected negative number");
		origin0 -= numCols;
		if (origin0 < 0)
			origin0 = 0;
	}
	
	/**
	 * Move the view window to the right relative to the data source.
	 * This changes the value of the internally stored origin point.
	 * 
	 * @param numCols
	 */
	public void moveWindowRight(long numCols) {
		if (numCols < 0)
			throw new IllegalArgumentException("unexpected negative number");
		origin0 += numCols;
		if (origin0 > (dataView.d0() - this.width))
			origin0 = dataView.d0() - this.width;
	}
	
	/**
	 * Move the view window up relative to the data source.
	 * This changes the value of the internally stored origin point.
	 * 
	 * @param numRows
	 */
	public void moveWindowUp(long numRows) {
		if (numRows < 0)
			throw new IllegalArgumentException("unexpected negative number");
		origin1 -= numRows;
		if (origin1 < 0)
			origin1 = 0;
	}
	
	/**
	 * Move the view window down relative to the data source.
	 * This changes the value of the internally stored origin point.
	 * 
	 * @param numRows
	 */
	public void moveWindowDown(long numRows) {
		if (numRows < 0)
			throw new IllegalArgumentException("unexpected negative number");
		origin1 += numRows;
		if (origin1 > (dataView.d1() - this.height))
			origin1 = dataView.d1() - this.height;
	}
	
	/**
	 * Using coords local to this view get the value stored in the data
	 * source at the relevant model coords. No bounds checking is done
	 * on the input coords.
	 *   
	 * @param i0 The 0th component (x or y or z etc.) of the 2d view of data we are interested in
	 * @param i1 The 1th component (y or z or t etc.) of the 2d view of data we are interested in
	 * @param value
	 */
	public void get(int i0, int i1, U value) {
		dataView.get((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
	
	/**
	 * Using coords local to this view set the value stored in the data
	 * source at the relevant model coords. No bounds checking is done
	 * on the input coords.
	 * 
	 * @param i0 The 0th component (x or y or z etc.) of the 2d view of data we are interested in
	 * @param i1 The 1th component (y or z or t etc.) of the 2d view of data we are interested in
	 * @param value
	 */
	public void set(int i0, int i1, U value) {
		dataView.set((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
	
	/**
	 * safeGet will bounds check input coords. If they are out of bounds an
	 * exception is thrown. Otherwise safeGet() does a regular get().
	 * 
	 * @param i0 The 0th component (x or y or z etc.) of the 2d view of data we are interested in
	 * @param i1 The 1th component (y or z or t etc.) of the 2d view of data we are interested in
	 * @param value
	 */
	public void safeGet(int i0, int i1, U value) {
		dataView.safeGet((long)(origin0) + i0, (long)(origin1) + i1, value);
	}
	
	/**
	 * safeSet will bounds check input coords. If they are out of bounds an
	 * exception is thrown. Otherwise safeSet() does a regular set().
	 * 
	 * @param i0 The 0th component (x or y or z etc.) of the 2d view of data we are interested in
	 * @param i1 The 1th component (y or z or t etc.) of the 2d view of data we are interested in
	 * @param value
	 */
	public void safeSet(int i0, int i1, U value) {
		dataView.safeSet((long)(origin0) + i0, (long)(origin1) + i1, value);
	}

	/**
	 * Returns the model coords of the point on the model currently
	 * associated with the axis0/axis1 coords and the current slider
	 * positions.
	 * 
	 * @param i0 The coordinate of interest in the view along axisNumber0
	 * @param i1 The coordinate of interest in the view along axisNumber1
	 * @param modelCoords The output storage for the coords
	 */
	public void getModelCoords(int i0, int i1, long[] modelCoords) {
		long modelPos0 = origin0 + i0;
		long modelPos1 = origin1 + i1;
		dataView.getModelCoords(modelPos0, modelPos1, modelCoords);
	}
	
	/**
	 * Make a 2d image snapshot of the WindowView's viewport.
	 * Calibration and units are transferred to the snapshot
	 * as much as is feasible.
	 * 
	 * @param scratchVar A variable this routine can use for work
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <V extends Allocatable<V>>
		DimensionedDataSource<U> takeSnapsot(U scratchVar)
	{
		DimensionedDataSource<U> newDs = (DimensionedDataSource<U>)
				DimensionedStorage.allocate((V) scratchVar, new long[] {d0(), d1()});
		
		TwoDView<U> view = new TwoDView<>(newDs);
		
		for (int y = 0; y < d1(); y++) {
			for (int x = 0; x < d0(); x++) {
				dataView.get(origin0 + x, origin1 + y , scratchVar);
				view.set(x, y, scratchVar);
			}
		}
		
		DimensionedDataSource<U> origDs = dataView.getDataSource();

		String d0Str = origDs.getAxisType(dataView.axisNumber0());
		String d1Str = origDs.getAxisType(dataView.axisNumber1());
		String axes = "["+d0Str+":"+d1Str+"]";
		String miniTitle = axes + " : slice";

		newDs.setName(origDs.getName() == null ? miniTitle : (miniTitle + " of "+origDs.getName()));
		newDs.setAxisType(0, origDs.getAxisType(dataView.axisNumber0()));
		newDs.setAxisType(1, origDs.getAxisType(dataView.axisNumber1()));
		newDs.setAxisUnit(0, origDs.getAxisUnit(dataView.axisNumber0()));
		newDs.setAxisUnit(1, origDs.getAxisUnit(dataView.axisNumber1()));
		newDs.setValueType(origDs.getValueType());
		newDs.setValueUnit(origDs.getValueUnit());
		
		CoordinateSpace origSpace = dataView.getDataSource().getCoordinateSpace();
		if (origSpace instanceof LinearNdCoordinateSpace) {

			LinearNdCoordinateSpace origLinSpace =
					(LinearNdCoordinateSpace) dataView.getDataSource().getCoordinateSpace();
			
			BigDecimal[] scales = new BigDecimal[2];

			scales[0] = origLinSpace.getScale(dataView.axisNumber0());
			scales[1] = origLinSpace.getScale(dataView.axisNumber1());
			
			BigDecimal[] offsets = new BigDecimal[2];
			
			offsets[0] = origLinSpace.getOffset(dataView.axisNumber0());
			offsets[1] = origLinSpace.getOffset(dataView.axisNumber1());
			
			long[] coord = new long[origDs.numDimensions()];
			
			coord[dataView.axisNumber0()] = origin0;
			coord[dataView.axisNumber1()] = origin1;

			offsets[0] = origLinSpace.project(coord, dataView.axisNumber0());
			offsets[1] = origLinSpace.project(coord, dataView.axisNumber1());

			LinearNdCoordinateSpace newLinSpace = new LinearNdCoordinateSpace(scales, offsets);
			
			newDs.setCoordinateSpace(newLinSpace);
		}

		return newDs;
	}
	
}
