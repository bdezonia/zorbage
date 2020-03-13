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
package nom.bdezonia.zorbage.type.data.highprec.real;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FixedTransform2;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
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
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;

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

import nom.bdezonia.zorbage.type.algebra.TensorProduct;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionCartesianTensorProduct
	implements
		TensorProduct<HighPrecisionCartesianTensorProduct,HighPrecisionCartesianTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		ConstructibleNdLong<HighPrecisionCartesianTensorProductMember>,
		Norm<HighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		Scale<HighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		ScaleByHighPrec<HighPrecisionCartesianTensorProductMember>,
		ScaleByRational<HighPrecisionCartesianTensorProductMember>,
		ScaleByDouble<HighPrecisionCartesianTensorProductMember>,
		Tolerance<HighPrecisionMember, HighPrecisionCartesianTensorProductMember>
{
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

	@Override
	public HighPrecisionCartesianTensorProductMember construct(StorageConstruction s, long[] nd) {
		return new HighPrecisionCartesianTensorProductMember(s, nd);
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
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.HP, scalar, a.rawData(), b.rawData());
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
			FixedTransform2.compute(G.HP, scalar, G.HP.add(), a.rawData(), b.rawData());
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

	private Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> MULEL =
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
	
	private Procedure3<HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> DIVIDEEL =
			new Procedure3<HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember, HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b, HighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.HP,G.HP.divide(),a.rawData(),b.rawData(),c.rawData());
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
		
	private final Procedure1<Object> SEMI =
			new Procedure1<Object>()
	{
		@Override
		public void call(Object a) {
			TensorSemicolonDerivative.compute();
		}
	};
	
	@Override
	public Procedure1<Object> semicolonDerivative() {
		return SEMI;
	}
	
	// http://mathworld.wolfram.com/CommaDerivative.html
	
	private final Procedure3<IntegerIndex,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> COMMA =
			new Procedure3<IntegerIndex,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, HighPrecisionCartesianTensorProductMember a, HighPrecisionCartesianTensorProductMember b) {
			HighPrecisionMember val = G.HP.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,HighPrecisionCartesianTensorProductMember,HighPrecisionCartesianTensorProductMember> commaDerivative() {
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
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
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
	
}
