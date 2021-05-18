/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.Constructible1dLong;
import nom.bdezonia.zorbage.algebra.Constructible2dLong;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.MatrixMember;
import nom.bdezonia.zorbage.algebra.RModule;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.helper.MatrixColumnRModuleBridge;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixInvert {

	// do not instantiate
	
	private MatrixInvert() {}

	/**
	 * Invert a matrix
	 * 
	 * @param numAlgebra
	 * @param rmodAlgebra
	 * @param matAlgebra
	 * @param a
	 * @param b
	 */
	@SuppressWarnings("unchecked")
	public static
		<BASETYPE, // the base type like Float64Member or Octonion etc.
		BASETYPE_ALGEBRA extends RingWithUnity<BASETYPE_ALGEBRA,BASETYPE> & Invertible<BASETYPE>,
		RMODULE_MEMBER extends RModuleMember<BASETYPE>,
		RMODULE_ALGEBRA extends RModule<RMODULE_ALGEBRA,RMODULE_MEMBER,BASETYPE_ALGEBRA,BASETYPE> & Constructible1dLong<RMODULE_MEMBER>,
		MATRIX_MEMBER extends MatrixMember<BASETYPE>,
		MATRIX_ALGEBRA extends Algebra<MATRIX_ALGEBRA,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
	void compute(BASETYPE_ALGEBRA numAlgebra, RMODULE_ALGEBRA rmodAlgebra, MATRIX_ALGEBRA matAlgebra, MATRIX_MEMBER a, MATRIX_MEMBER b)
	{
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("can only invert square matrices");
		if (a.rows() != b.rows() || a.cols() != b.cols())
			b.alloc(a.rows(), a.cols());
		MATRIX_MEMBER lu = matAlgebra.construct(a);
		LUDecomp.compute(numAlgebra, matAlgebra, lu);
		RMODULE_MEMBER bCol =
				rmodAlgebra.construct(b.storageType(), b.rows());
		MatrixColumnRModuleBridge<BASETYPE> xBridge =
				new MatrixColumnRModuleBridge<BASETYPE>(numAlgebra, b);
		BASETYPE zero = numAlgebra.construct();
		BASETYPE one = numAlgebra.construct();
		numAlgebra.unity().call(one);
		for (long c = 0; c < b.cols(); c++) {
			xBridge.setCol(c);
			bCol.setV(c, one);
			// The cast that follows is necessary because the bridge class does not
			// implement Constructable1d. I think I can manipulate generics
			// with a separate rmod type decl that does not require it that
			// x can satisfy.
			LUSolve.compute(numAlgebra, rmodAlgebra, lu, bCol, (RMODULE_MEMBER) xBridge);
			bCol.setV(c, zero);
		}
		
	}
}
