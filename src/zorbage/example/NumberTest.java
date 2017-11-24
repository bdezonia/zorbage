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

import zorbage.groups.G;
import zorbage.type.data.int128.UnsignedInt128Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NumberTest {
	
	public void run() {
	//	addOneAndVTest();
	//	addFullRangeTest();
	//subtractOneAndVTest();
	//subtractFullRangeTest();
	//	multiplyFullRangeTest();
	//	setVTest();
	//	compareTest();
		versusTest();
		oneDivModTest();
	}

	// prove that I can divMod two numbers correctly
	private void oneDivModTest() {
		UnsignedInt128Member a = G.UINT128.construct("525");
		UnsignedInt128Member b = G.UINT128.construct("20");
		UnsignedInt128Member d = G.UINT128.construct();
		UnsignedInt128Member m = G.UINT128.construct();
		G.UINT128.divMod(a, b, d, m);
		if (!(d.v().equals(BigInteger.valueOf(26))))
			throw new IllegalArgumentException("Single divide did not work : 26 != "+d.v());
		if (!(m.v().equals(BigInteger.valueOf(5))))
			throw new IllegalArgumentException("Single mod did not work : 5 != "+m.v());
	}
	
	private void multiplyFullRangeTest() {
		System.out.println("Testing 128-bit unsigned multiply");
		UnsignedInt128Member a = new UnsignedInt128Member();
		UnsignedInt128Member b = new UnsignedInt128Member();
		UnsignedInt128Member c = new UnsignedInt128Member();
		BigInteger bigI, bigJ, max = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			//System.out.println("Pass "+i);
			bigI = BigInteger.valueOf(i);
			a.setV(bigI);
			for (int j = 0; j < 65536; j++) {
				bigJ = BigInteger.valueOf(j);
				b.setV(bigJ);
				G.UINT128.multiply(a, b, c);
				if (bigI.multiply(bigJ).mod(max).equals(c.v()))
					;
				else {
					System.out.println("multiply failed: i "+i+" j "+j+" prod "+c.v().toString());
				}
			}			
		}
		System.out.println("  done");
	}
	
	private void addOneAndVTest() {
		ArrayList<Long> list = new ArrayList<Long>();
		
		UnsignedInt128Member zero = G.UINT128.construct();
		UnsignedInt128Member min = G.UINT128.construct();
		UnsignedInt128Member max = G.UINT128.construct();
		G.UINT128.minBound(min);
		G.UINT128.maxBound(max);
		
		if (!G.UINT128.isEqual(min, zero))
			System.out.println("min bound is wrong!!!!!!!");
		
		UnsignedInt128Member v = G.UINT128.construct();
		while (G.UINT128.isLess(v, max)) {
			list.add(v.v().longValue());
			G.UINT128.succ(v, v);
		}
		list.add(v.v().longValue());
		System.out.println("Array list is this long: "+list.size());

		G.UINT128.succ(v, v);
		if (G.UINT128.isEqual(v, zero))
			System.out.println("OVERFLOW WAS CORRECT");
		else
			System.out.println("OVERFLOW WAS WRONG "+v.v());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).longValue() != i)
				System.out.println("index "+i+" equals "+list.get(i).longValue());
		}
	}

	private void subtractOneAndVTest() {
		ArrayList<Long> list = new ArrayList<Long>();
		
		UnsignedInt128Member zero = G.UINT128.construct();
		UnsignedInt128Member min = G.UINT128.construct();
		UnsignedInt128Member max = G.UINT128.construct();
		G.UINT128.minBound(min);
		G.UINT128.maxBound(max);
		
		if (!G.UINT128.isEqual(min, zero))
			System.out.println("min bound is wrong!!!!!!!");
		
		UnsignedInt128Member v = new UnsignedInt128Member(max);
		while (G.UINT128.isGreater(v, zero)) {
			list.add(v.v().longValue());
			G.UINT128.pred(v, v);
		}
		list.add(v.v().longValue());
		System.out.println("Array list is this long: "+list.size());

		G.UINT128.pred(v, v);
		if (G.UINT128.isEqual(v, max))
			System.out.println("UNDERFLOW WAS CORRECT");
		else
			System.out.println("UNDERFLOW WAS WRONG "+v.v());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).longValue() != (65535-i))
				System.out.println("index "+(65535-i)+" equals "+list.get(i).longValue());
		}
	}

	private void setVTest() {
		System.out.println("Start setVTest()");
		UnsignedInt128Member v = G.UINT128.construct();
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
		
		UnsignedInt128Member v = G.UINT128.construct();
		UnsignedInt128Member max = G.UINT128.construct();
		G.UINT128.maxBound(max);
		
		while (G.UINT128.isLess(v, max)) {
			list.add(v.duplicate());
			G.UINT128.succ(v, v);
		}
		list.add(v.duplicate());

		for (int i = 0; i < list.size(); i++) {
			UnsignedInt128Member x = list.get(i);
			if (i >= 1) {
				UnsignedInt128Member y = list.get(i-1);
				if (!G.UINT128.isGreater(x, y))
					System.out.println("successor is not greater than current");
				if (G.UINT128.isLessEqual(x, y))
					System.out.println("x is less equal to y! x " + x.v() + " y " + y.v());
			}
			if (!G.UINT128.isEqual(x,x))
				System.out.println("x is not equal to itself a " + x.v());
			if (G.UINT128.isNotEqual(x,x))
				System.out.println("x is not equal to itself b " + x.v());
			if (!G.UINT128.isGreaterEqual(x,x))
				System.out.println("x is not greater equal to itself " + x.v());
			if (!G.UINT128.isLessEqual(x,x))
				System.out.println("x is not less equal to itself " + x.v());
			if (i < list.size()-1) {
				UnsignedInt128Member y = list.get(i+1);
				if (!G.UINT128.isLess(x, y))
					System.out.println("predecessor is not less than current");
				if (G.UINT128.isGreaterEqual(x, y))
					System.out.println("x is greater equal to y! x " + x.v() + " y " + y.v());
			}
		}
		System.out.println("End compareTest()");
	}
	
	private void addFullRangeTest() {
		System.out.println("Start full range test");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger max = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			System.out.println("Pass "+i);
			a.setV(BigInteger.valueOf(i));
			for (int j = 0; j < 65536; j++) {
				b.setV(BigInteger.valueOf(j));
				G.UINT128.add(a,b,c);
				if (!(a.v().add(b.v()).mod(max).equals(c.v())))
					System.out.println("addition problem : a " + a.v() + " b " + b.v() + " c " + c.v());
			}
		}
		System.out.println("End full range test");
	}
	
	private void subtractFullRangeTest() {
		System.out.println("Start full range test");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger max = BigInteger.valueOf(65536);
		for (int i = 0; i < 1; i++) {  // TODO: more than 1
			System.out.println("Pass "+i);
			a.setV(BigInteger.valueOf(i));
			for (int j = 0; j < 65536; j++) {
				b.setV(BigInteger.valueOf(j));
				G.UINT128.subtract(a,b,c);
				// TODO I'M WRONG - FIXME
				if (!(a.v().subtract(b.v()).mod(max).equals(c.v())))
					System.out.println("subtraction problem : a " + a.v() + " b " + b.v() + " c " + c.v());
			}
		}
		System.out.println("End full range test");
	}
	
	private void versusTest() {
		long a = System.currentTimeMillis();
		
		UnsignedInt128Member v = G.UINT128.construct();
		UnsignedInt128Member one = G.UINT128.construct();
		G.UINT128.unity(one);
		for (int i = 0; i < 65536; i++) {
			G.UINT128.add(v, one, v);
		}

		long b = System.currentTimeMillis();

		BigInteger vb = BigInteger.ZERO;
		for (int i = 0; i < 65536; i++) {
			vb = vb.add(BigInteger.ONE);
		}
		
		long c = System.currentTimeMillis();
		
		System.out.println("Add one 64K times: UInt128 " + (b-a) + " BigInteger " + (c-b));
	}

}
