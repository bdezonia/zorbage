/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.highprec.quaternion;

import java.math.BigDecimal;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.RealConstants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.ImaginaryConstants;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.QuaternionConstants;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.SkewField;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionHighPrecisionAlgebra
  implements
    SkewField<QuaternionHighPrecisionAlgebra,QuaternionHighPrecisionMember>,
    RealConstants<QuaternionHighPrecisionMember>,
    ImaginaryConstants<QuaternionHighPrecisionMember>,
    QuaternionConstants<QuaternionHighPrecisionMember>,
    Norm<QuaternionHighPrecisionMember, HighPrecisionMember>,
    Conjugate<QuaternionHighPrecisionMember>,
    Exponential<QuaternionHighPrecisionMember>,
    Trigonometric<QuaternionHighPrecisionMember>,
    Hyperbolic<QuaternionHighPrecisionMember>,
    Power<QuaternionHighPrecisionMember>,
    Roots<QuaternionHighPrecisionMember>,
    RealUnreal<QuaternionHighPrecisionMember,HighPrecisionMember>,
    Scale<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember>
{
	private static final QuaternionHighPrecisionMember ZERO = new QuaternionHighPrecisionMember();
	private static final QuaternionHighPrecisionMember ONE = new QuaternionHighPrecisionMember(BigDecimal.ONE,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionHighPrecisionMember TWO = new QuaternionHighPrecisionMember(BigDecimal.valueOf(2),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionHighPrecisionMember THREE = new QuaternionHighPrecisionMember(BigDecimal.valueOf(3),BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionHighPrecisionMember I = new QuaternionHighPrecisionMember(BigDecimal.ZERO,BigDecimal.ONE,BigDecimal.ZERO,BigDecimal.ZERO);
	private static final QuaternionHighPrecisionMember J = new QuaternionHighPrecisionMember(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ONE,BigDecimal.ZERO);
	private static final QuaternionHighPrecisionMember K = new QuaternionHighPrecisionMember(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ONE);
	
	public QuaternionHighPrecisionAlgebra() { }
	
	private final Procedure1<QuaternionHighPrecisionMember> UNITY =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> unity() {
		return UNITY;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> MUL =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b, QuaternionHighPrecisionMember c) {
			// for safety must use tmps
			BigDecimal r = a.r().multiply(b.r());
			r = r.subtract(a.i().multiply(b.i()));
			r = r.subtract(a.j().multiply(b.j()));
			r = r.subtract(a.k().multiply(b.k()));
			BigDecimal i = a.r().multiply(b.i());
			i = i.add(a.i().multiply(b.r()));
			i = i.add(a.j().multiply(b.k()));
			i = i.subtract(a.k().multiply(b.j()));
			BigDecimal j = a.r().multiply(b.j());
			j = j.subtract(a.i().multiply(b.k()));
			j = j.add(a.j().multiply(b.r()));
			j = j.add(a.k().multiply(b.i()));
			BigDecimal k = a.r().multiply(b.k());
			k = k.add(a.i().multiply(b.j()));
			k = k.subtract(a.j().multiply(b.i()));
			k = k.add(a.k().multiply(b.r()));
			c.setR( r );
			c.setI( i );
			c.setJ( j );
			c.setK( k );
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> POWER =
			new Procedure3<Integer, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer power, QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.QHP, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> power() {
		return POWER;
	}

	private final Procedure1<QuaternionHighPrecisionMember> ZER =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			assign().call(ZERO, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> zero() {
		return ZER;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> NEG =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> ADD =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b, QuaternionHighPrecisionMember c) {
			c.setR( a.r().add(b.r()) );
			c.setI( a.i().add(b.i()) );
			c.setJ( a.j().add(b.j()) );
			c.setK( a.k().add(b.k()) );
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SUB =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b, QuaternionHighPrecisionMember c) {
			c.setR( a.r().subtract(b.r()) );
			c.setI( a.i().subtract(b.i()) );
			c.setJ( a.j().subtract(b.j()) );
			c.setK( a.k().subtract(b.k()) );
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> EQ =
			new Function2<Boolean, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k();
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> NEQ =
			new Function2<Boolean, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			return !isEqual().call(a,b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public QuaternionHighPrecisionMember construct() {
		return new QuaternionHighPrecisionMember();
	}

	@Override
	public QuaternionHighPrecisionMember construct(QuaternionHighPrecisionMember other) {
		return new QuaternionHighPrecisionMember(other);
	}

	@Override
	public QuaternionHighPrecisionMember construct(String s) {
		return new QuaternionHighPrecisionMember(s);
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> ASSIGN =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember from, QuaternionHighPrecisionMember to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> INV =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			QuaternionHighPrecisionMember c = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember scale = new QuaternionHighPrecisionMember();
			HighPrecisionMember nval = new HighPrecisionMember();
			norm().call(a, nval);
			scale.setR( BigDecimal.ONE.divide((nval.v().multiply(nval.v())), HighPrecisionAlgebra.getContext()) );
			conjugate().call(a, c);
			multiply().call(scale, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> invert() {
		return INV;
	}
	
	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> DIVIDE =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b, QuaternionHighPrecisionMember c) {
			QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
			invert().call(b,tmp);
			multiply().call(a, tmp, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> divide() {
		return DIVIDE;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> CONJ =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			b.setR( a.r() );
			b.setI( a.i().negate() );
			b.setJ( a.j().negate() );
			b.setK( a.k().negate() );
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<QuaternionHighPrecisionMember,HighPrecisionMember> NORM =
			new Procedure2<QuaternionHighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, HighPrecisionMember b) {
			BigDecimal sum = a.r().multiply(a.r());
			sum = sum.add(a.i().multiply(a.i()));
			sum = sum.add(a.j().multiply(a.j()));
			sum = sum.add(a.k().multiply(a.k()));
			b.setV( BigDecimalMath.sqrt(sum, HighPrecisionAlgebra.getContext()) );
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure1<QuaternionHighPrecisionMember> PI_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			HighPrecisionMember pi = G.HP.construct();
			G.HP.PI().call(pi);
			a.setR(pi.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> PI() {
		return PI_;
	}

	private final Procedure1<QuaternionHighPrecisionMember> E_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			HighPrecisionMember e = G.HP.construct();
			G.HP.E().call(e);
			a.setR(e.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> E() {
		return E_;
	}

	private final Procedure1<QuaternionHighPrecisionMember> GAMMA_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			HighPrecisionMember gamma = G.HP.construct();
			G.HP.GAMMA().call(gamma);
			a.setR(gamma.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<QuaternionHighPrecisionMember> PHI_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			HighPrecisionMember phi = G.HP.construct();
			G.HP.PHI().call(phi);
			a.setR(phi.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> PHI() {
		return PHI_;
	}

	private final Procedure1<QuaternionHighPrecisionMember> I_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> I() {
		return I_;
	}

	private final Procedure1<QuaternionHighPrecisionMember> J_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> J() {
		return J_;
	}

	private final Procedure1<QuaternionHighPrecisionMember> K_ =
			new Procedure1<QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionMember> K() {
		return K_;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> EXP =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
			BigDecimal u = BigDecimalMath.exp(a.r(), HighPrecisionAlgebra.getContext());
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinc().call(z, z2);
			BigDecimal w = z2.v();
			BigDecimal t = u.multiply(w);
			b.setR( u.multiply(BigDecimalMath.cos(z.v(), HighPrecisionAlgebra.getContext())) );
			b.setI( t.multiply(a.i()) );
			b.setJ( t.multiply(a.j()) );
			b.setK( t.multiply(a.k()) );
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> exp() {
		return EXP;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> LOG =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			HighPrecisionMember norm = new HighPrecisionMember(); 
			HighPrecisionMember term = new HighPrecisionMember(); 
			HighPrecisionMember v1 = new HighPrecisionMember();
			HighPrecisionMember v2 = new HighPrecisionMember();
			HighPrecisionMember v3 = new HighPrecisionMember();
			norm().call(a, norm);
			HighPrecisionMember multiplier = new HighPrecisionMember(a.r().divide(norm.v(), HighPrecisionAlgebra.getContext()));
			v1.setV( a.i().multiply(multiplier.v()) );
			v2.setV( a.j().multiply(multiplier.v()) );
			v3.setV( a.k().multiply(multiplier.v()) );
			G.HP.acos().call(multiplier, term);
			b.setR(BigDecimalMath.log(norm.v(), HighPrecisionAlgebra.getContext()));
			b.setI( v1.v().multiply(term.v()) );
			b.setJ( v2.v().multiply(term.v()) );
			b.setK( v3.v().multiply(term.v()) );
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> log() {
		return LOG;
	}

	private final Procedure2<QuaternionHighPrecisionMember,HighPrecisionMember> REAL =
			new Procedure2<QuaternionHighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.r());
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMember,HighPrecisionMember> real() {
		return REAL;
	}
	
	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> UNREAL =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			assign().call(a, b);
			b.setR(BigDecimal.ZERO);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> unreal() {
		return UNREAL;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINH =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionHighPrecisionMember negA = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember sum = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember tmp1 = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember tmp2 = new QuaternionHighPrecisionMember();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sinh() {
		return SINH;
	}
	
	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> COSH =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionHighPrecisionMember negA = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember sum = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember tmp1 = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember tmp2 = new QuaternionHighPrecisionMember();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> cosh() {
		return COSH;
    }

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINHANDCOSH =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember s, QuaternionHighPrecisionMember c) {
			// TODO adapted from Complex64Algebra: might be wrong
			QuaternionHighPrecisionMember negA = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember sum = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember tmp1 = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember tmp2 = new QuaternionHighPrecisionMember();
			
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
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> TANH =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			QuaternionHighPrecisionMember s = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember c = new QuaternionHighPrecisionMember();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> tanh() {
		return TANH;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SIN =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinch().call(z, z2);
			BigDecimal cos = BigDecimalMath.cos(a.r(), HighPrecisionAlgebra.getContext());
			BigDecimal sin = BigDecimalMath.sin(a.r(), HighPrecisionAlgebra.getContext());
			BigDecimal sinhc_pi = z2.v();
			BigDecimal cosh = BigDecimalMath.cosh(z.v(), HighPrecisionAlgebra.getContext());
			BigDecimal ws = cos.multiply(sinhc_pi);
			b.setR( sin.multiply(cosh) );
			b.setI( ws.multiply(a.i()) );
			b.setJ( ws.multiply(a.j()) );
			b.setK( ws.multiply(a.k()) );
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sin() {
		return SIN;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> COS =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinch().call(z, z2);
			BigDecimal cos = BigDecimalMath.cos(a.r(), HighPrecisionAlgebra.getContext());
			BigDecimal sin = BigDecimalMath.sin(a.r(), HighPrecisionAlgebra.getContext());
			BigDecimal sinhc_pi = z2.v();
			BigDecimal cosh = BigDecimalMath.cosh(z.v(), HighPrecisionAlgebra.getContext());
			BigDecimal wc = sin.negate().multiply(sinhc_pi);
			b.setR( cos.multiply(cosh) );
			b.setI( wc.multiply(a.i()) );
			b.setJ( wc.multiply(a.j()) );
			b.setK( wc.multiply(a.k()) );
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> cos() {
		return COS;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINANDCOS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember s, QuaternionHighPrecisionMember c) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinch().call(z, z2);
			BigDecimal cos = BigDecimalMath.cos(a.r(), HighPrecisionAlgebra.getContext());
			BigDecimal sin = BigDecimalMath.sin(a.r(), HighPrecisionAlgebra.getContext());
			BigDecimal sinhc_pi = z2.v();
			BigDecimal cosh = BigDecimalMath.cosh(z.v(), HighPrecisionAlgebra.getContext());
			BigDecimal ws = cos.multiply(sinhc_pi);
			BigDecimal wc = sin.negate().multiply(sinhc_pi);
			s.setR( sin.multiply(cosh) );
			s.setI( ws.multiply(a.i()) );
			s.setJ( ws.multiply(a.j()) );
			s.setK( ws.multiply(a.k()) );
			c.setR( cos.multiply(cosh) );
			c.setI( wc.multiply(a.i()) );
			c.setJ( wc.multiply(a.j()) );
			c.setK( wc.multiply(a.k()) );
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sinAndCos() {
		return SINANDCOS;
	}
	
	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> TAN =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			QuaternionHighPrecisionMember sin = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember cos = new QuaternionHighPrecisionMember();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> tan() {
		return TAN;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> POW =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b, QuaternionHighPrecisionMember c) {
			QuaternionHighPrecisionMember logA = new QuaternionHighPrecisionMember();
			QuaternionHighPrecisionMember bLogA = new QuaternionHighPrecisionMember();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> pow() {
		return POW;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINCH =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			Sinch.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sinch() {
		return SINCH;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINCHPI =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			Sinchpi.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINC =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			Sinc.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sinc() {
		return SINC;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SINCPI =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			Sincpi.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> SQRT =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			QuaternionHighPrecisionMember ONE_HALF = G.QHP.construct();
			ONE_HALF.setR(BigDecimal.ONE.divide(TWO.r(), HighPrecisionAlgebra.getContext()));
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> sqrt() {
		return SQRT;
	}

	private final Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> CBRT =
			new Procedure2<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember a, QuaternionHighPrecisionMember b) {
			QuaternionHighPrecisionMember ONE_THIRD = G.QHP.construct();
			ONE_THIRD.setR(BigDecimal.ONE.divide(THREE.r(), HighPrecisionAlgebra.getContext()));
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionMember,QuaternionHighPrecisionMember> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, QuaternionHighPrecisionMember> ISZERO =
			new Function1<Boolean, QuaternionHighPrecisionMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionMember a) {
			return a.r().equals(BigDecimal.ZERO) && a.i().equals(BigDecimal.ZERO) &&
					a.j().equals(BigDecimal.ZERO) && a.k().equals(BigDecimal.ZERO);
		}
	};

	@Override
	public Function1<Boolean, QuaternionHighPrecisionMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionMember, QuaternionHighPrecisionMember> scale() {
		return MUL;
	}

}
