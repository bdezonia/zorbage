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
import zorbage.type.storage.ArrayStorageFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float64ModuleTensorProductMember {

	private ArrayStorageFloat64 storage;
	private int[] dims;
	private int[] multipliers;
	
	public Float64ModuleTensorProductMember() {
		dims = new int[0];
		storage = new ArrayStorageFloat64(0);
		multipliers = calcMultipliers();
	}

	public Float64ModuleTensorProductMember(Float64ModuleTensorProductMember other) { 
		dims = other.dims.clone();
		storage = other.storage.duplicate();
		multipliers = calcMultipliers();
	}
	
	public Float64ModuleTensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		List<OctonionRepresentation> data = rep.values();
		storage = new ArrayStorageFloat64(data.size());
		dims = rep.dimensions().clone();
		multipliers = calcMultipliers();
		Float64Member tmp = new Float64Member();
		for (int i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r().doubleValue());
			storage.put(i, tmp);
		}
	}
	
	public int numDims() {
		return dims.length;
	}
	
	public int dim(int dim) {
		return dims[dim];
	}

	public void dims(int[] d) {
		if (d.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		for (int i = 0; i < d.length; i++) {
			d[i] = dims[i];
		}
	}
	
	public void setDims(int[] newDims) {
		int newCount = numElems(newDims);
		if (newCount != storage.size()) {
			storage = new ArrayStorageFloat64(newCount);
		}
		else {
			Float64Member zero = new Float64Member();
			for (int i = 0; i < storage.size(); i++) {
				storage.put(i, zero);
			}
		}
		dims = newDims.clone();
		multipliers = calcMultipliers();
	}
	
	public int numElems() {
		return (int) storage.size();
	}
	
	public void v(int index, Float64Member value) {
		if (index < 0 || index >= storage.size())
			throw new IllegalArgumentException("invald index in tensor member");
		storage.get(index, value);
	}
	
	public void v(int[] index, Float64Member value) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		int idx = indexToInt(index);
		storage.get(idx, value);
	}
	
	public void setV(int index, Float64Member value) {
		if (index < 0 || index >= storage.size())
			throw new IllegalArgumentException("invald index in tensor member");
		storage.put(index, value);
	}
	
	public void setV(int[] index, Float64Member value) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		int idx = indexToInt(index);
		storage.put(idx, value);
	}
	
	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		Float64Member tmp = new Float64Member();
		int[] index = new int[this.dims.length];
		// [2,2,2] dims
		// [0,0,0]  [[[num
		// [1,0,0]  [[[num,num
		// [0,1,0]  [[[num,num][num
		// [1,1,0]  [[[num,num][num,num
		// [0,0,1]  [[[num,num][num,num]][[num
		// [1,0,1]  [[[num,num][num,num]][[num,num
		// [0,1,1]  [[[num,num][num,num]][[num,num][num
		// [1,1,1]  [[[num,num][num,num]][[num,num][num,num]]]
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			intToIndex(i, index);
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

	private int[] calcMultipliers() {
		if (dims.length == 0) return new int[0];
		int[] result = new int[dims.length-1];
		int mult = 1;
		for (int i = 0; i < result.length; i++) {
			result[i] = mult;
			mult *= dims[i];
		}
		return result;
	}
	
	private int numElems(int[] dims) {
		if (dims.length == 0) return 0;
		int count = 1;
		for (int d : dims) {
			count *= d;
		}
		return count;
	}

	/*
	 * dims = [4,5,6]
	 * idx = [1,2,3]
	 * long = 3*5*4 + 2*4 + 1;
	 */
	private int indexToInt(int[] idx) {
		if (idx.length == 0) return 0;
		int index = 0;
		int mult = 1;
		for (int i = 0; i < idx.length; i++) {
			index += mult * idx[i];
			mult *= dims[i];
		}
		return index;
	}

	private void intToIndex(int idx, int[] result) {
		if (result.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		for (int i = result.length-1; i >= 0; i--) {
			result[i] = idx / multipliers[i];
			idx = idx % multipliers[i];
		}
	}
}
