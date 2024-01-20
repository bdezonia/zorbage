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
package nom.bdezonia.zorbage.type.quaternion.float64;

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
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64RModule
	implements
		RModule<QuaternionFloat64RModule,QuaternionFloat64RModuleMember,QuaternionFloat64Algebra,QuaternionFloat64Member>,
		Constructible1dLong<QuaternionFloat64RModuleMember>,
		Norm<QuaternionFloat64RModuleMember,Float64Member>,
		Products<QuaternionFloat64RModuleMember,QuaternionFloat64Member, QuaternionFloat64MatrixMember>,
		DirectProduct<QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>,
		Rounding<Float64Member,QuaternionFloat64RModuleMember>, Infinite<QuaternionFloat64RModuleMember>,
		NaN<QuaternionFloat64RModuleMember>,
		ScaleByHighPrec<QuaternionFloat64RModuleMember>,
		ScaleByRational<QuaternionFloat64RModuleMember>,
		ScaleByDouble<QuaternionFloat64RModuleMember>,
		ScaleByOneHalf<QuaternionFloat64RModuleMember>,
		ScaleByTwo<QuaternionFloat64RModuleMember>,
		Tolerance<Float64Member,QuaternionFloat64RModuleMember>,
		ArrayLikeMethods<QuaternionFloat64RModuleMember,QuaternionFloat64Member>,
		ConstructibleFromBytes<QuaternionFloat64RModuleMember>,
		ConstructibleFromShorts<QuaternionFloat64RModuleMember>,
		ConstructibleFromInts<QuaternionFloat64RModuleMember>,
		ConstructibleFromLongs<QuaternionFloat64RModuleMember>,
		ConstructibleFromFloats<QuaternionFloat64RModuleMember>,
		ConstructibleFromDoubles<QuaternionFloat64RModuleMember>,
		ConstructibleFromBigIntegers<QuaternionFloat64RModuleMember>,
		ConstructibleFromBigDecimals<QuaternionFloat64RModuleMember>,
		ExactlyConstructibleFromBytes<QuaternionFloat64RModuleMember>,
		ExactlyConstructibleFromShorts<QuaternionFloat64RModuleMember>,
		ExactlyConstructibleFromInts<QuaternionFloat64RModuleMember>,
		ExactlyConstructibleFromFloats<QuaternionFloat64RModuleMember>,
		ExactlyConstructibleFromDoubles<QuaternionFloat64RModuleMember>,
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
		return "64-bit based quaternion rmodule";
	}

	public QuaternionFloat64RModule() { }

	@Override
	public QuaternionFloat64RModuleMember construct() {
		return new QuaternionFloat64RModuleMember();
	}

	@Override
	public QuaternionFloat64RModuleMember construct(QuaternionFloat64RModuleMember other) {
		return new QuaternionFloat64RModuleMember(other);
	}

	@Override
	public QuaternionFloat64RModuleMember construct(String s) {
		return new QuaternionFloat64RModuleMember(s);
	}

	@Override
	public QuaternionFloat64RModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionFloat64RModuleMember(s, d1);
	}

	@Override
	public QuaternionFloat64RModuleMember constructExactly(byte... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}
	
	@Override
	public QuaternionFloat64RModuleMember constructExactly(short... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionFloat64RModuleMember constructExactly(int... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionFloat64RModuleMember constructExactly(float... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionFloat64RModuleMember constructExactly(double... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}
	
	@Override
	public QuaternionFloat64RModuleMember construct(byte... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(short... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(int... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(long... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(float... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(double... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(BigInteger... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public QuaternionFloat64RModuleMember construct(BigDecimal... vals) {
		QuaternionFloat64RModuleMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}
	
	private final Procedure1<QuaternionFloat64RModuleMember> ZER = 
			new Procedure1<QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64RModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> NEG =
			new Procedure2<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleNegate.compute(G.QDBL, a, b);
		}
	};
	

	@Override
	public Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> ADD =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c)
		{
			RModuleAdd.compute(G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> SUB =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c)
		{
			RModuleSubtract.compute(G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> EQ =
			new Function2<Boolean, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			return RModuleEqual.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> NEQ =
			new Function2<Boolean, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> ASSIGN = 
			new Procedure2<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember from, QuaternionFloat64RModuleMember to) {
			RModuleAssign.compute(G.QDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat64RModuleMember,Float64Member> NORM =
			new Procedure2<QuaternionFloat64RModuleMember, Float64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, Float64Member b) {
			RModuleDefaultNorm.compute(G.QDBL, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64RModuleMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> SCALE =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleScale.compute(G.QDBL, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> CROSS =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c)
		{
			CrossProduct.compute(G.QDBL_RMOD, G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> DOT =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
			DotProduct.compute(G.QDBL_RMOD, G.QDBL, G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> PERP =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
			PerpDotProduct.compute(G.QDBL_RMOD, G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> VTRIPLE =
			new Procedure4<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c, QuaternionFloat64RModuleMember d)
		{
			QuaternionFloat64RModuleMember b_cross_c = new QuaternionFloat64RModuleMember(new double[3*4]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> STRIPLE =
			new Procedure4<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b,
				QuaternionFloat64RModuleMember c, QuaternionFloat64Member d)
		{
			QuaternionFloat64RModuleMember b_cross_c = new QuaternionFloat64RModuleMember(new double[3*4]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> CONJ =
			new Procedure2<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleConjugate.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> VDP =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember in1, QuaternionFloat64RModuleMember in2,
				QuaternionFloat64MatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> DP =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember in1, QuaternionFloat64RModuleMember in2,
				QuaternionFloat64MatrixMember out)
		{
			RModuleDirectProduct.compute(G.QDBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember,QuaternionFloat64RModuleMember,QuaternionFloat64MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, QuaternionFloat64RModuleMember> ISNAN =
			new Function1<Boolean, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a) {
			return SequenceIsNan.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64RModuleMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat64RModuleMember> NAN =
			new Procedure1<QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a) {
			FillNaN.compute(G.QDBL, a.rawData());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64RModuleMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat64RModuleMember> ISINF =
			new Function1<Boolean, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a) {
			return SequenceIsInf.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64RModuleMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat64RModuleMember> INF =
			new Procedure1<QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a) {
			FillInfinite.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Procedure1<QuaternionFloat64RModuleMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> ROUND =
			new Procedure4<Mode, Float64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			RModuleRound.compute(G.QDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat64RModuleMember> ISZERO =
			new Function1<Boolean, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64RModuleMember a) {
			return SequenceIsZero.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64RModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
			RModuleScaleByHighPrec.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
			RModuleScaleByRational.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> SBD =
			new Procedure3<Double, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
			RModuleScaleByDouble.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> WITHIN =
			new Function3<Boolean, Float64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public Boolean call(Float64Member tol, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			return SequencesSimilar.compute(G.QDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> ADDS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QDBL, G.QDBL.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> SUBS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QDBL, G.QDBL.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> MULS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> DIVS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QDBL, G.QDBL.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> MULTELEM =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> DIVELEM =
			new Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QDBL, G.QDBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			ScaleHelper.compute(G.QDBL_RMOD, G.QDBL, new QuaternionFloat64Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> SCBH =
			new Procedure3<Integer, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
			ScaleHelper.compute(G.QDBL_RMOD, G.QDBL, new QuaternionFloat64Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64RModuleMember, QuaternionFloat64RModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
