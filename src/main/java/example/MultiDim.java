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
import nom.bdezonia.zorbage.algorithm.DimensionalPermutation;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.axis.IdentityAxisEquation;
import nom.bdezonia.zorbage.axis.StringDefinedAxisEquation;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.data.ProcedurePaddedDimensionedDataSource;
import nom.bdezonia.zorbage.oob.nd.ConstantNdOOB;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Algebra;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class MultiDim {
	
	/*
	 * Zorbage has built in support for multidimensional data.
	 */
	void example1() {
	
		// Create a multidim dataset
		
		long[] dims = new long[] {1024, 1024, 30000};  // 30000 z planes of 1024 x 1024 size
		
		UnsignedInt16Member type = G.UINT16.construct();
		
		DimensionedDataSource<UnsignedInt16Member> data = DimensionedStorage.allocate(type, dims);
		
		data.numDimensions(); // 3
		data.dimension(0); // 1024
		data.dimension(1); // 1024
		data.dimension(2); // 30000
		
		// At the core a multidim data source just wraps a big array and has some associated
		// metadata. One thing you can do with the data is to reach down to the lower level
		// array when speedy access is paramount.
		
		UnsignedInt16Member value = new UnsignedInt16Member(1234);
		
		Fill.compute(G.UINT16, value, data.rawData());
		
		// You can also access data one pixel at a time in a random access approach
		
		IntegerIndex index = new IntegerIndex(data.numDimensions());
		
		value.setV(200);
		
		index.set(0, 44);     // x = 44
		index.set(1, 104);    // y = 104
		index.set(2, 15000);  // z = 15000
		
		// set the data but throw an exception if index is outside bounds of data set
		data.safeSet(index, value);
		
		// set the data but trust user that index is inside bounds of data set
		data.set(index, value);
		
		// Here is one way to work with a subset of the data.
		// Set the 5th z plane to all 7's
		
		value.setV(7);
		
		SamplingIterator<IntegerIndex> coordIterator =
				GridIterator.compute(new long[] {0,0,4}, new long[] {1023,1023,4});
		
		while (coordIterator.hasNext()) {
			coordIterator.next(index);
			data.set(index, value);
		}
		
		// The data set has a set of associated axes. the default is that the axis is
		// mapped one to one with the coordinates.
		
		HighPrecisionMember axisValue = G.HP.construct();
		
		Procedure2<Long, HighPrecisionMember> xAxisEqn = new IdentityAxisEquation();
		
		xAxisEqn.call(4L, axisValue);  // axisValue will equal 4
		
		// You can calibrate the axes to arbitrary precision with any equation you desire.
		
		xAxisEqn = new StringDefinedAxisEquation("$0 * 5 - 3");  // out = in * 5 - 3
		
		xAxisEqn.call(4L, axisValue);  // axisValue will equal 4 * 5 - 3 (= 17)
		
		// One more thing you can do with these data sources is store and retrieve
		// metadata.
		
		data.setName("My multidim datasource");
		data.setValueType("temperature");
		data.setValueUnit("degrees C");
		data.setAxisType(0, "distance");
		data.setAxisUnit(0, "meter");
		data.metadata().put("data creator", "Barry DeZonia");
		
		// Finally let's visit the get()/set() vs. getSafe()/setSafe() discussion from
		// before. Multi dim data can be padded with a strategy for producing values
		// when you poke out of the bounds of a dataset. There is much more on this
		// topic in the OutOfBoundsData topic in the same directory as this file.
		
		// For now let's just make the dataset return 83 when accessing out of bounds
		
		value.setV(83);
		
		ConstantNdOOB<UnsignedInt16Algebra,UnsignedInt16Member> oobProc =
				new ConstantNdOOB<>(G.UINT16, data, value);
		
		DimensionedDataSource<UnsignedInt16Member> paddedData =
				new ProcedurePaddedDimensionedDataSource<>(G.UINT16, data, oobProc);
		
		value.setV(0);
		
		index.set(0, -1);
		index.set(1, -1);
		index.set(2, -1);
		
		paddedData.get(index, value);  // value == 83 here
	}
	
	// Zorbage has a helper for manipulating multidim datasets. Sometimes when you
	// import from elsewhere (using let's say zorbage-netcdf or zorbage-gdal) you
	// might get data encoded in an order you do not wish to work with. You can
	// permute the data so that it is organized how you wish. No data are changed
	// by this transformation. Maybe this is best shown with an example.
	
	@SuppressWarnings("null")
	void example2() {
	
		DimensionedDataSource<Float64Member> origSrc = null;
		
		// We're setting it to null for now but we'll assume it was loaded with the
		// following metadata.
		
		// organized as (channel, x, y, z, time)
		
		origSrc.numDimensions(); // == 5
		
		origSrc.dimension(0);    // == 4
		origSrc.getAxisType(0);  // == "channel"
		
		origSrc.dimension(1);    // == 512
		origSrc.getAxisType(1);  // == "x"
		
		origSrc.dimension(2);    // == 768
		origSrc.getAxisType(2);  // == "y"
		
		origSrc.dimension(3);    // = 300
		origSrc.getAxisType(3);  // == "z"
		
		origSrc.dimension(4);    // = 1000
		origSrc.getAxisType(4);  // == "time"
		
		int[] permutation = new int[] {3, 0, 1, 2, 4};
		
			// orig index 0 -> 3
			// orig index 1 -> 0
			// orig index 2 -> 1
			// orig index 3 -> 2
			// orig index 4 -> 4
		
		DimensionedDataSource<Float64Member> result =
				DimensionalPermutation.compute(G.DBL, permutation, origSrc);

		// organized as (x, y, z, channel, time)
		
		result.numDimensions(); // == 5
		
		result.dimension(0);    // == 512
		result.getAxisType(0);  // == "x"
		
		result.dimension(1);    // == 768
		result.getAxisType(1);  // == "y"
		
		result.dimension(2);    // = 300
		result.getAxisType(2);  // == "z"
		
		result.dimension(3);    // == 4
		result.getAxisType(3);  // == "channel"
		
		result.dimension(4);    // == 1000
		result.getAxisType(4);  // == "time"
	}
}
