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
package nom.bdezonia.zorbage.type.data.float16.complex;

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
import nom.bdezonia.zorbage.type.algebra.VectorSpace;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat16Vector
  implements
    VectorSpace<ComplexFloat16Vector,ComplexFloat16VectorMember,ComplexFloat16Algebra,ComplexFloat16Member>,
    Constructible1dLong<ComplexFloat16VectorMember>,
    Norm<ComplexFloat16VectorMember,Float16Member>,
    Products<ComplexFloat16VectorMember, ComplexFloat16Member, ComplexFloat16MatrixMember>,
    DirectProduct<ComplexFloat16VectorMember, ComplexFloat16MatrixMember>,
    Rounding<Float16Member,ComplexFloat16VectorMember>, Infinite<ComplexFloat16VectorMember>,
    NaN<ComplexFloat16VectorMember>
{
	public ComplexFloat16Vector() { }
	
	private final Procedure1<ComplexFloat16VectorMember> ZER =
			new Procedure1<ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> NEG =
			new Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleNegate.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> ADD =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleAdd.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> SUB =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleSubtract.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> EQ =
			new Function2<Boolean, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			return (Boolean) RModuleEqual.compute(G.CHLF, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> NEQ =
			new Function2<Boolean, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat16VectorMember construct() {
		return new ComplexFloat16VectorMember();
	}

	@Override
	public ComplexFloat16VectorMember construct(ComplexFloat16VectorMember other) {
		return new ComplexFloat16VectorMember(other);
	}

	@Override
	public ComplexFloat16VectorMember construct(String s) {
		return new ComplexFloat16VectorMember(s);
	}

	@Override
	public ComplexFloat16VectorMember construct(StorageConstruction s, long d1) {
		return new ComplexFloat16VectorMember(s, d1);
	}

	private final Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> ASSIGN =
			new Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember from, ComplexFloat16VectorMember to) {
			RModuleAssign.compute(G.CHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat16VectorMember,Float16Member> NORM =
			new Procedure2<ComplexFloat16VectorMember, Float16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, Float16Member b) {
			ComplexFloat16Member aTmp = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member tmp = new ComplexFloat16Member();
			// TODO Look into preventing overflow. can do so similar to float case using norms
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.CHLF.conjugate().call(aTmp, tmp);
				G.CHLF.multiply().call(aTmp, tmp, tmp);
				G.CHLF.add().call(sum, tmp, sum);
			}
			b.setV(Math.sqrt(sum.r()));
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16VectorMember,ComplexFloat16VectorMember> SCALE =
			new Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleScale.compute(G.CHLF, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16VectorMember,ComplexFloat16VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> CROSS =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			CrossProduct.compute(G.CHLF_VEC, G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> DOT =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16Member c) {
			DotProduct.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> PERP =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16Member c) {
			PerpDotProduct.compute(G.CHLF_VEC, G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> VTRIPLE =
			new Procedure4<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c,
				ComplexFloat16VectorMember d)
		{
			ComplexFloat16VectorMember b_cross_c = new ComplexFloat16VectorMember(new double[3*2]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> STRIPLE =
			new Procedure4<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c,
				ComplexFloat16Member d)
		{
			ComplexFloat16VectorMember b_cross_c = new ComplexFloat16VectorMember(new double[3*2]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> CONJ =
			new Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleConjugate.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> VDP =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember in1, ComplexFloat16VectorMember in2, ComplexFloat16MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> DP =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember in1, ComplexFloat16VectorMember in2, ComplexFloat16MatrixMember out) {
			RModuleDirectProduct.compute(G.CHLF, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexFloat16VectorMember> ISNAN =
			new Function1<Boolean, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a) {
			return SequenceIsNan.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat16VectorMember> NAN =
			new Procedure1<ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a) {
			RModuleNaN.compute(G.CHLF, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat16VectorMember> ISINF =
			new Function1<Boolean, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a) {
			return SequenceIsInf.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat16VectorMember> INF =
			new Procedure1<ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a) {
			RModuleInfinite.compute(G.CHLF, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> ROUND =
			new Procedure4<Mode, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleRound.compute(G.CHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat16VectorMember> ISZERO =
			new Function1<Boolean, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a) {
			return SequenceIsZero.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16VectorMember> isZero() {
		return ISZERO;
	}
}
