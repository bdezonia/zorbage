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
package nom.bdezonia.zorbage.type.octonion.float32;

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
public class OctonionFloat32GeneralTensorProduct
	implements
		TensorProduct<OctonionFloat32GeneralTensorProduct,OctonionFloat32GeneralTensorProductMember,Float32Algebra,Float32Member>,
		Norm<OctonionFloat32GeneralTensorProductMember,Float32Member>,
		Scale<OctonionFloat32GeneralTensorProductMember,OctonionFloat32Member>,
		Rounding<Float32Member,OctonionFloat32GeneralTensorProductMember>,
		Infinite<OctonionFloat32GeneralTensorProductMember>,
		NaN<OctonionFloat32GeneralTensorProductMember>,
		ScaleByHighPrec<OctonionFloat32GeneralTensorProductMember>,
		ScaleByRational<OctonionFloat32GeneralTensorProductMember>,
		ScaleByDouble<OctonionFloat32GeneralTensorProductMember>,
		ScaleByOneHalf<OctonionFloat32GeneralTensorProductMember>,
		ScaleByTwo<OctonionFloat32GeneralTensorProductMember>,
		Tolerance<Float32Member, OctonionFloat32GeneralTensorProductMember>,
		ArrayLikeMethods<OctonionFloat32GeneralTensorProductMember, OctonionFloat32Member>,
		MadeOfElements<OctonionFloat32Algebra,OctonionFloat32Member>,
		ConstructibleTensor<OctonionFloat32GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "32-bit based octonion tensor";
	}

	@Override
	public OctonionFloat32GeneralTensorProductMember construct() {
		return new OctonionFloat32GeneralTensorProductMember();
	}

	@Override
	public OctonionFloat32GeneralTensorProductMember construct(OctonionFloat32GeneralTensorProductMember other) {
		return new OctonionFloat32GeneralTensorProductMember(other);
	}

	@Override
	public OctonionFloat32GeneralTensorProductMember construct(String s) {
		return new OctonionFloat32GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember from, OctonionFloat32GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.OFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat32GeneralTensorProductMember> ZER =
			new Procedure1<OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> NEG =
			new Procedure2<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OFLT, G.OFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> ADDEL =
			new Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> SUBEL =
			new Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat32GeneralTensorProductMember,Float32Member> NORM =
			new Procedure2<OctonionFloat32GeneralTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.OFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat32GeneralTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> SCALE =
			new Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.OFLT, G.OFLT.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.OFLT, G.OFLT.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat32Member,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member scalar, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			OctonionFloat32Member tmp = G.OFLT.construct();
			G.OFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> MULEL =
			new Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OFLT, G.OFLT.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> MUL =
			new Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorContract.compute(G.OFLT, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorPower.compute(G.OFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat32GeneralTensorProductMember,OctonionFloat32GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat32GeneralTensorProductMember> UNITY =
			new Procedure1<OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember result) {
			TensorUnity.compute(G.OFLT_TEN, G.OFLT, result);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat32GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat32GeneralTensorProductMember> NAN =
			new Procedure1<OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a) {
			FillNaN.compute(G.OFLT, a.rawData());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat32GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat32GeneralTensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat32GeneralTensorProductMember> INF =
			new Procedure1<OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a) {
			FillInfinite.compute(G.OFLT, a.rawData());
		}
	};
			
	@Override
	public Procedure1<OctonionFloat32GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorRound.compute(G.OFLT_TEN, G.OFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat32GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.OFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat32GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member factor, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32Member factor, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			OctonionFloat32Member invFactor = G.OFLT.construct();
			G.OFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat32Member, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, OctonionFloat32GeneralTensorProductMember metricInverse, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.OFLT, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat32GeneralTensorProductMember metric, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.OFLT, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat32GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> OUTER =
			new Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b, OctonionFloat32GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.OFLT_TEN, G.OFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OFLT_TEN, G.OFLT, new OctonionFloat32Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat32GeneralTensorProductMember a, OctonionFloat32GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OFLT_TEN, G.OFLT, new OctonionFloat32Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat32GeneralTensorProductMember, OctonionFloat32GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, OctonionFloat32GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, OctonionFloat32GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat32GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.OFLT, a);
		}
	};
	
	@Override
	public Function1<Boolean, OctonionFloat32GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public OctonionFloat32Algebra getElementAlgebra() {
		return G.OFLT;
	}

	@Override
	public OctonionFloat32GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new OctonionFloat32GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
