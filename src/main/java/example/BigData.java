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
package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.Find;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.NdData;
import nom.bdezonia.zorbage.datasource.BigListDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.storage.extmem.ExtMemStorage;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.string.StringMember;

import java.math.BigDecimal;

/**
 * @author Barry DeZonia
 */
class BigData {

	// Zorbage is written to store and accurately calculate upon very large sets of data.

	void example1() {
		
		// G contains all the algebras provided by default in Zorbage.
		// G.INT16 is the algebra for signed 16 bit integers

		// construct a temp variable

		SignedInt16Member value = G.INT16.construct();

		// Allocate a huge list: 10 billion short integers (20 billion bytes). Zorbage takes large
		//   requests like this and allocates the best data structure for the job. It will first
		//   try to allocate an in memory structure (one that is allowed to grow beyond 2 gig in
		//   memory use). This structure is fast and completely contained in RAM so if your Java
		//   heap size is large enough (which is configurable by a user of your application) the
		//   storage allocator will generate one. If you do not have enough RAM for a complete
		//   in memory data structure the storage allocator will return a file based list that
		//   contains a 4K byte memory buffer. All the values of the list are paged to a file on
		//   disk as needed. The created list is zero filled. This access is much slower than RAM
		//   access but can allocate mind boggling large lists of data.

		IndexedDataSource<SignedInt16Member> data = Storage.allocate(value, 10L * 1000 * 1000 * 1000);

		// Fill the list with random numbers
		//   G.INT16.random() is the function that returns a random signed 16 bit integer when called
		//   The Fill algorithm works in parallel and works best with in memory data structures.

		Fill.compute(G.INT16, G.INT16.random(), data);

		// Now count the number of fours we found.
		//   Notice the list is indexed by a 64-bit integer. Lists can contain up to 2^63-1 elements.

		long numFours = 0;
		for (long i = 0; i < data.size(); i++) {
			data.get(i, value);
			if (value.v() == 4)
				numFours++;
		}

		System.out.println("Number of integers with value of 4 was " + numFours);
	}

	@SuppressWarnings("unused")
	void example2() {
		
		// As described in example one there are multiple ways to generate big data structures.
		// One storage allocator that excels at allocating big, fast, ALL IN RAM lists is the
		// ExtMemStorage allocator. It can return very large lists with greater than 2 gig
		// elements that reside completely in RAM and is only limited by the (configurable)
		// Java heap size.
		
		IndexedDataSource<StringMember> lotsaStrings =
				ExtMemStorage.allocate(G.STRING.construct(),  10L * 1000 * 1000 * 1000);
		
		long index = Find.compute(G.STRING, new StringMember("Arby's"), lotsaStrings);
		
		// One of the interesting things about any IndexedDataSource is that it can be
		// wrapped to become a multidimensional data source quite easily.
		
		DimensionedDataSource<StringMember> multiDimStructure =
				new NdData<>(new long[] {10,1000,1000,1000}, lotsaStrings);
	}
	
	void example3() {

		// Another way to allocate a huge list (ALL IN RAM): 10 billion short integers (20 billion bytes).
		//   Use the BigList class from Zorbage. The created list is zero filled. BigList based code
		//   can allocate up to 2^62 elements; all in RAM. Actually achieving this is not realistic since
		//   it is limited to the amount of physical RAM allocated to the JVM. But you can configure the
		//   your JVM to use as much RAM as possible and the BigList will tap into it bypassing the
		//   2 gig limit on the number of elements in RAM for one list.

		// construct a temp variable

		SignedInt16Member value = G.INT16.construct();

		// allocate a BigList oriented structure

		IndexedDataSource<SignedInt16Member> data =
				new BigListDataSource<SignedInt16Algebra,SignedInt16Member>(G.INT16, 10L * 1000 * 1000 * 1000);

		// Fill the list with random numbers.

		Fill.compute(G.INT16, G.INT16.random(), data);

		// Now count the number of fours we found.
		//   Notice the list is indexed by a 64-bit integer. Lists can contain up to 2^63-1 elements.

		long numFours = 0;
		for (long i = 0; i < data.size(); i++) {
			data.get(i, value);
			if (value.v() == 4)
				numFours++;
		}

		System.out.println("Number of integers with value of 4 was " + numFours);
	}

	void example4() {

		// One issue with working with lots of data is that doing math with many numbers can result
		// in overflows or underflows or losses of precision. One trick Zorbage uses is to allow
		// one to accumulate values in high precision floating point numbers. The precision of which
		// can be set between 1 and 4000 decimal places. These numbers maintain said precision and
		// are unbounded so they do not over/underflow.

		// Allocate a 16 bit unsigned value to pass to the storage allocator

		UnsignedInt16Member value = G.UINT16.construct();

		// Allocate a huge list: 10 billion unsigned short integers (20 billion bytes).
		// The created list is zero filled.

		IndexedDataSource<UnsignedInt16Member> data = Storage.allocate(value, 10L * 1000 * 1000 * 1000);

		// Fill it with random data

		Fill.compute(G.UINT16, G.UINT16.random(), data);

		// Let's pull out values as unbounded floating point numbers that can't lose precision. We wrap the
		// original data in a filter that reads values in the original data type and converts the result to
		// a high precision value.

		IndexedDataSource<HighPrecisionMember> filter = new ReadOnlyHighPrecisionDataSource<>(G.UINT16, data);

		// Let's set the decimal place accuracy we want to maintain. Ideally this is called once by your
		// whole program at start up.

		HighPrecisionAlgebra.setPrecision(30); // 30 decimal places

		// Create placeholders for results

		HighPrecisionMember sum = G.HP.construct();
		
		HighPrecisionMember mean = G.HP.construct();

		// Compute the sum of all the data

		Sum.compute(G.HP, filter, sum);  // will not overflow

		// Compute the mean of all the data

		Mean.compute(G.HP, filter, mean);  // will not lose precision

		System.out.println("sum = " + sum);

		System.out.println("mean = " + mean);

		// A quicker way to calculate the mean. Avoid the Mean.compute() call altogether.

		HighPrecisionMember count = G.HP.construct();

		count.setV(BigDecimal.valueOf(data.size()));

		G.HP.divide().call(sum, count, mean);

		System.out.println("mean = " + mean);
	}
}
