/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.Invertible;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixPower {

	private MatrixPower() { }
	
	public static
	<BASETYPE, // the base type like Float64Member or Octonion etc.
	BASETYPE_GROUP extends RingWithUnity<BASETYPE_GROUP,BASETYPE> & Invertible<BASETYPE>,
	RMODULE_MEMBER extends RModuleMember<BASETYPE>,
	RMODULE_GROUP extends RModule<RMODULE_GROUP,RMODULE_MEMBER,BASETYPE_GROUP,BASETYPE> & Constructible1dLong<RMODULE_MEMBER>,
	MATRIX_MEMBER extends MatrixMember<BASETYPE>,
	MATRIX_GROUP extends Group<MATRIX_GROUP,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
		void compute(int power, BASETYPE_GROUP numGroup, RMODULE_GROUP rmodGroup, MATRIX_GROUP matGroup, MATRIX_MEMBER a, MATRIX_MEMBER b)
	{
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0) {
			MATRIX_MEMBER aInv = matGroup.construct();
			MatrixInvert.compute(numGroup, rmodGroup, matGroup, a, aInv);
			MatrixPower.compute(-power, numGroup, rmodGroup, matGroup, aInv, b);
		}
		else if (power == 0) {
			if (matGroup.isZero().call(a)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			b.alloc(a.rows(), a.cols());
			MatrixUnity.compute(numGroup, b);
		}
		else if (power == 1)
			MatrixAssign.compute(numGroup, a, b);
		else { // power >= 2
			MATRIX_MEMBER tmp = matGroup.construct(a);
			MATRIX_MEMBER tmp2 = matGroup.construct();
			for (int i = 2; i <= power; i++) {
				MatrixMultiply.compute(numGroup, tmp, a, tmp2);
				MatrixAssign.compute(numGroup, tmp2, tmp);
			}
			MatrixAssign.compute(numGroup, tmp, b);
		}
	}
}
