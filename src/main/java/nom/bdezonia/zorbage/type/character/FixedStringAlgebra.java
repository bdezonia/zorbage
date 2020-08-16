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
import nom.bdezonia.zorbage.type.character.FixedStringMember;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FixedStringAlgebra
	implements
		Algebra<FixedStringAlgebra, FixedStringMember>,
		Addition<FixedStringMember>,
		Norm<FixedStringMember, SignedInt32Member>,
		Ordered<FixedStringMember>
{
	@Override
	public FixedStringMember construct() {
		return new FixedStringMember();
	}

	@Override
	public FixedStringMember construct(FixedStringMember other) {
		return other.duplicate();
	}

	@Override
	public FixedStringMember construct(String str) {
		return new FixedStringMember(str);
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> EQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) == 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> NEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) != 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<FixedStringMember, FixedStringMember> ASSIGN =
			new Procedure2<FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b) {
			a.setV(b.v());
		}
	};
	
	@Override
	public Procedure2<FixedStringMember, FixedStringMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<FixedStringMember> ZERO =
			new Procedure1<FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a) {
			a.setV("");
		}
	};
			
	@Override
	public Procedure1<FixedStringMember> zero() {
		return ZERO;
	}

	private final Function1<Boolean, FixedStringMember> ISZERO =
			new Function1<Boolean, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a) {
			return ISEMPTY.call(a);
		}
	};

	@Override
	public Function1<Boolean, FixedStringMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure2<FixedStringMember, FixedStringMember> negate() {
		return ASSIGN;  // ignore negations: unsigned numbers do similar things
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> ADD =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			c.setV(a.v() + b.v());
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> add() {
		return ADD;
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> SUB =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			String aV = a.v();
			String bV = b.v();
			if (aV.endsWith(bV)) {
				int offset = aV.lastIndexOf(bV);
				c.setV(aV.substring(0, offset));
			}
			else {
				c.setV(a.v());
			}
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> LESS =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isLess() {
		return LESS;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> LESSEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isLessEqual() {
		return LESSEQ;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> GREAT =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> GREATEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isGreaterEqual() {
		return GREATEQ;
	}

	private final Function2<Integer, FixedStringMember, FixedStringMember> CMP =
			new Function2<Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember a, FixedStringMember b) {
			return a.v().compareTo(b.v());
		}
	};

	@Override
	public Function2<Integer, FixedStringMember, FixedStringMember> compare() {
		return CMP;
	}

	private final Function1<Integer, FixedStringMember> SIGNUM =
			new Function1<Integer, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember a) {
			if (a.v().length() == 0)
				return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<Integer, FixedStringMember> signum() {
		return SIGNUM;
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> MIN =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			if (isLess().call(a, b))
				c.setV(a.v());
			else
				c.setV(b.v());
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> min() {
		return MIN;
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> MAX =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			if (isGreater().call(a, b))
				c.setV(a.v());
			else
				c.setV(b.v());
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> max() {
		return MAX;
	}

	private final Procedure2<FixedStringMember, SignedInt32Member> LEN =
			new Procedure2<FixedStringMember, SignedInt32Member>()
	{
		@Override
		public void call(FixedStringMember a, SignedInt32Member b) {
			b.setV(a.codePointCount());
		}
	};

	public Procedure2<FixedStringMember, SignedInt32Member> length() {
		return LEN;
	}

	@Override
	public Procedure2<FixedStringMember, SignedInt32Member> norm() {
		return LEN;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> EQIGCASE =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return a.v().equalsIgnoreCase(b.v());
		}
	};
	
	public Function2<Boolean, FixedStringMember, FixedStringMember> isEqualIgnoreCase() {
		return EQIGCASE;
	}

	private final Function2<Integer, FixedStringMember, FixedStringMember> CMPIGCASE =
			new Function2<Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember a, FixedStringMember b) {
			return a.v().compareToIgnoreCase(b.v());
		}
	};

	public Function2<Integer, FixedStringMember, FixedStringMember> compareIgnoreCase() {
		return CMPIGCASE;
	}
	
	private final Function2<Integer, Integer, FixedStringMember> GCP =
			new Function2<Integer, Integer, FixedStringMember>()
	{
		@Override
		public Integer call(Integer i, FixedStringMember a) {
			return a.codePointAt(i);
		}
	};

	public Function2<Integer, Integer, FixedStringMember> codePointAt() {
		return GCP;
	}
	
	private final Function1<Integer, FixedStringMember> GCPC =
			new Function1<Integer, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember a) {
			return a.codePointCount();
		}
	};

	public Function1<Integer, FixedStringMember> codePointCount() {
		return GCPC;
	}
	
	private final Function2<Boolean, FixedStringMember, FixedStringMember> ENDSWITH =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember suffix, FixedStringMember a) {
			return a.v().endsWith(suffix.v());
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> endsWith() {
		return ENDSWITH;
	}
	
	private final Function2<Boolean, FixedStringMember, FixedStringMember> STARTSWITH =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember prefix, FixedStringMember a) {
			return a.v().startsWith(prefix.v());
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> startsWith() {
		return STARTSWITH;
	}
	
	private final Procedure2<FixedStringMember, FixedStringMember> TRIM =
			new Procedure2<FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().trim());
		}
	};
	
	public Procedure2<FixedStringMember, FixedStringMember> trim() {
		return TRIM;
	}

	private final Procedure2<FixedStringMember, FixedStringMember> TOLOWER =
			new Procedure2<FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().toLowerCase());
		}
	};

	public Procedure2<FixedStringMember, FixedStringMember> toLower() {
		return TOLOWER;
	}
	
	private final Procedure2<FixedStringMember, FixedStringMember> TOUPPER =
			new Procedure2<FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().toUpperCase());
		}
	};

	public Procedure2<FixedStringMember, FixedStringMember> toUpper() {
		return TOUPPER;
	}
	
	private final Procedure3<Locale, FixedStringMember, FixedStringMember> TOLOWERL =
			new Procedure3<Locale, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(Locale locale, FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().toLowerCase(locale));
		}
	};

	public Procedure3<Locale, FixedStringMember, FixedStringMember> toLowerWithLocale() {
		return TOLOWERL;
	}
	
	private final Procedure3<Locale, FixedStringMember, FixedStringMember> TOUPPERL =
			new Procedure3<Locale, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(Locale locale, FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().toUpperCase(locale));
		}
	};

	public Procedure3<Locale, FixedStringMember, FixedStringMember> toUpperWithLocale() {
		return TOUPPERL;
	}

	private final Function1<Boolean, FixedStringMember> ISEMPTY =
			new Function1<Boolean, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a) {
			return a.codePointCount() == 0;
		}
	};

	public Function1<Boolean, FixedStringMember> isEmpty() {
		return ISEMPTY;
	}
	
	private final Function2<Boolean, FixedStringMember, FixedStringMember> CONTAINS =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return b.v().contains(a.v());
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> contains() {
		return CONTAINS;
	}
	
	private final Procedure4<Integer, Integer, FixedStringMember, FixedStringMember> SUBSTR2 =
			new Procedure4<Integer, Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(Integer start, Integer end, FixedStringMember a, FixedStringMember b) {
			if (start < 0 || start >= a.codePointCount())
				throw new IllegalArgumentException("start offset out of string bounds");
			if (end < 0 || end >= a.codePointCount() || end < start)
				throw new IllegalArgumentException("end offset out of string bounds");
			for (int ai = start, bi = 0;
					ai < end && ai < a.codePointCount() && bi < b.codePointCount();
					ai++, bi++)
			{
				int cp = a.codePointAt(ai);
				b.setCodePointAt(bi, cp);
				if (cp == 0) { // NUL
					break;
				}
			}
			if (b.codePointCount() > a.codePointCount()) {
				b.setCodePointAt(end - start, 0); // NUL
			}
		}
	};

	public Procedure4<Integer, Integer, FixedStringMember, FixedStringMember> substringFromStartToEnd() {
		return SUBSTR2;
	}

	private final Procedure3<Integer, FixedStringMember, FixedStringMember> SUBSTR =
			new Procedure3<Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(Integer start, FixedStringMember a, FixedStringMember b) {
			substringFromStartToEnd().call(start, a.codePointCount(), a, b);
		}
	};

	public Procedure3<Integer, FixedStringMember, FixedStringMember> subStringFromStart() {
		return SUBSTR;
	}

	private final Function2<Integer, Integer, FixedStringMember> IDXOF =
			new Function2<Integer, Integer, FixedStringMember>()
	{
		@Override
		public Integer call(Integer codePoint, FixedStringMember a) {
			return indexOfFrom().call(codePoint, 0, a);
		}
	};
	
	public Function2<Integer, Integer, FixedStringMember> indexOf() {
		return IDXOF;
	}

	private final Function3<Integer, Integer, Integer, FixedStringMember> IDXOFFROM =
			new Function3<Integer, Integer, Integer, FixedStringMember>()
	{
		@Override
		public Integer call(Integer codePoint, Integer from, FixedStringMember a) {
			for (int i = from; i < a.codePointCount(); i++) {
				int cp = a.codePointAt(i);
				if (cp == 0)
					return -1;
				if (cp == codePoint)
					return i;
			}
			return -1;
		}
	};
	
	public Function3<Integer, Integer, Integer, FixedStringMember> indexOfFrom() {
		return IDXOFFROM;
	}

	private final Function2<Integer, Integer, FixedStringMember> LASTIDXOF =
			new Function2<Integer, Integer, FixedStringMember>()
	{
		@Override
		public Integer call(Integer codePoint, FixedStringMember a) {
			return lastIndexOfFrom().call(codePoint, a.codePointCount()-1, a);
		}
	};
	
	public Function2<Integer, Integer, FixedStringMember> lastIndexOf() {
		return LASTIDXOF;
	}

	private final Function3<Integer, Integer, Integer, FixedStringMember> LASTIDXOFFROM =
			new Function3<Integer, Integer, Integer, FixedStringMember>()
	{
		@Override
		public Integer call(Integer codePoint, Integer from, FixedStringMember a) {
			int firstNull = a.codePointCount();
			for (int i = 0; i < a.codePointCount(); i++) {
				if (a.codePointAt(i) == 0) {
					firstNull = i;
					break;
				}
			}
			if (firstNull <= from)
				from = firstNull - 1;
			for (int i = from; i >= 0; i--) {
				int cp = a.codePointAt(i);
				if (cp == 0)
					return -1;
				if (cp == codePoint)
					return i;
			}
			return -1;
		}
	};
	
	public Function3<Integer, Integer, Integer, FixedStringMember> lastIndexOfFrom() {
		return LASTIDXOFFROM;
	}

	private final Procedure4<Integer, Integer, FixedStringMember, FixedStringMember> REPLACE =
			new Procedure4<Integer, Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(Integer oldCP, Integer newCP, FixedStringMember a, FixedStringMember b) {
			if (oldCP == 0 || newCP == 0)
				throw new IllegalArgumentException("replace method does not allow NUL char manipulations");
			assign().call(a, b);
			for (int i = 0; i < b.codePointCount(); i++) {
				if (b.codePointAt(i) == oldCP)
					b.setCodePointAt(i, newCP);
			}
		}
	};

	public Procedure4<Integer, Integer, FixedStringMember, FixedStringMember> replace() {
		return REPLACE;
	}
	
	private final Function1<byte[], FixedStringMember> GETBYTES =
			new Function1<byte[], FixedStringMember>()
	{
		@Override
		public byte[] call(FixedStringMember a) {
			return a.toString().getBytes();
		}
	};

	public Function1<byte[], FixedStringMember> getBytes() {
		return GETBYTES;
	}
	
	private final Function2<byte[], Charset, FixedStringMember> GETBYTESCHARSET =
			new Function2<byte[], Charset, FixedStringMember>()
	{
		@Override
		public byte[] call(Charset charset, FixedStringMember a) {
			return a.toString().getBytes(charset);
		}
	};

	public Function2<byte[], Charset, FixedStringMember> getBytesUsingCharset() {
		return GETBYTESCHARSET;
	}

	private final Function2<byte[],String, FixedStringMember> GETBYTESSTRING =
			new Function2<byte[], String, FixedStringMember>()
	{
		@Override
		public byte[] call(String charsetName, FixedStringMember a) {
			byte[] bytes;
			try {
				bytes = a.toString().getBytes(charsetName);
			} catch (Exception e) {
				bytes = new byte[0];
			}
			return bytes;
		}
	};

	public Function2<byte[], String, FixedStringMember> getBytesUsingCharsetName() {
		return GETBYTESSTRING;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> LESSIGCASE =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compareIgnoreCase().call(a, b) < 0;
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> isLessIgnoreCase() {
		return LESSIGCASE;
	}
	
	private final Function2<Boolean, FixedStringMember, FixedStringMember> LESSEQIGCASE =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compareIgnoreCase().call(a, b) <= 0;
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> isLessEqualIgnoreCase() {
		return LESSEQIGCASE;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> GREATIGCASE =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compareIgnoreCase().call(a, b) > 0;
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> isGreaterIgnoreCase() {
		return GREATIGCASE;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> GREATEQIGCASE =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compareIgnoreCase().call(a, b) >= 0;
		}
	};

	public Function2<Boolean, FixedStringMember, FixedStringMember> isGreaterEqualIgnoreCase() {
		return GREATEQIGCASE;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> NEQIGCASE =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return !isEqualIgnoreCase().call(a, b);
		}
	};
	
	public Function2<Boolean, FixedStringMember, FixedStringMember> isNotEqualIgnoreCase() {
		return NEQIGCASE;
	}

	private final Procedure3<FixedStringMember,FixedStringMember,FixedStringMember> CONCAT =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			c.setV(a.v().concat(b.v()));
		}
	};

	public Procedure3<FixedStringMember,FixedStringMember,FixedStringMember> concat() {
		return CONCAT;
	}
	
	private final Function2<FixedStringMember[], FixedStringMember, FixedStringMember> SPLIT =
			new Function2<FixedStringMember[], FixedStringMember, FixedStringMember>()
	{
		@Override
		public FixedStringMember[] call(FixedStringMember regex, FixedStringMember a) {
			String[] splits = a.v().split(regex.v());
			FixedStringMember[] members = new FixedStringMember[splits.length];
			for (int i = 0; i < splits.length; i++) {
				members[i] = new FixedStringMember(splits[i]);
			}
			return members;
		}
	};

	public Function2<FixedStringMember[], FixedStringMember, FixedStringMember> split() {
		return SPLIT;
	}

	private final Function3<FixedStringMember[], Integer, FixedStringMember, FixedStringMember> SPLITLIM =
			new Function3<FixedStringMember[], Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public FixedStringMember[] call(Integer limit, FixedStringMember regex, FixedStringMember a) {
			String[] splits = a.v().split(regex.v(), limit);
			FixedStringMember[] members = new FixedStringMember[splits.length];
			for (int i = 0; i < splits.length; i++) {
				members[i] = new FixedStringMember(splits[i]);
			}
			return members;
		}
	};

	public Function3<FixedStringMember[], Integer, FixedStringMember, FixedStringMember> splitWithLimit() {
		return SPLITLIM;
	}

	private final Function1<char[], FixedStringMember> TOCHARS =
			new Function1<char[], FixedStringMember>()
	{
		@Override
		public char[] call(FixedStringMember a) {
			return a.v().toCharArray();
		}
	};

	public Function1<char[], FixedStringMember> toCharArray() {
		return TOCHARS;
	}

	private static final Function2<FixedStringMember,FixedStringMember,FixedStringMember[]> JOIN =
			new Function2<FixedStringMember, FixedStringMember, FixedStringMember[]>()
	{
		@Override
		public FixedStringMember call(FixedStringMember delimeter, FixedStringMember[] elements) {
			String[] strings = new String[elements.length];
			for (int i = 0; i < elements.length; i++) {
				strings[i] = elements[i].v();
			}
			return new FixedStringMember(String.join(delimeter.v(), strings));
		}
	};
	
	private final Function2<Integer, FixedStringMember, FixedStringMember> INDEXOF =
			new Function2<Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember substring, FixedStringMember a) {
			return a.v().indexOf(substring.v());
		}
	};

	public Function2<Integer, FixedStringMember, FixedStringMember> indexOfCodePoints()  {
		return INDEXOF;
	}
	
	private final Function3<Integer, Integer, FixedStringMember, FixedStringMember> INDEXOFFROM =
			new Function3<Integer, Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(Integer from, FixedStringMember substring, FixedStringMember a) {
			return a.v().indexOf(substring.v(), from);
		}
	};
	
	public Function3<Integer, Integer, FixedStringMember, FixedStringMember> indexOfCodePointsFrom()  {
		return INDEXOFFROM;
	}
	
	private final Function2<Integer, FixedStringMember, FixedStringMember> LASTINDEXOF =
			new Function2<Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember substring, FixedStringMember a) {
			return a.v().lastIndexOf(substring.v());
		}
	};
	
	public Function2<Integer, FixedStringMember, FixedStringMember> lastIndexOfCodePoints()  {
		return LASTINDEXOF;
	}
	
	private final Function3<Integer, Integer, FixedStringMember, FixedStringMember> LASTINDEXOFFROM =
			new Function3<Integer, Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(Integer from, FixedStringMember substring, FixedStringMember a) {
			return a.v().lastIndexOf(substring.v(), from);
		}
	};
	
	public Function3<Integer, Integer, FixedStringMember, FixedStringMember> lastIndexOfCodePointsFrom()  {
		return LASTINDEXOFFROM;
	}
	
	public static Function2<FixedStringMember, FixedStringMember, FixedStringMember[]> join() {
		return JOIN;
	}

	private final Procedure4<FixedStringMember,FixedStringMember,FixedStringMember,FixedStringMember> REPLACECP =
			new Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember fromCodePoints, FixedStringMember toCodePoints, FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().replace(fromCodePoints.v(), toCodePoints.v()));
		}
	};
	
	public Procedure4<FixedStringMember,FixedStringMember,FixedStringMember,FixedStringMember> replaceCodePoints() {
		return REPLACECP;
	}
	
	private final Function2<Boolean,FixedStringMember,FixedStringMember> CEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember content, FixedStringMember a) {
			return a.v().contentEquals(content.v());
		}
	};

	public Function2<Boolean,FixedStringMember,FixedStringMember> contentEquals() {
		return CEQ;
	}
	
	private final Function5<Boolean,Integer,FixedStringMember,Integer,Integer,FixedStringMember> RMATCH =
			new Function5<Boolean, Integer, FixedStringMember, Integer, Integer, FixedStringMember>()
	{
		@Override
		public Boolean call(Integer toffset, FixedStringMember other, Integer ooffset, Integer len, FixedStringMember a) {
			return a.v().regionMatches(toffset, other.v(), ooffset, len);
		}
	};
	
	public Function5<Boolean,Integer,FixedStringMember,Integer,Integer,FixedStringMember> regionMatches() {
		return RMATCH;
	}
	
	private final Function6<Boolean,Boolean,Integer,FixedStringMember,Integer,Integer,FixedStringMember> RMATCHCASE =
			new Function6<Boolean, Boolean, Integer, FixedStringMember, Integer, Integer, FixedStringMember>()
	{
		@Override
		public Boolean call(Boolean ignoreCase, Integer toffset, FixedStringMember other, Integer ooffset, Integer len, FixedStringMember a) {
			return a.v().regionMatches(ignoreCase, toffset, other.v(), ooffset, len);
		}
	};
	
	public Function6<Boolean,Boolean,Integer,FixedStringMember,Integer,Integer,FixedStringMember> regionMatchesWithCase() {
		return RMATCHCASE;
	}

	private final Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember> REPLACEALL =
			new Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember regex, FixedStringMember replacement, FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().replaceAll(regex.v(), replacement.v()));
		}
	};
	
	public Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember> replaceAll() {
		return REPLACEALL;
	}

	private final Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember> REPLACEFIRST =
			new Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember regex, FixedStringMember replacement, FixedStringMember a, FixedStringMember b) {
			b.setV(a.v().replaceFirst(regex.v(), replacement.v()));
		}
	};
	
	public Procedure4<FixedStringMember, FixedStringMember, FixedStringMember, FixedStringMember> replaceFirst() {
		return REPLACEFIRST;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> MATCHES =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember regex, FixedStringMember a) {
			return a.v().matches(regex.v());
		}
	};
	
	public Function2<Boolean, FixedStringMember, FixedStringMember> matches() {
		return MATCHES;
	}
}
