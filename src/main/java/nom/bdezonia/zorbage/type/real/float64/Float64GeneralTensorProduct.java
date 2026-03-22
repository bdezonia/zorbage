/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.real.float64;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorShape;
import nom.bdezonia.zorbage.algorithm.TensorUnity;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64GeneralTensorProduct
	implements
		TensorProduct<Float64GeneralTensorProduct,Float64GeneralTensorProductMember,Float64Algebra,Float64Member>,
		Norm<Float64GeneralTensorProductMember,Float64Member>,
		Scale<Float64GeneralTensorProductMember,Float64Member>,
		Rounding<Float64Member,Float64GeneralTensorProductMember>,
		Infinite<Float64GeneralTensorProductMember>,
		NaN<Float64GeneralTensorProductMember>,
		ScaleByHighPrec<Float64GeneralTensorProductMember>,
		ScaleByRational<Float64GeneralTensorProductMember>,
		ScaleByDouble<Float64GeneralTensorProductMember>,
		ScaleByOneHalf<Float64GeneralTensorProductMember>,
		ScaleByTwo<Float64GeneralTensorProductMember>,
		Tolerance<Float64Member, Float64GeneralTensorProductMember>,
		ArrayLikeMethods<Float64GeneralTensorProductMember, Float64Member>,
		MadeOfElements<Float64Algebra,Float64Member>,
		ConstructibleTensor<Float64GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "64-bit based real tensor";
	}

	@Override
	public Float64GeneralTensorProductMember construct() {
		return new Float64GeneralTensorProductMember();
	}

	@Override
	public Float64GeneralTensorProductMember construct(Float64GeneralTensorProductMember other) {
		return new Float64GeneralTensorProductMember(other);
	}

	@Override
	public Float64GeneralTensorProductMember construct(String s) {
		return new Float64GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> EQ =
			new Function2<Boolean,Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.DBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> NEQ =
			new Function2<Boolean,Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> ASSIGN =
			new Procedure2<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember from, Float64GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.DBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float64GeneralTensorProductMember> ZER =
			new Procedure1<Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float64GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> NEG =
			new Procedure2<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.DBL, G.DBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> ADDEL =
			new Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> SUBEL =
			new Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float64GeneralTensorProductMember,Float64Member> NORM =
			new Procedure2<Float64GeneralTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.DBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float64GeneralTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float64Member,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> SCALE =
			new Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.DBL, G.DBL.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float64Member,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.DBL, G.DBL.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float64Member,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			Float64Member tmp = G.DBL.construct();
			G.DBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> MULEL =
			new Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.DBL, G.DBL.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> MUL =
			new Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64GeneralTensorProductMember,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorContract.compute(G.DBL, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> POWER =
			new Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorPower.compute(G.DBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float64GeneralTensorProductMember,Float64GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float64GeneralTensorProductMember> UNITY =
			new Procedure1<Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember result) {
			TensorUnity.compute(G.DBL_TEN, G.DBL, result);
		}
	};
	
	@Override
	public Procedure1<Float64GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float64GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float64GeneralTensorProductMember> NAN =
			new Procedure1<Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a) {
			FillNaN.compute(G.DBL, a.rawData());
		}
	};
	
	@Override
	public Procedure1<Float64GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float64GeneralTensorProductMember> ISINF =
			new Function1<Boolean, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float64GeneralTensorProductMember> INF =
			new Procedure1<Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a) {
			FillInfinite.compute(G.DBL, a.rawData());
		}
	};
			
	@Override
	public Procedure1<Float64GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorRound.compute(G.DBL_TEN, G.DBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float64GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> SBD =
			new Procedure3<Double, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.DBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.DBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64Member factor, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64Member factor, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			Float64Member invFactor = G.DBL.construct();
			G.DBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure4<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> RAISE =
		new Procedure4<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float64GeneralTensorProductMember inverseMetric, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			
			TensorFlipIndex.compute(G.DBL, inverseMetric, idx, IndexType.CONTRAVARIANT, a, b);
		}
	};

	@Override
	public Procedure4<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure4<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float64GeneralTensorProductMember metric, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			
			TensorFlipIndex.compute(G.DBL, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float64GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> OUTER =
			new Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b, Float64GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.DBL_TEN, G.DBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float64GeneralTensorProductMember, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.DBL_TEN, G.DBL, new Float64Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float64GeneralTensorProductMember a, Float64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.DBL_TEN, G.DBL, new Float64Member(0.5), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64GeneralTensorProductMember, Float64GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, Float64GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, Float64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.DBL, a);
		}
	};
	
	@Override
	public Function1<Boolean, Float64GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public Float64Algebra getElementAlgebra() {
		return G.DBL;
	}

	@Override
	public Float64GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new Float64GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
