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
package nom.bdezonia.zorbage.storage.file;

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

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.IntCoder;

/**
 *
 * @author Barry DeZonia
 *
 */
public class FileStorageSignedInt32<U extends IntCoder & Allocatable<U>>
		implements IndexedDataSource<U>, Allocatable<FileStorageSignedInt32<U>>
{
	private static final int BYTE_CHUNK = 4096;
	private final long numElements;
	private final U type;
	private final int[] tmpArray;
	private final File file;
	private final RandomAccessFile raf;
	private final FileChannel channel;
	private final ByteBuffer buffer1;
	private final ByteBuffer buffer2;
	private final int elementByteSize;
	private final int elementsPerPage;
	private final int bytesPerPage;
	private long pageLoaded1;
	private long pageLoaded2;
	private boolean dirty1;
	private boolean dirty2;
	private int lru;

	/**
	 *
	 * @param numElements
	 * @param type
	 */
	public FileStorageSignedInt32(long numElements, U type) {
		this.numElements = numElements;
		this.type = type.allocate();
		this.tmpArray = new int[type.intCount()];
		this.elementByteSize = type.intCount() * 4;
		if (elementByteSize <= 0) {
			// overflow happened
			throw new IllegalArgumentException("element type is too big to be buffered: max intCount is "+(Integer.MAX_VALUE/4));
		}
		long elementsInPage = BYTE_CHUNK / elementByteSize;
		if (elementsInPage <= 0) {
			// Emergency: type is bigger than our default max buffer size. Just buffer one.
			elementsInPage = 1;
		}
		this.elementsPerPage = (int) elementsInPage;
		this.bytesPerPage = (int) (elementsInPage * elementByteSize);
		this.pageLoaded1 = -1;
		this.pageLoaded2 = -1;
		this.dirty1 = false;
		this.dirty2 = false;
		this.lru = 1;
		try {
			this.file = File.createTempFile("Storage", ".storage");
			this.file.deleteOnExit();
			this.raf = new RandomAccessFile(file, "rw");
			this.channel = raf.getChannel();
			// make a one element buffer
			this.buffer1 = ByteBuffer.allocate(bytesPerPage);
			this.buffer2 = ByteBuffer.allocate(bytesPerPage);
			// fill the buffers with zeroes
			for (int i = 0; i < bytesPerPage; i++) {
				buffer1.put((byte) 0);
				buffer2.put((byte) 0);
			}
			// write zeroes to the file over and over
			channel.position(0);
			for (long i = 0; i <= (numElements / elementsPerPage); i++) {  // <= is intentional here
				buffer1.rewind();
				channel.write(buffer1);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public FileStorageSignedInt32(FileStorageSignedInt32<U> other, U type) {
		synchronized(other) {
			this.numElements = other.numElements;
			this.type = type.allocate();
			this.tmpArray = other.tmpArray.clone();
			this.bytesPerPage = other.bytesPerPage;
			this.elementByteSize = other.elementByteSize;
			this.elementsPerPage = other.elementsPerPage;
			this.pageLoaded1 = other.pageLoaded1;
			this.pageLoaded2 = other.pageLoaded2;
			this.dirty1 = other.dirty1;
			this.dirty2 = other.dirty2;
			this.lru = other.lru;
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
				this.buffer1 = other.buffer1.duplicate();
				this.buffer2 = other.buffer2.duplicate();
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
				long desiredPage = index / elementsPerPage;
				if (desiredPage != pageLoaded1 && desiredPage != pageLoaded2) {
					// save out old page if needed
					if (lru == 1) {
						if (dirty1) {
							buffer1.rewind();
							channel.position(pageLoaded1 * bytesPerPage);
							channel.write(buffer1);
							dirty1 = false;
						}
						// read in new page
						buffer1.rewind();
						channel.position(desiredPage * bytesPerPage);
						channel.read(buffer1);
						pageLoaded1 = desiredPage;
						lru = 2;
					}
					else if (lru == 2) {
						if (dirty2) {
							buffer2.rewind();
							channel.position(pageLoaded2 * bytesPerPage);
							channel.write(buffer2);
							dirty2 = false;
						}
						// read in new page
						buffer2.rewind();
						channel.position(desiredPage * bytesPerPage);
						channel.read(buffer2);
						pageLoaded2 = desiredPage;
						lru = 1;
					}
				}
				value.toIntArray(tmpArray, 0);
				ByteBuffer buffer;
				if (desiredPage == pageLoaded1) {
					buffer = buffer1;
					dirty1 = true;
				}
				else {
					buffer = buffer2;
					dirty2 = true;
				}
				int idx = (int)(index % elementsPerPage);
				for (int i = 0; i < tmpArray.length; i++) {
					buffer.putInt(idx*elementByteSize + i*4, tmpArray[i]);
				}
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
				long desiredPage = index / elementsPerPage;
				if (desiredPage != pageLoaded1 && desiredPage != pageLoaded2) {
					// save out old page if needed
					if (lru == 1) {
						if (dirty1) {
							buffer1.rewind();
							channel.position(pageLoaded1 * bytesPerPage);
							channel.write(buffer1);
							dirty1 = false;
						}
						// read in new page
						buffer1.rewind();
						channel.position(desiredPage * bytesPerPage);
						channel.read(buffer1);
						pageLoaded1 = desiredPage;
						lru = 2;
					}
					else if (lru == 2) {
						if (dirty2) {
							buffer2.rewind();
							channel.position(pageLoaded2 * bytesPerPage);
							channel.write(buffer2);
							dirty2 = false;
						}
						// read in new page
						buffer2.rewind();
						channel.position(desiredPage * bytesPerPage);
						channel.read(buffer2);
						pageLoaded2 = desiredPage;
						lru = 1;
					}
				}
				ByteBuffer buffer = (desiredPage == pageLoaded1) ? buffer1 : buffer2;
				int idx = (int) (index % elementsPerPage);
				for (int i = 0; i < tmpArray.length; i++) {
					tmpArray[i] = buffer.getInt(idx*elementByteSize + i*4);
				}
				value.fromIntArray(tmpArray, 0);
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
	public FileStorageSignedInt32<U> allocate() {
		return new FileStorageSignedInt32<U>(numElements, type);
	}

	@Override
	public FileStorageSignedInt32<U> duplicate() {
		return new FileStorageSignedInt32<U>(this, type);
	}

	@Override
	protected void finalize() throws Throwable {
		raf.close();
	}
}
