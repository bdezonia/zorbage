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
import nom.bdezonia.zorbage.algorithm.FFT;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.datasource.FixedSizeDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ProcedurePaddedDataSource;
import nom.bdezonia.zorbage.oob.nd.ConstantNdOOB;
import nom.bdezonia.zorbage.oob.nd.CyclicNdOOB;
import nom.bdezonia.zorbage.oob.nd.EdgeNdOOB;
import nom.bdezonia.zorbage.oob.nd.MirrorNdOOB;
import nom.bdezonia.zorbage.oob.nd.NanNdOOB;
import nom.bdezonia.zorbage.oob.nd.ZeroNdOOB;
import nom.bdezonia.zorbage.oob.oned.ConstantOOB;
import nom.bdezonia.zorbage.oob.oned.CyclicOOB;
import nom.bdezonia.zorbage.oob.oned.EdgeOOB;
import nom.bdezonia.zorbage.oob.oned.MirrorOOB;
import nom.bdezonia.zorbage.oob.oned.NanOOB;
import nom.bdezonia.zorbage.oob.oned.ZeroOOB;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Algebra;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * @author Barry DeZonia
 */
class OutOfBoundsData {

	/*
	 * Sometimes you use an algorithm that can query data point values which are out of
	 * the bounds of the dataset of interest. Zorbage provides a mechanism for padding
	 * datasets so that (probably neutral) values can pass through an algorithm without
	 * corrupting the output values.
	 */
	
	/*
	 * This can be apparent in 1-d datasets if you consider the case of finding the FFT
	 * of a dataset. The current FFT algorithm requires an input dataset whose length
	 * is a power of 2. In order to pass any size list of data to the FFT algorithm
	 * Zorbage allows you to pad a dataset with appropriate values. Then you wrap the
	 * dataset so that any observer thinks its list's length is a power of two. Maybe
	 * an example will be helpful here.
	 */
	
	void example1() {
		
		// Make an unusual size list
		
		IndexedDataSource<ComplexFloat32Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.CFLT.construct(), 413);

		// 413
		long weirdSize = data.size();
		
		// 512
		long perfectSize = FFT.enclosingPowerOf2(weirdSize);
		
		// Fill our input list with random data
		
		Fill.compute(G.CFLT, G.CFLT.random(), data);
		
		// Now make sure querying it outside its bounds will return the value zero. The
		// ZeroOOB procedure does the heavy lifting. The ProcedurePaddedDataSource returns
		// values from its underlying data source when the coordinates queried are within
		// its bounds and returns the result from a procedure call when they are out.
		
		IndexedDataSource<ComplexFloat32Member> paddedData =
				new ProcedurePaddedDataSource<>(
						G.CFLT,
						data,
						new ZeroOOB<>(G.CFLT, data.size()));
		
		// The FFT wants a power of two size. make the list have a perfect size.
		
		IndexedDataSource<ComplexFloat32Member> perfectSizedData =
				new FixedSizeDataSource<>(perfectSize, paddedData);
		
		// Allocate the destination data list
		
		IndexedDataSource<ComplexFloat32Member> fftData =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.CFLT.construct(), perfectSize);
		
		// The fft algorithm will hit every location (0 to 511). The out of bounds procedure
		// will return 0 when the original data is queried beyond location 412. The extra
		// zero values will have no effect on the final results
		
		FFT.compute(G.CFLT, G.FLT, perfectSizedData, fftData);
	}
	
	// There are other predefined 1-d out of bounds behaviors. Here is a quick tour of them.
	
	@SuppressWarnings("unused")
	void example2() {
		
		IndexedDataSource<Float32Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.FLT.construct(), 100);

		// pad with a constant like 0.4
		
		IndexedDataSource<Float32Member> constantPad =
				new ProcedurePaddedDataSource<>(
						G.FLT,
						data,
						new ConstantOOB<>(G.FLT, data.size(), G.FLT.construct("0.4")));

		// pad in a cyclic fashion
		//   imagine list = [0, 1, 2, 3]
		//   then with cyclic oob
		//     list[3] == 3
		//     list[4] == 0
		//     list[5] == 1
		//     list[6] == 2
		//     list[7] == 3
		//     list[8] == 0
		//     list[9] == 1
		
		IndexedDataSource<Float32Member> cyclicPad =
				new ProcedurePaddedDataSource<>(
						G.FLT,
						data,
						new CyclicOOB<>(data));
		
		// pad from the edges
		//   imagine list = [1, 2, 3, 4]
		//   then with edge oob
		//     etc.
		//     list[-2] == 1
		//     list[-1] == 1
		//     list[0] == 1
		//     list[1] == 2
		//     list[2] == 3
		//     list[3] == 4
		//     list[4] == 4
		//     list[5] == 4
		//     etc.
		
		IndexedDataSource<Float32Member> edgePad =
				new ProcedurePaddedDataSource<>(
						G.FLT,
						data,
						new EdgeOOB<>(data));
		
		// pad in a mirrored fashion
		//   imagine list = [1, 2, 3, 4]
		//   then with mirrored oob
		//     etc.
		//     list[-6] == 3
		//     list[-5] == 4
		//     list[-4] == 4
		//     list[-3] == 3
		//     list[-2] == 2
		//     list[-1] == 1
		//     list[0] == 1
		//     list[1] == 2
		//     list[2] == 3
		//     list[3] == 4
		//     list[4] == 4
		//     list[5] == 3
		//     list[4] == 2
		//     list[5] == 1
		//     list[4] == 1
		//     list[5] == 2
		//     etc.
		
		IndexedDataSource<Float32Member> mirrorPad =
				new ProcedurePaddedDataSource<>(
						G.FLT,
						data,
						new MirrorOOB<>(data));
		

		// pad with NaN
		
		IndexedDataSource<Float32Member> nanPad =
				new ProcedurePaddedDataSource<>(
						G.FLT,
						data,
						new NanOOB<>(G.FLT, data.size()));
		
		// pad with 0.0
		
		IndexedDataSource<Float32Member> zeroPad =
				new ProcedurePaddedDataSource<>(
						G.FLT,
						data,
						new ZeroOOB<>(G.FLT, data.size()));

	}
	
	/*
	 * Zorbage has similar capabilities in the n-d domain rather than 1-d domain. One 
	 * example where these become useful is when doing convolutions on n-d datasets.
	 */
	
	void example3() {
	
		// original dims of input data
		
		long[] dims = new long[] {640,480};
		
		// make input
		
		DimensionedDataSource<Float32Member> input =
				DimensionedStorage.allocate(G.FLT.construct(), dims);
		
		// assume here we've filled it with some values
		
		// now pad it so id we request an out of bounds pixel we get a 0
		
		ProcedurePaddedDimensionedDataSource<Float32Algebra,Float32Member> paddedInput = 
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new ZeroNdOOB<>(G.FLT, input));
		
		// make an output dataset that has the same dims as the original dataset
		
		DimensionedDataSource<Float32Member> output =
				DimensionedStorage.allocate(G.FLT.construct(), dims);
		
		// make a 3x3 convolution filter
		
		DimensionedDataSource<Float32Member> filter =
				DimensionedStorage.allocate(G.FLT.construct(), new long[] {3,3});
		
		// assume we've set the nine filter values to something sensible
		
		// run a convolution. the convolve algorithm will slide the window around on the
		// padded image and will poke outside the bounds of the original image. the padded
		// image just returns zero for these places. and this has no effect on the computed
		// output values.
		
		ConvolveND.compute(G.FLT, filter, paddedInput, output);
	}
	
	// There are other predefined n-d out of bounds behaviors. Here is a quick tour of them.
	// Note that their behavior matches their 1-d counterparts documented above. The
	// following code just shows you how to construct them.
	
	@SuppressWarnings("unused")
	void example4() {
		
		// make input
		
		DimensionedDataSource<Float32Member> input =
				DimensionedStorage.allocate(G.FLT.construct(), new long[] {1024, 1024});
		
		// make constant padded input
		
		DimensionedDataSource<Float32Member> constantPad =
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new ConstantNdOOB<>(G.FLT, input, G.FLT.construct("1.3")));
		
		// make cyclic padded input
		
		DimensionedDataSource<Float32Member> cyclicPad =
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new CyclicNdOOB<>(input));
		
		// make edge padded input
		
		DimensionedDataSource<Float32Member> edgePad =
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new EdgeNdOOB<>(input));
		
		// make mirror padded input
		
		DimensionedDataSource<Float32Member> mirrorPad =
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new MirrorNdOOB<>(input));
		
		// make NaN padded input
		
		DimensionedDataSource<Float32Member> nanPad =
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new NanNdOOB<>(G.FLT, input));
		
		// make zero padded input
		
		DimensionedDataSource<Float32Member> zeroPad =
				new ProcedurePaddedDimensionedDataSource<>(
						G.FLT,
						input,
						new ZeroNdOOB<>(G.FLT, input));
	}
	
}
