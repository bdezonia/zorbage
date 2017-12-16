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

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
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
public final class QuaternionFloat64RModuleMember
	implements
		RModuleMember<QuaternionFloat64Member>,
		Gettable<QuaternionFloat64RModuleMember>,
		Settable<QuaternionFloat64RModuleMember>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member(); 

	private LinearStorage<?,QuaternionFloat64Member> storage;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public QuaternionFloat64RModuleMember() {
		storage = new ArrayStorageFloat64<QuaternionFloat64Member>(0, new QuaternionFloat64Member());
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
	}
	
	public QuaternionFloat64RModuleMember(double[] vals) {
		final int count = vals.length / 4;
		storage = new ArrayStorageFloat64<QuaternionFloat64Member>(count, new QuaternionFloat64Member());
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (int i = 0; i < count; i++) {
			final int index = 4*i;
			value.setR(vals[index]);
			value.setI(vals[index + 1]);
			value.setJ(vals[index + 2]);
			value.setK(vals[index + 3]);
			storage.set(i,  value);
		}
	}
	
	public QuaternionFloat64RModuleMember(QuaternionFloat64RModuleMember other) {
		storage = other.storage.duplicate();
		m = other.m;
		s = other.s;
	}
	
	public QuaternionFloat64RModuleMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		BigList<OctonionRepresentation> data = rep.firstVectorValues();
		storage = new ArrayStorageFloat64<QuaternionFloat64Member>(data.size(), new QuaternionFloat64Member());
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
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

	public QuaternionFloat64RModuleMember(MemoryConstruction m, StorageConstruction s, long d1) {
		this.m = m;
		this.s = s;
		if (s == StorageConstruction.ARRAY)
			storage = new ArrayStorageFloat64<QuaternionFloat64Member>(d1, new QuaternionFloat64Member());
		else
			storage = new FileStorageFloat64<QuaternionFloat64Member>(d1, new QuaternionFloat64Member());
	}
	
	@Override
	public void v(long i, QuaternionFloat64Member v) {
		if (i < storage.size()) {
			storage.get(i, v);
		}
		else {
			G.QDBL.zero(v);
		}
	}

	@Override
	public void setV(long i, QuaternionFloat64Member v) {
		storage.set(i, v);
	}
	
	@Override
	public void set(QuaternionFloat64RModuleMember other) {
		if (this == other) return;
		storage = other.storage.duplicate();
		m = other.m;
		s = other.s;
	}
	
	@Override
	public void get(QuaternionFloat64RModuleMember other) {
		if (this == other) return;
		other.storage = storage.duplicate();
		other.m = m;
		other.s = s;
	}

	@Override
	public long length() { return storage.size(); }
	
	@Override
	public String toString() {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
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

	@Override
	public void init(long size) {
		if (storage == null || storage.size() != size) {
			if (s == StorageConstruction.ARRAY)
				storage = new ArrayStorageFloat64<QuaternionFloat64Member>(size, new QuaternionFloat64Member());
			else
				storage = new FileStorageFloat64<QuaternionFloat64Member>(size, new QuaternionFloat64Member());
		}
		else {
			for (long i = 0; i < storage.size(); i++) {
				storage.set(i, ZERO);
			}
		}
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public void reshape(long len) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("implement me");
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d == 0) return storage.size();
		return 1;
	}
}
