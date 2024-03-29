/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.bool.BooleanMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MaskedDataSource<U>
	implements
		IndexedDataSource<U>
{
	private final IndexedDataSource<U> list;
	private final IndexedDataSource<BooleanMember> mask;
	private final long listSize;
	private final long maskSize;
	private final long trueCount;
	private static final ThreadLocal<BooleanMember> bTmp =
			new ThreadLocal<BooleanMember>() {
				@Override
				protected BooleanMember initialValue() {
					return G.BOOL.construct();
				}
			};
	
	/**
	 * 
	 * @param list
	 * @param mask
	 */
	public MaskedDataSource(IndexedDataSource<U> list, IndexedDataSource<BooleanMember> mask) {
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
		BooleanMember b = bTmp.get();
		long count = 0;
		for (long i = 0; i < maskSize; i++) {
			mask.get(i, b);
			if (b.v())
				count++;
		}
		this.trueCount = count;
	}
	
	@Override
	public MaskedDataSource<U> duplicate() {
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
		BooleanMember b = bTmp.get();
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
		BooleanMember b = bTmp.get();
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

	@Override
	public StorageConstruction storageType() {
		return list.storageType();
	}

	@Override
	public boolean accessWithOneThread() {
		return list.accessWithOneThread();
	}
}
