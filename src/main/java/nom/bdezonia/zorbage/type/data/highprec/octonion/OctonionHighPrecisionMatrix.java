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
package nom.bdezonia.zorbage.type.data.highprec.octonion;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
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
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionHighPrecisionMatrix
	implements
		RingWithUnity<OctonionHighPrecisionMatrix, OctonionHighPrecisionMatrixMember>,
		MatrixRing<OctonionHighPrecisionMatrix, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionAlgebra, OctonionHighPrecisionMember>,
		Constructible2dLong<OctonionHighPrecisionMatrixMember>,
		Norm<OctonionHighPrecisionMatrixMember,HighPrecisionMember>,
		DirectProduct<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember>,
		Exponential<OctonionHighPrecisionMatrixMember>,
		Trigonometric<OctonionHighPrecisionMatrixMember>,
		Hyperbolic<OctonionHighPrecisionMatrixMember>,
		RealConstants<OctonionHighPrecisionMatrixMember>,
		Tolerance<HighPrecisionMember,OctonionHighPrecisionMatrixMember>
{
	public OctonionHighPrecisionMatrix() { }

	private final Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> MUL =
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b, OctonionHighPrecisionMatrixMember c) {
			MatrixMultiply.compute(G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> POWER =
			new Procedure3<java.lang.Integer, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			MatrixPower.compute(power, G.OHP, G.OHP_RMOD, G.OHP_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> power() {
		return POWER;
	}

	private Procedure1<OctonionHighPrecisionMatrixMember> ZER =
			new Procedure1<OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a) {
			MatrixZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> NEG =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			MatrixNegate.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> ADD =
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b, OctonionHighPrecisionMatrixMember c) {
			MatrixAddition.compute(G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> SUB =
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b, OctonionHighPrecisionMatrixMember c) {
			MatrixSubtraction.compute(G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> EQ =
			new Function2<Boolean, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			return MatrixEqual.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> NEQ =
			new Function2<Boolean, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> isNotEqual() {
		return NEQ;
	}

	private Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember from, OctonionHighPrecisionMatrixMember to) {
			MatrixAssign.compute(G.OHP, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public OctonionHighPrecisionMatrixMember construct() {
		return new OctonionHighPrecisionMatrixMember();
	}

	@Override
	public OctonionHighPrecisionMatrixMember construct(OctonionHighPrecisionMatrixMember other) {
		return new OctonionHighPrecisionMatrixMember(other);
	}

	@Override
	public OctonionHighPrecisionMatrixMember construct(String s) {
		return new OctonionHighPrecisionMatrixMember(s);
	}

	@Override
	public OctonionHighPrecisionMatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionHighPrecisionMatrixMember(s, d1, d2);
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionMatrixMember, HighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, HighPrecisionMember b) {
			MatrixSpectralNorm.compute(G.OHP_MAT, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> CONJ =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			MatrixConjugate.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> TRANSP =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			MatrixTranspose.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> CONJTRANSP =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			OctonionHighPrecisionMatrixMember tmp = new OctonionHighPrecisionMatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO - test

	private final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMember> DET =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMember b) {
			MatrixDeterminant.compute(G.OHP_MAT, G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMember> det() {
		return DET;
	}

	private final Procedure1<OctonionHighPrecisionMatrixMember> UNITY =
			new Procedure1<OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a) {
			MatrixUnity.compute(G.OHP, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> INVERT = 
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			MatrixInvert.compute(G.OHP, G.OHP_RMOD, G.OHP_MAT, a, b);
		}
	};
	
	@Override
	public final Procedure2<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> invert() {
		return INVERT;
	}

	private final Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> DIVIDE =
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b, OctonionHighPrecisionMatrixMember c) {
			// invert and multiply
			OctonionHighPrecisionMatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> DP =
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember in1, OctonionHighPrecisionMatrixMember in2, OctonionHighPrecisionMatrixMember out) {
			MatrixDirectProduct.compute(G.OHP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember,OctonionHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SCALE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMatrixMember b, OctonionHighPrecisionMatrixMember c) {
			MatrixScale.compute(G.OHP, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINH =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			TaylorEstimateSinh.compute(18, G.OHP_MAT, G.OHP, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> COSH =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			TaylorEstimateCosh.compute(18, G.OHP_MAT, G.OHP, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINHANDCOSH = 
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember s, OctonionHighPrecisionMatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> TANH =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			OctonionHighPrecisionMatrixMember s = G.OHP_MAT.construct();
			OctonionHighPrecisionMatrixMember c = G.OHP_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINCH =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			Sinch.compute(G.OHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINCHPI =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			Sinchpi.compute(G.OHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SIN =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			TaylorEstimateSin.compute(18, G.OHP_MAT, G.OHP, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> COS =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			TaylorEstimateCos.compute(18, G.OHP_MAT, G.OHP, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> cos() {
		return COS;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> TAN =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			OctonionHighPrecisionMatrixMember s = G.OHP_MAT.construct();
			OctonionHighPrecisionMatrixMember c = G.OHP_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINANDCOS =
			new Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember s, OctonionHighPrecisionMatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINC =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			Sinc.compute(G.OHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> SINCPI =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			Sincpi.compute(G.OHP_MAT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> EXP =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			TaylorEstimateExp.compute(35, G.OHP_MAT, G.OHP, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> LOG =
			new Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			TaylorEstimateLog.compute(8, G.OHP_MAT, G.OHP, a, b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, OctonionHighPrecisionMatrixMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMatrixMember a) {
			return SequenceIsZero.compute(G.OHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionMatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<OctonionHighPrecisionMatrixMember> PI =
			new Procedure1<OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a) {
			OctonionHighPrecisionMember pi = G.OHP.construct();
			G.OHP.PI().call(pi);
			MatrixConstantDiagonal.compute(G.OHP, pi, a);
		}
	};

	@Override
	public Procedure1<OctonionHighPrecisionMatrixMember> PI() {
		return PI;
	}

	private final Procedure1<OctonionHighPrecisionMatrixMember> E =
			new Procedure1<OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a) {
			OctonionHighPrecisionMember e = G.OHP.construct();
			G.OHP.E().call(e);
			MatrixConstantDiagonal.compute(G.OHP, e, a);
		}
	};

	@Override
	public Procedure1<OctonionHighPrecisionMatrixMember> E() {
		return E;
	}

	private final Procedure1<OctonionHighPrecisionMatrixMember> PHI =
			new Procedure1<OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a) {
			OctonionHighPrecisionMember phi = G.OHP.construct();
			G.OHP.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.OHP, phi, a);
		}
	};

	@Override
	public Procedure1<OctonionHighPrecisionMatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<OctonionHighPrecisionMatrixMember> GAMMA =
			new Procedure1<OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMatrixMember a) {
			OctonionHighPrecisionMember gamma = G.OHP.construct();
			G.OHP.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.OHP, gamma, a);
		}
	};

	@Override
	public Procedure1<OctonionHighPrecisionMatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionMatrixMember a, OctonionHighPrecisionMatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			return SequencesSimilar.compute(G.OHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionMatrixMember, OctonionHighPrecisionMatrixMember> within() {
		return WITHIN;
	}
}
