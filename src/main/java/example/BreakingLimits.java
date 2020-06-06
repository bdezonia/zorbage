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

import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.int12.SignedInt12Member;
import nom.bdezonia.zorbage.type.int128.SignedInt128Member;
import nom.bdezonia.zorbage.type.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.int4.UnsignedInt4Member;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * @author Barry DeZonia
 */
public class BreakingLimits {
	
	// Zorbage breaks through a lot of limitations that Java normally imposes on its programs:
	
	/*
	 * Array/List lengths
	 *   Java imposes a 2^31 limit on list lengths. Arrays are indexed by 32-bit ints.
	 *   Zorbage allows a 2^63 limit on list lengths. Zorbage arrays are indexed by 64-bit ints.
	 *     In memory arrays can be glued together if needed to make huge in memory data structures.
	 *     Other list abstractions exist to provide ways of making huge lists of data.
	 */
	
	void example1() {

		SignedInt12Member value = G.INT12.construct();
		
		IndexedDataSource<SignedInt12Member> list = Storage.allocate(17L * 1000 * 1000 * 1000, value);
		
		System.out.println(list.size()); // prints 17000000000
		
		for (long i = 0; i < list.size(); i++) {
			G.INT12.random().call(value);
			list.set(i, value);
		}
	}
	
	/*
	 * Unsigned numbers
	 *   Java does not support unsigned integers
	 *   Zorbage does support unsigned integers (from 1 bit to 64 bit)
	 */
	
	void example2() {
		
		UnsignedInt4Member u4 = G.UINT4.construct();  // stores values from 0 to 15
		
		u4.setV(14);
		
		System.out.println(u4.v()); // prints 14
		
		G.UINT4.succ().call(u4, u4); // increment

		System.out.println(u4.v()); // prints 15
		
		G.UINT4.succ().call(u4, u4); // increment

		System.out.println(u4.v());  // prints 0
		
	}
	
	/*
	 * Function pointers
	 *   Java does not support function pointers
	 *   Zorbage supports passing around 1st class type safe function and procedure pointers
	 */
	
	void example3() {
		
		// setup some temp variables
		
		SignedInt64Member value = G.INT64.construct();
		
		SignedInt64Member zero = G.INT64.construct();
		
		// get a pointer to the random value function for the 64-bit signed integer type
		
		Procedure1<SignedInt64Member> randProc = G.INT64.random();
		
		// get a pointer to the absolute value function for the 64-bit signed integer type
		
		Procedure2<SignedInt64Member,SignedInt64Member> absProc = G.INT64.abs();
		
		// generate some results
		
		for (long i = 0; i < 45; i++) {
			
			// get a value
			randProc.call(value);
			
			// test it
			if (G.INT64.isLess().call(value, zero))
				System.out.println("Something is negative");
			
			// now make all values move into positive range
			absProc.call(value, value);
			
			// test it
			if (G.INT64.isLess().call(value, zero))
				System.out.println("Something went wrong");
		}
	}
	
	/*
	 * Many floating point types
	 *   Java supports 32 bit and 64 bit floating types
	 *   Zorbage supports 16 bit, 32 bit, 64 bit, and seemingly limitless precision floating bit numbers.
	 *   
	 *     One nice thing about floating point in Zorbage is that you can write one algorithm and by
	 *     passing it different parameters you can compute a result with 3 places precision or 7 places
	 *     precision or 16 places or even 4000. You can see this in the FloatingTypes example in this
	 *     directory.
	 */
	
	void example4() {
	
		Float16Member value = G.HLF.construct();
		
		value.setV(13.1f);
		
		G.HLF.add().call(value, value, value);
		
		System.out.println(value.v()); // prints 26.2
	}
	
	/*
	 * Pass by reference of primitive types
	 *   In Java you cannot write a method that changes the value of a primitive type (int, float, etc.)
	 *   In Zorbage pass by reference even works with primitive types
	 */
	
	void example5() {
		
		Procedure1<SignedInt32Member> doubleIt =
				new Procedure1<SignedInt32Member>()
		{
			@Override
			public void call(SignedInt32Member value) {
				G.INT32.add().call(value, value, value);
			}
		};
		
		SignedInt32Member value = G.INT32.construct();
		
		// set the value to 1
		
		G.INT32.unity().call(value);
		
		// now double it
		
		doubleIt.call(value);

		// double it again
		
		doubleIt.call(value);
		
		// double it one more time
		
		doubleIt.call(value);
		
		// print result
		
		System.out.println(value.v()); // prints 8
	}
	
	/*
	 *   
	 * Non byte-aligned integers
	 *   In Java the integers are always byte aligned: 8 bit byte, 16 bit short, 32 bit int, 64 bit long
	 *   In Zorbage all these types are present but also ints of depth 1 bit, 2 bit, 3 bit, up to 16-bit
	 */
	
	void example6() {

		// You've already seen some examples above

		// construct the integers that match Java's standard bit-depths
		
		G.INT8.construct();
		G.INT16.construct();
		G.INT32.construct();
		G.INT64.construct();

		// now construct some nonstandard signed integer types
		
		G.INT1.construct();
		G.INT2.construct();
		G.INT3.construct();
		G.INT4.construct();
		G.INT5.construct();
		G.INT6.construct();
		G.INT7.construct();
		G.INT9.construct();
		G.INT10.construct();
		G.INT11.construct();
		G.INT12.construct();
		G.INT13.construct();
		G.INT14.construct();
		G.INT15.construct();
		G.INT128.construct();
		
		// now construct unsigned integer types : all nonstandard according to Java
		
		G.UINT1.construct();
		G.UINT2.construct();
		G.UINT3.construct();
		G.UINT4.construct();
		G.UINT5.construct();
		G.UINT6.construct();
		G.UINT7.construct();
		G.UINT8.construct();
		G.UINT9.construct();
		G.UINT10.construct();
		G.UINT11.construct();
		G.UINT12.construct();
		G.UINT13.construct();
		G.UINT14.construct();
		G.UINT15.construct();
		G.UINT16.construct();
		G.UINT32.construct();
		G.UINT64.construct();
		G.UINT128.construct();
		
	}
	
	/*
	 * Specialized numeric types
	 *   Zorbage supports things Java does not:
	 *   - 128 bit signed integers (SignedInt128Member)
	 *   - 128 bit unsigned integers (UnsignedInt128Member)
	 *   - unbounded integers (UnboundedIntMember)
	 *   - rational numbers (RationalMember)
	 *       Rational numbers are great for safely scaling lists of integers while avoiding overflow and
	 *       underflow and double precision math and rounding.
	 */

	void example7() {
		
		// signed 128 bit ints
		
		SignedInt128Member value128s = G.INT128.construct();
		
		value128s.setV(BigInteger.ONE.add(BigInteger.ONE).pow(93)); // set to 2^93
		
		// unsigned 128 bit ints
		
		UnsignedInt128Member value128u = G.UINT128.construct();
		
		value128u.setV(BigInteger.ONE.add(BigInteger.ONE).pow(93)); // set to 2^93
		
		// unbounded ints
		
		UnboundedIntMember valueUB = G.UNBOUND.construct();
		
		valueUB.setV(BigInteger.ONE.add(BigInteger.ONE).pow(264)); // set to 2^264
		
		// rational numbers
		
		RationalMember rationalScale = new RationalMember(7,5);  // 7/5's : a little more than one
		
		UnsignedInt8Member value = G.UINT8.construct("128");
		
		G.UINT8.scaleByRational().call(rationalScale, value, value);
		
		System.out.println(value.v()); // prints 179
		
		// notice how the number did not overflow even though 128 * 7 / 5 does temporarily exceed 255
	}
	
}
