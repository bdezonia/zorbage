/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float16.octonion;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleInfinite;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNaN;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Products;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat16RModule
  implements
    RModule<OctonionFloat16RModule,OctonionFloat16RModuleMember,OctonionFloat16Algebra,OctonionFloat16Member>,
    Constructible1dLong<OctonionFloat16RModuleMember>,
	Norm<OctonionFloat16RModuleMember,Float16Member>,
	Products<OctonionFloat16RModuleMember, OctonionFloat16Member, OctonionFloat16MatrixMember>,
	DirectProduct<OctonionFloat16RModuleMember, OctonionFloat16MatrixMember>,
	Rounding<Float16Member,OctonionFloat16RModuleMember>, Infinite<OctonionFloat16RModuleMember>,
	NaN<OctonionFloat16RModuleMember>
{
	public OctonionFloat16RModule() { }
	
	private final Procedure1<OctonionFloat16RModuleMember> ZER =
			new Procedure1<OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a) {
			RModuleZero.compute(G.OHLF, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16RModuleMember> zero() {
		return ZER;
	}

	private Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> NEG =
			new Procedure2<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			RModuleNegate.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> negate() {
		return NEG;
	}
	
	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> ADD =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			RModuleAdd.compute(G.OHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> SUB =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			RModuleSubtract.compute(G.OHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> EQ =
			new Function2<Boolean, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			return (Boolean) RModuleEqual.compute(G.OHLF, a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> NEQ =
			new Function2<Boolean, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			return !isEqual().call(a,b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public OctonionFloat16RModuleMember construct() {
		return new OctonionFloat16RModuleMember();
	}

	@Override
	public OctonionFloat16RModuleMember construct(OctonionFloat16RModuleMember other) {
		return new OctonionFloat16RModuleMember(other);
	}

	@Override
	public OctonionFloat16RModuleMember construct(String s) {
		return new OctonionFloat16RModuleMember(s);
	}

	@Override
	public OctonionFloat16RModuleMember construct(StorageConstruction s, long d1) {
		return new OctonionFloat16RModuleMember(s, d1);
	}

	private final Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> ASSIGN =
			new Procedure2<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember from, OctonionFloat16RModuleMember to) {
			RModuleAssign.compute(G.OHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat16RModuleMember,Float16Member> NORM =
			new Procedure2<OctonionFloat16RModuleMember, Float16Member>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, Float16Member b) {
			OctonionFloat16Member aTmp = new OctonionFloat16Member();
			OctonionFloat16Member sum = new OctonionFloat16Member();
			OctonionFloat16Member tmp = new OctonionFloat16Member();
			// TODO Look into preventing overflow. can do so similar to float
			// case using norms. Also could avoid a lot of adds and multiplies
			// by just working with components of the oct as reals since
			// that is all that survives. This can be extended to quats and
			// complexes. maybe not, think it through. But the conjugate()
			// and multiply() can be simplified.
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.OHLF.conjugate().call(aTmp, tmp);
				G.OHLF.multiply().call(aTmp, tmp, tmp);
				G.OHLF.add().call(sum, tmp, sum);
			}
			b.setV(Math.sqrt(sum.r()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16RModuleMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> SCALE =
			new Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			RModuleScale.compute(G.OHLF, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> CROSS =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			CrossProduct.compute(G.OHLF_RMOD, G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16Member> DOT =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16Member c) {
			DotProduct.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16Member> PERP =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16Member c) {
			PerpDotProduct.compute(G.OHLF_RMOD, G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> VTRIPLE =
			new Procedure4<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c,
				OctonionFloat16RModuleMember d)
		{
			OctonionFloat16RModuleMember b_cross_c = new OctonionFloat16RModuleMember(new double[3*8]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}
	
	private final Procedure4<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16Member> STRIPLE =
			new Procedure4<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c,
				OctonionFloat16Member d)
		{
			OctonionFloat16RModuleMember b_cross_c = new OctonionFloat16RModuleMember(new double[3*8]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};

	@Override
	public Procedure4<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> CONJ =
			new Procedure2<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			RModuleConjugate.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16MatrixMember> VDP =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16MatrixMember c) {
			directProduct().call(a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16MatrixMember> DP =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember in1, OctonionFloat16RModuleMember in2, OctonionFloat16MatrixMember out) {
			RModuleDirectProduct.compute(G.OHLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember,OctonionFloat16MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, OctonionFloat16RModuleMember> ISNAN =
			new Function1<Boolean, OctonionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat16RModuleMember a) {
			return SequenceIsNan.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat16RModuleMember> NAN =
			new Procedure1<OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a) {
			RModuleNaN.compute(G.OHLF, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat16RModuleMember> ISINF =
			new Function1<Boolean, OctonionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat16RModuleMember a) {
			return SequenceIsInf.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat16RModuleMember> INF =
			new Procedure1<OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a) {
			RModuleInfinite.compute(G.OHLF, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat16RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> ROUND =
			new Procedure4<Mode, Float16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			RModuleRound.compute(G.OHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat16RModuleMember> ISZERO =
			new Function1<Boolean, OctonionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat16RModuleMember a) {
			return SequenceIsZero.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16RModuleMember> isZero() {
		return ISZERO;
	}

}
