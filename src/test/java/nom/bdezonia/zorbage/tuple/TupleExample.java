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
package nom.bdezonia.zorbage.tuple;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.accessor.AccessorA;
import nom.bdezonia.zorbage.accessor.AccessorB;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.tuple.Tuple5;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TupleExample {

	/*
	 * Show that tuples of differing sizes can be sent to methods.
	 */
	@Test
	public void run( ) {
		Tuple2<Integer,Float> tuple2 =
				new Tuple2<Integer,Float>(1,7.0f);
		
		Tuple5<Integer,Float,Double,Character,Short> tuple5 =
				new Tuple5<Integer,Float,Double,Character,Short>(9,13.0f,5.7,'f',(short)33);
		
		method(tuple2, 1, 7.0f);
		
		method(tuple5, 9, 13.0f);
	}
	
	private <T extends AccessorA<Integer> & AccessorB<Float>> void method(T tuple, int expectedA, float expectedB) {
		assertEquals(expectedA, (int) tuple.a());
		assertEquals(expectedB, (float) tuple.b(), 0.00001f);
	}
}
