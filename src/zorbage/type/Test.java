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

import zorbage.type.algebra.IntegralDomain;
import zorbage.type.algebra.Ordered;
import zorbage.type.converter.Converter;
import zorbage.type.converter.ConverterFloat64ToSignedInt32;
import zorbage.type.converter.ConverterSignedInt32ToFloat64;
import zorbage.type.data.Float64Member;
import zorbage.type.data.Float64OrderedField;
import zorbage.type.data.SignedInt32Integer;
import zorbage.type.data.SignedInt32Member;
import zorbage.type.storage.Binding;
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
	
	public static <T extends IntegralDomain<T,U> & Ordered<U>, U> void testOrderedIntegralDomain(T g) {
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
	
	public static <T extends IntegralDomain<T,U> & Ordered<U>, U> void testOrderedIntegralDomainAndStrings(T g) {
		U a = g.construct("1040");
		U b = g.construct("160");
		U c = g.construct();
		g.add(a, b, c);
		System.out.println(a.toString() + " plus " + b.toString() + " equals " + c.toString());
		System.out.println(a.toString() + " equals " + b.toString() + " : " + g.isEqual(a, b));
		System.out.println(a.toString() + " is greater than " + b.toString() + " : " + g.isGreater(a, b));
	}

	public static <U> void scaleByConstant(U value, Float64Member constant,
			Converter<U, Float64Member> toFloat, Converter<Float64Member, U> fromFloat)
	{
		IntegralDomain<Float64OrderedField,Float64Member> g = new Float64OrderedField();
		Float64Member tmp = new Float64Member();
		toFloat.convert(value, tmp);
		g.multiply(tmp, constant, tmp);
		fromFloat.convert(tmp, value);
	}
	
	public static void testConversion() {
		int v = 1000;
		double scale = 1.4;
		SignedInt32Member value = new SignedInt32Member(v);
		scaleByConstant(
			value,
			new Float64Member(scale),
			new ConverterSignedInt32ToFloat64(),
			new ConverterFloat64ToSignedInt32()
		);
		System.out.println("scaling " + v + " by " + scale + " equals " + value.v());
	}
	
	public static void testBindings() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32 storage = new ArrayStorageSignedInt32(10);
		Binding<SignedInt32Member> binding = new Binding<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (binding.hasNext()) {
			binding.fwd();
			binding.get();
			value.setV(i++);
			binding.put();
		}
		// visit the data in reverse order
		binding.afterLast();
		while (binding.hasPrev()) {
			binding.back();
			binding.get();
			System.out.println("element " + binding.pos() + " equals " + value.v());
		}
	}

	// Scale a collection of Int32s by a floating point number
	public static void testGroupOfConversions() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32 storage = new ArrayStorageSignedInt32(10);
		Binding<SignedInt32Member> binding = new Binding<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (binding.hasNext()) {
			binding.fwd();
			binding.get();
			value.setV(i++);
			binding.put();
		}
		// scale it by 6.3
		Float64OrderedField g = new Float64OrderedField();
		ConverterSignedInt32ToFloat64 toFloat = new ConverterSignedInt32ToFloat64();
		ConverterFloat64ToSignedInt32 fromFloat = new ConverterFloat64ToSignedInt32(); // TODO make a rounding converter
		Float64Member scale = new Float64Member(6.3);
		Float64Member tmp = new Float64Member();
		binding.beforeFirst();
		while (binding.hasNext()) {
			binding.fwd();
			binding.get();
			toFloat.convert(value, tmp);
			g.multiply(tmp, scale, tmp);
			fromFloat.convert(tmp, value);
			binding.put();
		}
		// print it out
		binding.beforeFirst();
		while (binding.hasNext()) {
			binding.fwd();
			binding.get();
			System.out.println("element " + binding.pos() + " equals " + value.v());
		}
	}

	public static void main(String[] args) {
		testInts();
		testFloats();
		testOrderedIntegralDomain(new SignedInt32Integer());
		testOrderedIntegralDomain(new Float64OrderedField());
		testOrderedIntegralDomainAndStrings(new SignedInt32Integer());
		testOrderedIntegralDomainAndStrings(new Float64OrderedField());
		testConversion();
		testBindings();
		testGroupOfConversions();
	}
}
