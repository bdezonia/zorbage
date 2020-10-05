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
import nom.bdezonia.zorbage.algorithm.BoolToUInt1;
import nom.bdezonia.zorbage.algorithm.DataConvert;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.UInt1ToBool;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.float32.real.Float32VectorMember;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.int14.SignedInt14Member;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalConverter;

/**
 * @author Barry DeZonia
 */
class Conversions {
	
	// Zorbage has some built in conversion utilities for simply and accurately moving between types.
	
	// Zorbage has a DataConvert algorithm that works well.
	
	void example1() {
		
		// Let's say I have some 16-bit data that I will want to take the FFT of. Before I can run the
		// FFT algorithm I need to create a complex floating point data set. This can be done in a
		// pretty simple fashion.
		
		// here is my list of short data
		
		IndexedDataSource<SignedInt16Member> shortList = Storage.allocate(G.INT16.construct(), new short[] {1,2,3,4,5});
		
		// here is the target list of the same size
		
		IndexedDataSource<ComplexFloat64Member> complexList = Storage.allocate(shortList.size(), new ComplexFloat64Member());
		
		// convert the data
		
		DataConvert.compute(G.INT16, G.CDBL, shortList, complexList);
		
		// complexList now looks like this: {1.0, 0.0}, {2.0, 0.0}, {3.0, 0.0}, {4.0, 0.0}, {5.0, 0.0}
	}
	
	/*
	 * DataConvert relies on the lists being passed to it to support the PrimitiveConversion interface.
	 * This interface is a little verbose but it specifies how to convert any numeric type that is based
	 * upon primitive numeric values (byte, short, int, long, float, double, BigInteger, BigDecimal) and
	 * finds the least lossy conversion possible while avoiding object creations and destruction. The
	 * PrimitiveConversion algorithm is very fast and maximally accurate.
	 * 
	 * One interesting design aspect of PrimtiveConversion is that it operates from the assumption all
	 * types passing through it can be thought of as a tensor of octonions. A tensor can represent a
	 * number, a vector, a matrix, or a true tensor. Octonions can represent reals, complexes,
	 * quaternions, and octonions by specifying which components are zero.
	 * 
	 * When a number to number conversion is specified then translation is straightforward. However
	 * when the types mismatch in dimensionality (a number is 0 dimensional, a vector is 1 dimensional,
	 * a matrix is 2 dimensional, and a tensor is n dimensional) then an algorithm is used to move
	 * between such types:
	 * 
	 * - from a number to a number: convert values directly
	 * - from a number to a vector: destination vector[0] gets number conversion. vector set to zero everywhere else.
	 * - from a number to a matrix: destination matrix[0][0] gets number conversion. matrix set to zero everywhere else.
	 * - from a number to a tensor: destination tensor[0][0]... gets number conversion. tensor set to zero everywhere else
	 * 
	 * - from a vector to a number: number = conversion of vector[0]
	 * - from a vector to a vector: convert values directly. vector lengths don't change. out of bounds values treated as zero.
	 * - from a vector to a matrix: row 0 of destination matrix = converted values of source vector. oob values treated as zero.
	 * - from a vector to a tensor: row 0 of matrix 0 of destination tensor = converted values of source vector. oob values treated as zero.
	 * 
	 * - from a matrix to a number: number = conversion of matrix[0][0]
	 * - from a matrix to a vector: vector = conversion of row 0 of matrix. out of bounds values treated as zero.
	 * - from a matrix to a matrix: convert values directly. matrix sizes don't change. out of bounds values treated as zero.
	 * - from a matrix to a tensor: matrix 0 of destination tensor = converted values of source matrix. oob values treated as zero.
	 * 
	 * - from a tensor to a number: number = conversion of tensor[0][0]...
	 * - from a tensor to a vector: matrix = conversion of row 0 of matrix 0 of tensor. out of bounds values treated as zero.
	 * - from a tensor to a matrix: matrix = conversion of matrix 0 of tensor. out of bounds values treated as zero.
	 * - from a tensor to a tensor: convert values directly. tensor shapes don't change. out of bounds values treated as zero.
	 */
	
	/*
	 * Another helpful data conversion interface is the UniversalRepresentation. It represents a tensor made of octonions of
	 * nearly limitless precision. When you define a new type you can have it implement UniversalRepresentation. Types that
	 * implement this interface have a second lossless way of translating to other values. Again the number/vector/quat/oct
	 * rules outlined above apply.
	 *
	 * Note UniversalRepresentation conversions are more memory intensive and less performant than using PrimitiveConversion.
	 * But it can get you by in a pinch. The numeric parsers within Zorbage use these methods to accurately and simply create
	 * values from strings.
	 */
	
	void example2() {
		
		SignedInt14Member a = G.INT14.construct("1000");
		
		Float32VectorMember vec = new Float32VectorMember(new float[] {77,55,33});

		TensorOctonionRepresentation representation = new TensorOctonionRepresentation();

		// do this:
		
		a.toRep(representation);
		vec.fromRep(representation);  // vec = [1000,0,0]
		
		// or you can do this:
		
		UniversalConverter.convert(representation, a, vec);
	}
	
	/*
	 * Finally I should mention the two types that mirror each other. UnsignedInt1Members and BooleanMembers.
	 * Both can be used to represent true/false kinds of data. Often you might need to convert between one
	 * format or the other depending upon what algorithm you want to apply. Zorbage has two builtin converters
	 * for just such a task: UInt1ToBool and BoolToUInt1.
	 */
	
	void example3() {

		// a "true" value
		
		UnsignedInt1Member value = G.UINT1.construct("1");
		
		// allocate some booleans
		
		IndexedDataSource<BooleanMember> bools = Storage.allocate(G.BOOL.construct(), new boolean[] {true, false, true, false});
		
		// allocate a similar number of uint1's
		
		IndexedDataSource<UnsignedInt1Member> uint1s = Storage.allocate(bools.size(), new UnsignedInt1Member());

		// set the uint1's to the same values as the booelans
		
		BoolToUInt1.compute(bools, uint1s);
		
		// fill the uint1 container with true values
		
		Fill.compute(G.UINT1, value, uint1s);
		
		// convert the uint1s back into booleans
		
		UInt1ToBool.compute(uint1s, bools);
		
		// bools will now look like : [true, true, true, true]
	}
	
}

