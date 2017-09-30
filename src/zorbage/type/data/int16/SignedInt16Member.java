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
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.ShortCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class SignedInt16Member
	implements ShortCoder<SignedInt16Member>, Allocatable<SignedInt16Member>, Settable<SignedInt16Member>, Gettable<SignedInt16Member>
{

	private short v;
	
	public SignedInt16Member() {
		v = 0;
	}
	
	public SignedInt16Member(short value) {
		v = value;
	}
	
	public SignedInt16Member(SignedInt16Member value) {
		v = value.v;
	}
	
	public SignedInt16Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		v = val.r().shortValue();
	}

	public short v() { return v; }
	
	public void setV(short val) { v = val; }
	
	@Override
	public void set(SignedInt16Member other) {
		v = other.v;
	}
	
	@Override
	public void get(SignedInt16Member other) {
		other.v = v;
	}

	@Override
	public String toString() { return "" + v; }

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
	public SignedInt16Member allocate() {
		return new SignedInt16Member();
	}

}
