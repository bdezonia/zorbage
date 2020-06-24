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
	
	public FixedStringMember(int len) {
		maxLen = len;
		v = "";
	}

	public FixedStringMember() {
		this(0);
	}
	
	public FixedStringMember(String value) {
		maxLen = value.length();
		v = value;
	}
	
	public FixedStringMember(FixedStringMember value) {
		maxLen = value.maxLen;
		v = value.v;
	}
	
	public String v() {
		return chars(v);
	}
	
	public void setV(String val) {
		if (maxLen < val.length()) {
			v = val.substring(0, maxLen);
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
		return chars(v);
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
			if (arr[index+1+i] != 0)
				b.append((char) arr[index+1+i]);
		}
		v = b.toString();
	}

	@Override
	public void toIntArray(int[] arr, int index) {
		arr[index] = maxLen;
		String chs = chars(v);
		for (int i = 0; i < chs.length() && i < maxLen; i++) {
			arr[index+1+i] = chs.charAt(i);
		}
		for (int i = chs.length(); i < maxLen; i++) {
			arr[index+1+i] = 0;
		}
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
		v = Hasher.PRIME * v + Hasher.hashCode(chars(this.v));
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
		int len = value.length();
		for (int i = 0; i < len; i++) {
			char ch = value.charAt(i);
			if (ch == 0)
				return b.toString();
			else
				b.append(ch);
		}
		return b.toString();
	}
}
