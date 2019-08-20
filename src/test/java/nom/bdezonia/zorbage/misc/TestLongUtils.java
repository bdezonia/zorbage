package nom.bdezonia.zorbage.misc;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLongUtils {

	@Test
	public void test() {
		assertEquals(0, LongUtils.numElements(new long[] {}));
		assertEquals(1, LongUtils.numElements(new long[] {1}));
		assertEquals(6, LongUtils.numElements(new long[] {2,3}));
		assertEquals(120, LongUtils.numElements(new long[] {4,5,6}));
		try {
			LongUtils.numElements(new long[] {-1});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		try {
			LongUtils.numElements(new long[] {2, Long.MAX_VALUE});
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
