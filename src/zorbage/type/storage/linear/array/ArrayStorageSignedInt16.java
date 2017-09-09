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

import zorbage.type.data.int16.SignedInt16Member;
import zorbage.type.storage.linear.LinearStorage;
import zorbage.util.Fraction;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageSignedInt16
	implements LinearStorage<ArrayStorageSignedInt16,SignedInt16Member>
{

	private final short[] data;
	
	public static final Fraction BYTESIZE = new Fraction(2);
	
	public ArrayStorageSignedInt16(long size) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageSignedInt16 cannot handle a negative request");
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorageSignedInt16 can handle at most " + Integer.MAX_VALUE + " signedint16s");
		this.data = new short[(int)size];
	}

	@Override
	public void set(long index, SignedInt16Member value) {
		data[(int)index] = value.v();
	}

	@Override
	public void get(long index, SignedInt16Member value) {
		value.setV(data[(int)index]);
	}
	
	@Override
	public long size() {
		return data.length;
	}

	@Override
	public ArrayStorageSignedInt16 duplicate() {
		ArrayStorageSignedInt16 s = new ArrayStorageSignedInt16(size());
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

	@Override
	public Fraction elementSize() {
		return BYTESIZE;
	}

}
