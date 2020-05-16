/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;

import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int8.SignedInt8Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestScaleByHighPrec {

	@Test
	public void test1() {
		SignedInt8Member value = G.INT8.construct();
		IndexedDataSource<SignedInt8Member> bytes1 = ArrayStorage.allocateBytes(
				new byte[] {-128, -64, -36, -15, -2, -1, 0, 1, 2, 11, 27, 84, 100});
		IndexedDataSource<SignedInt8Member> bytes2 = Storage.allocate(bytes1.size(), value);
		HighPrecisionMember scale = new HighPrecisionMember(BigDecimal.valueOf(1.3));
		ScaleByHighPrec.compute(G.INT8, scale, bytes1, bytes2);
		bytes2.get(0, value);
		assertEquals((byte)(1.3*(-128)), value.v());
		bytes2.get(1, value);
		assertEquals((byte)(1.3*(-64)), value.v());
		bytes2.get(2, value);
		assertEquals((byte)(1.3*(-36)), value.v());
		bytes2.get(3, value);
		assertEquals((byte)(1.3*(-15)), value.v());
		bytes2.get(4, value);
		assertEquals((byte)(1.3*(-2)), value.v());
		bytes2.get(5, value);
		assertEquals((byte)(1.3*(-1)), value.v());
		bytes2.get(6, value);
		assertEquals((byte)(1.3*(0)), value.v());
		bytes2.get(7, value);
		assertEquals((byte)(1.3*(1)), value.v());
		bytes2.get(8, value);
		assertEquals((byte)(1.3*(2)), value.v());
		bytes2.get(9, value);
		assertEquals((byte)(1.3*(11)), value.v());
		bytes2.get(10, value);
		assertEquals((byte)(1.3*(27)), value.v());
		bytes2.get(11, value);
		assertEquals((byte)(1.3*(84)), value.v());
		bytes2.get(12, value);
		assertEquals((byte)(1.3*(100)), value.v());
	}
}
