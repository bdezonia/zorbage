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
package nom.bdezonia.zorbage.storage.extmem;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;

/**
 * 
 * @author Barry DeZonia
 *
 * This storage type is designed to be an in memory data structure for
 * storing elements. It can contain up to 128 gig elements and each
 * element can have multiple components.
 */
public class ExtMemStorageBigDecimal<U extends BigDecimalCoder & Allocatable<U>>
	extends AbstractExtMemStorage
	implements IndexedDataSource<U>, Allocatable<ExtMemStorageBigDecimal<U>>
{
	private final U type;
	private final long numElements;
	private final BigDecimal[][] pipedContainer;
	
	public ExtMemStorageBigDecimal(U type, long numElements) {
		super();
		if (numElements < 0)
			throw new NegativeArraySizeException();
		this.type = type.allocate();
		this.numElements = numElements;
		long totalPipeds = (numElements / ELEMENTS_PER_PIPED) + ( numElements % ELEMENTS_PER_PIPED > 0 ? 1 : 0);
		if (totalPipeds > Integer.MAX_VALUE)
			throw new IllegalArgumentException(
					"storage cannot allocate enough RAM for this many elements");
		pipedContainer = new BigDecimal[(int)totalPipeds][];
		for (int i = 0; i < totalPipeds; i++) {
			pipedContainer[i] = new BigDecimal[ELEMENTS_PER_PIPED * type.bigDecimalCount()];
			for (int j = 0; j < pipedContainer[i].length; j++) {
				pipedContainer[i][j] = BigDecimal.ZERO;
			}
		}
	}
	
	@Override
	public ExtMemStorageBigDecimal<U> duplicate() {
		ExtMemStorageBigDecimal<U> newData =
				new ExtMemStorageBigDecimal<U>(type, numElements);
		copyDataTo(newData);
		return newData;
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_ARRAY;
	}

	@Override
	public ExtMemStorageBigDecimal<U> allocate() {
		return new ExtMemStorageBigDecimal<U>(type, numElements);
	}

	@Override
	public void set(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		int containerIndex = (int) (index / ELEMENTS_PER_PIPED);
		int subIndex = (int) (index % ELEMENTS_PER_PIPED);
		value.toBigDecimalArray(pipedContainer[containerIndex], subIndex * value.bigDecimalCount());
	}

	@Override
	public void get(long index, U value) {
		if (index < 0)
			throw new IllegalArgumentException("negative index exception");
		if (index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		int containerIndex = (int) (index / ELEMENTS_PER_PIPED);
		int subIndex = (int) (index % ELEMENTS_PER_PIPED);
		value.fromBigDecimalArray(pipedContainer[containerIndex], subIndex * value.bigDecimalCount());
	}

	@Override
	public long size() {
		return numElements;
	}
	
	private void copyDataTo(ExtMemStorageBigDecimal<U> other) {
		for (int i = 0; i < pipedContainer.length; i++) {
			for (int j = 0; j < pipedContainer[j].length; j++) {
				other.pipedContainer[i][j] = pipedContainer[i][j];
			}
		}
	}

	@Override
	public boolean accessWithOneThread() {
		return false;
	}
}
