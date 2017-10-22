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
package zorbage.type.data.int16;

import java.io.IOException;
import java.io.RandomAccessFile;

import zorbage.type.algebra.Gettable;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;
import zorbage.type.ctor.Duplicatable;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.ShortCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnsignedInt16Member
	implements
		ShortCoder<UnsignedInt16Member>,
		Allocatable<UnsignedInt16Member>, Duplicatable<UnsignedInt16Member>,
		Settable<UnsignedInt16Member>, Gettable<UnsignedInt16Member>
{

	short v;
	
	public UnsignedInt16Member() {
		v = 0;
	}
	
	public UnsignedInt16Member(int value) {
		v = (short) (0xffff & value);
	}
	
	public UnsignedInt16Member(UnsignedInt16Member value) {
		v = value.v;
	}
	
	public UnsignedInt16Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		int x = val.r().intValue();
		v = (short) (0xffff & x);
	}

	public int v() { return v & 0xffff; }

	public void setV(int val) {
		v = (short) val;
	}
	
	@Override
	public void set(UnsignedInt16Member other) {
		v = other.v;
	}
	
	@Override
	public void get(UnsignedInt16Member other) {
		other.v = v;
	}

	@Override
	public String toString() {
		return String.valueOf(v & 0xffff);
	}

	@Override
	public int shortCount() {
		return 1;
	}

	@Override
	public void toValue(short[] arr, int index) {
		v = arr[index];
	}

	@Override
	public void toArray(short[] arr, int index) {
		arr[index] = v;
	}

	@Override
	public void toValue(RandomAccessFile raf) throws IOException {
		v = raf.readShort();
	}

	@Override
	public void toFile(RandomAccessFile raf) throws IOException {
		raf.writeShort(v);
	}

	@Override
	public UnsignedInt16Member allocate() {
		return new UnsignedInt16Member();
	}

	@Override
	public UnsignedInt16Member duplicate() {
		return new UnsignedInt16Member(this);
	}

}
