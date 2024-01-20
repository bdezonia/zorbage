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
package nom.bdezonia.zorbage.type.octonion.highprec;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

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
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
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
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionHighPrecisionRModule
	implements
		RModule<OctonionHighPrecisionRModule,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionAlgebra,OctonionHighPrecisionMember>,
		Constructible1dLong<OctonionHighPrecisionRModuleMember>,
		Norm<OctonionHighPrecisionRModuleMember,HighPrecisionMember>,
		Products<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMember, OctonionHighPrecisionMatrixMember>,
		DirectProduct<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMatrixMember>,
		ScaleByHighPrec<OctonionHighPrecisionRModuleMember>,
		ScaleByRational<OctonionHighPrecisionRModuleMember>,
		ScaleByDouble<OctonionHighPrecisionRModuleMember>,
		ScaleByOneHalf<OctonionHighPrecisionRModuleMember>,
		ScaleByTwo<OctonionHighPrecisionRModuleMember>,
		Tolerance<HighPrecisionMember,OctonionHighPrecisionRModuleMember>,
		ArrayLikeMethods<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember>,
		ConstructibleFromBytes<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromShorts<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromInts<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromLongs<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromFloats<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromDoubles<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromBigIntegers<OctonionHighPrecisionRModuleMember>,
		ConstructibleFromBigDecimals<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromBytes<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromShorts<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromInts<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromLongs<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromFloats<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromDoubles<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromBigIntegers<OctonionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromBigDecimals<OctonionHighPrecisionRModuleMember>,
		CompositeType,
		ExactType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision octonion rmodule";
	}

	public OctonionHighPrecisionRModule() { }

	@Override
	public OctonionHighPrecisionRModuleMember construct() {
		return new OctonionHighPrecisionRModuleMember();
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(OctonionHighPrecisionRModuleMember other) {
		return new OctonionHighPrecisionRModuleMember(other);
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(String s) {
		return new OctonionHighPrecisionRModuleMember(s);
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(StorageConstruction s, long d1) {
		return new OctonionHighPrecisionRModuleMember(s, d1);
	}

	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(byte... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(short... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(int... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(long... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromLongsExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(float... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(double... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(BigInteger... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromBigIntegersExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember constructExactly(BigDecimal... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromBigDecimalsExact(vals);
		return v;
	}
	
	@Override
	public OctonionHighPrecisionRModuleMember construct(byte... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(short... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(int... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(long... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(float... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(double... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(BigInteger... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionRModuleMember construct(BigDecimal... vals) {
		OctonionHighPrecisionRModuleMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	private final Procedure1<OctonionHighPrecisionRModuleMember> ZER =
			new Procedure1<OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionRModuleMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> NEG =
			new Procedure2<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			RModuleNegate.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> negate() {
		return NEG;
	}
	
	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> ADD =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			RModuleAdd.compute(G.OHP, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> SUB =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			RModuleSubtract.compute(G.OHP, a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> EQ =
			new Function2<Boolean, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			return RModuleEqual.compute(G.OHP, a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> NEQ =
			new Function2<Boolean, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember from, OctonionHighPrecisionRModuleMember to) {
			RModuleAssign.compute(G.OHP, from, to);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionHighPrecisionRModuleMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionRModuleMember, HighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, HighPrecisionMember b) {
			RModuleDefaultNorm.compute(G.OHP, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionRModuleMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> SCALE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			RModuleScale.compute(G.OHP, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> CROSS =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			CrossProduct.compute(G.OHP_RMOD, G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember> DOT =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionMember c) {
			DotProduct.compute(G.OHP_RMOD, G.OHP, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember> PERP =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionMember c) {
			PerpDotProduct.compute(G.OHP_RMOD, G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> VTRIPLE =
			new Procedure4<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c,
				OctonionHighPrecisionRModuleMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*8];
			Arrays.fill(arr, BigDecimal.ZERO);
			OctonionHighPrecisionRModuleMember b_cross_c = new OctonionHighPrecisionRModuleMember(arr);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}
	
	private final Procedure4<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember> STRIPLE =
			new Procedure4<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c,
				OctonionHighPrecisionMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*8];
			Arrays.fill(arr, BigDecimal.ZERO);
			OctonionHighPrecisionRModuleMember b_cross_c = new OctonionHighPrecisionRModuleMember(arr);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};

	@Override
	public Procedure4<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> CONJ =
			new Procedure2<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			RModuleConjugate.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMatrixMember> VDP =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionMatrixMember c) {
			directProduct().call(a, b, c);
		}
	};

	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMatrixMember> DP =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember in1, OctonionHighPrecisionRModuleMember in2, OctonionHighPrecisionMatrixMember out) {
			RModuleDirectProduct.compute(G.OHP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember,OctonionHighPrecisionRModuleMember,OctonionHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, OctonionHighPrecisionRModuleMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionRModuleMember a) {
			return SequenceIsZero.compute(G.OHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionRModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			RModuleScaleByHighPrec.compute(G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> SBR =
			new Procedure3<RationalMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(RationalMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			RModuleScaleByRational.compute(G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> SBD =
			new Procedure3<Double, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Double a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			RModuleScaleByDouble.compute(G.OHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			return SequencesSimilar.compute(G.OHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> ADDS =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHP, G.OHP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> SUBS =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHP, G.OHP.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> MULS =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHP, G.OHP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> DIVS =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember scalar, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.OHP, G.OHP.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> MULTELEM =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OHP, G.OHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> DIVELEM =
			new Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b, OctonionHighPrecisionRModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.OHP, G.OHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> SCB2 =
			new Procedure3<Integer, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			ScaleHelper.compute(G.OHP_RMOD, G.OHP, new OctonionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> SCBH =
			new Procedure3<Integer, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Integer numTimes, OctonionHighPrecisionRModuleMember a, OctonionHighPrecisionRModuleMember b) {
			ScaleHelper.compute(G.OHP_RMOD, G.OHP, new OctonionHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, OctonionHighPrecisionRModuleMember, OctonionHighPrecisionRModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
