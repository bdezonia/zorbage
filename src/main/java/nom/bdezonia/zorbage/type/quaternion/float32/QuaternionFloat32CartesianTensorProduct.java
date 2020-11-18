/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.quaternion.float32;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
import nom.bdezonia.zorbage.algorithm.TensorShape;
import nom.bdezonia.zorbage.algorithm.TensorUnity;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat32CartesianTensorProduct
	implements
		TensorLikeProduct<QuaternionFloat32CartesianTensorProduct,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32Algebra,QuaternionFloat32Member>,
		Norm<QuaternionFloat32CartesianTensorProductMember,Float32Member>,
		Scale<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32Member>,
		Rounding<Float32Member,QuaternionFloat32CartesianTensorProductMember>,
		Infinite<QuaternionFloat32CartesianTensorProductMember>,
		NaN<QuaternionFloat32CartesianTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat32CartesianTensorProductMember>,
		ScaleByRational<QuaternionFloat32CartesianTensorProductMember>,
		ScaleByDouble<QuaternionFloat32CartesianTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat32CartesianTensorProductMember>,
		ScaleByTwo<QuaternionFloat32CartesianTensorProductMember>,
		Tolerance<Float32Member, QuaternionFloat32CartesianTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32Member>
{
	@Override
	public QuaternionFloat32CartesianTensorProductMember construct() {
		return new QuaternionFloat32CartesianTensorProductMember();
	}

	@Override
	public QuaternionFloat32CartesianTensorProductMember construct(QuaternionFloat32CartesianTensorProductMember other) {
		return new QuaternionFloat32CartesianTensorProductMember(other);
	}

	@Override
	public QuaternionFloat32CartesianTensorProductMember construct(String s) {
		return new QuaternionFloat32CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember from, QuaternionFloat32CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat32CartesianTensorProductMember> ZER =
			new Procedure1<QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> NEG =
			new Procedure2<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QFLT, G.QFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat32CartesianTensorProductMember,Float32Member> NORM =
			new Procedure2<QuaternionFloat32CartesianTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.QFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32CartesianTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure2<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> CONJ =
			new Procedure2<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QFLT, G.QFLT.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.QFLT, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.QFLT, scalar, G.QFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			QuaternionFloat32Member tmp = G.QFLT.construct();
			G.QFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> MUL =
			new Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorContract.compute(G.QFLT, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.QFLT_TEN, G.QFLT, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.QFLT_TEN, G.QFLT, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorPower.compute(G.QFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat32CartesianTensorProductMember,QuaternionFloat32CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat32CartesianTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember result) {
			TensorUnity.compute(G.QFLT_TEN, G.QFLT, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat32CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat32CartesianTensorProductMember> NAN =
			new Procedure1<QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a) {
			FillNaN.compute(G.QFLT, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat32CartesianTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat32CartesianTensorProductMember> INF =
			new Procedure1<QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a) {
			FillInfinite.compute(G.QFLT, a);
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat32CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorRound.compute(G.QFLT_TEN, G.QFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat32CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member factor, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member factor, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			QuaternionFloat32Member invFactor = G.QFLT.construct();
			G.QFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat32CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b, QuaternionFloat32CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.QFLT_TEN, G.QFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QFLT_TEN, G.QFLT, new QuaternionFloat32Member(2, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat32CartesianTensorProductMember a, QuaternionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QFLT_TEN, G.QFLT, new QuaternionFloat32Member(0.5f, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat32CartesianTensorProductMember, QuaternionFloat32CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}
}
