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
import nom.bdezonia.zorbage.type.integer.int12.SignedInt12Member;

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
	
	// For more details about the ranges and sizes of these integers see:
	//
	//   https://github.com/bdezonia/zorbage/blob/master/src/main/java/example/Numbers.java
	
	// Here are the supported calls for Integers (using int12s as an example)
	
	@SuppressWarnings("unused")
	void example1() {
		
		SignedInt12Member a = G.INT12.construct();
		SignedInt12Member b = G.INT12.construct("14");
		SignedInt12Member c = G.INT12.construct(b);
		SignedInt12Member d = new SignedInt12Member(25);

		G.INT12.zero();
		G.INT12.unity();
		
		G.INT12.maxBound();
		G.INT12.minBound();

		G.INT12.isEqual();
		G.INT12.isEven();
		G.INT12.isOdd();
		G.INT12.isNotEqual();
		G.INT12.isGreater();
		G.INT12.isGreaterEqual();
		G.INT12.isLess();
		G.INT12.isLessEqual();
		G.INT12.isZero();
		G.INT12.signum();
		G.INT12.compare();

		G.INT12.assign();
		G.INT12.add();
		G.INT12.subtract();
		G.INT12.multiply();
		G.INT12.div();
		G.INT12.mod();
		G.INT12.divMod();
		G.INT12.pow();
		G.INT12.power();
		G.INT12.negate();

		G.INT12.bitAnd();
		G.INT12.bitAndNot();
		G.INT12.bitNot();
		G.INT12.bitOr();
		G.INT12.bitXor();
		G.INT12.bitShiftLeft();
		G.INT12.bitShiftRight();
		G.INT12.bitShiftRightFillZero();

		G.INT12.abs();
		G.INT12.gcd();
		G.INT12.lcm();
		G.INT12.max();
		G.INT12.min();
		G.INT12.norm();
		G.INT12.within();

		G.INT12.pred();
		G.INT12.succ();

		G.INT12.scale();
		G.INT12.scaleByDouble();
		G.INT12.scaleByDoubleAndRound();
		G.INT12.scaleByHighPrec();
		G.INT12.scaleByHighPrecAndRound();
		G.INT12.scaleByRational();
		G.INT12.scaleByOneHalf();
		G.INT12.scaleByTwo();

		G.INT12.random();
	}
}
