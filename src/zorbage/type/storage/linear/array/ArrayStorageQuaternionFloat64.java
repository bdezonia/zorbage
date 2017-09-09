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

import zorbage.type.data.float64.quaternion.QuaternionFloat64Member;
import zorbage.type.storage.linear.LinearStorage;
import zorbage.util.Fraction;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class ArrayStorageQuaternionFloat64
	implements LinearStorage<ArrayStorageQuaternionFloat64,QuaternionFloat64Member>
{

	private final double[] data;
	
	public static final Fraction BYTESIZE = new Fraction(32);
	
	public ArrayStorageQuaternionFloat64(long size) {
		if (size < 0)
			throw new IllegalArgumentException("ArrayStorageQuaternionFloat64 cannot handle a negative request");
		if (size > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException("ArrayStorageQuaternionFloat64 can handle at most " + (Integer.MAX_VALUE / 4) + " quaternionfloat64s");
		this.data = new double[(int)(size * 4)];
	}

	@Override
	public void set(long index, QuaternionFloat64Member value) {
		final int idx = ((int) index) * 4;
		data[idx    ] = value.r();
		data[idx + 1] = value.i();
		data[idx + 2] = value.j();
		data[idx + 3] = value.k();
	}

	@Override
	public void get(long index, QuaternionFloat64Member value) {
		final int idx = ((int) index) * 4;
		value.setR(data[idx    ]);
		value.setI(data[idx + 1]);
		value.setJ(data[idx + 2]);
		value.setK(data[idx + 3]);
	}
	
	@Override
	public long size() {
		return data.length/4;
	}

	@Override
	public ArrayStorageQuaternionFloat64 duplicate() {
		ArrayStorageQuaternionFloat64 s = new ArrayStorageQuaternionFloat64(size());
		for (int i = 0; i < data.length; i++)
			s.data[i] = data[i];
		return s;
	}

	@Override
	public Fraction elementSize() {
		return BYTESIZE;
	}

}
