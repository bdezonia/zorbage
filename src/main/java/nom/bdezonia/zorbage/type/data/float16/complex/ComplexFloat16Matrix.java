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
package nom.bdezonia.zorbage.type.data.float16.complex;

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
import nom.bdezonia.zorbage.algorithm.MatrixScaleByDouble;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByRational;
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
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
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat16Matrix
	implements
		RingWithUnity<ComplexFloat16Matrix, ComplexFloat16MatrixMember>,
		MatrixRing<ComplexFloat16Matrix, ComplexFloat16MatrixMember, ComplexFloat16Algebra, ComplexFloat16Member>,
		Constructible2dLong<ComplexFloat16MatrixMember>,
		Rounding<Float16Member, ComplexFloat16MatrixMember>,
		Norm<ComplexFloat16MatrixMember,Float16Member>,
		DirectProduct<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>,
		Exponential<ComplexFloat16MatrixMember>,
		Trigonometric<ComplexFloat16MatrixMember>,
		Hyperbolic<ComplexFloat16MatrixMember>,
		RealConstants<ComplexFloat16MatrixMember>,
		Infinite<ComplexFloat16MatrixMember>,
		NaN<ComplexFloat16MatrixMember>,
		ScaleByHighPrec<ComplexFloat16MatrixMember>,
		ScaleByRational<ComplexFloat16MatrixMember>,
		ScaleByDouble<ComplexFloat16MatrixMember>,
		Tolerance<Float16Member,ComplexFloat16MatrixMember>
{
	public ComplexFloat16Matrix() { }

	private final Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> MUL =
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixMultiply.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> POWER =
			new Procedure3<Integer, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		
		@Override
		public void call(Integer power, ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			MatrixPower.compute(power, G.CHLF, G.CHLF_VEC, G.CHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat16MatrixMember> ZER =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			MatrixZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> NEG =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			MatrixNegate.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> ADD =
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixAddition.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> SUB =
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixSubtraction.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> EQ =
			new Function2<Boolean, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			return MatrixEqual.compute(G.CHLF, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> NEQ =
			new Function2<Boolean, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> ASSIGN =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember from, ComplexFloat16MatrixMember to) {
			MatrixAssign.compute(G.CHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public ComplexFloat16MatrixMember construct() {
		return new ComplexFloat16MatrixMember();
	}

	@Override
	public ComplexFloat16MatrixMember construct(ComplexFloat16MatrixMember other) {
		return new ComplexFloat16MatrixMember(other);
	}

	@Override
	public ComplexFloat16MatrixMember construct(String s) {
		return new ComplexFloat16MatrixMember(s);
	}

	@Override
	public ComplexFloat16MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexFloat16MatrixMember(s, d1, d2);
	}

	private final Procedure2<ComplexFloat16MatrixMember,Float16Member> NORM =
			new Procedure2<ComplexFloat16MatrixMember, Float16Member>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, Float16Member b) {
			MatrixSpectralNorm.compute(G.CHLF_MAT, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16MatrixMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float16Member,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			MatrixRound.compute(G.CHLF, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat16MatrixMember> ISNAN =
			new Function1<Boolean,ComplexFloat16MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat16MatrixMember a) {
			return SequenceIsNan.compute(G.CHLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat16MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat16MatrixMember> NAN =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			MatrixNaN.compute(G.CHLF, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,ComplexFloat16MatrixMember> ISINF =
			new Function1<Boolean,ComplexFloat16MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat16MatrixMember a) {
			return SequenceIsInf.compute(G.CHLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat16MatrixMember> isInfinite() {
		return ISINF;
	}
	
	private final Procedure1<ComplexFloat16MatrixMember> INF =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			MatrixInfinite.compute(G.CHLF, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> CONJ =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			MatrixConjugate.compute(G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> TRANSP =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			MatrixTranspose.compute(G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> CONJTRANSP =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			ComplexFloat16MatrixMember tmp = new ComplexFloat16MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16Member> DET =
			new Procedure2<ComplexFloat16MatrixMember,ComplexFloat16Member>()
	{				
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16Member b) {
			MatrixDeterminant.compute(G.CHLF_MAT, G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16Member> det() {
		return DET;
	}

	private final Procedure1<ComplexFloat16MatrixMember> UNITY =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			MatrixUnity.compute(G.CHLF, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> INV =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			MatrixInvert.compute(G.CHLF, G.CHLF_VEC, G.CHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> DIVIDE =
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			// invert and multiply
			ComplexFloat16MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> DP =
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember in1, ComplexFloat16MatrixMember in2, ComplexFloat16MatrixMember out) {
			MatrixDirectProduct.compute(G.CHLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16MatrixMember,ComplexFloat16MatrixMember,ComplexFloat16MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<ComplexFloat16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SCALE =
			new Procedure3<ComplexFloat16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixScale.compute(G.CHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINH =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.CHLF_MAT, G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> COSH =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.CHLF_MAT, G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINHANDCOSH = 
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember s, ComplexFloat16MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> TANH =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			ComplexFloat16MatrixMember s = G.CHLF_MAT.construct();
			ComplexFloat16MatrixMember c = G.CHLF_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINCH =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			Sinch.compute(G.CHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINCHPI =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			Sinchpi.compute(G.CHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SIN =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			TaylorEstimateSin.compute(18, G.CHLF_MAT, G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> COS =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			TaylorEstimateCos.compute(18, G.CHLF_MAT, G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> TAN =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			ComplexFloat16MatrixMember s = G.CHLF_MAT.construct();
			ComplexFloat16MatrixMember c = G.CHLF_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINANDCOS =
			new Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember s, ComplexFloat16MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINC =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			Sinc.compute(G.CHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SINCPI =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			Sincpi.compute(G.CHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> EXP =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			TaylorEstimateExp.compute(35, G.CHLF_MAT, G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> LOG =
			new Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			TaylorEstimateLog.compute(8, G.CHLF_MAT, G.CHLF, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, ComplexFloat16MatrixMember> ISZERO =
			new Function1<Boolean, ComplexFloat16MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat16MatrixMember a) {
			return SequenceIsZero.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<ComplexFloat16MatrixMember> PI =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			ComplexFloat16Member pi = G.CHLF.construct();
			G.CHLF.PI().call(pi);
			MatrixConstantDiagonal.compute(G.CHLF, pi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<ComplexFloat16MatrixMember> E =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			ComplexFloat16Member e = G.CHLF.construct();
			G.CHLF.E().call(e);
			MatrixConstantDiagonal.compute(G.CHLF, e, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16MatrixMember> E() {
		return E;
	}

	private final Procedure1<ComplexFloat16MatrixMember> PHI =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			ComplexFloat16Member phi = G.CHLF.construct();
			G.CHLF.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.CHLF, phi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<ComplexFloat16MatrixMember> GAMMA =
			new Procedure1<ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16MatrixMember a) {
			ComplexFloat16Member gamma = G.CHLF.construct();
			G.CHLF.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.CHLF, gamma, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16MatrixMember> GAMMA() {
		return GAMMA;
	}

	private Procedure3<HighPrecisionMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private Procedure3<RationalMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SBR =
			new Procedure3<RationalMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixScaleByRational.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> scaleByRational() {
		return SBR;
	}

	private Procedure3<Double, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> SBD =
			new Procedure3<Double, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(Double a, ComplexFloat16MatrixMember b, ComplexFloat16MatrixMember c) {
			MatrixScaleByDouble.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> WITHIN =
			new Function3<Boolean, Float16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public Boolean call(Float16Member tol, ComplexFloat16MatrixMember a, ComplexFloat16MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.CHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, ComplexFloat16MatrixMember, ComplexFloat16MatrixMember> within() {
		return WITHIN;
	}
}
