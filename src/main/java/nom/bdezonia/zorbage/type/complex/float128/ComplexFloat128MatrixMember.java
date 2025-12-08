/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.MatrixType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.MatrixReshape;
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
public final class ComplexFloat128MatrixMember
	implements
		MatrixMember<ComplexFloat128Member>,
		Gettable<ComplexFloat128MatrixMember>,
		Settable<ComplexFloat128MatrixMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<ComplexFloat128Member>,
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
		GetAlgebra<ComplexFloat128Matrix, ComplexFloat128MatrixMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		MatrixType,
		NanIncludedType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final ComplexFloat128Member ZERO = new ComplexFloat128Member();
	
	private IndexedDataSource<ComplexFloat128Member> storage;
	private long rows;
	private long cols;
	private StorageConstruction s;
	
	public ComplexFloat128MatrixMember() {
		this(0,0);
	}
	
	public ComplexFloat128MatrixMember(ComplexFloat128MatrixMember other) {
		set(other);
	}
	
	public ComplexFloat128MatrixMember(long r, long c) {
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(r,c);
	}
	
	public ComplexFloat128MatrixMember(long r, long c, BigDecimal... vals) {
		this(r,c);
		setFromBigDecimals(vals);
	}
	
	public ComplexFloat128MatrixMember(long r, long c, BigInteger... vals) {
		this(r,c);
		setFromBigIntegers(vals);
	}
	
	public ComplexFloat128MatrixMember(long r, long c, double... vals) {
		this(r,c);
		setFromDoubles(vals);
	}
	
	public ComplexFloat128MatrixMember(long r, long c, long... vals) {
		this(r,c);
		setFromLongs(vals);
	}

	public ComplexFloat128MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		this.s = StorageConstruction.MEM_ARRAY;
		init(dimensions[1],dimensions[0]);
		ComplexFloat128Member tmp = new ComplexFloat128Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r());
			tmp.setI(val.i());
			storage.set(i, tmp);
		}
	}
	
	public ComplexFloat128MatrixMember(StorageConstruction s, long d1, long d2) {
		rows = -1;
		cols = -1;
		this.s = s;
		init(d2, d1);
	}
	
	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public long rows() { return rows; }

	@Override
	public long cols() { return cols; }

	@Override
	public boolean alloc(long r, long c) {
		if (rows != r || cols != c) {
			rows = r;
			cols = c;
		}
		if (storage == null || storage.size() != r*c) {
			storage = Storage.allocate(s, new ComplexFloat128Member(), r*c);
			return true;
		}
		return false;
	}
	
	@Override
	public void init(long r, long c) {
		if (!alloc(r,c)) {
			long storageSize = r*c;
			for (long i = 0; i < storageSize; i++)
				storage.set(i, ZERO);
		}
	}
	
	@Override
	public void getV(long r, long c, ComplexFloat128Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, ComplexFloat128Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.set(index, value);
	}

	@Override
	public void set(ComplexFloat128MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(ComplexFloat128MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		ComplexFloat128Member value = G.CQUAD.construct();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.r().v();
			BigDecimal im = value.i().v();
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(im);
			o.setJ(BigDecimal.ZERO);
			o.setK(BigDecimal.ZERO);
			o.setL(BigDecimal.ZERO);
			o.setI0(BigDecimal.ZERO);
			o.setJ0(BigDecimal.ZERO);
			o.setK0(BigDecimal.ZERO);
		}
		rep.setMatrix(rows, cols, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		ComplexFloat128Member value = G.CQUAD.construct();
		BigList<OctonionRepresentation> mat = rep.getMatrix();
		alloc(rep.getMatrixRowDim(), rep.getMatrixColDim());
		long matSize = mat.size();
		for (long i = 0; i < matSize; i++) {
			OctonionRepresentation o = mat.get(i);
			value.setR(o.r());
			value.setI(o.i());
			storage.set(i, value);
		}
	}

	@Override
	public String toString() {
		ComplexFloat128Member tmp = G.CQUAD.construct();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (long r = 0; r < rows; r++) {
			builder.append('[');
			for (long c = 0; c < cols; c++) {
				if (c != 0)
					builder.append(',');
				getV(r, c, tmp);
				builder.append(tmp.toString());
			}
			builder.append(']');
		}
		builder.append(']');
		return builder.toString();
	}

	@Override
	public int numDimensions() {
		return 2;
	}

	@Override
	public void reshape(long rows, long cols) {
		MatrixReshape.compute(G.CQUAD_MAT, G.CQUAD, rows, cols, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
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
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else
			tmp.setI(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(new BigDecimal(v));
		else
			tmp.setI(new BigDecimal(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long c = index.get(0);
		long r = index.get(1);
		ComplexFloat128Member tmp = tmpComp.get();
		getV(r, c, tmp);
		if (component == 0)
			tmp.setR(v);
		else
			tmp.setI(v);
		setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(new BigDecimal(v));
			else
				tmp.setI(new BigDecimal(v));
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			long c = index.get(0);
			long r = index.get(1);
			ComplexFloat128Member tmp = tmpComp.get();
			getV(r, c, tmp);
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
			setV(r, c, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		ComplexFloat128Member tmp = tmpComp.get();
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
			if (component == 0) return tmp.r().v().shortValue();
			return tmp.i().v().shortValue();
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(1) >= rows) {
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
			getV(index.get(1), index.get(0), tmp);
			if (component == 0) return tmp.r().v();
			return tmp.i().v();
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<ComplexFloat128Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		ComplexFloat128Member tmp = G.CQUAD.construct();
		long rows = rows();
		long cols = cols();
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(rows);
		v = Hasher.PRIME * v + Hasher.hashCode(cols);
		if (rows > 0 && cols > 0) {
			storage.get(0, tmp);
			v = Hasher.PRIME * v + tmp.hashCode();
		}
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ComplexFloat128MatrixMember) {
			return G.CQUAD_MAT.isEqual().call(this, (ComplexFloat128MatrixMember) o);
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
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  new BigDecimal(vals[i + 0]) );
			value.setI(  new BigDecimal(vals[i + 1]) );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 2;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		ComplexFloat128Member value = G.CQUAD.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}
	
	@Override
	public byte[] getAsByteArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		byte[] values = new byte[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().byteValue();
			values[k++] = value.i().v().byteValue();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		short[] values = new short[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().shortValue();
			values[k++] = value.i().v().shortValue();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		int[] values = new int[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().intValue();
			values[k++] = value.i().v().intValue();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		long[] values = new long[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().longValue();
			values[k++] = value.i().v().longValue();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		float[] values = new float[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().floatValue();
			values[k++] = value.i().v().floatValue();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		double[] values = new double[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().doubleValue();
			values[k++] = value.i().v().doubleValue();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 2))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		ComplexFloat128Member value = G.CQUAD.construct();
		BigInteger[] values = new BigInteger[2 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().v().toBigInteger();
			values[k++] = value.i().v().toBigInteger();
		}
		return values;
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

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}

	@Override
	public ComplexFloat128Matrix getAlgebra() {

		return G.CQUAD_MAT;
	}
}
