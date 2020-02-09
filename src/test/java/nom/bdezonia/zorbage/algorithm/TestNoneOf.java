/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.predicate.GreaterThanConstant;
import nom.bdezonia.zorbage.predicate.GreaterThanEqualConstant;
import nom.bdezonia.zorbage.predicate.LessThanConstant;
import nom.bdezonia.zorbage.predicate.Predicate;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Algebra;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNoneOf {

	@Test
	public void test() {
		IndexedDataSource<SignedInt8Member> storage =
				ArrayStorage.allocateBytes(new byte[]{0,1,2,3,4,5,6,7,8,9});
		
		Predicate<SignedInt8Member> lessZero =
				new LessThanConstant<SignedInt8Algebra, SignedInt8Member>(G.INT8, G.INT8.construct());
		assertEquals(true, NoneOf.compute(G.INT8, lessZero, storage));
		
		Predicate<SignedInt8Member> greaterEqualZero =
				new GreaterThanEqualConstant<SignedInt8Algebra, SignedInt8Member>(G.INT8, G.INT8.construct());
		assertEquals(false, NoneOf.compute(G.INT8, greaterEqualZero, storage));
		
		Predicate<SignedInt8Member> greaterNine =
				new GreaterThanConstant<SignedInt8Algebra, SignedInt8Member>(G.INT8, G.INT8.construct("9"));
		assertEquals(true, NoneOf.compute(G.INT8, greaterNine, storage));
	}
}
