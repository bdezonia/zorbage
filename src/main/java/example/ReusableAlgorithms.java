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
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.Product;
import nom.bdezonia.zorbage.algorithm.Sort;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSin;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.character.StringMember;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.int8.SignedInt8Member;

/**
 * @author Barry DeZonia
 */
class ReusableAlgorithms {

	//
	//  Zorbage is designed to make it easy to reuse code algorithms that are
	//  provided or that you or others write.
	//
	
	// Calculating transcendentals
	
	void example1() {
	
		// A taylor series can be used to estimate values such as sin, cos, etc.
		// One interesting thing to note is that one algorithm can be used to
		// calc values with numbers or matrices.

		// calc for numbers
		
		Float64Member numIn = new Float64Member(Math.PI / 3);
		Float64Member numSine = G.DBL.construct();
		TaylorEstimateSin.compute(35, G.DBL, G.DBL, numIn, numSine);
		
		// calc for square matrices
		
		Float64MatrixMember matIn = new Float64MatrixMember(2,2,new double[] {2,5,3,8});
		Float64MatrixMember matSine = G.DBL_MAT.construct();
		TaylorEstimateSin.compute(35, G.DBL_MAT, G.DBL, matIn, matSine);
	}

	// floating point numeric types
	
	void example2() {

		// Zorbage provides a number of floating point types. Many algorithms simply
		// work with them because of their compatible type definitions. Just
		// switching the types of algebras and elements you pass into the algorithm
		// changes what it computes.
		
		// allocate differently typed storage: reuse the Storage.allocate() algorithm
		
		IndexedDataSource<Float16Member> f16s =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.HLF.construct(), 100);
		
		IndexedDataSource<Float32Member> f32s =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.FLT.construct(), 100);
		
		IndexedDataSource<Float64Member> f64s =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(), 100);
		
		// fill each of them with random values. reuse the Fill algorithm over and over
		
		Fill.compute(G.HLF, G.HLF.random(), f16s);
		
		Fill.compute(G.FLT, G.FLT.random(), f32s);
		
		Fill.compute(G.DBL, G.DBL.random(), f64s);
		
		// now compute the means for all those arrays. reuse the Mean algorithm over and over
		
		Float16Member f16Result = G.HLF.construct();
		
		Float32Member f32Result = G.FLT.construct();
		
		Float64Member f64Result = G.DBL.construct();
		
		Mean.compute(G.HLF, f16s, f16Result);
		
		Mean.compute(G.FLT, f32s, f32Result);
		
		Mean.compute(G.DBL, f64s, f64Result);
	}
	
	// integer numeric types
	
	void example3() {
		
		// There are dozens of integral numeric types in Zorbage. Many algorithms
		// just work in a type agnostic way no matter what kind of integers you
		// want to work with.

		IndexedDataSource<SignedInt8Member> i8s = nom.bdezonia.zorbage.storage.Storage.allocate(G.INT8.construct(), new byte[] {1,2,3,4,5});
		IndexedDataSource<SignedInt16Member> i16s = nom.bdezonia.zorbage.storage.Storage.allocate(G.INT16.construct(), new short[] {1,2,3,4,5});
		IndexedDataSource<SignedInt32Member> i32s = nom.bdezonia.zorbage.storage.Storage.allocate(G.INT32.construct(), new int[] {1,2,3,4,5});
		IndexedDataSource<SignedInt64Member> i64s = nom.bdezonia.zorbage.storage.Storage.allocate(G.INT64.construct(), new long[] {1,2,3,4,5});
		
		SignedInt8Member i8Result = G.INT8.construct();
		SignedInt16Member i16Result = G.INT16.construct();
		SignedInt32Member i32Result = G.INT32.construct();
		SignedInt64Member i64Result = G.INT64.construct();
		
		Product.compute(G.INT8, i8s, i8Result);
		Product.compute(G.INT16, i16s, i16Result);
		Product.compute(G.INT32, i32s, i32Result);
		Product.compute(G.INT64, i64s, i64Result);
	}
	
	// non numeric types supported too
	
	void example4() {
		
		// Some algorithms can be reused with a wide variety of types
		
		// Sort a list of numbers
		
		IndexedDataSource<SignedInt16Member> nums = ArrayStorage.allocate(G.INT16.construct(), 100);
		  // <skip step here where data is filled with something>
		Sort.compute(G.INT16, nums);
		
		// Sort a list of strings
		
		IndexedDataSource<StringMember> strings = ArrayStorage.allocate(G.STRING.construct(), 100);
		  // <skip step here where data is filled with something>
		Sort.compute(G.STRING, strings);
		
	}
	
	// The various Transform methods are very reusable. They are very flexible in the types they
	// can transform with.
	
	void example5() {
		
		// here is one example: transforming a list of strings into a list of counts
		
		IndexedDataSource<StringMember> strings = ArrayStorage.allocate(G.STRING.construct(), 100);
		IndexedDataSource<UnsignedInt32Member> counts = ArrayStorage.allocate(G.UINT32.construct(),strings.size());
		Procedure2<StringMember,UnsignedInt32Member> countWhitespaceChars =
				new Procedure2<StringMember, UnsignedInt32Member>()
		{
			@Override
			public void call(StringMember string, UnsignedInt32Member count) {
				long cnt = 0;
				String str = string.v();
				for (int i = 0; i < str.length(); i++) {
					if (Character.isWhitespace(str.charAt(i)))
						cnt++;
				}
				count.setV(cnt);
			}
		};
		
		Transform2.compute(G.STRING, G.UINT32, countWhitespaceChars, strings, counts);
		
		// There are an infinite number of other algorithms you could design that are just transforms
		// of data by just varying the type parameters that are passed in.		
		// - a transform of a list of BigIntegers into a list of Strings.
		// - a transform of a list of real matrices into a list of their real number norms.
		// - etc.
	}
}
