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

/**
 * @author Barry DeZonia
 */
class DataTypes {

	/*
	 * Zorbage supports many kinds of data types. They are documented separately in the
	 * following files in this same directory:
	 * 
	 *   Integers
	 *   FloatingTypes
	 *   
	 *   Complexes
	 *   Quaternions
	 *   Octonions
	 *   
	 *   Numbers
	 *   Vectors
	 *   Matrices
	 *   Tensors
	 *   
	 *   MiscTypes
	 *     booleans, strings, rgb, argb, rationals, n-dimensional real points
	 *  
	 * Some things that are worth knowing about types in Zorbage:
	 * 
	 *   1) Zorbage defines most of it's algorithms around what we call "dumb" types.
	 *      By dumb we mean that for the most part types are passed around without
	 *      knowing their capabilities. This makes for very flexible code. Algebras
	 *      are passed to algorithms as needed and they know everything necessary
	 *      to manipulate their own types. Types tend to take on the abilities to
	 *      set/get values and some construction and conversion methods. To calculate
	 *      the distance between points you don't do pt1.distanceTo(pt2). You instead
	 *      do something more like pointAlgebra.distanceBetween(pt1, pt2). In the
	 *      first example Points would need to be smart. In the second example they
	 *      don't.
	 *      
	 *   2) Zorbage algorithms use this generality to be widely applicable to many
	 *      different types. In Zorbage numbers have been the main focus. But recently
	 *      fixed length strings were added. Because of the flexibility of the
	 *      algorithms and algebras the existing Sort algorithm just worked with lists
	 *      of strings. This is quite nice. If you, as a user, define a new type you
	 *      will find 100's of algorithms that will just work with them. One such
	 *      consideration is 128-bit floating point types. If they ever make it into
	 *      the Java language spec it will only take a few lines of code before all
	 *      the existing algorithms can be used with them. Another possibility: find
	 *      a software library that does 128-bit floating point in software. Wrap it
	 *      in a type and use it now. When 128-bit hardware floating point is supported
	 *      by Java then the guts of the type are rewritten and your old code will
	 *      continue working and just become faster.
	 *   
	 *   3) In Zorbage most types derive from the idea that their data can be specified
	 *      as a fixed record size made of primitive types (byte, short, int, long,
	 *      float, double). Since bytes are one of the choices you can represent all
	 *      kinds of logical types that aren't even numbers. This fixed record capability
	 *      is used to store types to and from disk as needed. If you can't describe
	 *      your type in fixed length primitives (for instance if they are made up of
	 *      Objects or BigIntegers or BigDecimals or Java Strings) you can still use
	 *      all of Zorbage's in memory structures and run all the existing algorithms
	 *      with them. And the data can still be huge (see BigListDataSource) provided
	 *      you have enough RAM.
	 *      
	 * Some notes about specific types
	 * 
	 *   1) The integer types are uncoupled from the machine's 8 bit byte boundaries.
	 *      Zorbage has all kinds of bit depth integers and supports unsigned values
	 *      where Java does not. You can use 6-bit integers if you have a need. They
	 *      will overflow and underflow (and thus wrap around) in a way that is
	 *      consistent with how Java's 8-bit aligned types do. The sub byte/short
	 *      types are stored as bytes/short to improve their speed of calculation.
	 *      If you want them to use less space you can store them in bit oriented
	 *      data structures at some runtime cost.
	 *   
	 *   2) The floating types are written to be interchangeable in algorithms.
	 *      At the writing of this help file Zorbage supports 16-bit, 32-bit, 64-bit,
	 *      and nearly unlimited size floating point numbers. You can use or write
	 *      one algorithm and can substitute the precision you desire. Use just
	 *      what you need or just what is fast etc. Zorbage will figure it out for
	 *      you.
	 */
}