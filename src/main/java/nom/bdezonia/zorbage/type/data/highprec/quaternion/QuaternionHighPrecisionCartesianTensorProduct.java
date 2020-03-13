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
package nom.bdezonia.zorbage.type.data.highprec.quaternion;

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
import nom.bdezonia.zorbage.type.algebra.TensorLikeProduct;

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
omplex */
public class QuaternionHighPrecisionCartesianTensorProduct
	implements
		TensorLikeProduct<QuaternionHighPrecisionCartesianTensorProduct,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionAlgebra,QuaternionHighPrecisionMember>,
		ConstructibleNdLong<QuaternionHighPrecisionCartesianTensorProductMember>,
		Norm<QuaternionHighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		Scale<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionMember>,
		ScaleByHighPrec<QuaternionHighPrecisionCartesianTensorProductMember>,
		ScaleByRational<QuaternionHighPrecisionCartesianTensorProductMember>,
		ScaleByDouble<QuaternionHighPrecisionCartesianTensorProductMember>,
		Tolerance<HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember>
{
	@Override
	public QuaternionHighPrecisionCartesianTensorProductMember construct() {
		return new QuaternionHighPrecisionCartesianTensorProductMember();
	}

	@Override
	public QuaternionHighPrecisionCartesianTensorProductMember construct(QuaternionHighPrecisionCartesianTensorProductMember other) {
		return new QuaternionHighPrecisionCartesianTensorProductMember(other);
	}

	@Override
	public QuaternionHighPrecisionCartesianTensorProductMember construct(String s) {
		return new QuaternionHighPrecisionCartesianTensorProductMember(s);
	}

	@Override
	public QuaternionHighPrecisionCartesianTensorProductMember construct(StorageConstruction s, long[] nd) {
		return new QuaternionHighPrecisionCartesianTensorProductMember(s, nd);
	}

	private final Function2<Boolean,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> EQ =
			new Function2<Boolean,QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> ASSIGN =
			new Procedure2<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember from, QuaternionHighPrecisionCartesianTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.QHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionHighPrecisionCartesianTensorProductMember> ZER =
			new Procedure1<QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionCartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> NEG =
			new Procedure2<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QHP, G.QHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> ADDEL =
			new Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> SUBEL =
			new Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<QuaternionHighPrecisionCartesianTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.QHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> CONJ =
			new Procedure2<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QHP, G.QHP.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> SCALE =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.QHP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.QHP, scalar, G.QHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			QuaternionHighPrecisionMember tmp = G.QHP.construct();
			G.QHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> MULEL =
			new Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHP, G.QHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> MUL =
			new Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorContract.compute(G.QHP, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> COMMA =
			new Procedure3<IntegerIndex,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			QuaternionHighPrecisionMember val = G.QHP.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorPower.compute(G.QHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionHighPrecisionCartesianTensorProductMember> UNITY =
			new Procedure1<QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember result) {
			TensorUnity.compute(G.QHP_TEN, G.QHP, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionCartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionHighPrecisionCartesianTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionCartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.QHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionHighPrecisionCartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> SBD =
			new Procedure3<Double, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember factor, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember factor, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			QuaternionHighPrecisionMember invFactor = G.QHP.construct();
			G.QHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> RAISE =
		new Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> LOWER =
		new Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionHighPrecisionCartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> OUTER =
			new Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b, QuaternionHighPrecisionCartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.QHP_TEN, G.QHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
