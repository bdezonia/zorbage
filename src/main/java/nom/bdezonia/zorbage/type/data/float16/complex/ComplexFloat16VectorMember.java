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
package nom.bdezonia.zorbage.type.data.float16.complex;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.RModuleReshape;
import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.RawData;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class ComplexFloat16VectorMember
	implements
		RModuleMember<ComplexFloat16Member>,
		Gettable<ComplexFloat16VectorMember>,
		Settable<ComplexFloat16VectorMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<ComplexFloat16Member>
{
	private static final ComplexFloat16Member ZERO = new ComplexFloat16Member(0,0); 

	private IndexedDataSource<ComplexFloat16Member> storage;
	private StorageConstruction s;
	
	public ComplexFloat16VectorMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, 0, new ComplexFloat16Member());
	}
	
	public ComplexFloat16VectorMember(float[] vals) {
		final int count = vals.length / 2;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, count, new ComplexFloat16Member());
		ComplexFloat16Member value = new ComplexFloat16Member();
		for (int i = 0; i < count; i++) {
			final int index = 2*i;
			value.setR(vals[index]);
			value.setI(vals[index+1]);
			storage.set(i,  value);
		}
	}
	
	public ComplexFloat16VectorMember(ComplexFloat16VectorMember other) {
		set(other);
	}
	
	public ComplexFloat16VectorMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, data.size(), new ComplexFloat16Member());
		ComplexFloat16Member tmp = new ComplexFloat16Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().floatValue());
			tmp.setI(val.i().floatValue());
			storage.set(i, tmp);
		}
	}

	public ComplexFloat16VectorMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void v(long i, ComplexFloat16Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			G.CHLF.zero().call(v);
		}
	}

	@Override
	public void setV(long i, ComplexFloat16Member v) {
		storage.set(i, v);
	}

	@Override
	public void set(ComplexFloat16VectorMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(ComplexFloat16VectorMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		ComplexFloat16Member value = new ComplexFloat16Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal real = BigDecimal.valueOf(value.r());
			BigDecimal imag = BigDecimal.valueOf(value.i());
			OctonionRepresentation o = new OctonionRepresentation(real,imag);
			values.set(i, o);
		}
		rep.setRModule(length(), values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		ComplexFloat16Member value = new ComplexFloat16Member();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setR(o.r().floatValue());
			value.setI(o.i().floatValue());
			storage.set(i,value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		ComplexFloat16Member tmp = new ComplexFloat16Member();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
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
			storage = Storage.allocate(s, size, new ComplexFloat16Member());
			return true;
		}
		return false;
	}
	
	@Override
	public void init(long size) {
		if (!alloc(size)) {
			for (long i = 0; i < size; i++) {
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
		RModuleReshape.compute(G.CHLF_VEC, G.CHLF, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
	
	private static ThreadLocal<ComplexFloat16Member> tmpComp =
			new ThreadLocal<ComplexFloat16Member>()
	{
		protected ComplexFloat16Member initialValue() {
			return new ComplexFloat16Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR((float)v);
		else
			tmp.setI((float)v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v.floatValue());
		else
			tmp.setI(v.floatValue());
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long i = index.get(0);
		ComplexFloat16Member tmp = tmpComp.get();
		v(i, tmp);
		if (component == 0)
			tmp.setR(v.floatValue());
		else
			tmp.setI(v.floatValue());
		setV(i, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR((float)v);
			else
				tmp.setI((float)v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v.floatValue());
			else
				tmp.setI(v.floatValue());
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(i, tmp);
			if (component == 0)
				tmp.setR(v.floatValue());
			else
				tmp.setI(v.floatValue());
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return (byte) tmp.r();
		return (byte) tmp.i();
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return (short) tmp.r();
		return (short) tmp.i();
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return (int) tmp.r();
		return (int) tmp.i();
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return (long) tmp.r();
		return (long) tmp.i();
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return (float) tmp.r();
		return (float) tmp.i();
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return tmp.r();
		return tmp.i();
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return BigInteger.ZERO;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return BigDecimal.valueOf(tmp.r()).toBigInteger();
		return BigDecimal.valueOf(tmp.i()).toBigInteger();
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return BigDecimal.ZERO;
		ComplexFloat16Member tmp = tmpComp.get();
		v(index.get(0), tmp);
		if (component == 0) return BigDecimal.valueOf(tmp.r());
		return BigDecimal.valueOf(tmp.i());
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return (byte) tmp.r();
			return (byte) tmp.i();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return (short) tmp.r();
			return (short) tmp.i();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return (int) tmp.r();
			return (int) tmp.i();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return (long) tmp.r();
			return (long) tmp.i();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return (float) tmp.r();
			return (float) tmp.i();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return tmp.r();
			return tmp.i();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return BigDecimal.valueOf(tmp.r()).toBigInteger();
			return BigDecimal.valueOf(tmp.i()).toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 1;
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
			ComplexFloat16Member tmp = tmpComp.get();
			v(index.get(0), tmp);
			if (component == 0) return BigDecimal.valueOf(tmp.r());
			return BigDecimal.valueOf(tmp.i());
		}
	}

	@Override
	public void primitiveInit() {
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<ComplexFloat16Member> rawData() {
		return storage;
	}
}
