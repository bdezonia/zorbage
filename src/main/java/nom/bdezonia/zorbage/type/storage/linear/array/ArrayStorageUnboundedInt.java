/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage.linear.array;

import java.math.BigInteger;

import nom.bdezonia.zorbage.type.data.bigint.UnboundedIntMember;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageUnboundedInt
	implements LinearStorage<ArrayStorageUnboundedInt,UnboundedIntMember>
{
	private final BigInteger[] data;
	
	public ArrayStorageUnboundedInt(long size) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageUnboundedInt cannot handle a negative request");
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorageUnboundedInt can handle at most " + Integer.MAX_VALUE + " unbounded integers");
		this.data = new BigInteger[(int)size];
		for (int i = 0; i < data.length; i++) {
			data[i] = BigInteger.ZERO;
		}
	}

	@Override
	public void set(long index, UnboundedIntMember value) {
		data[(int)index] = value.v();
	}

	@Override
	public void get(long index, UnboundedIntMember value) {
		value.setV(data[(int)index]);
	}
	
	@Override
	public long size() {
		return data.length;
	}

	@Override
	public ArrayStorageUnboundedInt duplicate() {
		ArrayStorageUnboundedInt s = new ArrayStorageUnboundedInt(size());
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

}
