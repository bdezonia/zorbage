/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.example;

import nom.bdezonia.zorbage.algorithm.Average;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Median;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.linear.LinearAccessor;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageSignedInt32;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMain {
	
	
	
	
	// Scale a collection of Int32s by a floating point number
	public static void testGroupOfConversions() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32<SignedInt32Member> storage = new ArrayStorageSignedInt32<SignedInt32Member>(10, new SignedInt32Member());
		LinearAccessor<SignedInt32Member> accessor = new LinearAccessor<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		// scale it by 6.3
		Float64Member scale = new Float64Member(6.3);
		Float64Member tmp = new Float64Member();
		accessor.beforeFirst();
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			tmp.setV(value.v());
			G.DBL.multiply(tmp, scale, tmp);
			value.setV((int)Math.round(tmp.v()));
			accessor.put();
		}
		// print it out
		accessor.beforeFirst();
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			System.out.println("element " + accessor.pos() + " equals " + value.v());
		}
	}

	

	public static void main(String[] args) {
		testGroupOfConversions();
	}

}
