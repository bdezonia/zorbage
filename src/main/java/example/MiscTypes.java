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
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.point.Point;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.rgb.ArgbMember;
import nom.bdezonia.zorbage.type.rgb.RgbMember;

/**
 * @author Barry DeZonia
 */
class MiscTypes {
	
	// Zorbage supports a few types that are neither integers nor float derived.

	// Rational numbers are supported by Zorbage. They lie somewhere between an integer and
	// a real. They are unlimited in their size and their decimal places of accuracy.
	
	void example1() {
		
		RationalMember frac = new RationalMember(52, 4007);  // 52/4007
		
		// java operations
		
		frac.equals(frac);
		frac.hashCode();
		frac.toString();
		
		// construction
		
		G.RAT.construct();
		G.RAT.construct(frac);
		G.RAT.construct("4.25");

		// set to zero or 1
		
		G.RAT.zero();
		G.RAT.unity();

		// basic operations
		
		G.RAT.assign();
		G.RAT.add();
		G.RAT.subtract();
		G.RAT.multiply();
		G.RAT.divide();
		G.RAT.div();
		G.RAT.mod();
		G.RAT.divMod();
		G.RAT.power();
		G.RAT.abs();
		G.RAT.invert();
		G.RAT.negate();
		G.RAT.norm();
		G.RAT.within();  // is one rational within a tolerance of another rational

		// comparisons
		
		G.RAT.compare();
		G.RAT.signum();
		G.RAT.isEqual();
		G.RAT.isGreater();
		G.RAT.isGreaterEqual();
		G.RAT.isLess();
		G.RAT.isLessEqual();
		G.RAT.isNotEqual();
		G.RAT.isZero();
		G.RAT.min();  // return the minimum of two values
		G.RAT.max();  // return the maximum of two values
		
		// scaling
		
		G.RAT.scale();
		G.RAT.scaleByHighPrec();
		G.RAT.scaleByRational();
	}
	
	// Zorbage supports a real n-dimensional Point type. The components are doubles.
	
	@SuppressWarnings("unused")
	void example2() {
	
		// 3d point
		
		Point pt = new Point(3);
		
		// Allocate 100 of them. Note the pt passed to allocator determines the dimensionality
		// of all allocated points in the returned list.
		
		IndexedDataSource<Point> points = nom.bdezonia.zorbage.storage.Storage.allocate(100, pt);

		// java operations
		
		pt.equals(pt);
		pt.hashCode();
		pt.toString();
		
		// construction
		
		G.POINT.construct();
		G.POINT.construct(pt);
		G.POINT.construct("something"); // might not actually be supported

		// basic operations
		
		G.POINT.assign();   // set b = a
		G.POINT.add();      // set c = a + b
		G.POINT.subtract(); // set c = a - b
		G.POINT.negate();   // set b = -a
		G.POINT.zero();     // set to zero
		G.POINT.random();   // set the coords of a point to a bunch of random doubles
		G.POINT.within();   // within a tolerance of another point

		// comparison
		
		G.POINT.isEqual();
		G.POINT.isNotEqual();
		G.POINT.isZero();
		
		// scaling
		
		G.POINT.scale();
		G.POINT.scaleByDouble();
		G.POINT.scaleByHighPrec();
		G.POINT.scaleByRational();
	}

	// Zorbage supports Boolean values.
	
	void example3() {
		
		BooleanMember bool = new BooleanMember(true);
		
		// java operations
		
		bool.equals(bool);
		bool.hashCode();
		bool.toString();
		
		// construction
		
		G.BOOL.construct();
		G.BOOL.construct(bool);
		G.BOOL.construct("1");  // also "true"  or "T" or "+"
		G.BOOL.construct("0");  // also "false" or "F" or "-"

		// basic operations
		
		G.BOOL.assign();   // set b = a
		G.BOOL.unity();    // set to one  / true
		G.BOOL.zero();     // set to zero / false
		G.BOOL.random();   // set to a random value
		G.BOOL.ternary();  // emulate the (a ? b : c) ternary operator from C
		
		// comparisons

		G.BOOL.isEqual();
		G.BOOL.isGreater();
		G.BOOL.isGreaterEqual();
		G.BOOL.isLess();
		G.BOOL.isLessEqual();
		G.BOOL.isNotEqual();
		G.BOOL.isZero();
		G.BOOL.compare();
		G.BOOL.signum();
		G.BOOL.min();       // return the minimum of two values
		G.BOOL.max();       // return the maximum of two values
		G.BOOL.minBound();  // 0 / false
		G.BOOL.maxBound();  // 1 / true
		
		// logical operations
		G.BOOL.logicalAnd();
		G.BOOL.logicalAndNot();
		G.BOOL.logicalNot();
		G.BOOL.logicalOr();
		G.BOOL.logicalXor();
	}

	// Zorbage supports RGB color values
	
	void example4() {
	
		RgbMember color = new RgbMember(255, 0, 0);
		
		// java operations
		
		color.equals(color);
		color.hashCode();
		color.toString();

		// construction

		G.RGB.construct();
		G.RGB.construct(color);
		G.RGB.construct("something");  // this may not actually be supported yet

		// basic operations
		
		G.RGB.assign();  // set b = a
		G.RGB.blend();   // blend two colors into a third
		G.RGB.random();  // set to a random color
		G.RGB.zero();    // set to zero (minBound)
		
		// comparisons
		
		G.RGB.isEqual();
		G.RGB.isNotEqual();
		G.RGB.isZero();
		G.RGB.minBound();  // (0,0,0)
		G.RGB.maxBound();  // (255,255,255)
	}

	// Zorbage supports ARGB color values
	
	void example5() {
	
		ArgbMember color = new ArgbMember(0, 255, 0, 0);
		
		// java operations
		
		color.equals(color);
		color.hashCode();
		color.toString();

		// construction

		G.ARGB.construct();
		G.ARGB.construct(color);
		G.ARGB.construct("something");  // this may not actually be supported yet

		// basic operations
		
		G.ARGB.assign();  // set b = a
		G.ARGB.blend();   // blend two colors into a third
		G.ARGB.random();  // set to a random color
		G.ARGB.zero();    // set to zero (minBound)
		
		// comparisons
		
		G.ARGB.isEqual();
		G.ARGB.isNotEqual();
		G.ARGB.isZero();
		G.ARGB.minBound();  // (0,0,0,0)
		G.ARGB.maxBound();  // (255,255,255,255)
	}

}