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
package nom.bdezonia.zorbage.type.octonion.highprec;

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
public class OctonionHighPrecisionGeneralTensorProduct
	implements
		TensorProduct<OctonionHighPrecisionGeneralTensorProduct,OctonionHighPrecisionGeneralTensorProductMember,HighPrecisionAlgebra,HighPrecisionMember>,
		Norm<OctonionHighPrecisionGeneralTensorProductMember,HighPrecisionMember>,
		Scale<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionMember>,
		ScaleByHighPrec<OctonionHighPrecisionGeneralTensorProductMember>,
		ScaleByRational<OctonionHighPrecisionGeneralTensorProductMember>,
		ScaleByDouble<OctonionHighPrecisionGeneralTensorProductMember>,
		ScaleByOneHalf<OctonionHighPrecisionGeneralTensorProductMember>,
		ScaleByTwo<OctonionHighPrecisionGeneralTensorProductMember>,
		Tolerance<HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember>,
		ArrayLikeMethods<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionMember>,
		MadeOfElements<OctonionHighPrecisionAlgebra,OctonionHighPrecisionMember>,
		ConstructibleTensor<OctonionHighPrecisionGeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision octonion tensor";
	}

	@Override
	public OctonionHighPrecisionGeneralTensorProductMember construct() {
		return new OctonionHighPrecisionGeneralTensorProductMember();
	}

	@Override
	public OctonionHighPrecisionGeneralTensorProductMember construct(OctonionHighPrecisionGeneralTensorProductMember other) {
		return new OctonionHighPrecisionGeneralTensorProductMember(other);
	}

	@Override
	public OctonionHighPrecisionGeneralTensorProductMember construct(String s) {
		return new OctonionHighPrecisionGeneralTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> EQ =
			new Function2<Boolean,OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> NEQ =
			new Function2<Boolean,OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember from, OctonionHighPrecisionGeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.OHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionHighPrecisionGeneralTensorProductMember> ZER =
			new Procedure1<OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionGeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> NEG =
			new Procedure2<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OHP, G.OHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> ADDEL =
			new Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> SUBEL =
			new Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionHighPrecisionGeneralTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionGeneralTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.OHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionGeneralTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	@Override
	public Procedure2<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> SCALE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.OHP, G.OHP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.OHP, G.OHP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			OctonionHighPrecisionMember tmp = G.OHP.construct();
			G.OHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> MULEL =
			new Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OHP, G.OHP.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> MUL =
			new Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorContract.compute(G.OHP, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> POWER =
			new Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorPower.compute(G.OHP_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionHighPrecisionGeneralTensorProductMember,OctonionHighPrecisionGeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionHighPrecisionGeneralTensorProductMember> UNITY =
			new Procedure1<OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember result) {
			TensorUnity.compute(G.OHP_TEN, G.OHP, result);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionGeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionHighPrecisionGeneralTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionGeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.OHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionGeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> SBD =
			new Procedure3<Double, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember factor, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			OctonionHighPrecisionMember invFactor = G.OHP.construct();
			G.OHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> RAISE =
			new Procedure4<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, OctonionHighPrecisionGeneralTensorProductMember metricInverse, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.OHP, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> LOWER =
		new Procedure4<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionHighPrecisionGeneralTensorProductMember metric, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.OHP, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionHighPrecisionGeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> OUTER =
			new Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b, OctonionHighPrecisionGeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.OHP_TEN, G.OHP, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OHP_TEN, G.OHP, new OctonionHighPrecisionMember(2,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> SCBH =
			new Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionHighPrecisionGeneralTensorProductMember a, OctonionHighPrecisionGeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OHP_TEN, G.OHP, new OctonionHighPrecisionMember(0.5,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionHighPrecisionGeneralTensorProductMember, OctonionHighPrecisionGeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, OctonionHighPrecisionGeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, OctonionHighPrecisionGeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionGeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.OHP, a);
		}
	};
	
	@Override
	public Function1<Boolean, OctonionHighPrecisionGeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public OctonionHighPrecisionAlgebra getElementAlgebra() {
		return G.OHP;
	}

	@Override
	public OctonionHighPrecisionGeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new OctonionHighPrecisionGeneralTensorProductMember(indexTypes, axisSizes);
	}

}
