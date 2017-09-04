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
package zorbage.type.data;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.linear.array.ArrayStorageComplexFloat64;
import zorbage.util.BigList;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class ComplexFloat64VectorMember {

	private static final ComplexFloat64Group g = new ComplexFloat64Group();
	//private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0); 

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
			storage.set(i,  value);
		}
	}
	
	public ComplexFloat64VectorMember(ComplexFloat64VectorMember other) {
		storage = new ArrayStorageComplexFloat64(other.storage.size());
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.set(i,  value);
		}
	}
	
	public ComplexFloat64VectorMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		storage = new ArrayStorageComplexFloat64(data.size());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			storage.set(i, tmp);
		}
	}

	public ComplexFloat64VectorMember(long d1) {
		storage = new ArrayStorageComplexFloat64(d1);
	}
	
	public void v(long i, ComplexFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			g.zero(v);
		}
	}

	public void setV(long i, ComplexFloat64Member v) {
		storage.set(i, v);
	}
	
	public void set(ComplexFloat64VectorMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			storage = new ArrayStorageComplexFloat64(other.storage.size());
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.set(i, value);
		}
	}
	
	public void get(ComplexFloat64VectorMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			other.storage = new ArrayStorageComplexFloat64(storage.size());
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			other.storage.set(i, value);
		}
	}

	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		for (long i = 0; i < storage.size(); i++) {
			if (i != 0)
				builder.append(',');
			v(i, tmp);
			builder.append(tmp.toString());
		}
		builder.append(']');
		return builder.toString();
	}

}
