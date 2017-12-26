/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.data.int8.UnsignedInt8Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestLessGreaterForIntTypes {

	@Test
	public void int8() {
		SignedInt8Member a = new SignedInt8Member((byte) Byte.MIN_VALUE);
		SignedInt8Member b = new SignedInt8Member((byte) (Byte.MIN_VALUE+1));
		SignedInt8Member c = new SignedInt8Member((byte)-1);
		SignedInt8Member d = new SignedInt8Member((byte)0);
		SignedInt8Member e = new SignedInt8Member((byte)1);
		SignedInt8Member f = new SignedInt8Member((byte) (Byte.MAX_VALUE-1));
		SignedInt8Member g = new SignedInt8Member((byte) Byte.MAX_VALUE);
		
		assertTrue(G.INT8.isLess(a, b));
		assertFalse(G.INT8.isGreater(a, b));
		
		assertTrue(G.INT8.isLess(b, c));
		assertFalse(G.INT8.isGreater(b, c));
		
		assertTrue(G.INT8.isLess(c, d));
		assertFalse(G.INT8.isGreater(c, d));
		
		assertTrue(G.INT8.isLess(d, e));
		assertFalse(G.INT8.isGreater(d, e));
		
		assertTrue(G.INT8.isLess(e, f));
		assertFalse(G.INT8.isGreater(e, f));
		
		assertTrue(G.INT8.isLess(f, g));
		assertFalse(G.INT8.isGreater(f, g));
	}

	@Test
	public void uint8() {
		UnsignedInt8Member a = new UnsignedInt8Member(0);
		UnsignedInt8Member b = new UnsignedInt8Member(1);
		UnsignedInt8Member c = new UnsignedInt8Member(2);
		UnsignedInt8Member d = new UnsignedInt8Member(127);
		UnsignedInt8Member e = new UnsignedInt8Member(128);
		UnsignedInt8Member f = new UnsignedInt8Member(254);
		UnsignedInt8Member g = new UnsignedInt8Member(255);
		
		assertTrue(G.UINT8.isLess(a, b));
		assertFalse(G.UINT8.isGreater(a, b));
		
		assertTrue(G.UINT8.isLess(b, c));
		assertFalse(G.UINT8.isGreater(b, c));
		
		assertTrue(G.UINT8.isLess(c, d));
		assertFalse(G.UINT8.isGreater(c, d));
		
		assertTrue(G.UINT8.isLess(d, e));
		assertFalse(G.UINT8.isGreater(d, e));
		
		assertTrue(G.UINT8.isLess(e, f));
		assertFalse(G.UINT8.isGreater(e, f));
		
		assertTrue(G.UINT8.isLess(f, g));
		assertFalse(G.UINT8.isGreater(f, g));
	}

	@Test
	public void int16() {
		SignedInt16Member a = new SignedInt16Member(Short.MIN_VALUE);
		SignedInt16Member b = new SignedInt16Member((short)(Short.MIN_VALUE+1));
		SignedInt16Member c = new SignedInt16Member((short)-1);
		SignedInt16Member d = new SignedInt16Member((short)0);
		SignedInt16Member e = new SignedInt16Member((short)1);
		SignedInt16Member f = new SignedInt16Member((short)(Short.MAX_VALUE-1));
		SignedInt16Member g = new SignedInt16Member(Short.MAX_VALUE);
		
		assertTrue(G.INT16.isLess(a, b));
		assertFalse(G.INT16.isGreater(a, b));
		
		assertTrue(G.INT16.isLess(b, c));
		assertFalse(G.INT16.isGreater(b, c));
		
		assertTrue(G.INT16.isLess(c, d));
		assertFalse(G.INT16.isGreater(c, d));
		
		assertTrue(G.INT16.isLess(d, e));
		assertFalse(G.INT16.isGreater(d, e));
		
		assertTrue(G.INT16.isLess(e, f));
		assertFalse(G.INT16.isGreater(e, f));
		
		assertTrue(G.INT16.isLess(f, g));
		assertFalse(G.INT16.isGreater(f, g));
	}

	@Test
	public void uint16() {
		UnsignedInt16Member a = new UnsignedInt16Member(0);
		UnsignedInt16Member b = new UnsignedInt16Member(1);
		UnsignedInt16Member c = new UnsignedInt16Member(2);
		UnsignedInt16Member d = new UnsignedInt16Member(32767);
		UnsignedInt16Member e = new UnsignedInt16Member(32768);
		UnsignedInt16Member f = new UnsignedInt16Member(65534);
		UnsignedInt16Member g = new UnsignedInt16Member(65535);
		
		assertTrue(G.UINT16.isLess(a, b));
		assertFalse(G.UINT16.isGreater(a, b));
		
		assertTrue(G.UINT16.isLess(b, c));
		assertFalse(G.UINT16.isGreater(b, c));
		
		assertTrue(G.UINT16.isLess(c, d));
		assertFalse(G.UINT16.isGreater(c, d));
		
		assertTrue(G.UINT16.isLess(d, e));
		assertFalse(G.UINT16.isGreater(d, e));
		
		assertTrue(G.UINT16.isLess(e, f));
		assertFalse(G.UINT16.isGreater(e, f));
		
		assertTrue(G.UINT16.isLess(f, g));
		assertFalse(G.UINT16.isGreater(f, g));
	}

	@Test
	public void int32() {
		SignedInt32Member a = new SignedInt32Member(Integer.MIN_VALUE);
		SignedInt32Member b = new SignedInt32Member(Integer.MIN_VALUE+1);
		SignedInt32Member c = new SignedInt32Member(-1);
		SignedInt32Member d = new SignedInt32Member(0);
		SignedInt32Member e = new SignedInt32Member(1);
		SignedInt32Member f = new SignedInt32Member(Integer.MAX_VALUE-1);
		SignedInt32Member g = new SignedInt32Member(Integer.MAX_VALUE);
		
		assertTrue(G.INT32.isLess(a, b));
		assertFalse(G.INT32.isGreater(a, b));
		
		assertTrue(G.INT32.isLess(b, c));
		assertFalse(G.INT32.isGreater(b, c));
		
		assertTrue(G.INT32.isLess(c, d));
		assertFalse(G.INT32.isGreater(c, d));
		
		assertTrue(G.INT32.isLess(d, e));
		assertFalse(G.INT32.isGreater(d, e));
		
		assertTrue(G.INT32.isLess(e, f));
		assertFalse(G.INT32.isGreater(e, f));
		
		assertTrue(G.INT32.isLess(f, g));
		assertFalse(G.INT32.isGreater(f, g));
	}

	@Test
	public void uint32() {
		UnsignedInt32Member a = new UnsignedInt32Member(0);
		UnsignedInt32Member b = new UnsignedInt32Member(1);
		UnsignedInt32Member c = new UnsignedInt32Member(2);
		UnsignedInt32Member d = new UnsignedInt32Member(0x7fffffffL);
		UnsignedInt32Member e = new UnsignedInt32Member(0x80000000L);
		UnsignedInt32Member f = new UnsignedInt32Member(0xffffffffL-1);
		UnsignedInt32Member g = new UnsignedInt32Member(0xffffffffL);
		
		assertTrue(G.UINT32.isLess(a, b));
		assertFalse(G.UINT32.isGreater(a, b));
		
		assertTrue(G.UINT32.isLess(b, c));
		assertFalse(G.UINT32.isGreater(b, c));
		
		assertTrue(G.UINT32.isLess(c, d));
		assertFalse(G.UINT32.isGreater(c, d));
		
		assertTrue(G.UINT32.isLess(d, e));
		assertFalse(G.UINT32.isGreater(d, e));
		
		assertTrue(G.UINT32.isLess(e, f));
		assertFalse(G.UINT32.isGreater(e, f));
		
		assertTrue(G.UINT32.isLess(f, g));
		assertFalse(G.UINT32.isGreater(f, g));
	}

	@Test
	public void int64() {
		SignedInt64Member a = new SignedInt64Member(Long.MIN_VALUE);
		SignedInt64Member b = new SignedInt64Member(Long.MIN_VALUE+1);
		SignedInt64Member c = new SignedInt64Member(-1);
		SignedInt64Member d = new SignedInt64Member(0);
		SignedInt64Member e = new SignedInt64Member(1);
		SignedInt64Member f = new SignedInt64Member(Long.MAX_VALUE-1);
		SignedInt64Member g = new SignedInt64Member(Long.MAX_VALUE);
		
		assertTrue(G.INT64.isLess(a, b));
		assertFalse(G.INT64.isGreater(a, b));
		
		assertTrue(G.INT64.isLess(b, c));
		assertFalse(G.INT64.isGreater(b, c));
		
		assertTrue(G.INT64.isLess(c, d));
		assertFalse(G.INT64.isGreater(c, d));
		
		assertTrue(G.INT64.isLess(d, e));
		assertFalse(G.INT64.isGreater(d, e));
		
		assertTrue(G.INT64.isLess(e, f));
		assertFalse(G.INT64.isGreater(e, f));
		
		assertTrue(G.INT64.isLess(f, g));
		assertFalse(G.INT64.isGreater(f, g));
	}

	@Test
	public void uint64() {
		UnsignedInt64Member a = new UnsignedInt64Member(BigInteger.ZERO);
		UnsignedInt64Member b = new UnsignedInt64Member(BigInteger.ONE);
		UnsignedInt64Member c = new UnsignedInt64Member(BigInteger.ONE.add(BigInteger.ONE));
		UnsignedInt64Member d = new UnsignedInt64Member(new BigInteger("7fffffffffffffff",16));
		UnsignedInt64Member e = new UnsignedInt64Member(new BigInteger("8000000000000000",16));
		UnsignedInt64Member f = new UnsignedInt64Member(new BigInteger("ffffffffffffffff",16).subtract(BigInteger.ONE));
		UnsignedInt64Member g = new UnsignedInt64Member(new BigInteger("ffffffffffffffff",16));
		
		assertTrue(G.UINT64.isLess(a, b));
		assertFalse(G.UINT64.isGreater(a, b));
		
		assertTrue(G.UINT64.isLess(b, c));
		assertFalse(G.UINT64.isGreater(b, c));
		
		assertTrue(G.UINT64.isLess(c, d));
		assertFalse(G.UINT64.isGreater(c, d));
		
		assertTrue(G.UINT64.isLess(d, e));
		assertFalse(G.UINT64.isGreater(d, e));
		
		assertTrue(G.UINT64.isLess(e, f));
		assertFalse(G.UINT64.isGreater(e, f));
		
		assertTrue(G.UINT64.isLess(f, g));
		assertFalse(G.UINT64.isGreater(f, g));
	}

	@Test
	public void int128() {
		/* 
		 *  TODO - uncomment when signed int 128 class is in place
		 * 
		SignedInt128Member a = new SignedInt128Member(new BigInteger("minus max"));
		SignedInt128Member b = new SignedInt128Member(new BigInteger("minus max").add(BigInteger.ONE));
		SignedInt128Member c = new SignedInt128Member(BigInteger.valueOf(-1));
		SignedInt128Member d = new SignedInt128Member(BigInteger.ZERO);
		SignedInt128Member e = new SignedInt128Member(BigInteger.ONE);
		SignedInt128Member f = new SignedInt128Member(new BigInteger("plus max").subtract(BigInteger.ONE));
		SignedInt128Member g = new SignedInt128Member(new BigInteger("plus max"));
		
		assertTrue(G.INT128.isLess(a, b));
		assertFalse(G.INT128.isGreater(a, b));
		
		assertTrue(G.INT128.isLess(b, c));
		assertFalse(G.INT128.isGreater(b, c));
		
		assertTrue(G.INT128.isLess(c, d));
		assertFalse(G.INT128.isGreater(c, d));
		
		assertTrue(G.INT128.isLess(d, e));
		assertFalse(G.INT128.isGreater(d, e));
		
		assertTrue(G.INT128.isLess(e, f));
		assertFalse(G.INT128.isGreater(e, f));
		
		assertTrue(G.INT128.isLess(f, g));
		assertFalse(G.INT128.isGreater(f, g));
		*/
	}

	@Test
	public void uint128() {
		UnsignedInt128Member a = new UnsignedInt128Member(BigInteger.ZERO);
		UnsignedInt128Member b = new UnsignedInt128Member(BigInteger.ONE);
		UnsignedInt128Member c = new UnsignedInt128Member(BigInteger.ONE.add(BigInteger.ONE));
		UnsignedInt128Member d = new UnsignedInt128Member(new BigInteger("7fffffffffffffffffffffffffffffff",16));
		UnsignedInt128Member e = new UnsignedInt128Member(new BigInteger("80000000000000000000000000000000",16));
		UnsignedInt128Member f = new UnsignedInt128Member(new BigInteger("ffffffffffffffffffffffffffffffff",16).subtract(BigInteger.ONE));
		UnsignedInt128Member g = new UnsignedInt128Member(new BigInteger("ffffffffffffffffffffffffffffffff",16));
		
		assertTrue(G.UINT128.isLess(a, b));
		assertFalse(G.UINT128.isGreater(a, b));
		
		assertTrue(G.UINT128.isLess(b, c));
		assertFalse(G.UINT128.isGreater(b, c));
		
		assertTrue(G.UINT128.isLess(c, d));
		assertFalse(G.UINT128.isGreater(c, d));
		
		assertTrue(G.UINT128.isLess(d, e));
		assertFalse(G.UINT128.isGreater(d, e));
		
		assertTrue(G.UINT128.isLess(e, f));
		assertFalse(G.UINT128.isGreater(e, f));
		
		assertTrue(G.UINT128.isLess(f, g));
		assertFalse(G.UINT128.isGreater(f, g));
	}
}
