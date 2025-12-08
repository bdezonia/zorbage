/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.octonion.float128;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NumberType;
import nom.bdezonia.zorbage.algebra.type.markers.OctonionType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
public final class OctonionFloat128Member
	implements
		NumberMember<OctonionFloat128Member>,
		ByteCoder,
		Allocatable<OctonionFloat128Member>, Duplicatable<OctonionFloat128Member>,
		Settable<OctonionFloat128Member>, Gettable<OctonionFloat128Member>,
		PrimitiveConversion,
		UniversalRepresentation, SetOctonion<Float128Member>, GetOctonion<Float128Member>,
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
		GetAlgebra<OctonionFloat128Algebra, OctonionFloat128Member>,
		ApproximateType,
		CompoundType,
		FixedSize,
		InfinityIncludedType,
		NanIncludedType,
		NumberType,
		OctonionType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	private final Float128Member r, i, j, k, l, i0, j0, k0;
	
	public OctonionFloat128Member() {
		r = new Float128Member();
		i = new Float128Member();
		j = new Float128Member();
		k = new Float128Member();
		l = new Float128Member();
		i0 = new Float128Member();
		j0 = new Float128Member();
		k0 = new Float128Member();
	}
	
	// Prefer this ctor over the BigDecimal based one since it can propagate
	// nan, inf, etc.
	
	public OctonionFloat128Member(Float128Member r, Float128Member i, Float128Member j, Float128Member k, Float128Member l, Float128Member i0, Float128Member j0, Float128Member k0) {
		this();
		setR(r);
		setI(i);
		setJ(j);
		setK(k);
		setL(l);
		setI0(i0);
		setJ0(j0);
		setK0(k0);
	}

	public OctonionFloat128Member(OctonionFloat128Member value) {
		this();
		set(value);
	}

	public OctonionFloat128Member(String value) {
		this();
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r());
		setI(val.i());
		setJ(val.j());
		setK(val.k());
		setL(val.l());
		setI0(val.i0());
		setJ0(val.j0());
		setK0(val.k0());
	}
	
	public OctonionFloat128Member(BigDecimal... vals) {
		this();
		setFromBigDecimals(vals);
	}
	
	public OctonionFloat128Member(BigInteger... vals) {
		this();
		setFromBigIntegers(vals);
	}
	
	public OctonionFloat128Member(double... vals) {
		this();
		setFromDoubles(vals);
	}
	
	public OctonionFloat128Member(long... vals) {
		this();
		setFromLongs(vals);
	}

	public Float128Member r() { return r; }
	
	public Float128Member i() { return i; }
	
	public Float128Member j() { return j; }
	
	public Float128Member k() { return k; }
	
	public Float128Member l() { return l; }
	
	public Float128Member i0() { return i0; }
	
	public Float128Member j0() { return j0; }
	
	public Float128Member k0() { return k0; }
	
	public void setR(BigDecimal val) { r.setV(val); }
	
	public void setI(BigDecimal val) { i.setV(val); }
	
	public void setJ(BigDecimal val) { j.setV(val); }
	
	public void setK(BigDecimal val) { k.setV(val); }
	
	public void setL(BigDecimal val) { l.setV(val); }
	
	public void setI0(BigDecimal val) { i0.setV(val); }
	
	public void setJ0(BigDecimal val) { j0.setV(val); }
	
	public void setK0(BigDecimal val) { k0.setV(val); }
	
	@Override
	public void set(OctonionFloat128Member other) {
		//if (this == other) return;
		r.set(other.r);
		i.set(other.i);
		j.set(other.j);
		k.set(other.k);
		l.set(other.l);
		i0.set(other.i0);
		j0.set(other.j0);
		k0.set(other.k0);
	}

	@Override
	public void get(OctonionFloat128Member other) {
		//if (this == other) return;
		other.r.set(r);
		other.i.set(i);
		other.j.set(j);
		other.k.set(k);
		other.l.set(l);
		other.i0.set(i0);
		other.j0.set(j0);
		other.k0.set(k0);
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
		builder.append(',');
		builder.append(l());
		builder.append(',');
		builder.append(i0());
		builder.append(',');
		builder.append(j0());
		builder.append(',');
		builder.append(k0());
		builder.append('}');
		return builder.toString();
	}

	@Override
	public int byteCount() {
		return 128;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		r.fromByteArray(arr, index);
		i.fromByteArray(arr, index+16);
		j.fromByteArray(arr, index+32);
		k.fromByteArray(arr, index+48);
		l.fromByteArray(arr, index+64);
		i0.fromByteArray(arr, index+80);
		j0.fromByteArray(arr, index+96);
		k0.fromByteArray(arr, index+112);
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		r.toByteArray(arr, index);
		i.toByteArray(arr, index+16);
		j.toByteArray(arr, index+32);
		k.toByteArray(arr, index+48);
		l.toByteArray(arr, index+64);
		i0.toByteArray(arr, index+80);
		j0.toByteArray(arr, index+96);
		k0.toByteArray(arr, index+112);
	}

	@Override
	public OctonionFloat128Member allocate() {
		return new OctonionFloat128Member();
	}

	@Override
	public OctonionFloat128Member duplicate() {
		return new OctonionFloat128Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(OctonionFloat128Member value) {
		get(value);
	}

	@Override
	public void setV(OctonionFloat128Member value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				r().v(),
				i().v(),
				j().v(),
				k().v(),
				l().v(),
				i0().v(),
				j0().v(),
				k0().v()
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
		setL(v.l());
		setI0(v.i0());
		setJ0(v.j0());
		setK0(v.k0());
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
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(new BigDecimal(v));
				else // component == 5
					this.setI0(new BigDecimal(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(new BigDecimal(v));
				else // component == 7
					this.setK0(new BigDecimal(v));
			}
		}
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(v);
				else // component == 5
					this.setI0(v);
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(v);
				else // component == 7
					this.setK0(v);
			}
		}
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(new BigDecimal(v));
					else // component == 5
						this.setI0(new BigDecimal(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(new BigDecimal(v));
					else // component == 7
						this.setK0(new BigDecimal(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(v);
					else // component == 5
						this.setI0(v);
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(v);
					else // component == 7
						this.setK0(v);
				}
			}
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsByte();
				else // component == 1
					return i().getAsByte();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsByte();
				else // component == 3
					return k().getAsByte();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsByte();
				else // component == 5
					return i0().getAsByte();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsByte();
				else // component == 7
					return k0().getAsByte();
			}
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsShort();
				else // component == 1
					return i().getAsShort();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsShort();
				else // component == 3
					return k().getAsShort();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsShort();
				else // component == 5
					return i0().getAsShort();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsShort();
				else // component == 7
					return k0().getAsShort();
			}
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsInt();
				else // component == 1
					return i().getAsInt();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsInt();
				else // component == 3
					return k().getAsInt();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsInt();
				else // component == 5
					return i0().getAsInt();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsInt();
				else // component == 7
					return k0().getAsInt();
			}
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsLong();
				else // component == 1
					return i().getAsLong();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsLong();
				else // component == 3
					return k().getAsLong();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsLong();
				else // component == 5
					return i0().getAsLong();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsLong();
				else // component == 7
					return k0().getAsLong();
			}
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsFloat();
				else // component == 1
					return i().getAsFloat();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsFloat();
				else // component == 3
					return k().getAsFloat();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsFloat();
				else // component == 5
					return i0().getAsFloat();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsFloat();
				else // component == 7
					return k0().getAsFloat();
			}
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsDouble();
				else // component == 1
					return i().getAsDouble();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsDouble();
				else // component == 3
					return k().getAsDouble();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsDouble();
				else // component == 5
					return i0().getAsDouble();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsDouble();
				else // component == 7
					return k0().getAsDouble();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsBigInteger();
				else // component == 1
					return i().getAsBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsBigInteger();
				else // component == 3
					return k().getAsBigInteger();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsBigInteger();
				else // component == 5
					return i0().getAsBigInteger();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsBigInteger();
				else // component == 7
					return k0().getAsBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().getAsBigDecimal();
				else // component == 1
					return i().getAsBigDecimal();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().getAsBigDecimal();
				else // component == 3
					return k().getAsBigDecimal();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().getAsBigDecimal();
				else // component == 5
					return i0().getAsBigDecimal();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().getAsBigDecimal();
				else // component == 7
					return k0().getAsBigDecimal();
			}
		}
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsByte();
					else // component == 1
						return i().getAsByte();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsByte();
					else // component == 3
						return k().getAsByte();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsByte();
					else // component == 5
						return i0().getAsByte();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsByte();
					else // component == 7
						return k0().getAsByte();
				}
			}
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsShort();
					else // component == 1
						return i().getAsShort();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsShort();
					else // component == 3
						return k().getAsShort();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsShort();
					else // component == 5
						return i0().getAsShort();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsShort();
					else // component == 7
						return k0().getAsShort();
				}
			}
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsInt();
					else // component == 1
						return i().getAsInt();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsInt();
					else // component == 3
						return k().getAsInt();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsInt();
					else // component == 5
						return i0().getAsInt();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsInt();
					else // component == 7
						return k0().getAsInt();
				}
			}
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsLong();
					else // component == 1
						return i().getAsLong();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsLong();
					else // component == 3
						return k().getAsLong();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsLong();
					else // component == 5
						return i0().getAsLong();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsLong();
					else // component == 7
						return k0().getAsLong();
				}
			}
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsFloat();
					else // component == 1
						return i().getAsFloat();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsFloat();
					else // component == 3
						return k().getAsFloat();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsFloat();
					else // component == 5
						return i0().getAsFloat();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsFloat();
					else // component == 7
						return k0().getAsFloat();
				}
			}
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsDouble();
					else // component == 1
						return i().getAsDouble();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsDouble();
					else // component == 3
						return k().getAsDouble();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsDouble();
					else // component == 5
						return i0().getAsDouble();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsDouble();
					else // component == 7
						return k0().getAsDouble();
				}
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsBigInteger();
					else // component == 1
						return i().getAsBigInteger();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsBigInteger();
					else // component == 3
						return k().getAsBigInteger();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsBigInteger();
					else // component == 5
						return i0().getAsBigInteger();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsBigInteger();
					else // component == 7
						return k0().getAsBigInteger();
				}
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().getAsBigDecimal();
					else // component == 1
						return i().getAsBigDecimal();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().getAsBigDecimal();
					else // component == 3
						return k().getAsBigDecimal();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().getAsBigDecimal();
					else // component == 5
						return i0().getAsBigDecimal();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().getAsBigDecimal();
					else // component == 7
						return k0().getAsBigDecimal();
				}
			}
		}
	}

	@Override
	public void primitiveInit() {
		r.setPosZero();
		i.setPosZero();
		j.setPosZero();
		k.setPosZero();
		l.setPosZero();
		i0.setPosZero();
		j0.setPosZero();
		k0.setPosZero();
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
	public void setL(Float128Member val) {
		l.set(val);
	}

	@Override
	public void setI0(Float128Member val) {
		i0.set(val);
	}

	@Override
	public void setJ0(Float128Member val) {
		j0.set(val);
	}

	@Override
	public void setK0(Float128Member val) {
		k0.set(val);
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
	public void getL(Float128Member v) {
		v.set(l);
	}

	@Override
	public void getI0(Float128Member v) {
		v.set(i0);
	}

	@Override
	public void getJ0(Float128Member v) {
		v.set(j0);
	}

	@Override
	public void getK0(Float128Member v) {
		v.set(k0);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + r.hashCode();
		v = Hasher.PRIME * v + i.hashCode();
		v = Hasher.PRIME * v + j.hashCode();
		v = Hasher.PRIME * v + k.hashCode();
		v = Hasher.PRIME * v + l.hashCode();
		v = Hasher.PRIME * v + i0.hashCode();
		v = Hasher.PRIME * v + j0.hashCode();
		v = Hasher.PRIME * v + k0.hashCode();
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OctonionFloat128Member) {
			return G.OQUAD.isEqual().call(this, (OctonionFloat128Member) o);
		}
		return false;
	}

	@Override
	public void setFromLongs(long... vals) {
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(BigDecimal.valueOf(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(BigDecimal.valueOf(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(BigDecimal.valueOf(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(BigDecimal.valueOf(vals[7]));
	}

	@Override
	public void setFromDoubles(double... vals) {
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(BigDecimal.valueOf(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(BigDecimal.valueOf(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(BigDecimal.valueOf(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(BigDecimal.valueOf(vals[7]));
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(new BigDecimal(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(new BigDecimal(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(new BigDecimal(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(new BigDecimal(vals[7]));
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(vals[4]);
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(vals[5]);
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(vals[6]);
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(vals[7]);
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {r().getAsBigDecimal(), i().getAsBigDecimal(), j().getAsBigDecimal(), k().getAsBigDecimal(), l().getAsBigDecimal(), i0().getAsBigDecimal(), j0().getAsBigDecimal(), k0().getAsBigDecimal()};
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] { r().getAsBigInteger(), i().getAsBigInteger(), j().getAsBigInteger(), k().getAsBigInteger(), l().getAsBigInteger(), i0().getAsBigInteger(), j0().getAsBigInteger(), k0().getAsBigInteger() };
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] { r().getAsDouble(), i().getAsDouble(), j().getAsDouble(), k().getAsDouble(), l().getAsDouble(), i0().getAsDouble(), j0().getAsDouble(), k0().getAsDouble() };
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] { r().getAsFloat(), i().getAsFloat(), j().getAsFloat(), k().getAsFloat(), l().getAsFloat(), i0().getAsFloat(), j0().getAsFloat(), k0().getAsFloat() };
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] { r().getAsLong(), i().getAsLong(), j().getAsLong(), k().getAsLong(), l().getAsLong(), i0().getAsLong(), j0().getAsLong(), k0().getAsLong() };
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] { r().getAsInt(), i().getAsInt(), j().getAsInt(), k().getAsInt(), l().getAsInt(), i0().getAsInt(), j0().getAsInt(), k0().getAsInt() };
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] { r().getAsShort(), i().getAsShort(), j().getAsShort(), k().getAsShort(), l().getAsShort(), i0().getAsShort(), j0().getAsShort(), k0().getAsShort() };
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] { r().getAsByte(), i().getAsByte(), j().getAsByte(), k().getAsByte(), l().getAsByte(), i0().getAsByte(), j0().getAsByte(), k0().getAsByte() };
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
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(BigDecimal.valueOf(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(BigDecimal.valueOf(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(BigDecimal.valueOf(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(BigDecimal.valueOf(vals[7]));
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
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(BigDecimal.valueOf(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(BigDecimal.valueOf(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(BigDecimal.valueOf(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(BigDecimal.valueOf(vals[7]));
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(BigDecimal.valueOf(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(BigDecimal.valueOf(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(BigDecimal.valueOf(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(BigDecimal.valueOf(vals[7]));
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		
		if (vals.length == 0 || vals.length > 8)
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
		
		if (vals.length < 5)
			setL(BigDecimal.ZERO);
		else
			setL(BigDecimal.valueOf(vals[4]));
		
		if (vals.length < 6)
			setI0(BigDecimal.ZERO);
		else
			setI0(BigDecimal.valueOf(vals[5]));
		
		if (vals.length < 7)
			setJ0(BigDecimal.ZERO);
		else
			setJ0(BigDecimal.valueOf(vals[6]));
		
		if (vals.length < 8)
			setK0(BigDecimal.ZERO);
		else
			setK0(BigDecimal.valueOf(vals[7]));
	}
	
	@Override
	public OctonionFloat128Algebra getAlgebra() {

		return G.OQUAD;
	}
}
