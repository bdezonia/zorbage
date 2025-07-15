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
package nom.bdezonia.zorbage.type.gaussian.int16;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAlgebra;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArray;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArray;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsByteArray;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArray;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.GetAsFloatArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsIntArray;
import nom.bdezonia.zorbage.algebra.GetAsIntArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsLongArray;
import nom.bdezonia.zorbage.algebra.GetAsLongArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsShortArray;
import nom.bdezonia.zorbage.algebra.GetAsShortArrayExact;
import nom.bdezonia.zorbage.algebra.GetComplex;
import nom.bdezonia.zorbage.algebra.GetReal;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.NativeShortSupport;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimals;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegers;
import nom.bdezonia.zorbage.algebra.SetFromBytes;
import nom.bdezonia.zorbage.algebra.SetFromBytesExact;
import nom.bdezonia.zorbage.algebra.SetFromDoubles;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.SetFromShortsExact;
import nom.bdezonia.zorbage.algebra.SetReal;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
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
public class GaussianInt16Member
	implements
		ShortCoder,
		Allocatable<GaussianInt16Member>, Duplicatable<GaussianInt16Member>,
		Settable<GaussianInt16Member>, Gettable<GaussianInt16Member>,
		UniversalRepresentation, NumberMember<GaussianInt16Member>,
		PrimitiveConversion,
		SetReal<SignedInt16Member>, GetReal<SignedInt16Member>,
		SetComplex<SignedInt16Member>, GetComplex<SignedInt16Member>,
		NativeShortSupport,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromLongs,
		SetFromFloats,
		SetFromDoubles,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByteArray,
		GetAsShortArray,
		GetAsShortArrayExact,
		GetAsIntArray,
		GetAsIntArrayExact,
		GetAsLongArray,
		GetAsLongArrayExact,
		GetAsFloatArray,
		GetAsFloatArrayExact,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigIntegerArrayExact,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<GaussianInt16Algebra, GaussianInt16Member>,
		FixedSize
{
	short r;
	short i;
	
	public GaussianInt16Member() {
		r = i = 0;
	}
	
	public GaussianInt16Member(GaussianInt16Member other) {
		set(other);
	}
	
	public GaussianInt16Member(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().intValue());
		setI(val.i().intValue());
	}
	
	public GaussianInt16Member(int... vals) {
		setFromInts(vals);
	}
	
	@Override
	public long dimension(int d) {
		return 0;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	public int r() { return r; }
	
	public int i() { return i; }
	
	public void setR(int val) { r = (short) val; }
	
	public void setI(int val) { i = (short) val; }
	
	@Override
	public void setR(SignedInt16Member val) {
		r = val.v();
	}

	@Override
	public void getR(SignedInt16Member v) {
		v.setV(r);
	}

	@Override
	public void setI(SignedInt16Member val) {
		i = val.v();
	}

	@Override
	public void getI(SignedInt16Member v) {
		v.setV(i);
	}

	@Override
	public void getV(GaussianInt16Member value) {
		get(value);
	}

	@Override
	public void setV(GaussianInt16Member value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
				new OctonionRepresentation(
					BigDecimal.valueOf(r()),
					BigDecimal.valueOf(i())
				)
			);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionRepresentation v = rep.getValue();
		setR(v.r().intValue());
		setI(v.i().intValue());
	}

	@Override
	public void get(GaussianInt16Member other) {
		other.r = r;
		other.i = i;
	}

	@Override
	public void set(GaussianInt16Member other) {
		r = other.r;
		i = other.i;
	}

	@Override
	public GaussianInt16Member duplicate() {
		return new GaussianInt16Member(this);
	}

	@Override
	public GaussianInt16Member allocate() {
		return new GaussianInt16Member();
	}

	@Override
	public int shortCount() {
		return 2;
	}

	@Override
	public void fromShortArray(short[] arr, int index) {
		r = arr[index+0];
		i = arr[index+1];
	}

	@Override
	public void toShortArray(short[] arr, int index) {
		arr[index+0] = r;
		arr[index+1] = i;
	}
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.SHORT;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		if (component == 0)
			this.setR((int) v);
		else
			this.setI((int) v);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		if (component == 0)
			this.setR((int) v);
		else
			this.setI((int) v);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		if (component == 0)
			this.setR((int) v);
		else
			this.setI((int) v);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		if (component == 0)
			this.setR((int) v);
		else
			this.setI((int) v);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		if (component == 0)
			this.setR((int) v);
		else
			this.setI((int) v);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component == 0)
			this.setR((int) v);
		else
			this.setI((int) v);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component == 0)
			this.setR(v.intValue());
		else
			this.setI(v.intValue());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component == 0)
			this.setR(v.intValue());
		else
			this.setI(v.intValue());
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR((int) v);
			else
				this.setI((int) v);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR((int) v);
			else
				this.setI((int) v);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR((int) v);
			else
				this.setI((int) v);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR((int) v);
			else
				this.setI((int) v);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR((int) v);
			else
				this.setI((int) v);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR((int) v);
			else
				this.setI((int) v);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR(v.intValue());
			else
				this.setI(v.intValue());
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0)
				this.setR(v.intValue());
			else
				this.setI(v.intValue());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (byte) r();
		if (component == 1) return (byte) i();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (short) r();
		if (component == 1) return (short) i();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r();
		if (component == 1) return i();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r();
		if (component == 1) return i();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r();
		if (component == 1) return i();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r();
		if (component == 1) return i();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigDecimal.valueOf(r()).toBigInteger();
		if (component == 1) return BigDecimal.valueOf(i()).toBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigDecimal.valueOf(r());
		if (component == 1) return BigDecimal.valueOf(i());
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return (byte) r();
			else return (byte) i();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return (short) r();
			else return (short) i();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return r();
			else return i();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return r();
			else return i();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return r();
			else return i();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return r();
			else return i();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return BigDecimal.valueOf(r()).toBigInteger();
			else return BigDecimal.valueOf(i()).toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			if (component == 0) return BigDecimal.valueOf(r());
			else return BigDecimal.valueOf(i());
		}
	}

	@Override
	public void primitiveInit() {
		r = i = 0;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append(r());
		builder.append(',');
		builder.append(i());
		builder.append('}');
		return builder.toString();
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(i);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof GaussianInt16Member) {
			return G.GAUSS16.isEqual().call(this, (GaussianInt16Member) o);
		}
		return false;
	}

	@Override
	public void setFromInts(int... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0]);
		
		if (vals.length == 2)
			setI(vals[1]);
		else
			setI(0);
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {r(), i()};
	}

	@Override
	public short getNative(int component) {

		if (component == 0)
			return r;
		else if (component == 1)
			return i;
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public void setNative(int component, short val) {

		if (component == 0)
			setR(val);
		else if (component == 1)
			setI(val);
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public Short componentMin() {

		return Short.MIN_VALUE;
	}

	@Override
	public Short componentMax() {

		return Short.MAX_VALUE;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {BigDecimal.valueOf(r()), BigDecimal.valueOf(i())};
	}

	@Override
	public BigInteger[] getAsBigIntegerArrayExact() {
		return getAsBigIntegerArray();
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] {BigDecimal.valueOf(r()).toBigInteger(), BigDecimal.valueOf(i()).toBigInteger()};
	}

	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] {(double) r(), (double) i()};
	}

	@Override
	public float[] getAsFloatArrayExact() {
		return getAsFloatArray();
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] {(float) r(), (float) i()};
	}

	@Override
	public long[] getAsLongArrayExact() {
		return getAsLongArray();
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] {(long) r(), (long) i()};
	}

	@Override
	public int[] getAsIntArrayExact() {
		return getAsIntArray();
	}

	@Override
	public short[] getAsShortArrayExact() {
		return getAsShortArray();
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] {(short) r(), (short) i()};
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] {(byte) r(), (byte) i()};
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0].shortValue());
		
		if (vals.length == 2)
			setI(vals[1].shortValue());
		else
			setI(0);
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0].shortValue());
		
		if (vals.length == 2)
			setI(vals[1].shortValue());
		else
			setI(0);
	}

	@Override
	public void setFromDoubles(double... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR((short) vals[0]);
		
		if (vals.length == 2)
			setI((short) vals[1]);
		else
			setI(0);
	}

	@Override
	public void setFromFloats(float... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR((short) vals[0]);
		
		if (vals.length == 2)
			setI((short) vals[1]);
		else
			setI(0);
	}

	@Override
	public void setFromLongs(long... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR((short) vals[0]);
		
		if (vals.length == 2)
			setI((short) vals[1]);
		else
			setI(0);
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0]);
		
		if (vals.length == 2)
			setI(vals[1]);
		else
			setI(0);
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0]);
		
		if (vals.length == 2)
			setI(vals[1]);
		else
			setI(0);
	}
	
	@Override
	public GaussianInt16Algebra getAlgebra() {

		return G.GAUSS16;
	}
}
