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
package nom.bdezonia.zorbage.type.octonion.float128;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
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
public final class OctonionFloat128RModuleMember
	implements
		RModuleMember<OctonionFloat128Member>,
		Gettable<OctonionFloat128RModuleMember>,
		Settable<OctonionFloat128RModuleMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionFloat128Member>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromIntsExact,
		SetFromLongs,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
		SetFromDoublesExact,
		SetFromBigIntegers,
		SetFromBigDecimals,
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
		GetAlgebra<OctonionFloat128RModule, OctonionFloat128RModuleMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	private static final OctonionFloat128Member ZERO = new OctonionFloat128Member(); 

	private IndexedDataSource<OctonionFloat128Member> storage;
	private StorageConstruction s;
	
	public OctonionFloat128RModuleMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat128Member(), 0);
	}
	
	public OctonionFloat128RModuleMember(BigDecimal... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat128Member(), count);
		setFromBigDecimals(vals);
	}
	
	public OctonionFloat128RModuleMember(BigInteger... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat128Member(), count);
		setFromBigIntegers(vals);
	}
	
	public OctonionFloat128RModuleMember(double... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat128Member(), count);
		setFromDoubles(vals);
	}
	
	public OctonionFloat128RModuleMember(long... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat128Member(), count);
		setFromLongs(vals);
	}
	
	public OctonionFloat128RModuleMember(OctonionFloat128RModuleMember other) {
		set(other);
	}
	
	public OctonionFloat128RModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat128Member(), data.size());
		OctonionFloat128Member tmp = new OctonionFloat128Member();
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

	public OctonionFloat128RModuleMember(StorageConstruction s, long d1) {
		this.s = s;
		alloc(d1);
	}

	public OctonionFloat128RModuleMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, OctonionFloat128Member v) {
		storage.get(i, v);
	}

	@Override
	public void setV(long i, OctonionFloat128Member v) {
		storage.set(i, v);
	}
	
	@Override
	public void set(OctonionFloat128RModuleMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(OctonionFloat128RModuleMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		OctonionFloat128Member value = new OctonionFloat128Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
		for (long i = 0; i < length(); i++) {
			storage.get(i, value);
			BigDecimal r = value.r().v();
			BigDecimal im = value.i().v();
			BigDecimal j = value.j().v();
			BigDecimal k = value.k().v();
			BigDecimal l = value.l().v();
			BigDecimal i0 = value.i0().v();
			BigDecimal j0 = value.j0().v();
			BigDecimal k0 = value.k0().v();
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
		OctonionFloat128Member value = new OctonionFloat128Member();
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
		OctonionFloat128Member tmp = new OctonionFloat128Member();
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
			storage = Storage.allocate(s, new OctonionFloat128Member(), size);
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
		RModuleReshape.compute(G.OQUAD_RMOD, G.OQUAD, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}

	private static final ThreadLocal<OctonionFloat128Member> tmpOct =
			new ThreadLocal<OctonionFloat128Member>()
	{
		protected OctonionFloat128Member initialValue() {
			return new OctonionFloat128Member();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
			OctonionFloat128Member tmp = tmpOct.get();
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
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().byteValue();
				else // component == 1
					return tmp.i().v().byteValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().byteValue();
				else // component == 3
					return tmp.k().v().byteValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().byteValue();
				else // component == 5
					return tmp.i0().v().byteValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().byteValue();
				else // component == 7
					return tmp.k0().v().byteValue();
			}
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().shortValue();
				else // component == 1
					return tmp.i().v().shortValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().shortValue();
				else // component == 3
					return tmp.k().v().shortValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().shortValue();
				else // component == 5
					return tmp.i0().v().shortValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().shortValue();
				else // component == 7
					return tmp.k0().v().shortValue();
			}
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().intValue();
				else // component == 1
					return tmp.i().v().intValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().intValue();
				else // component == 3
					return tmp.k().v().intValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().intValue();
				else // component == 5
					return tmp.i0().v().intValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().intValue();
				else // component == 7
					return tmp.k0().v().intValue();
			}
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().longValue();
				else // component == 1
					return tmp.i().v().longValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().longValue();
				else // component == 3
					return tmp.k().v().longValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().longValue();
				else // component == 5
					return tmp.i0().v().longValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().longValue();
				else // component == 7
					return tmp.k0().v().longValue();
			}
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().floatValue();
				else // component == 1
					return tmp.i().v().floatValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().floatValue();
				else // component == 3
					return tmp.k().v().floatValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().floatValue();
				else // component == 5
					return tmp.i0().v().floatValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().floatValue();
				else // component == 7
					return tmp.k0().v().floatValue();
			}
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().doubleValue();
				else // component == 1
					return tmp.i().v().doubleValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().doubleValue();
				else // component == 3
					return tmp.k().v().doubleValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().doubleValue();
				else // component == 5
					return tmp.i0().v().doubleValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().doubleValue();
				else // component == 7
					return tmp.k0().v().doubleValue();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigInteger.ZERO;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v().toBigInteger();
				else // component == 1
					return tmp.i().v().toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v().toBigInteger();
				else // component == 3
					return tmp.k().v().toBigInteger();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v().toBigInteger();
				else // component == 5
					return tmp.i0().v().toBigInteger();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v().toBigInteger();
				else // component == 7
					return tmp.k0().v().toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigDecimal.ZERO;
		OctonionFloat128Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return tmp.r().v();
				else // component == 1
					return tmp.i().v();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return tmp.j().v();
				else // component == 3
					return tmp.k().v();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return tmp.l().v();
				else // component == 5
					return tmp.i0().v();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return tmp.j0().v();
				else // component == 7
					return tmp.k0().v();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().byteValue();
					else // component == 1
						return tmp.i().v().byteValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().byteValue();
					else // component == 3
						return tmp.k().v().byteValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().byteValue();
					else // component == 5
						return tmp.i0().v().byteValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().byteValue();
					else // component == 7
						return tmp.k0().v().byteValue();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().shortValue();
					else // component == 1
						return tmp.i().v().shortValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().shortValue();
					else // component == 3
						return tmp.k().v().shortValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().shortValue();
					else // component == 5
						return tmp.i0().v().shortValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().shortValue();
					else // component == 7
						return tmp.k0().v().shortValue();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().intValue();
					else // component == 1
						return tmp.i().v().intValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().intValue();
					else // component == 3
						return tmp.k().v().intValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().intValue();
					else // component == 5
						return tmp.i0().v().intValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().intValue();
					else // component == 7
						return tmp.k0().v().intValue();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().longValue();
					else // component == 1
						return tmp.i().v().longValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().longValue();
					else // component == 3
						return tmp.k().v().longValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().longValue();
					else // component == 5
						return tmp.i0().v().longValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().longValue();
					else // component == 7
						return tmp.k0().v().longValue();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().floatValue();
					else // component == 1
						return tmp.i().v().floatValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().floatValue();
					else // component == 3
						return tmp.k().v().floatValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().floatValue();
					else // component == 5
						return tmp.i0().v().floatValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().floatValue();
					else // component == 7
						return tmp.k0().v().floatValue();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().doubleValue();
					else // component == 1
						return tmp.i().v().doubleValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().doubleValue();
					else // component == 3
						return tmp.k().v().doubleValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().doubleValue();
					else // component == 5
						return tmp.i0().v().doubleValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().doubleValue();
					else // component == 7
						return tmp.k0().v().doubleValue();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v().toBigInteger();
					else // component == 1
						return tmp.i().v().toBigInteger();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v().toBigInteger();
					else // component == 3
						return tmp.k().v().toBigInteger();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v().toBigInteger();
					else // component == 5
						return tmp.i0().v().toBigInteger();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v().toBigInteger();
					else // component == 7
						return tmp.k0().v().toBigInteger();
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
			OctonionFloat128Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return tmp.r().v();
					else // component == 1
						return tmp.i().v();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return tmp.j().v();
					else // component == 3
						return tmp.k().v();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return tmp.l().v();
					else // component == 5
						return tmp.i0().v();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return tmp.j0().v();
					else // component == 7
						return tmp.k0().v();
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
	public IndexedDataSource<OctonionFloat128Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		OctonionFloat128Member tmp = G.OQUAD.construct();
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
		if (o instanceof OctonionFloat128RModuleMember) {
			return G.OQUAD_RMOD.isEqual().call(this, (OctonionFloat128RModuleMember) o);
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
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}

	@Override
	public void setFromDoublesExact(double... vals) {
		setFromDoubles(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
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
		OctonionFloat128Member value = G.OQUAD.construct();
		byte[] values = new byte[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().byteValue();
			values[k++] = value.i().v().byteValue();
			values[k++] = value.j().v().byteValue();
			values[k++] = value.k().v().byteValue();
			values[k++] = value.l().v().byteValue();
			values[k++] = value.i0().v().byteValue();
			values[k++] = value.j0().v().byteValue();
			values[k++] = value.k0().v().byteValue();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		short[] values = new short[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().shortValue();
			values[k++] = value.i().v().shortValue();
			values[k++] = value.j().v().shortValue();
			values[k++] = value.k().v().shortValue();
			values[k++] = value.l().v().shortValue();
			values[k++] = value.i0().v().shortValue();
			values[k++] = value.j0().v().shortValue();
			values[k++] = value.k0().v().shortValue();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		int[] values = new int[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().intValue();
			values[k++] = value.i().v().intValue();
			values[k++] = value.j().v().intValue();
			values[k++] = value.k().v().intValue();
			values[k++] = value.l().v().intValue();
			values[k++] = value.i0().v().intValue();
			values[k++] = value.j0().v().intValue();
			values[k++] = value.k0().v().intValue();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		long[] values = new long[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().longValue();
			values[k++] = value.i().v().longValue();
			values[k++] = value.j().v().longValue();
			values[k++] = value.k().v().longValue();
			values[k++] = value.l().v().longValue();
			values[k++] = value.i0().v().longValue();
			values[k++] = value.j0().v().longValue();
			values[k++] = value.k0().v().longValue();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		float[] values = new float[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().floatValue();
			values[k++] = value.i().v().floatValue();
			values[k++] = value.j().v().floatValue();
			values[k++] = value.k().v().floatValue();
			values[k++] = value.l().v().floatValue();
			values[k++] = value.i0().v().floatValue();
			values[k++] = value.j0().v().floatValue();
			values[k++] = value.k0().v().floatValue();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		double[] values = new double[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().doubleValue();
			values[k++] = value.i().v().doubleValue();
			values[k++] = value.j().v().doubleValue();
			values[k++] = value.k().v().doubleValue();
			values[k++] = value.l().v().doubleValue();
			values[k++] = value.i0().v().doubleValue();
			values[k++] = value.j0().v().doubleValue();
			values[k++] = value.k0().v().doubleValue();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		BigInteger[] values = new BigInteger[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().toBigInteger();
			values[k++] = value.i().v().toBigInteger();
			values[k++] = value.j().v().toBigInteger();
			values[k++] = value.k().v().toBigInteger();
			values[k++] = value.l().v().toBigInteger();
			values[k++] = value.i0().v().toBigInteger();
			values[k++] = value.j0().v().toBigInteger();
			values[k++] = value.k0().v().toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat128Member value = G.OQUAD.construct();
		BigDecimal[] values = new BigDecimal[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v();
			values[k++] = value.i().v();
			values[k++] = value.j().v();
			values[k++] = value.k().v();
			values[k++] = value.l().v();
			values[k++] = value.i0().v();
			values[k++] = value.j0().v();
			values[k++] = value.k0().v();
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public OctonionFloat128RModule getAlgebra() {

		return G.OQUAD_RMOD;
	}
}
