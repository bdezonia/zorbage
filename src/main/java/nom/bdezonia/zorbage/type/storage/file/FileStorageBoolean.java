/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageBoolean;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageBoolean<U extends BooleanCoder & Allocatable<U>>
	extends AbstractFileStorage<U>
	implements Allocatable<FileStorageBoolean<U>>
{
	private ArrayStorageBoolean<U> buffer;
	private U type;
	private ThreadLocal<boolean[]> tmpA;
	private ThreadLocal<U> tmpU;
	
	public FileStorageBoolean(long numElements, U type) {
		super(numElements,type);
	}
	
	public FileStorageBoolean(FileStorageBoolean<U> other, U t) {
		super(other, t);
	}
	
	@Override
	public FileStorageBoolean<U> duplicate() {
		return new FileStorageBoolean<U>(this, this.type);
	}
	
	@Override
	public FileStorageBoolean<U> allocate() {
		return new FileStorageBoolean<U>(size(), type);
	}

	@Override
	protected void setLocals(U type) {
		this.type = type.allocate();
		this.tmpA = new ThreadLocal<boolean[]>() {
			@Override
			protected boolean[] initialValue() {
				return new boolean[type.booleanCount()];
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
		buffer = new ArrayStorageBoolean<U>(numElements, type);
	}

	@Override
	protected void writeZeroElement(RandomAccessFile raf) throws IOException {
		boolean[] arr = tmpA.get();
		for (int i = 0; i < arr.length; i++) {
			raf.writeBoolean(false);
		}
	}

	@Override
	protected void writeFromBufferToRaf(RandomAccessFile raf, long index) throws IOException {
		U value = tmpU.get();
		buffer.get(index, value);
		boolean arr[] = tmpA.get();
		value.toBooleanArray(arr, 0);
		for (int i = 0; i < arr.length; i++) {
			raf.writeBoolean(arr[i]);
		}
	}

	@Override
	protected void readFromRafIntoBuffer(RandomAccessFile raf, long index) throws IOException {
		boolean arr[] = tmpA.get();
		for (int i = 0; i < type.booleanCount(); i++) {
			arr[i] = raf.readBoolean();
		}
		U value = tmpU.get();
		value.fromBooleanArray(arr, 0);
		buffer.set(index, value);
	}

	@Override
	protected int elementByteSize() {
		return type.booleanCount() * 1;
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
		ArrayStorageBoolean<U> otherBuffer = (ArrayStorageBoolean<U>) other;
		buffer = otherBuffer.duplicate();
	}

	@Override
	protected IndexedDataSource<?,U> buffer() {
		return buffer;
	}
}
