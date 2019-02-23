/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.BitOperations;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;

//Adapted from an algorithm as published by Stepanov and Rose 2015.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DivMod {

	/**
	 * 
	 * @param alg
	 * @param a
	 * @param b
	 * @param d
	 * @param m
	 */
	/*
	public static <T extends Algebra<T,U> & Ordered<U> & Unity<U> & Addition<U> & BitOperations<U> & Bounded<U>,U>
		void bloop(T alg, U a, U b, U d, U m)
	{
		// TODO: need code that can handle -minint cases without negate() called
		
		if (alg.isZero().call(b))
			throw new IllegalArgumentException("divide by zero");
		
		U zero = alg.construct();
		U one = alg.construct();
		alg.unity().call(one);
		U min = alg.construct();
		alg.minBound().call(min);
		
		// NOTE: I could write the algo to always work in neg nums and never
		// negate. This would solve the pronlem better than I am now with
		// special cases.
		
		// if signed and denom is -minint
		if (alg.isLess().call(a, zero) && alg.isEqual().call(a, min)) {
			if (alg.isEqual().call(a, b)) {
				alg.assign().call(one, d);
				alg.assign().call(zero, m);
			}
			else {
				// a == -minint and b > a
				// a mod b = ((a+1) mod b) - 1; does this map b = 4 correctly for all cases
				// a div b = ???
				throw new IllegalArgumentException("TODO");
			}
			return;
		}

		// if signed and denom is -minint
		if (alg.isLess().call(b, zero) && alg.isEqual().call(b, min)) {
			// if here a != b
			alg.assign().call(zero, d);
			alg.assign().call(a, m);
			// TODO: flip sign maybe based upon sign of a? b? both?
			return;
		}

		// if here then neither a nor b is -minint. so if needed can be negated.
		
		// make sure inputs are positive. this code only applies to signed types.
		// unsigned types just pass through.
		
		boolean aNeg;
		boolean bNeg;
		U aPos = alg.construct();
		U bPos = alg.construct();
		if (alg.isLess().call(a, zero)) {
			aNeg = true;
			alg.negate().call(a, aPos);
		}
		else {
			aNeg = false;
			alg.assign().call(a, aPos);
		}
		if (alg.isLess().call(b, zero)) {
			bNeg = true;
			alg.negate().call(b, bPos);
		}
		else {
			bNeg = false;
			alg.assign().call(b, bPos);
		}

		if (alg.isLess().call(aPos, bPos)) 
		{
			if (alg.isLess().call(a, zero))
				alg.negate().call(aPos, aPos);
			alg.assign().call(zero, d);
			alg.assign().call(aPos, m);
			return;
		}
		U c = alg.construct();
		largestDoubling(alg,aPos,bPos,c);
		U n = alg.construct(one);
		alg.subtract().call(aPos, c, aPos);
		while (!alg.isEqual().call(c,bPos)) {
			alg.bitShiftRight().call(1, c, c);
			alg.add().call(n, n, n);
			if (alg.isLessEqual().call(c, aPos)) {
				alg.subtract().call(aPos, c, aPos);
				alg.add().call(n, one, n);
			}
		}
		if (aNeg != bNeg)
			alg.negate().call(n, n);
		if (alg.isLess().call(a, zero))
			alg.negate().call(aPos, aPos);
		alg.assign().call(n, d);
		alg.assign().call(aPos, m);
	}
	*/
	
	private static <T extends Algebra<T,U> & Ordered<U> & Addition<U>,U>
		void largestDoublingP(T alg, U a, U b, U c)
	{
		U tmpB = alg.construct(b);
		U diff = alg.construct();
		alg.subtract().call(a, tmpB, diff);
		while (alg.isGreaterEqual().call(diff, tmpB)) {
			alg.add().call(tmpB, tmpB, tmpB);
			alg.subtract().call(a, tmpB, diff);
		}
		alg.assign().call(tmpB, c);
		//System.out.println("largestDoublingP returning "+c);
	}

	private static <T extends Algebra<T,U> & Ordered<U> & Addition<U>,U>
		void largestDoublingN(T alg, U a, U b, U c)
	{
		U tmpB = alg.construct(b);
		U diff = alg.construct();
		alg.subtract().call(a, tmpB, diff);
		while (alg.isLessEqual().call(diff, tmpB)) {
			alg.add().call(tmpB, tmpB, tmpB);
			alg.subtract().call(a, tmpB, diff);
		}
		alg.assign().call(tmpB, c);
		//System.out.println("largestDoublingN returning "+c);
	}

	public static <T extends Algebra<T,U> & Ordered<U> & Unity<U> & Addition<U> & BitOperations<U> & Bounded<U>,U>
		void compute(T alg, U a, U b, U d, U m)
	{
		if (alg.isZero().call(b))
			throw new IllegalArgumentException("divide by zero");

		U zero = alg.construct();
		U one = alg.construct();
		U min = alg.construct();
		alg.unity().call(one);
		alg.minBound().call(min);
		
		if (alg.isLess().call(min, zero)) {
			// signed numbers: work in negative numbers to accommodate -minint asymmetry
			boolean aPos;
			boolean bPos;
			U aNeg = alg.construct();
			U bNeg = alg.construct();
			if (alg.isGreater().call(a, zero)) {
				aPos = true;
				alg.negate().call(a, aNeg);
			}
			else {
				aPos = false;
				alg.assign().call(a, aNeg);
			}
			if (alg.isGreater().call(b, zero)) {
				bPos = true;
				alg.negate().call(b, bNeg);
			}
			else {
				bPos = false;
				alg.assign().call(b, bNeg);
			}
			if (alg.isGreater().call(aNeg, bNeg)) 
			{
				// TODO: look closely at me
				if (aPos)
					alg.negate().call(aNeg, aNeg);
				alg.assign().call(zero, d);
				alg.assign().call(aNeg, m);
				//System.out.println("aaa");
				return;
			}
			U c = alg.construct();
			largestDoublingN(alg, aNeg, bNeg, c);
			if (alg.isGreater().call(aNeg, zero))
				throw new IllegalArgumentException("bad aNeg");
			if (alg.isGreater().call(bNeg, zero))
				throw new IllegalArgumentException("bad bNeg");
			if (alg.isGreater().call(c, zero))
				throw new IllegalArgumentException("bad c");
			U n = alg.construct(one);
			//System.out.println("aNeg "+aNeg+" bNeg "+bNeg+" c "+c+" n "+n);
			alg.subtract().call(aNeg, c, aNeg);
			while (!alg.isEqual().call(c, bNeg)) {
				alg.bitShiftRight().call(1, c, c);
				alg.add().call(n, n, n);
				if (alg.isGreaterEqual().call(c, aNeg)) {
					alg.subtract().call(aNeg, c, aNeg);
					alg.add().call(n, one, n);
				}
				if (alg.isGreater().call(aNeg, zero))
					throw new IllegalArgumentException("bad aNeg");
				if (alg.isGreater().call(bNeg, zero))
					throw new IllegalArgumentException("bad bNeg");
				if (alg.isGreater().call(c, zero))
					throw new IllegalArgumentException("bad c");
				//System.out.println("aNeg "+aNeg+" bNeg "+bNeg+" c "+c+" n "+n);
			}
			if (aPos != bPos)
				alg.negate().call(n, n);
			if (aPos)
				alg.negate().call(aNeg, aNeg);
			alg.assign().call(n, d);
			alg.assign().call(aNeg, m);
			//System.out.println("bbb");
		}
		else {
			// unsigned numbers: work with positive number algorithm
			U aPos = alg.construct(a);
			U bPos = alg.construct(b);
			if (alg.isLess().call(aPos, bPos)) 
			{
				alg.assign().call(zero, d);
				alg.assign().call(aPos, m);
				return;
			}
			U c = alg.construct();
			largestDoublingP(alg, aPos, bPos, c);
			U n = alg.construct(one);
			alg.subtract().call(aPos, c, aPos);
			while (!alg.isEqual().call(c, bPos)) {
				alg.bitShiftRight().call(1, c, c);
				alg.add().call(n, n, n);
				if (alg.isLessEqual().call(c, aPos)) {
					alg.subtract().call(aPos, c, aPos);
					alg.add().call(n, one, n);
				}
			}
			alg.assign().call(n, d);
			alg.assign().call(aPos, m);
		}
	}
}