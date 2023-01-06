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
package nom.bdezonia.zorbage.type.string;


import nom.bdezonia.zorbage.algebra.*;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class StringMember
	implements
		Allocatable<StringMember>, Duplicatable<StringMember>,
		Settable<StringMember>, Gettable<StringMember>,
		SetFromString, GetAsString, GetAsStringArray, NativeStringSupport
{
	private String v;
	
	public StringMember() {
		setV(null);
	}
	
	public StringMember(String value) {
		setV(value);
	}
	
	public StringMember(StringMember value) {
		set(value);
	}
	
	public String v() {
		return v;
	}
	
	public void setV(String val) {
		if (val == null)
			v = "";
		else
			v = val;
	}
	
	@Override
	public void set(StringMember other) {
		v = other.v;
	}
	
	@Override
	public void get(StringMember other) {
		other.v = v;
	}

	@Override
	public String toString() {
		return v;
	}

	@Override
	public StringMember allocate() {
		return new StringMember();
	}

	@Override
	public StringMember duplicate() {
		return new StringMember(this);
	}

	@Override
	public int hashCode() {
		return v.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof StringMember) {
			return G.STRING.isEqual().call(this, (StringMember) o);
		}
		return false;
	}
	
	public int[] codePoints() {
		return v.codePoints().toArray();
	}

	@Override
	public void setFromString(String... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public String[] getAsStringArray() {
		return new String[] {v()};
	}

	@Override
	public String getAsString() {
		return v();
	}

	@Override
	public String getNative(int component) {

		return v();
	}

	@Override
	public void setNative(int component, String val) {

		setV(val);
	}

	@Override
	public String minNative() {

		return null;  // there is no min string
	}

	@Override
	public String maxNative() {

		return null;  // there is no max string
	}

	@Override
	public int componentCount() {

		return 1;
	}
}
