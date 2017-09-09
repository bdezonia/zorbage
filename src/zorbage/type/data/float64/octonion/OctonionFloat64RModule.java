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
package zorbage.type.data.float64.octonion;

import zorbage.type.algebra.RModule;
import zorbage.type.ctor.Constructible1dLong;
import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64RModule
  implements
    RModule<OctonionFloat64RModule,OctonionFloat64RModuleMember,OctonionFloat64Group,OctonionFloat64Member>,
    Constructible1dLong<OctonionFloat64RModuleMember>
{
	private static final OctonionFloat64Group odbl = new OctonionFloat64Group();
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();
	
	public OctonionFloat64RModule() {
	}
	
	@Override
	public void zero(OctonionFloat64RModuleMember a) {
		for (long i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, tmp);
			odbl.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void add(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			odbl.add(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (long i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public void subtract(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			odbl.subtract(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (long i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public boolean isEqual(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			if (odbl.isNotEqual(atmp, btmp))
				return false;
		}
		return true;
	}

	@Override
	public boolean isNotEqual(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		return !isEqual(a,b);
	}

	@Override
	public OctonionFloat64RModuleMember construct() {
		return new OctonionFloat64RModuleMember();
	}

	@Override
	public OctonionFloat64RModuleMember construct(OctonionFloat64RModuleMember other) {
		return new OctonionFloat64RModuleMember(other);
	}

	@Override
	public OctonionFloat64RModuleMember construct(String s) {
		return new OctonionFloat64RModuleMember(s);
	}

	@Override
	public OctonionFloat64RModuleMember construct(MemoryConstruction m, StorageConstruction s, long d1) {
		return new OctonionFloat64RModuleMember(m, s, d1);
	}

	@Override
	public void assign(OctonionFloat64RModuleMember from, OctonionFloat64RModuleMember to) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long i = 0; i < from.length(); i++) {
			from.v(i, tmp);
			to.setV(i, tmp);
		}
		for (long i = from.length(); i < to.length(); i++) {
			to.setV(i, ZERO);
		}
	}

	@Override
	public void norm(OctonionFloat64RModuleMember a, OctonionFloat64Member b) {
		throw new IllegalArgumentException("TODO");
		// TODO
		//OctonionFloat64Member norm2 = new OctonionFloat64Member();
		//OctonionFloat64Member tmp = new OctonionFloat64Member();
		//OctonionFloat64Member tmp2 = new OctonionFloat64Member();
		//for (int i = 0; i < a.length(); i++) {
		//	a.v(i, tmp);
		//	g.multiply(tmp,tmp,tmp2);
		//	g.add(norm2, tmp2, norm2);
		//}
		//OctonionFloat64Member norm = Math.sqrt(norm2); // TODO this is the tricky part
		//b.set(norm);
		// is a norm of a Octonion RModule a Octonion number or a real number? read.
	}

	@Override
	public void scale(OctonionFloat64Member scalar, OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		// two loops minimizes memory allocations
		final long min = Math.min(a.length(), b.length());
		for (long i = 0; i < min; i++) {
			a.v(i, tmp);
			odbl.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
		final long max = Math.max(a.length(), b.length());
		for (long i = min; i < max; i++) {
			a.v(i, tmp);
			odbl.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void crossProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
		// kludgy: 3 dim only. treating lower dim RModules as having zeroes in other positions.
		if (!compatible(3,a) || !compatible(3,b))
			throw new UnsupportedOperationException("RModule cross product defined for 3 dimensions");
		OctonionFloat64RModuleMember tmp = new OctonionFloat64RModuleMember(new double[3*8]);
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		OctonionFloat64Member term1 = new OctonionFloat64Member();
		OctonionFloat64Member term2 = new OctonionFloat64Member();
		OctonionFloat64Member t = new OctonionFloat64Member();
		a.v(1, atmp);
		b.v(2, btmp);
		odbl.multiply(atmp, btmp, term1);
		a.v(2, atmp);
		b.v(1, btmp);
		odbl.multiply(atmp, btmp, term2);
		odbl.subtract(term1, term2, t);
		tmp.setV(0, t);
		a.v(2, atmp);
		b.v(0, btmp);
		odbl.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(2, btmp);
		odbl.multiply(atmp, btmp, term2);
		odbl.subtract(term1, term2, t);
		tmp.setV(1, t);
		a.v(0, atmp);
		b.v(1, btmp);
		odbl.multiply(atmp, btmp, term1);
		a.v(1, atmp);
		b.v(0, btmp);
		odbl.multiply(atmp, btmp, term2);
		odbl.subtract(term1, term2, t);
		tmp.setV(2, t);
		assign(tmp, c);
	}

	private boolean compatible(long dim, OctonionFloat64RModuleMember v) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long i = dim; i < v.length(); i++) {
			v.v(i, tmp);
			if (odbl.isNotEqual(tmp, ZERO))
				return false;
		}
		return true;
	}
	
	@Override
	public void dotProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64Member c) {
		final long min = Math.min(a.length(), b.length());
		OctonionFloat64Member sum = new OctonionFloat64Member();
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		for (long i = 0; i < min; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			odbl.multiply(atmp, btmp, btmp);
			odbl.add(sum, btmp, sum);
		}
		c.set(sum);
	}

	@Override
	public void perpDotProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64Member c) {
		// kludgy: 2 dim only. treating lower dim RModules as having zeroes in other positions.
		if (!compatible(2,a) || !compatible(2,b))
			throw new UnsupportedOperationException("RModule perp dot product defined for 2 dimensions");
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		OctonionFloat64Member term1 = new OctonionFloat64Member();
		OctonionFloat64Member term2 = new OctonionFloat64Member();
		a.v(1, atmp);
		b.v(0, btmp);
		odbl.negate(atmp, atmp);
		odbl.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(1, btmp);
		odbl.multiply(atmp, btmp, term2);
		odbl.add(term1, term2, c);
	}

	@Override
	public void vectorTripleProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c,
			OctonionFloat64RModuleMember d)
	{
		OctonionFloat64RModuleMember b_cross_c = new OctonionFloat64RModuleMember(new double[3*8]);
		crossProduct(b, c, b_cross_c);
		crossProduct(a, b_cross_c, d);
	}

	@Override
	public void scalarTripleProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c,
			OctonionFloat64Member d)
	{
		OctonionFloat64RModuleMember b_cross_c = new OctonionFloat64RModuleMember(new double[3*8]);
		crossProduct(b, c, b_cross_c);
		dotProduct(a, b_cross_c, d);
	}

	@Override
	public void conjugate(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long i = 0; i < a.length(); i++) {
			a.v(i, tmp);
			odbl.conjugate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

}
