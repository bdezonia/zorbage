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
package nom.bdezonia.zorbage.type.quaternion.float128;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
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
public final class QuaternionFloat128Member 
	implements
		NumberMember<QuaternionFloat128Member>,
		ByteCoder,
		Allocatable<QuaternionFloat128Member>, Duplicatable<QuaternionFloat128Member>,
		Settable<QuaternionFloat128Member>, Gettable<QuaternionFloat128Member>,
		PrimitiveConversion, UniversalRepresentation,
		SetQuaternion<Float128Member>, GetQuaternion<Float128Member>
{

	private final Float128Member r, i, j, k;
	
	public QuaternionFloat128Member() {
		this.r = new Float128Member();
		this.i = new Float128Member();
		this.j = new Float128Member();
		this.k = new Float128Member();
		primitiveInit();
	}
	
	// Prefer this ctor over the BugDecimal based one since it can propagate
	// nan, inf, etc.
	
	public QuaternionFloat128Member(Float128Member r, Float128Member i, Float128Member j, Float128Member k) {
		this.r = new Float128Member();
		this.i = new Float128Member();
		this.j = new Float128Member();
		this.k = new Float128Member();
		setR(r);
		setI(i);
		setJ(j);
		setK(k);
	}

	// Prefer the previous ctor over this one since this cannot represent
	// nan, inf, etc.
	
	public QuaternionFloat128Member(BigDecimal r, BigDecimal i, BigDecimal j, BigDecimal k) {
		this.r = new Float128Member();
		this.i = new Float128Member();
		this.j = new Float128Member();
		this.k = new Float128Member();
		setR(r);
		setI(i);
		setJ(j);
		setK(k);
	}
	
	public QuaternionFloat128Member(QuaternionFloat128Member value) {
		this.r = new Float128Member();
		this.i = new Float128Member();
		this.j = new Float128Member();
		this.k = new Float128Member();
		set(value);
	}

	public QuaternionFloat128Member(String value) {
		this.r = new Float128Member();
		this.i = new Float128Member();
		this.j = new Float128Member();
		this.k = new Float128Member();
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r());
		setI(val.i());
		setJ(val.j());
		setK(val.k());
	}

	public Float128Member r() { return r; }
	
	public Float128Member i() { return i; }
	
	public Float128Member j() { return j; }
	
	public Float128Member k() { return k; }
	
	public void setR(BigDecimal val) { r.setV(val); }
	
	public void setI(BigDecimal val) { i.setV(val); }
	
	public void setJ(BigDecimal val) { j.setV(val); }
	
	public void setK(BigDecimal val) { k.setV(val); }
	
	@Override
	public void set(QuaternionFloat128Member other) {
		//if (this == other) return;
		r.set(other.r);
		i.set(other.i);
		j.set(other.j);
		k.set(other.k);
	}

	@Override
	public void get(QuaternionFloat128Member other) {
		//if (this == other) return;
		other.r.set(r);
		other.i.set(i);
		other.j.set(j);
		other.k.set(k);
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
	public int byteCount() {
		return 64;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		r.fromByteArray(arr, index);
		i.fromByteArray(arr, index+16);
		j.fromByteArray(arr, index+32);
		k.fromByteArray(arr, index+48);
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		r.toByteArray(arr, index);
		i.toByteArray(arr, index+16);
		j.toByteArray(arr, index+32);
		k.toByteArray(arr, index+48);
	}

	@Override
	public QuaternionFloat128Member allocate() {
		return new QuaternionFloat128Member();
	}

	@Override
	public QuaternionFloat128Member duplicate() {
		return new QuaternionFloat128Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(QuaternionFloat128Member value) {
		get(value);
	}

	@Override
	public void setV(QuaternionFloat128Member value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				r().v(),
				i().v(),
				j().v(),
				k().v()
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
				return r().v().byteValue();
			else // component == 1
				return i().v().byteValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().byteValue();
			else // component == 3
				return k().v().byteValue();
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
				return r().v().shortValue();
			else // component == 1
				return i().v().shortValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().shortValue();
			else // component == 3
				return k().v().shortValue();
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
				return r().v().intValue();
			else // component == 1
				return i().v().intValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().intValue();
			else // component == 3
				return k().v().intValue();
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
				return r().v().longValue();
			else // component == 1
				return i().v().longValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().longValue();
			else // component == 3
				return k().v().longValue();
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
				return r().v().floatValue();
			else // component == 1
				return i().v().floatValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().floatValue();
			else // component == 3
				return k().v().floatValue();
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
				return r().v().doubleValue();
			else // component == 1
				return i().v().doubleValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().doubleValue();
			else // component == 3
				return k().v().doubleValue();
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
				return r().v().toBigInteger();
			else // component == 1
				return i().v().toBigInteger();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v().toBigInteger();
			else // component == 3
				return k().v().toBigInteger();
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
				return r().v();
			else // component == 1
				return i().v();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return j().v();
			else // component == 3
				return k().v();
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
					return r().v().byteValue();
				else // component == 1
					return i().v().byteValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().byteValue();
				else // component == 3
					return k().v().byteValue();
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
					return r().v().shortValue();
				else // component == 1
					return i().v().shortValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().shortValue();
				else // component == 3
					return k().v().shortValue();
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
					return r().v().intValue();
				else // component == 1
					return i().v().intValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().intValue();
				else // component == 3
					return k().v().intValue();
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
					return r().v().longValue();
				else // component == 1
					return i().v().longValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().longValue();
				else // component == 3
					return k().v().longValue();
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
					return r().v().floatValue();
				else // component == 1
					return i().v().floatValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().floatValue();
				else // component == 3
					return k().v().floatValue();
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
					return r().v().doubleValue();
				else // component == 1
					return i().v().doubleValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().doubleValue();
				else // component == 3
					return k().v().doubleValue();
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
					return r().v().toBigInteger();
				else // component == 1
					return i().v().toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v().toBigInteger();
				else // component == 3
					return k().v().toBigInteger();
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
					return r().v();
				else // component == 1
					return i().v();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().v();
				else // component == 3
					return k().v();
			}
		}
	}

	@Override
	public void primitiveInit() {
		r.setPosZero();
		i.setPosZero();
		j.setPosZero();
		k.setPosZero();
	}

	@Override
	public void setR(Float128Member val) {
		r.set(val);
	}

	@Override
	public void setI(Float128Member val) {
		i.set(val);
	}

	@Override
	public void setJ(Float128Member val) {
		j.set(val);
	}

	@Override
	public void setK(Float128Member val) {
		k.set(val);
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
	public void getJ(Float128Member v) {
		v.set(j);
	}

	@Override
	public void getK(Float128Member v) {
		v.set(k);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + r.hashCode();
		v = Hasher.PRIME * v + i.hashCode();
		v = Hasher.PRIME * v + j.hashCode();
		v = Hasher.PRIME * v + k.hashCode();
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof QuaternionFloat128Member) {
			return G.QQUAD.isEqual().call(this, (QuaternionFloat128Member) o);
		}
		return false;
	}
}
