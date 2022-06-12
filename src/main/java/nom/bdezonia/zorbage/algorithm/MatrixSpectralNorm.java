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
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.MatrixOps;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixSpectralNorm {

	// http://mathworld.wolfram.com/MatrixNorm.html
	// https://mathworld.wolfram.com/SpectralNorm.html
	
	// do not instantiate
	
	private MatrixSpectralNorm() {}
	
	public static <T extends Algebra<T,MATRIX>,
					MATRIX,
					X extends Algebra<X,COMPONENT>,
					COMPONENT>
		void compute(T matAlgebra, X cmpAlgebra, MATRIX matrix, COMPONENT normValue)
	{
		
		// future implementation is below

		throw new IllegalArgumentException("TODO");
	}
	
	/**
	 * 
	 * @param matAlgebra
	 * @param numAlgebra
	 * @param cmpAlgebra
	 * @param matrix
	 * @param normValue
	 */
	public static <T extends Algebra<T,MATRIX> & MatrixOps<MATRIX,COMPONENT> & Multiplication<MATRIX>,
					MATRIX extends MatrixMember<NUMBER>,
					V extends Algebra<V,NUMBER> & Norm<NUMBER,COMPONENT>,
					NUMBER extends NumberMember<COMPONENT> & Allocatable<NUMBER>,
					X extends Algebra<X,COMPONENT> & Invertible<COMPONENT> & Roots<COMPONENT> & Ordered<COMPONENT>,
					COMPONENT>
		void compute(T matAlgebra, V numAlgebra, X cmpAlgebra, MATRIX matrix, COMPONENT normValue)
	{
		MATRIX conjTransp = matAlgebra.construct();
		MATRIX tmp = matAlgebra.construct();
		matAlgebra.conjugateTranspose().call(matrix, conjTransp);
		matAlgebra.multiply().call(conjTransp, matrix, tmp);
		NUMBER num = numAlgebra.construct();
		IndexedDataSource<NUMBER> eigenValues = Storage.allocate(num, tmp.cols());
		// TODO find the eigenvalues of tmp matrix
		COMPONENT l2x = cmpAlgebra.construct();
		COMPONENT l2Ax = cmpAlgebra.construct();
		COMPONENT ratio = cmpAlgebra.construct();
		COMPONENT max = cmpAlgebra.construct();
		
		for (long i = 0; i < eigenValues.size(); i++) {
			// TODO
			// find the eigenvector associated with this eigenvalue
			// then multiply the tmp matrix by the eigenvector to get a new vector
			// take the norm of that vector: l2x
			numAlgebra.norm().call(num, l2x);
			if (cmpAlgebra.isZero().call(l2x))
				continue;
			// take the norm of the eigenvector l2Ax
			numAlgebra.norm().call(num, l2Ax);
			// calc their ratio
			cmpAlgebra.divide().call(l2Ax, l2x, ratio);
			// record the max so far
			Max.compute(cmpAlgebra, ratio, max, max);
		}
		cmpAlgebra.sqrt().call(max, normValue);
	}
}
