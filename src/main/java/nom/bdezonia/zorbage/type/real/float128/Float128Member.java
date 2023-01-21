/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.GetAsBigDecimal;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArray;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsBigInteger;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArray;
import nom.bdezonia.zorbage.algebra.GetAsByte;
import nom.bdezonia.zorbage.algebra.GetAsByteArray;
import nom.bdezonia.zorbage.algebra.GetAsDouble;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArray;
import nom.bdezonia.zorbage.algebra.GetAsFloat;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.GetAsInt;
import nom.bdezonia.zorbage.algebra.GetAsIntArray;
import nom.bdezonia.zorbage.algebra.GetAsLong;
import nom.bdezonia.zorbage.algebra.GetAsLongArray;
import nom.bdezonia.zorbage.algebra.GetAsShort;
import nom.bdezonia.zorbage.algebra.GetAsShortArray;
import nom.bdezonia.zorbage.algebra.GetReal;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.HighPrecRepresentation;
import nom.bdezonia.zorbage.algebra.NativeBigDecimalSupport;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimals;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegers;
import nom.bdezonia.zorbage.algebra.SetFromBytes;
import nom.bdezonia.zorbage.algebra.SetFromBytesExact;
import nom.bdezonia.zorbage.algebra.SetFromDoubles;
import nom.bdezonia.zorbage.algebra.SetFromDoublesExact;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromFloatsExact;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromIntsExact;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromLongsExact;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.SetFromShortsExact;
import nom.bdezonia.zorbage.algebra.SetReal;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
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
		HighPrecRepresentation, SetReal<Float128Member>, GetReal<Float128Member>,
		NativeBigDecimalSupport,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromIntsExact,
		SetFromLongs,
		SetFromLongsExact,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
		SetFromDoublesExact,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByte,
		GetAsByteArray,
		GetAsShort,
		GetAsShortArray,
		GetAsInt,
		GetAsIntArray,
		GetAsLong,
		GetAsLongArray,
		GetAsFloat,
		GetAsFloatArray,
		GetAsDouble,
		GetAsDoubleArray,
		GetAsBigInteger,
		GetAsBigIntegerArray,
		GetAsBigDecimal,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact
{
	BigDecimal num;
	byte classification;

	static final BigDecimal TWO = BigDecimal.valueOf(2);
	static final BigDecimal MAX_NORMAL = TWO.pow(16383).multiply(TWO.subtract(TWO.pow(-112, Float128Algebra.CONTEXT)));
	static final BigDecimal MIN_NORMAL = MAX_NORMAL.negate();
	static final BigDecimal MAX_SUBNORMAL = TWO.pow(-16382, Float128Algebra.CONTEXT).multiply(BigDecimal.ONE.subtract(TWO.pow(-112, Float128Algebra.CONTEXT)));
	static final BigDecimal MIN_SUBNORMAL = TWO.pow(-16382, Float128Algebra.CONTEXT).multiply(TWO.pow(-112, Float128Algebra.CONTEXT));
	static final BigInteger FULL_RANGE = new BigInteger("10000000000000000000000000000",16);
	static final BigDecimal FULL_RANGE_BD = new BigDecimal(FULL_RANGE);
	static final BigInteger FULL_FRACTION = new BigInteger("ffffffffffffffffffffffffffff", 16);
	
	static final byte NORMAL = 0;
	static final byte POSZERO = 1;
	static final byte NEGZERO = -1;
	static final byte POSINF = 2;
	static final byte NEGINF = -2;
	static final byte NAN = 3;

	public Float128Member() {
		primitiveInit();
	}
	
	// Prefer this ctor over the BigDecimal based one since it can propagate
	// nan, inf, etc.

	public Float128Member(Float128Member other) {
		set(other);
	}

	// Prefer the previous ctor over this one since this cannot represent
	// nan, inf, etc.
	
	public Float128Member(BigDecimal... vals) {
		setFromBigDecimals(vals);
	}
	
	public Float128Member(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		OctonionRepresentation val = rep.firstValue();
		setV(val.r());
	}

	public Float128Member(BigInteger... vals) {
		setFromBigIntegers(vals);
	}
	
	public Float128Member(long... vals) {
		setFromLongs(vals);
	}
	
	public Float128Member(double... vals) {
		setFromDoubles(vals);
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
	public BigDecimal getAsBigDecimal() {
		if (!isFinite()) {
			if (isNegative())
				return MIN_NORMAL;
			else if (isPositive())
				return MAX_NORMAL;
			else
				return BigDecimal.ZERO; // NAN
		}
		return v();
	}
	
	@Override
	public void setFromLongs(long... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]));
	}
	
	@Override
	public void setFromDoubles(double... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]));
	}
	
	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(new BigDecimal(vals[0]));
	}
	
	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public void toHighPrec(HighPrecisionMember output) {
		output.setV(getAsBigDecimal());
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
		if (component == 0) return getAsByte();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsShort();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsInt();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsLong();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsFloat();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsDouble();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return getAsBigDecimal();
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
			return getAsByte();
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
			return getAsShort();
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
			return getAsInt();
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
			return getAsLong();
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
			return getAsFloat();
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
			return getAsDouble();
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
			return getAsBigInteger();
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
			return getAsBigDecimal();
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
		return 16;
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

	public boolean isNormal() {
		return classification == NORMAL;
	}

	public boolean isPosZero() {
		return classification == POSZERO;
	}

	public boolean isNegZero() {
		return classification == NEGZERO;
	}

	public boolean isPosInf() {
		return classification == POSINF;
	}

	public boolean isNegInf() {
		return classification == NEGINF;
	}

	public boolean isNan() {
		return classification == NAN;
	}

	public boolean isFinite() {
		return classification == NORMAL || classification == POSZERO || classification == NEGZERO;
	}

	public boolean isInfinite() {
		return classification == POSINF || classification == NEGINF;
	}

	public boolean isZero() {
		return classification == POSZERO || classification == NEGZERO;
	}

	public boolean isPositive() {
		return classification == POSINF ||
				(classification == NORMAL && num.signum() > 0);
	}

	public boolean isNegative() {
		return classification == NEGINF ||
				(classification == NORMAL && num.signum() < 0);
	}

	public boolean hasPositiveSign() {
		return classification == POSZERO || isPositive();
	}
	
	public boolean hasNegativeSign() {
		return classification == NEGZERO || isNegative();
	}

	// DO NOT MAKE THIS ONE PUBLIC: use num.setV(BigDecimal)
	// because it does some data classification and clamping.
	// If people started using this method arbitrarily they
	// could violate the internal consistency of how the
	// numbers are handled and bad numerical results could
	// sneak in.
	
	void setNormal(BigDecimal value) {
		num = value;
		classification = NORMAL;
	}

	public void setPosZero() {
		num = BigDecimal.ZERO;
		classification = POSZERO;
	}

	public void setNegZero() {
		num = BigDecimal.ZERO;
		classification = NEGZERO;
	}

	public void setPosInf() {
		num = BigDecimal.ZERO;
		classification = POSINF;
	}

	public void setNegInf() {
		num = BigDecimal.ZERO;
		classification = NEGINF;
	}
	
	public void setNan() {
		num = BigDecimal.ZERO;
		classification = NAN;
	}

	// Take my classification and BigDecimal values and encode them as
	//   an IEEE128 in the 16 bytes of arr starting at offset
	
	void encode(byte[] arr, int offset) {
		switch (classification) {
		case NORMAL:
			
			BigInteger ff = BigInteger.valueOf(255);
			BigDecimal tmp = num.abs();
			int signBit = (num.signum() < 0) ? 0x80 : 0;
			
			if (tmp.compareTo(BigDecimal.ONE) == 0) {
				arr[offset + 15] = (byte) (signBit | 0x3f);
				arr[offset + 14] = (byte) 0xff;
				for (int i = 13; i >= 0; i--) {
					arr[offset + i] = 0;
				}
			}
			
			// is it a subnormal?
			
			else if (tmp.compareTo(MIN_SUBNORMAL) >= 0 && tmp.compareTo(MAX_SUBNORMAL) <= 0) {
				BigInteger fraction = findFraction(MIN_SUBNORMAL, MAX_SUBNORMAL, tmp);
				arr[offset + 15] = (byte) signBit;
				arr[offset + 14] = 0;
				for (int i = 0; i < 14; i++) {
					byte b = fraction.and(ff).byteValue();
					fraction = fraction.shiftRight(8);
					arr[offset + i] = b;
				}
			}
			
			// is it between zero and one?
			
			else if (tmp.compareTo(BigDecimal.ZERO) > 0 && tmp.compareTo(BigDecimal.ONE) < 0) {
				// it's a number > 0 and < 1
				//BigDecimal lg2 = BigDecimalMath.log2(tmp, Float128Algebra.CONTEXT);
				//int exponent = lg2.intValue();
				int exponent = -(BigDecimal.ONE.divide(tmp, Float128Algebra.CONTEXT).toBigInteger().bitLength()-1); 
				BigDecimal lowerBound = TWO.pow(exponent-1, Float128Algebra.CONTEXT);
				BigDecimal upperBound = TWO.pow(exponent, Float128Algebra.CONTEXT);
				BigInteger fraction = findFraction(lowerBound, upperBound, tmp);
				exponent += 16382;
				int ehi = (exponent & 0xff00) >> 8;
				int elo = (exponent & 0x00ff) >> 0;
				arr[offset + 15] = (byte) (signBit | ehi);
				arr[offset + 14] = (byte) (elo);
				for (int i = 0; i < 14; i++) {
					byte b = fraction.and(ff).byteValue();
					fraction = fraction.shiftRight(8);
					arr[offset + i] = b;
				}
			}
			
			else {

				// it's a number > 1 and <= MAXBOUND
				
				BigInteger bi = tmp.toBigInteger();
				int exponent = bi.bitLength() - 1;
				BigDecimal lowerBound = TWO.pow(exponent, Float128Algebra.CONTEXT);
				BigDecimal upperBound = TWO.pow(exponent+1, Float128Algebra.CONTEXT);
				BigInteger fraction = findFraction(lowerBound, upperBound, tmp);
				exponent += 16383;
				int ehi = (exponent & 0xff00) >> 8;
				int elo = (exponent & 0x00ff) >> 0;
				arr[offset + 15] = (byte) (signBit | ehi);
				arr[offset + 14] = (byte) (elo);
				for (int i = 0; i < 14; i++) {
					byte b = fraction.and(ff).byteValue();
					fraction = fraction.shiftRight(8);
					arr[offset + i] = b;
				}
			}
			break;
			
		case POSZERO:
			// encode a regular (positive) 0
			for (int i = 15; i >= 0; i--) {
				arr[offset + i] = 0;
			}
			break;
			
		case NEGZERO:
			// encode a negative 0
			arr[offset + 15] = (byte) 0x80;
			for (int i = 14; i >= 0; i--) {
				arr[offset + i] = 0;
			}
			break;
			
		case POSINF:
			// +1 / 0
			// encode a positive infinity in the remaining 16 bytes
			arr[offset + 15] = (byte) 0x7f;
			arr[offset + 14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				arr[offset + i] = 0;
			}
			break;
			
		case NEGINF:
			// -1 / 0
			// encode a negative infinity in the remaining 16 bytes
			arr[offset + 15] = (byte) 0xff;
			arr[offset + 14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				arr[offset + i] = 0;
			}
			break;
			
		case NAN:
			// 0 / 0
			// encode a NaN in the remaining 16 bytes
			arr[offset + 15] = (byte) 0x7f;
			arr[offset + 14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				// Any non zero value tells the OS this is a nan rather than
				// an inf.
				// TODO : do I have to deal with signaling versus nonsignaling
				// or is that only applicable to hardware implementations?
				arr[offset + i] = 1;
			}
			break;
			
		default:
			throw new IllegalArgumentException("unknown number classification "+classification);
		}
	}
	
	// Take the 16 bytes stored in arr starting at offset and decode them
	//   into my denom and BigDecimal values.
	
	void decode(byte[] buffer, int offset) {
		
		int sign = (buffer[offset + 15] & 0x80);
		
		int exponent = ((buffer[offset + 15] & 0x7f) << 8) + (buffer[offset + 14] & 0xff);
		
		BigInteger fraction = BigInteger.ZERO;
		
		for (int i = 13; i >= 0; i--) {
			fraction = fraction.shiftLeft(8).add(BigInteger.valueOf(buffer[offset + i] & 0xff));
		}
		
		if (exponent > 0 && exponent < 0x7fff) {

			// a regular number
			
			BigDecimal value = new BigDecimal(fraction).divide(FULL_RANGE_BD, Float128Algebra.CONTEXT);
			value = value.add(BigDecimal.ONE);
			value = value.multiply(TWO.pow(exponent - 16383, Float128Algebra.CONTEXT));
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

				BigDecimal value = new BigDecimal(fraction).divide(FULL_RANGE_BD, Float128Algebra.CONTEXT);
				value = value.multiply(TWO.pow(-16382, Float128Algebra.CONTEXT));
				if (sign != 0)
					value = value.negate();
				// setV() rather than setNormal() to clamp off roundoff issues
				setV(value);
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

	private BigInteger findFraction(BigDecimal lowerBound, BigDecimal upperBound, BigDecimal value) {
		BigDecimal numer = value.subtract(lowerBound);
		BigDecimal denom = upperBound.subtract(lowerBound);
		BigDecimal ratio = numer.divide(denom, Float128Algebra.CONTEXT);
		// adding half to make sure we are correctly rounding
		BigInteger fraction = FULL_RANGE_BD.multiply(ratio).add(BigDecimalUtils.ONE_HALF).toBigInteger();
		// due to rounding quirks with java you might actually reach or surpass 1.0 so clamp it
		if (fraction.compareTo(FULL_FRACTION) > 0)
			fraction = FULL_FRACTION;
		return fraction;
	}
	
	private void clamp() {
		if (classification == NORMAL) {
			if (num.compareTo(MIN_NORMAL) < 0) {
				setNegInf();
			}
			else if (num.compareTo(MAX_NORMAL) > 0) {
				setPosInf();
			}
			else if (num.abs().compareTo(MIN_SUBNORMAL) < 0) {
				if (num.signum() < 0)
					setNegZero();
				else
					setPosZero();
			}
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {v()};
	}

	@Override
	public BigDecimal getNative(int component) {

		return v();
	}

	@Override
	public void setNative(int component, BigDecimal val) {

		setV(val);
	}

	@Override
	public BigDecimal componentMin() {

		return MIN_NORMAL;
	}

	@Override
	public BigDecimal componentMax() {

		return MAX_NORMAL;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] { getAsBigInteger()};
	}

	@Override
	public BigInteger getAsBigInteger() {
		if (!isFinite()) {
			if (isNegative())
				return MIN_NORMAL.toBigInteger();
			else if (isPositive())
				return MAX_NORMAL.toBigInteger();
			else
				return BigInteger.ZERO; // NAN
		}
		return v().toBigInteger();
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] { getAsDouble()};
	}

	@Override
	public double getAsDouble() {
		if (!isFinite()) {
			if (isNegative())
				return Double.NEGATIVE_INFINITY;
			else if (isPositive())
				return Double.POSITIVE_INFINITY;
			else
				return Double.NaN; // NAN
		}
		return v().doubleValue();
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] { getAsFloat()};
	}

	@Override
	public float getAsFloat() {
		if (!isFinite()) {
			if (isNegative())
				return Float.NEGATIVE_INFINITY;
			else if (isPositive())
				return Float.POSITIVE_INFINITY;
			else
				return Float.NaN; // NAN
		}
		return v().floatValue();
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] { getAsLong()};
	}

	@Override
	public long getAsLong() {
		if (!isFinite()) {
			if (isNegative())
				return Long.MIN_VALUE;
			else if (isPositive())
				return Long.MAX_VALUE;
			else
				return 0; // NAN
		}
		return v().longValue();
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] { getAsInt()};
	}

	@Override
	public int getAsInt() {
		if (!isFinite()) {
			if (isNegative())
				return Integer.MIN_VALUE;
			else if (isPositive())
				return Integer.MAX_VALUE;
			else
				return 0; // NAN
		}
		return v().intValue();
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] { getAsShort()};
	}

	@Override
	public short getAsShort() {
		if (!isFinite()) {
			if (isNegative())
				return Short.MIN_VALUE;
			else if (isPositive())
				return Short.MAX_VALUE;
			else
				return 0; // NAN
		}
		return v().shortValue();
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] { getAsByte()};
	}

	@Override
	public byte getAsByte() {
		if (!isFinite()) {
			if (isNegative())
				return Byte.MIN_VALUE;
			else if (isPositive())
				return Byte.MAX_VALUE;
			else
				return 0; // NAN
		}
		return v().byteValue();
	}

	@Override
	public void setFromDoublesExact(double... vals) {
		setFromDoubles(vals);
	}

	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}

	@Override
	public void setFromFloats(float... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]));
	}

	@Override
	public void setFromLongsExact(long... vals) {
		setFromLongs(vals);
	}

	@Override
	public void setFromIntsExact(int... vals) {
		setFromInts(vals);
	}

	@Override
	public void setFromInts(int... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]));
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]));
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]));
	}
}
