/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.complex;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
import nom.bdezonia.zorbage.algorithm.MatrixConstantDiagonal;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInfinite;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNaN;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixScale;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByRational;
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Round;
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
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Matrix
	implements
		RingWithUnity<ComplexFloat64Matrix, ComplexFloat64MatrixMember>,
		MatrixRing<ComplexFloat64Matrix, ComplexFloat64MatrixMember, ComplexFloat64Algebra, ComplexFloat64Member>,
		Constructible2dLong<ComplexFloat64MatrixMember>,
		Rounding<Float64Member, ComplexFloat64MatrixMember>,
		Norm<ComplexFloat64MatrixMember,Float64Member>,
		DirectProduct<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>,
		Exponential<ComplexFloat64MatrixMember>,
		Trigonometric<ComplexFloat64MatrixMember>,
		Hyperbolic<ComplexFloat64MatrixMember>,
		RealConstants<ComplexFloat64MatrixMember>,
		Infinite<ComplexFloat64MatrixMember>,
		NaN<ComplexFloat64MatrixMember>,
		ScaleByHighPrec<ComplexFloat64MatrixMember>,
		ScaleByRational<ComplexFloat64MatrixMember>,
		Tolerance<Float64Member,ComplexFloat64MatrixMember>
{
	public ComplexFloat64Matrix() { }

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> MUL =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixMultiply.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> POWER =
			new Procedure3<Integer, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		
		@Override
		public void call(Integer power, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixPower.compute(power, G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat64MatrixMember> ZER =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> NEG =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixNegate.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> ADD =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixAddition.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> SUB =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixSubtraction.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> EQ =
			new Function2<Boolean, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			return MatrixEqual.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> NEQ =
			new Function2<Boolean, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> ASSIGN =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember from, ComplexFloat64MatrixMember to) {
			MatrixAssign.compute(G.CDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public ComplexFloat64MatrixMember construct() {
		return new ComplexFloat64MatrixMember();
	}

	@Override
	public ComplexFloat64MatrixMember construct(ComplexFloat64MatrixMember other) {
		return new ComplexFloat64MatrixMember(other);
	}

	@Override
	public ComplexFloat64MatrixMember construct(String s) {
		return new ComplexFloat64MatrixMember(s);
	}

	@Override
	public ComplexFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexFloat64MatrixMember(s, d1, d2);
	}

	private final Procedure2<ComplexFloat64MatrixMember,Float64Member> NORM =
			new Procedure2<ComplexFloat64MatrixMember, Float64Member>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, Float64Member b) {
			MatrixSpectralNorm.compute(G.CDBL_MAT, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixRound.compute(G.CDBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat64MatrixMember> ISNAN =
			new Function1<Boolean,ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a) {
			return SequenceIsNan.compute(G.CDBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat64MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat64MatrixMember> NAN =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixNaN.compute(G.CDBL, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,ComplexFloat64MatrixMember> ISINF =
			new Function1<Boolean,ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a) {
			return SequenceIsInf.compute(G.CDBL, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat64MatrixMember> isInfinite() {
		return ISINF;
	}
	
	private final Procedure1<ComplexFloat64MatrixMember> INF =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixInfinite.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> CONJ =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixConjugate.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> TRANSP =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixTranspose.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> CONJTRANSP =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			ComplexFloat64MatrixMember tmp = new ComplexFloat64MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64Member> DET =
			new Procedure2<ComplexFloat64MatrixMember,ComplexFloat64Member>()
	{				
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64Member b) {
			MatrixDeterminant.compute(G.CDBL_MAT, G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64Member> det() {
		return DET;
	}

	private final Procedure1<ComplexFloat64MatrixMember> UNITY =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixUnity.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> INV =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixInvert.compute(G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> DIVIDE =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			// invert and multiply
			ComplexFloat64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> DP =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember in1, ComplexFloat64MatrixMember in2, ComplexFloat64MatrixMember out) {
			MatrixDirectProduct.compute(G.CDBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<ComplexFloat64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SCALE =
			new Procedure3<ComplexFloat64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixScale.compute(G.CDBL, a, b, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINH =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.CDBL_MAT, G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> COSH =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.CDBL_MAT, G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINHANDCOSH = 
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember s, ComplexFloat64MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> TANH =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			ComplexFloat64MatrixMember s = G.CDBL_MAT.construct();
			ComplexFloat64MatrixMember c = G.CDBL_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINCH =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			Sinch.compute(G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINCHPI =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			Sinchpi.compute(G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SIN =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			TaylorEstimateSin.compute(18, G.CDBL_MAT, G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> COS =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			TaylorEstimateCos.compute(18, G.CDBL_MAT, G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> TAN =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			ComplexFloat64MatrixMember s = G.CDBL_MAT.construct();
			ComplexFloat64MatrixMember c = G.CDBL_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINANDCOS =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember s, ComplexFloat64MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINC =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			Sinc.compute(G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SINCPI =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			Sincpi.compute(G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> EXP =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			TaylorEstimateExp.compute(35, G.CDBL_MAT, G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> LOG =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			TaylorEstimateLog.compute(8, G.CDBL_MAT, G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, ComplexFloat64MatrixMember> ISZERO =
			new Function1<Boolean, ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a) {
			return SequenceIsZero.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<ComplexFloat64MatrixMember> PI =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			ComplexFloat64Member pi = G.CDBL.construct();
			G.CDBL.PI().call(pi);
			MatrixConstantDiagonal.compute(G.CDBL, pi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<ComplexFloat64MatrixMember> E =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			ComplexFloat64Member e = G.CDBL.construct();
			G.CDBL.E().call(e);
			MatrixConstantDiagonal.compute(G.CDBL, e, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64MatrixMember> E() {
		return E;
	}

	private final Procedure1<ComplexFloat64MatrixMember> PHI =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			ComplexFloat64Member phi = G.CDBL.construct();
			G.CDBL.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.CDBL, phi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<ComplexFloat64MatrixMember> GAMMA =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			ComplexFloat64Member gamma = G.CDBL.construct();
			G.CDBL.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.CDBL, gamma, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64MatrixMember> GAMMA() {
		return GAMMA;
	}

	private Procedure3<HighPrecisionMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private Procedure3<RationalMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> SBR =
			new Procedure3<RationalMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixScaleByRational.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Function3<Boolean, Float64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> WITHIN =
			new Function3<Boolean, Float64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(Float64Member tol, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.CDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember> within() {
		return WITHIN;
	}
}
