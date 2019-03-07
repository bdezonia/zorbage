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
package nom.bdezonia.zorbage.type.data.float32.octonion;

import nom.bdezonia.zorbage.algebras.G;
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
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat32Matrix
	implements
		RingWithUnity<OctonionFloat32Matrix, OctonionFloat32MatrixMember>,
		MatrixRing<OctonionFloat32Matrix, OctonionFloat32MatrixMember, OctonionFloat32Algebra, OctonionFloat32Member>,
		Constructible2dLong<OctonionFloat32MatrixMember>,
		Rounding<Float32Member, OctonionFloat32MatrixMember>,
		Norm<OctonionFloat32MatrixMember,Float32Member>,
		DirectProduct<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember>,
		Exponential<OctonionFloat32MatrixMember>,
		Trigonometric<OctonionFloat32MatrixMember>,
		Hyperbolic<OctonionFloat32MatrixMember>
{
	public OctonionFloat32Matrix() { }

	private final Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> MUL =
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b, OctonionFloat32MatrixMember c) {
			MatrixMultiply.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			MatrixPower.compute(power, G.OFLT, G.OFLT_RMOD, G.OFLT_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> power() {
		return POWER;
	}

	private Procedure1<OctonionFloat32MatrixMember> ZER =
			new Procedure1<OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a) {
			MatrixZero.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> NEG =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			MatrixNegate.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> ADD =
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b, OctonionFloat32MatrixMember c) {
			MatrixAddition.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> SUB =
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b, OctonionFloat32MatrixMember c) {
			MatrixSubtraction.compute(G.OFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> EQ =
			new Function2<Boolean, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			return MatrixEqual.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> NEQ =
			new Function2<Boolean, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> isNotEqual() {
		return NEQ;
	}

	private Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> ASSIGN =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember from, OctonionFloat32MatrixMember to) {
			MatrixAssign.compute(G.OFLT, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public OctonionFloat32MatrixMember construct() {
		return new OctonionFloat32MatrixMember();
	}

	@Override
	public OctonionFloat32MatrixMember construct(OctonionFloat32MatrixMember other) {
		return new OctonionFloat32MatrixMember(other);
	}

	@Override
	public OctonionFloat32MatrixMember construct(String s) {
		return new OctonionFloat32MatrixMember(s);
	}

	@Override
	public OctonionFloat32MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionFloat32MatrixMember(s, d1, d2);
	}

	private final Procedure2<OctonionFloat32MatrixMember,Float32Member> NORM =
			new Procedure2<OctonionFloat32MatrixMember, Float32Member>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, Float32Member b) {
			MatrixSpectralNorm.compute(G.OFLT_MAT, G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float32Member,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float32Member, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			MatrixRound.compute(G.OFLT, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> round() {
		return ROUND;
	}

	private Function1<Boolean,OctonionFloat32MatrixMember> ISNAN =
			new Function1<Boolean, OctonionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat32MatrixMember a) {
			return MatrixIsNaN.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat32MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat32MatrixMember> NAN =
			new Procedure1<OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a) {
			MatrixNaN.compute(G.OFLT, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat32MatrixMember> nan() {
		return NAN;
	}
	
	private Function1<Boolean,OctonionFloat32MatrixMember> ISINF =
			new Function1<Boolean, OctonionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat32MatrixMember a) {
			return MatrixIsInfinite.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat32MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat32MatrixMember> INF =
			new Procedure1<OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a) {
			MatrixInfinite.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> CONJ =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			MatrixConjugate.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> TRANSP =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			MatrixTranspose.compute(G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> CONJTRANSP =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			OctonionFloat32MatrixMember tmp = new OctonionFloat32MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO - test

	private final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32Member> DET =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32Member>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32Member b) {
			MatrixDeterminant.compute(G.OFLT_MAT, G.OFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32MatrixMember,OctonionFloat32Member> det() {
		return DET;
	}

	private final Procedure1<OctonionFloat32MatrixMember> UNITY =
			new Procedure1<OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a) {
			MatrixUnity.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> INVERT = 
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			MatrixInvert.compute(G.OFLT, G.OFLT_RMOD, G.OFLT_MAT, a, b);
		}
	};
	
	@Override
	public final Procedure2<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> invert() {
		return INVERT;
	}

	private final Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> DIVIDE =
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b, OctonionFloat32MatrixMember c) {
			// invert and multiply
			OctonionFloat32MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> DP =
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember in1, OctonionFloat32MatrixMember in2, OctonionFloat32MatrixMember out) {
			MatrixDirectProduct.compute(G.OFLT, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32MatrixMember,OctonionFloat32MatrixMember,OctonionFloat32MatrixMember> directProduct()
	{
		return DP;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SCALE =
			new Procedure3<OctonionFloat32Member, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32Member a, OctonionFloat32MatrixMember b, OctonionFloat32MatrixMember c) {
			MatrixScale.compute(G.OFLT, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINH =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.OFLT_MAT, G.OFLT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> COSH =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.OFLT_MAT, G.OFLT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINHANDCOSH = 
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember s, OctonionFloat32MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> TANH =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			OctonionFloat32MatrixMember s = G.OFLT_MAT.construct();
			OctonionFloat32MatrixMember c = G.OFLT_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINCH =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat32MatrixMember sinha = G.OFLT_MAT.construct();
			sinh().call(a, sinha);
			divide().call(sinha, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINCHPI =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat32Member pi = G.OFLT.construct();
			G.OFLT.PI().call(pi);
			OctonionFloat32MatrixMember sinha = G.OFLT_MAT.construct();
			sinh().call(a, sinha);
			scale().call(pi, sinha, sinha);
			OctonionFloat32MatrixMember pi_a = G.OFLT_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sinha, pi_a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SIN =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			TaylorEstimateSin.compute(18, G.OFLT_MAT, G.OFLT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> COS =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			TaylorEstimateCos.compute(18, G.OFLT_MAT, G.OFLT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> TAN =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			OctonionFloat32MatrixMember s = G.OFLT_MAT.construct();
			OctonionFloat32MatrixMember c = G.OFLT_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINANDCOS =
			new Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember s, OctonionFloat32MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINC =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat32MatrixMember sina = G.OFLT_MAT.construct();
			sin().call(a, sina);
			divide().call(sina, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> SINCPI =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			if (isZero().call(a)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat32Member pi = G.OFLT.construct();
			G.OFLT.PI().call(pi);
			OctonionFloat32MatrixMember sina = G.OFLT_MAT.construct();
			sin().call(a, sina);
			scale().call(pi, sina, sina);
			OctonionFloat32MatrixMember pi_a = G.OFLT_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sina, pi_a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> EXP =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			TaylorEstimateExp.compute(35, G.OFLT_MAT, G.OFLT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> LOG =
			new Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember>()
	{
		@Override
		public void call(OctonionFloat32MatrixMember a, OctonionFloat32MatrixMember b) {
			TaylorEstimateLog.compute(8, G.OFLT_MAT, G.OFLT, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32MatrixMember, OctonionFloat32MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, OctonionFloat32MatrixMember> ISZERO =
			new Function1<Boolean, OctonionFloat32MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat32MatrixMember a) {
			return MatrixIsZero.compute(G.OFLT, a);
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32MatrixMember> isZero() {
		return ISZERO;
	}
}