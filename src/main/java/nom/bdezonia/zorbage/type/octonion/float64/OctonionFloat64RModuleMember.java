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
package nom.bdezonia.zorbage.type.octonion.float64;

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
public final class OctonionFloat64RModuleMember
	implements
		RModuleMember<OctonionFloat64Member>,
		Gettable<OctonionFloat64RModuleMember>,
		Settable<OctonionFloat64RModuleMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionFloat64Member>,
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
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		ThreadAccess,
		GetAlgebra<OctonionFloat64RModule, OctonionFloat64RModuleMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member(); 

	private IndexedDataSource<OctonionFloat64Member> storage;
	private StorageConstruction s;
	
	public OctonionFloat64RModuleMember() {
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat64Member(), 0);
	}
	
	public OctonionFloat64RModuleMember(double... vals) {
		final int count = vals.length / 8;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat64Member(), count);
		setFromDoubles(vals);
	}
	
	public OctonionFloat64RModuleMember(OctonionFloat64RModuleMember other) {
		set(other);
	}
	
	public OctonionFloat64RModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat64Member(), data.size());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
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
		alloc(d1);
	}

	public OctonionFloat64RModuleMember(long d1) {
		this(StorageConstruction.MEM_ARRAY, d1);
	}

	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void getV(long i, OctonionFloat64Member v) {
		storage.get(i, v);
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
	public void toRep(TensorOctonionRepresentation rep) {
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(length(), new OctonionRepresentation());
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
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> rmod = rep.getRModule();
		long rmodSize = rmod.size();
		init(rmodSize);
		for (long i = 0; i < rmodSize; i++) {
			OctonionRepresentation o = rmod.get(i);
			value.setR(o.r().doubleValue());
			value.setI(o.i().doubleValue());
			value.setJ(o.j().doubleValue());
			value.setK(o.k().doubleValue());
			value.setL(o.l().doubleValue());
			value.setI0(o.i0().doubleValue());
			value.setJ0(o.j0().doubleValue());
			value.setK0(o.k0().doubleValue());
			storage.set(i, value);
		}
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() { 
		OctonionFloat64Member tmp = new OctonionFloat64Member();
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
			storage = Storage.allocate(s, new OctonionFloat64Member(), size);
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
		RModuleReshape.compute(G.ODBL_RMOD, G.ODBL, len, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}

	private static final ThreadLocal<OctonionFloat64Member> tmpOct =
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
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		OctonionFloat64Member tmp = tmpOct.get();
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
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		OctonionFloat64Member tmp = tmpOct.get();
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
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		OctonionFloat64Member tmp = tmpOct.get();
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
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		OctonionFloat64Member tmp = tmpOct.get();
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
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		OctonionFloat64Member tmp = tmpOct.get();
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
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(v.doubleValue());
				else // component == 1
					tmp.setI(v.doubleValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(v.doubleValue());
				else // component == 3
					tmp.setK(v.doubleValue());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(v.doubleValue());
				else // component == 5
					tmp.setI0(v.doubleValue());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(v.doubleValue());
				else // component == 7
					tmp.setK0(v.doubleValue());
			}
		}
		setV(i, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		OctonionFloat64Member tmp = tmpOct.get();
		long i = index.get(0);
		getV(i, tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(v.doubleValue());
				else // component == 1
					tmp.setI(v.doubleValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(v.doubleValue());
				else // component == 3
					tmp.setK(v.doubleValue());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					tmp.setL(v.doubleValue());
				else // component == 5
					tmp.setI0(v.doubleValue());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					tmp.setJ0(v.doubleValue());
				else // component == 7
					tmp.setK0(v.doubleValue());
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			getV(i, tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						tmp.setR(v.doubleValue());
					else // component == 1
						tmp.setI(v.doubleValue());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						tmp.setJ(v.doubleValue());
					else // component == 3
						tmp.setK(v.doubleValue());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(v.doubleValue());
					else // component == 5
						tmp.setI0(v.doubleValue());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(v.doubleValue());
					else // component == 7
						tmp.setK0(v.doubleValue());
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
			OctonionFloat64Member tmp = tmpOct.get();
			long i = index.get(0);
			getV(i, tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						tmp.setR(v.doubleValue());
					else // component == 1
						tmp.setI(v.doubleValue());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						tmp.setJ(v.doubleValue());
					else // component == 3
						tmp.setK(v.doubleValue());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						tmp.setL(v.doubleValue());
					else // component == 5
						tmp.setI0(v.doubleValue());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						tmp.setJ0(v.doubleValue());
					else // component == 7
						tmp.setK0(v.doubleValue());
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
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return (byte) tmp.r();
				else // component == 1
					return (byte) tmp.i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (byte) tmp.j();
				else // component == 3
					return (byte) tmp.k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (byte) tmp.l();
				else // component == 5
					return (byte) tmp.i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (byte) tmp.j0();
				else // component == 7
					return (byte) tmp.k0();
			}
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return (short) tmp.r();
				else // component == 1
					return (short) tmp.i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (short) tmp.j();
				else // component == 3
					return (short) tmp.k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (short) tmp.l();
				else // component == 5
					return (short) tmp.i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (short) tmp.j0();
				else // component == 7
					return (short) tmp.k0();
			}
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return (int) tmp.r();
				else // component == 1
					return (int) tmp.i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (int) tmp.j();
				else // component == 3
					return (int) tmp.k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (int) tmp.l();
				else // component == 5
					return (int) tmp.i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (int) tmp.j0();
				else // component == 7
					return (int) tmp.k0();
			}
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return (long) tmp.r();
				else // component == 1
					return (long) tmp.i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (long) tmp.j();
				else // component == 3
					return (long) tmp.k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (long) tmp.l();
				else // component == 5
					return (long) tmp.i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (long) tmp.j0();
				else // component == 7
					return (long) tmp.k0();
			}
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return (float) tmp.r();
				else // component == 1
					return (float) tmp.i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return (float) tmp.j();
				else // component == 3
					return (float) tmp.k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return (float) tmp.l();
				else // component == 5
					return (float) tmp.i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return (float) tmp.j0();
				else // component == 7
					return (float) tmp.k0();
			}
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
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
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigInteger.ZERO;
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return BigDecimal.valueOf(tmp.r()).toBigInteger();
				else // component == 1
					return BigDecimal.valueOf(tmp.i()).toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return BigDecimal.valueOf(tmp.j()).toBigInteger();
				else // component == 3
					return BigDecimal.valueOf(tmp.k()).toBigInteger();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return BigDecimal.valueOf(tmp.l()).toBigInteger();
				else // component == 5
					return BigDecimal.valueOf(tmp.i0()).toBigInteger();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return BigDecimal.valueOf(tmp.j0()).toBigInteger();
				else // component == 7
					return BigDecimal.valueOf(tmp.k0()).toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigDecimal.ZERO;
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index.get(0), tmp);
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return BigDecimal.valueOf(tmp.r());
				else // component == 1
					return BigDecimal.valueOf(tmp.i());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return BigDecimal.valueOf(tmp.j());
				else // component == 3
					return BigDecimal.valueOf(tmp.k());
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return BigDecimal.valueOf(tmp.l());
				else // component == 5
					return BigDecimal.valueOf(tmp.i0());
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return BigDecimal.valueOf(tmp.j0());
				else // component == 7
					return BigDecimal.valueOf(tmp.k0());
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return (byte) tmp.r();
					else // component == 1
						return (byte) tmp.i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (byte) tmp.j();
					else // component == 3
						return (byte) tmp.k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (byte) tmp.l();
					else // component == 5
						return (byte) tmp.i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (byte) tmp.j0();
					else // component == 7
						return (byte) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return (short) tmp.r();
					else // component == 1
						return (short) tmp.i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (short) tmp.j();
					else // component == 3
						return (short) tmp.k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (short) tmp.l();
					else // component == 5
						return (short) tmp.i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (short) tmp.j0();
					else // component == 7
						return (short) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return (int) tmp.r();
					else // component == 1
						return (int) tmp.i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (int) tmp.j();
					else // component == 3
						return (int) tmp.k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (int) tmp.l();
					else // component == 5
						return (int) tmp.i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (int) tmp.j0();
					else // component == 7
						return (int) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return (long) tmp.r();
					else // component == 1
						return (long) tmp.i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (long) tmp.j();
					else // component == 3
						return (long) tmp.k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (long) tmp.l();
					else // component == 5
						return (long) tmp.i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (long) tmp.j0();
					else // component == 7
						return (long) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return (float) tmp.r();
					else // component == 1
						return (float) tmp.i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return (float) tmp.j();
					else // component == 3
						return (float) tmp.k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return (float) tmp.l();
					else // component == 5
						return (float) tmp.i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return (float) tmp.j0();
					else // component == 7
						return (float) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return BigDecimal.valueOf(tmp.r()).toBigInteger();
					else // component == 1
						return BigDecimal.valueOf(tmp.i()).toBigInteger();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return BigDecimal.valueOf(tmp.j()).toBigInteger();
					else // component == 3
						return BigDecimal.valueOf(tmp.k()).toBigInteger();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return BigDecimal.valueOf(tmp.l()).toBigInteger();
					else // component == 5
						return BigDecimal.valueOf(tmp.i0()).toBigInteger();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return BigDecimal.valueOf(tmp.j0()).toBigInteger();
					else // component == 7
						return BigDecimal.valueOf(tmp.k0()).toBigInteger();
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index.get(0), tmp);
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return BigDecimal.valueOf(tmp.r());
					else // component == 1
						return BigDecimal.valueOf(tmp.i());
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return BigDecimal.valueOf(tmp.j());
					else // component == 3
						return BigDecimal.valueOf(tmp.k());
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return BigDecimal.valueOf(tmp.l());
					else // component == 5
						return BigDecimal.valueOf(tmp.i0());
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return BigDecimal.valueOf(tmp.j0());
					else // component == 7
						return BigDecimal.valueOf(tmp.k0());
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
	public IndexedDataSource<OctonionFloat64Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		OctonionFloat64Member tmp = G.ODBL.construct();
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
		if (o instanceof OctonionFloat64RModuleMember) {
			return G.ODBL_RMOD.isEqual().call(this, (OctonionFloat64RModuleMember) o);
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
		OctonionFloat64Member value = G.ODBL.construct();
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
	public void setFromShorts(short... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
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
	public void setFromInts(int... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
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
	public void setFromLongs(long... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
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
	public void setFromFloats(float... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
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
	public void setFromDoubles(double... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
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
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].doubleValue() );
			value.setI(  vals[i + 1].doubleValue() );
			value.setJ(  vals[i + 2].doubleValue() );
			value.setK(  vals[i + 3].doubleValue() );
			value.setL(  vals[i + 4].doubleValue() );
			value.setI0( vals[i + 5].doubleValue() );
			value.setJ0( vals[i + 6].doubleValue() );
			value.setK0( vals[i + 7].doubleValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != length()) {
			reshape(vals.length/componentCount);
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].doubleValue() );
			value.setI(  vals[i + 1].doubleValue() );
			value.setJ(  vals[i + 2].doubleValue() );
			value.setK(  vals[i + 3].doubleValue() );
			value.setL(  vals[i + 4].doubleValue() );
			value.setI0( vals[i + 5].doubleValue() );
			value.setJ0( vals[i + 6].doubleValue() );
			value.setK0( vals[i + 7].doubleValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
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
		OctonionFloat64Member value = G.ODBL.construct();
		byte[] values = new byte[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (byte) value.r();
			values[k++] = (byte) value.i();
			values[k++] = (byte) value.j();
			values[k++] = (byte) value.k();
			values[k++] = (byte) value.l();
			values[k++] = (byte) value.i0();
			values[k++] = (byte) value.j0();
			values[k++] = (byte) value.k0();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		short[] values = new short[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (short) value.r();
			values[k++] = (short) value.i();
			values[k++] = (short) value.j();
			values[k++] = (short) value.k();
			values[k++] = (short) value.l();
			values[k++] = (short) value.i0();
			values[k++] = (short) value.j0();
			values[k++] = (short) value.k0();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		int[] values = new int[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (int) value.r();
			values[k++] = (int) value.i();
			values[k++] = (int) value.j();
			values[k++] = (int) value.k();
			values[k++] = (int) value.l();
			values[k++] = (int) value.i0();
			values[k++] = (int) value.j0();
			values[k++] = (int) value.k0();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		long[] values = new long[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (long) value.r();
			values[k++] = (long) value.i();
			values[k++] = (long) value.j();
			values[k++] = (long) value.k();
			values[k++] = (long) value.l();
			values[k++] = (long) value.i0();
			values[k++] = (long) value.j0();
			values[k++] = (long) value.k0();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		float[] values = new float[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (float) value.r();
			values[k++] = (float) value.i();
			values[k++] = (float) value.j();
			values[k++] = (float) value.k();
			values[k++] = (float) value.l();
			values[k++] = (float) value.i0();
			values[k++] = (float) value.j0();
			values[k++] = (float) value.k0();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		double[] values = new double[8 * (int) storage.size()];
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
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		BigInteger[] values = new BigInteger[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = BigDecimal.valueOf(value.r()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.i()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.j()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.k()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.l()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.i0()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.j0()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.k0()).toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		BigDecimal[] values = new BigDecimal[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = BigDecimal.valueOf(value.r());
			values[k++] = BigDecimal.valueOf(value.i());
			values[k++] = BigDecimal.valueOf(value.j());
			values[k++] = BigDecimal.valueOf(value.k());
			values[k++] = BigDecimal.valueOf(value.l());
			values[k++] = BigDecimal.valueOf(value.i0());
			values[k++] = BigDecimal.valueOf(value.j0());
			values[k++] = BigDecimal.valueOf(value.k0());
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public OctonionFloat64RModule getAlgebra() {

		return G.ODBL_RMOD;
	}
}
