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
		int numThreads;
		long numElems;
		
		tuple = ThreadingUtils.arrange(1, 1, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 1);
		assertEquals(1, numThreads);
		assertEquals(1, numElems);
		
		tuple = ThreadingUtils.arrange(1, 50, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 50);
		assertEquals(1, numThreads);
		assertEquals(50, numElems);
		
		tuple = ThreadingUtils.arrange(2, 50, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 50);
		assertEquals(2, numThreads);
		assertEquals(25, numElems);
		
		tuple = ThreadingUtils.arrange(2, 51, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 51);
		assertEquals(2, numThreads);
		assertEquals(26, numElems);
		
		tuple = ThreadingUtils.arrange(400, 51, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 51);
		assertEquals(51, numThreads);
		assertEquals(1, numElems);
		
		tuple = ThreadingUtils.arrange(400, 451, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 451);
		assertEquals(226, numThreads);
		assertEquals(2, numElems);
		
		tuple = ThreadingUtils.arrange(10, 501, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 501);
		assertEquals(10, numThreads);
		assertEquals(51, numElems);
		
		tuple = ThreadingUtils.arrange(Integer.MAX_VALUE, 100L*Integer.MAX_VALUE, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 100L*Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, numThreads);
		assertEquals(100, numElems);

		tuple = ThreadingUtils.arrange(12345, 100L*Integer.MAX_VALUE, false);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 100L*Integer.MAX_VALUE);
		assertEquals(12345, numThreads);
		assertEquals(100L*Integer.MAX_VALUE/12345+1, numElems);

		tuple = ThreadingUtils.arrange(12345, 100L*Integer.MAX_VALUE, true);
		numThreads = tuple.a();
		numElems = tuple.b();
		assertTrue(numThreads * numElems >= 100L*Integer.MAX_VALUE);
		assertEquals(1, numThreads);
		assertEquals(100L*Integer.MAX_VALUE, numElems);
	}

}
