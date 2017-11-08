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
package zorbage.type.data.float16.real;

import java.io.IOException;
import java.io.RandomAccessFile;

import zorbage.type.algebra.Gettable;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;
import zorbage.type.ctor.Duplicatable;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.ShortCoder;

// https://en.wikipedia.org/wiki/Half-precision_floating-point_format

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float16Member
	implements
	ShortCoder<Float16Member>,
	Allocatable<Float16Member>, Duplicatable<Float16Member>,
	Settable<Float16Member>, Gettable<Float16Member>
{
	private short v;
	
	public Float16Member() {
		v = 0;
	}
	
	public Float16Member(double value) {
		v = toFloat16(value);
	}
	
	public Float16Member(Float16Member value) {
		v = value.v;
	}
	
	public Float16Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		v = toFloat16(val.r().doubleValue());
	}
	
	public double v() { return toDouble(v); }
	
	public void setV(double val) { v = toFloat16(val); }
	
	@Override
	public void set(Float16Member other) {
		v = other.v;
	}
	
	@Override
	public void get(Float16Member other) {
		other.v = v;
	}
	
	@Override
	public String toString() {
		return String.valueOf(toDouble(v));
	}
	
	@Override
	public int shortCount() {
		return 1;
	}
	
	@Override
	public void toValue(short[] arr, int index) {
		v = arr[index];
	}
	
	@Override
	public void toArray(short[] arr, int index) {
		arr[index] = v;
	}
	
	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		v = raf.readShort();
	}
	
	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeShort(v);
	}
	
	@Override
	public Float16Member allocate() {
		return new Float16Member();
	}
	
	@Override
	public Float16Member duplicate() {
		return new Float16Member(this);
	}

	static short toFloat16(double value) {
		throw new IllegalArgumentException("TODO");
	}
	
	static double toDouble(short value) {
		int exponent = ((value & 0b0111110000000000) >> 10);
		int significand = (value & 0b0000001111111111);
		int sign = (value & 0b1000000000000000) >>> 15;
		if (exponent == 0){
			if (significand == 0) {
				if (sign > 0)
					return -0;
				else
					return +0;
			}
			else {
				// subnormal numbers
				return Math.pow(-1, sign) * Math.pow(2, -14) * (fraction(significand));
			}
		}
		else if (exponent == 31) {
			if (significand == 0) {
				if (sign > 0)
					return Double.NEGATIVE_INFINITY;
				else
					return Double.POSITIVE_INFINITY;
			}
			else { // significand != 0
				return Double.NaN;
			}
		}
		else // exponent 0 < exponent < 31
		{
			// normalized numbers
			return Math.pow(-1, sign) * Math.pow(2, exponent-15) * (1.0 + fraction(significand));
		}
	}
	
	private static double fraction(int significand) {
		double result = 0;
		double frac = 0.5;
		int mask = 1024;
		while (mask > 0) {
			if ((significand & mask) > 0)
				result += frac;
			mask = mask >> 1;
			frac = frac / 2;
		}
		return result;
	}
	
}
