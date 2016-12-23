/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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

import zorbage.type.algebra.AdditiveGroup;
import zorbage.type.algebra.Ordered;
import zorbage.type.algebra.Unity;
import zorbage.type.converter.ConverterFloat64ToSignedInt32;
import zorbage.type.converter.ConverterSignedInt32ToFloat64;
import zorbage.type.data.BooleanMember;
import zorbage.type.data.Float64Member;
import zorbage.type.data.Float64OrderedField;
import zorbage.type.data.SignedInt32Integer;
import zorbage.type.data.SignedInt32Member;
import zorbage.type.math.Average;
import zorbage.type.math.Max;
import zorbage.type.math.Median;
import zorbage.type.math.Min;
import zorbage.type.math.Sum;
import zorbage.type.storage.Accessor;
import zorbage.type.storage.ArrayStorageFloat64;
import zorbage.type.storage.ArrayStorageSignedInt32;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Test {
	
	public static void testInts() {
		  
		SignedInt32Integer g = new SignedInt32Integer();
		  
		SignedInt32Member a = new SignedInt32Member(1);
		SignedInt32Member b = new SignedInt32Member(4);
		SignedInt32Member sum = new SignedInt32Member(99);

		g.add(a,b,sum);
		  
		System.out.println(a.v() + " plus " + b.v() + " equals " + sum.v());
	}
	
	public static void testFloats() {
		  
		Float64OrderedField g = new Float64OrderedField();
		  
		Float64Member a = new Float64Member(1.1);
		Float64Member b = new Float64Member(4.2);
		Float64Member sum = new Float64Member(99.3);

		g.add(a,b,sum);
		  
		System.out.println(a.v() + " plus " + b.v() + " equals " + sum.v());
	}
	
	public static <T extends AdditiveGroup<T,U> & Unity<U> & Ordered<U>, U> void test1(T g) {
		U a = g.construct();
		g.unity(a);
		U b = g.construct();
		g.zero(b);
		g.add(a, a, b);
		U c = g.construct();
		g.add(a, b, c);
		System.out.println(a.toString() + " plus " + b.toString() + " equals " + c.toString());
		System.out.println(a.toString() + " equals " + b.toString() + " : " + g.isEqual(a, b));
		System.out.println(a.toString() + " is greater than " + b.toString() + " : " + g.isGreater(a, b));
	}
	
	public static <T extends AdditiveGroup<T,U> & Ordered<U>, U> void test2(T g) {
		U a = g.construct("1040");
		U b = g.construct("160");
		U c = g.construct();
		g.add(a, b, c);
		System.out.println(a.toString() + " plus " + b.toString() + " equals " + c.toString());
		System.out.println(a.toString() + " equals " + b.toString() + " : " + g.isEqual(a, b));
		System.out.println(a.toString() + " is greater than " + b.toString() + " : " + g.isGreater(a, b));
	}
	
	public static void testAccessor() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32 storage = new ArrayStorageSignedInt32(10);
		Accessor<SignedInt32Member> accessor = new Accessor<SignedInt32Member>(value, storage);
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
		ArrayStorageSignedInt32 storage = new ArrayStorageSignedInt32(10);
		Accessor<SignedInt32Member> accessor = new Accessor<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		// scale it by 6.3
		Float64OrderedField g = new Float64OrderedField();
		ConverterSignedInt32ToFloat64 toFloat = new ConverterSignedInt32ToFloat64();
		ConverterFloat64ToSignedInt32 fromFloat = new ConverterFloat64ToSignedInt32(); // TODO make a rounding converter
		Float64Member scale = new Float64Member(6.3);
		Float64Member tmp = new Float64Member();
		accessor.beforeFirst();
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			toFloat.convert(value, tmp);
			g.multiply(tmp, scale, tmp);
			fromFloat.convert(tmp, value);
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
		ArrayStorageFloat64 storage = new ArrayStorageFloat64(10);
		Accessor<Float64Member> accessor = new Accessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Average<Float64OrderedField,Float64Member> a =
				new Average<Float64OrderedField,Float64Member>(new Float64OrderedField());
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Average value = " + result.v());
	}
	
	public static void testMedian() {
		Float64OrderedField g = new Float64OrderedField();
		Float64Member value = new Float64Member();
		ArrayStorageFloat64 storage = new ArrayStorageFloat64(10);
		Accessor<Float64Member> accessor = new Accessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Median<Float64OrderedField,Float64Member> a = new Median<Float64OrderedField,Float64Member>(g);
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Median value = " + result.v());
	}

	public static void testMin() {
		Float64OrderedField g = new Float64OrderedField();
		Float64Member value = new Float64Member();
		ArrayStorageFloat64 storage = new ArrayStorageFloat64(10);
		Accessor<Float64Member> accessor = new Accessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Min<Float64OrderedField,Float64Member> a = new Min<Float64OrderedField,Float64Member>(g);
		Float64Member result = new Float64Member();
		Float64Member max = new Float64Member();
		g.maxBound(max);
		a.calculate(storage, max, result);
		System.out.println("Minimum value = " + result.v());
	}

	public static void testMax() {
		Float64OrderedField g = new Float64OrderedField();
		Float64Member value = new Float64Member();
		ArrayStorageFloat64 storage = new ArrayStorageFloat64(10);
		Accessor<Float64Member> accessor = new Accessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Max<Float64OrderedField,Float64Member> a = new Max<Float64OrderedField,Float64Member>(g);
		Float64Member result = new Float64Member();
		Float64Member min = new Float64Member();
		g.minBound(min);
		a.calculate(storage, min, result);
		System.out.println("Maximum value = " + result.v());
	}

	public static void testSum() {
		Float64Member value = new Float64Member();
		ArrayStorageFloat64 storage = new ArrayStorageFloat64(10);
		Accessor<Float64Member> accessor = new Accessor<Float64Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		Sum<Float64OrderedField,Float64Member> a =
				new Sum<Float64OrderedField,Float64Member>(new Float64OrderedField());
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
	
	public static void main(String[] args) {
		testInts();
		testFloats();
		test1(new SignedInt32Integer());
		test1(new Float64OrderedField());
		test2(new SignedInt32Integer());
		test2(new Float64OrderedField());
		testAccessor();
		testGroupOfConversions();
		testAverage();
		testMedian();
		testMin();
		testMax();
		testSum();
		testParsing();
	}
}
