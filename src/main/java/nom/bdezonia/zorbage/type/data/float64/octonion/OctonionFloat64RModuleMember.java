/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.octonion;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.file.FileStorageFloat64;
import nom.bdezonia.zorbage.util.BigList;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class OctonionFloat64RModuleMember
	implements
		RModuleMember<OctonionFloat64Member>,
		Gettable<OctonionFloat64RModuleMember>,
		Settable<OctonionFloat64RModuleMember>,
		PrimitiveConversion, UniversalRepresentation
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member(); 

	private IndexedDataSource<?,OctonionFloat64Member> storage;
	private StorageConstruction s;
	
	public OctonionFloat64RModuleMember() {
		storage = new ArrayStorageFloat64<OctonionFloat64Member>(0, new OctonionFloat64Member());
		s = StorageConstruction.MEM_ARRAY;
	}
	
	public OctonionFloat64RModuleMember(double[] vals) {
		final int count = vals.length / 8;
		storage = new ArrayStorageFloat64<OctonionFloat64Member>(count, new OctonionFloat64Member());
		s = StorageConstruction.MEM_ARRAY;
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (int i = 0; i < count; i++) {
			final int index = 8*i;
			value.setR(vals[index]);
			value.setI(vals[index + 1]);
			value.setJ(vals[index + 2]);
			value.setK(vals[index + 3]);
			value.setL(vals[index + 4]);
			value.setI0(vals[index + 5]);
			value.setJ0(vals[index + 6]);
			value.setK0(vals[index + 7]);
			storage.set(i,  value);
		}
	}
	
	public OctonionFloat64RModuleMember(OctonionFloat64RModuleMember other) {
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	public OctonionFloat64RModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		storage = new ArrayStorageFloat64<OctonionFloat64Member>(data.size(), new OctonionFloat64Member());
		s = StorageConstruction.MEM_ARRAY;
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			tmp.setJ(val.j().doubleValue());
			tmp.setK(val.k().doubleValue());
			tmp.setL(val.l().doubleValue());
			tmp.setI0(val.i0().doubleValue());
			tmp.setJ0(val.j0().doubleValue());
			tmp.setK0(val.k0().doubleValue());
			storage.set(i, tmp);
		}
	}

	public OctonionFloat64RModuleMember(StorageConstruction s, long d1) {
		this.s = s;
		if (s == StorageConstruction.MEM_ARRAY)
			storage = new ArrayStorageFloat64<OctonionFloat64Member>(d1, new OctonionFloat64Member());
		else
			storage = new FileStorageFloat64<OctonionFloat64Member>(d1, new OctonionFloat64Member());
	}

	@Override
	public void v(long i, OctonionFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			G.ODBL.zero(v);
		}
	}

	@Override
	public void setV(long i, OctonionFloat64Member v) {
		storage.set(i, v);
	}
	
	@Override
	public void set(OctonionFloat64RModuleMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(OctonionFloat64RModuleMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void setTensorFromSelf(TensorOctonionRepresentation rep) {
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal r = BigDecimal.valueOf(value.r());
			BigDecimal im = BigDecimal.valueOf(value.i());
			BigDecimal j = BigDecimal.valueOf(value.j());
			BigDecimal k = BigDecimal.valueOf(value.k());
			BigDecimal l = BigDecimal.valueOf(value.l());
			BigDecimal i0 = BigDecimal.valueOf(value.i0());
			BigDecimal j0 = BigDecimal.valueOf(value.j0());
			BigDecimal k0 = BigDecimal.valueOf(value.k0());
			OctonionRepresentation o = new OctonionRepresentation(r,im,j,k,l,i0,j0,k0);
			values.set(i, o);
		}
		rep.setRModule(length(), values);
	}

	@Override
	public void setSelfFromTensor(TensorOctonionRepresentation rep) {
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		init(rmod.size());
		for (long i = 0; i < rmod.size(); i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setR(o.r().doubleValue());
			value.setI(o.i().doubleValue());
			value.setJ(o.j().doubleValue());
			value.setK(o.k().doubleValue());
			value.setL(o.l().doubleValue());
			value.setI0(o.i0().doubleValue());
			value.setJ0(o.j0().doubleValue());
			value.setK0(o.k0().doubleValue());
			storage.set(i,value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() { 
		OctonionFloat64Member tmp = new OctonionFloat64Member();
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

	public boolean alloc(long size) {
		if (storage == null || storage.size() != size) {
			if (s == StorageConstruction.MEM_ARRAY)
				storage = new ArrayStorageFloat64<OctonionFloat64Member>(size, new OctonionFloat64Member());
			else
				storage = new FileStorageFloat64<OctonionFloat64Member>(size, new OctonionFloat64Member());
			return true;
		}
		return false;
	}
	
	@Override
	public void init(long size) {
		if (!alloc(size)) {
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

	private static ThreadLocal<OctonionFloat64Member> tmpOct =
			new ThreadLocal<OctonionFloat64Member>()
	{
		protected OctonionFloat64Member initialValue() {
			return new OctonionFloat64Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
	}

	@Override
	public int componentCount() {
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else
			tmp.setK0(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else
			tmp.setK0(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else
			tmp.setK0(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else
			tmp.setK0(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else
			tmp.setK0(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else
			tmp.setK0(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v.doubleValue());
		else if (component == 1)
			tmp.setI(v.doubleValue());
		else if (component == 2)
			tmp.setJ(v.doubleValue());
		else if (component == 3)
			tmp.setK(v.doubleValue());
		else if (component == 4)
			tmp.setL(v.doubleValue());
		else if (component == 5)
			tmp.setI0(v.doubleValue());
		else if (component == 6)
			tmp.setJ0(v.doubleValue());
		else
			tmp.setK0(v.doubleValue());
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		v(i, tmp);
		if (component == 0)
			tmp.setR(v.doubleValue());
		else if (component == 1)
			tmp.setI(v.doubleValue());
		else if (component == 2)
			tmp.setJ(v.doubleValue());
		else if (component == 3)
			tmp.setK(v.doubleValue());
		else if (component == 4)
			tmp.setL(v.doubleValue());
		else if (component == 5)
			tmp.setI0(v.doubleValue());
		else if (component == 6)
			tmp.setJ0(v.doubleValue());
		else
			tmp.setK0(v.doubleValue());
		setV(i, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else
				tmp.setK0(v);
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else
				tmp.setK0(v);
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else
				tmp.setK0(v);
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else
				tmp.setK0(v);
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else
				tmp.setK0(v);
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else
				tmp.setK0(v);
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v.doubleValue());
			else if (component == 1)
				tmp.setI(v.doubleValue());
			else if (component == 2)
				tmp.setJ(v.doubleValue());
			else if (component == 3)
				tmp.setK(v.doubleValue());
			else if (component == 4)
				tmp.setL(v.doubleValue());
			else if (component == 5)
				tmp.setI0(v.doubleValue());
			else if (component == 6)
				tmp.setJ0(v.doubleValue());
			else
				tmp.setK0(v.doubleValue());
			setV(i, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			v(i, tmp);
			if (component == 0)
				tmp.setR(v.doubleValue());
			else if (component == 1)
				tmp.setI(v.doubleValue());
			else if (component == 2)
				tmp.setJ(v.doubleValue());
			else if (component == 3)
				tmp.setK(v.doubleValue());
			else if (component == 4)
				tmp.setL(v.doubleValue());
			else if (component == 5)
				tmp.setI0(v.doubleValue());
			else if (component == 6)
				tmp.setJ0(v.doubleValue());
			else
				tmp.setK0(v.doubleValue());
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return (byte) tmp.r();
		if (component == 1) return (byte) tmp.i();
		if (component == 2) return (byte) tmp.j();
		if (component == 3) return (byte) tmp.k();
		if (component == 4) return (byte) tmp.l();
		if (component == 5) return (byte) tmp.i0();
		if (component == 6) return (byte) tmp.j0();
		return (byte) tmp.k0();
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return (short) tmp.r();
		if (component == 1) return (short) tmp.i();
		if (component == 2) return (short) tmp.j();
		if (component == 3) return (short) tmp.k();
		if (component == 4) return (short) tmp.l();
		if (component == 5) return (short) tmp.i0();
		if (component == 6) return (short) tmp.j0();
		return (short) tmp.k0();
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return (int) tmp.r();
		if (component == 1) return (int) tmp.i();
		if (component == 2) return (int) tmp.j();
		if (component == 3) return (int) tmp.k();
		if (component == 4) return (int) tmp.l();
		if (component == 5) return (int) tmp.i0();
		if (component == 6) return (int) tmp.j0();
		return (int) tmp.k0();
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return (long) tmp.r();
		if (component == 1) return (long) tmp.i();
		if (component == 2) return (long) tmp.j();
		if (component == 3) return (long) tmp.k();
		if (component == 4) return (long) tmp.l();
		if (component == 5) return (long) tmp.i0();
		if (component == 6) return (long) tmp.j0();
		return (long) tmp.k0();
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return (float) tmp.r();
		if (component == 1) return (float) tmp.i();
		if (component == 2) return (float) tmp.j();
		if (component == 3) return (float) tmp.k();
		if (component == 4) return (float) tmp.l();
		if (component == 5) return (float) tmp.i0();
		if (component == 6) return (float) tmp.j0();
		return (float) tmp.k0();
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return tmp.r();
		if (component == 1) return tmp.i();
		if (component == 2) return tmp.j();
		if (component == 3) return tmp.k();
		if (component == 4) return tmp.l();
		if (component == 5) return tmp.i0();
		if (component == 6) return tmp.j0();
		return tmp.k0();
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigInteger.ZERO;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return BigInteger.valueOf((long) tmp.r());
		if (component == 1) return BigInteger.valueOf((long) tmp.i());
		if (component == 2) return BigInteger.valueOf((long) tmp.j());
		if (component == 3) return BigInteger.valueOf((long) tmp.k());
		if (component == 4) return BigInteger.valueOf((long) tmp.l());
		if (component == 5) return BigInteger.valueOf((long) tmp.i0());
		if (component == 6) return BigInteger.valueOf((long) tmp.j0());
		return BigInteger.valueOf((long) tmp.k0());
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigDecimal.ZERO;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(0), tmp);
		if (component == 0) return BigDecimal.valueOf(tmp.r());
		if (component == 1) return BigDecimal.valueOf(tmp.i());
		if (component == 2) return BigDecimal.valueOf(tmp.j());
		if (component == 3) return BigDecimal.valueOf(tmp.k());
		if (component == 4) return BigDecimal.valueOf(tmp.l());
		if (component == 5) return BigDecimal.valueOf(tmp.i0());
		if (component == 6) return BigDecimal.valueOf(tmp.j0());
		return BigDecimal.valueOf(tmp.k0());
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return (byte) tmp.r();
			if (component == 1) return (byte) tmp.i();
			if (component == 2) return (byte) tmp.j();
			if (component == 3) return (byte) tmp.k();
			if (component == 4) return (byte) tmp.l();
			if (component == 5) return (byte) tmp.i0();
			if (component == 6) return (byte) tmp.j0();
			return (byte) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return (short) tmp.r();
			if (component == 1) return (short) tmp.i();
			if (component == 2) return (short) tmp.j();
			if (component == 3) return (short) tmp.k();
			if (component == 4) return (short) tmp.l();
			if (component == 5) return (short) tmp.i0();
			if (component == 6) return (short) tmp.j0();
			return (short) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return (int) tmp.r();
			if (component == 1) return (int) tmp.i();
			if (component == 2) return (int) tmp.j();
			if (component == 3) return (int) tmp.k();
			if (component == 4) return (int) tmp.l();
			if (component == 5) return (int) tmp.i0();
			if (component == 6) return (int) tmp.j0();
			return (int) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return (long) tmp.r();
			if (component == 1) return (long) tmp.i();
			if (component == 2) return (long) tmp.j();
			if (component == 3) return (long) tmp.k();
			if (component == 4) return (long) tmp.l();
			if (component == 5) return (long) tmp.i0();
			if (component == 6) return (long) tmp.j0();
			return (long) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return (float) tmp.r();
			if (component == 1) return (float) tmp.i();
			if (component == 2) return (float) tmp.j();
			if (component == 3) return (float) tmp.k();
			if (component == 4) return (float) tmp.l();
			if (component == 5) return (float) tmp.i0();
			if (component == 6) return (float) tmp.j0();
			return (float) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return tmp.r();
			if (component == 1) return tmp.i();
			if (component == 2) return tmp.j();
			if (component == 3) return tmp.k();
			if (component == 4) return tmp.l();
			if (component == 5) return tmp.i0();
			if (component == 6) return tmp.j0();
			return tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return BigInteger.valueOf((long) tmp.r());
			if (component == 1) return BigInteger.valueOf((long) tmp.i());
			if (component == 2) return BigInteger.valueOf((long) tmp.j());
			if (component == 3) return BigInteger.valueOf((long) tmp.k());
			if (component == 4) return BigInteger.valueOf((long) tmp.l());
			if (component == 5) return BigInteger.valueOf((long) tmp.i0());
			if (component == 6) return BigInteger.valueOf((long) tmp.j0());
			return BigInteger.valueOf((long) tmp.k0());
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(0), tmp);
			if (component == 0) return BigDecimal.valueOf(tmp.r());
			if (component == 1) return BigDecimal.valueOf(tmp.i());
			if (component == 2) return BigDecimal.valueOf(tmp.j());
			if (component == 3) return BigDecimal.valueOf(tmp.k());
			if (component == 4) return BigDecimal.valueOf(tmp.l());
			if (component == 5) return BigDecimal.valueOf(tmp.i0());
			if (component == 6) return BigDecimal.valueOf(tmp.j0());
			return BigDecimal.valueOf(tmp.k0());
		}
	}

	@Override
	public void primitiveInit() {
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, ZERO);
	}
}
