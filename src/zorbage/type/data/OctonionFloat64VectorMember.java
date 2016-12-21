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
public final class OctonionFloat64VectorMember {

	private static final OctonionFloat64SkewField g = new OctonionFloat64SkewField();
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member(); 

	private ArrayStorageOctonionFloat64 storage;
	
	public OctonionFloat64VectorMember() {
		storage = new ArrayStorageOctonionFloat64(0);
	}
	
	public OctonionFloat64VectorMember(double[] vals) {
		storage = new ArrayStorageOctonionFloat64(vals.length/8);
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (int i = 0; i < vals.length/8; i++) {
			value.setR(vals[8*i]);
			value.setI(vals[8*i + 1]);
			value.setJ(vals[8*i + 2]);
			value.setK(vals[8*i + 3]);
			value.setL(vals[8*i + 4]);
			value.setI0(vals[8*i + 5]);
			value.setJ0(vals[8*i + 6]);
			value.setK0(vals[8*i + 7]);
			storage.put(i,  value);
		}
	}
	
	public OctonionFloat64VectorMember(OctonionFloat64VectorMember other) {
		storage = new ArrayStorageOctonionFloat64(other.storage.size());
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.put(i,  value);
		}
	}
	
	public OctonionFloat64VectorMember(String value) {
		throw new IllegalArgumentException("Not yet implemented");
	}

	public void v(int i, OctonionFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			g.zero(v);
		}
	}

	public void setV(int i, OctonionFloat64Member v) {
		if (i >= storage.size()) {
			if (g.isEqual(v, ZERO)) return;
			ArrayStorageOctonionFloat64 tmp = storage;
			storage = new ArrayStorageOctonionFloat64(i+1);
			OctonionFloat64Member value = new OctonionFloat64Member();
			for (long j = 0; j < tmp.size(); j++) {
				tmp.get(j, value);
				storage.put(j, value);
			}
		}
		storage.put(i, v);
	}
	
	public void set(OctonionFloat64VectorMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			storage = new ArrayStorageOctonionFloat64(other.storage.size());
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.put(i, value);
		}
	}
	
	public void get(OctonionFloat64VectorMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			other.storage = new ArrayStorageOctonionFloat64(storage.size());
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			other.storage.put(i, value);
		}
	}

	public int length() { return (int) storage.size(); }
	
	@Override
	public String toString() { throw new IllegalArgumentException("Not yet implemented"); }

}
