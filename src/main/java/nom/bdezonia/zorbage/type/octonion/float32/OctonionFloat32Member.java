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
package nom.bdezonia.zorbage.type.octonion.float32;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
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
public final class OctonionFloat32Member
	implements
		NumberMember<OctonionFloat32Member>,
		FloatCoder,
		Allocatable<OctonionFloat32Member>, Duplicatable<OctonionFloat32Member>,
		Settable<OctonionFloat32Member>, Gettable<OctonionFloat32Member>,
		PrimitiveConversion,
		UniversalRepresentation, SetOctonion<Float32Member>, GetOctonion<Float32Member>,
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
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsFloatArrayExact,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<OctonionFloat32Algebra, OctonionFloat32Member>,
		FixedSize
{
	private float r, i, j, k, l, i0, j0, k0;
	
	public OctonionFloat32Member() {
		primitiveInit();
	}
	
	public OctonionFloat32Member(OctonionFloat32Member value) {
		set(value);
	}

	public OctonionFloat32Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().floatValue());
		setI(val.i().floatValue());
		setJ(val.j().floatValue());
		setK(val.k().floatValue());
		setL(val.l().floatValue());
		setI0(val.i0().floatValue());
		setJ0(val.j0().floatValue());
		setK0(val.k0().floatValue());
	}
	
	public OctonionFloat32Member(float... vals) {
		setFromFloats(vals);
	}

	public float r() { return r; }
	
	public float i() { return i; }
	
	public float j() { return j; }
	
	public float k() { return k; }
	
	public float l() { return l; }
	
	public float i0() { return i0; }
	
	public float j0() { return j0; }
	
	public float k0() { return k0; }
	
	public void setR(float val) { r = val; }
	
	public void setI(float val) { i = val; }
	
	public void setJ(float val) { j = val; }
	
	public void setK(float val) { k = val; }
	
	public void setL(float val) { l = val; }
	
	public void setI0(float val) { i0 = val; }
	
	public void setJ0(float val) { j0 = val; }
	
	public void setK0(float val) { k0 = val; }
	
	@Override
	public void set(OctonionFloat32Member other) {
		//if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
		l = other.l;
		i0 = other.i0;
		j0 = other.j0;
		k0 = other.k0;
	}

	@Override
	public void get(OctonionFloat32Member other) {
		//if (this == other) return;
		other.r = r;
		other.i = i;
		other.j = j;
		other.k = k;
		other.l = l;
		other.i0 = i0;
		other.j0 = j0;
		other.k0 = k0;
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
	public int floatCount() {
		return 8;
	}

	@Override
	public void fromFloatArray(float[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
		l = arr[index+4];
		i0 = arr[index+5];
		j0 = arr[index+6];
		k0 = arr[index+7];
	}

	@Override
	public void toFloatArray(float[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
		arr[index+4] = l;
		arr[index+5] = i0;
		arr[index+6] = j0;
		arr[index+7] = k0;
	}

	@Override
	public OctonionFloat32Member allocate() {
		return new OctonionFloat32Member();
	}

	@Override
	public OctonionFloat32Member duplicate() {
		return new OctonionFloat32Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(OctonionFloat32Member value) {
		get(value);
	}

	@Override
	public void setV(OctonionFloat32Member value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				BigDecimal.valueOf(r()),
				BigDecimal.valueOf(i()),
				BigDecimal.valueOf(j()),
				BigDecimal.valueOf(k()),
				BigDecimal.valueOf(l()),
				BigDecimal.valueOf(i0()),
				BigDecimal.valueOf(j0()),
				BigDecimal.valueOf(k0())
			)
		);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionRepresentation v = rep.getValue();
		setR(v.r().floatValue());
		setI(v.i().floatValue());
		setJ(v.j().floatValue());
		setK(v.k().floatValue());
		setL(v.l().floatValue());
		setI0(v.i0().floatValue());
		setJ0(v.j0().floatValue());
		setK0(v.k0().floatValue());
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
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
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
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
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
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
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
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
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
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
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
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR((float)v);
				else // component == 1
					this.setI((float)v);
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ((float)v);
				else // component == 3
					this.setK((float)v);
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL((float)v);
				else // component == 5
					this.setI0((float)v);
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0((float)v);
				else // component == 7
					this.setK0((float)v);
			}
		}
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(v.floatValue());
				else // component == 1
					this.setI(v.floatValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(v.floatValue());
				else // component == 3
					this.setK(v.floatValue());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(v.floatValue());
				else // component == 5
					this.setI0(v.floatValue());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(v.floatValue());
				else // component == 7
					this.setK0(v.floatValue());
			}
		}
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(v.floatValue());
				else // component == 1
					this.setI(v.floatValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(v.floatValue());
				else // component == 3
					this.setK(v.floatValue());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(v.floatValue());
				else // component == 5
					this.setI0(v.floatValue());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(v.floatValue());
				else // component == 7
					this.setK0(v.floatValue());
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
						this.setR((float)v);
					else // component == 1
						this.setI((float)v);
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ((float)v);
					else // component == 3
						this.setK((float)v);
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL((float)v);
					else // component == 5
						this.setI0((float)v);
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0((float)v);
					else // component == 7
						this.setK0((float)v);
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
						this.setR(v.floatValue());
					else // component == 1
						this.setI(v.floatValue());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(v.floatValue());
					else // component == 3
						this.setK(v.floatValue());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(v.floatValue());
					else // component == 5
						this.setI0(v.floatValue());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(v.floatValue());
					else // component == 7
						this.setK0(v.floatValue());
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
						this.setR(v.floatValue());
					else // component == 1
						this.setI(v.floatValue());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(v.floatValue());
					else // component == 3
						this.setK(v.floatValue());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(v.floatValue());
					else // component == 5
						this.setI0(v.floatValue());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(v.floatValue());
					else // component == 7
						this.setK0(v.floatValue());
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
					return (byte) r();
				else // component == 1
					return (byte) i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (byte) j();
				else // component == 3
					return (byte) k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (byte) l();
				else // component == 5
					return (byte) i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (byte) j0();
				else // component == 7
					return (byte) k0();
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
					return (short) r();
				else // component == 1
					return (short) i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (short) j();
				else // component == 3
					return (short) k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (short) l();
				else // component == 5
					return (short) i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (short) j0();
				else // component == 7
					return (short) k0();
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
					return (int) r();
				else // component == 1
					return (int) i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (int) j();
				else // component == 3
					return (int) k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (int) l();
				else // component == 5
					return (int) i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (int) j0();
				else // component == 7
					return (int) k0();
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
					return (long) r();
				else // component == 1
					return (long) i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (long) j();
				else // component == 3
					return (long) k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (long) l();
				else // component == 5
					return (long) i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (long) j0();
				else // component == 7
					return (long) k0();
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l();
				else // component == 5
					return i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0();
				else // component == 7
					return k0();
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l();
				else // component == 5
					return i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0();
				else // component == 7
					return k0();
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
					return BigDecimal.valueOf(r()).toBigInteger();
				else // component == 1
					return BigDecimal.valueOf(i()).toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return BigDecimal.valueOf(j()).toBigInteger();
				else // component == 3
					return BigDecimal.valueOf(k()).toBigInteger();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return BigDecimal.valueOf(l()).toBigInteger();
				else // component == 5
					return BigDecimal.valueOf(i0()).toBigInteger();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return BigDecimal.valueOf(j0()).toBigInteger();
				else // component == 7
					return BigDecimal.valueOf(k0()).toBigInteger();
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
					return BigDecimal.valueOf(r());
				else // component == 1
					return BigDecimal.valueOf(i());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return BigDecimal.valueOf(j());
				else // component == 3
					return BigDecimal.valueOf(k());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return BigDecimal.valueOf(l());
				else // component == 5
					return BigDecimal.valueOf(i0());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return BigDecimal.valueOf(j0());
				else // component == 7
					return BigDecimal.valueOf(k0());
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
						return (byte) r();
					else // component == 1
						return (byte) i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (byte) j();
					else // component == 3
						return (byte) k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (byte) l();
					else // component == 5
						return (byte) i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (byte) j0();
					else // component == 7
						return (byte) k0();
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
						return (short) r();
					else // component == 1
						return (short) i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (short) j();
					else // component == 3
						return (short) k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (short) l();
					else // component == 5
						return (short) i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (short) j0();
					else // component == 7
						return (short) k0();
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
						return (int) r();
					else // component == 1
						return (int) i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (int) j();
					else // component == 3
						return (int) k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (int) l();
					else // component == 5
						return (int) i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (int) j0();
					else // component == 7
						return (int) k0();
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
						return (long) r();
					else // component == 1
						return (long) i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (long) j();
					else // component == 3
						return (long) k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (long) l();
					else // component == 5
						return (long) i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (long) j0();
					else // component == 7
						return (long) k0();
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l();
					else // component == 5
						return i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0();
					else // component == 7
						return k0();
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l();
					else // component == 5
						return i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0();
					else // component == 7
						return k0();
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
						return BigDecimal.valueOf(r()).toBigInteger();
					else // component == 1
						return BigDecimal.valueOf(i()).toBigInteger();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return BigDecimal.valueOf(j()).toBigInteger();
					else // component == 3
						return BigDecimal.valueOf(k()).toBigInteger();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return BigDecimal.valueOf(l()).toBigInteger();
					else // component == 5
						return BigDecimal.valueOf(i0()).toBigInteger();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return BigDecimal.valueOf(j0()).toBigInteger();
					else // component == 7
						return BigDecimal.valueOf(k0()).toBigInteger();
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
						return BigDecimal.valueOf(r());
					else // component == 1
						return BigDecimal.valueOf(i());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return BigDecimal.valueOf(j());
					else // component == 3
						return BigDecimal.valueOf(k());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return BigDecimal.valueOf(l());
					else // component == 5
						return BigDecimal.valueOf(i0());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return BigDecimal.valueOf(j0());
					else // component == 7
						return BigDecimal.valueOf(k0());
				}
			}
		}
	}

	@Override
	public void primitiveInit() {
		r = i = j = k = l = i0 = j0 = k0 = 0;
	}

	@Override
	public void setR(Float32Member val) {
		setR(val.v());
	}

	@Override
	public void setI(Float32Member val) {
		setI(val.v());
	}

	@Override
	public void setJ(Float32Member val) {
		setJ(val.v());
	}

	@Override
	public void setK(Float32Member val) {
		setK(val.v());
	}

	@Override
	public void setL(Float32Member val) {
		setL(val.v());
	}

	@Override
	public void setI0(Float32Member val) {
		setI0(val.v());
	}

	@Override
	public void setJ0(Float32Member val) {
		setJ0(val.v());
	}

	@Override
	public void setK0(Float32Member val) {
		setK0(val.v());
	}

	@Override
	public void getR(Float32Member v) {
		v.setV(r);
	}

	@Override
	public void getI(Float32Member v) {
		v.setV(i);
	}

	@Override
	public void getJ(Float32Member v) {
		v.setV(j);
	}

	@Override
	public void getK(Float32Member v) {
		v.setV(k);
	}

	@Override
	public void getL(Float32Member v) {
		v.setV(l);
	}

	@Override
	public void getI0(Float32Member v) {
		v.setV(i0);
	}

	@Override
	public void getJ0(Float32Member v) {
		v.setV(j0);
	}

	@Override
	public void getK0(Float32Member v) {
		v.setV(k0);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(i);
		v = Hasher.PRIME * v + Hasher.hashCode(j);
		v = Hasher.PRIME * v + Hasher.hashCode(k);
		v = Hasher.PRIME * v + Hasher.hashCode(l);
		v = Hasher.PRIME * v + Hasher.hashCode(i0);
		v = Hasher.PRIME * v + Hasher.hashCode(j0);
		v = Hasher.PRIME * v + Hasher.hashCode(k0);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OctonionFloat32Member) {
			return G.OFLT.isEqual().call(this, (OctonionFloat32Member) o);
		}
		return false;
	}

	@Override
	public void setFromLongs(long... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1]);
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2]);
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3]);
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4]);
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5]);
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6]);
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7]);
	}

	@Override
	public void setFromFloats(float... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1]);
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2]);
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3]);
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4]);
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5]);
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6]);
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7]);
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] {r(), i(), j(), k(), l(), i0(), j0(), k0()};
	}

	@Override
	public float getNative(int component) {

		if (component == 0)
			return r();
		else if (component == 1)
			return i();
		else if (component == 2)
			return j();
		else if (component == 3)
			return k();
		else if (component == 4)
			return l();
		else if (component == 5)
			return i0();
		else if (component == 6)
			return j0();
		else if (component == 7)
			return k0();
		else
			throw new IllegalArgumentException("component number out of bounds");
	}

	@Override
	public void setNative(int component, float val) {

		if (component == 0)
			setR(val);
		else if (component == 1)
			setI(val);
		else if (component == 2)
			setJ(val);
		else if (component == 3)
			setK(val);
		else if (component == 4)
			setL(val);
		else if (component == 5)
			setI0(val);
		else if (component == 6)
			setJ0(val);
		else if (component == 7)
			setK0(val);
		else
			throw new IllegalArgumentException("component number out of bounds");
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
		return new BigDecimal[] {BigDecimal.valueOf(r()), BigDecimal.valueOf(i()), BigDecimal.valueOf(j()), BigDecimal.valueOf(k()), BigDecimal.valueOf(l()), BigDecimal.valueOf(i0()), BigDecimal.valueOf(j0()), BigDecimal.valueOf(k0())};
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] {BigDecimal.valueOf(r()).toBigInteger(), BigDecimal.valueOf(i()).toBigInteger(), BigDecimal.valueOf(j()).toBigInteger(), BigDecimal.valueOf(k()).toBigInteger(), BigDecimal.valueOf(l()).toBigInteger(), BigDecimal.valueOf(i0()).toBigInteger(), BigDecimal.valueOf(j0()).toBigInteger(), BigDecimal.valueOf(k0()).toBigInteger()};
	}

	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] {r(), i(), j(), k(), l(), i0(), j0(), k0()};
	}

	@Override
	public float[] getAsFloatArrayExact() {
		return getAsFloatArray();
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] {(long) r(), (long) i(), (long) j(), (long) k(), (long) l(), (long) i0(), (long) j0(), (long) k0()};
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {(int) r(), (int) i(), (int) j(), (int) k(), (int) l(), (int) i0(), (int) j0(), (int) k0()};
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] {(short) r(), (short) i(), (short) j(), (short) k(), (short) l(), (short) i0(), (short) j0(), (short) k0()};
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] {(byte) r(), (byte) i(), (byte) j(), (byte) k(), (byte) k(), (byte) l(), (byte) i0(), (byte) j0(), (byte) k0()};
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0].floatValue());
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1].floatValue());
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2].floatValue());
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3].floatValue());
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4].floatValue());
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5].floatValue());
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6].floatValue());
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7].floatValue());
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0].floatValue());
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1].floatValue());
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2].floatValue());
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3].floatValue());
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4].floatValue());
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5].floatValue());
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6].floatValue());
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7].floatValue());
	}

	@Override
	public void setFromDoubles(double... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR((float) vals[0]);
		
		if (vals.length < 2)
			setI(0);
		else
			setI((float) vals[1]);
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ((float) vals[2]);
		
		if (vals.length < 4)
			setK(0);
		else
			setK((float) vals[3]);
		
		if (vals.length < 5)
			setL(0);
		else
			setL((float) vals[4]);
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0((float) vals[5]);
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0((float) vals[6]);
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0((float) vals[7]);
	}

	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}

	@Override
	public void setFromInts(int... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1]);
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2]);
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3]);
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4]);
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5]);
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6]);
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7]);
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1]);
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2]);
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3]);
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4]);
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5]);
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6]);
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7]);
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0]);
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1]);
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2]);
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3]);
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4]);
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5]);
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6]);
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7]);
	}
	
	@Override
	public OctonionFloat32Algebra getAlgebra() {

		return G.OFLT;
	}
}
