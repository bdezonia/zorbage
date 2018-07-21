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
package nom.bdezonia.zorbage.type.data.int16;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnsignedInt16Group {

	@Test
	public void testPred() {
		UnsignedInt16Member v = new UnsignedInt16Member();
		
		v.setV(3);
		assertEquals(3, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(2, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(1, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0xffff, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0xfffe, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0xfffd, v.v());

		v.setV(0x8002);
		assertEquals(0x8002, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x8001, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x8000, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x7fff, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x7ffe, v.v());
		G.UINT16.pred().call(v, v);
		assertEquals(0x7ffd, v.v());
	}
	
	@Test
	public void testSucc() {
		UnsignedInt16Member v = new UnsignedInt16Member();
		
		v.setV(0xfffd);
		assertEquals(0xfffd, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0xfffe, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0xffff, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(1, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(2, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(3, v.v());

		v.setV(0x7ffd);
		assertEquals(0x7ffd, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x7ffe, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x7fff, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x8000, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x8001, v.v());
		G.UINT16.succ().call(v, v);
		assertEquals(0x8002, v.v());
	}

}
