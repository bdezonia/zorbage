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
package nom.bdezonia.zorbage.type.complex.float128;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
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
public final class ComplexFloat128Member
	implements
		ByteCoder,
		Allocatable<ComplexFloat128Member>, Duplicatable<ComplexFloat128Member>,
		Settable<ComplexFloat128Member>, Gettable<ComplexFloat128Member>,
		NumberMember<ComplexFloat128Member>, PrimitiveConversion,
		UniversalRepresentation, SetComplex<Float128Member>, GetComplex<Float128Member>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromIntsExact,
		SetFromLongs,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
		SetFromDoublesExact,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<ComplexFloat128Algebra, ComplexFloat128Member>,
		FixedSize
{
	private final Float128Member r, i;
	
	public ComplexFloat128Member() {
		r = new Float128Member();
		i = new Float128Member();
	}
	
	// Prefer this ctor over the BigDecimal based one since it can propagate
	// nan, inf, etc.
	
	public ComplexFloat128Member(Float128Member rvalue, Float128Member ivalue) {
		this();
		setR(rvalue);
		setI(ivalue);
	}

	// Prefer the previous ctor over this one since this cannot represent
	// nan, inf, etc.
	
	public ComplexFloat128Member(BigDecimal... vals) {
		this();
		setFromBigDecimals(vals);
	}
	
	public ComplexFloat128Member(ComplexFloat128Member value) {
		this();
		set(value);
	}

	public ComplexFloat128Member(String value) {
		this();
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r());
		setI(val.i());
	}
	
	public Float128Member r() { return r; }
	
	public Float128Member i() { return i; }
	
	@Override
	public void get(ComplexFloat128Member other) {
		other.r.set(r);
		other.i.set(i);
	}
	
	@Override
	public void set(ComplexFloat128Member other) {
		r.set(other.r);
		i.set(other.i);
	}
	
	public void setR(BigDecimal val) {
		r.setV(val);
	}
	
	public void setI(BigDecimal val) {
		i.setV(val);
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
	public int byteCount() {
		return 32;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		r.fromByteArray(arr, index);
		i.fromByteArray(arr, index+16);
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		r.toByteArray(arr, index);
		i.toByteArray(arr, index+16);
	}

	@Override
	public ComplexFloat128Member allocate() {
		return new ComplexFloat128Member();
	}

	@Override
	public ComplexFloat128Member duplicate() {
		return new ComplexFloat128Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(ComplexFloat128Member value) {
		get(value);
	}

	@Override
	public void setV(ComplexFloat128Member value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				r.v(),
				i.v()
			)
		);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionRepresentation v = rep.getValue();
		setR(v.r());
		setI(v.i());
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public long dimension(int i) {
		return 0;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v));
		else
			this.setI(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v));
		else
			this.setI(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v));
		else
			this.setI(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v));
		else
			this.setI(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v));
		else
			this.setI(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v));
		else
			this.setI(BigDecimal.valueOf(v));
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component == 0)
			this.setR(new BigDecimal(v));
		else
			this.setI(new BigDecimal(v));
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component == 0)
			this.setR(v);
		else
			this.setI(v);
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
				this.setR(BigDecimal.valueOf(v));
			else
				this.setI(BigDecimal.valueOf(v));
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
				this.setR(BigDecimal.valueOf(v));
			else
				this.setI(BigDecimal.valueOf(v));
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
				this.setR(BigDecimal.valueOf(v));
			else
				this.setI(BigDecimal.valueOf(v));
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
				this.setR(BigDecimal.valueOf(v));
			else
				this.setI(BigDecimal.valueOf(v));
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
				this.setR(BigDecimal.valueOf(v));
			else
				this.setI(BigDecimal.valueOf(v));
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
				this.setR(BigDecimal.valueOf(v));
			else
				this.setI(BigDecimal.valueOf(v));
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
				this.setR(new BigDecimal(v));
			else
				this.setI(new BigDecimal(v));
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
				this.setR(v);
			else
				this.setI(v);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsByte();
		if (component == 1) return i.getAsByte();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsShort();
		if (component == 1) return i.getAsShort();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsInt();
		if (component == 1) return i.getAsInt();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsLong();
		if (component == 1) return i.getAsLong();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsFloat();
		if (component == 1) return i.getAsFloat();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsDouble();
		if (component == 1) return i.getAsDouble();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsBigInteger();
		if (component == 1) return i.getAsBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r.getAsBigDecimal();
		if (component == 1) return i.getAsBigDecimal();
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
			if (component == 0) return r.getAsByte();
			else return i.getAsByte();
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
			if (component == 0) return r.getAsShort();
			else return i.getAsShort();
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
			if (component == 0) return r.getAsInt();
			else return i.getAsInt();
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
			if (component == 0) return r.getAsLong();
			else return i.getAsLong();
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
			if (component == 0) return r.getAsFloat();
			else return i.getAsFloat();
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
			if (component == 0) return r.getAsDouble();
			else return i.getAsDouble();
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
			if (component == 0) return r.getAsBigInteger();
			else return i.getAsBigInteger();
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
			if (component == 0) return r.getAsBigDecimal();
			else return i.getAsBigDecimal();
		}
	}

	@Override
	public void primitiveInit() {
		r.setPosZero();
		i.setPosZero();
	}

	@Override
	public void setR(Float128Member val) {
		r.setR(val);
	}

	@Override
	public void setI(Float128Member val) {
		i.set(val);
	}

	@Override
	public void getR(Float128Member v) {
		v.set(r);
	}

	@Override
	public void getI(Float128Member v) {
		v.set(i);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + r.hashCode();
		v = Hasher.PRIME * v + i.hashCode();
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ComplexFloat128Member) {
			return G.CQUAD.isEqual().call(this, (ComplexFloat128Member) o);
		}
		return false;
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromIntsExact(int... vals) {
		setFromInts(vals);
	}

	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}

	@Override
	public void setFromDoublesExact(double... vals) {
		setFromDoubles(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
	}

	@Override
	public void setFromShorts(short... vals) {
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
	}

	@Override
	public void setFromInts(int... vals) {
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
	}

	@Override
	public void setFromLongs(long... vals) {
		
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
	}

	@Override
	public void setFromFloats(float... vals) {
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
	}

	@Override
	public void setFromDoubles(double... vals) {
		
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(new BigDecimal(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(new BigDecimal(vals[1]));
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		
		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(vals[1]);
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {r().getAsBigDecimal(), i().getAsBigDecimal()};
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] {r().getAsBigInteger(), i().getAsBigInteger()};
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] {r().getAsDouble(), i().getAsDouble()};
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] {r().getAsFloat(), i().getAsFloat()};
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] {r().getAsLong(), i().getAsLong()};
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {r().getAsInt(), i().getAsInt()};
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] {r().getAsShort(), i().getAsShort()};
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] {r().getAsByte(), i().getAsByte()};
	}
	
	@Override
	public ComplexFloat128Algebra getAlgebra() {

		return G.CQUAD;
	}
}
