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

import java.math.BigInteger;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class UInt64Tests {

	public void run() {
		System.out.println("UInt64 tests");
		testPred();
		testSucc();
	}
	
	private static void test(boolean b, int testNum) {
		if (!b)
			throw new IllegalArgumentException("test "+testNum+" failed");
	}
	
	private static void testPred() {
		UnsignedInt64Member a = G.UINT64.construct();
		a.setV(BigInteger.valueOf(4));
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("3",16)),1);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("2",16)),2);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("1",16)),3);
		G.UINT64.pred(a, a);
		test(a.v().equals(BigInteger.ZERO),4);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("ffffffffffffffff",16)),5);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("fffffffffffffffe",16)),6);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("fffffffffffffffd",16)),7);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("fffffffffffffffc",16)),8);
		a.setV(new BigInteger("8000000000000003",16));
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("8000000000000002",16)),10);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("8000000000000001",16)),11);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("8000000000000000",16)),12);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("7fffffffffffffff",16)),13);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("7ffffffffffffffe",16)),14);
		G.UINT64.pred(a, a);
		test(a.v().equals(new BigInteger("7ffffffffffffffd",16)),15);
	}

	private static void testSucc() {
		UnsignedInt64Member a = G.UINT64.construct();
		test(a.v().equals(BigInteger.ZERO),199);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("1",16)),16);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("2",16)),17);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("3",16)),18);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("4",16)),19);
		a.setV(new BigInteger("7ffffffffffffffd",16));
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("7ffffffffffffffe",16)),20);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("7fffffffffffffff",16)),21);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("8000000000000000",16)),22);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("8000000000000001",16)),23);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("8000000000000002",16)),24);
		a.setV(new BigInteger("fffffffffffffffc",16));
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("fffffffffffffffd",16)),25);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("fffffffffffffffe",16)),26);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("ffffffffffffffff",16)),27);
		G.UINT64.succ(a, a);
		test(a.v().equals(BigInteger.ZERO),28);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("1",16)),29);
		G.UINT64.succ(a, a);
		test(a.v().equals(new BigInteger("2",16)),30);
	}
}
