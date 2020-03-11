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
package nom.bdezonia.zorbage.type.data.float32.complex;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
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
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;

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

import nom.bdezonia.zorbage.type.algebra.TensorProduct;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat32TensorProduct
	implements
		TensorProduct<ComplexFloat32TensorProduct,ComplexFloat32TensorProductMember,ComplexFloat32Algebra,ComplexFloat32Member>,
		ConstructibleNdLong<ComplexFloat32TensorProductMember>,
		Norm<ComplexFloat32TensorProductMember,Float32Member>,
		Scale<ComplexFloat32TensorProductMember,ComplexFloat32Member>,
		Rounding<Float32Member,ComplexFloat32TensorProductMember>,
		Infinite<ComplexFloat32TensorProductMember>,
		NaN<ComplexFloat32TensorProductMember>,
		ScaleByHighPrec<ComplexFloat32TensorProductMember>,
		ScaleByRational<ComplexFloat32TensorProductMember>,
		ScaleByDouble<ComplexFloat32TensorProductMember>,
		Tolerance<Float32Member, ComplexFloat32TensorProductMember>
{
	@Override
	public ComplexFloat32TensorProductMember construct() {
		return new ComplexFloat32TensorProductMember();
	}

	@Override
	public ComplexFloat32TensorProductMember construct(ComplexFloat32TensorProductMember other) {
		return new ComplexFloat32TensorProductMember(other);
	}

	@Override
	public ComplexFloat32TensorProductMember construct(String s) {
		return new ComplexFloat32TensorProductMember(s);
	}

	@Override
	public ComplexFloat32TensorProductMember construct(StorageConstruction s, long[] nd) {
		return new ComplexFloat32TensorProductMember(s, nd);
	}

	private final Function2<Boolean,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember from, ComplexFloat32TensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.CFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat32TensorProductMember> ZER =
			new Procedure1<ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32TensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> NEG =
			new Procedure2<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CFLT, G.CFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> ADDEL =
			new Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> SUBEL =
			new Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat32TensorProductMember,Float32Member> NORM =
			new Procedure2<ComplexFloat32TensorProductMember, Float32Member>() 
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.CFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32TensorProductMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure2<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> CONJ =
			new Procedure2<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CFLT, G.CFLT.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> SCALE =
			new Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.CFLT, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.CFLT, scalar, G.CFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			ComplexFloat32Member tmp = G.CFLT.construct();
			G.CFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> MULEL =
			new Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> MUL =
			new Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorContract.compute(G.CFLT, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> COMMA =
			new Procedure3<IntegerIndex,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			ComplexFloat32Member val = G.CFLT.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorPower.compute(G.CFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat32TensorProductMember,ComplexFloat32TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat32TensorProductMember> UNITY =
			new Procedure1<ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember result) {
			TensorUnity.compute(G.CFLT_TEN, G.CFLT, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat32TensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat32TensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32TensorProductMember a) {
			return SequenceIsNan.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32TensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat32TensorProductMember> NAN =
			new Procedure1<ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a) {
			FillNaN.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32TensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat32TensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat32TensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32TensorProductMember a) {
			return SequenceIsInf.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32TensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat32TensorProductMember> INF =
			new Procedure1<ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a) {
			FillInfinite.compute(G.CFLT, a);
		}
	};
			
	@Override
	public Procedure1<ComplexFloat32TensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorRound.compute(G.CFLT_TEN, G.CFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat32TensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat32TensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32TensorProductMember a) {
			return SequenceIsZero.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32TensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member factor, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member factor, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			ComplexFloat32Member invFactor = G.CFLT.construct();
			G.CFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> RAISE =
		new Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> LOWER =
		new Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat32TensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> OUTER =
			new Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32TensorProductMember a, ComplexFloat32TensorProductMember b, ComplexFloat32TensorProductMember c) {

			TensorOuterProduct.compute(G.CFLT_TEN, G.CFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember, ComplexFloat32TensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
