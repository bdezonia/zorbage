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
import nom.bdezonia.zorbage.algorithm.CartesianProduct;
import nom.bdezonia.zorbage.algorithm.GetI0Values;
import nom.bdezonia.zorbage.algorithm.GetIValues;
import nom.bdezonia.zorbage.algorithm.GetJ0Values;
import nom.bdezonia.zorbage.algorithm.GetJValues;
import nom.bdezonia.zorbage.algorithm.GetK0Values;
import nom.bdezonia.zorbage.algorithm.GetKValues;
import nom.bdezonia.zorbage.algorithm.GetLValues;
import nom.bdezonia.zorbage.algorithm.GetRValues;
import nom.bdezonia.zorbage.algorithm.GetV;
import nom.bdezonia.zorbage.algorithm.SetI0Values;
import nom.bdezonia.zorbage.algorithm.SetIValues;
import nom.bdezonia.zorbage.algorithm.SetJ0Values;
import nom.bdezonia.zorbage.algorithm.SetJValues;
import nom.bdezonia.zorbage.algorithm.SetK0Values;
import nom.bdezonia.zorbage.algorithm.SetKValues;
import nom.bdezonia.zorbage.algorithm.SetLValues;
import nom.bdezonia.zorbage.algorithm.SetRValues;
import nom.bdezonia.zorbage.algorithm.SetV;
import nom.bdezonia.zorbage.algorithm.Splat6;
import nom.bdezonia.zorbage.algorithm.Unzip;
import nom.bdezonia.zorbage.algorithm.Zip;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ListDataSource;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.tuple.Tuple3;
import nom.bdezonia.zorbage.tuple.Tuple4;
import nom.bdezonia.zorbage.tuple.Tuple6;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16Member;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int12.UnsignedInt12Algebra;
import nom.bdezonia.zorbage.type.int12.UnsignedInt12Member;
import nom.bdezonia.zorbage.type.int14.SignedInt14Algebra;
import nom.bdezonia.zorbage.type.int14.SignedInt14Member;
import nom.bdezonia.zorbage.type.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.tuple.Tuple3Algebra;

/**
 * @author Barry DeZonia
 */
class Conveniences {
	
	// Zorbage provides some helper algorithms to make data handling easier.
	
	// Tuples : a tuple is a grouping of multiple items into a record like structure. Imagine
	//   one could take an RGB value and hold it in memory as (r,g,b). This tuple (r,g,b) allows
	//   one to access the individual components of the tuple.

	void example1() {

		Float64Member val = G.DBL.construct();
		
		// Make a 3 element tuple and allocate objects for all of it's internal memory
		
		Tuple3<Float64Member,Float64Member,Float64Member> tuple =
				new Tuple3<>(G.DBL.construct(),G.DBL.construct(),G.DBL.construct());
		
		// Set a memory location the tuple stores internally
		
		tuple.setA(val);
		
		// Get a memory location the tuple stores internally
		
		val = tuple.b();
		
		// Assign a value's contents into the memory location of one of the tuple's objects
		
		G.DBL.assign().call(val, tuple.c());
	}
	
	// SetV and GetV make working with lists and tuples a little more easy

	// SetV/GetV with tuples
	
	void example3() {
		
		Float64Member val1 = G.DBL.construct("33.2");
		
		Float64Member val2 = G.DBL.construct();

		// Make a 4 element tuple and allocate objects for all of it's internal memory
		
		Tuple4<Float64Member,Float64Member,Float64Member,Float64Member> tuple =
				new Tuple4<>(G.DBL.construct(),G.DBL.construct(),G.DBL.construct(),G.DBL.construct());
		
		// set a value
		
		SetV.second(G.DBL, tuple, val1);
		
		G.DBL.isEqual().call(tuple.b(), val1);  // returns true

		// get a value
		
		GetV.fourth(G.DBL, tuple, val2);
		
		G.DBL.isEqual().call(tuple.d(), val2);  // returns true
	}

	// SetV/GetV with lists
	
	void example4() {
		
		Float64Member val = G.DBL.construct("33.2");
		
		// Make a 4 element tuple and allocate objects for all of it's internal memory
		
		IndexedDataSource<Float64Member> list = ArrayStorage.allocateDoubles(new double[] {1,2,3,4,5});

		// set a value
		
		SetV.second(list, val);
		
		val.setV(0);
		
		list.get(1, val);  // 1 == the second value in the list
		
		System.out.println(val.v()); // prints 33.2

		// get a value
		
		GetV.fourth(list, val);
		
		System.out.println(val.v()); // prints 4
		
		GetV.last(list, val);
		
		System.out.println(val.v()); // prints 5
		
	}
	
	// Splat: the splat algorithms (Splat1, Splat2, ..., Splat8) make it possible to easily
	//   move numbers into tuples and tuples into numbers. By numbers we mean reals, complexes,
	//   quaternions, and octonions.

	void example5() {
		
		Float32Member value = G.FLT.construct();
		
		OctonionFloat32Member oct = new OctonionFloat32Member("{1,2,3,4,5,6,7,8}");
		
		Tuple6<Float32Member,Float32Member,Float32Member,Float32Member,Float32Member,Float32Member> tuple =
				new Tuple6<>(G.FLT.construct(),G.FLT.construct(),G.FLT.construct(),G.FLT.construct(),G.FLT.construct(),G.FLT.construct());
		
		Splat6.toTuple(oct, tuple);  // tuple looks like (1,2,3,4,5,6)

		value.setV(103);
		
		SetV.second(G.FLT, tuple, value); // tuple looks like (1,103,3,4,5,6)
		
		value.setV(63);
		
		SetV.sixth(G.FLT, tuple, value); // tuple looks like (1,103,3,4,5,63)
		
		Splat6.toValue(tuple, oct); // oct looks like {1,103,3,4,5,63,7,8}
	}
	
	// Zip and Unzip are used to move between multiple lists and lists of tuples

	void example6() {
		
		int sz = 4;
		
		// assume it equals [1,2,3,4]
		IndexedDataSource<UnsignedInt12Member> aList = ArrayStorage.allocate(sz, G.UINT12.construct());
		
		// assume it equals [-5,-6,-7,-8]
		IndexedDataSource<SignedInt14Member> bList = ArrayStorage.allocate(sz, G.INT14.construct());
		
		// assume it equals [22.0, 23.0, 24.0, 25.0]
		IndexedDataSource<Float64Member> cList = ArrayStorage.allocate(sz, G.DBL.construct());
		
		Tuple3Algebra<UnsignedInt12Algebra,UnsignedInt12Member,SignedInt14Algebra,SignedInt14Member,Float64Algebra,Float64Member> algebra =
				new Tuple3Algebra<UnsignedInt12Algebra,UnsignedInt12Member,SignedInt14Algebra,SignedInt14Member,Float64Algebra,Float64Member>(G.UINT12, G.INT14, G.DBL);
		
		IndexedDataSource<Tuple3<UnsignedInt12Member,SignedInt14Member,Float64Member>> tupleList = 
				ListDataSource.construct(algebra, sz);

		Zip.three(G.UINT12, G.INT14, G.DBL, aList, bList, cList, tupleList);
		
		// tupleList = [ (1,-5,22.0), (2,-6,23.0), (3,-7,24.0), (4,-8,25.0) ]
		
		IndexedDataSource<UnsignedInt12Member> xList = ArrayStorage.allocate(sz, G.UINT12.construct());
		
		IndexedDataSource<SignedInt14Member> yList = ArrayStorage.allocate(sz, G.INT14.construct());
		
		IndexedDataSource<Float64Member> zList = ArrayStorage.allocate(sz, G.DBL.construct());
		
		Unzip.three(G.UINT12, G.INT14, G.DBL, tupleList, xList, yList, zList);

		// xlist = [1,2,3,4]
		// ylist = [-5,-6,-7,-8]
		// zlist = [22.0, 23.0, 24.0, 25.0]
	}

	// CartesianProduct: create an interleaved data set from two input data sets. The lists
	//   can be of different lengths. The distinct pairs are combined via a user defined
	//   transform function and the results are put in one resulting dataset in an interleaved
	//   order.
	//
	//   Data set 1 : a, b, c
	//   Data set 2 : x, y
	//   Result data set : xform(a,x), xform(a,y), xform(b,x), xform(b,y), xform(c,x), xform(c,y)

	void example7() {
		
		IndexedDataSource<SignedInt8Member> list1 = ArrayStorage.allocateBytes(new byte[] {1,3,5});

		IndexedDataSource<SignedInt8Member> list2 = ArrayStorage.allocateBytes(new byte[] {7,1,12});
		
		IndexedDataSource<SignedInt8Member> result = ArrayStorage.allocate(list1.size() * list2.size(), G.INT8.construct());

		Procedure3<SignedInt8Member,SignedInt8Member,SignedInt8Member> proc =
				new Procedure3<SignedInt8Member, SignedInt8Member, SignedInt8Member>()
		{
			@Override
			public void call(SignedInt8Member a, SignedInt8Member b, SignedInt8Member c) {

				// set c to a linear combo of a and b
				
				c.setV( 2*a.v() + b.v() );
			}
		};
		
		CartesianProduct.compute(G.INT8, G.INT8, G.INT8, proc, list1, list2, result);
		
		// result = [9, 3, 14, 13, 7, 18, 17, 11, 22] 
	}
	
	// Zorbage has a series of convenience functions for setting and getting the components of
	//   collections of complex, quaternion, and octonion data en masse.
	
	void example8() {
		
		DimensionedDataSource<OctonionFloat16Member> data =
				DimensionedStorage.allocate(new long[] {400, 500}, G.OHLF.construct());
		
		IndexedDataSource<Float16Member> components =
				nom.bdezonia.zorbage.storage.Storage.allocate(data.numElements(), G.HLF.construct());

		GetRValues.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetIValues.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetJValues.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetKValues.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetLValues.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetI0Values.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetJ0Values.compute(G.OHLF, G.HLF, data.rawData(), components);
		GetK0Values.compute(G.OHLF, G.HLF, data.rawData(), components);
		
		Float16Member value = new Float16Member(2525);
		
		components.set(0, value);
		
		SetRValues.compute(G.HLF, G.OHLF, components, data.rawData());
		SetIValues.compute(G.HLF, G.OHLF, components, data.rawData());
		SetJValues.compute(G.HLF, G.OHLF, components, data.rawData());
		SetKValues.compute(G.HLF, G.OHLF, components, data.rawData());
		SetLValues.compute(G.HLF, G.OHLF, components, data.rawData());
		SetI0Values.compute(G.HLF, G.OHLF, components, data.rawData());
		SetJ0Values.compute(G.HLF, G.OHLF, components, data.rawData());
		SetK0Values.compute(G.HLF, G.OHLF, components, data.rawData());
	}
	
	// DataConvert is another convenience algorithm. It allows one to make lists of one type
	//   of data into a list of another kind of data. Its use is detailed in the Conversions
	//   example in this directory.
	
}
