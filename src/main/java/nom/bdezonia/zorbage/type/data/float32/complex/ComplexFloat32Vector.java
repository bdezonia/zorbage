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
package nom.bdezonia.zorbage.type.data.float32.complex;

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
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
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
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat32Vector
  implements
    VectorSpace<ComplexFloat32Vector,ComplexFloat32VectorMember,ComplexFloat32Algebra,ComplexFloat32Member>,
    Constructible1dLong<ComplexFloat32VectorMember>,
    Norm<ComplexFloat32VectorMember,Float32Member>,
    Products<ComplexFloat32VectorMember, ComplexFloat32Member, ComplexFloat32MatrixMember>,
    DirectProduct<ComplexFloat32VectorMember, ComplexFloat32MatrixMember>,
    Rounding<Float32Member,ComplexFloat32VectorMember>, Infinite<ComplexFloat32VectorMember>,
    NaN<ComplexFloat32VectorMember>,
    Tolerance<Float32Member,ComplexFloat32VectorMember>
{
	public ComplexFloat32Vector() { }
	
	private final Procedure1<ComplexFloat32VectorMember> ZER =
			new Procedure1<ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat32VectorMember,ComplexFloat32VectorMember> NEG =
			new Procedure2<ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			RModuleNegate.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32VectorMember,ComplexFloat32VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> ADD =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32VectorMember c) {
			RModuleAdd.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> SUB =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32VectorMember c) {
			RModuleSubtract.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat32VectorMember,ComplexFloat32VectorMember> EQ =
			new Function2<Boolean, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			return (Boolean) RModuleEqual.compute(G.CFLT, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat32VectorMember,ComplexFloat32VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat32VectorMember,ComplexFloat32VectorMember> NEQ =
			new Function2<Boolean, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat32VectorMember,ComplexFloat32VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat32VectorMember construct() {
		return new ComplexFloat32VectorMember();
	}

	@Override
	public ComplexFloat32VectorMember construct(ComplexFloat32VectorMember other) {
		return new ComplexFloat32VectorMember(other);
	}

	@Override
	public ComplexFloat32VectorMember construct(String s) {
		return new ComplexFloat32VectorMember(s);
	}

	@Override
	public ComplexFloat32VectorMember construct(StorageConstruction s, long d1) {
		return new ComplexFloat32VectorMember(s, d1);
	}

	private final Procedure2<ComplexFloat32VectorMember,ComplexFloat32VectorMember> ASSIGN =
			new Procedure2<ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember from, ComplexFloat32VectorMember to) {
			RModuleAssign.compute(G.CFLT, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32VectorMember,ComplexFloat32VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat32VectorMember,Float32Member> NORM =
			new Procedure2<ComplexFloat32VectorMember, Float32Member>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, Float32Member b) {
			ComplexFloat32Member aTmp = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member tmp = new ComplexFloat32Member();
			// TODO Look into preventing overflow. can do so similar to float case using norms
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.CFLT.conjugate().call(aTmp, tmp);
				G.CFLT.multiply().call(aTmp, tmp, tmp);
				G.CFLT.add().call(sum, tmp, sum);
			}
			b.setV((float) Math.sqrt(sum.r()));
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32VectorMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32VectorMember,ComplexFloat32VectorMember> SCALE =
			new Procedure3<ComplexFloat32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			RModuleScale.compute(G.CFLT, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32VectorMember,ComplexFloat32VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> CROSS =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32VectorMember c) {
			CrossProduct.compute(G.CFLT_VEC, G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32Member> DOT =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32Member c) {
			DotProduct.compute(G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32Member> PERP =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32Member c) {
			PerpDotProduct.compute(G.CFLT_VEC, G.CFLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> VTRIPLE =
			new Procedure4<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32VectorMember c,
				ComplexFloat32VectorMember d)
		{
			ComplexFloat32VectorMember b_cross_c = new ComplexFloat32VectorMember(new float[3*2]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32Member> STRIPLE =
			new Procedure4<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b, ComplexFloat32VectorMember c,
				ComplexFloat32Member d)
		{
			ComplexFloat32VectorMember b_cross_c = new ComplexFloat32VectorMember(new float[3*2]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexFloat32VectorMember,ComplexFloat32VectorMember> CONJ =
			new Procedure2<ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			RModuleConjugate.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32VectorMember, ComplexFloat32VectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32MatrixMember> VDP =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember in1, ComplexFloat32VectorMember in2, ComplexFloat32MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32MatrixMember> DP =
			new Procedure3<ComplexFloat32VectorMember, ComplexFloat32VectorMember, ComplexFloat32MatrixMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember in1, ComplexFloat32VectorMember in2, ComplexFloat32MatrixMember out) {
			RModuleDirectProduct.compute(G.CFLT, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat32VectorMember,ComplexFloat32VectorMember,ComplexFloat32MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexFloat32VectorMember> ISNAN =
			new Function1<Boolean, ComplexFloat32VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat32VectorMember a) {
			return SequenceIsNan.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat32VectorMember> NAN =
			new Procedure1<ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a) {
			RModuleNaN.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat32VectorMember> ISINF =
			new Function1<Boolean, ComplexFloat32VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat32VectorMember a) {
			return SequenceIsInf.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat32VectorMember> INF =
			new Procedure1<ComplexFloat32VectorMember>()
	{
		@Override
		public void call(ComplexFloat32VectorMember a) {
			RModuleInfinite.compute(G.CFLT, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember> ROUND =
			new Procedure4<Mode, Float32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			RModuleRound.compute(G.CFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat32VectorMember> ISZERO =
			new Function1<Boolean, ComplexFloat32VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat32VectorMember a) {
			return SequenceIsZero.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32VectorMember> isZero() {
		return ISZERO;
	}

	private final Function3<Boolean, Float32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember> WITHIN =
			new Function3<Boolean, Float32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember>()
	{
		@Override
		public Boolean call(Float32Member tol, ComplexFloat32VectorMember a, ComplexFloat32VectorMember b) {
			return SequencesSimilar.compute(G.CFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, ComplexFloat32VectorMember, ComplexFloat32VectorMember> within() {
		return WITHIN;
	}
}
