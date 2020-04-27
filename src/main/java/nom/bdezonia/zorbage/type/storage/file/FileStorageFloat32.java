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
import nom.bdezonia.zorbage.type.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageFloat32<U extends FloatCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<FileStorageFloat32<U>>
{
	private final long numElements;
	private final U type;
	private final float[] tmpArray;
	private final int bufSize;
	private final File file;
	private final RandomAccessFile raf;
	private final FileChannel channel;
	private final ByteBuffer buffer;
	
	/**
	 * 
	 * @param numElements
	 * @param type
	 */
	public FileStorageFloat32(long numElements, U type) {
		this.numElements = numElements;
		this.type = type.allocate();
		this.tmpArray = new float[type.floatCount()];
		this.bufSize = type.floatCount() * 4;
		try {
			this.file = File.createTempFile("Storage", ".storage");
			this.file.deleteOnExit();
			this.raf = new RandomAccessFile(file, "rw");
			this.channel = raf.getChannel();
			// make a one element buffer
			this.buffer = ByteBuffer.allocate(bufSize);
			// fill the buffer with zeroes
			for (int i = 0; i < type.floatCount(); i++) {
				buffer.putFloat(0);
			}
			// write zeroes to the file over and over
			channel.position(0);
			for (long i = 0; i < numElements; i++) {
				channel.write(buffer);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public FileStorageFloat32(FileStorageFloat32<U> other, U type) {
		synchronized(other) {
			this.numElements = other.numElements;
			this.type = type.allocate();
			this.tmpArray = other.tmpArray.clone();
			this.bufSize = other.bufSize;
			try {
				this.file = File.createTempFile("Storage", ".storage");
				this.file.deleteOnExit();
				this.buffer = ByteBuffer.allocate(bufSize);
				Path from = Paths.get(other.file.getAbsolutePath());
				Path to = Paths.get(file.getAbsolutePath());
				//overwrite existing file, if exists
				CopyOption[] options = new CopyOption[]{
					StandardCopyOption.REPLACE_EXISTING,
					StandardCopyOption.COPY_ATTRIBUTES
				};
				Files.copy(from, to, options);
				// these two must happen after the file copy
				this.raf = new RandomAccessFile(file, "rw");
				this.channel = raf.getChannel();
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
				value.toFloatArray(tmpArray, 0);
				for (int i = 0; i < tmpArray.length; i++) {
					buffer.putFloat(i*4, tmpArray[i]);
				}
				buffer.rewind();
				long pos = index * bufSize;
				channel.position(pos);
				channel.write(buffer);
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
				buffer.rewind();
				long pos = index * bufSize;
				channel.position(pos);
				channel.read(buffer);
				for (int i = 0; i < tmpArray.length; i++) {
					tmpArray[i] = buffer.getFloat(i*4);
				}
				value.fromFloatArray(tmpArray, 0);
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
	public FileStorageFloat32<U> allocate() {
		return new FileStorageFloat32<U>(numElements, type);
	}

	@Override
	public FileStorageFloat32<U> duplicate() {
		return new FileStorageFloat32<U>(this, type);
	}

	@Override
	protected void finalize() throws Throwable {
		raf.close();
	}
}
