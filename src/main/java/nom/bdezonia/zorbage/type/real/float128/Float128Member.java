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
		if (Math.abs(classification) >= 2)
			throw new java.lang.NumberFormatException("nan/posinf/neginf cannot be converted to big decimal");
		return num;
	}
	
	public void setV(BigDecimal v) {
		if (v == null)
			throw new IllegalArgumentException("this class does not allow null values");
		if (v.compareTo(BigDecimal.ZERO) == 0) {
			setPosZero();
		}
		else {
			num = v;
			classification = 0;
			clamp();
		}
	}
	
	@Override
	public void toHighPrec(HighPrecisionMember output) {
		if (Math.abs(classification) >= 2)
			throw new java.lang.NumberFormatException("nan/posinf/neginf cannot encode as a high precision decimal");
		output.setV(num); // this is a trick with classifications 0, 1, and -1
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
		if (classification == 0) {
			return num.toString();
		}
		else if (classification == 1) {
			return "0";
		}
		else if (classification == -1) {
			return "-0";
		}
		else if (classification == 2) {
			return "Infinity";
		}
		else if (classification == -2) {
			return "-Infinity";
		}
		else if (classification == 3) {
			return "NaN";
		}
		else
			throw new IllegalArgumentException("unknown number classification "+classification);
	}

	boolean isPosZero() {
		return classification == 1;
	}

	boolean isNegZero() {
		return classification == -1;
	}

	boolean isPosInf() {
		return classification == 2;
	}

	boolean isNegInf() {
		return classification == -2;
	}

	boolean isNan() {
		return classification == 3;
	}

	void setNormal(BigDecimal value) {
		num = value;
		classification = 0;
	}

	void setPosZero() {
		num = BigDecimal.ZERO;
		classification = 1;
	}

	void setNegZero() {
		num = BigDecimal.ZERO;
		classification = -1;
	}

	void setPosInf() {
		num = BigDecimal.ONE;
		classification = 2;
	}

	void setNegInf() {
		num = BigDecimal.ONE;
		classification = -2;
	}
	
	void setNan() {
		num = BigDecimal.ZERO;
		classification = 3;
	}

	// Take my denom and BigDecimal values and encode them as a denom and
	//   IEEE128 in the 17 bytes of arr starting at offset
	
	void encode(byte[] arr, int offset) {
		arr[offset] = classification;
		if (classification == 0) {
			// regular number
			int sign = (num.compareTo(BigDecimal.ZERO) < 0) ? 1 : 0;
			BigDecimal absVal = num.abs();
			if (absVal.compareTo(MAX_SUBNORMAL) <= 0) {
				if (absVal.compareTo(MIN_SUBNORMAL) < 0) {
					// too small: write the data as pos or neg zero
					if (sign != 0) {
						arr[offset + 1 + 15] = (byte) 0x80;
					}
					else {
						arr[offset + 1 + 15] = 0;
					}
					for (int i = 14; i >= 0; i--) {
						arr[offset + 1 + i] = 0;
					}
				}
				else {
					// a number in the valid subnormal range
					// make a subnormal number
					// a real value in the normal range
					BigDecimal tmp = absVal;
					BigDecimal bound = MAX_SUBNORMAL;
					int exponent = 0;
					BigDecimal leftovers;
					while (tmp.compareTo(bound) < 0) {
						exponent--;
						bound = bound.divide(TWO);
					}
					BigDecimal left = bound;
					BigDecimal right = bound.multiply(TWO);
					BigDecimal partialInterval = tmp.subtract(left);
					BigDecimal fullInterval = right.subtract(left);
					leftovers = partialInterval.divide(fullInterval, Float128Algebra.CONTEXT);
					BigInteger twoi = BigInteger.valueOf(2);
					BigInteger fraction = BigInteger.ZERO;
					BigDecimal divisor = BigDecimal.ONE.divide(TWO);
					for (int i = 0; i < 112; i++) {
						if (leftovers.compareTo(divisor) >= 0) {
							fraction = fraction.add(BigInteger.ONE);
							fraction = fraction.multiply(twoi);
							leftovers = leftovers.subtract(divisor);
						}
						divisor = divisor.divide(TWO);
					}
					for (int i = 15; i >= 0; i--) {
						arr[offset + 1 + i] = 0;
					}
					if (sign != 0) {
						arr[offset + 1 + 15] = (byte) 0x80;
					}
					int upper7 = exponent >> 8;
					for (int i = 0x40; i <= 1; i >>= 1) {
						arr[offset + 1 + 15] |= (((i & upper7) != 0) ? i : 0);
					}
					int lower8 = exponent & 0xff;
					for (int i = 0x80; i <= 1; i >>= 1) {
						arr[offset + 1 + 14] |= (((i & lower8) != 0) ? i : 0);
					}
					for (int i = 13; i >= 0; i--) {
						int delta = i * 8;
						int v = 0;
						for (int b = 7; b >= 0; b--) {
							v <<= 1;
							if (fraction.testBit(delta+b)) {
								v += 1;
							}
						}
						arr[offset + 1 + i] = (byte) v;
					}
				}
			}
			else {
				// a real value in the normal range
				BigDecimal tmp = absVal;
				BigDecimal bound = BigDecimal.ONE;
				int exponent = 0;
				BigDecimal leftovers;
				// is num between 0 and 1?
				if (tmp.compareTo(BigDecimal.ONE) < 0) {
					while (tmp.compareTo(bound) < 0) {
						exponent--;
						bound = bound.divide(TWO);
					}
					BigDecimal left = bound;
					BigDecimal right = bound.multiply(TWO);
					BigDecimal partialInterval = tmp.subtract(left);
					BigDecimal fullInterval = right.subtract(left);
					leftovers = partialInterval.divide(fullInterval, Float128Algebra.CONTEXT);
				}
				else { // num is > 1
					while (tmp.compareTo(bound) > 0) {
						exponent++;
						bound = bound.multiply(TWO);
					}
					BigDecimal left = bound.divide(TWO);
					BigDecimal right = bound;
					BigDecimal partialInterval = tmp.subtract(left);
					BigDecimal fullInterval = right.subtract(left);
					leftovers = partialInterval.divide(fullInterval, Float128Algebra.CONTEXT);
				}
				BigInteger twoi = BigInteger.valueOf(2);
				BigInteger fraction = BigInteger.ZERO;
				BigDecimal divisor = BigDecimal.ONE.divide(TWO);
				for (int i = 0; i < 112; i++) {
					if (leftovers.compareTo(divisor) >= 0) {
						fraction = fraction.add(BigInteger.ONE);
						fraction = fraction.multiply(twoi);
						leftovers = leftovers.subtract(divisor);
					}
					divisor = divisor.divide(TWO);
				}
				for (int i = 15; i >= 0; i--) {
					arr[offset + 1 + i] = 0;
				}
				if (sign != 0) {
					arr[offset + 1 + 15] = (byte) 0x80;
				}
				int upper7 = exponent >> 8;
				for (int i = 0x40; i <= 1; i >>= 1) {
					arr[offset + 1 + 15] |= (((i & upper7) != 0) ? i : 0);
				}
				int lower8 = exponent & 0xff;
				for (int i = 0x80; i <= 1; i >>= 1) {
					arr[offset + 1 + 14] |= (((i & lower8) != 0) ? i : 0);
				}
				for (int i = 13; i >= 0; i--) {
					int delta = i * 8;
					int v = 0;
					for (int b = 7; b >= 0; b--) {
						v <<= 1;
						if (fraction.testBit(delta+b)) {
							v += 1;
						}
					}
					arr[offset + 1 + i] = (byte) v;
				}
			}
		}
		else if (classification == 1) {
			// encode a regular (positive) 0
			for (int i = 15; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
		}
		else if (classification == -1) {
			// encode a negative 0
			arr[offset+1+15] = (byte) 0x80;
			for (int i = 14; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
		}
		else if (classification == 2) {
			// +1 / 0
			// encode a positive infinity in the remaining 16 bytes
			arr[offset+1+15] = (byte) 0x7f;
			arr[offset+1+14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
		}
		else if (classification == -2) {
			// -1 / 0
			// encode a negative infinity in the remaining 16 bytes
			arr[offset+1+15] = (byte) 0xff;
			arr[offset+1+14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				arr[offset+1+i] = 0;
			}
		}
		else if (classification == 3) {
			// 0 / 0
			// encode a NaN in the remaining 16 bytes
			arr[offset+1+15] = (byte) 0x7f;
			arr[offset+1+14] = (byte) 0xff;
			for (int i = 13; i >= 0; i--) {
				// any non zero value tells the OS this is a nan rather than an inf
				// TODO : do I have to deal with signaling versus nonsignaling or is that all done in hardware?
				arr[offset+1+i] = 1;
			}
		}
		else
			throw new IllegalArgumentException("unknown number classification "+classification);
//		System.out.println("Encoded");
//		for (int i = 0; i < 16; i++) {
//			System.out.println(String.format("%x",arr[offset+1+i]));
//		}
	}
	
	// Take the 17 bytes stored in arr starting at offset and decode them
	//   into my denom and BigDecimal values.
	
	void decode(byte[] buffer, int offset) {
		
		int sign = 0;
		int exponent = 0;
		BigInteger fraction = BigInteger.ZERO;
		
		sign = (buffer[offset + 1 + 15] & 0x80);
		
		exponent = ((buffer[offset + 1 + 15] & 0x7f) << 8) + (buffer[offset + 1 + 14] & 0xff);
		
		for (int i = 13; i <= 0; i--) {
			fraction = fraction.shiftLeft(8).add(BigInteger.valueOf(buffer[offset + 1 + i] & 0xff));
		}

		if (buffer[offset] == 1) { // denominator == 1
			
			// encoded as a real value: decode it and if out of bounds reclassify it
			
			if ((exponent > 0) && (exponent < 0x7fff)) {
				// a regular number
				//   (−1)signbit × 2exponentbits2 − 16383 × 1.significandbits2
				BigDecimal value = BigDecimal.ONE;
				BigDecimal inc = BigDecimal.valueOf(0.5);
				for (int i = 111; i >= 0; i--) {
					if (fraction.testBit(i))
						value.add(inc);
					inc = inc.divide(TWO);
				}
				BigDecimal scale = TWO.pow(exponent - 16383, Float128Algebra.CONTEXT);
				value = value.multiply(scale);
				if (sign != 0) {
					value = value.negate();
				}
				setNormal(value);
			}
			else if (exponent == 0) {
				if (fraction.compareTo(BigInteger.ZERO) == 0) {
					if (sign != 0) {
						setNegZero();
					}
					else {
						setPosZero();
					}
				}
				else { // fraction does not equal zero
					// subnormal number
					//   (−1)signbit × 2−16382 × 0.significandbits2
					BigDecimal value = BigDecimal.ZERO;
					BigDecimal inc = BigDecimal.valueOf(0.5);
					for (int i = 111; i >= 0; i--) {
						if (fraction.testBit(i))
							value.add(inc);
						inc = inc.divide(TWO);
					}
					BigDecimal scale = TWO.pow(-16382, Float128Algebra.CONTEXT);
					value = value.multiply(scale);
					if (sign != 0) {
						value = value.negate();
					}
					setNormal(value);
				}
			}
			else {
				// exponent == 0x7fff
				if (fraction.compareTo(BigInteger.ZERO) == 0) {
					// an infinity
					if (sign != 0)
						setNegInf();
					else
						setPosInf();
				}
				else { // fraction does not equal zero
					// a nan
					setNan();
				}
			}
		}
		else {
			// buffer[offset] == 0; denom == 0. we encoded a nan or pos inf or neg inf
			if (fraction.compareTo(BigInteger.ZERO) == 0) {
				if (sign != 0)
					setNegInf();
				else
					setPosInf();
			}
			else
				setNan();
		}
	}

	private void clamp() {
		if (classification == 0) {
			if (num.compareTo(MIN_NORMAL) < 0) {
				setNegInf();
			}
			else if (num.compareTo(MAX_NORMAL) > 0) {
				setPosInf();
			}
		}
	}
}
