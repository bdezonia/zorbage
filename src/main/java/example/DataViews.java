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
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.dataview.FiveDView;
import nom.bdezonia.zorbage.dataview.OneDView;
import nom.bdezonia.zorbage.dataview.ThreeDView;
import nom.bdezonia.zorbage.dataview.TwoDView;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorageSignedInt16;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
class DataViews {

	// Zorbage defines a very fast data accessor facility for multidimensional
	// data known as data views. When your algorithm knows the exact number of
	// dimensions it works in it can take a data source of those same number
	// of dimensions and wrap a view around it.
	
	// Views come in many dimensions. There is one for every dimension (at the
	// moment) between 1 and 21. A 1-d view is defined by the OneDView class.
	// A 4-d view is defined by the FourDView class. You can probably guess
	// what a 21-d view is defined as.
	
	// One reason to use views is because they are very simple to declare and
	// use to make clear algorithms. Sometimes the regular DimensionedDataSource
	// approach is slow and unclear. Maybe an example will help.
	
	void example1() {

		// create a 5-d float32 data source
		
		Float32Member value = G.FLT.construct();

		long X = 200;
		long Y = 200;
		long Z = 40;
		long C = 4;
		long T = 100;
		
		long[] dims = new long[] {X, Y, Z, C, T};
		
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);
		
		// set every value to 3:
		
		//   note: you would never do it this way. use the Fill algo on DS.RawData.
		
		value.setV(3);
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(dataSource);
		
		IntegerIndex idx = new IntegerIndex(dataSource.numDimensions());
		
		while (iter.hasNext()) {
		
			iter.next(idx);
			dataSource.set(idx, value);
			
		}
		
		// this is successful but can be quite slow
	}
	
	// an example that uses a data view

	void example2() {

		// create a 5-d float32 data source
		
		Float32Member value = G.FLT.construct();

		long X = 200;
		long Y = 200;
		long Z = 40;
		long C = 4;
		long T = 100;
		
		long[] dims = new long[] {X, Y, Z, C, T};
		
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);
		
		// set every value to 3:
		
		//   note: much clearer and faster way: still use the Fill algo on DS.RawData.
		
		value.setV(3);
		
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
		
		value.setV(-1);
		
		view.get(X-1, Y-1, Z-1, C-1, T-1, value);
		
		if (value.v() != 2) System.out.println("View failed");

		// this code is successful and can run as much as 10X faster
		//   than the default multidimensional code above
	}
	
	// One thing about data views is that they allow you to grab any subset
	// of data you can express in nested for loops.
	
	void example3() {

		// create a 3-d float32 data source
		
		long X = 200;
		long Y = 200;
		long Z = 40;
		
		long[] dims = new long[] {X, Y, Z};
		
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);
		
		// find the sum of values in a 3x3 box in upper left corner of 4th z plane
		
		Float32Member sum = G.FLT.construct();
		Float32Member tmp = G.FLT.construct();

		ThreeDView<Float32Member> view = new ThreeDView<>(dataSource);

		long z = 4-1; // 4th plane is at index 3
		for (long y = 0; y < 3; y++) {
			for (long x = 0; x < 3; x++) {
				view.get(x, y, z, tmp);
				G.FLT.add().call(sum, tmp, sum);
			}
		}
		
		System.out.println(sum);
	}
	
	// One can define all kinds of regions via for loops such as pipeds,
	// planes, volumes (plane collections), hypervolumes, one channel of
	// a whole multichannel dataset, etc. You can take every 5th value
	// along the time axis etc.

	// You can construct data views from a DimensionedDataSource or an
	// IndexedDataSource. Data views do not respect out of bounds 
	// policies for a given source. It is assumed you will always access
	// a data view in bounds. You can call the safeSet()/safeGet()
	// methods. They bounds check but simply abort their operation
	// with an error exception.
	
	// Here is an example of this using an IndexedDataSource
	
	void example4() {
		
		IndexedDataSource<Float32Member> data =
				Storage.allocate(G.FLT.construct(), 100);
		
		OneDView<Float32Member> view = new OneDView<>(data.size(),data);
		
		Float32Member value = G.FLT.construct();
		
		value.setV(0);
		
		view.get(-data.size(), value);  // may poke a random memory location
		
		view.safeGet(-data.size(), value);  // will throw an index oob exception
		
		view.get(data.size()*2, value);  // may poke a random memory location
		
		view.safeGet(data.size()*2, value);  // will throw an index oob exception
	}
	
	// And here is an example of this using a DimensionedDataSource
	
	void example5() {
		
		long[] dims = new long[] {100, 100, 25}; // x, y, z
		
		DimensionedDataSource<Float32Member> dds =
				DimensionedStorage.allocate(G.FLT.construct(), dims);
		
		ThreeDView<Float32Member> view = new ThreeDView<>(dds);
		
		Float32Member value = G.FLT.construct();
		
		view.get(50, 50, -1, value);  // may poke a random memory location
		
		view.safeGet(50, 50, -1, value);  // will throw an index oob exception
		
		view.get(50, 50, 1000000, value);  // may poke a random memory location
		
		view.safeGet(50, 50, 1000000, value);  // will throw an index oob exception
	}
	
	// One nice thing about a data view is that you can enforce a shape to
	// the data you might prefer. For instance, given a 1-d list of storage
	// you might want to wrap it like a 2-d dataset and manipulate values
	// as you desire.
	
	void example6() {
		
		IndexedDataSource<SignedInt16Member> nums =
				new ArrayStorageSignedInt16<>(G.INT16.construct(), 64);
		
		TwoDView<SignedInt16Member> view = new TwoDView<>(8, 8, nums);

		SignedInt16Member value = G.INT16.construct();
		
		for (long y = 0; y < view.d1(); y++) {
			for (long x = 0; x < view.d0(); x++) {
				value.setV((int) x);
				view.set(x, y, value);
			}
		}
		
		// Note that this is also possible with DimensionedDataSources
		// in an indirect fashion. Normally a view that takes a
		// DimensionedDataSource as input just uses the dimensions of
		// the source. But you can create a redimensioned source by
		// passing the dimensionedDataSource.rawData() to a view
		// constructor and specify the new dimensions.
	}

}
