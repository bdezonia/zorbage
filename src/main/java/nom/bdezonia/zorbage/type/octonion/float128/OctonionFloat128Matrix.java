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
package nom.bdezonia.zorbage.type.octonion.float128;

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
public class OctonionFloat128Matrix
	implements
		RingWithUnity<OctonionFloat128Matrix, OctonionFloat128MatrixMember>,
		MatrixRing<OctonionFloat128Matrix, OctonionFloat128MatrixMember, OctonionFloat128Algebra, OctonionFloat128Member>,
		Constructible2dLong<OctonionFloat128MatrixMember>,
		Rounding<Float128Member, OctonionFloat128MatrixMember>,
		Norm<OctonionFloat128MatrixMember,Float128Member>,
		DirectProduct<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember>,
		Exponential<OctonionFloat128MatrixMember>,
		Trigonometric<OctonionFloat128MatrixMember>,
		Hyperbolic<OctonionFloat128MatrixMember>,
		RealConstants<OctonionFloat128MatrixMember>,
		Infinite<OctonionFloat128MatrixMember>,
		NaN<OctonionFloat128MatrixMember>,
		ScaleByHighPrec<OctonionFloat128MatrixMember>,
		ScaleByRational<OctonionFloat128MatrixMember>,
		ScaleByDouble<OctonionFloat128MatrixMember>,
		ScaleByOneHalf<OctonionFloat128MatrixMember>,
		ScaleByTwo<OctonionFloat128MatrixMember>,
		Tolerance<Float128Member,OctonionFloat128MatrixMember>,
		ArrayLikeMethods<OctonionFloat128MatrixMember,OctonionFloat128Member>
{
	@Override
	public String typeDescription() {
		return "128-bit based octonion matrix";
	}

	public OctonionFloat128Matrix() { }

	@Override
	public OctonionFloat128MatrixMember construct() {
		return new OctonionFloat128MatrixMember();
	}

	@Override
	public OctonionFloat128MatrixMember construct(OctonionFloat128MatrixMember other) {
		return new OctonionFloat128MatrixMember(other);
	}

	@Override
	public OctonionFloat128MatrixMember construct(String s) {
		return new OctonionFloat128MatrixMember(s);
	}

	@Override
	public OctonionFloat128MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionFloat128MatrixMember(s, d1, d2);
	}

	private final Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> MUL =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixMultiply.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixPower.compute(power, G.OQUAD, G.OQUAD_RMOD, G.OQUAD_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat128MatrixMember> ZER =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> NEG =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixNegate.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> ADD =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixAddition.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> SUB =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixSubtraction.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> EQ =
			new Function2<Boolean, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			return MatrixEqual.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> NEQ =
			new Function2<Boolean, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> ASSIGN =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember from, OctonionFloat128MatrixMember to) {
			MatrixAssign.compute(G.OQUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat128MatrixMember,Float128Member> NORM =
			new Procedure2<OctonionFloat128MatrixMember, Float128Member>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, Float128Member b) {
			MatrixSpectralNorm.compute(G.OQUAD_MAT, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float128Member,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixRound.compute(G.OQUAD, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,OctonionFloat128MatrixMember> ISNAN =
			new Function1<Boolean, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat128MatrixMember a) {
			return SequenceIsNan.compute(G.OQUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat128MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat128MatrixMember> NAN =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			FillNaN.compute(G.OQUAD, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat128MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,OctonionFloat128MatrixMember> ISINF =
			new Function1<Boolean, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat128MatrixMember a) {
			return SequenceIsInf.compute(G.OQUAD, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat128MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat128MatrixMember> INF =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			FillInfinite.compute(G.OQUAD, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> CONJ =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixConjugate.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> TRANSP =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixTranspose.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> CONJTRANSP =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixConjugateTranspose.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO - test

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128Member> DET =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128Member b) {
			MatrixDeterminant.compute(G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128MatrixMember,OctonionFloat128Member> det() {
		return DET;
	}

	private final Procedure1<OctonionFloat128MatrixMember> UNITY =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			MatrixUnity.compute(G.OQUAD, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> INVERT = 
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			MatrixInvert.compute(G.OQUAD, G.OQUAD_RMOD, G.OQUAD_MAT, a, b);
		}
	};
	
	@Override
	public final Procedure2<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> invert() {
		return INVERT;
	}

	private final Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> DIVIDE =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			// invert and multiply
			OctonionFloat128MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> DP =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember in1, OctonionFloat128MatrixMember in2, OctonionFloat128MatrixMember out) {
			MatrixDirectProduct.compute(G.OQUAD, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember,OctonionFloat128MatrixMember,OctonionFloat128MatrixMember> directProduct()
	{
		return DP;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SCALE =
			new Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixScale.compute(G.OQUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINH =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> COSH =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINHANDCOSH = 
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember s, OctonionFloat128MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> TANH =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			OctonionFloat128MatrixMember s = construct();
			OctonionFloat128MatrixMember c = construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINCH =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			Sinch.compute(G.OQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINCHPI =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			Sinchpi.compute(G.OQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SIN =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			TaylorEstimateSin.compute(18, G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> COS =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			TaylorEstimateCos.compute(18, G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> TAN =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			OctonionFloat128MatrixMember s = construct();
			OctonionFloat128MatrixMember c = construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINANDCOS =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember s, OctonionFloat128MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINC =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			Sinc.compute(G.OQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SINCPI =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			Sincpi.compute(G.OQUAD_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> EXP =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			TaylorEstimateExp.compute(35, G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> LOG =
			new Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			TaylorEstimateLog.compute(8, G.OQUAD_MAT, G.OQUAD, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, OctonionFloat128MatrixMember> ISZERO =
			new Function1<Boolean, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat128MatrixMember a) {
			return SequenceIsZero.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<OctonionFloat128MatrixMember> PI =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			OctonionFloat128Member pi = G.OQUAD.construct();
			G.OQUAD.PI().call(pi);
			MatrixConstantDiagonal.compute(G.OQUAD, pi, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat128MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<OctonionFloat128MatrixMember> E =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			OctonionFloat128Member e = G.OQUAD.construct();
			G.OQUAD.E().call(e);
			MatrixConstantDiagonal.compute(G.OQUAD, e, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat128MatrixMember> E() {
		return E;
	}

	private final Procedure1<OctonionFloat128MatrixMember> PHI =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			OctonionFloat128Member phi = G.OQUAD.construct();
			G.OQUAD.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.OQUAD, phi, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat128MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<OctonionFloat128MatrixMember> GAMMA =
			new Procedure1<OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a) {
			OctonionFloat128Member gamma = G.OQUAD.construct();
			G.OQUAD.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.OQUAD, gamma, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat128MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SBR =
			new Procedure3<RationalMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixScaleByRational.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SBD =
			new Procedure3<Double, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(Double a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			MatrixScaleByDouble.compute(G.OQUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> WITHIN =
			new Function3<Boolean, Float128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(Float128Member tol, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.OQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> ADDS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.OQUAD, scalar, G.OQUAD.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SUBS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.OQUAD, scalar, G.OQUAD.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> MULS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.OQUAD, scalar, G.OQUAD.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> DIVS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			FixedTransform2b.compute(G.OQUAD, scalar, G.OQUAD.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> MULTELEM =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.OQUAD, G.OQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> DIVELEM =
			new Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b, OctonionFloat128MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.OQUAD, G.OQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128MatrixMember, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SCB2 =
			new Procedure3<Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			ScaleHelper.compute(G.OQUAD_MAT, G.OQUAD, new OctonionFloat128Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> SCBH =
			new Procedure3<Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat128MatrixMember a, OctonionFloat128MatrixMember b) {
			ScaleHelper.compute(G.OQUAD_MAT, G.OQUAD, new OctonionFloat128Member(0.5, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat128MatrixMember, OctonionFloat128MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, OctonionFloat128MatrixMember> ISUNITY =
			new Function1<Boolean, OctonionFloat128MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat128MatrixMember a) {
			return MatrixIsUnity.compute(G.OQUAD, a);
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128MatrixMember> isUnity() {
		return ISUNITY;
	}
}
