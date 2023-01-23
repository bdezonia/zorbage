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
package nom.bdezonia.zorbage.type.gaussian.unbounded;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArray;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArray;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsByteArray;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArray;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.GetAsIntArray;
import nom.bdezonia.zorbage.algebra.GetAsLongArray;
import nom.bdezonia.zorbage.algebra.GetAsShortArray;
import nom.bdezonia.zorbage.algebra.GetComplex;
import nom.bdezonia.zorbage.algebra.GetReal;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.NativeBigIntegerSupport;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetComplex;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimals;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegers;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegersExact;
import nom.bdezonia.zorbage.algebra.SetFromBytes;
import nom.bdezonia.zorbage.algebra.SetFromBytesExact;
import nom.bdezonia.zorbage.algebra.SetFromDoubles;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromIntsExact;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromLongsExact;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.SetFromShortsExact;
import nom.bdezonia.zorbage.algebra.SetReal;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.type.integer.unbounded.UnboundedIntMember;
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
public class GaussianIntUnboundedMember
	implements
		BigIntegerCoder,
		Allocatable<GaussianIntUnboundedMember>, Duplicatable<GaussianIntUnboundedMember>,
		Settable<GaussianIntUnboundedMember>, Gettable<GaussianIntUnboundedMember>,
		UniversalRepresentation, NumberMember<GaussianIntUnboundedMember>,
		PrimitiveConversion,
		SetReal<UnboundedIntMember>, GetReal<UnboundedIntMember>,
		SetComplex<UnboundedIntMember>, GetComplex<UnboundedIntMember>,
		NativeBigIntegerSupport,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromIntsExact,
		SetFromLongs,
		SetFromLongsExact,
		SetFromFloats,
		SetFromDoubles,
		SetFromBigIntegers,
		SetFromBigIntegersExact,
		SetFromBigDecimals,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsBigIntegerArray,
		GetAsBigIntegerArrayExact,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact
{
	BigInteger r;
	BigInteger i;
	
	public GaussianIntUnboundedMember() {
		r = i = BigInteger.ZERO;
	}
	
	public GaussianIntUnboundedMember(GaussianIntUnboundedMember other) {
		set(other);
	}
	
	public GaussianIntUnboundedMember(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().toBigInteger());
		setI(val.i().toBigInteger());
	}
	
	public GaussianIntUnboundedMember(BigInteger... vals) {
		setFromBigIntegers(vals);
	}
	
	@Override
	public long dimension(int d) {
		return 0;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	public BigInteger r() { return r; }
	
	public BigInteger i() { return i; }
	
	public void setR(BigInteger val) { r = val; }
	
	public void setI(BigInteger val) { i = val; }
	
	@Override
	public void setR(UnboundedIntMember val) {
		r = val.v();
	}

	@Override
	public void getR(UnboundedIntMember v) {
		v.setV(r);
	}

	@Override
	public void setI(UnboundedIntMember val) {
		i = val.v();
	}

	@Override
	public void getI(UnboundedIntMember v) {
		v.setV(i);
	}

	@Override
	public void getV(GaussianIntUnboundedMember value) {
		get(value);
	}

	@Override
	public void setV(GaussianIntUnboundedMember value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
				new OctonionRepresentation(
					new BigDecimal(r()),
					new BigDecimal(i())
				)
			);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionRepresentation v = rep.getValue();
		setR(v.r().toBigInteger());
		setI(v.i().toBigInteger());
	}

	@Override
	public void get(GaussianIntUnboundedMember other) {
		other.r = r;
		other.i = i;
	}

	@Override
	public void set(GaussianIntUnboundedMember other) {
		r = other.r;
		i = other.i;
	}

	@Override
	public GaussianIntUnboundedMember duplicate() {
		return new GaussianIntUnboundedMember(this);
	}

	@Override
	public GaussianIntUnboundedMember allocate() {
		return new GaussianIntUnboundedMember();
	}

	@Override
	public int bigIntegerCount() {
		return 2;
	}

	@Override
	public void fromBigIntegerArray(BigInteger[] arr, int index) {
		r = arr[index+0];
		i = arr[index+1];
	}

	@Override
	public void toBigIntegerArray(BigInteger[] arr, int index) {
		arr[index+0] = r;
		arr[index+1] = i;
	}
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGINTEGER;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		if (component == 0)
			this.setR(BigInteger.valueOf(v));
		else
			this.setI(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		if (component == 0)
			this.setR(BigInteger.valueOf(v));
		else
			this.setI(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		if (component == 0)
			this.setR(BigInteger.valueOf(v));
		else
			this.setI(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		if (component == 0)
			this.setR(BigInteger.valueOf(v));
		else
			this.setI(BigInteger.valueOf(v));
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v).toBigInteger());
		else
			this.setI(BigDecimal.valueOf(v).toBigInteger());
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component == 0)
			this.setR(BigDecimal.valueOf(v).toBigInteger());
		else
			this.setI(BigDecimal.valueOf(v).toBigInteger());
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component == 0)
			this.setR(v);
		else
			this.setI(v);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component == 0)
			this.setR(v.toBigInteger());
		else
			this.setI(v.toBigInteger());
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
				this.setR(BigInteger.valueOf(v));
			else
				this.setI(BigInteger.valueOf(v));
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
				this.setR(BigInteger.valueOf(v));
			else
				this.setI(BigInteger.valueOf(v));
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
				this.setR(BigInteger.valueOf(v));
			else
				this.setI(BigInteger.valueOf(v));
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
				this.setR(BigInteger.valueOf(v));
			else
				this.setI(BigInteger.valueOf(v));
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
				this.setR(BigDecimal.valueOf(v).toBigInteger());
			else
				this.setI(BigDecimal.valueOf(v).toBigInteger());
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
				this.setR(BigDecimal.valueOf(v).toBigInteger());
			else
				this.setI(BigDecimal.valueOf(v).toBigInteger());
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
				this.setR(v);
			else
				this.setI(v);
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
				this.setR(v.toBigInteger());
			else
				this.setI(v.toBigInteger());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r().byteValue();
		if (component == 1) return i().byteValue();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r().shortValue();
		if (component == 1) return i().shortValue();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r().intValue();
		if (component == 1) return i().intValue();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r().longValue();
		if (component == 1) return i().longValue();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r().floatValue();
		if (component == 1) return i().floatValue();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r().doubleValue();
		if (component == 1) return i().doubleValue();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return r();
		if (component == 1) return i();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return new BigDecimal(r());
		if (component == 1) return new BigDecimal(i());
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
			if (component == 0) return r().byteValue();
			else return i().byteValue();
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
			if (component == 0) return r().shortValue();
			else return i().shortValue();
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
			if (component == 0) return r().intValue();
			else return i().intValue();
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
			if (component == 0) return r().longValue();
			else return i().longValue();
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
			if (component == 0) return r().floatValue();
			else return i().floatValue();
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
			if (component == 0) return r().doubleValue();
			else return i().doubleValue();
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
			if (component == 0) return r();
			else return i();
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
			if (component == 0) return new BigDecimal(r());
			else return new BigDecimal(i());
		}
	}

	@Override
	public void primitiveInit() {
		r = i = BigInteger.ZERO;
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
		if (o instanceof GaussianIntUnboundedMember) {
			return G.GAUSSU.isEqual().call(this, (GaussianIntUnboundedMember) o);
		}
		return false;
	}

	@Override
	public void setFromLongs(long... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(BigInteger.valueOf(vals[0]));
		
		if (vals.length == 2)
			setI(BigInteger.valueOf(vals[1]));
		else
			setI(BigInteger.ZERO);
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0]);
		
		if (vals.length == 2)
			setI(vals[1]);
		else
			setI(BigInteger.ZERO);
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] {r(), i()};
	}

	@Override
	public BigInteger getNative(int component) {
		
		if (component == 0)
			return r();
		else if (component == 1)
			return i();
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public void setNative(int component, BigInteger val) {

		if (component == 0)
			setR(val);
		else if (component == 1)
			setI(val);
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public BigInteger componentMin() {

		return null; // bigintegers do not have a bound
	}

	@Override
	public BigInteger componentMax() {

		return null; // bigintegers do not have a bound
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {new BigDecimal(r()), new BigDecimal(i())};
	}

	@Override
	public BigInteger[] getAsBigIntegerArrayExact() {
		return new BigInteger[] {r(), i()};
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] {r().doubleValue(), i().doubleValue()};
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] {r().floatValue(), i().floatValue()};
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] {r().longValue(), i().longValue()};
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {r().intValue(), i().intValue()};
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] {r().shortValue(), i().shortValue()};
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] {r().byteValue(), i().byteValue()};
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(vals[0].toBigInteger());
		
		if (vals.length == 2)
			setI(vals[1].toBigInteger());
		else
			setI(BigInteger.ZERO);
	}

	@Override
	public void setFromBigIntegersExact(BigInteger... vals) {
		setFromBigIntegers(vals);
	}

	@Override
	public void setFromDoubles(double... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(BigDecimal.valueOf(vals[0]).toBigInteger());
		
		if (vals.length == 2)
			setI(BigDecimal.valueOf(vals[1]).toBigInteger());
		else
			setI(BigInteger.ZERO);
	}

	@Override
	public void setFromFloats(float... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(BigDecimal.valueOf(vals[0]).toBigInteger());
		
		if (vals.length == 2)
			setI(BigDecimal.valueOf(vals[1]).toBigInteger());
		else
			setI(BigInteger.ZERO);
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

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(BigInteger.valueOf(vals[0]));
		
		if (vals.length == 2)
			setI(BigInteger.valueOf(vals[1]));
		else
			setI(BigInteger.ZERO);
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(BigInteger.valueOf(vals[0]));
		
		if (vals.length == 2)
			setI(BigInteger.valueOf(vals[1]));
		else
			setI(BigInteger.ZERO);
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {

		if (vals.length == 0 || vals.length > 2)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		
		setR(BigInteger.valueOf(vals[0]));
		
		if (vals.length == 2)
			setI(BigInteger.valueOf(vals[1]));
		else
			setI(BigInteger.ZERO);
	}
}
