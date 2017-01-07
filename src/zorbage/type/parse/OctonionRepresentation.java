/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.parse;

import java.math.BigDecimal;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionRepresentation {

	private BigDecimal r, i, j, k, l, i0, j0, k0;
	
	public OctonionRepresentation(BigDecimal r, BigDecimal i, BigDecimal j, BigDecimal k,
			BigDecimal l, BigDecimal i0, BigDecimal j0, BigDecimal k0)
	{
		if (r == null) r = BigDecimal.ZERO;
		if (i == null) i = BigDecimal.ZERO;
		if (j == null) j = BigDecimal.ZERO;
		if (k == null) k = BigDecimal.ZERO;
		if (l == null) l = BigDecimal.ZERO;
		if (i0 == null) i0 = BigDecimal.ZERO;
		if (j0 == null) j0 = BigDecimal.ZERO;
		if (k0 == null) k0 = BigDecimal.ZERO;
		this.r = r;
		this.i = i;
		this.j = j;
		this.k = k;
		this.l = l;
		this.i0 = i0;
		this.j0 = j0;
		this.k0 = k0;
	}
	
	public BigDecimal r() { return r; }
	public BigDecimal i() { return i; }
	public BigDecimal j() { return j; }
	public BigDecimal k() { return k; }
	public BigDecimal l() { return l; }
	public BigDecimal i0() { return i0; }
	public BigDecimal j0() { return j0; }
	public BigDecimal k0() { return k0; }
}
