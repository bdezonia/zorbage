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
package nom.bdezonia.zorbage.type.data.float32.real;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleInfinite;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNaN;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float32Vector
  implements
    VectorSpace<Float32Vector,Float32VectorMember,Float32Algebra,Float32Member>,
    Constructible1dLong<Float32VectorMember>,
	Norm<Float32VectorMember,Float32Member>,
	Products<Float32VectorMember, Float32Member, Float32MatrixMember>,
	DirectProduct<Float32VectorMember, Float32MatrixMember>,
	Rounding<Float32Member,Float32VectorMember>, Infinite<Float32VectorMember>,
	NaN<Float32VectorMember>,
	Tolerance<Float32Member, Float32VectorMember>
{
	public Float32Vector() { }
	
	private final Procedure1<Float32VectorMember> ZER =
			new Procedure1<Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<Float32VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<Float32VectorMember,Float32VectorMember> NEG =
			new Procedure2<Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b) {
			RModuleNegate.compute(G.FLT, a, b);
		}
	};
	
	@Override
	public Procedure2<Float32VectorMember,Float32VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32VectorMember> ADD =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32VectorMember c) {
			RModuleAdd.compute(G.FLT, a, b, c);
		}
	};

	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32VectorMember> add() {
		return ADD;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32VectorMember> SUB =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32VectorMember c) {
			RModuleSubtract.compute(G.FLT, a, b, c);
		}
	};

	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float32VectorMember,Float32VectorMember> EQ =
			new Function2<Boolean, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public Boolean call(Float32VectorMember a, Float32VectorMember b) {
			return (Boolean) RModuleEqual.compute(G.FLT, a, b);
		}
	};

	@Override
	public Function2<Boolean,Float32VectorMember,Float32VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float32VectorMember,Float32VectorMember> NEQ =
			new Function2<Boolean, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public Boolean call(Float32VectorMember a, Float32VectorMember b) {
			return !isEqual().call(a,b);
		}
	};

	@Override
	public Function2<Boolean,Float32VectorMember,Float32VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float32VectorMember construct() {
		return new Float32VectorMember();
	}

	@Override
	public Float32VectorMember construct(Float32VectorMember other) {
		return new Float32VectorMember(other);
	}

	@Override
	public Float32VectorMember construct(String s) {
		return new Float32VectorMember(s);
	}

	@Override
	public Float32VectorMember construct(StorageConstruction s, long d1) {
		return new Float32VectorMember(s, d1);
	}

	private final Procedure2<Float32VectorMember,Float32VectorMember> ASSIGN =
			new Procedure2<Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember from, Float32VectorMember to) {
			RModuleAssign.compute(G.FLT, from, to);
		}
	};
	
	@Override
	public Procedure2<Float32VectorMember,Float32VectorMember> assign() {
		return ASSIGN;
	}

	private Procedure2<Float32VectorMember,Float32Member> NORM =
			new Procedure2<Float32VectorMember, Float32Member>()
	{
		@Override
		public void call(Float32VectorMember a, Float32Member b) {
			Float32Member max = new Float32Member();
			Float32Member tmp = new Float32Member();
			Float32Member norm2 = new Float32Member(0);
			for (long i = 0; i < a.length(); i++) {
				a.v(i, tmp);
				max.setV(Math.max(Math.abs(tmp.v()), max.v()));
			}
			for (long i = 0; i < a.length(); i++) {
				a.v(i, tmp);
				G.FLT.divide().call(tmp, max, tmp);
				G.FLT.multiply().call(tmp, tmp, tmp);
				G.FLT.add().call(norm2, tmp, norm2);
			}
			double norm = max.v() * Math.sqrt(norm2.v());
			b.setV((float)norm);
		}
	};
	
	@Override
	public Procedure2<Float32VectorMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure3<Float32Member,Float32VectorMember,Float32VectorMember> SCALE =
			new Procedure3<Float32Member, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32Member scalar, Float32VectorMember a, Float32VectorMember b) {
			RModuleScale.compute(G.FLT, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32VectorMember,Float32VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32VectorMember> CROSS =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32VectorMember c) {
			CrossProduct.compute(G.FLT_VEC, G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32Member> DOT =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32Member>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32Member c) {
			DotProduct.compute(G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32Member> PERP =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32Member>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32Member c) {
			PerpDotProduct.compute(G.FLT_VEC, G.FLT, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<Float32VectorMember,Float32VectorMember,Float32VectorMember,Float32VectorMember> VTRIPLE =
			new Procedure4<Float32VectorMember, Float32VectorMember, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32VectorMember c, Float32VectorMember d) {
			Float32VectorMember b_cross_c = new Float32VectorMember(new float[3]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float32VectorMember,Float32VectorMember,Float32VectorMember,Float32VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<Float32VectorMember,Float32VectorMember,Float32VectorMember,Float32Member> STRIPLE =
			new Procedure4<Float32VectorMember, Float32VectorMember, Float32VectorMember, Float32Member>()
	{
		@Override
		public void call(Float32VectorMember a, Float32VectorMember b, Float32VectorMember c, Float32Member d) {
			Float32VectorMember b_cross_c = new Float32VectorMember(new float[3]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float32VectorMember,Float32VectorMember,Float32VectorMember,Float32Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<Float32VectorMember,Float32VectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32MatrixMember> VDP =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32VectorMember in1, Float32VectorMember in2, Float32MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<Float32VectorMember,Float32VectorMember,Float32MatrixMember> DP =
			new Procedure3<Float32VectorMember, Float32VectorMember, Float32MatrixMember>()
	{
		@Override
		public void call(Float32VectorMember in1, Float32VectorMember in2, Float32MatrixMember out) {
			RModuleDirectProduct.compute(G.FLT, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float32VectorMember,Float32VectorMember,Float32MatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, Float32VectorMember> ISNAN =
			new Function1<Boolean, Float32VectorMember>()
	{
		@Override
		public Boolean call(Float32VectorMember a) {
			return SequenceIsNan.compute(G.FLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float32VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float32VectorMember> NAN =
			new Procedure1<Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a) {
			RModuleNaN.compute(G.FLT, a);
		}
	};
	
	@Override
	public Procedure1<Float32VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float32VectorMember> ISINF =
			new Function1<Boolean, Float32VectorMember>()
	{
		@Override
		public Boolean call(Float32VectorMember a) {
			return SequenceIsInf.compute(G.FLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float32VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float32VectorMember> INF =
			new Procedure1<Float32VectorMember>()
	{
		@Override
		public void call(Float32VectorMember a) {
			RModuleInfinite.compute(G.FLT, a);
		}
	};

	@Override
	public Procedure1<Float32VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, Float32VectorMember, Float32VectorMember> ROUND =
			new Procedure4<Mode, Float32Member, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, Float32VectorMember a, Float32VectorMember b) {
			RModuleRound.compute(G.FLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, Float32VectorMember, Float32VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float32VectorMember> ISZERO =
			new Function1<Boolean, Float32VectorMember>()
	{
		@Override	
		public Boolean call(Float32VectorMember a) {
			return SequenceIsZero.compute(G.FLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float32VectorMember> isZero() {
		return ISZERO;
	}

	private final Function3<Boolean, Float32Member, Float32VectorMember, Float32VectorMember> WITHIN =
			new Function3<Boolean, Float32Member, Float32VectorMember, Float32VectorMember>()
	{
		@Override
		public Boolean call(Float32Member tol, Float32VectorMember a, Float32VectorMember b) {
			return SequencesSimilar.compute(G.FLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, Float32VectorMember, Float32VectorMember> within() {
		return WITHIN;
	}
}
