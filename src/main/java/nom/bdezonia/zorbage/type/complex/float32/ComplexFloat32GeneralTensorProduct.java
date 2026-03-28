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
package nom.bdezonia.zorbage.type.complex.float32;

import java.lang.Integer;
import java.math.BigDecimal;

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
public class ComplexFloat32GeneralTensorProduct
	implements
		TensorProduct<ComplexFloat32GeneralTensorProduct,ComplexFloat32GeneralTensorProductMember,Float32Algebra,Float32Member>,
		Norm<ComplexFloat32GeneralTensorProductMember,Float32Member>,
		Scale<ComplexFloat32GeneralTensorProductMember,ComplexFloat32Member>,
		Rounding<Float32Member,ComplexFloat32GeneralTensorProductMember>,
		Infinite<ComplexFloat32GeneralTensorProductMember>,
		NaN<ComplexFloat32GeneralTensorProductMember>,
		ScaleByHighPrec<ComplexFloat32GeneralTensorProductMember>,
		ScaleByRational<ComplexFloat32GeneralTensorProductMember>,
		ScaleByDouble<ComplexFloat32GeneralTensorProductMember>,
		ScaleByOneHalf<ComplexFloat32GeneralTensorProductMember>,
		ScaleByTwo<ComplexFloat32GeneralTensorProductMember>,
		Tolerance<Float32Member, ComplexFloat32GeneralTensorProductMember>,
		ArrayLikeMethods<ComplexFloat32GeneralTensorProductMember, ComplexFloat32Member>,
		MadeOfElements<ComplexFloat32Algebra,ComplexFloat32Member>,
		ConstructibleTensor<ComplexFloat32GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "32-bit based complex tensor";
	}

	@Override
	public ComplexFloat32GeneralTensorProductMember construct() {
		return new ComplexFloat32GeneralTensorProductMember();
	}

	@Override
	public ComplexFloat32GeneralTensorProductMember construct(ComplexFloat32GeneralTensorProductMember other) {
		return new ComplexFloat32GeneralTensorProductMember(other);
	}

	@Override
	public ComplexFloat32GeneralTensorProductMember construct(String s) {
		return new ComplexFloat32GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember from, ComplexFloat32GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat32GeneralTensorProductMember> ZER =
			new Procedure1<ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> NEG =
			new Procedure2<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CFLT, G.CFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat32GeneralTensorProductMember,Float32Member> NORM =
			new Procedure2<ComplexFloat32GeneralTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.CFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32GeneralTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> SCALE =
			new Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CFLT, G.CFLT.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CFLT, G.CFLT.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			ComplexFloat32Member tmp = G.CFLT.construct();
			G.CFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> MULEL =
			new Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> MUL =
			new Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorContract.compute(G.CFLT, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorPower.compute(G.CFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat32GeneralTensorProductMember,ComplexFloat32GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat32GeneralTensorProductMember> UNITY =
			new Procedure1<ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember result) {
			TensorUnity.compute(G.CFLT_TEN, G.CFLT, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat32GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat32GeneralTensorProductMember> NAN =
			new Procedure1<ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a) {
			FillNaN.compute(G.CFLT, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat32GeneralTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat32GeneralTensorProductMember> INF =
			new Procedure1<ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a) {
			FillInfinite.compute(G.CFLT, a.rawData());
		}
	};
			
	@Override
	public Procedure1<ComplexFloat32GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorRound.compute(G.CFLT_TEN, G.CFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat32GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member factor, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member factor, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			ComplexFloat32Member invFactor = G.CFLT.construct();
			G.CFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, ComplexFloat32GeneralTensorProductMember metricInverse, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.CFLT, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat32GeneralTensorProductMember metric, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.CFLT, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat32GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> OUTER =
			new Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b, ComplexFloat32GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.CFLT_TEN, G.CFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CFLT_TEN, G.CFLT, new ComplexFloat32Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat32GeneralTensorProductMember a, ComplexFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CFLT_TEN, G.CFLT, new ComplexFloat32Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat32GeneralTensorProductMember, ComplexFloat32GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat32GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat32GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public ComplexFloat32Algebra getElementAlgebra() {
		return G.CFLT;
	}

	@Override
	public ComplexFloat32GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new ComplexFloat32GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
