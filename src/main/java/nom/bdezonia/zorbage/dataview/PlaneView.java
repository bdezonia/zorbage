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

/**
 * 
 * @author Barry DeZonia
 * 
 * @param <U>
 */
public class PlaneView<U> implements Dimensioned {

	private final int c0;
	private final int c1;
	private final long d0;
	private final long d1;
	private final Accessor<U> accessor;
	private final DimensionedDataSource<U> data;

	/**
	 * Construct a view from an {@link DimensionedDataSource} and some dimensions.
	 * 
	 * @param data The n-d data source the view is being built around.
	 * @param c0 The coordinate position of the "x" component in the data source
	 * @param c1 The coordinate position of the "y" component in the data source
	 */
	public PlaneView(DimensionedDataSource<U> data, int c0, int c1) {

		int numD = data.numDimensions();

		if (numD == 0)
			throw new IllegalArgumentException(
						"data source must have at least 1 dimension");

		if (c0 == c1)
			throw new IllegalArgumentException("same coordinate axis specified twice");
		
		if (c0 >= c1)
			throw new IllegalArgumentException(
					"axis specified out of order: all numbers assume left to right declaration");
		
		if (c0 < 0 || c0 >= numD)
			throw new IllegalArgumentException("coordinate component 0 is outside number of dimensions");
		
		if (c1 < 0 || ((numD == 1 && c1 > 1) || (numD > 1 && c1 >= numD)))
			throw new IllegalArgumentException("coordinate component 1 is outside number of dimensions");

		this.data = data;
		
		this.c0 = c0;
		this.c1 = c1;

		this.d0 = data.dimension(c0);
		this.d1 = numD == 1 ? 1 : data.dimension(c1);

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
		default:
			throw new IllegalArgumentException(""+numD+" dimensions not yet supported in PlaneView");
		}
	}

	/**
	 * Returns the {@link DimensionedDataSource} that the PlaneView is attached to.
	 */
	public DimensionedDataSource<U> getDataSource() {
		return data;
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
	public int c0() {
		return c0;
	}

	/**
	 * Return the column number of 1th index view position
	 * (i.e. which column is "y" in the view).
	 */
	public int c1() {
		return c1;
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
		if (d == 0) return d0;
		if (d == 1) return d1;
		throw new IllegalArgumentException("dimension out of bounds");
	}

	/**
	 * Returns the number of dimensions beyond 2 that this Planeview can manipulate.
	 * @return
	 */
	public int getExtraDimsCount() {
		return accessor.getExtraDimsCount();
	}
	
	/**
	 * Set the position value of one of the dimensions of the PlaneView
	 */
	public void setExtraDimValue(int i, long v) {
		accessor.setExtraDimValue(i, v);
	}

	/**
	 * Get the position value of one of the dimensions of the PlaneView
	 */
	public long getExtraDimValue(int i) {
		return accessor.getExtraDimValue(i);
	}

	// ----------------------------------------------------------------------
	//   PRIVATE DECLARATIONS FOLLOW
	// ----------------------------------------------------------------------
	
	private boolean outOfBounds(long i0, long i1) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		return false;
	}

	private interface Accessor<X> {

		void set(long i0, long i1, X value);
		
		void get(long i0, long i1, X value);
		
		int getExtraDimsCount();
		
		void setExtraDimValue(int i, long v);
		
		long getExtraDimValue(int i);
	}

	private abstract class AccessorBase {
		
		private long[] extraDimPositions;
		
		AccessorBase(int numD) {
			int size = numD - 2;
			if (size < 0) size = 0;
			extraDimPositions = new long[size]; 
		}

		public int getExtraDimsCount() {
			return extraDimPositions.length;
		}
		
		public void setExtraDimValue(int i, long v) {
			if (i < 0 || i >= extraDimPositions.length)
				throw new IllegalArgumentException("illegal extra dim position");
			extraDimPositions[i] = v;
		}
		
		public long getExtraDimValue(int i) {
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
			}
			else if (c0 == 1 && c1 == 2) {
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 3d "+c0+" "+c1);
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
				u3 = getExtraDimValue(1);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
				u3 = getExtraDimValue(1);
			}
			else if (c0 == 0 && c1 == 3) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = i1;
			}
			else if (c0 == 1 && c1 == 2) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getExtraDimValue(1);
			}
			else if (c0 == 1 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = i1;
			}
			else if (c0 == 2 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 4d "+c0+" "+c1);
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
			}
			else if (c0 == 0 && c1 == 3) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
			}
			else if (c0 == 0 && c1 == 4) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
			}
			else if (c0 == 1 && c1 == 2) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
			}
			else if (c0 == 1 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
			}
			else if (c0 == 1 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
			}
			else if (c0 == 2 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getExtraDimValue(2);
			}
			else if (c0 == 2 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = i1;
			}
			else if (c0 == 3 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 5d "+c0+" "+c1);
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 0 && c1 == 3) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 0 && c1 == 4) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 0 && c1 == 5) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
			}
			else if (c0 == 1 && c1 == 2) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 1 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 1 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 1 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
			}
			else if (c0 == 2 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 2 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 2 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
			}
			else if (c0 == 3 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getExtraDimValue(3);
			}
			else if (c0 == 3 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = i1;
			}
			else if (c0 == 4 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 6d "+c0+" "+c1);
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 0 && c1 == 3) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 0 && c1 == 4) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 0 && c1 == 5) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 0 && c1 == 6) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
			}
			else if (c0 == 1 && c1 == 2) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 1 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 1 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 1 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 1 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
			}
			else if (c0 == 2 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 2 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 2 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 2 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
			}
			else if (c0 == 3 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 3 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 3 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
			}
			else if (c0 == 4 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getExtraDimValue(4);
			}
			else if (c0 == 4 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = getExtraDimValue(4);
				u6 = i1;
			}
			else if (c0 == 5 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = i0;
				u6 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 7d "+c0+" "+c1);
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 0 && c1 == 3) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 0 && c1 == 4) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 0 && c1 == 5) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 0 && c1 == 6) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 0 && c1 == 7) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
			}
			else if (c0 == 1 && c1 == 2) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 1 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 1 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 1 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 1 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 1 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
			}
			else if (c0 == 2 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 2 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 2 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 2 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 2 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
			}
			else if (c0 == 3 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 3 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 3 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 3 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
			}
			else if (c0 == 4 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 4 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 4 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
			}
			else if (c0 == 5 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = i0;
				u6 = i1;
				u7 = getExtraDimValue(5);
			}
			else if (c0 == 5 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = i0;
				u6 = getExtraDimValue(5);
				u7 = i1;
			}
			else if (c0 == 6 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = getExtraDimValue(5);
				u6 = i0;
				u7 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 8d "+c0+" "+c1);
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
			if (c0 == 0 && c1 == 1) {
				u0 = i0;
				u1 = i1;
				u2 = getExtraDimValue(0);
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 2) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 3) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 4) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 5) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 6) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 7) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 0 && c1 == 8) { 
				u0 = i0;
				u1 = getExtraDimValue(0);
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 1 && c1 == 2) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = i1;
				u3 = getExtraDimValue(1);
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 1 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 1 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 1 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 1 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 1 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 1 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = i0;
				u2 = getExtraDimValue(1);
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 2 && c1 == 3) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = i1;
				u4 = getExtraDimValue(2);
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 2 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 2 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 2 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 2 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 2 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = i0;
				u3 = getExtraDimValue(2);
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 3 && c1 == 4) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = i1;
				u5 = getExtraDimValue(3);
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 3 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 3 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 3 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 3 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = i0;
				u4 = getExtraDimValue(3);
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 4 && c1 == 5) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = i1;
				u6 = getExtraDimValue(4);
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 4 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = getExtraDimValue(4);
				u6 = i1;
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 4 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 4 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = i0;
				u5 = getExtraDimValue(4);
				u6 = getExtraDimValue(5);
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 5 && c1 == 6) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = i0;
				u6 = i1;
				u7 = getExtraDimValue(5);
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 5 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = i0;
				u6 = getExtraDimValue(5);
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 5 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = i0;
				u6 = getExtraDimValue(5);
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 6 && c1 == 7) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = getExtraDimValue(5);
				u6 = i0;
				u7 = i1;
				u8 = getExtraDimValue(6);
			}
			else if (c0 == 6 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = getExtraDimValue(5);
				u6 = i0;
				u7 = getExtraDimValue(6);
				u8 = i1;
			}
			else if (c0 == 7 && c1 == 8) { 
				u0 = getExtraDimValue(0);
				u1 = getExtraDimValue(1);
				u2 = getExtraDimValue(2);
				u3 = getExtraDimValue(3);
				u4 = getExtraDimValue(4);
				u5 = getExtraDimValue(5);
				u6 = getExtraDimValue(6);
				u7 = i0;
				u8 = i1;
			}
			else
				throw new IllegalArgumentException("missing coordinate combo for 8d "+c0+" "+c1);
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
}