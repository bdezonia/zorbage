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
package nom.bdezonia.zorbage.type.storage.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt8;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageSignedInt8<U extends ByteCoder & Allocatable<U>>
	extends AbstractFileStorage<U>
	implements Allocatable<FileStorageSignedInt8<U>>
{
	private ArrayStorageSignedInt8<U> buffer;
	private U type;
	private ThreadLocal<byte[]> tmpA;
	private ThreadLocal<U> tmpU;
	
	public FileStorageSignedInt8(long numElements, U type) {
		super(numElements,type);
	}
	
	public FileStorageSignedInt8(FileStorageSignedInt8<U> other, U t) {
		super(other, t);
	}
	
	@Override
	public FileStorageSignedInt8<U> duplicate() {
		return new FileStorageSignedInt8<U>(this, this.type);
	}
	
	@Override
	public FileStorageSignedInt8<U> allocate() {
		return new FileStorageSignedInt8<U>(size(), type);
	}

	@Override
	protected void setLocals(U t) {
		this.type = t.allocate();
		this.tmpA = new ThreadLocal<byte[]>() {
			@Override
			protected byte[] initialValue() {
				return new byte[type.byteCount()];
			}
		};
		this.tmpU = new ThreadLocal<U>() {
			@Override
			protected U initialValue() {
				return type.allocate();
			}
		};
	}
	
	@Override
	protected void allocateBuffer(long numElements, U type) {
		buffer = new ArrayStorageSignedInt8<U>(numElements, type);
	}

	@Override
	protected void writeZeroElement(RandomAccessFile raf) throws IOException {
		byte arr[] = tmpA.get();
		for (int i = 0; i < arr.length; i++) {
			raf.writeByte(0);
		}
	}

	@Override
	protected void writeFromBufferToRaf(RandomAccessFile raf, long index) throws IOException {
		U value = tmpU.get();
		buffer.get(index, value);
		byte arr[] = tmpA.get();
		value.toByteArray(arr, 0);
		for (int i = 0; i < arr.length; i++) {
			raf.writeByte(arr[i]);
		}
	}

	@Override
	protected void readFromRafIntoBuffer(RandomAccessFile raf, long index) throws IOException {
		byte arr[] = tmpA.get();
		for (int i = 0; i < type.byteCount(); i++) {
			arr[i] = raf.readByte();
		}
		U value = tmpU.get();
		value.fromByteArray(arr, 0);
		buffer.set(index, value);
	}

	@Override
	protected int elementByteSize() {
		return type.byteCount() * 1;
	}

	@Override
	protected void setBufferValue(long idx, U value) {
		buffer.set(idx, value);
	}

	@Override
	protected void getBufferValue(long idx, U value) {
		buffer.get(idx, value);
	}

	@Override
	protected void duplicateBuffer(IndexedDataSource<?,U> other) {
		@SuppressWarnings("unchecked")
		ArrayStorageSignedInt8<U> otherBuffer = (ArrayStorageSignedInt8<U>) other;
		buffer = otherBuffer.duplicate();
	}

	@Override
	protected IndexedDataSource<?,U> buffer() {
		return buffer;
	}
}
