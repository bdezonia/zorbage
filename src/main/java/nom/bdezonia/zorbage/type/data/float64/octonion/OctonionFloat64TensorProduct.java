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
package nom.bdezonia.zorbage.type.data.float64.octonion;

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
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
import nom.bdezonia.zorbage.algorithm.TensorShape;
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
import nom.bdezonia.zorbage.type.algebra.TensorLikeProduct;

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

import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
omplex */
public class OctonionFloat64TensorProduct
	implements
		TensorLikeProduct<OctonionFloat64TensorProduct,OctonionFloat64TensorProductMember,OctonionFloat64Algebra,OctonionFloat64Member>,
		ConstructibleNdLong<OctonionFloat64TensorProductMember>,
		Norm<OctonionFloat64TensorProductMember,Float64Member>,
		Scale<OctonionFloat64TensorProductMember,OctonionFloat64Member>,
		Rounding<Float64Member,OctonionFloat64TensorProductMember>,
		Infinite<OctonionFloat64TensorProductMember>,
		NaN<OctonionFloat64TensorProductMember>,
		ScaleByHighPrec<OctonionFloat64TensorProductMember>,
		ScaleByRational<OctonionFloat64TensorProductMember>,
		ScaleByDouble<OctonionFloat64TensorProductMember>,
		Tolerance<Float64Member, OctonionFloat64TensorProductMember>
{
	@Override
	public OctonionFloat64TensorProductMember construct() {
		return new OctonionFloat64TensorProductMember();
	}

	@Override
	public OctonionFloat64TensorProductMember construct(OctonionFloat64TensorProductMember other) {
		return new OctonionFloat64TensorProductMember(other);
	}

	@Override
	public OctonionFloat64TensorProductMember construct(String s) {
		return new OctonionFloat64TensorProductMember(s);
	}

	@Override
	public OctonionFloat64TensorProductMember construct(StorageConstruction s, long[] nd) {
		return new OctonionFloat64TensorProductMember(s, nd);
	}

	private final Function2<Boolean,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> EQ =
			new Function2<Boolean,OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.ODBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> NEQ =
			new Function2<Boolean,OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> ASSIGN =
			new Procedure2<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember from, OctonionFloat64TensorProductMember to) {
			TensorShape.compute(from, to);
			Copy.compute(G.ODBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<OctonionFloat64TensorProductMember> ZER =
			new Procedure1<OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64TensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> NEG =
			new Procedure2<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.ODBL, G.ODBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> ADDEL =
			new Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> SUBEL =
			new Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<OctonionFloat64TensorProductMember,Float64Member> NORM =
			new Procedure2<OctonionFloat64TensorProductMember, Float64Member>() 
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.ODBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<OctonionFloat64TensorProductMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> CONJ =
			new Procedure2<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.ODBL, G.ODBL.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> SCALE =
			new Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.ODBL, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> ADDSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2.compute(G.ODBL, scalar, G.ODBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<OctonionFloat64Member,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> SUBSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member scalar, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			OctonionFloat64Member tmp = G.ODBL.construct();
			G.ODBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> MULEL =
			new Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> DIVIDEEL =
			new Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.ODBL, G.ODBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> MUL =
			new Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorContract.compute(G.ODBL, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> contract() {
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
	
	private final Procedure3<IntegerIndex,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> COMMA =
			new Procedure3<IntegerIndex,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			OctonionFloat64Member val = G.ODBL.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> POWER =
			new Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Integer power, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorPower.compute(G.ODBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,OctonionFloat64TensorProductMember,OctonionFloat64TensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat64TensorProductMember> UNITY =
			new Procedure1<OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember result) {
			OctonionFloat64Member one = G.ODBL.construct();
			G.ODBL.unity().call(one);
			zero().call(result);
			IntegerIndex index = new IntegerIndex(result.rank());
			for (long d = 0; d < result.dimension(0); d++) {
				for (int r = 0; r < result.rank(); r++) {
					index.set(r, d);
				}
				result.setV(index, one);
			}
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64TensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, OctonionFloat64TensorProductMember> ISNAN =
			new Function1<Boolean, OctonionFloat64TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64TensorProductMember a) {
			return SequenceIsNan.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64TensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat64TensorProductMember> NAN =
			new Procedure1<OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a) {
			FillNaN.compute(G.ODBL, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat64TensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, OctonionFloat64TensorProductMember> ISINF =
			new Function1<Boolean, OctonionFloat64TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64TensorProductMember a) {
			return SequenceIsInf.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64TensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat64TensorProductMember> INF =
			new Procedure1<OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a) {
			FillInfinite.compute(G.ODBL, a);
		}
	};
			
	@Override
	public Procedure1<OctonionFloat64TensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			OctonionFloat64Member tmp = G.ODBL.construct();
			long numElems = a.numElems();
			for (long i = 0; i < numElems; i++) {
				a.v(i, tmp);
				G.ODBL.round().call(mode, delta, tmp, tmp);
				b.setV(i, tmp);
			}
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, OctonionFloat64TensorProductMember> ISZERO =
			new Function1<Boolean, OctonionFloat64TensorProductMember>()
	{
		@Override
		public Boolean call(OctonionFloat64TensorProductMember a) {
			return SequenceIsZero.compute(G.ODBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat64TensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> SBR =
			new Procedure3<RationalMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.ODBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> SBD =
			new Procedure3<Double, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Double factor, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.ODBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.ODBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.dimension(i) != b.dimension(i))
					return false;
			}
			return SequencesSimilar.compute(G.ODBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> MULBYSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member factor, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> DIVBYSCALAR =
			new Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64Member factor, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			OctonionFloat64Member invFactor = G.ODBL.construct();
			G.ODBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat64Member, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> RAISE =
		new Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> LOWER =
		new Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Integer idx, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> INNER =
		new Procedure5<Integer, Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			OctonionFloat64TensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> OUTER =
			new Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember>()
	{
		@Override
		public void call(OctonionFloat64TensorProductMember a, OctonionFloat64TensorProductMember b, OctonionFloat64TensorProductMember c) {

			// how an outer product is calculated:
			//   https://www.math3ma.com/blog/the-tensor-product-demystified
			
			if (c == a || c == b)
				throw new IllegalArgumentException("destination tensor cannot be one of the inputs");
			long dimA = a.dimension(0);
			long dimB = b.dimension(0);
			if (dimA != dimB)
				throw new IllegalArgumentException("dimension of tensors must match");
			int rankA = a.numDimensions();
			int rankB = b.numDimensions();
			int rankC = rankA + rankB;
			long[] cDims = new long[rankC];
			for (int i = 0; i < cDims.length; i++) {
				cDims[i] = dimA;
			}
			c.alloc(cDims);
			OctonionFloat64Member aTmp = G.ODBL.construct();
			OctonionFloat64Member bTmp = G.ODBL.construct();
			OctonionFloat64Member cTmp = G.ODBL.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.ODBL.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember, OctonionFloat64TensorProductMember> outerProduct() {
		return OUTER;
	}
	
}
