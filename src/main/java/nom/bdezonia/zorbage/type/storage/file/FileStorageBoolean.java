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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 *
 * @author Barry DeZonia
 *
 */
public class FileStorageBoolean<U extends BooleanCoder & Allocatable<U>>
		implements IndexedDataSource<U>, Allocatable<FileStorageBoolean<U>>
{
	private static final int BYTE_CHUNK = 2000;
	private final long numElements;
	private final U type;
	private final boolean[] tmpArray;
	private final File file;
	private final RandomAccessFile raf;
	private final FileChannel channel;
	private final ByteBuffer buffer;
	private final int elementByteSize;
	private final int elementsPerPage;
	private final int bytesPerPage;
	private long pageLoaded;
	private boolean dirty;

	/**
	 *
	 * @param numElements
	 * @param type
	 */
	public FileStorageBoolean(long numElements, U type) {
		this.numElements = numElements;
		this.type = type.allocate();
		this.tmpArray = new boolean[type.booleanCount()];
		this.elementByteSize = type.booleanCount() * 1;
		if (elementByteSize <= 0) {
			// overflow happened
			throw new IllegalArgumentException("virtual type is too big to be buffered: max booleanCount is "+(Integer.MAX_VALUE/1));
		}
		long byteSize = BYTE_CHUNK / elementByteSize;
		if (byteSize <= 0) {
			// emergency: type is very big. Just buffer one.
			byteSize = elementByteSize;
		}
		this.bytesPerPage = (int) byteSize;
		this.elementsPerPage = (int) (byteSize / elementByteSize);
		this.pageLoaded = -1;
		this.dirty = false;
		try {
			this.file = File.createTempFile("Storage", ".storage");
			this.file.deleteOnExit();
			this.raf = new RandomAccessFile(file, "rw");
			this.channel = raf.getChannel();
			// make a one element buffer
			this.buffer = ByteBuffer.allocate(bytesPerPage);
			// fill the buffer with zeroes
			for (int i = 0; i < bytesPerPage; i++) {
				buffer.put((byte) 0);
			}
			// write zeroes to the file over and over
			channel.position(0);
			for (long i = 0; i <= (numElements / elementsPerPage); i++) {  // <= is intentional here
				buffer.rewind();
				channel.write(buffer);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public FileStorageBoolean(FileStorageBoolean<U> other, U type) {
		synchronized(other) {
			this.numElements = other.numElements;
			this.type = type.allocate();
			this.tmpArray = other.tmpArray.clone();
			this.bytesPerPage = other.bytesPerPage;
			this.elementByteSize = other.elementByteSize;
			this.elementsPerPage = other.elementsPerPage;
			this.pageLoaded = other.pageLoaded;
			this.dirty = other.dirty;
			try {
				this.file = File.createTempFile("Storage", ".storage");
				this.file.deleteOnExit();
				Path from = Paths.get(other.file.getAbsolutePath());
				Path to = Paths.get(file.getAbsolutePath());
				//overwrite existing file, if exists
				CopyOption[] options = new CopyOption[]{
						StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES
				};
				Files.copy(from, to, options);
				// these first two must happen after the file copy
				this.raf = new RandomAccessFile(file, "rw");
				this.channel = raf.getChannel();
				this.buffer = other.buffer.duplicate();
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_VIRTUAL;
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("storage index out of bounds");
		synchronized(this) {
			try {
				long newPage = index / elementsPerPage;
				if (newPage != pageLoaded) {
					// save out old page if needed
					if (dirty) {
						buffer.rewind();
						channel.position(pageLoaded * bytesPerPage);
						channel.write(buffer);
						dirty = false;
					}
					// read in new page
					buffer.rewind();
					channel.position(newPage * bytesPerPage);
					channel.read(buffer);
					pageLoaded = newPage;
				}
				value.toBooleanArray(tmpArray, 0);
				int idx = (int)(index % elementsPerPage);
				for (int i = 0; i < tmpArray.length; i++) {
					byte b = (byte) (tmpArray[i] ? 1 : 0);
					buffer.put(idx*elementByteSize + i*1, b);
				}
				dirty = true;
			} catch (IOException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("storage index out of bounds");
		synchronized(this) {
			try {
				long newPage = index / elementsPerPage;
				if (newPage != pageLoaded) {
					// save out old page if needed
					if (dirty) {
						buffer.rewind();
						channel.position(pageLoaded * bytesPerPage);
						channel.write(buffer);
						dirty = false;
					}
					// read in new page
					buffer.rewind();
					channel.position(newPage * bytesPerPage);
					channel.read(buffer);
					pageLoaded = newPage;
				}
				int idx = (int) (index % elementsPerPage);
				for (int i = 0; i < tmpArray.length; i++) {
					byte b = buffer.get(idx*elementByteSize + i*1);
					tmpArray[i] = (b == 1);
				}
				value.fromBooleanArray(tmpArray, 0);
			} catch (IOException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}

	@Override
	public long size() {
		return numElements;
	}

	@Override
	public FileStorageBoolean<U> allocate() {
		return new FileStorageBoolean<U>(numElements, type);
	}

	@Override
	public FileStorageBoolean<U> duplicate() {
		return new FileStorageBoolean<U>(this, type);
	}

	@Override
	protected void finalize() throws Throwable {
		raf.close();
	}
}
