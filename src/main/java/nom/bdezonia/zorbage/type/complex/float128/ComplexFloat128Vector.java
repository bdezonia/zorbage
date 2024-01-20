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
package nom.bdezonia.zorbage.type.complex.float128;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.RModuleType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
public class ComplexFloat128Vector
	implements
		VectorSpace<ComplexFloat128Vector,ComplexFloat128VectorMember,ComplexFloat128Algebra,ComplexFloat128Member>,
		Constructible1dLong<ComplexFloat128VectorMember>,
		Norm<ComplexFloat128VectorMember,Float128Member>,
		Products<ComplexFloat128VectorMember, ComplexFloat128Member, ComplexFloat128MatrixMember>,
		DirectProduct<ComplexFloat128VectorMember, ComplexFloat128MatrixMember>,
		Rounding<Float128Member,ComplexFloat128VectorMember>, Infinite<ComplexFloat128VectorMember>,
		NaN<ComplexFloat128VectorMember>,
		ScaleByHighPrec<ComplexFloat128VectorMember>,
		ScaleByRational<ComplexFloat128VectorMember>,
		ScaleByDouble<ComplexFloat128VectorMember>,
		ScaleByOneHalf<ComplexFloat128VectorMember>,
		ScaleByTwo<ComplexFloat128VectorMember>,
		Tolerance<Float128Member,ComplexFloat128VectorMember>,
		ArrayLikeMethods<ComplexFloat128VectorMember,ComplexFloat128Member>,
		ConstructibleFromBytes<ComplexFloat128VectorMember>,
		ConstructibleFromShorts<ComplexFloat128VectorMember>,
		ConstructibleFromInts<ComplexFloat128VectorMember>,
		ConstructibleFromLongs<ComplexFloat128VectorMember>,
		ConstructibleFromFloats<ComplexFloat128VectorMember>,
		ConstructibleFromDoubles<ComplexFloat128VectorMember>,
		ConstructibleFromBigIntegers<ComplexFloat128VectorMember>,
		ConstructibleFromBigDecimals<ComplexFloat128VectorMember>,
		ExactlyConstructibleFromBytes<ComplexFloat128VectorMember>,
		ExactlyConstructibleFromShorts<ComplexFloat128VectorMember>,
		ExactlyConstructibleFromInts<ComplexFloat128VectorMember>,
		ExactlyConstructibleFromFloats<ComplexFloat128VectorMember>,
		ExactlyConstructibleFromDoubles<ComplexFloat128VectorMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "128-bit based complex vector";
	}
	
	public ComplexFloat128Vector() { }

	@Override
	public ComplexFloat128VectorMember construct() {
		return new ComplexFloat128VectorMember();
	}

	@Override
	public ComplexFloat128VectorMember construct(ComplexFloat128VectorMember other) {
		return new ComplexFloat128VectorMember(other);
	}

	@Override
	public ComplexFloat128VectorMember construct(String s) {
		return new ComplexFloat128VectorMember(s);
	}

	@Override
	public ComplexFloat128VectorMember construct(StorageConstruction s, long d1) {
		return new ComplexFloat128VectorMember(s, d1);
	}
	
	private final Procedure1<ComplexFloat128VectorMember> ZER =
			new Procedure1<ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat128VectorMember,ComplexFloat128VectorMember> NEG =
			new Procedure2<ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			RModuleNegate.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128VectorMember,ComplexFloat128VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> ADD =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			RModuleAdd.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> SUB =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			RModuleSubtract.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat128VectorMember,ComplexFloat128VectorMember> EQ =
			new Function2<Boolean, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			return RModuleEqual.compute(G.CQUAD, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat128VectorMember,ComplexFloat128VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat128VectorMember,ComplexFloat128VectorMember> NEQ =
			new Function2<Boolean, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat128VectorMember,ComplexFloat128VectorMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat128VectorMember,ComplexFloat128VectorMember> ASSIGN =
			new Procedure2<ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember from, ComplexFloat128VectorMember to) {
			RModuleAssign.compute(G.CQUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128VectorMember,ComplexFloat128VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat128VectorMember,Float128Member> NORM =
			new Procedure2<ComplexFloat128VectorMember, Float128Member>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, Float128Member b) {
			RModuleDefaultNorm.compute(G.CQUAD, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128VectorMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128VectorMember,ComplexFloat128VectorMember> SCALE =
			new Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			RModuleScale.compute(G.CQUAD, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128VectorMember,ComplexFloat128VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> CROSS =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			CrossProduct.compute(G.CQUAD_VEC, G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128Member> DOT =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128Member c) {
			DotProduct.compute(G.CQUAD_VEC, G.CQUAD, G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128Member> PERP =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128Member c) {
			PerpDotProduct.compute(G.CQUAD_VEC, G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> VTRIPLE =
			new Procedure4<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c,
				ComplexFloat128VectorMember d)
		{
			ComplexFloat128VectorMember b_cross_c = new ComplexFloat128VectorMember(3);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128Member> STRIPLE =
			new Procedure4<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c,
				ComplexFloat128Member d)
		{
			ComplexFloat128VectorMember b_cross_c = new ComplexFloat128VectorMember(3);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexFloat128VectorMember,ComplexFloat128VectorMember> CONJ =
			new Procedure2<ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			RModuleConjugate.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128VectorMember, ComplexFloat128VectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128MatrixMember> VDP =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember in1, ComplexFloat128VectorMember in2, ComplexFloat128MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128MatrixMember> DP =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember in1, ComplexFloat128VectorMember in2, ComplexFloat128MatrixMember out) {
			RModuleDirectProduct.compute(G.CQUAD, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat128VectorMember,ComplexFloat128VectorMember,ComplexFloat128MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexFloat128VectorMember> ISNAN =
			new Function1<Boolean, ComplexFloat128VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat128VectorMember a) {
			return SequenceIsNan.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat128VectorMember> NAN =
			new Procedure1<ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a) {
			FillNaN.compute(G.CQUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat128VectorMember> ISINF =
			new Function1<Boolean, ComplexFloat128VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat128VectorMember a) {
			return SequenceIsInf.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat128VectorMember> INF =
			new Procedure1<ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a) {
			FillInfinite.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Procedure1<ComplexFloat128VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> ROUND =
			new Procedure4<Mode, Float128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			RModuleRound.compute(G.CQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat128VectorMember> ISZERO =
			new Function1<Boolean, ComplexFloat128VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat128VectorMember a) {
			return SequenceIsZero.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128VectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			RModuleScaleByHighPrec.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> SBR =
			new Procedure3<RationalMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			RModuleScaleByRational.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat128VectorMember, ComplexFloat128VectorMember> SBD =
			new Procedure3<Double, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(Double a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			RModuleScaleByDouble.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexFloat128VectorMember, ComplexFloat128VectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> WITHIN =
			new Function3<Boolean, Float128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public Boolean call(Float128Member tol, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			return SequencesSimilar.compute(G.CQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> ADDS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> SUBS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> MULS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> DIVS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128VectorMember, ComplexFloat128VectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> MULTELEM =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> DIVELEM =
			new Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(ComplexFloat128VectorMember a, ComplexFloat128VectorMember b, ComplexFloat128VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.CQUAD, G.CQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128VectorMember, ComplexFloat128VectorMember, ComplexFloat128VectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, ComplexFloat128VectorMember, ComplexFloat128VectorMember> SCB2 =
			new Procedure3<Integer, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			ScaleHelper.compute(G.CQUAD_VEC, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128VectorMember, ComplexFloat128VectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat128VectorMember, ComplexFloat128VectorMember> SCBH =
			new Procedure3<Integer, ComplexFloat128VectorMember, ComplexFloat128VectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128VectorMember a, ComplexFloat128VectorMember b) {
			ScaleHelper.compute(G.CQUAD_VEC, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128VectorMember, ComplexFloat128VectorMember> scaleByOneHalf() {
		return SCBH;
	}

	@Override
	public ComplexFloat128VectorMember constructExactly(byte... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember constructExactly(short... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember constructExactly(int... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember constructExactly(float... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember constructExactly(double... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(byte... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(short... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(int... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(long... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(float... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(double... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}
	
	@Override
	public ComplexFloat128VectorMember construct(BigInteger... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public ComplexFloat128VectorMember construct(BigDecimal... vals) {
		ComplexFloat128VectorMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}
}
