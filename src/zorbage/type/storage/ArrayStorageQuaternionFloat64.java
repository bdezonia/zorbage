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

import zorbage.type.data.QuaternionFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageQuaternionFloat64
	implements Storage<QuaternionFloat64Member>
{

	private final double[] data;
	
	public ArrayStorageQuaternionFloat64(long size) {
		size = size * 4;
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorage cannot handle a negative request");
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("ArrayStorage cannot handle such a large request");
		this.data = new double[(int)size];
	}

	@Override
	public void put(long index, QuaternionFloat64Member value) {
		final int idx = ((int) index) * 4;
		data[idx    ] = value.r();
		data[idx + 1] = value.i();
		data[idx + 2] = value.j();
		data[idx + 3] = value.k();
	}

	@Override
	public void get(long index, QuaternionFloat64Member value) {
		final int idx = ((int) index) * 4;
		value.setR(data[idx + 0]);
		value.setI(data[idx + 1]);
		value.setJ(data[idx + 2]);
		value.setK(data[idx + 3]);
	}
	
	@Override
	public long size() {
		return data.length/4;
	}

}
