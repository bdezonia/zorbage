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
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
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
public class OctonionFloat16Matrix
	implements
		RingWithUnity<OctonionFloat16Matrix, OctonionFloat16MatrixMember>,
		MatrixRing<OctonionFloat16Matrix, OctonionFloat16MatrixMember, OctonionFloat16Algebra, OctonionFloat16Member>,
		Constructible2dLong<OctonionFloat16MatrixMember>,
		Rounding<Float16Member, OctonionFloat16MatrixMember>,
		Norm<OctonionFloat16MatrixMember,Float16Member>,
		DirectProduct<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember>,
		Exponential<OctonionFloat16MatrixMember>,
		Trigonometric<OctonionFloat16MatrixMember>,
		Hyperbolic<OctonionFloat16MatrixMember>,
		RealConstants<OctonionFloat16MatrixMember>,
		Infinite<OctonionFloat16MatrixMember>,
		NaN<OctonionFloat16MatrixMember>,
		ScaleByHighPrec<OctonionFloat16MatrixMember>,
		ScaleByRational<OctonionFloat16MatrixMember>,
		ScaleByDouble<OctonionFloat16MatrixMember>,
		ScaleByOneHalf<OctonionFloat16MatrixMember>,
		ScaleByTwo<OctonionFloat16MatrixMember>,
		Tolerance<Float16Member,OctonionFloat16MatrixMember>,
		ArrayLikeMethods<OctonionFloat16MatrixMember,OctonionFloat16Member>,
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
		return "16-bit based octonion matrix";
	}

	public OctonionFloat16Matrix() { }

	@Override
	public OctonionFloat16MatrixMember construct() {
		return new OctonionFloat16MatrixMember();
	}

	@Override
	public OctonionFloat16MatrixMember construct(OctonionFloat16MatrixMember other) {
		return new OctonionFloat16MatrixMember(other);
	}

	@Override
	public OctonionFloat16MatrixMember construct(String s) {
		return new OctonionFloat16MatrixMember(s);
	}

	@Override
	public OctonionFloat16MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionFloat16MatrixMember(s, d1, d2);
	}

	private final Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> MUL =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixMultiply.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixPower.compute(power, G.OHLF, G.OHLF_RMOD, G.OHLF_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat16MatrixMember> ZER =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> NEG =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixNegate.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> ADD =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixAddition.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> SUB =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixSubtraction.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> EQ =
			new Function2<Boolean, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			return MatrixEqual.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> NEQ =
			new Function2<Boolean, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> ASSIGN =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember from, OctonionFloat16MatrixMember to) {
			MatrixAssign.compute(G.OHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat16MatrixMember,Float16Member> NORM =
			new Procedure2<OctonionFloat16MatrixMember, Float16Member>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, Float16Member b) {
			MatrixSpectralNorm.compute(G.OHLF_MAT, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float16Member,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixRound.compute(G.OHLF, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,OctonionFloat16MatrixMember> ISNAN =
			new Function1<Boolean, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat16MatrixMember a) {
			return SequenceIsNan.compute(G.OHLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat16MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat16MatrixMember> NAN =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			FillNaN.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Procedure1<OctonionFloat16MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,OctonionFloat16MatrixMember> ISINF =
			new Function1<Boolean, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat16MatrixMember a) {
			return SequenceIsInf.compute(G.OHLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat16MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat16MatrixMember> INF =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			FillInfinite.compute(G.OHLF, a.rawData());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> CONJ =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixConjugate.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> TRANSP =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixTranspose.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> CONJTRANSP =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixConjugateTranspose.compute(G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO - test

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16Member> DET =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16Member>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16Member b) {
			MatrixDeterminant.compute(G.OHLF_MAT, G.OHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16MatrixMember,OctonionFloat16Member> det() {
		return DET;
	}

	private final Procedure1<OctonionFloat16MatrixMember> UNITY =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			MatrixUnity.compute(G.OHLF, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> INVERT = 
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			MatrixInvert.compute(G.OHLF, G.OHLF_RMOD, G.OHLF_MAT, a, b);
		}
	};
	
	@Override
	public final Procedure2<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> invert() {
		return INVERT;
	}

	private final Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> DIVIDE =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			// invert and multiply
			OctonionFloat16MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> DP =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember in1, OctonionFloat16MatrixMember in2, OctonionFloat16MatrixMember out) {
			MatrixDirectProduct.compute(G.OHLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember,OctonionFloat16MatrixMember,OctonionFloat16MatrixMember> directProduct()
	{
		return DP;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SCALE =
			new Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16Member a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixScale.compute(G.OHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINH =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.OHLF_MAT, G.OHLF, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> COSH =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.OHLF_MAT, G.OHLF, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINHANDCOSH = 
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember s, OctonionFloat16MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> TANH =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			OctonionFloat16MatrixMember s = G.OHLF_MAT.construct();
			OctonionFloat16MatrixMember c = G.OHLF_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINCH =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			Sinch.compute(G.OHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINCHPI =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			Sinchpi.compute(G.OHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SIN =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			TaylorEstimateSin.compute(18, G.OHLF_MAT, G.OHLF, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> COS =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			TaylorEstimateCos.compute(18, G.OHLF_MAT, G.OHLF, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> TAN =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			OctonionFloat16MatrixMember s = G.OHLF_MAT.construct();
			OctonionFloat16MatrixMember c = G.OHLF_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINANDCOS =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember s, OctonionFloat16MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINC =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			Sinc.compute(G.OHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SINCPI =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			Sincpi.compute(G.OHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> EXP =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			TaylorEstimateExp.compute(35, G.OHLF_MAT, G.OHLF, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> LOG =
			new Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			TaylorEstimateLog.compute(8, G.OHLF_MAT, G.OHLF, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, OctonionFloat16MatrixMember> ISZERO =
			new Function1<Boolean, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat16MatrixMember a) {
			return SequenceIsZero.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<OctonionFloat16MatrixMember> PI =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			OctonionFloat16Member pi = G.OHLF.construct();
			G.OHLF.PI().call(pi);
			MatrixConstantDiagonal.compute(G.OHLF, pi, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat16MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<OctonionFloat16MatrixMember> E =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			OctonionFloat16Member e = G.OHLF.construct();
			G.OHLF.E().call(e);
			MatrixConstantDiagonal.compute(G.OHLF, e, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat16MatrixMember> E() {
		return E;
	}

	private final Procedure1<OctonionFloat16MatrixMember> PHI =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			OctonionFloat16Member phi = G.OHLF.construct();
			G.OHLF.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.OHLF, phi, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat16MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<OctonionFloat16MatrixMember> GAMMA =
			new Procedure1<OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a) {
			OctonionFloat16Member gamma = G.OHLF.construct();
			G.OHLF.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.OHLF, gamma, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat16MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SBR =
			new Procedure3<RationalMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixScaleByRational.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SBD =
			new Procedure3<Double, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(Double a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			MatrixScaleByDouble.compute(G.OHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> WITHIN =
			new Function3<Boolean, Float16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(Float16Member tol, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.OHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> ADDS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.OHLF, G.OHLF.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SUBS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.OHLF, G.OHLF.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> MULS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.OHLF, G.OHLF.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> DIVS =
			new Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.OHLF, G.OHLF.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> MULTELEM =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.OHLF, G.OHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> DIVELEM =
			new Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b, OctonionFloat16MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.OHLF, G.OHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16MatrixMember, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SCB2 =
			new Procedure3<Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			ScaleHelper.compute(G.OHLF_MAT, G.OHLF, new OctonionFloat16Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> SCBH =
			new Procedure3<Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat16MatrixMember a, OctonionFloat16MatrixMember b) {
			ScaleHelper.compute(G.OHLF_MAT, G.OHLF, new OctonionFloat16Member(0.5f, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat16MatrixMember, OctonionFloat16MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, OctonionFloat16MatrixMember> ISUNITY =
			new Function1<Boolean, OctonionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat16MatrixMember a) {
			return MatrixIsUnity.compute(G.OHLF, a);
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16MatrixMember> isUnity() {
		return ISUNITY;
	}
}
