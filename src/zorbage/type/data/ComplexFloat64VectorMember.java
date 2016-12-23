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

import java.util.List;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.ArrayStorageComplexFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class ComplexFloat64VectorMember {

	private static final ComplexFloat64Field g = new ComplexFloat64Field();
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0); 

	private ArrayStorageComplexFloat64 storage;
	
	public ComplexFloat64VectorMember() {
		storage = new ArrayStorageComplexFloat64(0);
	}
	
	public ComplexFloat64VectorMember(double[] vals) {
		final int count = vals.length / 2;
		storage = new ArrayStorageComplexFloat64(count);
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (int i = 0; i < count; i++) {
			final int index = 2*i;
			value.setR(vals[index]);
			value.setI(vals[index+1]);
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
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		List<OctonionRepresentation> data = rep.firstVectorValues();
		storage = new ArrayStorageComplexFloat64(data.size());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			storage.put(i, tmp);
		}
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
			if (g.isEqual(v, ZERO)) return;
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
	
	public void set(ComplexFloat64VectorMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			storage = new ArrayStorageComplexFloat64(other.storage.size());
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.put(i, value);
		}
	}
	
	public void get(ComplexFloat64VectorMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			other.storage = new ArrayStorageComplexFloat64(storage.size());
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			other.storage.put(i, value);
		}
	}

	public int length() { return (int) storage.size(); }
	
	@Override
	public String toString() { throw new IllegalArgumentException("Not yet implemented"); }

}
