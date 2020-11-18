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
package nom.bdezonia.zorbage.type.quaternion.float16;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2b;
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
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat16RModule
	implements
		RModule<QuaternionFloat16RModule,QuaternionFloat16RModuleMember,QuaternionFloat16Algebra,QuaternionFloat16Member>,
		Constructible1dLong<QuaternionFloat16RModuleMember>,
		Norm<QuaternionFloat16RModuleMember,Float16Member>,
		Products<QuaternionFloat16RModuleMember,QuaternionFloat16Member, QuaternionFloat16MatrixMember>,
		DirectProduct<QuaternionFloat16RModuleMember, QuaternionFloat16MatrixMember>,
		Rounding<Float16Member,QuaternionFloat16RModuleMember>, Infinite<QuaternionFloat16RModuleMember>,
		NaN<QuaternionFloat16RModuleMember>,
		ScaleByHighPrec<QuaternionFloat16RModuleMember>,
		ScaleByRational<QuaternionFloat16RModuleMember>,
		ScaleByDouble<QuaternionFloat16RModuleMember>,
		ScaleByOneHalf<QuaternionFloat16RModuleMember>,
		ScaleByTwo<QuaternionFloat16RModuleMember>,
		Tolerance<Float16Member,QuaternionFloat16RModuleMember>,
		ArrayLikeMethods<QuaternionFloat16RModuleMember,QuaternionFloat16Member>
{
	public QuaternionFloat16RModule() { }
	
	private final Procedure1<QuaternionFloat16RModuleMember> ZER = 
			new Procedure1<QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16RModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> NEG =
			new Procedure2<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			RModuleNegate.compute(G.QHLF, a, b);
		}
	};
	

	@Override
	public Procedure2<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> ADD =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b,
				QuaternionFloat16RModuleMember c)
		{
			RModuleAdd.compute(G.QHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> SUB =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b,
				QuaternionFloat16RModuleMember c)
		{
			RModuleSubtract.compute(G.QHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> EQ =
			new Function2<Boolean, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			return RModuleEqual.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> NEQ =
			new Function2<Boolean, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public QuaternionFloat16RModuleMember construct() {
		return new QuaternionFloat16RModuleMember();
	}

	@Override
	public QuaternionFloat16RModuleMember construct(QuaternionFloat16RModuleMember other) {
		return new QuaternionFloat16RModuleMember(other);
	}

	@Override
	public QuaternionFloat16RModuleMember construct(String s) {
		return new QuaternionFloat16RModuleMember(s);
	}

	@Override
	public QuaternionFloat16RModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionFloat16RModuleMember(s, d1);
	}

	private final Procedure2<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> ASSIGN = 
			new Procedure2<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember from, QuaternionFloat16RModuleMember to) {
			RModuleAssign.compute(G.QHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat16RModuleMember,Float16Member> NORM =
			new Procedure2<QuaternionFloat16RModuleMember, Float16Member>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, Float16Member b) {
			RModuleDefaultNorm.compute(G.QHLF, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16RModuleMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> SCALE =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			RModuleScale.compute(G.QHLF, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> CROSS =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b,
				QuaternionFloat16RModuleMember c)
		{
			CrossProduct.compute(G.QHLF_RMOD, G.QHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16Member> DOT =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b, QuaternionFloat16Member c) {
			DotProduct.compute(G.QHLF_RMOD, G.QHLF, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16Member> PERP =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b, QuaternionFloat16Member c) {
			PerpDotProduct.compute(G.QHLF_RMOD, G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> VTRIPLE =
			new Procedure4<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b,
				QuaternionFloat16RModuleMember c, QuaternionFloat16RModuleMember d)
		{
			QuaternionFloat16RModuleMember b_cross_c = new QuaternionFloat16RModuleMember(new float[3*4]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16Member> STRIPLE =
			new Procedure4<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b,
				QuaternionFloat16RModuleMember c, QuaternionFloat16Member d)
		{
			QuaternionFloat16RModuleMember b_cross_c = new QuaternionFloat16RModuleMember(new float[3*4]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> CONJ =
			new Procedure2<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			RModuleConjugate.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16MatrixMember> VDP =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember in1, QuaternionFloat16RModuleMember in2,
				QuaternionFloat16MatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16MatrixMember> DP =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember in1, QuaternionFloat16RModuleMember in2,
				QuaternionFloat16MatrixMember out)
		{
			RModuleDirectProduct.compute(G.QHLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16RModuleMember,QuaternionFloat16RModuleMember,QuaternionFloat16MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, QuaternionFloat16RModuleMember> ISNAN =
			new Function1<Boolean, QuaternionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16RModuleMember a) {
			return SequenceIsNan.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat16RModuleMember> NAN =
			new Procedure1<QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a) {
			FillNaN.compute(G.QHLF, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat16RModuleMember> ISINF =
			new Function1<Boolean, QuaternionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16RModuleMember a) {
			return SequenceIsInf.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat16RModuleMember> INF =
			new Procedure1<QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a) {
			FillInfinite.compute(G.QHLF, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat16RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> ROUND =
			new Procedure4<Mode, Float16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			RModuleRound.compute(G.QHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat16RModuleMember> ISZERO =
			new Function1<Boolean, QuaternionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16RModuleMember a) {
			return SequenceIsZero.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16RModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat16RModuleMember b, QuaternionFloat16RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat16RModuleMember b, QuaternionFloat16RModuleMember c) {
			RModuleScaleByRational.compute(G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> SBD =
			new Procedure3<Double, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat16RModuleMember b, QuaternionFloat16RModuleMember c) {
			RModuleScaleByDouble.compute(G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> WITHIN =
			new Function3<Boolean, Float16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(Float16Member tol, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			return SequencesSimilar.compute(G.QHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> ADDS =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			FixedTransform2b.compute(G.QHLF, scalar, G.QHLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> SUBS =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			FixedTransform2b.compute(G.QHLF, scalar, G.QHLF.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> MULS =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			FixedTransform2b.compute(G.QHLF, scalar, G.QHLF.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> DIVS =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			FixedTransform2b.compute(G.QHLF, scalar, G.QHLF.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> MULTELEM =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b, QuaternionFloat16RModuleMember c) {
			Transform3.compute(G.QHLF, G.QHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> DIVELEM =
			new Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b, QuaternionFloat16RModuleMember c) {
			Transform3.compute(G.QHLF, G.QHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			ScaleHelper.compute(G.QHLF_RMOD, G.QHLF, new QuaternionFloat16Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> SCBH =
			new Procedure3<Integer, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat16RModuleMember a, QuaternionFloat16RModuleMember b) {
			ScaleHelper.compute(G.QHLF_RMOD, G.QHLF, new QuaternionFloat16Member(0.5f, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat16RModuleMember, QuaternionFloat16RModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
