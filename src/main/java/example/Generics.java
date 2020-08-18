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
class Generics {

	/*
	 * Zorbage is designed around Java generics. There are many generic algorithms
	 * included that can be used with many types (even many types that have yet to
	 * be defined). This is a primary goal of Zorbage: people sharing code between
	 * each other that can work with many current and (as needed) newly defined
	 * types.
	 * 
	 * Zorbage is defined around Algebras that manipulate generic types which are
	 * often labeled as U's. A generic U type (such as a SignedInt32Member) is
	 * meant to be designed as a dumb data holder. A U can set and get values,
	 * define how they are stored to data structures, and provide various type
	 * conversions. Otherwise U's should be dumb. This frees up the many algorithms
	 * of Zorbage to manipulate values using Algebras that are smart.
	 * 
	 * One aspect of these dumb U's and an orientation of using generics is that
	 * Java's code generator can define optimal code for manipulating U objects
	 * as inline stack constructed data structures.
	 */
	
}