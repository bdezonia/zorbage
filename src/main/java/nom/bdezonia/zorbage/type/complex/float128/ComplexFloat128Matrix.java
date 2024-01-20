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
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCos;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCosh;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateExp;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateLog;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSin;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSinh;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
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

import java.lang.Integer;
import java.math.BigDecimal;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat128Matrix
	implements
		RingWithUnity<ComplexFloat128Matrix, ComplexFloat128MatrixMember>,
		MatrixRing<ComplexFloat128Matrix, ComplexFloat128MatrixMember, ComplexFloat128Algebra, ComplexFloat128Member>,
		Constructible2dLong<ComplexFloat128MatrixMember>,
		Rounding<Float128Member, ComplexFloat128MatrixMember>,
		Norm<ComplexFloat128MatrixMember,Float128Member>,
		DirectProduct<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>,
		Exponential<ComplexFloat128MatrixMember>,
		Trigonometric<ComplexFloat128MatrixMember>,
		Hyperbolic<ComplexFloat128MatrixMember>,
		RealConstants<ComplexFloat128MatrixMember>,
		Infinite<ComplexFloat128MatrixMember>,
		NaN<ComplexFloat128MatrixMember>,
		ScaleByHighPrec<ComplexFloat128MatrixMember>,
		ScaleByRational<ComplexFloat128MatrixMember>,
		ScaleByDouble<ComplexFloat128MatrixMember>,
		ScaleByOneHalf<ComplexFloat128MatrixMember>,
		ScaleByTwo<ComplexFloat128MatrixMember>,
		Tolerance<Float128Member,ComplexFloat128MatrixMember>,
		ArrayLikeMethods<ComplexFloat128MatrixMember,ComplexFloat128Member>,
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
		return "128-bit based complex matrix";
	}
	
	public ComplexFloat128Matrix() { }

	@Override
	public ComplexFloat128MatrixMember construct() {
		return new ComplexFloat128MatrixMember();
	}

	@Override
	public ComplexFloat128MatrixMember construct(ComplexFloat128MatrixMember other) {
		return new ComplexFloat128MatrixMember(other);
	}

	@Override
	public ComplexFloat128MatrixMember construct(String s) {
		return new ComplexFloat128MatrixMember(s);
	}

	@Override
	public ComplexFloat128MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexFloat128MatrixMember(s, d1, d2);
	}

	private final Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> MUL =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixMultiply.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> POWER =
			new Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		
		@Override
		public void call(Integer power, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixPower.compute(power, G.CQUAD, G.CQUAD_VEC, G.CQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat128MatrixMember> ZER =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> NEG =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixNegate.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> ADD =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixAddition.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> SUB =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixSubtraction.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> EQ =
			new Function2<Boolean, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			return MatrixEqual.compute(G.CQUAD, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> NEQ =
			new Function2<Boolean, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> ASSIGN =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember from, ComplexFloat128MatrixMember to) {
			MatrixAssign.compute(G.CQUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat128MatrixMember,Float128Member> NORM =
			new Procedure2<ComplexFloat128MatrixMember, Float128Member>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, Float128Member b) {
			MatrixSpectralNorm.compute(G.CQUAD_MAT, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128MatrixMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float128Member,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixRound.compute(G.CQUAD, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat128MatrixMember> ISNAN =
			new Function1<Boolean,ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat128MatrixMember a) {
			return SequenceIsNan.compute(G.CQUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat128MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat128MatrixMember> NAN =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			FillNaN.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Procedure1<ComplexFloat128MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,ComplexFloat128MatrixMember> ISINF =
			new Function1<Boolean,ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat128MatrixMember a) {
			return SequenceIsInf.compute(G.CQUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat128MatrixMember> isInfinite() {
		return ISINF;
	}
	
	private final Procedure1<ComplexFloat128MatrixMember> INF =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			FillInfinite.compute(G.CQUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> CONJ =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixConjugate.compute(G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> TRANSP =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixTranspose.compute(G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> CONJTRANSP =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixConjugateTranspose.compute(G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128Member> DET =
			new Procedure2<ComplexFloat128MatrixMember,ComplexFloat128Member>()
	{				
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128Member b) {
			MatrixDeterminant.compute(G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128Member> det() {
		return DET;
	}

	private final Procedure1<ComplexFloat128MatrixMember> UNITY =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			MatrixUnity.compute(G.CQUAD, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> INV =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			MatrixInvert.compute(G.CQUAD, G.CQUAD_VEC, G.CQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> DIVIDE =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			// invert and multiply
			ComplexFloat128MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> DP =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember in1, ComplexFloat128MatrixMember in2, ComplexFloat128MatrixMember out) {
			MatrixDirectProduct.compute(G.CQUAD, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember,ComplexFloat128MatrixMember,ComplexFloat128MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SCALE =
			new Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixScale.compute(G.CQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINH =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> COSH =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINHANDCOSH = 
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember s, ComplexFloat128MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> TANH =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			ComplexFloat128MatrixMember s = construct();
			ComplexFloat128MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINCH =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			Sinch.compute(G.CQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINCHPI =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			Sinchpi.compute(G.CQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SIN =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TaylorEstimateSin.compute(18, G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> COS =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TaylorEstimateCos.compute(18, G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> TAN =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			ComplexFloat128MatrixMember s = construct();
			ComplexFloat128MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINANDCOS =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember s, ComplexFloat128MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINC =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			Sinc.compute(G.CQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SINCPI =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			Sincpi.compute(G.CQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> EXP =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TaylorEstimateExp.compute(35, G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> LOG =
			new Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TaylorEstimateLog.compute(8, G.CQUAD_MAT, G.CQUAD, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, ComplexFloat128MatrixMember> ISZERO =
			new Function1<Boolean, ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat128MatrixMember a) {
			return SequenceIsZero.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<ComplexFloat128MatrixMember> PI =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			ComplexFloat128Member pi = G.CQUAD.construct();
			G.CQUAD.PI().call(pi);
			MatrixConstantDiagonal.compute(G.CQUAD, pi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<ComplexFloat128MatrixMember> E =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			ComplexFloat128Member e = G.CQUAD.construct();
			G.CQUAD.E().call(e);
			MatrixConstantDiagonal.compute(G.CQUAD, e, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128MatrixMember> E() {
		return E;
	}

	private final Procedure1<ComplexFloat128MatrixMember> PHI =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			ComplexFloat128Member phi = G.CQUAD.construct();
			G.CQUAD.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.CQUAD, phi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<ComplexFloat128MatrixMember> GAMMA =
			new Procedure1<ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a) {
			ComplexFloat128Member gamma = G.CQUAD.construct();
			G.CQUAD.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.CQUAD, gamma, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SBR =
			new Procedure3<RationalMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixScaleByRational.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SBD =
			new Procedure3<Double, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(Double a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			MatrixScaleByDouble.compute(G.CQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> WITHIN =
			new Function3<Boolean, Float128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(Float128Member tol, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.CQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> ADDS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SUBS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> MULS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> DIVS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> MULTELEM =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> DIVELEM =
			new Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b, ComplexFloat128MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.CQUAD, G.CQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128MatrixMember, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SCB2 =
			new Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			ScaleHelper.compute(G.CQUAD_MAT, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> SCBH =
			new Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128MatrixMember a, ComplexFloat128MatrixMember b) {
			ScaleHelper.compute(G.CQUAD_MAT, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128MatrixMember, ComplexFloat128MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat128MatrixMember> ISUNITY =
			new Function1<Boolean, ComplexFloat128MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat128MatrixMember a) {
			return MatrixIsUnity.compute(G.CQUAD, a);
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128MatrixMember> isUnity() {
		return ISUNITY;
	}
}
