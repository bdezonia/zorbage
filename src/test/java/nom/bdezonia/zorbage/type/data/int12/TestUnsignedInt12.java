package nom.bdezonia.zorbage.type.data.int12;

import org.junit.Test;

import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.array.ArrayStorage;

public class TestUnsignedInt12 {
	
	@Test
	public void test() {
		
		IndexedDataSource<?, UnsignedInt12Member> data =
				ArrayStorage.allocate(100, new UnsignedInt12Member());
		UnsignedInt12Member val = new UnsignedInt12Member();
		for (long i = 0; i < data.size(); i++) {
			data.get(i, val);
			data.set(i, val);
		}
	}

}
