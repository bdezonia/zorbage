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
package zorbage.type.data.int64;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

import zorbage.type.algebra.Gettable;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;
import zorbage.type.ctor.Duplicatable;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.LongCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnsignedInt64Member
	implements
		LongCoder<UnsignedInt64Member>,
		Allocatable<UnsignedInt64Member>, Duplicatable<UnsignedInt64Member>,
		Settable<UnsignedInt64Member>, Gettable<UnsignedInt64Member>
{

	private static final BigInteger UPPER = new BigInteger("8000000000000000",16);

	long v;
	
	public UnsignedInt64Member() {
		v = 0;
	}
	
	public UnsignedInt64Member(BigInteger value) {
		setV(value);
	}
	
	public UnsignedInt64Member(UnsignedInt64Member value) {
		v = value.v;
	}
	
	public UnsignedInt64Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		v = val.r().longValue();
	}

	public BigInteger v() {
		BigInteger lower = BigInteger.valueOf(v & 0x7fffffffffffffffL);
		BigInteger upper = (v < 0) ? UPPER : BigInteger.ZERO;
		return lower.add(upper);
	}

	public void setV(BigInteger val) {
		v = val.longValue();
	}
	
	@Override
	public void set(UnsignedInt64Member other) {
		v = other.v;
	}
	
	@Override
	public void get(UnsignedInt64Member other) {
		other.v = v;
	}

	@Override
	public String toString() {
		
		return String.valueOf(v());
	}

	@Override
	public int longCount() {
		return 1;
	}

	@Override
	public void toValue(long[] arr, int index) {
		v = arr[index];
	}

	@Override
	public void toArray(long[] arr, int index) {
		arr[index] = v;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		v = raf.readLong();
	}

	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeLong(v);
	}

	@Override
	public UnsignedInt64Member allocate() {
		return new UnsignedInt64Member();
	}

	@Override
	public UnsignedInt64Member duplicate() {
		return new UnsignedInt64Member(this);
	}

}
