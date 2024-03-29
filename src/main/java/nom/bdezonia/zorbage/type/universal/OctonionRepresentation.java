/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.GetOctonion;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.SetOctonion;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionRepresentation
	implements
		Allocatable<OctonionRepresentation>, Duplicatable<OctonionRepresentation>,
		Gettable<OctonionRepresentation>, Settable<OctonionRepresentation>,
		GetOctonion<HighPrecisionMember>, SetOctonion<HighPrecisionMember>
{
	private BigDecimal r, i, j, k, l, i0, j0, k0;
	
	public OctonionRepresentation() {
		this(null,null,null,null,null,null,null,null);
	}

	public OctonionRepresentation(BigDecimal r) {
		this(r,null,null,null,null,null,null,null);
	}

	public OctonionRepresentation(BigDecimal r, BigDecimal i) {
		this(r,i,null,null,null,null,null,null);
	}

	public OctonionRepresentation(BigDecimal r, BigDecimal i, BigDecimal j, BigDecimal k) {
		this(r,i,j,k,null,null,null,null);
	}

	public OctonionRepresentation(BigDecimal r, BigDecimal i, BigDecimal j, BigDecimal k,
			BigDecimal l, BigDecimal i0, BigDecimal j0, BigDecimal k0)
	{
		this.r = BigDecimalUtils.value(r);
		this.i = BigDecimalUtils.value(i);
		this.j = BigDecimalUtils.value(j);
		this.k = BigDecimalUtils.value(k);
		this.l = BigDecimalUtils.value(l);
		this.i0 = BigDecimalUtils.value(i0);
		this.j0 = BigDecimalUtils.value(j0);
		this.k0 = BigDecimalUtils.value(k0);
	}
	
	public OctonionRepresentation(OctonionRepresentation other) {
		set(other);
	}
	
	public BigDecimal r() { return r; }
	public BigDecimal i() { return i; }
	public BigDecimal j() { return j; }
	public BigDecimal k() { return k; }
	public BigDecimal l() { return l; }
	public BigDecimal i0() { return i0; }
	public BigDecimal j0() { return j0; }
	public BigDecimal k0() { return k0; }
	
	public void setR(BigDecimal r) { this.r = BigDecimalUtils.value(r); }
	public void setI(BigDecimal i) { this.i = BigDecimalUtils.value(i); }
	public void setJ(BigDecimal j) { this.j = BigDecimalUtils.value(j); }
	public void setK(BigDecimal k) { this.k = BigDecimalUtils.value(k); }
	public void setL(BigDecimal l) { this.l = BigDecimalUtils.value(l); }
	public void setI0(BigDecimal i0) { this.i0 = BigDecimalUtils.value(i0); }
	public void setJ0(BigDecimal j0) { this.j0 = BigDecimalUtils.value(j0); }
	public void setK0(BigDecimal k0) { this.k0 = BigDecimalUtils.value(k0); }
	
	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(i);
		v = Hasher.PRIME * v + Hasher.hashCode(j);
		v = Hasher.PRIME * v + Hasher.hashCode(k);
		v = Hasher.PRIME * v + Hasher.hashCode(l);
		v = Hasher.PRIME * v + Hasher.hashCode(i0);
		v = Hasher.PRIME * v + Hasher.hashCode(j0);
		v = Hasher.PRIME * v + Hasher.hashCode(k0);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof OctonionRepresentation) {
			OctonionRepresentation oct = (OctonionRepresentation) o;
			return
				this.r.equals(oct.r) &&
				this.i.equals(oct.i) &&
				this.j.equals(oct.j) &&
				this.k.equals(oct.k) &&
				this.l.equals(oct.l) &&
				this.i0.equals(oct.i0) &&
				this.j0.equals(oct.j0) &&
				this.k0.equals(oct.k0);
		}
		return false;
	}

	@Override
	public OctonionRepresentation allocate() {
		return new OctonionRepresentation();
	}

	@Override
	public OctonionRepresentation duplicate() {
		return new OctonionRepresentation(this);
	}

	@Override
	public void getR(HighPrecisionMember v) {
		v.setV(this.r);
	}

	@Override
	public void getI(HighPrecisionMember v) {
		v.setV(this.i);
	}

	@Override
	public void getJ(HighPrecisionMember v) {
		v.setV(this.j);
	}

	@Override
	public void getK(HighPrecisionMember v) {
		v.setV(this.k);
	}

	@Override
	public void getL(HighPrecisionMember v) {
		v.setV(this.l);
	}

	@Override
	public void getI0(HighPrecisionMember v) {
		v.setV(this.i0);
	}

	@Override
	public void getJ0(HighPrecisionMember v) {
		v.setV(this.j0);
	}

	@Override
	public void getK0(HighPrecisionMember v) {
		v.setV(this.k0);
	}

	@Override
	public void setR(HighPrecisionMember val) {
		this.r = val.v();
	}

	@Override
	public void setI(HighPrecisionMember val) {
		this.i = val.v();
	}

	@Override
	public void setJ(HighPrecisionMember val) {
		this.j = val.v();
	}

	@Override
	public void setK(HighPrecisionMember val) {
		this.k = val.v();
	}

	@Override
	public void setL(HighPrecisionMember val) {
		this.l = val.v();
	}

	@Override
	public void setI0(HighPrecisionMember val) {
		this.i0 = val.v();
	}

	@Override
	public void setJ0(HighPrecisionMember val) {
		this.j0 = val.v();
	}

	@Override
	public void setK0(HighPrecisionMember val) {
		this.k0 = val.v();
	}

	@Override
	public void set(OctonionRepresentation other) {
		this.r = other.r;
		this.i = other.i;
		this.j = other.j;
		this.k = other.k;
		this.l = other.l;
		this.i0 = other.i0;
		this.j0 = other.j0;
		this.k0 = other.k0;
	}

	@Override
	public void get(OctonionRepresentation other) {
		other.r = this.r;
		other.i = this.i;
		other.j = this.j;
		other.k = this.k;
		other.l = this.l;
		other.i0 = this.i0;
		other.j0 = this.j0;
		other.k0 = this.k0;
	}
}
