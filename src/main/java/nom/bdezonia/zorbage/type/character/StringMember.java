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

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class StringMember
	implements
		Allocatable<StringMember>, Duplicatable<StringMember>,
		Settable<StringMember>, Gettable<StringMember>
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
		if (this == o)
			return true;
		if (o instanceof StringMember) {
			return G.STRING.isEqual().call(this, (StringMember) o);
		}
		return false;
	}
}
