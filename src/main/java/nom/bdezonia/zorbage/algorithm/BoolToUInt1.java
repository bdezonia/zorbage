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

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

import nom.bdezonia.zorbage.algebra.*;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BoolToUInt1 {

	// do not instantiate
	
	private BoolToUInt1() { }

	/**
	 * Take a list of {@link BooleanMember} values and place their
	 * equivalent {@link UnsignedInt1Member} values in an output list.
	 * 
	 * @param bools
	 * @param ints
	 */
	public static
		void compute(IndexedDataSource<BooleanMember> bools, IndexedDataSource<UnsignedInt1Member> ints)
	{
		long boolsSize = bools.size();
		long intsSize = ints.size();
		if (boolsSize != intsSize)
			throw new IllegalArgumentException("mismatched list sizes");
		
		Transform2.compute(G.BOOL, G.UINT1, converter, bools, ints);
	}
	
	private static final Procedure2<BooleanMember,UnsignedInt1Member> converter =
			new Procedure2<BooleanMember, UnsignedInt1Member>()
	{
		@Override
		public void call(BooleanMember a, UnsignedInt1Member b) {
			b.setV(a.v() ? 1 : 0);
		}
	};
}
