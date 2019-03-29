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
package nom.bdezonia.zorbage.type.data.float32.octonion;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleInfinite;
import nom.bdezonia.zorbage.algorithm.RModuleIsEqual;
import nom.bdezonia.zorbage.algorithm.RModuleIsZero;
import nom.bdezonia.zorbage.algorithm.RModuleNaN;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
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
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat32RModule
  implements
    RModule<OctonionFloat32RModule,OctonionFloat32RModuleMember,OctonionFloat32Algebra,OctonionFloat32Member>,
    Constructible1dLong<OctonionFloat32RModuleMember>,
	Norm<OctonionFloat32RModuleMember,Float32Member>,
	Products<OctonionFloat32RModuleMember, OctonionFloat32Member, OctonionFloat32MatrixMember>,
	DirectProduct<OctonionFloat32RModuleMember, OctonionFloat32MatrixMember>,
	Rounding<Float32Member,OctonionFloat32RModuleMember>, Infinite<OctonionFloat32RModuleMember>,
	NaN<OctonionFloat32RModuleMember>
{
	private static final OctonionFloat32Member ZERO = new OctonionFloat32Member();
	
	public OctonionFloat32RModule() { }
	
	private final Procedure1<OctonionFloat32RModuleMember> ZER =
			new Procedure1<OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a) {
			for (long i = 0; i < a.length(); i++)
				a.setV(i, ZERO);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32RModuleMember> zero() {
		return ZER;
	}

	private Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> NEG =
			new Procedure2<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			RModuleNegate.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> negate() {
		return NEG;
	}
	
	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> ADD =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			RModuleAdd.compute(G.OFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> SUB =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			RModuleSubtract.compute(G.OFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> EQ =
			new Function2<Boolean, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			return RModuleIsEqual.compute(G.OFLT, a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> NEQ =
			new Function2<Boolean, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			return !isEqual().call(a,b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public OctonionFloat32RModuleMember construct() {
		return new OctonionFloat32RModuleMember();
	}

	@Override
	public OctonionFloat32RModuleMember construct(OctonionFloat32RModuleMember other) {
		return new OctonionFloat32RModuleMember(other);
	}

	@Override
	public OctonionFloat32RModuleMember construct(String s) {
		return new OctonionFloat32RModuleMember(s);
	}

	@Override
	public OctonionFloat32RModuleMember construct(StorageConstruction s, long d1) {
		return new OctonionFloat32RModuleMember(s, d1);
	}

	private final Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> ASSIGN =
			new Procedure2<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember from, OctonionFloat32RModuleMember to) {
			RModuleAssign.compute(G.OFLT, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat32RModuleMember,Float32Member> NORM =
			new Procedure2<OctonionFloat32RModuleMember, Float32Member>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, Float32Member b) {
			OctonionFloat32Member aTmp = new OctonionFloat32Member();
			OctonionFloat32Member sum = new OctonionFloat32Member();
			OctonionFloat32Member tmp = new OctonionFloat32Member();
			// TODO Look into preventing overflow. can do so similar to float
			// case using norms. Also could avoid a lot of adds and multiplies
			// by just working with components of the oct as reals since
			// that is all that survives. This can be extended to quats and
			// complexes. maybe not, think it through. But the conjugate()
			// and multiply() can be simplified.
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.OFLT.conjugate().call(aTmp, tmp);
				G.OFLT.multiply().call(aTmp, tmp, tmp);
				G.OFLT.add().call(sum, tmp, sum);
			}
			b.setV((float)Math.sqrt(sum.r()));
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32RModuleMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> SCALE =
			new Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			RModuleScale.compute(G.OFLT, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> CROSS =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			CrossProduct.compute(G.OFLT_RMOD, G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32Member> DOT =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32Member c) {
			DotProduct.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32Member> PERP =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32Member c) {
			PerpDotProduct.compute(G.OFLT_RMOD, G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> VTRIPLE =
			new Procedure4<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c,
				OctonionFloat32RModuleMember d)
		{
			OctonionFloat32RModuleMember b_cross_c = new OctonionFloat32RModuleMember(new float[3*8]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}
	
	private final Procedure4<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32Member> STRIPLE =
			new Procedure4<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c,
				OctonionFloat32Member d)
		{
			OctonionFloat32RModuleMember b_cross_c = new OctonionFloat32RModuleMember(new float[3*8]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};

	@Override
	public Procedure4<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> CONJ =
			new Procedure2<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			RModuleConjugate.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32MatrixMember> VDP =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32MatrixMember c) {
			directProduct().call(a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32MatrixMember> DP =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember in1, OctonionFloat32RModuleMember in2, OctonionFloat32MatrixMember out) {
			RModuleDirectProduct.compute(G.OFLT, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember,OctonionFloat32MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, OctonionFloat32RModuleMember> ISNAN =
			new Function1<Boolean, OctonionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat32RModuleMember a) {
			return SequenceIsNan.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat32RModuleMember> NAN =
			new Procedure1<OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a) {
			RModuleNaN.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat32RModuleMember> ISINF =
			new Function1<Boolean, OctonionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat32RModuleMember a) {
			return SequenceIsInf.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat32RModuleMember> INF =
			new Procedure1<OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a) {
			RModuleInfinite.compute(G.OFLT, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat32RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> ROUND =
			new Procedure4<Mode, Float32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			RModuleRound.compute(G.OFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat32RModuleMember> ISZERO =
			new Function1<Boolean, OctonionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat32RModuleMember a) {
			return RModuleIsZero.compute(G.OFLT, a);
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32RModuleMember> isZero() {
		return ISZERO;
	}

}
