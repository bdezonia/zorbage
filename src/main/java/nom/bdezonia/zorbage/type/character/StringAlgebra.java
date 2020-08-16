/*
v * Zorbage: an algebraic data hierarchy for use in numeric processing.
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

import java.nio.charset.Charset;
import java.util.Locale;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.function.Function5;
import nom.bdezonia.zorbage.function.Function6;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.character.StringMember;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class StringAlgebra
	implements
		Algebra<StringAlgebra, StringMember>,
		Addition<StringMember>,
		Norm<StringMember, SignedInt32Member>,
		Ordered<StringMember>
{
	@Override
	public StringMember construct() {
		return new StringMember();
	}

	@Override
	public StringMember construct(StringMember other) {
		return other.duplicate();
	}

	@Override
	public StringMember construct(String str) {
		return new StringMember(str);
	}

	private final Function2<Boolean, StringMember, StringMember> EQ =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compare().call(a, b) == 0;
		}
	};
	
	@Override
	public Function2<Boolean, StringMember, StringMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, StringMember, StringMember> NEQ =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compare().call(a, b) != 0;
		}
	};
	
	@Override
	public Function2<Boolean, StringMember, StringMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<StringMember, StringMember> ASSIGN =
			new Procedure2<StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b) {
			a.setV(b.v());
		}
	};
	
	@Override
	public Procedure2<StringMember, StringMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<StringMember> ZERO =
			new Procedure1<StringMember>()
	{
		@Override
		public void call(StringMember a) {
			a.setV("");
		}
	};
			
	@Override
	public Procedure1<StringMember> zero() {
		return ZERO;
	}

	private final Function1<Boolean, StringMember> ISZERO =
			new Function1<Boolean, StringMember>()
	{
		@Override
		public Boolean call(StringMember a) {
			return ISEMPTY.call(a);
		}
	};

	@Override
	public Function1<Boolean, StringMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure2<StringMember, StringMember> negate() {
		return ASSIGN;  // ignore negations: unsigned numbers do similar things
	}

	private final Procedure3<StringMember, StringMember, StringMember> ADD =
			new Procedure3<StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b, StringMember c) {
			c.setV(a.v() + b.v());
		}
	};

	@Override
	public Procedure3<StringMember, StringMember, StringMember> add() {
		return ADD;
	}

	private final Procedure3<StringMember, StringMember, StringMember> SUB =
			new Procedure3<StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b, StringMember c) {
			String aV = a.v();
			String bV = b.v();
			if (aV.endsWith(bV)) {
				int offset = aV.lastIndexOf(bV);
				c.setV(aV.substring(0, offset));
			}
			else {
				c.setV(aV);
			}
		}
	};

	@Override
	public Procedure3<StringMember, StringMember, StringMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean, StringMember, StringMember> LESS =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean, StringMember, StringMember> isLess() {
		return LESS;
	}

	private final Function2<Boolean, StringMember, StringMember> LESSEQ =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean, StringMember, StringMember> isLessEqual() {
		return LESSEQ;
	}

	private final Function2<Boolean, StringMember, StringMember> GREAT =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean, StringMember, StringMember> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, StringMember, StringMember> GREATEQ =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean, StringMember, StringMember> isGreaterEqual() {
		return GREATEQ;
	}

	private final Function2<Integer, StringMember, StringMember> CMP =
			new Function2<Integer, StringMember, StringMember>()
	{
		@Override
		public Integer call(StringMember a, StringMember b) {
			return a.v().compareTo(b.v());
		}
	};

	@Override
	public Function2<Integer, StringMember, StringMember> compare() {
		return CMP;
	}

	private final Function1<Integer, StringMember> SIGNUM =
			new Function1<Integer, StringMember>()
	{
		@Override
		public Integer call(StringMember a) {
			if (isEmpty().call(a))
				return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<Integer, StringMember> signum() {
		return SIGNUM;
	}

	private final Procedure3<StringMember, StringMember, StringMember> MIN =
			new Procedure3<StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b, StringMember c) {
			if (isLess().call(a, b))
				c.setV(a.v());
			else
				c.setV(b.v());
		}
	};

	@Override
	public Procedure3<StringMember, StringMember, StringMember> min() {
		return MIN;
	}

	private final Procedure3<StringMember, StringMember, StringMember> MAX =
			new Procedure3<StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b, StringMember c) {
			if (isGreater().call(a, b))
				c.setV(a.v());
			else
				c.setV(b.v());
		}
	};

	@Override
	public Procedure3<StringMember, StringMember, StringMember> max() {
		return MAX;
	}

	private final Procedure2<StringMember, SignedInt32Member> LEN =
			new Procedure2<StringMember, SignedInt32Member>()
	{
		@Override
		public void call(StringMember a, SignedInt32Member b) {
			b.setV(a.v().length());
		}
	};

	public Procedure2<StringMember, SignedInt32Member> length() {
		return LEN;
	}

	@Override
	public Procedure2<StringMember, SignedInt32Member> norm() {
		return LEN;
	}

	private final Function2<Boolean, StringMember, StringMember> EQIGCASE =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return a.v().equalsIgnoreCase(b.v());
		}
	};
	
	public Function2<Boolean, StringMember, StringMember> isEqualIgnoreCase() {
		return EQIGCASE;
	}

	private final Function2<Integer, StringMember, StringMember> CMPIGCASE =
			new Function2<Integer, StringMember, StringMember>()
	{
		@Override
		public Integer call(StringMember a, StringMember b) {
			return a.v().compareToIgnoreCase(b.v());
		}
	};

	public Function2<Integer, StringMember, StringMember> compareIgnoreCase() {
		return CMPIGCASE;
	}
	
	private final Function2<Boolean, StringMember, StringMember> ENDSWITH =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember suffix, StringMember a) {
			return a.v().endsWith(suffix.v());
		}
	};

	public Function2<Boolean, StringMember, StringMember> endsWith() {
		return ENDSWITH;
	}
	
	private final Function2<Boolean, StringMember, StringMember> STARTSWITH =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember prefix, StringMember a) {
			return a.v().startsWith(prefix.v());
		}
	};

	public Function2<Boolean, StringMember, StringMember> startsWith() {
		return STARTSWITH;
	}
	
	private final Procedure2<StringMember, StringMember> TRIM =
			new Procedure2<StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b) {
			b.setV(a.v().trim());
		}
	};
	
	public Procedure2<StringMember, StringMember> trim() {
		return TRIM;
	}

	private final Procedure2<StringMember, StringMember> TOLOWER =
			new Procedure2<StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b) {
			b.setV(a.v().toLowerCase());
		}
	};

	public Procedure2<StringMember, StringMember> toLower() {
		return TOLOWER;
	}
	
	private final Procedure2<StringMember, StringMember> TOUPPER =
			new Procedure2<StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b) {
			b.setV(a.v().toUpperCase());
		}
	};

	public Procedure2<StringMember, StringMember> toUpper() {
		return TOUPPER;
	}
	
	private final Procedure3<Locale, StringMember, StringMember> TOLOWERL =
			new Procedure3<Locale, StringMember, StringMember>()
	{
		@Override
		public void call(Locale locale, StringMember a, StringMember b) {
			b.setV(a.v().toLowerCase(locale));
		}
	};

	public Procedure3<Locale, StringMember, StringMember> toLowerWithLocale() {
		return TOLOWERL;
	}
	
	private final Procedure3<Locale, StringMember, StringMember> TOUPPERL =
			new Procedure3<Locale, StringMember, StringMember>()
	{
		@Override
		public void call(Locale locale, StringMember a, StringMember b) {
			b.setV(a.v().toUpperCase(locale));
		}
	};

	public Procedure3<Locale, StringMember, StringMember> toUpperWithLocale() {
		return TOUPPERL;
	}

	private final Function1<Boolean, StringMember> ISEMPTY =
			new Function1<Boolean, StringMember>()
	{
		@Override
		public Boolean call(StringMember a) {
			return a.v() == null || a.v().length() == 0;
		}
	};

	public Function1<Boolean, StringMember> isEmpty() {
		return ISEMPTY;
	}
	
	private final Function2<Boolean, StringMember, StringMember> CONTAINS =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember pattern, StringMember a) {
			return a.v().contains(pattern.v());
		}
	};

	public Function2<Boolean, StringMember, StringMember> contains() {
		return CONTAINS;
	}
	
	private final Procedure4<Integer, Integer, StringMember, StringMember> SUBSTR2 =
			new Procedure4<Integer, Integer, StringMember, StringMember>()
	{
		@Override
		public void call(Integer start, Integer end, StringMember a, StringMember b) {
			b.setV(a.v().substring(start, end));
		}
	};

	public Procedure4<Integer, Integer, StringMember, StringMember> substringFromStartToEnd() {
		return SUBSTR2;
	}

	private final Procedure3<Integer, StringMember, StringMember> SUBSTR =
			new Procedure3<Integer, StringMember, StringMember>()
	{
		@Override
		public void call(Integer start, StringMember a, StringMember b) {
			substringFromStartToEnd().call(start, a.v().length(), a, b);
		}
	};

	public Procedure3<Integer, StringMember, StringMember> subStringFromStart() {
		return SUBSTR;
	}

	private final Function2<Integer, Integer, StringMember> IDXOF =
			new Function2<Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer codePoint, StringMember a) {
			return indexOfCodePointFrom().call(codePoint, 0, a);
		}
	};
	
	public Function2<Integer, Integer, StringMember> indexOfCodePoint() {
		return IDXOF;
	}

	private final Function3<Integer, Integer, Integer, StringMember> IDXOFFROM =
			new Function3<Integer, Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer ch, Integer from, StringMember a) {
			return a.v().indexOf(ch, from);
		}
	};
	
	public Function3<Integer, Integer, Integer, StringMember> indexOfCodePointFrom() {
		return IDXOFFROM;
	}

	private final Function2<Integer, Integer, StringMember> LASTIDXOF =
			new Function2<Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer codePoint, StringMember a) {
			return lastIndexOfCodePointFrom().call(codePoint, a.v().length(), a);
		}
	};
	
	public Function2<Integer, Integer, StringMember> lastIndexOfCodePoint() {
		return LASTIDXOF;
	}

	private final Function3<Integer, Integer, Integer, StringMember> LASTIDXOFFROM =
			new Function3<Integer, Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer ch, Integer from, StringMember a) {
			return a.v().lastIndexOf(ch, from);
		}
	};
	
	public Function3<Integer, Integer, Integer, StringMember> lastIndexOfCodePointFrom() {
		return LASTIDXOFFROM;
	}

	private final Procedure4<Character, Character, StringMember, StringMember> REPLACE =
			new Procedure4<Character, Character, StringMember, StringMember>()
	{
		@Override
		public void call(Character oldChar, Character newChar, StringMember a, StringMember b) {
			b.setV(a.v().replace(oldChar, newChar));
		}
	};

	public Procedure4<Character, Character, StringMember, StringMember> replace() {
		return REPLACE;
	}
	
	private final Function1<byte[], StringMember> GETBYTES =
			new Function1<byte[], StringMember>()
	{
		@Override
		public byte[] call(StringMember a) {
			return a.v().getBytes();
		}
	};

	public Function1<byte[], StringMember> getBytes() {
		return GETBYTES;
	}
	
	private final Function2<byte[], Charset, StringMember> GETBYTESCHARSET =
			new Function2<byte[], Charset, StringMember>()
	{
		@Override
		public byte[] call(Charset charset, StringMember a) {
			return a.v().getBytes(charset);
		}
	};

	public Function2<byte[], Charset, StringMember> getBytesUsingCharset() {
		return GETBYTESCHARSET;
	}

	private final Function2<byte[], String, StringMember> GETBYTESSTRING =
			new Function2<byte[], String, StringMember>()
	{
		@Override
		public byte[] call(String charsetName, StringMember a) {
			byte[] bytes;
			try {
				bytes = a.v().getBytes(charsetName);
			} catch (Exception e) {
				bytes = new byte[0];
			}
			return bytes;
		}
	};

	public Function2<byte[], String, StringMember> getBytesUsingCharsetName() {
		return GETBYTESSTRING;
	}

	private final Function2<Boolean, StringMember, StringMember> LESSIGCASE =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compareIgnoreCase().call(a, b) < 0;
		}
	};

	public Function2<Boolean, StringMember, StringMember> isLessIgnoreCase() {
		return LESSIGCASE;
	}
	
	private final Function2<Boolean, StringMember, StringMember> LESSEQIGCASE =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compareIgnoreCase().call(a, b) <= 0;
		}
	};

	public Function2<Boolean, StringMember, StringMember> isLessEqualIgnoreCase() {
		return LESSEQIGCASE;
	}

	private final Function2<Boolean, StringMember, StringMember> GREATIGCASE =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compareIgnoreCase().call(a, b) > 0;
		}
	};

	public Function2<Boolean, StringMember, StringMember> isGreaterIgnoreCase() {
		return GREATIGCASE;
	}

	private final Function2<Boolean, StringMember, StringMember> GREATEQIGCASE =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return compareIgnoreCase().call(a, b) >= 0;
		}
	};

	public Function2<Boolean, StringMember, StringMember> isGreaterEqualIgnoreCase() {
		return GREATEQIGCASE;
	}

	private final Function2<Boolean, StringMember, StringMember> NEQIGCASE =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember a, StringMember b) {
			return !isEqualIgnoreCase().call(a, b);
		}
	};
	
	public Function2<Boolean, StringMember, StringMember> isNotEqualIgnoreCase() {
		return NEQIGCASE;
	}

	private final Function2<Integer, StringMember, StringMember> INDEXOF =
			new Function2<Integer, StringMember, StringMember>()
	{
		@Override
		public Integer call(StringMember substring, StringMember a) {
			return a.v().indexOf(substring.v());
		}
	};

	public Function2<Integer, StringMember, StringMember> indexOf()  {
		return INDEXOF;
	}
	
	private final Function3<Integer, Integer, StringMember, StringMember> INDEXOFFROM =
			new Function3<Integer, Integer, StringMember, StringMember>()
	{
		@Override
		public Integer call(Integer from, StringMember substring, StringMember a) {
			return a.v().indexOf(substring.v(), from);
		}
	};
	
	public Function3<Integer, Integer, StringMember, StringMember> indexOfFrom()  {
		return INDEXOFFROM;
	}
	
	private final Function2<Integer, StringMember, StringMember> LASTINDEXOF =
			new Function2<Integer, StringMember, StringMember>()
	{
		@Override
		public Integer call(StringMember substring, StringMember a) {
			return a.v().lastIndexOf(substring.v());
		}
	};
	
	public Function2<Integer, StringMember, StringMember> lastIndexOf()  {
		return LASTINDEXOF;
	}
	
	private final Function3<Integer, Integer, StringMember, StringMember> LASTINDEXOFFROM =
			new Function3<Integer, Integer, StringMember, StringMember>()
	{
		@Override
		public Integer call(Integer from, StringMember substring, StringMember a) {
			return a.v().lastIndexOf(substring.v(), from);
		}
	};
	
	public Function3<Integer, Integer, StringMember, StringMember> lastIndexOfFrom()  {
		return LASTINDEXOFFROM;
	}
	
	private final Function2<Boolean, StringMember, StringMember> MATCHES =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember regex, StringMember a) {
			return a.v().matches(regex.v());
		}
	};
	
	public Function2<Boolean, StringMember, StringMember> matches() {
		return MATCHES;
	}

	private final Procedure4<StringMember, StringMember, StringMember, StringMember> REPLACEALL =
			new Procedure4<StringMember, StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember regex, StringMember replacement, StringMember a, StringMember b) {
			b.setV(a.v().replaceAll(regex.v(), replacement.v()));
		}
	};
	
	public Procedure4<StringMember, StringMember, StringMember, StringMember> replaceAll() {
		return REPLACEALL;
	}

	private final Procedure4<StringMember, StringMember, StringMember, StringMember> REPLACEFIRST =
			new Procedure4<StringMember, StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember regex, StringMember replacement, StringMember a, StringMember b) {
			b.setV(a.v().replaceFirst(regex.v(), replacement.v()));
		}
	};
	
	public Procedure4<StringMember, StringMember, StringMember, StringMember> replaceFirst() {
		return REPLACEFIRST;
	}

	private final Function2<StringMember[], StringMember, StringMember> SPLIT =
			new Function2<StringMember[], StringMember, StringMember>()
	{
		@Override
		public StringMember[] call(StringMember regex, StringMember a) {
			String[] splits = a.v().split(regex.v());
			StringMember[] members = new StringMember[splits.length];
			for (int i = 0; i < splits.length; i++) {
				members[i] = new StringMember(splits[i]);
			}
			return members;
		}
	};

	public Function2<StringMember[], StringMember, StringMember> split() {
		return SPLIT;
	}

	private final Function3<StringMember[], Integer, StringMember, StringMember> SPLITLIM =
			new Function3<StringMember[], Integer, StringMember, StringMember>()
	{
		@Override
		public StringMember[] call(Integer limit, StringMember regex, StringMember a) {
			String[] splits = a.v().split(regex.v(), limit);
			StringMember[] members = new StringMember[splits.length];
			for (int i = 0; i < splits.length; i++) {
				members[i] = new StringMember(splits[i]);
			}
			return members;
		}
	};

	public Function3<StringMember[], Integer, StringMember, StringMember> splitWithLimit() {
		return SPLITLIM;
	}

	private final Function1<char[], StringMember> TOCHARS =
			new Function1<char[], StringMember>()
	{
		@Override
		public char[] call(StringMember a) {
			return a.v().toCharArray();
		}
	};

	public Function1<char[], StringMember> toCharArray() {
		return TOCHARS;
	}

	private final Procedure3<StringMember,StringMember,StringMember> CONCAT =
			new Procedure3<StringMember, StringMember, StringMember>()
	{
		@Override
		public void call(StringMember a, StringMember b, StringMember c) {
			c.setV(a.v().concat(b.v()));
		}
	};

	public Procedure3<StringMember,StringMember,StringMember> concat() {
		return CONCAT;
	}
	
	private final Function2<Character,Integer,StringMember> CHARAT =
			new Function2<Character, Integer, StringMember>()
	{
		@Override
		public Character call(Integer index, StringMember a) {
			return a.v().charAt(index);
		}
	};
	
	public Function2<Character,Integer,StringMember> charAt() {
		return CHARAT;
	}

	private final Function2<Boolean,StringMember,StringMember> CEQ =
			new Function2<Boolean, StringMember, StringMember>()
	{
		@Override
		public Boolean call(StringMember content, StringMember a) {
			return a.v().contentEquals(content.v());
		}
	};

	public Function2<Boolean,StringMember,StringMember> contentEquals() {
		return CEQ;
	}
	
	private final Function2<Integer,Integer,StringMember> CPA =
			new Function2<Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer index, StringMember a) {
			return a.v().codePointAt(index);
		}
	};
	
	public Function2<Integer,Integer,StringMember> codePointAt() {
		return CPA;
	}
	
	private final Function2<Integer,Integer,StringMember> CPB =
			new Function2<Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer index, StringMember a) {
			return a.v().codePointBefore(index);
		}
	};
	
	public Function2<Integer,Integer,StringMember> codePointBefore() {
		return CPB;
	}
	
	private final Function3<Integer,Integer,Integer,StringMember> CPC =
			new Function3<Integer, Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer beginIndex, Integer endIndex, StringMember a) {
			return a.v().codePointCount(beginIndex, endIndex);
		}
	};

	public Function3<Integer,Integer,Integer,StringMember> codePointCount() {
		return CPC;
	}
	
	private final Function3<Integer,Integer,Integer,StringMember> OFFCP =
			new Function3<Integer, Integer, Integer, StringMember>()
	{
		@Override
		public Integer call(Integer index, Integer codePointOffset, StringMember a) {
			return a.v().offsetByCodePoints(index, codePointOffset);
		}
	};

	public Function3<Integer,Integer,Integer,StringMember> offsetByCodePoints() {
		return OFFCP;
	}

	private final Function5<Boolean,Integer,StringMember,Integer,Integer,StringMember> RMATCH =
			new Function5<Boolean, Integer, StringMember, Integer, Integer, StringMember>()
	{
		@Override
		public Boolean call(Integer toffset, StringMember other, Integer ooffset, Integer len, StringMember a) {
			return a.v().regionMatches(toffset, other.v(), ooffset, len);
		}
	};
	
	public Function5<Boolean,Integer,StringMember,Integer,Integer,StringMember> regionMatches() {
		return RMATCH;
	}
	
	private final Function6<Boolean,Boolean,Integer,StringMember,Integer,Integer,StringMember> RMATCHCASE =
			new Function6<Boolean, Boolean, Integer, StringMember, Integer, Integer, StringMember>()
	{
		@Override
		public Boolean call(Boolean ignoreCase, Integer toffset, StringMember other, Integer ooffset, Integer len, StringMember a) {
			return a.v().regionMatches(ignoreCase, toffset, other.v(), ooffset, len);
		}
	};
	
	public Function6<Boolean,Boolean,Integer,StringMember,Integer,Integer,StringMember> regionMatchesWithCase() {
		return RMATCHCASE;
	}

	private static final Function2<StringMember,StringMember,StringMember[]> JOIN =
			new Function2<StringMember, StringMember, StringMember[]>()
	{
		@Override
		public StringMember call(StringMember delimeter, StringMember[] elements) {
			String[] strings = new String[elements.length];
			for (int i = 0; i < elements.length; i++) {
				strings[i] = elements[i].v();
			}
			return new StringMember(String.join(delimeter.v(), strings));
		}
	};
	
	public static Function2<StringMember, StringMember, StringMember[]> join() {
		return JOIN;
	}
}
