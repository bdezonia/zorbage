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
package nom.bdezonia.zorbage.type.parse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.type.data.bool.BooleanMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBoolean {

	@Test
	public void test() {
		BooleanMember b;
		b = new BooleanMember("false");
		assertFalse(b.v());
		b = new BooleanMember("true");
		assertTrue(b.v());
		b = new BooleanMember("1");
		assertTrue(b.v());
		b = new BooleanMember("0");
		assertFalse(b.v());
		b = new BooleanMember("2");
		assertTrue(b.v());
		b = new BooleanMember("3.4");
		assertTrue(b.v());
		b = new BooleanMember("-3.4");
		assertTrue(b.v());
		b = new BooleanMember("+3.4");
		assertTrue(b.v());
		b = new BooleanMember("1.2e+05");
		assertTrue(b.v());
		b = new BooleanMember("(1,0)");
		assertTrue(b.v());
		b = new BooleanMember("(0,1)");
		assertFalse(b.v());
		b = new BooleanMember("[0]");
		assertFalse(b.v());
		b = new BooleanMember("[1]");
		assertTrue(b.v());
		b = new BooleanMember("[1,0,0]");
		assertTrue(b.v());
		b = new BooleanMember("[[1,0,0][0,0,0]]");
		assertTrue(b.v());
		b = new BooleanMember("[[[1,0,0][0,0,0]][[0,0,0][0,0,0]]]");
		assertTrue(b.v());
	}
}
