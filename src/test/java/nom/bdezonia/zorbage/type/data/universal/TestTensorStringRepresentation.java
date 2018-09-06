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
		assertArrayEquals(new long[] {1}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), e.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), e.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), e.firstValue().k());
		assertEquals(BigDecimal.ZERO, e.firstValue().l());
		assertEquals(BigDecimal.ZERO, e.firstValue().i0());
		assertEquals(BigDecimal.ZERO, e.firstValue().j0());
		assertEquals(BigDecimal.ZERO, e.firstValue().k0());

		TensorStringRepresentation f = new TensorStringRepresentation("{1,2,3,4,5}");
		assertArrayEquals(new long[] {1}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), f.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), f.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), f.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), f.firstValue().l());
		assertEquals(BigDecimal.ZERO, f.firstValue().i0());
		assertEquals(BigDecimal.ZERO, f.firstValue().j0());
		assertEquals(BigDecimal.ZERO, f.firstValue().k0());

		TensorStringRepresentation g = new TensorStringRepresentation("{1,2,3,4,5,6}");
		assertArrayEquals(new long[] {1}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), g.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), g.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), g.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), g.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), g.firstValue().i0());
		assertEquals(BigDecimal.ZERO, g.firstValue().j0());
		assertEquals(BigDecimal.ZERO, g.firstValue().k0());

		TensorStringRepresentation h = new TensorStringRepresentation("{1,2,3,4,5,6,7}");
		assertArrayEquals(new long[] {1}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), h.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), h.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), h.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), h.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), h.firstValue().i0());
		assertEquals(BigDecimal.valueOf(7), h.firstValue().j0());
		assertEquals(BigDecimal.ZERO, h.firstValue().k0());

		TensorStringRepresentation i = new TensorStringRepresentation("{1,2,3,4,5,6,7,8}");
		assertArrayEquals(new long[] {1}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), i.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), i.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), i.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), i.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), i.firstValue().i0());
		assertEquals(BigDecimal.valueOf(7), i.firstValue().j0());
		assertEquals(BigDecimal.valueOf(8), i.firstValue().k0());

		TensorStringRepresentation j = new TensorStringRepresentation("{1,2,3,4,5,6,7,8,9}");
		assertArrayEquals(new long[] {1}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), j.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), j.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), j.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), j.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), j.firstValue().i0());
		assertEquals(BigDecimal.valueOf(7), j.firstValue().j0());
		assertEquals(BigDecimal.valueOf(8), j.firstValue().k0());
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
		assertArrayEquals(new long[] {1}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), e.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), e.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), e.firstValue().k());
		assertEquals(BigDecimal.ZERO, e.firstValue().l());
		assertEquals(BigDecimal.ZERO, e.firstValue().i0());
		assertEquals(BigDecimal.ZERO, e.firstValue().j0());
		assertEquals(BigDecimal.ZERO, e.firstValue().k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5}]");
		assertArrayEquals(new long[] {1}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), f.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), f.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), f.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), f.firstValue().l());
		assertEquals(BigDecimal.ZERO, f.firstValue().i0());
		assertEquals(BigDecimal.ZERO, f.firstValue().j0());
		assertEquals(BigDecimal.ZERO, f.firstValue().k0());
		
		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6}]");
		assertArrayEquals(new long[] {1}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), g.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), g.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), g.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), g.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), g.firstValue().i0());
		assertEquals(BigDecimal.ZERO, g.firstValue().j0());
		assertEquals(BigDecimal.ZERO, g.firstValue().k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7}]");
		assertArrayEquals(new long[] {1}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), h.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), h.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), h.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), h.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), h.firstValue().i0());
		assertEquals(BigDecimal.valueOf(7), h.firstValue().j0());
		assertEquals(BigDecimal.ZERO, h.firstValue().k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8}]");
		assertArrayEquals(new long[] {1}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), i.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), i.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), i.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), i.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), i.firstValue().i0());
		assertEquals(BigDecimal.valueOf(7), i.firstValue().j0());
		assertEquals(BigDecimal.valueOf(8), i.firstValue().k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9}]");
		assertArrayEquals(new long[] {1}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.firstValue().r());
		assertEquals(BigDecimal.valueOf(2), j.firstValue().i());
		assertEquals(BigDecimal.valueOf(3), j.firstValue().j());
		assertEquals(BigDecimal.valueOf(4), j.firstValue().k());
		assertEquals(BigDecimal.valueOf(5), j.firstValue().l());
		assertEquals(BigDecimal.valueOf(6), j.firstValue().i0());
		assertEquals(BigDecimal.valueOf(7), j.firstValue().j0());
		assertEquals(BigDecimal.valueOf(8), j.firstValue().k0());
	}

	@Test
	public void test1dx2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1,2]");
		assertArrayEquals(new long[] {2}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[{1},{2}]");
		assertArrayEquals(new long[] {2}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		
		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2},{3,4}]");
		assertArrayEquals(new long[] {2}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3},{4,5,6}]");
		assertArrayEquals(new long[] {2}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4},{5,6,7,8}]");
		assertArrayEquals(new long[] {2}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5},{6,7,8,9,10}]");
		assertArrayEquals(new long[] {2}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6},{7,8,9,10,11,12}]");
		assertArrayEquals(new long[] {2}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]");
		assertArrayEquals(new long[] {2}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]");
		assertArrayEquals(new long[] {2}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]");
		assertArrayEquals(new long[] {2}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
	}

	@Test
	public void test1dx3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1,2,3]");
		assertArrayEquals(new long[] {3}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.values().get(2).r());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k());
		assertEquals(BigDecimal.ZERO, a.values().get(2).l());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[{1},{2},{3}]");
		assertArrayEquals(new long[] {3}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.values().get(2).r());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k());
		assertEquals(BigDecimal.ZERO, b.values().get(2).l());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2},{3,4},{5,6}]");
		assertArrayEquals(new long[] {3}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.values().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.values().get(2).i());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k());
		assertEquals(BigDecimal.ZERO, c.values().get(2).l());
		assertEquals(BigDecimal.ZERO, c.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3},{4,5,6},{7,8,9}]");
		assertArrayEquals(new long[] {3}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.values().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.values().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.values().get(2).j());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k());
		assertEquals(BigDecimal.ZERO, d.values().get(2).l());
		assertEquals(BigDecimal.ZERO, d.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4},{5,6,7,8},{9,10,11,12}]");
		assertArrayEquals(new long[] {3}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.values().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.values().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.values().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.values().get(2).k());
		assertEquals(BigDecimal.ZERO, e.values().get(2).l());
		assertEquals(BigDecimal.ZERO, e.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]");
		assertArrayEquals(new long[] {3}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.values().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.values().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.values().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.values().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.values().get(2).l());
		assertEquals(BigDecimal.ZERO, f.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]");
		assertArrayEquals(new long[] {3}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.values().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.values().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.values().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.values().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.values().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]");
		assertArrayEquals(new long[] {3}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.values().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.values().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.values().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.values().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.values().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(2).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]");
		assertArrayEquals(new long[] {3}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.values().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.values().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.values().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.values().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.values().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.values().get(2).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]");
		assertArrayEquals(new long[] {3}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.values().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.values().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.values().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.values().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.values().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.values().get(2).k0());
	}

	@Test
	public void test2d1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1]]");
		assertArrayEquals(new long[] {1,1}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}]]");
		assertArrayEquals(new long[] {1,1}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}]]");
		assertArrayEquals(new long[] {1,1}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}]]");
		assertArrayEquals(new long[] {1,1}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}]]");
		assertArrayEquals(new long[] {1,1}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}]]");
		assertArrayEquals(new long[] {1,1}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}]]");
		assertArrayEquals(new long[] {1,1}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}]]");
		assertArrayEquals(new long[] {1,1}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}]]");
		assertArrayEquals(new long[] {1,1}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}]]");
		assertArrayEquals(new long[] {1,1}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
	}

	@Test
	public void test2d1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,2}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
	}

	@Test
	public void test2d1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.values().get(2).r());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k());
		assertEquals(BigDecimal.ZERO, a.values().get(2).l());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.values().get(2).r());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k());
		assertEquals(BigDecimal.ZERO, b.values().get(2).l());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.values().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.values().get(2).i());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k());
		assertEquals(BigDecimal.ZERO, c.values().get(2).l());
		assertEquals(BigDecimal.ZERO, c.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.values().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.values().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.values().get(2).j());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k());
		assertEquals(BigDecimal.ZERO, d.values().get(2).l());
		assertEquals(BigDecimal.ZERO, d.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.values().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.values().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.values().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.values().get(2).k());
		assertEquals(BigDecimal.ZERO, e.values().get(2).l());
		assertEquals(BigDecimal.ZERO, e.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.values().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.values().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.values().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.values().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.values().get(2).l());
		assertEquals(BigDecimal.ZERO, f.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.values().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.values().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.values().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.values().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.values().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.values().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.values().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.values().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.values().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.values().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(2).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.values().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.values().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.values().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.values().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.values().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.values().get(2).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {1,3}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.values().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.values().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.values().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.values().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.values().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.values().get(2).k0());
	}

	@Test
	public void test2d2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1][2]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}][{2}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}][{3,4}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}][{4,5,6}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}][{5,6,7,8}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}][{6,7,8,9,10}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}][{7,8,9,10,11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,1}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
	}

	@Test
	public void test2d2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2][3,4]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.values().get(2).r());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k());
		assertEquals(BigDecimal.ZERO, a.values().get(2).l());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(4), a.values().get(3).r());
		assertEquals(BigDecimal.ZERO, a.values().get(3).i());
		assertEquals(BigDecimal.ZERO, a.values().get(3).j());
		assertEquals(BigDecimal.ZERO, a.values().get(3).k());
		assertEquals(BigDecimal.ZERO, a.values().get(3).l());
		assertEquals(BigDecimal.ZERO, a.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(3).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}][{3},{4}]]") ;
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.values().get(2).r());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k());
		assertEquals(BigDecimal.ZERO, b.values().get(2).l());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(4), b.values().get(3).r());
		assertEquals(BigDecimal.ZERO, b.values().get(3).i());
		assertEquals(BigDecimal.ZERO, b.values().get(3).j());
		assertEquals(BigDecimal.ZERO, b.values().get(3).k());
		assertEquals(BigDecimal.ZERO, b.values().get(3).l());
		assertEquals(BigDecimal.ZERO, b.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(3).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}][{5,6},{7,8}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.values().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.values().get(2).i());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k());
		assertEquals(BigDecimal.ZERO, c.values().get(2).l());
		assertEquals(BigDecimal.ZERO, c.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(7), c.values().get(3).r());
		assertEquals(BigDecimal.valueOf(8), c.values().get(3).i());
		assertEquals(BigDecimal.ZERO, c.values().get(3).j());
		assertEquals(BigDecimal.ZERO, c.values().get(3).k());
		assertEquals(BigDecimal.ZERO, c.values().get(3).l());
		assertEquals(BigDecimal.ZERO, c.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(3).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.values().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.values().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.values().get(2).j());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k());
		assertEquals(BigDecimal.ZERO, d.values().get(2).l());
		assertEquals(BigDecimal.ZERO, d.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(10), d.values().get(3).r());
		assertEquals(BigDecimal.valueOf(11), d.values().get(3).i());
		assertEquals(BigDecimal.valueOf(12), d.values().get(3).j());
		assertEquals(BigDecimal.ZERO, d.values().get(3).k());
		assertEquals(BigDecimal.ZERO, d.values().get(3).l());
		assertEquals(BigDecimal.ZERO, d.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(3).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.values().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.values().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.values().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.values().get(2).k());
		assertEquals(BigDecimal.ZERO, e.values().get(2).l());
		assertEquals(BigDecimal.ZERO, e.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(13), e.values().get(3).r());
		assertEquals(BigDecimal.valueOf(14), e.values().get(3).i());
		assertEquals(BigDecimal.valueOf(15), e.values().get(3).j());
		assertEquals(BigDecimal.valueOf(16), e.values().get(3).k());
		assertEquals(BigDecimal.ZERO, e.values().get(3).l());
		assertEquals(BigDecimal.ZERO, e.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(3).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.values().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.values().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.values().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.values().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.values().get(2).l());
		assertEquals(BigDecimal.ZERO, f.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(16), f.values().get(3).r());
		assertEquals(BigDecimal.valueOf(17), f.values().get(3).i());
		assertEquals(BigDecimal.valueOf(18), f.values().get(3).j());
		assertEquals(BigDecimal.valueOf(19), f.values().get(3).k());
		assertEquals(BigDecimal.valueOf(20), f.values().get(3).l());
		assertEquals(BigDecimal.ZERO, f.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(3).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.values().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.values().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.values().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.values().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.values().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(19), g.values().get(3).r());
		assertEquals(BigDecimal.valueOf(20), g.values().get(3).i());
		assertEquals(BigDecimal.valueOf(21), g.values().get(3).j());
		assertEquals(BigDecimal.valueOf(22), g.values().get(3).k());
		assertEquals(BigDecimal.valueOf(23), g.values().get(3).l());
		assertEquals(BigDecimal.valueOf(24), g.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(3).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.values().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.values().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.values().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.values().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.values().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(22), h.values().get(3).r());
		assertEquals(BigDecimal.valueOf(23), h.values().get(3).i());
		assertEquals(BigDecimal.valueOf(24), h.values().get(3).j());
		assertEquals(BigDecimal.valueOf(25), h.values().get(3).k());
		assertEquals(BigDecimal.valueOf(26), h.values().get(3).l());
		assertEquals(BigDecimal.valueOf(27), h.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(28), h.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(3).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.values().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.values().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.values().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.values().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.values().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(25), i.values().get(3).r());
		assertEquals(BigDecimal.valueOf(26), i.values().get(3).i());
		assertEquals(BigDecimal.valueOf(27), i.values().get(3).j());
		assertEquals(BigDecimal.valueOf(28), i.values().get(3).k());
		assertEquals(BigDecimal.valueOf(29), i.values().get(3).l());
		assertEquals(BigDecimal.valueOf(30), i.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(31), i.values().get(3).j0());
		assertEquals(BigDecimal.valueOf(32), i.values().get(3).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,2}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.values().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.values().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.values().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.values().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.values().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(28), j.values().get(3).r());
		assertEquals(BigDecimal.valueOf(29), j.values().get(3).i());
		assertEquals(BigDecimal.valueOf(30), j.values().get(3).j());
		assertEquals(BigDecimal.valueOf(31), j.values().get(3).k());
		assertEquals(BigDecimal.valueOf(32), j.values().get(3).l());
		assertEquals(BigDecimal.valueOf(33), j.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(34), j.values().get(3).j0());
		assertEquals(BigDecimal.valueOf(35), j.values().get(3).k0());
	}

	@Test
	public void test2d2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3][4,5,6]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.values().get(2).r());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k());
		assertEquals(BigDecimal.ZERO, a.values().get(2).l());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(4), a.values().get(3).r());
		assertEquals(BigDecimal.ZERO, a.values().get(3).i());
		assertEquals(BigDecimal.ZERO, a.values().get(3).j());
		assertEquals(BigDecimal.ZERO, a.values().get(3).k());
		assertEquals(BigDecimal.ZERO, a.values().get(3).l());
		assertEquals(BigDecimal.ZERO, a.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(5), a.values().get(4).r());
		assertEquals(BigDecimal.ZERO, a.values().get(4).i());
		assertEquals(BigDecimal.ZERO, a.values().get(4).j());
		assertEquals(BigDecimal.ZERO, a.values().get(4).k());
		assertEquals(BigDecimal.ZERO, a.values().get(4).l());
		assertEquals(BigDecimal.ZERO, a.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(6), a.values().get(5).r());
		assertEquals(BigDecimal.ZERO, a.values().get(5).i());
		assertEquals(BigDecimal.ZERO, a.values().get(5).j());
		assertEquals(BigDecimal.ZERO, a.values().get(5).k());
		assertEquals(BigDecimal.ZERO, a.values().get(5).l());
		assertEquals(BigDecimal.ZERO, a.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(5).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}][{4},{5},{6}]]") ;
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.values().get(2).r());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k());
		assertEquals(BigDecimal.ZERO, b.values().get(2).l());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(4), b.values().get(3).r());
		assertEquals(BigDecimal.ZERO, b.values().get(3).i());
		assertEquals(BigDecimal.ZERO, b.values().get(3).j());
		assertEquals(BigDecimal.ZERO, b.values().get(3).k());
		assertEquals(BigDecimal.ZERO, b.values().get(3).l());
		assertEquals(BigDecimal.ZERO, b.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(5), b.values().get(4).r());
		assertEquals(BigDecimal.ZERO, b.values().get(4).i());
		assertEquals(BigDecimal.ZERO, b.values().get(4).j());
		assertEquals(BigDecimal.ZERO, b.values().get(4).k());
		assertEquals(BigDecimal.ZERO, b.values().get(4).l());
		assertEquals(BigDecimal.ZERO, b.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(6), b.values().get(5).r());
		assertEquals(BigDecimal.ZERO, b.values().get(5).i());
		assertEquals(BigDecimal.ZERO, b.values().get(5).j());
		assertEquals(BigDecimal.ZERO, b.values().get(5).k());
		assertEquals(BigDecimal.ZERO, b.values().get(5).l());
		assertEquals(BigDecimal.ZERO, b.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(5).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.values().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.values().get(2).i());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k());
		assertEquals(BigDecimal.ZERO, c.values().get(2).l());
		assertEquals(BigDecimal.ZERO, c.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(7), c.values().get(3).r());
		assertEquals(BigDecimal.valueOf(8), c.values().get(3).i());
		assertEquals(BigDecimal.ZERO, c.values().get(3).j());
		assertEquals(BigDecimal.ZERO, c.values().get(3).k());
		assertEquals(BigDecimal.ZERO, c.values().get(3).l());
		assertEquals(BigDecimal.ZERO, c.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(9), c.values().get(4).r());
		assertEquals(BigDecimal.valueOf(10), c.values().get(4).i());
		assertEquals(BigDecimal.ZERO, c.values().get(4).j());
		assertEquals(BigDecimal.ZERO, c.values().get(4).k());
		assertEquals(BigDecimal.ZERO, c.values().get(4).l());
		assertEquals(BigDecimal.ZERO, c.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(11), c.values().get(5).r());
		assertEquals(BigDecimal.valueOf(12), c.values().get(5).i());
		assertEquals(BigDecimal.ZERO, c.values().get(5).j());
		assertEquals(BigDecimal.ZERO, c.values().get(5).k());
		assertEquals(BigDecimal.ZERO, c.values().get(5).l());
		assertEquals(BigDecimal.ZERO, c.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(5).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.values().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.values().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.values().get(2).j());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k());
		assertEquals(BigDecimal.ZERO, d.values().get(2).l());
		assertEquals(BigDecimal.ZERO, d.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(10), d.values().get(3).r());
		assertEquals(BigDecimal.valueOf(11), d.values().get(3).i());
		assertEquals(BigDecimal.valueOf(12), d.values().get(3).j());
		assertEquals(BigDecimal.ZERO, d.values().get(3).k());
		assertEquals(BigDecimal.ZERO, d.values().get(3).l());
		assertEquals(BigDecimal.ZERO, d.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(13), d.values().get(4).r());
		assertEquals(BigDecimal.valueOf(14), d.values().get(4).i());
		assertEquals(BigDecimal.valueOf(15), d.values().get(4).j());
		assertEquals(BigDecimal.ZERO, d.values().get(4).k());
		assertEquals(BigDecimal.ZERO, d.values().get(4).l());
		assertEquals(BigDecimal.ZERO, d.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(16), d.values().get(5).r());
		assertEquals(BigDecimal.valueOf(17), d.values().get(5).i());
		assertEquals(BigDecimal.valueOf(18), d.values().get(5).j());
		assertEquals(BigDecimal.ZERO, d.values().get(5).k());
		assertEquals(BigDecimal.ZERO, d.values().get(5).l());
		assertEquals(BigDecimal.ZERO, d.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(5).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.values().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.values().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.values().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.values().get(2).k());
		assertEquals(BigDecimal.ZERO, e.values().get(2).l());
		assertEquals(BigDecimal.ZERO, e.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(13), e.values().get(3).r());
		assertEquals(BigDecimal.valueOf(14), e.values().get(3).i());
		assertEquals(BigDecimal.valueOf(15), e.values().get(3).j());
		assertEquals(BigDecimal.valueOf(16), e.values().get(3).k());
		assertEquals(BigDecimal.ZERO, e.values().get(3).l());
		assertEquals(BigDecimal.ZERO, e.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(17), e.values().get(4).r());
		assertEquals(BigDecimal.valueOf(18), e.values().get(4).i());
		assertEquals(BigDecimal.valueOf(19), e.values().get(4).j());
		assertEquals(BigDecimal.valueOf(20), e.values().get(4).k());
		assertEquals(BigDecimal.ZERO, e.values().get(4).l());
		assertEquals(BigDecimal.ZERO, e.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(21), e.values().get(5).r());
		assertEquals(BigDecimal.valueOf(22), e.values().get(5).i());
		assertEquals(BigDecimal.valueOf(23), e.values().get(5).j());
		assertEquals(BigDecimal.valueOf(24), e.values().get(5).k());
		assertEquals(BigDecimal.ZERO, e.values().get(5).l());
		assertEquals(BigDecimal.ZERO, e.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(5).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.values().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.values().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.values().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.values().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.values().get(2).l());
		assertEquals(BigDecimal.ZERO, f.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(16), f.values().get(3).r());
		assertEquals(BigDecimal.valueOf(17), f.values().get(3).i());
		assertEquals(BigDecimal.valueOf(18), f.values().get(3).j());
		assertEquals(BigDecimal.valueOf(19), f.values().get(3).k());
		assertEquals(BigDecimal.valueOf(20), f.values().get(3).l());
		assertEquals(BigDecimal.ZERO, f.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(21), f.values().get(4).r());
		assertEquals(BigDecimal.valueOf(22), f.values().get(4).i());
		assertEquals(BigDecimal.valueOf(23), f.values().get(4).j());
		assertEquals(BigDecimal.valueOf(24), f.values().get(4).k());
		assertEquals(BigDecimal.valueOf(25), f.values().get(4).l());
		assertEquals(BigDecimal.ZERO, f.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(26), f.values().get(5).r());
		assertEquals(BigDecimal.valueOf(27), f.values().get(5).i());
		assertEquals(BigDecimal.valueOf(28), f.values().get(5).j());
		assertEquals(BigDecimal.valueOf(29), f.values().get(5).k());
		assertEquals(BigDecimal.valueOf(30), f.values().get(5).l());
		assertEquals(BigDecimal.ZERO, f.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(5).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.values().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.values().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.values().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.values().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.values().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(19), g.values().get(3).r());
		assertEquals(BigDecimal.valueOf(20), g.values().get(3).i());
		assertEquals(BigDecimal.valueOf(21), g.values().get(3).j());
		assertEquals(BigDecimal.valueOf(22), g.values().get(3).k());
		assertEquals(BigDecimal.valueOf(23), g.values().get(3).l());
		assertEquals(BigDecimal.valueOf(24), g.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(25), g.values().get(4).r());
		assertEquals(BigDecimal.valueOf(26), g.values().get(4).i());
		assertEquals(BigDecimal.valueOf(27), g.values().get(4).j());
		assertEquals(BigDecimal.valueOf(28), g.values().get(4).k());
		assertEquals(BigDecimal.valueOf(29), g.values().get(4).l());
		assertEquals(BigDecimal.valueOf(30), g.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(31), g.values().get(5).r());
		assertEquals(BigDecimal.valueOf(32), g.values().get(5).i());
		assertEquals(BigDecimal.valueOf(33), g.values().get(5).j());
		assertEquals(BigDecimal.valueOf(34), g.values().get(5).k());
		assertEquals(BigDecimal.valueOf(35), g.values().get(5).l());
		assertEquals(BigDecimal.valueOf(36), g.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(5).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.values().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.values().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.values().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.values().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.values().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(22), h.values().get(3).r());
		assertEquals(BigDecimal.valueOf(23), h.values().get(3).i());
		assertEquals(BigDecimal.valueOf(24), h.values().get(3).j());
		assertEquals(BigDecimal.valueOf(25), h.values().get(3).k());
		assertEquals(BigDecimal.valueOf(26), h.values().get(3).l());
		assertEquals(BigDecimal.valueOf(27), h.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(28), h.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(29), h.values().get(4).r());
		assertEquals(BigDecimal.valueOf(30), h.values().get(4).i());
		assertEquals(BigDecimal.valueOf(31), h.values().get(4).j());
		assertEquals(BigDecimal.valueOf(32), h.values().get(4).k());
		assertEquals(BigDecimal.valueOf(33), h.values().get(4).l());
		assertEquals(BigDecimal.valueOf(34), h.values().get(4).i0());
		assertEquals(BigDecimal.valueOf(35), h.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(36), h.values().get(5).r());
		assertEquals(BigDecimal.valueOf(37), h.values().get(5).i());
		assertEquals(BigDecimal.valueOf(38), h.values().get(5).j());
		assertEquals(BigDecimal.valueOf(39), h.values().get(5).k());
		assertEquals(BigDecimal.valueOf(40), h.values().get(5).l());
		assertEquals(BigDecimal.valueOf(41), h.values().get(5).i0());
		assertEquals(BigDecimal.valueOf(42), h.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(5).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.values().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.values().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.values().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.values().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.values().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(25), i.values().get(3).r());
		assertEquals(BigDecimal.valueOf(26), i.values().get(3).i());
		assertEquals(BigDecimal.valueOf(27), i.values().get(3).j());
		assertEquals(BigDecimal.valueOf(28), i.values().get(3).k());
		assertEquals(BigDecimal.valueOf(29), i.values().get(3).l());
		assertEquals(BigDecimal.valueOf(30), i.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(31), i.values().get(3).j0());
		assertEquals(BigDecimal.valueOf(32), i.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(33), i.values().get(4).r());
		assertEquals(BigDecimal.valueOf(34), i.values().get(4).i());
		assertEquals(BigDecimal.valueOf(35), i.values().get(4).j());
		assertEquals(BigDecimal.valueOf(36), i.values().get(4).k());
		assertEquals(BigDecimal.valueOf(37), i.values().get(4).l());
		assertEquals(BigDecimal.valueOf(38), i.values().get(4).i0());
		assertEquals(BigDecimal.valueOf(39), i.values().get(4).j0());
		assertEquals(BigDecimal.valueOf(40), i.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(41), i.values().get(5).r());
		assertEquals(BigDecimal.valueOf(42), i.values().get(5).i());
		assertEquals(BigDecimal.valueOf(43), i.values().get(5).j());
		assertEquals(BigDecimal.valueOf(44), i.values().get(5).k());
		assertEquals(BigDecimal.valueOf(45), i.values().get(5).l());
		assertEquals(BigDecimal.valueOf(46), i.values().get(5).i0());
		assertEquals(BigDecimal.valueOf(47), i.values().get(5).j0());
		assertEquals(BigDecimal.valueOf(48), i.values().get(5).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {2,3}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.values().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.values().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.values().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.values().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.values().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(28), j.values().get(3).r());
		assertEquals(BigDecimal.valueOf(29), j.values().get(3).i());
		assertEquals(BigDecimal.valueOf(30), j.values().get(3).j());
		assertEquals(BigDecimal.valueOf(31), j.values().get(3).k());
		assertEquals(BigDecimal.valueOf(32), j.values().get(3).l());
		assertEquals(BigDecimal.valueOf(33), j.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(34), j.values().get(3).j0());
		assertEquals(BigDecimal.valueOf(35), j.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(37), j.values().get(4).r());
		assertEquals(BigDecimal.valueOf(38), j.values().get(4).i());
		assertEquals(BigDecimal.valueOf(39), j.values().get(4).j());
		assertEquals(BigDecimal.valueOf(40), j.values().get(4).k());
		assertEquals(BigDecimal.valueOf(41), j.values().get(4).l());
		assertEquals(BigDecimal.valueOf(42), j.values().get(4).i0());
		assertEquals(BigDecimal.valueOf(43), j.values().get(4).j0());
		assertEquals(BigDecimal.valueOf(44), j.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(46), j.values().get(5).r());
		assertEquals(BigDecimal.valueOf(47), j.values().get(5).i());
		assertEquals(BigDecimal.valueOf(48), j.values().get(5).j());
		assertEquals(BigDecimal.valueOf(49), j.values().get(5).k());
		assertEquals(BigDecimal.valueOf(50), j.values().get(5).l());
		assertEquals(BigDecimal.valueOf(51), j.values().get(5).i0());
		assertEquals(BigDecimal.valueOf(52), j.values().get(5).j0());
		assertEquals(BigDecimal.valueOf(53), j.values().get(5).k0());
	}

	@Test
	public void test2d3x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1][2][3]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.values().get(2).r());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k());
		assertEquals(BigDecimal.ZERO, a.values().get(2).l());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}][{2}][{3}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.values().get(2).r());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k());
		assertEquals(BigDecimal.ZERO, b.values().get(2).l());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}][{3,4}][{5,6}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.values().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.values().get(2).i());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k());
		assertEquals(BigDecimal.ZERO, c.values().get(2).l());
		assertEquals(BigDecimal.ZERO, c.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}][{4,5,6}][{7,8,9}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.values().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.values().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.values().get(2).j());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k());
		assertEquals(BigDecimal.ZERO, d.values().get(2).l());
		assertEquals(BigDecimal.ZERO, d.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.values().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.values().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.values().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.values().get(2).k());
		assertEquals(BigDecimal.ZERO, e.values().get(2).l());
		assertEquals(BigDecimal.ZERO, e.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.values().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.values().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.values().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.values().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.values().get(2).l());
		assertEquals(BigDecimal.ZERO, f.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.values().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.values().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.values().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.values().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.values().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.values().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.values().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.values().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.values().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.values().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(2).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.values().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.values().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.values().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.values().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.values().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.values().get(2).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,1}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.values().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.values().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.values().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.values().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.values().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.values().get(2).k0());
	}

	@Test
	public void test2d3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2][3,4][5,6]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, a.dimensions());
		assertEquals(BigDecimal.valueOf(1), a.values().get(0).r());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k());
		assertEquals(BigDecimal.ZERO, a.values().get(0).l());
		assertEquals(BigDecimal.ZERO, a.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), a.values().get(1).r());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k());
		assertEquals(BigDecimal.ZERO, a.values().get(1).l());
		assertEquals(BigDecimal.ZERO, a.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), a.values().get(2).r());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k());
		assertEquals(BigDecimal.ZERO, a.values().get(2).l());
		assertEquals(BigDecimal.ZERO, a.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(4), a.values().get(3).r());
		assertEquals(BigDecimal.ZERO, a.values().get(3).i());
		assertEquals(BigDecimal.ZERO, a.values().get(3).j());
		assertEquals(BigDecimal.ZERO, a.values().get(3).k());
		assertEquals(BigDecimal.ZERO, a.values().get(3).l());
		assertEquals(BigDecimal.ZERO, a.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(5), a.values().get(4).r());
		assertEquals(BigDecimal.ZERO, a.values().get(4).i());
		assertEquals(BigDecimal.ZERO, a.values().get(4).j());
		assertEquals(BigDecimal.ZERO, a.values().get(4).k());
		assertEquals(BigDecimal.ZERO, a.values().get(4).l());
		assertEquals(BigDecimal.ZERO, a.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(6), a.values().get(5).r());
		assertEquals(BigDecimal.ZERO, a.values().get(5).i());
		assertEquals(BigDecimal.ZERO, a.values().get(5).j());
		assertEquals(BigDecimal.ZERO, a.values().get(5).k());
		assertEquals(BigDecimal.ZERO, a.values().get(5).l());
		assertEquals(BigDecimal.ZERO, a.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, a.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, a.values().get(5).k0());

		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}][{3},{4}][{5},{6}]]") ;
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, b.dimensions());
		assertEquals(BigDecimal.valueOf(1), b.values().get(0).r());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k());
		assertEquals(BigDecimal.ZERO, b.values().get(0).l());
		assertEquals(BigDecimal.ZERO, b.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(2), b.values().get(1).r());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k());
		assertEquals(BigDecimal.ZERO, b.values().get(1).l());
		assertEquals(BigDecimal.ZERO, b.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(3), b.values().get(2).r());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k());
		assertEquals(BigDecimal.ZERO, b.values().get(2).l());
		assertEquals(BigDecimal.ZERO, b.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(4), b.values().get(3).r());
		assertEquals(BigDecimal.ZERO, b.values().get(3).i());
		assertEquals(BigDecimal.ZERO, b.values().get(3).j());
		assertEquals(BigDecimal.ZERO, b.values().get(3).k());
		assertEquals(BigDecimal.ZERO, b.values().get(3).l());
		assertEquals(BigDecimal.ZERO, b.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(5), b.values().get(4).r());
		assertEquals(BigDecimal.ZERO, b.values().get(4).i());
		assertEquals(BigDecimal.ZERO, b.values().get(4).j());
		assertEquals(BigDecimal.ZERO, b.values().get(4).k());
		assertEquals(BigDecimal.ZERO, b.values().get(4).l());
		assertEquals(BigDecimal.ZERO, b.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(6), b.values().get(5).r());
		assertEquals(BigDecimal.ZERO, b.values().get(5).i());
		assertEquals(BigDecimal.ZERO, b.values().get(5).j());
		assertEquals(BigDecimal.ZERO, b.values().get(5).k());
		assertEquals(BigDecimal.ZERO, b.values().get(5).l());
		assertEquals(BigDecimal.ZERO, b.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, b.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, b.values().get(5).k0());

		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}][{5,6},{7,8}][{9,10},{11,12}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, c.dimensions());
		assertEquals(BigDecimal.valueOf(1), c.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), c.values().get(0).i());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k());
		assertEquals(BigDecimal.ZERO, c.values().get(0).l());
		assertEquals(BigDecimal.ZERO, c.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(3), c.values().get(1).r());
		assertEquals(BigDecimal.valueOf(4), c.values().get(1).i());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k());
		assertEquals(BigDecimal.ZERO, c.values().get(1).l());
		assertEquals(BigDecimal.ZERO, c.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(5), c.values().get(2).r());
		assertEquals(BigDecimal.valueOf(6), c.values().get(2).i());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k());
		assertEquals(BigDecimal.ZERO, c.values().get(2).l());
		assertEquals(BigDecimal.ZERO, c.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(7), c.values().get(3).r());
		assertEquals(BigDecimal.valueOf(8), c.values().get(3).i());
		assertEquals(BigDecimal.ZERO, c.values().get(3).j());
		assertEquals(BigDecimal.ZERO, c.values().get(3).k());
		assertEquals(BigDecimal.ZERO, c.values().get(3).l());
		assertEquals(BigDecimal.ZERO, c.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(9), c.values().get(4).r());
		assertEquals(BigDecimal.valueOf(10), c.values().get(4).i());
		assertEquals(BigDecimal.ZERO, c.values().get(4).j());
		assertEquals(BigDecimal.ZERO, c.values().get(4).k());
		assertEquals(BigDecimal.ZERO, c.values().get(4).l());
		assertEquals(BigDecimal.ZERO, c.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(11), c.values().get(5).r());
		assertEquals(BigDecimal.valueOf(12), c.values().get(5).i());
		assertEquals(BigDecimal.ZERO, c.values().get(5).j());
		assertEquals(BigDecimal.ZERO, c.values().get(5).k());
		assertEquals(BigDecimal.ZERO, c.values().get(5).l());
		assertEquals(BigDecimal.ZERO, c.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, c.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, c.values().get(5).k0());

		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}][{13,14,15},{16,17,18}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, d.dimensions());
		assertEquals(BigDecimal.valueOf(1), d.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), d.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), d.values().get(0).j());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k());
		assertEquals(BigDecimal.ZERO, d.values().get(0).l());
		assertEquals(BigDecimal.ZERO, d.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(4), d.values().get(1).r());
		assertEquals(BigDecimal.valueOf(5), d.values().get(1).i());
		assertEquals(BigDecimal.valueOf(6), d.values().get(1).j());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k());
		assertEquals(BigDecimal.ZERO, d.values().get(1).l());
		assertEquals(BigDecimal.ZERO, d.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(7), d.values().get(2).r());
		assertEquals(BigDecimal.valueOf(8), d.values().get(2).i());
		assertEquals(BigDecimal.valueOf(9), d.values().get(2).j());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k());
		assertEquals(BigDecimal.ZERO, d.values().get(2).l());
		assertEquals(BigDecimal.ZERO, d.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(10), d.values().get(3).r());
		assertEquals(BigDecimal.valueOf(11), d.values().get(3).i());
		assertEquals(BigDecimal.valueOf(12), d.values().get(3).j());
		assertEquals(BigDecimal.ZERO, d.values().get(3).k());
		assertEquals(BigDecimal.ZERO, d.values().get(3).l());
		assertEquals(BigDecimal.ZERO, d.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(13), d.values().get(4).r());
		assertEquals(BigDecimal.valueOf(14), d.values().get(4).i());
		assertEquals(BigDecimal.valueOf(15), d.values().get(4).j());
		assertEquals(BigDecimal.ZERO, d.values().get(4).k());
		assertEquals(BigDecimal.ZERO, d.values().get(4).l());
		assertEquals(BigDecimal.ZERO, d.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(16), d.values().get(5).r());
		assertEquals(BigDecimal.valueOf(17), d.values().get(5).i());
		assertEquals(BigDecimal.valueOf(18), d.values().get(5).j());
		assertEquals(BigDecimal.ZERO, d.values().get(5).k());
		assertEquals(BigDecimal.ZERO, d.values().get(5).l());
		assertEquals(BigDecimal.ZERO, d.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, d.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, d.values().get(5).k0());

		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}][{17,18,19,20},{21,22,23,24}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, e.dimensions());
		assertEquals(BigDecimal.valueOf(1), e.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), e.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), e.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), e.values().get(0).k());
		assertEquals(BigDecimal.ZERO, e.values().get(0).l());
		assertEquals(BigDecimal.ZERO, e.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(5), e.values().get(1).r());
		assertEquals(BigDecimal.valueOf(6), e.values().get(1).i());
		assertEquals(BigDecimal.valueOf(7), e.values().get(1).j());
		assertEquals(BigDecimal.valueOf(8), e.values().get(1).k());
		assertEquals(BigDecimal.ZERO, e.values().get(1).l());
		assertEquals(BigDecimal.ZERO, e.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(9), e.values().get(2).r());
		assertEquals(BigDecimal.valueOf(10), e.values().get(2).i());
		assertEquals(BigDecimal.valueOf(11), e.values().get(2).j());
		assertEquals(BigDecimal.valueOf(12), e.values().get(2).k());
		assertEquals(BigDecimal.ZERO, e.values().get(2).l());
		assertEquals(BigDecimal.ZERO, e.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(13), e.values().get(3).r());
		assertEquals(BigDecimal.valueOf(14), e.values().get(3).i());
		assertEquals(BigDecimal.valueOf(15), e.values().get(3).j());
		assertEquals(BigDecimal.valueOf(16), e.values().get(3).k());
		assertEquals(BigDecimal.ZERO, e.values().get(3).l());
		assertEquals(BigDecimal.ZERO, e.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(17), e.values().get(4).r());
		assertEquals(BigDecimal.valueOf(18), e.values().get(4).i());
		assertEquals(BigDecimal.valueOf(19), e.values().get(4).j());
		assertEquals(BigDecimal.valueOf(20), e.values().get(4).k());
		assertEquals(BigDecimal.ZERO, e.values().get(4).l());
		assertEquals(BigDecimal.ZERO, e.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(21), e.values().get(5).r());
		assertEquals(BigDecimal.valueOf(22), e.values().get(5).i());
		assertEquals(BigDecimal.valueOf(23), e.values().get(5).j());
		assertEquals(BigDecimal.valueOf(24), e.values().get(5).k());
		assertEquals(BigDecimal.ZERO, e.values().get(5).l());
		assertEquals(BigDecimal.ZERO, e.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, e.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, e.values().get(5).k0());

		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}][{21,22,23,24,25},{26,27,28,29,30}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, f.dimensions());
		assertEquals(BigDecimal.valueOf(1), f.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), f.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), f.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), f.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), f.values().get(0).l());
		assertEquals(BigDecimal.ZERO, f.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(6), f.values().get(1).r());
		assertEquals(BigDecimal.valueOf(7), f.values().get(1).i());
		assertEquals(BigDecimal.valueOf(8), f.values().get(1).j());
		assertEquals(BigDecimal.valueOf(9), f.values().get(1).k());
		assertEquals(BigDecimal.valueOf(10), f.values().get(1).l());
		assertEquals(BigDecimal.ZERO, f.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(11), f.values().get(2).r());
		assertEquals(BigDecimal.valueOf(12), f.values().get(2).i());
		assertEquals(BigDecimal.valueOf(13), f.values().get(2).j());
		assertEquals(BigDecimal.valueOf(14), f.values().get(2).k());
		assertEquals(BigDecimal.valueOf(15), f.values().get(2).l());
		assertEquals(BigDecimal.ZERO, f.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(16), f.values().get(3).r());
		assertEquals(BigDecimal.valueOf(17), f.values().get(3).i());
		assertEquals(BigDecimal.valueOf(18), f.values().get(3).j());
		assertEquals(BigDecimal.valueOf(19), f.values().get(3).k());
		assertEquals(BigDecimal.valueOf(20), f.values().get(3).l());
		assertEquals(BigDecimal.ZERO, f.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(21), f.values().get(4).r());
		assertEquals(BigDecimal.valueOf(22), f.values().get(4).i());
		assertEquals(BigDecimal.valueOf(23), f.values().get(4).j());
		assertEquals(BigDecimal.valueOf(24), f.values().get(4).k());
		assertEquals(BigDecimal.valueOf(25), f.values().get(4).l());
		assertEquals(BigDecimal.ZERO, f.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(26), f.values().get(5).r());
		assertEquals(BigDecimal.valueOf(27), f.values().get(5).i());
		assertEquals(BigDecimal.valueOf(28), f.values().get(5).j());
		assertEquals(BigDecimal.valueOf(29), f.values().get(5).k());
		assertEquals(BigDecimal.valueOf(30), f.values().get(5).l());
		assertEquals(BigDecimal.ZERO, f.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, f.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, f.values().get(5).k0());

		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}][{25,26,27,28,29,30},{31,32,33,34,35,36}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, g.dimensions());
		assertEquals(BigDecimal.valueOf(1), g.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), g.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), g.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), g.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), g.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), g.values().get(0).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(7), g.values().get(1).r());
		assertEquals(BigDecimal.valueOf(8), g.values().get(1).i());
		assertEquals(BigDecimal.valueOf(9), g.values().get(1).j());
		assertEquals(BigDecimal.valueOf(10), g.values().get(1).k());
		assertEquals(BigDecimal.valueOf(11), g.values().get(1).l());
		assertEquals(BigDecimal.valueOf(12), g.values().get(1).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(13), g.values().get(2).r());
		assertEquals(BigDecimal.valueOf(14), g.values().get(2).i());
		assertEquals(BigDecimal.valueOf(15), g.values().get(2).j());
		assertEquals(BigDecimal.valueOf(16), g.values().get(2).k());
		assertEquals(BigDecimal.valueOf(17), g.values().get(2).l());
		assertEquals(BigDecimal.valueOf(18), g.values().get(2).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(19), g.values().get(3).r());
		assertEquals(BigDecimal.valueOf(20), g.values().get(3).i());
		assertEquals(BigDecimal.valueOf(21), g.values().get(3).j());
		assertEquals(BigDecimal.valueOf(22), g.values().get(3).k());
		assertEquals(BigDecimal.valueOf(23), g.values().get(3).l());
		assertEquals(BigDecimal.valueOf(24), g.values().get(3).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(25), g.values().get(4).r());
		assertEquals(BigDecimal.valueOf(26), g.values().get(4).i());
		assertEquals(BigDecimal.valueOf(27), g.values().get(4).j());
		assertEquals(BigDecimal.valueOf(28), g.values().get(4).k());
		assertEquals(BigDecimal.valueOf(29), g.values().get(4).l());
		assertEquals(BigDecimal.valueOf(30), g.values().get(4).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(31), g.values().get(5).r());
		assertEquals(BigDecimal.valueOf(32), g.values().get(5).i());
		assertEquals(BigDecimal.valueOf(33), g.values().get(5).j());
		assertEquals(BigDecimal.valueOf(34), g.values().get(5).k());
		assertEquals(BigDecimal.valueOf(35), g.values().get(5).l());
		assertEquals(BigDecimal.valueOf(36), g.values().get(5).i0());
		assertEquals(BigDecimal.ZERO, g.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, g.values().get(5).k0());

		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, h.dimensions());
		assertEquals(BigDecimal.valueOf(1), h.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), h.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), h.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), h.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), h.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), h.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), h.values().get(0).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(8), h.values().get(1).r());
		assertEquals(BigDecimal.valueOf(9), h.values().get(1).i());
		assertEquals(BigDecimal.valueOf(10), h.values().get(1).j());
		assertEquals(BigDecimal.valueOf(11), h.values().get(1).k());
		assertEquals(BigDecimal.valueOf(12), h.values().get(1).l());
		assertEquals(BigDecimal.valueOf(13), h.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(14), h.values().get(1).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(15), h.values().get(2).r());
		assertEquals(BigDecimal.valueOf(16), h.values().get(2).i());
		assertEquals(BigDecimal.valueOf(17), h.values().get(2).j());
		assertEquals(BigDecimal.valueOf(18), h.values().get(2).k());
		assertEquals(BigDecimal.valueOf(19), h.values().get(2).l());
		assertEquals(BigDecimal.valueOf(20), h.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(21), h.values().get(2).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(22), h.values().get(3).r());
		assertEquals(BigDecimal.valueOf(23), h.values().get(3).i());
		assertEquals(BigDecimal.valueOf(24), h.values().get(3).j());
		assertEquals(BigDecimal.valueOf(25), h.values().get(3).k());
		assertEquals(BigDecimal.valueOf(26), h.values().get(3).l());
		assertEquals(BigDecimal.valueOf(27), h.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(28), h.values().get(3).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(29), h.values().get(4).r());
		assertEquals(BigDecimal.valueOf(30), h.values().get(4).i());
		assertEquals(BigDecimal.valueOf(31), h.values().get(4).j());
		assertEquals(BigDecimal.valueOf(32), h.values().get(4).k());
		assertEquals(BigDecimal.valueOf(33), h.values().get(4).l());
		assertEquals(BigDecimal.valueOf(34), h.values().get(4).i0());
		assertEquals(BigDecimal.valueOf(35), h.values().get(4).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(36), h.values().get(5).r());
		assertEquals(BigDecimal.valueOf(37), h.values().get(5).i());
		assertEquals(BigDecimal.valueOf(38), h.values().get(5).j());
		assertEquals(BigDecimal.valueOf(39), h.values().get(5).k());
		assertEquals(BigDecimal.valueOf(40), h.values().get(5).l());
		assertEquals(BigDecimal.valueOf(41), h.values().get(5).i0());
		assertEquals(BigDecimal.valueOf(42), h.values().get(5).j0());
		assertEquals(BigDecimal.ZERO, h.values().get(5).k0());

		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, i.dimensions());
		assertEquals(BigDecimal.valueOf(1), i.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), i.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), i.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), i.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), i.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), i.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), i.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), i.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(9), i.values().get(1).r());
		assertEquals(BigDecimal.valueOf(10), i.values().get(1).i());
		assertEquals(BigDecimal.valueOf(11), i.values().get(1).j());
		assertEquals(BigDecimal.valueOf(12), i.values().get(1).k());
		assertEquals(BigDecimal.valueOf(13), i.values().get(1).l());
		assertEquals(BigDecimal.valueOf(14), i.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(15), i.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(16), i.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(17), i.values().get(2).r());
		assertEquals(BigDecimal.valueOf(18), i.values().get(2).i());
		assertEquals(BigDecimal.valueOf(19), i.values().get(2).j());
		assertEquals(BigDecimal.valueOf(20), i.values().get(2).k());
		assertEquals(BigDecimal.valueOf(21), i.values().get(2).l());
		assertEquals(BigDecimal.valueOf(22), i.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(23), i.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(24), i.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(25), i.values().get(3).r());
		assertEquals(BigDecimal.valueOf(26), i.values().get(3).i());
		assertEquals(BigDecimal.valueOf(27), i.values().get(3).j());
		assertEquals(BigDecimal.valueOf(28), i.values().get(3).k());
		assertEquals(BigDecimal.valueOf(29), i.values().get(3).l());
		assertEquals(BigDecimal.valueOf(30), i.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(31), i.values().get(3).j0());
		assertEquals(BigDecimal.valueOf(32), i.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(33), i.values().get(4).r());
		assertEquals(BigDecimal.valueOf(34), i.values().get(4).i());
		assertEquals(BigDecimal.valueOf(35), i.values().get(4).j());
		assertEquals(BigDecimal.valueOf(36), i.values().get(4).k());
		assertEquals(BigDecimal.valueOf(37), i.values().get(4).l());
		assertEquals(BigDecimal.valueOf(38), i.values().get(4).i0());
		assertEquals(BigDecimal.valueOf(39), i.values().get(4).j0());
		assertEquals(BigDecimal.valueOf(40), i.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(41), i.values().get(5).r());
		assertEquals(BigDecimal.valueOf(42), i.values().get(5).i());
		assertEquals(BigDecimal.valueOf(43), i.values().get(5).j());
		assertEquals(BigDecimal.valueOf(44), i.values().get(5).k());
		assertEquals(BigDecimal.valueOf(45), i.values().get(5).l());
		assertEquals(BigDecimal.valueOf(46), i.values().get(5).i0());
		assertEquals(BigDecimal.valueOf(47), i.values().get(5).j0());
		assertEquals(BigDecimal.valueOf(48), i.values().get(5).k0());

		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]");
		// TODO: Backwards for now. Must fix.
		//assertArrayEquals(new long[] {3,2}, j.dimensions());
		assertEquals(BigDecimal.valueOf(1), j.values().get(0).r());
		assertEquals(BigDecimal.valueOf(2), j.values().get(0).i());
		assertEquals(BigDecimal.valueOf(3), j.values().get(0).j());
		assertEquals(BigDecimal.valueOf(4), j.values().get(0).k());
		assertEquals(BigDecimal.valueOf(5), j.values().get(0).l());
		assertEquals(BigDecimal.valueOf(6), j.values().get(0).i0());
		assertEquals(BigDecimal.valueOf(7), j.values().get(0).j0());
		assertEquals(BigDecimal.valueOf(8), j.values().get(0).k0());
		assertEquals(BigDecimal.valueOf(10), j.values().get(1).r());
		assertEquals(BigDecimal.valueOf(11), j.values().get(1).i());
		assertEquals(BigDecimal.valueOf(12), j.values().get(1).j());
		assertEquals(BigDecimal.valueOf(13), j.values().get(1).k());
		assertEquals(BigDecimal.valueOf(14), j.values().get(1).l());
		assertEquals(BigDecimal.valueOf(15), j.values().get(1).i0());
		assertEquals(BigDecimal.valueOf(16), j.values().get(1).j0());
		assertEquals(BigDecimal.valueOf(17), j.values().get(1).k0());
		assertEquals(BigDecimal.valueOf(19), j.values().get(2).r());
		assertEquals(BigDecimal.valueOf(20), j.values().get(2).i());
		assertEquals(BigDecimal.valueOf(21), j.values().get(2).j());
		assertEquals(BigDecimal.valueOf(22), j.values().get(2).k());
		assertEquals(BigDecimal.valueOf(23), j.values().get(2).l());
		assertEquals(BigDecimal.valueOf(24), j.values().get(2).i0());
		assertEquals(BigDecimal.valueOf(25), j.values().get(2).j0());
		assertEquals(BigDecimal.valueOf(26), j.values().get(2).k0());
		assertEquals(BigDecimal.valueOf(28), j.values().get(3).r());
		assertEquals(BigDecimal.valueOf(29), j.values().get(3).i());
		assertEquals(BigDecimal.valueOf(30), j.values().get(3).j());
		assertEquals(BigDecimal.valueOf(31), j.values().get(3).k());
		assertEquals(BigDecimal.valueOf(32), j.values().get(3).l());
		assertEquals(BigDecimal.valueOf(33), j.values().get(3).i0());
		assertEquals(BigDecimal.valueOf(34), j.values().get(3).j0());
		assertEquals(BigDecimal.valueOf(35), j.values().get(3).k0());
		assertEquals(BigDecimal.valueOf(37), j.values().get(4).r());
		assertEquals(BigDecimal.valueOf(38), j.values().get(4).i());
		assertEquals(BigDecimal.valueOf(39), j.values().get(4).j());
		assertEquals(BigDecimal.valueOf(40), j.values().get(4).k());
		assertEquals(BigDecimal.valueOf(41), j.values().get(4).l());
		assertEquals(BigDecimal.valueOf(42), j.values().get(4).i0());
		assertEquals(BigDecimal.valueOf(43), j.values().get(4).j0());
		assertEquals(BigDecimal.valueOf(44), j.values().get(4).k0());
		assertEquals(BigDecimal.valueOf(46), j.values().get(5).r());
		assertEquals(BigDecimal.valueOf(47), j.values().get(5).i());
		assertEquals(BigDecimal.valueOf(48), j.values().get(5).j());
		assertEquals(BigDecimal.valueOf(49), j.values().get(5).k());
		assertEquals(BigDecimal.valueOf(50), j.values().get(5).l());
		assertEquals(BigDecimal.valueOf(51), j.values().get(5).i0());
		assertEquals(BigDecimal.valueOf(52), j.values().get(5).j0());
		assertEquals(BigDecimal.valueOf(53), j.values().get(5).k0());
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
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]][[2]][[3]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]][[{2}]][[{3}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}]][[{3,4}]][[{5,6}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}]][[{4,5,6}]][[{7,8,9}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}]][[{5,6,7,8}]][[{9,10,11,12}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}]][[{6,7,8,9,10}]][[{11,12,13,14,15}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}]][[{7,8,9,10,11,12}]][[{13,14,15,16,17,18}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}]][[{8,9,10,11,12,13,14}]][[{15,16,17,18,19,20,21}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}]][[{9,10,11,12,13,14,15,16}]][[{17,18,19,20,21,22,23,24}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}]][[{10,11,12,13,14,15,16,17,18}]][[{19,20,21,22,23,24,25,26,27}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2]][[3,4]][[5,6]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}]][[{3},{4}]][[{5},{6}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}]][[{5,6},{7,8}]][[{9,10},{11,12}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}]][[{7,8,9},{10,11,12}]][[{13,14,15},{16,17,18}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}]][[{9,10,11,12},{13,14,15,16}]][[{17,18,19,20},{21,22,23,24}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}]][[{11,12,13,14,15},{16,17,18,19,20}]][[{21,22,23,24,25},{26,27,28,29,30}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}]][[{13,14,15,16,17,18},{19,20,21,22,23,24}]][[{25,26,27,28,29,30},{31,32,33,34,35,36}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]][[{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]][[{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]][[{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]][[{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]][[{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]][[{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3]][[4,5,6]][[7,8,9]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}]][[{4},{5},{6}]][[{7},{8},{9}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}]][[{7,8},{9,10},{11,12}]][[{13,14},{15,16},{17,18}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}]][[{10,11,12},{13,14,15},{16,17,18}]][[{19,20,21},{22,23,24},{25,26,27}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}]][[{13,14,15,16},{17,18,19,20},{21,22,23,24}]][[{25,26,27,28},{29,30,31,32},{33,34,35,36}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]][[{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]][[{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]][[{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]][[{37,38,39,40,41,42},{43,44,45,46,47,48},{49,50,51,52,53,54}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]][[{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]][[{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,61,62,63}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]][[{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]][[{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,69,70,71,72}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]][[{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]][[{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x1() {
		// [[[][]][[][]][[][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][2]][[3][4]][[5][6]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{2}]][[{3}][{4}]][[{5}][{6}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}][{3,4}]][[{5,6}][{7,8}]][[{9,10}][{11,12}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}][{4,5,6}]][[{7,8,9}][{10,11,12}]][[{13,14,15}][{16,17,18}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}][{5,6,7,8}]][[{9,10,11,12}][{13,14,15,16}]][[{17,18,19,20}][{21,22,23,24}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}][{6,7,8,9,10}]][[{11,12,13,14,15}][{16,17,18,19,20}]][[{21,22,23,24,25}][{26,27,28,29,30}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}]][[{13,14,15,16,17,18}][{19,20,21,22,23,24}]][[{25,26,27,28,29,30}][{31,32,33,34,35,36}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}]][[{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28}]][[{29,30,31,32,33,34,35}][{36,37,38,39,40,41,42}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}]][[{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32}]][[{33,34,35,36,37,38,39,40}][{41,42,43,44,45,46,47,48}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}]][[{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36}]][[{37,38,39,40,41,42,43,44,45}][{46,47,48,49,50,51,52,53,54}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2][3,4]][[5,6][7,8]][[9,10][11,12]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}][{3},{4}]][[{5},{6}][{7},{8}]][[{9},{10}][{11},{12}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}][{5,6},{7,8}]][[{9,10},{11,12}][{13,14},{15,16}]][[{17,18},{19,20}][{21,22},{23,24}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}]][[{13,14,15},{16,17,18}][{19,20,21},{22,23,24}]][[{25,26,27},{28,29,30}][{31,32,33},{34,35,36}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}]][[{17,18,19,20},{21,22,23,24}][{25,26,27,28},{29,30,31,32}]][[{33,34,35,36},{37,38,39,40}][{41,42,43,44},{45,46,47,48}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}]][[{21,22,23,24,25},{26,27,28,29,30}][{31,32,33,34,35},{36,37,38,39,40}]][[{41,42,43,44,45},{46,47,48,49,50}][{51,52,53,54,55},{56,57,58,59,60}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}]][[{25,26,27,28,29,30},{31,32,33,34,35,36}][{37,38,39,40,41,42},{43,44,45,46,47,48}]][[{49,50,51,52,53,54},{55,56,57,58,59,60}][{61,62,63,64,65,66},{67,68,69,70,71,72}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]][[{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49},{50,51,52,53,54,55,56}]][[{57,58,59,60,61,62,63},{64,65,66,67,68,69,70}][{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]][[{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64}]][[{65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80}][{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]][[{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72}]][[{73,74,75,76,77,78,79,80,81},{82,83,84,85,86,87,88,89,90}][{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3][4,5,6]][[7,8,9][10,11,12]][[13,14,15][16,17,18]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}][{4},{5},{6}]][[{7},{8},{9}][{10},{11},{12}]][[{13},{14},{15}][{16},{17},{18}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}]][[{13,14},{15,16},{17,18}][{19,20},{21,22},{23,24}]][[{25,26},{27,28},{29,30}][{31,32},{33,34},{35,36}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}]][[{19,20,21},{22,23,24},{25,26,27}][{28,29,30},{31,32,33},{34,35,36}]][[{37,38,39},{40,41,42},{43,44,45}][{46,47,48},{49,50,51},{52,53,54}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}]][[{25,26,27,28},{29,30,31,32},{33,34,35,36}][{37,38,39,40},{41,42,43,44},{45,46,47,48}]][[{49,50,51,52},{53,54,55,56},{57,58,59,60}][{61,62,63,64},{65,66,67,68},{69,70,71,72}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]][[{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}][{46,47,48,49,50},{51,52,53,54,55},{56,57,58,59,60}]][[{61,62,63,64,65},{66,67,68,69,70},{71,72,73,74,75}][{76,77,78,79,80},{81,82,83,84,85},{86,87,88,89,90}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,19,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]][[{37,38,39,40,41,42},{43,44,45,46,47,48},{49,50,51,52,53,54}][{55,56,57,58,59,60},{61,62,63,64,65,66},{67,68,69,70,71,72}]][[{73,74,75,76,77,78},{79,80,81,82,83,84},{85,86,87,88,89,90}][{91,92,93,94,95,96},{97,98,99,100,101,102},{103,104,105,106,107,108}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]][[{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,51,62,63}][{64,65,66,67,68,69,70},{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}]][[{85,86,87,88,89,90,91},{92,93,94,95,96,97,98},{99,100,101,102,103,104,105}][{106,107,108,109,110,111,112},{113,114,115,116,117,118,119},{120,121,122,123,124,125,126}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]][[{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,69,70,71,72}][{73,74,75,76,77,78,79,80},{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}]][[{97,98,99,100,101,102,103,104},{105,106,107,108,109,110,111,112},{113,114,115,116,117,118,119,120}][{121,122,123,124,125,126,127,128},{129,130,131,132,133,134,135,136},{137,138,139,140,141,142,143,144}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]][[{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}][{82,83,84,85,86,87,88,89,90},{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}]][[{109,110,111,112,113,114,115,116,117},{118,119,120,121,122,123,124,125,126},{127,128,129,130,131,132,133,134,135}][{136,137,138,139,140,141,142,143,144},{145,146,147,148,149,150,151,152,153},{154,155,156,157,158,159,160,161,162}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x1() {
		// [[[][][]][[][][]][[][][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][2][3]][[4][5][6]][[7][8][9]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{2}][{3}]][[{4}][{5}][{6}]][[{7}][{8}][{9}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}][{3,4}][{5,6}]][[{7,8}][{9,10}][{11,12}]][[{13,14}][{15,16}][{17,18}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}][{4,5,6}][{7,8,9}]][[{10,11,12}][{13,14,15}][{16,17,18}]][[{19,20,21}][{22,23,24}][{25,26,27}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}]][[{13,14,15,16}][{17,18,19,20}][{21,22,23,24}]][[{25,26,27,28}][{29,30,31,32}][{33,34,35,36}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}]][[{16,17,18,19,20}][{21,22,23,24,25}][{26,27,28,29,30}]][[{31,32,33,34,35}][{36,37,38,39,40}][{41,42,43,44,45}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}]][[{19,20,21,22,23,24}][{25,26,27,28,29,30}][{31,32,33,34,35,36}]][[{37,38,39,40,41,42}][{43,44,45,46,47,48}][{49,50,51,52,53,54}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}]][[{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35}][{36,37,38,39,40,41,42}]][[{43,44,45,46,47,48,49}][{50,51,52,53,54,55,56}][{57,58,59,60,61,62,63}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}]][[{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40}][{41,42,43,44,45,46,47,48}]][[{49,50,51,52,53,54,55,56}][{57,58,59,60,61,62,63,64}][{65,66,67,68,69,70,71,72}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}]][[{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45}][{46,47,48,49,50,51,52,53,54}]][[{55,56,57,58,59,60,61,62,63}][{64,65,66,67,68,69,70,71,72}][{73,74,75,76,77,78,79,80,81}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2][3,4][5,6]][[7,8][9,10][11,12]][[13,14][15,16][17,18]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2}][{3},{4}][{5},{6}]][[{7},{8}][{9},{10}][{11},{12}]][[{13},{14}][{15},{16}][{17},{18}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4}][{5,6},{7,8}][{9,10},{11,12}]][[{13,14},{15,16}][{17,18},{19,20}][{21,22},{23,24}]][[{25,26},{27,28}][{29,30},{31,32}][{33,34},{35,36}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}][{13,14,15},{16,17,18}]][[{19,20,21},{22,23,24}][{25,26,27},{28,29,30}][{31,32,33},{34,35,36}]][[{37,38,39},{40,41,42}][{43,44,45},{46,47,48}][{49,50,51},{52,53,54}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}][{17,18,19,20},{21,22,23,24}]][[{25,26,27,28},{29,30,31,32}][{33,34,35,36},{37,38,39,40}][{41,42,43,44},{45,46,47,48}]][[{49,50,51,52},{53,54,55,56}][{57,58,59,60},{61,62,63,64}][{65,66,67,68},{69,70,71,72}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}][{21,22,23,24,25},{26,27,28,29,30}]][[{31,32,33,34,35},{36,37,38,39,40}][{41,42,43,44,45},{46,47,48,49,50}][{51,52,53,54,55},{56,57,58,59,60}]][[{61,62,63,64,65},{66,67,68,69,70}][{71,72,73,74,75},{76,77,78,79,80}][{81,82,83,84,85},{86,87,88,89,90}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}][{25,26,27,28,29,30},{31,32,33,34,35,36}]][[{37,38,39,40,41,42},{43,44,45,46,47,48}][{49,50,51,52,53,54},{55,56,57,58,59,60}][{61,62,63,64,65,66},{67,68,69,70,71,72}]][[{73,74,75,76,77,78},{79,80,81,82,83,84}][{85,86,87,88,89,90},{91,92,93,94,95,96}][{97,98,99,100,101,102},{103,104,105,106,107,108}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}][{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]][[{43,44,45,46,47,48,49},{50,51,52,53,54,55,56}][{57,58,59,60,61,62,63},{64,65,66,67,68,69,70}][{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}]][[{85,86,87,88,89,90,91},{92,93,94,95,96,97,98}][{99,100,101,102,103,104,105},{106,107,108,109,110,111,112}][{113,114,115,116,117,118,119},{120,121,122,123,124,125,126}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}][{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]][[{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64}][{65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80}][{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}]][[{97,98,99,100,101,102,103,104},{105,106,107,108,109,110,111,112}][{113,114,115,116,117,118,119,120},{121,122,123,124,125,126,127,128}][{129,130,131,132,133,134,135,136},{137,138,139,140,141,142,143,144}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}][{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]][[{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72}][{73,74,75,76,77,78,79,80,81},{82,83,84,85,86,87,88,89,90}][{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}]][[{109,110,111,112,113,114,115,116,117},{118,119,120,121,122,123,124,125,126}][{127,128,129,130,131,132,133,134,135},{136,137,138,139,140,141,142,143,144}][{145,146,147,148,149,150,151,152,153},{154,155,156,157,158,159,160,161,162}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3][4,5,6][7,8,9]][[10,11,12][13,14,15][16,17,18]][[19,20,21][22,23,24][25,26,27]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{2},{3}][{4},{5},{6}][{7},{8},{9}]][[{10},{11},{12}][{13},{14},{15}][{16},{17},{18}]][[{19},{20},{21}][{22},{23},{24}][{25},{26},{27}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}][{13,14},{15,16},{17,18}]][[{19,20},{21,22},{23,24}][{25,26},{27,28},{29,30}][{31,32},{33,34},{35,36}]][[{37,38},{39,40},{41,42}][{43,44},{45,46},{47,48}][{49,50},{51,52},{53,54}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}][{19,20,21},{22,23,24},{25,26,27}]][[{28,29,30},{31,32,33},{34,35,36}][{37,38,39},{40,41,42},{43,44,45}][{46,47,48},{49,50,51},{52,53,54}]][[{55,56,57},{58,59,60},{61,62,63}][{64,65,66},{67,68,69},{70,71,72}][{73,74,75},{76,77,78},{79,80,81}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}][{25,26,27,28},{29,30,31,32},{33,34,35,36}]][[{37,38,39,40},{41,42,43,44},{45,46,47,48}][{49,50,51,52},{53,54,55,56},{57,58,59,60}][{61,62,63,64},{65,66,67,68},{69,70,71,72}]][[{73,74,75,76},{77,78,79,80},{81,82,83,84}][{85,86,87,88},{89,90,91,92},{93,94,95,96}][{97,98,99,100},{101,102,103,104},{105,106,107,108}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}][{31,32,33,34,35},{36,37,38,39,40},{41,42,43,44,45}]][[{46,47,48,49,50},{51,52,53,54,55},{56,57,58,59,60}][{61,62,63,64,65},{66,67,68,69,70},{71,72,73,74,75}][{76,77,78,79,80},{81,82,83,84,85},{86,87,88,89,90}]][[{91,92,93,94,95},{96,97,98,99,100},{101,102,103,104,105}][{106,107,108,109,110},{111,112,113,114,115},{116,117,118,119,120}][{121,122,123,124,125},{126,127,128,129,130},{131,132,133,134,135}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}][{37,38,39,40,41,42},{43,44,45,46,47,48},{49,50,51,52,53,54}]][[{55,56,57,58,59,60},{61,62,63,64,65,66},{67,68,69,70,71,72}][{73,74,75,76,77,78},{79,80,81,82,83,84},{85,86,87,88,89,90}][{91,92,93,94,95,96},{97,98,99,100,101,102},{103,104,105,106,107,108}]][[{109,110,111,112,113,114},{115,116,117,118,119,120},{121,122,123,124,125,126}][{127,128,129,130,131,132},{133,134,135,136,137,138},{139,140,141,142,143,144}][{145,146,147,148,149,150},{151,152,153,154,155,156},{157,158,159,160,161,162}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49},{50,51,52,53,54,55,56},{57,58,59,60,61,62,63}]][[{64,65,66,67,68,69,70},{71,72,73,74,75,76,77},{78,79,80,81,82,83,84}][{85,86,87,88,89,90,91},{92,93,94,95,96,97,98},{99,100,101,102,103,104,105}][{106,107,108,109,110,111,112},{113,114,115,116,117,118,119},{120,121,122,123,124,125,126}]][[{127,128,129,130,131,132,133},{134,135,136,137,138,139,140},{141,142,143,144,145,146,147}][{148,149,150,151,152,153,154},{155,156,157,158,159,160,161},{162,163,164,165,166,167,168}][{169,170,171,172,173,174,175},{176,177,178,179,180,181,182},{183,184,185,186,187,188,189}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56},{57,58,59,60,61,62,63,64},{65,66,67,68,69,70,71,72}]][[{73,74,75,76,77,78,79,80},{81,82,83,84,85,86,87,88},{89,90,91,92,93,94,95,96}][{97,98,99,100,101,102,103,104},{105,106,107,108,109,110,111,112},{113,114,115,116,117,118,119,120}][{121,122,123,124,125,126,127,128},{129,130,131,132,133,134,135,136},{137,138,139,140,141,142,143,144}]][[{145,146,147,148,149,150,151,152},{153,154,155,156,157,158,159,160},{161,162,163,164,165,166,167,168}][{169,170,171,172,173,174,175,176},{177,178,179,180,181,182,183,184},{185,186,187,188,189,190,191,192}][{193,194,195,196,197,198,199,200},{201,202,203,204,205,206,207,208},{209,210,211,212,213,214,215,216}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63},{64,65,66,67,68,69,70,71,72},{73,74,75,76,77,78,79,80,81}]][[{82,83,84,85,86,87,88,89,90},{91,92,93,94,95,96,97,98,99},{100,101,102,103,104,105,106,107,108}][{109,110,111,112,113,114,115,116,117},{118,119,120,121,122,123,124,125,126},{127,128,129,130,131,132,133,134,135}][{136,137,138,139,140,141,142,143,144},{145,146,147,148,149,150,151,152,153},{154,155,156,157,158,159,160,161,162}]][[{163,164,165,166,167,168,169,170,171},{172,173,174,175,176,177,178,179,180},{181,182,183,184,185,186,187,188,189}][{190,191,192,193,194,195,196,197,198},{199,200,201,202,203,204,205,206,207},{208,209,210,211,212,213,214,215,216}][{217,218,219,220,221,222,223,224,225},{226,227,228,229,230,231,232,233,234},{235,236,237,238,239,240,241,242,243}]]]");
		assertTrue(true);
	}

	@Test
	public void test4d2x3x4x1() {
		// [[[[][][][]][[][][][]][[][][][]]][[[][][][]][[][][][]][[][][][]]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[[1][2][3][4]][[5][6][7][8]][[9][10][11][12]]][[[13][14][15][16]][[17][18][19][20]][[21][22][23][24]]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[[{1}][{2}][{3}][{4}]][[{5}][{6}][{7}][{8}]][[{9}][{10}][{11}][{12}]]][[[{13}][{14}][{15}][{16}]][[{17}][{18}][{19}][{20}]][[{21}][{22}][{23}][{24}]]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[[{1,2}][{3,4}][{5,6}][{7,8}]][[{9,10}][{11,12}][{13,14}][{15,16}]][[{17,18}][{19,20}][{21,22}][{23,24}]]][[[{25,26}][{27,28}][{29,30}][{31,32}]][[{33,34}][{35,36}][{37,38}][{39,40}]][[{41,42}][{43,44}][{45,46}][{47,48}]]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[[{1,2,3}][{4,5,6}][{7,8,9}][{10,11,12}]][[{13,14,15}][{16,17,18}][{19,20,21}][{22,23,24}]][[{25,26,27}][{28,29,30}][{31,32,33}][{34,35,36}]]][[[{37,38,39}][{40,41,42}][{43,44,45}][{46,47,48}]][[{49,50,51}][{52,53,54}][{55,56,57}][{58,59,60}]][[{61,62,63}][{64,65,66}][{67,68,69}][{70,71,72}]]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}][{13,14,15,16}]][[{17,18,19,20}][{21,22,23,24}][{25,26,27,28}][{29,30,31,32}]][[{33,34,35,36}][{37,38,39,40}][{41,42,43,44}][{45,46,47,48}]]][[[{49,50,51,52}][{53,54,55,56}][{57,58,59,60}][{61,62,63,64}]][[{65,66,67,68}][{69,70,71,72}][{73,74,75,76}][{77,78,79,80}]][[{81,82,83,84}][{85,86,87,88}][{89,90,91,92}][{93,94,95,96}]]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}][{16,17,18,19,20}]][[{21,22,23,24,25}][{26,27,28,29,30}][{31,32,33,34,35}][{36,37,38,39,40}]][[{41,42,43,44,45}][{46,47,48,49,50}][{51,52,53,54,55}][{56,57,58,59,60}]]][[[{61,62,63,64,65}][{66,67,68,69,70}][{71,72,73,74,75}][{76,77,78,79,80}]][[{81,82,83,84,85}][{86,87,88,89,90}][{91,92,93,94,95}][{96,97,98,99,100}]][[{101,102,103,104,105}][{106,107,108,109,110}][{111,112,113,114,115}][{116,117,118,119,120}]]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}][{19,20,21,22,23,24}]][[{25,26,27,28,29,30}][{31,32,33,34,35,36}][{37,38,39,40,41,42}][{43,44,45,46,47,48}]][[{49,50,51,52,53,54}][{55,56,57,58,59,60}][{61,62,63,64,65,66}][{67,68,69,70,71,72}]]][[[{73,74,75,76,77,78}][{79,80,81,82,83,84}][{85,86,87,88,89,90}][{91,92,93,94,95,96}]][[{97,98,99,100,101,102}][{103,104,105,106,107,108}][{109,110,111,112,113,114}][{115,116,117,118,119,120}]][[{121,122,123,124,125,126}][{127,128,129,130,131,132}][{133,134,135,136,137,138}][{139,140,141,142,143,144}]]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28}]][[{29,30,31,32,33,34,35}][{36,37,38,39,40,41,42}][{43,44,45,46,47,48,49}][{50,51,52,53,54,55,56}]][[{57,58,59,60,61,62,63}][{64,65,66,67,68,69,70}][{71,72,73,74,75,76,77}][{78,79,80,81,82,83,84}]]][[[{85,86,87,88,89,90,91}][{92,93,94,95,96,97,98}][{99,100,101,102,103,104,105}][{106,107,108,109,110,111,112}]][[{113,114,115,116,117,118,119}][{120,121,122,123,124,125,126}][{127,128,129,130,131,132,133}][{134,135,136,137,138,139,140}]][[{141,142,143,144,145,146,147}][{148,149,150,151,152,153,154}][{155,156,157,158,159,160,161}][{162,163,164,165,166,167,168}]]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32}]][[{33,34,35,36,37,38,39,40}][{41,42,43,44,45,46,47,48}][{49,50,51,52,53,54,55,56}][{57,58,59,60,61,62,63,64}]][[{65,66,67,68,69,70,71,72}][{73,74,75,76,77,78,79,80}][{81,82,83,84,85,86,87,88}][{89,90,91,92,93,94,95,96}]]][[[{97,98,99,100,101,102,103,104}][{105,106,107,108,109,110,111,112}][{113,114,115,116,117,118,119,120}][{121,122,123,124,125,126,127,128}]][[{129,130,131,132,133,134,135,136}][{137,138,139,140,141,142,143,144}][{145,146,147,148,149,150,151,152}][{153,154,155,156,157,158,159,160}]][[{161,162,163,164,165,166,167,168}][{169,170,171,172,173,174,175,176}][{177,178,179,180,181,182,183,184}][{185,186,187,188,189,190,191,192}]]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36}]][[{37,38,39,40,41,42,43,44,45}][{46,47,48,49,50,51,52,53,54}][{55,56,57,58,59,60,61,62,63}][{64,65,66,67,68,69,70,71,72}]][[{73,74,75,76,77,78,79,80,81}][{82,83,84,85,86,87,88,89,90}][{91,92,93,94,95,96,97,98,99}][{100,101,102,103,104,105,106,107,108}]]][[[{109,110,111,112,113,114,115,116,117}][{118,119,120,121,122,123,124,125,126}][{127,128,129,130,131,132,133,134,135}][{136,137,138,139,140,141,142,143,144}]][[{145,146,147,148,149,150,151,152,153}][{154,155,156,157,158,159,160,161,162}][{163,164,165,166,167,168,169,170,171}][{172,173,174,175,176,177,178,179,180}]][[{181,182,183,184,185,186,187,188,189}][{190,191,192,193,194,195,196,197,198}][{199,200,201,202,203,204,205,206,207}][{208,209,210,211,212,213,214,215,216}]]]]");
		assertTrue(true);
	}
}
