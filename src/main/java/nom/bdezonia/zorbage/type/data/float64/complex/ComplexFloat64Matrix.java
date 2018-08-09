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
package nom.bdezonia.zorbage.type.data.float64.complex;

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
public class ComplexFloat64Matrix
	implements
		RingWithUnity<ComplexFloat64Matrix, ComplexFloat64MatrixMember>,
		MatrixRing<ComplexFloat64Matrix, ComplexFloat64MatrixMember, ComplexFloat64Group, ComplexFloat64Member>,
		Constructible2dLong<ComplexFloat64MatrixMember>,
		Rounding<Float64Member, ComplexFloat64MatrixMember>,
		Norm<ComplexFloat64MatrixMember,Float64Member>,
		DirectProduct<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>
{
	public ComplexFloat64Matrix() { }

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> MUL =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixMultiply.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> POWER =
			new Procedure3<Integer, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		
		@Override
		public void call(Integer power, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixPower.compute(power, G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat64MatrixMember> ZER =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixZero.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> NEG =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixNegate.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> ADD =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixAddition.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> SUB =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			MatrixSubtraction.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> EQ =
			new Function2<Boolean, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			return MatrixEqual.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> NEQ =
			new Function2<Boolean, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> ASSIGN =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember from, ComplexFloat64MatrixMember to) {
			MatrixAssign.compute(G.CDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public ComplexFloat64MatrixMember construct() {
		return new ComplexFloat64MatrixMember();
	}

	@Override
	public ComplexFloat64MatrixMember construct(ComplexFloat64MatrixMember other) {
		return new ComplexFloat64MatrixMember(other);
	}

	@Override
	public ComplexFloat64MatrixMember construct(String s) {
		return new ComplexFloat64MatrixMember(s);
	}

	@Override
	public ComplexFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexFloat64MatrixMember(s, d1, d2);
	}

	private final Procedure2<ComplexFloat64MatrixMember,Float64Member> NORM =
			new Procedure2<ComplexFloat64MatrixMember, Float64Member>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, Float64Member b) {
			// TODO
			throw new IllegalArgumentException("TODO");
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixRound.compute(G.CDBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat64MatrixMember> ISNAN =
			new Function1<Boolean,ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a) {
			return MatrixIsNaN.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat64MatrixMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat64MatrixMember> NAN =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixNaN.compute(G.CDBL, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64MatrixMember> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,ComplexFloat64MatrixMember> ISINF =
			new Function1<Boolean,ComplexFloat64MatrixMember>()
	{
		@Override
		public Boolean call(ComplexFloat64MatrixMember a) {
			return MatrixIsInfinite.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat64MatrixMember> isInfinite() {
		return ISINF;
	}
	
	private final Procedure2<Boolean, ComplexFloat64MatrixMember> INF =
			new Procedure2<Boolean, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(Boolean positive, ComplexFloat64MatrixMember a) {
			MatrixInfinite.compute(G.CDBL, positive, a);
		}
	};
	
	@Override
	public Procedure2<Boolean, ComplexFloat64MatrixMember> infinite() {
		return INF;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> CONJ =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixConjugate.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> TRANSP =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixTranspose.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> CONJTRANSP =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			ComplexFloat64MatrixMember tmp = new ComplexFloat64MatrixMember();
			conjugate().call(a, tmp);
			transpose().call(tmp, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64Member> DET =
			new Procedure2<ComplexFloat64MatrixMember,ComplexFloat64Member>()
	{				
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64Member b) {
			MatrixDeterminant.compute(G.CDBL_MAT, G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64Member> det() {
		return DET;
	}

	private final Procedure1<ComplexFloat64MatrixMember> UNITY =
			new Procedure1<ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a) {
			MatrixUnity.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> INV =
			new Procedure2<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
			MatrixInvert.compute(G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> DIVIDE =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
			// invert and multiply
			ComplexFloat64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> DP =
			new Procedure3<ComplexFloat64MatrixMember, ComplexFloat64MatrixMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64MatrixMember in1, ComplexFloat64MatrixMember in2, ComplexFloat64MatrixMember out) {
			MatrixDirectProduct.compute(G.CDBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64MatrixMember,ComplexFloat64MatrixMember,ComplexFloat64MatrixMember> directProduct()
	{
		return DP;
	}

}
