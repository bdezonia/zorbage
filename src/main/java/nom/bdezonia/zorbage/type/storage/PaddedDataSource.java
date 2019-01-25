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

import nom.bdezonia.zorbage.type.algebra.Algebra;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 * @param <U>
 */
public class PaddedDataSource<T extends Algebra<T,U>,U>
	implements IndexedDataSource<PaddedDataSource<T,U>, U>
{
	final private T algebra;
	final private IndexedDataSource<?,U> storage;
	final private U zero;
	
	/**
	 * 
	 * @param algebra
	 * @param storage
	 */
	public PaddedDataSource(T algebra, IndexedDataSource<?,U> storage) {
		this.algebra = algebra;
		this.storage = storage;
		this.zero = algebra.construct();
	}

	@Override
	public PaddedDataSource<T, U> duplicate() {
		// shallow copy
		return new PaddedDataSource<T,U>(algebra, storage);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= storage.size()) {
			if (algebra.isNotEqual().call(zero, value))
				throw new IllegalArgumentException("Cannot set out of bounds value as nonzero");
		}
		else {
			storage.set(index, value);
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= storage.size()) {
			algebra.assign().call(zero, value);
		}
		else {
			storage.get(index, value);
		}
	}

	@Override
	public long size() {
		return storage.size();
	}
}
