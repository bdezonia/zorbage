package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;
import nom.bdezonia.zorbage.type.algebra.Unity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixUnity {

	private MatrixUnity() { }
	
	/**
	 * 
	 * @param group
	 * @param a
	 */
	public static <T extends Group<T,U> & Unity<U>, U>
		void compute(T group, MatrixMember<U> a)
	{
		U zero = group.construct();
		U one = group.construct();
		group.unity().call(one);
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				if (r == c)
					a.setV(r, c, one);
				else
					a.setV(r, c, zero);
			}
		}
	}
}
