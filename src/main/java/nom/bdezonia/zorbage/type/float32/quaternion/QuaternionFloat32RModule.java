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
package nom.bdezonia.zorbage.type.float32.quaternion;

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
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat32RModule
  implements
	RModule<QuaternionFloat32RModule,QuaternionFloat32RModuleMember,QuaternionFloat32Algebra,QuaternionFloat32Member>,
	Constructible1dLong<QuaternionFloat32RModuleMember>,
	Norm<QuaternionFloat32RModuleMember,Float32Member>,
	Products<QuaternionFloat32RModuleMember,QuaternionFloat32Member, QuaternionFloat32MatrixMember>,
	DirectProduct<QuaternionFloat32RModuleMember, QuaternionFloat32MatrixMember>,
	Rounding<Float32Member,QuaternionFloat32RModuleMember>, Infinite<QuaternionFloat32RModuleMember>,
	NaN<QuaternionFloat32RModuleMember>,
	ScaleByHighPrec<QuaternionFloat32RModuleMember>,
	ScaleByRational<QuaternionFloat32RModuleMember>,
	ScaleByDouble<QuaternionFloat32RModuleMember>,
	Tolerance<Float32Member,QuaternionFloat32RModuleMember>,
	ArrayLikeMethods<QuaternionFloat32RModuleMember, QuaternionFloat32Member>
{
	public QuaternionFloat32RModule() { }
	
	private final Procedure1<QuaternionFloat32RModuleMember> ZER = 
			new Procedure1<QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32RModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> NEG =
			new Procedure2<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			RModuleNegate.compute(G.QFLT, a, b);
		}
	};
	

	@Override
	public Procedure2<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> ADD =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b,
				QuaternionFloat32RModuleMember c)
		{
			RModuleAdd.compute(G.QFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> SUB =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b,
				QuaternionFloat32RModuleMember c)
		{
			RModuleSubtract.compute(G.QFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> EQ =
			new Function2<Boolean, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			return RModuleEqual.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> NEQ =
			new Function2<Boolean, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public QuaternionFloat32RModuleMember construct() {
		return new QuaternionFloat32RModuleMember();
	}

	@Override
	public QuaternionFloat32RModuleMember construct(QuaternionFloat32RModuleMember other) {
		return new QuaternionFloat32RModuleMember(other);
	}

	@Override
	public QuaternionFloat32RModuleMember construct(String s) {
		return new QuaternionFloat32RModuleMember(s);
	}

	@Override
	public QuaternionFloat32RModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionFloat32RModuleMember(s, d1);
	}

	private final Procedure2<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> ASSIGN = 
			new Procedure2<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember from, QuaternionFloat32RModuleMember to) {
			RModuleAssign.compute(G.QFLT, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat32RModuleMember,Float32Member> NORM =
			new Procedure2<QuaternionFloat32RModuleMember, Float32Member>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, Float32Member b) {
			RModuleDefaultNorm.compute(G.QFLT, G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32RModuleMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> SCALE =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			RModuleScale.compute(G.QFLT, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> CROSS =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b,
				QuaternionFloat32RModuleMember c)
		{
			CrossProduct.compute(G.QFLT_RMOD, G.QFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32Member> DOT =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b, QuaternionFloat32Member c) {
			DotProduct.compute(G.QFLT_RMOD, G.QFLT, G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32Member> PERP =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b, QuaternionFloat32Member c) {
			PerpDotProduct.compute(G.QFLT_RMOD, G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> VTRIPLE =
			new Procedure4<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b,
				QuaternionFloat32RModuleMember c, QuaternionFloat32RModuleMember d)
		{
			QuaternionFloat32RModuleMember b_cross_c = new QuaternionFloat32RModuleMember(new float[3*4]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32Member> STRIPLE =
			new Procedure4<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b,
				QuaternionFloat32RModuleMember c, QuaternionFloat32Member d)
		{
			QuaternionFloat32RModuleMember b_cross_c = new QuaternionFloat32RModuleMember(new float[3*4]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> CONJ =
			new Procedure2<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			RModuleConjugate.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32MatrixMember> VDP =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember in1, QuaternionFloat32RModuleMember in2,
				QuaternionFloat32MatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32MatrixMember> DP =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember in1, QuaternionFloat32RModuleMember in2,
				QuaternionFloat32MatrixMember out)
		{
			RModuleDirectProduct.compute(G.QFLT, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32RModuleMember,QuaternionFloat32RModuleMember,QuaternionFloat32MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, QuaternionFloat32RModuleMember> ISNAN =
			new Function1<Boolean, QuaternionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32RModuleMember a) {
			return SequenceIsNan.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat32RModuleMember> NAN =
			new Procedure1<QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a) {
			FillNaN.compute(G.QFLT, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat32RModuleMember> ISINF =
			new Function1<Boolean, QuaternionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32RModuleMember a) {
			return SequenceIsInf.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat32RModuleMember> INF =
			new Procedure1<QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a) {
			FillInfinite.compute(G.QFLT, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> ROUND =
			new Procedure4<Mode, Float32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			RModuleRound.compute(G.QFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat32RModuleMember> ISZERO =
			new Function1<Boolean, QuaternionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32RModuleMember a) {
			return SequenceIsZero.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32RModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat32RModuleMember b, QuaternionFloat32RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat32RModuleMember b, QuaternionFloat32RModuleMember c) {
			RModuleScaleByRational.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> SBD =
			new Procedure3<Double, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat32RModuleMember b, QuaternionFloat32RModuleMember c) {
			RModuleScaleByDouble.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> WITHIN =
			new Function3<Boolean, Float32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(Float32Member tol, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			return SequencesSimilar.compute(G.QFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> ADDS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> SUBS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> MULS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> DIVS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> MULTELEM =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b, QuaternionFloat32RModuleMember c) {
			Transform3.compute(G.QFLT, G.QFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> DIVELEM =
			new Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat32RModuleMember a, QuaternionFloat32RModuleMember b, QuaternionFloat32RModuleMember c) {
			Transform3.compute(G.QFLT, G.QFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember, QuaternionFloat32RModuleMember> divideElements() {
		return DIVELEM;
	}
}
