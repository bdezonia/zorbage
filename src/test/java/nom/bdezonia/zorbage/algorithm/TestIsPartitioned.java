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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.predicate.EqualConstant;
import nom.bdezonia.zorbage.predicate.GreaterThanConstant;
import nom.bdezonia.zorbage.predicate.LessThanConstant;
import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Algebra;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestIsPartitioned {

	@Test
	public void test() {
		IndexedDataSource<SignedInt8Member> a = ArrayStorage.allocateBytes(
				new byte[] {1,2,3,4,5,6,7,8});
		
		Predicate<SignedInt8Member> cond1 = new EqualConstant<SignedInt8Algebra, SignedInt8Member>(G.INT8, new SignedInt8Member(4));
		assertFalse(IsPartitioned.compute(G.INT8, cond1, a));

		Predicate<SignedInt8Member> cond2 = new LessThanConstant<SignedInt8Algebra, SignedInt8Member>(G.INT8, new SignedInt8Member(4));
		assertTrue(IsPartitioned.compute(G.INT8, cond2, a));

		Predicate<SignedInt8Member> cond3 = new GreaterThanConstant<SignedInt8Algebra, SignedInt8Member>(G.INT8, new SignedInt8Member(4));
		assertTrue(IsPartitioned.compute(G.INT8, cond3, a));
	}
}
