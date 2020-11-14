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
package nom.bdezonia.zorbage.type.float64.real;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2b;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConstantDiagonal;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
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
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

import java.lang.Integer;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64Matrix
	implements
		RingWithUnity<Float64Matrix, Float64MatrixMember>,
		MatrixRing<Float64Matrix, Float64MatrixMember, Float64Algebra, Float64Member>,
		Constructible2dLong<Float64MatrixMember>,
		Rounding<Float64Member, Float64MatrixMember>,
		Norm<Float64MatrixMember,Float64Member>,
		DirectProduct<Float64MatrixMember, Float64MatrixMember>,
		Exponential<Float64MatrixMember>,
		Trigonometric<Float64MatrixMember>,
		Hyperbolic<Float64MatrixMember>,
		RealConstants<Float64MatrixMember>,
		Infinite<Float64MatrixMember>,
		NaN<Float64MatrixMember>,
		ScaleByHighPrec<Float64MatrixMember>,
		ScaleByRational<Float64MatrixMember>,
		ScaleByDouble<Float64MatrixMember>,
		ScaleByOneHalf<Float64MatrixMember>,
		ScaleByTwo<Float64MatrixMember>,
		Tolerance<Float64Member,Float64MatrixMember>,
		ArrayLikeMethods<Float64MatrixMember,Float64Member>
{
	public Float64Matrix() { }

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> MUL =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixMultiply.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float64MatrixMember,Float64MatrixMember> POWER =
			new Procedure3<Integer, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Integer power, Float64MatrixMember a, Float64MatrixMember b) {
			MatrixPower.compute(power, G.DBL, G.DBL_VEC, G.DBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float64MatrixMember,Float64MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<Float64MatrixMember> ZER =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> NEG =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			MatrixNegate.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> ADD =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixAddition.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> SUB =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixSubtraction.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float64MatrixMember,Float64MatrixMember> EQ =
			new Function2<Boolean, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a, Float64MatrixMember b) {
			return MatrixEqual.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64MatrixMember,Float64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64MatrixMember,Float64MatrixMember> NEQ =
			new Function2<Boolean, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a, Float64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64MatrixMember,Float64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> ASSIGN =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember from, Float64MatrixMember to) {
			MatrixAssign.compute(G.DBL, from, to);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public Float64MatrixMember construct() {
		return new Float64MatrixMember();
	}

	@Override
	public Float64MatrixMember construct(Float64MatrixMember other) {
		return new Float64MatrixMember(other);
	}

	@Override
	public Float64MatrixMember construct(String s) {
		return new Float64MatrixMember(s);
	}

	@Override
	public Float64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new Float64MatrixMember(s, d1, d2);
	}

	private final Procedure2<Float64MatrixMember,Float64Member> NORM =
			new Procedure2<Float64MatrixMember, Float64Member>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64Member b) {
			MatrixSpectralNorm.compute(G.DBL_MAT, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,Float64MatrixMember,Float64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64MatrixMember a, Float64MatrixMember b) {
			MatrixRound.compute(G.DBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,Float64MatrixMember,Float64MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,Float64MatrixMember> ISNAN =
			new Function1<Boolean, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a) {
			return SequenceIsNan.compute(G.DBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float64MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float64MatrixMember> NAN =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			FillNaN.compute(G.DBL, a);
		}
	};

	@Override
	public Procedure1<Float64MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,Float64MatrixMember> ISINF =
			new Function1<Boolean, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a) {
			return SequenceIsInf.compute(G.DBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float64MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float64MatrixMember> INF =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			FillInfinite.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64MatrixMember> infinite() {
		return INF;
	}

	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> TRANSP =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			MatrixTranspose.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> CONJTRANSP =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			transpose().call(a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<Float64MatrixMember,Float64Member> DET =
			new Procedure2<Float64MatrixMember, Float64Member>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64Member b) {
			MatrixDeterminant.compute(G.DBL_MAT, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64Member> det() {
		return DET;
	}

	private final Procedure1<Float64MatrixMember> UNITY =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			MatrixUnity.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> INV =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			MatrixInvert.compute(G.DBL, G.DBL_VEC, G.DBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> DIVIDE =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			// invert and multiply
			Float64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> DP =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember in1, Float64MatrixMember in2, Float64MatrixMember out) {
			MatrixDirectProduct.compute(G.DBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> directProduct() {
		return DP;
	}

	private final Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> SCALE =
			new Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64Member a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixScale.compute(G.DBL, a, b, c);
		}
	};

	@Override
	public Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> SINH =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.DBL_MAT, G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> COSH =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.DBL_MAT, G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> SINHANDCOSH = 
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember s, Float64MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> TANH =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			Float64MatrixMember s = construct();
			Float64MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> SINCH =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			Sinch.compute(G.DBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> SINCHPI =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			Sinchpi.compute(G.DBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> SIN =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			TaylorEstimateSin.compute(18, G.DBL_MAT, G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> COS =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			TaylorEstimateCos.compute(18, G.DBL_MAT, G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> TAN =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			Float64MatrixMember s = construct();
			Float64MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> SINANDCOS =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember s, Float64MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> SINC =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			Sinc.compute(G.DBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> SINCPI =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			Sincpi.compute(G.DBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> EXP =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			TaylorEstimateExp.compute(35, G.DBL_MAT, G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<Float64MatrixMember, Float64MatrixMember> LOG =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			TaylorEstimateLog.compute(8, G.DBL_MAT, G.DBL, a, b);
		}
	};

	@Override
	public Procedure2<Float64MatrixMember, Float64MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, Float64MatrixMember> ISZERO =
			new Function1<Boolean, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a) {
			return SequenceIsZero.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<Float64MatrixMember> PI =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			Float64Member pi = G.DBL.construct();
			G.DBL.PI().call(pi);
			MatrixConstantDiagonal.compute(G.DBL, pi, a);
		}
	};

	@Override
	public Procedure1<Float64MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<Float64MatrixMember> E =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			Float64Member e = G.DBL.construct();
			G.DBL.E().call(e);
			MatrixConstantDiagonal.compute(G.DBL, e, a);
		}
	};

	@Override
	public Procedure1<Float64MatrixMember> E() {
		return E;
	}

	private final Procedure1<Float64MatrixMember> PHI =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			Float64Member phi = G.DBL.construct();
			G.DBL.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.DBL, phi, a);
		}
	};

	@Override
	public Procedure1<Float64MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<Float64MatrixMember> GAMMA =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			Float64Member gamma = G.DBL.construct();
			G.DBL.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.DBL, gamma, a);
		}
	};

	@Override
	public Procedure1<Float64MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, Float64MatrixMember, Float64MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, Float64MatrixMember, Float64MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float64MatrixMember, Float64MatrixMember> SBR =
			new Procedure3<RationalMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(RationalMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixScaleByRational.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, Float64MatrixMember, Float64MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float64MatrixMember, Float64MatrixMember> SBD =
			new Procedure3<Double, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Double a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixScaleByDouble.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, Float64MatrixMember, Float64MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, Float64MatrixMember, Float64MatrixMember> WITHIN =
			new Function3<Boolean, Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64Member tol, Float64MatrixMember a, Float64MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.DBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Float64MatrixMember, Float64MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> ADDS =
			new Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64MatrixMember a, Float64MatrixMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> SUBS =
			new Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64MatrixMember a, Float64MatrixMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> MULS =
			new Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64MatrixMember a, Float64MatrixMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> DIVS =
			new Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64MatrixMember a, Float64MatrixMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64MatrixMember, Float64MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> MULTELEM =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			Transform3.compute(G.DBL, G.DBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> DIVELEM =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			Transform3.compute(G.DBL, G.DBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, Float64MatrixMember, Float64MatrixMember> SCB2 =
			new Procedure3<Integer, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, Float64MatrixMember a, Float64MatrixMember b) {
			Float64Member factor = new Float64Member(2);
			Float64MatrixMember prod = G.DBL_MAT.construct(a);
			for (int i = 0; i < numTimes; i++) {
				scale().call(factor, prod, prod);
			}
			G.DBL_MAT.assign().call(prod, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64MatrixMember, Float64MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float64MatrixMember, Float64MatrixMember> SCBH =
			new Procedure3<Integer, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, Float64MatrixMember a, Float64MatrixMember b) {
			Float64Member factor = new Float64Member(0.5);
			Float64MatrixMember prod = G.DBL_MAT.construct(a);
			for (int i = 0; i < numTimes; i++) {
				scale().call(factor, prod, prod);
			}
			G.DBL_MAT.assign().call(prod, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64MatrixMember, Float64MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

}
