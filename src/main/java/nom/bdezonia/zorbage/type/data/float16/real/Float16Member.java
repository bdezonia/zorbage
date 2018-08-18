/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.float16.real;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;

// https://en.wikipedia.org/wiki/Half-precision_floating-point_format

// TODO write conversion tests

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float16Member
	implements
	ShortCoder<Float16Member>,
	Allocatable<Float16Member>, Duplicatable<Float16Member>,
	Settable<Float16Member>, Gettable<Float16Member>,
	UniversalRepresentation, PrimitiveConversion
{
	private static final double SUBNORMAL_BOUND = Math.pow(2,-14);
	private static final short MAX_BOUND_I = (short) 0b0111101111111111;
	private static final short MIN_BOUND_I = (short) 0b1111101111111111;
	private static final double MAX_BOUND_R = toDouble(MAX_BOUND_I);
	private static final double MIN_BOUND_R = toDouble(MIN_BOUND_I);
	
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
		G.DBL.assign().call(other.v, v);
	}
	
	@Override
	public void get(Float16Member other) {
		G.DBL.assign().call(v, other.v);
	}
	
	@Override
	public String toString() {
		// make sure we don't stray from values of format
		return String.valueOf(toDouble(toShort(v())));
	}
	
	@Override
	public int shortCount() {
		return 1;
	}
	
	@Override
	public void fromShortArray(short[] arr, int index) {
		v.setV(toDouble(arr[index]));
	}
	
	@Override
	public void toShortArray(short[] arr, int index) {
		arr[index] = toShort(v());
	}
	
	@Override
	public void fromShortFile(RandomAccessFile raf) throws IOException {
		v.setV(toDouble(raf.readShort()));
	}
	
	@Override
	public void toShortFile(RandomAccessFile raf) throws IOException {
		raf.writeShort(toShort(v()));
	}
	
	@Override
	public Float16Member allocate() {
		return new Float16Member();
	}
	
	@Override
	public Float16Member duplicate() {
		return new Float16Member(this);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		double val = toDouble(toShort(v()));
		rep.setValue(new OctonionRepresentation(BigDecimal.valueOf(val)));
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		setV(rep.getValue().r().doubleValue());
	}

	static short toShort(double value) {
		if (value == Double.NEGATIVE_INFINITY) return (short) 0b1111110000000000;
			
		if (value == Double.POSITIVE_INFINITY) return (short) 0b0111110000000000;
			
		if (Double.isNaN(value)) return (short) 0b0111111111111111;
		
		if (value == +0) return (short) 0b0000000000000000;
		
		if (value == -0) return (short) 0b1000000000000000;

		if (value <= MIN_BOUND_R) return MIN_BOUND_I;
					
		if (value >= MAX_BOUND_R) return MAX_BOUND_I;
					
		// if here its a number in representational range
		
// https://stackoverflow.com/questions/6162651/half-precision-floating-point-in-java/6162687#6162687
		
		int sign = (value < 0 ? 0b1000000000000000 : 0);
		value = Math.abs(value);
		if (value < SUBNORMAL_BOUND) {
			// encode as a subnormal number
			double tmp = value / SUBNORMAL_BOUND;
			double frac = 0.5;
			int mask = 512;
			int signif = 0;
			while (mask > 0) {
				if (tmp >= frac) {
					signif |= mask;
					tmp -= frac;
				}
				mask >>= 1;
				frac /= 2.0;
			}
			return (short) (sign | signif);
		}
		else {
			// encode as a normalized number
			// sign already set
			// exp ranges -14 to 15  (1 to 30)
			int exp = (int) Math.floor(Math.log(value) / Math.log(2));
			if (exp < -14) throw new IllegalArgumentException("whoops 1");
			if (exp > 15) throw new IllegalArgumentException("whoops 2");
			value = value / Math.pow(2,exp);
			double tmp = value - 1.0;
			double frac = 0.5;
			int mask = 512;
			int signif = 0;
			while (mask > 0) {
				if (tmp >= frac) {
					signif |= mask;
					tmp -= frac;
				}
				mask >>= 1;
				frac /= 2.0;
			}
			return (short) (sign | ((exp + 15) << 10) | signif);
		}
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
			frac /= 2.0;
		}
		return result;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
	}

	@Override
	public long dimension(int i) {
		return 0;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		setV(toDouble(toShort(v)));
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		setV(toDouble(toShort(v)));
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		setV(toDouble(toShort(v)));
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		setV(toDouble(toShort(v)));
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		setV(toDouble(toShort(v)));
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		setV(toDouble(toShort(v)));
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		setV(toDouble(toShort(v.doubleValue())));
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		setV(toDouble(toShort(v.doubleValue())));
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v)));
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v)));
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v)));
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v)));
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v)));
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v)));
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v.doubleValue())));
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(toDouble(toShort(v.doubleValue())));
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (byte) toDouble(toShort(v()));
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (short) toDouble(toShort(v()));
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (int) toDouble(toShort(v()));
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (long) toDouble(toShort(v()));
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (float) toDouble(toShort(v()));
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return toDouble(toShort(v()));
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigInteger.valueOf((long)toDouble(toShort(v())));
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return new BigDecimal(toDouble(toShort(v())));
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return (byte) toDouble(toShort(v()));
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return (short) toDouble(toShort(v()));
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return (int) toDouble(toShort(v()));
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return (long) toDouble(toShort(v()));
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return (float) toDouble(toShort(v()));
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return toDouble(toShort(v()));
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigInteger.ZERO;
		}
		else {
			return BigInteger.valueOf((long)toDouble(toShort(v())));
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigDecimal.ZERO;
		}
		else {
			return BigDecimal.valueOf(toDouble(toShort(v())));
		}
	}

	@Override
	public void primitiveInit() {
		setV(0);  // TODO is this good enough? As used by primconvert yes.
		          //   But maybe not if used generally.
	}
	
}
