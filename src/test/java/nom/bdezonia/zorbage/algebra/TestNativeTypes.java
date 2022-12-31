package nom.bdezonia.zorbage.algebra;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNativeTypes {

	@Test
	public void test1() {

		floatImplementation(G.FLT);
		
		assertTrue(true);
	}

	@Test
	public void test2() {

		shortImplementation(G.UINT8);
		shortImplementation(G.UINT9);
		shortImplementation(G.UINT10);
		shortImplementation(G.UINT11);
		shortImplementation(G.UINT12);
		shortImplementation(G.UINT13);
		shortImplementation(G.UINT14);
		shortImplementation(G.UINT15);
		
		shortImplementation(G.INT9);
		shortImplementation(G.INT10);
		shortImplementation(G.INT11);
		shortImplementation(G.INT12);
		shortImplementation(G.INT13);
		shortImplementation(G.INT14);
		shortImplementation(G.INT15);
		shortImplementation(G.INT16);

		assertTrue(true);
	}
	
	private <T extends Algebra<T,U> & Bounded<U> & Random<U>, U extends NativeGetSetFloat>
		void floatImplementation(T alg)
	{
		U minBound = alg.construct();
		U maxBound = alg.construct();
		
		alg.minBound().call(minBound);
		alg.maxBound().call(maxBound);
		
		float min = minBound.getNative();
		float max = maxBound.getNative();
		
		U value = alg.construct();
		
		// this will return a float between 0.0 and 1.0
		
		alg.random().call(value);
		
		System.out.println(alg.typeDescription() + " " + min + " " + max + " " + value.getNative());
	}
	
	private <T extends Algebra<T,U> & Bounded<U> & Random<U>, U extends NativeGetSetShort>
		void shortImplementation(T alg)
	{
		U minBound = alg.construct();
		U maxBound = alg.construct();
		
		alg.minBound().call(minBound);
		alg.maxBound().call(maxBound);
		
		short min = minBound.getNative();
		short max = maxBound.getNative();
		
		U value = alg.construct();
		
		// this will return a float between 0.0 and 1.0
		
		alg.random().call(value);
		
		System.out.println(alg.typeDescription() + "\t" + min + "\t" + max + "\t" + value.getNative());
	}
}
