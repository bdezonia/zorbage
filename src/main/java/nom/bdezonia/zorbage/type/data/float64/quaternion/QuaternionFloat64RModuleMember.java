/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.file.FileStorageFloat64;
import nom.bdezonia.zorbage.util.BigList;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionFloat64RModuleMember
	implements
		RModuleMember<QuaternionFloat64Member>,
		Gettable<QuaternionFloat64RModuleMember>,
		Settable<QuaternionFloat64RModuleMember>,
		PrimitiveConversion
// TODO: UniversalRepresentation
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(); 

	private LinearStorage<?,QuaternionFloat64Member> storage;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public QuaternionFloat64RModuleMember() {
		storage = new ArrayStorageFloat64<QuaternionFloat64Member>(0, new QuaternionFloat64Member());
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
	}
	
	public QuaternionFloat64RModuleMember(double[] vals) {
		final int count = vals.length / 4;
		storage = new ArrayStorageFloat64<QuaternionFloat64Member>(count, new QuaternionFloat64Member());
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (int i = 0; i < count; i++) {
			final int index = 4*i;
			value.setR(vals[index]);
			value.setI(vals[index + 1]);
			value.setJ(vals[index + 2]);
			value.setK(vals[index + 3]);
			storage.set(i,  value);
		}
	}
	
	public QuaternionFloat64RModuleMember(QuaternionFloat64RModuleMember other) {
		storage = other.storage.duplicate();
		m = other.m;
		s = other.s;
	}
	
	public QuaternionFloat64RModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		storage = new ArrayStorageFloat64<QuaternionFloat64Member>(data.size(), new QuaternionFloat64Member());
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			tmp.setJ(val.j().doubleValue());
			tmp.setK(val.k().doubleValue());
			storage.set(i, tmp);
		}
	}

	public QuaternionFloat64RModuleMember(MemoryConstruction m, StorageConstruction s, long d1) {
		this.m = m;
		this.s = s;
		if (s == StorageConstruction.ARRAY)
			storage = new ArrayStorageFloat64<QuaternionFloat64Member>(d1, new QuaternionFloat64Member());
		else
			storage = new FileStorageFloat64<QuaternionFloat64Member>(d1, new QuaternionFloat64Member());
	}
	
	@Override
	public void v(long i, QuaternionFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			G.QDBL.zero(v);
		}
	}

	@Override
	public void setV(long i, QuaternionFloat64Member v) {
		storage.set(i, v);
	}
	
	@Override
	public void set(QuaternionFloat64RModuleMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		m = other.m;
		s = other.s;
	}
	
	@Override
	public void get(QuaternionFloat64RModuleMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.m = m;
		other.s = s;
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (long i = 0; i < storage.size(); i++) {
			if (i != 0)
				builder.append(',');
			v(i, tmp);
			builder.append(tmp.toString());
		}
		builder.append(']');
		return builder.toString();
	}

	@Override
	public void init(long size) {
		if (storage == null || storage.size() != size) {
			if (s == StorageConstruction.ARRAY)
				storage = new ArrayStorageFloat64<QuaternionFloat64Member>(size, new QuaternionFloat64Member());
			else
				storage = new FileStorageFloat64<QuaternionFloat64Member>(size, new QuaternionFloat64Member());
		}
		else {
			for (long i = 0; i < storage.size(); i++) {
				storage.set(i, ZERO);
			}
		}
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public void reshape(long len) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("implement me");
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
	
	private static ThreadLocal<QuaternionFloat64Member> tmpQuat =
			new ThreadLocal<QuaternionFloat64Member>()
	{
		protected QuaternionFloat64Member initialValue() {
			return new QuaternionFloat64Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
	}

	@Override
	public int componentCount() {
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v);
		else if (component == 1)
			tmpQuat.get().setI(v);
		else if (component == 2)
			tmpQuat.get().setJ(v);
		else
			tmpQuat.get().setK(v);
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v);
		else if (component == 1)
			tmpQuat.get().setI(v);
		else if (component == 2)
			tmpQuat.get().setJ(v);
		else
			tmpQuat.get().setK(v);
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v);
		else if (component == 1)
			tmpQuat.get().setI(v);
		else if (component == 2)
			tmpQuat.get().setJ(v);
		else
			tmpQuat.get().setK(v);
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v);
		else if (component == 1)
			tmpQuat.get().setI(v);
		else if (component == 2)
			tmpQuat.get().setJ(v);
		else
			tmpQuat.get().setK(v);
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v);
		else if (component == 1)
			tmpQuat.get().setI(v);
		else if (component == 2)
			tmpQuat.get().setJ(v);
		else
			tmpQuat.get().setK(v);
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v);
		else if (component == 1)
			tmpQuat.get().setI(v);
		else if (component == 2)
			tmpQuat.get().setJ(v);
		else
			tmpQuat.get().setK(v);
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v.doubleValue());
		else if (component == 1)
			tmpQuat.get().setI(v.doubleValue());
		else if (component == 2)
			tmpQuat.get().setJ(v.doubleValue());
		else
			tmpQuat.get().setK(v.doubleValue());
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long i = index.get(0);
		v(i, tmpQuat.get());
		if (component == 0)
			tmpQuat.get().setR(v.doubleValue());
		else if (component == 1)
			tmpQuat.get().setI(v.doubleValue());
		else if (component == 2)
			tmpQuat.get().setJ(v.doubleValue());
		else
			tmpQuat.get().setK(v.doubleValue());
		setV(i, tmpQuat.get());
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v);
			else if (component == 1)
				tmpQuat.get().setI(v);
			else if (component == 2)
				tmpQuat.get().setJ(v);
			else
				tmpQuat.get().setK(v);
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v);
			else if (component == 1)
				tmpQuat.get().setI(v);
			else if (component == 2)
				tmpQuat.get().setJ(v);
			else
				tmpQuat.get().setK(v);
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v);
			else if (component == 1)
				tmpQuat.get().setI(v);
			else if (component == 2)
				tmpQuat.get().setJ(v);
			else
				tmpQuat.get().setK(v);
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v);
			else if (component == 1)
				tmpQuat.get().setI(v);
			else if (component == 2)
				tmpQuat.get().setJ(v);
			else
				tmpQuat.get().setK(v);
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v);
			else if (component == 1)
				tmpQuat.get().setI(v);
			else if (component == 2)
				tmpQuat.get().setJ(v);
			else
				tmpQuat.get().setK(v);
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v);
			else if (component == 1)
				tmpQuat.get().setI(v);
			else if (component == 2)
				tmpQuat.get().setJ(v);
			else
				tmpQuat.get().setK(v);
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v.doubleValue());
			else if (component == 1)
				tmpQuat.get().setI(v.doubleValue());
			else if (component == 2)
				tmpQuat.get().setJ(v.doubleValue());
			else
				tmpQuat.get().setK(v.doubleValue());
			setV(i, tmpQuat.get());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
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
			long i = index.get(0);
			v(i, tmpQuat.get());
			if (component == 0)
				tmpQuat.get().setR(v.doubleValue());
			else if (component == 1)
				tmpQuat.get().setI(v.doubleValue());
			else if (component == 2)
				tmpQuat.get().setJ(v.doubleValue());
			else
				tmpQuat.get().setK(v.doubleValue());
			setV(i, tmpQuat.get());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return (byte) tmpQuat.get().r();
		if (component == 1) return (byte) tmpQuat.get().i();
		if (component == 2) return (byte) tmpQuat.get().j();
		return (byte) tmpQuat.get().k();
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return (short) tmpQuat.get().r();
		if (component == 1) return (short) tmpQuat.get().i();
		if (component == 2) return (short) tmpQuat.get().j();
		return (short) tmpQuat.get().k();
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return (int) tmpQuat.get().r();
		if (component == 1) return (int) tmpQuat.get().i();
		if (component == 2) return (int) tmpQuat.get().j();
		return (int) tmpQuat.get().k();
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return (long) tmpQuat.get().r();
		if (component == 1) return (long) tmpQuat.get().i();
		if (component == 2) return (long) tmpQuat.get().j();
		return (long) tmpQuat.get().k();
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return (float) tmpQuat.get().r();
		if (component == 1) return (float) tmpQuat.get().i();
		if (component == 2) return (float) tmpQuat.get().j();
		return (float) tmpQuat.get().k();
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return tmpQuat.get().r();
		if (component == 1) return tmpQuat.get().i();
		if (component == 2) return tmpQuat.get().j();
		return tmpQuat.get().k();
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return BigInteger.ZERO;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return BigInteger.valueOf((long) tmpQuat.get().r());
		if (component == 1) return BigInteger.valueOf((long) tmpQuat.get().i());
		if (component == 2) return BigInteger.valueOf((long) tmpQuat.get().j());
		return BigInteger.valueOf((long) tmpQuat.get().k());
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return BigDecimal.ZERO;
		v(index.get(0), tmpQuat.get());
		if (component == 0) return BigDecimal.valueOf(tmpQuat.get().r());
		if (component == 1) return BigDecimal.valueOf(tmpQuat.get().i());
		if (component == 2) return BigDecimal.valueOf(tmpQuat.get().j());
		return BigDecimal.valueOf(tmpQuat.get().k());
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return (byte) tmpQuat.get().r();
			if (component == 1) return (byte) tmpQuat.get().i();
			if (component == 2) return (byte) tmpQuat.get().j();
			return (byte) tmpQuat.get().k();
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return (short) tmpQuat.get().r();
			if (component == 1) return (short) tmpQuat.get().i();
			if (component == 2) return (short) tmpQuat.get().j();
			return (short) tmpQuat.get().k();
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return (int) tmpQuat.get().r();
			if (component == 1) return (int) tmpQuat.get().i();
			if (component == 2) return (int) tmpQuat.get().j();
			return (int) tmpQuat.get().k();
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return (long) tmpQuat.get().r();
			if (component == 1) return (long) tmpQuat.get().i();
			if (component == 2) return (long) tmpQuat.get().j();
			return (long) tmpQuat.get().k();
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return (float) tmpQuat.get().r();
			if (component == 1) return (float) tmpQuat.get().i();
			if (component == 2) return (float) tmpQuat.get().j();
			return (float) tmpQuat.get().k();
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return tmpQuat.get().r();
			if (component == 1) return tmpQuat.get().i();
			if (component == 2) return tmpQuat.get().j();
			return tmpQuat.get().k();
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigInteger.ZERO;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return BigInteger.valueOf((long) tmpQuat.get().r());
			if (component == 1) return BigInteger.valueOf((long) tmpQuat.get().i());
			if (component == 2) return BigInteger.valueOf((long) tmpQuat.get().j());
			return BigInteger.valueOf((long) tmpQuat.get().k());
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
				if (i == 0) {
					if (index.get(0) >= storage.size()) {
						oob = true;
						break;
					}
				}
				else if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigDecimal.ZERO;
		}
		else {
			v(index.get(0), tmpQuat.get());
			if (component == 0) return BigDecimal.valueOf(tmpQuat.get().r());
			if (component == 1) return BigDecimal.valueOf(tmpQuat.get().i());
			if (component == 2) return BigDecimal.valueOf(tmpQuat.get().j());
			return BigDecimal.valueOf(tmpQuat.get().k());
		}
	}

	@Override
	public void primitiveInit() {
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, ZERO);
	}
}
