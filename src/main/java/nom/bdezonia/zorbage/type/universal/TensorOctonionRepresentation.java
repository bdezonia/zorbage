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
package nom.bdezonia.zorbage.type.universal;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.algebra.DimensionsResizable;
import nom.bdezonia.zorbage.misc.Hasher;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorOctonionRepresentation implements DimensionsResizable {

	private BigList<OctonionRepresentation> values;
	private long[] dims;
	
	public TensorOctonionRepresentation() {
		 setDims(new long[] {});
	}
	
	@Override
	public void setDims(long[] dims) {
		long count = LongUtils.numElements(dims);
		if (count == 0) count = 1;
		if (values == null || count != values.size()) {
			values = new BigList<OctonionRepresentation>(count, new OctonionRepresentation());
		}
		this.dims = dims.clone();
	}
	
	public long[] getDims() {
		return dims;
	}
	
	public boolean isValue() {
		return dims.length == 0;
	}

	public boolean isRModule() {
		return dims.length == 1;
	}

	public boolean isMatrix() {
		return dims.length == 2;
	}

	public boolean isTensor() {
		return dims.length > 2;
	}

	// single value
	
	public void setValue(OctonionRepresentation value) {
		setDims(new long[] {});
		values.set(0, value);
	}
	
	// vector/rmodule value
	
	public void setRModule(long n, BigList<OctonionRepresentation> values) {
		setDims(new long[] {n});
		long thisValsSize = this.values.size();
		if (thisValsSize != values.size())
			throw new IllegalArgumentException("Incorrect number of values passed");
		for (long i = 0; i < thisValsSize; i++) {
			this.values.set(i, values.get(i));
		}
	}
	
	// matrix value
	
	public void setMatrix(long r, long c, BigList<OctonionRepresentation> values) {
		setDims(new long[] {c,r});
		long thisValsSize = this.values.size();
		if (thisValsSize != values.size())
			throw new IllegalArgumentException("Incorrect number of values passed");
		for (long i = 0; i < thisValsSize; i++) {
			this.values.set(i, values.get(i));
		}
	}
	
	// tensor value
	
	public void setTensor(long[] dims, BigList<OctonionRepresentation> values) {
		setDims(dims);
		long thisValsSize = this.values.size();
		if (thisValsSize != values.size())
			throw new IllegalArgumentException("Incorrect number of values passed");
		for (long i = 0; i < thisValsSize; i++) {
			this.values.set(i, values.get(i));
		}
	}
	
	public OctonionRepresentation getValue() {
		return nonNull(values.get(0));
	}

	public long getRModuleDim() {
		long size = 1;
		if (dims.length >= 1 && dims[0] > 0)
			size = dims[0];
		return size;
	}
	
	public BigList<OctonionRepresentation> getRModule() {
		long size = getRModuleDim();
		BigList<OctonionRepresentation> list = new BigList<OctonionRepresentation>(size, new OctonionRepresentation());
		for (long i = 0; i < size; i++) {
			list.set(i, nonNull(values.get(i)));
		}
		return list;
	}
	
	public long getMatrixColDim() {
		long size = 1;
		if (dims.length >= 1 && dims[0] > 0)
			size = dims[0];
		return size;
	}
	
	public long getMatrixRowDim() {
		long size = 1;
		if (dims.length >= 2 && dims[1] > 0)
			size = dims[1];
		return size;
	}
	
	public BigList<OctonionRepresentation> getMatrix() {
		long r = getMatrixRowDim();
		long c = getMatrixColDim();
		long size = LongUtils.numElements(new long[] {c,r});
		BigList<OctonionRepresentation> list = new BigList<OctonionRepresentation>(size, new OctonionRepresentation());
		for (long i = 0; i < size; i++) {
			list.set(i, nonNull(values.get(i)));
		}
		return list;
	}

	public long[] getTensorDims() {
		return dims.clone();
	}

	public BigList<OctonionRepresentation> getTensor() {
		long valuesSize = values.size();
		BigList<OctonionRepresentation> list = new BigList<OctonionRepresentation>(valuesSize, new OctonionRepresentation());
		for (long i = 0; i < valuesSize; i++) {
			list.set(i, nonNull(values.get(i)));
		}
		return list;
	}
	
	private OctonionRepresentation nonNull(OctonionRepresentation o) {
		if (o != null)
			return o;
		return new OctonionRepresentation();
	}
	
	@Override
	public int hashCode() {
		OctonionRepresentation tmp = null;
		long len = values.size();
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(len);
		if (len > 0) {
			tmp = values.get(0);
			v = Hasher.PRIME * v + tmp.hashCode();
		}
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof TensorOctonionRepresentation) {
			TensorOctonionRepresentation t = (TensorOctonionRepresentation) o;
			if (this.dims.length != t.dims.length)
				return false;
			for (int i = 0; i < this.dims.length; i++) {
				if (this.dims[i] != t.dims[i])
					return false;
			}
			for (long i = 0; i < values.size(); i++) {
				OctonionRepresentation a = this.values.get(i);
				OctonionRepresentation b = t.values.get(i);
				if (!a.equals(b))
					return false;
			}
			return true;
		}
		return false;
	}
}
