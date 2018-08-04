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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleIsEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Products;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64RModule
  implements
    RModule<QuaternionFloat64RModule,QuaternionFloat64RModuleMember,QuaternionFloat64Group,QuaternionFloat64Member>,
    Constructible1dLong<QuaternionFloat64RModuleMember>,
    Norm<QuaternionFloat64RModuleMember,Float64Member>,
    Products<QuaternionFloat64RModuleMember,QuaternionFloat64Member, QuaternionFloat64MatrixMember>,
    DirectProduct<QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>
//TODO Round, Nan, Inf
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member();
	
	public QuaternionFloat64RModule() { }
	
	private final Procedure1<QuaternionFloat64RModuleMember> ZER = 
			new Procedure1<QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a) {
			for (long i = 0; i < a.length(); i++)
				a.setV(i, ZERO);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64RModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> NEG =
			new Procedure2<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleNegate.compute(G.QDBL, a, b);
		}
	};
	

	@Override
	public Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> ADD =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c)
		{
			RModuleAdd.compute(G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> SUB =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c)
		{
			RModuleSubtract.compute(G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> subtract() {
		return SUB;
	}

	private Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> EQ =
			new Function2<Boolean, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			return RModuleIsEqual.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> NEQ =
			new Function2<Boolean, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			return !isEqual().call(a,b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public QuaternionFloat64RModuleMember construct() {
		return new QuaternionFloat64RModuleMember();
	}

	@Override
	public QuaternionFloat64RModuleMember construct(QuaternionFloat64RModuleMember other) {
		return new QuaternionFloat64RModuleMember(other);
	}

	@Override
	public QuaternionFloat64RModuleMember construct(String s) {
		return new QuaternionFloat64RModuleMember(s);
	}

	@Override
	public QuaternionFloat64RModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionFloat64RModuleMember(s, d1);
	}

	private final Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> ASSIGN = 
			new Procedure2<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember from, QuaternionFloat64RModuleMember to) {
			RModuleAssign.compute(G.QDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat64RModuleMember,Float64Member> NORM =
			new Procedure2<QuaternionFloat64RModuleMember, Float64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, Float64Member b) {
			QuaternionFloat64Member aTmp = new QuaternionFloat64Member();
			QuaternionFloat64Member sum = new QuaternionFloat64Member();
			QuaternionFloat64Member tmp = new QuaternionFloat64Member();
			// TODO Look into preventing overflow. can do so similar to float case using norms
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.QDBL.conjugate().call(aTmp, tmp);
				G.QDBL.multiply().call(aTmp, tmp, tmp);
				G.QDBL.add().call(sum, tmp, sum);
			}
			b.setV(Math.sqrt(sum.r()));
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64RModuleMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> SCALE =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleScale.compute(G.QDBL, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> CROSS =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c)
		{
			CrossProduct.compute(G.QDBL_RMOD, G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> DOT =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
			DotProduct.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> PERP =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
			PerpDotProduct.compute(G.QDBL_RMOD, G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> VTRIPLE =
			new Procedure4<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c, QuaternionFloat64RModuleMember d)
		{
			QuaternionFloat64RModuleMember b_cross_c = new QuaternionFloat64RModuleMember(new double[3*4]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> STRIPLE =
			new Procedure4<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c, QuaternionFloat64Member d)
		{
			QuaternionFloat64RModuleMember b_cross_c = new QuaternionFloat64RModuleMember(new double[3*4]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> CONJ =
			new Procedure2<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleConjugate.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> VDP =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember in1, QuaternionFloat64RModuleMember in2,
				QuaternionFloat64MatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> DP =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember in1, QuaternionFloat64RModuleMember in2,
				QuaternionFloat64MatrixMember out)
		{
			RModuleDirectProduct.compute(G.QDBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> directProduct()
	{
		return DP;
	}

}
