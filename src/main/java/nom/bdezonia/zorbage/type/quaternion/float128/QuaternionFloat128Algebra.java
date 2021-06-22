/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.quaternion.float128;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.QuaternionNumberWithin;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float128.Float128Algebra;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat128Algebra
	implements
		SkewField<QuaternionFloat128Algebra,QuaternionFloat128Member>,
		RealConstants<QuaternionFloat128Member>,
		ImaginaryConstants<QuaternionFloat128Member>,
		QuaternionConstants<QuaternionFloat128Member>,
		Norm<QuaternionFloat128Member, Float128Member>,
		Conjugate<QuaternionFloat128Member>,
		Infinite<QuaternionFloat128Member>,
		NaN<QuaternionFloat128Member>,
		Rounding<Float128Member,QuaternionFloat128Member>,
		Random<QuaternionFloat128Member>,
		Exponential<QuaternionFloat128Member>,
		Trigonometric<QuaternionFloat128Member>,
		Hyperbolic<QuaternionFloat128Member>,
		Power<QuaternionFloat128Member>,
		Roots<QuaternionFloat128Member>,
		RealUnreal<QuaternionFloat128Member,Float128Member>,
		Scale<QuaternionFloat128Member,QuaternionFloat128Member>,
		ScaleByHighPrec<QuaternionFloat128Member>,
		ScaleByRational<QuaternionFloat128Member>,
		ScaleByDouble<QuaternionFloat128Member>,
		ScaleComponents<QuaternionFloat128Member, Float128Member>,
		Tolerance<Float128Member,QuaternionFloat128Member>,
		ScaleByOneHalf<QuaternionFloat128Member>,
		ScaleByTwo<QuaternionFloat128Member>,
		ConstructibleFromBigDecimal<QuaternionFloat128Member>,
		ConstructibleFromBigInteger<QuaternionFloat128Member>,
		ConstructibleFromDouble<QuaternionFloat128Member>,
		ConstructibleFromLong<QuaternionFloat128Member>
{
	private static final QuaternionFloat128Member ZERO = new QuaternionFloat128Member();
	private static final QuaternionFloat128Member ONE_THIRD = new QuaternionFloat128Member(BigDecimal.ONE.divide(BigDecimal.valueOf(3), Float128Algebra.CONTEXT),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member ONE_HALF = new QuaternionFloat128Member(BigDecimal.valueOf(0.5),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member ONE = new QuaternionFloat128Member(BigDecimal.ONE,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member TWO = new QuaternionFloat128Member(BigDecimal.valueOf(2),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member PI = new QuaternionFloat128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member E = new QuaternionFloat128Member(new BigDecimal(HighPrecisionAlgebra.E_STR.substring(0,40)),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member GAMMA = new QuaternionFloat128Member(new BigDecimal(HighPrecisionAlgebra.GAMMA_STR.substring(0,40)),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member PHI = new QuaternionFloat128Member(new BigDecimal(HighPrecisionAlgebra.PHI_STR.substring(0,40)),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member I = new QuaternionFloat128Member(BigDecimal.ZERO,BigDecimal.ONE,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionFloat128Member J = new QuaternionFloat128Member(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ONE,BigDecimal.ZERO);
	private static final QuaternionFloat128Member K = new QuaternionFloat128Member(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ONE);
	
	public QuaternionFloat128Algebra() { }

	@Override
	public QuaternionFloat128Member construct() {
		return new QuaternionFloat128Member();
	}

	@Override
	public QuaternionFloat128Member construct(QuaternionFloat128Member other) {
		return new QuaternionFloat128Member(other);
	}

	@Override
	public QuaternionFloat128Member construct(String s) {
		return new QuaternionFloat128Member(s);
	}

	@Override
	public QuaternionFloat128Member construct(long... val) {
		return new QuaternionFloat128Member(val);
	}

	@Override
	public QuaternionFloat128Member construct(double... val) {
		return new QuaternionFloat128Member(val);
	}

	@Override
	public QuaternionFloat128Member construct(BigInteger... val) {
		return new QuaternionFloat128Member(val);
	}

	@Override
	public QuaternionFloat128Member construct(BigDecimal... val) {
		return new QuaternionFloat128Member(val);
	}
	
	private final Procedure1<QuaternionFloat128Member> UNITY =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> unity() {
		return UNITY;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> MUL =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			// for safety must use tmps
			Float128Member sumR = new Float128Member();
			Float128Member sumI = new Float128Member();
			Float128Member sumJ = new Float128Member();
			Float128Member sumK = new Float128Member();
			Float128Member tmp = new Float128Member();
			
			sumR.setPosZero();
			G.QUAD.multiply().call(a.r(), b.r(), tmp);
			G.QUAD.add().call(sumR, tmp, sumR);
			G.QUAD.multiply().call(a.i(), b.i(), tmp);
			G.QUAD.subtract().call(sumR, tmp, sumR);
			G.QUAD.multiply().call(a.j(), b.j(), tmp);
			G.QUAD.subtract().call(sumR, tmp, sumR);
			G.QUAD.multiply().call(a.k(), b.k(), tmp);
			G.QUAD.subtract().call(sumR, tmp, sumR);
			
			sumI.setPosZero();
			G.QUAD.multiply().call(a.r(), b.i(), tmp);
			G.QUAD.add().call(sumI, tmp, sumI);
			G.QUAD.multiply().call(a.i(), b.r(), tmp);
			G.QUAD.add().call(sumI, tmp, sumI);
			G.QUAD.multiply().call(a.j(), b.k(), tmp);
			G.QUAD.add().call(sumI, tmp, sumI);
			G.QUAD.multiply().call(a.k(), b.j(), tmp);
			G.QUAD.subtract().call(sumI, tmp, sumI);
			
			sumJ.setPosZero();
			G.QUAD.multiply().call(a.r(), b.j(), tmp);
			G.QUAD.add().call(sumJ, tmp, sumJ);
			G.QUAD.multiply().call(a.i(), b.k(), tmp);
			G.QUAD.subtract().call(sumJ, tmp, sumJ);
			G.QUAD.multiply().call(a.j(), b.r(), tmp);
			G.QUAD.add().call(sumJ, tmp, sumJ);
			G.QUAD.multiply().call(a.k(), b.i(), tmp);
			G.QUAD.add().call(sumJ, tmp, sumJ);
			
			sumK.setPosZero();
			G.QUAD.multiply().call(a.r(), b.k(), tmp);
			G.QUAD.add().call(sumK, tmp, sumK);
			G.QUAD.multiply().call(a.i(), b.j(), tmp);
			G.QUAD.add().call(sumK, tmp, sumK);
			G.QUAD.multiply().call(a.j(), b.i(), tmp);
			G.QUAD.subtract().call(sumK, tmp, sumK);
			G.QUAD.multiply().call(a.k(), b.r(), tmp);
			G.QUAD.add().call(sumK, tmp, sumK);

			c.setR(sumR);
			c.setI(sumI);
			c.setJ(sumJ);
			c.setK(sumK);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionFloat128Member,QuaternionFloat128Member> POWER =
			new Procedure3<Integer, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionFloat128Member a, QuaternionFloat128Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.QQUAD, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionFloat128Member,QuaternionFloat128Member> power() {
		return POWER;
	}

	private final Procedure1<QuaternionFloat128Member> ZER =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> NEG =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> ADD =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			G.QUAD.add().call(a.r(), b.r(), c.r());
			G.QUAD.add().call(a.i(), b.i(), c.i());
			G.QUAD.add().call(a.j(), b.j(), c.j());
			G.QUAD.add().call(a.k(), b.k(), c.k());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> add() {
		return ADD;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> SUB =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			G.QUAD.subtract().call(a.r(), b.r(), c.r());
			G.QUAD.subtract().call(a.i(), b.i(), c.i());
			G.QUAD.subtract().call(a.j(), b.j(), c.j());
			G.QUAD.subtract().call(a.k(), b.k(), c.k());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionFloat128Member,QuaternionFloat128Member> EQ =
			new Function2<Boolean, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public Boolean call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			return
				G.QUAD.isEqual().call(a.r(), b.r()) &&
				G.QUAD.isEqual().call(a.i(), b.i()) &&
				G.QUAD.isEqual().call(a.j(), b.j()) &&
				G.QUAD.isEqual().call(a.k(), b.k());
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128Member,QuaternionFloat128Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionFloat128Member,QuaternionFloat128Member> NEQ =
			new Function2<Boolean, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public Boolean call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionFloat128Member,QuaternionFloat128Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> ASSIGN =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member from, QuaternionFloat128Member to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> INV =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			QuaternionFloat128Member conjA = new QuaternionFloat128Member();
			QuaternionFloat128Member scale = new QuaternionFloat128Member();
			Float128Member nval = new Float128Member();
			norm().call(a, nval);
			G.QUAD.multiply().call(nval, nval, nval);
			G.QUAD.invert().call(nval, scale.r());
			conjugate().call(a, conjA);
			multiply().call(scale, conjA, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> invert() {
		return INV;
	}
	
	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> DIVIDE =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			QuaternionFloat128Member tmp = new QuaternionFloat128Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};

	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> divide() {
		return DIVIDE;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> CONJ =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			G.QUAD.assign().call(a.r(), b.r());
			G.QUAD.negate().call(a.i(), b.i());
			G.QUAD.negate().call(a.j(), b.j());
			G.QUAD.negate().call(a.k(), b.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionFloat128Member,Float128Member> NORM =
			new Procedure2<QuaternionFloat128Member, Float128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, Float128Member b) {
			// a hypot()-like implementation that avoids overflow
			Float128Member max = new Float128Member();
			Float128Member abs1 = new Float128Member();
			Float128Member abs2 = new Float128Member();
			Float128Member abs3 = new Float128Member();
			Float128Member abs4 = new Float128Member();
			G.QUAD.abs().call(a.r(), abs1);
			G.QUAD.abs().call(a.i(), abs2);
			G.QUAD.abs().call(a.j(), abs3);
			G.QUAD.abs().call(a.k(), abs4);
			G.QUAD.max().call(abs1, abs2, max);
			G.QUAD.max().call(max, abs3, max);
			G.QUAD.max().call(max, abs4, max);
			if (G.QUAD.isZero().call(max)) {
				b.setPosZero();
			}
			else {
				Float128Member sum = new Float128Member();
				Float128Member tmp = new Float128Member();
				G.QUAD.divide().call(a.r(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.i(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.j(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.k(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.sqrt().call(sum, tmp);
				G.QUAD.multiply().call(max, tmp, b);
			}
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,Float128Member> norm() {
		return NORM;
	}

	private final Procedure1<QuaternionFloat128Member> PI_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> PI() {
		return PI_;
	}

	private final Procedure1<QuaternionFloat128Member> E_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> E() {
		return E_;
	}

	private final Procedure1<QuaternionFloat128Member> GAMMA_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<QuaternionFloat128Member> PHI_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> PHI() {
		return PHI_;
	}

	private final Procedure1<QuaternionFloat128Member> I_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> I() {
		return I_;
	}

	private final Procedure1<QuaternionFloat128Member> J_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> J() {
		return J_;
	}

	private final Procedure1<QuaternionFloat128Member> K_ =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> K() {
		return K_;
	}

	private final Procedure4<Round.Mode,Float128Member,QuaternionFloat128Member,QuaternionFloat128Member> ROUND =
			new Procedure4<Round.Mode, Float128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Float128Member tmp = new Float128Member();
			tmp.setV(a.r());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setI(tmp.v());
			tmp.setV(a.j());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setJ(tmp.v());
			tmp.setV(a.k());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setK(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,QuaternionFloat128Member,QuaternionFloat128Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,QuaternionFloat128Member> ISNAN =
			new Function1<Boolean, QuaternionFloat128Member>()
	{
		@Override
		public Boolean call(QuaternionFloat128Member a) {
			return a.r().isNan() || a.i().isNan() || a.j().isNan() || a.k().isNan();
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat128Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<QuaternionFloat128Member> NAN =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			a.r().setNan();
			a.i().setNan();
			a.j().setNan();
			a.k().setNan();
		}
	};

	@Override
	public Procedure1<QuaternionFloat128Member> nan() {
		return NAN;
	}
	
	private final Function1<Boolean,QuaternionFloat128Member> ISINF =
			new Function1<Boolean, QuaternionFloat128Member>()
	{
		@Override
		public Boolean call(QuaternionFloat128Member a) {
			return !isNaN().call(a) && (
					a.r().isInfinite() || a.i().isInfinite() || a.j().isInfinite() || a.k().isInfinite());
		}
	};
	
	@Override
	public Function1<Boolean,QuaternionFloat128Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<QuaternionFloat128Member> INF =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			a.r().setPosInf();
			a.i().setPosInf();
			a.j().setPosInf();
			a.k().setPosInf();
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> infinite() {
		return INF;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> EXP =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			QuaternionFloat128Member tmp = new QuaternionFloat128Member();
			Float128Member u = new Float128Member();
			G.QUAD.exp().call(a.r(), u);
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinc().call(z, z2);
			Float128Member w = new Float128Member();
			Float128Member cos = new Float128Member();
			Float128Member uw = new Float128Member();
			w.set(z2);
			G.QUAD.multiply().call(u, w, uw);
			G.QUAD.cos().call(z, cos);
			G.QUAD.multiply().call(u, cos, b.r());
			G.QUAD.multiply().call(uw, a.i(), b.i());
			G.QUAD.multiply().call(uw, a.j(), b.j());
			G.QUAD.multiply().call(uw, a.k(), b.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> LOG =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Float128Member norm = new Float128Member(); 
			Float128Member term = new Float128Member(); 
			Float128Member v1 = new Float128Member();
			Float128Member v2 = new Float128Member();
			Float128Member v3 = new Float128Member();
			norm().call(a, norm);
			Float128Member multiplier = new Float128Member();
			G.QUAD.divide().call(a.r(), norm, multiplier);
			G.QUAD.multiply().call(multiplier, a.i(), v1);
			G.QUAD.multiply().call(multiplier, a.j(), v2);
			G.QUAD.multiply().call(multiplier, a.k(), v3);
			G.QUAD.acos().call(multiplier, term);
			
			G.QUAD.log().call(norm, b.r());
			G.QUAD.multiply().call(term, v1, b.i());
			G.QUAD.multiply().call(term, v2, b.j());
			G.QUAD.multiply().call(term, v3, b.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> log() {
		return LOG;
	}

	private final Procedure1<QuaternionFloat128Member> RAND =
			new Procedure1<QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a) {
			G.QUAD.random().call(a.r());
			G.QUAD.random().call(a.i());
			G.QUAD.random().call(a.j());
			G.QUAD.random().call(a.k());
		}
	};
	
	@Override
	public Procedure1<QuaternionFloat128Member> random() {
		return RAND;
	}

	private final Procedure2<QuaternionFloat128Member,Float128Member> REAL =
			new Procedure2<QuaternionFloat128Member, Float128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, Float128Member b) {
			b.set(a.r());
		}
	};

	@Override
	public Procedure2<QuaternionFloat128Member,Float128Member> real() {
		return REAL;
	}
	
	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> UNREAL =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			assign().call(a, b);
			b.r().setPosZero();
		}
	};

	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SINH =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat128Member negA = new QuaternionFloat128Member();
			QuaternionFloat128Member sum = new QuaternionFloat128Member();
			QuaternionFloat128Member tmp1 = new QuaternionFloat128Member();
			QuaternionFloat128Member tmp2 = new QuaternionFloat128Member();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sinh() {
		return SINH;
	}
	
	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> COSH =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat128Member negA = new QuaternionFloat128Member();
			QuaternionFloat128Member sum = new QuaternionFloat128Member();
			QuaternionFloat128Member tmp1 = new QuaternionFloat128Member();
			QuaternionFloat128Member tmp2 = new QuaternionFloat128Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};

	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> cosh() {
		return COSH;
    }

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> SINHANDCOSH =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member s, QuaternionFloat128Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionFloat128Member negA = new QuaternionFloat128Member();
			QuaternionFloat128Member sum = new QuaternionFloat128Member();
			QuaternionFloat128Member tmp1 = new QuaternionFloat128Member();
			QuaternionFloat128Member tmp2 = new QuaternionFloat128Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, s);

			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> TANH =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			QuaternionFloat128Member s = new QuaternionFloat128Member();
			QuaternionFloat128Member c = new QuaternionFloat128Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SIN =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member s) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			QuaternionFloat128Member tmp = new QuaternionFloat128Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinch().call(z, z2);
			Float128Member cos = new Float128Member();
			Float128Member sin = new Float128Member();
			Float128Member sinhc_pi = new Float128Member();
			Float128Member cosh = new Float128Member();
			Float128Member ws = new Float128Member();
			G.QUAD.cos().call(a.r(), cos);
			G.QUAD.sin().call(a.r(), sin);
			sinhc_pi.set(z2);
			G.QUAD.cosh().call(z, cosh);
			G.QUAD.multiply().call(cos, sinhc_pi, ws);
			
			G.QUAD.multiply().call(sin, cosh, s.r());
			G.QUAD.multiply().call(ws, a.i(), s.i());
			G.QUAD.multiply().call(ws, a.j(), s.j());
			G.QUAD.multiply().call(ws, a.k(), s.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> COS =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member s) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			QuaternionFloat128Member tmp = new QuaternionFloat128Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinch().call(z, z2);
			Float128Member cos = new Float128Member();
			Float128Member sin = new Float128Member();
			Float128Member sinhc_pi = new Float128Member();
			Float128Member cosh = new Float128Member();
			Float128Member wc = new Float128Member();
			G.QUAD.cos().call(a.r(), cos);
			G.QUAD.sin().call(a.r(), sin);
			sinhc_pi.set(z2);
			G.QUAD.cosh().call(z, cosh);
			G.QUAD.negate().call(sin, wc);
			G.QUAD.multiply().call(wc, sinhc_pi, wc);
			
			G.QUAD.multiply().call(cos, cosh, s.r());
			G.QUAD.multiply().call(wc, a.i(), s.i());
			G.QUAD.multiply().call(wc, a.j(), s.j());
			G.QUAD.multiply().call(wc, a.k(), s.k());
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> cos() {
		return COS;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> SINANDCOS =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member s, QuaternionFloat128Member c) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			QuaternionFloat128Member tmp = new QuaternionFloat128Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinch().call(z, z2);
			Float128Member cos = new Float128Member();
			Float128Member sin = new Float128Member();
			Float128Member sinhc_pi = new Float128Member();
			Float128Member cosh = new Float128Member();
			Float128Member ws = new Float128Member();
			Float128Member wc = new Float128Member();
			G.QUAD.cos().call(a.r(), cos);
			G.QUAD.sin().call(a.r(), sin);
			sinhc_pi.set(z2);
			G.QUAD.cosh().call(z, cosh);
			G.QUAD.multiply().call(cos, sinhc_pi, ws);
			G.QUAD.multiply().call(sin, sinhc_pi, wc);
			G.QUAD.negate().call(wc, wc);
			
			G.QUAD.multiply().call(sin, cosh, s.r());
			G.QUAD.multiply().call(ws, a.i(), s.i());
			G.QUAD.multiply().call(ws, a.j(), s.j());
			G.QUAD.multiply().call(ws, a.k(), s.k());

			G.QUAD.multiply().call(cos, cosh, c.r());
			G.QUAD.multiply().call(wc, a.i(), c.i());
			G.QUAD.multiply().call(wc, a.j(), c.j());
			G.QUAD.multiply().call(wc, a.k(), c.k());
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> sinAndCos() {
		return SINANDCOS;
	}
	
	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> TAN =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			QuaternionFloat128Member sin = new QuaternionFloat128Member();
			QuaternionFloat128Member cos = new QuaternionFloat128Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> POW =
			new Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			QuaternionFloat128Member logA = new QuaternionFloat128Member();
			QuaternionFloat128Member bLogA = new QuaternionFloat128Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionFloat128Member,QuaternionFloat128Member,QuaternionFloat128Member> pow() {
		return POW;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SINCH =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Sinch.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SINCHPI =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Sinchpi.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SINC =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Sinc.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SINCPI =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			Sincpi.compute(G.QQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> SQRT =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> CBRT =
			new Procedure2<QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(QuaternionFloat128Member a, QuaternionFloat128Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionFloat128Member,QuaternionFloat128Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, QuaternionFloat128Member> ISZERO =
			new Function1<Boolean, QuaternionFloat128Member>()
	{
		@Override
		public Boolean call(QuaternionFloat128Member a) {
			return a.r().isZero() && a.i().isZero() && a.j().isZero() && a.k().isZero();
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<QuaternionFloat128Member, QuaternionFloat128Member, QuaternionFloat128Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, QuaternionFloat128Member, QuaternionFloat128Member> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			G.QUAD.scaleByHighPrec().call(a, b.r(), c.r());
			G.QUAD.scaleByHighPrec().call(a, b.i(), c.i());
			G.QUAD.scaleByHighPrec().call(a, b.j(), c.j());
			G.QUAD.scaleByHighPrec().call(a, b.k(), c.k());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, QuaternionFloat128Member, QuaternionFloat128Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionFloat128Member, QuaternionFloat128Member> SBR =
			new Procedure3<RationalMember, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(RationalMember a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			G.QUAD.scaleByRational().call(a, b.r(), c.r());
			G.QUAD.scaleByRational().call(a, b.i(), c.i());
			G.QUAD.scaleByRational().call(a, b.j(), c.j());
			G.QUAD.scaleByRational().call(a, b.k(), c.k());
		}
	};

	@Override
	public Procedure3<RationalMember, QuaternionFloat128Member, QuaternionFloat128Member> scaleByRational() {
		return SBR;
	}
	
	private final Procedure3<Double, QuaternionFloat128Member, QuaternionFloat128Member> SBD =
			new Procedure3<Double, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(Double a, QuaternionFloat128Member b, QuaternionFloat128Member c) {
			G.QUAD.scaleByDouble().call(a, b.r(), c.r());
			G.QUAD.scaleByDouble().call(a, b.i(), c.i());
			G.QUAD.scaleByDouble().call(a, b.j(), c.j());
			G.QUAD.scaleByDouble().call(a, b.k(), c.k());
		}
	};

	@Override
	public Procedure3<Double, QuaternionFloat128Member, QuaternionFloat128Member> scaleByDouble() {
		return SBD;
	}
	
	private final Procedure3<Float128Member, QuaternionFloat128Member, QuaternionFloat128Member> SC =
			new Procedure3<Float128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(Float128Member factor, QuaternionFloat128Member a, QuaternionFloat128Member b) {
			G.QUAD.multiply().call(factor, a.r(), b.r());
			G.QUAD.multiply().call(factor, a.i(), b.i());
			G.QUAD.multiply().call(factor, a.j(), b.j());
			G.QUAD.multiply().call(factor, a.k(), b.k());
		}
	};

	@Override
	public Procedure3<Float128Member, QuaternionFloat128Member, QuaternionFloat128Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float128Member, QuaternionFloat128Member, QuaternionFloat128Member> WITHIN =
			new Function3<Boolean, Float128Member, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		
		@Override
		public Boolean call(Float128Member tol, QuaternionFloat128Member a, QuaternionFloat128Member b) {
			return QuaternionNumberWithin.compute(G.QUAD, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float128Member, QuaternionFloat128Member, QuaternionFloat128Member> within() {
		return WITHIN;
	}
	
	private final Procedure3<java.lang.Integer, QuaternionFloat128Member, QuaternionFloat128Member> STWO =
			new Procedure3<java.lang.Integer, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat128Member a, QuaternionFloat128Member b) {
			ScaleHelper.compute(G.QQUAD, G.QQUAD, TWO, numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat128Member, QuaternionFloat128Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, QuaternionFloat128Member, QuaternionFloat128Member> SHALF =
			new Procedure3<java.lang.Integer, QuaternionFloat128Member, QuaternionFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, QuaternionFloat128Member a, QuaternionFloat128Member b) {
			ScaleHelper.compute(G.QQUAD, G.QQUAD, ONE_HALF, numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, QuaternionFloat128Member, QuaternionFloat128Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, QuaternionFloat128Member> ISUNITY =
			new Function1<Boolean, QuaternionFloat128Member>()
	{
		@Override
		public Boolean call(QuaternionFloat128Member a) {
			return G.QQUAD.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, QuaternionFloat128Member> isUnity() {
		return ISUNITY;
	}
}
