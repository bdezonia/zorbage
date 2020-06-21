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
package nom.bdezonia.zorbage.type.highprec.complex;

import nom.bdezonia.zorbage.algebra.*;
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
import nom.bdezonia.zorbage.algorithm.MatrixScale;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByDouble;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.MatrixScaleByRational;
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
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
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

import java.lang.Integer;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexHighPrecisionMatrix
	implements
		RingWithUnity<ComplexHighPrecisionMatrix, ComplexHighPrecisionMatrixMember>,
		MatrixRing<ComplexHighPrecisionMatrix, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionAlgebra, ComplexHighPrecisionMember>,
		Constructible2dLong<ComplexHighPrecisionMatrixMember>,
		Norm<ComplexHighPrecisionMatrixMember,HighPrecisionMember>,
		DirectProduct<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>,
		Exponential<ComplexHighPrecisionMatrixMember>,
		Trigonometric<ComplexHighPrecisionMatrixMember>,
		Hyperbolic<ComplexHighPrecisionMatrixMember>,
		RealConstants<ComplexHighPrecisionMatrixMember>,
		ScaleByHighPrec<ComplexHighPrecisionMatrixMember>,
		ScaleByRational<ComplexHighPrecisionMatrixMember>,
		ScaleByDouble<ComplexHighPrecisionMatrixMember>,
		Tolerance<HighPrecisionMember,ComplexHighPrecisionMatrixMember>,
		ArrayLikeMethods<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMember>
{
	public ComplexHighPrecisionMatrix() { }

	private final Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> MUL =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixMultiply.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> POWER =
			new Procedure3<Integer, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		
		@Override
		public void call(Integer power, ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			MatrixPower.compute(power, G.CHP, G.CHP_VEC, G.CHP_MAT, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexHighPrecisionMatrixMember> ZER =
			new Procedure1<ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionMatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> NEG =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			MatrixNegate.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> ADD =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixAddition.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> SUB =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixSubtraction.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> EQ =
			new Function2<Boolean, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			return MatrixEqual.compute(G.CHP, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> NEQ =
			new Function2<Boolean, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember from, ComplexHighPrecisionMatrixMember to) {
			MatrixAssign.compute(G.CHP, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public ComplexHighPrecisionMatrixMember construct() {
		return new ComplexHighPrecisionMatrixMember();
	}

	@Override
	public ComplexHighPrecisionMatrixMember construct(ComplexHighPrecisionMatrixMember other) {
		return new ComplexHighPrecisionMatrixMember(other);
	}

	@Override
	public ComplexHighPrecisionMatrixMember construct(String s) {
		return new ComplexHighPrecisionMatrixMember(s);
	}

	@Override
	public ComplexHighPrecisionMatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexHighPrecisionMatrixMember(s, d1, d2);
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionMatrixMember, HighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, HighPrecisionMember b) {
			MatrixSpectralNorm.compute(G.CHP_MAT, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> CONJ =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			MatrixConjugate.compute(G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> TRANSP =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			MatrixTranspose.compute(G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> CONJTRANSP =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			MatrixConjugateTranspose.compute(G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMember> DET =
			new Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMember>()
	{				
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMember b) {
			MatrixDeterminant.compute(G.CHP_MAT, G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMember> det() {
		return DET;
	}

	private final Procedure1<ComplexHighPrecisionMatrixMember> UNITY =
			new Procedure1<ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a) {
			MatrixUnity.compute(G.CHP, a);
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionMatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> INV =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			MatrixInvert.compute(G.CHP, G.CHP_VEC, G.CHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> DIVIDE =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			// invert and multiply
			ComplexHighPrecisionMatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> DP =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember in1, ComplexHighPrecisionMatrixMember in2, ComplexHighPrecisionMatrixMember out) {
			MatrixDirectProduct.compute(G.CHP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember,ComplexHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SCALE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixScale.compute(G.CHP, a, b, c);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINH =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			TaylorEstimateSinh.compute(18, G.CHP_MAT, G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> COSH =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			TaylorEstimateCosh.compute(18, G.CHP_MAT, G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINHANDCOSH = 
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember s, ComplexHighPrecisionMatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> TANH =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			ComplexHighPrecisionMatrixMember s = G.CHP_MAT.construct();
			ComplexHighPrecisionMatrixMember c = G.CHP_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINCH =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			Sinch.compute(G.CHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINCHPI =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			Sinchpi.compute(G.CHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SIN =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			TaylorEstimateSin.compute(18, G.CHP_MAT, G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> COS =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			TaylorEstimateCos.compute(18, G.CHP_MAT, G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> cos() {
		return COS;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> TAN =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			ComplexHighPrecisionMatrixMember s = G.CHP_MAT.construct();
			ComplexHighPrecisionMatrixMember c = G.CHP_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINANDCOS =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember s, ComplexHighPrecisionMatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINC =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			Sinc.compute(G.CHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SINCPI =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			Sincpi.compute(G.CHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> EXP =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			TaylorEstimateExp.compute(35, G.CHP_MAT, G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> LOG =
			new Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			TaylorEstimateLog.compute(8, G.CHP_MAT, G.CHP, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, ComplexHighPrecisionMatrixMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMatrixMember a) {
			return SequenceIsZero.compute(G.CHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionMatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<ComplexHighPrecisionMatrixMember> PI =
			new Procedure1<ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a) {
			ComplexHighPrecisionMember pi = G.CHP.construct();
			G.CHP.PI().call(pi);
			MatrixConstantDiagonal.compute(G.CHP, pi, a);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMatrixMember> PI() {
		return PI;
	}

	private final Procedure1<ComplexHighPrecisionMatrixMember> E =
			new Procedure1<ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a) {
			ComplexHighPrecisionMember e = G.CHP.construct();
			G.CHP.E().call(e);
			MatrixConstantDiagonal.compute(G.CHP, e, a);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMatrixMember> E() {
		return E;
	}

	private final Procedure1<ComplexHighPrecisionMatrixMember> PHI =
			new Procedure1<ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a) {
			ComplexHighPrecisionMember phi = G.CHP.construct();
			G.CHP.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.CHP, phi, a);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<ComplexHighPrecisionMatrixMember> GAMMA =
			new Procedure1<ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a) {
			ComplexHighPrecisionMember gamma = G.CHP.construct();
			G.CHP.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.CHP, gamma, a);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixScaleByHighPrec.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SBR =
			new Procedure3<RationalMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(RationalMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixScaleByRational.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SBD =
			new Procedure3<Double, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(Double a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			MatrixScaleByDouble.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.CHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> ADDS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.CHP, scalar, G.CHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> SUBS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.CHP, scalar, G.CHP.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> MULS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.CHP, scalar, G.CHP.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> DIVS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.CHP, scalar, G.CHP.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> MULTELEM =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			Transform3.compute(G.CHP, G.CHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> DIVELEM =
			new Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMatrixMember a, ComplexHighPrecisionMatrixMember b, ComplexHighPrecisionMatrixMember c) {
			Transform3.compute(G.CHP, G.CHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember, ComplexHighPrecisionMatrixMember> divideElements() {
		return DIVELEM;
	}
}
