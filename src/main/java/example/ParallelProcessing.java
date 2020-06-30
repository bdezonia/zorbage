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
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algorithm.DataConvert;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.Generate;
import nom.bdezonia.zorbage.algorithm.ParallelConvolveND;
import nom.bdezonia.zorbage.algorithm.ParallelCorrelateND;
import nom.bdezonia.zorbage.algorithm.ParallelFill;
import nom.bdezonia.zorbage.algorithm.ParallelMatrixMultiply;
import nom.bdezonia.zorbage.algorithm.ParallelTransform1;
import nom.bdezonia.zorbage.algorithm.ParallelTransform2;
import nom.bdezonia.zorbage.algorithm.ParallelTransform3;
import nom.bdezonia.zorbage.algorithm.ParallelTransform4;
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
import nom.bdezonia.zorbage.type.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;

/**
 * @author Barry DeZonia
 */
class ParallelProcessing {
	
	/*
	 * Zorbage provides some precanned algorithms that use parallel processing to improve performance.
	 *   Some of them are very general and reusable.
	 */
	
	// DataConvert : convert values in one list into values of a different type in another list
	
	void example1() {
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(10000000, G.INT16.construct());
		
		// here is the target list of the same size
		
		IndexedDataSource<HighPrecisionMember> highPrecList = Storage.allocate(shortList.size(), G.HP.construct());
		
		// convert the data with as many threads as the computer can spare
		
		DataConvert.compute(G.INT16, G.HP, shortList, highPrecList);

		// the highPrecList is now full of converted short values
	}
	
	// ParallelFill : fill a list with data

	void example2() {
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(10000000, G.INT16.construct());
		
		// fill it with as many threads as the computer can spare
		
		ParallelFill.compute(G.INT16, G.INT16.construct("300"), shortList);

		// the shortList is now full of 300s
	}
	
	// Generate : similar to ParallelFill. ParallelFill can take a Procedure1<U> as a parameter. Generate
	//   can instead take a Procedure<U> as a parameter. Procedure<U>'s can be fed input parameters by
	//   the caller. Generate is slightly more powerful than ParallelFill.
	
	void example3() {

		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(10000000, G.INT16.construct());

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
	
	// ParallelTransform1 : transform one list by setting values to the computation of a passed in Procedure1.
	//   A Procedure1 computes a value with no input form any source (except initialization provided to the
	//   Procedure1 by the developer). This is in essence very similar to Generate.
	
	void example4() {
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = ArrayStorage.allocate(10000000, G.INT16.construct());
		
		ParallelTransform1.compute(G.INT16, G.INT16.maxBound(), shortList);
		
		// the shortList has each value set to 32767
	}
	
	// ParallelTransform2 : transform a destination list by applying a Procedure2 to a source list. This is
	// an honest to goodness transformation. A Procedure2 takes an input value, calculates something, and
	// sets an output value to the result. ParallelTransform2 uses as many threads as the cpu can spare to
	// do this transformation quickly. This algorithm is very similar to the Map algorithm.
	
	void example5() {

		// source list of all zeroes
		
		IndexedDataSource<SignedInt16Member> shortList1 = ArrayStorage.allocate(10000000, G.INT16.construct());

		// dest list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList2 = shortList1.duplicate();
		
		// fill the source list with random positive and negative 16-bit values
		
		ParallelFill.compute(G.INT16, G.INT16.random(), shortList1);
		
		// now set the destination list to contain only the values in the source list each incremented by one.
		// For integers succ(x) == x+1.
		
		ParallelTransform2.compute(G.INT16, G.INT16.succ(), shortList1, shortList2);
	}
	
	// ParallelTransform3 : transform a destination list by applying a Procedure3 to two source lists. A
	// Procedure3 takes two input values, calculates something, and sets an output value to the result.
	// ParallelTransform3 uses as many threads as the cpu can spare to do this transformation quickly.
	
	void example6() {
		
		// source1 list of all zeroes
		
		IndexedDataSource<SignedInt16Member> shortList1 = ArrayStorage.allocate(10000000, G.INT16.construct());

		// source2 list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList2 = shortList1.duplicate();
		
		// dest list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList3 = shortList1.duplicate();
		
		// fill the source lists with random positive and negative 16-bit values
		
		ParallelFill.compute(G.INT16, G.INT16.random(), shortList1);
		ParallelFill.compute(G.INT16, G.INT16.random(), shortList2);
		
		// now set the destination list to contain the sum of the values in the source lists
		
		ParallelTransform3.compute(G.INT16, G.INT16.add(), shortList1, shortList2, shortList3);
	}
	
	// ParallelTransform4 : transform a destination list by applying a Procedure4 to three source lists.
	// A Procedure4 takes three input values, calculates something, and sets an output value to the result.
	// ParallelTransform4 uses as many threads as the cpu can spare to do this transformation quickly.
	
	void example7() {
		
		// source1 list of all zeroes
		
		IndexedDataSource<SignedInt16Member> shortList1 = ArrayStorage.allocate(10000000, G.INT16.construct());

		// source2 list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList2 = shortList1.duplicate();
		
		// source3 list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList3 = shortList1.duplicate();
		
		// dest list of same size and contents
		
		IndexedDataSource<SignedInt16Member> shortList4 = shortList1.duplicate();
		
		// fill the source lists with random positive and negative values
		
		ParallelFill.compute(G.INT16, G.INT16.random(), shortList1);
		ParallelFill.compute(G.INT16, G.INT16.random(), shortList2);
		ParallelFill.compute(G.INT16, G.INT16.random(), shortList3);

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
		
		ParallelTransform4.compute(G.INT16, proc, shortList1, shortList2, shortList3, shortList4);
	}
	
	/* Zorbage also provides some specialized algorithms that use multiple threads for speed. */
	
	// ParallelMatrixMultiply: multiple matrices quickly by utilizing multiple threads
	
	void example8() {
		
		Float64MatrixMember a = new Float64MatrixMember(StorageConstruction.MEM_ARRAY, 1000, 1000);
		Float64MatrixMember b = new Float64MatrixMember(StorageConstruction.MEM_ARRAY, 1000, 1000);
		Float64MatrixMember c = new Float64MatrixMember();
		
		ParallelFill.compute(G.DBL, G.DBL.random(), a.rawData());
		ParallelFill.compute(G.DBL, G.DBL.random(), b.rawData());
		
		ParallelMatrixMultiply.compute(G.DBL, a, b, c);
	}
	
	// ParallelConvolveND : convolve a n-d dataset using multiple threads

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
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(new long[] {1000,1000,1000}, G.DBL.construct());
		
		// fill it
		
		ParallelFill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// pad it so that out of bounds accesses will still produce a value. Convolution will always poke out of bounds.
		
		DimensionedDataSource<Float64Member> padded = 
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, proc);
		
		// get ready to store result
		
		DimensionedDataSource<Float64Member> result = DimensionedStorage.allocate(new long[] {1000,1000,1000}, G.DBL.construct());
		
		// create the convolution kernel to use to combine neighboring values with
		
		DimensionedDataSource<Float64Member> kernel = DimensionedStorage.allocate(new long[] {3,3,3}, G.DBL.construct());
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
		
		ParallelConvolveND.compute(G.DBL, kernel, padded, result);
	}
	
	// ParallelCorrelateND : correlate a n-d dataset using multiple threads
	
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
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(new long[] {1000,1000,1000}, G.DBL.construct());
		
		// fill it
		
		ParallelFill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// pad it so that out of bounds accesses will still produce a value. Correlation will always poke out of bounds.
		
		DimensionedDataSource<Float64Member> padded = 
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, proc);
		
		// get ready to store result
		
		DimensionedDataSource<Float64Member> result = DimensionedStorage.allocate(new long[] {1000,1000,1000}, G.DBL.construct());
		
		// create the correlation kernel to use to combine neighboring values with
		
		DimensionedDataSource<Float64Member> kernel = DimensionedStorage.allocate(new long[] {3,3,3}, G.DBL.construct());
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
		
		ParallelCorrelateND.compute(G.DBL, kernel, padded, result);
	}

	// ResampleNearestNeighbor : resample a dataset to a different resolution using nearest neighbor interpolation
	
	DimensionedDataSource<Float64Member> example11() {
		
		// create the dataset to read
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(new long[] {8192,8192}, G.DBL.construct());

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
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(new long[] {8192,8192}, G.DBL.construct());

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
		
		DimensionedDataSource<Float64Member> ds = DimensionedStorage.allocate(new long[] {8192,8192}, G.DBL.construct());

		// pad the dataset so out of bounds accesses don't fail
		
		DimensionedDataSource<Float64Member> padded =
				new ProcedurePaddedDimensionedDataSource<Float64Algebra, Float64Member>(G.DBL, ds, new MirrorNdOOB<>(ds));
		
		// fill it with random data
		
		Fill.compute(G.DBL, G.DBL.random(), ds.rawData());
		
		// resample the 2d image to a much smaller size
		
		return ResampleAveragedCubics.compute(G.DBL, new long[] {500, 500}, padded);
	}
	
}
