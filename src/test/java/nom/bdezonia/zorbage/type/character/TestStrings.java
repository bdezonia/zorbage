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
package nom.bdezonia.zorbage.type.character;

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
public class TestStrings {

	@Test
	public void test1() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("def");
		StringMember c = G.STRING.construct();
		assertEquals("", c.v());
		G.STRING.add().call(a, b, c);
		assertEquals("abcdef", c.v());
	}

	@Test
	public void test2() {
		StringMember a = new StringMember("abc");
		StringMember b = G.STRING.construct();
		assertEquals("", b.v());
		G.STRING.assign().call(a, b);
		assertEquals("abc", b.v());
	}

	@Test
	public void test3() {
		StringMember a = new StringMember("abc");
		assertEquals((char) 'a', (char) G.STRING.charAt().call(0, a));
		assertEquals((char) 'b', (char) G.STRING.charAt().call(1, a));
		assertEquals((char) 'c', (char) G.STRING.charAt().call(2, a));
	}

	@Test
	public void test4() {
		StringMember a = new StringMember("abc");
		assertEquals((int) 'a', (int) G.STRING.codePointAt().call(0, a));
		assertEquals((int) 'b', (int) G.STRING.codePointAt().call(1, a));
		assertEquals((int) 'c', (int) G.STRING.codePointAt().call(2, a));
	}

	@Test
	public void test5() {
		StringMember a = new StringMember("abc");
		assertEquals((int) 'a', (int) G.STRING.codePointBefore().call(1, a));
		assertEquals((int) 'b', (int) G.STRING.codePointBefore().call(2, a));
	}

	@Test
	public void test6() {
		StringMember a = new StringMember("abc");
		assertEquals(1, (int) G.STRING.codePointCount().call(0, 1, a));
		assertEquals(1, (int) G.STRING.codePointCount().call(1, 2, a));
		assertEquals(1, (int) G.STRING.codePointCount().call(2, 3, a));
		assertEquals(3, (int) G.STRING.codePointCount().call(0, 3, a));
	}

	@Test
	public void test7() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("def");
		assertTrue(G.STRING.compare().call(a, b) < 0);
		assertTrue(G.STRING.compare().call(b, a) > 0);
		assertTrue(G.STRING.compare().call(a, a) == 0);
		assertTrue(G.STRING.compare().call(b, b) == 0);
	}

	@Test
	public void test8() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("def");
		StringMember c = G.STRING.construct();
		assertEquals("", c.v());
		G.STRING.concat().call(a, b, c);
		assertEquals("abcdef", c.v());
	}

	@Test
	public void test9() {
		StringMember a = G.STRING.construct();
		assertEquals("", a.v());
		StringMember b = G.STRING.construct("floo");
		assertEquals("floo", b.v());
		StringMember c = G.STRING.construct(b);
		assertEquals("floo", c.v());
	}

	@Test
	public void test10() {
		StringMember a = G.STRING.construct("one two three four");
		StringMember x = G.STRING.construct("three");
		StringMember y = G.STRING.construct("five");
		assertTrue(G.STRING.contains().call(x, a));
		assertFalse(G.STRING.contains().call(y, a));
	}

	@Test
	public void test11() {
		StringMember a = new StringMember("abc");
		StringMember b = G.STRING.construct(a);
		StringMember c = new StringMember("def");
		assertTrue(G.STRING.contentEquals().call(b, a));
		assertFalse(G.STRING.contentEquals().call(c, a));
	}

	@Test
	public void test12() {
		StringMember a = new StringMember("abcdabbadabbadoo");
		StringMember end1 = new StringMember("doo");
		StringMember end2 = new StringMember("boo");
		assertTrue(G.STRING.endsWith().call(end1, a));
		assertFalse(G.STRING.endsWith().call(end2, a));
	}

	@Test
	public void test13() {
		byte[] fixed;
		StringMember a = new StringMember("Fred");
		fixed = "Fred".getBytes();
		assertArrayEquals(fixed, G.STRING.getBytes().call(a));
		fixed = "Fred".getBytes(Charset.forName("utf8"));
		assertArrayEquals(fixed, G.STRING.getBytesUsingCharset().call(Charset.forName("utf8"), a));
		try {
			fixed = "Fred".getBytes("utf8");
		} catch (Exception e) {
			fixed = new byte[0];
		}
		assertArrayEquals(fixed, G.STRING.getBytesUsingCharsetName().call("utf8", a));
	}

	@Test
	public void test14() {
		StringMember string = new StringMember("123 456 78 9 10 11 12 456");
		StringMember piece1 = new StringMember("456");
		StringMember piece2 = new StringMember("789");
		assertEquals(4, (int) G.STRING.indexOf().call(piece1, string));
		assertEquals(-1, (int) G.STRING.indexOf().call(piece2, string));
	}

	@Test
	public void test15() {
		StringMember string = new StringMember("123 456 78 9 10 11 12 456");
		StringMember piece1 = new StringMember("456");
		StringMember piece2 = new StringMember("789");
		assertEquals(4, (int) G.STRING.indexOfFrom().call(piece1, 0, string));
		assertEquals(22, (int) G.STRING.indexOfFrom().call(piece1, 5, string));
		assertEquals(-1, (int) G.STRING.indexOfFrom().call(piece2, 0, string));
	}

	@Test
	public void test16() {
		StringMember string = new StringMember("abc");
		assertEquals(0, (int) G.STRING.indexOfCodePoint().call((int) 'a', string));
		assertEquals(1, (int) G.STRING.indexOfCodePoint().call((int) 'b', string));
		assertEquals(2, (int) G.STRING.indexOfCodePoint().call((int) 'c', string));
		assertEquals(-1, (int) G.STRING.indexOfCodePoint().call((int) 'z', string));
	}

	@Test
	public void test17() {
		StringMember string = new StringMember("a b c d e f e d s");
		assertEquals(6, (int) G.STRING.indexOfCodePointFrom().call((int) 'd', 0, string));
		assertEquals(14, (int) G.STRING.indexOfCodePointFrom().call((int) 'd', 7, string));
		assertEquals(-1, (int) G.STRING.indexOfCodePointFrom().call((int) 'z', 0, string));
	}

	@Test
	public void test18() {
		StringMember a = new StringMember("ahddk3");
		assertFalse(G.STRING.isEmpty().call(a));
		assertFalse(G.STRING.isZero().call(a));
		G.STRING.zero().call(a);
		assertTrue(G.STRING.isEmpty().call(a));
		assertTrue(G.STRING.isZero().call(a));
	}

	@Test
	public void test19() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("ABC");
		assertTrue(G.STRING.isEqual().call(a,a));
		assertTrue(G.STRING.isEqual().call(b,b));
		assertFalse(G.STRING.isEqual().call(a,b));
		assertTrue(G.STRING.isEqualIgnoreCase().call(a,a));
		assertTrue(G.STRING.isEqualIgnoreCase().call(b,b));
		assertTrue(G.STRING.isEqualIgnoreCase().call(a,b));
		assertFalse(G.STRING.isNotEqual().call(a,a));
		assertFalse(G.STRING.isNotEqual().call(b,b));
		assertTrue(G.STRING.isNotEqual().call(a,b));
		assertFalse(G.STRING.isNotEqualIgnoreCase().call(a,a));
		assertFalse(G.STRING.isNotEqualIgnoreCase().call(b,b));
		assertFalse(G.STRING.isNotEqualIgnoreCase().call(a,b));
	}

	@Test
	public void test20() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("abc1");
		StringMember c = new StringMember("ABC");
		assertFalse(G.STRING.isGreater().call(a, b));
		assertTrue(G.STRING.isGreater().call(b, c));
		assertTrue(G.STRING.isGreater().call(a, c));
		assertFalse(G.STRING.isGreaterIgnoreCase().call(a, b));
		assertTrue(G.STRING.isGreaterIgnoreCase().call(b, c));
		assertFalse(G.STRING.isGreaterIgnoreCase().call(a, c));
		assertTrue(G.STRING.isGreater().call(b, a));
		assertFalse(G.STRING.isGreater().call(c, b));
		assertFalse(G.STRING.isGreater().call(c, a));
		assertTrue(G.STRING.isGreaterIgnoreCase().call(b, a));
		assertFalse(G.STRING.isGreaterIgnoreCase().call(c, b));
		assertFalse(G.STRING.isGreaterIgnoreCase().call(c, a));
	}

	@Test
	public void test21() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("abc1");
		StringMember c = new StringMember("ABC");
		assertFalse(G.STRING.isGreaterEqual().call(a, b));
		assertTrue(G.STRING.isGreaterEqual().call(b, c));
		assertTrue(G.STRING.isGreaterEqual().call(a, c));
		assertFalse(G.STRING.isGreaterEqualIgnoreCase().call(a, b));
		assertTrue(G.STRING.isGreaterEqualIgnoreCase().call(b, c));
		assertTrue(G.STRING.isGreaterEqualIgnoreCase().call(a, c));
		assertTrue(G.STRING.isGreaterEqual().call(b, a));
		assertFalse(G.STRING.isGreaterEqual().call(c, b));
		assertFalse(G.STRING.isGreaterEqual().call(c, a));
		assertTrue(G.STRING.isGreaterEqualIgnoreCase().call(b, a));
		assertFalse(G.STRING.isGreaterEqualIgnoreCase().call(c, b));
		assertTrue(G.STRING.isGreaterEqualIgnoreCase().call(c, a));
	}

	@Test
	public void test22() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("abc1");
		StringMember c = new StringMember("ABC");
		assertTrue(G.STRING.isLess().call(a, b));
		assertFalse(G.STRING.isLess().call(b, c));
		assertFalse(G.STRING.isLess().call(a, c));
		assertTrue(G.STRING.isLessIgnoreCase().call(a, b));
		assertFalse(G.STRING.isLessIgnoreCase().call(b, c));
		assertFalse(G.STRING.isLessIgnoreCase().call(a, c));
		assertFalse(G.STRING.isLess().call(b, a));
		assertTrue(G.STRING.isLess().call(c, b));
		assertTrue(G.STRING.isLess().call(c, a));
		assertFalse(G.STRING.isLessIgnoreCase().call(b, a));
		assertTrue(G.STRING.isLessIgnoreCase().call(c, b));
		assertFalse(G.STRING.isLessIgnoreCase().call(c, a));
	}

	@Test
	public void test23() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("abc1");
		StringMember c = new StringMember("ABC");
		assertTrue(G.STRING.isLessEqual().call(a, b));
		assertFalse(G.STRING.isLessEqual().call(b, c));
		assertFalse(G.STRING.isLessEqual().call(a, c));
		assertTrue(G.STRING.isLessEqualIgnoreCase().call(a, b));
		assertFalse(G.STRING.isLessEqualIgnoreCase().call(b, c));
		assertTrue(G.STRING.isLessEqualIgnoreCase().call(a, c));
		assertFalse(G.STRING.isLessEqual().call(b, a));
		assertTrue(G.STRING.isLessEqual().call(c, b));
		assertTrue(G.STRING.isLessEqual().call(c, a));
		assertFalse(G.STRING.isLessEqualIgnoreCase().call(b, a));
		assertTrue(G.STRING.isLessEqualIgnoreCase().call(c, b));
		assertTrue(G.STRING.isLessEqualIgnoreCase().call(c, a));
	}

	@Test
	public void test24() {
		StringMember[] strings = new StringMember[3];
		strings[0] = new StringMember("abc");
		strings[1] = new StringMember("def");
		strings[2] = new StringMember("ghi");
		StringMember delim = new StringMember(",");
		StringMember result = G.STRING.join().call(delim, strings);
		assertEquals("abc,def,ghi",result.v());
	}

	@Test
	public void test25() {
		StringMember a = new StringMember("1 2 3 4 5 6 7 8 9 0 1 2 3 4");
		StringMember srch = new StringMember("1");
		
		assertEquals(20, (int) G.STRING.lastIndexOf().call(srch, a));

		assertEquals(20, (int) G.STRING.lastIndexOfFrom().call(srch, 27, a));
		assertEquals(0, (int) G.STRING.lastIndexOfFrom().call(srch, 19, a));
		
		assertEquals(20, (int) G.STRING.lastIndexOfCodePoint().call((int) '1', a));

		assertEquals(20, (int) G.STRING.lastIndexOfCodePointFrom().call((int) '1', 27, a));
		assertEquals(0, (int) G.STRING.lastIndexOfCodePointFrom().call((int) '1', 19, a));
	}

	@Test
	public void test26() {
		StringMember a = new StringMember();
		SignedInt32Member len = G.INT32.construct();

		G.STRING.length().call(a, len);
		assertEquals(0, len.v());
		G.STRING.norm().call(a, len);
		assertEquals(0, len.v());
		
		a.setV("1234");

		G.STRING.length().call(a, len);
		assertEquals(4, len.v());
		G.STRING.norm().call(a, len);
		assertEquals(4, len.v());
	}

	@Test
	public void test27() {
		StringMember a = new StringMember("123.456.789.");
		StringMember regex1 = new StringMember(".*23.*");
		StringMember regex2 = new StringMember(".*34.*");
		assertTrue(G.STRING.matches().call(regex1, a));
		assertFalse(G.STRING.matches().call(regex2, a));
	}

	@Test
	public void test28() {
		StringMember a = new StringMember("abc");
		StringMember b = new StringMember("def");
		StringMember c = G.STRING.construct();
		
		G.STRING.max().call(a, b, c);
		assertEquals("def", c.v());
		
		G.STRING.min().call(a, b, c);
		assertEquals("abc", c.v());
	}

	@Test
	public void test29() {
		StringMember a = new StringMember("abc");
		G.STRING.negate().call(a, a);
		assertEquals("abc", a.v());
	}

	@Test
	public void test30() {
		String s = "askf4ja38sj";
		int r = s.offsetByCodePoints(4, 5);
		StringMember a = new StringMember(s);
		int result = G.STRING.offsetByCodePoints().call(4, 5, a);
		assertEquals(r, result);
	}

	@Test
	public void test31() {
		StringMember a = new StringMember("Hello, How are you");
		StringMember patt1 = new StringMember("How");
		StringMember patt2 = new StringMember("HOW");
		
		assertTrue(G.STRING.regionMatches().call(7, patt1, 0, 3, a));
		assertFalse(G.STRING.regionMatches().call(7, patt2, 0, 3, a));
		assertTrue(G.STRING.regionMatchesWithCase().call(true, 7, patt2, 0, 3, a));
	}

	@Test
	public void test32() {
		StringMember a = new StringMember("1 2 3 4 5 6 7 8 1 2 3 4 5 6 7 8");
		StringMember b = G.STRING.construct();
		
		G.STRING.replace().call('3', '9', a, b);
		assertEquals("1 2 9 4 5 6 7 8 1 2 9 4 5 6 7 8", b.v());
		
		StringMember patt1 = new StringMember("1 2");
		StringMember patt2 = new StringMember("forp");
		G.STRING.replaceAll().call(patt1, patt2, a, b);
		assertEquals("forp 3 4 5 6 7 8 forp 3 4 5 6 7 8", b.v());
		
		G.STRING.replaceFirst().call(patt1, patt2, a, b);
		assertEquals("forp 3 4 5 6 7 8 1 2 3 4 5 6 7 8", b.v());
	}

	@Test
	public void test33() {
		StringMember a = new StringMember();
		assertEquals(0, (int) G.STRING.signum().call(a));
		a.setV("14");
		assertEquals(1, (int) G.STRING.signum().call(a));
	}

	@Test
	public void test34() {
		StringMember a = new StringMember("1,2,3,4,5,6");
		StringMember delim = new StringMember(",");
		StringMember[] result = G.STRING.split().call(delim, a);
		assertEquals(6, result.length);
		assertEquals("1", result[0].v());
		assertEquals("2", result[1].v());
		assertEquals("3", result[2].v());
		assertEquals("4", result[3].v());
		assertEquals("5", result[4].v());
		assertEquals("6", result[5].v());
		
		result = G.STRING.splitWithLimit().call(4, delim, a);
		assertEquals(4, result.length);
		assertEquals("1", result[0].v());
		assertEquals("2", result[1].v());
		assertEquals("3", result[2].v());
		assertEquals("4,5,6", result[3].v());
	}

	@Test
	public void test35() {
		StringMember a = new StringMember("abcdefg");
		StringMember patt1 = new StringMember("bc");
		StringMember patt2 = new StringMember("abc");
		assertFalse(G.STRING.startsWith().call(patt1, a));
		assertTrue(G.STRING.startsWith().call(patt2, a));
	}

	@Test
	public void test36() {
		StringMember a = new StringMember("abcdef");
		StringMember b = G.STRING.construct();
		G.STRING.subStringFromStart().call(2, a, b);
		assertEquals("cdef", b.v());
		G.STRING.substringFromStartToEnd().call(1, 4, a, b);
		assertEquals("bcd", b.v());
	}

	@Test
	public void test37() {
		StringMember a = new StringMember("fredfred");
		StringMember patt1 = new StringMember("klk");
		StringMember patt2 = new StringMember("fred");
		StringMember c = G.STRING.construct();
		G.STRING.subtract().call(a, patt1, c);
		assertEquals("fredfred", c.v());
		G.STRING.subtract().call(a, patt2, c);
		assertEquals("fred", c.v());
		G.STRING.subtract().call(c, patt2, c);
		assertEquals("", c.v());
	}

	@Test
	public void test38() {
		StringMember a = new StringMember("abc");
		char[] result = G.STRING.toCharArray().call(a);
		assertEquals(3, result.length);
		assertEquals('a', result[0]);
		assertEquals('b', result[1]);
		assertEquals('c', result[2]);
	}

	@Test
	public void test39() {
		StringMember a = new StringMember("ABC");
		StringMember b = G.STRING.construct();
		G.STRING.toLower().call(a, b);
		assertEquals("abc", b.v());
		G.STRING.toLowerWithLocale().call(Locale.FRANCE, a, b);
		assertEquals("abc", b.v());
	}

	@Test
	public void test40() {
		StringMember a = new StringMember("abc");
		StringMember b = G.STRING.construct();
		G.STRING.toUpper().call(a, b);
		assertEquals("ABC", b.v());
		G.STRING.toUpperWithLocale().call(Locale.FRANCE, a, b);
		assertEquals("ABC", b.v());
	}

	@Test
	public void test41() {
		StringMember a = new StringMember("    abc     ");
		StringMember b = G.STRING.construct();
		G.STRING.trim().call(a, b);
		assertEquals("abc", b.v());
	}

}
