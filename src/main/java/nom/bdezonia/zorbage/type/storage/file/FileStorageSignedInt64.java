/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageSignedInt64;
import nom.bdezonia.zorbage.type.storage.coder.LongCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageSignedInt64<U extends LongCoder & Allocatable<U>>
	extends AbstractFileStorage<U>
	implements Allocatable<FileStorageSignedInt64<U>>
{
	private ArrayStorageSignedInt64<U> buffer;
	private U type;
	private ThreadLocal<long[]> tmpA;
	private ThreadLocal<U> tmpU;
	
	public FileStorageSignedInt64(long numElements, U type) {
		super(numElements,type);
	}
	
	public FileStorageSignedInt64(FileStorageSignedInt64<U> other, U t) {
		super(other, t);
	}
	
	@Override
	public FileStorageSignedInt64<U> duplicate() {
		return new FileStorageSignedInt64<U>(this, this.type);
	}
	
	@Override
	public FileStorageSignedInt64<U> allocate() {
		return new FileStorageSignedInt64<U>(size(), type);
	}

	@Override
	protected void setLocals(U t) {
		this.type = t.allocate();
		this.tmpA = new ThreadLocal<long[]>() {
			@Override
			protected long[] initialValue() {
				return new long[type.longCount()];
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
		buffer = new ArrayStorageSignedInt64<U>(numElements, type);
	}

	@Override
	protected void writeZeroElement(RandomAccessFile raf) throws IOException {
		long[] arr = tmpA.get();
		for (int i = 0; i < arr.length; i++) {
			raf.writeLong(0);
		}
	}

	@Override
	protected void writeFromBufferToRaf(RandomAccessFile raf, long index) throws IOException {
		U value = tmpU.get();
		buffer.get(index, value);
		long arr[] = tmpA.get();
		value.toLongArray(arr, 0);
		for (int i = 0; i < arr.length; i++) {
			raf.writeLong(arr[i]);
		}
	}

	@Override
	protected void readFromRafIntoBuffer(RandomAccessFile raf, long index) throws IOException {
		long arr[] = tmpA.get();
		for (int i = 0; i < type.longCount(); i++) {
			arr[i] = raf.readLong();
		}
		U value = tmpU.get();
		value.fromLongArray(arr, 0);
		buffer.set(index, value);
	}

	@Override
	protected int elementByteSize() {
		return type.longCount() * 8;
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
	protected void duplicateBuffer(IndexedDataSource<U> other) {
		ArrayStorageSignedInt64<U> otherBuffer = (ArrayStorageSignedInt64<U>) other;
		buffer = otherBuffer.duplicate();
	}

	@Override
	protected IndexedDataSource<U> buffer() {
		return buffer;
	}
	
	@Override
	protected int componentCount(U type) {
		return type.longCount();
	}
}
