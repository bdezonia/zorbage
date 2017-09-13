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
package zorbage.type.data.float64.octonion;

import java.io.IOException;
import java.io.RandomAccessFile;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class OctonionFloat64Member
	implements DoubleCoder<OctonionFloat64Member>
{

	private double r, i, j, k, l, i0, j0, k0;
	
	public OctonionFloat64Member() {
		r = i = j = k = l = i0 = j0 = k0 = 0;
	}
	
	public OctonionFloat64Member(double r, double i, double j, double k, double l, double i0, double j0, double k0) {
		this.r = r;
		this.i = i;
		this.j = j;
		this.k = k;
		this.l = l;
		this.i0 = i0;
		this.j0 = j0;
		this.k0 = k0;
	}
	
	public OctonionFloat64Member(OctonionFloat64Member value) {
		r = value.r;
		i = value.i;
		j = value.j;
		k = value.k;
		l = value.l;
		i0 = value.i0;
		j0 = value.j0;
		k0 = value.k0;
	}

	public OctonionFloat64Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		r = val.r().doubleValue();
		i = val.i().doubleValue();
		j = val.j().doubleValue();
		k = val.k().doubleValue();
		l = val.l().doubleValue();
		i0 = val.i0().doubleValue();
		j0 = val.j0().doubleValue();
		k0 = val.k0().doubleValue();
	}

	public double r() { return r; }
	
	public double i() { return i; }
	
	public double j() { return j; }
	
	public double k() { return k; }
	
	public double l() { return l; }
	
	public double i0() { return i0; }
	
	public double j0() { return j0; }
	
	public double k0() { return k0; }
	
	public void setR(double val) { r = val; }
	
	public void setI(double val) { i = val; }
	
	public void setJ(double val) { j = val; }
	
	public void setK(double val) { k = val; }
	
	public void setL(double val) { l = val; }
	
	public void setI0(double val) { i0 = val; }
	
	public void setJ0(double val) { j0 = val; }
	
	public void setK0(double val) { k0 = val; }
	
	public void set(OctonionFloat64Member other) {
		if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
		l = other.l;
		i0 = other.i0;
		j0 = other.j0;
		k0 = other.k0;
	}

	public void get(OctonionFloat64Member other) {
		if (this == other) return;
		other.r = r;
		other.i = i;
		other.j = j;
		other.k = k;
		other.l = l;
		other.i0 = i0;
		other.j0 = j0;
		other.k0 = k0;
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
		builder.append(',');
		builder.append(l);
		builder.append(',');
		builder.append(i0);
		builder.append(',');
		builder.append(j0);
		builder.append(',');
		builder.append(k0);
		builder.append(')');
		return builder.toString();
	}

	@Override
	public int doubleCount() {
		return 8;
	}

	@Override
	public void toValue(double[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
		l = arr[index+4];
		i0 = arr[index+5];
		j0 = arr[index+6];
		k0 = arr[index+7];
	}

	@Override
	public void toArray(double[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
		arr[index+4] = l;
		arr[index+5] = i0;
		arr[index+6] = j0;
		arr[index+7] = k0;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		r = raf.readDouble();
		i = raf.readDouble();
		j = raf.readDouble();
		k = raf.readDouble();
		l = raf.readDouble();
		i0 = raf.readDouble();
		j0 = raf.readDouble();
		k0 = raf.readDouble();
	}

	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeDouble(r);
		raf.writeDouble(i);
		raf.writeDouble(j);
		raf.writeDouble(k);
		raf.writeDouble(l);
		raf.writeDouble(i0);
		raf.writeDouble(j0);
		raf.writeDouble(k0);
	}

}
