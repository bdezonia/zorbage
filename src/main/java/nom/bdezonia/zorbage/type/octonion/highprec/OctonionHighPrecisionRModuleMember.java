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
package nom.bdezonia.zorbage.type.octonion.highprec;

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
public final class OctonionHighPrecisionRModuleMember
	implements
		RModuleMember<OctonionHighPrecisionMember>,
		Gettable<OctonionHighPrecisionRModuleMember>,
		Settable<OctonionHighPrecisionRModuleMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionHighPrecisionMember>,
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
		GetAlgebra<OctonionHighPrecisionRModule, OctonionHighPrecisionRModuleMember>
{
	private static final OctonionHighPrecisionMember ZERO = new OctonionHighPrecisionMember(); 

	private IndexedDataSource<OctonionHighPrecisionMember> storage;
	private StorageConstruction s;
	
	public OctonionHighPrecisionRModuleMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionHighPrecisionMember(), 0);
	}
	
	public OctonionHighPrecisionRModuleMember(BigDecimal... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionHighPrecisionMember(), count);
		setFromBigDecimals(vals);
	}
	
	public OctonionHighPrecisionRModuleMember(BigInteger... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionHighPrecisionMember(), count);
		setFromBigIntegers(vals);
	}
	
	public OctonionHighPrecisionRModuleMember(double... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionHighPrecisionMember(), count);
		setFromDoubles(vals);
	}
	
	public OctonionHighPrecisionRModuleMember(long... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionHighPrecisionMember(), count);
		setFromLongs(vals);
	}
	
	public OctonionHighPrecisionRModuleMember(OctonionHighPrecisionRModuleMember other) {
		set(other);
	}
	
	public OctonionHighPrecisionRModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionHighPrecisionMember(), data.size());
		OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r());
			tmp.setI(val.i());
			tmp.setJ(val.j());
			tmp.setK(val.k());
			tmp.setL(val.l());
			tmp.setI0(val.i0());
			tmp.setJ0(val.j0());
			tmp.setK0(val.k0());
			storage.set(i, tmp);
		}
	}

	public OctonionHighPrecisionRModuleMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}

	public OctonionHighPrecisionRModuleMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, OctonionHighPrecisionMember v) {
		storage.get(i, v);
	}

	@Override
	public void setV(long i, OctonionHighPrecisionMember v) {
		storage.set(i, v);
	}
	
	@Override
	public void set(OctonionHighPrecisionRModuleMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(OctonionHighPrecisionRModuleMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		OctonionHighPrecisionMember value = new OctonionHighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal r = value.r();
			BigDecimal im = value.i();
			BigDecimal j = value.j();
			BigDecimal k = value.k();
			BigDecimal l = value.l();
			BigDecimal i0 = value.i0();
			BigDecimal j0 = value.j0();
			BigDecimal k0 = value.k0();
			OctonionRepresentation o = values.get(i);
			o.setR(r);
			o.setI(im);
			o.setJ(j);
			o.setK(k);
			o.setL(l);
			o.setI0(i0);
			o.setJ0(j0);
			o.setK0(k0);
		}
		rep.setRModule(length(), values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionHighPrecisionMember value = new OctonionHighPrecisionMember();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setR(o.r());
			value.setI(o.i());
			value.setJ(o.j());
			value.setK(o.k());
			value.setL(o.l());
			value.setI0(o.i0());
			value.setJ0(o.j0());
			value.setK0(o.k0());
			storage.set(i, value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() { 
		OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
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
			storage = Storage.allocate(s, new OctonionHighPrecisionMember(), size);
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
		RModuleReshape.compute(G.OHP_RMOD, G.OHP, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}

	private static final ThreadLocal<OctonionHighPrecisionMember> tmpOct =
			new ThreadLocal<OctonionHighPrecisionMember>()
	{
		protected OctonionHighPrecisionMember initialValue() {
			return new OctonionHighPrecisionMember();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(BigDecimal.valueOf(v));
				else // component == 5
					tmp.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					tmp.setK0(BigDecimal.valueOf(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(BigDecimal.valueOf(v));
				else // component == 5
					tmp.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					tmp.setK0(BigDecimal.valueOf(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(BigDecimal.valueOf(v));
				else // component == 5
					tmp.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					tmp.setK0(BigDecimal.valueOf(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(BigDecimal.valueOf(v));
				else // component == 5
					tmp.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					tmp.setK0(BigDecimal.valueOf(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(BigDecimal.valueOf(v));
				else // component == 5
					tmp.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					tmp.setK0(BigDecimal.valueOf(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(BigDecimal.valueOf(v));
				else // component == 5
					tmp.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					tmp.setK0(BigDecimal.valueOf(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(new BigDecimal(v));
				else // component == 5
					tmp.setI0(new BigDecimal(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(new BigDecimal(v));
				else // component == 7
					tmp.setK0(new BigDecimal(v));
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		OctonionHighPrecisionMember tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(v);
				else // component == 5
					tmp.setI0(v);
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(v);
				else // component == 7
					tmp.setK0(v);
			}
		}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(BigDecimal.valueOf(v));
					else // component == 5
						tmp.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						tmp.setK0(BigDecimal.valueOf(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(BigDecimal.valueOf(v));
					else // component == 5
						tmp.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						tmp.setK0(BigDecimal.valueOf(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(BigDecimal.valueOf(v));
					else // component == 5
						tmp.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						tmp.setK0(BigDecimal.valueOf(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(BigDecimal.valueOf(v));
					else // component == 5
						tmp.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						tmp.setK0(BigDecimal.valueOf(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(BigDecimal.valueOf(v));
					else // component == 5
						tmp.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						tmp.setK0(BigDecimal.valueOf(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(BigDecimal.valueOf(v));
					else // component == 5
						tmp.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						tmp.setK0(BigDecimal.valueOf(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(new BigDecimal(v));
					else // component == 5
						tmp.setI0(new BigDecimal(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(new BigDecimal(v));
					else // component == 7
						tmp.setK0(new BigDecimal(v));
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(v);
					else // component == 5
						tmp.setI0(v);
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(v);
					else // component == 7
						tmp.setK0(v);
				}
			}
			setV(i, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().byteValue();
				else // component == 5
					return tmp.i0().byteValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().byteValue();
				else // component == 7
					return tmp.k0().byteValue();
			}
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().shortValue();
				else // component == 5
					return tmp.i0().shortValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().shortValue();
				else // component == 7
					return tmp.k0().shortValue();
			}
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().intValue();
				else // component == 5
					return tmp.i0().intValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().intValue();
				else // component == 7
					return tmp.k0().intValue();
			}
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().longValue();
				else // component == 5
					return tmp.i0().longValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().longValue();
				else // component == 7
					return tmp.k0().longValue();
			}
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().floatValue();
				else // component == 5
					return tmp.i0().floatValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().floatValue();
				else // component == 7
					return tmp.k0().floatValue();
			}
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().doubleValue();
				else // component == 5
					return tmp.i0().doubleValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().doubleValue();
				else // component == 7
					return tmp.k0().doubleValue();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigInteger.ZERO;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().toBigInteger();
				else // component == 5
					return tmp.i0().toBigInteger();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().toBigInteger();
				else // component == 7
					return tmp.k0().toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigDecimal.ZERO;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
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
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l();
				else // component == 5
					return tmp.i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0();
				else // component == 7
					return tmp.k0();
			}
		}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().byteValue();
					else // component == 5
						return tmp.i0().byteValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().byteValue();
					else // component == 7
						return tmp.k0().byteValue();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().shortValue();
					else // component == 5
						return tmp.i0().shortValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().shortValue();
					else // component == 7
						return tmp.k0().shortValue();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().intValue();
					else // component == 5
						return tmp.i0().intValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().intValue();
					else // component == 7
						return tmp.k0().intValue();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().longValue();
					else // component == 5
						return tmp.i0().longValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().longValue();
					else // component == 7
						return tmp.k0().longValue();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().floatValue();
					else // component == 5
						return tmp.i0().floatValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().floatValue();
					else // component == 7
						return tmp.k0().floatValue();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().doubleValue();
					else // component == 5
						return tmp.i0().doubleValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().doubleValue();
					else // component == 7
						return tmp.k0().doubleValue();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().toBigInteger();
					else // component == 5
						return tmp.i0().toBigInteger();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().toBigInteger();
					else // component == 7
						return tmp.k0().toBigInteger();
				}
			}
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
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
			else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l();
					else // component == 5
						return tmp.i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0();
					else // component == 7
						return tmp.k0();
				}
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
	public IndexedDataSource<OctonionHighPrecisionMember> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		OctonionHighPrecisionMember tmp = G.OHP.construct();
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
		if (o instanceof OctonionHighPrecisionRModuleMember) {
			return G.OHP_RMOD.isEqual().call(this, (OctonionHighPrecisionRModuleMember) o);
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
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			value.setL(  BigDecimal.valueOf(vals[i + 4]) );
			value.setI0( BigDecimal.valueOf(vals[i + 5]) );
			value.setJ0( BigDecimal.valueOf(vals[i + 6]) );
			value.setK0( BigDecimal.valueOf(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			value.setL(  BigDecimal.valueOf(vals[i + 4]) );
			value.setI0( BigDecimal.valueOf(vals[i + 5]) );
			value.setJ0( BigDecimal.valueOf(vals[i + 6]) );
			value.setK0( BigDecimal.valueOf(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			value.setL(  BigDecimal.valueOf(vals[i + 4]) );
			value.setI0( BigDecimal.valueOf(vals[i + 5]) );
			value.setJ0( BigDecimal.valueOf(vals[i + 6]) );
			value.setK0( BigDecimal.valueOf(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			value.setL(  BigDecimal.valueOf(vals[i + 4]) );
			value.setI0( BigDecimal.valueOf(vals[i + 5]) );
			value.setJ0( BigDecimal.valueOf(vals[i + 6]) );
			value.setK0( BigDecimal.valueOf(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			value.setL(  BigDecimal.valueOf(vals[i + 4]) );
			value.setI0( BigDecimal.valueOf(vals[i + 5]) );
			value.setJ0( BigDecimal.valueOf(vals[i + 6]) );
			value.setK0( BigDecimal.valueOf(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
			value.setL(  BigDecimal.valueOf(vals[i + 4]) );
			value.setI0( BigDecimal.valueOf(vals[i + 5]) );
			value.setJ0( BigDecimal.valueOf(vals[i + 6]) );
			value.setK0( BigDecimal.valueOf(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  new BigDecimal(vals[i + 0]) );
			value.setI(  new BigDecimal(vals[i + 1]) );
			value.setJ(  new BigDecimal(vals[i + 2]) );
			value.setK(  new BigDecimal(vals[i + 3]) );
			value.setL(  new BigDecimal(vals[i + 4]) );
			value.setI0( new BigDecimal(vals[i + 5]) );
			value.setJ0( new BigDecimal(vals[i + 6]) );
			value.setK0( new BigDecimal(vals[i + 7]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionHighPrecisionMember value = G.OHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0( vals[i + 5] );
			value.setJ0( vals[i + 6] );
			value.setK0( vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}
	
	@Override
	public byte[] getAsByteArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		byte[] values = new byte[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().byteValue();
			values[k++] = value.i().byteValue();
			values[k++] = value.j().byteValue();
			values[k++] = value.k().byteValue();
			values[k++] = value.l().byteValue();
			values[k++] = value.i0().byteValue();
			values[k++] = value.j0().byteValue();
			values[k++] = value.k0().byteValue();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		short[] values = new short[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().shortValue();
			values[k++] = value.i().shortValue();
			values[k++] = value.j().shortValue();
			values[k++] = value.k().shortValue();
			values[k++] = value.l().shortValue();
			values[k++] = value.i0().shortValue();
			values[k++] = value.j0().shortValue();
			values[k++] = value.k0().shortValue();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		int[] values = new int[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().intValue();
			values[k++] = value.i().intValue();
			values[k++] = value.j().intValue();
			values[k++] = value.k().intValue();
			values[k++] = value.l().intValue();
			values[k++] = value.i0().intValue();
			values[k++] = value.j0().intValue();
			values[k++] = value.k0().intValue();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		long[] values = new long[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().longValue();
			values[k++] = value.i().longValue();
			values[k++] = value.j().longValue();
			values[k++] = value.k().longValue();
			values[k++] = value.l().longValue();
			values[k++] = value.i0().longValue();
			values[k++] = value.j0().longValue();
			values[k++] = value.k0().longValue();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		float[] values = new float[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().floatValue();
			values[k++] = value.i().floatValue();
			values[k++] = value.j().floatValue();
			values[k++] = value.k().floatValue();
			values[k++] = value.l().floatValue();
			values[k++] = value.i0().floatValue();
			values[k++] = value.j0().floatValue();
			values[k++] = value.k0().floatValue();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		double[] values = new double[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().doubleValue();
			values[k++] = value.i().doubleValue();
			values[k++] = value.j().doubleValue();
			values[k++] = value.k().doubleValue();
			values[k++] = value.l().doubleValue();
			values[k++] = value.i0().doubleValue();
			values[k++] = value.j0().doubleValue();
			values[k++] = value.k0().doubleValue();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		BigInteger[] values = new BigInteger[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().toBigInteger();
			values[k++] = value.i().toBigInteger();
			values[k++] = value.j().toBigInteger();
			values[k++] = value.k().toBigInteger();
			values[k++] = value.l().toBigInteger();
			values[k++] = value.i0().toBigInteger();
			values[k++] = value.j0().toBigInteger();
			values[k++] = value.k0().toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionHighPrecisionMember value = G.OHP.construct();
		BigDecimal[] values = new BigDecimal[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r();
			values[k++] = value.i();
			values[k++] = value.j();
			values[k++] = value.k();
			values[k++] = value.l();
			values[k++] = value.i0();
			values[k++] = value.j0();
			values[k++] = value.k0();
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public OctonionHighPrecisionRModule getAlgebra() {

		return G.OHP_RMOD;
	}
}
