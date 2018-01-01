/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorageFloat32;
import nom.bdezonia.zorbage.type.storage.coder.FloatCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageFloat32<U extends FloatCoder<U> & Allocatable<U>>
	implements IndexedDataSource<FileStorageFloat32<U>,U>
{
	// TODO
	// 1) add low level array access to Array storage classes so can do block reads/writes
	// 2) make BUFFERSIZE and numBuffers configurable
	
	private final U type;
	private long numElements;
	private ArrayStorageFloat32<U> buffer;
	private File file;
	private boolean dirty;
	private long pageIndex;
	
	private static final long BUFFERSIZE = 2048;

	public FileStorageFloat32(long numElements, U type) {
		if (numElements < 0)
			throw new IllegalArgumentException("size must be >= 0");
		this.type = type.allocate();
		this.numElements = numElements;
		this.dirty = false;
		this.buffer = new ArrayStorageFloat32<U>(BUFFERSIZE,type);
		this.pageIndex = numElements;
		try { 
			this.file = File.createTempFile("Storage", ".storage");
			// if the file is new set it all to zero
			if (!file.exists() || file.length() == 0) {
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				for (long l = 0; l < (numElements+BUFFERSIZE); l++) {
					for (int i = 0; i < type.floatCount(); i++) {
						raf.writeFloat(0);
					}
				}
				raf.close();
			}
			this.file.deleteOnExit();
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public void set(long index, U value) {
		synchronized (this) {
			load(index);
			buffer.set(index % BUFFERSIZE, value);
			dirty = true;
		}
	}

	@Override
	public void get(long index, U value) {
		synchronized (this) {
			load(index);
			buffer.get(index % BUFFERSIZE, value);
		}
	}

	@Override
	public long size() {
		return numElements;
	}

	@Override
	public FileStorageFloat32<U> duplicate() {
		synchronized (this) {
			flush();
			try {
				FileStorageFloat32<U> other = new FileStorageFloat32<U>(numElements,type);
				other.buffer = buffer.duplicate();
				other.dirty = dirty;
				other.pageIndex = pageIndex;
			    Path FROM = Paths.get(file.getAbsolutePath());
			    Path TO = Paths.get(other.file.getAbsolutePath());
			    //overwrite existing file, if exists
			    CopyOption[] options = new CopyOption[]{
			      StandardCopyOption.REPLACE_EXISTING,
			      StandardCopyOption.COPY_ATTRIBUTES
			    }; 
			    Files.copy(FROM, TO, options);
				return other;
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	private void flush() {
		if (!dirty) return;
		try {
			U tmp = type.allocate();
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek((pageIndex/BUFFERSIZE)*BUFFERSIZE*type.floatCount()*4);
			for (long i = 0; i < BUFFERSIZE; i++) {
				buffer.get(i, tmp);
				tmp.toFile(raf);
			}
			raf.close();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		dirty = false;
	}

	// TODO: improve performance. use java 8. use nio?

	private void load(long index) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		if (index < pageIndex || index >= pageIndex + BUFFERSIZE) {
			if (dirty) {
				flush();
			}
			// read file data into array using sizeof()
			try {
				U tmp = type.allocate();
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek((index/BUFFERSIZE)*BUFFERSIZE*type.floatCount()*4);
				for (long i = 0; i < BUFFERSIZE; i++) {
					tmp.toValue(raf);
					buffer.set(i, tmp);
				}
				raf.close();
			} catch (Exception e) {
				System.out.println(e);
				throw new IllegalArgumentException(e.getMessage());
			}
			pageIndex = (index / BUFFERSIZE) * BUFFERSIZE;
		}
	}
}
