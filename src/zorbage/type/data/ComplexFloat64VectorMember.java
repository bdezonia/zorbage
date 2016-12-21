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

import zorbage.type.storage.ArrayStorageComplexFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class ComplexFloat64VectorMember {

	private ArrayStorageComplexFloat64 storage;
	private static final ComplexFloat64Field g = new ComplexFloat64Field();
	private static final ComplexFloat64Member zero = new ComplexFloat64Member(0); 
	
	public ComplexFloat64VectorMember() {
		storage = new ArrayStorageComplexFloat64(0);
	}
	
	public ComplexFloat64VectorMember(double[] rvals, double[] ivals) {
		if (rvals.length != ivals.length)
			throw new IllegalArgumentException("unmatched input lengths to vector constructor");
		storage = new ArrayStorageComplexFloat64(rvals.length);
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (int i = 0; i < rvals.length; i++) {
			storage.get(i, value);
			value.setR(rvals[i]);
			value.setI(ivals[i]);
			storage.put(i,  value);
		}
	}
	
	public ComplexFloat64VectorMember(ComplexFloat64VectorMember other) {
		storage = new ArrayStorageComplexFloat64(other.storage.size());
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.put(i,  value);
		}
	}
	
	public ComplexFloat64VectorMember(String value) {
		throw new IllegalArgumentException("Not yet implemented");
	}

	public void v(int i, ComplexFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			g.zero(v);
		}
	}

	public void setV(int i, ComplexFloat64Member v) {
		if (i >= storage.size()) {
			if (g.isEqual(v, zero)) return;
			ArrayStorageComplexFloat64 tmp = storage;
			storage = new ArrayStorageComplexFloat64(i+1);
			ComplexFloat64Member value = new ComplexFloat64Member();
			for (long j = 0; j < tmp.size(); j++) {
				tmp.get(j, value);
				storage.put(j, value);
			}
		}
		storage.put(i, v);
	}
	
	public int length() { return (int) storage.size(); }
	
	@Override
	public String toString() { throw new IllegalArgumentException("Not yet implemented"); }

}
