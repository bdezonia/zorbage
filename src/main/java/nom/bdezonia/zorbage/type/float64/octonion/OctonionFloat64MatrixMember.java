/*
 * Zorbage: an algebrSaic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.float64.octonion;

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
public final class OctonionFloat64MatrixMember
	implements
		MatrixMember<OctonionFloat64Member>,
		Gettable<OctonionFloat64MatrixMember>,
		Settable<OctonionFloat64MatrixMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionFloat64Member>
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();

	private IndexedDataSource<OctonionFloat64Member> storage;
	private long rows;
	private long cols;
	private StorageConstruction s;
	
	public OctonionFloat64MatrixMember() {
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(0,0);
	}
	
	public OctonionFloat64MatrixMember(int r, int c, double[] vals) {
		if (vals.length != r*c*8)
			throw new IllegalArgumentException("input values do not match declared shape");
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(r,c);
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		int octCount = vals.length / 8;
		for (int i = 0; i < octCount; i++) {
			tmp.setR(vals[8*i]);
			tmp.setI(vals[8*i+1]);
			tmp.setJ(vals[8*i+2]);
			tmp.setK(vals[8*i+3]);
			tmp.setL(vals[8*i+4]);
			tmp.setI0(vals[8*i+5]);
			tmp.setJ0(vals[8*i+6]);
			tmp.setK0(vals[8*i+7]);
			storage.set(i, tmp);
		}
	}
	
	public OctonionFloat64MatrixMember(OctonionFloat64MatrixMember other) {
		set(other);
	}
	
	public OctonionFloat64MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		this.s = StorageConstruction.MEM_ARRAY;
		init(dimensions[1],dimensions[0]);
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
	
	public OctonionFloat64MatrixMember(StorageConstruction s, long d1, long d2) {
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
			storage = Storage.allocate(s, r*c, new OctonionFloat64Member());
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
	public void v(long r, long c, OctonionFloat64Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, OctonionFloat64Member value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(OctonionFloat64MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(OctonionFloat64MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = BigDecimal.valueOf(value.r());
			BigDecimal im = BigDecimal.valueOf(value.i());
			BigDecimal j = BigDecimal.valueOf(value.j());
			BigDecimal k = BigDecimal.valueOf(value.k());
			BigDecimal l = BigDecimal.valueOf(value.l());
			BigDecimal i0 = BigDecimal.valueOf(value.i0());
			BigDecimal j0 = BigDecimal.valueOf(value.j0());
			BigDecimal k0 = BigDecimal.valueOf(value.k0());
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(im);
			o.setJ(j);
			o.setK(k);
			o.setL(l);
			o.setI0(i0);
			o.setJ0(j0);
			o.setK0(k0);
		}
		rep.setMatrix(rows, cols, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> mat = rep.getMatrix();
		alloc(rep.getMatrixRowDim(), rep.getMatrixColDim());
		long matSize = mat.size();
		for (long i = 0; i < matSize; i++) {
			OctonionRepresentation o = mat.get(i);
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
	public String toString() {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
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
		MatrixReshape.compute(G.ODBL_MAT, G.ODBL, rows, cols, this);
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
		return 1;
	}

	private static ThreadLocal<OctonionFloat64Member> tmpOct =
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
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionFloat64Member tmp = tmpOct.get();
		v(r, c, tmp);
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
		setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
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
					if (index.get(0) >= cols) {
						oob = true;
						break;
					}
				}
				else if (i == 1) {
					if (index.get(0) >= rows) {
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(r, c, tmp);
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
			setV(r, c, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionFloat64Member tmp = tmpOct.get();
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
		v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
			OctonionFloat64Member tmp = tmpOct.get();
			v(index.get(1), index.get(0), tmp);
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
		if (this == o)
			return true;
		if (o instanceof OctonionFloat64MatrixMember) {
			return G.ODBL_MAT.isEqual().call(this, (OctonionFloat64MatrixMember) o);
		}
		return false;
	}
}