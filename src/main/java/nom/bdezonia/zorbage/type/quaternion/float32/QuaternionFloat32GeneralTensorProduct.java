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
package nom.bdezonia.zorbage.type.quaternion.float32;

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
import nom.bdezonia.zorbage.type.real.float32.Float32Algebra;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat32GeneralTensorProduct
	implements
		TensorProduct<QuaternionFloat32GeneralTensorProduct,QuaternionFloat32GeneralTensorProductMember,Float32Algebra,Float32Member>,
		Norm<QuaternionFloat32GeneralTensorProductMember,Float32Member>,
		Scale<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32Member>,
		Rounding<Float32Member,QuaternionFloat32GeneralTensorProductMember>,
		Infinite<QuaternionFloat32GeneralTensorProductMember>,
		NaN<QuaternionFloat32GeneralTensorProductMember>,
		ScaleByHighPrec<QuaternionFloat32GeneralTensorProductMember>,
		ScaleByRational<QuaternionFloat32GeneralTensorProductMember>,
		ScaleByDouble<QuaternionFloat32GeneralTensorProductMember>,
		ScaleByOneHalf<QuaternionFloat32GeneralTensorProductMember>,
		ScaleByTwo<QuaternionFloat32GeneralTensorProductMember>,
		Tolerance<Float32Member, QuaternionFloat32GeneralTensorProductMember>,
		ArrayLikeMethods<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32Member>,
		MadeOfElements<QuaternionFloat32Algebra,QuaternionFloat32Member>,
		ConstructibleTensor<QuaternionFloat32GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "32-bit based quaternion tensor";
	}

	@Override
	public QuaternionFloat32GeneralTensorProductMember construct() {
		return new QuaternionFloat32GeneralTensorProductMember();
	}

	@Override
	public QuaternionFloat32GeneralTensorProductMember construct(QuaternionFloat32GeneralTensorProductMember other) {
		return new QuaternionFloat32GeneralTensorProductMember(other);
	}

	@Override
	public QuaternionFloat32GeneralTensorProductMember construct(String s) {
		return new QuaternionFloat32GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> EQ =
			new Function2<Boolean,QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> NEQ =
			new Function2<Boolean,QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> ASSIGN =
			new Procedure2<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember from, QuaternionFloat32GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.QFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<QuaternionFloat32GeneralTensorProductMember> ZER =
			new Procedure1<QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> NEG =
			new Procedure2<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.QFLT, G.QFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> ADDEL =
			new Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> SUBEL =
			new Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<QuaternionFloat32GeneralTensorProductMember,Float32Member> NORM =
			new Procedure2<QuaternionFloat32GeneralTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.QFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat32GeneralTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> SCALE =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QFLT, G.QFLT.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.QFLT, G.QFLT.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<QuaternionFloat32Member,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member scalar, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			QuaternionFloat32Member tmp = G.QFLT.construct();
			G.QFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> MULEL =
			new Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.QFLT, G.QFLT.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> MUL =
			new Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorContract.compute(G.QFLT, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> POWER =
			new Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorPower.compute(G.QFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,QuaternionFloat32GeneralTensorProductMember,QuaternionFloat32GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat32GeneralTensorProductMember> UNITY =
			new Procedure1<QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember result) {
			TensorUnity.compute(G.QFLT_TEN, G.QFLT, result);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<QuaternionFloat32GeneralTensorProductMember> NAN =
			new Procedure1<QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a) {
			FillNaN.compute(G.QFLT, a.rawData());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat32GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> ISINF =
			new Function1<Boolean, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat32GeneralTensorProductMember> INF =
			new Procedure1<QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a) {
			FillInfinite.compute(G.QFLT, a.rawData());
		}
	};
			
	@Override
	public Procedure1<QuaternionFloat32GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorRound.compute(G.QFLT_TEN, G.QFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.QFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.QFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> SBD =
			new Procedure3<Double, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.QFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.QFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.QFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member factor, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32Member factor, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			QuaternionFloat32Member invFactor = G.QFLT.construct();
			G.QFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat32Member, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, QuaternionFloat32GeneralTensorProductMember metricInverse, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.QFLT, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, QuaternionFloat32GeneralTensorProductMember metric, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.QFLT, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			QuaternionFloat32GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> OUTER =
			new Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b, QuaternionFloat32GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.QFLT_TEN, G.QFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QFLT_TEN, G.QFLT, new QuaternionFloat32Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionFloat32GeneralTensorProductMember a, QuaternionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.QFLT_TEN, G.QFLT, new QuaternionFloat32Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionFloat32GeneralTensorProductMember, QuaternionFloat32GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, QuaternionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(QuaternionFloat32GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.QFLT, a);
		}
	};
	
	@Override
	public Function1<Boolean, QuaternionFloat32GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public QuaternionFloat32Algebra getElementAlgebra() {
		return G.QFLT;
	}

	@Override
	public QuaternionFloat32GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new QuaternionFloat32GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
