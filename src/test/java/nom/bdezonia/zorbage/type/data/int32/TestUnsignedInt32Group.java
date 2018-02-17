/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int32;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt32Group {

	@Test
	public void testPred() {
		UnsignedInt32Member v = new UnsignedInt32Member();
		
		v.setV(3);
		assertEquals(3, v.v());
		G.UINT32.pred(v, v);
		assertEquals(2, v.v());
		G.UINT32.pred(v, v);
		assertEquals(1, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0xffffffffL, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0xfffffffeL, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0xfffffffdL, v.v());

		v.setV(0x80000002L);
		assertEquals(0x80000002L, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0x80000001L, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0x80000000L, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0x7fffffffL, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0x7ffffffeL, v.v());
		G.UINT32.pred(v, v);
		assertEquals(0x7ffffffdL, v.v());
	}
	
	@Test
	public void testSucc() {
		UnsignedInt32Member v = new UnsignedInt32Member();
		
		v.setV(0xfffffffdL);
		assertEquals(0xfffffffdL, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0xfffffffeL, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0xffffffffL, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0, v.v());
		G.UINT32.succ(v, v);
		assertEquals(1, v.v());
		G.UINT32.succ(v, v);
		assertEquals(2, v.v());
		G.UINT32.succ(v, v);
		assertEquals(3, v.v());

		v.setV(0x7ffffffdL);
		assertEquals(0x7ffffffdL, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0x7ffffffeL, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0x7fffffffL, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0x80000000L, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0x80000001L, v.v());
		G.UINT32.succ(v, v);
		assertEquals(0x80000002L, v.v());
	}

}
