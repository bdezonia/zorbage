/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data.float64.complex;

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
public final class ComplexFloat64MatrixMember
	implements
		MatrixMember<ComplexFloat64Member>,
		Gettable<ComplexFloat64MatrixMember>,
		Settable<ComplexFloat64MatrixMember>,
		PrimitiveConversion
// TODO: UniversalRepresentation
{
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	
	private LinearStorage<?,ComplexFloat64Member> storage;
	private long rows;
	private long cols;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public ComplexFloat64MatrixMember() {
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		init(0,0);
	}
	
	public ComplexFloat64MatrixMember(ComplexFloat64MatrixMember other) {
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	public ComplexFloat64MatrixMember(int r, int c, double[] vals) {
		if (vals.length*2 != r*c)
			throw new IllegalArgumentException("input values do not match declared shape");
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		init(r,c);
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		int compCount = vals.length / 2;
		for (int i = 0; i < compCount; i++) {
			tmp.setR(vals[2*i]);
			tmp.setI(vals[2*i+1]);
			storage.set(i, tmp);
		}
	}

	public ComplexFloat64MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		this.s = StorageConstruction.ARRAY;
		init(dimensions[1],dimensions[0]);
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			storage.set(i, tmp);
		}
	}
	
	public ComplexFloat64MatrixMember(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
		rows = -1;
		cols = -1;
		this.m = m;
		this.s = s;
		init(d2, d1);
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
			storage = new ArrayStorageFloat64<ComplexFloat64Member>(r*c, new ComplexFloat64Member());
		else
			storage = new FileStorageFloat64<ComplexFloat64Member>(r*c, new ComplexFloat64Member());
	}
	
	@Override
	public void v(long r, long c, ComplexFloat64Member value) {
		long index = r * cols + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, ComplexFloat64Member value) {
		long index = r * cols + c;
		storage.set(index, value);
	}

	@Override
	public void set(ComplexFloat64MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		// TODO: do we keep our mem/store strategies or adopt the other's
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(ComplexFloat64MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		// TODO: do we keep our mem/store strategies or adopt the other's
		other.m = m;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public String toString() {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
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
		if (this.rows == rows && this.cols == cols) return;
		LinearStorage<?, ComplexFloat64Member> orig = storage;
		init(rows, cols);
		// TODO: look at vectorMember. copy overlapping data.
		// set other to zero.
		throw new IllegalArgumentException("not implemented yet");
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return cols;
		if (d == 1) return rows;
		return 1;
	}
	
	private static ThreadLocal<ComplexFloat64Member> tmpComp =
			new ThreadLocal<ComplexFloat64Member>()
	{
		protected ComplexFloat64Member initialValue() {
			return new ComplexFloat64Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v);
		else
			tmpComp.get().setI(v);
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v);
		else
			tmpComp.get().setI(v);
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v);
		else
			tmpComp.get().setI(v);
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v);
		else
			tmpComp.get().setI(v);
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v);
		else
			tmpComp.get().setI(v);
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v);
		else
			tmpComp.get().setI(v);
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v.doubleValue());
		else
			tmpComp.get().setI(v.doubleValue());
		setV(r, c, tmpComp.get());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		long c = index.get(0);
		long r = index.get(1);
		v(r, c, tmpComp.get());
		if (component == 0)
			tmpComp.get().setR(v.doubleValue());
		else
			tmpComp.get().setI(v.doubleValue());
		setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v);
			else
				tmpComp.get().setI(v);
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v);
			else
				tmpComp.get().setI(v);
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v);
			else
				tmpComp.get().setI(v);
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v);
			else
				tmpComp.get().setI(v);
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v);
			else
				tmpComp.get().setI(v);
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v);
			else
				tmpComp.get().setI(v);
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v.doubleValue());
			else
				tmpComp.get().setI(v.doubleValue());
			setV(r, c, tmpComp.get());
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
			v(r, c, tmpComp.get());
			if (component == 0)
				tmpComp.get().setR(v.doubleValue());
			else
				tmpComp.get().setI(v.doubleValue());
			setV(r, c, tmpComp.get());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return (byte) tmpComp.get().r();
		return (byte) tmpComp.get().i();
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return (short) tmpComp.get().r();
		return (short) tmpComp.get().i();
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return (int) tmpComp.get().r();
		return (int) tmpComp.get().i();
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return (long) tmpComp.get().r();
		return (long) tmpComp.get().i();
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return (float) tmpComp.get().r();
		return (float) tmpComp.get().i();
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return 0;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return tmpComp.get().r();
		return tmpComp.get().i();
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return BigInteger.ZERO;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return BigInteger.valueOf((long) tmpComp.get().r());
		return BigInteger.valueOf((long) tmpComp.get().i());
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component > 1) return BigDecimal.ZERO;
		v(index.get(1), index.get(0), tmpComp.get());
		if (component == 0) return BigDecimal.valueOf(tmpComp.get().r());
		return BigDecimal.valueOf(tmpComp.get().i());
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return (byte) tmpComp.get().r();
			return (byte) tmpComp.get().i();
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return (short) tmpComp.get().r();
			return (short) tmpComp.get().i();
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return (int) tmpComp.get().r();
			return (int) tmpComp.get().i();
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return (long) tmpComp.get().r();
			return (long) tmpComp.get().i();
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return (float) tmpComp.get().r();
			return (float) tmpComp.get().i();
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return tmpComp.get().r();
			return tmpComp.get().i();
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return BigInteger.valueOf((long) tmpComp.get().r());
			return BigInteger.valueOf((long) tmpComp.get().i());
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
			v(index.get(1), index.get(0), tmpComp.get());
			if (component == 0) return BigDecimal.valueOf(tmpComp.get().r());
			return BigDecimal.valueOf(tmpComp.get().i());
		}
	}

	@Override
	public void primitiveInit() {
		tmpComp.get().setR(0);
		tmpComp.get().setI(0);
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, tmpComp.get());
	}
}
