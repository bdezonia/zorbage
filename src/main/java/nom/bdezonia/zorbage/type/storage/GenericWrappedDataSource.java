/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GenericWrappedDataSource<K extends Algebra<K,L>,L> implements IndexedDataSource<L> {

	private K algebra;
	private L[] data;
	
	public GenericWrappedDataSource(K algebra, L[] data) {
		this.algebra = algebra;
		this.data = data;
	}
	
	@Override
	public IndexedDataSource<L> duplicate() {
		L[] out = data.clone();
		for (int i = 0; i < out.length; i++) {
			out[i] = algebra.construct();
			algebra.assign().call(data[i], out[i]);
		}
		return new GenericWrappedDataSource<K,L>(algebra, out);
	}

	@Override
	public void set(long index, L value) {
		if (index < 0 || index >= data.length)
			throw new IllegalArgumentException("index oob");
		algebra.assign().call(value, data[(int) index]);
	}

	@Override
	public void get(long index, L value) {
		if (index < 0 || index >= data.length)
			throw new IllegalArgumentException("index oob");
		algebra.assign().call(data[(int) index], value);
	}

	@Override
	public long size() {
		return data.length;
	}
	
}
