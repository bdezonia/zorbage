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
package nom.bdezonia.zorbage.type.complex.highprec;

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
public class ComplexHighPrecisionGeneralTensorProduct
	implements
		TensorProduct<ComplexHighPrecisionGeneralTensorProduct,ComplexHighPrecisionGeneralTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		Norm<ComplexHighPrecisionGeneralTensorProductMember,HighPrecisionMember>,
		Scale<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionMember>,
		ScaleByHighPrec<ComplexHighPrecisionGeneralTensorProductMember>,
		ScaleByRational<ComplexHighPrecisionGeneralTensorProductMember>,
		ScaleByDouble<ComplexHighPrecisionGeneralTensorProductMember>,
		ScaleByOneHalf<ComplexHighPrecisionGeneralTensorProductMember>,
		ScaleByTwo<ComplexHighPrecisionGeneralTensorProductMember>,
		Tolerance<HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember>,
		ArrayLikeMethods<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionMember>,
		MadeOfElements<ComplexHighPrecisionAlgebra,ComplexHighPrecisionMember>,
		ConstructibleTensor<ComplexHighPrecisionGeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision complex tensor";
	}

	@Override
	public ComplexHighPrecisionGeneralTensorProductMember construct() {
		return new ComplexHighPrecisionGeneralTensorProductMember();
	}

	@Override
	public ComplexHighPrecisionGeneralTensorProductMember construct(ComplexHighPrecisionGeneralTensorProductMember other) {
		return new ComplexHighPrecisionGeneralTensorProductMember(other);
	}

	@Override
	public ComplexHighPrecisionGeneralTensorProductMember construct(String s) {
		return new ComplexHighPrecisionGeneralTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> EQ =
			new Function2<Boolean,ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> NEQ =
			new Function2<Boolean,ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember from, ComplexHighPrecisionGeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexHighPrecisionGeneralTensorProductMember> ZER =
			new Procedure1<ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionGeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> NEG =
			new Procedure2<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CHP, G.CHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> ADDEL =
			new Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> SUBEL =
			new Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexHighPrecisionGeneralTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionGeneralTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.CHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionGeneralTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	@Override
	public Procedure2<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> SCALE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CHP, G.CHP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CHP, G.CHP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			ComplexHighPrecisionMember tmp = G.CHP.construct();
			G.CHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> MULEL =
			new Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> MUL =
			new Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorContract.compute(G.CHP, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> POWER =
			new Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorPower.compute(G.CHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexHighPrecisionGeneralTensorProductMember,ComplexHighPrecisionGeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexHighPrecisionGeneralTensorProductMember> UNITY =
			new Procedure1<ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember result) {
			TensorUnity.compute(G.CHP_TEN, G.CHP, result);
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionGeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexHighPrecisionGeneralTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionGeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.CHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionGeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> SBD =
			new Procedure3<Double, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember factor, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember factor, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			ComplexHighPrecisionMember invFactor = G.CHP.construct();
			G.CHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> RAISE =
			new Procedure4<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, ComplexHighPrecisionGeneralTensorProductMember metricInverse, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.CHP, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> LOWER =
		new Procedure4<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexHighPrecisionGeneralTensorProductMember metric, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.CHP, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexHighPrecisionGeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> OUTER =
			new Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b, ComplexHighPrecisionGeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.CHP_TEN, G.CHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CHP_TEN, G.CHP, new ComplexHighPrecisionMember(2,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexHighPrecisionGeneralTensorProductMember a, ComplexHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CHP_TEN, G.CHP, new ComplexHighPrecisionMember(0.5,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexHighPrecisionGeneralTensorProductMember, ComplexHighPrecisionGeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexHighPrecisionGeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionGeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.CHP, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexHighPrecisionGeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public ComplexHighPrecisionAlgebra getElementAlgebra() {
		return G.CHP;
	}

	@Override
	public ComplexHighPrecisionGeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new ComplexHighPrecisionGeneralTensorProductMember(indexTypes, axisSizes);
	}

}
