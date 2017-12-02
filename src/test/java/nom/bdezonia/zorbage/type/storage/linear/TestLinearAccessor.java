package nom.bdezonia.zorbage.type.storage.linear;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.linear.LinearAccessor;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageSignedInt32;

public class TestLinearAccessor {

	@Test
	public void testAccessor() {
		SignedInt32Member value = new SignedInt32Member();
		ArrayStorageSignedInt32<SignedInt32Member> storage = new ArrayStorageSignedInt32<SignedInt32Member>(10, new SignedInt32Member());
		LinearAccessor<SignedInt32Member> accessor = new LinearAccessor<SignedInt32Member>(value, storage);
		// build the initial test data
		int i = 0;
		while (accessor.hasNext()) {
			accessor.fwd();
			accessor.get();
			value.setV(i++);
			accessor.put();
		}
		// visit the data in reverse order
		accessor.afterLast();
		while (accessor.hasPrev()) {
			accessor.back();
			accessor.get();
			assertEquals(value.v(), accessor.pos());
		}
	}

}
