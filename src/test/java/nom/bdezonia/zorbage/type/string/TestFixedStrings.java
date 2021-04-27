/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.string;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Locale;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFixedStrings {

	@Test
	public void test1() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("def");
		FixedStringMember c = G.FSTRING.construct("       ");
		assertEquals("       ", c.v());
		G.FSTRING.add().call(a, b, c);
		assertEquals("abcdef", c.v());
	}

	@Test
	public void test2() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = G.FSTRING.construct("       ");
		assertEquals("       ", b.v());
		G.FSTRING.assign().call(a, b);
		assertEquals("abc", b.v());
	}

	@Test
	public void test3() {
		//FixedStringMember a = new FixedStringMember("abc");
		//asertEquals((char) 'a', (char) G.FSTRING.charAt().call(0, a));
		//assertEquals((char) 'b', (char) G.FSTRING.charAt().call(1, a));
		//assertEquals((char) 'c', (char) G.FSTRING.charAt().call(2, a));
	}

	@Test
	public void test4() {
		FixedStringMember a = new FixedStringMember("abc");
		assertEquals((int) 'a', (int) G.FSTRING.codePointAt().call(0, a));
		assertEquals((int) 'b', (int) G.FSTRING.codePointAt().call(1, a));
		assertEquals((int) 'c', (int) G.FSTRING.codePointAt().call(2, a));
	}

	@Test
	public void test5() {
		//FixedStringMember a = new FixedStringMember("abc");
		//assertEquals((int) 'a', (int) G.FSTRING.codePointBefore().call(1, a));
		//assertEquals((int) 'b', (int) G.FSTRING.codePointBefore().call(2, a));
	}

	@Test
	public void test6() {
		FixedStringMember a = new FixedStringMember("abc");
		assertEquals(3, (int) G.FSTRING.codePointCount().call(a));
	}

	@Test
	public void test7() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("def");
		assertTrue(G.FSTRING.compare().call(a, b) < 0);
		assertTrue(G.FSTRING.compare().call(b, a) > 0);
		assertTrue(G.FSTRING.compare().call(a, a) == 0);
		assertTrue(G.FSTRING.compare().call(b, b) == 0);
	}

	@Test
	public void test8() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("def");
		FixedStringMember c = G.FSTRING.construct("        ");
		assertEquals("        ", c.v());
		G.FSTRING.concat().call(a, b, c);
		assertEquals("abcdef", c.v());
	}

	@Test
	public void test9() {
		FixedStringMember a = G.FSTRING.construct();
		assertEquals("", a.v());
		FixedStringMember b = G.FSTRING.construct("floo");
		assertEquals("floo", b.v());
		FixedStringMember c = G.FSTRING.construct(b);
		assertEquals("floo", c.v());
	}

	@Test
	public void test10() {
		FixedStringMember a = G.FSTRING.construct("one two three four");
		FixedStringMember x = G.FSTRING.construct("three");
		FixedStringMember y = G.FSTRING.construct("five");
		assertTrue(G.FSTRING.contains().call(x, a));
		assertFalse(G.FSTRING.contains().call(y, a));
	}

	@Test
	public void test11() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = G.FSTRING.construct(a);
		FixedStringMember c = new FixedStringMember("def");
		assertTrue(G.FSTRING.contentEquals().call(b, a));
		assertFalse(G.FSTRING.contentEquals().call(c, a));
	}

	@Test
	public void test12() {
		FixedStringMember a = new FixedStringMember("abcdabbadabbadoo");
		FixedStringMember end1 = new FixedStringMember("doo");
		FixedStringMember end2 = new FixedStringMember("boo");
		assertTrue(G.FSTRING.endsWith().call(end1, a));
		assertFalse(G.FSTRING.endsWith().call(end2, a));
	}

	@Test
	public void test13() {
		byte[] fixed;
		FixedStringMember a = new FixedStringMember("Fred");
		fixed = "Fred".getBytes();
		assertArrayEquals(fixed, G.FSTRING.getBytes().call(a));
		fixed = "Fred".getBytes(Charset.forName("utf8"));
		assertArrayEquals(fixed, G.FSTRING.getBytesUsingCharset().call(Charset.forName("utf8"), a));
		try {
			fixed = "Fred".getBytes("utf8");
		} catch (Exception e) {
			fixed = new byte[0];
		}
		assertArrayEquals(fixed, G.FSTRING.getBytesUsingCharsetName().call("utf8", a));
	}

	@Test
	public void test14() {
		//FixedStringMember string = new FixedStringMember("123 456 78 9 10 11 12 456");
		//FixedStringMember piece1 = new FixedStringMember("456");
		//FixedStringMember piece2 = new FixedStringMember("789");
		//assertEquals(4, (int) G.FSTRING.indexOf().call(piece1, string));
		//assertEquals(-1, (int) G.FSTRING.indexOf().call(piece2, string));
	}

	@Test
	public void test15() {
		//FixedStringMember string = new FixedStringMember("123 456 78 9 10 11 12 456");
		//FixedStringMember piece1 = new FixedStringMember("456");
		//FixedStringMember piece2 = new FixedStringMember("789");
		//assertEquals(4, (int) G.FSTRING.indexOfFrom().call(piece1, 0, string));
		//assertEquals(22, (int) G.FSTRING.indexOfFrom().call(piece1, 5, string));
		//assertEquals(-1, (int) G.FSTRING.indexOfFrom().call(piece2, 0, string));
	}

	@Test
	public void test16() {
		//FixedStringMember string = new FixedStringMember("abc");
		//assertEquals(0, (int) G.FSTRING.indexOfCodePoint().call((int) 'a', string));
		//assertEquals(1, (int) G.FSTRING.indexOfCodePoint().call((int) 'b', string));
		//assertEquals(2, (int) G.FSTRING.indexOfCodePoint().call((int) 'c', string));
		//assertEquals(-1, (int) G.FSTRING.indexOfCodePoint().call((int) 'z', string));
	}

	@Test
	public void test17() {
		//FixedStringMember string = new FixedStringMember("a b c d e f e d s");
		//assertEquals(6, (int) G.FSTRING.indexOfCodePointFrom().call((int) 'd', 0, string));
		//assertEquals(14, (int) G.FSTRING.indexOfCodePointFrom().call((int) 'd', 7, string));
		//assertEquals(-1, (int) G.FSTRING.indexOfCodePointFrom().call((int) 'z', 0, string));
	}

	@Test
	public void test18() {
		FixedStringMember a = new FixedStringMember("ahddk3");
		assertFalse(G.FSTRING.isEmpty().call(a));
		assertFalse(G.FSTRING.isZero().call(a));
		G.FSTRING.zero().call(a);
		assertTrue(G.FSTRING.isEmpty().call(a));
		assertTrue(G.FSTRING.isZero().call(a));
	}

	@Test
	public void test19() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("ABC");
		assertTrue(G.FSTRING.isEqual().call(a,a));
		assertTrue(G.FSTRING.isEqual().call(b,b));
		assertFalse(G.FSTRING.isEqual().call(a,b));
		assertTrue(G.FSTRING.isEqualIgnoreCase().call(a,a));
		assertTrue(G.FSTRING.isEqualIgnoreCase().call(b,b));
		assertTrue(G.FSTRING.isEqualIgnoreCase().call(a,b));
		assertFalse(G.FSTRING.isNotEqual().call(a,a));
		assertFalse(G.FSTRING.isNotEqual().call(b,b));
		assertTrue(G.FSTRING.isNotEqual().call(a,b));
		assertFalse(G.FSTRING.isNotEqualIgnoreCase().call(a,a));
		assertFalse(G.FSTRING.isNotEqualIgnoreCase().call(b,b));
		assertFalse(G.FSTRING.isNotEqualIgnoreCase().call(a,b));
	}

	@Test
	public void test20() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("abc1");
		FixedStringMember c = new FixedStringMember("ABC");
		assertFalse(G.FSTRING.isGreater().call(a, b));
		assertTrue(G.FSTRING.isGreater().call(b, c));
		assertTrue(G.FSTRING.isGreater().call(a, c));
		assertFalse(G.FSTRING.isGreaterIgnoreCase().call(a, b));
		assertTrue(G.FSTRING.isGreaterIgnoreCase().call(b, c));
		assertFalse(G.FSTRING.isGreaterIgnoreCase().call(a, c));
		assertTrue(G.FSTRING.isGreater().call(b, a));
		assertFalse(G.FSTRING.isGreater().call(c, b));
		assertFalse(G.FSTRING.isGreater().call(c, a));
		assertTrue(G.FSTRING.isGreaterIgnoreCase().call(b, a));
		assertFalse(G.FSTRING.isGreaterIgnoreCase().call(c, b));
		assertFalse(G.FSTRING.isGreaterIgnoreCase().call(c, a));
	}

	@Test
	public void test21() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("abc1");
		FixedStringMember c = new FixedStringMember("ABC");
		assertFalse(G.FSTRING.isGreaterEqual().call(a, b));
		assertTrue(G.FSTRING.isGreaterEqual().call(b, c));
		assertTrue(G.FSTRING.isGreaterEqual().call(a, c));
		assertFalse(G.FSTRING.isGreaterEqualIgnoreCase().call(a, b));
		assertTrue(G.FSTRING.isGreaterEqualIgnoreCase().call(b, c));
		assertTrue(G.FSTRING.isGreaterEqualIgnoreCase().call(a, c));
		assertTrue(G.FSTRING.isGreaterEqual().call(b, a));
		assertFalse(G.FSTRING.isGreaterEqual().call(c, b));
		assertFalse(G.FSTRING.isGreaterEqual().call(c, a));
		assertTrue(G.FSTRING.isGreaterEqualIgnoreCase().call(b, a));
		assertFalse(G.FSTRING.isGreaterEqualIgnoreCase().call(c, b));
		assertTrue(G.FSTRING.isGreaterEqualIgnoreCase().call(c, a));
	}

	@Test
	public void test22() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("abc1");
		FixedStringMember c = new FixedStringMember("ABC");
		assertTrue(G.FSTRING.isLess().call(a, b));
		assertFalse(G.FSTRING.isLess().call(b, c));
		assertFalse(G.FSTRING.isLess().call(a, c));
		assertTrue(G.FSTRING.isLessIgnoreCase().call(a, b));
		assertFalse(G.FSTRING.isLessIgnoreCase().call(b, c));
		assertFalse(G.FSTRING.isLessIgnoreCase().call(a, c));
		assertFalse(G.FSTRING.isLess().call(b, a));
		assertTrue(G.FSTRING.isLess().call(c, b));
		assertTrue(G.FSTRING.isLess().call(c, a));
		assertFalse(G.FSTRING.isLessIgnoreCase().call(b, a));
		assertTrue(G.FSTRING.isLessIgnoreCase().call(c, b));
		assertFalse(G.FSTRING.isLessIgnoreCase().call(c, a));
	}

	@Test
	public void test23() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("abc1");
		FixedStringMember c = new FixedStringMember("ABC");
		assertTrue(G.FSTRING.isLessEqual().call(a, b));
		assertFalse(G.FSTRING.isLessEqual().call(b, c));
		assertFalse(G.FSTRING.isLessEqual().call(a, c));
		assertTrue(G.FSTRING.isLessEqualIgnoreCase().call(a, b));
		assertFalse(G.FSTRING.isLessEqualIgnoreCase().call(b, c));
		assertTrue(G.FSTRING.isLessEqualIgnoreCase().call(a, c));
		assertFalse(G.FSTRING.isLessEqual().call(b, a));
		assertTrue(G.FSTRING.isLessEqual().call(c, b));
		assertTrue(G.FSTRING.isLessEqual().call(c, a));
		assertFalse(G.FSTRING.isLessEqualIgnoreCase().call(b, a));
		assertTrue(G.FSTRING.isLessEqualIgnoreCase().call(c, b));
		assertTrue(G.FSTRING.isLessEqualIgnoreCase().call(c, a));
	}

	@Test
	public void test24() {
		FixedStringMember[] strings = new FixedStringMember[3];
		strings[0] = new FixedStringMember("abc");
		strings[1] = new FixedStringMember("def");
		strings[2] = new FixedStringMember("ghi");
		FixedStringMember delim = new FixedStringMember(",");
		FixedStringMember result = G.FSTRING.join().call(delim, strings);
		assertEquals("abc,def,ghi",result.v());
	}

	@Test
	public void test25() {
		//FixedStringMember a = new FixedStringMember("1 2 3 4 5 6 7 8 9 0 1 2 3 4");
		//FixedStringMember srch = new FixedStringMember("1");
		
		//assertEquals(20, (int) G.FSTRING.lastIndexOf().call(srch, a));

		//assertEquals(20, (int) G.FSTRING.lastIndexOfFrom().call(srch, 27, a));
		//assertEquals(0, (int) G.FSTRING.lastIndexOfFrom().call(srch, 19, a));
		
		//assertEquals(20, (int) G.FSTRING.lastIndexOfCodePoint().call((int) '1', a));

		//assertEquals(20, (int) G.FSTRING.lastIndexOfCodePointFrom().call((int) '1', 27, a));
		//assertEquals(0, (int) G.FSTRING.lastIndexOfCodePointFrom().call((int) '1', 19, a));
	}

	@Test
	public void test26() {
		FixedStringMember a = new FixedStringMember();
		SignedInt32Member len = G.INT32.construct();

		G.FSTRING.length().call(a, len);
		assertEquals(0, len.v());
		G.FSTRING.norm().call(a, len);
		assertEquals(0, len.v());
		
		FixedStringMember b = new FixedStringMember("1234");

		G.FSTRING.length().call(b, len);
		assertEquals(4, len.v());
		G.FSTRING.norm().call(b, len);
		assertEquals(4, len.v());
	}

	@Test
	public void test27() {
		FixedStringMember a = new FixedStringMember("123.456.789.");
		FixedStringMember regex1 = new FixedStringMember(".*23.*");
		FixedStringMember regex2 = new FixedStringMember(".*34.*");
		assertTrue(G.FSTRING.matches().call(regex1, a));
		assertFalse(G.FSTRING.matches().call(regex2, a));
	}

	@Test
	public void test28() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = new FixedStringMember("def");
		FixedStringMember c = G.FSTRING.construct("xxx");
		
		G.FSTRING.max().call(a, b, c);
		assertEquals("def", c.v());
		
		G.FSTRING.min().call(a, b, c);
		assertEquals("abc", c.v());
	}

	@Test
	public void test29() {
		FixedStringMember a = new FixedStringMember("abc");
		G.FSTRING.negate().call(a, a);
		assertEquals("abc", a.v());
	}

	@Test
	public void test30() {
		//String s = "askf4ja38sj";
		//int r = s.offsetByCodePoints(4, 5);
		//FixedStringMember a = new FixedStringMember(s);
		//int result = G.FSTRING.offsetByCodePoints().call(4, 5, a);
		//assertEquals(r, result);
	}

	@Test
	public void test31() {
		FixedStringMember a = new FixedStringMember("Hello, How are you");
		FixedStringMember patt1 = new FixedStringMember("How");
		FixedStringMember patt2 = new FixedStringMember("HOW");
		
		assertTrue(G.FSTRING.regionMatches().call(7, patt1, 0, 3, a));
		assertFalse(G.FSTRING.regionMatches().call(7, patt2, 0, 3, a));
		assertTrue(G.FSTRING.regionMatchesWithCase().call(true, 7, patt2, 0, 3, a));
	}

	@Test
	public void test32() {
		FixedStringMember a = new FixedStringMember("1 2 3 4 5 6 7 8 1 2 3 4 5 6 7 8");
		FixedStringMember b = G.FSTRING.construct(a);
		
		G.FSTRING.replace().call((int) '3', (int) '9', a, b);
		assertEquals("1 2 9 4 5 6 7 8 1 2 9 4 5 6 7 8", b.v());
		
		FixedStringMember patt1 = new FixedStringMember("1 2");
		FixedStringMember patt2 = new FixedStringMember("forp");
		G.FSTRING.replaceAll().call(patt1, patt2, a, b);
		assertEquals("forp 3 4 5 6 7 8 forp 3 4 5 6 7", b.v());
		
		G.FSTRING.replaceFirst().call(patt1, patt2, a, b);
		assertEquals("forp 3 4 5 6 7 8 1 2 3 4 5 6 7 ", b.v());
	}

	@Test
	public void test33() {
		FixedStringMember a = new FixedStringMember();
		assertEquals(0, (int) G.FSTRING.signum().call(a));
		FixedStringMember b = new FixedStringMember("0");
		assertEquals(1, (int) G.FSTRING.signum().call(b));
	}

	@Test
	public void test34() {
		FixedStringMember a = new FixedStringMember("1,2,3,4,5,6");
		FixedStringMember delim = new FixedStringMember(",");
		FixedStringMember[] result = G.FSTRING.split().call(delim, a);
		assertEquals(6, result.length);
		assertEquals("1", result[0].v());
		assertEquals("2", result[1].v());
		assertEquals("3", result[2].v());
		assertEquals("4", result[3].v());
		assertEquals("5", result[4].v());
		assertEquals("6", result[5].v());
		
		result = G.FSTRING.splitWithLimit().call(4, delim, a);
		assertEquals(4, result.length);
		assertEquals("1", result[0].v());
		assertEquals("2", result[1].v());
		assertEquals("3", result[2].v());
		assertEquals("4,5,6", result[3].v());
	}

	@Test
	public void test35() {
		FixedStringMember a = new FixedStringMember("abcdefg");
		FixedStringMember patt1 = new FixedStringMember("bc");
		FixedStringMember patt2 = new FixedStringMember("abc");
		assertFalse(G.FSTRING.startsWith().call(patt1, a));
		assertTrue(G.FSTRING.startsWith().call(patt2, a));
	}

	@Test
	public void test36() {
		FixedStringMember a = new FixedStringMember("abcdef");
		FixedStringMember b = G.FSTRING.construct("xxxxxx");
		G.FSTRING.substringFromStartToEnd().call(1, 4, a, b);
		assertEquals("bcd", b.v());
		G.FSTRING.subStringFromStart().call(2, a, b);
		assertEquals("cdef", b.v());
	}

	@Test
	public void test37() {
		FixedStringMember a = new FixedStringMember("fredfred");
		FixedStringMember patt1 = new FixedStringMember("klk");
		FixedStringMember patt2 = new FixedStringMember("fred");
		FixedStringMember c = G.FSTRING.construct("000000000000");
		G.FSTRING.subtract().call(a, patt1, c);
		assertEquals("fredfred", c.v());
		G.FSTRING.subtract().call(a, patt2, c);
		assertEquals("fred", c.v());
		G.FSTRING.subtract().call(c, patt2, c);
		assertEquals("", c.v());
	}

	@Test
	public void test38() {
		FixedStringMember a = new FixedStringMember("abc");
		char[] result = G.FSTRING.toCharArray().call(a);
		assertEquals(3, result.length);
		assertEquals('a', result[0]);
		assertEquals('b', result[1]);
		assertEquals('c', result[2]);
	}

	@Test
	public void test39() {
		FixedStringMember a = new FixedStringMember("ABC");
		FixedStringMember b = G.FSTRING.construct("   ");
		G.FSTRING.toLower().call(a, b);
		assertEquals("abc", b.v());
		G.FSTRING.toLowerWithLocale().call(Locale.FRANCE, a, b);
		assertEquals("abc", b.v());
	}

	@Test
	public void test40() {
		FixedStringMember a = new FixedStringMember("abc");
		FixedStringMember b = G.FSTRING.construct("   ");
		G.FSTRING.toUpper().call(a, b);
		assertEquals("ABC", b.v());
		G.FSTRING.toUpperWithLocale().call(Locale.FRANCE, a, b);
		assertEquals("ABC", b.v());
	}

	@Test
	public void test41() {
		FixedStringMember a = new FixedStringMember("    abc     ");
		FixedStringMember b = G.FSTRING.construct("           ");
		G.FSTRING.trim().call(a, b);
		assertEquals("abc", b.v());
	}
	
	public void test42() {
		FixedStringMember a = new FixedStringMember(12);
		FixedStringMember b = new FixedStringMember("12345678");
		FixedStringMember c = new FixedStringMember("90123456");
		G.FSTRING.add().call(b, c, a);
		assertEquals("123456789012", a.v());
	}

}
