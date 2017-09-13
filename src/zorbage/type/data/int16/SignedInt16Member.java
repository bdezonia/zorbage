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

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.ShortCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class SignedInt16Member
	implements ShortCoder<SignedInt16Member>
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
	
	
	public void set(SignedInt16Member other) {
		v = other.v;
	}
	
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
	public void arrayToValue(short[] arr, int index, SignedInt16Member value) {
		value.v = arr[index];
	}

	@Override
	public void valueToArray(short[] arr, int index, SignedInt16Member value) {
		arr[index] = value.v;
	}

	@Override
	public void fileToValue(RandomAccessFile raf, SignedInt16Member value) throws IOException {
		value.v = raf.readShort();
	}

	@Override
	public void valueToFile(RandomAccessFile raf, SignedInt16Member value) throws IOException {
		raf.writeShort(value.v);
	}

}
