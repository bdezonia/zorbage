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
import nom.bdezonia.zorbage.algorithm.CreateMask;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.StablePartition;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.predicate.AndPredicate;
import nom.bdezonia.zorbage.predicate.Equal;
import nom.bdezonia.zorbage.predicate.EqualConstant;
import nom.bdezonia.zorbage.predicate.GreaterThan;
import nom.bdezonia.zorbage.predicate.GreaterThanConstant;
import nom.bdezonia.zorbage.predicate.GreaterThanEqual;
import nom.bdezonia.zorbage.predicate.GreaterThanEqualConstant;
import nom.bdezonia.zorbage.predicate.LessThan;
import nom.bdezonia.zorbage.predicate.LessThanConstant;
import nom.bdezonia.zorbage.predicate.LessThanEqual;
import nom.bdezonia.zorbage.predicate.LessThanEqualConstant;
import nom.bdezonia.zorbage.predicate.NandPredicate;
import nom.bdezonia.zorbage.predicate.NorPredicate;
import nom.bdezonia.zorbage.predicate.NotEqual;
import nom.bdezonia.zorbage.predicate.NotEqualConstant;
import nom.bdezonia.zorbage.predicate.NotPredicate;
import nom.bdezonia.zorbage.predicate.OrPredicate;
import nom.bdezonia.zorbage.predicate.XnorPredicate;
import nom.bdezonia.zorbage.predicate.XorPredicate;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * @author Barry DeZonia
 */
class Predicates {

	/* 
	 * A predicate is a logical function that tests an input and returns a boolean.
	 * You can also think of it as a condition. Some conditions include "number is
	 * odd", "number is greater than 100.0", "a string is lexicographically less
	 * than another string".
	 */
	
	/*
	 * Some basic predicates are already defined
	 */
	
	@SuppressWarnings("unused")
	void exmaple0() {

		boolean result;
		
		// basic one input predicates
		
		SignedInt16Member value = new SignedInt16Member(56);
		
		EqualConstant<SignedInt16Algebra, SignedInt16Member> eqC = new EqualConstant<>(G.INT16, value);
		NotEqualConstant<SignedInt16Algebra, SignedInt16Member> neqC = new NotEqualConstant<>(G.INT16, value);
		LessThanConstant<SignedInt16Algebra, SignedInt16Member> lessC = new LessThanConstant<>(G.INT16, value);
		LessThanEqualConstant<SignedInt16Algebra, SignedInt16Member> lessEqC = new LessThanEqualConstant<>(G.INT16, value);
		GreaterThanConstant<SignedInt16Algebra, SignedInt16Member> greatC = new GreaterThanConstant<>(G.INT16, value);
		GreaterThanEqualConstant<SignedInt16Algebra, SignedInt16Member> greatEqC = new GreaterThanEqualConstant<>(G.INT16, value);

		SignedInt16Member value2 = new SignedInt16Member(105);
		
		result = eqC.call(value2);
		result = neqC.call(value2);
		result = lessC.call(value2);
		result = lessEqC.call(value2);
		result = greatC.call(value2);
		result = greatEqC.call(value2);
		
		// basic two input predicates
		
		Equal<SignedInt16Algebra, SignedInt16Member> eq = new Equal<>(G.INT16);
		NotEqual<SignedInt16Algebra, SignedInt16Member> neq = new NotEqual<>(G.INT16);
		LessThan<SignedInt16Algebra, SignedInt16Member> less = new LessThan<>(G.INT16);
		LessThanEqual<SignedInt16Algebra, SignedInt16Member> lessEq = new LessThanEqual<>(G.INT16);
		GreaterThan<SignedInt16Algebra, SignedInt16Member> great = new GreaterThan<>(G.INT16);
		GreaterThanEqual<SignedInt16Algebra, SignedInt16Member> greatEq = new GreaterThanEqual<>(G.INT16);
		
		SignedInt16Member a = new SignedInt16Member(45);
		SignedInt16Member b = new SignedInt16Member(104);
		
		Tuple2<SignedInt16Member,SignedInt16Member> tuple = new Tuple2<>(a,b);
		
		result = eq.call(tuple);
		result = neq.call(tuple);
		result = less.call(tuple);
		result = lessEq.call(tuple);
		result = great.call(tuple);
		result = greatEq.call(tuple);
		
	}
	
	/*
	 *  Some classes are already predicate aware. Many of the C++ STL methods use predicates.
	 */
	
	void example1() {

		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(),250);
		
		Fill.compute(G.DBL, G.DBL.random(), data);
		
		Function1<Boolean,Float64Member> condition =
				new Function1<Boolean,Float64Member>()
		{
			@Override
			public Boolean call(Float64Member value) {
				return value.v() > 7;
			}
		};
		
		StablePartition.compute(G.DBL, condition, data);
	}

	/*
	 * You can write your own predicates : 1 input
	 */
	
	@SuppressWarnings("unused")
	void example2() {
	
		Function1<Boolean,SignedInt16Member> lessThan100 =
				new Function1<Boolean,SignedInt16Member>()
		{
			@Override
			public Boolean call(SignedInt16Member value) {
				return value.v() < 100;
			}
		};
		
		SignedInt16Member v = new SignedInt16Member(22);
		
		boolean result = lessThan100.call(v);
	}
	
	/*
	 * You can write your own predicates : 2 inputs
	 */
	
	@SuppressWarnings("unused")
	void example3() {
	
		Function1<Boolean,Tuple2<SignedInt16Member,SignedInt16Member>> notEqual =
				new Function1<Boolean,Tuple2<SignedInt16Member,SignedInt16Member>>()
		{
			@Override
			public Boolean call(Tuple2<SignedInt16Member,SignedInt16Member> value) {
				
				return value.a().v() != value.b().v();
			}
		};
		
		SignedInt16Member v1 = new SignedInt16Member(22);
		
		SignedInt16Member v2 = new SignedInt16Member(25);
		
		Tuple2<SignedInt16Member,SignedInt16Member> tuple = new Tuple2<>(v1,v2);

		boolean result = notEqual.call(tuple);
	}
	
	/*
	 * Zorbage provides a number of ways to combine other predicates into more
	 * sophisticated predicates.
	 */
	
	@SuppressWarnings("unused")
	void example4() {
	
		Function1<Boolean,SignedInt16Member> eq7Cond = new EqualConstant<>(G.INT16, new SignedInt16Member(7));

		Function1<Boolean,SignedInt16Member> lessThan5Cond =  new LessThanConstant<>(G.INT16, new SignedInt16Member(5));

		// Here are the seven basic logical combinations of predicates
		
		AndPredicate<SignedInt16Member> cond1 = new AndPredicate<SignedInt16Member>(eq7Cond, lessThan5Cond);
	
		OrPredicate<SignedInt16Member> cond2 = new OrPredicate<SignedInt16Member>(eq7Cond, lessThan5Cond);
		
		NandPredicate<SignedInt16Member> cond3 = new NandPredicate<SignedInt16Member>(eq7Cond, lessThan5Cond);
		
		NorPredicate<SignedInt16Member> cond4 = new NorPredicate<SignedInt16Member>(eq7Cond, lessThan5Cond);
		
		NotPredicate<SignedInt16Member> cond5 = new NotPredicate<SignedInt16Member>(lessThan5Cond);
		
		XorPredicate<SignedInt16Member> cond6 = new XorPredicate<SignedInt16Member>(eq7Cond, lessThan5Cond);
		
		XnorPredicate<SignedInt16Member> cond7 = new XnorPredicate<SignedInt16Member>(eq7Cond, lessThan5Cond);
		
		SignedInt16Member num = new SignedInt16Member(-13);
		
		boolean result;
		
		result = cond1.call(num);
		result = cond2.call(num);
		result = cond3.call(num);
		result = cond4.call(num);
		result = cond5.call(num);
		result = cond6.call(num);
		result = cond7.call(num);
	}
	
	/*
	 * You can use predicates to create a mask from another data set
	 */
	
	@SuppressWarnings("unused")
	void example() {
		
		IndexedDataSource<Float64Member> data =
				nom.bdezonia.zorbage.storage.Storage.allocate(G.DBL.construct(),250);
		
		Fill.compute(G.DBL, G.DBL.random(), data);
		
		Function1<Boolean,Float64Member> condition =
				new Function1<Boolean,Float64Member>()
		{
			@Override
			public Boolean call(Float64Member value) {
				return (value.v() >= 0 && value.v() < 0.3);
			}
		};
		
		IndexedDataSource<UnsignedInt1Member> mask = CreateMask.compute(G.DBL, condition, data);
	}

}