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

import static org.junit.Assert.*;

import nom.bdezonia.zorbage.algebra.G;
import org.junit.Test;

import nom.bdezonia.zorbage.predicate.EqualConstant;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.bool.BooleanAlgebra;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestAnyOf {

	@Test
	public void test() {
		
		IndexedDataSource<BooleanMember> storage;
		
		Function1<Boolean,BooleanMember> condition = new EqualConstant<BooleanAlgebra,BooleanMember>(G.BOOL, new BooleanMember(false));

		storage = Storage.allocate(G.BOOL.construct(), new boolean[]{});
				
		assertEquals(false, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = Storage.allocate(G.BOOL.construct(), new boolean[]{false});
		
		assertEquals(true, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = Storage.allocate(G.BOOL.construct(), new boolean[]{true});
		
		assertEquals(false, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = Storage.allocate(G.BOOL.construct(), new boolean[]{true, false});
		
		assertEquals(true, AnyOf.compute(G.BOOL, condition, storage));
		
		storage = Storage.allocate(G.BOOL.construct(), new boolean[]{true, true, false});

		assertEquals(true, AnyOf.compute(G.BOOL, condition, storage));
		
	}

}
