/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.algebra.AdditiveGroup;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestGenericAlgebras {

	@Test
	public void testA() {
		test1(G.INT32);
		test1(G.DBL);
	}
	
	@Test
	public void testB() {
		test2(G.INT32);
		test2(G.DBL);
	}
	
	private <T extends AdditiveGroup<T,U> & Unity<U> & Ordered<U>, U> void test1(T alg) {
		U a = alg.construct();
		alg.unity().call(a);
		U b = alg.construct();
		alg.zero().call(b);
		alg.add().call(a, a, b);
		U c = alg.construct();
		alg.add().call(a, b, c);
		assertTrue(alg.isEqual().call(alg.construct("3"), c));
		assertFalse(alg.isEqual().call(a, b));
		assertFalse(alg.isGreater().call(a, b));
		assertTrue(alg.isLess().call(a, b));
		assertTrue(alg.isLess().call(a, c));
		assertTrue(alg.isLess().call(b, c));
	}
	
	private <T extends AdditiveGroup<T,U> & Ordered<U>, U> void test2(T alg) {
		U a = alg.construct("1040");
		U b = alg.construct("160");
		U c = alg.construct();
		alg.add().call(a, b, c);
		assertTrue(alg.isEqual().call(alg.construct("1200"), c));
		assertFalse(alg.isEqual().call(a, b));
		assertTrue(alg.isGreater().call(a, b));
		assertFalse(alg.isLess().call(a, b));
		assertTrue(alg.isLess().call(a, c));
		assertTrue(alg.isLess().call(b, c));
	}
}
