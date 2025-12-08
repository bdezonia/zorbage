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
package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.HighPrecRepresentation;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ReadOnlyHighPrecisionDataSource<T extends Algebra<T,U>, U extends HighPrecRepresentation>
	implements IndexedDataSource<HighPrecisionMember>
{
	private final T alg;
	private final IndexedDataSource<U> src;
	private final ThreadLocal<U> tmp;
	
	/**
	 * Make an {@link IndexedDataSource} that extracts numbers as {@link HighPrecisionMember}s. Thus one
	 * can run accurate statistical analysis of huge lists or lists of values whose sum etc. might overflow 
	 * or lose precision using its native type.
	 * 
	 * @param algebra The {@link Algebra} that can manipulate the native type.
	 * @param src The {@link IndexedDataSource} that contains native types.
	 */
	public ReadOnlyHighPrecisionDataSource(T algebra, IndexedDataSource<U> src) {
		this.alg = algebra;
		this.src = src;
		this.tmp = new ThreadLocal<U>() {
			@Override
			protected U initialValue() {
				return alg.construct();
			}
		};
	}
	
	@Override
	public ReadOnlyHighPrecisionDataSource<T,U> duplicate() {
		// shallow copy
		return new ReadOnlyHighPrecisionDataSource<T, U>(alg, src);
	}

	@Override
	public void set(long index, HighPrecisionMember value) {
		throw new IllegalArgumentException("cannot set a value when using a readonly data source");
	}

	@Override
	public void get(long index, HighPrecisionMember value) {
		U val = tmp.get();
		src.get(index, val);
		val.toHighPrec(value);
	}

	@Override
	public long size() {
		return src.size();
	}

	@Override
	public StorageConstruction storageType() {
		return src.storageType();
	}

	@Override
	public boolean accessWithOneThread() {
		return src.accessWithOneThread();
	}
}
