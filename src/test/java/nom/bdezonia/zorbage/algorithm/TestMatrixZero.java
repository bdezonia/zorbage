package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64MatrixMember;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestMatrixZero {

	@Test
	public void test() {
		Float64MatrixMember a = new Float64MatrixMember(2,2,new double[] {1,2,3,4});
		Float64Member value = G.DBL.construct();
		G.DBL_MAT.zero().call(a);
		for (int i = 0; i < a.rows(); i++) {
			for (int j = 0; j < a.cols(); j++) {
				a.v(i, j, value);
				assertEquals(0, value.v(), 0);
			}
		}
	}
}
