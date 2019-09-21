/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.storage.array;

import java.math.BigInteger;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageBigInteger<U extends BigIntegerCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<ArrayStorageBigInteger<U>>
{
	private final U type;
	private final BigInteger[] data;

	public ArrayStorageBigInteger(long size, U type) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageBigInteger cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / type.bigIntegerCount()))
			throw new IllegalArgumentException("ArrayStorageBigInteger can handle at most " + (Integer.MAX_VALUE / type.bigIntegerCount()) + " BigInteger based entities");
		this.type = type.allocate();
		this.data = new BigInteger[(int)size * type.bigIntegerCount()];
		for (int i = 0; i < data.length; i++) {
			data[i] = BigInteger.ZERO;
		}
	}

	@Override
	public void set(long index, U value) {
		value.toBigIntegerArray(data, (int)(index * type.bigIntegerCount()));
	}

	@Override
	public void get(long index, U value) {
		value.fromBigIntegerArray(data, (int)(index * type.bigIntegerCount()));
	}

	@Override
	public long size() {
		return data.length / type.bigIntegerCount();
	}
	
	@Override
	public ArrayStorageBigInteger<U> duplicate() {
		ArrayStorageBigInteger<U> s = new ArrayStorageBigInteger<U>(size(), type);
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

	@Override
	public ArrayStorageBigInteger<U> allocate() {
		return new ArrayStorageBigInteger<U>(size(), type);
	}
}
