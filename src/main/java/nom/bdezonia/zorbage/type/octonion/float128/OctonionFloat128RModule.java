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
package nom.bdezonia.zorbage.type.octonion.float128;

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
public class OctonionFloat128RModule
	implements
		RModule<OctonionFloat128RModule,OctonionFloat128RModuleMember,OctonionFloat128Algebra,OctonionFloat128Member>,
		Constructible1dLong<OctonionFloat128RModuleMember>,
		Norm<OctonionFloat128RModuleMember,Float128Member>,
		Products<OctonionFloat128RModuleMember, OctonionFloat128Member, OctonionFloat128MatrixMember>,
		DirectProduct<OctonionFloat128RModuleMember, OctonionFloat128MatrixMember>,
		Rounding<Float128Member,OctonionFloat128RModuleMember>, Infinite<OctonionFloat128RModuleMember>,
		NaN<OctonionFloat128RModuleMember>,
		ScaleByHighPrec<OctonionFloat128RModuleMember>,
		ScaleByRational<OctonionFloat128RModuleMember>,
		ScaleByDouble<OctonionFloat128RModuleMember>,
		ScaleByOneHalf<OctonionFloat128RModuleMember>,
		ScaleByTwo<OctonionFloat128RModuleMember>,
		Tolerance<Float128Member,OctonionFloat128RModuleMember>,
		ArrayLikeMethods<OctonionFloat128RModuleMember,OctonionFloat128Member>,
		ConstructibleFromBigDecimals<OctonionFloat128RModuleMember>,
		ConstructibleFromBigIntegers<OctonionFloat128RModuleMember>,
		ConstructibleFromDoubles<OctonionFloat128RModuleMember>,
		ConstructibleFromLongs<OctonionFloat128RModuleMember>
{

	@Override
	public String typeDescription() {
		return "128-bit based octonion rmodule";
	}

	public OctonionFloat128RModule() { }

	@Override
	public OctonionFloat128RModuleMember construct() {
		return new OctonionFloat128RModuleMember();
	}

	@Override
	public OctonionFloat128RModuleMember construct(OctonionFloat128RModuleMember other) {
		return new OctonionFloat128RModuleMember(other);
	}

	@Override
	public OctonionFloat128RModuleMember construct(String s) {
		return new OctonionFloat128RModuleMember(s);
	}

	@Override
	public OctonionFloat128RModuleMember construct(StorageConstruction s, long d1) {
		return new OctonionFloat128RModuleMember(s, d1);
	}

	@Override
	public OctonionFloat128RModuleMember construct(double... vals) {
		return new OctonionFloat128RModuleMember(vals);
	}

	@Override
	public OctonionFloat128RModuleMember construct(long... vals) {
		return new OctonionFloat128RModuleMember(vals);
	}

	@Override
	public OctonionFloat128RModuleMember construct(BigInteger... vals) {
		return new OctonionFloat128RModuleMember(vals);
	}

	@Override
	public OctonionFloat128RModuleMember construct(BigDecimal... vals) {
		return new OctonionFloat128RModuleMember(vals);
	}
	
	private final Procedure1<OctonionFloat128RModuleMember> ZER =
			new Procedure1<OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128RModuleMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> NEG =
			new Procedure2<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			RModuleNegate.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> negate() {
		return NEG;
	}
	
	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> ADD =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			RModuleAdd.compute(G.OQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> SUB =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			RModuleSubtract.compute(G.OQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> EQ =
			new Function2<Boolean, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			return RModuleEqual.compute(G.OQUAD, a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> NEQ =
			new Function2<Boolean, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> ASSIGN =
			new Procedure2<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember from, OctonionFloat128RModuleMember to) {
			RModuleAssign.compute(G.OQUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat128RModuleMember,Float128Member> NORM =
			new Procedure2<OctonionFloat128RModuleMember, Float128Member>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, Float128Member b) {
			RModuleDefaultNorm.compute(G.OQUAD, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128RModuleMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> SCALE =
			new Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			RModuleScale.compute(G.OQUAD, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> CROSS =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			CrossProduct.compute(G.OQUAD_RMOD, G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128Member> DOT =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128Member c) {
			DotProduct.compute(G.OQUAD_RMOD, G.OQUAD, G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128Member> PERP =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128Member c) {
			PerpDotProduct.compute(G.OQUAD_RMOD, G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> VTRIPLE =
			new Procedure4<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c,
				OctonionFloat128RModuleMember d)
		{
			OctonionFloat128RModuleMember b_cross_c = new OctonionFloat128RModuleMember(new double[3*8]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}
	
	private final Procedure4<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128Member> STRIPLE =
			new Procedure4<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c,
				OctonionFloat128Member d)
		{
			OctonionFloat128RModuleMember b_cross_c = new OctonionFloat128RModuleMember(new double[3*8]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};

	@Override
	public Procedure4<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> CONJ =
			new Procedure2<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			RModuleConjugate.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128MatrixMember> VDP =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128MatrixMember c) {
			directProduct().call(a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128MatrixMember> DP =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember in1, OctonionFloat128RModuleMember in2, OctonionFloat128MatrixMember out) {
			RModuleDirectProduct.compute(G.OQUAD, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128RModuleMember,OctonionFloat128RModuleMember,OctonionFloat128MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, OctonionFloat128RModuleMember> ISNAN =
			new Function1<Boolean, OctonionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat128RModuleMember a) {
			return SequenceIsNan.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat128RModuleMember> NAN =
			new Procedure1<OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a) {
			FillNaN.compute(G.OQUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat128RModuleMember> ISINF =
			new Function1<Boolean, OctonionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat128RModuleMember a) {
			return SequenceIsInf.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat128RModuleMember> INF =
			new Procedure1<OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a) {
			FillInfinite.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Procedure1<OctonionFloat128RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> ROUND =
			new Procedure4<Mode, Float128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			RModuleRound.compute(G.OQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat128RModuleMember> ISZERO =
			new Function1<Boolean, OctonionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(OctonionFloat128RModuleMember a) {
			return SequenceIsZero.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128RModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> SBR =
			new Procedure3<RationalMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			RModuleScaleByRational.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> SBD =
			new Procedure3<Double, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(Double a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			RModuleScaleByDouble.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> WITHIN =
			new Function3<Boolean, Float128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public Boolean call(Float128Member tol, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			return SequencesSimilar.compute(G.OQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> ADDS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OQUAD, G.OQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> SUBS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OQUAD, G.OQUAD.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> MULS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OQUAD, G.OQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> DIVS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OQUAD, G.OQUAD.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> MULTELEM =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OQUAD, G.OQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> DIVELEM =
			new Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b, OctonionFloat128RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OQUAD, G.OQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128RModuleMember, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> SCB2 =
			new Procedure3<Integer, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			ScaleHelper.compute(G.OQUAD_RMOD, G.OQUAD, new OctonionFloat128Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> SCBH =
			new Procedure3<Integer, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat128RModuleMember a, OctonionFloat128RModuleMember b) {
			ScaleHelper.compute(G.OQUAD_RMOD, G.OQUAD, new OctonionFloat128Member(0.5, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat128RModuleMember, OctonionFloat128RModuleMember> scaleByOneHalf() {
		return SCBH;
	}
}
