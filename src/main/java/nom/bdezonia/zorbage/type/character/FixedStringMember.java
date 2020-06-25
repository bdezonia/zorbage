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


import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.storage.coder.IntCoder;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class FixedStringMember
	implements
		IntCoder, Allocatable<FixedStringMember>, Duplicatable<FixedStringMember>,
		Settable<FixedStringMember>, Gettable<FixedStringMember>
{
	private int maxLen;
	private String v;
	
	public FixedStringMember(int len, String s) {
		if (len < 0)
			throw new IllegalArgumentException("max string length must be >= 0");
		maxLen = len;
		setV(s);
	}

	public FixedStringMember(int len) {
		this(len, "");
	}

	public FixedStringMember() {
		this(0);
	}
	
	public FixedStringMember(String value) {
		this(value.codePointCount(0, value.length()), value);
	}
	
	public FixedStringMember(FixedStringMember value) {
		this(value.maxLen, value.v);
	}
	
	public String v() {
		return v;
	}
	
	public void setV(String val) {
		String st = chars(val);
		if (maxLen < st.codePointCount(0, st.length())) {
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < maxLen; i++)
				b.appendCodePoint(st.codePointAt(i));
			v = b.toString();
		}
		else {
			v = val;
		}
	}
	
	@Override
	public void set(FixedStringMember other) {
		setV(other.v());
	}
	
	@Override
	public void get(FixedStringMember other) {
		other.setV(v());
	}

	@Override
	public String toString() {
		return v;
	}

	@Override
	public int intCount() {
		return maxLen + 1;
	}

	@Override
	public void fromIntArray(int[] arr, int index) {
		maxLen = arr[index];
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < maxLen; i++) {
			int ch = arr[index+1+i];
			if (ch == 0) // NUL
				break;
			b.appendCodePoint(ch);
		}
		v = b.toString();
	}

	@Override
	public void toIntArray(int[] arr, int index) {
		arr[index] = maxLen;
		int len = v.codePointCount(0, v.length());
		for (int i = 0; i < len && i < maxLen; i++) {
			arr[index+1+i] = v.codePointAt(i);
		}
		// NUL terminate if necessary
		if (len < maxLen)
			arr[index+1+len] = 0;
	}

	@Override
	public FixedStringMember allocate() {
		return new FixedStringMember(maxLen);
	}

	@Override
	public FixedStringMember duplicate() {
		return new FixedStringMember(this);
	}

	public void getV(FixedStringMember value) {
		get(value);
	}

	public void setV(FixedStringMember value) {
		set(value);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(this.v);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof FixedStringMember) {
			return G.FSTRING.isEqual().call(this, (FixedStringMember) o);
		}
		return false;
	}
	
	private String chars(String value) {
		StringBuilder b = new StringBuilder();
		int len = value.codePointCount(0, value.length());
		for (int i = 0; i < len; i++) {
			int ch = value.codePointAt(i);
			if (ch == 0) // NUL
				return b.toString();
			b.appendCodePoint(ch);
		}
		return b.toString();
	}
}
