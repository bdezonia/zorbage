/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UInt1ToBool {

	private UInt1ToBool() { }
	
	/**
	 * 
	 * @param ints
	 * @param bools
	 */
	public static
		void compute(IndexedDataSource<UnsignedInt1Member> ints, IndexedDataSource<BooleanMember> bools)
	{
		long boolsSize = bools.size();
		long intsSize = ints.size();
		if (boolsSize != intsSize)
			throw new IllegalArgumentException("mismatched list sizes");
		
		Transform2.compute(G.UINT1, G.BOOL, converter, ints, bools);
	}

	private static Procedure2<UnsignedInt1Member,BooleanMember> converter =
			new Procedure2<UnsignedInt1Member, BooleanMember>()
	{
		@Override
		public void call(UnsignedInt1Member a, BooleanMember b) {
			b.setV(a.v() == 1 ? true : false);
		}
	};
}
