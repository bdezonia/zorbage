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
package nom.bdezonia.zorbage.type.universal;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.integer.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.integer.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestUniversalConversions {

	@Test
	public void test1() {
		TensorOctonionRepresentation rep = new TensorOctonionRepresentation();
		BigInteger i = BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.valueOf(Integer.MAX_VALUE));
		UnsignedInt32Member a = G.UINT32.construct(i.toString());
		UnboundedIntMember b = G.UNBOUND.construct();
		UniversalConverter.convert(rep, a, b);
		assertEquals(i, b.v());
	}

	@Test
	public void test2() {
		TensorOctonionRepresentation rep = new TensorOctonionRepresentation();
		Integer i = -97;
		SignedInt32Member a = G.INT32.construct(i.toString());
		SignedInt16Member b = G.INT16.construct();
		UniversalConverter.convert(rep, a, b);
		assertEquals((int)i, b.v());
	}
}
