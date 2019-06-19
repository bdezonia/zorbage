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
package nom.bdezonia.zorbage.type.data.float16.quaternion;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
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
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
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
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat16Matrix
	implements
		RingWithUnity<QuaternionFloat16Matrix, QuaternionFloat16MatrixMember>,
		MatrixRing<QuaternionFloat16Matrix, QuaternionFloat16MatrixMember, QuaternionFloat16Algebra, QuaternionFloat16Member>,
		Constructible2dLong<QuaternionFloat16MatrixMember>,
		Rounding<Float16Member, QuaternionFloat16MatrixMember>,
		Norm<QuaternionFloat16MatrixMember,Float16Member>,
		DirectProduct<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>,
		Exponential<QuaternionFloat16MatrixMember>,
		Trigonometric<QuaternionFloat16MatrixMember>,
		Hyperbolic<QuaternionFloat16MatrixMember>,
		RealConstants<QuaternionFloat16MatrixMember>
{
	public QuaternionFloat16Matrix() { }

	private final Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> MUL =
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b,
				QuaternionFloat16MatrixMember c)
		{
			MatrixMultiply.compute(G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> POWER =
			new Procedure3<java.lang.Integer, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat16MatrixMember a,
				QuaternionFloat16MatrixMember b)
		{
			MatrixPower.compute(power, G.QHLF, G.QHLF_RMOD, G.QHLF_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> ZER =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			MatrixZero.compute(a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat16MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> NEG =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			MatrixNegate.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> ADD =
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b,
				QuaternionFloat16MatrixMember c)
		{
			MatrixAddition.compute(G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> SUB =
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b,
				QuaternionFloat16MatrixMember c)
		{
			MatrixSubtraction.compute(G.QHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> EQ =
			new Function2<Boolean, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			return MatrixEqual.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> NEQ =
			new Function2<Boolean, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> ASSIGN =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember from, QuaternionFloat16MatrixMember to) {
			MatrixAssign.compute(G.QHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public QuaternionFloat16MatrixMember construct() {
		return new QuaternionFloat16MatrixMember();
	}

	@Override
	public QuaternionFloat16MatrixMember construct(QuaternionFloat16MatrixMember other) {
		return new QuaternionFloat16MatrixMember(other);
	}

	@Override
	public QuaternionFloat16MatrixMember construct(String s) {
		return new QuaternionFloat16MatrixMember(s);
	}

	@Override
	public QuaternionFloat16MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new QuaternionFloat16MatrixMember(s, d1, d2);
	}

	private Procedure2<QuaternionFloat16MatrixMember,Float16Member> NORM =
			new Procedure2<QuaternionFloat16MatrixMember, Float16Member>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, Float16Member b) {
			MatrixSpectralNorm.compute(G.QHLF_MAT, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float16Member,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float16Member, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			MatrixRound.compute(G.QHLF, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat16MatrixMember> ISNAN =
			new Function1<Boolean,QuaternionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16MatrixMember a) {
			return SequenceIsNan.compute(G.QHLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat16MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> NAN =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			MatrixNaN.compute(G.QHLF, a);
		}
	};

	@Override
	public Procedure1<QuaternionFloat16MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat16MatrixMember> ISINF =
			new Function1<Boolean,QuaternionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16MatrixMember a) {
			return SequenceIsInf.compute(G.QHLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat16MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> INF =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			MatrixInfinite.compute(G.QHLF, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> CONJ =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			MatrixConjugate.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> TRANSP =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			MatrixTranspose.compute(G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> CONJTRANSP =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			QuaternionFloat16MatrixMember tmp = new QuaternionFloat16MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO test

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16Member> DET =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16Member>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16Member b) {
			MatrixDeterminant.compute(G.QHLF_MAT, G.QHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16Member> det() {
		return DET;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> UNITY =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			MatrixUnity.compute(G.QHLF, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> INV =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			MatrixInvert.compute(G.QHLF, G.QHLF_RMOD, G.QHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> DIVIDE =
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b,
				QuaternionFloat16MatrixMember c)
		{
			// invert and multiply
			QuaternionFloat16MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> divide()
	{
		return DIVIDE;
	}

	private final Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> DP =
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember in1, QuaternionFloat16MatrixMember in2,
				QuaternionFloat16MatrixMember out)
		{
			MatrixDirectProduct.compute(G.QHLF, in1, in2, out);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember,QuaternionFloat16MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SCALE =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16Member a, QuaternionFloat16MatrixMember b, QuaternionFloat16MatrixMember c) {
			MatrixScale.compute(G.QHLF, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINH =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.QHLF_MAT, G.QHLF, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> COSH =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.QHLF_MAT, G.QHLF, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINHANDCOSH = 
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember s, QuaternionFloat16MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> TANH =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			QuaternionFloat16MatrixMember s = G.QHLF_MAT.construct();
			QuaternionFloat16MatrixMember c = G.QHLF_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINCH =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			Sinch.compute(G.QHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINCHPI =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			Sinchpi.compute(G.QHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SIN =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			TaylorEstimateSin.compute(18, G.QHLF_MAT, G.QHLF, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> COS =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			TaylorEstimateCos.compute(18, G.QHLF_MAT, G.QHLF, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> TAN =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			QuaternionFloat16MatrixMember s = G.QHLF_MAT.construct();
			QuaternionFloat16MatrixMember c = G.QHLF_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINANDCOS =
			new Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember s, QuaternionFloat16MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINC =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			Sinc.compute(G.QHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> SINCPI =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			Sincpi.compute(G.QHLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> EXP =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			TaylorEstimateExp.compute(35, G.QHLF_MAT, G.QHLF, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> LOG =
			new Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a, QuaternionFloat16MatrixMember b) {
			TaylorEstimateLog.compute(8, G.QHLF_MAT, G.QHLF, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16MatrixMember, QuaternionFloat16MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, QuaternionFloat16MatrixMember> ISZERO =
			new Function1<Boolean, QuaternionFloat16MatrixMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16MatrixMember a) {
			return SequenceIsZero.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> PI =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			QuaternionFloat16Member zero = G.QHLF.construct();
			QuaternionFloat16Member pi = G.QHLF.construct();
			G.QHLF.PI().call(pi);
			for (long r = 0; r < a.rows(); r++) {
				for (long c = 0; c < a.cols(); c++) {
					if (r == c)
						a.setV(r, c, pi);
					else
						a.setV(r, c, zero);
				}
			}
		}
	};

	@Override
	public Procedure1<QuaternionFloat16MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> E =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			QuaternionFloat16Member zero = G.QHLF.construct();
			QuaternionFloat16Member e = G.QHLF.construct();
			G.QHLF.E().call(e);
			for (long r = 0; r < a.rows(); r++) {
				for (long c = 0; c < a.cols(); c++) {
					if (r == c)
						a.setV(r, c, e);
					else
						a.setV(r, c, zero);
				}
			}
		}
	};

	@Override
	public Procedure1<QuaternionFloat16MatrixMember> E() {
		return E;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> PHI =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			QuaternionFloat16Member zero = G.QHLF.construct();
			QuaternionFloat16Member phi = G.QHLF.construct();
			G.QHLF.PHI().call(phi);
			for (long r = 0; r < a.rows(); r++) {
				for (long c = 0; c < a.cols(); c++) {
					if (r == c)
						a.setV(r, c, phi);
					else
						a.setV(r, c, zero);
				}
			}
		}
	};

	@Override
	public Procedure1<QuaternionFloat16MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<QuaternionFloat16MatrixMember> GAMMA =
			new Procedure1<QuaternionFloat16MatrixMember>()
	{
		@Override
		public void call(QuaternionFloat16MatrixMember a) {
			QuaternionFloat16Member zero = G.QHLF.construct();
			QuaternionFloat16Member gamma = G.QHLF.construct();
			G.QHLF.GAMMA().call(gamma);
			for (long r = 0; r < a.rows(); r++) {
				for (long c = 0; c < a.cols(); c++) {
					if (r == c)
						a.setV(r, c, gamma);
					else
						a.setV(r, c, zero);
				}
			}
		}
	};

	@Override
	public Procedure1<QuaternionFloat16MatrixMember> GAMMA() {
		return GAMMA;
	}
}
