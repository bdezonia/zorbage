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
package zorbage.type.data;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.linear.array.ArrayStorageOctonionFloat64;
import zorbage.util.BigList;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class OctonionFloat64MatrixMember {
	
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();

	private ArrayStorageOctonionFloat64 storage;
	private long rows;
	private long cols;
	
	public OctonionFloat64MatrixMember() {
		rows = -1;
		cols = -1;
		init(0,0);
	}
	
	public OctonionFloat64MatrixMember(OctonionFloat64MatrixMember other) {
		rows = -1;
		cols = -1;
		init(other.rows(),other.cols());
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.set(i, value);
		}
	}
	
	public OctonionFloat64MatrixMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.firstMatrixValues();
		long[] dimensions = rep.dimensions();
		rows = -1;
		cols = -1;
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
	
	public OctonionFloat64MatrixMember(long d1, long d2) {
		rows = -1;
		cols = -1;
		init(d2,d1);
	}
	
	public long rows() { return rows; }

	public long cols() { return cols; }

	public void init(long r, long c) {
		if (rows != r || cols != c) {
			rows = r;
			cols = c;
		}
		
		if (r*c != storage.size()) {
			storage = new ArrayStorageOctonionFloat64(r*c);
		}
		else {
			for (long i = 0; i < storage.size(); i++) {
				storage.set(i, ZERO);
			}
		}
	}
	
	public void v(long r, long c, OctonionFloat64Member value) {
		long index = r * rows + c;
		storage.get(index, value);
	}
	
	public void setV(long r, long c, OctonionFloat64Member value) {
		long index = r * rows + c;
		storage.set(index, value);
	}
	
	public void set(OctonionFloat64MatrixMember other) {
		if (this == other) return;
		rows = other.rows;
		cols = other.cols;
		if (storage.size() != other.storage.size())
			storage = new ArrayStorageOctonionFloat64(other.storage.size());
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.set(i, value);
		}
	}
	
	public void get(OctonionFloat64MatrixMember other) {
		if (this == other) return;
		other.rows = rows;
		other.cols = cols;
		if (storage.size() != other.storage.size())
			other.storage = new ArrayStorageOctonionFloat64(storage.size());
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			other.storage.set(i, value);
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
}
