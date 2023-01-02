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
package nom.bdezonia.zorbage.type.quaternion.float64;

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
public final class QuaternionFloat64Member 
	implements
		NumberMember<QuaternionFloat64Member>,
		DoubleCoder,
		Allocatable<QuaternionFloat64Member>, Duplicatable<QuaternionFloat64Member>,
		Settable<QuaternionFloat64Member>, Gettable<QuaternionFloat64Member>,
		PrimitiveConversion, UniversalRepresentation,
		SetQuaternion<Float64Member>, GetQuaternion<Float64Member>,
		SetFromDouble, SetFromLong, GetAsDoubleArray
{

	private double r, i, j, k;
	
	public QuaternionFloat64Member() {
		primitiveInit();
	}
	
	public QuaternionFloat64Member(double r, double i, double j, double k) {
		setR(r);
		setI(i);
		setJ(j);
		setK(k);
	}
	
	public QuaternionFloat64Member(QuaternionFloat64Member value) {
		set(value);
	}

	public QuaternionFloat64Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().doubleValue());
		setI(val.i().doubleValue());
		setJ(val.j().doubleValue());
		setK(val.k().doubleValue());
	}

	public QuaternionFloat64Member(double... vals) {
		setFromDouble(vals);
	}

	public double r() { return r; }
	
	public double i() { return i; }
	
	public double j() { return j; }
	
	public double k() { return k; }
	
	public void setR(double val) { r = val; }
	
	public void setI(double val) { i = val; }
	
	public void setJ(double val) { j = val; }
	
	public void setK(double val) { k = val; }
	
	@Override
	public void set(QuaternionFloat64Member other) {
		//if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
	}

	@Override
	public void get(QuaternionFloat64Member other) {
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
	public int doubleCount() {
		return 4;
	}

	@Override
	public void fromDoubleArray(double[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
	}

	@Override
	public void toDoubleArray(double[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
	}

	@Override
	public QuaternionFloat64Member allocate() {
		return new QuaternionFloat64Member();
	}

	@Override
	public QuaternionFloat64Member duplicate() {
		return new QuaternionFloat64Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(QuaternionFloat64Member value) {
		get(value);
	}

	@Override
	public void setV(QuaternionFloat64Member value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				BigDecimal.valueOf(r()),
				BigDecimal.valueOf(i()),
				BigDecimal.valueOf(j()),
				BigDecimal.valueOf(k())
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
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
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
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
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
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
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
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
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
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
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
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
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
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
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
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
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
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
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
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
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
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
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
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
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
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
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
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
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
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
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
		}
	}

	@Override
	public void primitiveInit() {
		r = i = j = k = 0;
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
		if (o instanceof QuaternionFloat64Member) {
			return G.QDBL.isEqual().call(this, (QuaternionFloat64Member) o);
		}
		return false;
	}

	@Override
	public void setFromLong(long... vals) {
		
		if (vals.length == 0 || vals.length > 4)
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
	}

	@Override
	public void setFromDouble(double... vals) {
		
		if (vals.length == 0 || vals.length > 4)
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
	}

	@Override
	public double[] getAsDoubleArray() {
		return new double[] {r(), i(), j(), k()};
	}
}
