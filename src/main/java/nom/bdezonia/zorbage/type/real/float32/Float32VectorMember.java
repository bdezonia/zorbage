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
package nom.bdezonia.zorbage.type.real.float32;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.RModuleReshape;
import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float32VectorMember
	implements
		RModuleMember<Float32Member>,
		Gettable<Float32VectorMember>,
		Settable<Float32VectorMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<Float32Member>,
		SetFromFloat, GetAsFloatArray
{
	private static final Float32Member ZERO = new Float32Member(0); 

	private IndexedDataSource<Float32Member> storage;
	private StorageConstruction s;
	
	public Float32VectorMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float32Member(), 0);
	}
	
	public Float32VectorMember(float... vals) {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float32Member(), vals.length);
		setFromFloat(vals);
	}
	
	public Float32VectorMember(Float32VectorMember other) {
		set(other);
	}
	
	public Float32VectorMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float32Member(), data.size());
		Float32Member tmp = new Float32Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r().floatValue());
			storage.set(i, tmp);
		}
	}

	public Float32VectorMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}

	public Float32VectorMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, Float32Member v) {
		storage.get(i, v);
	}

	@Override
	public void setV(long i, Float32Member v) {
		storage.set(i, v);
	}
	
	
	@Override
	public void set(Float32VectorMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(Float32VectorMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		Float32Member value = new Float32Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal r = BigDecimal.valueOf(value.v());
			OctonionRepresentation o = values.get(i);
			o.setR(r);
			o.setI(BigDecimal.ZERO);
			o.setJ(BigDecimal.ZERO);
			o.setK(BigDecimal.ZERO);
			o.setL(BigDecimal.ZERO);
			o.setI0(BigDecimal.ZERO);
			o.setJ0(BigDecimal.ZERO);
			o.setK0(BigDecimal.ZERO);
		}
		rep.setRModule(length(), values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		Float32Member value = new Float32Member();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setV(o.r().floatValue());
			storage.set(i, value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		Float32Member tmp = new Float32Member();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			if (i != 0)
				builder.append(',');
			getV(i, tmp);
			builder.append(tmp.toString());
		}
		builder.append(']');
		return builder.toString();
	}

	public boolean alloc(long size) {
		if (storage == null || storage.size() != size) {
			storage = Storage.allocate(s, new Float32Member(), size);
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
		RModuleReshape.compute(G.FLT_VEC, G.FLT, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
	
	private static final ThreadLocal<Float32Member> tmpFloat =
			new ThreadLocal<Float32Member>()
	{
		protected Float32Member initialValue() {
			return new Float32Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV((float)v);
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v.floatValue());
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long i = index.get(0);
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v.floatValue());
		setV(i, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV((float)v);
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v.floatValue());
			setV(i, tmp);
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v.floatValue());
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (byte) tmp.v();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (short) tmp.v();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (int) tmp.v();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (long) tmp.v();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return tmp.v();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return tmp.v();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return BigDecimal.valueOf(tmp.v()).toBigInteger();
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return BigDecimal.valueOf(tmp.v());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (byte) tmp.v();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (short) tmp.v();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (int) tmp.v();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return (long) tmp.v();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return tmp.v();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return tmp.v();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return BigDecimal.valueOf(tmp.v()).toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
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
			Float32Member tmp = tmpFloat.get();
			getV(index.get(0), tmp);
			return BigDecimal.valueOf(tmp.v());
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<Float32Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		Float32Member tmp = G.FLT.construct();
		long len = length();
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(len);
		if (len > 0) {
			storage.get(0, tmp);
			v = Hasher.PRIME * v + tmp.hashCode();
		}
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Float32VectorMember) {
			return G.FLT_VEC.isEqual().call(this, (Float32VectorMember) o);
		}
		return false;
	}

	@Override
	public void setFromFloat(float... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		Float32Member value = G.FLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float32Member value = G.FLT.construct();
		float[] values = new float[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = value.v();
		}
		return values;
	}
}
