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
package nom.bdezonia.zorbage.storage;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.storage.file.FileStorage;
import nom.bdezonia.zorbage.storage.sparse.SparseStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Storage {

	/**
	 * 
	 * @param numElements
	 * @param type
	 * @return
	 */
	public static <U extends Allocatable<U>> IndexedDataSource<U>
		allocate(long numElements, U type)
	{
		try {
			return ArrayStorage.allocate(numElements, type);
		}
		catch (Exception e) {
			// out of memory
			// or requested size is larger than any array can hold
			// or others I might be too accepting of
		}
		return FileStorage.allocate(numElements, type);
	}
	
	/**
	 * 
	 * @param strategy
	 * @param numElements
	 * @param type
	 * @return
	 */
	public static <U extends Allocatable<U>> IndexedDataSource<U>
		allocate(StorageConstruction strategy, long numElements, U type)
	{
		if (strategy == StorageConstruction.MEM_ARRAY) {
			return ArrayStorage.allocate(numElements, type);
			// if would be nice if we tested size and if too big for array we tried to
			//   allocate a BigListDataSource. But that ctor needs an Algebra. At one
			//   time I tried changing these signatures to take (numElem, algebra)
			//   which was pretty elegant but it killed the ability to support the
			//   Point type with varying sized elements (2d or 3d or 4d etc.).
		}
		else if (strategy == StorageConstruction.MEM_SPARSE)
			return SparseStorage.allocate(numElements, type);
		else if (strategy == StorageConstruction.MEM_VIRTUAL)
			return FileStorage.allocate(numElements, type);
		throw new IllegalArgumentException("Unknown storage strategy "+strategy);
	}
}
