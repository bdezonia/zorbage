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
package nom.bdezonia.zorbage.type.real.float128;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
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
public final class Float128MatrixMember
	implements
		MatrixMember<Float128Member>,
		Settable<Float128MatrixMember>,
		Gettable<Float128MatrixMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<Float128Member>,
		SetFromBigDecimal, SetFromBigInteger, SetFromDouble, SetFromLong,
		GetAsBigDecimalArray,
		ThreadAccess
{
	private static final Float128Member ZERO = new Float128Member();

	private IndexedDataSource<Float128Member> storage;
	private long rows;
	private long cols;
	private StorageConstruction s;
	
	public Float128MatrixMember() {
		this(0,0);
	}
	
	public Float128MatrixMember(long r, long c) {
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(r,c);
	}
	
	public Float128MatrixMember(long r, long c, BigDecimal... vals) {
		this(r,c);
		setFromBigDecimal(vals);
	}
	
	public Float128MatrixMember(long r, long c, BigInteger... vals) {
		this(r,c);
		setFromBigInteger(vals);
	}
	
	public Float128MatrixMember(long r, long c, double... vals) {
		this(r,c);
		setFromDouble(vals);
	}
	
	public Float128MatrixMember(long r, long c, long... vals) {
		this(r,c);
		setFromLong(vals);
	}
	
	public Float128MatrixMember(Float128MatrixMember other) {
		set(other);
	}
	
	public Float128MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		this.s = StorageConstruction.MEM_ARRAY;
		init(dimensions[1],dimensions[0]);
		Float128Member tmp = new Float128Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r());
			storage.set(i, tmp);
		}
	}
	
	public Float128MatrixMember(StorageConstruction s, long d1, long d2) {
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
			storage = Storage.allocate(s, new Float128Member(), r*c);
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
	public void getV(long r, long c, Float128Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, Float128Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(Float128MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(Float128MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.s = s;
		other.storage = storage.duplicate();
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		Float128Member value = new Float128Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.v();
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(BigDecimal.ZERO);
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
		Float128Member value = new Float128Member();
		BigList<OctonionRepresentation> mat = rep.getMatrix();
		alloc(rep.getMatrixRowDim(), rep.getMatrixColDim());
		long matSize = mat.size();
		for (long i = 0; i < matSize; i++) {
			OctonionRepresentation o = mat.get(i);
			value.setV(o.r());
			storage.set(i, value);
		}
	}

	@Override
	public String toString() {
		Float128Member tmp = new Float128Member();
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
		MatrixReshape.compute(G.QUAD_MAT, G.QUAD, rows, cols, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
		return 1;
	}
	
	private static final ThreadLocal<Float128Member> tmpFloat =
			new ThreadLocal<Float128Member>()
	{
		protected Float128Member initialValue() {
			return new Float128Member();
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
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(BigDecimal.valueOf(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(new BigDecimal(v));
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		Float128Member tmp = tmpFloat.get();
		long c = index.get(0);
		long r = index.get(1);
		tmp.setV(v);
		setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(BigDecimal.valueOf(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(new BigDecimal(v));
			setV(r, c, tmp);
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
			Float128Member tmp = tmpFloat.get();
			long c = index.get(0);
			long r = index.get(1);
			tmp.setV(v);
			setV(r, c, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
			Float128Member tmp = tmpFloat.get();
			getV(index.get(1), index.get(0), tmp);
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
	public IndexedDataSource<Float128Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		Float128Member tmp = G.QUAD.construct();
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
		if (o instanceof Float128MatrixMember) {
			return G.QUAD_MAT.isEqual().call(this, (Float128MatrixMember) o);
		}
		return false;
	}

	@Override
	public void setFromLong(long... vals) {
		if (vals.length != storage.size()) {
			throw new IllegalArgumentException("number of elements passed in do not fit allocated storage");
		}
		Float128Member tmp = new Float128Member();
		for (int i = 0; i < vals.length; i++) {
			tmp.setV(BigDecimal.valueOf(vals[i]));
			storage.set(i, tmp);
		}
	}

	@Override
	public void setFromDouble(double... vals) {
		if (vals.length != storage.size()) {
			throw new IllegalArgumentException("number of elements passed in do not fit allocated storage");
		}
		Float128Member tmp = new Float128Member();
		for (int i = 0; i < vals.length; i++) {
			tmp.setV(BigDecimal.valueOf(vals[i]));
			storage.set(i, tmp);
		}
	}

	@Override
	public void setFromBigInteger(BigInteger... vals) {
		if (vals.length != storage.size()) {
			throw new IllegalArgumentException("number of elements passed in do not fit allocated storage");
		}
		Float128Member tmp = new Float128Member();
		for (int i = 0; i < vals.length; i++) {
			tmp.setV(new BigDecimal(vals[i]));
			storage.set(i, tmp);
		}
	}

	@Override
	public void setFromBigDecimal(BigDecimal... vals) {
		if (vals.length != storage.size()) {
			throw new IllegalArgumentException("number of elements passed in do not fit allocated storage");
		}
		Float128Member tmp = new Float128Member();
		for (int i = 0; i < vals.length; i++) {
			tmp.setV(vals[i]);
			storage.set(i, tmp);
		}
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float128Member value = G.QUAD.construct();
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
