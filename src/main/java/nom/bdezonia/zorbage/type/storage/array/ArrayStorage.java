/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
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
package nom.bdezonia.zorbage.type.storage.array;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.data.bool.BooleanMember;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.BitCoder;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.type.storage.coder.FloatCoder;
import nom.bdezonia.zorbage.type.storage.coder.IntCoder;
import nom.bdezonia.zorbage.type.storage.coder.LongCoder;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ArrayStorage {
	
	/**
	 * 
	 * @param size
	 * @param type
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <U extends Allocatable<U>> IndexedDataSource<?,U> allocate(long size, U type) {

		if (type instanceof DoubleCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageFloat64(size, (DoubleCoder)type);
		}
		if (type instanceof FloatCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageFloat32(size, (FloatCoder)type);
		}
		if (type instanceof LongCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageSignedInt64(size, (LongCoder)type);
		}
		if (type instanceof IntCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageSignedInt32(size, (IntCoder)type);
		}
		if (type instanceof ShortCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageSignedInt16(size, (ShortCoder)type);
		}
		if (type instanceof BooleanCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageBoolean(size, (BooleanCoder)type);
		}
		// Best if one of last as many types might support Bytes by default but prefer
		// other types for speed
		if (type instanceof ByteCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageSignedInt8(size, (ByteCoder)type);
		}
		
		// Best if last: since bit types are slow
		if (type instanceof BitCoder) {
			return (IndexedDataSource<?,U>) new ArrayStorageBit(size, (BitCoder)type);
		}

		throw new IllegalArgumentException("Unsupported type in ArrayStorage");
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static IndexedDataSource<?,SignedInt8Member> allocateBytes(byte[] bytes) {
		SignedInt8Member type = new SignedInt8Member();
		IndexedDataSource<?,SignedInt8Member> store =
				new ArrayStorageSignedInt8<SignedInt8Member>(bytes.length, type);
		for (int i = 0; i < bytes.length; i++) {
			type.setV(bytes[i]);
			store.set(i, type);
		}
		return store;
	}
	
	/**
	 * 
	 * @param shorts
	 * @return
	 */
	public static IndexedDataSource<?,SignedInt16Member> allocateShorts(short[] shorts) {
		SignedInt16Member type = new SignedInt16Member();
		IndexedDataSource<?,SignedInt16Member> store =
				new ArrayStorageSignedInt16<SignedInt16Member>(shorts.length, type);
		for (int i = 0; i < shorts.length; i++) {
			type.setV(shorts[i]);
			store.set(i, type);
		}
		return store;
	}
	
	/**
	 * 
	 * @param ints
	 * @return
	 */
	public static IndexedDataSource<?,SignedInt32Member> allocateInts(int[] ints) {
		SignedInt32Member type = new SignedInt32Member();
		IndexedDataSource<?,SignedInt32Member> store =
				new ArrayStorageSignedInt32<SignedInt32Member>(ints.length, type);
		for (int i = 0; i < ints.length; i++) {
			type.setV(ints[i]);
			store.set(i, type);
		}
		return store;
	}
	
	/**
	 * 
	 * @param longs
	 * @return
	 */
	public static IndexedDataSource<?,SignedInt64Member> allocateLongs(long[] longs) {
		SignedInt64Member type = new SignedInt64Member();
		IndexedDataSource<?,SignedInt64Member> store =
				new ArrayStorageSignedInt64<SignedInt64Member>(longs.length, type);
		for (int i = 0; i < longs.length; i++) {
			type.setV(longs[i]);
			store.set(i, type);
		}
		return store;
	}
	
	/**
	 * 
	 * @param floats
	 * @return
	 */
	public static IndexedDataSource<?,Float32Member> allocateFloats(float[] floats) {
		Float32Member type = new Float32Member();
		IndexedDataSource<?,Float32Member> store =
				new ArrayStorageFloat32<Float32Member>(floats.length, type);
		for (int i = 0; i < floats.length; i++) {
			type.setV(floats[i]);
			store.set(i, type);
		}
		return store;
	}
	
	/**
	 * 
	 * @param doubles
	 * @return
	 */
	public static IndexedDataSource<?,Float64Member> allocateDoubles(double[] doubles) {
		Float64Member type = new Float64Member();
		IndexedDataSource<?,Float64Member> store =
				new ArrayStorageFloat64<Float64Member>(doubles.length, type);
		for (int i = 0; i < doubles.length; i++) {
			type.setV(doubles[i]);
			store.set(i, type);
		}
		return store;
	}
	
	/**
	 * 
	 * @param booleans
	 * @return
	 */
	public static IndexedDataSource<?,BooleanMember> allocateBooleans(boolean[] booleans) {
		BooleanMember type = new BooleanMember();
		IndexedDataSource<?,BooleanMember> store =
				new ArrayStorageBoolean<BooleanMember>(booleans.length, type);
		for (int i = 0; i < booleans.length; i++) {
			type.setV(booleans[i]);
			store.set(i, type);
		}
		return store;
	}

}
