package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.algebra.MatrixMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MatrixEqual {

	private MatrixEqual() { }
	
	public static <T extends Group<T,U>,U>
		boolean compute(T group, MatrixMember<U> a, MatrixMember<U> b)
	{
		if (a == b) return true;
		if (a.rows() != b.rows()) return false;
		if (a.cols() != b.cols()) return false;
		U value1 = group.construct();
		U value2 = group.construct();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value1);
				b.v(r, c, value2);
				if (group.isNotEqual(value1, value2))
					return false;
			}
		}
		return true;
	}
}
