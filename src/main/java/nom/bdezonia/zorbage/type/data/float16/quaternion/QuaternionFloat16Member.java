/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.data.float16.quaternion;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.GetQuaternion;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.NumberMember;
import nom.bdezonia.zorbage.type.algebra.SetQuaternion;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Util;
import nom.bdezonia.zorbage.type.data.helper.Hasher;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionFloat16Member 
	implements
		NumberMember<QuaternionFloat16Member>,
		ShortCoder,
		Allocatable<QuaternionFloat16Member>, Duplicatable<QuaternionFloat16Member>,
		Settable<QuaternionFloat16Member>, Gettable<QuaternionFloat16Member>,
		PrimitiveConversion, UniversalRepresentation,
		SetQuaternion<Float16Member>, GetQuaternion<Float16Member>
{
	private static final short ZERO = Float16Util.convertFloatToHFloat(0);
	
	private short r, i, j, k;
	
	public QuaternionFloat16Member() {
		primitiveInit();
	}
	
	public QuaternionFloat16Member(float r, float i, float j, float k) {
		setR(r);
		setI(i);
		setJ(j);
		setK(k);
	}
	
	public QuaternionFloat16Member(QuaternionFloat16Member value) {
		set(value);
	}

	public QuaternionFloat16Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r().floatValue());
		setI(val.i().floatValue());
		setJ(val.j().floatValue());
		setK(val.k().floatValue());
	}

	public float r() { return Float16Util.convertHFloatToFloat(r); }
	
	public float i() { return Float16Util.convertHFloatToFloat(i); }
	
	public float j() { return Float16Util.convertHFloatToFloat(j); }
	
	public float k() { return Float16Util.convertHFloatToFloat(k); }
	
	public void setR(float val) { r = Float16Util.convertFloatToHFloat(val); }
	
	public void setI(float val) { i = Float16Util.convertFloatToHFloat(val); }
	
	public void setJ(float val) { j = Float16Util.convertFloatToHFloat(val); }
	
	public void setK(float val) { k = Float16Util.convertFloatToHFloat(val); }
	
	@Override
	public void set(QuaternionFloat16Member other) {
		//if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
	}

	@Override
	public void get(QuaternionFloat16Member other) {
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
	public int shortCount() {
		return 4;
	}

	@Override
	public void fromShortArray(short[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
	}

	@Override
	public void toShortArray(short[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
	}

	@Override
	public QuaternionFloat16Member allocate() {
		return new QuaternionFloat16Member();
	}

	@Override
	public QuaternionFloat16Member duplicate() {
		return new QuaternionFloat16Member(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void v(QuaternionFloat16Member value) {
		get(value);
	}

	@Override
	public void setV(QuaternionFloat16Member value) {
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
		setR(v.r().floatValue());
		setI(v.i().floatValue());
		setJ(v.j().floatValue());
		setK(v.k().floatValue());
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
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
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
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
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
		r = i = j = k = ZERO;
	}

	@Override
	public void setR(Float16Member val) {
		r = val.encV();
	}

	@Override
	public void setI(Float16Member val) {
		i = val.encV();
	}

	@Override
	public void setJ(Float16Member val) {
		j = val.encV();
	}

	@Override
	public void setK(Float16Member val) {
		k = val.encV();
	}

	@Override
	public void getR(Float16Member v) {
		v.setEncV(r);
	}

	@Override
	public void getI(Float16Member v) {
		v.setEncV(i);
	}

	@Override
	public void getJ(Float16Member v) {
		v.setEncV(j);
	}

	@Override
	public void getK(Float16Member v) {
		v.setEncV(k);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = 23 * v + Hasher.hashCode(r);
		v = 23 * v + Hasher.hashCode(i);
		v = 23 * v + Hasher.hashCode(j);
		v = 23 * v + Hasher.hashCode(k);
		return v;
	}
}
