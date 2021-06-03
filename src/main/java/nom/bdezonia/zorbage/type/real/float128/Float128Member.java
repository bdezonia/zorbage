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
package nom.bdezonia.zorbage.type.real.float128;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetReal;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.HighPrecRepresentation;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetReal;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;

// TODO: I will need to write a bunch of tests and translate known values back and
// forth and expect no drift.

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float128Member
	implements
		NumberMember<Float128Member>,
		ByteCoder,
		Allocatable<Float128Member>, Duplicatable<Float128Member>,
		Settable<Float128Member>, Gettable<Float128Member>,
		UniversalRepresentation, PrimitiveConversion,
		HighPrecRepresentation, SetReal<Float128Member>, GetReal<Float128Member>
{
	BigDecimal num;
	byte classification;

	static final BigDecimal TWO = BigDecimal.valueOf(2);
	static final BigDecimal MAX_NORMAL = TWO.pow(16383).multiply(TWO.subtract(TWO.pow(-112, Float128Algebra.CONTEXT)));
	static final BigDecimal MIN_NORMAL = MAX_NORMAL.negate();
	static final BigDecimal MAX_SUBNORMAL = TWO.pow(-16382, Float128Algebra.CONTEXT).multiply(BigDecimal.ONE.subtract(TWO.pow(-112, Float128Algebra.CONTEXT)));
	static final BigDecimal MIN_SUBNORMAL = TWO.pow(-16382, Float128Algebra.CONTEXT).multiply(TWO.pow(-112, Float128Algebra.CONTEXT));
	//static final BigInteger FULL_RANGE = new BigInteger("ffffffffffffffffffffffffffff",16);
	static final BigInteger FULL_RANGE = new BigInteger( "10000000000000000000000000000",16);

	static final byte NORMAL = 0;
	static final byte POSZERO = 1;
	static final byte NEGZERO = -1;
	static final byte POSINF = 2;
	static final byte NEGINF = -2;
	static final byte NAN = 3;

	public Float128Member() {
		primitiveInit();
	}
	
	public Float128Member(Float128Member other) {
		set(other);
	}

	public Float128Member(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		OctonionRepresentation val = rep.firstValue();
		setV(val.r());
	}

	public Float128Member(BigDecimal v) {
		setV(v);
	}
	
	@Override
	public long dimension(int d) {
		return 0;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void setR(Float128Member val) {
		set(val);
	}

	@Override
	public void getR(Float128Member val) {
		get(val);
	}

	public BigDecimal v() {
		if (classification == POSINF || classification == NEGINF || classification == NAN)
			throw new java.lang.NumberFormatException("nan/posinf/neginf cannot be converted to big decimal");
		return num;
	}
	
	public void setV(BigDecimal v) {
		if (v == null)
			throw new IllegalArgumentException("this class does not allow null values");
		if (v.signum() == 0) {
			setPosZero();
		}
		else {
			setNormal(v);
			clamp();
		}
	}
	
	@Override
	public void toHighPrec(HighPrecisionMember output) {
		if (classification == POSINF || classification == NEGINF || classification == NAN)
			throw new java.lang.NumberFormatException("nan/posinf/neginf cannot encode as a high precision decimal");
		// can use num with classifications NORMAL, POSZERO, and NEGZERO because they are all accurate
		output.setV(num);
	}

	@Override
	public void fromHighPrec(HighPrecisionMember input) {
		setV(input.v());
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		setV(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		setV(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		setV(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		setV(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		setV(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		setV(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		setV(new BigDecimal(v));
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		setV(v);
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
			setV(BigDecimal.valueOf(v));
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
			setV(BigDecimal.valueOf(v));
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
			setV(BigDecimal.valueOf(v));
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
			setV(BigDecimal.valueOf(v));
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
			setV(BigDecimal.valueOf(v));
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
			setV(BigDecimal.valueOf(v));
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
			setV(new BigDecimal(v));
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
			setV(v);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().byteValue();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().shortValue();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().intValue();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().longValue();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().floatValue();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().doubleValue();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v().toBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v();
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
			return v().byteValue();
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
			return v().shortValue();
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
			return v().intValue();
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
			return v().longValue();
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
			return v().floatValue();
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
			return v().doubleValue();
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
			return v().toBigInteger();
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
			return v();
		}
	}

	@Override
	public void primitiveInit() {
		setPosZero();
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(new OctonionRepresentation(v()));
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		setV(rep.getValue().r());
	}

	@Override
	public void get(Float128Member other) {
		other.num = this.num;
		other.classification = this.classification;
	}

	@Override
	public void set(Float128Member other) {
		this.num = other.num;
		this.classification = other.classification;
	}

	@Override
	public Float128Member duplicate() {
		return new Float128Member(this);
	}

	@Override
	public Float128Member allocate() {
		return new Float128Member();
	}

	@Override
	public int byteCount() {
		return 17;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		decode(arr, index);
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		encode(arr, index);
	}

	@Override
	public void getV(Float128Member value) {
		get(value);
	}

	@Override
	public void setV(Float128Member value) {
		set(value);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(num);
		v = Hasher.PRIME * v + Hasher.hashCode(classification);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Float128Member) {
			return G.QUAD.isEqual().call(this, (Float128Member) o);
		}
		return false;
	}
	
	@Override
	public String toString() {
		switch (classification) {
		
		case NORMAL:
			return num.toString();
		
		case POSZERO:
			return "0";
		
		case NEGZERO:
			return "-0";
		
		case POSINF:
			return "Infinity";
		
		case NEGINF:
			return "-Infinity";
		
		case NAN:
			return "NaN";
		
		default:
			throw new IllegalArgumentException("unknown number classification "+classification);
		}
	}

	boolean isNormal() {
		return classification == NORMAL;
	}

	boolean isPosZero() {
		return classification == POSZERO;
	}

	boolean isNegZero() {
		return classification == NEGZERO;
	}

	boolean isPosInf() {
		return classification == POSINF;
	}

	boolean isNegInf() {
		return classification == NEGINF;
	}

	boolean isNan() {
		return classification == NAN;
	}

	boolean isFinite() {
		return classification == NORMAL || classification == POSZERO || classification == NEGZERO;
	}

	boolean isInfinite() {
		return classification == POSINF || classification == NEGINF;
	}

	boolean isPositive() {
		return classification == POSZERO || classification == POSINF ||
				(classification == NORMAL && num.signum() > 0);
	}

	boolean isNegative() {
		return classification == NEGZERO || classification == NEGINF ||
				(classification == NORMAL && num.signum() < 0);
	}

	boolean isZero() {
		return classification == POSZERO || classification == NEGZERO;
	}

	void setNormal(BigDecimal value) {
		num = value;
		classification = NORMAL;
	}

	void setPosZero() {
		num = BigDecimal.ZERO;
		classification = POSZERO;
	}

	void setNegZero() {
		num = BigDecimal.ZERO;
		classification = NEGZERO;
	}

	void setPosInf() {
		num = BigDecimal.ZERO;
		classification = POSINF;
	}

	void setNegInf() {
		num = BigDecimal.ZERO;
		classification = NEGINF;
	}
	
	void setNan() {
		num = BigDecimal.ZERO;
		classification = NAN;
	}

	// Take my denom and BigDecimal values and encode them as a denom and
	//   IEEE128 in the 17 bytes of arr starting at offset
	
	void encode(byte[] arr, int offset) {
		arr[offset] = classification;
		switch (classification) {
		case NORMAL:
			BigDecimal tmp = num.abs();
			int signBit = (num.signum() < 0) ? 0x80 : 0;
			// is it a sub normal?
			if (tmp.compareTo(BigDecimal.ONE) == 0) {
				arr[offset + 1 + 15] = (byte) (signBit | 0x3f);
				arr[offset + 1 + 14] = (byte) 0xf0;
				for (int i = 13; i >= 0; i--) {
					arr[offset + 1 + i] = 0;
				}
			}
			// is it a subnormal?
			else if (tmp.compareTo(MIN_SUBNORMAL) >= 0 && tmp.compareTo(MAX_SUBNORMAL) <= 0) {
				BigDecimal numer = tmp.subtract(MIN_SUBNORMAL);
				BigDecimal denom = MAX_SUBNORMAL.subtract(MIN_SUBNORMAL);
				BigDecimal ratio = numer.divide(denom, Float128Algebra.CONTEXT);
				BigInteger fraction = new BigDecimal(FULL_RANGE).multiply(ratio).toBigInteger();
				arr[offset + 1 + 15] = (byte) signBit;
				arr[offset + 1 + 14] = 0;
				int bitNum = 111;
				for (int i = 13; i >= 0; i--) {
					byte b = 0;
					for (int bitMask = 0x80; bitMask > 0; bitMask >>= 1) {
						if (fraction.testBit(bitNum))
							b |= bitMask;
						bitNum--;
					}
					arr[offset + 1 + i] = b;
				}
			}
			// is it between zero and one?
			else if (tmp.compareTo(BigDecimal.ZERO) >= 0 && tmp.compareTo(BigDecimal.ONE) < 0) {
				// it's a number > 0 and < 1
				int exponent = 0;
				BigDecimal lowerBound = BigDecimal.ONE;
				BigDecimal upperBound = lowerBound;
				while (tmp.compareTo(lowerBound) > 0) {
					upperBound = lowerBound;
					lowerBound = lowerBound.divide(TWO);
					exponent++;
				}
				BigDecimal numer = tmp.subtract(lowerBound);
				BigDecimal denom = upperBound.subtract(lowerBound);
				BigDecimal ratio = numer.divide(denom, Float128Algebra.CONTEXT);
				BigInteger fraction = new BigDecimal(FULL_RANGE).multiply(ratio).toBigInteger();
				exponent += 0x3ffe;
				int ehi = (exponent & 0xff00) >> 8;
				int elo = (exponent & 0x00ff) >> 0;
				arr[offset + 1 + 15] = (byte) (signBit | ehi);
				arr[offset + 1 + 14] = (byte) (elo);
				int bitNum = 111;
				for (int i = 13; i >= 0; i--) {
					byte b = 0;
					for (int bitMask = 0x80; bitMask > 0; bitMask >>= 1) {
						if (fraction.testBit(bitNum))
							b |= bitMask;
						bitNum--;
					}
					arr[offset + 1 + i] = b;
				}
			}
			else {
				// it's a number > 1 and <= MAXBOUND
				int exponent = 0;
				BigDecimal lowerBound = BigDecimal.ONE;
				BigDecimal upperBound = lowerBound;
				while (tmp.compareTo(upperBound) > 0) {
					lowerBound = upperBound;
					upperBound = upperBound.multiply(TWO);
					exponent++;
				}
				BigDecimal numer = tmp.subtract(lowerBound);
				BigDecimal denom = upperBound.subtract(lowerBound);
				BigDecimal ratio = numer.divide(denom, Float128Algebra.CONTEXT);
				BigInteger fraction = new BigDecimal(FULL_RANGE).multiply(ratio).toBigInteger();
				exponent += 0x3ffe;
				int ehi = (exponent & 0xff00) >> 8;
				int elo = (exponent & 0x00ff) >> 0;
				arr[offset + 1 + 15] = (byte) (signBit | ehi);
				arr[offset + 1 + 14] = (byte) (elo);
				int bitNum = 111;
				for (int i = 13; i >= 0; i--) {
					byte b = 0;
					for (int bitMask = 0x80; bitMask > 0; bitMask >>= 1) {
						if (fraction.testBit(bitNum))
							b |= bitMask;
						bitNum--;
					}
					arr[offset + 1 + i] = b;
				}
			}
			break;
			
		case POSZERO:
			// encode a regular (positive) 0
			for (int i = 15; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
			break;
			
		case NEGZERO:
			// encode a negative 0
			arr[offset+1+15] = (byte) 0x80;
			for (int i = 14; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
			break;
			
		case POSINF:
			// +1 / 0
			// encode a positive infinity in the remaining 16 bytes
			arr[offset+1+15] = (byte) 0x7f;
			arr[offset+1+14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
			break;
			
		case NEGINF:
			// -1 / 0
			// encode a negative infinity in the remaining 16 bytes
			arr[offset+1+15] = (byte) 0xff;
			arr[offset+1+14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
			break;
			
		case NAN:
			// 0 / 0
			// encode a NaN in the remaining 16 bytes
			arr[offset+1+15] = (byte) 0x7f;
			arr[offset+1+14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				// any non zero value tells the OS this is a nan rather than an inf
				// TODO : do I have to deal with signaling versus nonsignaling or is
				// that all done in hardware?
				arr[offset+1+i] = 1;
			}
			break;
			
		default:
			throw new IllegalArgumentException("unknown number classification "+classification);
		}
	}
	
	// Take the 17 bytes stored in arr starting at offset and decode them
	//   into my denom and BigDecimal values. Really just decode the last
	//   16 bytes. Assume the 1st byte (the zorbage code) is irrelevant
	//   for decoding purposes.
	
	void decode(byte[] buffer, int offset) {
		
		int sign = 0;
		int exponent = 0;
		BigInteger fraction = BigInteger.ZERO;
		
		sign = (buffer[offset + 1 + 15] & 0x80);
		
		exponent = ((buffer[offset + 1 + 15] & 0x7f) << 8) + (buffer[offset + 1 + 14] & 0xff);
		
		for (int i = 13; i <= 0; i--) {
			fraction = fraction.shiftLeft(8).add(BigInteger.valueOf(buffer[offset + 1 + i] & 0xff));
		}
		
		if (exponent > 0 || exponent < 0x7fff) {

			// a regular number
			
			BigDecimal value = new BigDecimal(fraction).divide(new BigDecimal(FULL_RANGE), Float128Algebra.CONTEXT);
			value = value.add(BigDecimal.ONE);
			value = value.multiply(TWO.pow(exponent - 16383));
			if (sign != 0)
				value = value.negate();
			setNormal(value);
		}
		else if (exponent == 0) {
			
			// a special number : zeroes and subnormals
			
			if (fraction.compareTo(BigInteger.ZERO) == 0) {
				
				// a zero
				
				if (sign != 0)
					setNegZero();
				else
					setPosZero();
			}
			else {
				
				// subnormal number
				
				BigDecimal value = new BigDecimal(fraction).divide(new BigDecimal(FULL_RANGE), Float128Algebra.CONTEXT);
				value = value.multiply(TWO.pow(-16382));
				if (sign != 0)
					value = value.negate();
				setNormal(value);
			}
		}
		else if (exponent == 0x7fff) {
			
			// a special number; infinities and nan
			
			if (fraction.compareTo(BigInteger.ZERO) == 0) {
				
				// an infinity
				
				if (sign != 0)
					setNegInf();
				else
					setPosInf();
			}
			else {
				
				// a nan
				
				setNan();
			}
		}
		else
			throw new IllegalArgumentException("illegal exponent "+exponent);
	}

	private void clamp() {
		if (classification == NORMAL) {
			if (num.compareTo(MIN_NORMAL) < 0) {
				setNegInf();
			}
			else if (num.compareTo(MAX_NORMAL) > 0) {
				setPosInf();
			}
		}
	}
}
