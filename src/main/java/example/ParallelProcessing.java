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
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algorithm.DataConvert;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.Generate;
import nom.bdezonia.zorbage.algorithm.ConvolveND;
import nom.bdezonia.zorbage.algorithm.CorrelateND;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.Transform1;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.Transform4;
import nom.bdezonia.zorbage.algorithm.ResampleAveragedCubics;
import nom.bdezonia.zorbage.algorithm.ResampleAveragedLinears;
import nom.bdezonia.zorbage.algorithm.ResampleNearestNeighbor;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.oob.nd.MirrorNdOOB;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class ParallelProcessing {
	
	/*
	 * Zorbage provides some precanned algorithms that use parallel processing
	 *   to improve performance. Some of them are very general and reusable.
	 */
	
	// DataConvert : convert values in one list into values of a different type in another list
	
	void example1() {
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(G.INT16.construct(), 10000000);
		
		// here is the target list of the same size
		
		IndexedDataSource<HighPrecisionMember> highPrecList = Storage.allocate(G.HP.construct(), shortList.size());
		
		// convert the data with as many threads as the computer can spare
		
		DataConvert.compute(G.INT16, G.HP, shortList, highPrecList);

		// the highPrecList is now full of converted short values
	}
	
	// Fill : fill a list with data

	void example2() {
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(G.INT16.construct(), 10000000);
		
		// fill it with as many threads as the computer can spare
		
		Fill.compute(G.INT16, G.INT16.construct("300"), shortList);

		// the shortList is now full of 300s
	}
	
	// Generate : similar to Fill. Fill can take a Procedure1<U> as a parameter. Generate
	//   can instead take a Procedure<U> as a parameter. Procedure<U>'s can be fed input parameters by
	//   the caller. Generate is slightly more powerful than Fill.
	
	void example3() {

		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(G.INT16.construct(), 10000000);

		// here is my procedure that generates values
		
		Procedure<SignedInt16Member> proc44 = new Procedure<SignedInt16Member>()
		{
			@Override
			public void call(SignedInt16Member result, SignedInt16Member... inputs) {
				result.setV(44);
			}
		};

		// generate data with as many threads as the computer can spare
		
		Generate.compute(G.INT16, proc44, shortList);
		
		// the shortList is now full of 44s
	}
	
	// Transform1 : transform one list by setting values to the computation of a passed in Procedure1.
	//   A Procedure1 computes a value with no input form any source (except initialization provided to the
	//   Procedure1 by the developer). This is in essence very similar to Generate.
	
	void example4() {
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(G.INT16.construct(), 10000000);
		
		Transform1.compute(G.INT16, G.INT16.maxBound(), shortList);
		
		// the shortList has each value set to 32767
	}
	
	// Transform2 : transform a destination list by applying a Procedure2 to a source list. This is
	// an honest to goodness transformation. A Procedure2 takes an input value, calculates something, and
	// sets an output value to the result. Transform2 uses as many threads as the cpu can spare to
	// do this transformation quickly. This algorithm is very similar to the Map algorithm.
	
	void example5() {

		// source list of all zeroes
		
		IndexedDataSource<SignedInt16Member> shortList1 = ArrayStorage.allocate(G.INT16.construct(), 10000000);

		// dest list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList2 = shortList1.duplicate();
		
		// fill the source list with random positive and negative 16-bit values
		
		Fill.compute(G.INT16, G.INT16.random(), shortList1);
		
		// now set the destination list to contain only the values in the source list each incremented by one.
		// For integers succ(x) == x+1.
		
		Transform2.compute(G.INT16, G.INT16.succ(), shortList1, shortList2);
	}
	
	// Transform3 : transform a destination list by applying a Procedure3 to two source lists. A
	// Procedure3 takes two input values, calculates something, and sets an output value to the result.
	// Transform3 uses as many threads as the cpu can spare to do this transformation quickly.
	
	void example6() {
		
		// source1 list of all zeroes
		
		IndexedDataSource<SignedInt16Member> shortList1 = ArrayStorage.allocate(G.INT16.construct(),10000000);

		// source2 list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList2 = shortList1.duplicate();
		
		// dest list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList3 = shortList1.duplicate();
		
		// fill the source lists with random positive and negative 16-bit values
		
		Fill.compute(G.INT16, G.INT16.random(), shortList1);
		Fill.compute(G.INT16, G.INT16.random(), shortList2);
		
		// now set the destination list to contain the sum of the values in the source lists
		
		Transform3.compute(G.INT16, G.INT16.add(), shortList1, shortList2, shortList3);
	}
	
	// Transform4 : transform a destination list by applying a Procedure4 to three source lists.
	// A Procedure4 takes three input values, calculates something, and sets an output value to the result.
	// Transform4 uses as many threads as the cpu can spare to do this transformation quickly.
	
	void example7() {
		
		// source1 list of all zeroes
		
		IndexedDataSource<SignedInt16Member> shortList1 = ArrayStorage.allocate(G.INT16.construct(),10000000);

		// source2 list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList2 = shortList1.duplicate();
		
		// source3 list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList3 = shortList1.duplicate();
		
		// dest list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList4 = shortList1.duplicate();
		
		// fill the source lists with random positive and negative values
		
		Fill.compute(G.INT16, G.INT16.random(), shortList1);
		Fill.compute(G.INT16, G.INT16.random(), shortList2);
		Fill.compute(G.INT16, G.INT16.random(), shortList3);

		// define a three way function
		
		Procedure4<SignedInt16Member,SignedInt16Member,SignedInt16Member,SignedInt16Member> proc =
				new Procedure4<SignedInt16Member, SignedInt16Member, SignedInt16Member, SignedInt16Member>()
		{
			@Override
			public void call(SignedInt16Member a, SignedInt16Member b, SignedInt16Member c, SignedInt16Member d) {
				
				// set d to a linear combination of a, b, and c
				
				d.setV(4*a.v() - 2*b.v() + c.v());
			}
		}; 
		
		// now set the destination list to contain a 3 way function of the values in the source lists
		
		Transform4.compute(G.INT16, proc, shortList1, shortList2, shortList3, shortList4);
	}
	
	/* Zorbage also provides some specialized algorithms that use multiple threads for speed. */
	
	// MatrixMultiply: multiple matrices quickly by utilizing multiple threads
	
	void example8() {
		
		Float64MatrixMember a = new Float64MatrixMember(StorageConstruction.MEM_ARRAY, 1000, 1000);
		Float64MatrixMember b = new Float64MatrixMember(StorageConstruction.MEM_ARRAY, 1000, 1000);
		Float64MatrixMember c = new Float64MatrixMember();
		
		Fill.compute(G.DBL, G.DBL.random(), a.rawData());
		Fill.compute(G.DBL, G.DBL.random(), b.rawData());
		
		MatrixMultiply.compute(G.DBL, a, b, c);
	}
	
	// ConvolveND : convolve a n-d dataset using multiple threads

	void example9() {
		
		// create an out of bounds value producing procedure
		
		Procedure2<IntegerIndex,Float64Member> proc = new Procedure2<IntegerIndex, Float64Member>()
		{
			@Override
			public void call(IntegerIndex a, Float64Member b) {
				b.setV(53.2); // for now ignore position index a. Just set to a constant value when out of bounds.
			}
		};
		
		// create the dataset to read
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(G.DBL.construct(), new long[] {1000,1000,1000});
		
		// fill it
		
		Fill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// pad it so that out of bounds accesses will still produce a value. Convolution will always poke out of bounds.
		
		DimensionedDataSource<Float64Member> padded = 
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, proc);
		
		// get ready to store result
		
		DimensionedDataSource<Float64Member> result = DimensionedStorage.allocate(G.DBL.construct(), new long[] {1000,1000,1000});
		
		// create the convolution kernel to use to combine neighboring values with
		
		DimensionedDataSource<Float64Member> kernel = DimensionedStorage.allocate(G.DBL.construct(), new long[] {3,3,3});
		IntegerIndex idx = new IntegerIndex(ds.numDimensions());
		Float64Member value = G.DBL.construct();

		value.setV(-1);
		idx.set(0, 0);
		idx.set(1, 0);
		idx.set(2, 0);
		kernel.set(idx, value);
		
		value.setV(4);
		idx.set(0, 1);
		idx.set(1, 2);
		idx.set(2, 1);
		kernel.set(idx, value);
		
		value.setV(2);
		idx.set(0, 2);
		idx.set(1, 1);
		idx.set(2, 2);
		kernel.set(idx, value);
		
		// run the convolution in parallel
		
		ConvolveND.compute(G.DBL, kernel, padded, result);
	}
	
	// CorrelateND : correlate a n-d dataset using multiple threads
	
	void example10() {
		
		// create an out of bounds value producing procedure
		
		Procedure2<IntegerIndex,Float64Member> proc = new Procedure2<IntegerIndex, Float64Member>()
		{
			@Override
			public void call(IntegerIndex a, Float64Member b) {
				b.setV(53.2); // for now ignore position index a. Just set to a constant value when out of bounds.
			}
		};
		
		// create the dataset to read
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(G.DBL.construct(), new long[] {1000,1000,1000});
		
		// fill it
		
		Fill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// pad it so that out of bounds accesses will still produce a value. Correlation will always poke out of bounds.
		
		DimensionedDataSource<Float64Member> padded = 
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, proc);
		
		// get ready to store result
		
		DimensionedDataSource<Float64Member> result = DimensionedStorage.allocate(G.DBL.construct(), new long[] {1000,1000,1000});
		
		// create the correlation kernel to use to combine neighboring values with
		
		DimensionedDataSource<Float64Member> kernel = DimensionedStorage.allocate(G.DBL.construct(), new long[] {3,3,3});
		IntegerIndex idx = new IntegerIndex(ds.numDimensions());
		Float64Member value = G.DBL.construct();

		value.setV(-1);
		idx.set(0, 0);
		idx.set(1, 0);
		idx.set(2, 0);
		kernel.set(idx, value);
		
		value.setV(4);
		idx.set(0, 1);
		idx.set(1, 2);
		idx.set(2, 1);
		kernel.set(idx, value);
		
		value.setV(2);
		idx.set(0, 2);
		idx.set(1, 1);
		idx.set(2, 2);
		kernel.set(idx, value);
		
		// run the correlation in parallel
		
		CorrelateND.compute(G.DBL, kernel, padded, result);
	}

	// ResampleNearestNeighbor : resample a dataset to a different resolution using nearest neighbor interpolation
	
	DimensionedDataSource<Float64Member> example11() {
		
		// create the dataset to read
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(G.DBL.construct(), new long[] {8192,8192});

		// pad the dataset so out of bounds accesses don't fail
		
		DimensionedDataSource<Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, new MirrorNdOOB<>(ds));
		
		// fill it with random data
		
		Fill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// resample the 2d image to a much smaller size
		
		return ResampleNearestNeighbor.compute(G.DBL, new long[] {500, 500}, padded);
	}
	
	// ResampleAveragedLinears : resample a dataset to a different resolution using linear interpolation
	
	DimensionedDataSource<Float64Member> example12() {
		
		// create the dataset to read
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(G.DBL.construct(), new long[] {8192,8192});

		// pad the dataset so out of bounds accesses don't fail
		
		DimensionedDataSource<Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, new MirrorNdOOB<>(ds));
		
		// fill it with random data
		
		Fill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// resample the 2d image to a much smaller size
		
		return ResampleAveragedLinears.compute(G.DBL, new long[] {500, 500}, padded);
	}
	
	// ResampleAveragedCubics : resample a dataset to a different resolution using cubic interpolation
	
	DimensionedDataSource<Float64Member> example13() {
		
		// create the dataset to read
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(G.DBL.construct(), new long[] {8192,8192});

		// pad the dataset so out of bounds accesses don't fail
		
		DimensionedDataSource<Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, new MirrorNdOOB<>(ds));
		
		// fill it with random data
		
		Fill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// resample the 2d image to a much smaller size
		
		return ResampleAveragedCubics.compute(G.DBL, new long[] {500, 500}, padded);
	}
	
}
