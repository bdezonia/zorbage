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
package nom.bdezonia.zorbage.data;

import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class IndexUtils {

	// do not instantiate
	
	private IndexUtils() { }
	
	/**
	 * 
	 * @param dims
	 * @param idx
	 * @return
	 */
	public static long indexToLong(long[] dims, IntegerIndex idx) {
		/*
		 * dims = [4,5,6]
		 * idx = [1,2,3]
		 * long = 3*5*4 + 2*4 + 1;
		 */
	
		int overlap = Math.min(dims.length, idx.numDimensions());
		long index = 0;
		long mult = 1;
		for (int i = 0; i < overlap; i++) {
			index += mult * idx.get(i);
			mult *= dims[i];
		}
		return index;
	}

	/**
	 * 
	 * @param dims
	 * @param idx
	 * @return
	 */
	public static long safeIndexToLong(long[] dims, IntegerIndex idx) {
		if (indexOob(dims, idx))
			throw new IllegalArgumentException("index out of bounds");
		return indexToLong(dims, idx);
	}

	/**
	 * 
	 * @param dims
	 * @param idx
	 * @return
	 */
	public static boolean indexOob(long[] dims, IntegerIndex idx) {

		// calc the overlapping dims
		int overlap = Math.min(dims.length, idx.numDimensions());
		
		// check the overlapping dims
		for (int i = 0; i < overlap; i++) {
			long index = idx.get(i);
			if (index < 0)
				throw new IllegalArgumentException("negative index in indexOob");
			if (index >= dims[i])
				return true;
		}

		// check the idx dims that are beyond the overlap
		for (int i = overlap; i < idx.numDimensions(); i++) {
			long index = idx.get(i);
			if (index < 0)
				throw new IllegalArgumentException("negative index in indexOob");
			if (index > 0)
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param dims
	 * @param idx
	 * @param component
	 * @param componentCount
	 * @return
	 */
	public static boolean componentOob(long[] dims, IntegerIndex idx, Integer component, Integer componentCount) {
		if (component < 0)
			throw new IllegalArgumentException("negative component specified in indexOob");
		if (component >= componentCount)
			return true;
		return indexOob(dims, idx);
	}
	
	/**
	 * 
	 * @param dims
	 * @return
	 */
	public static long[] calcMultipliers(long[] dims) {
		if (dims.length == 0)
			return new long[0];
		long[] result = new long[dims.length-1];
		long mult = 1;
		for (int i = 0; i < result.length; i++) {
			result[i] = mult;
			mult *= dims[i];
		}
		return result;
	}
	
	/**
	 * 
	 * @param multipliers
	 * @param dimCount
	 * @param size
	 * @param idx
	 * @param result
	 */
	public static void longToIntegerIndex(long[] multipliers, int dimCount, long size, long idx, IntegerIndex result) {
		if (idx < 0)
			throw new IllegalArgumentException("negative index in tensor addressing");
		if (idx >= size)
			throw new IllegalArgumentException("index beyond end of tensor storage");
		if (result.numDimensions() < dimCount)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		for (int i = dimCount; i < result.numDimensions(); i++) {
			result.set(i, 0);
		}
		for (int i = dimCount-1; i >= 0; i--) {
			result.set(i, idx / multipliers[i]);
			idx = idx % multipliers[i];
		}
	}

}
