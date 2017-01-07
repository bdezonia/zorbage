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
	
	public Float64ModuleTensorProductMember() {
		dims = new int[0];
		storage = new ArrayStorageFloat64(0);
	}

	public Float64ModuleTensorProductMember(Float64ModuleTensorProductMember other) { 
		dims = other.dims.clone();
		storage = other.storage.duplicate();
	}
	
	public Float64ModuleTensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		List<OctonionRepresentation> data = rep.values();
		storage = new ArrayStorageFloat64(data.size());
		dims = rep.dimensions().clone();
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
		for (int i = 0; i < d.length; i++)
			d[i] = dims[i];
	}
	
	public void setDims(int[] dims) {
		long count = count(dims);
		if (count != count(this.dims)) {
			storage = new ArrayStorageFloat64(count);
		}
		this.dims = dims.clone();
	}
	
	public void v(int[] index, Float64Member value) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		long idx = indexToLong(index);
		storage.get(idx, value);
	}
	
	public void setV(int[] index, Float64Member value) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		long idx = indexToLong(index);
		storage.put(idx, value);
	}
	
	@Override
	public String toString() {
		throw new IllegalArgumentException("TODO float64 tensor member toString()"); // TODO
	}
	
	private long count(int[] dims) {
		if (dims.length == 0)
			return 0;
		long count = 1;
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
	private long indexToLong(int[] idx) {
		if (idx.length == 0) return 0;
		long index = 0;
		long mult = 1;
		for (int i = 0; i < idx.length; i++) {
			index += mult * idx[i];
			mult *= dims[i];
		}
		return index;
	}

	/*
	private void longToIndex(long idx, int[] result) {
		if (result.length != this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		long mult = 1;
		for (int i = 0; i < result.length; i++) {
			idx -= mult * (idx % dims[i]);
			mult *= dims[i];
		wrong wrong wrong	
		}
	}
	*/
}
