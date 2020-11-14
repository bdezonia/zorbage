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
package nom.bdezonia.zorbage.type.float64.octonion;

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
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64Matrix
	implements
		RingWithUnity<OctonionFloat64Matrix, OctonionFloat64MatrixMember>,
		MatrixRing<OctonionFloat64Matrix, OctonionFloat64MatrixMember, OctonionFloat64Algebra, OctonionFloat64Member>,
		Constructible2dLong<OctonionFloat64MatrixMember>,
		Rounding<Float64Member, OctonionFloat64MatrixMember>,
		Norm<OctonionFloat64MatrixMember,Float64Member>,
		DirectProduct<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember>,
		Exponential<OctonionFloat64MatrixMember>,
		Trigonometric<OctonionFloat64MatrixMember>,
		Hyperbolic<OctonionFloat64MatrixMember>,
		RealConstants<OctonionFloat64MatrixMember>,
		Infinite<OctonionFloat64MatrixMember>,
		NaN<OctonionFloat64MatrixMember>,
		ScaleByHighPrec<OctonionFloat64MatrixMember>,
		ScaleByRational<OctonionFloat64MatrixMember>,
		ScaleByDouble<OctonionFloat64MatrixMember>,
		ScaleByOneHalf<OctonionFloat64MatrixMember>,
		ScaleByTwo<OctonionFloat64MatrixMember>,
		Tolerance<Float64Member,OctonionFloat64MatrixMember>,
		ArrayLikeMethods<OctonionFloat64MatrixMember,OctonionFloat64Member>
{
	public OctonionFloat64Matrix() { }

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> MUL =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixMultiply.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixPower.compute(power, G.ODBL, G.ODBL_RMOD, G.ODBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat64MatrixMember> ZER =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> NEG =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixNegate.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> ADD =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixAddition.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> SUB =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixSubtraction.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> EQ =
			new Function2<Boolean, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			return MatrixEqual.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> NEQ =
			new Function2<Boolean, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> ASSIGN =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember from, OctonionFloat64MatrixMember to) {
			MatrixAssign.compute(G.ODBL, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public OctonionFloat64MatrixMember construct() {
		return new OctonionFloat64MatrixMember();
	}

	@Override
	public OctonionFloat64MatrixMember construct(OctonionFloat64MatrixMember other) {
		return new OctonionFloat64MatrixMember(other);
	}

	@Override
	public OctonionFloat64MatrixMember construct(String s) {
		return new OctonionFloat64MatrixMember(s);
	}

	@Override
	public OctonionFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionFloat64MatrixMember(s, d1, d2);
	}

	private final Procedure2<OctonionFloat64MatrixMember,Float64Member> NORM =
			new Procedure2<OctonionFloat64MatrixMember, Float64Member>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, Float64Member b) {
			MatrixSpectralNorm.compute(G.ODBL_MAT, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixRound.compute(G.ODBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,OctonionFloat64MatrixMember> ISNAN =
			new Function1<Boolean, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a) {
			return SequenceIsNan.compute(G.ODBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat64MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat64MatrixMember> NAN =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			FillNaN.compute(G.ODBL, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,OctonionFloat64MatrixMember> ISINF =
			new Function1<Boolean, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a) {
			return SequenceIsInf.compute(G.ODBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat64MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat64MatrixMember> INF =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			FillInfinite.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> CONJ =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixConjugate.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> TRANSP =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixTranspose.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> CONJTRANSP =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixConjugateTranspose.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO - test

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64Member> DET =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64Member b) {
			MatrixDeterminant.compute(G.ODBL_MAT, G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64Member> det() {
		return DET;
	}

	private final Procedure1<OctonionFloat64MatrixMember> UNITY =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			MatrixUnity.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> INVERT = 
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixInvert.compute(G.ODBL, G.ODBL_RMOD, G.ODBL_MAT, a, b);
		}
	};
	
	@Override
	public final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> invert() {
		return INVERT;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> DIVIDE =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			// invert and multiply
			OctonionFloat64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> DP =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember in1, OctonionFloat64MatrixMember in2, OctonionFloat64MatrixMember out) {
			MatrixDirectProduct.compute(G.ODBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> directProduct()
	{
		return DP;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SCALE =
			new Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixScale.compute(G.ODBL, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> COSH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINHANDCOSH = 
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember s, OctonionFloat64MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> TANH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember s = construct();
			OctonionFloat64MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINCH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			Sinch.compute(G.ODBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINCHPI =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			Sinchpi.compute(G.ODBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SIN =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateSin.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> COS =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateCos.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> TAN =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember s = construct();
			OctonionFloat64MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINANDCOS =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember s, OctonionFloat64MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINC =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			Sinc.compute(G.ODBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINCPI =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			Sincpi.compute(G.ODBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> EXP =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateExp.compute(35, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> LOG =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateLog.compute(8, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, OctonionFloat64MatrixMember> ISZERO =
			new Function1<Boolean, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a) {
			return SequenceIsZero.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<OctonionFloat64MatrixMember> PI =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			OctonionFloat64Member pi = G.ODBL.construct();
			G.ODBL.PI().call(pi);
			MatrixConstantDiagonal.compute(G.ODBL, pi, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<OctonionFloat64MatrixMember> E =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			OctonionFloat64Member e = G.ODBL.construct();
			G.ODBL.E().call(e);
			MatrixConstantDiagonal.compute(G.ODBL, e, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64MatrixMember> E() {
		return E;
	}

	private final Procedure1<OctonionFloat64MatrixMember> PHI =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			OctonionFloat64Member phi = G.ODBL.construct();
			G.ODBL.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.ODBL, phi, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<OctonionFloat64MatrixMember> GAMMA =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			OctonionFloat64Member gamma = G.ODBL.construct();
			G.ODBL.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.ODBL, gamma, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SBR =
			new Procedure3<RationalMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixScaleByRational.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SBD =
			new Procedure3<Double, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(Double a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixScaleByDouble.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> WITHIN =
			new Function3<Boolean, Float64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(Float64Member tol, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.ODBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> ADDS =
			new Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			FixedTransform2b.compute(G.ODBL, scalar, G.ODBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SUBS =
			new Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			FixedTransform2b.compute(G.ODBL, scalar, G.ODBL.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> MULS =
			new Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			FixedTransform2b.compute(G.ODBL, scalar, G.ODBL.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> DIVS =
			new Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			FixedTransform2b.compute(G.ODBL, scalar, G.ODBL.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> MULTELEM =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			Transform3.compute(G.ODBL, G.ODBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> DIVELEM =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			Transform3.compute(G.ODBL, G.ODBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SCB2 =
			new Procedure3<Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64Member factor = new OctonionFloat64Member(2, 0, 0, 0, 0, 0, 0, 0);
			OctonionFloat64MatrixMember prod = G.ODBL_MAT.construct(a);
			for (int i = 0; i < numTimes; i++) {
				scale().call(factor, prod, prod);
			}
			G.ODBL_MAT.assign().call(prod, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SCBH =
			new Procedure3<Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64Member factor = new OctonionFloat64Member(0.5, 0, 0, 0, 0, 0, 0, 0);
			OctonionFloat64MatrixMember prod = G.ODBL_MAT.construct(a);
			for (int i = 0; i < numTimes; i++) {
				scale().call(factor, prod, prod);
			}
			G.ODBL_MAT.assign().call(prod, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

}
