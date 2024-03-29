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

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.MatrixType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
import nom.bdezonia.zorbage.algorithm.MatrixConjugateTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixConstantDiagonal;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixIsUnity;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixScale;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByDouble;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByRational;
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCos;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCosh;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateExp;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateLog;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSin;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSinh;
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
public class QuaternionFloat64Matrix
	implements
		RingWithUnity<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember>,
		MatrixRing<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember, QuaternionFloat64Algebra, QuaternionFloat64Member>,
		Constructible2dLong<QuaternionFloat64MatrixMember>,
		Rounding<Float64Member, QuaternionFloat64MatrixMember>,
		Norm<QuaternionFloat64MatrixMember,Float64Member>,
		DirectProduct<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>,
		Exponential<QuaternionFloat64MatrixMember>,
		Trigonometric<QuaternionFloat64MatrixMember>,
		Hyperbolic<QuaternionFloat64MatrixMember>,
		RealConstants<QuaternionFloat64MatrixMember>,
		Infinite<QuaternionFloat64MatrixMember>,
		NaN<QuaternionFloat64MatrixMember>,
		ScaleByHighPrec<QuaternionFloat64MatrixMember>,
		ScaleByRational<QuaternionFloat64MatrixMember>,
		ScaleByDouble<QuaternionFloat64MatrixMember>,
		ScaleByOneHalf<QuaternionFloat64MatrixMember>,
		ScaleByTwo<QuaternionFloat64MatrixMember>,
		Tolerance<Float64Member,QuaternionFloat64MatrixMember>,
		ArrayLikeMethods<QuaternionFloat64MatrixMember,QuaternionFloat64Member>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		MatrixType,
		NanIncludedType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "64-bit based quaternion matrix";
	}

	public QuaternionFloat64Matrix() { }

	@Override
	public QuaternionFloat64MatrixMember construct() {
		return new QuaternionFloat64MatrixMember();
	}

	@Override
	public QuaternionFloat64MatrixMember construct(QuaternionFloat64MatrixMember other) {
		return new QuaternionFloat64MatrixMember(other);
	}

	@Override
	public QuaternionFloat64MatrixMember construct(String s) {
		return new QuaternionFloat64MatrixMember(s);
	}

	@Override
	public QuaternionFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new QuaternionFloat64MatrixMember(s, d1, d2);
	}

	private final Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> MUL =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b,
				QuaternionFloat64MatrixMember c)
		{
			MatrixMultiply.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> POWER =
			new Procedure3<java.lang.Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat64MatrixMember a,
				QuaternionFloat64MatrixMember b)
		{
			MatrixPower.compute(power, G.QDBL, G.QDBL_RMOD, G.QDBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> ZER =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<QuaternionFloat64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> NEG =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			MatrixNegate.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> ADD =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b,
				QuaternionFloat64MatrixMember c)
		{
			MatrixAddition.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> SUB =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b,
				QuaternionFloat64MatrixMember c)
		{
			MatrixSubtraction.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> EQ =
			new Function2<Boolean, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			return MatrixEqual.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> NEQ =
			new Function2<Boolean, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> ASSIGN =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember from, QuaternionFloat64MatrixMember to) {
			MatrixAssign.compute(G.QDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,Float64Member> NORM =
			new Procedure2<QuaternionFloat64MatrixMember, Float64Member>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, Float64Member b) {
			MatrixSpectralNorm.compute(G.QDBL_MAT, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			MatrixRound.compute(G.QDBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat64MatrixMember> ISNAN =
			new Function1<Boolean,QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64MatrixMember a) {
			return SequenceIsNan.compute(G.QDBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat64MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> NAN =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			FillNaN.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Procedure1<QuaternionFloat64MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat64MatrixMember> ISINF =
			new Function1<Boolean,QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64MatrixMember a) {
			return SequenceIsInf.compute(G.QDBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat64MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> INF =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			FillInfinite.compute(G.QDBL, a.rawData());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> CONJ =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			MatrixConjugate.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> TRANSP =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			MatrixTranspose.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> CONJTRANSP =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			MatrixConjugateTranspose.compute(G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO test

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64Member> DET =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64Member>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64Member b) {
			MatrixDeterminant.compute(G.QDBL_MAT, G.QDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64Member> det() {
		return DET;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> UNITY =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			MatrixUnity.compute(G.QDBL, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> INV =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			MatrixInvert.compute(G.QDBL, G.QDBL_RMOD, G.QDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> DIVIDE =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b,
				QuaternionFloat64MatrixMember c)
		{
			// invert and multiply
			QuaternionFloat64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> divide()
	{
		return DIVIDE;
	}

	private final Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> DP =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember in1, QuaternionFloat64MatrixMember in2,
				QuaternionFloat64MatrixMember out)
		{
			MatrixDirectProduct.compute(G.QDBL, in1, in2, out);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember,QuaternionFloat64MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SCALE =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64Member a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
			MatrixScale.compute(G.QDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINH =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.QDBL_MAT, G.QDBL, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> COSH =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.QDBL_MAT, G.QDBL, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINHANDCOSH = 
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember s, QuaternionFloat64MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> TANH =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			QuaternionFloat64MatrixMember s = construct();
			QuaternionFloat64MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINCH =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			Sinch.compute(G.QDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINCHPI =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			Sinchpi.compute(G.QDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SIN =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			TaylorEstimateSin.compute(18, G.QDBL_MAT, G.QDBL, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> COS =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			TaylorEstimateCos.compute(18, G.QDBL_MAT, G.QDBL, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> TAN =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			QuaternionFloat64MatrixMember s = construct();
			QuaternionFloat64MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINANDCOS =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember s, QuaternionFloat64MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINC =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			Sinc.compute(G.QDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SINCPI =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			Sincpi.compute(G.QDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> EXP =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			TaylorEstimateExp.compute(35, G.QDBL_MAT, G.QDBL, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> LOG =
			new Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			TaylorEstimateLog.compute(8, G.QDBL_MAT, G.QDBL, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, QuaternionFloat64MatrixMember> ISZERO =
			new Function1<Boolean, QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64MatrixMember a) {
			return SequenceIsZero.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> PI =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			QuaternionFloat64Member pi = G.QDBL.construct();
			G.QDBL.PI().call(pi);
			MatrixConstantDiagonal.compute(G.QDBL, pi, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat64MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> E =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			QuaternionFloat64Member e = G.QDBL.construct();
			G.QDBL.E().call(e);
			MatrixConstantDiagonal.compute(G.QDBL, e, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat64MatrixMember> E() {
		return E;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> PHI =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			QuaternionFloat64Member phi = G.QDBL.construct();
			G.QDBL.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.QDBL, phi, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat64MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<QuaternionFloat64MatrixMember> GAMMA =
			new Procedure1<QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a) {
			QuaternionFloat64Member gamma = G.QDBL.construct();
			G.QDBL.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.QDBL, gamma, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat64MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
			MatrixScaleByRational.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SBD =
			new Procedure3<Double, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
			MatrixScaleByDouble.compute(G.QDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> WITHIN =
			new Function3<Boolean, Float64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(Float64Member tol, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.QDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> ADDS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.QDBL, G.QDBL.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SUBS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.QDBL, G.QDBL.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> MULS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> DIVS =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.QDBL, G.QDBL.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> MULTELEM =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> DIVELEM =
			new Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.QDBL, G.QDBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			ScaleHelper.compute(G.QDBL_MAT, G.QDBL, new QuaternionFloat64Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> SCBH =
			new Procedure3<Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
			ScaleHelper.compute(G.QDBL_MAT, G.QDBL, new QuaternionFloat64Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat64MatrixMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64MatrixMember a) {
			return MatrixIsUnity.compute(G.QDBL, a);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64MatrixMember> isUnity() {
		return ISUNITY;
	}
}
