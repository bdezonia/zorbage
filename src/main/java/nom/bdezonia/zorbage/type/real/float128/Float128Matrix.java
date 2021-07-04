/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.real.float128;

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
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

import java.lang.Integer;
import java.math.BigDecimal;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float128Matrix
	implements
		RingWithUnity<Float128Matrix, Float128MatrixMember>,
		MatrixRing<Float128Matrix, Float128MatrixMember, Float128Algebra, Float128Member>,
		Constructible2dLong<Float128MatrixMember>,
		Rounding<Float128Member, Float128MatrixMember>,
		Norm<Float128MatrixMember,Float128Member>,
		DirectProduct<Float128MatrixMember, Float128MatrixMember>,
		Exponential<Float128MatrixMember>,
		Trigonometric<Float128MatrixMember>,
		Hyperbolic<Float128MatrixMember>,
		RealConstants<Float128MatrixMember>,
		Infinite<Float128MatrixMember>,
		NaN<Float128MatrixMember>,
		ScaleByHighPrec<Float128MatrixMember>,
		ScaleByRational<Float128MatrixMember>,
		ScaleByDouble<Float128MatrixMember>,
		ScaleByOneHalf<Float128MatrixMember>,
		ScaleByTwo<Float128MatrixMember>,
		Tolerance<Float128Member,Float128MatrixMember>,
		ArrayLikeMethods<Float128MatrixMember,Float128Member>
{
	public Float128Matrix() { }

	@Override
	public Float128MatrixMember construct() {
		return new Float128MatrixMember();
	}

	@Override
	public Float128MatrixMember construct(Float128MatrixMember other) {
		return new Float128MatrixMember(other);
	}

	@Override
	public Float128MatrixMember construct(String s) {
		return new Float128MatrixMember(s);
	}

	@Override
	public Float128MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new Float128MatrixMember(s, d1, d2);
	}

	private final Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> MUL =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixMultiply.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float128MatrixMember,Float128MatrixMember> POWER =
			new Procedure3<Integer, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Integer power, Float128MatrixMember a, Float128MatrixMember b) {
			MatrixPower.compute(power, G.QUAD, G.QUAD_VEC, G.QUAD_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float128MatrixMember,Float128MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<Float128MatrixMember> ZER =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float128MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<Float128MatrixMember,Float128MatrixMember> NEG =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			MatrixNegate.compute(G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> ADD =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixAddition.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> SUB =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixSubtraction.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float128MatrixMember,Float128MatrixMember> EQ =
			new Function2<Boolean, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128MatrixMember a, Float128MatrixMember b) {
			return MatrixEqual.compute(G.QUAD, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float128MatrixMember,Float128MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float128MatrixMember,Float128MatrixMember> NEQ =
			new Function2<Boolean, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128MatrixMember a, Float128MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float128MatrixMember,Float128MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float128MatrixMember,Float128MatrixMember> ASSIGN =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember from, Float128MatrixMember to) {
			MatrixAssign.compute(G.QUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128MatrixMember> assign() {
		return ASSIGN;
	}
	
	private final Procedure2<Float128MatrixMember,Float128Member> NORM =
			new Procedure2<Float128MatrixMember, Float128Member>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128Member b) {
			MatrixSpectralNorm.compute(G.QUAD_MAT, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float128Member,Float128MatrixMember,Float128MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, Float128MatrixMember a, Float128MatrixMember b) {
			MatrixRound.compute(G.QUAD, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,Float128MatrixMember,Float128MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,Float128MatrixMember> ISNAN =
			new Function1<Boolean, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128MatrixMember a) {
			return SequenceIsNan.compute(G.QUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float128MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float128MatrixMember> NAN =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			FillNaN.compute(G.QUAD, a);
		}
	};

	@Override
	public Procedure1<Float128MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,Float128MatrixMember> ISINF =
			new Function1<Boolean, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128MatrixMember a) {
			return SequenceIsInf.compute(G.QUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float128MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float128MatrixMember> INF =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			FillInfinite.compute(G.QUAD, a);
		}
	};
	
	@Override
	public Procedure1<Float128MatrixMember> infinite() {
		return INF;
	}

	@Override
	public Procedure2<Float128MatrixMember,Float128MatrixMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure2<Float128MatrixMember,Float128MatrixMember> TRANSP =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			MatrixTranspose.compute(G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<Float128MatrixMember,Float128MatrixMember> CONJTRANSP =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			transpose().call(a, b);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<Float128MatrixMember,Float128Member> DET =
			new Procedure2<Float128MatrixMember, Float128Member>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128Member b) {
			MatrixDeterminant.compute(G.QUAD_MAT, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128Member> det() {
		return DET;
	}

	private final Procedure1<Float128MatrixMember> UNITY =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			MatrixUnity.compute(G.QUAD, a);
		}
	};
	
	@Override
	public Procedure1<Float128MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<Float128MatrixMember,Float128MatrixMember> INV =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			MatrixInvert.compute(G.QUAD, G.QUAD_VEC, G.QUAD_MAT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128MatrixMember,Float128MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> DIVIDE =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b, Float128MatrixMember c) {
			// invert and multiply
			Float128MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> DP =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember in1, Float128MatrixMember in2, Float128MatrixMember out) {
			MatrixDirectProduct.compute(G.QUAD, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember,Float128MatrixMember,Float128MatrixMember> directProduct() {
		return DP;
	}

	private final Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> SCALE =
			new Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128Member a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixScale.compute(G.QUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> SINH =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.QUAD_MAT, G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> COSH =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.QUAD_MAT, G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> SINHANDCOSH = 
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember s, Float128MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> TANH =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			Float128MatrixMember s = construct();
			Float128MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> SINCH =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			Sinch.compute(G.QUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> SINCHPI =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			Sinchpi.compute(G.QUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> SIN =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			TaylorEstimateSin.compute(18, G.QUAD_MAT, G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> COS =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			TaylorEstimateCos.compute(18, G.QUAD_MAT, G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> TAN =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			Float128MatrixMember s = construct();
			Float128MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> SINANDCOS =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember s, Float128MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> SINC =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			Sinc.compute(G.QUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> SINCPI =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			Sincpi.compute(G.QUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> EXP =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			TaylorEstimateExp.compute(35, G.QUAD_MAT, G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<Float128MatrixMember, Float128MatrixMember> LOG =
			new Procedure2<Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b) {
			TaylorEstimateLog.compute(8, G.QUAD_MAT, G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128MatrixMember, Float128MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, Float128MatrixMember> ISZERO =
			new Function1<Boolean, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128MatrixMember a) {
			return SequenceIsZero.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<Float128MatrixMember> PI =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			Float128Member pi = G.QUAD.construct();
			G.QUAD.PI().call(pi);
			MatrixConstantDiagonal.compute(G.QUAD, pi, a);
		}
	};

	@Override
	public Procedure1<Float128MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<Float128MatrixMember> E =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			Float128Member e = G.QUAD.construct();
			G.QUAD.E().call(e);
			MatrixConstantDiagonal.compute(G.QUAD, e, a);
		}
	};

	@Override
	public Procedure1<Float128MatrixMember> E() {
		return E;
	}

	private final Procedure1<Float128MatrixMember> PHI =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			Float128Member phi = G.QUAD.construct();
			G.QUAD.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.QUAD, phi, a);
		}
	};

	@Override
	public Procedure1<Float128MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<Float128MatrixMember> GAMMA =
			new Procedure1<Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a) {
			Float128Member gamma = G.QUAD.construct();
			G.QUAD.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.QUAD, gamma, a);
		}
	};

	@Override
	public Procedure1<Float128MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, Float128MatrixMember, Float128MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, Float128MatrixMember, Float128MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float128MatrixMember, Float128MatrixMember> SBR =
			new Procedure3<RationalMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(RationalMember a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixScaleByRational.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, Float128MatrixMember, Float128MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float128MatrixMember, Float128MatrixMember> SBD =
			new Procedure3<Double, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Double a, Float128MatrixMember b, Float128MatrixMember c) {
			MatrixScaleByDouble.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, Float128MatrixMember, Float128MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, Float128MatrixMember, Float128MatrixMember> WITHIN =
			new Function3<Boolean, Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128Member tol, Float128MatrixMember a, Float128MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.QUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, Float128MatrixMember, Float128MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> ADDS =
			new Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128MatrixMember a, Float128MatrixMember b) {
			FixedTransform2b.compute(G.QUAD, scalar, G.QUAD.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> SUBS =
			new Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128MatrixMember a, Float128MatrixMember b) {
			FixedTransform2b.compute(G.QUAD, scalar, G.QUAD.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> MULS =
			new Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128MatrixMember a, Float128MatrixMember b) {
			FixedTransform2b.compute(G.QUAD, scalar, G.QUAD.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> DIVS =
			new Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128MatrixMember a, Float128MatrixMember b) {
			FixedTransform2b.compute(G.QUAD, scalar, G.QUAD.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128MatrixMember, Float128MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> MULTELEM =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b, Float128MatrixMember c) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				throw new IllegalArgumentException("multiplyElements() requires similarly sized matrices");
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> DIVELEM =
			new Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128MatrixMember a, Float128MatrixMember b, Float128MatrixMember c) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				throw new IllegalArgumentException("multiplyElements() requires similarly sized matrices");
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.QUAD, G.QUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128MatrixMember, Float128MatrixMember, Float128MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, Float128MatrixMember, Float128MatrixMember> SCB2 =
			new Procedure3<Integer, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, Float128MatrixMember a, Float128MatrixMember b) {
			ScaleHelper.compute(G.QUAD_MAT, G.QUAD, new Float128Member(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128MatrixMember, Float128MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float128MatrixMember, Float128MatrixMember> SCBH =
			new Procedure3<Integer, Float128MatrixMember, Float128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, Float128MatrixMember a, Float128MatrixMember b) {
			ScaleHelper.compute(G.QUAD_MAT, G.QUAD, new Float128Member(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128MatrixMember, Float128MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, Float128MatrixMember> ISUNITY =
			new Function1<Boolean, Float128MatrixMember>()
	{
		@Override
		public Boolean call(Float128MatrixMember a) {
			return MatrixIsUnity.compute(G.QUAD, a);
		}
	};

	@Override
	public Function1<Boolean, Float128MatrixMember> isUnity() {
		return ISUNITY;
	}
}
