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
import zorbage.type.storage.ArrayStorageOctonionFloat64;
import zorbage.type.storage.ArrayStorageQuaternionFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionFloat64RModuleMember {

	private static final QuaternionFloat64SkewField g = new QuaternionFloat64SkewField();
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(); 

	private ArrayStorageQuaternionFloat64 storage;
	
	public QuaternionFloat64RModuleMember() {
		storage = new ArrayStorageQuaternionFloat64(0);
	}
	
	public QuaternionFloat64RModuleMember(double[] vals) {
		final int count = vals.length / 4;
		storage = new ArrayStorageQuaternionFloat64(count);
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (int i = 0; i < count; i++) {
			final int index = 4*i;
			value.setR(vals[index]);
			value.setI(vals[index + 1]);
			value.setJ(vals[index + 2]);
			value.setK(vals[index + 3]);
			storage.put(i,  value);
		}
	}
	
	public QuaternionFloat64RModuleMember(QuaternionFloat64RModuleMember other) {
		storage = new ArrayStorageQuaternionFloat64(other.storage.size());
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.put(i,  value);
		}
	}
	
	public QuaternionFloat64RModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		List<OctonionRepresentation> data = rep.firstVectorValues();
		storage = new ArrayStorageQuaternionFloat64(data.size());
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (int i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setR(val.r().doubleValue());
			tmp.setI(val.i().doubleValue());
			tmp.setJ(val.j().doubleValue());
			tmp.setK(val.k().doubleValue());
			storage.put(i, tmp);
		}
	}

	public void v(int i, QuaternionFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			g.zero(v);
		}
	}

	public void setV(int i, QuaternionFloat64Member v) {
		if (i >= storage.size()) {
			if (g.isEqual(v, ZERO)) return;
			ArrayStorageQuaternionFloat64 tmp = storage;
			storage = new ArrayStorageQuaternionFloat64(i+1);
			QuaternionFloat64Member value = new QuaternionFloat64Member();
			for (long j = 0; j < tmp.size(); j++) {
				tmp.get(j, value);
				storage.put(j, value);
			}
		}
		storage.put(i, v);
	}
	
	public void set(QuaternionFloat64RModuleMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			storage = new ArrayStorageQuaternionFloat64(other.storage.size());
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, value);
			storage.put(i, value);
		}
	}
	
	public void get(QuaternionFloat64RModuleMember other) {
		if (this == other) return;
		if (storage.size() != other.storage.size())
			other.storage = new ArrayStorageQuaternionFloat64(storage.size());
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			other.storage.put(i, value);
		}
	}

	public int length() { return (int) storage.size(); }
	
	@Override
	public String toString() { throw new IllegalArgumentException("Not yet implemented"); }

}
