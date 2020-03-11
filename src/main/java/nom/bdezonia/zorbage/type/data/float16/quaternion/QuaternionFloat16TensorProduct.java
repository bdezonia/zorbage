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
package nom.bdezonia.zorbage.type.data.float16.quaternion;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
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
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.TensorLikeProduct;

//note that many implementations of tensors on the internet treat them as generalized matrices.
//they do not worry about upper and lower indices or even matching shapes. They do element by
//element ops like sin() of each elem. That will not be my approach.

//do I skip Vector and Matrix and even Scalar?

// this implies it is an OrderedField. Do tensors have an ordering? abs() exists in TensorFlow.
//@Override
//public void abs(TensorMember a, TensorMember b) {}
// tensorflow also has trigonometric and hyperbolic

//public void takeDiagonal(TensorMember a, Object b) {} // change Object to Vector
//many more

import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
omplex */
public class QuaternionFloat16TensorProduct
	implements
		TensorLikeProduct<QuaternionFloat16TensorProduct,QuaternionFloat16TensorProductMember,QuaternionFloat16Algebra,QuaternionFloat16Member>,
		ConstructibleNdLong<QuaternionFloat16TensorProductMember>,
		Norm<QuaternionFloat16TensorProductMember,Float16Member>,
		Scale<QuaternionFloat16TensorProductMember,QuaternionFloat16Member>,
		Rounding<Float16Member,QuaternionFloat16TensorProductMember>,
		Infinite<QuaternionFloat16TensorProductMember>,
		NaN<QuaternionFloat16TensorProductMember>,
		ScaleByHighPrec<QuaternionFloat16TensorProductMember>,
		ScaleByRational<QuaternionFloat16TensorProductMember>,
		ScaleByDouble<QuaternionFloat16TensorProductMember>,
		Tolerance<Float16Member, QuaternionFloat16TensorProductMember>
{
	@Override
	public QuaternionFloat16TensorProductMember construct() {
		return new QuaternionFloat16TensorProductMember();
	}

	@Override
	public QuaternionFloat16TensorProductMember construct(QuaternionFloat16TensorProductMember other) {
		return new QuaternionFloat16TensorProductMember(other);
	}

	@Override
	public QuaternionFloat16TensorProductMember construct(String s) {
		return new QuaternionFloat16TensorProductMember(s);
	}

	@Override
	public QuaternionFloat16TensorProductMember construct(StorageConstruction s, long[] nd) {
		return new QuaternionFloat16TensorProductMember(s, nd);
	}

	private final Function2<Boolean,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember from, QuaternionFloat16TensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.QHLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat16TensorProductMember> ZER =
			new Procedure1<QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16TensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> NEG =
			new Procedure2<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QHLF, G.QHLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat16TensorProductMember,Float16Member> NORM =
			new Procedure2<QuaternionFloat16TensorProductMember, Float16Member>() 
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.QHLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16TensorProductMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure2<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> CONJ =
			new Procedure2<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QHLF, G.QHLF.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> SCALE =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.QHLF, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.QHLF, scalar, G.QHLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			QuaternionFloat16Member tmp = G.QHLF.construct();
			G.QHLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> MULEL =
			new Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> MUL =
			new Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorContract.compute(G.QHLF, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> contract() {
		return CONTRACT;
	}
		
	private final Procedure1<Object> SEMI =
			new Procedure1<Object>()
	{
		@Override
		public void call(Object a) {
			TensorSemicolonDerivative.compute();
		}
	};
	
	@Override
	public Procedure1<Object> semicolonDerivative() {
		return SEMI;
	}
	
	// http://mathworld.wolfram.com/CommaDerivative.html
	
	private final Procedure3<IntegerIndex,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> COMMA =
			new Procedure3<IntegerIndex,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			QuaternionFloat16Member val = G.QHLF.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorPower.compute(G.QHLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat16TensorProductMember,QuaternionFloat16TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat16TensorProductMember> UNITY =
			new Procedure1<QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember result) {
			TensorUnity.compute(G.QHLF_TEN, G.QHLF, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat16TensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16TensorProductMember a) {
			return SequenceIsNan.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16TensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat16TensorProductMember> NAN =
			new Procedure1<QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a) {
			FillNaN.compute(G.QHLF, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16TensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat16TensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16TensorProductMember a) {
			return SequenceIsInf.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16TensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat16TensorProductMember> INF =
			new Procedure1<QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a) {
			FillInfinite.compute(G.QHLF, a);
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat16TensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorRound.compute(G.QHLF_TEN, G.QHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat16TensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16TensorProductMember a) {
			return SequenceIsZero.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16TensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member factor, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member factor, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			QuaternionFloat16Member invFactor = G.QHLF.construct();
			G.QHLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> RAISE =
		new Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> LOWER =
		new Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat16TensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> OUTER =
			new Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16TensorProductMember a, QuaternionFloat16TensorProductMember b, QuaternionFloat16TensorProductMember c) {

			// how an outer product is calculated:
			//   https://www.math3ma.com/blog/the-tensor-product-demystified
			
			if (c == a || c == b)
				throw new IllegalArgumentException("destination tensor cannot be one of the inputs");
			long dimA = a.dimension(0);
			long dimB = b.dimension(0);
			if (dimA != dimB)
				throw new IllegalArgumentException("dimension of tensors must match");
			int rankA = a.numDimensions();
			int rankB = b.numDimensions();
			int rankC = rankA + rankB;
			long[] cDims = new long[rankC];
			for (int i = 0; i < cDims.length; i++) {
				cDims[i] = dimA;
			}
			c.alloc(cDims);
			QuaternionFloat16Member aTmp = G.QHLF.construct();
			QuaternionFloat16Member bTmp = G.QHLF.construct();
			QuaternionFloat16Member cTmp = G.QHLF.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.QHLF.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember, QuaternionFloat16TensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
