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
package nom.bdezonia.zorbage.type.data.highprec.real;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConstantDiagonal;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixScale;
import nom.bdezonia.zorbage.algorithm.MatrixSpectralNorm;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
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
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionMatrix
	implements
		RingWithUnity<HighPrecisionMatrix, HighPrecisionMatrixMember>,
		MatrixRing<HighPrecisionMatrix, HighPrecisionMatrixMember, HighPrecisionAlgebra, HighPrecisionMember>,
		Constructible2dLong<HighPrecisionMatrixMember>,
		Norm<HighPrecisionMatrixMember,HighPrecisionMember>,
		DirectProduct<HighPrecisionMatrixMember, HighPrecisionMatrixMember>,
		Exponential<HighPrecisionMatrixMember>,
		Trigonometric<HighPrecisionMatrixMember>,
		Hyperbolic<HighPrecisionMatrixMember>,
		RealConstants<HighPrecisionMatrixMember>
{
	public HighPrecisionMatrix() { }

	private final Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> MUL =
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b, HighPrecisionMatrixMember c) {
			MatrixMultiply.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,HighPrecisionMatrixMember,HighPrecisionMatrixMember> POWER =
			new Procedure3<Integer, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(Integer power, HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			MatrixPower.compute(power, G.HP, G.HP_VEC, G.HP_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,HighPrecisionMatrixMember,HighPrecisionMatrixMember> power() {
		return POWER;
	}

	private final Procedure1<HighPrecisionMatrixMember> ZER =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			MatrixZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> NEG =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			MatrixNegate.compute(G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> ADD =
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b, HighPrecisionMatrixMember c) {
			MatrixAddition.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> add() {
		return ADD;
	}

	private final Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> SUB =
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b, HighPrecisionMatrixMember c) {
			MatrixSubtraction.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,HighPrecisionMatrixMember,HighPrecisionMatrixMember> EQ =
			new Function2<Boolean, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			return MatrixEqual.compute(G.HP, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMatrixMember,HighPrecisionMatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionMatrixMember,HighPrecisionMatrixMember> NEQ =
			new Function2<Boolean, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMatrixMember,HighPrecisionMatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> ASSIGN =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember from, HighPrecisionMatrixMember to) {
			MatrixAssign.compute(G.HP, from, to);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public HighPrecisionMatrixMember construct() {
		return new HighPrecisionMatrixMember();
	}

	@Override
	public HighPrecisionMatrixMember construct(HighPrecisionMatrixMember other) {
		return new HighPrecisionMatrixMember(other);
	}

	@Override
	public HighPrecisionMatrixMember construct(String s) {
		return new HighPrecisionMatrixMember(s);
	}

	@Override
	public HighPrecisionMatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new HighPrecisionMatrixMember(s, d1, d2);
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMember b) {
			MatrixSpectralNorm.compute(G.HP_MAT, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Function1<Boolean,HighPrecisionMatrixMember> ISNAN =
			new Function1<Boolean, HighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMatrixMember a) {
			// TODO
			// return SequenceIsNan.compute(G.HP, a.rawData());
			return false;
		}
	};
	
	@Override
	public Function1<Boolean,HighPrecisionMatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<HighPrecisionMatrixMember> NAN =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			// TODO
			//MatrixNaN.compute(G.HP, a);
		}
	};

	@Override
	public Procedure1<HighPrecisionMatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,HighPrecisionMatrixMember> ISINF =
			new Function1<Boolean, HighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMatrixMember a) {
			// TODO
			//return SequenceIsInf.compute(G.HP, a.rawData());
			return false;
		}
	};
	
	@Override
	public Function1<Boolean,HighPrecisionMatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<HighPrecisionMatrixMember> INF =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			// TODO
			//MatrixInfinite.compute(G.HP, a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMatrixMember> infinite() {
		return INF;
	}

	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> TRANSP =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			MatrixTranspose.compute(G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> CONJTRANSP =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			transpose().call(a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMember> DET =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMember b) {
			MatrixDeterminant.compute(G.HP_MAT, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMember> det() {
		return DET;
	}

	private final Procedure1<HighPrecisionMatrixMember> UNITY =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			MatrixUnity.compute(G.HP, a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> INV =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			MatrixInvert.compute(G.HP, G.HP_VEC, G.HP_MAT, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMatrixMember,HighPrecisionMatrixMember> invert() {
		return INV;
	}

	private final Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> DIVIDE =
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b, HighPrecisionMatrixMember c) {
			// invert and multiply
			HighPrecisionMatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> DP =
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember in1, HighPrecisionMatrixMember in2, HighPrecisionMatrixMember out) {
			MatrixDirectProduct.compute(G.HP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMatrixMember,HighPrecisionMatrixMember,HighPrecisionMatrixMember> directProduct() {
		return DP;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember> SCALE =
			new Procedure3<HighPrecisionMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMatrixMember b, HighPrecisionMatrixMember c) {
			MatrixScale.compute(G.HP, a, b, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINH =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			TaylorEstimateSinh.compute(18, G.HP_MAT, G.HP, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> COSH =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			TaylorEstimateCosh.compute(18, G.HP_MAT, G.HP, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINHANDCOSH = 
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember s, HighPrecisionMatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> TANH =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			HighPrecisionMatrixMember s = G.HP_MAT.construct();
			HighPrecisionMatrixMember c = G.HP_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINCH =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			Sinch.compute(G.HP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINCHPI =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			Sinchpi.compute(G.HP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> SIN =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			TaylorEstimateSin.compute(18, G.HP_MAT, G.HP, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> COS =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			TaylorEstimateCos.compute(18, G.HP_MAT, G.HP, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> cos() {
		return COS;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> TAN =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			HighPrecisionMatrixMember s = G.HP_MAT.construct();
			HighPrecisionMatrixMember c = G.HP_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINANDCOS =
			new Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember s, HighPrecisionMatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionMatrixMember, HighPrecisionMatrixMember, HighPrecisionMatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINC =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			Sinc.compute(G.HP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> SINCPI =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			Sincpi.compute(G.HP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> EXP =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			TaylorEstimateExp.compute(35, G.HP_MAT, G.HP, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> LOG =
			new Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a, HighPrecisionMatrixMember b) {
			TaylorEstimateLog.compute(8, G.HP_MAT, G.HP, a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMatrixMember, HighPrecisionMatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, HighPrecisionMatrixMember> ISZERO =
			new Function1<Boolean, HighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMatrixMember a) {
			return SequenceIsZero.compute(G.HP, a.rawData());
		}	
	};

	@Override
	public Function1<Boolean, HighPrecisionMatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<HighPrecisionMatrixMember> PI =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			HighPrecisionMember pi = G.HP.construct();
			G.HP.PI().call(pi);
			MatrixConstantDiagonal.compute(G.HP, pi, a);
		}
	};

	@Override
	public Procedure1<HighPrecisionMatrixMember> PI() {
		return PI;
	}

	private final Procedure1<HighPrecisionMatrixMember> E =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			HighPrecisionMember e = G.HP.construct();
			G.HP.E().call(e);
			MatrixConstantDiagonal.compute(G.HP, e, a);
		}
	};

	@Override
	public Procedure1<HighPrecisionMatrixMember> E() {
		return E;
	}

	private final Procedure1<HighPrecisionMatrixMember> PHI =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			HighPrecisionMember phi = G.HP.construct();
			G.HP.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.HP, phi, a);
		}
	};

	@Override
	public Procedure1<HighPrecisionMatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<HighPrecisionMatrixMember> GAMMA =
			new Procedure1<HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionMatrixMember a) {
			HighPrecisionMember gamma = G.HP.construct();
			G.HP.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.HP, gamma, a);
		}
	};

	@Override
	public Procedure1<HighPrecisionMatrixMember> GAMMA() {
		return GAMMA;
	}
}