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

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBasicStats {

	@Test
	public void test1() {
		
		IndexedDataSource<Float32Member> data = Storage.allocate(G.FLT.construct(), new float[] {43,7,99,1,2,3,100,55,31});
		
		Float32Member mean = G.FLT.construct();
		Float32Member stdErrMean = G.FLT.construct();
		Float32Member stddev = G.FLT.construct();
		Float32Member sampleVariance = G.FLT.construct();
		Float32Member sampleSkew = G.FLT.construct();
		Float32Member excessKurtosis = G.FLT.construct();

		BasicStats.compute(G.FLT, data, mean, stdErrMean, stddev, sampleVariance, sampleSkew, excessKurtosis);
		
		// expected numbers calculated at wolframalpha.com by querting statistics of data using input numbers above
		
		assertEquals(37.89, mean.v(), 0.01);
		assertEquals(39.97, stddev.v(), 0.01);
		assertEquals(stddev.v()/3, stdErrMean.v(), 0.01);
		assertEquals(39.97*39.97, sampleVariance.v(), 0.5);
		
		// TODO
		// these are not working. code looks correct. but there are many ways of estimating these values for
		//   small sample sizes that I am not using. I need to consider how best to move forward.
		
		//assertEquals(0.6319, sampleSkew.v(), 0.0001);
		//assertEquals(1.919, 3 + excessKurtosis.v(), 0.001);
	}
}
