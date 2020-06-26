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
	private int[] codePoints;
	
	public FixedStringMember(int len, String s) {
		if (len < 0)
			throw new IllegalArgumentException("max string length must be >= 0");
		codePoints = new int[len];
		setV(s);
	}

	public FixedStringMember(int len) {
		this(len, "");
	}

	public FixedStringMember() {
		this(0);
	}
	
	public FixedStringMember(String value) {
		this(value.codePointCount(0, value.length()));
		setV(value);
	}
	
	public FixedStringMember(FixedStringMember value) {
		this(value.codePoints.length);
		set(value);
	}
	
	public int getCodePointCount() {
		for (int i = 0; i < codePoints.length; i++) {
			if (codePoints[i] == 0) { // NUL
				return i;
			}
		}
		return codePoints.length;
	}

	public int getCodePoint(int i) {
		if (i < 0 || i >= codePoints.length)
			throw new IllegalArgumentException("code point index out of bounds");
		for (int n = 0; n <= i; n++) {
			if (codePoints[n] == 0) // if a NUL found
				return 0;  // treat all subsequent codepoints as NUL
		}
		return codePoints[i];
	}
	
	public String v() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < codePoints.length; i++) {
			int cp = codePoints[i];
			if (cp == 0) { // NUL
				return b.toString();
			}
			b.appendCodePoint(cp);
		}
		return b.toString();
	}
	
	public void setV(String val) {
		int codePointCount = val.codePointCount(0, val.length());
		for (int i = 0; i < codePointCount && i < codePoints.length; i++) {
			int cp = val.codePointAt(i);
			codePoints[i] = cp;
			if (cp == 0) { //  NUL
				break;
			}
		}
		if (codePointCount < codePoints.length) {
			codePoints[codePointCount] = 0; // NUL
		}
	}
	
	@Override
	public void set(FixedStringMember other) {
		for (int i = 0; i < codePoints.length && i < other.codePoints.length; i++) {
			codePoints[i] = other.codePoints[i];
		}
		if (codePoints.length > other.codePoints.length) {
			codePoints[other.codePoints.length] = 0; // NUL
		}
	}
	
	@Override
	public void get(FixedStringMember other) {
		for (int i = 0; i < codePoints.length && i < other.codePoints.length; i++) {
			other.codePoints[i] = codePoints[i];
		}
		if (other.codePoints.length > codePoints.length) {
			other.codePoints[codePoints.length] = 0; // NUL
		}
	}

	@Override
	public String toString() {
		return v();
	}

	@Override
	public int intCount() {
		return codePoints.length + 1;
	}

	@Override
	public void fromIntArray(int[] arr, int index) {
		if (codePoints.length != arr[index]) {
			codePoints = new int[arr[index]];
		}
		for (int i = 0; i < codePoints.length; i++) {
			int cp = arr[index+1+i];
			codePoints[i] = cp;
			if (cp == 0) { // NUL 
				break;
			}
		}
	}

	@Override
	public void toIntArray(int[] arr, int index) {
		arr[index] = codePoints.length;
		for (int i = 0; i < codePoints.length; i++) {
			int cp = codePoints[i];
			arr[index+1+i] = cp;
			if (cp == 0) { // NUL
				break;
			}
		}
	}

	@Override
	public FixedStringMember allocate() {
		return new FixedStringMember(codePoints.length);
	}

	@Override
	public FixedStringMember duplicate() {
		return new FixedStringMember(this);
	}

	@Override
	public int hashCode() {
		int v = 1;
		for (int i = 0; i < codePoints.length; i++) {
			int cp = codePoints[i];
			v = Hasher.PRIME * v + Hasher.hashCode(cp);
			if (cp == 0) { // NUL
				break;
			}
		}
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
}
