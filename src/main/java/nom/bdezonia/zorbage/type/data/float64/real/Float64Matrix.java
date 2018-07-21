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
package nom.bdezonia.zorbage.type.data.float64.real;

import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixDirectProduct;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixIsInfinite;
import nom.bdezonia.zorbage.algorithm.MatrixIsNaN;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64Matrix
	implements
		RingWithUnity<Float64Matrix, Float64MatrixMember>,
		MatrixRing<Float64Matrix, Float64MatrixMember, Float64Group, Float64Member>,
		Constructible2dLong<Float64MatrixMember>,
		Rounding<Float64Member, Float64MatrixMember>,
		Norm<Float64MatrixMember,Float64Member>,
		DirectProduct<Float64MatrixMember, Float64MatrixMember>
{
	public Float64Matrix() { }

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> MUL =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixMultiply.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,Float64MatrixMember,Float64MatrixMember> POWER =
			new Procedure3<Integer, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Integer power, Float64MatrixMember a, Float64MatrixMember b) {
			MatrixPower.compute(power, G.DBL, G.DBL_VEC, G.DBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,Float64MatrixMember,Float64MatrixMember> power() {
		return POWER;
	}

	private final Procedure1<Float64MatrixMember> ZER =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			MatrixZero.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64MatrixMember> zero() {
		return ZER;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> NEG =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			MatrixNegate.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> ADD =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixAddition.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> add() {
		return ADD;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> SUB =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			MatrixSubtraction.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float64MatrixMember,Float64MatrixMember> EQ =
			new Function2<Boolean, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a, Float64MatrixMember b) {
			return MatrixEqual.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64MatrixMember,Float64MatrixMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64MatrixMember,Float64MatrixMember> NEQ =
			new Function2<Boolean, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a, Float64MatrixMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64MatrixMember,Float64MatrixMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> ASSIGN =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember from, Float64MatrixMember to) {
			MatrixAssign.compute(G.DBL, from, to);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> assign() {
		return ASSIGN;
	}

	@Override
	public Float64MatrixMember construct() {
		return new Float64MatrixMember();
	}

	@Override
	public Float64MatrixMember construct(Float64MatrixMember other) {
		return new Float64MatrixMember(other);
	}

	@Override
	public Float64MatrixMember construct(String s) {
		return new Float64MatrixMember(s);
	}

	@Override
	public Float64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new Float64MatrixMember(s, d1, d2);
	}

	private final Procedure2<Float64MatrixMember,Float64Member> NORM =
			new Procedure2<Float64MatrixMember, Float64Member>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64Member b) {
			// TODO
			throw new IllegalArgumentException("TODO");
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure4<Round.Mode,Float64Member,Float64MatrixMember,Float64MatrixMember> ROUND =
			new Procedure4<Round.Mode, Float64Member, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64MatrixMember a, Float64MatrixMember b) {
			MatrixRound.compute(G.DBL, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,Float64MatrixMember,Float64MatrixMember> round() {
		return ROUND;
	}

	private final Function1<Boolean,Float64MatrixMember> NAN =
			new Function1<Boolean, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a) {
			return MatrixIsNaN.compute(G.DBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,Float64MatrixMember> isNaN() {
		return NAN;
	}

	private final Function1<Boolean,Float64MatrixMember> INF =
			new Function1<Boolean, Float64MatrixMember>()
	{
		@Override
		public Boolean call(Float64MatrixMember a) {
			return MatrixIsInfinite.compute(G.DBL, a);
		}
	};
	
	@Override
	public Function1<Boolean,Float64MatrixMember> isInfinite() {
		return INF;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> CONJ =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> TRANSP =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			MatrixTranspose.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> transpose() {
		return TRANSP;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> CONJTRANSP =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			transpose().call(a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> conjugateTranspose() {
		return CONJTRANSP;
	}

	private final Procedure2<Float64MatrixMember,Float64Member> DET =
			new Procedure2<Float64MatrixMember, Float64Member>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64Member b) {
			MatrixDeterminant.compute(G.DBL_MAT, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64Member> det() {
		return DET;
	}

	private final Procedure1<Float64MatrixMember> UNITY =
			new Procedure1<Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a) {
			MatrixUnity.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64MatrixMember> unity() {
		return UNITY;
	}

	private final Procedure2<Float64MatrixMember,Float64MatrixMember> INV =
			new Procedure2<Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b) {
			MatrixInvert.compute(G.DBL, G.DBL_VEC, G.DBL_MAT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64MatrixMember,Float64MatrixMember> invert() {
		return INV;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> DIVIDE =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
			// invert and multiply
			Float64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
			invert().call(b, invB);
			multiply().call(a, invB, c);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> divide() {
		return DIVIDE;
	}

	private final Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> DP =
			new Procedure3<Float64MatrixMember, Float64MatrixMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64MatrixMember in1, Float64MatrixMember in2, Float64MatrixMember out) {
			MatrixDirectProduct.compute(G.DBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float64MatrixMember,Float64MatrixMember,Float64MatrixMember> directProduct() {
		return DP;
	}

}
