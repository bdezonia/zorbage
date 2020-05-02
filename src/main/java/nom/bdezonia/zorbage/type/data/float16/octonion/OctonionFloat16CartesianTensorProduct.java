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
package nom.bdezonia.zorbage.type.data.float16.octonion;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
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
import nom.bdezonia.zorbage.type.algebra.ArrayLikeMethods;
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
import nom.bdezonia.zorbage.type.data.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat16CartesianTensorProduct
	implements
		TensorLikeProduct<OctonionFloat16CartesianTensorProduct,OctonionFloat16CartesianTensorProductMember,OctonionFloat16Algebra,OctonionFloat16Member>,
		Norm<OctonionFloat16CartesianTensorProductMember,Float16Member>,
		Scale<OctonionFloat16CartesianTensorProductMember,OctonionFloat16Member>,
		Rounding<Float16Member,OctonionFloat16CartesianTensorProductMember>,
		Infinite<OctonionFloat16CartesianTensorProductMember>,
		NaN<OctonionFloat16CartesianTensorProductMember>,
		ScaleByHighPrec<OctonionFloat16CartesianTensorProductMember>,
		ScaleByRational<OctonionFloat16CartesianTensorProductMember>,
		ScaleByDouble<OctonionFloat16CartesianTensorProductMember>,
		Tolerance<Float16Member, OctonionFloat16CartesianTensorProductMember>,
		ArrayLikeMethods<OctonionFloat16CartesianTensorProductMember, OctonionFloat16Member>
{
	@Override
	public OctonionFloat16CartesianTensorProductMember construct() {
		return new OctonionFloat16CartesianTensorProductMember();
	}

	@Override
	public OctonionFloat16CartesianTensorProductMember construct(OctonionFloat16CartesianTensorProductMember other) {
		return new OctonionFloat16CartesianTensorProductMember(other);
	}

	@Override
	public OctonionFloat16CartesianTensorProductMember construct(String s) {
		return new OctonionFloat16CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember from, OctonionFloat16CartesianTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.OHLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat16CartesianTensorProductMember> ZER =
			new Procedure1<OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> NEG =
			new Procedure2<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHLF, G.OHLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> ADDEL =
			new Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHLF, G.OHLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> SUBEL =
			new Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHLF, G.OHLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat16CartesianTensorProductMember,Float16Member> NORM =
			new Procedure2<OctonionFloat16CartesianTensorProductMember, Float16Member>() 
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.OHLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat16CartesianTensorProductMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> CONJ =
			new Procedure2<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHLF, G.OHLF.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> SCALE =
			new Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.OHLF, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.OHLF, scalar, G.OHLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat16Member,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member scalar, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			OctonionFloat16Member tmp = G.OHLF.construct();
			G.OHLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> MULEL =
			new Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHLF, G.OHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHLF, G.OHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> MUL =
			new Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorContract.compute(G.OHLF, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.OHLF_TEN, G.OHLF, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.OHLF_TEN, G.OHLF, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorPower.compute(G.OHLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat16CartesianTensorProductMember,OctonionFloat16CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat16CartesianTensorProductMember> UNITY =
			new Procedure1<OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember result) {
			TensorUnity.compute(G.OHLF_TEN, G.OHLF, result);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat16CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat16CartesianTensorProductMember> NAN =
			new Procedure1<OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a) {
			FillNaN.compute(G.OHLF, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat16CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat16CartesianTensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat16CartesianTensorProductMember> INF =
			new Procedure1<OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a) {
			FillInfinite.compute(G.OHLF, a);
		}
	};
			
	@Override
	public Procedure1<OctonionFloat16CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorRound.compute(G.OHLF_TEN, G.OHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat16CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat16CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.OHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat16CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member factor, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16Member factor, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			OctonionFloat16Member invFactor = G.OHLF.construct();
			G.OHLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat16Member, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat16CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> OUTER =
			new Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat16CartesianTensorProductMember a, OctonionFloat16CartesianTensorProductMember b, OctonionFloat16CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.OHLF_TEN, G.OHLF, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember, OctonionFloat16CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
