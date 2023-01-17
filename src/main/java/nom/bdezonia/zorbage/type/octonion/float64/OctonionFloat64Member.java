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
package nom.bdezonia.zorbage.type.octonion.float64;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
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
public final class OctonionFloat64Member
	implements
		NumberMember<OctonionFloat64Member>,
		DoubleCoder,
		Allocatable<OctonionFloat64Member>, Duplicatable<OctonionFloat64Member>,
		Settable<OctonionFloat64Member>, Gettable<OctonionFloat64Member>,
		PrimitiveConversion,
		UniversalRepresentation, SetOctonion<Float64Member>, GetOctonion<Float64Member>,
		NativeDoubleSupport,
		SetFromByte,
		SetFromByteExact,
		SetFromShort,
		SetFromShortExact,
		SetFromInt,
		SetFromIntExact,
		SetFromLong,
		SetFromFloat,
		SetFromFloatExact,
		SetFromDouble,
		SetFromDoubleExact,
		SetFromBigInteger,
		SetFromBigDecimal,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact
{
	private double r, i, j, k, l, i0, j0, k0;
	
	public OctonionFloat64Member() {
		primitiveInit();
	}
	
	public OctonionFloat64Member(OctonionFloat64Member value) {
		set(value);
	}

	public OctonionFloat64Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().doubleValue());
		setI(val.i().doubleValue());
		setJ(val.j().doubleValue());
		setK(val.k().doubleValue());
		setL(val.l().doubleValue());
		setI0(val.i0().doubleValue());
		setJ0(val.j0().doubleValue());
		setK0(val.k0().doubleValue());
	}

	public OctonionFloat64Member(double... vals) {
		setFromDouble(vals);
	}
	
	public double r() { return r; }
	
	public double i() { return i; }
	
	public double j() { return j; }
	
	public double k() { return k; }
	
	public double l() { return l; }
	
	public double i0() { return i0; }
	
	public double j0() { return j0; }
	
	public double k0() { return k0; }
	
	public void setR(double val) { r = val; }
	
	public void setI(double val) { i = val; }
	
	public void setJ(double val) { j = val; }
	
	public void setK(double val) { k = val; }
	
	public void setL(double val) { l = val; }
	
	public void setI0(double val) { i0 = val; }
	
	public void setJ0(double val) { j0 = val; }
	
	public void setK0(double val) { k0 = val; }
	
	@Override
	public void set(OctonionFloat64Member other) {
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
	public void get(OctonionFloat64Member other) {
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
	public int doubleCount() {
		return 8;
	}

	@Override
	public void fromDoubleArray(double[] arr, int index) {
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
	public void toDoubleArray(double[] arr, int index) {
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
	public OctonionFloat64Member allocate() {
		return new OctonionFloat64Member();
	}

	@Override
	public OctonionFloat64Member duplicate() {
		return new OctonionFloat64Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(OctonionFloat64Member value) {
		get(value);
	}

	@Override
	public void setV(OctonionFloat64Member value) {
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
		setR(v.r().doubleValue());
		setI(v.i().doubleValue());
		setJ(v.j().doubleValue());
		setK(v.k().doubleValue());
		setL(v.l().doubleValue());
		setI0(v.i0().doubleValue());
		setJ0(v.j0().doubleValue());
		setK0(v.k0().doubleValue());
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
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
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(v.doubleValue());
				else // component == 1
					this.setI(v.doubleValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(v.doubleValue());
				else // component == 3
					this.setK(v.doubleValue());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(v.doubleValue());
				else // component == 5
					this.setI0(v.doubleValue());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(v.doubleValue());
				else // component == 7
					this.setK0(v.doubleValue());
			}
		}
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(v.doubleValue());
				else // component == 1
					this.setI(v.doubleValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(v.doubleValue());
				else // component == 3
					this.setK(v.doubleValue());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(v.doubleValue());
				else // component == 5
					this.setI0(v.doubleValue());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(v.doubleValue());
				else // component == 7
					this.setK0(v.doubleValue());
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
						this.setR(v.doubleValue());
					else // component == 1
						this.setI(v.doubleValue());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(v.doubleValue());
					else // component == 3
						this.setK(v.doubleValue());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(v.doubleValue());
					else // component == 5
						this.setI0(v.doubleValue());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(v.doubleValue());
					else // component == 7
						this.setK0(v.doubleValue());
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
						this.setR(v.doubleValue());
					else // component == 1
						this.setI(v.doubleValue());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(v.doubleValue());
					else // component == 3
						this.setK(v.doubleValue());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(v.doubleValue());
					else // component == 5
						this.setI0(v.doubleValue());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(v.doubleValue());
					else // component == 7
						this.setK0(v.doubleValue());
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
					return (float) r();
				else // component == 1
					return (float) i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (float) j();
				else // component == 3
					return (float) k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (float) l();
				else // component == 5
					return (float) i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (float) j0();
				else // component == 7
					return (float) k0();
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
						return (float) r();
					else // component == 1
						return (float) i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (float) j();
					else // component == 3
						return (float) k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (float) l();
					else // component == 5
						return (float) i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (float) j0();
					else // component == 7
						return (float) k0();
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
	public void setR(Float64Member val) {
		setR(val.v());
	}

	@Override
	public void setI(Float64Member val) {
		setI(val.v());
	}

	@Override
	public void setJ(Float64Member val) {
		setJ(val.v());
	}

	@Override
	public void setK(Float64Member val) {
		setK(val.v());
	}

	@Override
	public void setL(Float64Member val) {
		setL(val.v());
	}

	@Override
	public void setI0(Float64Member val) {
		setI0(val.v());
	}

	@Override
	public void setJ0(Float64Member val) {
		setJ0(val.v());
	}

	@Override
	public void setK0(Float64Member val) {
		setK0(val.v());
	}

	@Override
	public void getR(Float64Member v) {
		v.setV(r);
	}

	@Override
	public void getI(Float64Member v) {
		v.setV(i);
	}

	@Override
	public void getJ(Float64Member v) {
		v.setV(j);
	}

	@Override
	public void getK(Float64Member v) {
		v.setV(k);
	}

	@Override
	public void getL(Float64Member v) {
		v.setV(l);
	}

	@Override
	public void getI0(Float64Member v) {
		v.setV(i0);
	}

	@Override
	public void getJ0(Float64Member v) {
		v.setV(j0);
	}

	@Override
	public void getK0(Float64Member v) {
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
		if (o instanceof OctonionFloat64Member) {
			return G.ODBL.isEqual().call(this, (OctonionFloat64Member) o);
		}
		return false;
	}

	@Override
	public void setFromLong(long... vals) {
		
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
	public void setFromDouble(double... vals) {
		
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
	public double[] getAsDoubleArray() {
		return new double[] {r(), i(), j(), k(), l(), i0(), j0(), k0()};
	}

	@Override
	public double getNative(int component) {

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
	public void setNative(int component, double val) {

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
	public Double componentMin() {

		return -Double.MAX_VALUE;
	}

	@Override
	public Double componentMax() {

		return Double.MAX_VALUE;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] { BigDecimal.valueOf(r()), BigDecimal.valueOf(i()), BigDecimal.valueOf(j()), BigDecimal.valueOf(k()), BigDecimal.valueOf(l()), BigDecimal.valueOf(i0()), BigDecimal.valueOf(j0()), BigDecimal.valueOf(k0()) };
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		return new BigInteger[] { BigDecimal.valueOf(r()).toBigInteger(), BigDecimal.valueOf(i()).toBigInteger(), BigDecimal.valueOf(j()).toBigInteger(), BigDecimal.valueOf(k()).toBigInteger(), BigDecimal.valueOf(l()).toBigInteger(), BigDecimal.valueOf(i0()).toBigInteger(), BigDecimal.valueOf(j0()).toBigInteger(), BigDecimal.valueOf(k0()).toBigInteger() };
	}

	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
	}

	@Override
	public float[] getAsFloatArray() {
		return new float[] { (float) r(), (float) i(), (float) j(), (float) k(), (float) l(), (float) i0(), (float) j0(), (float) k0() };
	}

	@Override
	public long[] getAsLongArray() {
		return new long[] { (long) r(), (long) i(), (long) j(), (long) k(), (long) l(), (long) i0(), (long) j0(), (long) k0() };
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] { (int) r(), (int) i(), (int) j(), (int) k(), (int) l(), (int) i0(), (int) j0(), (int) k0() };
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] { (short) r(), (short) i(), (short) j(), (short) k(), (short) l(), (short) i0(), (short) j0(), (short) k0() };
	}

	@Override
	public byte[] getAsByteArray() {
		return new byte[] { (byte) r(), (byte) i(), (byte) j(), (byte) k(), (byte) l(), (byte) i0(), (byte) j0(), (byte) k0() };
	}

	@Override
	public void setFromBigDecimal(BigDecimal... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0].doubleValue());
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1].doubleValue());
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2].doubleValue());
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3].doubleValue());
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4].doubleValue());
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5].doubleValue());
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6].doubleValue());
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7].doubleValue());
	}

	@Override
	public void setFromBigInteger(BigInteger... vals) {
		
		if (vals.length == 0 || vals.length > 8)
			throw new IllegalArgumentException("mismatch between component count and input values count");

		setR(vals[0].doubleValue());
		
		if (vals.length < 2)
			setI(0);
		else
			setI(vals[1].doubleValue());
		
		if (vals.length < 3)
			setJ(0);
		else
			setJ(vals[2].doubleValue());
		
		if (vals.length < 4)
			setK(0);
		else
			setK(vals[3].doubleValue());
		
		if (vals.length < 5)
			setL(0);
		else
			setL(vals[4].doubleValue());
		
		if (vals.length < 6)
			setI0(0);
		else
			setI0(vals[5].doubleValue());
		
		if (vals.length < 7)
			setJ0(0);
		else
			setJ0(vals[6].doubleValue());
		
		if (vals.length < 8)
			setK0(0);
		else
			setK0(vals[7].doubleValue());
	}

	@Override
	public void setFromDoubleExact(double... vals) {
		setFromDouble(vals);
	}

	@Override
	public void setFromFloatExact(float... vals) {
		setFromFloat(vals);
	}

	@Override
	public void setFromFloat(float... vals) {
		
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
	public void setFromIntExact(int... vals) {
		setFromInt(vals);
	}

	@Override
	public void setFromInt(int... vals) {
		
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
	public void setFromShortExact(short... vals) {
		setFromShort(vals);
	}

	@Override
	public void setFromShort(short... vals) {
		
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
	public void setFromByteExact(byte... vals) {
		setFromByte(vals);
	}

	@Override
	public void setFromByte(byte... vals) {
		
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
}
