package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.RModuleMember;

public class RModuleNegate {

	private RModuleNegate() {
		// do not instantiate
	}
	
	public static <U extends RModuleMember<W>, V extends Group<V,W> & Addition<W>, W>
		void compute(V memberGroup, U a, U b)
	{
		W tmp = memberGroup.construct();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, tmp);
			memberGroup.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}
}
