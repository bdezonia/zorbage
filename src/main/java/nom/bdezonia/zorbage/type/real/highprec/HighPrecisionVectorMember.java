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
package nom.bdezonia.zorbage.type.real.highprec;

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
public final class HighPrecisionVectorMember
	implements
		RModuleMember<HighPrecisionMember>,
		Gettable<HighPrecisionVectorMember>,
		Settable<HighPrecisionVectorMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<HighPrecisionMember>,
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
		GetAsBigDecimalArray,
		ThreadAccess
{
	private static final HighPrecisionMember ZERO = new HighPrecisionMember(); 

	private IndexedDataSource<HighPrecisionMember> storage;
	private StorageConstruction s;
	
	public HighPrecisionVectorMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new HighPrecisionMember(), 0);
	}
	
	public HighPrecisionVectorMember(BigDecimal... vals) {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new HighPrecisionMember(), vals.length);
		setFromBigDecimals(vals);
	}
	
	public HighPrecisionVectorMember(BigInteger... vals) {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new HighPrecisionMember(), vals.length);
		setFromBigIntegers(vals);
	}
	
	public HighPrecisionVectorMember(double... vals) {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new HighPrecisionMember(), vals.length);
		setFromDoubles(vals);
	}
	
	public HighPrecisionVectorMember(long... vals) {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new HighPrecisionMember(), vals.length);
		setFromLongs(vals);
	}
	
	public HighPrecisionVectorMember(HighPrecisionVectorMember other) {
		set(other);
	}
	
	public HighPrecisionVectorMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new HighPrecisionMember(), data.size());
		HighPrecisionMember tmp = new HighPrecisionMember();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r());
			storage.set(i, tmp);
		}
	}

	public HighPrecisionVectorMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}

	public HighPrecisionVectorMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, HighPrecisionMember v) {
		storage.get(i, v);
	}

	@Override
	public void setV(long i, HighPrecisionMember v) {
		storage.set(i, v);
	}
	
	
	@Override
	public void set(HighPrecisionVectorMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(HighPrecisionVectorMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		HighPrecisionMember value = new HighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal r = value.v();
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
		HighPrecisionMember value = new HighPrecisionMember();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setV(o.r());
			storage.set(i, value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		HighPrecisionMember tmp = new HighPrecisionMember();
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
			storage = Storage.allocate(s, new HighPrecisionMember(), size);
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
		RModuleReshape.compute(G.HP_VEC, G.HP, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
	
	private static final ThreadLocal<HighPrecisionMember> tmpHP =
			new ThreadLocal<HighPrecisionMember>()
	{
		protected HighPrecisionMember initialValue() {
			return new HighPrecisionMember();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(new BigDecimal(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long i = index.get(0);
		HighPrecisionMember tmp = tmpHP.get();
		tmp.setV(v);
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(new BigDecimal(v));
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
			HighPrecisionMember tmp = tmpHP.get();
			tmp.setV(v);
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().byteValue();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().shortValue();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().intValue();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().longValue();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().floatValue();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().doubleValue();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().toBigInteger();
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().byteValue();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().shortValue();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().intValue();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().longValue();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().floatValue();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().doubleValue();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v().toBigInteger();
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
			HighPrecisionMember tmp = tmpHP.get();
			getV(index.get(0), tmp);
			return tmp.v();
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<HighPrecisionMember> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		HighPrecisionMember tmp = G.HP.construct();
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
		if (o instanceof HighPrecisionVectorMember) {
			return G.HP_VEC.isEqual().call(this, (HighPrecisionVectorMember) o);
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
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  BigDecimal.valueOf(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  BigDecimal.valueOf(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  BigDecimal.valueOf(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  BigDecimal.valueOf(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  BigDecimal.valueOf(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  BigDecimal.valueOf(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  new BigDecimal(vals[i + 0]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		HighPrecisionMember value = G.HP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		HighPrecisionMember value = G.HP.construct();
		BigDecimal[] values = new BigDecimal[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = value.v();
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
}
