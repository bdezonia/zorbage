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
package nom.bdezonia.zorbage.misc;

import nom.bdezonia.zorbage.tuple.Tuple2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ThreadingUtils {

	// do not instantiate
	
	private ThreadingUtils() { }

	/**
	 * 
	 * @return A tuple (numPieces, elemsPerPiece)
	 */
	public static Tuple2<Integer,Long> arrange(long numElems, boolean useOneThread) {

		return arrange(Runtime.getRuntime().availableProcessors(), numElems, useOneThread);
	}

	/**
	 * 
	 * @return A tuple (numPieces, elemsPerPiece)
	 */
	public static Tuple2<Integer,Long> arrange(int numThreads, long numElems, boolean useOneThread)
	{
		if (numThreads <= 0)
			throw new IllegalArgumentException("threading arrangement: num threads must be > 0");
		
		if (numElems <= 0)
			throw new IllegalArgumentException("threading arrangement: num elems must be > 0");
	
		if (numThreads == 1 || useOneThread)
			return new Tuple2<>(1, numElems);

		long elemsPerThread = numElems / numThreads;
		
		long extraElems = numElems % numThreads;
		
		if (extraElems == 0) {
		
			return new Tuple2<>(numThreads, elemsPerThread);
		}
		else {
			
			return new Tuple2<>(numThreads, elemsPerThread+1);
		}
	}
}
