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
package nom.bdezonia.zorbage.type.complex.float128;

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
public final class ComplexFloat128VectorMember
	implements
		RModuleMember<ComplexFloat128Member>,
		Gettable<ComplexFloat128VectorMember>,
		Settable<ComplexFloat128VectorMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<ComplexFloat128Member>,
		SetFromBigDecimal, SetFromBigInteger, SetFromDouble, SetFromLong,
		GetAsBigDecimalArray
{
	private static final ComplexFloat128Member ZERO = new ComplexFloat128Member(); 

	private IndexedDataSource<ComplexFloat128Member> storage;
	private StorageConstruction s;
	
	public ComplexFloat128VectorMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new ComplexFloat128Member(), 0);
	}
	
	public ComplexFloat128VectorMember(BigDecimal... vals) {
		final int count = vals.length / 2;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new ComplexFloat128Member(), count);
		setFromBigDecimal(vals);
	}
	
	public ComplexFloat128VectorMember(BigInteger... vals) {
		final int count = vals.length / 2;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new ComplexFloat128Member(), count);
		setFromBigInteger(vals);
	}
	
	public ComplexFloat128VectorMember(double... vals) {
		final int count = vals.length / 2;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new ComplexFloat128Member(), count);
		setFromDouble(vals);
	}
	
	public ComplexFloat128VectorMember(long... vals) {
		final int count = vals.length / 2;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new ComplexFloat128Member(), count);
		setFromLong(vals);
	}
	
	public ComplexFloat128VectorMember(ComplexFloat128VectorMember other) {
		set(other);
	}
	
	public ComplexFloat128VectorMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new ComplexFloat128Member(), data.size());
		ComplexFloat128Member tmp = new ComplexFloat128Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r());
			tmp.setI(val.i());
			storage.set(i, tmp);
		}
	}

	public ComplexFloat128VectorMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}

	public ComplexFloat128VectorMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, ComplexFloat128Member v) {
		storage.get(i, v);
	}

	@Override
	public void setV(long i, ComplexFloat128Member v) {
		storage.set(i, v);
	}

	@Override
	public void set(ComplexFloat128VectorMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(ComplexFloat128VectorMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		ComplexFloat128Member value = G.CQUAD.construct();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal real = value.r().v();
			BigDecimal imag = value.i().v();
			OctonionRepresentation o = values.get(i);
			o.setR(real);
			o.setI(imag);
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
		ComplexFloat128Member value = G.CQUAD.construct();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setR(o.r());
			value.setI(o.i());
			storage.set(i, value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		ComplexFloat128Member tmp = new ComplexFloat128Member();
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
			storage = Storage.allocate(s, new ComplexFloat128Member(), size);
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
		RModuleReshape.compute(G.CQUAD_VEC, G.CQUAD, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
	
	private static final ThreadLocal<ComplexFloat128Member> tmpComp =
			new ThreadLocal<ComplexFloat128Member>()
	{
		protected ComplexFloat128Member initialValue() {
			return new ComplexFloat128Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(new BigDecimal(v));
		else
			tmp.setI(new BigDecimal(v));
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long i = index.get(0);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(i, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(new BigDecimal(v));
			else
				tmp.setI(new BigDecimal(v));
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(i, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().byteValue();
		return tmp.i().v().byteValue();
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().shortValue();
		return tmp.i().v().shortValue();
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().intValue();
		return tmp.i().v().intValue();
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().longValue();
		return tmp.i().v().longValue();
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().floatValue();
		return tmp.i().v().floatValue();
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().doubleValue();
		return tmp.i().v().doubleValue();
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return BigInteger.ZERO;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v().toBigInteger();
		return tmp.i().v().toBigInteger();
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return BigDecimal.ZERO;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(0), tmp);
		if (component == 0) return tmp.r().v();
		return tmp.i().v();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v().byteValue();
			return tmp.i().v().byteValue();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return (short) tmp.r().v().shortValue();
			return (short) tmp.i().v().shortValue();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v().intValue();
			return tmp.i().v().intValue();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v().longValue();
			return tmp.i().v().longValue();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v().floatValue();
			return tmp.i().v().floatValue();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v().doubleValue();
			return tmp.i().v().doubleValue();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v().toBigInteger();
			return tmp.i().v().toBigInteger();
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
			ComplexFloat128Member tmp = tmpComp.get();
			getV(index.get(0), tmp);
			if (component == 0) return tmp.r().v();
			return tmp.i().v();
		}
	}

	@Override
	public void primitiveInit() {
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<ComplexFloat128Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		ComplexFloat128Member tmp = G.CQUAD.construct();
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
		if (o instanceof ComplexFloat128VectorMember) {
			return G.CQUAD_VEC.isEqual().call(this, (ComplexFloat128VectorMember) o);
		}
		return false;
	}

	@Override
	public void setFromLong(long... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDouble(double... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigInteger(BigInteger... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  new BigDecimal(vals[i + 0]) );
			value.setI(  new BigDecimal(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimal(BigDecimal... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		BigDecimal[] values = new BigDecimal[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v();
			values[k++] = value.i().v();
		}
		return values;
	}
}
