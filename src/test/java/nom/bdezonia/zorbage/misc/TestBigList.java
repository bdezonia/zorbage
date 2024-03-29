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
package nom.bdezonia.zorbage.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int10.UnsignedInt10Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBigList {

	@Test
	public void test() {
		
		UnsignedInt10Member value = G.UINT10.construct("99");
		UnsignedInt10Member zero = G.UINT10.construct();
		UnsignedInt10Member tmp = G.UINT10.construct();
		
		BigList<UnsignedInt10Member> b = new BigList<UnsignedInt10Member>();
		
		b.add(value);
		tmp.set(b.get(0));
		assertEquals(value.v(), tmp.v());
		
		for (int i = 0; i < 44; i++) {
			b.add(zero);
		}
		assertEquals(45, b.size());
		
		G.UINT10.succ().call(value, value);
		tmp.set(zero);
		b.set(44, value);
		tmp.set(b.get(44));
		assertEquals(value.v(), tmp.v());
		
		/*
		b = new BigList<UnsignedInt10Member>(45L + Integer.MAX_VALUE);
		tmp.set(zero);
		b.set(44L + Integer.MAX_VALUE, value);
		tmp.set(b.get(44L + Integer.MAX_VALUE));
		assertEquals(value.v(), tmp.v());
		*/
	}
}
