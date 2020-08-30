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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.DeepCopy;
import nom.bdezonia.zorbage.algorithm.FFT;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.GetV;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.Median;
import nom.bdezonia.zorbage.algorithm.SetV;
import nom.bdezonia.zorbage.algorithm.Sort;
import nom.bdezonia.zorbage.algorithm.StdDev;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.datasource.ArrayDataSource;
import nom.bdezonia.zorbage.datasource.BigListDataSource;
import nom.bdezonia.zorbage.datasource.ConcatenatedDataSource;
import nom.bdezonia.zorbage.datasource.ConditionalDataSource;
import nom.bdezonia.zorbage.datasource.DataSourceListener;
import nom.bdezonia.zorbage.datasource.FixedSizeDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ListDataSource;
import nom.bdezonia.zorbage.datasource.MaskedDataSource;
import nom.bdezonia.zorbage.datasource.ProcedureDataSource;
import nom.bdezonia.zorbage.datasource.ProcedurePaddedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import nom.bdezonia.zorbage.datasource.ReversedDataSource;
import nom.bdezonia.zorbage.datasource.SequencedDataSource;
import nom.bdezonia.zorbage.datasource.TransformedDataSource;
import nom.bdezonia.zorbage.datasource.TrimmedDataSource;
import nom.bdezonia.zorbage.datasource.WriteNotifyingDataSource;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.float16.real.Float16Algebra;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.int10.UnsignedInt10Algebra;
import nom.bdezonia.zorbage.type.int10.UnsignedInt10Member;
import nom.bdezonia.zorbage.type.int12.UnsignedInt12Member;
import nom.bdezonia.zorbage.type.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.int4.UnsignedInt4Member;

/**
 * @author Barry DeZonia
 */
class DataSources {
	
	// IndexedDataSource;
	//
	//   IndexedDataSource is the interface around which much of Zorbage is organized.
	//   Many algorithms take IndexedDataSources as an input and calculate results
	//   from their data. An IndexedDataSource can be thought of as an array indexed
	//   by longs. In reality an IndexedDataSource might be an actual array, or a
	//   JDBC database table or a virtual file backed array or many other possibilities.
	//   Zorbage's algorithms do not have to worry about how the data is stored. Any
	//   structure can be walked using the interface. In fact in the future Zorbage
	//   may provide clustered data access through an IndexedDataSource interface.
	
	void example1() {
		
		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(100, G.DBL.construct());

		Float64Member value = G.DBL.construct();
		
		GetV.fifth(data, value);
		
		value.setV(10101);
		
		SetV.eighteenth(data, value);
	}
	
	// ArrayDataSource
	//
	//   The regular Storage allocator requires that the type of data that will be stored
	//   implements certain primitive coder interfaces. Sometimes this is too restrictive.
	//   If someone defines a type that can't easily be stored to disk (like for instance
	//   when its primitive data does not have a fixed size) you can use this data source
	//   to wrap plain object array data so that it can be used with all of Zorbage's
	//   algorithms. Note that this code is type safe. It also is completely resident in
	//   ram and cannot be indexed beyond the range of a 32 bit integer so one is limited
	//   as to how much data can be allocated and processed. It can be useful for
	//   interfacing with other libraries that return arrays of objects.

	void example2() {
		
		IndexedDataSource<HighPrecisionMember> list = ArrayDataSource.construct(G.HP, 1234);
		
		// fill the list with values
		
		Fill.compute(G.HP, G.HP.unity(), list);
		
		// then calculate a result

		HighPrecisionMember result = G.HP.construct();
		
		Sum.compute(G.HP, list, result);  // result should equal 1234
	}
	
	// BigListDataSource
	//
	//   In java arrays are limited to the number of elements that can be represented by a
	//   32-bit integer. That is the max size and also the most amount of ram a primitive
	//   data source can use. However in Zorbage the BigListDataSource allows very large
	//   lists to be contained in ram and to be indexed with 64-bit integers. The JVM can
	//   be tweaked to allocate lots of ram and the BigListDataSource class can take
	//   advantage of it. Internally it stores a list of lists.

	void example3() {
		
		IndexedDataSource<SignedInt16Member> list =
				new BigListDataSource<SignedInt16Algebra, SignedInt16Member>(G.INT16, 100000);

		// elsewhere fill the list with values
		
		// then calculate a result
		
		SignedInt16Member result = G.INT16.construct();
		
		Median.compute(G.INT16, list, result);
	}
	
	// ConcatenatedDataSource
	//
	//   A ConcatenatedDataSource glues two other lists together so they can be treated
	//   as one. The concatenated lists can be passed to other algorithms.
	
	void example4() {
		
		IndexedDataSource<UnsignedInt1Member> list1 = 
				nom.bdezonia.zorbage.storage.Storage.allocate(100, G.UINT1.construct());
		
		IndexedDataSource<UnsignedInt1Member> list2 = 
				nom.bdezonia.zorbage.storage.Storage.allocate(1000, G.UINT1.construct());
		
		IndexedDataSource<UnsignedInt1Member> joinedList =
				new ConcatenatedDataSource<>(list1, list2);
		
		Fill.compute(G.UINT1, G.UINT1.random(), joinedList);
	}
	
	// ConditionalDataSource
	//
	//   A ConditionalDataSource creates a list that only contains values from an
	//   underlying list that satisfy a condition. One of the design constraints on
	//   Zorbage is that for the most part all IndexedDataSources have a fixed size.
	//   Because of this ConditionalDataSources must also follow this contract. To
	//   do so you make sure that all list data writes only succeed with values that
	//   satisfy the original condition upon which the data source was built.
	
	@SuppressWarnings("unused")
	void example5() {
		
		// allocate a list
		
		IndexedDataSource<UnsignedInt10Member> list =
				nom.bdezonia.zorbage.storage.Storage.allocate(100000, G.UINT10.construct());
		
		// fill it with something
		
		Fill.compute(G.UINT10, G.UINT10.random(), list);
		
		// then make the condition "value is less than 44"
		
		Function1<Boolean,UnsignedInt10Member> lessThan44 = new Function1<Boolean,UnsignedInt10Member>()
		{
			@Override
			public Boolean call(UnsignedInt10Member value) {

				return value.v() < 44;
			}
		};
		
		// get a view of all values that satisfy this
		
		IndexedDataSource<UnsignedInt10Member> conditionalList =
				new ConditionalDataSource<UnsignedInt10Algebra, UnsignedInt10Member>(
						G.UINT10, list, lessThan44);
		
		// count how many values satisfy this constraint
		
		long count = conditionalList.size();

		// now get soem values that satisy the condition
		
		UnsignedInt10Member value = G.UINT10.construct();
		
		GetV.first(conditionalList, value);
		
		// try to set the values to something else
		
		value.setV(22);
		
		// this succeeds since 22 < 44 and satisfies the condition
		
		SetV.first(conditionalList, value);
		
		value.setV(99);
		
		// this fails since 99 >= 44 and does not satisfy the condition
		//   an exception will be thrown
		
		SetV.first(conditionalList, value);
	}
	
	// FixedSizeDataSource
	//
	//   Sometimes you have a requirement that an algorithm is expecting a certain size
	//   list and the one you have does not match. You can cap the size of a list using
	//   a FixedSizeDataSource. The FFT algorithm commonly needs to do this.
	
	void example6() {
	
		// allocate some data
		
		IndexedDataSource<ComplexFloat32Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(1234, G.CFLT.construct());
		
		// elsewhere fill it with something
		
		// then define an out of bounds padding that is all zero
		
		Procedure2<Long,ComplexFloat32Member> proc = new Procedure2<Long, ComplexFloat32Member>()
		{
			@Override
			public void call(Long a, ComplexFloat32Member b) {
				b.setR(0);
				b.setI(0);
			}
		};
		
		// tie the padding and the zero proc together. reads beyond data's length will return 0
		
		IndexedDataSource<ComplexFloat32Member> padded =
				new ProcedurePaddedDataSource<>(G.CFLT, data, proc);
		
		// compute an ideal power of two size that the FFT algorithm will want to use
		
		long idealSize = FFT.enclosingPowerOf2(data.size());
		
		// make the FixedsizeDataSource here that satisfies the FFT algorithm's requirements
		
		IndexedDataSource<ComplexFloat32Member> fixedSize =
				new FixedSizeDataSource<>(idealSize, padded);
		
		// allocate the same amount of space for the results
		
		IndexedDataSource<ComplexFloat32Member> outList =
				nom.bdezonia.zorbage.storage.Storage.allocate(idealSize, G.CFLT.construct());

		// and compute the FFT
		
		FFT.compute(G.CFLT, G.FLT, fixedSize, outList);
	}
	
	// ListDataSource
	//
	//   The regular Storage allocator requires that the type of data that will be stored
	//   implements certain primitive coder interfaces. Sometimes this is too restrictive.
	//   If someone defines a type that can't easily be stored to disk (like for instance
	//   when its primitive data does not have a fixed size) you can use this data source
	//   to wrap plain object list data so that it can be used with all of Zorbage's
	//   algorithms. Note that this code is type safe. It also is completely resident in
	//   ram and cannot be indexed beyond the range of a 32 bit integer so one is limited
	//   as to how much data can be allocated and processed. It can be useful for
	//   interfacing with other libraries that return lists of objects.

	void example7() {
		
		// allocate the data
		
		IndexedDataSource<HighPrecisionMember> data = ListDataSource.construct(G.HP, 1234);
		
		// fill the list with values
		
		HighPrecisionMember value = G.HP.construct();
		
		for (long i = 0; i < data.size(); i++) {
			
			value.setV(BigDecimal.valueOf(i));

			data.set(i, value);
		}

		// then calculate a result

		HighPrecisionMember result = G.HP.construct();
		
		StdDev.compute(G.HP, data, result);
	}
	
	// MaskedDataSource
	//
	//   Sometimes you want to compute on values from a data source that have a logical
	//   classification. If you build a boolean mask that is true where you are interested
	//   in the value you can build a MaskedDataSource and do you computations upon only
	//   that data. An example mask might be booleans describing which values in the
	//   original list are contained within a threshold value.

	void example8() {

		// setup some data
		
		IndexedDataSource<Float64Member> list = ArrayStorage.allocate(9, G.DBL.construct());
		
		// fill it with random values
		
		Fill.compute(G.DBL, G.DBL.random(), list);
		
		// build a mask
		
		IndexedDataSource<BooleanMember> mask = ArrayStorage.allocateBooleans(
				new boolean[] {true, false, false, true, false, false, true, true, true});
		
		// make the filter
		
		IndexedDataSource<Float64Member> maskedData = new MaskedDataSource<>(list, mask);
		
		// do some computations
		
		maskedData.size(); // equals 5
		
		// 3rd value in the masked data = the 7th value of the original list
		
		Float64Member value = G.DBL.construct();
		
		maskedData.get(3, value);
		
		// compute a value on data only where the mask is true in the original dataset
		
		Mean.compute(G.DBL, maskedData, value);
	}
	
	// ProcedureDataSource
	//
	//   A ProcedureDataSource can be used to treat a mathematical or tabulated function
	//   as a source of data. One could define a Procedure that returns values from a
	//   table or from some computation and then wrap it and pass it to all the algorithms
	//   Zorbage provides.

	void example9() {
		
		// an uninteresting procedure we will calculate values from
		
		Procedure2<Long,UnsignedInt12Member> proc = new Procedure2<Long, UnsignedInt12Member>()
		{
			@Override
			public void call(Long a, UnsignedInt12Member b) {
				b.setV((int) (long) a);
			}
		};
		
		// the ProcedureDataSource we will query
		
		IndexedDataSource<UnsignedInt12Member> list = new ProcedureDataSource<>(proc);
		
		// limit how much data to consider since procedures have a nearly unlimited x axis range
		
		IndexedDataSource<UnsignedInt12Member> fixedList = new FixedSizeDataSource<>(1000, list);
		
		// then calculate some values
		
		UnsignedInt12Member value = G.UINT12.construct();
		
		Sum.compute(G.UINT12, fixedList, value);
	}
	
	// ReadOnlyDataSource
	//
	//   Sometimes you want to make sure that users of your list cannot modify its contents.
	//   Wrapping a data source in this read only wrapper can be just what you need. 
	
	void example10() {
		
		// allocate a regular list
		
		IndexedDataSource<UnsignedInt4Member> list =
				nom.bdezonia.zorbage.storage.Storage.allocate(1000, G.UINT4.construct());
		
		// fill it with data
		
		Fill.compute(G.UINT4, G.UINT4.random(), list);
		
		// protect it from writes
		
		IndexedDataSource<UnsignedInt4Member> readonlyList = new ReadOnlyDataSource<>(list);
		
		// now play with list
		
		UnsignedInt4Member value = G.UINT4.construct();
		
		// success
		
		readonlyList.get(44, value);

		// prepare to write
		
		value.setV(100);
		
		// failure: throws exception. writing not allowed
		
		readonlyList.set(22, value);
	}
	
	// ReadOnlyHighPrecisionDataSource
	//
	//   Use this data source to pull data out of an underlying data source as high
	//   precision numbers so you can do highly accurate calculations. This approach
	//   only uses minimal amounts of additional ram. The calculations are a bit
	//   slower than raw data access. Each single number of the underlying data source
	//   is translated to a high precision representation upon reads to the filter.
	//   Only one number is translated at a time as they are requested.
	
	void example11() {

		// allocate a regular list
		
		IndexedDataSource<UnsignedInt4Member> list =
				nom.bdezonia.zorbage.storage.Storage.allocate(1000, G.UINT4.construct());
		
		// fill it with data
		
		Fill.compute(G.UINT4, G.UINT4.random(), list);
		
		// wrap it
		
		IndexedDataSource<HighPrecisionMember> highPrecData =
				new ReadOnlyHighPrecisionDataSource<>(G.UINT4, list);
		
		// do a high precision calculation
		
		HighPrecisionMember result = G.HP.construct();
		
		Mean.compute(G.HP, highPrecData, result);
		
		// one thing to notice is that a sum/mean of a 1000 UINT4's would certainly
		// overflow the range of an output UINT4. This is a good example of where the
		// high precision approach makes sense even if perfect accuracy is not required.
		// the high prec output cannot overflow.
	}
	
	// ReversedDataSource
	//
	//   Sometimes you want to be able to pull values out of a list in reverse order.
	//   If you want you can write a for loop from max to min. But Zorbage does
	//   provide a ReversedDataSource filter in case you want to pass data in
	//   reverse order to algorithms or if you want to write straightforward for
	//   loops and just concentrate on the writing of your algorithm.

	void example12() {
		
		// an example of passing data to algorithms in reverse order
		
		// make some data
		
		IndexedDataSource<SignedInt32Member> nums = ArrayStorage.allocateInts(
				new int[] {-1, 345, 1, -3044, 0, 0, 1066, -12});
		
		// sort it
		
		Sort.compute(G.INT32, nums);
		
		// this is how the results look
		//   nums = [-3044, -12, -1, 0, 0, 1, 345, 1066]
		
		// now wrap a reverse filter around the data
		
		IndexedDataSource<SignedInt32Member> revNums = new ReversedDataSource<>(nums);

		// sort using the filter
		
		Sort.compute(G.INT32, revNums);
		
		// this is how the results look
		//   revNums = [-3044, -12, -1, 0, 0, 1, 345, 1066]
		//   nums = [1066, 345, 1, 0, 0, -1, -12, -3044]
		
		// now here is a some code showing some another way to use the filter
		
		SignedInt32Member value = G.INT32.construct();
		
		GetV.second(revNums, value);
		
		// gets the second value in the reverse list. so it gets the second to last
		// value in the original list.
		//   value = -12
	}
	
	// SequencedDataSource
	//
	//   Sometimes you want to access a datasource in a strided fashion. Zorbage provides
	//   multidimensional iteration code for many use cases. But sometimes you know you
	//   just want to fill a column or a plane or a volume etc. You can use a strided
	//   accessor (a SequencedDataSource) if you want to accomplish things quickly and
	//   simply.
	
	void example13() {
		
		// create a list of zeroes
		
		IndexedDataSource<Float64Member> list =
				nom.bdezonia.zorbage.storage.Storage.allocate(1000, G.DBL.construct());

		// now setup a view that will increment by 3 starting at the list[4] and steps
		// up to 100 times.
		
		IndexedDataSource<Float64Member> seqData =
				new SequencedDataSource<Float64Member>(list, 4, 3, 100);

		seqData.size();  // size == 100
		
		// now set a bunch of values
		
		Float64Member value = G.DBL.construct();
		
		for (long i = 0; i < seqData.size(); i++) {
			value.setV(i);
			seqData.set(i, value);
		}
		
		// data = [0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 3, 0, 0, 4, 0, 0, 5, 0, 0, ...]
	}
	
	// TransformedDataSource
	//
	//   Sometimes you want to calculate a value on a data source that doesn't
	//   have an appropriate type. You can wrap the data source with a
	//   TransformedDatSource and do back and forth conversions as necessary.
	//   The transform needs to use a couple procedures for translating between
	//   types.
	
	void example14() {
		
		// original data: a bunch of ints
		
		IndexedDataSource<SignedInt32Member> list =
				nom.bdezonia.zorbage.storage.Storage.allocate(100, G.INT32.construct());
		
		// a procedure to transforms ints to doubles
		
		Procedure2<SignedInt32Member,Float64Member> intToDblProc =
				new Procedure2<SignedInt32Member, Float64Member>()
		{
			@Override
			public void call(SignedInt32Member a, Float64Member b) {
				b.setV(a.v());
			}
		};
				
		// a procedure to transforms doubles to ints
		
		Procedure2<Float64Member,SignedInt32Member> dblToIntProc =
				new Procedure2<Float64Member,SignedInt32Member>()
		{
			@Override
			public void call(Float64Member a, SignedInt32Member b) {
				b.setV((int) a.v());
			}
		};
		
		// the definition of the transformed data source
		
		IndexedDataSource<Float64Member> xformer = 
				new TransformedDataSource<>(G.INT32, list, intToDblProc, dblToIntProc);

		// now calculate some results. Notice that Mean can't normally be used on
		//   integer data
		
		// not possible: Integers do not have the correct type of division operator
		
		// SignedInt32Member resI = G.INT32.construct();
		
		// Mean.compute(G.INT32, list, resI);
		
		// with the transformer we can calc mean

		Float64Member result = G.DBL.construct();
		
		Mean.compute(G.DBL, xformer, result);
	}
	
	// TrimmedDataSource
	//
	//   Make a list that is a window on another list. Sometimes you might want some
	//   calculation of the next N numbers of a list (for example sum the next 100
	//   numbers of a list). You can define a trimmed view of a list and pass the
	//   trimmed list to algorithms that do further calculations.
	
	@SuppressWarnings("unused")
	void example15() {

		// make a list of 10,000 numbers
		
		IndexedDataSource<Float32Member> original =
				nom.bdezonia.zorbage.storage.Storage.allocate(10000, G.FLT.construct());

		// make a list that is a subset of the previous list (numbers from locations 1,000 - 2,999)
		
		IndexedDataSource<Float32Member> trimmed = new TrimmedDataSource<>(original, 1000, 2000);
		
		// the trimmed list has length 2,000 and is indexed from 0 to 1,999 returning data from
		//   locations 1,000 - 2,999 in the original list.
		
	}

	// WriteNotifyingDataSource
	//
	//   Make a list that notifies listeners every time it stores a value
	
	void example16() {
		
		// allocate 10,000 float16's
		
		IndexedDataSource<Float16Member> origData =
				nom.bdezonia.zorbage.storage.Storage.allocate(10000, G.HLF.construct());
		
		// wrap a notifier around it
		
		WriteNotifyingDataSource<Float16Algebra, Float16Member> observedData =
				new WriteNotifyingDataSource<>(G.HLF, origData);
	
		// observe the process
		
		observedData.subscribe(new DataSourceListener<Float16Algebra, Float16Member>()
		{
			private long lastPercent = -1;

			@Override
			public void notify(Float16Algebra alegbra, IndexedDataSource<Float16Member> source, long index) {
				long cutoff = source.size() / 100;
				long percent = index / cutoff;
				if (index >= source.size()-1) {
					percent = 100;
				}
				if (percent != lastPercent) {
					System.out.println("Operation is " + lastPercent + "done.");
					lastPercent = percent;
				}
			}
		});

		// do the fill: we'll get a bunch of status updates
		
		Float16Member value = new Float16Member(1234);
		
		Fill.compute(G.HLF, value, observedData);
	}

	// DeepCopy
	//
	//   Many of the data sources shown above make what is called a shallow copy of the
	//   data source around which they are wrapped. A TrimmedDataSource does not make a
	//   copy of the list it wraps but rather passes data input and output calls to it.
	//   Sometimes you might want to make a full copy of the data underlying any and all
	//   data sources you've chained together. For instance to calculate a Median of a
	//   data set you need to make a DeepCopy of the original data so you can sort it
	//   without affecting any of the underlying data sets. A DeepCopy returns a set
	//   of data with all data values copied to newly allocated memory (or disk storage
	//   as appropriate).

	@SuppressWarnings("unused")
	void example17() {
		
		// make a list of 10,000 numbers
		
		IndexedDataSource<Float32Member> original =
				nom.bdezonia.zorbage.storage.Storage.allocate(10000, G.FLT.construct());

		// make a list that is a subset of the previous list (numbers from locations 1,000 - 2,999)
		
		IndexedDataSource<Float32Member> trimmed = new TrimmedDataSource<>(original, 1000, 2000);
		
		// then create a new memory copy of those 2000 numbers
		
		IndexedDataSource<Float32Member> theCopy = DeepCopy.compute(G.FLT, trimmed);
	}
}
