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
package nom.bdezonia.zorbage.type.data.int32;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;

import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.universal.InternalRepresentation;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.coder.IntCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnsignedInt32Member
	implements
		IntCoder<UnsignedInt32Member>,
		Allocatable<UnsignedInt32Member>, Duplicatable<UnsignedInt32Member>,
		Settable<UnsignedInt32Member>, Gettable<UnsignedInt32Member>,
		InternalRepresentation
{

	int v;
	
	public UnsignedInt32Member() {
		v = 0;
	}
	
	public UnsignedInt32Member(long value) {
		v = (int) value;
	}
	
	public UnsignedInt32Member(UnsignedInt32Member value) {
		v = value.v;
	}
	
	public UnsignedInt32Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		long x = val.r().longValue();
		v = (int) x;
	}

	public long v() { return v & 0xffffffffL; }

	public void setV(long val) {
		v = (int) val;
	}
	
	@Override
	public void set(UnsignedInt32Member other) {
		v = other.v;
	}
	
	@Override
	public void get(UnsignedInt32Member other) {
		other.v = v;
	}

	@Override
	public String toString() {
		return String.valueOf(v());
	}

	@Override
	public int intCount() {
		return 1;
	}

	@Override
	public void toValue(int[] arr, int index) {
		v = arr[index];
	}

	@Override
	public void toArray(int[] arr, int index) {
		arr[index] = v;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		v = raf.readInt();
	}

	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeInt(v);
	}

	@Override
	public UnsignedInt32Member allocate() {
		return new UnsignedInt32Member();
	}

	@Override
	public UnsignedInt32Member duplicate() {
		return new UnsignedInt32Member(this);
	}

	@Override
	public void setInternalRep(TensorOctonionRepresentation rep) {
		rep.setFirstValue(new OctonionRepresentation(BigDecimal.valueOf(v())));
	}

	@Override
	public void setSelf(TensorOctonionRepresentation rep) {
		v = (int) rep.getFirstValue().r().longValue();
	}

}
