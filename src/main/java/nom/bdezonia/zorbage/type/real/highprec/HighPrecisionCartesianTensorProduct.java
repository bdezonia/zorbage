/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.TensorType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
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

import nom.bdezonia.zorbage.type.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionCartesianTensorProduct
	implements
		TensorProduct<HighPrecisionCartesianTensorProduct,HighPrecisionCartesianTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		Norm<HighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		Scale<HighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		ScaleByHighPrec<HighPrecisionCartesianTensorProductMember>,
		ScaleByRational<HighPrecisionCartesianTensorProductMember>,
		ScaleByDouble<HighPrecisionCartesianTensorProductMember>,
		ScaleByOneHalf<HighPrecisionCartesianTensorProductMember>,
		ScaleByTwo<HighPrecisionCartesianTensorProductMember>,
		Tolerance<HighPrecisionMember, HighPrecisionCartesianTensorProductMember>,
		ArrayLikeMethods<HighPrecisionCartesianTensorProductMember, HighPrecisionMember>,
		CompositeType,
		ExactType,
		SignedType,
		TensorType,
		UnityIncludedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision real tensor";
	}

	@Override
	public HighPrecisionCartesianTensorProductMember construct() {
		return new HighPrecisionCartesianTensorProductMember();
	}

	@Override
	public HighPrecisionCartesianTensorProductMember construct(HighPrecisionCartesianTensorProductMember other) {
		return new HighPrecisionCartesianTensorProductMember(other);
	}

	@Override
	public HighPrecisionCartesianTensorProductMember construct(String s) {
		return new HighPrecisionCartesianTensorProductMember(s);
	}

	private final Function2<Boolean,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> EQ =
			new Function2<Boolean,HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> NEQ =
			new Function2<Boolean,HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> ASSIGN =
			new Procedure2<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember from, HighPrecisionCartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.HP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<HighPrecisionCartesianTensorProductMember> ZER =
			new Procedure1<HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<HighPrecisionCartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> NEG =
			new Procedure2<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.HP, G.HP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> ADDEL =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> SUBEL =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionCartesianTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.HP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	@Override
	public Procedure2<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> SCALE =
			new Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.HP, G.HP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> ADDSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.HP, G.HP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> SUBSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			HighPrecisionMember tmp = G.HP.construct();
			G.HP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> MULEL =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> DIVIDEEL =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP, G.HP.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> MUL =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorContract.compute(G.HP, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> SEMI =
			new Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.HP_TEN, G.HP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> COMMA =
			new Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.HP_TEN, G.HP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> POWER =
			new Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorPower.compute(G.HP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<HighPrecisionCartesianTensorProductMember> UNITY =
			new Procedure1<HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember result) {
			TensorUnity.compute(G.HP_TEN, G.HP, result);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionCartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, HighPrecisionCartesianTensorProductMember> ISZERO =
			new Function1<Boolean, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionCartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.HP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionCartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> SBD =
			new Procedure3<Double, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.HP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.HP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			HighPrecisionMember invFactor = G.HP.construct();
			G.HP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> RAISE =
		new Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> LOWER =
		new Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			HighPrecisionCartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> OUTER =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.HP_TEN, G.HP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.HP_TEN, G.HP, new HighPrecisionMember(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> SCBH =
			new Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.HP_TEN, G.HP, new HighPrecisionMember(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, HighPrecisionCartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionCartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.HP, a);
		}
	};
	
	@Override
	public Function1<Boolean, HighPrecisionCartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
