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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.FoldL;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.algorithm.ReduceL;
import nom.bdezonia.zorbage.algorithm.Scale;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.algorithm.Transform1;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.dataview.FiveDView;
import nom.bdezonia.zorbage.dataview.OneDView;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class WaysToComputeThings {

	// Zorbage has a number of ways to calc similar things. We'll compare them here.
	//   (Some day soon I will time these and show the surprising results)
	
	// setting data : use fills for examples
	
	void fill1dData() {

		IndexedDataSource<Float32Member> list = Storage.allocate(G.FLT.construct(), 1000);
		
		Float32Member value = G.FLT.construct(46);
		
		// one method
		
		Fill.compute(G.FLT, value, list);
		
		// a second method

		long sz = list.size();
		for (long i = 0; i < sz; i++) {
			list.set(i, value);
		}
		
		// a third method
		
		Procedure1<Float32Member> proc =
			new Procedure1<Float32Member>() {
					
				@Override
				public void call(Float32Member a) {
					a.set(value);
				}
			};
		
		Transform1.compute(G.FLT, proc, list);
		
		// a fourth method
		
		OneDView<Float32Member> view = new OneDView<>(sz, list);
		
		for (long i = 0; i < view.d0(); i++) {
			view.set(i, value);
		}
	}
	
	void fillNdData() {

		long X = 200;
		long Y = 200;
		long Z = 40;
		long C = 4;
		long T = 100;
		
		long[] dims = new long[] {X, Y, Z, C, T};
		
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);

		Float32Member value = G.FLT.construct(103);
		
		// one way: pass the n-d data source's rawData() accessor to any of the 
		//   four 1-d fill approaches shown above
		//
		// e.g. here is one example
		//
		Fill.compute(G.FLT, value, dataSource.rawData());
		
		// second way: use a view
	
		FiveDView<Float32Member> view = new FiveDView<>(dataSource);
		
		for (long t = 0; t < view.d4(); t++) {
			for (long c = 0; c < view.d3(); c++) {
				for (long z = 0; z < view.d2(); z++) {
					for (long y = 0; y < view.d1(); y++) {
						for (long x = 0; x < view.d0(); x++) {
							view.set(x, y, z, c, t, value);
						}
					}
				}
			}
		}
		
		// third way: n-d access
		
		IntegerIndex idx = new IntegerIndex(dataSource.numDimensions());
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(dataSource);
		
		while (iter.hasNext()) {
			iter.next(idx);
			dataSource.set(idx, value);
		}
	}

	// getting data : use sums as an example
	
	void sum1dData() {

		IndexedDataSource<Float32Member> list = Storage.allocate(G.FLT.construct(), 1000);
		
		Float32Member sum = G.FLT.construct();
		
		Float32Member tmp = G.FLT.construct();
		
		// one method
		
		Sum.compute(G.FLT, list, sum);
		
		// a second method

		G.FLT.zero().call(sum);
		for (long i = 0; i < list.size(); i++) {
			list.get(i, tmp);
			G.FLT.add().call(sum, tmp, sum);
		}
		
		// a third method
		
		OneDView<Float32Member> view = new OneDView<>(list.size(), list);
		G.FLT.zero().call(sum);
		for (long i = 0; i < view.d0(); i++) {
			view.get(i, tmp);
			G.FLT.add().call(sum, tmp, sum);
		}
		
		// a fourth method using a reduce algorithm
		
		ReduceL.compute(G.FLT, G.FLT.add(), list, sum);

		// a fifth method using a fold algorithm
		
		FoldL.compute(G.FLT, G.FLT.add(), G.FLT.construct(), list, sum);
	}
	
	void sumNdData() {
		
		long X = 200;
		long Y = 200;
		long Z = 40;
		long C = 4;
		long T = 100;
		
		long[] dims = new long[] {X, Y, Z, C, T};
		
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);

		Float32Member sum = G.FLT.construct();
		
		Float32Member tmp = G.FLT.construct();
		
		// one way: pass the n-d data source's rawData() accessor to any of the 
		//   four 1-d sum approaches shown above
		//
		// e.g. here is one example
		//
		Sum.compute(G.FLT, dataSource.rawData(), sum);

		// second way: use a view
		
		FiveDView<Float32Member> view = new FiveDView<>(dataSource);

		G.FLT.zero().call(sum);
		
		for (long t = 0; t < view.d4(); t++) {
			for (long c = 0; c < view.d3(); c++) {
				for (long z = 0; z < view.d2(); z++) {
					for (long y = 0; y < view.d1(); y++) {
						for (long x = 0; x < view.d0(); x++) {
							view.get(x, y, z, c, t, tmp);
							G.FLT.add().call(sum, tmp, sum);
						}
					}
				}
			}
		}
		
		// third way: n-d access
		
		IntegerIndex idx = new IntegerIndex(dataSource.numDimensions());
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(dataSource);
		
		G.FLT.zero().call(sum);
		
		while (iter.hasNext()) {
			iter.next(idx);
			dataSource.get(idx, tmp);
			G.FLT.add().call(sum, tmp, sum);
		}
	}

	// getting and setting data : use scale for examples
	
	void scale1dData() {

		IndexedDataSource<Float32Member> list = Storage.allocate(G.FLT.construct(), 1000);
		
		Float32Member scale = G.FLT.construct(46);
		
		Float32Member tmp = G.FLT.construct();
		
		// one method
		
		Scale.compute(G.FLT, scale, list, list);
		
		// a second method

		long sz = list.size();
		for (long i = 0; i < sz; i++) {
			list.get(i, tmp);
			G.FLT.multiply().call(tmp, scale, tmp);
			list.set(i, tmp);
		}
		
		// a third method
		
		Procedure3<Float32Member,Float32Member,Float32Member> proc =
			new Procedure3<Float32Member,Float32Member,Float32Member>() {
					
				@Override
				public void call(Float32Member scale, Float32Member a, Float32Member b) {
					b.setV(scale.v() * a.v());
				}
			};
		
		TransformWithConstant.compute(G.FLT, proc, scale, list, list);
		
		// a fourth method
		
		OneDView<Float32Member> view = new OneDView<>(sz, list);
		
		for (long i = 0; i < view.d0(); i++) {
			view.get(i, tmp);
			G.FLT.multiply().call(tmp, scale, tmp);
			view.set(i, tmp);
		}
	}

	void scaleNdData() {
		
		long X = 200;
		long Y = 200;
		long Z = 40;
		long C = 4;
		long T = 100;
		
		long[] dims = new long[] {X, Y, Z, C, T};
		
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);

		Float32Member scale = G.FLT.construct(33);
		
		Float32Member tmp = G.FLT.construct();
		
		// one way: pass the n-d data source's rawData() accessor to any of the 
		//   four 1-d sum approaches shown above. Usually quite fast.
		//
		// e.g. here is one example
		//
		Scale.compute(G.FLT, scale, dataSource.rawData(), dataSource.rawData());

		// second way: use a view: usually quite fast
		
		FiveDView<Float32Member> view = new FiveDView<>(dataSource);

		for (long t = 0; t < view.d4(); t++) {
			for (long c = 0; c < view.d3(); c++) {
				for (long z = 0; z < view.d2(); z++) {
					for (long y = 0; y < view.d1(); y++) {
						for (long x = 0; x < view.d0(); x++) {
							view.get(x, y, z, c, t, tmp);
							G.FLT.multiply().call(tmp, scale, tmp);
							view.set(x, y, z, c, t, tmp);
						}
					}
				}
			}
		}
		
		// third way: n-d access: slow
		
		IntegerIndex idx = new IntegerIndex(dataSource.numDimensions());
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(dataSource);
		
		while (iter.hasNext()) {
			iter.next(idx);
			dataSource.get(idx, tmp);
			G.FLT.multiply().call(tmp, scale, tmp);
			dataSource.set(idx, tmp);
		}
	}
	
}
