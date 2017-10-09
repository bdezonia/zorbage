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

import java.math.BigInteger;
import java.util.ArrayList;

import zorbage.type.data.int128.UnsignedInt128Group;
import zorbage.type.data.int128.UnsignedInt128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NumberTest {
	
	private UnsignedInt128Group uint128 = new UnsignedInt128Group();
	
	public void run() {
		addOneAndVTest();
		addFullRangeTest();
		setVTest();
		compareTest();
	}
	
	private void addOneAndVTest() {
		ArrayList<Long> list = new ArrayList<Long>();
		
		UnsignedInt128Member zero = uint128.construct();
		UnsignedInt128Member min = uint128.construct();
		UnsignedInt128Member max = uint128.construct();
		uint128.minBound(min);
		uint128.maxBound(max);
		
		if (!uint128.isEqual(min, zero))
			System.out.println("min bound is wrong!!!!!!!");
		
		UnsignedInt128Member v = uint128.construct();
		while (uint128.isLess(v, max)) {
			list.add(v.v().longValue());
			uint128.succ(v, v);
		}
		list.add(v.v().longValue());

		uint128.succ(v, v);
		if (uint128.isEqual(v, zero))
			System.out.println("OVERFLOW WAS CORRECT");
		else
			System.out.println("OVERFLOW WAS WRONG "+v.v());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).longValue() != i)
				System.out.println("index "+i+" equals "+list.get(i).longValue());
		}
		System.out.println("Array list is this long: "+list.size());
	}

	private void setVTest() {
		System.out.println("Start setVTest()");
		UnsignedInt128Member v = uint128.construct();
		for (int i = 0; i < 65536; i++) {
			v.setV(BigInteger.valueOf(i));
			if (v.v().longValue() != i)
				System.out.println("Problem: i = "+i+" and v = "+v.v().longValue());
		}
		System.out.println("End setVTest()");
	}
	
	private void compareTest() {
		System.out.println("Start compareTest()");
		ArrayList<UnsignedInt128Member> list = new ArrayList<UnsignedInt128Member>();
		
		UnsignedInt128Member v = uint128.construct();
		UnsignedInt128Member max = uint128.construct();
		uint128.maxBound(max);
		
		while (uint128.isLess(v, max)) {
			list.add(v.duplicate());
			uint128.succ(v, v);
		}
		list.add(v.duplicate());

		for (int i = 0; i < list.size(); i++) {
			UnsignedInt128Member x = list.get(i);
			if (i >= 1) {
				UnsignedInt128Member y = list.get(i-1);
				if (!uint128.isGreater(x, y))
					System.out.println("successor is not greater than current");
				if (uint128.isLessEqual(x, y))
					System.out.println("x is less equal to y! x " + x.v() + " y " + y.v());
			}
			if (!uint128.isEqual(x,x))
				System.out.println("x is not equal to itself a " + x.v());
			if (uint128.isNotEqual(x,x))
				System.out.println("x is not equal to itself b " + x.v());
			if (!uint128.isGreaterEqual(x,x))
				System.out.println("x is not greater equal to itself " + x.v());
			if (!uint128.isLessEqual(x,x))
				System.out.println("x is not less equal to itself " + x.v());
			if (i < list.size()-1) {
				UnsignedInt128Member y = list.get(i+1);
				if (!uint128.isLess(x, y))
					System.out.println("predecessor is not less than current");
				if (uint128.isGreaterEqual(x, y))
					System.out.println("x is greater equal to y! x " + x.v() + " y " + y.v());
			}
		}
		System.out.println("End compareTest()");
	}
	
	private void addFullRangeTest() {
		System.out.println("Start full range test");
		UnsignedInt128Member a = uint128.construct();
		UnsignedInt128Member b = uint128.construct();
		UnsignedInt128Member c = uint128.construct();
		for (int i = 0; i < 65536; i++) {
			System.out.println("Pass "+i);
			a.setV(BigInteger.valueOf(i));
			for (int j = 0; j < 65536; j++) {
				b.setV(BigInteger.valueOf(j));
				uint128.add(a,b,c);
				if (!(a.v().add(b.v()).mod(BigInteger.valueOf(65536)).equals(c.v())))
					System.out.println("addition problem : a " + a.v() + " b " + b.v() + " c " + c.v());
			}
		}
		System.out.println("End full range test");
	}

}
