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

import zorbage.groups.G;
import zorbage.type.algebra.Gettable;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;
import zorbage.type.ctor.Duplicatable;
import zorbage.type.data.float64.real.Float64Member;
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
	//private static final double SMALL = Math.pow(2,-14);
	
	private Float64Member v;
	
	public Float16Member() {
		v = new Float64Member();
	}
	
	public Float16Member(double value) {
		value = toDouble(toShort(value));
		v = new Float64Member(value);
	}
	
	public Float16Member(Float16Member value) {
		v = new Float64Member(value.v);
	}
	
	public Float16Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		v = new Float64Member(toDouble(toShort(val.r().doubleValue())));
	}
	
	public double v() { return v.v(); }
	
	// Note: I am going to let internal values stray from Float16 format for performance reasons.
	// Only deal with format conversions when writing to or reading from a Float16 container.
	
	public void setV(double val) { v.setV(val); }
	
	@Override
	public void set(Float16Member other) {
		G.DBL.assign(other.v, v);
	}
	
	@Override
	public void get(Float16Member other) {
		G.DBL.assign(v, other.v);
	}
	
	@Override
	public String toString() {
		// make sure we don't stray from values of format
		return String.valueOf(toDouble(toShort(v.v())));
	}
	
	@Override
	public int shortCount() {
		return 1;
	}
	
	@Override
	public void toValue(short[] arr, int index) {
		v.setV(toDouble(arr[index]));
	}
	
	@Override
	public void toArray(short[] arr, int index) {
		arr[index] = toShort(v.v());
	}
	
	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		v.setV(toDouble(raf.readShort()));
	}
	
	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeShort(toShort(v.v()));
	}
	
	@Override
	public Float16Member allocate() {
		return new Float16Member();
	}
	
	@Override
	public Float16Member duplicate() {
		return new Float16Member(this);
	}

	static short toShort(double value) {
		if (value == Double.NEGATIVE_INFINITY)
			return (short) 0b1111110000000000;
			
		if (value == Double.POSITIVE_INFINITY)
			return (short) 0b0111110000000000;
			
		if (Double.isNaN(value))
			return (short) 0b0111111111111111;
		
		if (value == +0)
			return (short) 0b0000000000000000;
		
		if (value == -0)
			return (short) 0b1000000000000000;

		// if here its a nonspecial number

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
				return (sign != 0 ? -1 : 1) * Math.pow(2, -14) * (fraction(significand));
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
		else // exponent: 1 <= exponent <= 30
		{
			// normalized numbers
			return (sign != 0 ? -1 : 1) * Math.pow(2, exponent-15) * (1.0 + fraction(significand));
		}
	}
	
	private static double fraction(int significand) {
		double result = 0;
		double frac = 0.5;
		int mask = 512;
		while (mask > 0) {
			if ((significand & mask) > 0)
				result += frac;
			mask >>= 1;
			frac /= 2;
		}
		return result;
	}
	
}
