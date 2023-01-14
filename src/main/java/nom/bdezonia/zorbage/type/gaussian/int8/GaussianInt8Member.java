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
package nom.bdezonia.zorbage.type.gaussian.int8;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimal;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArray;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalExact;
import nom.bdezonia.zorbage.algebra.GetAsBigInteger;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArray;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerExact;
import nom.bdezonia.zorbage.algebra.GetAsByte;
import nom.bdezonia.zorbage.algebra.GetAsByteArray;
import nom.bdezonia.zorbage.algebra.GetAsByteArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsByteExact;
import nom.bdezonia.zorbage.algebra.GetAsDouble;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArray;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsDoubleExact;
import nom.bdezonia.zorbage.algebra.GetAsFloat;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.GetAsFloatArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsFloatExact;
import nom.bdezonia.zorbage.algebra.GetAsInt;
import nom.bdezonia.zorbage.algebra.GetAsIntArray;
import nom.bdezonia.zorbage.algebra.GetAsIntArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsIntExact;
import nom.bdezonia.zorbage.algebra.GetAsLong;
import nom.bdezonia.zorbage.algebra.GetAsLongArray;
import nom.bdezonia.zorbage.algebra.GetAsLongArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsLongExact;
import nom.bdezonia.zorbage.algebra.GetAsShort;
import nom.bdezonia.zorbage.algebra.GetAsShortArray;
import nom.bdezonia.zorbage.algebra.GetAsShortArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsShortExact;
import nom.bdezonia.zorbage.algebra.GetComplex;
import nom.bdezonia.zorbage.algebra.GetReal;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.NativeByteSupport;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimal;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimalExact;
import nom.bdezonia.zorbage.algebra.SetFromBigInteger;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegerExact;
import nom.bdezonia.zorbage.algebra.SetFromByte;
import nom.bdezonia.zorbage.algebra.SetFromByteExact;
import nom.bdezonia.zorbage.algebra.SetFromDouble;
import nom.bdezonia.zorbage.algebra.SetFromDoubleExact;
import nom.bdezonia.zorbage.algebra.SetFromFloat;
import nom.bdezonia.zorbage.algebra.SetFromFloatExact;
import nom.bdezonia.zorbage.algebra.SetFromInt;
import nom.bdezonia.zorbage.algebra.SetFromIntExact;
import nom.bdezonia.zorbage.algebra.SetFromLong;
import nom.bdezonia.zorbage.algebra.SetFromLongExact;
import nom.bdezonia.zorbage.algebra.SetFromShort;
import nom.bdezonia.zorbage.algebra.SetFromShortExact;
import nom.bdezonia.zorbage.algebra.SetReal;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Member;
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
public class GaussianInt8Member
	implements
		ByteCoder,
		Allocatable<GaussianInt8Member>, Duplicatable<GaussianInt8Member>,
		Settable<GaussianInt8Member>, Gettable<GaussianInt8Member>,
		UniversalRepresentation, NumberMember<GaussianInt8Member>,
		PrimitiveConversion,
		SetReal<SignedInt8Member>, GetReal<SignedInt8Member>,
		SetComplex<SignedInt8Member>, GetComplex<SignedInt8Member>,
		NativeByteSupport,
		SetFromByte,
		SetFromByteExact,
		SetFromShort,
		SetFromInt,
		SetFromLong,
		SetFromFloat,
		SetFromDouble,
		SetFromBigInteger,
		SetFromBigDecimal,
		GetAsByteArray,
		GetAsByteArrayExact,
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
		GetAsBigDecimalArrayExact
{
	byte r;
	byte i;
	
	public GaussianInt8Member() {
		r = i = 0;
	}
	
	public GaussianInt8Member(GaussianInt8Member other) {
		set(other);
	}
	
	public GaussianInt8Member(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().intValue());
		setI(val.i().intValue());
	}
	
	public GaussianInt8Member(int... vals) {
		setFromInt(vals);
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
	
	public void setR(int val) { r = (byte) val; }
	
	public void setI(int val) { i = (byte) val; }
	
	@Override
	public void setR(SignedInt8Member val) {
		r = val.v();
	}

	@Override
	public void getR(SignedInt8Member v) {
		v.setV(r);
	}

	@Override
	public void setI(SignedInt8Member val) {
		i = val.v();
	}

	@Override
	public void getI(SignedInt8Member v) {
		v.setV(i);
	}

	@Override
	public void getV(GaussianInt8Member value) {
		get(value);
	}

	@Override
	public void setV(GaussianInt8Member value) {
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
	public void get(GaussianInt8Member other) {
		other.r = r;
		other.i = i;
	}

	@Override
	public void set(GaussianInt8Member other) {
		r = other.r;
		i = other.i;
	}

	@Override
	public GaussianInt8Member duplicate() {
		return new GaussianInt8Member(this);
	}

	@Override
	public GaussianInt8Member allocate() {
		return new GaussianInt8Member();
	}

	@Override
	public int byteCount() {
		return 2;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		r = arr[index+0];
		i = arr[index+1];
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		arr[index+0] = r;
		arr[index+1] = i;
	}
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BYTE;
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
			this.setR(v);
		else
			this.setI(v);
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
				this.setR(v);
			else
				this.setI(v);
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
		if (o instanceof GaussianInt8Member) {
			return G.GAUSS8.isEqual().call(this, (GaussianInt8Member) o);
		}
		return false;
	}

	@Override
	public void setFromInt(int... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0]);
		
		if (vals.length == 2)
			setI(vals[1]);
		else
			setI(0);
	}

	@Override
	public void setFromLong(long... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR((int) vals[0]);
		
		if (vals.length == 2)
			setI((int) vals[1]);
		else
			setI(0);
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {r(), i()};
	}

	@Override
	public byte getNative(int component) {

		if (component == 0)
			return r;
		else if (component == 1)
			return i;
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public void setNative(int component, byte val) {

		if (component == 0)
			setR(val);
		else if (component == 1)
			setI(val);
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public Byte componentMin() {

		return Byte.MIN_VALUE;
	}

	@Override
	public Byte componentMax() {

		return Byte.MAX_VALUE;
	}
}
