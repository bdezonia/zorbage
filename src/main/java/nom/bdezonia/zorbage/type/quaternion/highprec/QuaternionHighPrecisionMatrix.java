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
package nom.bdezonia.zorbage.type.quaternion.highprec;

import java.lang.Integer;
import java.math.BigDecimal;

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
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
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
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionHighPrecisionMatrix
	implements
		RingWithUnity<QuaternionHighPrecisionMatrix, QuaternionHighPrecisionMatrixMember>,
		MatrixRing<QuaternionHighPrecisionMatrix, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionAlgebra, QuaternionHighPrecisionMember>,
		Constructible2dLong<QuaternionHighPrecisionMatrixMember>,
		Norm<QuaternionHighPrecisionMatrixMember,HighPrecisionMember>,
		DirectProduct<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>,
		Exponential<QuaternionHighPrecisionMatrixMember>,
		Trigonometric<QuaternionHighPrecisionMatrixMember>,
		Hyperbolic<QuaternionHighPrecisionMatrixMember>,
		RealConstants<QuaternionHighPrecisionMatrixMember>,
		ScaleByHighPrec<QuaternionHighPrecisionMatrixMember>,
		ScaleByRational<QuaternionHighPrecisionMatrixMember>,
		ScaleByDouble<QuaternionHighPrecisionMatrixMember>,
		ScaleByOneHalf<QuaternionHighPrecisionMatrixMember>,
		ScaleByTwo<QuaternionHighPrecisionMatrixMember>,
		Tolerance<HighPrecisionMember,QuaternionHighPrecisionMatrixMember>,
		ArrayLikeMethods<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMember>
{
	public QuaternionHighPrecisionMatrix() { }

	private final Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> MUL =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b,
				QuaternionHighPrecisionMatrixMember c)
		{
			MatrixMultiply.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> POWER =
			new Procedure3<java.lang.Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionHighPrecisionMatrixMember a,
				QuaternionHighPrecisionMatrixMember b)
		{
			MatrixPower.compute(power, G.QHP, G.QHP_RMOD, G.QHP_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionHighPrecisionMatrixMember> ZER =
			new Procedure1<QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<QuaternionHighPrecisionMatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> NEG =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			MatrixNegate.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> ADD =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b,
				QuaternionHighPrecisionMatrixMember c)
		{
			MatrixAddition.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> SUB =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b,
				QuaternionHighPrecisionMatrixMember c)
		{
			MatrixSubtraction.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> EQ =
			new Function2<Boolean, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			return MatrixEqual.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> NEQ =
			new Function2<Boolean, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> ASSIGN =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember from, QuaternionHighPrecisionMatrixMember to) {
			MatrixAssign.compute(G.QHP, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public QuaternionHighPrecisionMatrixMember construct() {
		return new QuaternionHighPrecisionMatrixMember();
	}

	@Override
	public QuaternionHighPrecisionMatrixMember construct(QuaternionHighPrecisionMatrixMember other) {
		return new QuaternionHighPrecisionMatrixMember(other);
	}

	@Override
	public QuaternionHighPrecisionMatrixMember construct(String s) {
		return new QuaternionHighPrecisionMatrixMember(s);
	}

	@Override
	public QuaternionHighPrecisionMatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new QuaternionHighPrecisionMatrixMember(s, d1, d2);
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,HighPrecisionMember> NORM =
			new Procedure2<QuaternionHighPrecisionMatrixMember, HighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, HighPrecisionMember b) {
			MatrixSpectralNorm.compute(G.QHP_MAT, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> CONJ =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			MatrixConjugate.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> TRANSP =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			MatrixTranspose.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> CONJTRANSP =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			MatrixConjugateTranspose.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMember> DET =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMember b) {
			MatrixDeterminant.compute(G.QHP_MAT, G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMember> det() {
		return DET;
	}

	private final Procedure1<QuaternionHighPrecisionMatrixMember> UNITY =
			new Procedure1<QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a) {
			MatrixUnity.compute(G.QHP, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> INV =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			MatrixInvert.compute(G.QHP, G.QHP_RMOD, G.QHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> invert() {
		return INV;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> DIVIDE =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b,
				QuaternionHighPrecisionMatrixMember c)
		{
			// invert and multiply
			QuaternionHighPrecisionMatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> divide()
	{
		return DIVIDE;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> DP =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember in1, QuaternionHighPrecisionMatrixMember in2,
				QuaternionHighPrecisionMatrixMember out)
		{
			MatrixDirectProduct.compute(G.QHP, in1, in2, out);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember,QuaternionHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}


	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SCALE =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMatrixMember b, QuaternionHighPrecisionMatrixMember c) {
			MatrixScale.compute(G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINH =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			TaylorEstimateSinh.compute(18, G.QHP_MAT, G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> COSH =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			TaylorEstimateCosh.compute(18, G.QHP_MAT, G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINHANDCOSH = 
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember s, QuaternionHighPrecisionMatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> TANH =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			QuaternionHighPrecisionMatrixMember s = G.QHP_MAT.construct();
			QuaternionHighPrecisionMatrixMember c = G.QHP_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINCH =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			Sinch.compute(G.QHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINCHPI =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			Sinchpi.compute(G.QHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SIN =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			TaylorEstimateSin.compute(18, G.QHP_MAT, G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> COS =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			TaylorEstimateCos.compute(18, G.QHP_MAT, G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> cos() {
		return COS;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> TAN =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			QuaternionHighPrecisionMatrixMember s = G.QHP_MAT.construct();
			QuaternionHighPrecisionMatrixMember c = G.QHP_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINANDCOS =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember s, QuaternionHighPrecisionMatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINC =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			Sinc.compute(G.QHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SINCPI =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			Sincpi.compute(G.QHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> EXP =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			TaylorEstimateExp.compute(35, G.QHP_MAT, G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> LOG =
			new Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			TaylorEstimateLog.compute(8, G.QHP_MAT, G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, QuaternionHighPrecisionMatrixMember> ISZERO =
			new Function1<Boolean, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionMatrixMember a) {
			return SequenceIsZero.compute(G.QHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionHighPrecisionMatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<QuaternionHighPrecisionMatrixMember> PI =
			new Procedure1<QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a) {
			QuaternionHighPrecisionMember pi = G.QHP.construct();
			G.QHP.PI().call(pi);
			MatrixConstantDiagonal.compute(G.QHP, pi, a);
		}
	};

	@Override
	public Procedure1<QuaternionHighPrecisionMatrixMember> PI() {
		return PI;
	}

	private final Procedure1<QuaternionHighPrecisionMatrixMember> E =
			new Procedure1<QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a) {
			QuaternionHighPrecisionMember e = G.QHP.construct();
			G.QHP.E().call(e);
			MatrixConstantDiagonal.compute(G.QHP, e, a);
		}
	};

	@Override
	public Procedure1<QuaternionHighPrecisionMatrixMember> E() {
		return E;
	}

	private final Procedure1<QuaternionHighPrecisionMatrixMember> PHI =
			new Procedure1<QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a) {
			QuaternionHighPrecisionMember phi = G.QHP.construct();
			G.QHP.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.QHP, phi, a);
		}
	};

	@Override
	public Procedure1<QuaternionHighPrecisionMatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<QuaternionHighPrecisionMatrixMember> GAMMA =
			new Procedure1<QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a) {
			QuaternionHighPrecisionMember gamma = G.QHP.construct();
			G.QHP.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.QHP, gamma, a);
		}
	};

	@Override
	public Procedure1<QuaternionHighPrecisionMatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Procedure3<HighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionHighPrecisionMatrixMember b, QuaternionHighPrecisionMatrixMember c) {
			MatrixScaleByHighPrec.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SBR =
			new Procedure3<RationalMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionHighPrecisionMatrixMember b, QuaternionHighPrecisionMatrixMember c) {
			MatrixScaleByRational.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SBD =
			new Procedure3<Double, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(Double a, QuaternionHighPrecisionMatrixMember b, QuaternionHighPrecisionMatrixMember c) {
			MatrixScaleByDouble.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.QHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> ADDS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.QHP, scalar, G.QHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SUBS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.QHP, scalar, G.QHP.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> MULS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.QHP, scalar, G.QHP.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> DIVS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			FixedTransform2b.compute(G.QHP, scalar, G.QHP.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> MULTELEM =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b, QuaternionHighPrecisionMatrixMember c) {
			Transform3.compute(G.QHP, G.QHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> DIVELEM =
			new Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b, QuaternionHighPrecisionMatrixMember c) {
			Transform3.compute(G.QHP, G.QHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SCB2 =
			new Procedure3<Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			ScaleHelper.compute(G.QHP_MAT, G.QHP, new QuaternionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> SCBH =
			new Procedure3<Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionMatrixMember a, QuaternionHighPrecisionMatrixMember b) {
			ScaleHelper.compute(G.QHP_MAT, G.QHP, new QuaternionHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionMatrixMember, QuaternionHighPrecisionMatrixMember> scaleByOneHalf() {
		return SCBH;
	}

}
