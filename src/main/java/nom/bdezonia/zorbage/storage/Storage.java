/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.storage;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.storage.coder.BigIntegerCoder;
import nom.bdezonia.zorbage.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.storage.coder.CharCoder;
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.storage.coder.IntCoder;
import nom.bdezonia.zorbage.storage.coder.LongCoder;
import nom.bdezonia.zorbage.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.storage.coder.StringCoder;
import nom.bdezonia.zorbage.storage.extmem.ExtMemStorage;
import nom.bdezonia.zorbage.storage.file.FileStorage;
import nom.bdezonia.zorbage.storage.sparse.SparseStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Storage {

	/**
	 * Allocate a list of elements using the most convenient method
	 * zorbage can find at runtime. In memory structures are preferred.
	 * 
	 * @param type
	 * @param numElements
	 * @param <U>
	 * @return
	 */
	public static <U extends Allocatable<U>> IndexedDataSource<U>
		allocate(U type, long numElements)
	{
		// catch the most basic of errors
		
		if (numElements < 0)
			throw new IllegalArgumentException("negative index exception");
		
		// try the fastest simplest storage list type
		
		try {
			return ArrayStorage.allocate(type, numElements);
		}
		catch (Exception e) {
			// out of memory
			// or requested size is larger than any array can hold
			// or others I might be too accepting of
		}
		
		// assume it was a list too long problem. try a bigger in ram solution.
		
		try {
			return ExtMemStorage.allocate(type, numElements);
		}
		catch (Exception e) {
			// out of memory
			// or requested size is larger than any array can hold
			// or others I might be too accepting of
		}
		
		// fall back to a virtual file solution
		
		return FileStorage.allocate(type, numElements);
	}
	
	/**
	 * Allocate a list of elements using the method specified by the
	 * api user.
	 * 
	 * @param strategy
	 * @param type
	 * @param numElements
	 * @param <U>
	 * @return
	 */
	public static <U extends Allocatable<U>> IndexedDataSource<U>
		allocate(StorageConstruction strategy, U type, long numElements)
	{
		// catch the most basic of errors
		
		if (numElements < 0)
			throw new IllegalArgumentException("negative index exception");
		
		if (strategy == StorageConstruction.MEM_ARRAY) {

			try {
				return ArrayStorage.allocate(type, numElements);
			}
			catch (Exception e) {
				// out of memory
				// or requested size is larger than any array can hold
				// or others I might be too accepting of
			}
			
			// assume it was a list too long problem. try a bigger in ram solution.
			
			try {
				return ExtMemStorage.allocate(type, numElements);
			}
			catch (Exception e) {
				// out of memory
				// or requested size is larger than any array can hold
				// or others I might be too accepting of
			}
			// if would be nice if we tested size and if too big for array we tried to
			//   allocate a BigListDataSource. But that ctor needs an Algebra. At one
			//   time I tried changing these signatures to take (numElem, algebra)
			//   which was pretty elegant but it killed the ability to support the
			//   Point type with varying sized elements (2d or 3d or 4d etc.).
		}
		else if (strategy == StorageConstruction.MEM_SPARSE) {
		
			return SparseStorage.allocate(type, numElements);
		}
		else if (strategy == StorageConstruction.MEM_VIRTUAL) {
		
			return FileStorage.allocate(type, numElements);
		}
		throw new IllegalArgumentException("Unknown storage strategy "+strategy);
	}
	
	/**
	 * Allocate a list of byte based elements and initialize their values
	 * from a passed in byte array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & ByteCoder>
		IndexedDataSource<U> allocate(U type, byte[] array)
	{
		if (array.length % type.byteCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.byteCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.byteCount()) {
			tmp.fromByteArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of short based elements and initialize their values
	 * from a passed in short array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & ShortCoder>
		IndexedDataSource<U> allocate(U type, short[] array)
	{
		if (array.length % type.shortCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.shortCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.shortCount()) {
			tmp.fromShortArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of int based elements and initialize their values
	 * from a passed in int array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & IntCoder>
		IndexedDataSource<U> allocate(U type, int[] array)
	{
		if (array.length % type.intCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.intCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.intCount()) {
			tmp.fromIntArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of long based elements and initialize their values
	 * from a passed in long array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & LongCoder>
		IndexedDataSource<U> allocate(U type, long[] array)
	{
		if (array.length % type.longCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.longCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.longCount()) {
			tmp.fromLongArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of float based elements and initialize their values
	 * from a passed in float array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & FloatCoder>
		IndexedDataSource<U> allocate(U type, float[] array)
	{
		if (array.length % type.floatCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.floatCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.floatCount()) {
			tmp.fromFloatArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of double based elements and initialize their values
	 * from a passed in double array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & DoubleCoder>
		IndexedDataSource<U> allocate(U type, double[] array)
	{
		if (array.length % type.doubleCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.doubleCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.doubleCount()) {
			tmp.fromDoubleArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of BigInteger based elements and initialize their values
	 * from a passed in BigInteger array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & BigIntegerCoder>
		IndexedDataSource<U> allocate(U type, BigInteger[] array)
	{
		if (array.length % type.bigIntegerCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.bigIntegerCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.bigIntegerCount()) {
			tmp.fromBigIntegerArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of BigDecimal based elements and initialize their values
	 * from a passed in BigDecimal array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & BigDecimalCoder>
		IndexedDataSource<U> allocate(U type, BigDecimal[] array)
	{
		if (array.length % type.bigDecimalCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.bigDecimalCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.bigDecimalCount()) {
			tmp.fromBigDecimalArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	/**
	 * Allocate a list of String based elements and initialize their values
	 * from a passed in String array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & StringCoder>
		IndexedDataSource<U> allocate(U type, String[] array)
	{
		if (array.length % type.stringCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.stringCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.stringCount()) {
			tmp.fromStringArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}

	/**
	 * Allocate a list of char based elements and initialize their values
	 * from a passed in char array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & CharCoder>
		IndexedDataSource<U> allocate(U type, char[] array)
	{
		if (array.length % type.charCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.charCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.charCount()) {
			tmp.fromCharArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}

	/**
	 * Allocate a list of boolean based elements and initialize their values
	 * from a passed in boolean array of data.
	 * 
	 * @param type
	 * @param array
	 * @return
	 */
	public static <U extends Allocatable<U> & BooleanCoder>
		IndexedDataSource<U> allocate(U type, boolean[] array)
	{
		if (array.length % type.booleanCount() != 0)
			throw new IllegalArgumentException("allocation must be correctly aligned");
		IndexedDataSource<U> list = allocate(type, array.length / type.booleanCount());
		U tmp = type.allocate();
		for (int i = 0, offset = 0; i < list.size(); i++, offset += type.booleanCount()) {
			tmp.fromBooleanArray(array, offset);
			list.set(i, tmp);
		}
		return list;
	}
	
	// do not instantiate
	
	private Storage() { }
}
