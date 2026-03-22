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
package nom.bdezonia.zorbage.type.real.float16;

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

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float16GeneralTensorProduct
	implements
		TensorProduct<Float16GeneralTensorProduct,Float16GeneralTensorProductMember,Float16Algebra,Float16Member>,
		Norm<Float16GeneralTensorProductMember,Float16Member>,
		Scale<Float16GeneralTensorProductMember,Float16Member>,
		Rounding<Float16Member,Float16GeneralTensorProductMember>,
		Infinite<Float16GeneralTensorProductMember>,
		NaN<Float16GeneralTensorProductMember>,
		ScaleByHighPrec<Float16GeneralTensorProductMember>,
		ScaleByRational<Float16GeneralTensorProductMember>,
		ScaleByDouble<Float16GeneralTensorProductMember>,
		ScaleByOneHalf<Float16GeneralTensorProductMember>,
		ScaleByTwo<Float16GeneralTensorProductMember>,
		Tolerance<Float16Member, Float16GeneralTensorProductMember>,
		ArrayLikeMethods<Float16GeneralTensorProductMember, Float16Member>,
		MadeOfElements<Float16Algebra,Float16Member>,
		ConstructibleTensor<Float16GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "16-bit based real tensor";
	}

	@Override
	public Float16GeneralTensorProductMember construct() {
		return new Float16GeneralTensorProductMember();
	}

	@Override
	public Float16GeneralTensorProductMember construct(Float16GeneralTensorProductMember other) {
		return new Float16GeneralTensorProductMember(other);
	}

	@Override
	public Float16GeneralTensorProductMember construct(String s) {
		return new Float16GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> EQ =
			new Function2<Boolean,Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> NEQ =
			new Function2<Boolean,Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> ASSIGN =
			new Procedure2<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember from, Float16GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.HLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<Float16GeneralTensorProductMember> ZER =
			new Procedure1<Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float16GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> NEG =
			new Procedure2<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.HLF, G.HLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> ADDEL =
			new Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> SUBEL =
			new Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<Float16GeneralTensorProductMember,Float16Member> NORM =
			new Procedure2<Float16GeneralTensorProductMember, Float16Member>() 
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.HLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<Float16GeneralTensorProductMember,Float16Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float16Member,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> SCALE =
			new Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.HLF, G.HLF.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float16Member,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.HLF, G.HLF.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<Float16Member,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			Float16Member tmp = G.HLF.construct();
			G.HLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> MULEL =
			new Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HLF, G.HLF.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> MUL =
			new Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16GeneralTensorProductMember,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorContract.compute(G.HLF, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> contract() {
		return CONTRACT;
	}

	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> POWER =
			new Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorPower.compute(G.HLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,Float16GeneralTensorProductMember,Float16GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<Float16GeneralTensorProductMember> UNITY =
			new Procedure1<Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember result) {
			TensorUnity.compute(G.HLF_TEN, G.HLF, result);
		}
	};
	
	@Override
	public Procedure1<Float16GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float16GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float16GeneralTensorProductMember> NAN =
			new Procedure1<Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a) {
			FillNaN.compute(G.HLF, a.rawData());
		}
	};
	
	@Override
	public Procedure1<Float16GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float16GeneralTensorProductMember> ISINF =
			new Function1<Boolean, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16GeneralTensorProductMember> INF =
			new Procedure1<Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a) {
			FillInfinite.compute(G.HLF, a.rawData());
		}
	};
			
	@Override
	public Procedure1<Float16GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorRound.compute(G.HLF_TEN, G.HLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float16GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> SBD =
			new Procedure3<Double, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.HLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16Member factor, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16Member factor, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			Float16Member invFactor = G.HLF.construct();
			G.HLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure4<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> RAISE =
		new Procedure4<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float16GeneralTensorProductMember metric, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.HLF, metric, idx, IndexType.CONTRAVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
	
	private final Procedure4<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, Float16GeneralTensorProductMember metric, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.HLF, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			Float16GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> OUTER =
			new Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b, Float16GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.HLF_TEN, G.HLF, a, b, c);

		}
	};
			
	@Override
	public Procedure3<Float16GeneralTensorProductMember, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.HLF_TEN, G.HLF, new Float16Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, Float16GeneralTensorProductMember a, Float16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.HLF_TEN, G.HLF, new Float16Member(0.5f), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float16GeneralTensorProductMember, Float16GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, Float16GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, Float16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.HLF, a);
		}
	};
	
	@Override
	public Function1<Boolean, Float16GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public Float16Algebra getElementAlgebra() {
		return G.HLF;
	}

	@Override
	public Float16GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new Float16GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
