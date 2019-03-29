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
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleInfinite;
import nom.bdezonia.zorbage.algorithm.RModuleIsEqual;
import nom.bdezonia.zorbage.algorithm.RModuleIsZero;
import nom.bdezonia.zorbage.algorithm.RModuleNaN;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float16Vector
  implements
    VectorSpace<Float16Vector,Float16VectorMember,Float16Algebra,Float16Member>,
    Constructible1dLong<Float16VectorMember>,
	Norm<Float16VectorMember,Float16Member>,
	Products<Float16VectorMember, Float16Member, Float16MatrixMember>,
	DirectProduct<Float16VectorMember, Float16MatrixMember>,
	Rounding<Float16Member,Float16VectorMember>, Infinite<Float16VectorMember>,
	NaN<Float16VectorMember>
{
	private static final Float16Member ZERO = new Float16Member(0);

	public Float16Vector() { }
	
	private final Procedure1<Float16VectorMember> ZER =
			new Procedure1<Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a) {
			for (long i = 0; i < a.length(); i++)
				a.setV(i, ZERO);
		}
	};
	
	@Override
	public Procedure1<Float16VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<Float16VectorMember,Float16VectorMember> NEG =
			new Procedure2<Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b) {
			RModuleNegate.compute(G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16VectorMember,Float16VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> ADD =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			RModuleAdd.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> add() {
		return ADD;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> SUB =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			RModuleSubtract.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float16VectorMember,Float16VectorMember> EQ =
			new Function2<Boolean, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a, Float16VectorMember b) {
			return RModuleIsEqual.compute(G.HLF, a, b);
		}
	};

	@Override
	public Function2<Boolean,Float16VectorMember,Float16VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16VectorMember,Float16VectorMember> NEQ =
			new Function2<Boolean, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a, Float16VectorMember b) {
			return !isEqual().call(a,b);
		}
	};

	@Override
	public Function2<Boolean,Float16VectorMember,Float16VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float16VectorMember construct() {
		return new Float16VectorMember();
	}

	@Override
	public Float16VectorMember construct(Float16VectorMember other) {
		return new Float16VectorMember(other);
	}

	@Override
	public Float16VectorMember construct(String s) {
		return new Float16VectorMember(s);
	}

	@Override
	public Float16VectorMember construct(StorageConstruction s, long d1) {
		return new Float16VectorMember(s, d1);
	}

	private final Procedure2<Float16VectorMember,Float16VectorMember> ASSIGN =
			new Procedure2<Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember from, Float16VectorMember to) {
			RModuleAssign.compute(G.HLF, from, to);
		}
	};
	
	@Override
	public Procedure2<Float16VectorMember,Float16VectorMember> assign() {
		return ASSIGN;
	}

	private Procedure2<Float16VectorMember,Float16Member> NORM =
			new Procedure2<Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16Member b) {
			Float16Member max = new Float16Member();
			Float16Member tmp = new Float16Member();
			Float16Member norm2 = new Float16Member(0);
			for (long i = 0; i < a.length(); i++) {
				a.v(i, tmp);
				max.setV(Math.max(Math.abs(tmp.v()), max.v()));
			}
			for (long i = 0; i < a.length(); i++) {
				a.v(i, tmp);
				G.HLF.divide().call(tmp, max, tmp);
				G.HLF.multiply().call(tmp, tmp, tmp);
				G.HLF.add().call(norm2, tmp, norm2);
			}
			double norm = max.v() * Math.sqrt(norm2.v());
			b.setV(norm);
		}
	};
	
	@Override
	public Procedure2<Float16VectorMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure3<Float16Member,Float16VectorMember,Float16VectorMember> SCALE =
			new Procedure3<Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16VectorMember a, Float16VectorMember b) {
			RModuleScale.compute(G.HLF, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16VectorMember,Float16VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> CROSS =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			CrossProduct.compute(G.HLF_VEC, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> DOT =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16Member c) {
			DotProduct.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> PERP =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16Member c) {
			PerpDotProduct.compute(G.HLF_VEC, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16VectorMember> VTRIPLE =
			new Procedure4<Float16VectorMember, Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c, Float16VectorMember d) {
			Float16VectorMember b_cross_c = new Float16VectorMember(new double[3]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16Member> STRIPLE =
			new Procedure4<Float16VectorMember, Float16VectorMember, Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c, Float16Member d) {
			Float16VectorMember b_cross_c = new Float16VectorMember(new double[3]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<Float16VectorMember,Float16VectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> VDP =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16VectorMember in1, Float16VectorMember in2, Float16MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> DP =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16VectorMember in1, Float16VectorMember in2, Float16MatrixMember out) {
			RModuleDirectProduct.compute(G.HLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, Float16VectorMember> ISNAN =
			new Function1<Boolean, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a) {
			return SequenceIsNan.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float16VectorMember> NAN =
			new Procedure1<Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a) {
			RModuleNaN.compute(G.HLF, a);
		}
	};
	
	@Override
	public Procedure1<Float16VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float16VectorMember> ISINF =
			new Function1<Boolean, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a) {
			return SequenceIsInf.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16VectorMember> INF =
			new Procedure1<Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a) {
			RModuleInfinite.compute(G.HLF, a);
		}
	};

	@Override
	public Procedure1<Float16VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, Float16VectorMember, Float16VectorMember> ROUND =
			new Procedure4<Mode, Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16VectorMember a, Float16VectorMember b) {
			RModuleRound.compute(G.HLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, Float16VectorMember, Float16VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float16VectorMember> ISZERO =
			new Function1<Boolean, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a) {
			return RModuleIsZero.compute(G.HLF, a);
		}
	};

	@Override
	public Function1<Boolean, Float16VectorMember> isZero() {
		return ISZERO;
	}
}
