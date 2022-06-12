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

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2b;
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
public class QuaternionFloat128Matrix
	implements
		RingWithUnity<QuaternionFloat128Matrix, QuaternionFloat128MatrixMember>,
		MatrixRing<QuaternionFloat128Matrix, QuaternionFloat128MatrixMember, QuaternionFloat128Algebra, QuaternionFloat128Member>,
		Constructible2dLong<QuaternionFloat128MatrixMember>,
		Rounding<Float128Member, QuaternionFloat128MatrixMember>,
		Norm<QuaternionFloat128MatrixMember,Float128Member>,
		DirectProduct<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>,
		Exponential<QuaternionFloat128MatrixMember>,
		Trigonometric<QuaternionFloat128MatrixMember>,
		Hyperbolic<QuaternionFloat128MatrixMember>,
		RealConstants<QuaternionFloat128MatrixMember>,
		Infinite<QuaternionFloat128MatrixMember>,
		NaN<QuaternionFloat128MatrixMember>,
		ScaleByHighPrec<QuaternionFloat128MatrixMember>,
		ScaleByRational<QuaternionFloat128MatrixMember>,
		ScaleByDouble<QuaternionFloat128MatrixMember>,
		ScaleByOneHalf<QuaternionFloat128MatrixMember>,
		ScaleByTwo<QuaternionFloat128MatrixMember>,
		Tolerance<Float128Member,QuaternionFloat128MatrixMember>,
		ArrayLikeMethods<QuaternionFloat128MatrixMember,QuaternionFloat128Member>
{
	@Override
	public String typeDescription() {
		return "128-bit based quaternion matrix";
	}

	public QuaternionFloat128Matrix() { }

	@Override
	public QuaternionFloat128MatrixMember construct() {
		return new QuaternionFloat128MatrixMember();
	}

	@Override
	public QuaternionFloat128MatrixMember construct(QuaternionFloat128MatrixMember other) {
		return new QuaternionFloat128MatrixMember(other);
	}

	@Override
	public QuaternionFloat128MatrixMember construct(String s) {
		return new QuaternionFloat128MatrixMember(s);
	}

	@Override
	public QuaternionFloat128MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new QuaternionFloat128MatrixMember(s, d1, d2);
	}

	private final Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> MUL =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b,
				QuaternionFloat128MatrixMember c)
		{
			MatrixMultiply.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> POWER =
			new Procedure3<java.lang.Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat128MatrixMember a,
				QuaternionFloat128MatrixMember b)
		{
			MatrixPower.compute(power, G.QQUAD, G.QQUAD_RMOD, G.QQUAD_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> ZER =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<QuaternionFloat128MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> NEG =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			MatrixNegate.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> ADD =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b,
				QuaternionFloat128MatrixMember c)
		{
			MatrixAddition.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> SUB =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b,
				QuaternionFloat128MatrixMember c)
		{
			MatrixSubtraction.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> EQ =
			new Function2<Boolean, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			return MatrixEqual.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> NEQ =
			new Function2<Boolean, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> ASSIGN =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember from, QuaternionFloat128MatrixMember to) {
			MatrixAssign.compute(G.QQUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,Float128Member> NORM =
			new Procedure2<QuaternionFloat128MatrixMember, Float128Member>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, Float128Member b) {
			MatrixSpectralNorm.compute(G.QQUAD_MAT, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float128Member,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			MatrixRound.compute(G.QQUAD, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat128MatrixMember> ISNAN =
			new Function1<Boolean,QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128MatrixMember a) {
			return SequenceIsNan.compute(G.QQUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat128MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> NAN =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			FillNaN.compute(G.QQUAD, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat128MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat128MatrixMember> ISINF =
			new Function1<Boolean,QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128MatrixMember a) {
			return SequenceIsInf.compute(G.QQUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat128MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> INF =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			FillInfinite.compute(G.QQUAD, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> CONJ =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			MatrixConjugate.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> TRANSP =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			MatrixTranspose.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> CONJTRANSP =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			MatrixConjugateTranspose.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO test

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128Member> DET =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128Member b) {
			MatrixDeterminant.compute(G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128Member> det() {
		return DET;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> UNITY =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			MatrixUnity.compute(G.QQUAD, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> INV =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			MatrixInvert.compute(G.QQUAD, G.QQUAD_RMOD, G.QQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> DIVIDE =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b,
				QuaternionFloat128MatrixMember c)
		{
			// invert and multiply
			QuaternionFloat128MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> divide()
	{
		return DIVIDE;
	}

	private final Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> DP =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember in1, QuaternionFloat128MatrixMember in2,
				QuaternionFloat128MatrixMember out)
		{
			MatrixDirectProduct.compute(G.QQUAD, in1, in2, out);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember,QuaternionFloat128MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SCALE =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128MatrixMember b, QuaternionFloat128MatrixMember c) {
			MatrixScale.compute(G.QQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINH =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> COSH =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINHANDCOSH = 
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember s, QuaternionFloat128MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> TANH =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			QuaternionFloat128MatrixMember s = construct();
			QuaternionFloat128MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINCH =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			Sinch.compute(G.QQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINCHPI =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			Sinchpi.compute(G.QQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SIN =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			TaylorEstimateSin.compute(18, G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> COS =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			TaylorEstimateCos.compute(18, G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> TAN =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			QuaternionFloat128MatrixMember s = construct();
			QuaternionFloat128MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINANDCOS =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember s, QuaternionFloat128MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINC =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			Sinc.compute(G.QQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SINCPI =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			Sincpi.compute(G.QQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> EXP =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			TaylorEstimateExp.compute(35, G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> LOG =
			new Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			TaylorEstimateLog.compute(8, G.QQUAD_MAT, G.QQUAD, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, QuaternionFloat128MatrixMember> ISZERO =
			new Function1<Boolean, QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128MatrixMember a) {
			return SequenceIsZero.compute(G.QQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> PI =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			QuaternionFloat128Member pi = G.QQUAD.construct();
			G.QQUAD.PI().call(pi);
			MatrixConstantDiagonal.compute(G.QQUAD, pi, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat128MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> E =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			QuaternionFloat128Member e = G.QQUAD.construct();
			G.QQUAD.E().call(e);
			MatrixConstantDiagonal.compute(G.QQUAD, e, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat128MatrixMember> E() {
		return E;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> PHI =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			QuaternionFloat128Member phi = G.QQUAD.construct();
			G.QQUAD.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.QQUAD, phi, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat128MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<QuaternionFloat128MatrixMember> GAMMA =
			new Procedure1<QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a) {
			QuaternionFloat128Member gamma = G.QQUAD.construct();
			G.QQUAD.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.QQUAD, gamma, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat128MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat128MatrixMember b, QuaternionFloat128MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat128MatrixMember b, QuaternionFloat128MatrixMember c) {
			MatrixScaleByRational.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SBD =
			new Procedure3<Double, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat128MatrixMember b, QuaternionFloat128MatrixMember c) {
			MatrixScaleByDouble.compute(G.QQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> WITHIN =
			new Function3<Boolean, Float128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(Float128Member tol, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.QQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> ADDS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.QQUAD, scalar, G.QQUAD.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SUBS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.QQUAD, scalar, G.QQUAD.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> MULS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.QQUAD, scalar, G.QQUAD.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> DIVS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128Member scalar, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.QQUAD, scalar, G.QQUAD.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> MULTELEM =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b, QuaternionFloat128MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.QQUAD, G.QQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> DIVELEM =
			new Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b, QuaternionFloat128MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.QQUAD, G.QQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			ScaleHelper.compute(G.QQUAD_MAT, G.QQUAD, new QuaternionFloat128Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> SCBH =
			new Procedure3<Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat128MatrixMember a, QuaternionFloat128MatrixMember b) {
			ScaleHelper.compute(G.QQUAD_MAT, G.QQUAD, new QuaternionFloat128Member(0.5, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat128MatrixMember, QuaternionFloat128MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat128MatrixMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat128MatrixMember a) {
			return MatrixIsUnity.compute(G.QQUAD, a);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128MatrixMember> isUnity() {
		return ISUNITY;
	}
}
