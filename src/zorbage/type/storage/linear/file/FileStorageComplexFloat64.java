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
package zorbage.type.storage.linear.file;

import java.io.File;

import zorbage.type.data.ComplexFloat64Member;
import zorbage.type.storage.LinearStorage;
import zorbage.type.storage.linear.array.ArrayStorageComplexFloat64;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageComplexFloat64
	implements LinearStorage<FileStorageComplexFloat64,ComplexFloat64Member>
{
	// TODO
	// 1) finish
	// 2) multiple buffer support
	// 3) add low level array access to Array storage classes so can do block reads/writes
	
	private static final int BUFFERSIZE = 512;
	private long numElements;
	private ArrayStorageComplexFloat64 buffer;
	private File fileData;
	private String filename;
	private boolean dirty;
	private long loadedIndex;

	
	public FileStorageComplexFloat64(long numElements, String filename) {
		if (numElements < 0)
			throw new IllegalArgumentException("size must be >= 0");
		this.numElements = numElements;
		this.dirty = false;
		this.buffer = new ArrayStorageComplexFloat64(BUFFERSIZE);
		this.filename = filename;  // test that we can make this file
		//this.fileData = fileOfZerosOfSize(numElements + BUFFERSIZE);
	}
	
	public FileStorageComplexFloat64(String existingFilename) {
		// TODO
		// also how to know num elements unless stored as a long at the front of the file?
	}
	
	@Override
	public void set(long index, ComplexFloat64Member value) {
		load(index);
		buffer.set(index % BUFFERSIZE, value);
		dirty = true;
	}

	@Override
	public void get(long index, ComplexFloat64Member value) {
		load(index);
		buffer.get(index % BUFFERSIZE, value);
	}

	@Override
	public long size() {
		return numElements;
	}

	@Override
	public FileStorageComplexFloat64 duplicate() {
		flush();
		FileStorageComplexFloat64 other = new FileStorageComplexFloat64(numElements, tmpFilename());
		other.buffer = buffer.duplicate();
		other.dirty = dirty;
		other.loadedIndex = loadedIndex;
		// duplicate file data
		return other;
	}
	
	public String filename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		flush();
		// rename file on disk and point to it
		this.filename = filename;
	}
	
	public void flush() {
		if (!dirty) return;
		// write buffer to file using sizeof() maybe
		dirty = false;
	}
	
	private void load(long index) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		if (index < loadedIndex || index > loadedIndex + BUFFERSIZE) {
			if (dirty) flush();
			// read file data into array using sizeof() maybe
		}
	}
	
	private String tmpFilename() {
		// TODO implement me: generate a temp file name
		return "something";
	}
}
