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
package nom.bdezonia.zorbage.predicate;

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBooleanPredicate {

	@Test
	public void test() {
		
		Function1<Boolean, SignedInt32Member> testFunc =
				new Function1<Boolean, SignedInt32Member>()
		{
			@Override
			public Boolean call(SignedInt32Member b) {
				return b.v() == 7;
			}
		};

		BooleanPredicate<SignedInt32Member> pred =
				new BooleanPredicate<>(testFunc);
		
		SignedInt32Member value = G.INT32.construct();
		
		value.setV(5);
		assertEquals(5==7, pred.isTrue(value));
		
		value.setV(6);
		assertEquals(6==7, pred.isTrue(value));
		
		value.setV(7);
		assertEquals(7==7, pred.isTrue(value));
		
		value.setV(8);
		assertEquals(8==7, pred.isTrue(value));
	}

}