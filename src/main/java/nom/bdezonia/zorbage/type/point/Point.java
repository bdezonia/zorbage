/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.point;

import java.lang.Integer;
import java.nio.ByteBuffer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.misc.RealUtils;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64VectorMember;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Point
	implements ByteCoder, DoubleCoder, Settable<Point>, Gettable<Point>, DimensionCount,
		Allocatable<Point>, Duplicatable<Point>, Tolerance<Float64Member,Point>
{
	private double[] vector;
	
	public Point(int dimension) {
		this.vector = new double[dimension];
	}
	
	public Point() {
		this(0);
	}
	
	public Point(Point other) {
		set(other);
	}

	public Point(String str) {
		Float64Member val = G.DBL.construct();
		Float64VectorMember vec = new Float64VectorMember(str);
		if (vec.length() > Integer.MAX_VALUE)
			throw new IllegalArgumentException("string has too many components to fit in a Point");
		this.vector = new double[(int)vec.length()];
		for (int i = 0; i < this.vector.length; i++) {
			vec.getV(i, val);
			setComponent(i, val.v());
		}
	}

	@Override
	public int numDimensions() {
		return this.vector.length;
	}

	public double component(int i) {
		return this.vector[i];
	}
	
	public void setComponent(int i, double value) {
		this.vector[i] = value;
	}
	
	@Override
	public int byteCount() {
		return 4 + numDimensions() * 8;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.put(0, arr[index+0]);
		buff.put(1, arr[index+1]);
		buff.put(2, arr[index+2]);
		buff.put(3, arr[index+3]);
		int n = buff.getInt();
		if (this.numDimensions() != n) {
			this.vector = new double[n];
		}
		buff = ByteBuffer.allocate(8);
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < 8; i++) {
				buff.put(i, arr[index+4+(k*8)+i]); 
			}
			this.vector[k] = buff.getDouble();
		}
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		ByteBuffer buff = ByteBuffer.allocate(4);
		byte[] bytes = buff.putInt(this.vector.length).array();
		for (int i = 0; i < 4; i++) {
			arr[index+i] = bytes[i];
		}
		buff = ByteBuffer.allocate(8);
		for (int k = 0; k < this.vector.length; k++) {
			bytes = buff.putDouble(this.vector[k]).array();
			for (int i = 0; i < 8; i++) {
				arr[index+4+(k*8)+i] = bytes[i];
			}
		}
	}

	@Override
	public int doubleCount() {
		return numDimensions() + 1;
	}

	@Override
	public void fromDoubleArray(double[] arr, int index) {
		int dim = (int) arr[index];
		if (dim != numDimensions()) {
			this.vector = new double[dim];
		}
		for (int i = 0; i < dim; i++) {
			this.vector[i] = arr[index + 1 + i];
		}
	}

	@Override
	public void toDoubleArray(double[] arr, int index) {
		arr[index] = numDimensions();
		for (int i = 0; i < numDimensions(); i++) {
			arr[index + 1 + i] = this.vector[i];
		}
	}

	@Override
	public void get(Point other) {
		if (this == other) return;
		if (other.vector.length != this.vector.length)
			other.vector = this.vector.clone();
		else {
			for (int i = 0; i < this.vector.length; i++) {
				other.vector[i] = this.vector[i];
			}
		}
	}

	@Override
	public void set(Point other) {
		if (this == other) return;
		if (this.vector == null || this.vector.length != other.vector.length)
			this.vector = other.vector.clone();
		else {
			for (int i = 0; i < other.vector.length; i++) {
				this.vector[i] = other.vector[i];
			}
		}
	}

	@Override
	public Point allocate() {
		return new Point(numDimensions());
	}

	@Override
	public Point duplicate() {
		Point p = allocate();
		for (int i = 0; i < this.vector.length; i++) {
			p.setComponent(i, this.vector[i]);
		}
		return p;
	}

	private final Function3<Boolean, Float64Member, Point, Point> WITHIN =
			new Function3<Boolean, Float64Member, Point, Point>()
	{
		
		@Override
		public Boolean call(Float64Member tol, Point a, Point b) {
			if (a.vector.length != b.vector.length)
				throw new IllegalArgumentException("input points do not have the same dimension");
			for (int i = 0; i < a.vector.length; i++) {
				if (RealUtils.near(a.vector[i], b.vector[i], tol.v()))
					return false;
			}
			return true;
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Point, Point> within() {
		return WITHIN;
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(vector.length);
		for (int i = 0; i < vector.length; i++) {
			v = Hasher.PRIME * v + Hasher.hashCode(vector[i]);
		}
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof Point) {
			return G.POINT.isEqual().call(this, (Point) o);
		}
		return false;
	}
}
