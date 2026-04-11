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
package nom.bdezonia.zorbage.type.quaternion.float16;

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
import nom.bdezonia.zorbage.type.real.float16.Float16Algebra;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat16GeneralTensorProduct
	implements
		TensorProduct<QuaternionFloat16GeneralTensorProduct,QuaternionFloat16GeneralTensorProductMember,Float16Algebra,Float16Member>,
		Norm<QuaternionFloat16GeneralTensorProductMember,Float16Member>,
		Scale<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16Member>,
		Rounding<Float16Member,QuaternionFloat16GeneralTensorProductMember>,
		Infinite<QuaternionFloat16GeneralTensorProductMember>,
		NaN<QuaternionFloat16GeneralTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat16GeneralTensorProductMember>,
		ScaleByRational<QuaternionFloat16GeneralTensorProductMember>,
		ScaleByDouble<QuaternionFloat16GeneralTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat16GeneralTensorProductMember>,
		ScaleByTwo<QuaternionFloat16GeneralTensorProductMember>,
		Tolerance<Float16Member, QuaternionFloat16GeneralTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16Member>,
		MadeOfElements<QuaternionFloat16Algebra,QuaternionFloat16Member>,
		ConstructibleTensor<QuaternionFloat16GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "16-bit based quaternion tensor";
	}

	@Override
	public QuaternionFloat16GeneralTensorProductMember construct() {
		return new QuaternionFloat16GeneralTensorProductMember();
	}

	@Override
	public QuaternionFloat16GeneralTensorProductMember construct(QuaternionFloat16GeneralTensorProductMember other) {
		return new QuaternionFloat16GeneralTensorProductMember(other);
	}

	@Override
	public QuaternionFloat16GeneralTensorProductMember construct(String s) {
		return new QuaternionFloat16GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember from, QuaternionFloat16GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QHLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat16GeneralTensorProductMember> ZER =
			new Procedure1<QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> NEG =
			new Procedure2<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QHLF, G.QHLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat16GeneralTensorProductMember,Float16Member> NORM =
			new Procedure2<QuaternionFloat16GeneralTensorProductMember, Float16Member>() 
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.QHLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat16GeneralTensorProductMember,Float16Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QHLF, G.QHLF.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QHLF, G.QHLF.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat16Member,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member scalar, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			QuaternionFloat16Member tmp = G.QHLF.construct();
			G.QHLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QHLF, G.QHLF.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> MUL =
			new Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorContract.compute(G.QHLF, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorPower.compute(G.QHLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat16GeneralTensorProductMember,QuaternionFloat16GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat16GeneralTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember result) {
			TensorUnity.compute(G.QHLF_TEN, G.QHLF, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat16GeneralTensorProductMember> NAN =
			new Procedure1<QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a) {
			FillNaN.compute(G.QHLF, a.rawData());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat16GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat16GeneralTensorProductMember> INF =
			new Procedure1<QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a) {
			FillInfinite.compute(G.QHLF, a.rawData());
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat16GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorRound.compute(G.QHLF_TEN, G.QHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.QHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member factor, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16Member factor, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			QuaternionFloat16Member invFactor = G.QHLF.construct();
			G.QHLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat16Member, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, QuaternionFloat16GeneralTensorProductMember metricInverse, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.QHLF, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat16GeneralTensorProductMember metric, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.QHLF, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat16GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b, QuaternionFloat16GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.QHLF_TEN, G.QHLF, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QHLF_TEN, G.QHLF, new QuaternionFloat16Member(2,0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat16GeneralTensorProductMember a, QuaternionFloat16GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QHLF_TEN, G.QHLF, new QuaternionFloat16Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat16GeneralTensorProductMember, QuaternionFloat16GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat16GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat16GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.QHLF, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionFloat16GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public QuaternionFloat16Algebra getElementAlgebra() {
		return G.QHLF;
	}

	@Override
	public QuaternionFloat16GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new QuaternionFloat16GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
