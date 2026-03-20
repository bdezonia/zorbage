/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.algebra.IndexType;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;

/**
 *
 * @author Barry DeZonia
 *
 */
public class TensorOuterProduct {

	// do noy instantiate
	
	private TensorOuterProduct() { }

	/**
	 * Calculate the outer product of two tensors and place result in an output tensor
	 * 
	 * @param <S>
	 * @param <TENSOR>
	 * @param <M>
	 * @param <NUMBER>
	 * @param tensAlg
	 * @param numberAlg
	 * @param a
	 * @param b
	 * @param c
	 */
	public static <S extends Algebra<S,TENSOR>,
					TENSOR extends TensorMember<NUMBER> & RawData<NUMBER>,
					M extends Algebra<M,NUMBER> & Multiplication<NUMBER>,
					NUMBER>
		void compute(S tensAlg, M numberAlg, TENSOR a, TENSOR b, TENSOR c)
	{
		if (c == a || c == b)
			throw new IllegalArgumentException("destination tensor cannot be one of the inputs");

		final int rankA = a.rank();
		final int rankB = b.rank();
		final int rankC = rankA + rankB;

		// Build output shape = shape(a) followed by shape(b)

		long[] aShape = new long[rankA];
		long[] bShape = new long[rankB];
		a.shape(aShape);
		b.shape(bShape);

		long[] cShape = new long[rankC];
		for (int i = 0; i < rankA; i++) {
			cShape[i] = aShape[i];
		}
		for (int i = 0; i < rankB; i++) {
			cShape[rankA + i] = bShape[i];
		}

		// Build output variance = index types of a followed by index types of b

		IndexType[] cTypes = new IndexType[rankC];
		for (int i = 0; i < rankA; i++) {
			cTypes[i] = a.indexType(i);
		}
		for (int i = 0; i < rankB; i++) {
			cTypes[rankA + i] = b.indexType(i);
		}

		c.alloc(cShape, cTypes);

		NUMBER aTmp = numberAlg.construct();
		NUMBER bTmp = numberAlg.construct();
		NUMBER cTmp = numberAlg.construct();

		IndexedDataSource<NUMBER> aList = a.rawData();
		IndexedDataSource<NUMBER> bList = b.rawData();
		IndexedDataSource<NUMBER> cList = c.rawData();

		long k = 0;
		long numElemsA = aList.size();
		long numElemsB = bList.size();

		// TODO: am I storing oututs in the right order.
		//   I think I am but remember the universal
		//   representation might have different ideas.

		for (long i = 0; i < numElemsA; i++) {
			aList.get(i, aTmp);
			for (long j = 0; j < numElemsB; j++) {
				bList.get(j, bTmp);
				numberAlg.multiply().call(aTmp, bTmp, cTmp);
				cList.set(k++, cTmp);
			}
		}
	}
}
