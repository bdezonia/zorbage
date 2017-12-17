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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
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
public final class QuaternionFloat64MatrixMember
	implements
		MatrixMember<QuaternionFloat64Member>,
		Gettable<QuaternionFloat64MatrixMember>,
		Settable<QuaternionFloat64MatrixMember>
// TODO: UniversalRepresentation
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member();

	private LinearStorage<?,QuaternionFloat64Member> storage;
	private long rows;
	private long cols;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public QuaternionFloat64MatrixMember() {
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		init(0,0);
	}
	
	public QuaternionFloat64MatrixMember(QuaternionFloat64MatrixMember other) {
		storage = other.storage.duplicate();
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
	}
	
	public QuaternionFloat64MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		this.s = StorageConstruction.ARRAY;
		init(dimensions[1], dimensions[0]);
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			tmp.setJ(val.j().doubleValue());
			tmp.setK(val.k().doubleValue());
			storage.set(i, tmp);
		}
	}
	
	public QuaternionFloat64MatrixMember(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
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
			storage = new ArrayStorageFloat64<QuaternionFloat64Member>(r*c, new QuaternionFloat64Member());
		else
			storage = new FileStorageFloat64<QuaternionFloat64Member>(r*c, new QuaternionFloat64Member());
	}
	
	@Override
	public void v(long r, long c, QuaternionFloat64Member value) {
		long index = r * rows + c;
		storage.get(index, value);
	}
	
	@Override
	public void setV(long r, long c, QuaternionFloat64Member value) {
		long index = r * rows + c;
		storage.set(index, value);
	}
	
	@Override
	public void set(QuaternionFloat64MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	@Override
	public void get(QuaternionFloat64MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.m = m;
		other.s = s;
		other.storage = storage.duplicate();
	}
	
	@Override
	public String toString() {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
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
}
