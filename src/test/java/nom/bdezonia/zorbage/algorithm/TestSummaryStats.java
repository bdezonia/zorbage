/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestSummaryStats {

	@Test
	public void test0() {
	
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {});
		
		try {
			
			Float64Member min = G.DBL.construct();
			Float64Member q1 = G.DBL.construct();
			Float64Member median = G.DBL.construct();
			Float64Member mean = G.DBL.construct();
			Float64Member q3 = G.DBL.construct();
			Float64Member max = G.DBL.construct();

			SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
			
			fail();

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void test1() {
		
		// In R:
		// x <- c(13)
		// summary(x)
		//   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
		//   13      13      13      13      13      13
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {13});
		
		Float64Member min = G.DBL.construct();
		Float64Member q1 = G.DBL.construct();
		Float64Member median = G.DBL.construct();
		Float64Member mean = G.DBL.construct();
		Float64Member q3 = G.DBL.construct();
		Float64Member max = G.DBL.construct();

		SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
		
		assertEquals(13, min.v(), 0);
		assertEquals(13, q1.v(), 0);
		assertEquals(13, median.v(), 0);
		assertEquals(13, mean.v(), 0);
		assertEquals(13, q3.v(), 0);
		assertEquals(13, max.v(), 0);
	}

	@Test
	public void test2() {
		
		// In R:
		// x <- c(13,21)
		// summary(x)
		// Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
		// 13      15      17      17      19      21
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {13,21});
		
		Float64Member min = G.DBL.construct();
		Float64Member q1 = G.DBL.construct();
		Float64Member median = G.DBL.construct();
		Float64Member mean = G.DBL.construct();
		Float64Member q3 = G.DBL.construct();
		Float64Member max = G.DBL.construct();

		SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
		
		assertEquals(13, min.v(), 0);
		assertEquals(13, q1.v(), 0); // does not agree with R
		assertEquals(17, median.v(), 0);
		assertEquals(17, mean.v(), 0);
		assertEquals(21, q3.v(), 0); // does not agree with R
		assertEquals(21, max.v(), 0);
	}

	@Test
	public void test3() {

		// In R:
		// x <- c(4,13,21)
		// summary(x)
		//  Min. 1st Qu.  Median    Mean 3rd Qu.    Max.
		//  4.00    8.50   13.00   12.67   17.00   21.00
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {4,13,21});
		
		Float64Member min = G.DBL.construct();
		Float64Member q1 = G.DBL.construct();
		Float64Member median = G.DBL.construct();
		Float64Member mean = G.DBL.construct();
		Float64Member q3 = G.DBL.construct();
		Float64Member max = G.DBL.construct();

		SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
		
		assertEquals(4, min.v(), 0);
		assertEquals(4, q1.v(), 0); // does not agree with R
		assertEquals(13, median.v(), 0);
		assertEquals(12.67, mean.v(), 0.01);
		assertEquals(21, q3.v(), 0); // does not agree with R
		assertEquals(21, max.v(), 0);
	}
	
	@Test
	public void test4() {
		
		// In R:
		// x <- c(1,5,7,44)
		// summary(x)
		//   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
		//   1.00    4.00    6.00   14.25   16.25   44.00 
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {1,5,7,44});
		
		Float64Member min = G.DBL.construct();
		Float64Member q1 = G.DBL.construct();
		Float64Member median = G.DBL.construct();
		Float64Member mean = G.DBL.construct();
		Float64Member q3 = G.DBL.construct();
		Float64Member max = G.DBL.construct();

		SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
		
		assertEquals(1, min.v(), 0);
		assertEquals(3, q1.v(), 0); // does not agree with R
		assertEquals(6, median.v(), 0);
		assertEquals(14.25, mean.v(), 0);
		assertEquals(25.5, q3.v(), 0); // does not agree with R
		assertEquals(44, max.v(), 0);
	}
	
	@Test
	public void test5() {
		
		// In R:
		// x <- c(3,7,12,22,55)
		// summary(x)
		//   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
		//    3.0     7.0    12.0    19.8    22.0    55.0 
		
		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {3,7,12,22,55});
		
		Float64Member min = G.DBL.construct();
		Float64Member q1 = G.DBL.construct();
		Float64Member median = G.DBL.construct();
		Float64Member mean = G.DBL.construct();
		Float64Member q3 = G.DBL.construct();
		Float64Member max = G.DBL.construct();

		SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
		
		assertEquals(3, min.v(), 0);
		assertEquals(5, q1.v(), 0);  // does not agree with R
		assertEquals(12, median.v(), 0);
		assertEquals(19.8, mean.v(), 0.001);
		assertEquals(38.5, q3.v(), 0);  // does not agree with R
		assertEquals(55, max.v(), 0);
	}
	
	@Test
	public void test6orMore() {
		
		// In R:
		// x <- c(1,3,7,12,55,100)
		// summary(x)
		//   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
		//   1.00    4.00    9.50   29.67   44.25  100.00		

		IndexedDataSource<Float64Member> nums = Storage.allocate(G.DBL.construct(), new double[] {1,3,7,12,55,100});
		
		Float64Member min = G.DBL.construct();
		Float64Member q1 = G.DBL.construct();
		Float64Member median = G.DBL.construct();
		Float64Member mean = G.DBL.construct();
		Float64Member q3 = G.DBL.construct();
		Float64Member max = G.DBL.construct();

		SummaryStats.compute(G.DBL, nums, min, q1, median, mean, q3, max);
		
		assertEquals(1, min.v(), 0);
		assertEquals(3, q1.v(), 0);  // does not agree with R
		assertEquals(9.5, median.v(), 0);
		assertEquals(29.67, mean.v(), 0.01);
		assertEquals(55, q3.v(), 0);  // does not agree with R
		assertEquals(100, max.v(), 0);
	}
}
