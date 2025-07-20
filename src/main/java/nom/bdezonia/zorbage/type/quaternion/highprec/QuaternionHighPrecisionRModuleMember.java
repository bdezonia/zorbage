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
package nom.bdezonia.zorbage.type.quaternion.highprec;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.RModuleType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
public final class QuaternionHighPrecisionRModuleMember
	implements
		RModuleMember<QuaternionHighPrecisionMember>,
		Gettable<QuaternionHighPrecisionRModuleMember>,
		Settable<QuaternionHighPrecisionRModuleMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<QuaternionHighPrecisionMember>,
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
		SetFromBigIntegersExact,
		SetFromBigDecimals,
		SetFromBigDecimalsExact,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		ThreadAccess,
		GetAlgebra<QuaternionHighPrecisionRModule, QuaternionHighPrecisionRModuleMember>,
		CompositeType,
		ExactType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	private static final QuaternionHighPrecisionMember ZERO = new QuaternionHighPrecisionMember();

	private IndexedDataSource<QuaternionHighPrecisionMember> storage;
	private StorageConstruction s;
	
	public QuaternionHighPrecisionRModuleMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), 0);
	}
	
	public QuaternionHighPrecisionRModuleMember(BigDecimal... vals) {
		final int count = vals.length / 4;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), count);
		setFromBigDecimals(vals);
	}
	
	public QuaternionHighPrecisionRModuleMember(BigInteger... vals) {
		final int count = vals.length / 4;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), count);
		setFromBigIntegers(vals);
	}
	
	public QuaternionHighPrecisionRModuleMember(double... vals) {
		final int count = vals.length / 4;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), count);
		setFromDoubles(vals);
	}
	
	public QuaternionHighPrecisionRModuleMember(long... vals) {
		final int count = vals.length / 4;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), count);
		setFromLongs(vals);
	}
	
	public QuaternionHighPrecisionRModuleMember(QuaternionHighPrecisionRModuleMember other) {
		set(other);
	}
	
	public QuaternionHighPrecisionRModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), data.size());
		QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r());
			tmp.setI(val.i());
			tmp.setJ(val.j());
			tmp.setK(val.k());
			storage.set(i, tmp);
		}
	}

	public QuaternionHighPrecisionRModuleMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}
	
	public QuaternionHighPrecisionRModuleMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, QuaternionHighPrecisionMember v) {
		storage.get(i, v);
	}

	@Override
	public void setV(long i, QuaternionHighPrecisionMember v) {
		storage.set(i, v);
	}
	
	@Override
	public void set(QuaternionHighPrecisionRModuleMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(QuaternionHighPrecisionRModuleMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal r = value.r();
			BigDecimal im = value.i();
			BigDecimal j = value.j();
			BigDecimal k = value.k();
			OctonionRepresentation o = values.get(i);
			o.setR(r);
			o.setI(im);
			o.setJ(j);
			o.setK(k);
			o.setL(BigDecimal.ZERO);
			o.setI0(BigDecimal.ZERO);
			o.setJ0(BigDecimal.ZERO);
			o.setK0(BigDecimal.ZERO);
		}
		rep.setRModule(length(), values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setR(o.r());
			value.setI(o.i());
			value.setJ(o.j());
			value.setK(o.k());
			storage.set(i, value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
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
			storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), size);
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
		RModuleReshape.compute(G.QHP_RMOD, G.QHP, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
	
	private static final ThreadLocal<QuaternionHighPrecisionMember> tmpQuat =
			new ThreadLocal<QuaternionHighPrecisionMember>()
	{
		protected QuaternionHighPrecisionMember initialValue() {
			return new QuaternionHighPrecisionMember();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else // component == 1
				tmp.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else // component == 3
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else // component == 1
				tmp.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else // component == 3
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else // component == 1
				tmp.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else // component == 3
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else // component == 1
				tmp.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else // component == 3
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else // component == 1
				tmp.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else // component == 3
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else // component == 1
				tmp.setI(BigDecimal.valueOf(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else // component == 3
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(new BigDecimal(v));
			else // component == 1
				tmp.setI(new BigDecimal(v));
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(new BigDecimal(v));
			else // component == 3
				tmp.setK(new BigDecimal(v));
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(v);
			else // component == 1
				tmp.setI(v);
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(v);
			else // component == 3
				tmp.setK(v);
		}
		setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else // component == 1
					tmp.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else // component == 3
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else // component == 1
					tmp.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else // component == 3
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else // component == 1
					tmp.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else // component == 3
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else // component == 1
					tmp.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else // component == 3
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else // component == 1
					tmp.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else // component == 3
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else // component == 1
					tmp.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else // component == 3
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(new BigDecimal(v));
				else // component == 1
					tmp.setI(new BigDecimal(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(new BigDecimal(v));
				else // component == 3
					tmp.setK(new BigDecimal(v));
			}
			setV(i, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(v);
				else // component == 1
					tmp.setI(v);
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(v);
				else // component == 3
					tmp.setK(v);
			}
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().byteValue();
			else // component == 1
				return tmp.i().byteValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().byteValue();
			else // component == 3
				return tmp.k().byteValue();
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().shortValue();
			else // component == 1
				return tmp.i().shortValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().shortValue();
			else // component == 3
				return tmp.k().shortValue();
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().intValue();
			else // component == 1
				return tmp.i().intValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().intValue();
			else // component == 3
				return tmp.k().intValue();
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().longValue();
			else // component == 1
				return tmp.i().longValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().longValue();
			else // component == 3
				return tmp.k().longValue();
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().floatValue();
			else // component == 1
				return tmp.i().floatValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().floatValue();
			else // component == 3
				return tmp.k().floatValue();
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().doubleValue();
			else // component == 1
				return tmp.i().doubleValue();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().doubleValue();
			else // component == 3
				return tmp.k().doubleValue();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return BigInteger.ZERO;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r().toBigInteger();
			else // component == 1
				return tmp.i().toBigInteger();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j().toBigInteger();
			else // component == 3
				return tmp.k().toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return BigDecimal.ZERO;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index.get(0), tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				return tmp.r();
			else // component == 1
				return tmp.i();
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				return tmp.j();
			else // component == 3
				return tmp.k();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().byteValue();
				else // component == 1
					return tmp.i().byteValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().byteValue();
				else // component == 3
					return tmp.k().byteValue();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().shortValue();
				else // component == 1
					return tmp.i().shortValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().shortValue();
				else // component == 3
					return tmp.k().shortValue();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().intValue();
				else // component == 1
					return tmp.i().intValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().intValue();
				else // component == 3
					return tmp.k().intValue();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().longValue();
				else // component == 1
					return tmp.i().longValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().longValue();
				else // component == 3
					return tmp.k().longValue();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().floatValue();
				else // component == 1
					return tmp.i().floatValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().floatValue();
				else // component == 3
					return tmp.k().floatValue();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().doubleValue();
				else // component == 1
					return tmp.i().doubleValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().doubleValue();
				else // component == 3
					return tmp.k().doubleValue();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().toBigInteger();
				else // component == 1
					return tmp.i().toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().toBigInteger();
				else // component == 3
					return tmp.k().toBigInteger();
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index.get(0), tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r();
				else // component == 1
					return tmp.i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j();
				else // component == 3
					return tmp.k();
			}
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<QuaternionHighPrecisionMember> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		QuaternionHighPrecisionMember tmp = G.QHP.construct();
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
		if (o instanceof QuaternionHighPrecisionRModuleMember) {
			return G.QHP_RMOD.isEqual().call(this, (QuaternionHighPrecisionRModuleMember) o);
		}
		return false;
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}
	
	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}
	
	@Override
	public void setFromIntsExact(int... vals) {
		setFromInts(vals);
	}
	
	@Override
	public void setFromLongsExact(long... vals) {
		setFromLongs(vals);
	}
	
	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}
	
	@Override
	public void setFromDoublesExact(double... vals) {
		setFromDoubles(vals);
	}
	
	@Override
	public void setFromBigIntegersExact(BigInteger... vals) {
		setFromBigIntegers(vals);
	}
	
	@Override
	public void setFromBigDecimalsExact(BigDecimal... vals) {
		setFromBigDecimals(vals);
	}
	
	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  new BigDecimal(vals[i + 0]) );
			value.setI(  new BigDecimal(vals[i + 1]) );
			value.setJ(  new BigDecimal(vals[i + 2]) );
			value.setK(  new BigDecimal(vals[i + 3]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}
	
	@Override
	public byte[] getAsByteArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		byte[] values = new byte[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().byteValue();
			values[k++] = value.i().byteValue();
			values[k++] = value.j().byteValue();
			values[k++] = value.k().byteValue();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		short[] values = new short[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().shortValue();
			values[k++] = value.i().shortValue();
			values[k++] = value.j().shortValue();
			values[k++] = value.k().shortValue();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		int[] values = new int[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().intValue();
			values[k++] = value.i().intValue();
			values[k++] = value.j().intValue();
			values[k++] = value.k().intValue();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		long[] values = new long[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().longValue();
			values[k++] = value.i().longValue();
			values[k++] = value.j().longValue();
			values[k++] = value.k().longValue();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		float[] values = new float[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().floatValue();
			values[k++] = value.i().floatValue();
			values[k++] = value.j().floatValue();
			values[k++] = value.k().floatValue();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		double[] values = new double[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().doubleValue();
			values[k++] = value.i().doubleValue();
			values[k++] = value.j().doubleValue();
			values[k++] = value.k().doubleValue();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		BigInteger[] values = new BigInteger[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().toBigInteger();
			values[k++] = value.i().toBigInteger();
			values[k++] = value.j().toBigInteger();
			values[k++] = value.k().toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		BigDecimal[] values = new BigDecimal[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r();
			values[k++] = value.i();
			values[k++] = value.j();
			values[k++] = value.k();
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public QuaternionHighPrecisionRModule getAlgebra() {

		return G.QHP_RMOD;
	}
}
