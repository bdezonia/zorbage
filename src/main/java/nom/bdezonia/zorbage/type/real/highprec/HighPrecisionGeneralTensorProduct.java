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
package nom.bdezonia.zorbage.type.real.highprec;

import java.lang.Integer;
import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
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

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionGeneralTensorProduct
	implements
		TensorProduct<HighPrecisionGeneralTensorProduct,HighPrecisionGeneralTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		Norm<HighPrecisionGeneralTensorProductMember,HighPrecisionMember>,
		Scale<HighPrecisionGeneralTensorProductMember,HighPrecisionMember>,
		ScaleByHighPrec<HighPrecisionGeneralTensorProductMember>,
		ScaleByRational<HighPrecisionGeneralTensorProductMember>,
		ScaleByDouble<HighPrecisionGeneralTensorProductMember>,
		ScaleByOneHalf<HighPrecisionGeneralTensorProductMember>,
		ScaleByTwo<HighPrecisionGeneralTensorProductMember>,
		Tolerance<HighPrecisionMember, HighPrecisionGeneralTensorProductMember>,
		ArrayLikeMethods<HighPrecisionGeneralTensorProductMember, HighPrecisionMember>,
		MadeOfElements<HighPrecisionAlgebra,HighPrecisionMember>,
		ConstructibleTensor<HighPrecisionGeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "128-bit based real tensor";
	}

	@Override
	public HighPrecisionGeneralTensorProductMember construct() {
		return new HighPrecisionGeneralTensorProductMember();
	}

	@Override
	public HighPrecisionGeneralTensorProductMember construct(HighPrecisionGeneralTensorProductMember other) {
		return new HighPrecisionGeneralTensorProductMember(other);
	}

	@Override
	public HighPrecisionGeneralTensorProductMember construct(String s) {
		return new HighPrecisionGeneralTensorProductMember(s);
	}

	private final Function2<Boolean,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> EQ =
			new Function2<Boolean,HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> NEQ =
			new Function2<Boolean,HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> ASSIGN =
			new Procedure2<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember from, HighPrecisionGeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.HP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<HighPrecisionGeneralTensorProductMember> ZER =
			new Procedure1<HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<HighPrecisionGeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> NEG =
			new Procedure2<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.HP, G.HP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> ADDEL =
			new Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> SUBEL =
			new Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionGeneralTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.HP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	@Override
	public Procedure2<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> SCALE =
			new Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.HP, G.HP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> ADDSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.HP, G.HP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> SUBSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			HighPrecisionMember tmp = G.HP.construct();
			G.HP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> MULEL =
			new Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> DIVIDEEL =
			new Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> MUL =
			new Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorContract.compute(G.HP, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> POWER =
			new Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorPower.compute(G.HP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,HighPrecisionGeneralTensorProductMember,HighPrecisionGeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<HighPrecisionGeneralTensorProductMember> UNITY =
			new Procedure1<HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember result) {
			TensorUnity.compute(G.HP_TEN, G.HP, result);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionGeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, HighPrecisionGeneralTensorProductMember> ISZERO =
			new Function1<Boolean, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionGeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.HP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionGeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> SBD =
			new Procedure3<Double, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			HighPrecisionMember invFactor = G.HP.construct();
			G.HP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure4<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> RAISE =
			new Procedure4<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, HighPrecisionGeneralTensorProductMember metric, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.HP, metric, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> LOWER =
		new Procedure4<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, HighPrecisionGeneralTensorProductMember metric, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.HP, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			HighPrecisionGeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> OUTER =
			new Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b, HighPrecisionGeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.HP_TEN, G.HP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.HP_TEN, G.HP, new HighPrecisionMember(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> SCBH =
			new Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, HighPrecisionGeneralTensorProductMember a, HighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.HP_TEN, G.HP, new HighPrecisionMember(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionGeneralTensorProductMember, HighPrecisionGeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, HighPrecisionGeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, HighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionGeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.HP, a);
		}
	};
	
	@Override
	public Function1<Boolean, HighPrecisionGeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public HighPrecisionAlgebra getElementAlgebra() {
		return G.HP;
	}

	@Override
	public HighPrecisionGeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new HighPrecisionGeneralTensorProductMember(indexTypes, axisSizes);
	}
}
