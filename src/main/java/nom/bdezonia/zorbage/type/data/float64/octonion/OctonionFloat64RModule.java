/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.octonion;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDefaultNorm;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByDouble;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByRational;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
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
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64RModule
  implements
    RModule<OctonionFloat64RModule,OctonionFloat64RModuleMember,OctonionFloat64Algebra,OctonionFloat64Member>,
    Constructible1dLong<OctonionFloat64RModuleMember>,
	Norm<OctonionFloat64RModuleMember,Float64Member>,
	Products<OctonionFloat64RModuleMember, OctonionFloat64Member, OctonionFloat64MatrixMember>,
	DirectProduct<OctonionFloat64RModuleMember, OctonionFloat64MatrixMember>,
	Rounding<Float64Member,OctonionFloat64RModuleMember>, Infinite<OctonionFloat64RModuleMember>,
	NaN<OctonionFloat64RModuleMember>,
	ScaleByHighPrec<OctonionFloat64RModuleMember>,
	ScaleByRational<OctonionFloat64RModuleMember>,
	ScaleByDouble<OctonionFloat64RModuleMember>,
	Tolerance<Float64Member,OctonionFloat64RModuleMember>
{
	public OctonionFloat64RModule() { }
	
	private final Procedure1<OctonionFloat64RModuleMember> ZER =
			new Procedure1<OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64RModuleMember> zero() {
		return ZER;
	}

	private Procedure2<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> NEG =
			new Procedure2<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			RModuleNegate.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> negate() {
		return NEG;
	}
	
	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> ADD =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
			RModuleAdd.compute(G.ODBL, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> SUB =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
			RModuleSubtract.compute(G.ODBL, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> EQ =
			new Function2<Boolean, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			return (Boolean) RModuleEqual.compute(G.ODBL, a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> NEQ =
			new Function2<Boolean, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			return !isEqual().call(a,b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> isNotEqual() {
		return NEQ;
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
	public OctonionFloat64RModuleMember construct(StorageConstruction s, long d1) {
		return new OctonionFloat64RModuleMember(s, d1);
	}

	private final Procedure2<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> ASSIGN =
			new Procedure2<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember from, OctonionFloat64RModuleMember to) {
			RModuleAssign.compute(G.ODBL, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat64RModuleMember,Float64Member> NORM =
			new Procedure2<OctonionFloat64RModuleMember, Float64Member>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, Float64Member b) {
			RModuleDefaultNorm.compute(G.ODBL, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64RModuleMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> SCALE =
			new Procedure3<OctonionFloat64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			RModuleScale.compute(G.ODBL, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> CROSS =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
			CrossProduct.compute(G.ODBL_RMOD, G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64Member> DOT =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64Member c) {
			DotProduct.compute(G.ODBL_RMOD, G.ODBL, G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64Member> PERP =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64Member c) {
			PerpDotProduct.compute(G.ODBL_RMOD, G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> VTRIPLE =
			new Procedure4<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c,
				OctonionFloat64RModuleMember d)
		{
			OctonionFloat64RModuleMember b_cross_c = new OctonionFloat64RModuleMember(new double[3*8]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}
	
	private final Procedure4<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64Member> STRIPLE =
			new Procedure4<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c,
				OctonionFloat64Member d)
		{
			OctonionFloat64RModuleMember b_cross_c = new OctonionFloat64RModuleMember(new double[3*8]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};

	@Override
	public Procedure4<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> CONJ =
			new Procedure2<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			RModuleConjugate.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64MatrixMember> VDP =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64MatrixMember c) {
			directProduct().call(a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64MatrixMember> DP =
			new Procedure3<OctonionFloat64RModuleMember, OctonionFloat64RModuleMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember in1, OctonionFloat64RModuleMember in2, OctonionFloat64MatrixMember out) {
			RModuleDirectProduct.compute(G.ODBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64RModuleMember,OctonionFloat64RModuleMember,OctonionFloat64MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, OctonionFloat64RModuleMember> ISNAN =
			new Function1<Boolean, OctonionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat64RModuleMember a) {
			return SequenceIsNan.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat64RModuleMember> NAN =
			new Procedure1<OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a) {
			FillNaN.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat64RModuleMember> ISINF =
			new Function1<Boolean, OctonionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat64RModuleMember a) {
			return SequenceIsInf.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat64RModuleMember> INF =
			new Procedure1<OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(OctonionFloat64RModuleMember a) {
			FillInfinite.compute(G.ODBL, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> ROUND =
			new Procedure4<Mode, Float64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			RModuleRound.compute(G.ODBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat64RModuleMember> ISZERO =
			new Function1<Boolean, OctonionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat64RModuleMember a) {
			return SequenceIsZero.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64RModuleMember> isZero() {
		return ISZERO;
	}

	private Procedure3<HighPrecisionMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private Procedure3<RationalMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> SBR =
			new Procedure3<RationalMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
			RModuleScaleByRational.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> scaleByRational() {
		return SBR;
	}

	private Procedure3<Double, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> SBD =
			new Procedure3<Double, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public void call(Double a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
			RModuleScaleByDouble.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> WITHIN =
			new Function3<Boolean, Float64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(Float64Member tol, OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
			return SequencesSimilar.compute(G.ODBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, OctonionFloat64RModuleMember, OctonionFloat64RModuleMember> within() {
		return WITHIN;
	}

}
