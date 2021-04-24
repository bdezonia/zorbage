/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
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
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionHighPrecisionCartesianTensorProduct
	implements
		TensorLikeProduct<QuaternionHighPrecisionCartesianTensorProduct,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionAlgebra,QuaternionHighPrecisionMember>,
		Norm<QuaternionHighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		Scale<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionMember>,
		ScaleByHighPrec<QuaternionHighPrecisionCartesianTensorProductMember>,
		ScaleByRational<QuaternionHighPrecisionCartesianTensorProductMember>,
		ScaleByDouble<QuaternionHighPrecisionCartesianTensorProductMember>,
		ScaleByOneHalf<QuaternionHighPrecisionCartesianTensorProductMember>,
		ScaleByTwo<QuaternionHighPrecisionCartesianTensorProductMember>,
		Tolerance<HighPrecisionMember, QuaternionHighPrecisionCartesianTensorProductMember>,
		ArrayLikeMethods<QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionMember>
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
			if (from == to) return;
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
			FixedTransform2a.compute(G.QHP, scalar, G.QHP.add(), a.rawData(), b.rawData());
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

	private final Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> MULEL =
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
	
	private final Procedure3<QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> DIVIDEEL =
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
	
	private final Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> SEMI =
			new Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.QHP_TEN, G.QHP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> COMMA =
			new Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.QHP_TEN, G.QHP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionHighPrecisionCartesianTensorProductMember,QuaternionHighPrecisionCartesianTensorProductMember> commaDerivative() {
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
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

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

	private final Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QHP_TEN, G.QHP, new QuaternionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionCartesianTensorProductMember a, QuaternionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QHP_TEN, G.QHP, new QuaternionHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionCartesianTensorProductMember, QuaternionHighPrecisionCartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionHighPrecisionCartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionCartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.QHP, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionHighPrecisionCartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
