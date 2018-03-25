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
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.helper.MatrixColumnRModuleBridge;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixInvert {

	// do not instantiate
	
	private MatrixInvert() {}

	@SuppressWarnings("unchecked")
	public static
		<BASETYPE, // the base type like Float64Member or Octonion etc.
		BASETYPE_GROUP extends RingWithUnity<BASETYPE_GROUP,BASETYPE> & Invertible<BASETYPE>,
		RMODULE_MEMBER extends RModuleMember<BASETYPE>,
		RMODULE_GROUP extends RModule<RMODULE_GROUP,RMODULE_MEMBER,BASETYPE_GROUP,BASETYPE> & Constructible1dLong<RMODULE_MEMBER>,
		MATRIX_MEMBER extends MatrixMember<BASETYPE>,
		MATRIX_GROUP extends Group<MATRIX_GROUP,MATRIX_MEMBER> & Constructible2dLong<MATRIX_MEMBER>>
	void compute(BASETYPE_GROUP numGroup, RMODULE_GROUP rmodGroup, MATRIX_GROUP matGroup, MATRIX_MEMBER a, MATRIX_MEMBER b)
	{
		MATRIX_MEMBER lu = matGroup.construct(a);
		LUDecomp.compute(numGroup, matGroup, lu);
		RMODULE_MEMBER bCol =
				rmodGroup.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, a.rows());
		MatrixColumnRModuleBridge<BASETYPE> xBridge =
				new MatrixColumnRModuleBridge<BASETYPE>(numGroup, b);
		BASETYPE zero = numGroup.construct();
		BASETYPE one = numGroup.construct();
		numGroup.unity(one);
		for (long c = 0; c < b.cols(); c++) {
			xBridge.setCol(c);
			bCol.setV(c, one);
			LUSolve.compute(numGroup, rmodGroup, lu, bCol, (RMODULE_MEMBER) xBridge);
			bCol.setV(c, zero);
		}
		
	}
}
