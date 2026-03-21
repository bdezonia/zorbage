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
package nom.bdezonia.zorbage.type.real.float32;

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
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
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
public class Float32GeneralTensorProduct
	implements
		TensorProduct<Float32GeneralTensorProduct,Float32GeneralTensorProductMember,Float32Algebra,Float32Member>,
		Norm<Float32GeneralTensorProductMember,Float32Member>,
		Scale<Float32GeneralTensorProductMember,Float32Member>,
		Rounding<Float32Member,Float32GeneralTensorProductMember>,
		Infinite<Float32GeneralTensorProductMember>,
		NaN<Float32GeneralTensorProductMember>,
		ScaleByHighPrec<Float32GeneralTensorProductMember>,
		ScaleByRational<Float32GeneralTensorProductMember>,
		ScaleByDouble<Float32GeneralTensorProductMember>,
		ScaleByOneHalf<Float32GeneralTensorProductMember>,
		ScaleByTwo<Float32GeneralTensorProductMember>,
		Tolerance<Float32Member, Float32GeneralTensorProductMember>,
		ArrayLikeMethods<Float32GeneralTensorProductMember, Float32Member>,
		MadeOfElements<Float32Algebra,Float32Member>,
		ConstructibleTensor<Float32GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "32-bit based real tensor";
	}

	@Override
	public Float32GeneralTensorProductMember construct() {
		return new Float32GeneralTensorProductMember();
	}

	@Override
	public Float32GeneralTensorProductMember construct(Float32GeneralTensorProductMember other) {
		return new Float32GeneralTensorProductMember(other);
	}

	@Override
	public Float32GeneralTensorProductMember construct(String s) {
		return new Float32GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> EQ =
			new Function2<Boolean,Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.FLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> NEQ =
			new Function2<Boolean,Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> ASSIGN =
			new Procedure2<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember from, Float32GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.FLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float32GeneralTensorProductMember> ZER =
			new Procedure1<Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float32GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> NEG =
			new Procedure2<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.FLT, G.FLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> ADDEL =
			new Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.FLT, G.FLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> SUBEL =
			new Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.FLT, G.FLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float32GeneralTensorProductMember,Float32Member> NORM =
			new Procedure2<Float32GeneralTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.FLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float32GeneralTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float32Member,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> SCALE =
			new Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32Member scalar, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.FLT, G.FLT.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float32Member,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32Member scalar, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.FLT, G.FLT.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float32Member,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32Member scalar, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			Float32Member tmp = G.FLT.construct();
			G.FLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float32Member,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> MULEL =
			new Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.FLT, G.FLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.FLT, G.FLT.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> MUL =
			new Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float32GeneralTensorProductMember,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorContract.compute(G.FLT, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> SEMI =
			new Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.FLT_TEN, G.FLT, index, a, b);
		}
	};
	
	public Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> COMMA =
			new Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer index, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorCommaDerivative.compute(G.FLT_TEN, G.FLT, index, a, b);
		}
	};
	
	public Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> POWER =
			new Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorPower.compute(G.FLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float32GeneralTensorProductMember,Float32GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float32GeneralTensorProductMember> UNITY =
			new Procedure1<Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember result) {
			TensorUnity.compute(G.FLT_TEN, G.FLT, result);
		}
	};
	
	@Override
	public Procedure1<Float32GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float32GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.FLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float32GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float32GeneralTensorProductMember> NAN =
			new Procedure1<Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a) {
			FillNaN.compute(G.FLT, a.rawData());
		}
	};
	
	@Override
	public Procedure1<Float32GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float32GeneralTensorProductMember> ISINF =
			new Function1<Boolean, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.FLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float32GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float32GeneralTensorProductMember> INF =
			new Procedure1<Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a) {
			FillInfinite.compute(G.FLT, a.rawData());
		}
	};
			
	@Override
	public Procedure1<Float32GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorRound.compute(G.FLT_TEN, G.FLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float32GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.FLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float32GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.FLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> SBD =
			new Procedure3<Double, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.FLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.FLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.FLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32Member factor, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32Member factor, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			Float32Member invFactor = G.FLT.construct();
			G.FLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float32Member, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure4<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> RAISE =
		new Procedure4<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float32GeneralTensorProductMember inverseMetric, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			
			TensorFlipIndex.compute(G.FLT, inverseMetric, idx, IndexType.CONTRAVARIANT, a, b);
		}
	};

	@Override
	public Procedure4<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure4<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float32GeneralTensorProductMember metric, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			
			TensorFlipIndex.compute(G.FLT, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float32GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> OUTER =
			new Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b, Float32GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.FLT_TEN, G.FLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float32GeneralTensorProductMember, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.FLT_TEN, G.FLT, new Float32Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float32GeneralTensorProductMember a, Float32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.FLT_TEN, G.FLT, new Float32Member(0.5f), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float32GeneralTensorProductMember, Float32GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, Float32GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, Float32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.FLT, a);
		}
	};
	
	@Override
	public Function1<Boolean, Float32GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public Float32Algebra getElementAlgebra() {
		return G.FLT;
	}

	@Override
	public Float32GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new Float32GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
