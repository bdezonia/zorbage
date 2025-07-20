/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.character;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAlgebra;
import nom.bdezonia.zorbage.algebra.GetAsChar;
import nom.bdezonia.zorbage.algebra.GetAsCharArray;
import nom.bdezonia.zorbage.algebra.GetAsCharArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsCharExact;
import nom.bdezonia.zorbage.algebra.GetAsString;
import nom.bdezonia.zorbage.algebra.GetAsStringArray;
import nom.bdezonia.zorbage.algebra.GetAsStringArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsStringExact;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.NativeCharSupport;
import nom.bdezonia.zorbage.algebra.SetFromChars;
import nom.bdezonia.zorbage.algebra.SetFromCharsExact;
import nom.bdezonia.zorbage.algebra.SetFromStrings;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.type.markers.BoundedType;
import nom.bdezonia.zorbage.algebra.type.markers.CharType;
import nom.bdezonia.zorbage.algebra.type.markers.EnumerableType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.storage.coder.CharCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CharMember
	implements
		CharCoder, Gettable<CharMember>, Settable<CharMember>,
		Allocatable<CharMember>, Duplicatable<CharMember>,
		SetFromChars, GetAsChar, GetAsCharArray, NativeCharSupport,
		SetFromCharsExact, GetAsString, GetAsStringArray,
		GetAsCharExact, GetAsCharArrayExact,
		GetAsStringExact, GetAsStringArrayExact,
		SetFromStrings,
		GetAlgebra<CharAlgebra, CharMember>,
		BoundedType,
		CharType,
		EnumerableType,
		ExactType,
		FixedSize,
		ZeroIncludedType
{
	private char v;
	
	public CharMember() {
		v = 0;
	}
	
	public CharMember(char... vals) {
		setFromChars(vals);
	}
	
	public CharMember(CharMember other) {
		set(other);
	}
	
	public CharMember(String str) {
		if (str == null || str.length() == 0)
			v = 0;
		else
			v = str.charAt(0);
	}
	
	@Override
	public void set(CharMember ch) {
		v = ch.v;
	}
	
	@Override
	public void get(CharMember ch) {
		ch.v = v;
	}
	
	public char v() {
		return v;
	}
	
	public void setV(char ch) {
		v = ch;
	}

	@Override
	public int charCount() {
		return 1;
	}

	@Override
	public void fromCharArray(char[] arr, int index) {
		v = arr[index];
	}

	@Override
	public void toCharArray(char[] arr, int index) {
		arr[index] = v;
	}

	@Override
	public CharMember duplicate() {
		return new CharMember(this);
	}

	@Override
	public CharMember allocate() {
		return new CharMember();
	}

	@Override
	public String toString() {
		return Character.toString(v);
	}
	
	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(this.v);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CharMember) {
			return G.CHAR.isEqual().call(this, (CharMember) o);
		}
		return false;
	}

	@Override
	public char getAsChar() {
		return v();
	}

	@Override
	public void setFromCharsExact(char... vals) {
		setFromChars(vals);
	}
	
	@Override
	public void setFromChars(char... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public char[] getAsCharArray() {
		return new char[] {v()};
	}

	@Override
	public char getNative(int component) {

		return v();
	}

	@Override
	public void setNative(int component, char val) {

		setV(val);
	}

	@Override
	public int componentCount() {

		return 1;
	}

	@Override
	public Character componentMin() {

		return null;  // I don't think characters have a min
	}

	@Override
	public Character componentMax() {

		return null;  // I don't think characters have a max
	}

	@Override
	public String[] getAsStringArray() {
		return new String[] { getAsString() };
	}

	@Override
	public String getAsString() {
		return String.valueOf(v);
	}

	@Override
	public void setFromStrings(String... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		String s = vals[0];
		if (s.length() == 0)
			v = 0;
		else
			v = s.charAt(0);
	}

	@Override
	public String[] getAsStringArrayExact() {
		return getAsStringArray();
	}

	@Override
	public String getAsStringExact() {
		return getAsString();
	}

	@Override
	public char[] getAsCharArrayExact() {
		return getAsCharArray();
	}

	@Override
	public char getAsCharExact() {
		return getAsChar();
	}
	
	@Override
	public CharAlgebra getAlgebra() {

		return G.CHAR;
	}
}
