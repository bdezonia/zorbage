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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.ConvolveND;
import nom.bdezonia.zorbage.algorithm.CorrelateND;
import nom.bdezonia.zorbage.algorithm.FFT;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.InvFFT;
import nom.bdezonia.zorbage.algorithm.ResampleAveragedCubics;
import nom.bdezonia.zorbage.algorithm.ResampleAveragedLinears;
import nom.bdezonia.zorbage.algorithm.ResampleNearestNeighbor;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.oob.nd.ZeroNdOOB;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class SignalProcessing {
	
	// Zorbage provides some signal processing algorithms
	
	// FFT / inverse FFT
	
	void example1() {
		
		IndexedDataSource<ComplexFloat64Member> orig =
				nom.bdezonia.zorbage.storage.Storage.allocate(512L, G.CDBL.construct());
	
		IndexedDataSource<ComplexFloat64Member> tmp =
				nom.bdezonia.zorbage.storage.Storage.allocate(512L, G.CDBL.construct());
		
		Fill.compute(G.CDBL, G.CDBL.random(), orig);
		
		FFT.compute(G.CDBL, G.DBL, orig, tmp);
		
		// < do something here: like make high modulus values into zeroes >
		
		InvFFT.compute(G.CDBL, G.DBL, tmp, orig);
		
		// here the original signal has now had high frequency values removed
	}
	
	// correlation in 1-d or n-d: n-d shown here
	//   also note that parallel versions exists for speedy processing
	
	void example2() {
		
		long[] dims = new long[]{100,100,100};
		
		DimensionedDataSource<Float16Member> input =
				DimensionedStorage.allocate(G.HLF.construct(), dims);

		Fill.compute(G.HLF, G.HLF.random(), input.rawData());
		
		DimensionedDataSource<Float16Member> output =
				DimensionedStorage.allocate(G.HLF.construct(), dims);

		ProcedurePaddedDimensionedDataSource<?, Float16Member> padded =
				new ProcedurePaddedDimensionedDataSource<>(G.HLF, input, new ZeroNdOOB<>(G.HLF, input));
		
		
		DimensionedDataSource<Float16Member> filter =
				DimensionedStorage.allocate(G.HLF.construct(), new long[] {3,3,3});

		Float16Member value = G.HLF.construct();
		
		IntegerIndex index = new IntegerIndex(dims.length);
		
		index.set(0, 1);
		index.set(1, 1);
		index.set(2, 1);
		value.setV(3);
		filter.set(index, value);
		
		index.set(0, 2);
		index.set(1, 1);
		index.set(2, 2);
		value.setV(-4);
		filter.set(index, value);
		
		index.set(0, 0);
		index.set(1, 1);
		index.set(2, 2);
		value.setV(12);
		filter.set(index, value);
		
		CorrelateND.compute(G.HLF, filter, padded, output);
	}

	// convolution in 1-d or n-d: n-d shown here
	//   also note that parallel versions exists for speedy processing
	
	void example3() {
		
		long[] dims = new long[]{100,100,100};
		
		DimensionedDataSource<Float16Member> input =
				DimensionedStorage.allocate(G.HLF.construct(), dims);

		Fill.compute(G.HLF, G.HLF.random(), input.rawData());
		
		DimensionedDataSource<Float16Member> output =
				DimensionedStorage.allocate(G.HLF.construct(), dims);

		ProcedurePaddedDimensionedDataSource<?, Float16Member> padded =
				new ProcedurePaddedDimensionedDataSource<>(G.HLF, input, new ZeroNdOOB<>(G.HLF, input));
		
		
		DimensionedDataSource<Float16Member> filter =
				DimensionedStorage.allocate(G.HLF.construct(), new long[] {3,3,3});

		Float16Member value = G.HLF.construct();
		
		IntegerIndex index = new IntegerIndex(dims.length);
		
		index.set(0, 1);
		index.set(1, 1);
		index.set(2, 1);
		value.setV(3);
		filter.set(index, value);
		
		index.set(0, 2);
		index.set(1, 1);
		index.set(2, 2);
		value.setV(-4);
		filter.set(index, value);
		
		index.set(0, 0);
		index.set(1, 1);
		index.set(2, 2);
		value.setV(12);
		filter.set(index, value);
		
		ConvolveND.compute(G.HLF, filter, padded, output);
	}

	// resampling : multiple algorithms exist. There are the equivalent of nearest neighbor resampling,
	//   bilinear resampling, and bicubic resampling. The major difference is that they work in n-d
	//   space.
	
	@SuppressWarnings("unused")
	void example4() {
		
		DimensionedDataSource<HighPrecisionMember> input =
				DimensionedStorage.allocate(G.HP.construct(), new long[] {1000,1000,1000});
		long[] newDims = new long[] {330,542,861};
		DimensionedDataSource<HighPrecisionMember> output1 = ResampleNearestNeighbor.compute(G.HP, newDims, input);
		DimensionedDataSource<HighPrecisionMember> output2 = ResampleAveragedLinears.compute(G.HP, newDims, input);
		DimensionedDataSource<HighPrecisionMember> output3 = ResampleAveragedCubics.compute(G.HP, newDims, input);
	}
}
