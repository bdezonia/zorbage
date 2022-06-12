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
package nom.bdezonia.zorbage.storage.array;

import org.junit.Test;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Member;

// This test implemented for speed testing OctonionFloat64Member.
// I tried reading/writing components from 0 to 7 and also from 7
// to 0. No real difference was uncovered by this test.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestAccess {

	@Test
	public void test() {
		System.out.println("Speed of setting and getting in a list of octonions");
		for (int i = 0; i < 20; i++)
			doit();
		System.out.println();
	}
	
	private void doit() {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		long a = System.currentTimeMillis();
		IndexedDataSource<OctonionFloat64Member> storage =
				ArrayStorage.allocate(tmp, 5000);
		for (long i = 0; i < storage.size(); i++) {
			tmp.setR(i);
			tmp.setI(i);
			tmp.setJ(i);
			tmp.setK(i);
			tmp.setL(i);
			tmp.setI0(i);
			tmp.setJ0(i);
			tmp.setK0(i);
			storage.set(i, tmp);
		}
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
		}
		long b = System.currentTimeMillis();
		System.out.print(" " + (b-a));
	}
}
