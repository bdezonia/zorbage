package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.impl.Ramp;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.Storage;

public class TestMethodApplication {
	
	@Test
	public void test1() {
		
		IndexedDataSource<?, Float64Member> data = Storage.allocate(1000, G.DBL.construct());
		Ramp<Float64Group, Float64Member> ramp =
				new Ramp<Float64Group, Float64Member>(G.DBL, new Float64Member(), new Float64Member(0.1));
		Generate.compute(G.DBL, ramp, data);
		// multiply list1 by list2 and store in list2: with 1 list == squared values in place
		Transform3.compute(G.DBL, G.DBL.multiply(), 0, 0, 0, data.size(), 1, 1, 1, data, data, data);
		double tol = 0.0000000000001;
		Float64Member tmp = G.DBL.construct();
		data.get(0, tmp);
		assertEquals(0, tmp.v(), tol);
		data.get(1, tmp);
		assertEquals(0.1*0.1, tmp.v(), tol);
		data.get(2, tmp);
		assertEquals(0.2*0.2, tmp.v(), tol);
		data.get(3, tmp);
		assertEquals(0.3*0.3, tmp.v(), tol);
		data.get(4, tmp);
		assertEquals(0.4*0.4, tmp.v(), tol);
	}
}
