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

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.tuple.Tuple2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ThreadingUtilsTest {

	@Test
	public void test() {

		Tuple2<Integer,Long> tuple;
		
		tuple = ThreadingUtils.arrange(1, 1, false);
		assertTrue(tuple.a() * tuple.b() >= 1);
		assertEquals(1, (int) tuple.a());
		assertEquals(1, (long) tuple.b());
		
		tuple = ThreadingUtils.arrange(1, 50, false);
		assertTrue(tuple.a() * tuple.b() >= 50);
		assertEquals(1, (int) tuple.a());
		assertEquals(50, (long) tuple.b());
		
		tuple = ThreadingUtils.arrange(2, 50, false);
		assertTrue(tuple.a() * tuple.b() >= 50);
		assertEquals(2, (int) tuple.a());
		assertEquals(25, (long) tuple.b());
		
		tuple = ThreadingUtils.arrange(2, 51, false);
		assertTrue(tuple.a() * tuple.b() >= 51);
		assertEquals(2, (int) tuple.a());
		assertEquals(26, (long) tuple.b());

		tuple = ThreadingUtils.arrange(400, 51, false);
		assertTrue(tuple.a() * tuple.b() >= 51);
		assertEquals(400, (int) tuple.a());
		assertEquals(1, (long) tuple.b());

		tuple = ThreadingUtils.arrange(400, 451, false);
		assertTrue(tuple.a() * tuple.b() >= 451);
		assertEquals(400, (int) tuple.a());
		assertEquals(2, (long) tuple.b());

		tuple = ThreadingUtils.arrange(10, 501, false);
		assertTrue(tuple.a() * tuple.b() >= 501);
		assertEquals(10, (int) tuple.a());
		assertEquals(51, (long) tuple.b());

		tuple = ThreadingUtils.arrange(Integer.MAX_VALUE, 100L*Integer.MAX_VALUE, false);
		assertTrue(tuple.a() * tuple.b() >= 100L*Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, (int) tuple.a());
		assertEquals(100, (long) tuple.b());

		tuple = ThreadingUtils.arrange(12345, 100L*Integer.MAX_VALUE, false);
		assertTrue(tuple.a() * tuple.b() >= 100L*Integer.MAX_VALUE);
		assertEquals(12345, (int) tuple.a());
		assertEquals(100L*Integer.MAX_VALUE/12345+1, (long) tuple.b());

		tuple = ThreadingUtils.arrange(12345, 100L*Integer.MAX_VALUE, true);
		assertTrue(tuple.a() * tuple.b() >= 100L*Integer.MAX_VALUE);
		assertEquals(1, (int) tuple.a());
		assertEquals(100L*Integer.MAX_VALUE, (long) tuple.b());
	}

}
