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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public abstract class AbstractFileStorage<U extends Allocatable<U>>
	implements IndexedDataSource<U>
{
	protected abstract void setLocals(U type);
	protected abstract void allocateBuffer(long numElements, U type);
	protected abstract void writeZeroElement(RandomAccessFile raf) throws IOException;
	protected abstract int elementByteSize();
	protected abstract void writeFromBufferToRaf(RandomAccessFile raf, long i) throws IOException;
	protected abstract void readFromRafIntoBuffer(RandomAccessFile raf, long i) throws IOException;
	protected abstract void setBufferValue(long idx, U value);
	protected abstract void getBufferValue(long idx, U value);
	protected abstract IndexedDataSource<U> buffer();
	protected abstract void duplicateBuffer(IndexedDataSource<U> buffer);
	
	private long numElements;
	private File file;
	private boolean dirty;
	private long page;
	
	private static final long BUFFERSIZE = 512;

	protected AbstractFileStorage(long numElements, U type) {
		if (numElements < 0)
			throw new IllegalArgumentException("size must be >= 0");
		setLocals(type);
		this.numElements = numElements;
		this.dirty = false;
		this.page = 0;
		try {
			this.file = File.createTempFile("Storage", ".storage");
			// if the file is new set it all to zero
			if (!file.exists() || file.length() == 0) {
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				long pages = numElements / BUFFERSIZE;
				if ((numElements % BUFFERSIZE) > 0) pages++;
				for (long l = 0; l < pages; l++) {
					for (long i = 0; i < BUFFERSIZE; i++) {
						writeZeroElement(raf);
					}
				}
				raf.close();
			}
			this.file.deleteOnExit();
		}
		catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		allocateBuffer(BUFFERSIZE, type);
	}
	
	protected AbstractFileStorage(AbstractFileStorage<U> other, U type) {
		setLocals(type);
		try {
			this.numElements = other.numElements;
			this.dirty = other.dirty;
			duplicateBuffer(other.buffer());
			this.page = other.page;
			this.file = File.createTempFile("Storage", ".storage");
			Path FROM = Paths.get(other.file.getAbsolutePath());
			Path TO = Paths.get(file.getAbsolutePath());
			//overwrite existing file, if exists
			CopyOption[] options = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
			};
			Files.copy(FROM, TO, options);
			this.file.deleteOnExit();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public void set(long index, U value) {
		synchronized (this) {
			load(index);
			setBufferValue(index % BUFFERSIZE, value);
			dirty = true;
		}
	}

	@Override
	public void get(long index, U value) {
		synchronized (this) {
			load(index);
			getBufferValue(index % BUFFERSIZE, value);
		}
	}

	@Override
	public long size() {
		return numElements;
	}

	private void flush() {
		if (!dirty) return;
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(page * BUFFERSIZE * elementByteSize());
			for (long i = 0; i < BUFFERSIZE; i++) {
				writeFromBufferToRaf(raf, i);
			}
			raf.close();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		dirty = false;
	}

	private void load(long index) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		if (index < (page*BUFFERSIZE) || index >= ((page+1)*BUFFERSIZE)) {
			if (dirty) {
				flush();
			}
			page = index / BUFFERSIZE;
			// read file data into array using sizeof()
			try {
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek(page * BUFFERSIZE * elementByteSize());
				for (long i = 0; i < BUFFERSIZE; i++) {
					readFromRafIntoBuffer(raf, i);
				}
				raf.close();
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}

}
