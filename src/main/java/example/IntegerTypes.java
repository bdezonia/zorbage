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
class IntegerTypes {

	/*
	 *   The integer types are uncoupled from the machine's 8 bit byte boundaries.
	 *   Zorbage has all kinds of bit depth integers and supports unsigned values
	 *   where Java does not. You can use 6-bit integers if you have a need. They
	 *   will overflow and underflow (and thus wrap around) in a way that is
	 *   consistent with how Java's 8-bit aligned types do. The sub byte/short
	 *   types are stored as bytes/short to improve their speed of calculation.
	 *   If you want them to use less space you can store them in bit oriented
	 *   data structures at some runtime cost.
	 */
	
	// Zorbage supports integers of the following sizes:
	
	// 1-bit (signed and unsigned)
	// 2-bit (signed and unsigned)
	// 3-bit (signed and unsigned)
	// 4-bit (signed and unsigned)
	// 5-bit (signed and unsigned)
	// 6-bit (signed and unsigned)
	// 7-bit (signed and unsigned)
	// 8-bit (signed and unsigned)
	// 9-bit (signed and unsigned)
	// 10-bit (signed and unsigned)
	// 11-bit (signed and unsigned)
	// 12-bit (signed and unsigned)
	// 13-bit (signed and unsigned)
	// 14-bit (signed and unsigned)
	// 15-bit (signed and unsigned)
	// 16-bit (signed and unsigned)
	// 32-bit (signed and unsigned)
	// 64-bit (signed and unsigned)
	// 128-bit (signed and unsigned)
	// unbounded (signed)
	
}
