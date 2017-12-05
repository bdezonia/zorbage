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
package nom.bdezonia.zorbage.type.data.int128;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.universal.InternalRepresentation;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.coder.LongCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnsignedInt128Member
	implements
		LongCoder<UnsignedInt128Member>,
		Allocatable<UnsignedInt128Member>, Duplicatable<UnsignedInt128Member>,
		Settable<UnsignedInt128Member>, Gettable<UnsignedInt128Member>,
		InternalRepresentation
{
	static final BigInteger TWO64 = new BigInteger("2").pow(64);
	static final BigInteger TWO63 = new BigInteger("2").pow(63);
	static final BigInteger TWO63_MINUS_ONE = new BigInteger("2").pow(63).subtract(BigInteger.ONE);
	
	long lo, hi; // package access is necessary so group can manipulate values
	
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

	UnsignedInt128Member(long hi, long lo) {
		this.lo = lo;
		this.hi = hi;
	}

	// expensive but shouldn't need to call very often
	
	public BigInteger v() {
		BigInteger low = BigInteger.valueOf(lo & 0x7fffffffffffffffL);
		BigInteger lowInc = ((lo & 0x8000000000000000L) != 0) ? TWO63 : BigInteger.ZERO;
		BigInteger high = TWO64.multiply(BigInteger.valueOf(hi & 0x7fffffffffffffffL));
		BigInteger highInc =  ((hi & 0x8000000000000000L) != 0) ? TWO64.multiply(TWO63) : BigInteger.ZERO;
		return low.add(lowInc).add(high).add(highInc);
	}
	
	// expensive but shouldn't need to call very often
	
	public void setV(BigInteger val) {
		lo = TWO63_MINUS_ONE.and(val).longValue();
		hi = TWO64.multiply(TWO63_MINUS_ONE).and(val).shiftRight(64).longValue();
		if (val.testBit(63))
			lo |= 0x8000000000000000L;
		if (val.testBit(127))
			hi |= 0x8000000000000000L;
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
		lo = arr[index];
		hi = arr[index+1];
	}

	@Override
	public void toArray(long[] arr, int index) {
		arr[index] = lo;
		arr[index+1] = hi;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		lo = raf.readLong();
		hi = raf.readLong();
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

	@Override
	public void setInternalRep(TensorOctonionRepresentation rep) {
		rep.setFirstValue(new OctonionRepresentation(new BigDecimal(v())));
	}

	@Override
	public void setSelf(TensorOctonionRepresentation rep) {
		setV(rep.getFirstValue().r().toBigInteger());
	}

}
