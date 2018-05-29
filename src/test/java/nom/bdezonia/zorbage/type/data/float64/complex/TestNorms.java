package nom.bdezonia.zorbage.type.data.float64.complex;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.algorithm.MatrixMaximumAbsoluteColumnSumNorm;
import nom.bdezonia.zorbage.algorithm.MatrixMaximumAbsoluteRowSumNorm;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

public class TestNorms {

	@Test
	public void test1() {
		Float64Member norm = new Float64Member();
		ComplexFloat64MatrixMember matrix = new ComplexFloat64MatrixMember();
		MatrixMaximumAbsoluteColumnSumNorm.compute(G.CDBL, matrix, norm);
		assertTrue(true);
	}

	@Test
	public void test2() {
		Float64Member norm = new Float64Member();
		ComplexFloat64MatrixMember matrix = new ComplexFloat64MatrixMember();
		MatrixMaximumAbsoluteRowSumNorm.compute(G.CDBL, matrix, norm);
		assertTrue(true);
	}
}
