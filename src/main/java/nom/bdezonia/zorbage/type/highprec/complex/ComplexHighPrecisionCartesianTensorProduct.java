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
package nom.bdezonia.zorbage.type.highprec.complex;

import java.lang.Integer;
import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
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

import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexHighPrecisionCartesianTensorProduct
	implements
		TensorProduct<ComplexHighPrecisionCartesianTensorProduct,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionAlgebra,ComplexHighPrecisionMember>,
		Norm<ComplexHighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		Scale<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionMember>,
		ScaleByHighPrec<ComplexHighPrecisionCartesianTensorProductMember>,
		ScaleByRational<ComplexHighPrecisionCartesianTensorProductMember>,
		ScaleByDouble<ComplexHighPrecisionCartesianTensorProductMember>,
		ScaleByOneHalf<ComplexHighPrecisionCartesianTensorProductMember>,
		ScaleByTwo<ComplexHighPrecisionCartesianTensorProductMember>,
		Tolerance<HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember>,
		ArrayLikeMethods<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionMember>
{
	@Override
	public ComplexHighPrecisionCartesianTensorProductMember construct() {
		return new ComplexHighPrecisionCartesianTensorProductMember();
	}

	@Override
	public ComplexHighPrecisionCartesianTensorProductMember construct(ComplexHighPrecisionCartesianTensorProductMember other) {
		return new ComplexHighPrecisionCartesianTensorProductMember(other);
	}

	@Override
	public ComplexHighPrecisionCartesianTensorProductMember construct(String s) {
		return new ComplexHighPrecisionCartesianTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> EQ =
			new Function2<Boolean,ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> NEQ =
			new Function2<Boolean,ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember from, ComplexHighPrecisionCartesianTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.CHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexHighPrecisionCartesianTensorProductMember> ZER =
			new Procedure1<ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionCartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> NEG =
			new Procedure2<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CHP, G.CHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> ADDEL =
			new Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> SUBEL =
			new Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexHighPrecisionCartesianTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionCartesianTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.CHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionCartesianTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> CONJ =
			new Procedure2<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CHP, G.CHP.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> SCALE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.CHP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.CHP, scalar, G.CHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			ComplexHighPrecisionMember tmp = G.CHP.construct();
			G.CHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> MULEL =
			new Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHP, G.CHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> MUL =
			new Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorContract.compute(G.CHP, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> SEMI =
			new Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.CHP_TEN, G.CHP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> COMMA =
			new Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.CHP_TEN, G.CHP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> POWER =
			new Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorPower.compute(G.CHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexHighPrecisionCartesianTensorProductMember,ComplexHighPrecisionCartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexHighPrecisionCartesianTensorProductMember> UNITY =
			new Procedure1<ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember result) {
			TensorUnity.compute(G.CHP_TEN, G.CHP, result);
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionCartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexHighPrecisionCartesianTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionCartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.CHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionCartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> SBD =
			new Procedure3<Double, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember factor, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember factor, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			ComplexHighPrecisionMember invFactor = G.CHP.construct();
			G.CHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> RAISE =
		new Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> LOWER =
		new Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexHighPrecisionCartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> OUTER =
			new Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b, ComplexHighPrecisionCartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.CHP_TEN, G.CHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			ScaleHelper.compute(G.CHP_TEN, G.CHP, new ComplexHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexHighPrecisionCartesianTensorProductMember a, ComplexHighPrecisionCartesianTensorProductMember b) {
			ScaleHelper.compute(G.CHP_TEN, G.CHP, new ComplexHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexHighPrecisionCartesianTensorProductMember, ComplexHighPrecisionCartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}
	
}
