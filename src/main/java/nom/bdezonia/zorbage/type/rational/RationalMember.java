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
package nom.bdezonia.zorbage.type.rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RationalMember
	implements
		BigIntegerCoder,
		Allocatable<RationalMember>, Duplicatable<RationalMember>,
		Settable<RationalMember>, Gettable<RationalMember>,
		UniversalRepresentation, NumberMember<RationalMember>,
		PrimitiveConversion,
		NativeBigIntegerSupport,
		SetFromByte,
		SetFromByteExact,
		SetFromShort,
		SetFromShortExact,
		SetFromInt,
		SetFromIntExact,
		SetFromLong,
		SetFromLongExact,
		SetFromFloat,
		SetFromDouble,
		SetFromBigInteger,
		SetFromBigIntegerExact,
		SetFromBigDecimal,
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
		GetAsBigIntegerArrayExact,
		GetAsBigDecimal,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact
{
	// this is how many decimal places of accuracy the BigDecimal values will contain
	private static final int PLACES = 24;
	
	private static final MathContext CONTEXT = new MathContext(PLACES);
	
	private static final BigInteger BIG_DENOM = BigInteger.TEN.pow(PLACES);
	
	private static final BigDecimal BIG_DENOM_AS_BD = new BigDecimal(BIG_DENOM);
	
	BigInteger n, d;
	
	public RationalMember() {
		primitiveInit();
	}

	public RationalMember(RationalMember other) {
		set(other);
	}

	public RationalMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setV(val.r());
	}

	public RationalMember(BigInteger integer) {
		setV(integer);
	}

	public RationalMember(long integer) {
		setV(BigInteger.valueOf(integer));
	}

	public RationalMember(BigInteger n, BigInteger d) {
		setV(n, d);
	}

	public RationalMember(long n, long d) {
		setV(BigInteger.valueOf(n), BigInteger.valueOf(d));
	}

	public RationalMember(BigInteger... vals) {
		setFromBigInteger(vals);
	}

	public RationalMember(long... vals) {
		setFromLong(vals);
	}

	@Override
	public void getV(RationalMember value) {
		value.set(this);
	}

	@Override
	public void setV(RationalMember value) {
		this.set(value);
	}
	
	public BigDecimal v() {
		return new BigDecimal(n).divide(new BigDecimal(d), CONTEXT);
	}
	
	public BigInteger n() { return n; }
	
	public BigInteger d() { return d; }
	
	public void setV(BigDecimal v) {
		BigDecimal numer = v.multiply(BIG_DENOM_AS_BD, CONTEXT);
		if (numer.signum() < 0)
			numer = numer.subtract(BigDecimalUtils.ONE_HALF);
		else
			numer = numer.add(BigDecimalUtils.ONE_HALF);
		setV(numer.toBigInteger(), BIG_DENOM);
	}

	public void setV(BigInteger n) {
		this.n = n;
		this.d = BigInteger.ONE;
	}
	
	public void setV(long n, long d) {
		setV(BigInteger.valueOf(n), BigInteger.valueOf(d));
	}
	
	public void setV(BigInteger n, BigInteger d) {
		int signum = d.signum();
		if (signum == 0)
			throw new IllegalArgumentException("divide by zero");
		if (signum < 0) {
			// denominator is negative. normalize so that sign stored in numerator.
			n = n.negate();
			d = d.negate();
		}
		if (n.signum() == 0) {
			primitiveInit();
		}
		else {
			BigInteger gcd = n.gcd(d);
			this.n = n.divide(gcd);
			this.d = d.divide(gcd);
		}
	}
	
	@Override
	public void get(RationalMember other) {
		other.n = this.n;
		other.d = this.d;
	}

	@Override
	public void set(RationalMember other) {
		this.n = other.n;
		this.d = other.d;
	}

	@Override
	public RationalMember duplicate() {
		return new RationalMember(this);
	}

	@Override
	public RationalMember allocate() {
		return new RationalMember();
	}

	@Override
	public String toString() {
		return n.toString() + "/" + d.toString();
	}

	@Override
	public int bigIntegerCount() {
		return 2;
	}

	@Override
	public void fromBigIntegerArray(BigInteger[] arr, int index) {
		BigInteger numer = arr[index];
		BigInteger denom = arr[index+1];
		setV(numer, denom);
	}

	@Override
	public void toBigIntegerArray(BigInteger[] arr, int index) {
		arr[index] = n;
		arr[index+1] = d;
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
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(new OctonionRepresentation(v()));
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		setV(rep.getValue().r());
	}
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		setV(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		setV(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		setV(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		setV(BigInteger.valueOf(v));
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
		setV(v);
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
			setV(BigInteger.valueOf(v));
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
			setV(BigInteger.valueOf(v));
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
			setV(BigInteger.valueOf(v));
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
			setV(BigInteger.valueOf(v));
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
			setV(v);
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
		this.n = BigInteger.ZERO;
		this.d = BigInteger.ONE;
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(n);
		v = Hasher.PRIME * v + Hasher.hashCode(d);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof RationalMember) {
			return G.RAT.isEqual().call(this, (RationalMember) o);
		}
		return false;
	}

	@Override
	public void setFromBigInteger(BigInteger... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0], vals[1]);
	}

	@Override
	public void setFromLong(long... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigInteger.valueOf(vals[0]), BigInteger.valueOf(vals[1]));
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] {n(), d()};
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {new BigDecimal(n()), new BigDecimal(d())};
	}

	@Override
	public BigDecimal getAsBigDecimal() {
		return v();
	}

	@Override
	public BigInteger[] getAsBigIntegerArrayExact() {
		return getAsBigIntegerArray();
	}

	@Override
	public BigInteger getAsBigInteger() {
		return v().toBigInteger();
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] {n().doubleValue(), d().doubleValue()};
	}

	@Override
	public double getAsDouble() {
		return v().doubleValue();
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] {n().floatValue(), d().floatValue()};
	}

	@Override
	public float getAsFloat() {
		return v().floatValue();
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] {n().longValue(), d().longValue()};
	}

	@Override
	public long getAsLong() {
		return v().longValue();
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {n().intValue(), d().intValue()};
	}

	@Override
	public int getAsInt() {
		return v().intValue();
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] {n().shortValue(), d().shortValue()};
	}

	@Override
	public short getAsShort() {
		return v().shortValue();
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] {n().byteValue(), d().byteValue()};
	}

	@Override
	public byte getAsByte() {
		return v().byteValue();
	}

	@Override
	public void setFromBigDecimal(BigDecimal... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0].toBigInteger(), vals[1].toBigInteger());
	}

	@Override
	public void setFromBigIntegerExact(BigInteger... vals) {
		setFromBigInteger(vals);
	}

	@Override
	public void setFromDouble(double... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]).toBigInteger(), BigDecimal.valueOf(vals[1]).toBigInteger());
	}

	@Override
	public void setFromFloat(float... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigDecimal.valueOf(vals[0]).toBigInteger(), BigDecimal.valueOf(vals[1]).toBigInteger());
	}

	@Override
	public void setFromLongExact(long... vals) {
		setFromLong(vals);
	}

	@Override
	public void setFromIntExact(int... vals) {
		setFromInt(vals);
	}

	@Override
	public void setFromInt(int... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigInteger.valueOf(vals[0]), BigInteger.valueOf(vals[1]));
	}

	@Override
	public void setFromShortExact(short... vals) {
		setFromShort(vals);
	}

	@Override
	public void setFromShort(short... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigInteger.valueOf(vals[0]), BigInteger.valueOf(vals[1]));
	}

	@Override
	public void setFromByteExact(byte... vals) {
		setFromByte(vals);
	}

	@Override
	public void setFromByte(byte... vals) {
		if (vals.length != 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(BigInteger.valueOf(vals[0]), BigInteger.valueOf(vals[1]));
	}

	@Override
	public BigInteger getNative(int component) {
		
		if (component == 0)
			return n();
		else if (component == 1)
			return d();
		else
			throw new IllegalArgumentException("componenet number out of bounds");
	}

	@Override
	public void setNative(int component, BigInteger val) {
		
		if (component == 0)
			setV(val, d());
		else if (component == 1)
			setV(n(), val);
		else
			throw new IllegalArgumentException("componenet number out of bounds");
	}

	@Override
	public BigInteger componentMin() {

		return null; // bigintegers have no min
	}

	@Override
	public BigInteger componentMax() {

		return null; // bigintegers have no max
	}
}
