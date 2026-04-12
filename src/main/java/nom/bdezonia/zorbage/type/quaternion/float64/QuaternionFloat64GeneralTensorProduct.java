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
package nom.bdezonia.zorbage.type.quaternion.float64;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
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
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64GeneralTensorProduct
	implements
		TensorProduct<QuaternionFloat64GeneralTensorProduct,QuaternionFloat64GeneralTensorProductMember,Float64Algebra,Float64Member>,
		Norm<QuaternionFloat64GeneralTensorProductMember,Float64Member>,
		Scale<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64Member>,
		Rounding<Float64Member,QuaternionFloat64GeneralTensorProductMember>,
		Infinite<QuaternionFloat64GeneralTensorProductMember>,
		NaN<QuaternionFloat64GeneralTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat64GeneralTensorProductMember>,
		ScaleByRational<QuaternionFloat64GeneralTensorProductMember>,
		ScaleByDouble<QuaternionFloat64GeneralTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat64GeneralTensorProductMember>,
		ScaleByTwo<QuaternionFloat64GeneralTensorProductMember>,
		Tolerance<Float64Member, QuaternionFloat64GeneralTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64Member>,
		MadeOfElements<QuaternionFloat64Algebra,QuaternionFloat64Member>,
		ConstructibleTensor<QuaternionFloat64GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "64-bit based quaternion tensor";
	}

	@Override
	public QuaternionFloat64GeneralTensorProductMember construct() {
		return new QuaternionFloat64GeneralTensorProductMember();
	}

	@Override
	public QuaternionFloat64GeneralTensorProductMember construct(QuaternionFloat64GeneralTensorProductMember other) {
		return new QuaternionFloat64GeneralTensorProductMember(other);
	}

	@Override
	public QuaternionFloat64GeneralTensorProductMember construct(String s) {
		return new QuaternionFloat64GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QDBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember from, QuaternionFloat64GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QDBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat64GeneralTensorProductMember> ZER =
			new Procedure1<QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> NEG =
			new Procedure2<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QDBL, G.QDBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat64GeneralTensorProductMember,Float64Member> NORM =
			new Procedure2<QuaternionFloat64GeneralTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.QDBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat64GeneralTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QDBL, G.QDBL.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat64Member,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member scalar, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			QuaternionFloat64Member tmp = G.QDBL.construct();
			G.QDBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QDBL, G.QDBL.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> MUL =
			new Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorContract.compute(G.QDBL, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorPower.compute(G.QDBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat64GeneralTensorProductMember,QuaternionFloat64GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat64GeneralTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember result) {
			TensorUnity.compute(G.QDBL_TEN, G.QDBL, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat64GeneralTensorProductMember> NAN =
			new Procedure1<QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a) {
			FillNaN.compute(G.QDBL, a.rawData());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat64GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat64GeneralTensorProductMember> INF =
			new Procedure1<QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a) {
			FillInfinite.compute(G.QDBL, a.rawData());
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat64GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorRound.compute(G.QDBL_TEN, G.QDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.QDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member factor, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64Member factor, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			QuaternionFloat64Member invFactor = G.QDBL.construct();
			G.QDBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat64Member, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, QuaternionFloat64GeneralTensorProductMember metricInverse, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.QDBL, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat64GeneralTensorProductMember metric, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.QDBL, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat64GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b, QuaternionFloat64GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.QDBL_TEN, G.QDBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QDBL_TEN, G.QDBL, new QuaternionFloat64Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat64GeneralTensorProductMember a, QuaternionFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QDBL_TEN, G.QDBL, new QuaternionFloat64Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat64GeneralTensorProductMember, QuaternionFloat64GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat64GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.QDBL, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionFloat64GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public QuaternionFloat64Algebra getElementAlgebra() {
		return G.QDBL;
	}

	@Override
	public QuaternionFloat64GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new QuaternionFloat64GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
