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
package nom.bdezonia.zorbage.type.octonion.float16;

import java.lang.Integer;

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
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

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
		NaN<OctonionFloat16RModuleMember>,
		ScaleByHighPrec<OctonionFloat16RModuleMember>,
		ScaleByRational<OctonionFloat16RModuleMember>,
		ScaleByDouble<OctonionFloat16RModuleMember>,
		ScaleByOneHalf<OctonionFloat16RModuleMember>,
		ScaleByTwo<OctonionFloat16RModuleMember>,
		Tolerance<Float16Member,OctonionFloat16RModuleMember>,
		ArrayLikeMethods<OctonionFloat16RModuleMember,OctonionFloat16Member>,
		ConstructibleFromFloats<OctonionFloat16RModuleMember>
{
	@Override
	public String typeDescription() {
		return "16-bit based octonion rmodule";
	}

	public OctonionFloat16RModule() { }

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

	@Override
	public OctonionFloat16RModuleMember construct(float... vals) {
		return new OctonionFloat16RModuleMember(vals);
	}

	private final Procedure1<OctonionFloat16RModuleMember> ZER =
			new Procedure1<OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16RModuleMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> NEG =
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
			return RModuleEqual.compute(G.OHLF, a, b);
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
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat16RModuleMember,OctonionFloat16RModuleMember> isNotEqual() {
		return NEQ;
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
			RModuleDefaultNorm.compute(G.OHLF, G.HLF, a, b);
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
			DotProduct.compute(G.OHLF_RMOD, G.OHLF, G.HLF, a, b, c);
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
			OctonionFloat16RModuleMember b_cross_c = new OctonionFloat16RModuleMember(new float[3*8]);
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
			OctonionFloat16RModuleMember b_cross_c = new OctonionFloat16RModuleMember(new float[3*8]);
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
			FillNaN.compute(G.OHLF, a.rawData());
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
			FillInfinite.compute(G.OHLF, a.rawData());
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

	private final Procedure3<HighPrecisionMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> SBR =
			new Procedure3<RationalMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			RModuleScaleByRational.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> SBD =
			new Procedure3<Double, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(Double a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			RModuleScaleByDouble.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> WITHIN =
			new Function3<Boolean, Float16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public Boolean call(Float16Member tol, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			return SequencesSimilar.compute(G.OHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> ADDS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHLF, G.OHLF.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> SUBS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHLF, G.OHLF.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> MULS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHLF, G.OHLF.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> DIVS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHLF, G.OHLF.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> MULTELEM =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OHLF, G.OHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> DIVELEM =
			new Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b, OctonionFloat16RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OHLF, G.OHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16RModuleMember, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> SCB2 =
			new Procedure3<Integer, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			ScaleHelper.compute(G.OHLF_RMOD, G.OHLF, new OctonionFloat16Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> SCBH =
			new Procedure3<Integer, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat16RModuleMember a, OctonionFloat16RModuleMember b) {
			ScaleHelper.compute(G.OHLF_RMOD, G.OHLF, new OctonionFloat16Member(0.5f, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat16RModuleMember, OctonionFloat16RModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
