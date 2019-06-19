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
package nom.bdezonia.zorbage.type.data.float32.real;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float32Matrix
	implements
		RingWithUnity<Float32Matrix, Float32MatrixMember>,
		MatrixRing<Float32Matrix, Float32MatrixMember, Float32Algebra, Float32Member>,
		Constructible2dLong<Float32MatrixMember>,
		Rounding<Float32Member, Float32MatrixMember>,
		Norm<Float32MatrixMember,Float32Member>,
		DirectProduct<Float32MatrixMember, Float32MatrixMember>,
		Exponential<Float32MatrixMember>,
		Trigonometric<Float32MatrixMember>,
		Hyperbolic<Float32MatrixMember>,
		RealConstants<Float32MatrixMember>
{
	public Float32Matrix() { }

	private final Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> MUL =
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b, Float32MatrixMember c) {
			MatrixMultiply.compute(G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float32MatrixMember,Float32MatrixMember> POWER =
			new Procedure3<Integer, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Integer power, Float32MatrixMember a, Float32MatrixMember b) {
			MatrixPower.compute(power, G.FLT, G.FLT_VEC, G.FLT_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float32MatrixMember,Float32MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<Float32MatrixMember> ZER =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			MatrixZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<Float32MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<Float32MatrixMember,Float32MatrixMember> NEG =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			MatrixNegate.compute(G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> ADD =
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b, Float32MatrixMember c) {
			MatrixAddition.compute(G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> SUB =
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b, Float32MatrixMember c) {
			MatrixSubtraction.compute(G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float32MatrixMember,Float32MatrixMember> EQ =
			new Function2<Boolean, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public Boolean call(Float32MatrixMember a, Float32MatrixMember b) {
			return MatrixEqual.compute(G.FLT, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float32MatrixMember,Float32MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float32MatrixMember,Float32MatrixMember> NEQ =
			new Function2<Boolean, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public Boolean call(Float32MatrixMember a, Float32MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float32MatrixMember,Float32MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float32MatrixMember,Float32MatrixMember> ASSIGN =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember from, Float32MatrixMember to) {
			MatrixAssign.compute(G.FLT, from, to);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public Float32MatrixMember construct() {
		return new Float32MatrixMember();
	}

	@Override
	public Float32MatrixMember construct(Float32MatrixMember other) {
		return new Float32MatrixMember(other);
	}

	@Override
	public Float32MatrixMember construct(String s) {
		return new Float32MatrixMember(s);
	}

	@Override
	public Float32MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new Float32MatrixMember(s, d1, d2);
	}

	private final Procedure2<Float32MatrixMember,Float32Member> NORM =
			new Procedure2<Float32MatrixMember, Float32Member>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32Member b) {
			MatrixSpectralNorm.compute(G.FLT_MAT, G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float32Member,Float32MatrixMember,Float32MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float32Member, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, Float32MatrixMember a, Float32MatrixMember b) {
			MatrixRound.compute(G.FLT, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,Float32MatrixMember,Float32MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,Float32MatrixMember> ISNAN =
			new Function1<Boolean, Float32MatrixMember>()
	{
		@Override
		public Boolean call(Float32MatrixMember a) {
			return SequenceIsNan.compute(G.FLT, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float32MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float32MatrixMember> NAN =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			MatrixNaN.compute(G.FLT, a);
		}
	};

	@Override
	public Procedure1<Float32MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,Float32MatrixMember> ISINF =
			new Function1<Boolean, Float32MatrixMember>()
	{
		@Override
		public Boolean call(Float32MatrixMember a) {
			return SequenceIsInf.compute(G.FLT, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float32MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float32MatrixMember> INF =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			MatrixInfinite.compute(G.FLT, a);
		}
	};
	
	@Override
	public Procedure1<Float32MatrixMember> infinite() {
		return INF;
	}

	@Override
	public Procedure2<Float32MatrixMember,Float32MatrixMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure2<Float32MatrixMember,Float32MatrixMember> TRANSP =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			MatrixTranspose.compute(G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<Float32MatrixMember,Float32MatrixMember> CONJTRANSP =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			transpose().call(a, b);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<Float32MatrixMember,Float32Member> DET =
			new Procedure2<Float32MatrixMember, Float32Member>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32Member b) {
			MatrixDeterminant.compute(G.FLT_MAT, G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32Member> det() {
		return DET;
	}

	private final Procedure1<Float32MatrixMember> UNITY =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			MatrixUnity.compute(G.FLT, a);
		}
	};
	
	@Override
	public Procedure1<Float32MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<Float32MatrixMember,Float32MatrixMember> INV =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			MatrixInvert.compute(G.FLT, G.FLT_VEC, G.FLT_MAT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float32MatrixMember,Float32MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> DIVIDE =
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b, Float32MatrixMember c) {
			// invert and multiply
			Float32MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> DP =
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember in1, Float32MatrixMember in2, Float32MatrixMember out) {
			MatrixDirectProduct.compute(G.FLT, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float32MatrixMember,Float32MatrixMember,Float32MatrixMember> directProduct() {
		return DP;
	}

	private final Procedure3<Float32Member, Float32MatrixMember, Float32MatrixMember> SCALE =
			new Procedure3<Float32Member, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32Member a, Float32MatrixMember b, Float32MatrixMember c) {
			MatrixScale.compute(G.FLT, a, b, c);
		}
	};

	@Override
	public Procedure3<Float32Member, Float32MatrixMember, Float32MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> SINH =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.FLT_MAT, G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> COSH =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.FLT_MAT, G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember> SINHANDCOSH = 
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember s, Float32MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> TANH =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			Float32MatrixMember s = G.FLT_MAT.construct();
			Float32MatrixMember c = G.FLT_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> SINCH =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			Float32MatrixMember sinha = G.FLT_MAT.construct();
			sinh().call(a, sinha);
			divide().call(sinha, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> SINCHPI =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			Float32Member pi = G.FLT.construct();
			G.FLT.PI().call(pi);
			Float32MatrixMember sinha = G.FLT_MAT.construct();
			sinh().call(a, sinha);
			scale().call(pi, sinha, sinha);
			Float32MatrixMember pi_a = G.FLT_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sinha, pi_a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> SIN =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			TaylorEstimateSin.compute(18, G.FLT_MAT, G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> COS =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			TaylorEstimateCos.compute(18, G.FLT_MAT, G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> TAN =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			Float32MatrixMember s = G.FLT_MAT.construct();
			Float32MatrixMember c = G.FLT_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember> SINANDCOS =
			new Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember s, Float32MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<Float32MatrixMember, Float32MatrixMember, Float32MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> SINC =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			Float32MatrixMember sina = G.FLT_MAT.construct();
			sin().call(a, sina);
			divide().call(sina, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> SINCPI =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			Float32Member pi = G.FLT.construct();
			G.FLT.PI().call(pi);
			Float32MatrixMember sina = G.FLT_MAT.construct();
			sin().call(a, sina);
			scale().call(pi, sina, sina);
			Float32MatrixMember pi_a = G.FLT_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sina, pi_a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> EXP =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			TaylorEstimateExp.compute(35, G.FLT_MAT, G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<Float32MatrixMember, Float32MatrixMember> LOG =
			new Procedure2<Float32MatrixMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a, Float32MatrixMember b) {
			TaylorEstimateLog.compute(8, G.FLT_MAT, G.FLT, a, b);
		}
	};

	@Override
	public Procedure2<Float32MatrixMember, Float32MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, Float32MatrixMember> ISZERO =
			new Function1<Boolean, Float32MatrixMember>()
	{
		@Override
		public Boolean call(Float32MatrixMember a) {
			return SequenceIsZero.compute(G.FLT, a.rawData());
		}	
	};

	@Override
	public Function1<Boolean, Float32MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<Float32MatrixMember> PI =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			Float32Member zero = G.FLT.construct();
			Float32Member pi = G.FLT.construct();
			G.FLT.PI().call(pi);
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
	public Procedure1<Float32MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<Float32MatrixMember> E =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			Float32Member zero = G.FLT.construct();
			Float32Member e = G.FLT.construct();
			G.FLT.E().call(e);
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
	public Procedure1<Float32MatrixMember> E() {
		return E;
	}

	private final Procedure1<Float32MatrixMember> PHI =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			Float32Member zero = G.FLT.construct();
			Float32Member phi = G.FLT.construct();
			G.FLT.PHI().call(phi);
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
	public Procedure1<Float32MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<Float32MatrixMember> GAMMA =
			new Procedure1<Float32MatrixMember>()
	{
		@Override
		public void call(Float32MatrixMember a) {
			Float32Member zero = G.FLT.construct();
			Float32Member gamma = G.FLT.construct();
			G.FLT.GAMMA().call(gamma);
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
	public Procedure1<Float32MatrixMember> GAMMA() {
		return GAMMA;
	}
}
