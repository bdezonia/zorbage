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
package nom.bdezonia.zorbage.type.float32.octonion;

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

import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat32CartesianTensorProduct
	implements
		TensorLikeProduct<OctonionFloat32CartesianTensorProduct,OctonionFloat32CartesianTensorProductMember,OctonionFloat32Algebra,OctonionFloat32Member>,
		Norm<OctonionFloat32CartesianTensorProductMember,Float32Member>,
		Scale<OctonionFloat32CartesianTensorProductMember,OctonionFloat32Member>,
		Rounding<Float32Member,OctonionFloat32CartesianTensorProductMember>,
		Infinite<OctonionFloat32CartesianTensorProductMember>,
		NaN<OctonionFloat32CartesianTensorProductMember>,
		ScaleByHighPrec<OctonionFloat32CartesianTensorProductMember>,
		ScaleByRational<OctonionFloat32CartesianTensorProductMember>,
		ScaleByDouble<OctonionFloat32CartesianTensorProductMember>,
		ScaleByOneHalf<OctonionFloat32CartesianTensorProductMember>,
		ScaleByTwo<OctonionFloat32CartesianTensorProductMember>,
		Tolerance<Float32Member, OctonionFloat32CartesianTensorProductMember>,
		ArrayLikeMethods<OctonionFloat32CartesianTensorProductMember, OctonionFloat32Member>
{
	@Override
	public OctonionFloat32CartesianTensorProductMember construct() {
		return new OctonionFloat32CartesianTensorProductMember();
	}

	@Override
	public OctonionFloat32CartesianTensorProductMember construct(OctonionFloat32CartesianTensorProductMember other) {
		return new OctonionFloat32CartesianTensorProductMember(other);
	}

	@Override
	public OctonionFloat32CartesianTensorProductMember construct(String s) {
		return new OctonionFloat32CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember from, OctonionFloat32CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.OFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat32CartesianTensorProductMember> ZER =
			new Procedure1<OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> NEG =
			new Procedure2<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OFLT, G.OFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> ADDEL =
			new Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> SUBEL =
			new Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat32CartesianTensorProductMember,Float32Member> NORM =
			new Procedure2<OctonionFloat32CartesianTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.OFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32CartesianTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> CONJ =
			new Procedure2<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OFLT, G.OFLT.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> SCALE =
			new Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.OFLT, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.OFLT, scalar, G.OFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			OctonionFloat32Member tmp = G.OFLT.construct();
			G.OFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> MULEL =
			new Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> MUL =
			new Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorContract.compute(G.OFLT, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
		
	private final Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.OFLT_TEN, G.OFLT, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.OFLT_TEN, G.OFLT, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorPower.compute(G.OFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat32CartesianTensorProductMember,OctonionFloat32CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat32CartesianTensorProductMember> UNITY =
			new Procedure1<OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember result) {
			TensorUnity.compute(G.OFLT_TEN, G.OFLT, result);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat32CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat32CartesianTensorProductMember> NAN =
			new Procedure1<OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a) {
			FillNaN.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat32CartesianTensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat32CartesianTensorProductMember> INF =
			new Procedure1<OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a) {
			FillInfinite.compute(G.OFLT, a);
		}
	};
			
	@Override
	public Procedure1<OctonionFloat32CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorRound.compute(G.OFLT_TEN, G.OFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat32CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member factor, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member factor, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			OctonionFloat32Member invFactor = G.OFLT.construct();
			G.OFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat32CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> OUTER =
			new Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b, OctonionFloat32CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.OFLT_TEN, G.OFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OFLT_TEN, G.OFLT, new OctonionFloat32Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat32CartesianTensorProductMember a, OctonionFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OFLT_TEN, G.OFLT, new OctonionFloat32Member(0.5f, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat32CartesianTensorProductMember, OctonionFloat32CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

}
