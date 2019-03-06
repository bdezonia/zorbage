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
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageFloat64<U extends DoubleCoder & Allocatable<U>>
	extends AbstractFileStorage<U>
	implements Allocatable<FileStorageFloat64<U>>
{
	private ArrayStorageFloat64<U> buffer;
	private U type;
	private ThreadLocal<double[]> tmpA;
	private ThreadLocal<U> tmpU;
	
	public FileStorageFloat64(long numElements, U type) {
		super(numElements,type);
	}
	
	public FileStorageFloat64(FileStorageFloat64<U> other, U t) {
		super(other, t);
	}
	
	@Override
	public FileStorageFloat64<U> duplicate() {
		return new FileStorageFloat64<U>(this, this.type);
	}
	
	@Override
	public FileStorageFloat64<U> allocate() {
		return new FileStorageFloat64<U>(size(), type);
	}

	@Override
	protected void setLocals(U t) {
		this.type = t.allocate();
		this.tmpA = new ThreadLocal<double[]>() {
			@Override
			protected double[] initialValue() {
				return new double[type.doubleCount()];
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
		buffer = new ArrayStorageFloat64<U>(numElements, type);
	}

	@Override
	protected void writeZeroElement(RandomAccessFile raf) throws IOException {
		double[] arr = tmpA.get();
		for (int i = 0; i < arr.length; i++) {
			raf.writeDouble(0);
		}
	}

	@Override
	protected void writeFromBufferToRaf(RandomAccessFile raf, long index) throws IOException {
		U value = tmpU.get();
		buffer.get(index, value);
		double arr[] = tmpA.get();
		value.toDoubleArray(arr, 0);
		for (int i = 0; i < arr.length; i++) {
			raf.writeDouble(arr[i]);
		}
	}

	@Override
	protected void readFromRafIntoBuffer(RandomAccessFile raf, long index) throws IOException {
		double arr[] = tmpA.get();
		for (int i = 0; i < type.doubleCount(); i++) {
			arr[i] = raf.readDouble();
		}
		U value = tmpU.get();
		value.fromDoubleArray(arr, 0);
		buffer.set(index, value);
	}

	@Override
	protected int elementByteSize() {
		return type.doubleCount() * 8;
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
		ArrayStorageFloat64<U> otherBuffer = (ArrayStorageFloat64<U>) other;
		buffer = otherBuffer.duplicate();
	}

	@Override
	protected IndexedDataSource<?,U> buffer() {
		return buffer;
	}
}
