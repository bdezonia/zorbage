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
package nom.bdezonia.zorbage.type.data.highprec.octonion;

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
public class OctonionHighPrecisionTensorProduct
	implements
		TensorLikeProduct<OctonionHighPrecisionTensorProduct,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionAlgebra,OctonionHighPrecisionMember>,
		ConstructibleNdLong<OctonionHighPrecisionTensorProductMember>,
		Norm<OctonionHighPrecisionTensorProductMember,HighPrecisionMember>,
		Scale<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionMember>,
		ScaleByHighPrec<OctonionHighPrecisionTensorProductMember>,
		ScaleByRational<OctonionHighPrecisionTensorProductMember>,
		ScaleByDouble<OctonionHighPrecisionTensorProductMember>,
		Tolerance<HighPrecisionMember, OctonionHighPrecisionTensorProductMember>
{
	@Override
	public OctonionHighPrecisionTensorProductMember construct() {
		return new OctonionHighPrecisionTensorProductMember();
	}

	@Override
	public OctonionHighPrecisionTensorProductMember construct(OctonionHighPrecisionTensorProductMember other) {
		return new OctonionHighPrecisionTensorProductMember(other);
	}

	@Override
	public OctonionHighPrecisionTensorProductMember construct(String s) {
		return new OctonionHighPrecisionTensorProductMember(s);
	}

	@Override
	public OctonionHighPrecisionTensorProductMember construct(StorageConstruction s, long[] nd) {
		return new OctonionHighPrecisionTensorProductMember(s, nd);
	}

	private final Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> EQ =
			new Function2<Boolean,OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> NEQ =
			new Function2<Boolean,OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember from, OctonionHighPrecisionTensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.OHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionHighPrecisionTensorProductMember> ZER =
			new Procedure1<OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> NEG =
			new Procedure2<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHP, G.OHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> ADDEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> SUBEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.OHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> CONJ =
			new Procedure2<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHP, G.OHP.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> SCALE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.OHP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.OHP, scalar, G.OHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			OctonionHighPrecisionMember tmp = G.OHP.construct();
			G.OHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> MULEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> MUL =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorContract.compute(G.OHP, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> COMMA =
			new Procedure3<IntegerIndex,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			OctonionHighPrecisionMember val = G.OHP.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> POWER =
			new Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorPower.compute(G.OHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionHighPrecisionTensorProductMember,OctonionHighPrecisionTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionHighPrecisionTensorProductMember> UNITY =
			new Procedure1<OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember result) {
			TensorUnity.compute(G.OHP_TEN, G.OHP, result);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionHighPrecisionTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionTensorProductMember a) {
			return SequenceIsZero.compute(G.OHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> SBD =
			new Procedure3<Double, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			OctonionHighPrecisionMember invFactor = G.OHP.construct();
			G.OHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionHighPrecisionTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> OUTER =
			new Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionTensorProductMember a, OctonionHighPrecisionTensorProductMember b, OctonionHighPrecisionTensorProductMember c) {

			TensorOuterProduct.compute(G.OHP_TEN, G.OHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember, OctonionHighPrecisionTensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
