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
import nom.bdezonia.zorbage.procedure.Procedure3;

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
	private final Procedure3<Long,Long,U> setter;
	private final Procedure3<Long,Long,U> getter;

	/**
	 * Construct a view from an {@link IndexedDataSource} and some dimensions.
	 * 
	 * @param data The n-d data source the view is being built around.
	 * @param c0 The "x" component in the data source
	 * @param c1 The "y" component in the data source
	 */
	public PlaneView(DimensionedDataSource<U> data, int c0, int c1, long ... fixedComponents) {
		int numD = data.numDimensions();
		
		if (numD < 2)
			throw new IllegalArgumentException("data source must be 2d or greater");
		
		if (numD - fixedComponents.length != 2)
			throw new IllegalArgumentException("specified data source would not be 2d");
		
		if (c0 == c1)
			throw new IllegalArgumentException("same plane axis specified twice");
		
		if (c0 >= c1)
			throw new IllegalArgumentException(
					"axis specified out of order: all numbers assume left to right declaration");
		
		if (c0 < 0 || c0 >= numD)
			throw new IllegalArgumentException("plane component 0 is outside number of dimensions");
		
		if (c1 < 0 || c1 >= numD)
			throw new IllegalArgumentException("plane component 1 is outside number of dimensions");

		this.c0 = c0;
		this.c1 = c1;

		this.d0 = data.dimension(c0);
		this.d1 = data.dimension(c1);
		
		setter = chooseSetter(data, fixedComponents);
		getter = chooseGetter(data, fixedComponents);
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
		getter.call(i0, i1, val);
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
		setter.call(i0, i1, val);
	}

	/**
	 * A plneView.safeGet() call will do a get() call provided the
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

	private boolean outOfBounds(long i0, long i1) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		return false;
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

	private Procedure3<Long, Long, U> chooseGetter(DimensionedDataSource<U> data, long[] fixedComponents) {
		
		if (data.numDimensions() == 2) {
			return new Procedure3<Long, Long, U>() {
				
				private final TwoDView<U> view = new TwoDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					view.get(i0, i1, val);
				}
			};
		}
		if (data.numDimensions() == 3) {
			return new Procedure3<Long, Long, U>() {
				
				private final ThreeDView<U> view = new ThreeDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
					}
					else {  // (c0 == 1 && c1 == 2)
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
					}
					view.get(u0, u1, u2, val);
				}
			};
		}
		if (data.numDimensions() == 4) {
			return new Procedure3<Long, Long, U>() {
				
				private final FourDView<U> view = new FourDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
					}
					view.get(u0, u1, u2, u3, val);
				}
			};
		}
		if (data.numDimensions() == 5) {
			return new Procedure3<Long, Long, U>() {
				
				private final FiveDView<U> view = new FiveDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
					}
					view.get(u0, u1, u2, u3, u4, val);
				}
			};
		}
		if (data.numDimensions() == 6) {
			return new Procedure3<Long, Long, U>() {
				
				private final SixDView<U> view = new SixDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					long u5 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 5) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 1 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 2 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 3 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 4 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = i1;
					}
					view.get(u0, u1, u2, u3, u4, u5, val);
				}
			};
		}
		if (data.numDimensions() == 7) {
			return new Procedure3<Long, Long, U>() {
				
				private final SevenDView<U> view = new SevenDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					long u5 = -1;
					long u6 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 5) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 6) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 2 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 2 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 3 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 3 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 4 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 4 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 5 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = i0;
						u6 = i1;
					}
					view.get(u0, u1, u2, u3, u4, u5, u6, val);
				}
			};
		}
		if (data.numDimensions() == 8) {
			return new Procedure3<Long, Long, U>() {
				
				private final EightDView<U> view = new EightDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					long u5 = -1;
					long u6 = -1;
					long u7 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 5) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 6) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 7) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 3 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 3 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 3 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 4 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 4 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 4 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 5 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = i0;
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 5 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = i0;
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 5 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = fixedComponents[5];
						u6 = i0;
						u7 = i1;
					}
					view.get(u0, u1, u2, u3, u4, u5, u6, u7, val);
				}
			};
		}
		if (data.numDimensions() == 9) {
			
		}
		if (data.numDimensions() == 10) {
			
		}
		if (data.numDimensions() == 11) {
			
		}
		if (data.numDimensions() == 12) {
			
		}
		throw new IllegalArgumentException(
				"PlaneView only works on data sets with between 2 and 8 dimensions at the moment");
	}
	
	private Procedure3<Long, Long, U> chooseSetter(DimensionedDataSource<U> data, long[] fixedComponents) {
		if (data.numDimensions() == 2) {
			return new Procedure3<Long, Long, U>() {
				
				private final TwoDView<U> view = new TwoDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					view.set(i0, i1, val);
				}
			};
		}
		if (data.numDimensions() == 3) {
			return new Procedure3<Long, Long, U>() {
				
				private final ThreeDView<U> view = new ThreeDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
					}
					else {  // (c0 == 1 && c1 == 2)
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
					}
					view.set(u0, u1, u2, val);
				}
			};
		}
		if (data.numDimensions() == 4) {
			return new Procedure3<Long, Long, U>() {
				
				private final FourDView<U> view = new FourDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
					}
					view.set(u0, u1, u2, u3, val);
				}
			};
		}
		if (data.numDimensions() == 5) {
			return new Procedure3<Long, Long, U>() {
				
				private final FiveDView<U> view = new FiveDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
					}
					view.set(u0, u1, u2, u3, u4, val);
				}
			};
		}
		if (data.numDimensions() == 6) {
			return new Procedure3<Long, Long, U>() {
				
				private final SixDView<U> view = new SixDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					long u5 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 0 && c1 == 5) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 1 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 2 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
						u5 = fixedComponents[3];
					}
					else if (c0 == 3 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = i1;
					}
					else if (c0 == 4 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = i1;
					}
					view.set(u0, u1, u2, u3, u4, u5, val);
				}
			};
		}
		if (data.numDimensions() == 7) {
			return new Procedure3<Long, Long, U>() {
				
				private final SevenDView<U> view = new SevenDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					long u5 = -1;
					long u6 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 5) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 0 && c1 == 6) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 1 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 2 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 2 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
					}
					else if (c0 == 3 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 3 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 4 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = i1;
						u6 = fixedComponents[4];
					}
					else if (c0 == 4 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = fixedComponents[4];
						u6 = i1;
					}
					else if (c0 == 5 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = i0;
						u6 = i1;
					}
					view.set(u0, u1, u2, u3, u4, u5, u6, val);
				}
			};
		}
		if (data.numDimensions() == 8) {
			return new Procedure3<Long, Long, U>() {
				
				private final EightDView<U> view = new EightDView<>(data);
				
				@Override
				public void call(Long i0, Long i1, U val) {
					long u0 = -1;
					long u1 = -1;
					long u2 = -1;
					long u3 = -1;
					long u4 = -1;
					long u5 = -1;
					long u6 = -1;
					long u7 = -1;
					if (c0 == 0 && c1 == 1) {
						u0 = i0;
						u1 = i1;
						u2 = fixedComponents[0];
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 2) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 3) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 4) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 5) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 6) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 0 && c1 == 7) { 
						u0 = i0;
						u1 = fixedComponents[0];
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 1 && c1 == 2) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = i1;
						u3 = fixedComponents[1];
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 1 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = i0;
						u2 = fixedComponents[1];
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 2 && c1 == 3) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = i1;
						u4 = fixedComponents[2];
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 2 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = i0;
						u3 = fixedComponents[2];
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 3 && c1 == 4) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = i1;
						u5 = fixedComponents[3];
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 3 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 3 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 3 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = i0;
						u4 = fixedComponents[3];
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 4 && c1 == 5) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = i1;
						u6 = fixedComponents[4];
						u7 = fixedComponents[5];
					}
					else if (c0 == 4 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = fixedComponents[4];
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 4 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = i0;
						u5 = fixedComponents[4];
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 5 && c1 == 6) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = i0;
						u6 = i1;
						u7 = fixedComponents[5];
					}
					else if (c0 == 5 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = i0;
						u6 = fixedComponents[5];
						u7 = i1;
					}
					else if (c0 == 5 && c1 == 7) { 
						u0 = fixedComponents[0];
						u1 = fixedComponents[1];
						u2 = fixedComponents[2];
						u3 = fixedComponents[3];
						u4 = fixedComponents[4];
						u5 = fixedComponents[5];
						u6 = i0;
						u7 = i1;
					}
					view.set(u0, u1, u2, u3, u4, u5, u6, u7, val);
				}
			};
		}
		throw new IllegalArgumentException(
				"PlaneView only works on data sets with between 2 and 8 dimensions at the moment");
	}
}
