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
package zorbage.type.data.int128;

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
public final class UnsignedInt128Member
	implements
		LongCoder<UnsignedInt128Member>,
		Allocatable<UnsignedInt128Member>, Duplicatable<UnsignedInt128Member>,
		Settable<UnsignedInt128Member>, Gettable<UnsignedInt128Member>
{
	//static final BigInteger MAX = new BigInteger("340282366920938463463374607431768211456");
	static final BigInteger MAX = new BigInteger("65536");

	byte lo, hi; // package access is necessary so group can manipulate values
	
	public UnsignedInt128Member() {
		lo = hi = 0;
	}
	
	public UnsignedInt128Member(BigInteger v) {
		setV(v);
	}
	
	public UnsignedInt128Member(UnsignedInt128Member value) {
		lo = value.lo;
		hi = value.hi;
	}
	
	public UnsignedInt128Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		BigInteger r = val.r().toBigInteger();
		setV(r);
	}

	UnsignedInt128Member(byte hi, byte lo) {
		this.lo = lo;
		this.hi = hi;
	}

	// expensive but shouldn't need to call very often
	
	public BigInteger v() {
		BigInteger low = BigInteger.valueOf(lo & 0x7f);
		BigInteger lowInc = ((lo & 0x80) > 0) ? BigInteger.valueOf(0x80) : BigInteger.ZERO;
		BigInteger high = BigInteger.valueOf(256*(hi & 0x7f));
		BigInteger highInc =  ((hi & 0x80) > 0) ? BigInteger.valueOf(256*0x80) : BigInteger.ZERO;
		return low.add(lowInc).add(high).add(highInc);
	}
	
	// expensive but shouldn't need to call very often
	
	public void setV(BigInteger val) {
		lo = BigInteger.valueOf(0x7f).and(val).byteValue();
		hi = BigInteger.valueOf(256*0x7f).and(val).shiftRight(8).byteValue();
		if (val.testBit(7))
			lo |= 0x80;
		if (val.testBit(15))
			hi |= 0x80;
	}
	
	@Override
	public void set(UnsignedInt128Member other) {
		lo = other.lo;
		hi = other.hi;
	}
	
	@Override
	public void get(UnsignedInt128Member other) {
		other.lo = lo;
		other.hi = hi;
	}

	// expensive but shouldn't need to call very often
	
	@Override
	public String toString() { return v().toString(); }

	@Override
	public int longCount() {
		return 2;
	}

	@Override
	public void toValue(long[] arr, int index) {
		lo = (byte) arr[index];
		hi = (byte) arr[index+1];
	}

	@Override
	public void toArray(long[] arr, int index) {
		arr[index] = lo;
		arr[index+1] = hi;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		lo = (byte) raf.readLong();
		hi = (byte) raf.readLong();
	}

	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeLong(lo);
		raf.writeLong(hi);
	}

	@Override
	public UnsignedInt128Member allocate() {
		return new UnsignedInt128Member();
	}

	@Override
	public UnsignedInt128Member duplicate() {
		return new UnsignedInt128Member(this);
	}

}
