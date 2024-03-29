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
package nom.bdezonia.zorbage.type.real.highprec;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.RModuleType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleDefaultNorm;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByDouble;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByRational;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionVector
	implements
		VectorSpace<HighPrecisionVector,HighPrecisionVectorMember,HighPrecisionAlgebra,HighPrecisionMember>,
		Constructible1dLong<HighPrecisionVectorMember>,
		Norm<HighPrecisionVectorMember,HighPrecisionMember>,
		Products<HighPrecisionVectorMember, HighPrecisionMember, HighPrecisionMatrixMember>,
		DirectProduct<HighPrecisionVectorMember, HighPrecisionMatrixMember>,
		ScaleByHighPrec<HighPrecisionVectorMember>,
		ScaleByRational<HighPrecisionVectorMember>,
		ScaleByDouble<HighPrecisionVectorMember>,
		ScaleByOneHalf<HighPrecisionVectorMember>,
		ScaleByTwo<HighPrecisionVectorMember>,
		Tolerance<HighPrecisionMember,HighPrecisionVectorMember>,
		ArrayLikeMethods<HighPrecisionVectorMember,HighPrecisionMember>,
		ConstructibleFromBytes<HighPrecisionVectorMember>,
		ConstructibleFromShorts<HighPrecisionVectorMember>,
		ConstructibleFromInts<HighPrecisionVectorMember>,
		ConstructibleFromLongs<HighPrecisionVectorMember>,
		ConstructibleFromFloats<HighPrecisionVectorMember>,
		ConstructibleFromDoubles<HighPrecisionVectorMember>,
		ConstructibleFromBigIntegers<HighPrecisionVectorMember>,
		ConstructibleFromBigDecimals<HighPrecisionVectorMember>,
		ExactlyConstructibleFromBigDecimals<HighPrecisionVectorMember>,
		ExactlyConstructibleFromBigIntegers<HighPrecisionVectorMember>,
		ExactlyConstructibleFromDoubles<HighPrecisionVectorMember>,
		ExactlyConstructibleFromFloats<HighPrecisionVectorMember>,
		ExactlyConstructibleFromLongs<HighPrecisionVectorMember>,
		ExactlyConstructibleFromInts<HighPrecisionVectorMember>,
		ExactlyConstructibleFromShorts<HighPrecisionVectorMember>,
		ExactlyConstructibleFromBytes<HighPrecisionVectorMember>,
		CompositeType,
		ExactType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision real vector";
	}

	public HighPrecisionVector() { }
	
	private final Procedure1<HighPrecisionVectorMember> ZER =
			new Procedure1<HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<HighPrecisionVectorMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> NEG =
			new Procedure2<HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			RModuleNegate.compute(G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> negate() {
		return NEG;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> ADD =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleAdd.compute(G.HP, a, b, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> add() {
		return ADD;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> SUB =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleSubtract.compute(G.HP, a, b, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> EQ =
			new Function2<Boolean, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			return RModuleEqual.compute(G.HP, a, b);
		}
	};

	@Override
	public Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> NEQ =
			new Function2<Boolean, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public HighPrecisionVectorMember construct() {
		return new HighPrecisionVectorMember();
	}

	@Override
	public HighPrecisionVectorMember construct(HighPrecisionVectorMember other) {
		return new HighPrecisionVectorMember(other);
	}

	@Override
	public HighPrecisionVectorMember construct(String s) {
		return new HighPrecisionVectorMember(s);
	}

	@Override
	public HighPrecisionVectorMember construct(StorageConstruction s, long d1) {
		return new HighPrecisionVectorMember(s, d1);
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(byte... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(short... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(int... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(long... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromLongsExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(float... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(double... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(BigInteger... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromBigIntegersExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember constructExactly(BigDecimal... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromBigDecimalsExact(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(byte... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(short... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromShorts(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(int... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromInts(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(long... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromLongs(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(float... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromFloats(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(double... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(BigInteger... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}
	
	@Override
	public HighPrecisionVectorMember construct(BigDecimal... vals) {
		HighPrecisionVectorMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	private final Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> ASSIGN =
			new Procedure2<HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember from, HighPrecisionVectorMember to) {
			RModuleAssign.compute(G.HP, from, to);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<HighPrecisionVectorMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionMember b) {
			RModuleDefaultNorm.compute(G.HP, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionVectorMember,HighPrecisionVectorMember> SCALE =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			RModuleScale.compute(G.HP, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionVectorMember,HighPrecisionVectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> CROSS =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			CrossProduct.compute(G.HP_VEC, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> DOT =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionMember c) {
			DotProduct.compute(G.HP_VEC, G.HP, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> PERP =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionMember c) {
			PerpDotProduct.compute(G.HP_VEC, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> VTRIPLE =
			new Procedure4<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c, HighPrecisionVectorMember d) {
			BigDecimal[] vals = new BigDecimal[3];
			vals[0] = BigDecimal.ZERO;
			vals[1] = BigDecimal.ZERO;
			vals[2] = BigDecimal.ZERO;
			HighPrecisionVectorMember b_cross_c = new HighPrecisionVectorMember(vals);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> STRIPLE =
			new Procedure4<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c, HighPrecisionMember d) {
			BigDecimal[] vals = new BigDecimal[3];
			vals[0] = BigDecimal.ZERO;
			vals[1] = BigDecimal.ZERO;
			vals[2] = BigDecimal.ZERO;
			HighPrecisionVectorMember b_cross_c = new HighPrecisionVectorMember(vals);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> VDP =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember in1, HighPrecisionVectorMember in2, HighPrecisionMatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> DP =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember in1, HighPrecisionVectorMember in2, HighPrecisionMatrixMember out) {
			RModuleDirectProduct.compute(G.HP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, HighPrecisionVectorMember> ISZERO =
			new Function1<Boolean, HighPrecisionVectorMember>()
	{
		@Override	
		public Boolean call(HighPrecisionVectorMember a) {
			return SequenceIsZero.compute(G.HP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionVectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> SBHP =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleScaleByHighPrec.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, HighPrecisionVectorMember, HighPrecisionVectorMember> SBR =
			new Procedure3<RationalMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(RationalMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleScaleByRational.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, HighPrecisionVectorMember, HighPrecisionVectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, HighPrecisionVectorMember, HighPrecisionVectorMember> SBD =
			new Procedure3<Double, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(Double a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleScaleByDouble.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, HighPrecisionVectorMember, HighPrecisionVectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			return SequencesSimilar.compute(G.HP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> ADDS =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HP, G.HP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> SUBS =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HP, G.HP.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> MULS =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HP, G.HP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> DIVS =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HP, G.HP.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember> MULTELEM =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.HP, G.HP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember> DIVELEM =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.HP, G.HP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, HighPrecisionVectorMember, HighPrecisionVectorMember> SCB2 =
			new Procedure3<Integer, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(Integer numTimes, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			ScaleHelper.compute(G.HP_VEC, G.HP, new HighPrecisionMember(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionVectorMember, HighPrecisionVectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, HighPrecisionVectorMember, HighPrecisionVectorMember> SCBH =
			new Procedure3<Integer, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(Integer numTimes, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			ScaleHelper.compute(G.HP_VEC, G.HP, new HighPrecisionMember(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionVectorMember, HighPrecisionVectorMember> scaleByOneHalf() {
		return SCBH;
	}

}
