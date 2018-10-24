/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInfinite;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixIsInfinite;
import nom.bdezonia.zorbage.algorithm.MatrixIsNaN;
import nom.bdezonia.zorbage.algorithm.MatrixIsZero;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNaN;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixScale;
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCos;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCosh;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateExp;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateLog;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSin;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSinh;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64Matrix
	implements
		RingWithUnity<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember>,
		MatrixRing<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember, QuaternionFloat64Group, QuaternionFloat64Member>,
		Constructible2dLong<QuaternionFloat64MatrixMember>,
		Rounding<Float64Member, QuaternionFloat64MatrixMember>,
		Norm<QuaternionFloat64MatrixMember,Float64Member>,
		DirectProduct<QuaternionFloat64MatrixMember, QuaternionFloat64MatrixMember>,
		Exponential<QuaternionFloat64MatrixMember>,
		Trigonometric<QuaternionFloat64MatrixMember>,
		Hyperbolic<QuaternionFloat64MatrixMember>
{
	public QuaternionFloat64Matrix() { }

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
			MatrixZero.compute(G.QDBL, a);
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

	private Procedure2<QuaternionFloat64MatrixMember,Float64Member> NORM =
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
			return MatrixIsNaN.compute(G.QDBL, a);
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
			MatrixNaN.compute(G.QDBL, a);
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
			return MatrixIsInfinite.compute(G.QDBL, a);
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
			MatrixInfinite.compute(G.QDBL, a);
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
			QuaternionFloat64MatrixMember tmp = new QuaternionFloat64MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
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
			QuaternionFloat64MatrixMember s = G.QDBL_MAT.construct();
			QuaternionFloat64MatrixMember c = G.QDBL_MAT.construct();
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
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			QuaternionFloat64MatrixMember sinha = G.QDBL_MAT.construct();
			sinh().call(a, sinha);
			divide().call(sinha, a, b);
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
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			QuaternionFloat64Member pi = G.QDBL.construct();
			G.QDBL.PI().call(pi);
			QuaternionFloat64MatrixMember sinha = G.QDBL_MAT.construct();
			sinh().call(a, sinha);
			scale().call(pi, sinha, sinha);
			QuaternionFloat64MatrixMember pi_a = G.QDBL_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sinha, pi_a, b);
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
			QuaternionFloat64MatrixMember s = G.QDBL_MAT.construct();
			QuaternionFloat64MatrixMember c = G.QDBL_MAT.construct();
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
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			QuaternionFloat64MatrixMember sina = G.QDBL_MAT.construct();
			sin().call(a, sina);
			divide().call(sina, a, b);
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
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			QuaternionFloat64Member pi = G.QDBL.construct();
			G.QDBL.PI().call(pi);
			QuaternionFloat64MatrixMember sina = G.QDBL_MAT.construct();
			sin().call(a, sina);
			scale().call(pi, sina, sina);
			QuaternionFloat64MatrixMember pi_a = G.QDBL_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sina, pi_a, b);
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
			return MatrixIsZero.compute(G.QDBL, a);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64MatrixMember> isZero() {
		return ISZERO;
	}
}
