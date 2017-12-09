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
package nom.bdezonia.zorbage.type.data.float64.real;

import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.file.FileStorageFloat64;
import nom.bdezonia.zorbage.util.BigList;


// rank
// dimension
// upper and lower indices

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float64TensorProductMember
	implements
		TensorMember<Float64Member>,
		Gettable<Float64TensorProductMember>,
		Settable<Float64TensorProductMember>
{

	private static final Float64Member ZERO = new Float64Member(0);

	private LinearStorage<?,Float64Member> storage;
	private long[] dims;
	private long[] multipliers;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public Float64TensorProductMember() {
		dims = new long[0];
		storage = new ArrayStorageFloat64<Float64Member>(0, new Float64Member());
		multipliers = calcMultipliers();
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
	}

	public Float64TensorProductMember(Float64TensorProductMember other) { 
		dims = other.dims.clone();
		storage = other.storage.duplicate();
		multipliers = calcMultipliers();
		m = other.m;
		s = other.s;
	}
	
	public Float64TensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.values();
		storage = new ArrayStorageFloat64<Float64Member>(data.size(), new Float64Member());
		dims = rep.dimensions().clone();
		multipliers = calcMultipliers();
		m = MemoryConstruction.DENSE;
		this.s = StorageConstruction.ARRAY;
		Float64Member tmp = new Float64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r().doubleValue());
			storage.set(i, tmp);
		}
	}
	
	public Float64TensorProductMember(MemoryConstruction m, StorageConstruction s, long[] nd) {
		dims = nd.clone();
		multipliers = calcMultipliers();
		if (s == StorageConstruction.ARRAY) {
			storage = new ArrayStorageFloat64<Float64Member>(numElems(nd), new Float64Member());
		}
		else {
			storage = new FileStorageFloat64<Float64Member>(numElems(nd), new Float64Member());
		}
	}

	@Override
	public void set(Float64TensorProductMember other) {
		if (this == other) return;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		m = other.m;
		s = other.s;
	}
	
	@Override
	public void get(Float64TensorProductMember other) {
		if (this == other) return;
		other.dims = dims.clone();
		other.multipliers = multipliers.clone();
		other.storage = storage.duplicate();
		other.m = m;
		other.s = s;
	}
	
	public long dim(int dim) {
		return dims[dim];
	}

	@Override
	public void dims(long[] d) {
		if (d.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		for (int i = 0; i < d.length; i++) {
			d[i] = dims[i];
		}
	}
	
	@Override
	public void init(long[] newDims) {
		dims = newDims.clone();
		multipliers = calcMultipliers();
		long newCount = numElems(newDims);
		if (newCount != storage.size()) {
			if (s == StorageConstruction.ARRAY) {
				storage = new ArrayStorageFloat64<Float64Member>(newCount, new Float64Member());
			}
			else {
				storage = new FileStorageFloat64<Float64Member>(newCount, new Float64Member());
			}
		}
		else {
			for (long i = 0; i < storage.size(); i++) {
				storage.set(i, ZERO);
			}
		}
	}
	
	public long numElems() {
		return storage.size();
	}
	
	public void v(long index, Float64Member value) {
		if (index < 0 || index >= storage.size())
			throw new IllegalArgumentException("invald index in tensor member");
		storage.get(index, value);
	}
	
	@Override
	public void v(long[] index, Float64Member value) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		long idx = indexToLong(index);
		storage.get(idx, value);
	}
	
	public void setV(long index, Float64Member value) {
		if (index < 0 || index >= storage.size())
			throw new IllegalArgumentException("invald index in tensor member");
		storage.set(index, value);
	}
	
	@Override
	public void setV(long[] index, Float64Member value) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		long idx = indexToLong(index);
		storage.set(idx, value);
	}
	
	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		Float64Member tmp = new Float64Member();
		long[] index = new long[this.dims.length];
		// [2,2,2] dims
		// [0,0,0]  [[[num
		// [1,0,0]  [[[num,num
		// [0,1,0]  [[[num,num][num
		// [1,1,0]  [[[num,num][num,num
		// [0,0,1]  [[[num,num][num,num]][[num
		// [1,0,1]  [[[num,num][num,num]][[num,num
		// [0,1,1]  [[[num,num][num,num]][[num,num][num
		// [1,1,1]  [[[num,num][num,num]][[num,num][num,num]]]
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			longToIndex(i, index);
			int j = 0;
			while (j < index.length && index[j++] == 0)
				builder.append('[');
			if (index[0] != 0)
				builder.append(',');
			builder.append(tmp.v());
			j = 0;
			while (j < index.length && index[j] == (dims[j++]-1))
				builder.append(']');
		}
		return builder.toString();
	}

	private long[] calcMultipliers() {
		if (dims.length == 0) return new long[0];
		long[] result = new long[dims.length-1];
		long mult = 1;
		for (int i = 0; i < result.length; i++) {
			result[i] = mult;
			mult *= dims[i];
		}
		return result;
	}
	
	private long numElems(long[] dims) {
		if (dims.length == 0) return 0;
		long count = 1;
		for (long d : dims) {
			count *= d;
		}
		return count;
	}

	/*
	 * dims = [4,5,6]
	 * idx = [1,2,3]
	 * long = 3*5*4 + 2*4 + 1;
	 */
	private long indexToLong(long[] idx) {
		if (idx.length == 0) return 0;
		long index = 0;
		long mult = 1;
		for (int i = 0; i < idx.length; i++) {
			index += mult * idx[i];
			mult *= dims[i];
		}
		return index;
	}

	private void longToIndex(long idx, long[] result) {
		if (result.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		for (int i = result.length-1; i >= 0; i--) {
			result[i] = idx / multipliers[i];
			idx = idx % multipliers[i];
		}
	}

	@Override
	public int numDimensions() {
		return dims.length;
	}

	@Override
	public void reshape(long[] dims) {
		// TODO
		throw new IllegalArgumentException("to implement");
	}
}
