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
package zorbage.type.data.int32;

import java.io.IOException;
import java.io.RandomAccessFile;

import zorbage.type.algebra.Gettable;
import zorbage.type.algebra.Settable;
import zorbage.type.ctor.Allocatable;
import zorbage.type.ctor.Duplicatable;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.IntCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class SignedInt32Member
	implements
		IntCoder<SignedInt32Member>,
		Allocatable<SignedInt32Member>, Duplicatable<SignedInt32Member>,
		Settable<SignedInt32Member>, Gettable<SignedInt32Member>
{

	private int v;
	
	public SignedInt32Member() {
		v = 0;
	}
	
	public SignedInt32Member(int value) {
		v = value;
	}
	
	public SignedInt32Member(SignedInt32Member value) {
		v = value.v;
	}
	
	public SignedInt32Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		v = val.r().intValue();
	}

	public int v() { return v; }

	public void setV(int val) { v = val; }
	
	@Override
	public void set(SignedInt32Member other) {
		v = other.v;
	}
	
	@Override
	public void get(SignedInt32Member other) {
		other.v = v;
	}

	@Override
	public String toString() { return "" + v; }

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
	public SignedInt32Member allocate() {
		return new SignedInt32Member();
	}

	@Override
	public SignedInt32Member duplicate() {
		return new SignedInt32Member(this);
	}

}
