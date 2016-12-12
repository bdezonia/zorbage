/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.storage;

import zorbage.type.data.BooleanMember;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageBit
	implements Storage<BooleanMember>
{

	private final long[] data;
	private final long size;
	
	public ArrayStorageBit(long size) {
	
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorage cannot handle a negative request");
		if (size > 64l * Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorage cannot handle such a large request");
		int count = (int)size / 64;
		if (count % 64 > 0) count += 1;
		this.data = new long[count];
		this.size = size;
	}

	@Override
	public void put(long index, BooleanMember value) {
		synchronized (data) {
			long bucket = data[(int)index / 64];
			if (value.v()) {
				bucket = bucket | (1 << (index % 64));
			}
			else {
				bucket = bucket & ~(1 << (index % 64));
			}
			data[(int)index / 64] = bucket;
		}
	}

	@Override
	public void get(long index, BooleanMember value) {
		synchronized (data) {
			long bucket = data[(int)index / 64];
			long bit = bucket & (1 << (index % 64));
			value.setV(bit > 0);
		}
	}
	
	@Override
	public long size() {
		return size;
	}

}
