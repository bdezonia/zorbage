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
package nom.bdezonia.zorbage.storage.file;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.IsSorted;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.Reverse;
import nom.bdezonia.zorbage.algorithm.Shuffle;
import nom.bdezonia.zorbage.algorithm.Sort;
import nom.bdezonia.zorbage.algorithm.StableSort;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFileStorageSpeed {

	@Test
	public void test1() {
		Float64Member value = G.DBL.construct();
		Random rng = ThreadLocalRandom.current();
		double[] dbls = new double[1300111];
		for (int i = 0; i < dbls.length; i++) {
			dbls[i] = rng.nextDouble();
		}
		
		long begin = System.currentTimeMillis();
		
		IndexedDataSource<Float64Member> list = FileStorage.allocate(new Float64Member(), dbls.length);
		for (int i = 0; i < dbls.length; i++) {
			value.setV(dbls[i]);
			list.set(i, value);
		}
		
		Arrays.sort(dbls);

		Mean.compute(G.DBL, list, value);
		Shuffle.compute(G.DBL, list);
		
		assertFalse(IsSorted.compute(G.DBL, G.DBL.isLess(), list));
		
		Sort.compute(G.DBL, list);
		
		assertTrue(IsSorted.compute(G.DBL, G.DBL.isLess(), list));
		assertTrue(listIsSorted(list, dbls));
		
		Reverse.compute(G.DBL, list);
		
		assertTrue(IsSorted.compute(G.DBL, G.DBL.isGreater(), list));
		assertTrue(listIsReverseSorted(list, dbls));
		
		Shuffle.compute(G.DBL, list);
		
		assertFalse(IsSorted.compute(G.DBL, G.DBL.isLess(), list));
		
		StableSort.compute(G.DBL, list);
		
		assertTrue(IsSorted.compute(G.DBL, G.DBL.isLess(), list));
		assertTrue(listIsSorted(list, dbls));
		
		Reverse.compute(G.DBL, list);
		assertTrue(IsSorted.compute(G.DBL, G.DBL.isGreater(), list));
		assertTrue(listIsReverseSorted(list, dbls));
		
		long end = System.currentTimeMillis();
		
		System.out.println(end-begin);
	}
	
	private boolean listIsSorted(IndexedDataSource<Float64Member> list, double[] dbls) {
		Float64Member value = G.DBL.construct();
		if (dbls.length != list.size())
			return false;
		for (int i = 0; i < dbls.length; i++) {
			list.get(i, value);
			if (value.v() != dbls[i])
				return false;
		}
		return true;
	}
	
	private boolean listIsReverseSorted(IndexedDataSource<Float64Member> list, double[] dbls) {
		Float64Member value = G.DBL.construct();
		if (dbls.length != list.size())
			return false;
		for (int i = 0; i < dbls.length; i++) {
			list.get(i, value);
			if (value.v() != dbls[dbls.length-1-i])
				return false;
		}
		return true;
	}
}
