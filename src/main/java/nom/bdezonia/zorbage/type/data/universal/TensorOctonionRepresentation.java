/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.type.algebra.DimensionsResizable;
import nom.bdezonia.zorbage.util.BigList;
import nom.bdezonia.zorbage.util.LongUtils;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorOctonionRepresentation implements DimensionsResizable {

	private static final OctonionRepresentation ZERO = new OctonionRepresentation();
	
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
	
	public void setFirstValue(OctonionRepresentation value) {
		setDims(new long[] {});
		values.set(0, value);
	}
	
	// vector value
	
	public void setFirstRModule(long n, OctonionRepresentation[] values) {
		if (n != values.length)
			throw new IllegalArgumentException("Incorrect number of values passed");
		setDims(new long[] {n});
		for (int i = 0; i < values.length; i++) {
			this.values.set(i, values[i]);
		}
	}
	
	// matrix value
	
	public void setFirstMatrix(long r, long c, OctonionRepresentation[] values) {
		if (r*c != values.length)
			throw new IllegalArgumentException("Incorrect number of values passed");
		setDims(new long[] {c,r});
		for (int i = 0; i < values.length; i++) {
			this.values.set(i, values[i]);
		}
	}
	
	// tensor value
	
	public void setTensor(long[] dims, OctonionRepresentation[] values) {
		if (LongUtils.numElements(dims) != values.length)
			throw new IllegalArgumentException("Incorrect number of values passed");
		setDims(dims);
		for (int i = 0; i < values.length; i++) {
			this.values.set(i, values[i]);
		}
	}
	
	public OctonionRepresentation getFirstValue() {
		return nonNull(values.get(0));
	}
	
	public void scaleBy(BigDecimal v) {
		OctonionRepresentation o = null;
		for (long i = 0; i < values.size(); i++) {
			o = values.get(i);
			o.setR(v.multiply(o.r()));
			o.setI(v.multiply(o.i()));
			o.setJ(v.multiply(o.j()));
			o.setK(v.multiply(o.k()));
			o.setL(v.multiply(o.l()));
			o.setI0(v.multiply(o.i0()));
			o.setJ0(v.multiply(o.j0()));
			o.setK0(v.multiply(o.k0()));
			// TODO: unnecessary?
			// values.set(i, o);
		}
	}
	
	private OctonionRepresentation nonNull(OctonionRepresentation o) {
		if (o == null)
			return ZERO; // TODO: does this make the constant malleable? Should I duplicate() it?
		return o;
	}
	
}
