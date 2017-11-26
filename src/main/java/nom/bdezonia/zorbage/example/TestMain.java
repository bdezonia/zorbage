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
package nom.bdezonia.zorbage.example;

import java.util.Arrays;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.AdditiveGroup;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.data.bigint.UnboundedIntMember;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.operation.Average;
import nom.bdezonia.zorbage.type.operation.Max;
import nom.bdezonia.zorbage.type.operation.Median;
import nom.bdezonia.zorbage.type.operation.Min;
import nom.bdezonia.zorbage.type.operation.Sum;
import nom.bdezonia.zorbage.type.storage.linear.LinearAccessor;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageSignedInt32;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMain {
	
	
	
	
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
		Float64Member scale = new Float64Member(6.3);
		Float64Member tmp = new Float64Member();
		accessor.beforeFirst();
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			tmp.setV(value.v());
			G.DBL.multiply(tmp, scale, tmp);
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
		Average<Float64Group,Float64Member> a = new Average<Float64Group,Float64Member>(G.DBL);
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Average value = " + result.v());
	}
	
	public static void testMedian() {
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
		Median<Float64Group,Float64Member> a = new Median<Float64Group,Float64Member>(G.DBL);
		Float64Member result = new Float64Member();
		a.calculate(storage, result);
		System.out.println("Median value = " + result.v());
	}

	public static void testMin() {
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
		Min<Float64Group,Float64Member> a = new Min<Float64Group,Float64Member>(G.DBL);
		Float64Member result = new Float64Member();
		Float64Member max = new Float64Member();
		G.DBL.maxBound(max);
		a.calculate(storage, max, result);
		System.out.println("Minimum value = " + result.v());
	}

	public static void testMax() {
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
		Max<Float64Group,Float64Member> a = new Max<Float64Group,Float64Member>(G.DBL);
		Float64Member result = new Float64Member();
		Float64Member min = new Float64Member();
		G.DBL.minBound(min);
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
		Sum<Float64Group,Float64Member> a = new Sum<Float64Group,Float64Member>(G.DBL);
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
		//new BigMatrixExample().run();
	}
	
	public static void testLUMatrix() {
		new LUDecompExample().run();
	}
	
	public static void testTuples() {
		new TupleExample().run();
	}
	
	public static void main(String[] args) {
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
		testLUMatrix();
		testTuples();
		
		// TODO when go from byte backed to long backed will need to remove this test due to speed
		new NumberTest().run();
		
		new UInt64Tests().run();
	}

}
