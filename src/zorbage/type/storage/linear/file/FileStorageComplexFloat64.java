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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
	// 1) multiple buffer support
	// 2) add low level array access to Array storage classes so can do block reads/writes
	// 3) make BUFFERESIZE configurable
	
	private static final long BUFFERSIZE = 512;
	private long numElements;
	private ArrayStorageComplexFloat64 buffer;
	private File file;
	private boolean dirty;
	private long pageIndex;
	
	public FileStorageComplexFloat64(long numElements, File f) {
		if (numElements < 0)
			throw new IllegalArgumentException("size must be >= 0");
		this.numElements = numElements;
		this.dirty = false;
		this.buffer = new ArrayStorageComplexFloat64(BUFFERSIZE);
		this.pageIndex = numElements;
		this.file = f;
		// if the file is new set it all to zero
		if (!f.exists() || f.length() == 0) {
			try { 
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
				raf.writeLong(numElements);
				for (long l = 0; l < (numElements + BUFFERSIZE)* 2; l++) {
					raf.writeDouble(0);
				}
				raf.close();
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	public FileStorageComplexFloat64(long numElements) throws IOException {
		this(numElements, File.createTempFile("Storage", ".storage"));
	}
	
	public FileStorageComplexFloat64(File f) throws IOException {
		this(findNumElem(f), f);
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
		try {
			File tmpfile = File.createTempFile("Storage", ".storage");
			FileStorageComplexFloat64 other = new FileStorageComplexFloat64(numElements, tmpfile);
			other.buffer = buffer.duplicate();
			other.dirty = dirty;
			other.pageIndex = pageIndex;
		    Path FROM = Paths.get(file.getAbsolutePath());
		    Path TO = Paths.get(tmpfile.getAbsolutePath());
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
	
	public String filename() {
		return file.getAbsolutePath();
	}
	
	public void setFilename(String filename) throws IOException {
		flush();
		if (!file.renameTo(new File(filename)))
			throw new IOException("Can't rename file from " + file.getAbsolutePath() + " to " + filename);
	}
	
	// TODO - make this part of the storage api. users should tell storage when they are done with it.
	// Using finalize() for this is problematic.
	
	public void flush() {
		if (!dirty) return;
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(8l + (pageIndex/BUFFERSIZE)*BUFFERSIZE*2*8);
			for (long i = 0; i < BUFFERSIZE; i++) {
				buffer.get(i, tmp);
				raf.writeDouble(tmp.r());
				raf.writeDouble(tmp.i());
			}
			raf.close();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		dirty = false;
	}

	// it's optional to call this
	
	public void dispose() {
		file.deleteOnExit();
	}

	// TODO: improve performance. use java 8. use nio?
	
	private void load(long index) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		if (index < pageIndex || index > pageIndex + BUFFERSIZE) {
			ComplexFloat64Member tmp = new ComplexFloat64Member();
			if (dirty)
				flush();
			// read file data into array using sizeof() and skipping 1st eight bytes
			try {
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek(8l + (index/BUFFERSIZE)*BUFFERSIZE*2*8);
				for (long i = 0; i < BUFFERSIZE; i++) {
					double real = raf.readDouble();
					double imag = raf.readDouble();
					tmp.setR(real);
					tmp.setI(imag);
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
	
	private static long findNumElem(File f) throws IOException {
		long val = 0;
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "r");
			val = raf.readLong();
			raf.close();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		return val;
	}
	
}
