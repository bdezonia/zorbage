package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

public class StaticAlgoRethink {

	private static <T extends Group<T,U> & Addition<U>, U>
		void compute(T group, U result)
	{ }
	
	private class OO<T extends Group<T,U> & Addition<U>, U>
		implements Procedure2<T,U>
	{
		@Override
		public void call(T a, U b) {}
		
	}
	
	public void test() {
		
		Float64Member result = G.DBL.construct();
		
		// THIS ?
		
		StaticAlgoRethink.compute(G.DBL, result);
		
		// OR THIS?
		
		new OO<Float64Group,Float64Member>().call(G.DBL, result);
		
		// I really don't like the second. It requires an object allocation and it requires
		// the specifying of the template parameter types.
		
		// The second has the good effect that algorithms could be passed around as procedures
		// and invoked. Imagine a class that works for any possible norm. You could pass in a
		// norm procedure and get different behavior. Otherwise you need one implementation of
		// the algorithm for each norm type you're interested in.
	}
}