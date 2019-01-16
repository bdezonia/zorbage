/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.point;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.algebra.DimensionCount;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64VectorMember;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Point
	implements ByteCoder, DoubleCoder, Settable<Point>, Gettable<Point>, DimensionCount,
		Allocatable<Point>, Duplicatable<Point>
{
	private double[] vector;
	
	public Point(int dimension) {
		vector = new double[dimension];
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
		vector = new double[(int)vec.length()];
		for (int i = 0; i < vector.length; i++) {
			vec.v(i, val);
			setComponent(i, val.v());
		}
	}

	@Override
	public int numDimensions() {
		return vector.length;
	}

	public double component(int i) {
		return vector[i];
	}
	
	public void setComponent(int i, double value) {
		vector[i] = value;
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
		for (int k = 0; k < vector.length; k++) {
			bytes = buff.putDouble(this.vector[k]).array();
			for (int i = 0; i < 8; i++) {
				arr[index+4+(k*8)+i] = bytes[i];
			}
		}
	}

	@Override
	public void fromByteFile(RandomAccessFile raf) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.put(0, raf.readByte());
		buff.put(1, raf.readByte());
		buff.put(2, raf.readByte());
		buff.put(3, raf.readByte());
		int n = buff.getInt();
		if (this.numDimensions() != n) {
			this.vector = new double[n];
		}
		buff = ByteBuffer.allocate(8);
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < 8; i++) {
				buff.put(i, raf.readByte()); 
			}
			this.vector[k] = buff.getDouble();
		}
	}

	@Override
	public void toByteFile(RandomAccessFile raf) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		byte[] bytes = buff.putInt(this.vector.length).array();
		for (int i = 0; i < 4; i++) {
			raf.writeByte(bytes[i]);
		}
		buff = ByteBuffer.allocate(8);
		for (int k = 0; k < vector.length; k++) {
			bytes = buff.putDouble(this.vector[k]).array();
			for (int i = 0; i < 8; i++) {
				raf.writeByte(bytes[i]);
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
	public void fromDoubleFile(RandomAccessFile raf) throws IOException {
		int dim = (int) raf.readDouble();
		if (dim != numDimensions()) {
			this.vector = new double[dim];
		}
		for (int i = 0; i < dim; i++) {
			this.vector[i] = raf.readDouble();
		}
	}

	@Override
	public void toDoubleFile(RandomAccessFile raf) throws IOException {
		raf.writeDouble(numDimensions());
		for (int i = 0; i < numDimensions(); i++) {
			raf.writeDouble(this.vector[i]);
		}
	}

	@Override
	public void get(Point other) {
		if (this == other) return;
		other.vector = new double[numDimensions()];
		for (int i = 0; i < vector.length; i++) {
			other.vector[i] = vector[i];
		}
	}

	@Override
	public void set(Point other) {
		if (this == other) return;
		vector = new double[other.numDimensions()];
		for (int i = 0; i < vector.length; i++) {
			vector[i] = other.vector[i];
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
}
