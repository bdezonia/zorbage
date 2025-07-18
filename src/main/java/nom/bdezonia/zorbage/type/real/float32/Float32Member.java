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
package nom.bdezonia.zorbage.type.real.float32;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float32Member
	implements
		NumberMember<Float32Member>,
		FloatCoder,
		Allocatable<Float32Member>, Duplicatable<Float32Member>,
		Settable<Float32Member>, Gettable<Float32Member>,
		UniversalRepresentation, PrimitiveConversion,
		HighPrecRepresentation, SetReal<Float32Member>, GetReal<Float32Member>,
		NativeFloatSupport,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromLongs,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
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
		GetAsFloatExact,
		GetAsFloatArray,
		GetAsFloatArrayExact,
		GetAsDouble,
		GetAsDoubleExact,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigInteger,
		GetAsBigIntegerArray,
		GetAsBigDecimal,
		GetAsBigDecimalExact,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<Float32Algebra, Float32Member>,
		FixedSize
{
	float v;
	
	public Float32Member() {
		primitiveInit();
	}
	
	public Float32Member(float... vals) {
		setFromFloats(vals);
	}
	
	public Float32Member(Float32Member value) {
		set(value);
	}

	public Float32Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setV(val.r().floatValue());
	}
	
	public float v() { return v; }

	public void setV(float val) { v = val; }

	@Override
	public void set(Float32Member other) {
		v = other.v;
	}

	@Override
	public void get(Float32Member other) {
		other.v = v;
	}
	
	@Override
	public String toString() {
		return String.valueOf(v);
	}

	@Override
	public int floatCount() {
		return 1;
	}

	@Override
	public void fromFloatArray(float[] arr, int index) {
		v = arr[index];
	}

	@Override
	public void toFloatArray(float[] arr, int index) {
		arr[index] = v;
	}

	@Override
	public Float32Member allocate() {
		return new Float32Member();
	}

	@Override
	public Float32Member duplicate() {
		return new Float32Member(this);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(new OctonionRepresentation(BigDecimal.valueOf(v())));
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		setV(rep.getValue().r().floatValue());
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(Float32Member value) {
		get(value);
	}

	@Override
	public void setV(Float32Member value) {
		set(value);
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
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
		setV(v);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		setV(v);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		setV(v);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		setV(v);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		setV(v);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		setV((float)v);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		setV(v.floatValue());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		setV(v.floatValue());
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
			setV(v);
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
			setV(v);
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
			setV(v);
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
			setV(v);
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
			setV(v);
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
			setV((float)v);
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
			setV(v.floatValue());
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
			setV(v.floatValue());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (byte) v();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (short) v();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (int) v();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (long) v();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigDecimal.valueOf(v).toBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigDecimal.valueOf(v());
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
			return (byte) v();
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
			return (short) v();
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
			return (int) v();
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
			return (long) v();
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
			return v();
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
			return v();
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
			return BigDecimal.valueOf(v()).toBigInteger();
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
			return BigDecimal.valueOf(v());
		}
	}

	@Override
	public void primitiveInit() {
		v = 0;
	}

	@Override
	public void toHighPrec(HighPrecisionMember result) {
		result.setV(BigDecimal.valueOf(v()));
	}

	@Override
	public void fromHighPrec(HighPrecisionMember input) {
		setV(input.v().floatValue());
	}

	@Override
	public void setR(Float32Member val) {
		set(val);
	}

	@Override
	public void getR(Float32Member val) {
		get(val);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(this.v);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Float32Member) {
			return G.FLT.isEqual().call(this, (Float32Member) o);
		}
		return false;
	}

	@Override
	public float getAsFloat() {
		return v();
	}

	@Override
	public void setFromLongs(long... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public void setFromFloats(float... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] {v()};
	}

	@Override
	public float getNative(int component) {

		return v();
	}

	@Override
	public void setNative(int component, float val) {

		setV(val);
	}

	@Override
	public Float componentMin() {

		return -Float.MAX_VALUE;
	}

	@Override
	public Float componentMax() {

		return Float.MAX_VALUE;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] { getAsBigDecimal() };
	}

	@Override
	public BigDecimal getAsBigDecimalExact() {
		return getAsBigDecimal();
	}

	@Override
	public BigDecimal getAsBigDecimal() {
		return BigDecimal.valueOf(v());
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] { getAsBigInteger() };
	}

	@Override
	public BigInteger getAsBigInteger() {
		return BigDecimal.valueOf(v()).toBigInteger();
	}

	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] { getAsDouble() };
	}

	@Override
	public double getAsDoubleExact() {
		return getAsDouble();
	}

	@Override
	public double getAsDouble() {
		return (double) v();
	}

	@Override
	public float[] getAsFloatArrayExact() {
		return getAsFloatArray();
	}

	@Override
	public float getAsFloatExact() {
		return getAsFloat();
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] { getAsLong() };
	}

	@Override
	public long getAsLong() {
		return (long) v();
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] { getAsInt() };
	}

	@Override
	public int getAsInt() {
		return (int) v();
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] { getAsShort() };
	}

	@Override
	public short getAsShort() {
		return (short) v();
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] { getAsByte() };
	}

	@Override
	public byte getAsByte() {
		return (byte) v();
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0].floatValue());
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0].floatValue());
	}

	@Override
	public void setFromDoubles(double... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV((float) vals[0]);
	}

	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}

	@Override
	public void setFromInts(int... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}
	
	@Override
	public Float32Algebra getAlgebra() {

		return G.FLT;
	}
}
