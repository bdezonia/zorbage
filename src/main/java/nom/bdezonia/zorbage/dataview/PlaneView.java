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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Dimensioned;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.coordinates.LinearNdCoordinateSpace;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;

/**
 * 
 * @author Barry DeZonia
 * 
 * @param <U>
 */
public class PlaneView<U> implements Dimensioned {

	private final int axisNumber0;  // number of our 0th axis in the parent data source
	private final int axisNumber1;  // number of our 1th axis in the parent data source
	private final long axisNumber0Size;  // dimension of our 0th axis in the parent data source
	private final long axisNumber1Size;  // dimension of our 1th axis in the parent data source
	private final Accessor<U> accessor;
	private final DimensionedDataSource<U> data;

	/**
	 * Construct a 2-d view of a {@link DimensionedDataSource} by specifying
	 * two axis numbers.
	 * 
	 * @param data The n-d data source the view is being built upon.
	 * @param axis0 The axis number of the "x" component in the data source. X is
	 *              in quotes here because you can specify the y, x, time, channel
	 *              or whatever other axis is defined for the data source.
	 * @param axis1 The axis number of the "y" component in the data source. Y is
	 *              in quotes here because you can specify the y, x, time, channel
	 *              or whatever other axis is defined for the data source.
	 */
	public PlaneView(DimensionedDataSource<U> data, int axis0, int axis1) {

		int numD = data.numDimensions();

		if (numD == 0)
			throw new IllegalArgumentException(
						"data source must have at least 1 dimension");

		if (axis0 == axis1)
			throw new IllegalArgumentException("same coordinate axis specified twice");
		
		if (axis0 >= axis1)
			throw new IllegalArgumentException(
					"axis specified out of order: all numbers assume left to right declaration");
		
		if (axis0 < 0 || axis0 >= numD)
			throw new IllegalArgumentException("coordinate component 0 is outside number of dimensions");
		
		if (axis1 < 0 || ((numD == 1 && axis1 > 1) || (numD > 1 && axis1 >= numD)))
			throw new IllegalArgumentException("coordinate component 1 is outside number of dimensions");

		this.data = data;
		
		this.axisNumber0 = axis0;
		this.axisNumber1 = axis1;

		this.axisNumber0Size = data.dimension(axis0);
		this.axisNumber1Size = numD == 1 ? 1 : data.dimension(axis1);

		switch (numD) {
		case 1:
			accessor = new Accessor1d<U>(data);
			break;
		case 2:
			accessor = new Accessor2d<U>(data);
			break;
		case 3:
			accessor = new Accessor3d<U>(data);
			break;
		case 4:
			accessor = new Accessor4d<U>(data);
			break;
		case 5:
			accessor = new Accessor5d<U>(data);
			break;
		case 6:
			accessor = new Accessor6d<U>(data);
			break;
		case 7:
			accessor = new Accessor7d<U>(data);
			break;
		case 8:
			accessor = new Accessor8d<U>(data);
			break;
		case 9:
			accessor = new Accessor9d<U>(data);
			break;
		case 10:
			accessor = new Accessor10d<U>(data);
			break;
		case 11:
			accessor = new Accessor11d<U>(data);
			break;
		default:
			throw new IllegalArgumentException(
					""+numD+" dimensions not yet supported in PlaneView");
		}
	}

	/**
	 * Returns the {@link DimensionedDataSource} that the PlaneView is attached to.
	 */
	public DimensionedDataSource<U> getDataSource() {
		return data;
	}
	
	/**
	 * Returns the 0th dimension of the view (the width).
	 */
	public long d0() { return axisNumber0Size; }

	/**
	 * Returns the 1th dimension of the view (the height).
	 */
	public long d1() { return axisNumber1Size; }

	/**
	 * A view.get() call will pull the value at the view input coordinates
	 * from the data set into val. No index out of bounds checking is done.
	 * 
	 * @param i0 0th view input coord
	 * @param i1 1th view input coord
	 * @param val The output where the result is placed
	 */
	public void get(long i0, long i1, U val) {
		accessor.get(i0, i1, val);
	}

	/**
	 * A planeView.set() call will push the value at the view input
	 * coordinates into the data set. No index out of bounds checking
	 * is done.
	 * 
	 * @param i0 0th view input coord
	 * @param i1 1th view input coord
	 * @param val The input that is stored in the underlying data set
	 */
	public void set(long i0, long i1, U val) {
		accessor.set(i0, i1, val);
	}

	/**
	 * A planeView.safeGet() call will do a get() call provided the
	 * passed index coordinate values fit within the view's dimensions.
	 * If not an exception is thrown instead.
	 */
	public void safeGet(long i0, long i1, U val) {
		if (outOfBounds(i0, i1)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			get(i0, i1, val);
	}

	/**
	 * A planeView.safeSet() call will do a set() call provided the
	 * passed index coordinate values fit within the view's dimensions.
	 * If not an exception is thrown instead.
	 */
	public void safeSet(long i0, long i1, U val) {
		if (outOfBounds(i0, i1)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			set(i0, i1, val);
	}

	/**
	 * Return the column number of 0th index view position
	 * (i.e. which column is "x" in the view).
	 */
	public int axisNumber0() {
		return axisNumber0;
	}

	/**
	 * Return the column number of 1th index view position
	 * (i.e. which column is "y" in the view).
	 */
	public int axisNumber1() {
		return axisNumber1;
	}
	
	/**
	 * Return the number of dimensions in the view.
	 */
	@Override
	public int numDimensions() {
		return 2;
	}

	/**
	 * Retrieve each view dimension by index. Throws an exception if
	 * the dimension index number is outside the view dimensions.
	 */
	@Override
	public long dimension(int d) {
		if (d == 0) return axisNumber0Size;
		if (d == 1) return axisNumber1Size;
		throw new IllegalArgumentException("dimension out of bounds");
	}

	/**
	 * Returns the number of dimensions beyond 2 that this PlaneView can manipulate.
	 * @return
	 */
	public int getPositionsCount() {
		return accessor.getPositionsCount();
	}
	
	/**
	 * Set the position value of one of the dimensions of the PlaneView.
	 */
	public void setPositionValue(int i, long pos) {
		accessor.setPositionValue(i, pos);
	}

	/**
	 * Get the position value of one of the dimensions of the PlaneView.
	 */
	public long getPositionValue(int i) {
		return accessor.getPositionValue(i);
	}
	
	/**
	 * Translates the dimensions associated with sliders into their own
	 * original axis number of the parent data source. Imagine you have
	 * a 5d dataset. Your "x" and "y" axis numbers set to 1 and 3. The
	 * planeView stores the 3 other dims as 0, 1, and 2. But their
	 * original axis numbers in the parent data source were 0, 2, and 4.
	 * This method maps 0/1/2 into 0/2/4 in this one case.
	 *  
	 * @param extraDimPos
	 * @return
	 */
	public int getDataSourceAxisNumber(int extraDimPos) {
		int counted = 0;
		for (int i = 0; i < data.numDimensions(); i++) {
			if (i == axisNumber0 || i == axisNumber1)
				continue;
			if (counted == extraDimPos)
				return i;
			counted++;
		}
		return -1;
	}
	
	/**
	 * Returns the dimension of extra axis i in the parent data source.
	 * 
	 * @param extraDimPos
	 * @return
	 */
	public long getDataSourceAxisSize(int extraDimPos) {
		int pos = getDataSourceAxisNumber(extraDimPos);
		return data.dimension(pos);
	}

	/**
	 * Returns the model coords of the point on the model currently
	 * associated with the i0/i1 coords and the current slider
	 * positions.
	 * 
	 * @param i0 The 0th component of the point within the d0 x d1 view we are interested in
	 * @param i1 The 1th component of the point within the d0 x d1 view we are interested in
	 * @param modelCoords The array to store the output coords in.
	 */
	public void getModelCoords(long i0, long i1, long[] modelCoords) {
		for (int i = 0; i < getPositionsCount(); i++) {
			int pos = getDataSourceAxisNumber(i);
			long value = getPositionValue(i);
			modelCoords[pos] = value;
		}
		modelCoords[axisNumber0] = i0;
		modelCoords[axisNumber1] = i1;
	}

	/**
	 * Get a snapshot of a whole plane of data using the current axis positions
	 * and defined 0th and 1th axis designations. So one can easily generate a
	 * Y/Z plane where X == 250, for example. As much as feasible pass on the
	 * units and calibration to the new data source.
	 * 
	 * @param scratchVar A variable that the routine will use in its calcs. 
	 * 
	 * @return A 2-d data source containing the plane
	 */
	@SuppressWarnings("unchecked")
	public <V extends Allocatable<V>>
		DimensionedDataSource<U> copyPlane(U scratchVar)
	{
		DimensionedDataSource<U> newDs = (DimensionedDataSource<U>)
				DimensionedStorage.allocate((V) scratchVar, new long[] {axisNumber0Size,axisNumber1Size});
		
		TwoDView<U> view = new TwoDView<>(newDs);
		
		for (long y = 0; y < axisNumber1Size; y++) {
			for (long x = 0; x < axisNumber0Size; x++) {
				accessor.get(x, y, scratchVar);
				view.set(x, y, scratchVar);
			}
		}

		String d0Str = data.getAxisType(axisNumber0) == null ? ("dim "+axisNumber0) : data.getAxisType(axisNumber0);
		String d1Str = data.getAxisType(axisNumber1) == null ? ("dim "+axisNumber1) : data.getAxisType(axisNumber1);
		String axes = "["+d0Str+":"+d1Str+"]";
		String miniTitle = axes + "slice";
		
		newDs.setName(data.getName() == null ? miniTitle : (miniTitle + " of "+data.getName()));
		newDs.setAxisType(0, data.getAxisType(axisNumber0));
		newDs.setAxisType(1, data.getAxisType(axisNumber1));
		newDs.setAxisUnit(0, data.getAxisUnit(axisNumber0));
		newDs.setAxisUnit(1, data.getAxisUnit(axisNumber1));
		newDs.setValueType(data.getValueType());
		newDs.setValueUnit(data.getValueUnit());
		
		CoordinateSpace origSpace = data.getCoordinateSpace();
		if (origSpace instanceof LinearNdCoordinateSpace) {

			LinearNdCoordinateSpace origLinSpace =
					(LinearNdCoordinateSpace) data.getCoordinateSpace();
			
			BigDecimal[] scales = new BigDecimal[2];

			scales[0] = origLinSpace.getScale(axisNumber0);
			scales[1] = origLinSpace.getScale(axisNumber1);
			
			BigDecimal[] offsets = new BigDecimal[2];
			
			offsets[0] = origLinSpace.getOffset(axisNumber0);
			offsets[1] = origLinSpace.getOffset(axisNumber1);
			
			LinearNdCoordinateSpace newLinSpace = new LinearNdCoordinateSpace(scales, offsets);
			
			newDs.setCoordinateSpace(newLinSpace);
		}

		return newDs;
	}
	
	// ----------------------------------------------------------------------
	//   PRIVATE DECLARATIONS FOLLOW
	// ----------------------------------------------------------------------
	
	// TODO expose these publicly? Will they be useful to someone?
	
	private boolean outOfBounds(long i0, long i1) {
		if (i0 < 0 || i0 >= axisNumber0Size) return true;
		if (i1 < 0 || i1 >= axisNumber1Size) return true;
		return false;
	}

	private interface Accessor<X> {

		void set(long i0, long i1, X value);
		
		void get(long i0, long i1, X value);
		
		int getPositionsCount();
		
		void setPositionValue(int i, long v);
		
		long getPositionValue(int i);
	}

	private abstract class AccessorBase {
		
		private long[] extraDimPositions;
		
		AccessorBase(int numD) {
			int size = numD - 2;
			if (size < 0) size = 0;
			extraDimPositions = new long[size]; 
		}

		public int getPositionsCount() {
			return extraDimPositions.length;
		}
		
		public void setPositionValue(int i, long v) {
			if (i < 0 || i >= extraDimPositions.length)
				throw new IllegalArgumentException("illegal extra dim position");
			extraDimPositions[i] = v;
		}
		
		public long getPositionValue(int i) {
			if (i < 0 || i >= extraDimPositions.length)
				throw new IllegalArgumentException("illegal extra dim position");
			return extraDimPositions[i];
		}
	}
	
	private class Accessor1d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final OneDView<X> view;

		Accessor1d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new OneDView<>(data);
		}

		@Override
		public void set(long i0, long i1, X value) {
			view.set(i0, value);
		}

		@Override
		public void get(long i0, long i1, X value) {
			view.get(i0, value);
		}
	}
	
	private class Accessor2d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final TwoDView<X> view;
	
		Accessor2d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new TwoDView<>(data);
		}
	
		@Override
		public void set(long i0, long i1, X value) {
			view.set(i0, i1, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			view.get(i0, i1, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor3d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final ThreeDView<X> view;
		private long u0, u1, u2;
	
		Accessor3d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new ThreeDView<>(data);
		}

		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) {
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 3d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor4d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final FourDView<X> view;
		private long u0, u1, u2, u3;
	
		Accessor4d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new FourDView<>(data);
		}

		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 4d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor5d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final FiveDView<X> view;
		private long u0, u1, u2, u3, u4;
	
		Accessor5d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new FiveDView<>(data);
		}

		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 5d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor6d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final SixDView<X> view;
		private long u0, u1, u2, u3, u4, u5;
	
		Accessor6d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new SixDView<>(data);
		}

		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 5) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getPositionValue(3);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = i1;
			}
			else if (axisNumber0 == 4 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 6d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, u5, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, u5, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor7d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final SevenDView<X> view;
		private long u0, u1, u2, u3, u4, u5, u6;
	
		Accessor7d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new SevenDView<>(data);
		}

		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 5) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 6) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
			}
			else if (axisNumber0 == 4 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getPositionValue(4);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = i1;
			}
			else if (axisNumber0 == 5 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 7d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, u5, u6, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, u5, u6, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor8d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final EightDView<X> view;
		private long u0, u1, u2, u3, u4, u5, u6, u7;
	
		Accessor8d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new EightDView<>(data);
		}

		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 5) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 6) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 7) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
			}
			else if (axisNumber0 == 4 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
			}
			else if (axisNumber0 == 5 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = i1;
				u7 = getPositionValue(5);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = i1;
			}
			else if (axisNumber0 == 6 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 8d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, u5, u6, u7, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, u5, u6, u7, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor9d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final NineDView<X> view;
		private long u0, u1, u2, u3, u4, u5, u6, u7, u8;
	
		Accessor9d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new NineDView<>(data);
		}
	
		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 5) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 6) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 7) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 8) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 4 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 5 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 6 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = i1;
				u8 = getPositionValue(6);
			}
			else if (axisNumber0 == 6 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = getPositionValue(6);
				u8 = i1;
			}
			else if (axisNumber0 == 7 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = i0;
				u8 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 8d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, u5, u6, u7, u8, value);
		}
	
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, u5, u6, u7, u8, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor10d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final TenDView<X> view;
		private long u0, u1, u2, u3, u4, u5, u6, u7, u8, u9;
		
		Accessor10d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new TenDView<>(data);
		}
		
		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 5) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 6) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 7) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 8) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 9) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 4 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 5 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 6 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 6 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 6 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 7 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = i0;
				u8 = i1;
				u9 = getPositionValue(7);
			}
			else if (axisNumber0 == 7 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = i0;
				u8 = getPositionValue(7);
				u9 = i1;
			}
			else if (axisNumber0 == 8 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = getPositionValue(7);
				u8 = i0;
				u9 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 8d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, u5, u6, u7, u8, u9, value);
		}
		
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, u5, u6, u7, u8, u9, value);
		}
	}

	// spot checked that i* and u* are correctly ordered
	// spot checked that u* are completely specified
	
	private class Accessor11d<X>
		extends AccessorBase
		implements Accessor<X>
	{
		private final ElevenDView<X> view;
		private long u0, u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;
		
		Accessor11d(DimensionedDataSource<X> data) {
			super(data.numDimensions());
			view = new ElevenDView<>(data);
		}
		
		private void setPos(long i0, long i1) {
			if (axisNumber0 == 0 && axisNumber1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getPositionValue(0);
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 2) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 3) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 4) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 5) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 6) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 7) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 8) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 9) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 0 && axisNumber1 == 10) { 
				u0 = i0;
				u1 = getPositionValue(0);
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 1 && axisNumber1 == 2) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getPositionValue(1);
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 1 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = i0;
				u2 = getPositionValue(1);
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 2 && axisNumber1 == 3) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getPositionValue(2);
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 2 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = i0;
				u3 = getPositionValue(2);
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 3 && axisNumber1 == 4) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getPositionValue(3);
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 3 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = i0;
				u4 = getPositionValue(3);
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 4 && axisNumber1 == 5) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getPositionValue(4);
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 4 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = i0;
				u5 = getPositionValue(4);
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 5 && axisNumber1 == 6) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = i1;
				u7 = getPositionValue(5);
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 5 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = i0;
				u6 = getPositionValue(5);
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 6 && axisNumber1 == 7) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = i1;
				u8 = getPositionValue(6);
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 6 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = getPositionValue(6);
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 6 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 6 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = i0;
				u7 = getPositionValue(6);
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 7 && axisNumber1 == 8) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = i0;
				u8 = i1;
				u9 = getPositionValue(7);
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 7 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = i0;
				u8 = getPositionValue(7);
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 7 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = i0;
				u8 = getPositionValue(7);
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 8 && axisNumber1 == 9) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = getPositionValue(7);
				u8 = i0;
				u9 = i1;
				u10 = getPositionValue(8);
			}
			else if (axisNumber0 == 8 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = getPositionValue(7);
				u8 = i0;
				u9 = getPositionValue(8);
				u10 = i1;
			}
			else if (axisNumber0 == 9 && axisNumber1 == 10) { 
				u0 = getPositionValue(0);
				u1 = getPositionValue(1);
				u2 = getPositionValue(2);
				u3 = getPositionValue(3);
				u4 = getPositionValue(4);
				u5 = getPositionValue(5);
				u6 = getPositionValue(6);
				u7 = getPositionValue(7);
				u8 = getPositionValue(8);
				u9 = i0;
				u10 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 8d "+axisNumber0+" "+axisNumber1);
		}
		
		@Override
		public void set(long i0, long i1, X value) {
			setPos(i0, i1);
			view.set(u0, u1, u2, u3, u4, u5, u6, u7, u8, u9, u10, value);
		}
		
		@Override
		public void get(long i0, long i1, X value) {
			setPos(i0, i1);
			view.get(u0, u1, u2, u3, u4, u5, u6, u7, u8, u9, u10, value);
		}
	}
}
