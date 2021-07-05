/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.octonion.float32;

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
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

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
		NaN<OctonionFloat32RModuleMember>,
		ScaleByHighPrec<OctonionFloat32RModuleMember>,
		ScaleByRational<OctonionFloat32RModuleMember>,
		ScaleByDouble<OctonionFloat32RModuleMember>,
		ScaleByOneHalf<OctonionFloat32RModuleMember>,
		ScaleByTwo<OctonionFloat32RModuleMember>,
		Tolerance<Float32Member,OctonionFloat32RModuleMember>,
		ArrayLikeMethods<OctonionFloat32RModuleMember,OctonionFloat32Member>,
		ConstructibleFromFloat<OctonionFloat32RModuleMember>
{
	public OctonionFloat32RModule() { }

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

	@Override
	public OctonionFloat32RModuleMember construct(float... vals) {
		return new OctonionFloat32RModuleMember(vals);
	}
	
	private final Procedure1<OctonionFloat32RModuleMember> ZER =
			new Procedure1<OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32RModuleMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> NEG =
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
			return RModuleEqual.compute(G.OFLT, a, b);
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
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionFloat32RModuleMember,OctonionFloat32RModuleMember> isNotEqual() {
		return NEQ;
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
			RModuleDefaultNorm.compute(G.OFLT, G.FLT, a, b);
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
			DotProduct.compute(G.OFLT_RMOD, G.OFLT, G.FLT, a, b, c);
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
			FillNaN.compute(G.OFLT, a);
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
			FillInfinite.compute(G.OFLT, a);
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
			return SequenceIsZero.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32RModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> SBR =
			new Procedure3<RationalMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			RModuleScaleByRational.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> SBD =
			new Procedure3<Double, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(Double a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			RModuleScaleByDouble.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> WITHIN =
			new Function3<Boolean, Float32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public Boolean call(Float32Member tol, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			return SequencesSimilar.compute(G.OFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> ADDS =
			new Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			b.alloc(a.length());
			FixedTransform2b.compute(G.OFLT, scalar, G.OFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> SUBS =
			new Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			b.alloc(a.length());
			FixedTransform2b.compute(G.OFLT, scalar, G.OFLT.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> MULS =
			new Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			b.alloc(a.length());
			FixedTransform2b.compute(G.OFLT, scalar, G.OFLT.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> DIVS =
			new Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			b.alloc(a.length());
			FixedTransform2b.compute(G.OFLT, scalar, G.OFLT.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> MULTELEM =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OFLT, G.OFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> DIVELEM =
			new Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b, OctonionFloat32RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OFLT, G.OFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32RModuleMember, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> SCB2 =
			new Procedure3<Integer, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			ScaleHelper.compute(G.OFLT_RMOD, G.OFLT, new OctonionFloat32Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> SCBH =
			new Procedure3<Integer, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat32RModuleMember a, OctonionFloat32RModuleMember b) {
			ScaleHelper.compute(G.OFLT_RMOD, G.OFLT, new OctonionFloat32Member(0.5f, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat32RModuleMember, OctonionFloat32RModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
