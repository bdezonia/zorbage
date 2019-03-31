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
package nom.bdezonia.zorbage.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int10.UnsignedInt10Member;

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
		
		G.UINT10.succ().call(value, value);
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
