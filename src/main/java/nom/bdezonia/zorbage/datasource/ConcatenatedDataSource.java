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

import java.math.BigInteger;
import java.util.List;

import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ConcatenatedDataSource<U>
	implements IndexedDataSource<U>
{
	private final IndexedDataSource<U> first;
	private final IndexedDataSource<U> second;
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public ConcatenatedDataSource(IndexedDataSource<U> a, IndexedDataSource<U> b) {
		if (BigInteger.valueOf(a.size()).add(BigInteger.valueOf(b.size())).compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
			throw new IllegalArgumentException("the two input lists are too long to add together");
		this.first = a;
		this.second = b;
	}
	
	@Override
	public ConcatenatedDataSource<U> duplicate() {
		// shallow copy
		return new ConcatenatedDataSource<>(first, second);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		long sz = first.size();
		if (index < sz)
			first.set(index, value);
		else
			second.set(index-sz, value);
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		long sz = first.size();
		if (index < sz)
			first.get(index, value);
		else
			second.get(index-sz, value);
	}

	@Override
	public long size() {
		return first.size() + second.size();
	}

	@Override
	public StorageConstruction storageType() {
		
		// TODO - Maybe the order of these if statements should change.
		//   Or maybe more testing of the size()'s should factor in.
		
		StorageConstruction firstType = first.storageType();
		
		StorageConstruction secondType = first.storageType();
		
		// if both are sparse then so is the resulting list's
		
		if ((firstType == StorageConstruction.MEM_SPARSE) &&
				(secondType == StorageConstruction.MEM_SPARSE))
			return StorageConstruction.MEM_SPARSE;
		
		// if one is virtual then so is the resulting list's
		
		if ((firstType == StorageConstruction.MEM_VIRTUAL) ||
				(secondType == StorageConstruction.MEM_VIRTUAL))
			return StorageConstruction.MEM_VIRTUAL;
		
		// default otherwise to trying RAM
		
		return StorageConstruction.MEM_ARRAY;
	}

	@Override
	public boolean accessWithOneThread() {
		return first.accessWithOneThread() || second.accessWithOneThread();
	}

	/**
	 * Make a nice lg2 n hierarchy of concatenated data sources from a list of sources.
	 * 
	 * @param <R>
	 * @param sources
	 * @return
	 *
	 */ 
	public static <R>
		IndexedDataSource<R> optimalConcat(List<IndexedDataSource<R>> sources)
	{
		if (sources.size() == 0) { 
			throw new IllegalArgumentException("Cannot concatenate an empty list of data sources");
		}
		
		return concat(sources, 0, sources.size());
	}
	
	private static <R>
		IndexedDataSource<R> concat(List<IndexedDataSource<R>> sources, int left, int rightPlusOne)
	{
		if (left < 0)
			throw new IllegalArgumentException("concat has error condition 1");
			
		if (rightPlusOne > sources.size())
			throw new IllegalArgumentException("concat has error condition 2");
	
		if (left >= rightPlusOne)
			throw new IllegalArgumentException("concat has error condition 3");
	
		if (rightPlusOne - left == 1) {
			return sources.get(left);
		}
	
		else if (rightPlusOne - left == 2) {
			return new ConcatenatedDataSource<>(sources.get(left), sources.get(left+1));
		}
		else {
			int midPt = left + ((rightPlusOne - left) / 2);
			IndexedDataSource<R> leftSrc = concat(sources, left, midPt);
			IndexedDataSource<R> rightSrc = concat(sources, midPt + 1, rightPlusOne);
			return new ConcatenatedDataSource<>(leftSrc, rightSrc);
		}
	}
}
