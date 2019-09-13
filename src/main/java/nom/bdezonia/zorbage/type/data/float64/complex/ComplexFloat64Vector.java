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
package nom.bdezonia.zorbage.type.data.float64.complex;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleInfinite;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNaN;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
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
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Products;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.VectorSpace;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Vector
  implements
    VectorSpace<ComplexFloat64Vector,ComplexFloat64VectorMember,ComplexFloat64Algebra,ComplexFloat64Member>,
    Constructible1dLong<ComplexFloat64VectorMember>,
    Norm<ComplexFloat64VectorMember,Float64Member>,
    Products<ComplexFloat64VectorMember, ComplexFloat64Member, ComplexFloat64MatrixMember>,
    DirectProduct<ComplexFloat64VectorMember, ComplexFloat64MatrixMember>,
    Rounding<Float64Member,ComplexFloat64VectorMember>, Infinite<ComplexFloat64VectorMember>,
    NaN<ComplexFloat64VectorMember>,
    Tolerance<Float64Member,ComplexFloat64VectorMember>
{
	public ComplexFloat64Vector() { }
	
	private final Procedure1<ComplexFloat64VectorMember> ZER =
			new Procedure1<ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> NEG =
			new Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleNegate.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> ADD =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleAdd.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> SUB =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleSubtract.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> EQ =
			new Function2<Boolean, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			return (Boolean) RModuleEqual.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> NEQ =
			new Function2<Boolean, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat64VectorMember construct() {
		return new ComplexFloat64VectorMember();
	}

	@Override
	public ComplexFloat64VectorMember construct(ComplexFloat64VectorMember other) {
		return new ComplexFloat64VectorMember(other);
	}

	@Override
	public ComplexFloat64VectorMember construct(String s) {
		return new ComplexFloat64VectorMember(s);
	}

	@Override
	public ComplexFloat64VectorMember construct(StorageConstruction s, long d1) {
		return new ComplexFloat64VectorMember(s, d1);
	}

	private final Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> ASSIGN =
			new Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember from, ComplexFloat64VectorMember to) {
			RModuleAssign.compute(G.CDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat64VectorMember,Float64Member> NORM =
			new Procedure2<ComplexFloat64VectorMember, Float64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, Float64Member b) {
			ComplexFloat64Member aTmp = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member tmp = new ComplexFloat64Member();
			// TODO Look into preventing overflow. can do so similar to float case using norms
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.CDBL.conjugate().call(aTmp, tmp);
				G.CDBL.multiply().call(aTmp, tmp, tmp);
				G.CDBL.add().call(sum, tmp, sum);
			}
			b.setV(Math.sqrt(sum.r()));
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64VectorMember,ComplexFloat64VectorMember> SCALE =
			new Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleScale.compute(G.CDBL, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64VectorMember,ComplexFloat64VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> CROSS =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			CrossProduct.compute(G.CDBL_VEC, G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> DOT =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
			DotProduct.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> PERP =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
			PerpDotProduct.compute(G.CDBL_VEC, G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> VTRIPLE =
			new Procedure4<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
				ComplexFloat64VectorMember d)
		{
			ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3*2]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> STRIPLE =
			new Procedure4<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
				ComplexFloat64Member d)
		{
			ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3*2]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> CONJ =
			new Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleConjugate.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> VDP =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember in1, ComplexFloat64VectorMember in2, ComplexFloat64MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> DP =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember in1, ComplexFloat64VectorMember in2, ComplexFloat64MatrixMember out) {
			RModuleDirectProduct.compute(G.CDBL, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexFloat64VectorMember> ISNAN =
			new Function1<Boolean, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a) {
			return SequenceIsNan.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat64VectorMember> NAN =
			new Procedure1<ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a) {
			RModuleNaN.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat64VectorMember> ISINF =
			new Function1<Boolean, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a) {
			return SequenceIsInf.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat64VectorMember> INF =
			new Procedure1<ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a) {
			RModuleInfinite.compute(G.CDBL, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> ROUND =
			new Procedure4<Mode, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleRound.compute(G.CDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat64VectorMember> ISZERO =
			new Function1<Boolean, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a) {
			return SequenceIsZero.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64VectorMember> isZero() {
		return ISZERO;
	}

	private final Function3<Boolean, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> WITHIN =
			new Function3<Boolean, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(Float64Member tol, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			ComplexFloat64Member elemA = G.CDBL.construct();
			ComplexFloat64Member elemB = G.CDBL.construct();
			if (a.length() != b.length())
				return false;
			for (long i = 0; i < a.length(); i++) {
				a.v(i, elemA);
				b.v(i, elemB);
				if (!G.CDBL.within().call(tol, elemA, elemB))
					return false;
			}
			return true;
		}
	};

	@Override
	public Function3<Boolean, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> within() {
		return WITHIN;
	}
}
