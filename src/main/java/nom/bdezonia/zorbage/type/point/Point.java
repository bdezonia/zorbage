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
package nom.bdezonia.zorbage.type.point;

import java.lang.Integer;
import java.nio.ByteBuffer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64VectorMember;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Point
	implements ByteCoder, DoubleCoder, Settable<Point>, Gettable<Point>,
		Allocatable<Point>, Duplicatable<Point>, NumberMember<Point>,
		SetFromDouble, GetAsDoubleArray
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

	public Point(double... vals) {
		this(vals.length);
		setFromDouble(vals);
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
		if (other.vector == null || other.vector.length != this.vector.length)
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
		if (o instanceof Point) {
			return G.POINT.isEqual().call(this, (Point) o);
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0; i < vector.length; i++) {
			if (i != 0)
				b.append(',');
			b.append(vector[i]);
		}
		b.append(']');
		return b.toString();
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative index error");
		if (d < numDimensions())
			return 1;
		return 0;
	}

	@Override
	public void getV(Point value) {
		get(value);
	}

	@Override
	public void setV(Point value) {
		set(value);
	}

	@Override
	public void setFromDouble(double... vals) {
		if (vals.length != vector.length) {
			vector = vals.clone();
		}
		else {
			for (int i = 0; i < vals.length; i++) {
				vector[i] = vals[i];
			}
		}
	}

	@Override
	public double[] getAsDoubleArray() {
		return vector.clone();
	}
}
