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
package nom.bdezonia.zorbage.type.data.float16.real;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConstantDiagonal;
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
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float16Matrix
	implements
		RingWithUnity<Float16Matrix, Float16MatrixMember>,
		MatrixRing<Float16Matrix, Float16MatrixMember, Float16Algebra, Float16Member>,
		Constructible2dLong<Float16MatrixMember>,
		Rounding<Float16Member, Float16MatrixMember>,
		Norm<Float16MatrixMember,Float16Member>,
		DirectProduct<Float16MatrixMember, Float16MatrixMember>,
		Exponential<Float16MatrixMember>,
		Trigonometric<Float16MatrixMember>,
		Hyperbolic<Float16MatrixMember>,
		RealConstants<Float16MatrixMember>,
		Infinite<Float16MatrixMember>,
		NaN<Float16MatrixMember>,
		Tolerance<Float16Member,Float16MatrixMember>
{
	public Float16Matrix() { }

	private final Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> MUL =
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b, Float16MatrixMember c) {
			MatrixMultiply.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float16MatrixMember,Float16MatrixMember> POWER =
			new Procedure3<Integer, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Integer power, Float16MatrixMember a, Float16MatrixMember b) {
			MatrixPower.compute(power, G.HLF, G.HLF_VEC, G.HLF_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float16MatrixMember,Float16MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<Float16MatrixMember> ZER =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			MatrixZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<Float16MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<Float16MatrixMember,Float16MatrixMember> NEG =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			MatrixNegate.compute(G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> ADD =
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b, Float16MatrixMember c) {
			MatrixAddition.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> SUB =
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b, Float16MatrixMember c) {
			MatrixSubtraction.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float16MatrixMember,Float16MatrixMember> EQ =
			new Function2<Boolean, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public Boolean call(Float16MatrixMember a, Float16MatrixMember b) {
			return MatrixEqual.compute(G.HLF, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float16MatrixMember,Float16MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16MatrixMember,Float16MatrixMember> NEQ =
			new Function2<Boolean, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public Boolean call(Float16MatrixMember a, Float16MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float16MatrixMember,Float16MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float16MatrixMember,Float16MatrixMember> ASSIGN =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember from, Float16MatrixMember to) {
			MatrixAssign.compute(G.HLF, from, to);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public Float16MatrixMember construct() {
		return new Float16MatrixMember();
	}

	@Override
	public Float16MatrixMember construct(Float16MatrixMember other) {
		return new Float16MatrixMember(other);
	}

	@Override
	public Float16MatrixMember construct(String s) {
		return new Float16MatrixMember(s);
	}

	@Override
	public Float16MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new Float16MatrixMember(s, d1, d2);
	}

	private final Procedure2<Float16MatrixMember,Float16Member> NORM =
			new Procedure2<Float16MatrixMember, Float16Member>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16Member b) {
			MatrixSpectralNorm.compute(G.HLF_MAT, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float16Member,Float16MatrixMember,Float16MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float16Member, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16MatrixMember a, Float16MatrixMember b) {
			MatrixRound.compute(G.HLF, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,Float16MatrixMember,Float16MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,Float16MatrixMember> ISNAN =
			new Function1<Boolean, Float16MatrixMember>()
	{
		@Override
		public Boolean call(Float16MatrixMember a) {
			return SequenceIsNan.compute(G.HLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float16MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float16MatrixMember> NAN =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			MatrixNaN.compute(G.HLF, a);
		}
	};

	@Override
	public Procedure1<Float16MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,Float16MatrixMember> ISINF =
			new Function1<Boolean, Float16MatrixMember>()
	{
		@Override
		public Boolean call(Float16MatrixMember a) {
			return SequenceIsInf.compute(G.HLF, a.rawData());
		}
	};
	
	@Override
	public Function1<Boolean,Float16MatrixMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16MatrixMember> INF =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			MatrixInfinite.compute(G.HLF, a);
		}
	};
	
	@Override
	public Procedure1<Float16MatrixMember> infinite() {
		return INF;
	}

	@Override
	public Procedure2<Float16MatrixMember,Float16MatrixMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure2<Float16MatrixMember,Float16MatrixMember> TRANSP =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			MatrixTranspose.compute(G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<Float16MatrixMember,Float16MatrixMember> CONJTRANSP =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			transpose().call(a, b);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<Float16MatrixMember,Float16Member> DET =
			new Procedure2<Float16MatrixMember, Float16Member>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16Member b) {
			MatrixDeterminant.compute(G.HLF_MAT, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16Member> det() {
		return DET;
	}

	private final Procedure1<Float16MatrixMember> UNITY =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			MatrixUnity.compute(G.HLF, a);
		}
	};
	
	@Override
	public Procedure1<Float16MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<Float16MatrixMember,Float16MatrixMember> INV =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			MatrixInvert.compute(G.HLF, G.HLF_VEC, G.HLF_MAT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16MatrixMember,Float16MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> DIVIDE =
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b, Float16MatrixMember c) {
			// invert and multiply
			Float16MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> DP =
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember in1, Float16MatrixMember in2, Float16MatrixMember out) {
			MatrixDirectProduct.compute(G.HLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float16MatrixMember,Float16MatrixMember,Float16MatrixMember> directProduct() {
		return DP;
	}

	private final Procedure3<Float16Member, Float16MatrixMember, Float16MatrixMember> SCALE =
			new Procedure3<Float16Member, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16Member a, Float16MatrixMember b, Float16MatrixMember c) {
			MatrixScale.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16Member, Float16MatrixMember, Float16MatrixMember> scale() {
		return SCALE;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> SINH =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			TaylorEstimateSinh.compute(18, G.HLF_MAT, G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> sinh() {
		return SINH;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> COSH =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			TaylorEstimateCosh.compute(18, G.HLF_MAT, G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> cosh() {
		return COSH;
	}

	private final Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember> SINHANDCOSH = 
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember s, Float16MatrixMember c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> TANH =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			Float16MatrixMember s = G.HLF_MAT.construct();
			Float16MatrixMember c = G.HLF_MAT.construct();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> tanh() {
		return TANH;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> SINCH =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			Sinch.compute(G.HLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> sinch() {
		return SINCH;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> SINCHPI =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			Sinchpi.compute(G.HLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> SIN =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			TaylorEstimateSin.compute(18, G.HLF_MAT, G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> sin() {
		return SIN;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> COS =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			TaylorEstimateCos.compute(18, G.HLF_MAT, G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> cos() {
		return COS;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> TAN =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			Float16MatrixMember s = G.HLF_MAT.construct();
			Float16MatrixMember c = G.HLF_MAT.construct();
			sinAndCos().call(a, s, c);
			divide().call(s, c, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> tan() {
		return TAN;
	}

	private final Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember> SINANDCOS =
			new Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember s, Float16MatrixMember c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<Float16MatrixMember, Float16MatrixMember, Float16MatrixMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> SINC =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			Sinc.compute(G.HLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> sinc() {
		return SINC;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> SINCPI =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			Sincpi.compute(G.HLF_MAT, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> EXP =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			TaylorEstimateExp.compute(35, G.HLF_MAT, G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> exp() {
		return EXP;
	}

	private final Procedure2<Float16MatrixMember, Float16MatrixMember> LOG =
			new Procedure2<Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a, Float16MatrixMember b) {
			TaylorEstimateLog.compute(8, G.HLF_MAT, G.HLF, a, b);
		}
	};

	@Override
	public Procedure2<Float16MatrixMember, Float16MatrixMember> log() {
		return LOG;
	}

	private final Function1<Boolean, Float16MatrixMember> ISZERO =
			new Function1<Boolean, Float16MatrixMember>()
	{
		@Override
		public Boolean call(Float16MatrixMember a) {
			return SequenceIsZero.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16MatrixMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<Float16MatrixMember> PI =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			Float16Member pi = G.HLF.construct();
			G.HLF.PI().call(pi);
			MatrixConstantDiagonal.compute(G.HLF, pi, a);
		}
	};

	@Override
	public Procedure1<Float16MatrixMember> PI() {
		return PI;
	}

	private final Procedure1<Float16MatrixMember> E =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			Float16Member e = G.HLF.construct();
			G.HLF.E().call(e);
			MatrixConstantDiagonal.compute(G.HLF, e, a);
		}
	};

	@Override
	public Procedure1<Float16MatrixMember> E() {
		return E;
	}

	private final Procedure1<Float16MatrixMember> PHI =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			Float16Member phi = G.HLF.construct();
			G.HLF.PHI().call(phi);
			MatrixConstantDiagonal.compute(G.HLF, phi, a);
		}
	};

	@Override
	public Procedure1<Float16MatrixMember> PHI() {
		return PHI;
	}

	private final Procedure1<Float16MatrixMember> GAMMA =
			new Procedure1<Float16MatrixMember>()
	{
		@Override
		public void call(Float16MatrixMember a) {
			Float16Member gamma = G.HLF.construct();
			G.HLF.GAMMA().call(gamma);
			MatrixConstantDiagonal.compute(G.HLF, gamma, a);
		}
	};

	@Override
	public Procedure1<Float16MatrixMember> GAMMA() {
		return GAMMA;
	}

	private final Function3<Boolean, Float16Member, Float16MatrixMember, Float16MatrixMember> WITHIN =
			new Function3<Boolean, Float16Member, Float16MatrixMember, Float16MatrixMember>()
	{
		@Override
		public Boolean call(Float16Member tol, Float16MatrixMember a, Float16MatrixMember b) {
			if (a.rows() != b.rows() || a.cols() != b.cols())
				return false;
			Float16Member elemA = G.HLF.construct();
			Float16Member elemB = G.HLF.construct();
			IndexedDataSource<Float16Member> lista = a.rawData();
			IndexedDataSource<Float16Member> listb = b.rawData();
			for (long i = 0; i < lista.size(); i++) {
				lista.get(i, elemA);
				listb.get(i, elemB);
				if (!G.HLF.within().call(tol, elemA, elemB))
					return false;
			}
			return true;
		}
	};

	@Override
	public Function3<Boolean, Float16Member, Float16MatrixMember, Float16MatrixMember> within() {
		return WITHIN;
	}
}
