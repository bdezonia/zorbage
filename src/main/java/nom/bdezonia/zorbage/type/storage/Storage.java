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
package nom.bdezonia.zorbage.type.storage;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.type.storage.file.FileStorage;
import nom.bdezonia.zorbage.type.storage.sparse.SparseStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Storage {

	public static <U extends Allocatable<U>> IndexedDataSource<U>
		allocate(long numElements, U type)
	{
		try {

			return ArrayStorage.allocate(numElements, type);

		} catch (OutOfMemoryError e) {

			return FileStorage.allocate(numElements, type);

		}
	}
	
	public static <U extends Allocatable<U>> IndexedDataSource<U> allocate(StorageConstruction strategy, long numElements, U type) {
		if (strategy == StorageConstruction.MEM_ARRAY)
			return ArrayStorage.allocate(numElements, type);
		else if (strategy == StorageConstruction.MEM_SPARSE)
			return SparseStorage.allocate(numElements, type);
		else if (strategy == StorageConstruction.MEM_VIRTUAL)
			return FileStorage.allocate(numElements, type);
		throw new IllegalArgumentException("Unknown storage strategy "+strategy);
	}
}
