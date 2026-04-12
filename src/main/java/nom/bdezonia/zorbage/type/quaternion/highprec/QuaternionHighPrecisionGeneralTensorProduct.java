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
package nom.bdezonia.zorbage.type.quaternion.highprec;

import java.lang.Integer;

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
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionHighPrecisionGeneralTensorProduct
	implements
		TensorProduct<QuaternionHighPrecisionGeneralTensorProduct,QuaternionHighPrecisionGeneralTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		Norm<QuaternionHighPrecisionGeneralTensorProductMember,HighPrecisionMember>,
		Scale<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionMember>,
		ScaleByHighPrec<QuaternionHighPrecisionGeneralTensorProductMember>,
		ScaleByRational<QuaternionHighPrecisionGeneralTensorProductMember>,
		ScaleByDouble<QuaternionHighPrecisionGeneralTensorProductMember>,
		ScaleByOneHalf<QuaternionHighPrecisionGeneralTensorProductMember>,
		ScaleByTwo<QuaternionHighPrecisionGeneralTensorProductMember>,
		Tolerance<HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember>,
		ArrayLikeMethods<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionMember>,
		MadeOfElements<QuaternionHighPrecisionAlgebra,QuaternionHighPrecisionMember>,
		ConstructibleTensor<QuaternionHighPrecisionGeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision complex tensor";
	}

	@Override
	public QuaternionHighPrecisionGeneralTensorProductMember construct() {
		return new QuaternionHighPrecisionGeneralTensorProductMember();
	}

	@Override
	public QuaternionHighPrecisionGeneralTensorProductMember construct(QuaternionHighPrecisionGeneralTensorProductMember other) {
		return new QuaternionHighPrecisionGeneralTensorProductMember(other);
	}

	@Override
	public QuaternionHighPrecisionGeneralTensorProductMember construct(String s) {
		return new QuaternionHighPrecisionGeneralTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> EQ =
			new Function2<Boolean,QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> ASSIGN =
			new Procedure2<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember from, QuaternionHighPrecisionGeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionHighPrecisionGeneralTensorProductMember> ZER =
			new Procedure1<QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionGeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> NEG =
			new Procedure2<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QHP, G.QHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> ADDEL =
			new Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> SUBEL =
			new Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<QuaternionHighPrecisionGeneralTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.QHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	@Override
	public Procedure2<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> SCALE =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QHP, G.QHP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QHP, G.QHP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			QuaternionHighPrecisionMember tmp = G.QHP.construct();
			G.QHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> MULEL =
			new Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> MUL =
			new Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorContract.compute(G.QHP, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorPower.compute(G.QHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionHighPrecisionGeneralTensorProductMember,QuaternionHighPrecisionGeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionHighPrecisionGeneralTensorProductMember> UNITY =
			new Procedure1<QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember result) {
			TensorUnity.compute(G.QHP_TEN, G.QHP, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionGeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionHighPrecisionGeneralTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionGeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.QHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionHighPrecisionGeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> SBD =
			new Procedure3<Double, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember factor, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember factor, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			QuaternionHighPrecisionMember invFactor = G.QHP.construct();
			G.QHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> RAISE =
			new Procedure4<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, QuaternionHighPrecisionGeneralTensorProductMember metricInverse, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.QHP, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> LOWER =
		new Procedure4<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionHighPrecisionGeneralTensorProductMember metric, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.QHP, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionHighPrecisionGeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> OUTER =
			new Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b, QuaternionHighPrecisionGeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.QHP_TEN, G.QHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QHP_TEN, G.QHP, new QuaternionHighPrecisionMember(2,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionGeneralTensorProductMember a, QuaternionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QHP_TEN, G.QHP, new QuaternionHighPrecisionMember(0.5,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionGeneralTensorProductMember, QuaternionHighPrecisionGeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionHighPrecisionGeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionGeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.QHP, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionHighPrecisionGeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public QuaternionHighPrecisionAlgebra getElementAlgebra() {
		return G.QHP;
	}

	@Override
	public QuaternionHighPrecisionGeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new QuaternionHighPrecisionGeneralTensorProductMember(indexTypes, axisSizes);
	}

}
