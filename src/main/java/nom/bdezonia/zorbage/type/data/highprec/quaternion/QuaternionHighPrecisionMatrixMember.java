/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.data.highprec.quaternion;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixReshape;
import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.RawData;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionHighPrecisionMatrixMember
	implements
		MatrixMember<QuaternionHighPrecisionMember>,
		Gettable<QuaternionHighPrecisionMatrixMember>,
		Settable<QuaternionHighPrecisionMatrixMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<QuaternionHighPrecisionMember>
{
	private static final QuaternionHighPrecisionMember ZERO = new QuaternionHighPrecisionMember();

	private IndexedDataSource<QuaternionHighPrecisionMember> storage;
	private long rows;
	private long cols;
	private StorageConstruction s;
	
	public QuaternionHighPrecisionMatrixMember() {
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(0,0);
	}
	
	public QuaternionHighPrecisionMatrixMember(int r, int c, BigDecimal[] vals) {
		if (vals.length != r*c*4)
			throw new IllegalArgumentException("input values do not match declared shape");
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(r,c);
		QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
		int quatCount = vals.length / 4;
		for (int i = 0; i < quatCount; i++) {
			tmp.setR(vals[4*i]);
			tmp.setI(vals[4*i+1]);
			tmp.setJ(vals[4*i+2]);
			tmp.setK(vals[4*i+3]);
			storage.set(i, tmp);
		}
	}
	
	public QuaternionHighPrecisionMatrixMember(QuaternionHighPrecisionMatrixMember other) {
		set(other);
	}
	
	public QuaternionHighPrecisionMatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		this.s = StorageConstruction.MEM_ARRAY;
		init(dimensions[1], dimensions[0]);
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
	
	public QuaternionHighPrecisionMatrixMember(StorageConstruction s, long d1, long d2) {
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
			storage = Storage.allocate(s, r*c, new QuaternionHighPrecisionMember());
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
	public void v(long r, long c, QuaternionHighPrecisionMember value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, QuaternionHighPrecisionMember value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(QuaternionHighPrecisionMatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(QuaternionHighPrecisionMatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize);
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.r();
			BigDecimal im = value.i();
			BigDecimal j = value.j();
			BigDecimal k = value.k();
			OctonionRepresentation o = new OctonionRepresentation(re,im,j,k);
			values.set(i, o);
		}
		rep.setMatrix(rows, cols, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		BigList<OctonionRepresentation> mat = rep.getMatrix();
		alloc(rep.getMatrixRowDim(), rep.getMatrixColDim());
		long matSize = mat.size();
		for (long i = 0; i < matSize; i++) {
			OctonionRepresentation o = mat.get(i);
			value.setR(o.r());
			value.setI(o.i());
			value.setJ(o.j());
			value.setK(o.k());
			storage.set(i,value);
		}
	}

	@Override
	public String toString() {
		QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (long r = 0; r < rows; r++) {
			builder.append('[');
			for (long c = 0; c < cols; c++) {
				if (c != 0)
					builder.append(',');
				v(r, c, tmp);
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
		MatrixReshape.compute(G.QHP_MAT, G.QHP, rows, cols, this);
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
		return 1;
	}
	
	private static ThreadLocal<QuaternionHighPrecisionMember> tmpQuat =
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
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			long c = index.get(0);
			long r = index.get(1);
			v(r, c, tmp);
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
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 3) return 0;
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
		v(index.get(1),index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			v(index.get(1), index.get(0), tmp);
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
}
