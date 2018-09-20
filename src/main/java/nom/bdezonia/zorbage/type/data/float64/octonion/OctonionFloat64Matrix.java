/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.octonion;

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
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNaN;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixScale;
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
import nom.bdezonia.zorbage.groups.G;
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
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64Matrix
	implements
		RingWithUnity<OctonionFloat64Matrix, OctonionFloat64MatrixMember>,
		MatrixRing<OctonionFloat64Matrix, OctonionFloat64MatrixMember, OctonionFloat64Group, OctonionFloat64Member>,
		Constructible2dLong<OctonionFloat64MatrixMember>,
		Rounding<Float64Member, OctonionFloat64MatrixMember>,
		Norm<OctonionFloat64MatrixMember,Float64Member>,
		DirectProduct<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember>,
		Exponential<OctonionFloat64MatrixMember>,
		Trigonometric<OctonionFloat64MatrixMember>,
		Hyperbolic<OctonionFloat64MatrixMember>
{
	public OctonionFloat64Matrix() { }

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> MUL =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixMultiply.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixPower.compute(power, G.ODBL, G.ODBL_RMOD, G.ODBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> power() {
		return POWER;
	}

	private Procedure1<OctonionFloat64MatrixMember> ZER =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			MatrixZero.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> NEG =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixNegate.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> ADD =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixAddition.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> SUB =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixSubtraction.compute(G.ODBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> EQ =
			new Function2<Boolean, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			return MatrixEqual.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> NEQ =
			new Function2<Boolean, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> ASSIGN =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember from, OctonionFloat64MatrixMember to) {
			MatrixAssign.compute(G.ODBL, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public OctonionFloat64MatrixMember construct() {
		return new OctonionFloat64MatrixMember();
	}

	@Override
	public OctonionFloat64MatrixMember construct(OctonionFloat64MatrixMember other) {
		return new OctonionFloat64MatrixMember(other);
	}

	@Override
	public OctonionFloat64MatrixMember construct(String s) {
		return new OctonionFloat64MatrixMember(s);
	}

	@Override
	public OctonionFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionFloat64MatrixMember(s, d1, d2);
	}

	private final Procedure2<OctonionFloat64MatrixMember,Float64Member> NORM =
			new Procedure2<OctonionFloat64MatrixMember, Float64Member>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, Float64Member b) {
			// TODO
			throw new IllegalArgumentException("TODO");
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixRound.compute(G.ODBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> round() {
		return ROUND;
	}

	private Function1<Boolean,OctonionFloat64MatrixMember> ISNAN =
			new Function1<Boolean, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a) {
			return MatrixIsNaN.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat64MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat64MatrixMember> NAN =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			MatrixNaN.compute(G.ODBL, a);
		}
	};

	@Override
	public Procedure1<OctonionFloat64MatrixMember> nan() {
		return NAN;
	}
	
	private Function1<Boolean,OctonionFloat64MatrixMember> ISINF =
			new Function1<Boolean, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a) {
			return MatrixIsInfinite.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat64MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat64MatrixMember> INF =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			MatrixInfinite.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> CONJ =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixConjugate.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> TRANSP =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixTranspose.compute(G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> CONJTRANSP =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember tmp = new OctonionFloat64MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	// TODO - test

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64Member> DET =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64Member>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64Member b) {
			MatrixDeterminant.compute(G.ODBL_MAT, G.ODBL, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64MatrixMember,OctonionFloat64Member> det() {
		return DET;
	}

	private final Procedure1<OctonionFloat64MatrixMember> UNITY =
			new Procedure1<OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a) {
			MatrixUnity.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> INVERT = 
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			MatrixInvert.compute(G.ODBL, G.ODBL_RMOD, G.ODBL_MAT, a, b);
		}
	};
	
	@Override
	public final Procedure2<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> invert() {
		return INVERT;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> DIVIDE =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			// invert and multiply
			OctonionFloat64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> DP =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember in1, OctonionFloat64MatrixMember in2, OctonionFloat64MatrixMember out) {
			MatrixDirectProduct.compute(G.ODBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember,OctonionFloat64MatrixMember> directProduct()
	{
		return DP;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SCALE =
			new Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64Member a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
			MatrixScale.compute(G.ODBL, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> COSH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINHANDCOSH = 
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember s, OctonionFloat64MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> TANH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember s = G.ODBL_MAT.construct();
			OctonionFloat64MatrixMember c = G.ODBL_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINCH =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember zero = new OctonionFloat64MatrixMember(a);
			zero().call(zero);
			if (isEqual().call(a, zero)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat64MatrixMember sinha = G.ODBL_MAT.construct();
			sinh().call(a, sinha);
			divide().call(sinha, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINCHPI =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember zero = new OctonionFloat64MatrixMember(a);
			zero().call(zero);
			if (isEqual().call(a, zero)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat64Member pi = G.ODBL.construct();
			G.ODBL.PI().call(pi);
			OctonionFloat64MatrixMember sinha = G.ODBL_MAT.construct();
			sinh().call(a, sinha);
			scale().call(pi, sinha, sinha);
			OctonionFloat64MatrixMember pi_a = G.ODBL_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sinha, pi_a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SIN =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateSin.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> COS =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateCos.compute(18, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> TAN =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember s = G.ODBL_MAT.construct();
			OctonionFloat64MatrixMember c = G.ODBL_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINANDCOS =
			new Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember s, OctonionFloat64MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINC =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember zero = new OctonionFloat64MatrixMember(a);
			zero().call(zero);
			if (isEqual().call(a, zero)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat64MatrixMember sina = G.ODBL_MAT.construct();
			sin().call(a, sina);
			divide().call(sina, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> SINCPI =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			OctonionFloat64MatrixMember zero = new OctonionFloat64MatrixMember(a);
			zero().call(zero);
			if (isEqual().call(a, zero)) {
				b.alloc(a.rows(), a.cols());
				unity().call(b);
				return;
			}
			OctonionFloat64Member pi = G.ODBL.construct();
			G.ODBL.PI().call(pi);
			OctonionFloat64MatrixMember sina = G.ODBL_MAT.construct();
			sin().call(a, sina);
			scale().call(pi, sina, sina);
			OctonionFloat64MatrixMember pi_a = G.ODBL_MAT.construct();
			scale().call(pi, a, pi_a);
			divide().call(sina, pi_a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> EXP =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateExp.compute(35, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> LOG =
			new Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember>()
	{
		@Override
		public void call(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
			TaylorEstimateLog.compute(8, G.ODBL_MAT, G.ODBL, a, b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64MatrixMember, OctonionFloat64MatrixMember> log() {
		return LOG;
	}

}
