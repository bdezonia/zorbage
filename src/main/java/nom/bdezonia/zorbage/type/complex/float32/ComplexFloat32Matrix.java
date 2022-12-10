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
package nom.bdezonia.zorbage.type.complex.float32;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
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
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCos;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateCosh;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateExp;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateLog;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSin;
import nom.bdezonia.zorbage.algorithm.TaylorEstimateSinh;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
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

import java.lang.Integer;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat32Matrix
	implements
		RingWithUnity<ComplexFloat32Matrix, ComplexFloat32MatrixMember>,
		MatrixRing<ComplexFloat32Matrix, ComplexFloat32MatrixMember, ComplexFloat32Algebra, ComplexFloat32Member>,
		Constructible2dLong<ComplexFloat32MatrixMember>,
		Rounding<Float32Member, ComplexFloat32MatrixMember>,
		Norm<ComplexFloat32MatrixMember,Float32Member>,
		DirectProduct<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>,
		Exponential<ComplexFloat32MatrixMember>,
		Trigonometric<ComplexFloat32MatrixMember>,
		Hyperbolic<ComplexFloat32MatrixMember>,
		RealConstants<ComplexFloat32MatrixMember>,
		Infinite<ComplexFloat32MatrixMember>,
		NaN<ComplexFloat32MatrixMember>,
		ScaleByHighPrec<ComplexFloat32MatrixMember>,
		ScaleByRational<ComplexFloat32MatrixMember>,
		ScaleByDouble<ComplexFloat32MatrixMember>,
		ScaleByOneHalf<ComplexFloat32MatrixMember>,
		ScaleByTwo<ComplexFloat32MatrixMember>,
		Tolerance<Float32Member,ComplexFloat32MatrixMember>,
		ArrayLikeMethods<ComplexFloat32MatrixMember,ComplexFloat32Member>
{

	@Override
	public String typeDescription() {
		return "32-bit based complex matrix";
	}

	public ComplexFloat32Matrix() { }

	@Override
	public ComplexFloat32MatrixMember construct() {
		return new ComplexFloat32MatrixMember();
	}

	@Override
	public ComplexFloat32MatrixMember construct(ComplexFloat32MatrixMember other) {
		return new ComplexFloat32MatrixMember(other);
	}

	@Override
	public ComplexFloat32MatrixMember construct(String s) {
		return new ComplexFloat32MatrixMember(s);
	}

	@Override
	public ComplexFloat32MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexFloat32MatrixMember(s, d1, d2);
	}

	private final Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> MUL =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixMultiply.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> POWER =
			new Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		
		@Override
		public void call(Integer power, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixPower.compute(power, G.CFLT, G.CFLT_VEC, G.CFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat32MatrixMember> ZER =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> NEG =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixNegate.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> ADD =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixAddition.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> SUB =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixSubtraction.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> EQ =
			new Function2<Boolean, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			return MatrixEqual.compute(G.CFLT, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> NEQ =
			new Function2<Boolean, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> ASSIGN =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember from, ComplexFloat32MatrixMember to) {
			MatrixAssign.compute(G.CFLT, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat32MatrixMember,Float32Member> NORM =
			new Procedure2<ComplexFloat32MatrixMember, Float32Member>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, Float32Member b) {
			MatrixSpectralNorm.compute(G.CFLT_MAT, G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32MatrixMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float32Member,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixRound.compute(G.CFLT, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat32MatrixMember> ISNAN =
			new Function1<Boolean,ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat32MatrixMember a) {
			return SequenceIsNan.compute(G.CFLT, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat32MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat32MatrixMember> NAN =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			FillNaN.compute(G.CFLT, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,ComplexFloat32MatrixMember> ISINF =
			new Function1<Boolean,ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat32MatrixMember a) {
			return SequenceIsInf.compute(G.CFLT, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat32MatrixMember> isInfinite() {
		return ISINF;
	}
	
	private final Procedure1<ComplexFloat32MatrixMember> INF =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			FillInfinite.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> CONJ =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixConjugate.compute(G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> TRANSP =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixTranspose.compute(G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> CONJTRANSP =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixConjugateTranspose.compute(G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32Member> DET =
			new Procedure2<ComplexFloat32MatrixMember,ComplexFloat32Member>()
	{				
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32Member b) {
			MatrixDeterminant.compute(G.CFLT_MAT, G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32Member> det() {
		return DET;
	}

	private final Procedure1<ComplexFloat32MatrixMember> UNITY =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			MatrixUnity.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> INV =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			MatrixInvert.compute(G.CFLT, G.CFLT_VEC, G.CFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> DIVIDE =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			// invert and multiply
			ComplexFloat32MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> DP =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember in1, ComplexFloat32MatrixMember in2, ComplexFloat32MatrixMember out) {
			MatrixDirectProduct.compute(G.CFLT, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember,ComplexFloat32MatrixMember,ComplexFloat32MatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SCALE =
			new Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixScale.compute(G.CFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINH =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.CFLT_MAT, G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> COSH =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.CFLT_MAT, G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINHANDCOSH = 
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember s, ComplexFloat32MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> TANH =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			ComplexFloat32MatrixMember s = G.CFLT_MAT.construct();
			ComplexFloat32MatrixMember c = G.CFLT_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINCH =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			Sinch.compute(G.CFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINCHPI =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			Sinchpi.compute(G.CFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SIN =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			TaylorEstimateSin.compute(18, G.CFLT_MAT, G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> COS =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			TaylorEstimateCos.compute(18, G.CFLT_MAT, G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> TAN =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			ComplexFloat32MatrixMember s = G.CFLT_MAT.construct();
			ComplexFloat32MatrixMember c = G.CFLT_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINANDCOS =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember s, ComplexFloat32MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINC =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			Sinc.compute(G.CFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SINCPI =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			Sincpi.compute(G.CFLT_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> EXP =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			TaylorEstimateExp.compute(35, G.CFLT_MAT, G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> LOG =
			new Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			TaylorEstimateLog.compute(8, G.CFLT_MAT, G.CFLT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, ComplexFloat32MatrixMember> ISZERO =
			new Function1<Boolean, ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat32MatrixMember a) {
			return SequenceIsZero.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<ComplexFloat32MatrixMember> PI =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			ComplexFloat32Member pi = G.CFLT.construct();
			G.CFLT.PI().call(pi);
			MatrixConstantDiagonal.compute(G.CFLT, pi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<ComplexFloat32MatrixMember> E =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			ComplexFloat32Member e = G.CFLT.construct();
			G.CFLT.E().call(e);
			MatrixConstantDiagonal.compute(G.CFLT, e, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32MatrixMember> E() {
		return E;
	}

	private final Procedure1<ComplexFloat32MatrixMember> PHI =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			ComplexFloat32Member phi = G.CFLT.construct();
			G.CFLT.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.CFLT, phi, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<ComplexFloat32MatrixMember> GAMMA =
			new Procedure1<ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a) {
			ComplexFloat32Member gamma = G.CFLT.construct();
			G.CFLT.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.CFLT, gamma, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixScaleByHighPrec.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SBR =
			new Procedure3<RationalMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixScaleByRational.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SBD =
			new Procedure3<Double, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(Double a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			MatrixScaleByDouble.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> WITHIN =
			new Function3<Boolean, Float32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(Float32Member tol, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.CFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> ADDS =
			new Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.CFLT, G.CFLT.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SUBS =
			new Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.CFLT, G.CFLT.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> MULS =
			new Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.CFLT, G.CFLT.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> DIVS =
			new Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			b.alloc(a.rows(), a.cols());
			TransformWithConstant.compute(G.CFLT, G.CFLT.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> MULTELEM =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.CFLT, G.CFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> DIVELEM =
			new Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b, ComplexFloat32MatrixMember c) {
			c.alloc(a.rows(), a.cols());
			Transform3.compute(G.CFLT, G.CFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32MatrixMember, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SCB2 =
			new Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			ScaleHelper.compute(G.CFLT_MAT, G.CFLT, new ComplexFloat32Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> SCBH =
			new Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat32MatrixMember a, ComplexFloat32MatrixMember b) {
			ScaleHelper.compute(G.CFLT_MAT, G.CFLT, new ComplexFloat32Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat32MatrixMember, ComplexFloat32MatrixMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat32MatrixMember> ISUNITY =
			new Function1<Boolean, ComplexFloat32MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat32MatrixMember a) {
			return MatrixIsUnity.compute(G.CFLT, a);
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32MatrixMember> isUnity() {
		return ISUNITY;
	}
}
