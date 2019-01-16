package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Algebra;
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
	 * @param Algebra
	 * @param a
	 */
	public static <T extends Algebra<T,U> & Unity<U>, U>
		void compute(T Algebra, MatrixMember<U> a)
	{
		U zero = Algebra.construct();
		U one = Algebra.construct();
		Algebra.unity().call(one);
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
