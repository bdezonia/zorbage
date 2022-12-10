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
package nom.bdezonia.zorbage.type.quaternion.float128;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
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
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
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
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat128RModule
	implements
		RModule<QuaternionFloat128RModule,QuaternionFloat128RModuleMember,QuaternionFloat128Algebra,QuaternionFloat128Member>,
		Constructible1dLong<QuaternionFloat128RModuleMember>,
		Norm<QuaternionFloat128RModuleMember,Float128Member>,
		Products<QuaternionFloat128RModuleMember,QuaternionFloat128Member, QuaternionFloat128MatrixMember>,
		DirectProduct<QuaternionFloat128RModuleMember, QuaternionFloat128MatrixMember>,
		Rounding<Float128Member,QuaternionFloat128RModuleMember>, Infinite<QuaternionFloat128RModuleMember>,
		NaN<QuaternionFloat128RModuleMember>,
		ScaleByHighPrec<QuaternionFloat128RModuleMember>,
		ScaleByRational<QuaternionFloat128RModuleMember>,
		ScaleByDouble<QuaternionFloat128RModuleMember>,
		ScaleByOneHalf<QuaternionFloat128RModuleMember>,
		ScaleByTwo<QuaternionFloat128RModuleMember>,
		Tolerance<Float128Member,QuaternionFloat128RModuleMember>,
		ArrayLikeMethods<QuaternionFloat128RModuleMember,QuaternionFloat128Member>,
		ConstructibleFromLong<QuaternionFloat128RModuleMember>,
		ConstructibleFromDouble<QuaternionFloat128RModuleMember>,
		ConstructibleFromBigInteger<QuaternionFloat128RModuleMember>,
		ConstructibleFromBigDecimal<QuaternionFloat128RModuleMember>
{
	@Override
	public String typeDescription() {
		return "128-bit based quaternion rmodule";
	}

	public QuaternionFloat128RModule() { }

	@Override
	public QuaternionFloat128RModuleMember construct() {
		return new QuaternionFloat128RModuleMember();
	}

	@Override
	public QuaternionFloat128RModuleMember construct(QuaternionFloat128RModuleMember other) {
		return new QuaternionFloat128RModuleMember(other);
	}

	@Override
	public QuaternionFloat128RModuleMember construct(String s) {
		return new QuaternionFloat128RModuleMember(s);
	}

	@Override
	public QuaternionFloat128RModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionFloat128RModuleMember(s, d1);
	}

	@Override
	public QuaternionFloat128RModuleMember construct(long... vals) {
		return new QuaternionFloat128RModuleMember(vals);
	}

	@Override
	public QuaternionFloat128RModuleMember construct(double... vals) {
		return new QuaternionFloat128RModuleMember(vals);
	}

	@Override
	public QuaternionFloat128RModuleMember construct(BigInteger... vals) {
		return new QuaternionFloat128RModuleMember(vals);
	}

	@Override
	public QuaternionFloat128RModuleMember construct(BigDecimal... vals) {
		return new QuaternionFloat128RModuleMember(vals);
	}
	
	private final Procedure1<QuaternionFloat128RModuleMember> ZER = 
			new Procedure1<QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128RModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> NEG =
			new Procedure2<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			RModuleNegate.compute(G.QQUAD, a, b);
		}
	};
	

	@Override
	public Procedure2<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> ADD =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b,
				QuaternionFloat128RModuleMember c)
		{
			RModuleAdd.compute(G.QQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> SUB =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b,
				QuaternionFloat128RModuleMember c)
		{
			RModuleSubtract.compute(G.QQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> EQ =
			new Function2<Boolean, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			return RModuleEqual.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> NEQ =
			new Function2<Boolean, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> ASSIGN = 
			new Procedure2<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember from, QuaternionFloat128RModuleMember to) {
			RModuleAssign.compute(G.QQUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat128RModuleMember,Float128Member> NORM =
			new Procedure2<QuaternionFloat128RModuleMember, Float128Member>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, Float128Member b) {
			RModuleDefaultNorm.compute(G.QQUAD, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128RModuleMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> SCALE =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			RModuleScale.compute(G.QQUAD, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> CROSS =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b,
				QuaternionFloat128RModuleMember c)
		{
			CrossProduct.compute(G.QQUAD_RMOD, G.QQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128Member> DOT =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b, QuaternionFloat128Member c) {
			DotProduct.compute(G.QQUAD_RMOD, G.QQUAD, G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128Member> PERP =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b, QuaternionFloat128Member c) {
			PerpDotProduct.compute(G.QQUAD_RMOD, G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> VTRIPLE =
			new Procedure4<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b,
				QuaternionFloat128RModuleMember c, QuaternionFloat128RModuleMember d)
		{
			QuaternionFloat128RModuleMember b_cross_c = new QuaternionFloat128RModuleMember(new double[3*4]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128Member> STRIPLE =
			new Procedure4<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b,
				QuaternionFloat128RModuleMember c, QuaternionFloat128Member d)
		{
			QuaternionFloat128RModuleMember b_cross_c = new QuaternionFloat128RModuleMember(new double[3*4]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> CONJ =
			new Procedure2<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			RModuleConjugate.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128MatrixMember> VDP =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember in1, QuaternionFloat128RModuleMember in2,
				QuaternionFloat128MatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128MatrixMember> DP =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember in1, QuaternionFloat128RModuleMember in2,
				QuaternionFloat128MatrixMember out)
		{
			RModuleDirectProduct.compute(G.QQUAD, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128RModuleMember,QuaternionFloat128RModuleMember,QuaternionFloat128MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, QuaternionFloat128RModuleMember> ISNAN =
			new Function1<Boolean, QuaternionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128RModuleMember a) {
			return SequenceIsNan.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat128RModuleMember> NAN =
			new Procedure1<QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a) {
			FillNaN.compute(G.QQUAD, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat128RModuleMember> ISINF =
			new Function1<Boolean, QuaternionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128RModuleMember a) {
			return SequenceIsInf.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat128RModuleMember> INF =
			new Procedure1<QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a) {
			FillInfinite.compute(G.QQUAD, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat128RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> ROUND =
			new Procedure4<Mode, Float128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			RModuleRound.compute(G.QQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat128RModuleMember> ISZERO =
			new Function1<Boolean, QuaternionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128RModuleMember a) {
			return SequenceIsZero.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128RModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat128RModuleMember b, QuaternionFloat128RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat128RModuleMember b, QuaternionFloat128RModuleMember c) {
			RModuleScaleByRational.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> SBD =
			new Procedure3<Double, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat128RModuleMember b, QuaternionFloat128RModuleMember c) {
			RModuleScaleByDouble.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> WITHIN =
			new Function3<Boolean, Float128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(Float128Member tol, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			return SequencesSimilar.compute(G.QQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> ADDS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QQUAD, G.QQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> SUBS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QQUAD, G.QQUAD.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> MULS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QQUAD, G.QQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> DIVS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QQUAD, G.QQUAD.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> MULTELEM =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b, QuaternionFloat128RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QQUAD, G.QQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> DIVELEM =
			new Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b, QuaternionFloat128RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QQUAD, G.QQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			ScaleHelper.compute(G.QQUAD_RMOD, G.QQUAD, new QuaternionFloat128Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> SCBH =
			new Procedure3<Integer, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128RModuleMember a, QuaternionFloat128RModuleMember b) {
			ScaleHelper.compute(G.QQUAD_RMOD, G.QQUAD, new QuaternionFloat128Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128RModuleMember, QuaternionFloat128RModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
