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

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.storage.coder.CharCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CharMember
	implements
		CharCoder, Gettable<CharMember>, Settable<CharMember>, Allocatable<CharMember>, Duplicatable<CharMember>
{
	private char v;
	
	public CharMember() {
		v = 0;
	}
	
	public CharMember(char ch) {
		v = ch;
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
}
