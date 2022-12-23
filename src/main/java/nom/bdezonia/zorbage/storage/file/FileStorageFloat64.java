/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.storage.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorageFloat64<U extends DoubleCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<FileStorageFloat64<U>>
{
	private final long numElements;
	private final U type;
	private final double[] tmpArray;
	private final File file;
	private final RandomAccessFile raf;
	private final int bufSize;
	private final List<MappedByteBuffer> mappings = new ArrayList<>();

	/**
	 * 
	 * @param numElements
	 * @param type
	 */
	public FileStorageFloat64(U type, long numElements) {
		if (numElements < 0)
			throw new NegativeArraySizeException();
		this.numElements = numElements;
		this.type = type.allocate();
		this.tmpArray = new double[type.doubleCount()];
		int elementByteSize = type.doubleCount() * 8;
		if (elementByteSize <= 0) {
			// overflow happened
			throw new IllegalArgumentException("element type is too big to be buffered: max doubleCount is "+(Integer.MAX_VALUE/8));
		}
		long elementsInPage = FileStorage.IDEAL_BUFFER_SIZE / elementByteSize;
		if (elementsInPage <= 0) {
			// Emergency: type is bigger than our default max buffer size. Just buffer one.
			elementsInPage = 1;
		}
		this.bufSize = (int) (elementsInPage * elementByteSize);
		try {
			this.file = File.createTempFile("Storage", ".storage");
			this.file.deleteOnExit();
			this.raf = new RandomAccessFile(file, "rw");
			// write zeroes to the file over and over
			FileChannel channel = raf.getChannel();
			ByteBuffer buf = ByteBuffer.allocateDirect(bufSize);
			byte[] zeroes = new byte[bufSize];
			buf.put(zeroes);
			channel.position(0);
			long size = this.numElements * (type.doubleCount() * 8);
			for (long offset = 0; offset < size; offset += bufSize) {
				buf.rewind();
				channel.write(buf);
			}
			// now create the mappings
			for (long offset = 0; offset < size; offset += bufSize) {
				//System.out.println("mapping "+bufSize+" bytes at offset "+offset+" of total size "+size);
				mappings.add(channel.map(FileChannel.MapMode.READ_WRITE, offset, bufSize));
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public FileStorageFloat64(FileStorageFloat64<U> other, U type) {
		this.numElements = other.numElements;
		this.type = type.allocate();
		this.tmpArray = other.tmpArray.clone();
		this.bufSize = other.bufSize;
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
			// these can happen after file copy
			this.raf = new RandomAccessFile(file, "rw");
			long size = this.numElements * (type.doubleCount() * 8);
			for (long offset = 0; offset < size; offset += bufSize) {
				//System.out.println("mapping "+bufSize+" bytes at offset "+offset+" of total size "+size);
				mappings.add(raf.getChannel().map(FileChannel.MapMode.READ_WRITE, offset, bufSize));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
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
		long p = index * (type.doubleCount() * 8);
		int mapN = (int) (p / bufSize);
		int offN = (int) (p % bufSize);
		value.toDoubleArray(tmpArray, 0);
		MappedByteBuffer buf = mappings.get(mapN);
		buf.position(offN);
		for (int i = 0; i < tmpArray.length; i++) {
			buf.putDouble(tmpArray[i]);
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("storage index out of bounds");
		long p = index * (type.doubleCount() * 8);
		int mapN = (int) (p / bufSize);
		int offN = (int) (p % bufSize);
		MappedByteBuffer buf = mappings.get(mapN);
		buf.position(offN);
		for (int i = 0; i < tmpArray.length; i++) {
			tmpArray[i] = buf.getDouble();
		}
		value.fromDoubleArray(tmpArray, 0);
	}

	@Override
	public long size() {
		return numElements;
	}

	@Override
	public FileStorageFloat64<U> allocate() {
		return new FileStorageFloat64<U>(type, numElements);
	}

	@Override
	public FileStorageFloat64<U> duplicate() {
		return new FileStorageFloat64<U>(this, type);
	}

	@Override
	protected void finalize() throws Throwable {
		raf.close();
	}

	@Override
	public boolean accessWithOneThread() {
		return true;
	}
}
