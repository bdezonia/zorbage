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
package nom.bdezonia.zorbage.type.quaternion.float32;

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
public final class QuaternionFloat32MatrixMember
	implements
		MatrixMember<QuaternionFloat32Member>,
		Gettable<QuaternionFloat32MatrixMember>,
		Settable<QuaternionFloat32MatrixMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<QuaternionFloat32Member>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromLongs,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsFloatArrayExact,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		ThreadAccess,
		GetAlgebra<QuaternionFloat32Matrix, QuaternionFloat32MatrixMember>
{
	private static final QuaternionFloat32Member ZERO = new QuaternionFloat32Member();

	private IndexedDataSource<QuaternionFloat32Member> storage;
	private long rows;
	private long cols;
	private StorageConstruction s;
	
	public QuaternionFloat32MatrixMember() {
		this(0,0);
	}
	
	public QuaternionFloat32MatrixMember(long r, long c) {
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(r,c);
	}
	
	public QuaternionFloat32MatrixMember(long r, long c, float... vals) {
		this(r,c);
		setFromFloats(vals);
	}
	
	public QuaternionFloat32MatrixMember(QuaternionFloat32MatrixMember other) {
		set(other);
	}
	
	public QuaternionFloat32MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		this.s = StorageConstruction.MEM_ARRAY;
		init(dimensions[1], dimensions[0]);
		QuaternionFloat32Member tmp = new QuaternionFloat32Member();
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().floatValue());
			tmp.setI(val.i().floatValue());
			tmp.setJ(val.j().floatValue());
			tmp.setK(val.k().floatValue());
			storage.set(i, tmp);
		}
	}
	
	public QuaternionFloat32MatrixMember(StorageConstruction s, long d1, long d2) {
		rows = -1;
		cols = -1;
		this.s = s;
		init(d2,d1);
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
			storage = Storage.allocate(s, new QuaternionFloat32Member(), r*c);
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
	public void getV(long r, long c, QuaternionFloat32Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, QuaternionFloat32Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(QuaternionFloat32MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(QuaternionFloat32MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		QuaternionFloat32Member value = new QuaternionFloat32Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = BigDecimal.valueOf(value.r());
			BigDecimal im = BigDecimal.valueOf(value.i());
			BigDecimal j = BigDecimal.valueOf(value.j());
			BigDecimal k = BigDecimal.valueOf(value.k());
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(im);
			o.setJ(j);
			o.setK(k);
			o.setL(BigDecimal.ZERO);
			o.setI0(BigDecimal.ZERO);
			o.setJ0(BigDecimal.ZERO);
			o.setK0(BigDecimal.ZERO);
		}
		rep.setMatrix(rows, cols, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		QuaternionFloat32Member value = new QuaternionFloat32Member();
		BigList<OctonionRepresentation> mat = rep.getMatrix();
		alloc(rep.getMatrixRowDim(), rep.getMatrixColDim());
		long matSize = mat.size();
		for (long i = 0; i < matSize; i++) {
			OctonionRepresentation o = mat.get(i);
			value.setR(o.r().floatValue());
			value.setI(o.i().floatValue());
			value.setJ(o.j().floatValue());
			value.setK(o.k().floatValue());
			storage.set(i, value);
		}
	}

	@Override
	public String toString() {
		QuaternionFloat32Member tmp = new QuaternionFloat32Member();
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
		MatrixReshape.compute(G.QFLT_MAT, G.QFLT, rows, cols, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
		return 1;
	}
	
	private static final ThreadLocal<QuaternionFloat32Member> tmpQuat =
			new ThreadLocal<QuaternionFloat32Member>()
	{
		protected QuaternionFloat32Member initialValue() {
			return new QuaternionFloat32Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR((float)v);
			else // component == 1
				tmp.setI((float)v);
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ((float)v);
			else // component == 3
				tmp.setK((float)v);
		}
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(v.floatValue());
			else // component == 1
				tmp.setI(v.floatValue());
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(v.floatValue());
			else // component == 3
				tmp.setK(v.floatValue());
		}
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		QuaternionFloat32Member tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		getV(r, c, tmp);
		if (component < 2) {
			// 0 <= component <= 1
			if (component == 0)
				tmp.setR(v.floatValue());
			else // component == 1
				tmp.setI(v.floatValue());
		}
		else {
			// 2 <= component <= 3
			if (component == 2)
				tmp.setJ(v.floatValue());
			else // component == 3
				tmp.setK(v.floatValue());
		}
		setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
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
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
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
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
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
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
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
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
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
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR((float)v);
				else // component == 1
					tmp.setI((float)v);
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ((float)v);
				else // component == 3
					tmp.setK((float)v);
			}
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(v.floatValue());
				else // component == 1
					tmp.setI(v.floatValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(v.floatValue());
				else // component == 3
					tmp.setK(v.floatValue());
			}
			setV(r, c, tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			getV(r, c, tmp);
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					tmp.setR(v.floatValue());
				else // component == 1
					tmp.setI(v.floatValue());
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					tmp.setJ(v.floatValue());
				else // component == 3
					tmp.setK(v.floatValue());
			}
			setV(r, c, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return BigInteger.ZERO;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return BigDecimal.ZERO;
		QuaternionFloat32Member tmp = tmpQuat.get();
		getV(index.get(1),index.get(0), tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 3;
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
			QuaternionFloat32Member tmp = tmpQuat.get();
			getV(index.get(1), index.get(0), tmp);
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
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<QuaternionFloat32Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		QuaternionFloat32Member tmp = G.QFLT.construct();
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
		if (o instanceof QuaternionFloat32MatrixMember) {
			return G.QFLT_MAT.isEqual().call(this, (QuaternionFloat32MatrixMember) o);
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
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}
	
	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  (float) vals[i + 0] );
			value.setI(  (float) vals[i + 1] );
			value.setJ(  (float) vals[i + 2] );
			value.setK(  (float) vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].floatValue() );
			value.setI(  vals[i + 1].floatValue() );
			value.setJ(  vals[i + 2].floatValue() );
			value.setK(  vals[i + 3].floatValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat32Member value = G.QFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].floatValue() );
			value.setI(  vals[i + 1].floatValue() );
			value.setJ(  vals[i + 2].floatValue() );
			value.setK(  vals[i + 3].floatValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public float[] getAsFloatArrayExact() {
		return getAsFloatArray();
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
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		byte[] values = new byte[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (byte) value.r();
			values[k++] = (byte) value.i();
			values[k++] = (byte) value.j();
			values[k++] = (byte) value.k();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		short[] values = new short[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (short) value.r();
			values[k++] = (short) value.i();
			values[k++] = (short) value.j();
			values[k++] = (short) value.k();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		int[] values = new int[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (int) value.r();
			values[k++] = (int) value.i();
			values[k++] = (int) value.j();
			values[k++] = (int) value.k();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		long[] values = new long[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (long) value.r();
			values[k++] = (long) value.i();
			values[k++] = (long) value.j();
			values[k++] = (long) value.k();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		float[] values = new float[4 * (int) storage.size()];
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
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		double[] values = new double[4 * (int) storage.size()];
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
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		BigInteger[] values = new BigInteger[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = BigDecimal.valueOf(value.r()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.i()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.j()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.k()).toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat32Member value = G.QFLT.construct();
		BigDecimal[] values = new BigDecimal[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = BigDecimal.valueOf(value.r());
			values[k++] = BigDecimal.valueOf(value.i());
			values[k++] = BigDecimal.valueOf(value.j());
			values[k++] = BigDecimal.valueOf(value.k());
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public QuaternionFloat32Matrix getAlgebra() {

		return G.QFLT_MAT;
	}
}
