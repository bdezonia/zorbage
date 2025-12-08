/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.storage.coder.CharCoder;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.storage.coder.IntCoder;
import nom.bdezonia.zorbage.storage.coder.LongCoder;
import nom.bdezonia.zorbage.storage.coder.ShortCoder;

// TODO: notes that apply to all the file storage classes in this package:
// I moved from RandomAccessFile ops to NIO buffered reads and writes. Speeds
// improved by a factor of 10 to 100,
//   Some further improvements possible
//     1) use a MappedByteBuffer scheme and some kind of paging of multiple elements
//     2) refactor all the package classes to eliminate duplicate code

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FileStorage {

	/**
	 * A constant used by all the file storage implementations.
	 */
	
	public static int IDEAL_BUFFER_SIZE = 10000000;
	
	/**
	 * Allocate a file based storage structure for storing data
	 * elements. These storage types primarily live on disk.
	 * It is a slower storage type to access but one can
	 * allocate a huge number of elements. It is possible to fail
	 * to allocate lists when your system does not have enough
	 * free disk space.
	 * 
	 * @param type
	 * @param numElements
	 * @param <U>
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U extends Allocatable<U>> IndexedDataSource<U> allocate(U type, long numElements) {
		
		if (type instanceof DoubleCoder) {
			return new FileStorageFloat64((DoubleCoder)type, numElements);
		}
		if (type instanceof FloatCoder) {
			return new FileStorageFloat32((FloatCoder)type, numElements);
		}
		if (type instanceof LongCoder) {
			return new FileStorageSignedInt64((LongCoder)type, numElements);
		}
		if (type instanceof IntCoder) {
			return new FileStorageSignedInt32((IntCoder)type, numElements);
		}
		if (type instanceof ShortCoder) {
			return new FileStorageSignedInt16((ShortCoder)type, numElements);
		}
		if (type instanceof BooleanCoder) {
			return new FileStorageBoolean((BooleanCoder)type, numElements);
		}
		if (type instanceof CharCoder) {
			return new FileStorageChar((CharCoder)type, numElements);
		}
		// Best if close to last as types may define Bytes as a last ditch approach
		if (type instanceof ByteCoder) {
			return new FileStorageSignedInt8((ByteCoder)type, numElements);
		}
		
		// TODO: add bitCoder when it is done. It should certainly be last as it will
		//   do files reads as well as writes and thus will be slowest.
		
		throw new IllegalArgumentException("Unsupported type in FileStorage");
	}

	// do not instantiate
	
	private FileStorage() { }
	
}
