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
package nom.bdezonia.zorbage.type.data.bigint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.algorithm.MinMaxElement;
import nom.bdezonia.zorbage.algorithm.Shuffle;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageGeneric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUnboundedIntGroup {

	@Test
	public void testHugeNumbers() {
		UnboundedIntMember a = new UnboundedIntMember(Long.MAX_VALUE);
		UnboundedIntMember b = new UnboundedIntMember(44);
		UnboundedIntMember product = new UnboundedIntMember();

		G.BIGINT.multiply().call(a,b,product);
		
		assertTrue(new BigInteger("405828369621610135508").equals(product.v()));
	}
	
	@Test
	public void minmax() {
		IndexedDataSource<?, UnboundedIntMember> list =
				new ArrayStorageGeneric<UnboundedIntGroup,UnboundedIntMember>(5000, G.BIGINT);
		UnboundedIntMember a = G.BIGINT.construct("1234567890");
		UnboundedIntMember b = G.BIGINT.construct("55");
		for (long i = 0, j = list.size()-1; i < list.size()/2; i++,j--) {
			list.set(i, a);
			list.set(j, b);
			G.BIGINT.pred().call(a, a);
			G.BIGINT.succ().call(b, b);
		}
		Shuffle.compute(G.BIGINT, list);
		UnboundedIntMember min = G.BIGINT.construct();
		UnboundedIntMember max = G.BIGINT.construct();
		MinMaxElement.compute(G.BIGINT, list, min, max);
		assertEquals(BigInteger.valueOf(55), min.v());
		assertEquals(BigInteger.valueOf(1234567890), max.v());
	}
	
	@Test
	public void bigpower() {
		UnboundedIntMember a = G.BIGINT.construct("3");
		UnboundedIntMember b = G.BIGINT.construct("100");
		UnboundedIntMember c = G.BIGINT.construct();
		
		G.BIGINT.pow().call(a, b, c);
		
		assertEquals(new BigInteger("515377520732011331036461129765621272702107522001"), c.v());
	}
}
