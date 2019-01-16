/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.universal;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.type.algebra.DimensionsResizable;

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
			values = new BigList<OctonionRepresentation>(count);
			for (long i = 0; i < count; i++) {
				values.add(null);
			}
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
		if (this.values.size() != values.size())
			throw new IllegalArgumentException("Incorrect number of values passed");
		for (long i = 0; i < this.values.size(); i++) {
			this.values.set(i, values.get(i));
		}
	}
	
	// matrix value
	
	public void setMatrix(long r, long c, BigList<OctonionRepresentation> values) {
		setDims(new long[] {c,r});
		if (this.values.size() != values.size())
			throw new IllegalArgumentException("Incorrect number of values passed");
		for (long i = 0; i < this.values.size(); i++) {
			this.values.set(i, values.get(i));
		}
	}
	
	// tensor value
	
	public void setTensor(long[] dims, BigList<OctonionRepresentation> values) {
		setDims(dims);
		if (this.values.size() != values.size())
			throw new IllegalArgumentException("Incorrect number of values passed");
		for (long i = 0; i < this.values.size(); i++) {
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
		BigList<OctonionRepresentation> list = new BigList<OctonionRepresentation>(size);
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
		BigList<OctonionRepresentation> list = new BigList<OctonionRepresentation>(size);
		for (long i = 0; i < size; i++) {
			list.set(i, nonNull(values.get(i)));
		}
		return list;
	}

	public long[] getTensorDims() {
		return dims.clone();
	}

	public BigList<OctonionRepresentation> getTensor() {
		BigList<OctonionRepresentation> list = new BigList<OctonionRepresentation>(values.size());
		for (long i = 0; i < values.size(); i++) {
			list.set(i, nonNull(values.get(i)));
		}
		return list;
	}
	
	private OctonionRepresentation nonNull(OctonionRepresentation o) {
		if (o != null)
			return o;
		return new OctonionRepresentation();
	}
	
}
