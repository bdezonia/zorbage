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
package nom.bdezonia.zorbage.type.quaternion.highprec;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.misc.Hasher;
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
public final class QuaternionHighPrecisionMember 
	implements
		NumberMember<QuaternionHighPrecisionMember>,
		BigDecimalCoder,
		Allocatable<QuaternionHighPrecisionMember>, Duplicatable<QuaternionHighPrecisionMember>,
		Settable<QuaternionHighPrecisionMember>, Gettable<QuaternionHighPrecisionMember>,
		PrimitiveConversion, UniversalRepresentation,
		SetQuaternion<HighPrecisionMember>, GetQuaternion<HighPrecisionMember>,
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
		SetFromBigIntegersExact,
		SetFromBigDecimals,
		SetFromBigDecimalsExact,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<QuaternionHighPrecisionAlgebra, QuaternionHighPrecisionMember>,
		FixedSize
{
	private BigDecimal r, i, j, k;
	
	public QuaternionHighPrecisionMember() {
		primitiveInit();
	}
	
	public QuaternionHighPrecisionMember(BigDecimal r, BigDecimal i, BigDecimal j, BigDecimal k) {
		setR(r);
		setI(i);
		setJ(j);
		setK(k);
	}
	
	public QuaternionHighPrecisionMember(QuaternionHighPrecisionMember value) {
		set(value);
	}

	public QuaternionHighPrecisionMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r());
		setI(val.i());
		setJ(val.j());
		setK(val.k());
	}

	public QuaternionHighPrecisionMember(BigDecimal... vals) {
		setFromBigDecimals(vals);
	}

	public QuaternionHighPrecisionMember(BigInteger... vals) {
		setFromBigIntegers(vals);
	}

	public QuaternionHighPrecisionMember(double... vals) {
		setFromDoubles(vals);
	}

	public QuaternionHighPrecisionMember(long... vals) {
		setFromLongs(vals);
	}
	
	public BigDecimal r() { return r; }
	
	public BigDecimal i() { return i; }
	
	public BigDecimal j() { return j; }
	
	public BigDecimal k() { return k; }
	
	public void setR(BigDecimal val) { r = val; }
	
	public void setI(BigDecimal val) { i = val; }
	
	public void setJ(BigDecimal val) { j = val; }
	
	public void setK(BigDecimal val) { k = val; }
	
	@Override
	public void set(QuaternionHighPrecisionMember other) {
		//if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
	}

	@Override
	public void get(QuaternionHighPrecisionMember other) {
		//if (this == other) return;
		other.r = r;
		other.i = i;
		other.j = j;
		other.k = k;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append(r());
		builder.append(',');
		builder.append(i());
		builder.append(',');
		builder.append(j());
		builder.append(',');
		builder.append(k());
		builder.append('}');
		return builder.toString();
	}

	@Override
	public int bigDecimalCount() {
		return 4;
	}

	@Override
	public void fromBigDecimalArray(BigDecimal[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
	}

	@Override
	public void toBigDecimalArray(BigDecimal[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
	}

	@Override
	public QuaternionHighPrecisionMember allocate() {
		return new QuaternionHighPrecisionMember();
	}

	@Override
	public QuaternionHighPrecisionMember duplicate() {
		return new QuaternionHighPrecisionMember(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(QuaternionHighPrecisionMember value) {
		get(value);
	}

	@Override
	public void setV(QuaternionHighPrecisionMember value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				r(),
				i(),
				j(),
				k()
			)
		);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionRepresentation v = rep.getValue();
		setR(v.r());
		setI(v.i());
		setJ(v.j());
		setK(v.k());
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
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(BigDecimal.valueOf(v));
			else // component == 1
				this.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(BigDecimal.valueOf(v));
			else // component == 3
				this.setK(BigDecimal.valueOf(v));
		}
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(BigDecimal.valueOf(v));
			else // component == 1
				this.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(BigDecimal.valueOf(v));
			else // component == 3
				this.setK(BigDecimal.valueOf(v));
		}
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(BigDecimal.valueOf(v));
			else // component == 1
				this.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(BigDecimal.valueOf(v));
			else // component == 3
				this.setK(BigDecimal.valueOf(v));
		}
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(BigDecimal.valueOf(v));
			else // component == 1
				this.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(BigDecimal.valueOf(v));
			else // component == 3
				this.setK(BigDecimal.valueOf(v));
		}
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(BigDecimal.valueOf(v));
			else // component == 1
				this.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(BigDecimal.valueOf(v));
			else // component == 3
				this.setK(BigDecimal.valueOf(v));
		}
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(BigDecimal.valueOf(v));
			else // component == 1
				this.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(BigDecimal.valueOf(v));
			else // component == 3
				this.setK(BigDecimal.valueOf(v));
		}
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(new BigDecimal(v));
			else // component == 1
				this.setI(new BigDecimal(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(new BigDecimal(v));
			else // component == 3
				this.setK(new BigDecimal(v));
		}
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				this.setR(v);
			else // component == 1
				this.setI(v);
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				this.setJ(v);
			else // component == 3
				this.setK(v);
		}
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(new BigDecimal(v));
				else // component == 1
					this.setI(new BigDecimal(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(new BigDecimal(v));
				else // component == 3
					this.setK(new BigDecimal(v));
			}
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(v);
				else // component == 1
					this.setI(v);
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(v);
				else // component == 3
					this.setK(v);
			}
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().byteValue();
			else // component == 1
				return i().byteValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().byteValue();
			else // component == 3
				return k().byteValue();
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().shortValue();
			else // component == 1
				return i().shortValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().shortValue();
			else // component == 3
				return k().shortValue();
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().intValue();
			else // component == 1
				return i().intValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().intValue();
			else // component == 3
				return k().intValue();
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().longValue();
			else // component == 1
				return i().longValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().longValue();
			else // component == 3
				return k().longValue();
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().floatValue();
			else // component == 1
				return i().floatValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().floatValue();
			else // component == 3
				return k().floatValue();
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().doubleValue();
			else // component == 1
				return i().doubleValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().doubleValue();
			else // component == 3
				return k().doubleValue();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r().toBigInteger();
			else // component == 1
				return i().toBigInteger();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().toBigInteger();
			else // component == 3
				return k().toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return r();
			else // component == 1
				return i();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j();
			else // component == 3
				return k();
		}
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().byteValue();
				else // component == 1
					return i().byteValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().byteValue();
				else // component == 3
					return k().byteValue();
			}
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().shortValue();
				else // component == 1
					return i().shortValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().shortValue();
				else // component == 3
					return k().shortValue();
			}
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().intValue();
				else // component == 1
					return i().intValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().intValue();
				else // component == 3
					return k().intValue();
			}
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().longValue();
				else // component == 1
					return i().longValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().longValue();
				else // component == 3
					return k().longValue();
			}
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().floatValue();
				else // component == 1
					return i().floatValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().floatValue();
				else // component == 3
					return k().floatValue();
			}
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().doubleValue();
				else // component == 1
					return i().doubleValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().doubleValue();
				else // component == 3
					return k().doubleValue();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().toBigInteger();
				else // component == 1
					return i().toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().toBigInteger();
				else // component == 3
					return k().toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r();
				else // component == 1
					return i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j();
				else // component == 3
					return k();
			}
		}
	}

	@Override
	public void primitiveInit() {
		r = i = j = k = BigDecimal.ZERO;
	}

	@Override
	public void setR(HighPrecisionMember val) {
		setR(val.v());
	}

	@Override
	public void setI(HighPrecisionMember val) {
		setI(val.v());
	}

	@Override
	public void setJ(HighPrecisionMember val) {
		setJ(val.v());
	}

	@Override
	public void setK(HighPrecisionMember val) {
		setK(val.v());
	}

	@Override
	public void getR(HighPrecisionMember v) {
		v.setV(r);
	}

	@Override
	public void getI(HighPrecisionMember v) {
		v.setV(i);
	}

	@Override
	public void getJ(HighPrecisionMember v) {
		v.setV(j);
	}

	@Override
	public void getK(HighPrecisionMember v) {
		v.setV(k);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(i);
		v = Hasher.PRIME * v + Hasher.hashCode(j);
		v = Hasher.PRIME * v + Hasher.hashCode(k);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof QuaternionHighPrecisionMember) {
			return G.QHP.isEqual().call(this, (QuaternionHighPrecisionMember) o);
		}
		return false;
	}

	@Override
	public void setFromLongs(long... vals) {
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(BigDecimal.valueOf(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(BigDecimal.valueOf(vals[3]));
	}

	@Override
	public void setFromDoubles(double... vals) {
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(BigDecimal.valueOf(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(BigDecimal.valueOf(vals[3]));
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(new BigDecimal(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(new BigDecimal(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(new BigDecimal(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(new BigDecimal(vals[3]));
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(vals[1]);
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(vals[2]);
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(vals[3]);
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {r(), i(), j(), k()};
	}

	@Override
	public BigDecimal getNative(int component) {

		if (component == 0)
			return r();
		else if (component == 1)
			return i();
		else if (component == 2)
			return j();
		else if (component == 3)
			return k();
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public void setNative(int component, BigDecimal val) {

		if (component == 0)
			setR(val);
		else if (component == 1)
			setI(val);
		else if (component == 2)
			setJ(val);
		else if (component == 3)
			setK(val);
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public BigDecimal componentMin() {

		return null;  // a bigdecimal does not have a min value
	}

	@Override
	public BigDecimal componentMax() {

		return null;  // a bigdecimal does not have a max value
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] { r().toBigInteger(), i().toBigInteger(), j().toBigInteger(), k().toBigInteger() };
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] { r().doubleValue(), i().doubleValue(), j().doubleValue(), k().doubleValue() };
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] { r().floatValue(), i().floatValue(), j().floatValue(), k().floatValue() };
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] { r().longValue(), i().longValue(), j().longValue(), k().longValue() };
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] { r().intValue(), i().intValue(), j().intValue(), k().intValue() };
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] { r().shortValue(), i().shortValue(), j().shortValue(), k().shortValue() };
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] { r().byteValue(), i().byteValue(), j().byteValue(), k().byteValue() };
	}

	@Override
	public void setFromBigDecimalsExact(BigDecimal... vals) {
		setFromBigDecimals(vals);
	}

	@Override
	public void setFromBigIntegersExact(BigInteger... vals) {
		setFromBigIntegers(vals);
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
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(BigDecimal.valueOf(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(BigDecimal.valueOf(vals[3]));
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
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(BigDecimal.valueOf(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(BigDecimal.valueOf(vals[3]));
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(BigDecimal.valueOf(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(BigDecimal.valueOf(vals[3]));
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		
		if (vals.length == 0 || vals.length > 4)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(BigDecimal.valueOf(vals[0]));
		
		if (vals.length < 2)
			setI(BigDecimal.ZERO);
		else
			setI(BigDecimal.valueOf(vals[1]));
		
		if (vals.length < 3)
			setJ(BigDecimal.ZERO);
		else
			setJ(BigDecimal.valueOf(vals[2]));
		
		if (vals.length < 4)
			setK(BigDecimal.ZERO);
		else
			setK(BigDecimal.valueOf(vals[3]));
	}
	
	@Override
	public QuaternionHighPrecisionAlgebra getAlgebra() {

		return G.QHP;
	}
}
