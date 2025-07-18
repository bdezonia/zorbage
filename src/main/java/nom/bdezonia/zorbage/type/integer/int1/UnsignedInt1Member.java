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
package nom.bdezonia.zorbage.type.integer.int1;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.BitCoder;
import nom.bdezonia.zorbage.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Member;
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
public final class UnsignedInt1Member
	implements
		BitCoder, ByteCoder, BooleanCoder,
		Allocatable<UnsignedInt1Member>, Duplicatable<UnsignedInt1Member>,
		Settable<UnsignedInt1Member>, Gettable<UnsignedInt1Member>,
		UniversalRepresentation, NumberMember<UnsignedInt1Member>,
		PrimitiveConversion, HighPrecRepresentation,
		SetReal<SignedInt8Member>, GetReal<SignedInt8Member>,
		NativeByteSupport,
		SetFromBytes,
		SetFromShorts,
		SetFromInts,
		SetFromLongs,
		SetFromFloats,
		SetFromDoubles,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByte,
		GetAsByteExact,
		GetAsByteArray,
		GetAsByteArrayExact,
		GetAsShort,
		GetAsShortExact,
		GetAsShortArray,
		GetAsShortArrayExact,
		GetAsInt,
		GetAsIntExact,
		GetAsIntArray,
		GetAsIntArrayExact,
		GetAsLong,
		GetAsLongExact,
		GetAsLongArray,
		GetAsLongArrayExact,
		GetAsFloat,
		GetAsFloatExact,
		GetAsFloatArray,
		GetAsFloatArrayExact,
		GetAsDouble,
		GetAsDoubleExact,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigInteger,
		GetAsBigIntegerExact,
		GetAsBigIntegerArray,
		GetAsBigIntegerArrayExact,
		GetAsBigDecimal,
		GetAsBigDecimalExact,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<UnsignedInt1Algebra, UnsignedInt1Member>,
		FixedSize
{
	byte v;

	public UnsignedInt1Member() {
		v = 0;
	}

	public UnsignedInt1Member(UnsignedInt1Member other) {
		set(other);
	}

	public UnsignedInt1Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		int x = val.r().intValue();
		setV(x);
	}
	
	public UnsignedInt1Member(int... vals) {
		setFromInts(vals);
	}
	
	@Override
	public void getV(UnsignedInt1Member value) {
		get(value);
	}

	public byte v() {
		return v;
	}

	@Override
	public void setV(UnsignedInt1Member value) {
		set(value);
	}

	public void setV(int val) {

		v = (byte) (val & 0x1);
		
	}
	
	@Override
	public String toString() {
		return String.valueOf(v);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(new OctonionRepresentation(new BigDecimal(v)));
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		setV(rep.getValue().r().intValue());
	}

	@Override
	public void get(UnsignedInt1Member other) {
		other.v = v;
	}

	@Override
	public void set(UnsignedInt1Member other) {
		v = other.v;
	}

	@Override
	public UnsignedInt1Member duplicate() {
		return new UnsignedInt1Member(this);
	}

	@Override
	public UnsignedInt1Member allocate() {
		return new UnsignedInt1Member();
	}

	@Override
	public int bitCount() {
		return 1;
	}

	@Override
	public void fromBitArray(long[] arr, int index, int offset) {
		// 1 bit in one long
		long oldVals = arr[index] & (0x1L << offset);
		v = (byte) (oldVals >>> offset);
	}

	@Override
	public void toBitArray(long[] arr, int index, int offset) {
		// 1 bit in a long
		long oldVals = arr[index] & ~(0x0001L << offset);
		long newVals = ((long)v) << offset;
		arr[index] = newVals | oldVals;
	}


	@Override
	public int byteCount() {
		return 1;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		setV(arr[index]);
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		arr[index] = v;
	}


	@Override
	public int booleanCount() {
		return 1;
	}

	@Override
	public void fromBooleanArray(boolean[] arr, int index) {
		v = (byte) (arr[index] ? 1 : 0);
	}

	@Override
	public void toBooleanArray(boolean[] arr, int index) {
		arr[index] = (v == 1);
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
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BYTE;
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
		setV((int)v);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		setV((int)v);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		setV((int)v);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		setV(v.intValue());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		primComponentSetBigInteger(index, component, v.toBigInteger());
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
			setV((int)v);
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
			setV((int)v);
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
			setV((int)v);
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
			setV(v.intValue());
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		primComponentSetBigIntegerSafe(index, component, v.toBigInteger());
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigInteger.valueOf(v);
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigDecimal.valueOf(v);
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
			return v;
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
			return v;
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
			return v;
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
			return v;
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
			return v;
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
			return v;
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
			return BigInteger.valueOf(v);
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
			return BigDecimal.valueOf(v);
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
		setV(input.v().intValue());
	}

	@Override
	public void setR(SignedInt8Member val) {
		setV(val.v());
	}

	@Override
	public void getR(SignedInt8Member val) {
		val.setV(v);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(this.v);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof UnsignedInt1Member) {
			return G.UINT1.isEqual().call(this, (UnsignedInt1Member) o);
		}
		return false;
	}

	@Override
	public byte getAsByte() {
		return v();
	}

	@Override
	public void setFromInts(int... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}


	@Override
	public void setFromLongs(long... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV((int) vals[0]);
	}
	
	@Override
	public byte[] getAsByteArray() {
		return new byte[] {v()};
	}

	@Override
	public byte getNative(int component) {

		return v();
	}

	@Override
	public void setNative(int component, byte val) {

		setV(val);
	}

	@Override
	public Byte componentMin() {

		return 0;
	}

	@Override
	public Byte componentMax() {

		return 1;
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
	public BigInteger[] getAsBigIntegerArrayExact() {
		return getAsBigIntegerArray();
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] { getAsBigInteger() };
	}

	@Override
	public BigInteger getAsBigIntegerExact() {
		return getAsBigInteger();
	}

	@Override
	public BigInteger getAsBigInteger() {
		return BigInteger.valueOf(v());
	}

	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] { v() };
	}

	@Override
	public double getAsDoubleExact() {
		return getAsDouble();
	}

	@Override
	public double getAsDouble() {
		return v();
	}

	@Override
	public float[] getAsFloatArrayExact() {
		return getAsFloatArray();
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] { v() };
	}

	@Override
	public float getAsFloatExact() {
		return getAsFloat();
	}

	@Override
	public float getAsFloat() {
		return v();
	}

	@Override
	public long[] getAsLongArrayExact() {
		return getAsLongArray();
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] { v() };
	}

	@Override
	public long getAsLongExact() {
		return getAsLong();
	}

	@Override
	public long getAsLong() {
		return v();
	}

	@Override
	public int[] getAsIntArrayExact() {
		return getAsIntArray();
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] { v() };
	}

	@Override
	public int getAsIntExact() {
		return getAsInt();
	}

	@Override
	public int getAsInt() {
		return v();
	}

	@Override
	public short[] getAsShortArrayExact() {
		return getAsShortArray();
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] { v() };
	}

	@Override
	public short getAsShortExact() {
		return getAsShort();
	}

	@Override
	public short getAsShort() {
		return v();
	}

	@Override
	public byte[] getAsByteArrayExact() {
		return getAsByteArray();
	}

	@Override
	public byte getAsByteExact() {
		return v();
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0].intValue());
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0].intValue());
	}

	@Override
	public void setFromDoubles(double... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV((int) vals[0]);
	}

	@Override
	public void setFromFloats(float... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV((int) vals[0]);
	}

	@Override
	public void setFromShorts(short... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public void setFromBytes(byte... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}
	
	@Override
	public UnsignedInt1Algebra getAlgebra() {

		return G.UINT1;
	}
}
