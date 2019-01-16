/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage;

import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.type.algebra.Dimensioned;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 * @param <U>
 */
public class MultiDimAccessor<T extends IndexedDataSource<T,U>,U>
	implements
		Dimensioned
{
	private T storage;
	private long[] dims;
	
	public MultiDimAccessor(long[] dims, T storage) {
		this.dims = dims.clone();
		this.storage = storage;
		if (LongUtils.numElements(dims) != storage.size())
			throw new IllegalArgumentException("MultiDimAccessor has been given conflicting sizes");
	}

	public T storage() { return storage; }

	@Override
	public int numDimensions() {
		return dims.length;
	}
	
	public void getDimensions(long[] dims) {
		if (dims.length != this.dims.length)
			throw new IllegalArgumentException("target dims are not the correct size");
		for (int i = 0; i < this.dims.length; i++)
			dims[i] = this.dims[i];
	}

	public void resetDimensions(long[] newDims) {
		if (LongUtils.numElements(newDims) != storage.size())
			throw new IllegalArgumentException("MultiDimAccessor has been given conflicting sizes");
		dims = newDims.clone();
	}
	
	public void set(long[] index, U v) {
		long idx = indexToLong(index);
		storage.set(idx, v);
	}
	
	public void setSafe(long[] index, U v) {
		boundsCheck(index);
		set(index, v);
	}
	
	public void get(long[] index, U v) {
		long idx = indexToLong(index);
		storage.get(idx, v);
	}
	
	public void getSafe(long[] index, U v) {
		boundsCheck(index);
		get(index, v);
	}
	
	public long numElements() {
		return storage.size();
	}

	private void boundsCheck(long[] index) {
		if (index.length != this.dims.length)
			throw new IllegalArgumentException("index dimensionality not the same as accessor dimensions");
		for (int i = 0; i < index.length; i++) {
			if (index[i] < 0 || index[i] >= this.dims[i])
				throw new IllegalArgumentException("index out of bounds of accessor dimensions");
		}
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

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d >= dims.length) return 1;
		return dims[d];
	}
}
