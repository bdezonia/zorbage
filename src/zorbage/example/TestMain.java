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
package zorbage.example;

import java.util.Arrays;

import zorbage.type.algebra.AdditiveGroup;
import zorbage.type.algebra.Ordered;
import zorbage.type.algebra.Unity;
import zorbage.type.data.bigint.UnboundedIntGroup;
import zorbage.type.data.bigint.UnboundedIntMember;
import zorbage.type.data.bool.BooleanMember;
import zorbage.type.data.float64.real.Float64Group;
import zorbage.type.data.float64.real.Float64Member;
import zorbage.type.data.int32.SignedInt32Group;
import zorbage.type.data.int32.SignedInt32Member;
import zorbage.type.operation.Average;
import zorbage.type.operation.Max;
import zorbage.type.operation.Median;
import zorbage.type.operation.Min;
import zorbage.type.operation.Sum;
import zorbage.type.storage.linear.LinearAccessor;
import zorbage.type.storage.linear.array.ArrayStorageFloat64;
import zorbage.type.storage.linear.array.ArrayStorageSignedInt32;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMain {
	
	public static void testInts() {
		  
		SignedInt32Group int32 = new SignedInt32Group();
		  
		SignedInt32Member a = new SignedInt32Member(1);
		SignedInt32Member b = new SignedInt32Member(4);
		SignedInt32Member sum = new SignedInt32Member(99);

		int32.add(a,b,sum);
		  
		System.out.println(a.v() + " plus " + b.v() + " equals " + sum.v());
	}
	
	public static void testFloats() {
		  
		Float64Group dbl = new Float64Group();
		  
		Float64Member a = new Float64Member(1.1);
		Float64Member b = new Float64Member(4.2);
		Float64Member sum = new Float64Member(99.3);

		dbl.add(a,b,sum);
		  
		System.out.println(a.v() + " plus " + b.v() + " equals " + sum.v());
	}
	
	public static void testHugeNumbers() {
		UnboundedIntGroup huge = new UnboundedIntGroup();
		  
		UnboundedIntMember a = new UnboundedIntMember(Long.MAX_VALUE);
		UnboundedIntMember b = new UnboundedIntMember(44);
		UnboundedIntMember product = new UnboundedIntMember();

		huge.multiply(a,b,product);
		  
		System.out.println(a.v() + " times " + b.v() + " equals " + product.v());
	}
	
	public static <T extends AdditiveGroup<T,U> & Unity<U> & Ordered<U>, U> void test1(T grp) {
		U a = grp.construct();
		grp.unity(a);
		U b = grp.construct();
		grp.zero(b);
		grp.add(a, a, b);
		U c = grp.construct();
		grp.add(a, b, c);
		System.out.println(a.toString() + " plus " + b.toString() + " equals " + c.toString());
		System.out.println(a.toString() + " equals " + b.toString() + " : " + grp.isEqual(a, b));
		System.out.println(a.toString() + " is greater than " + b.toString() + " : " + grp.isGreater(a, b));
	}
	
	public static <T extends AdditiveGroup<T,U> & Ordered<U>, U> void test2(T grp) {
		U a = grp.construct("1040");
		U b = grp.construct("160");
		U c = grp.construct();
		grp.add(a, b, c);
		System.out.println(a.toString() + " plus " + b.toString() + " equals " + c.toString());
		System.out.println(a.toString() + " equals " + b.toString() + " : " + grp.isEqual(a, b));
		System.out.println(a.toString() + " is greater than " + b.toString() + " : " + grp.isGreater(a, b));
	}
	
	public static void testAccessor() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32<SignedInt32Member> storage = new ArrayStorageSignedInt32<SignedInt32Member>(10, new SignedInt32Member());
		LinearAccessor<SignedInt32Member> accessor = new LinearAccessor<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		// visit the data in reverse order
		accessor.afterLast();
		while (accessor.hasPrev()) {
			accessor.back();
			accessor.get();
			System.out.println("element " + accessor.pos() + " equals " + value.v());
		}
	}

	// Scale a collection of Int32s by a floating point number
	public static void testGroupOfConversions() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32<SignedInt32Member> storage = new ArrayStorageSignedInt32<SignedInt32Member>(10, new SignedInt32Member());
		LinearAccessor<SignedInt32Member> accessor = new LinearAccessor<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		// scale it by 6.3
		Float64Group dbl = new Float64Group();
		Float64Member scale = new Float64Member(6.3);
		Float64Member tmp = new Float64Member();
		accessor.beforeFirst();
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			tmp.setV(value.v());
			dbl.multiply(tmp, scale, tmp);
			value.setV((int)Math.round(tmp.v()));
			accessor.put();
		}
		// print it out
		accessor.beforeFirst();
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			System.out.println("element " + accessor.pos() + " equals " + value.v());
		}
	}

	public static void testAverage() {
		Float64Member value = new Float64Member();
		ArrayStorageFloat64<Float64Member> storage = new ArrayStorageFloat64<Float64Member>(10, value);
		LinearAccessor<Float64Member> accessor = new LinearAccessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Average<Float64Group,Float64Member> a =
				new Average<Float64Group,Float64Member>(new Float64Group());
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Average value = " + result.v());
	}
	
	public static void testMedian() {
		Float64Group g = new Float64Group();
		Float64Member value = new Float64Member();
		ArrayStorageFloat64<Float64Member> storage = new ArrayStorageFloat64<Float64Member>(10, value);
		LinearAccessor<Float64Member> accessor = new LinearAccessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Median<Float64Group,Float64Member> a = new Median<Float64Group,Float64Member>(g);
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Median value = " + result.v());
	}

	public static void testMin() {
		Float64Group dbl = new Float64Group();
		Float64Member value = new Float64Member();
		ArrayStorageFloat64<Float64Member> storage = new ArrayStorageFloat64<Float64Member>(10, value);
		LinearAccessor<Float64Member> accessor = new LinearAccessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Min<Float64Group,Float64Member> a = new Min<Float64Group,Float64Member>(dbl);
		Float64Member result = new Float64Member();
		Float64Member max = new Float64Member();
		dbl.maxBound(max);
		a.calculate(storage, max, result);
		System.out.println("Minimum value = " + result.v());
	}

	public static void testMax() {
		Float64Group dbl = new Float64Group();
		Float64Member value = new Float64Member();
		ArrayStorageFloat64<Float64Member> storage = new ArrayStorageFloat64<Float64Member>(10, value);
		LinearAccessor<Float64Member> accessor = new LinearAccessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Max<Float64Group,Float64Member> a = new Max<Float64Group,Float64Member>(dbl);
		Float64Member result = new Float64Member();
		Float64Member min = new Float64Member();
		dbl.minBound(min);
		a.calculate(storage, min, result);
		System.out.println("Maximum value = " + result.v());
	}

	public static void testSum() {
		Float64Member value = new Float64Member();
		ArrayStorageFloat64<Float64Member> storage = new ArrayStorageFloat64<Float64Member>(10, value);
		LinearAccessor<Float64Member> accessor = new LinearAccessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Sum<Float64Group,Float64Member> a =
				new Sum<Float64Group,Float64Member>(new Float64Group());
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Sum value = " + result.v());
	}

	public static void testParsing() {
		BooleanMember b;
		b = new BooleanMember("false");
		System.out.println("b should be false and is " + b.v());
		b = new BooleanMember("true");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("1");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("0");
		System.out.println("b should be false and is " + b.v());
		b = new BooleanMember("2");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("3.4");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("-3.4");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("+3.4");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("1.2e+05");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("(1,0)");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("(0,1)");
		System.out.println("b should be false and is " + b.v());
		b = new BooleanMember("[0]");
		System.out.println("b should be false and is " + b.v());
		b = new BooleanMember("[1]");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("[1,0,0]");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("[[1,0,0][0,0,0]]");
		System.out.println("b should be true and is " + b.v());
		b = new BooleanMember("[[[1,0,0][0,0,0]][[0,0,0][0,0,0]]]");
		System.out.println("b should be true and is " + b.v());
	}

	public static void testFileStorage() {
		new FileStorageExample().run();
		System.out.println("tested file backed data storage");
	}
	
	public static void testSpeed() {
		long[] timings = new long[20];
		long x = System.currentTimeMillis();
		for (int i = 0; i < timings.length; i++) {
			new DFTExample().run();
			long y = System.currentTimeMillis();
			timings[i] = y - x;
			x = y;
		}
		System.out.println(Arrays.toString(timings));
	}

	public static void testSize() {
		new BigMatrixExample().run();
	}
	
	public static void testQuats() {
		new QuaternionExample().run();
	}
	
	public static void main(String[] args) {
		testInts();
		testFloats();
		testHugeNumbers();
		testQuats();
		test1(new SignedInt32Group());
		test1(new Float64Group());
		test2(new SignedInt32Group());
		test2(new Float64Group());
		testAccessor();
		testGroupOfConversions();
		testAverage();
		testMedian();
		testMin();
		testMax();
		testSum();
		testParsing();
		testFileStorage();
		testSpeed();
		//testSize(); // very slow
	}

}
