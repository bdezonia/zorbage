/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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

import zorbage.type.storage.ArrayStorageOctonionFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class OctonionFloat64MatrixMember {
	
	private ArrayStorageOctonionFloat64 storage;
	private int rows;
	private int cols;
	
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
			storage.put(i, value);
		}
	}
	
	public OctonionFloat64MatrixMember(String s) {
		throw new IllegalArgumentException("TODO");
	}
	
	public int rows() { return rows; }

	public int cols() { return cols; }

	public void init(int r, int c) {
		if (rows != r || cols != c) {
			rows = r;
			cols = c;
		}
		
		if (((long)r)*c != storage.size()) {
			storage = new ArrayStorageOctonionFloat64(((long)r)*c);
		}
		else {
			OctonionFloat64Member zero = new OctonionFloat64Member();
			for (long i = 0; i < storage.size(); i++) {
				storage.put(i, zero);
			}
		}
	}
	
	void v(int r, int c, OctonionFloat64Member value) {
		long index = ((long)r) * rows + c;
		storage.get(index, value);
	}
	
	void setV(int r, int c, OctonionFloat64Member value) {
		long index = ((long)r) * rows + c;
		storage.put(index, value);
	}
	
	@Override
	public String toString() {
		throw new IllegalArgumentException("TODO");
	}
}
