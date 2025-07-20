package nom.bdezonia.zorbage.algebra.type.markers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;

public class TestAlgebraLibrary {


	private class FakeU extends Object { }
	
	private class FakeAlgebra
	
		implements
		
			Algebra<FakeAlgebra, FakeU>,
			ColorType
	{
		@Override
		public String typeDescription() {

			return "TOTALLY FAKE COLOR TYPE";
		}

		@Override
		public FakeU construct() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FakeU construct(FakeU other) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FakeU construct(String str) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Function2<Boolean, FakeU, FakeU> isEqual() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Function2<Boolean, FakeU, FakeU> isNotEqual() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Procedure2<FakeU, FakeU> assign() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Procedure1<FakeU> zero() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Function1<Boolean, FakeU> isZero() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private Algebra<?,?> FRED = new FakeAlgebra();
	
	@Test
	public void test1() {
		
		List<Algebra<?,?>> algs;
		
		algs = G.ALGEBRAS.findAlgebras(new Class<?>[0], new Class<?>[] {ComplexType.class});
		
		assertEquals(10, algs.size());
		
		assertTrue(algs.contains(G.CDBL));
		assertTrue(algs.contains(G.CFLT));
		assertTrue(algs.contains(G.CHLF));
		assertTrue(algs.contains(G.CHP));
		assertTrue(algs.contains(G.CQUAD));
		
		assertTrue(algs.contains(G.GAUSS16));
		assertTrue(algs.contains(G.GAUSS32));
		assertTrue(algs.contains(G.GAUSS64));
		assertTrue(algs.contains(G.GAUSS8));
		assertTrue(algs.contains(G.GAUSSU));
	}

	@Test
	public void test2() {
		
		List<Algebra<?,?>>algs;
		
		algs = G.ALGEBRAS.findAlgebras(new Class<?>[0], new Class<?>[] {SignedType.class, IntegerType.class});
		
		assertTrue(algs.size() == 20);
		
		assertTrue(algs.contains(G.INT1));
		assertTrue(algs.contains(G.INT2));
		assertTrue(algs.contains(G.INT3));
		assertTrue(algs.contains(G.INT4));
		assertTrue(algs.contains(G.INT5));
		assertTrue(algs.contains(G.INT6));
		assertTrue(algs.contains(G.INT7));
		assertTrue(algs.contains(G.INT8));
		assertTrue(algs.contains(G.INT9));
		assertTrue(algs.contains(G.INT10));
		assertTrue(algs.contains(G.INT11));
		assertTrue(algs.contains(G.INT12));
		assertTrue(algs.contains(G.INT13));
		assertTrue(algs.contains(G.INT14));
		assertTrue(algs.contains(G.INT15));
		assertTrue(algs.contains(G.INT16));
		assertTrue(algs.contains(G.INT32));
		assertTrue(algs.contains(G.INT64));
		assertTrue(algs.contains(G.INT128));
		assertTrue(algs.contains(G.UNBOUND));
	}

	@Test
	public void test3() {
		
		List<Algebra<?,?>> algs;
		
		algs = G.ALGEBRAS.findAlgebras(new Class<?>[0], new Class<?>[] {ColorType.class});
		
		assertFalse(algs.contains(FRED));
		
		int count = algs.size();

		G.ALGEBRAS.registerAlgebra(FRED);
		
		algs = G.ALGEBRAS.findAlgebras(new Class<?>[0], new Class<?>[] {ColorType.class});
		
		assertEquals(count+1, algs.size());

		assertTrue(algs.contains(FRED));
	}
}
