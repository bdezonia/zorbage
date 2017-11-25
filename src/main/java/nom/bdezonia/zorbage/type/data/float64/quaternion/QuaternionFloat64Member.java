/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.data.float64.quaternion;

import java.io.IOException;
import java.io.RandomAccessFile;

import zorbage.type.algebra.Gettable;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;
import zorbage.type.ctor.Duplicatable;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.DoubleCoder;

// TODO - do we nest Float64Members inside Quat<Float64Member>? Is this even possible?

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionFloat64Member 
	implements
		DoubleCoder<QuaternionFloat64Member>,
		Allocatable<QuaternionFloat64Member>, Duplicatable<QuaternionFloat64Member>,
		Settable<QuaternionFloat64Member>, Gettable<QuaternionFloat64Member>
{

	private double r, i, j, k;
	
	public QuaternionFloat64Member() {
		r = i = j = k = 0;
	}
	
	public QuaternionFloat64Member(double r, double i, double j, double k) {
		this.r = r;
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	public QuaternionFloat64Member(QuaternionFloat64Member value) {
		r = value.r;
		i = value.i;
		j = value.j;
		k = value.k;
	}

	public QuaternionFloat64Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		r = val.r().doubleValue();
		i = val.i().doubleValue();
		j = val.j().doubleValue();
		k = val.k().doubleValue();
	}

	public double r() { return r; }
	
	public double i() { return i; }
	
	public double j() { return j; }
	
	public double k() { return k; }
	
	public void setR(double val) { r = val; }
	
	public void setI(double val) { i = val; }
	
	public void setJ(double val) { j = val; }
	
	public void setK(double val) { k = val; }
	
	@Override
	public void set(QuaternionFloat64Member other) {
		if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
	}

	@Override
	public void get(QuaternionFloat64Member other) {
		if (this == other) return;
		other.r = r;
		other.i = i;
		other.j = j;
		other.k = k;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		builder.append(r);
		builder.append(',');
		builder.append(i);
		builder.append(',');
		builder.append(j);
		builder.append(',');
		builder.append(k);
		builder.append(')');
		return builder.toString();
	}

	@Override
	public int doubleCount() {
		return 4;
	}

	@Override
	public void toValue(double[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
	}

	@Override
	public void toArray(double[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		r = raf.readDouble();
		i = raf.readDouble();
		j = raf.readDouble();
		k = raf.readDouble();
	}

	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeDouble(r);
		raf.writeDouble(i);
		raf.writeDouble(j);
		raf.writeDouble(k);
	}

	@Override
	public QuaternionFloat64Member allocate() {
		return new QuaternionFloat64Member();
	}

	@Override
	public QuaternionFloat64Member duplicate() {
		return new QuaternionFloat64Member(this);
	}

}
