/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.quaternion.float32;

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
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat32Matrix
	implements
		RingWithUnity<QuaternionFloat32Matrix, QuaternionFloat32MatrixMember>,
		MatrixRing<QuaternionFloat32Matrix, QuaternionFloat32MatrixMember, QuaternionFloat32Algebra, QuaternionFloat32Member>,
		Constructible2dLong<QuaternionFloat32MatrixMember>,
		Rounding<Float32Member, QuaternionFloat32MatrixMember>,
		Norm<QuaternionFloat32MatrixMember,Float32Member>,
		DirectProduct<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>,
		Exponential<QuaternionFloat32MatrixMember>,
		Trigonometric<QuaternionFloat32MatrixMember>,
		Hyperbolic<QuaternionFloat32MatrixMember>,
		RealConstants<QuaternionFloat32MatrixMember>,
		Infinite<QuaternionFloat32MatrixMember>,
		NaN<QuaternionFloat32MatrixMember>,
		ScaleByHighPrec<QuaternionFloat32MatrixMember>,
		ScaleByRational<QuaternionFloat32MatrixMember>,
		ScaleByDouble<QuaternionFloat32MatrixMember>,
		ScaleByOneHalf<QuaternionFloat32MatrixMember>,
		ScaleByTwo<QuaternionFloat32MatrixMember>,
		Tolerance<Float32Member,QuaternionFloat32MatrixMember>,
		ArrayLikeMethods<QuaternionFloat32MatrixMember,QuaternionFloat32Member>
{
	public QuaternionFloat32Matrix() { }

	private final Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> MUL =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b,
				QuaternionFloat32MatrixMember c)
		{
			MatrixMultiply.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> POWER =
			new Procedure3<java.lang.Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat32MatrixMember a,
				QuaternionFloat32MatrixMember b)
		{
			MatrixPower.compute(power, G.QFLT, G.QFLT_RMOD, G.QFLT_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> ZER =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<QuaternionFloat32MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> NEG =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			MatrixNegate.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> ADD =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b,
				QuaternionFloat32MatrixMember c)
		{
			MatrixAddition.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> SUB =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b,
				QuaternionFloat32MatrixMember c)
		{
			MatrixSubtraction.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> EQ =
			new Function2<Boolean, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			return MatrixEqual.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> NEQ =
			new Function2<Boolean, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> ASSIGN =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember from, QuaternionFloat32MatrixMember to) {
			MatrixAssign.compute(G.QFLT, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public QuaternionFloat32MatrixMember construct() {
		return new QuaternionFloat32MatrixMember();
	}

	@Override
	public QuaternionFloat32MatrixMember construct(QuaternionFloat32MatrixMember other) {
		return new QuaternionFloat32MatrixMember(other);
	}

	@Override
	public QuaternionFloat32MatrixMember construct(String s) {
		return new QuaternionFloat32MatrixMember(s);
	}

	@Override
	public QuaternionFloat32MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new QuaternionFloat32MatrixMember(s, d1, d2);
	}

	private final Procedure2<QuaternionFloat32MatrixMember,Float32Member> NORM =
			new Procedure2<QuaternionFloat32MatrixMember, Float32Member>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, Float32Member b) {
			MatrixSpectralNorm.compute(G.QFLT_MAT, G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float32Member,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			MatrixRound.compute(G.QFLT, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat32MatrixMember> ISNAN =
			new Function1<Boolean,QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32MatrixMember a) {
			return SequenceIsNan.compute(G.QFLT, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat32MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> NAN =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			FillNaN.compute(G.QFLT, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat32MatrixMember> ISINF =
			new Function1<Boolean,QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32MatrixMember a) {
			return SequenceIsInf.compute(G.QFLT, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat32MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> INF =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			FillInfinite.compute(G.QFLT, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> CONJ =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			MatrixConjugate.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> TRANSP =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			MatrixTranspose.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> CONJTRANSP =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			MatrixConjugateTranspose.compute(G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO test

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32Member> DET =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32Member>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32Member b) {
			MatrixDeterminant.compute(G.QFLT_MAT, G.QFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32Member> det() {
		return DET;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> UNITY =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			MatrixUnity.compute(G.QFLT, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> INV =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			MatrixInvert.compute(G.QFLT, G.QFLT_RMOD, G.QFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> DIVIDE =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b,
				QuaternionFloat32MatrixMember c)
		{
			// invert and multiply
			QuaternionFloat32MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> divide()
	{
		return DIVIDE;
	}

	private final Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> DP =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember in1, QuaternionFloat32MatrixMember in2,
				QuaternionFloat32MatrixMember out)
		{
			MatrixDirectProduct.compute(G.QFLT, in1, in2, out);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember,QuaternionFloat32MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SCALE =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32Member a, QuaternionFloat32MatrixMember b, QuaternionFloat32MatrixMember c) {
			MatrixScale.compute(G.QFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINH =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.QFLT_MAT, G.QFLT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> COSH =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.QFLT_MAT, G.QFLT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINHANDCOSH = 
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember s, QuaternionFloat32MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> TANH =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			QuaternionFloat32MatrixMember s = G.QFLT_MAT.construct();
			QuaternionFloat32MatrixMember c = G.QFLT_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINCH =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			Sinch.compute(G.QFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINCHPI =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			Sinchpi.compute(G.QFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SIN =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			TaylorEstimateSin.compute(18, G.QFLT_MAT, G.QFLT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> COS =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			TaylorEstimateCos.compute(18, G.QFLT_MAT, G.QFLT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> TAN =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			QuaternionFloat32MatrixMember s = G.QFLT_MAT.construct();
			QuaternionFloat32MatrixMember c = G.QFLT_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINANDCOS =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember s, QuaternionFloat32MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINC =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			Sinc.compute(G.QFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SINCPI =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			Sincpi.compute(G.QFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> EXP =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			TaylorEstimateExp.compute(35, G.QFLT_MAT, G.QFLT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> LOG =
			new Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			TaylorEstimateLog.compute(8, G.QFLT_MAT, G.QFLT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, QuaternionFloat32MatrixMember> ISZERO =
			new Function1<Boolean, QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32MatrixMember a) {
			return SequenceIsZero.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> PI =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			QuaternionFloat32Member pi = G.QFLT.construct();
			G.QFLT.PI().call(pi);
			MatrixConstantDiagonal.compute(G.QFLT, pi, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> E =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			QuaternionFloat32Member e = G.QFLT.construct();
			G.QFLT.E().call(e);
			MatrixConstantDiagonal.compute(G.QFLT, e, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32MatrixMember> E() {
		return E;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> PHI =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			QuaternionFloat32Member phi = G.QFLT.construct();
			G.QFLT.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.QFLT, phi, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<QuaternionFloat32MatrixMember> GAMMA =
			new Procedure1<QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a) {
			QuaternionFloat32Member gamma = G.QFLT.construct();
			G.QFLT.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.QFLT, gamma, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat32MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat32MatrixMember b, QuaternionFloat32MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat32MatrixMember b, QuaternionFloat32MatrixMember c) {
			MatrixScaleByRational.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SBD =
			new Procedure3<Double, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(Double a, QuaternionFloat32MatrixMember b, QuaternionFloat32MatrixMember c) {
			MatrixScaleByDouble.compute(G.QFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> WITHIN =
			new Function3<Boolean, Float32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(Float32Member tol, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.QFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> ADDS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SUBS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> MULS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> DIVS =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			FixedTransform2b.compute(G.QFLT, scalar, G.QFLT.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> MULTELEM =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b, QuaternionFloat32MatrixMember c) {
			Transform3.compute(G.QFLT, G.QFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> DIVELEM =
			new Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b, QuaternionFloat32MatrixMember c) {
			Transform3.compute(G.QFLT, G.QFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			ScaleHelper.compute(G.QFLT_MAT, G.QFLT, new QuaternionFloat32Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> SCBH =
			new Procedure3<Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat32MatrixMember a, QuaternionFloat32MatrixMember b) {
			ScaleHelper.compute(G.QFLT_MAT, G.QFLT, new QuaternionFloat32Member(0.5f, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat32MatrixMember, QuaternionFloat32MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat32MatrixMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32MatrixMember a) {
			return MatrixIsUnity.compute(G.QFLT, a);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32MatrixMember> isUnity() {
		return ISUNITY;
	}
}
