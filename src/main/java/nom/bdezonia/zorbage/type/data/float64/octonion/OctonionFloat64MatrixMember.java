/*
 * Zorbage: an algebrSaic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.octonion;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.file.FileStorageFloat64;
import nom.bdezonia.zorbage.util.BigList;

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
		PrimitiveConversion
// TODO: UniversalRepresentation
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();

	private LinearStorage<?,OctonionFloat64Member> storage;
	private long rows;
	private long cols;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public OctonionFloat64MatrixMember() {
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		init(0,0);
	}
	
	public OctonionFloat64MatrixMember(int r, int c, double[] vals) {
		if (vals.length*8 != r*c)
			throw new IllegalArgumentException("input values do not match declared shape");
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
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
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	public OctonionFloat64MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		this.s = StorageConstruction.ARRAY;
		init(dimensions[1],dimensions[0]);
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
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
	
	public OctonionFloat64MatrixMember(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
		rows = -1;
		cols = -1;
		this.m = m;
		this.s = s;
		init(d2,d1);
	}
	
	@Override
	public long rows() { return rows; }

	@Override
	public long cols() { return cols; }

	@Override
	public void init(long r, long c) {
		if (rows != r || cols != c) {
			rows = r;
			cols = c;
		}
		if (storage != null && storage.size() == r*c) {
			for (long i = 0; i < storage.size(); i++)
				storage.set(i, ZERO);
		}
		else if (s == StorageConstruction.ARRAY)
			storage = new ArrayStorageFloat64<OctonionFloat64Member>(r*c, new OctonionFloat64Member());
		else
			storage = new FileStorageFloat64<OctonionFloat64Member>(r*c, new OctonionFloat64Member());
	}
	
	@Override
	public void v(long r, long c, OctonionFloat64Member value) {
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, OctonionFloat64Member value) {
		long index = r * cols + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(OctonionFloat64MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(OctonionFloat64MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.m = m;
		other.s = s;
		other.storage = storage.duplicate();
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
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("implement me");
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
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v);
		else if (component == 1)
			tmpOct.get().setI(v);
		else if (component == 2)
			tmpOct.get().setJ(v);
		else if (component == 3)
			tmpOct.get().setK(v);
		else if (component == 4)
			tmpOct.get().setL(v);
		else if (component == 5)
			tmpOct.get().setI0(v);
		else if (component == 6)
			tmpOct.get().setJ0(v);
		else
			tmpOct.get().setK0(v);
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v);
		else if (component == 1)
			tmpOct.get().setI(v);
		else if (component == 2)
			tmpOct.get().setJ(v);
		else if (component == 3)
			tmpOct.get().setK(v);
		else if (component == 4)
			tmpOct.get().setL(v);
		else if (component == 5)
			tmpOct.get().setI0(v);
		else if (component == 6)
			tmpOct.get().setJ0(v);
		else
			tmpOct.get().setK0(v);
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v);
		else if (component == 1)
			tmpOct.get().setI(v);
		else if (component == 2)
			tmpOct.get().setJ(v);
		else if (component == 3)
			tmpOct.get().setK(v);
		else if (component == 4)
			tmpOct.get().setL(v);
		else if (component == 5)
			tmpOct.get().setI0(v);
		else if (component == 6)
			tmpOct.get().setJ0(v);
		else
			tmpOct.get().setK0(v);
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v);
		else if (component == 1)
			tmpOct.get().setI(v);
		else if (component == 2)
			tmpOct.get().setJ(v);
		else if (component == 3)
			tmpOct.get().setK(v);
		else if (component == 4)
			tmpOct.get().setL(v);
		else if (component == 5)
			tmpOct.get().setI0(v);
		else if (component == 6)
			tmpOct.get().setJ0(v);
		else
			tmpOct.get().setK0(v);
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v);
		else if (component == 1)
			tmpOct.get().setI(v);
		else if (component == 2)
			tmpOct.get().setJ(v);
		else if (component == 3)
			tmpOct.get().setK(v);
		else if (component == 4)
			tmpOct.get().setL(v);
		else if (component == 5)
			tmpOct.get().setI0(v);
		else if (component == 6)
			tmpOct.get().setJ0(v);
		else
			tmpOct.get().setK0(v);
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v);
		else if (component == 1)
			tmpOct.get().setI(v);
		else if (component == 2)
			tmpOct.get().setJ(v);
		else if (component == 3)
			tmpOct.get().setK(v);
		else if (component == 4)
			tmpOct.get().setL(v);
		else if (component == 5)
			tmpOct.get().setI0(v);
		else if (component == 6)
			tmpOct.get().setJ0(v);
		else
			tmpOct.get().setK0(v);
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v.doubleValue());
		else if (component == 1)
			tmpOct.get().setI(v.doubleValue());
		else if (component == 2)
			tmpOct.get().setJ(v.doubleValue());
		else if (component == 3)
			tmpOct.get().setK(v.doubleValue());
		else if (component == 4)
			tmpOct.get().setL(v.doubleValue());
		else if (component == 5)
			tmpOct.get().setI0(v.doubleValue());
		else if (component == 6)
			tmpOct.get().setJ0(v.doubleValue());
		else
			tmpOct.get().setK0(v.doubleValue());
		setV(r, c, tmpOct.get());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpOct.get());
		if (component == 0)
			tmpOct.get().setR(v.doubleValue());
		else if (component == 1)
			tmpOct.get().setI(v.doubleValue());
		else if (component == 2)
			tmpOct.get().setJ(v.doubleValue());
		else if (component == 3)
			tmpOct.get().setK(v.doubleValue());
		else if (component == 4)
			tmpOct.get().setL(v.doubleValue());
		else if (component == 5)
			tmpOct.get().setI0(v.doubleValue());
		else if (component == 6)
			tmpOct.get().setJ0(v.doubleValue());
		else
			tmpOct.get().setK0(v.doubleValue());
		setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v);
			else if (component == 1)
				tmpOct.get().setI(v);
			else if (component == 2)
				tmpOct.get().setJ(v);
			else if (component == 3)
				tmpOct.get().setK(v);
			else if (component == 4)
				tmpOct.get().setL(v);
			else if (component == 5)
				tmpOct.get().setI0(v);
			else if (component == 6)
				tmpOct.get().setJ0(v);
			else
				tmpOct.get().setK0(v);
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v);
			else if (component == 1)
				tmpOct.get().setI(v);
			else if (component == 2)
				tmpOct.get().setJ(v);
			else if (component == 3)
				tmpOct.get().setK(v);
			else if (component == 4)
				tmpOct.get().setL(v);
			else if (component == 5)
				tmpOct.get().setI0(v);
			else if (component == 6)
				tmpOct.get().setJ0(v);
			else
				tmpOct.get().setK0(v);
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v);
			else if (component == 1)
				tmpOct.get().setI(v);
			else if (component == 2)
				tmpOct.get().setJ(v);
			else if (component == 3)
				tmpOct.get().setK(v);
			else if (component == 4)
				tmpOct.get().setL(v);
			else if (component == 5)
				tmpOct.get().setI0(v);
			else if (component == 6)
				tmpOct.get().setJ0(v);
			else
				tmpOct.get().setK0(v);
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v);
			else if (component == 1)
				tmpOct.get().setI(v);
			else if (component == 2)
				tmpOct.get().setJ(v);
			else if (component == 3)
				tmpOct.get().setK(v);
			else if (component == 4)
				tmpOct.get().setL(v);
			else if (component == 5)
				tmpOct.get().setI0(v);
			else if (component == 6)
				tmpOct.get().setJ0(v);
			else
				tmpOct.get().setK0(v);
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v);
			else if (component == 1)
				tmpOct.get().setI(v);
			else if (component == 2)
				tmpOct.get().setJ(v);
			else if (component == 3)
				tmpOct.get().setK(v);
			else if (component == 4)
				tmpOct.get().setL(v);
			else if (component == 5)
				tmpOct.get().setI0(v);
			else if (component == 6)
				tmpOct.get().setJ0(v);
			else
				tmpOct.get().setK0(v);
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v);
			else if (component == 1)
				tmpOct.get().setI(v);
			else if (component == 2)
				tmpOct.get().setJ(v);
			else if (component == 3)
				tmpOct.get().setK(v);
			else if (component == 4)
				tmpOct.get().setL(v);
			else if (component == 5)
				tmpOct.get().setI0(v);
			else if (component == 6)
				tmpOct.get().setJ0(v);
			else
				tmpOct.get().setK0(v);
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v.doubleValue());
			else if (component == 1)
				tmpOct.get().setI(v.doubleValue());
			else if (component == 2)
				tmpOct.get().setJ(v.doubleValue());
			else if (component == 3)
				tmpOct.get().setK(v.doubleValue());
			else if (component == 4)
				tmpOct.get().setL(v.doubleValue());
			else if (component == 5)
				tmpOct.get().setI0(v.doubleValue());
			else if (component == 6)
				tmpOct.get().setJ0(v.doubleValue());
			else
				tmpOct.get().setK0(v.doubleValue());
			setV(r, c, tmpOct.get());
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
			v(r, c, tmpOct.get());
			if (component == 0)
				tmpOct.get().setR(v.doubleValue());
			else if (component == 1)
				tmpOct.get().setI(v.doubleValue());
			else if (component == 2)
				tmpOct.get().setJ(v.doubleValue());
			else if (component == 3)
				tmpOct.get().setK(v.doubleValue());
			else if (component == 4)
				tmpOct.get().setL(v.doubleValue());
			else if (component == 5)
				tmpOct.get().setI0(v.doubleValue());
			else if (component == 6)
				tmpOct.get().setJ0(v.doubleValue());
			else
				tmpOct.get().setK0(v.doubleValue());
			setV(r, c, tmpOct.get());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return (byte) tmpOct.get().r();
		if (component == 1) return (byte) tmpOct.get().i();
		if (component == 2) return (byte) tmpOct.get().j();
		if (component == 3) return (byte) tmpOct.get().k();
		if (component == 4) return (byte) tmpOct.get().l();
		if (component == 5) return (byte) tmpOct.get().i0();
		if (component == 6) return (byte) tmpOct.get().j0();
		return (byte) tmpOct.get().k0();
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return (short) tmpOct.get().r();
		if (component == 1) return (short) tmpOct.get().i();
		if (component == 2) return (short) tmpOct.get().j();
		if (component == 3) return (short) tmpOct.get().k();
		if (component == 4) return (short) tmpOct.get().l();
		if (component == 5) return (short) tmpOct.get().i0();
		if (component == 6) return (short) tmpOct.get().j0();
		return (short) tmpOct.get().k0();
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return (int) tmpOct.get().r();
		if (component == 1) return (int) tmpOct.get().i();
		if (component == 2) return (int) tmpOct.get().j();
		if (component == 3) return (int) tmpOct.get().k();
		if (component == 4) return (int) tmpOct.get().l();
		if (component == 5) return (int) tmpOct.get().i0();
		if (component == 6) return (int) tmpOct.get().j0();
		return (int) tmpOct.get().k0();
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return (long) tmpOct.get().r();
		if (component == 1) return (long) tmpOct.get().i();
		if (component == 2) return (long) tmpOct.get().j();
		if (component == 3) return (long) tmpOct.get().k();
		if (component == 4) return (long) tmpOct.get().l();
		if (component == 5) return (long) tmpOct.get().i0();
		if (component == 6) return (long) tmpOct.get().j0();
		return (long) tmpOct.get().k0();
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return (float) tmpOct.get().r();
		if (component == 1) return (float) tmpOct.get().i();
		if (component == 2) return (float) tmpOct.get().j();
		if (component == 3) return (float) tmpOct.get().k();
		if (component == 4) return (float) tmpOct.get().l();
		if (component == 5) return (float) tmpOct.get().i0();
		if (component == 6) return (float) tmpOct.get().j0();
		return (float) tmpOct.get().k0();
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return 0;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return tmpOct.get().r();
		if (component == 1) return tmpOct.get().i();
		if (component == 2) return tmpOct.get().j();
		if (component == 3) return tmpOct.get().k();
		if (component == 4) return tmpOct.get().l();
		if (component == 5) return tmpOct.get().i0();
		if (component == 6) return tmpOct.get().j0();
		return tmpOct.get().k0();
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigInteger.ZERO;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return BigInteger.valueOf((long) tmpOct.get().r());
		if (component == 1) return BigInteger.valueOf((long) tmpOct.get().i());
		if (component == 2) return BigInteger.valueOf((long) tmpOct.get().j());
		if (component == 3) return BigInteger.valueOf((long) tmpOct.get().k());
		if (component == 4) return BigInteger.valueOf((long) tmpOct.get().l());
		if (component == 5) return BigInteger.valueOf((long) tmpOct.get().i0());
		if (component == 6) return BigInteger.valueOf((long) tmpOct.get().j0());
		return BigInteger.valueOf((long) tmpOct.get().k0());
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 7) return BigDecimal.ZERO;
		v(index.get(1), index.get(0), tmpOct.get());
		if (component == 0) return BigDecimal.valueOf(tmpOct.get().r());
		if (component == 1) return BigDecimal.valueOf(tmpOct.get().i());
		if (component == 2) return BigDecimal.valueOf(tmpOct.get().j());
		if (component == 3) return BigDecimal.valueOf(tmpOct.get().k());
		if (component == 4) return BigDecimal.valueOf(tmpOct.get().l());
		if (component == 5) return BigDecimal.valueOf(tmpOct.get().i0());
		if (component == 6) return BigDecimal.valueOf(tmpOct.get().j0());
		return BigDecimal.valueOf(tmpOct.get().k0());
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return (byte) tmpOct.get().r();
			if (component == 1) return (byte) tmpOct.get().i();
			if (component == 2) return (byte) tmpOct.get().j();
			if (component == 3) return (byte) tmpOct.get().k();
			if (component == 4) return (byte) tmpOct.get().l();
			if (component == 5) return (byte) tmpOct.get().i0();
			if (component == 6) return (byte) tmpOct.get().j0();
			return (byte) tmpOct.get().k0();
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return (short) tmpOct.get().r();
			if (component == 1) return (short) tmpOct.get().i();
			if (component == 2) return (short) tmpOct.get().j();
			if (component == 3) return (short) tmpOct.get().k();
			if (component == 4) return (short) tmpOct.get().l();
			if (component == 5) return (short) tmpOct.get().i0();
			if (component == 6) return (short) tmpOct.get().j0();
			return (short) tmpOct.get().k0();
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return (int) tmpOct.get().r();
			if (component == 1) return (int) tmpOct.get().i();
			if (component == 2) return (int) tmpOct.get().j();
			if (component == 3) return (int) tmpOct.get().k();
			if (component == 4) return (int) tmpOct.get().l();
			if (component == 5) return (int) tmpOct.get().i0();
			if (component == 6) return (int) tmpOct.get().j0();
			return (int) tmpOct.get().k0();
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return (long) tmpOct.get().r();
			if (component == 1) return (long) tmpOct.get().i();
			if (component == 2) return (long) tmpOct.get().j();
			if (component == 3) return (long) tmpOct.get().k();
			if (component == 4) return (long) tmpOct.get().l();
			if (component == 5) return (long) tmpOct.get().i0();
			if (component == 6) return (long) tmpOct.get().j0();
			return (long) tmpOct.get().k0();
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return (float) tmpOct.get().r();
			if (component == 1) return (float) tmpOct.get().i();
			if (component == 2) return (float) tmpOct.get().j();
			if (component == 3) return (float) tmpOct.get().k();
			if (component == 4) return (float) tmpOct.get().l();
			if (component == 5) return (float) tmpOct.get().i0();
			if (component == 6) return (float) tmpOct.get().j0();
			return (float) tmpOct.get().k0();
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return tmpOct.get().r();
			if (component == 1) return tmpOct.get().i();
			if (component == 2) return tmpOct.get().j();
			if (component == 3) return tmpOct.get().k();
			if (component == 4) return tmpOct.get().l();
			if (component == 5) return tmpOct.get().i0();
			if (component == 6) return tmpOct.get().j0();
			return tmpOct.get().k0();
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return BigInteger.valueOf((long) tmpOct.get().r());
			if (component == 1) return BigInteger.valueOf((long) tmpOct.get().i());
			if (component == 2) return BigInteger.valueOf((long) tmpOct.get().j());
			if (component == 3) return BigInteger.valueOf((long) tmpOct.get().k());
			if (component == 4) return BigInteger.valueOf((long) tmpOct.get().l());
			if (component == 5) return BigInteger.valueOf((long) tmpOct.get().i0());
			if (component == 6) return BigInteger.valueOf((long) tmpOct.get().j0());
			return BigInteger.valueOf((long) tmpOct.get().k0());
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
			v(index.get(1), index.get(0), tmpOct.get());
			if (component == 0) return BigDecimal.valueOf(tmpOct.get().r());
			if (component == 1) return BigDecimal.valueOf(tmpOct.get().i());
			if (component == 2) return BigDecimal.valueOf(tmpOct.get().j());
			if (component == 3) return BigDecimal.valueOf(tmpOct.get().k());
			if (component == 4) return BigDecimal.valueOf(tmpOct.get().l());
			if (component == 5) return BigDecimal.valueOf(tmpOct.get().i0());
			if (component == 6) return BigDecimal.valueOf(tmpOct.get().j0());
			return BigDecimal.valueOf(tmpOct.get().k0());
		}
	}

	@Override
	public void primitiveInit() {
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, ZERO);
	}
}
