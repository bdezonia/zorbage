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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;

// TODO: this class does a lot of recalculation. It is likely slow. But it can adapt to changes
// in the underlying list and the underlying mask. It is completely dynamic. Users must take
// care in a multithreaded environment.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MaskedDataSource<T extends IndexedDataSource<T,U>,U>
	implements
		IndexedDataSource<T,U>
{
	private final IndexedDataSource<T,U> list;
	private final IndexedDataSource<?,BooleanMember> mask;
	
	/**
	 * 
	 * @param list
	 * @param mask
	 */
	public MaskedDataSource(IndexedDataSource<T,U> list, IndexedDataSource<?,BooleanMember> mask) {
		this.list = list;
		this.mask = mask;
		if (mask.size() == 0)
			throw new IllegalArgumentException("mask must not be of length 0");
	}
	
	@Override
	public T duplicate() {
		// shallow copy
		// TODO: WTH? Why does this warning keep cropping up?
		return (T) new MaskedDataSource<>(list, mask);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		BooleanMember b = G.BOOL.construct();
		long count = -1;
		long maskSize = mask.size();
		for (long i = 0; i < list.size(); i++) {
			mask.get(i % maskSize, b);
			if (b.v()) {
				count++;
				if (count == index) {
					list.set(i, value);
					return;
				}
			}
		}
		throw new IllegalArgumentException("accessing masked data source off the end");
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		BooleanMember b = G.BOOL.construct();
		long count = -1;
		long maskSize = mask.size();
		for (long i = 0; i < list.size(); i++) {
			mask.get(i % maskSize, b);
			if (b.v()) {
				count++;
				if (count == index) {
					list.get(i, value);
					return;
				}
			}
		}
		throw new IllegalArgumentException("accessing masked data source off the end");
	}

	@Override
	public long size() {
		BooleanMember b = G.BOOL.construct();
		long maskSize = mask.size();
		long sz = 0;
		for (long i = 0; i < list.size(); i++) {
			mask.get(i % maskSize, b);
			if (b.v()) sz++;
		}
		return sz;
	}

}
