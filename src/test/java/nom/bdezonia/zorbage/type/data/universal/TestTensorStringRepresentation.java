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
package nom.bdezonia.zorbage.type.data.universal;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTensorStringRepresentation {

	@Test
	public void test0d() {
		TensorStringRepresentation a = new TensorStringRepresentation("1");
		assertArrayEquals(new long[] {1}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.firstValue().r());
		assertEquals(BigDecimal.ZERO, a.firstValue().i());
		assertEquals(BigDecimal.ZERO, a.firstValue().j());
		assertEquals(BigDecimal.ZERO, a.firstValue().k());
		assertEquals(BigDecimal.ZERO, a.firstValue().l());
		assertEquals(BigDecimal.ZERO, a.firstValue().i0());
		assertEquals(BigDecimal.ZERO, a.firstValue().j0());
		assertEquals(BigDecimal.ZERO, a.firstValue().k0());

		TensorStringRepresentation b = new TensorStringRepresentation("{1}");
		assertArrayEquals(new long[] {1}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.firstValue().r());
		assertEquals(BigDecimal.ZERO, b.firstValue().i());
		assertEquals(BigDecimal.ZERO, b.firstValue().j());
		assertEquals(BigDecimal.ZERO, b.firstValue().k());
		assertEquals(BigDecimal.ZERO, b.firstValue().l());
		assertEquals(BigDecimal.ZERO, b.firstValue().i0());
		assertEquals(BigDecimal.ZERO, b.firstValue().j0());
		assertEquals(BigDecimal.ZERO, b.firstValue().k0());

		TensorStringRepresentation c = new TensorStringRepresentation("{1,2}");
		assertArrayEquals(new long[] {1}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), c.firstValue().i());
		assertEquals(BigDecimal.ZERO, c.firstValue().j());
		assertEquals(BigDecimal.ZERO, c.firstValue().k());
		assertEquals(BigDecimal.ZERO, c.firstValue().l());
		assertEquals(BigDecimal.ZERO, c.firstValue().i0());
		assertEquals(BigDecimal.ZERO, c.firstValue().j0());
		assertEquals(BigDecimal.ZERO, c.firstValue().k0());

		TensorStringRepresentation d = new TensorStringRepresentation("{1,2,3}");
		assertArrayEquals(new long[] {1}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), d.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), d.firstValue().j());
		assertEquals(BigDecimal.ZERO, d.firstValue().k());
		assertEquals(BigDecimal.ZERO, d.firstValue().l());
		assertEquals(BigDecimal.ZERO, d.firstValue().i0());
		assertEquals(BigDecimal.ZERO, d.firstValue().j0());
		assertEquals(BigDecimal.ZERO, d.firstValue().k0());

		TensorStringRepresentation e = new TensorStringRepresentation("{1,2,3,4}");
		TensorStringRepresentation f = new TensorStringRepresentation("{1,2,3,4,5}");
		TensorStringRepresentation g = new TensorStringRepresentation("{1,2,3,4,5,6}");
		TensorStringRepresentation h = new TensorStringRepresentation("{1,2,3,4,5,6,7}");
		TensorStringRepresentation i = new TensorStringRepresentation("{1,2,3,4,5,6,7,8}");
		TensorStringRepresentation j = new TensorStringRepresentation("{1,2,3,4,5,6,7,8,9}");
		assertTrue(true);
	}

	@Test
	public void test1dx1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1]");
		assertArrayEquals(new long[] {1}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.firstValue().r());
		assertEquals(BigDecimal.ZERO, a.firstValue().i());
		assertEquals(BigDecimal.ZERO, a.firstValue().j());
		assertEquals(BigDecimal.ZERO, a.firstValue().k());
		assertEquals(BigDecimal.ZERO, a.firstValue().l());
		assertEquals(BigDecimal.ZERO, a.firstValue().i0());
		assertEquals(BigDecimal.ZERO, a.firstValue().j0());
		assertEquals(BigDecimal.ZERO, a.firstValue().k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[{1}]");
		assertArrayEquals(new long[] {1}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.firstValue().r());
		assertEquals(BigDecimal.ZERO, b.firstValue().i());
		assertEquals(BigDecimal.ZERO, b.firstValue().j());
		assertEquals(BigDecimal.ZERO, b.firstValue().k());
		assertEquals(BigDecimal.ZERO, b.firstValue().l());
		assertEquals(BigDecimal.ZERO, b.firstValue().i0());
		assertEquals(BigDecimal.ZERO, b.firstValue().j0());
		assertEquals(BigDecimal.ZERO, b.firstValue().k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2}]");
		assertArrayEquals(new long[] {1}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), c.firstValue().i());
		assertEquals(BigDecimal.ZERO, c.firstValue().j());
		assertEquals(BigDecimal.ZERO, c.firstValue().k());
		assertEquals(BigDecimal.ZERO, c.firstValue().l());
		assertEquals(BigDecimal.ZERO, c.firstValue().i0());
		assertEquals(BigDecimal.ZERO, c.firstValue().j0());
		assertEquals(BigDecimal.ZERO, c.firstValue().k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3}]");
		assertArrayEquals(new long[] {1}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), d.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), d.firstValue().j());
		assertEquals(BigDecimal.ZERO, d.firstValue().k());
		assertEquals(BigDecimal.ZERO, d.firstValue().l());
		assertEquals(BigDecimal.ZERO, d.firstValue().i0());
		assertEquals(BigDecimal.ZERO, d.firstValue().j0());
		assertEquals(BigDecimal.ZERO, d.firstValue().k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4}]");
		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5}]");
		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6}]");
		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7}]");
		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8}]");
		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9}]");
		assertTrue(true);
	}

	@Test
	public void test1dx2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1,2]");
		assertArrayEquals(new long[] {2}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[{1},{2}]");
		assertArrayEquals(new long[] {2}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).k0());
		
		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2},{3,4}]");
		assertArrayEquals(new long[] {2}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3},{4,5,6}]");
		assertArrayEquals(new long[] {2}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4},{5,6,7,8}]");
		assertArrayEquals(new long[] {2}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5},{6,7,8,9,10}]");
		assertArrayEquals(new long[] {2}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(1).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6},{7,8,9,10,11,12}]");
		assertArrayEquals(new long[] {2}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(1).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]");
		assertArrayEquals(new long[] {2}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.firstVectorValues().get(1).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]");
		assertArrayEquals(new long[] {2}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.firstVectorValues().get(1).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]");
		assertArrayEquals(new long[] {2}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.firstVectorValues().get(1).k0());

		assertTrue(true);
	}

	@Test
	public void test1dx3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1,2,3]");
		assertArrayEquals(new long[] {3}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.firstVectorValues().get(2).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[{1},{2},{3}]");
		assertArrayEquals(new long[] {3}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.firstVectorValues().get(2).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2},{3,4},{5,6}]");
		assertArrayEquals(new long[] {3}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.firstVectorValues().get(2).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3},{4,5,6},{7,8,9}]");
		assertArrayEquals(new long[] {3}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.firstVectorValues().get(2).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4},{5,6,7,8},{9,10,11,12}]");
		assertArrayEquals(new long[] {3}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.firstVectorValues().get(2).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]");
		assertArrayEquals(new long[] {3}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.firstVectorValues().get(2).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]");
		assertArrayEquals(new long[] {3}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.firstVectorValues().get(2).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]");
		assertArrayEquals(new long[] {3}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.firstVectorValues().get(2).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]");
		assertArrayEquals(new long[] {3}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.firstVectorValues().get(2).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]");
		assertArrayEquals(new long[] {3}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.firstVectorValues().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.firstVectorValues().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.firstVectorValues().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.firstVectorValues().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.firstVectorValues().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.firstVectorValues().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.firstVectorValues().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.firstVectorValues().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.firstVectorValues().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.firstVectorValues().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.firstVectorValues().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.firstVectorValues().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.firstVectorValues().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.firstVectorValues().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.firstVectorValues().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.firstVectorValues().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.firstVectorValues().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.firstVectorValues().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.firstVectorValues().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.firstVectorValues().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.firstVectorValues().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.firstVectorValues().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.firstVectorValues().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.firstVectorValues().get(2).k0());

		assertTrue(true);
	}

	@Test
	public void test2d1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}]]");
		assertTrue(true);
	}

	@Test
	public void test2d1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]]");
		assertTrue(true);
	}

	@Test
	public void test2d1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]]");
		assertTrue(true);
	}

	@Test
	public void test2d2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1][2]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}][{2}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}][{3,4}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}][{4,5,6}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}][{5,6,7,8}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}][{6,7,8,9,10}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}][{7,8,9,10,11,12}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}]]");
		assertTrue(true);
	}

	@Test
	public void test2d2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2][3,4]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}][{3},{4}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}][{5,6},{7,8}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]]");
		assertTrue(true);
	}

	@Test
	public void test2d2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3][4,5,6]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}][{4},{5},{6}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]");
		assertTrue(true);
	}

	@Test
	public void test2d3x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1][2][3]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}][{2}][{3}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}][{3,4}][{5,6}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}][{4,5,6}][{7,8,9}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}]]");
		assertTrue(true);
	}

	@Test
	public void test2d3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2][3,4][5,6]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}][{3},{4}][{5},{6}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}][{5,6},{7,8}][{9,10},{11,12}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}][{13,14,15},{16,17,18}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}][{17,18,19,20},{21,22,23,24}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}][{21,22,23,24,25},{26,27,28,29,30}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}][{25,26,27,28,29,30},{31,32,33,34,35,36}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]");
		assertTrue(true);
	}

	@Test
	public void test2d3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3][4,5,6][7,8,9]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}][{4},{5},{6}][{7},{8},{9}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}][{13,14},{15,16},{17,18}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}][{19,20,21},{22,23,24},{25,26,27}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}][{25,26,27,28},{29,30,31,32},{33,34,35,36}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}][{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}][{37,38,39,40,41,42},{43,44,45,46,47,48},{49,50,51,52,53,54}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,61,62,63}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,59,70,71,72}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{1,2}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{1,2,3}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{1,2,3,4,5}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{1,2,3,4,5,6}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{1,2,3,4,5,6,7}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{1,2},{1,2}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{1,2,3},{1,2,3}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{1,2,3,4},{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{1,2,3,4,5,6},{1,2,3,4,5,6}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{1,2,3,4,5,6,7},{1,2,3,4,5,6,7}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][2]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{2}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}][{3,4}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}][{4,5,6}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}][{5,6,7,8}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}][{6,7,8,9,10}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2][3,4]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}][{3},{4}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}][{5,6},{7,8}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3][4,5,6]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}][{4},{5},{6}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x3x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][2][3]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{2}][{3}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}][{3,4}][{5,6}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}][{4,5,6}][{7,8,9}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2][3,4][5,6]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}][{3},{4}][{5},{6}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}][{5,6},{7,8}][{9,10},{11,12}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}][{13,14,15},{16,17,18}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}][{17,18,19,20},{21,22,23,24}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}][{21,22,23,24,25},{26,27,28,29,30}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}][{25,25,27,28,29,30},{31,32,33,34,35,36}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3][4,5,6][7,8,9]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}][{4},{5},{6}][{7},{8},{9}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}][{13,14},{15,16},{17,18}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}][{19,20,21},{22,23,24},{25,26,27}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}][{25,26,27,28},{29,30,31,32},{33,34,35,36}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}][{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}][{37,38,39,40,41,42},{43,44,45,46,47,48},{49,50,51,52,53,54}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,61,62,63}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,69,70,71,72}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]][[2]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]][[{2}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}]][[{3,4}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}]][[{4,5,6}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}]][[{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}]][[{6,7,8,9,10}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}]][[{7,8,9,10,11,12}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}]][[{8,9,10,11,12,13,14}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}]][[{9,10,11,12,13,14,15,16}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}]][[{10,11,12,13,14,15,16,17,18}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2]][[3,4]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}]][[{3},{4}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}]][[{5,6},{7,8}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}]][[{7,8,9},{10,11,12}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}]][[{9,10,11,12},{13,14,15,16}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}]][[{11,12,13,14,15},{16,17,18,19,20}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}]][[{13,14,15,16,17,18},{19,20,21,22,23,24}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]][[{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]][[{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]][[{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3]][[4,5,6]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}]][[{4},{5},{6}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}]][[{7,8},{9,10},{11,12}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}]][[{10,11,12},{13,14,15},{16,17,18}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}]][[{13,14,15,16},{17,18,19,20},{21,22,23,24}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]][[{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]][[{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]][[{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]][[{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,32,43,44,45,46,47,48}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]][[{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][2]][[3][4]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{2}]][[{3}][{4}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}][{3,4}]][[{5,6}][{7,8}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}][{4,5,6}]][[{7,8,9}][{10,11,12}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}][{5,6,7,8}]][[{9,10,11,12}][{13,14,15,16}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}][{6,7,8,9,10}]][[{11,12,13,14,15}][{16,17,18,19,20}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}]][[{13,14,15,16,17,18}][{19,20,21,22,23,24}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}]][[{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}]][[{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}]][[{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2][3,4]][[5,6][7,8]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}][{3},{4}]][[{5},{6}][{7},{8}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}][{5,6},{7,8}]][[{9,10},{11,12}][{13,14},{15,16}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}]][[{13,14,15},{16,17,18}][{19,20,21},{22,23,24}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}]][[{17,18,19,20},{21,22,23,24}][{25,26,27,28},{29,30,31,32}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}]][[{21,22,23,24,25},{26,27,28,29,30}][{31,32,33,34,35},{36,37,38,39,40}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}]][[{25,26,27,28,29,30},{31,32,33,34,35,36}][{37,38,39,40,41,42},{43,44,45,46,47,48}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]][[{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49},{50,51,52,53,54,55,56}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]][[{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]][[{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3][4,5,6]][[7,8,9][10,11,12]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}][{4},{5},{6}]][[{7},{8},{9}][{10},{11},{12}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}]][[{13,14},{15,16},{17,18}][{19,20},{21,22},{23,24}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}]][[{19,20,21},{22,23,24},{25,26,27}][{28,29,30},{31,32,33},{34,35,36}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}]][[{25,26,27,28},{29,30,31,32},{33,34,35,36}][{37,38,39,40},{41,42,43,44},{45,46,47,48}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]][[{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}][{46,47,48,49,50},{51,52,53,54,55},{56,57,58,59,60}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]][[{37,38,39,40,41,42},{43,44,45,46,47,48},{49,50,51,52,53,54}][{55,56,57,58,59,60},{61,62,63,64,65,66},{67,68,69,70,71,72}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]][[{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,61,62,63}][{64,65,66,67,68,69,70},{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]][[{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,69,70,71,72}][{73,74,75,76,77,78,79,80},{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]][[{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}][{82,83,84,85,86,87,88,89,90},{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x3x1() {
		// [[[][][]][[][][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][2][3]][[4][5][6]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{2}][{3}]][[{4}][{5}][{6}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}][{3,4}][{5,6}]][[{7,8}][{9,10}][{11,12}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}][{4,5,6}][{7,8,9}]][[{10,11,12}][{13,14,15}][{16,17,18}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}]][[{13,14,15,16}][{17,18,19,20}][{21,22,23,24}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}]][[{16,17,18,19,20}][{21,22,23,24,25}][{26,27,28,29,30}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}]][[{19,20,21,22,23,24}][{25,26,27,28,29,30}][{31,32,33,34,35,36}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}]][[{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35}][{36,37,38,39,40,41,42}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}]][[{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40}][{41,42,43,44,45,46,47,48}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}]][[{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45}][{46,47,48,49,50,51,52,53,54}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2][3,4][5,6]][[7,8][9,10][11,12]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}][{3},{4}][{5},{6}]][[{7},{8}][{9},{10}][{11},{12}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}][{5,6},{7,8}][{9,10},{11,12}]][[{13,14},{15,16}][{17,18},{19,20}][{21,22},{23,24}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}][{13,14,15},{16,17,18}]][[{19,20,21},{22,23,24}][{25,26,27},{28,29,30}][{31,32,33},{34,35,36}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}][{17,18,19,20},{21,22,23,24]][[{25,26,27,28},{29,30,31,32}][{33,34,35,36},{37,38,39,40}][{41,42,43,44},{45,46,47,48}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}][{21,22,23,24,25},{26,27,28,29,30}]][[{31,32,33,34,35},{36,37,38,39,40}][{41,42,43,44,45},{46,47,48,49,50}][{51,52,53,54,55},{56,57,58,59,60}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}][{25,26,27,28,29,30},{31,32,33,34,35,36}]][[{37,38,39,40,41,42},{43,44,45,46,47,48}][{49,50,51,52,53,54},{55,56,57,58,59,60}][{61,62,63,64,65,66},{67,68,69,70,71,72}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]][[{43,44,45,46,47,48,49},{50,51,52,53,54,55,56}][{57,58,59,60,61,62,63},{64,65,66,67,68,69,70}][{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]][[{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64}][{65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80}][{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]][[{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72}][{73,74,75,76,77,78,79,80,81},{82,83,84,85,86,87,88,89,90}][{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3][4,5,6][7,8,9]][[10,11,12][13,14,15][16,17,18]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}][{4},{5},{6}][{7},{8},{9}]][[{10},{11},{12}][{13},{14},{15}][{16},{17},{18}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}][{13,14},{15,16},{17,18}]][[{19,20},{21,22},{23,24}][{25,26},{27,28},{29,30}][{31,32},{33,34},{35,36}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}][{19,20,21},{22,23,24},{25,26,27}]][[{28,29,30},{31,32,33},{34,35,36}][{37,38,39},{40,41,42},{43,44,45}][{46,47,48},{49,50,51},{52,53,54}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}][{25,26,27,28},{29,30,31,32},{33,34,35,36}]][[{37,38,39,40},{41,42,43,44},{45,46,47,48}][{49,50,51,52},{53,54,55,56},{57,58,59,60}][{61,62,63,64},{65,66,67,68},{69,70,71,72}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}][{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}]][[{46,47,48,49,50},{51,52,53,54,55},{56,57,58,59,60}][{61,62,63,64,65},{66,67,68,69,70},{71,72,73,74,75}][{76,77,78,79,80},{81,82,83,84,85},{86,87,88,89,90}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}][{37,38,39,40,41,42},{43,44,45,46,47,48}],{49,50,51,52,53,54}][[{55,56,57,58,59,60},{61,62,63,64,65,66},{67,68,69,70,71,72}][{73,74,75,76,77,78},{79,80,81,82,83,84},{85,86,87,88,89,90}][{91,92,93,94,95,96},{97,98,99,100,101,102},{103,104,105,106,107,108}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,61,62,63}]][[{64,65,66,67,68,69,70},{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}][{85,86,87,88,89,90,91},{92,93,94,95,96,97,98},{99,100,101,102,103,104,105}][{106,107,108,109,110,111,112},{113,114,115,116,117,118,119},{120,121,122,123,124,125,126}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,69,70,71,72}]][[{73,74,75,76,77,78,79,80},{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}][{97,98,99,100,101,102,103,104},{105,106,107,108,109,110,111,112},{113,114,115,116,117,118,119,120}][{121,122,123,124,125,126,127,128},{129,130,131,132,133,134,135,136},{137,138,139,140,141,142,143,144}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}]][[{82,83,84,85,86,87,88,89,90},{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}][{109,110,111,112,113,114,115,116,117},{118,119,120,121,122,123,124,125,126},{127,128,129,130,131,132,133,134,135}][{136,137,138,139,140,141,142,143,144},{145,146,147,148,149,150,151,152,153},{154,155,156,157,158,159,160,161,162}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x1() {
		// [[[]][[]][[]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]][[1]][[1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]][[{1}]][[{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}]][[{1,1}]][[{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}]][[{1,1,1}]][[{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}]][[{1,1,1,1}]][[{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}]][[{1,1,1,1,1}]][[{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}]][[{1,1,1,1,1,1}]][[{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1]][[1,1]][[1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}]][[{1},{1}]][[{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}]][[{1,1},{1,1}]][[{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1]][[1,1,1]][[1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}]][[{1},{1},{1}]][[{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x1() {
		// [[[][]][[][]][[][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1]][[1][1]][[1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}]][[{1}][{1}]][[{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}]][[{1,1}][{1,1}]][[{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][]{1,1,1}][[{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][]{1,1,1,1,1,1}][[{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1]][[1,1][1,1]][[1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}]][[{1},{1}][{1},{1}]][[{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1]][[1,1,1][1,1,1]][[1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x1() {
		// [[[][][]][[][][]][[][][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1][1]][[1][1][1]][[1][1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}][{1}]][[{1}][{1}][{1}]][[{1}][{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1][1,1]][[1,1][1,1][1,1]][[1,1][1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}][{1},{1}]][[{1},{1}][{1},{1}][{1},{1}]][[{1},{1}][{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1][1,1,1]][[1,1,1][1,1,1][1,1,1]][[1,1,1][1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test4d2x3x4x1() {
		// [[[[][][][]][[][][][]][[][][][]]][[[][][][]][[][][][]][[][][][]]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[[1][1][1][1]][[1][1][1][1]][[1][1][1][1]]][[[1][1][1][1]][[1][1][1][1]][[1][1][1][1]]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]]][[[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]]][[[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]]][[[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]][[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]][[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]][[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]][[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[]{1,1,1,1,1,1,1,1},[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]][[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]][[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]]");
		assertTrue(true);
	}
}
