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
package nom.bdezonia.zorbage.type.float64.octonion;

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

import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64CartesianTensorProduct
	implements
		TensorLikeProduct<OctonionFloat64CartesianTensorProduct,OctonionFloat64CartesianTensorProductMember,OctonionFloat64Algebra,OctonionFloat64Member>,
		Norm<OctonionFloat64CartesianTensorProductMember,Float64Member>,
		Scale<OctonionFloat64CartesianTensorProductMember,OctonionFloat64Member>,
		Rounding<Float64Member,OctonionFloat64CartesianTensorProductMember>,
		Infinite<OctonionFloat64CartesianTensorProductMember>,
		NaN<OctonionFloat64CartesianTensorProductMember>,
		ScaleByHighPrec<OctonionFloat64CartesianTensorProductMember>,
		ScaleByRational<OctonionFloat64CartesianTensorProductMember>,
		ScaleByDouble<OctonionFloat64CartesianTensorProductMember>,
		ScaleByOneHalf<OctonionFloat64CartesianTensorProductMember>,
		ScaleByTwo<OctonionFloat64CartesianTensorProductMember>,
		Tolerance<Float64Member, OctonionFloat64CartesianTensorProductMember>,
		ArrayLikeMethods<OctonionFloat64CartesianTensorProductMember, OctonionFloat64Member>
{
	@Override
	public OctonionFloat64CartesianTensorProductMember construct() {
		return new OctonionFloat64CartesianTensorProductMember();
	}

	@Override
	public OctonionFloat64CartesianTensorProductMember construct(OctonionFloat64CartesianTensorProductMember other) {
		return new OctonionFloat64CartesianTensorProductMember(other);
	}

	@Override
	public OctonionFloat64CartesianTensorProductMember construct(String s) {
		return new OctonionFloat64CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.ODBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember from, OctonionFloat64CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.ODBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat64CartesianTensorProductMember> ZER =
			new Procedure1<OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> NEG =
			new Procedure2<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.ODBL, G.ODBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> ADDEL =
			new Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> SUBEL =
			new Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat64CartesianTensorProductMember,Float64Member> NORM =
			new Procedure2<OctonionFloat64CartesianTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.ODBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64CartesianTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> CONJ =
			new Procedure2<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.ODBL, G.ODBL.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> SCALE =
			new Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.ODBL, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.ODBL, scalar, G.ODBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			OctonionFloat64Member tmp = G.ODBL.construct();
			G.ODBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> MULEL =
			new Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> MUL =
			new Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorContract.compute(G.ODBL, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.ODBL_TEN, G.ODBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.ODBL_TEN, G.ODBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorPower.compute(G.ODBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat64CartesianTensorProductMember,OctonionFloat64CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat64CartesianTensorProductMember> UNITY =
			new Procedure1<OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember result) {
			TensorUnity.compute(G.ODBL_TEN, G.ODBL, result);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat64CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat64CartesianTensorProductMember> NAN =
			new Procedure1<OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a) {
			FillNaN.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat64CartesianTensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat64CartesianTensorProductMember> INF =
			new Procedure1<OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a) {
			FillInfinite.compute(G.ODBL, a);
		}
	};
			
	@Override
	public Procedure1<OctonionFloat64CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorRound.compute(G.ODBL_TEN, G.ODBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat64CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.ODBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.ODBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.ODBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.ODBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member factor, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member factor, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			OctonionFloat64Member invFactor = G.ODBL.construct();
			G.ODBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat64CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> OUTER =
			new Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b, OctonionFloat64CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.ODBL_TEN, G.ODBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			ScaleHelper.compute(G.ODBL_TEN, G.ODBL, new OctonionFloat64Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat64CartesianTensorProductMember a, OctonionFloat64CartesianTensorProductMember b) {
			ScaleHelper.compute(G.ODBL_TEN, G.ODBL, new OctonionFloat64Member(0.5, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat64CartesianTensorProductMember, OctonionFloat64CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

}
