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
package nom.bdezonia.zorbage.type.octonion.highprec;

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
public final class OctonionHighPrecisionMatrixMember
	implements
		MatrixMember<OctonionHighPrecisionMember>,
		Gettable<OctonionHighPrecisionMatrixMember>,
		Settable<OctonionHighPrecisionMatrixMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionHighPrecisionMember>
{
	private static final OctonionHighPrecisionMember ZERO = new OctonionHighPrecisionMember();

	private IndexedDataSource<OctonionHighPrecisionMember> storage;
	private long rows;
	private long cols;
	private StorageConstruction s;
	
	public OctonionHighPrecisionMatrixMember() {
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(0,0);
	}
	
	public OctonionHighPrecisionMatrixMember(int r, int c, BigDecimal[] vals) {
		if (vals.length != r*c*8)
			throw new IllegalArgumentException("input values do not match declared shape");
		rows = -1;
		cols = -1;
		s = StorageConstruction.MEM_ARRAY;
		init(r,c);
		OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
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
	
	public OctonionHighPrecisionMatrixMember(OctonionHighPrecisionMatrixMember other) {
		set(other);
	}
	
	public OctonionHighPrecisionMatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		this.s = StorageConstruction.MEM_ARRAY;
		init(dimensions[1],dimensions[0]);
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
	
	public OctonionHighPrecisionMatrixMember(StorageConstruction s, long d1, long d2) {
		rows = -1;
		cols = -1;
		this.s = s;
		init(d2,d1);
	}
	
	public OctonionHighPrecisionMatrixMember(long rows, long cols) {
		this(StorageConstruction.MEM_ARRAY, cols, rows);
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
			storage = Storage.allocate(s, new OctonionHighPrecisionMember(), r*c);
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
	public void getV(long r, long c, OctonionHighPrecisionMember value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, OctonionHighPrecisionMember value) {
		if (r < 0 || r >= rows || c < 0 || c >= cols)
			throw new IllegalArgumentException("matrix oob access");
		long index = r * cols + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(OctonionHighPrecisionMatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(OctonionHighPrecisionMatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		OctonionHighPrecisionMember value = new OctonionHighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.r();
			BigDecimal im = value.i();
			BigDecimal j = value.j();
			BigDecimal k = value.k();
			BigDecimal l = value.l();
			BigDecimal i0 = value.i0();
			BigDecimal j0 = value.j0();
			BigDecimal k0 = value.k0();
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
		OctonionHighPrecisionMember value = new OctonionHighPrecisionMember();
		BigList<OctonionRepresentation> mat = rep.getMatrix();
		alloc(rep.getMatrixRowDim(), rep.getMatrixColDim());
		long matSize = mat.size();
		for (long i = 0; i < matSize; i++) {
			OctonionRepresentation o = mat.get(i);
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
	public String toString() {
		OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
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
		MatrixReshape.compute(G.OHP_MAT, G.OHP, rows, cols, this);
	}
	
	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
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
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
		setV(r, c, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long c = index.get(0);
		long r = index.get(1);
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(r, c, tmp);
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
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		OctonionHighPrecisionMember tmp = tmpOct.get();
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
		getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
			OctonionHighPrecisionMember tmp = tmpOct.get();
			getV(index.get(1), index.get(0), tmp);
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
		if (o instanceof OctonionHighPrecisionMatrixMember) {
			return G.OHP_MAT.isEqual().call(this, (OctonionHighPrecisionMatrixMember) o);
		}
		return false;
	}
}
