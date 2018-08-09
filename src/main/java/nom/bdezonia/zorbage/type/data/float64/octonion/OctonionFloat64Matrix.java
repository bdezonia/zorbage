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
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixIsInfinite;
import nom.bdezonia.zorbage.algorithm.MatrixIsNaN;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNaN;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
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
		DirectProduct<OctonionFloat64MatrixMember,OctonionFloat64MatrixMember>
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
	
	private Function1<Boolean,OctonionFloat64MatrixMember> INF =
			new Function1<Boolean, OctonionFloat64MatrixMember>()
	{
		@Override
		public Boolean call(OctonionFloat64MatrixMember a) {
			return MatrixIsInfinite.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat64MatrixMember> isInfinite() {
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
}
