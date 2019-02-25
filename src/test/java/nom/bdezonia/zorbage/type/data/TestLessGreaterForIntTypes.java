/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.int1.SignedInt1Member;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.data.int10.SignedInt10Member;
import nom.bdezonia.zorbage.type.data.int10.UnsignedInt10Member;
import nom.bdezonia.zorbage.type.data.int11.SignedInt11Member;
import nom.bdezonia.zorbage.type.data.int11.UnsignedInt11Member;
import nom.bdezonia.zorbage.type.data.int12.SignedInt12Member;
import nom.bdezonia.zorbage.type.data.int12.UnsignedInt12Member;
import nom.bdezonia.zorbage.type.data.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.data.int13.SignedInt13Member;
import nom.bdezonia.zorbage.type.data.int13.UnsignedInt13Member;
import nom.bdezonia.zorbage.type.data.int14.SignedInt14Member;
import nom.bdezonia.zorbage.type.data.int14.UnsignedInt14Member;
import nom.bdezonia.zorbage.type.data.int15.SignedInt15Member;
import nom.bdezonia.zorbage.type.data.int15.UnsignedInt15Member;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.data.int2.SignedInt2Member;
import nom.bdezonia.zorbage.type.data.int2.UnsignedInt2Member;
import nom.bdezonia.zorbage.type.data.int3.SignedInt3Member;
import nom.bdezonia.zorbage.type.data.int3.UnsignedInt3Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.data.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.data.int4.UnsignedInt4Member;
import nom.bdezonia.zorbage.type.data.int5.SignedInt5Member;
import nom.bdezonia.zorbage.type.data.int5.UnsignedInt5Member;
import nom.bdezonia.zorbage.type.data.int6.SignedInt6Member;
import nom.bdezonia.zorbage.type.data.int6.UnsignedInt6Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.data.int7.SignedInt7Member;
import nom.bdezonia.zorbage.type.data.int7.UnsignedInt7Member;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.data.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.data.int9.SignedInt9Member;
import nom.bdezonia.zorbage.type.data.int9.UnsignedInt9Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestLessGreaterForIntTypes {

	@Test
	public void int1() {
		SignedInt1Member a = new SignedInt1Member(-1);
		SignedInt1Member b = new SignedInt1Member(0);

		assertTrue(G.INT1.isLess().call(a, b));
		assertFalse(G.INT1.isGreater().call(a, b));
	}
	
	@Test
	public void uint1() {
		UnsignedInt1Member a = new UnsignedInt1Member(0);
		UnsignedInt1Member b = new UnsignedInt1Member(1);

		assertTrue(G.UINT1.isLess().call(a, b));
		assertFalse(G.UINT1.isGreater().call(a, b));
	}
	
	@Test
	public void int2() {
		SignedInt2Member a = new SignedInt2Member(-2);
		SignedInt2Member b = new SignedInt2Member(-1);
		SignedInt2Member c = new SignedInt2Member(0);
		SignedInt2Member d = new SignedInt2Member(1);
		
		assertTrue(G.INT2.isLess().call(a, b));
		assertFalse(G.INT2.isGreater().call(a, b));
		
		assertTrue(G.INT2.isLess().call(b, c));
		assertFalse(G.INT2.isGreater().call(b, c));
		
		assertTrue(G.INT2.isLess().call(c, d));
		assertFalse(G.INT2.isGreater().call(c, d));
	}
	
	@Test
	public void uint2() {
		UnsignedInt2Member a = new UnsignedInt2Member(0);
		UnsignedInt2Member b = new UnsignedInt2Member(1);
		UnsignedInt2Member c = new UnsignedInt2Member(2);
		UnsignedInt2Member d = new UnsignedInt2Member(3);
		
		assertTrue(G.UINT2.isLess().call(a, b));
		assertFalse(G.UINT2.isGreater().call(a, b));
		
		assertTrue(G.UINT2.isLess().call(b, c));
		assertFalse(G.UINT2.isGreater().call(b, c));
		
		assertTrue(G.UINT2.isLess().call(c, d));
		assertFalse(G.UINT2.isGreater().call(c, d));
	}
	
	@Test
	public void int3() {
		SignedInt3Member a = new SignedInt3Member(-4);
		SignedInt3Member b = new SignedInt3Member(-3);
		SignedInt3Member c = new SignedInt3Member(-2);
		SignedInt3Member d = new SignedInt3Member(-1);
		SignedInt3Member e = new SignedInt3Member(0);
		SignedInt3Member f = new SignedInt3Member(1);
		SignedInt3Member g = new SignedInt3Member(2);
		SignedInt3Member h = new SignedInt3Member(3);

		assertTrue(G.INT3.isLess().call(a, b));
		assertFalse(G.INT3.isGreater().call(a, b));
		
		assertTrue(G.INT3.isLess().call(b, c));
		assertFalse(G.INT3.isGreater().call(b, c));
		
		assertTrue(G.INT3.isLess().call(c, d));
		assertFalse(G.INT3.isGreater().call(c, d));
		
		assertTrue(G.INT3.isLess().call(d, e));
		assertFalse(G.INT3.isGreater().call(d, e));
		
		assertTrue(G.INT3.isLess().call(e, f));
		assertFalse(G.INT3.isGreater().call(e, f));
		
		assertTrue(G.INT3.isLess().call(f, g));
		assertFalse(G.INT3.isGreater().call(f, g));
		
		assertTrue(G.INT3.isLess().call(g, h));
		assertFalse(G.INT3.isGreater().call(g, h));
	}
	
	@Test
	public void uint3() {
		UnsignedInt3Member a = new UnsignedInt3Member(0);
		UnsignedInt3Member b = new UnsignedInt3Member(1);
		UnsignedInt3Member c = new UnsignedInt3Member(2);
		UnsignedInt3Member d = new UnsignedInt3Member(3);
		UnsignedInt3Member e = new UnsignedInt3Member(4);
		UnsignedInt3Member f = new UnsignedInt3Member(5);
		UnsignedInt3Member g = new UnsignedInt3Member(6);
		UnsignedInt3Member h = new UnsignedInt3Member(7);

		assertTrue(G.UINT3.isLess().call(a, b));
		assertFalse(G.UINT3.isGreater().call(a, b));
		
		assertTrue(G.UINT3.isLess().call(b, c));
		assertFalse(G.UINT3.isGreater().call(b, c));
		
		assertTrue(G.UINT3.isLess().call(c, d));
		assertFalse(G.UINT3.isGreater().call(c, d));
		
		assertTrue(G.UINT3.isLess().call(d, e));
		assertFalse(G.UINT3.isGreater().call(d, e));
		
		assertTrue(G.UINT3.isLess().call(e, f));
		assertFalse(G.UINT3.isGreater().call(e, f));
		
		assertTrue(G.UINT3.isLess().call(f, g));
		assertFalse(G.UINT3.isGreater().call(f, g));
		
		assertTrue(G.UINT3.isLess().call(g, h));
		assertFalse(G.UINT3.isGreater().call(g, h));
	}
	
	@Test
	public void int4() {
		SignedInt4Member a = new SignedInt4Member(-8);
		SignedInt4Member b = new SignedInt4Member(-7);
		SignedInt4Member c = new SignedInt4Member(-1);
		SignedInt4Member d = new SignedInt4Member(0);
		SignedInt4Member e = new SignedInt4Member(1);
		SignedInt4Member f = new SignedInt4Member(6);
		SignedInt4Member g = new SignedInt4Member(7);
		
		assertTrue(G.INT4.isLess().call(a, b));
		assertFalse(G.INT4.isGreater().call(a, b));
		
		assertTrue(G.INT4.isLess().call(b, c));
		assertFalse(G.INT4.isGreater().call(b, c));
		
		assertTrue(G.INT4.isLess().call(c, d));
		assertFalse(G.INT4.isGreater().call(c, d));
		
		assertTrue(G.INT4.isLess().call(d, e));
		assertFalse(G.INT4.isGreater().call(d, e));
		
		assertTrue(G.INT4.isLess().call(e, f));
		assertFalse(G.INT4.isGreater().call(e, f));
		
		assertTrue(G.INT4.isLess().call(f, g));
		assertFalse(G.INT4.isGreater().call(f, g));
	}
	
	@Test
	public void uint4() {
		UnsignedInt4Member a = new UnsignedInt4Member(0);
		UnsignedInt4Member b = new UnsignedInt4Member(1);
		UnsignedInt4Member c = new UnsignedInt4Member(7);
		UnsignedInt4Member d = new UnsignedInt4Member(8);
		UnsignedInt4Member e = new UnsignedInt4Member(9);
		UnsignedInt4Member f = new UnsignedInt4Member(14);
		UnsignedInt4Member g = new UnsignedInt4Member(15);
		
		assertTrue(G.UINT4.isLess().call(a, b));
		assertFalse(G.UINT4.isGreater().call(a, b));
		
		assertTrue(G.UINT4.isLess().call(b, c));
		assertFalse(G.UINT4.isGreater().call(b, c));
		
		assertTrue(G.UINT4.isLess().call(c, d));
		assertFalse(G.UINT4.isGreater().call(c, d));
		
		assertTrue(G.UINT4.isLess().call(d, e));
		assertFalse(G.UINT4.isGreater().call(d, e));
		
		assertTrue(G.UINT4.isLess().call(e, f));
		assertFalse(G.UINT4.isGreater().call(e, f));
		
		assertTrue(G.UINT4.isLess().call(f, g));
		assertFalse(G.UINT4.isGreater().call(f, g));
	}
	
	@Test
	public void int5() {
		SignedInt5Member a = new SignedInt5Member(-16);
		SignedInt5Member b = new SignedInt5Member(-15);
		SignedInt5Member c = new SignedInt5Member(-1);
		SignedInt5Member d = new SignedInt5Member(0);
		SignedInt5Member e = new SignedInt5Member(1);
		SignedInt5Member f = new SignedInt5Member(14);
		SignedInt5Member g = new SignedInt5Member(15);
		
		assertTrue(G.INT5.isLess().call(a, b));
		assertFalse(G.INT5.isGreater().call(a, b));
		
		assertTrue(G.INT5.isLess().call(b, c));
		assertFalse(G.INT5.isGreater().call(b, c));
		
		assertTrue(G.INT5.isLess().call(c, d));
		assertFalse(G.INT5.isGreater().call(c, d));
		
		assertTrue(G.INT5.isLess().call(d, e));
		assertFalse(G.INT5.isGreater().call(d, e));
		
		assertTrue(G.INT5.isLess().call(e, f));
		assertFalse(G.INT5.isGreater().call(e, f));
		
		assertTrue(G.INT5.isLess().call(f, g));
		assertFalse(G.INT5.isGreater().call(f, g));
	}
	
	@Test
	public void uint5() {
		UnsignedInt5Member a = new UnsignedInt5Member(0);
		UnsignedInt5Member b = new UnsignedInt5Member(1);
		UnsignedInt5Member c = new UnsignedInt5Member(15);
		UnsignedInt5Member d = new UnsignedInt5Member(16);
		UnsignedInt5Member e = new UnsignedInt5Member(17);
		UnsignedInt5Member f = new UnsignedInt5Member(30);
		UnsignedInt5Member g = new UnsignedInt5Member(31);
		
		assertTrue(G.UINT5.isLess().call(a, b));
		assertFalse(G.UINT5.isGreater().call(a, b));
		
		assertTrue(G.UINT5.isLess().call(b, c));
		assertFalse(G.UINT5.isGreater().call(b, c));
		
		assertTrue(G.UINT5.isLess().call(c, d));
		assertFalse(G.UINT5.isGreater().call(c, d));
		
		assertTrue(G.UINT5.isLess().call(d, e));
		assertFalse(G.UINT5.isGreater().call(d, e));
		
		assertTrue(G.UINT5.isLess().call(e, f));
		assertFalse(G.UINT5.isGreater().call(e, f));
		
		assertTrue(G.UINT5.isLess().call(f, g));
		assertFalse(G.UINT5.isGreater().call(f, g));
	}
	
	@Test
	public void int6() {
		SignedInt6Member a = new SignedInt6Member(-32);
		SignedInt6Member b = new SignedInt6Member(-31);
		SignedInt6Member c = new SignedInt6Member(-1);
		SignedInt6Member d = new SignedInt6Member(0);
		SignedInt6Member e = new SignedInt6Member(1);
		SignedInt6Member f = new SignedInt6Member(30);
		SignedInt6Member g = new SignedInt6Member(31);
		
		assertTrue(G.INT6.isLess().call(a, b));
		assertFalse(G.INT6.isGreater().call(a, b));
		
		assertTrue(G.INT6.isLess().call(b, c));
		assertFalse(G.INT6.isGreater().call(b, c));
		
		assertTrue(G.INT6.isLess().call(c, d));
		assertFalse(G.INT6.isGreater().call(c, d));
		
		assertTrue(G.INT6.isLess().call(d, e));
		assertFalse(G.INT6.isGreater().call(d, e));
		
		assertTrue(G.INT6.isLess().call(e, f));
		assertFalse(G.INT6.isGreater().call(e, f));
		
		assertTrue(G.INT6.isLess().call(f, g));
		assertFalse(G.INT6.isGreater().call(f, g));
	}
	
	@Test
	public void uint6() {
		UnsignedInt6Member a = new UnsignedInt6Member(0);
		UnsignedInt6Member b = new UnsignedInt6Member(1);
		UnsignedInt6Member c = new UnsignedInt6Member(31);
		UnsignedInt6Member d = new UnsignedInt6Member(32);
		UnsignedInt6Member e = new UnsignedInt6Member(33);
		UnsignedInt6Member f = new UnsignedInt6Member(62);
		UnsignedInt6Member g = new UnsignedInt6Member(63);
		
		assertTrue(G.UINT6.isLess().call(a, b));
		assertFalse(G.UINT6.isGreater().call(a, b));
		
		assertTrue(G.UINT6.isLess().call(b, c));
		assertFalse(G.UINT6.isGreater().call(b, c));
		
		assertTrue(G.UINT6.isLess().call(c, d));
		assertFalse(G.UINT6.isGreater().call(c, d));
		
		assertTrue(G.UINT6.isLess().call(d, e));
		assertFalse(G.UINT6.isGreater().call(d, e));
		
		assertTrue(G.UINT6.isLess().call(e, f));
		assertFalse(G.UINT6.isGreater().call(e, f));
		
		assertTrue(G.UINT6.isLess().call(f, g));
		assertFalse(G.UINT6.isGreater().call(f, g));
	}
	
	@Test
	public void int7() {
		SignedInt7Member a = new SignedInt7Member(-64);
		SignedInt7Member b = new SignedInt7Member(-63);
		SignedInt7Member c = new SignedInt7Member(-1);
		SignedInt7Member d = new SignedInt7Member(0);
		SignedInt7Member e = new SignedInt7Member(1);
		SignedInt7Member f = new SignedInt7Member(62);
		SignedInt7Member g = new SignedInt7Member(63);
		
		assertTrue(G.INT7.isLess().call(a, b));
		assertFalse(G.INT7.isGreater().call(a, b));
		
		assertTrue(G.INT7.isLess().call(b, c));
		assertFalse(G.INT7.isGreater().call(b, c));
		
		assertTrue(G.INT7.isLess().call(c, d));
		assertFalse(G.INT7.isGreater().call(c, d));
		
		assertTrue(G.INT7.isLess().call(d, e));
		assertFalse(G.INT7.isGreater().call(d, e));
		
		assertTrue(G.INT7.isLess().call(e, f));
		assertFalse(G.INT7.isGreater().call(e, f));
		
		assertTrue(G.INT7.isLess().call(f, g));
		assertFalse(G.INT7.isGreater().call(f, g));
	}
	
	@Test
	public void uint7() {
		UnsignedInt7Member a = new UnsignedInt7Member(0);
		UnsignedInt7Member b = new UnsignedInt7Member(1);
		UnsignedInt7Member c = new UnsignedInt7Member(63);
		UnsignedInt7Member d = new UnsignedInt7Member(64);
		UnsignedInt7Member e = new UnsignedInt7Member(65);
		UnsignedInt7Member f = new UnsignedInt7Member(123);
		UnsignedInt7Member g = new UnsignedInt7Member(127);
		
		assertTrue(G.UINT7.isLess().call(a, b));
		assertFalse(G.UINT7.isGreater().call(a, b));
		
		assertTrue(G.UINT7.isLess().call(b, c));
		assertFalse(G.UINT7.isGreater().call(b, c));
		
		assertTrue(G.UINT7.isLess().call(c, d));
		assertFalse(G.UINT7.isGreater().call(c, d));
		
		assertTrue(G.UINT7.isLess().call(d, e));
		assertFalse(G.UINT7.isGreater().call(d, e));
		
		assertTrue(G.UINT7.isLess().call(e, f));
		assertFalse(G.UINT7.isGreater().call(e, f));
		
		assertTrue(G.UINT7.isLess().call(f, g));
		assertFalse(G.UINT7.isGreater().call(f, g));
	}
	
	@Test
	public void int8() {
		SignedInt8Member a = new SignedInt8Member(Byte.MIN_VALUE);
		SignedInt8Member b = new SignedInt8Member(Byte.MIN_VALUE+1);
		SignedInt8Member c = new SignedInt8Member(-1);
		SignedInt8Member d = new SignedInt8Member(0);
		SignedInt8Member e = new SignedInt8Member(1);
		SignedInt8Member f = new SignedInt8Member(Byte.MAX_VALUE-1);
		SignedInt8Member g = new SignedInt8Member(Byte.MAX_VALUE);
		
		assertTrue(G.INT8.isLess().call(a, b));
		assertFalse(G.INT8.isGreater().call(a, b));
		
		assertTrue(G.INT8.isLess().call(b, c));
		assertFalse(G.INT8.isGreater().call(b, c));
		
		assertTrue(G.INT8.isLess().call(c, d));
		assertFalse(G.INT8.isGreater().call(c, d));
		
		assertTrue(G.INT8.isLess().call(d, e));
		assertFalse(G.INT8.isGreater().call(d, e));
		
		assertTrue(G.INT8.isLess().call(e, f));
		assertFalse(G.INT8.isGreater().call(e, f));
		
		assertTrue(G.INT8.isLess().call(f, g));
		assertFalse(G.INT8.isGreater().call(f, g));
	}

	@Test
	public void uint8() {
		UnsignedInt8Member a = new UnsignedInt8Member(0);
		UnsignedInt8Member b = new UnsignedInt8Member(1);
		UnsignedInt8Member c = new UnsignedInt8Member(127);
		UnsignedInt8Member d = new UnsignedInt8Member(128);
		UnsignedInt8Member e = new UnsignedInt8Member(129);
		UnsignedInt8Member f = new UnsignedInt8Member(254);
		UnsignedInt8Member g = new UnsignedInt8Member(255);
		
		assertTrue(G.UINT8.isLess().call(a, b));
		assertFalse(G.UINT8.isGreater().call(a, b));
		
		assertTrue(G.UINT8.isLess().call(b, c));
		assertFalse(G.UINT8.isGreater().call(b, c));
		
		assertTrue(G.UINT8.isLess().call(c, d));
		assertFalse(G.UINT8.isGreater().call(c, d));
		
		assertTrue(G.UINT8.isLess().call(d, e));
		assertFalse(G.UINT8.isGreater().call(d, e));
		
		assertTrue(G.UINT8.isLess().call(e, f));
		assertFalse(G.UINT8.isGreater().call(e, f));
		
		assertTrue(G.UINT8.isLess().call(f, g));
		assertFalse(G.UINT8.isGreater().call(f, g));
	}

	@Test
	public void int9() {
		SignedInt9Member a = new SignedInt9Member(-256);
		SignedInt9Member b = new SignedInt9Member(-255);
		SignedInt9Member c = new SignedInt9Member(-1);
		SignedInt9Member d = new SignedInt9Member(0);
		SignedInt9Member e = new SignedInt9Member(1);
		SignedInt9Member f = new SignedInt9Member(254);
		SignedInt9Member g = new SignedInt9Member(255);
		
		assertTrue(G.INT9.isLess().call(a, b));
		assertFalse(G.INT9.isGreater().call(a, b));
		
		assertTrue(G.INT9.isLess().call(b, c));
		assertFalse(G.INT9.isGreater().call(b, c));
		
		assertTrue(G.INT9.isLess().call(c, d));
		assertFalse(G.INT9.isGreater().call(c, d));
		
		assertTrue(G.INT9.isLess().call(d, e));
		assertFalse(G.INT9.isGreater().call(d, e));
		
		assertTrue(G.INT9.isLess().call(e, f));
		assertFalse(G.INT9.isGreater().call(e, f));
		
		assertTrue(G.INT9.isLess().call(f, g));
		assertFalse(G.INT9.isGreater().call(f, g));
	}

	@Test
	public void uint9() {
		UnsignedInt9Member a = new UnsignedInt9Member(0);
		UnsignedInt9Member b = new UnsignedInt9Member(1);
		UnsignedInt9Member c = new UnsignedInt9Member(255);
		UnsignedInt9Member d = new UnsignedInt9Member(256);
		UnsignedInt9Member e = new UnsignedInt9Member(257);
		UnsignedInt9Member f = new UnsignedInt9Member(510);
		UnsignedInt9Member g = new UnsignedInt9Member(511);
		
		assertTrue(G.UINT9.isLess().call(a, b));
		assertFalse(G.UINT9.isGreater().call(a, b));
		
		assertTrue(G.UINT9.isLess().call(b, c));
		assertFalse(G.UINT9.isGreater().call(b, c));
		
		assertTrue(G.UINT9.isLess().call(c, d));
		assertFalse(G.UINT9.isGreater().call(c, d));
		
		assertTrue(G.UINT9.isLess().call(d, e));
		assertFalse(G.UINT9.isGreater().call(d, e));
		
		assertTrue(G.UINT9.isLess().call(e, f));
		assertFalse(G.UINT9.isGreater().call(e, f));
		
		assertTrue(G.UINT9.isLess().call(f, g));
		assertFalse(G.UINT9.isGreater().call(f, g));
	}

	@Test
	public void int10() {
		SignedInt10Member a = new SignedInt10Member(-512);
		SignedInt10Member b = new SignedInt10Member(-511);
		SignedInt10Member c = new SignedInt10Member(-1);
		SignedInt10Member d = new SignedInt10Member(0);
		SignedInt10Member e = new SignedInt10Member(1);
		SignedInt10Member f = new SignedInt10Member(510);
		SignedInt10Member g = new SignedInt10Member(511);
		
		assertTrue(G.INT10.isLess().call(a, b));
		assertFalse(G.INT10.isGreater().call(a, b));
		
		assertTrue(G.INT10.isLess().call(b, c));
		assertFalse(G.INT10.isGreater().call(b, c));
		
		assertTrue(G.INT10.isLess().call(c, d));
		assertFalse(G.INT10.isGreater().call(c, d));
		
		assertTrue(G.INT10.isLess().call(d, e));
		assertFalse(G.INT10.isGreater().call(d, e));
		
		assertTrue(G.INT10.isLess().call(e, f));
		assertFalse(G.INT10.isGreater().call(e, f));
		
		assertTrue(G.INT10.isLess().call(f, g));
		assertFalse(G.INT10.isGreater().call(f, g));
	}

	@Test
	public void uint10() {
		UnsignedInt10Member a = new UnsignedInt10Member(0);
		UnsignedInt10Member b = new UnsignedInt10Member(1);
		UnsignedInt10Member c = new UnsignedInt10Member(511);
		UnsignedInt10Member d = new UnsignedInt10Member(512);
		UnsignedInt10Member e = new UnsignedInt10Member(513);
		UnsignedInt10Member f = new UnsignedInt10Member(1022);
		UnsignedInt10Member g = new UnsignedInt10Member(1023);
		
		assertTrue(G.UINT10.isLess().call(a, b));
		assertFalse(G.UINT10.isGreater().call(a, b));
		
		assertTrue(G.UINT10.isLess().call(b, c));
		assertFalse(G.UINT10.isGreater().call(b, c));
		
		assertTrue(G.UINT10.isLess().call(c, d));
		assertFalse(G.UINT10.isGreater().call(c, d));
		
		assertTrue(G.UINT10.isLess().call(d, e));
		assertFalse(G.UINT10.isGreater().call(d, e));
		
		assertTrue(G.UINT10.isLess().call(e, f));
		assertFalse(G.UINT10.isGreater().call(e, f));
		
		assertTrue(G.UINT10.isLess().call(f, g));
		assertFalse(G.UINT10.isGreater().call(f, g));
	}

	@Test
	public void int11() {
		SignedInt11Member a = new SignedInt11Member(-1024);
		SignedInt11Member b = new SignedInt11Member(-1023);
		SignedInt11Member c = new SignedInt11Member(-1);
		SignedInt11Member d = new SignedInt11Member(0);
		SignedInt11Member e = new SignedInt11Member(1);
		SignedInt11Member f = new SignedInt11Member(1022);
		SignedInt11Member g = new SignedInt11Member(1023);
		
		assertTrue(G.INT11.isLess().call(a, b));
		assertFalse(G.INT11.isGreater().call(a, b));
		
		assertTrue(G.INT11.isLess().call(b, c));
		assertFalse(G.INT11.isGreater().call(b, c));
		
		assertTrue(G.INT11.isLess().call(c, d));
		assertFalse(G.INT11.isGreater().call(c, d));
		
		assertTrue(G.INT11.isLess().call(d, e));
		assertFalse(G.INT11.isGreater().call(d, e));
		
		assertTrue(G.INT11.isLess().call(e, f));
		assertFalse(G.INT11.isGreater().call(e, f));
		
		assertTrue(G.INT11.isLess().call(f, g));
		assertFalse(G.INT11.isGreater().call(f, g));
	}

	@Test
	public void uint11() {
		UnsignedInt11Member a = new UnsignedInt11Member(0);
		UnsignedInt11Member b = new UnsignedInt11Member(1);
		UnsignedInt11Member c = new UnsignedInt11Member(1023);
		UnsignedInt11Member d = new UnsignedInt11Member(1024);
		UnsignedInt11Member e = new UnsignedInt11Member(1025);
		UnsignedInt11Member f = new UnsignedInt11Member(2046);
		UnsignedInt11Member g = new UnsignedInt11Member(2047);
		
		assertTrue(G.UINT11.isLess().call(a, b));
		assertFalse(G.UINT11.isGreater().call(a, b));
		
		assertTrue(G.UINT11.isLess().call(b, c));
		assertFalse(G.UINT11.isGreater().call(b, c));
		
		assertTrue(G.UINT11.isLess().call(c, d));
		assertFalse(G.UINT11.isGreater().call(c, d));
		
		assertTrue(G.UINT11.isLess().call(d, e));
		assertFalse(G.UINT11.isGreater().call(d, e));
		
		assertTrue(G.UINT11.isLess().call(e, f));
		assertFalse(G.UINT11.isGreater().call(e, f));
		
		assertTrue(G.UINT11.isLess().call(f, g));
		assertFalse(G.UINT11.isGreater().call(f, g));
	}

	@Test
	public void int12() {
		SignedInt12Member a = new SignedInt12Member(-2048);
		SignedInt12Member b = new SignedInt12Member(-2047);
		SignedInt12Member c = new SignedInt12Member(-1);
		SignedInt12Member d = new SignedInt12Member(0);
		SignedInt12Member e = new SignedInt12Member(1);
		SignedInt12Member f = new SignedInt12Member(2046);
		SignedInt12Member g = new SignedInt12Member(2047);
		
		assertTrue(G.INT12.isLess().call(a, b));
		assertFalse(G.INT12.isGreater().call(a, b));
		
		assertTrue(G.INT12.isLess().call(b, c));
		assertFalse(G.INT12.isGreater().call(b, c));
		
		assertTrue(G.INT12.isLess().call(c, d));
		assertFalse(G.INT12.isGreater().call(c, d));
		
		assertTrue(G.INT12.isLess().call(d, e));
		assertFalse(G.INT12.isGreater().call(d, e));
		
		assertTrue(G.INT12.isLess().call(e, f));
		assertFalse(G.INT12.isGreater().call(e, f));
		
		assertTrue(G.INT12.isLess().call(f, g));
		assertFalse(G.INT12.isGreater().call(f, g));
	}
	
	@Test
	public void uint12() {
		UnsignedInt12Member a = new UnsignedInt12Member(0);
		UnsignedInt12Member b = new UnsignedInt12Member(1);
		UnsignedInt12Member c = new UnsignedInt12Member(2047);
		UnsignedInt12Member d = new UnsignedInt12Member(2048);
		UnsignedInt12Member e = new UnsignedInt12Member(2049);
		UnsignedInt12Member f = new UnsignedInt12Member(4094);
		UnsignedInt12Member g = new UnsignedInt12Member(4095);
		
		assertTrue(G.UINT12.isLess().call(a, b));
		assertFalse(G.UINT12.isGreater().call(a, b));
		
		assertTrue(G.UINT12.isLess().call(b, c));
		assertFalse(G.UINT12.isGreater().call(b, c));
		
		assertTrue(G.UINT12.isLess().call(c, d));
		assertFalse(G.UINT12.isGreater().call(c, d));
		
		assertTrue(G.UINT12.isLess().call(d, e));
		assertFalse(G.UINT12.isGreater().call(d, e));
		
		assertTrue(G.UINT12.isLess().call(e, f));
		assertFalse(G.UINT12.isGreater().call(e, f));
		
		assertTrue(G.UINT12.isLess().call(f, g));
		assertFalse(G.UINT12.isGreater().call(f, g));
	}
	
	@Test
	public void int13() {
		SignedInt13Member a = new SignedInt13Member(-4096);
		SignedInt13Member b = new SignedInt13Member(-4095);
		SignedInt13Member c = new SignedInt13Member(-1);
		SignedInt13Member d = new SignedInt13Member(0);
		SignedInt13Member e = new SignedInt13Member(1);
		SignedInt13Member f = new SignedInt13Member(4094);
		SignedInt13Member g = new SignedInt13Member(4095);
		
		assertTrue(G.INT13.isLess().call(a, b));
		assertFalse(G.INT13.isGreater().call(a, b));
		
		assertTrue(G.INT13.isLess().call(b, c));
		assertFalse(G.INT13.isGreater().call(b, c));
		
		assertTrue(G.INT13.isLess().call(c, d));
		assertFalse(G.INT13.isGreater().call(c, d));
		
		assertTrue(G.INT13.isLess().call(d, e));
		assertFalse(G.INT13.isGreater().call(d, e));
		
		assertTrue(G.INT13.isLess().call(e, f));
		assertFalse(G.INT13.isGreater().call(e, f));
		
		assertTrue(G.INT13.isLess().call(f, g));
		assertFalse(G.INT13.isGreater().call(f, g));
	}

	@Test
	public void uint13() {
		UnsignedInt13Member a = new UnsignedInt13Member(0);
		UnsignedInt13Member b = new UnsignedInt13Member(1);
		UnsignedInt13Member c = new UnsignedInt13Member(4095);
		UnsignedInt13Member d = new UnsignedInt13Member(4096);
		UnsignedInt13Member e = new UnsignedInt13Member(4097);
		UnsignedInt13Member f = new UnsignedInt13Member(8190);
		UnsignedInt13Member g = new UnsignedInt13Member(8191);
		
		assertTrue(G.UINT13.isLess().call(a, b));
		assertFalse(G.UINT13.isGreater().call(a, b));
		
		assertTrue(G.UINT13.isLess().call(b, c));
		assertFalse(G.UINT13.isGreater().call(b, c));
		
		assertTrue(G.UINT13.isLess().call(c, d));
		assertFalse(G.UINT13.isGreater().call(c, d));
		
		assertTrue(G.UINT13.isLess().call(d, e));
		assertFalse(G.UINT13.isGreater().call(d, e));
		
		assertTrue(G.UINT13.isLess().call(e, f));
		assertFalse(G.UINT13.isGreater().call(e, f));
		
		assertTrue(G.UINT13.isLess().call(f, g));
		assertFalse(G.UINT13.isGreater().call(f, g));
	}

	@Test
	public void int14() {
		SignedInt14Member a = new SignedInt14Member(-8192);
		SignedInt14Member b = new SignedInt14Member(-8191);
		SignedInt14Member c = new SignedInt14Member(-1);
		SignedInt14Member d = new SignedInt14Member(0);
		SignedInt14Member e = new SignedInt14Member(1);
		SignedInt14Member f = new SignedInt14Member(8190);
		SignedInt14Member g = new SignedInt14Member(8191);
		
		assertTrue(G.INT14.isLess().call(a, b));
		assertFalse(G.INT14.isGreater().call(a, b));
		
		assertTrue(G.INT14.isLess().call(b, c));
		assertFalse(G.INT14.isGreater().call(b, c));
		
		assertTrue(G.INT14.isLess().call(c, d));
		assertFalse(G.INT14.isGreater().call(c, d));
		
		assertTrue(G.INT14.isLess().call(d, e));
		assertFalse(G.INT14.isGreater().call(d, e));
		
		assertTrue(G.INT14.isLess().call(e, f));
		assertFalse(G.INT14.isGreater().call(e, f));
		
		assertTrue(G.INT14.isLess().call(f, g));
		assertFalse(G.INT14.isGreater().call(f, g));
	}

	@Test
	public void uint14() {
		UnsignedInt14Member a = new UnsignedInt14Member(0);
		UnsignedInt14Member b = new UnsignedInt14Member(1);
		UnsignedInt14Member c = new UnsignedInt14Member(8191);
		UnsignedInt14Member d = new UnsignedInt14Member(8192);
		UnsignedInt14Member e = new UnsignedInt14Member(8193);
		UnsignedInt14Member f = new UnsignedInt14Member(16382);
		UnsignedInt14Member g = new UnsignedInt14Member(16383);
		
		assertTrue(G.UINT14.isLess().call(a, b));
		assertFalse(G.UINT14.isGreater().call(a, b));
		
		assertTrue(G.UINT14.isLess().call(b, c));
		assertFalse(G.UINT14.isGreater().call(b, c));
		
		assertTrue(G.UINT14.isLess().call(c, d));
		assertFalse(G.UINT14.isGreater().call(c, d));
		
		assertTrue(G.UINT14.isLess().call(d, e));
		assertFalse(G.UINT14.isGreater().call(d, e));
		
		assertTrue(G.UINT14.isLess().call(e, f));
		assertFalse(G.UINT14.isGreater().call(e, f));
		
		assertTrue(G.UINT14.isLess().call(f, g));
		assertFalse(G.UINT14.isGreater().call(f, g));
	}

	@Test
	public void int15() {
		SignedInt15Member a = new SignedInt15Member(-16384);
		SignedInt15Member b = new SignedInt15Member(-16383);
		SignedInt15Member c = new SignedInt15Member(-1);
		SignedInt15Member d = new SignedInt15Member(0);
		SignedInt15Member e = new SignedInt15Member(1);
		SignedInt15Member f = new SignedInt15Member(16382);
		SignedInt15Member g = new SignedInt15Member(16383);
		
		assertTrue(G.INT15.isLess().call(a, b));
		assertFalse(G.INT15.isGreater().call(a, b));
		
		assertTrue(G.INT15.isLess().call(b, c));
		assertFalse(G.INT15.isGreater().call(b, c));
		
		assertTrue(G.INT15.isLess().call(c, d));
		assertFalse(G.INT15.isGreater().call(c, d));
		
		assertTrue(G.INT15.isLess().call(d, e));
		assertFalse(G.INT15.isGreater().call(d, e));
		
		assertTrue(G.INT15.isLess().call(e, f));
		assertFalse(G.INT15.isGreater().call(e, f));
		
		assertTrue(G.INT15.isLess().call(f, g));
		assertFalse(G.INT15.isGreater().call(f, g));
	}

	@Test
	public void uint15() {
		UnsignedInt15Member a = new UnsignedInt15Member(0);
		UnsignedInt15Member b = new UnsignedInt15Member(1);
		UnsignedInt15Member c = new UnsignedInt15Member(16383);
		UnsignedInt15Member d = new UnsignedInt15Member(16384);
		UnsignedInt15Member e = new UnsignedInt15Member(16385);
		UnsignedInt15Member f = new UnsignedInt15Member(32766);
		UnsignedInt15Member g = new UnsignedInt15Member(32767);
		
		assertTrue(G.UINT15.isLess().call(a, b));
		assertFalse(G.UINT15.isGreater().call(a, b));
		
		assertTrue(G.UINT15.isLess().call(b, c));
		assertFalse(G.UINT15.isGreater().call(b, c));
		
		assertTrue(G.UINT15.isLess().call(c, d));
		assertFalse(G.UINT15.isGreater().call(c, d));
		
		assertTrue(G.UINT15.isLess().call(d, e));
		assertFalse(G.UINT15.isGreater().call(d, e));
		
		assertTrue(G.UINT15.isLess().call(e, f));
		assertFalse(G.UINT15.isGreater().call(e, f));
		
		assertTrue(G.UINT15.isLess().call(f, g));
		assertFalse(G.UINT15.isGreater().call(f, g));
	}

	@Test
	public void int16() {
		SignedInt16Member a = new SignedInt16Member(Short.MIN_VALUE);
		SignedInt16Member b = new SignedInt16Member(Short.MIN_VALUE+1);
		SignedInt16Member c = new SignedInt16Member(-1);
		SignedInt16Member d = new SignedInt16Member(0);
		SignedInt16Member e = new SignedInt16Member(1);
		SignedInt16Member f = new SignedInt16Member(Short.MAX_VALUE-1);
		SignedInt16Member g = new SignedInt16Member(Short.MAX_VALUE);
		
		assertTrue(G.INT16.isLess().call(a, b));
		assertFalse(G.INT16.isGreater().call(a, b));
		
		assertTrue(G.INT16.isLess().call(b, c));
		assertFalse(G.INT16.isGreater().call(b, c));
		
		assertTrue(G.INT16.isLess().call(c, d));
		assertFalse(G.INT16.isGreater().call(c, d));
		
		assertTrue(G.INT16.isLess().call(d, e));
		assertFalse(G.INT16.isGreater().call(d, e));
		
		assertTrue(G.INT16.isLess().call(e, f));
		assertFalse(G.INT16.isGreater().call(e, f));
		
		assertTrue(G.INT16.isLess().call(f, g));
		assertFalse(G.INT16.isGreater().call(f, g));
	}

	@Test
	public void uint16() {
		UnsignedInt16Member a = new UnsignedInt16Member(0);
		UnsignedInt16Member b = new UnsignedInt16Member(1);
		UnsignedInt16Member c = new UnsignedInt16Member(32767);
		UnsignedInt16Member d = new UnsignedInt16Member(32768);
		UnsignedInt16Member e = new UnsignedInt16Member(32769);
		UnsignedInt16Member f = new UnsignedInt16Member(65534);
		UnsignedInt16Member g = new UnsignedInt16Member(65535);
		
		assertTrue(G.UINT16.isLess().call(a, b));
		assertFalse(G.UINT16.isGreater().call(a, b));
		
		assertTrue(G.UINT16.isLess().call(b, c));
		assertFalse(G.UINT16.isGreater().call(b, c));
		
		assertTrue(G.UINT16.isLess().call(c, d));
		assertFalse(G.UINT16.isGreater().call(c, d));
		
		assertTrue(G.UINT16.isLess().call(d, e));
		assertFalse(G.UINT16.isGreater().call(d, e));
		
		assertTrue(G.UINT16.isLess().call(e, f));
		assertFalse(G.UINT16.isGreater().call(e, f));
		
		assertTrue(G.UINT16.isLess().call(f, g));
		assertFalse(G.UINT16.isGreater().call(f, g));
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
		
		assertTrue(G.INT32.isLess().call(a, b));
		assertFalse(G.INT32.isGreater().call(a, b));
		
		assertTrue(G.INT32.isLess().call(b, c));
		assertFalse(G.INT32.isGreater().call(b, c));
		
		assertTrue(G.INT32.isLess().call(c, d));
		assertFalse(G.INT32.isGreater().call(c, d));
		
		assertTrue(G.INT32.isLess().call(d, e));
		assertFalse(G.INT32.isGreater().call(d, e));
		
		assertTrue(G.INT32.isLess().call(e, f));
		assertFalse(G.INT32.isGreater().call(e, f));
		
		assertTrue(G.INT32.isLess().call(f, g));
		assertFalse(G.INT32.isGreater().call(f, g));
	}

	@Test
	public void uint32() {
		UnsignedInt32Member a = new UnsignedInt32Member(0);
		UnsignedInt32Member b = new UnsignedInt32Member(1);
		UnsignedInt32Member c = new UnsignedInt32Member(0x7ffffffeL);
		UnsignedInt32Member d = new UnsignedInt32Member(0x7fffffffL);
		UnsignedInt32Member e = new UnsignedInt32Member(0x80000000L);
		UnsignedInt32Member f = new UnsignedInt32Member(0xffffffffL-1);
		UnsignedInt32Member g = new UnsignedInt32Member(0xffffffffL);
		
		assertTrue(G.UINT32.isLess().call(a, b));
		assertFalse(G.UINT32.isGreater().call(a, b));
		
		assertTrue(G.UINT32.isLess().call(b, c));
		assertFalse(G.UINT32.isGreater().call(b, c));
		
		assertTrue(G.UINT32.isLess().call(c, d));
		assertFalse(G.UINT32.isGreater().call(c, d));
		
		assertTrue(G.UINT32.isLess().call(d, e));
		assertFalse(G.UINT32.isGreater().call(d, e));
		
		assertTrue(G.UINT32.isLess().call(e, f));
		assertFalse(G.UINT32.isGreater().call(e, f));
		
		assertTrue(G.UINT32.isLess().call(f, g));
		assertFalse(G.UINT32.isGreater().call(f, g));
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
		
		assertTrue(G.INT64.isLess().call(a, b));
		assertFalse(G.INT64.isGreater().call(a, b));
		
		assertTrue(G.INT64.isLess().call(b, c));
		assertFalse(G.INT64.isGreater().call(b, c));
		
		assertTrue(G.INT64.isLess().call(c, d));
		assertFalse(G.INT64.isGreater().call(c, d));
		
		assertTrue(G.INT64.isLess().call(d, e));
		assertFalse(G.INT64.isGreater().call(d, e));
		
		assertTrue(G.INT64.isLess().call(e, f));
		assertFalse(G.INT64.isGreater().call(e, f));
		
		assertTrue(G.INT64.isLess().call(f, g));
		assertFalse(G.INT64.isGreater().call(f, g));
	}

	@Test
	public void uint64() {
		UnsignedInt64Member a = new UnsignedInt64Member(BigInteger.ZERO);
		UnsignedInt64Member b = new UnsignedInt64Member(BigInteger.ONE);
		UnsignedInt64Member c = new UnsignedInt64Member(new BigInteger("7fffffffffffffff",16));
		UnsignedInt64Member d = new UnsignedInt64Member(new BigInteger("8000000000000000",16));
		UnsignedInt64Member e = new UnsignedInt64Member(new BigInteger("8000000000000001",16));
		UnsignedInt64Member f = new UnsignedInt64Member(new BigInteger("fffffffffffffffe",16));
		UnsignedInt64Member g = new UnsignedInt64Member(new BigInteger("ffffffffffffffff",16));
		
		assertTrue(G.UINT64.isLess().call(a, b));
		assertFalse(G.UINT64.isGreater().call(a, b));
		
		assertTrue(G.UINT64.isLess().call(b, c));
		assertFalse(G.UINT64.isGreater().call(b, c));
		
		assertTrue(G.UINT64.isLess().call(c, d));
		assertFalse(G.UINT64.isGreater().call(c, d));
		
		assertTrue(G.UINT64.isLess().call(d, e));
		assertFalse(G.UINT64.isGreater().call(d, e));
		
		assertTrue(G.UINT64.isLess().call(e, f));
		assertFalse(G.UINT64.isGreater().call(e, f));
		
		assertTrue(G.UINT64.isLess().call(f, g));
		assertFalse(G.UINT64.isGreater().call(f, g));
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
		
		assertTrue(G.INT128.isLess().call(a, b));
		assertFalse(G.INT128.isGreater().call(a, b));
		
		assertTrue(G.INT128.isLess().call(b, c));
		assertFalse(G.INT128.isGreater().call(b, c));
		
		assertTrue(G.INT128.isLess().call(c, d));
		assertFalse(G.INT128.isGreater().call(c, d));
		
		assertTrue(G.INT128.isLess().call(d, e));
		assertFalse(G.INT128.isGreater().call(d, e));
		
		assertTrue(G.INT128.isLess().call(e, f));
		assertFalse(G.INT128.isGreater().call(e, f));
		
		assertTrue(G.INT128.isLess().call(f, g));
		assertFalse(G.INT128.isGreater().call(f, g));
		*/
	}

	@Test
	public void uint128() {
		UnsignedInt128Member a = new UnsignedInt128Member(BigInteger.ZERO);
		UnsignedInt128Member b = new UnsignedInt128Member(BigInteger.ONE);
		UnsignedInt128Member c = new UnsignedInt128Member(new BigInteger("7fffffffffffffffffffffffffffffff",16));
		UnsignedInt128Member d = new UnsignedInt128Member(new BigInteger("80000000000000000000000000000000",16));
		UnsignedInt128Member e = new UnsignedInt128Member(new BigInteger("80000000000000000000000000000001",16));
		UnsignedInt128Member f = new UnsignedInt128Member(new BigInteger("fffffffffffffffffffffffffffffffe",16));
		UnsignedInt128Member g = new UnsignedInt128Member(new BigInteger("ffffffffffffffffffffffffffffffff",16));
		
		assertTrue(G.UINT128.isLess().call(a, b));
		assertFalse(G.UINT128.isGreater().call(a, b));
		
		assertTrue(G.UINT128.isLess().call(b, c));
		assertFalse(G.UINT128.isGreater().call(b, c));
		
		assertTrue(G.UINT128.isLess().call(c, d));
		assertFalse(G.UINT128.isGreater().call(c, d));
		
		assertTrue(G.UINT128.isLess().call(d, e));
		assertFalse(G.UINT128.isGreater().call(d, e));
		
		assertTrue(G.UINT128.isLess().call(e, f));
		assertFalse(G.UINT128.isGreater().call(e, f));
		
		assertTrue(G.UINT128.isLess().call(f, g));
		assertFalse(G.UINT128.isGreater().call(f, g));
	}
}
