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
package nom.bdezonia.zorbage.type.algebra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestDivModRem {
	
	// prove that div and mod for floats match behavior of ints
	//   This behavior also matches fortran and ruby
	
	@Test
	public void testDivMod() {
		Float64Member f1 = G.DBL.construct();
		Float64Member f2 = G.DBL.construct();
		Float64Member f3 = G.DBL.construct();
		Float64Member f4 = G.DBL.construct();
		
		SignedInt32Member i1 = G.INT32.construct();
		SignedInt32Member i2 = G.INT32.construct();
		SignedInt32Member i3 = G.INT32.construct();
		SignedInt32Member i4 = G.INT32.construct();
		
		f1.setV(13);
		f2.setV(4);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(13);
		i2.setV(4);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
		f1.setV(-13);
		f2.setV(4);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(-13);
		i2.setV(4);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
		f1.setV(13);
		f2.setV(-4);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(13);
		i2.setV(-4);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
		f1.setV(-13);
		f2.setV(-4);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(-13);
		i2.setV(-4);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);

		f1.setV(17);
		f2.setV(3);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(17);
		i2.setV(3);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
		f1.setV(-17);
		f2.setV(3);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(-17);
		i2.setV(3);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
		f1.setV(17);
		f2.setV(-3);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(17);
		i2.setV(-3);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
		f1.setV(-17);
		f2.setV(-3);
		G.DBL.divMod().call(f1,f2,f3,f4);
		
		i1.setV(-17);
		i2.setV(-3);
		G.INT32.divMod().call(i1,i2,i3,i4);

		assertEquals(Math.floor(f3.v()), i3.v(), 0);
		assertEquals(Math.floor(f4.v()), i4.v(), 0);
		assertEquals(Math.ceil(f3.v()), i3.v(), 0);
		assertEquals(Math.ceil(f4.v()), i4.v(), 0);
		
	}

	@Test
	public void testDivRem() {
		// TODO
	}
	
}