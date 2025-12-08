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
package nom.bdezonia.zorbage.type.helper;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.algebra.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SubRModuleBridge<U> implements RModuleMember<U> {

	private final U zero;
	private final RModuleMember<U> rmod;
	private long startElem, countElems;
	
	public SubRModuleBridge(Algebra<?,U> algebra, RModuleMember<U> rmod) {
		this.zero = algebra.construct();
		this.rmod = rmod;
		this.startElem = 0;
		this.countElems = rmod.length();
	}

	public void setSubrange(long rmodStartElem, long rmodCountElems) {
		if (rmodStartElem < 0)
			throw new IllegalArgumentException("negative start elem");
		if (rmodCountElems < 1)
			throw new IllegalArgumentException("non-positive count elems");
		if (rmodStartElem + rmodCountElems > rmod.length())
			throw new IllegalArgumentException("subelement beyond end of rmodule");
		this.startElem = rmodStartElem;
		this.countElems = rmodCountElems;
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("negative dimension exception");
		if (d == 0) return countElems;
		return 1;
	}

	@Override
	public int numDimensions() {
		return 1;
	}

	@Override
	public long length() {
		return countElems;
	}

	@Override
	public boolean alloc(long len) {
		if (len == countElems)
			return false;
		throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
	}

	@Override
	public void init(long len) {
		if (len == countElems) {
			for (long i = 0; i < countElems; i++) {
				setV(i, zero);
			}
		}
		else {
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
		}
	}

	@Override
	public void reshape(long len) {
		if (len != countElems) {
			throw new IllegalArgumentException("read only wrapper does not allow reallocation of data");
		}
	}

	@Override
	public void getV(long i, U value) {
		rmod.getV(startElem + i, value);
	}

	@Override
	public void setV(long i, U value) {
		rmod.setV(startElem + i, value);
	}

	@Override
	public StorageConstruction storageType() {
		return rmod.storageType();
	}
	
	@Override
	public boolean accessWithOneThread() {
		return rmod.accessWithOneThread();
	}
}
