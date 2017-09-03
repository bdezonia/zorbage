/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.storage.linear.array;

import java.math.BigInteger;

import zorbage.type.data.BooleanMember;
import zorbage.type.storage.LinearStorage;
import zorbage.util.Fraction;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageBoolean
	implements LinearStorage<ArrayStorageBoolean,BooleanMember>
{

	private final boolean[] data;
	
	public static final Fraction BYTESIZE = new Fraction(1);
	
	public ArrayStorageBoolean(long size) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageBoolean cannot handle a negative request");
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorageBoolean can handle at most " + Integer.MAX_VALUE + " booleans");
		this.data = new boolean[(int)size];
	}

	@Override
	public void set(long index, BooleanMember value) {
		data[(int)index] = value.v();
	}

	@Override
	public void get(long index, BooleanMember value) {
		value.setV(data[(int)index]);
	}
	
	@Override
	public long size() {
		return data.length;
	}

	@Override
	public ArrayStorageBoolean duplicate() {
		ArrayStorageBoolean s = new ArrayStorageBoolean(size());
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

	@Override
	public Fraction elementSize() {
		return BYTESIZE;
	}

}
