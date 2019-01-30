/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MaskedDataSource<T extends Algebra<T,U>,U>
	implements
		IndexedDataSource<MaskedDataSource<T,U>,U>
{
	private final IndexedDataSource<?,U> list;
	private final IndexedDataSource<?,BooleanMember> mask;
	private final long listSize;
	private final long maskSize;
	private final long trueCount;
	
	/**
	 * 
	 * @param list
	 * @param mask
	 */
	public MaskedDataSource(IndexedDataSource<?,U> list, IndexedDataSource<?,BooleanMember> mask) {
		this.list = list;
		this.mask = mask;
		this.listSize = list.size();
		long mSz = mask.size();
		if (mSz > listSize)
			this.maskSize = listSize;
		else
			this.maskSize = mSz;
		if (maskSize == 0)
			throw new IllegalArgumentException("mask must not be of length 0");
		BooleanMember b = G.BOOL.construct();
		long count = 0;
		for (long i = 0; i < maskSize; i++) {
			mask.get(i, b);
			if (b.v())
				count++;
		}
		trueCount = count;
	}
	
	@Override
	public MaskedDataSource<T,U> duplicate() {
		// shallow copy
		return new MaskedDataSource<>(list, mask);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		long pos = findPosition(index);
		if (pos >= 0)
			list.set(pos, value);
		else
			throw new IllegalArgumentException("accessing masked data source off the end");
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		long pos = findPosition(index);
		if (pos >= 0)
			list.get(pos, value);
		else
			throw new IllegalArgumentException("accessing masked data source off the end");
	}

	@Override
	public long size() {
		BooleanMember b = G.BOOL.construct();
		long numFullReps = listSize / maskSize;
		long sz = numFullReps * trueCount;
		long partialMaskSize = listSize % maskSize;
		for (long i = 0; i < partialMaskSize; i++) {
			mask.get(i, b);
			if (b.v())
				sz++;
		}
		return sz;
	}

	private long findPosition(long index) {
		BooleanMember b = G.BOOL.construct();
		long numFullReps = index / trueCount;
		long pos = numFullReps * maskSize;
		long found = 0;
		long mustFind = (index % trueCount) + 1;
		//long mustFind = index % trueCount;
		//if (mustFind == 0) mustFind = 1;
		long i = 0;
		while (found < mustFind) {
			mask.get(i, b);
			if (b.v())
				found++;
			if (found != mustFind)
				i++;
		}
		pos += i;
		//System.out.println("in " + index + "  out " + pos);
		return pos;
	}
}
