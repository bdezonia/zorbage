/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestConversion {

	// Scale a collection of Int32s by a floating point number
	
	// Made before universal conversion facilities in place
	
	@Test
	public void test() {
		// build the initial test data
		IndexedDataSource<SignedInt32Member> storage = Storage.allocate(G.INT32.construct(), 
				new int[] {0,1,2,3,4,5,6,7,8,9});
		// scale it by 6.3
		Float64Member scale = new Float64Member(6.3);
		Float64Member tmp = new Float64Member();
		SignedInt32Member value = new SignedInt32Member();
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i,  value);
			tmp.setV(value.v());
			G.DBL.multiply().call(tmp, scale, tmp);
			value.setV((int)Math.round(tmp.v()));
			storage.set(i, value);
		}
		
		storage.get(0, value);
		assertEquals(0, value.v());

		storage.get(1, value);
		assertEquals(6, value.v());
		
		storage.get(2, value);
		assertEquals(13, value.v());
		
		storage.get(3, value);
		assertEquals(19, value.v());
		
		storage.get(4, value);
		assertEquals(25, value.v());
		
		storage.get(5, value);
		assertEquals(32, value.v());
		
		storage.get(6, value);
		assertEquals(38, value.v());
		
		storage.get(7, value);
		assertEquals(44, value.v());
		
		storage.get(8, value);
		assertEquals(50, value.v());
		
		storage.get(9, value);
		assertEquals(57, value.v());
				
	}
}
