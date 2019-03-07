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

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.condition.Condition;
import nom.bdezonia.zorbage.condition.EqualConstant;
import nom.bdezonia.zorbage.type.data.bool.BooleanAlgebra;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestAnyOf {

	@Test
	public void test() {
		
		IndexedDataSource<BooleanMember> storage;
		
		Condition<BooleanMember> condition = new EqualConstant<BooleanAlgebra,BooleanMember>(G.BOOL, new BooleanMember(false));

		storage = ArrayStorage.allocateBooleans(new boolean[]{});
				
		assertEquals(false, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = ArrayStorage.allocateBooleans(new boolean[]{false});
		
		assertEquals(true, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = ArrayStorage.allocateBooleans(new boolean[]{true});
		
		assertEquals(false, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = ArrayStorage.allocateBooleans(new boolean[]{true, false});
		
		assertEquals(true, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = ArrayStorage.allocateBooleans(new boolean[]{true, true, false});

		assertEquals(true, AnyOf.compute(G.BOOL, condition, storage));
		
	}

}
