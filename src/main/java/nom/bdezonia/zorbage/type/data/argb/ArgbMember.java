/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.argb;

import java.io.IOException;
import java.io.RandomAccessFile;

import nom.bdezonia.zorbage.type.data.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArgbMember implements ByteCoder {

	private byte a, r, g, b;
	
	public ArgbMember() {
	}
	
	public ArgbMember(UnsignedInt8Member a, UnsignedInt8Member r, UnsignedInt8Member g, UnsignedInt8Member b) {
		setA(a);
		setR(r);
		setG(g);
		setB(b);
	}
	
	public void setA(UnsignedInt8Member a) {
		this.a = (byte) a.v();
	}
	
	public void setR(UnsignedInt8Member r) {
		this.r = (byte) r.v();
	}
	
	public void setG(UnsignedInt8Member g) {
		this.g = (byte) g.v();
	}
	
	public void setB(UnsignedInt8Member b) {
		this.b = (byte) b.v();
	}
	
	public int a() {
		return a & 0xff;
	}
	
	public int r() {
		return r & 0xff;
	}
	
	public int g() {
		return g & 0xff;
	}
	
	public int b() {
		return b & 0xff;
	}

	@Override
	public int byteCount() {
		return 4;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		a = arr[index];
		r = arr[index+1];
		g = arr[index+2];
		b = arr[index+3];
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		arr[index] = a;
		arr[index+1] = r;
		arr[index+2] = g;
		arr[index+3] = b;
	}

	@Override
	public void fromByteFile(RandomAccessFile raf) throws IOException {
		a = raf.readByte();
		r = raf.readByte();
		g = raf.readByte();
		b = raf.readByte();
	}

	@Override
	public void toByteFile(RandomAccessFile raf) throws IOException {
		raf.writeByte(a);
		raf.writeByte(r);
		raf.writeByte(g);
		raf.writeByte(b);
	}
}
