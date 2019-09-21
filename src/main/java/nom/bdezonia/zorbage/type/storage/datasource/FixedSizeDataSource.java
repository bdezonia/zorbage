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
package nom.bdezonia.zorbage.type.storage.datasource;

/**
 * A typical use for this class is to take a source, wrap it with an out of
 * bounds procedure, and pass that wrapped source to this constructor with
 * a fixed size. This kind of thing might be necessary when you want to
 * calc the FFT of a data source and you need to make sure its length is a
 * power of two.
 * 
 * @author Barry DeZonia
 *
 */
public class FixedSizeDataSource<U>
	implements IndexedDataSource<U>
{
	private final long size;
	private final IndexedDataSource<U> src;
	
	/**
	 * 
	 * @param size
	 * @param src
	 */
	public FixedSizeDataSource(long size, IndexedDataSource<U> src) {
		if (size < 0)
			throw new IllegalArgumentException("negative size not allowed");
		this.size = size;
		this.src = src;
	}
	
	@Override
	public FixedSizeDataSource<U> duplicate() {
		// shallow copy
		return new FixedSizeDataSource<U>(size, src);
	}

	@Override
	public void set(long index, U value) {
		src.set(index, value);
	}

	@Override
	public void get(long index, U value) {
		src.get(index, value);
	}

	@Override
	public long size() {
		return size;
	}

}
