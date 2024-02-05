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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OneDSnapshot {

	// do not instantiate

	private OneDSnapshot() { }

	/**
	 * This algorithm will grab a subset of data of an input dataset
	 *  and returns a one dimensional dataset using the normal
	 *  coordinate visit strategy. Out of bounds values are treated
	 *  as zero.
     *   
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param snapshotDims
	 * @param ds
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
	
		IndexedDataSource<U>
			
			compute(T alg, long[] snapshotDims, DimensionedDataSource<U> ds)
	{
		Procedure2<IntegerIndex, U> zeroFunc =

			new Procedure2<IntegerIndex, U>()
		{
				
			@Override
			public void call(IntegerIndex idx, U outVal) {
				
				alg.zero().call(outVal);
			}
		};
				
		return snap(alg, snapshotDims, zeroFunc, ds);
	}
	
	/**
	 * This algorithm will grab a subset of data of an input dataset
	 *  and returns a one dimensional dataset using the normal
	 *  coordinate visit strategy. Out of bounds values are computed
	 *  from an out of bounds function.
     *   
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param snapshotDims
	 * @param oobProc
	 * @param ds
	 * @return
	 */
	public static <T extends Algebra<T,U>, U extends Allocatable<U>>
	
		IndexedDataSource<U>
			
			snap(T alg, long[] snapshotDims, Procedure2<IntegerIndex,U> oobProc, DimensionedDataSource<U> ds)
	{
		if (snapshotDims.length != ds.numDimensions())
			
			throw new IllegalArgumentException("snapshot dims don't match dimensionality of input dataset");
		
		U val = alg.construct();
		
		IntegerIndex idx = new IntegerIndex(ds.numDimensions());
	
		IndexedDataSource<U> output =
				
				Storage.allocate(val, LongUtils.numElements(snapshotDims));
	
		long pos = 0;

		SamplingIterator<IntegerIndex> iter = GridIterator.compute(snapshotDims);
		
		while (iter.hasNext()) {
			
			iter.next(idx);
			
			if (ds.oob(idx)) {

				oobProc.call(idx, val);
			}
			else {

				ds.get(idx, val);
			}
			
			output.set(pos++, val);
		}
	
		return output;
	}
}
