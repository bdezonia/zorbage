/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.octonion.float128;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.TensorType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
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
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat128CartesianTensorProduct
	implements
		TensorLikeProduct<OctonionFloat128CartesianTensorProduct,OctonionFloat128CartesianTensorProductMember,OctonionFloat128Algebra,OctonionFloat128Member>,
		Norm<OctonionFloat128CartesianTensorProductMember,Float128Member>,
		Scale<OctonionFloat128CartesianTensorProductMember,OctonionFloat128Member>,
		Rounding<Float128Member,OctonionFloat128CartesianTensorProductMember>,
		Infinite<OctonionFloat128CartesianTensorProductMember>,
		NaN<OctonionFloat128CartesianTensorProductMember>,
		ScaleByHighPrec<OctonionFloat128CartesianTensorProductMember>,
		ScaleByRational<OctonionFloat128CartesianTensorProductMember>,
		ScaleByDouble<OctonionFloat128CartesianTensorProductMember>,
		ScaleByOneHalf<OctonionFloat128CartesianTensorProductMember>,
		ScaleByTwo<OctonionFloat128CartesianTensorProductMember>,
		Tolerance<Float128Member, OctonionFloat128CartesianTensorProductMember>,
		ArrayLikeMethods<OctonionFloat128CartesianTensorProductMember, OctonionFloat128Member>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		SignedType,
		TensorType,
		UnityIncludedType,
		ZeroIncludedType
{

	@Override
	public String typeDescription() {
		return "128-bit based octonion tensor";
	}

	@Override
	public OctonionFloat128CartesianTensorProductMember construct() {
		return new OctonionFloat128CartesianTensorProductMember();
	}

	@Override
	public OctonionFloat128CartesianTensorProductMember construct(OctonionFloat128CartesianTensorProductMember other) {
		return new OctonionFloat128CartesianTensorProductMember(other);
	}

	@Override
	public OctonionFloat128CartesianTensorProductMember construct(String s) {
		return new OctonionFloat128CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OQUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember from, OctonionFloat128CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.OQUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat128CartesianTensorProductMember> ZER =
			new Procedure1<OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> NEG =
			new Procedure2<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OQUAD, G.OQUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> ADDEL =
			new Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OQUAD, G.OQUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> SUBEL =
			new Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.OQUAD, G.OQUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat128CartesianTensorProductMember,Float128Member> NORM =
			new Procedure2<OctonionFloat128CartesianTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.OQUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat128CartesianTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> CONJ =
			new Procedure2<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.OQUAD, G.OQUAD.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> SCALE =
			new Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.OQUAD, G.OQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.OQUAD, G.OQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128Member scalar, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			OctonionFloat128Member tmp = G.OQUAD.construct();
			G.OQUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> MULEL =
			new Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OQUAD, G.OQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.OQUAD, G.OQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> MUL =
			new Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorContract.compute(G.OQUAD, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.OQUAD_TEN, G.OQUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.OQUAD_TEN, G.OQUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorPower.compute(G.OQUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat128CartesianTensorProductMember,OctonionFloat128CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat128CartesianTensorProductMember> UNITY =
			new Procedure1<OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember result) {
			TensorUnity.compute(G.OQUAD_TEN, G.OQUAD, result);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat128CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat128CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat128CartesianTensorProductMember> NAN =
			new Procedure1<OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a) {
			FillNaN.compute(G.OQUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat128CartesianTensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat128CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat128CartesianTensorProductMember> INF =
			new Procedure1<OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a) {
			FillInfinite.compute(G.OQUAD, a.rawData());
		}
	};
			
	@Override
	public Procedure1<OctonionFloat128CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorRound.compute(G.OQUAD_TEN, G.OQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat128CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat128CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.OQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.OQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.OQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.OQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.OQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128Member factor, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128Member factor, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			OctonionFloat128Member invFactor = G.OQUAD.construct();
			G.OQUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat128CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> OUTER =
			new Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b, OctonionFloat128CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.OQUAD_TEN, G.OQUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OQUAD_TEN, G.OQUAD, new OctonionFloat128Member(2, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionFloat128CartesianTensorProductMember a, OctonionFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.OQUAD_TEN, G.OQUAD, new OctonionFloat128Member(0.5, 0, 0, 0, 0, 0, 0, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionFloat128CartesianTensorProductMember, OctonionFloat128CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, OctonionFloat128CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, OctonionFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat128CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.OQUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, OctonionFloat128CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
