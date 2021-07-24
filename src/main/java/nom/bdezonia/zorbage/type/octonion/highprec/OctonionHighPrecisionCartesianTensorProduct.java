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
package nom.bdezonia.zorbage.type.octonion.highprec;

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
public class OctonionHighPrecisionCartesianTensorProduct
	implements
		TensorLikeProduct<OctonionHighPrecisionCartesianTensorProduct,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionAlgebra,OctonionHighPrecisionMember>,
		Norm<OctonionHighPrecisionCartesianTensorProductMember,HighPrecisionMember>,
		Scale<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionMember>,
		ScaleByHighPrec<OctonionHighPrecisionCartesianTensorProductMember>,
		ScaleByRational<OctonionHighPrecisionCartesianTensorProductMember>,
		ScaleByDouble<OctonionHighPrecisionCartesianTensorProductMember>,
		ScaleByOneHalf<OctonionHighPrecisionCartesianTensorProductMember>,
		ScaleByTwo<OctonionHighPrecisionCartesianTensorProductMember>,
		Tolerance<HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember>,
		ArrayLikeMethods<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionMember>
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision octonion tensor";
	}

	@Override
	public OctonionHighPrecisionCartesianTensorProductMember construct() {
		return new OctonionHighPrecisionCartesianTensorProductMember();
	}

	@Override
	public OctonionHighPrecisionCartesianTensorProductMember construct(OctonionHighPrecisionCartesianTensorProductMember other) {
		return new OctonionHighPrecisionCartesianTensorProductMember(other);
	}

	@Override
	public OctonionHighPrecisionCartesianTensorProductMember construct(String s) {
		return new OctonionHighPrecisionCartesianTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> EQ =
			new Function2<Boolean,OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> NEQ =
			new Function2<Boolean,OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember from, OctonionHighPrecisionCartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.OHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionHighPrecisionCartesianTensorProductMember> ZER =
			new Procedure1<OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionCartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> NEG =
			new Procedure2<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHP, G.OHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> ADDEL =
			new Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> SUBEL =
			new Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionHighPrecisionCartesianTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionCartesianTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.OHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionCartesianTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> CONJ =
			new Procedure2<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHP, G.OHP.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> SCALE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.OHP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.OHP, scalar, G.OHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			OctonionHighPrecisionMember tmp = G.OHP.construct();
			G.OHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> MULEL =
			new Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> MUL =
			new Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorContract.compute(G.OHP, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> SEMI =
			new Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.OHP_TEN, G.OHP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> COMMA =
			new Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.OHP_TEN, G.OHP, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> POWER =
			new Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorPower.compute(G.OHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionHighPrecisionCartesianTensorProductMember,OctonionHighPrecisionCartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionHighPrecisionCartesianTensorProductMember> UNITY =
			new Procedure1<OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember result) {
			TensorUnity.compute(G.OHP_TEN, G.OHP, result);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionCartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionHighPrecisionCartesianTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionCartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.OHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionCartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> SBD =
			new Procedure3<Double, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			OctonionHighPrecisionMember invFactor = G.OHP.construct();
			G.OHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionHighPrecisionCartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> OUTER =
			new Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b, OctonionHighPrecisionCartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.OHP_TEN, G.OHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OHP_TEN, G.OHP, new OctonionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> SCBH =
			new Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionHighPrecisionCartesianTensorProductMember a, OctonionHighPrecisionCartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OHP_TEN, G.OHP, new OctonionHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionHighPrecisionCartesianTensorProductMember, OctonionHighPrecisionCartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, OctonionHighPrecisionCartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, OctonionHighPrecisionCartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionCartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.OHP, a);
		}
	};
	
	@Override
	public Function1<Boolean, OctonionHighPrecisionCartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
