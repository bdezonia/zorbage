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
package zorbage.type.data.float64.real;

import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.linear.LinearStorage;
import zorbage.type.storage.linear.array.ArrayStorageFloat64;
import zorbage.type.storage.linear.file.FileStorageFloat64;
import zorbage.util.BigList;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float64MatrixMember {
	
	private static final Float64Member ZERO = new Float64Member(0);

	private LinearStorage<?,Float64Member> storage;
	private long rows;
	private long cols;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public Float64MatrixMember() {
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		init(0,0);
	}
	
	public Float64MatrixMember(Float64MatrixMember other) {
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	public Float64MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
		m = MemoryConstruction.DENSE;
		this.s = StorageConstruction.ARRAY;
		init(dimensions[1],dimensions[0]);
		Float64Member tmp = new Float64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r().doubleValue());
			storage.set(i, tmp);
		}
	}
	
	public Float64MatrixMember(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
		rows = -1;
		cols = -1;
		this.m = m;
		this.s = s;
		init(d2, d1);
	}
	
	public long rows() { return rows; }

	public long cols() { return cols; }

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
			storage = new ArrayStorageFloat64<Float64Member>(r*c, new Float64Member());
		else
			storage = new FileStorageFloat64<Float64Member>(r*c, new Float64Member());
	}
	
	public void v(long r, long c, Float64Member value) {
		long index = r * rows + c;
		storage.get(index, value);
	}
	
	public void setV(long r, long c, Float64Member value) {
		long index = r * rows + c;
		storage.set(index, value);
	}
	
	
	public void set(Float64MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		m = other.m;
		s = other.s;
		storage = other.storage.duplicate();
	}
	
	public void get(Float64MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		other.m = m;
		other.s = s;
		other.storage = storage.duplicate();
	}

	@Override
	public String toString() {
		Float64Member tmp = new Float64Member();
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
}
