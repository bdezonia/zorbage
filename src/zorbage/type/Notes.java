/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Notes {

	/*
	 * Motivation
	 * 
	 * I was a user of Imglib2 and found the type system limited. I wanted to build an algebraically correct and
	 * extensible type system that had a functional bent and will eventually support efficient access and reasonable
	 * memory use. I believe in the future when data is mapped to native containers Java's optimizing compiler will
	 * obtain runtime efficiencies due to simplicity of types.
	 * 
	 */
	
	// Much of the algebra hierarchy was derived from information in A Book of Abstract Algebra by Charles C. Pinter
	// plus a few other sources to fill in some details.
	
	// TODO add lots of notes from previous attempted code
	
	/*
	 * Have a Storage mechanism. Storage can read and write bytes. We can store records. Byte alignment might be
	 * important for floating types etc.
	 * see https://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
	 * 
	 * We could have specialized Storage like FloatStorage etc.
	 * 
	 * Storage is a List<sometype> so have length and grab one at a time. Can set and get. There can be readonly
	 * storage where you can only get.
	 * 
	 * We can structure on top of Storage for multidimensionality. This might not be a Storage but a container
	 * of some kind that has dimensions and associated Storage.
	 * 
	 * Must be able to map from object data to native data within containers. You have an accessor that
	 * copies an in memory type like SignedInt32 to/from a container via a well timed get/set call.
	 * 
	 * Root's pose a problem for numeric hierarchy?? The sqrt of -1 is complex. A separate root finder might need
	 * to be specific to Float64 and ComplexFloat32 etc. Where in numeric hierarchy is root(int power, T a) method?
	 * Also root(Equation e, Point nearPoint)?
	 * 
	 * Do we need a Real type instead of Float64 using OrderedField? Do I need a Complex type?
	 * 
	 * How to handle constants like PI, E, and all kinds of others like ones from Gnu Scientific Library? Maybe we
	 * just construct them as constants. But how can algebraic based code access them? Passed in as arguments?
	 * Or we break into single interfaces that can be implemented like E(U a) and generics of method signature can
	 * say U extends E?
	 * 
	 * Break all the methods in the interfaces into their own interfaces and include them in hierarchy. Then can do
	 * fine grained methods that say U extends Add & Subtract and thats all the method does. Then can pass a Field
	 * and it can do work. But any object that implements them and only them can be passed making need to implement
	 * extra methods nonexistent.
	 * 
	 * Matrices, Complexes, Quaternions, 2x2 matrices?, Polynomials, more int and float types, binaries, bools, 
	 * bit types, etc. Want string representations of them to build constructors from. More ideas: types from
	 * the symbolic algebra book I have. Vectors and vector space. Tensors.
	 * 
	 * Right now some classes have zero() and unity(). This is a difficult way to build numbers. Need base() like
	 * 1 or 2 or 4 or 8 or 10 or 12 or 16 or even higher? Need exponents? Then build numbers this way. And reals
	 * need decimal expansions like 1.70534 base 10 exp 23.
	 *   
	 */
	
	// TODO: do I make boolean methods in Hierarchy instead map to BooleanMembers?
	
	//idea: a numeric string type like json
	//tensor = [?]
	//matrix = [vector, vector, vector]
	//vector = [number, number, number]
	//number = int | real | complex | quaternion ( | octonion? )
	//int = \d+ or even based like ffff_16 or 777_8
	//real = \d+.\d+ | exponential_form
	//complex = ( real , real )
	//quaternion = ( real, real, real, real) or (complex, complex, complex, complex)
	//
	//a string parser ignores what it must to recognize the first number found setting
	//missing components to 0
	// it could range check and set elems outside range to max or min or range if needed
	// it would round if that made sense
	//a bit type would parse 10000 as 1
	//a quat type would parse 10000 as (10000,0,0,0)
	
	// TODO a BigDecimal based class
	
	// Have Vectors and Matrices and Tensors whose subelements are any numeric type.
	// Make complex double type be Complex<DoubleType>. This likely hurts efficiency.
	// But I can rewrite this in a different language. Also Matrix<Quaternion<Float32Type>>, etc.
	
}
