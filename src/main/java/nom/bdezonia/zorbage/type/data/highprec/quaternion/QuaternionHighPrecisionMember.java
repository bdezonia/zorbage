/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.highprec.quaternion;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.NumberMember;
import nom.bdezonia.zorbage.type.algebra.SetQuaternion;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.storage.coder.BigDecimalCoder;

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
		PrimitiveConversion, UniversalRepresentation, SetQuaternion<HighPrecisionMember>
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
	public void v(QuaternionHighPrecisionMember value) {
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
}
