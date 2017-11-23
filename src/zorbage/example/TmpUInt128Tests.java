package zorbage.example;

import java.math.BigInteger;

import zorbage.groups.G;
import zorbage.type.data.int128.UnsignedInt128Member;

public class TmpUInt128Tests {

	// Note: UInt128 initially designed as 2 bytes rather than 2 longs. This was done to get
	// the logic right before making huge untestable numbers. This means the range is 0 to
	// 65535. I can exhaustively test that range to ensure code works. Once basic operations
	// are working I can change underlying types to longs and constants appropriately and code
	// should be correct.
	
	// These tests are slow so make it possible to turn them on or off
	
	private static final boolean RUN_EXHAUSTIVE_16_BIT_TESTS = false;
	
	public void run() {
		if (RUN_EXHAUSTIVE_16_BIT_TESTS) {
			System.out.println("Running exhaustive 16 bit tests");
			addTests();
			subtractTests();
			multiplyTests();
			divideTests();
			modTests();
			System.out.println("  Done running exhaustive 16 bit tests");
		}
	}
	
	private static void addTests() {
		System.out.println("  Add tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger BASE = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 0; j < 65536; j++) {
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.add(a, b, c);
				assert(c.v().equals(I.add(J).mod(BASE)));
			}			
		}
	}

	private static void subtractTests() {
		System.out.println("  Subtract tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger BASE = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 0; j < 65536; j++) {
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.subtract(a, b, c);
				BigInteger result = I.subtract(J);
				if (result.compareTo(BigInteger.ZERO) < 0)
					result.add(BASE);
				assert(c.v().equals(result));
			}			
		}
	}

	private static void multiplyTests() {
		System.out.println("  Multiply tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		BigInteger BASE = BigInteger.valueOf(65536);
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 0; j < 65536; j++) {
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.multiply(a, b, c);
				assert(c.v().equals(I.multiply(J).mod(BASE)));
			}			
		}
	}

	private static void divideTests() {
		System.out.println("  Divide tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 1; j < 65536; j++) { // avoid divide by zero
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.div(a, b, c);
				assert(c.v().equals(I.divide(J)));
			}			
		}
	}

	private static void modTests() {
		System.out.println("  Mod tests");
		UnsignedInt128Member a = G.UINT128.construct();
		UnsignedInt128Member b = G.UINT128.construct();
		UnsignedInt128Member c = G.UINT128.construct();
		for (int i = 0; i < 65536; i++) {
			BigInteger I = BigInteger.valueOf(i);
			a.setV(I);
			for (int j = 1; j < 65536; j++) { // avoid divide by zero
				BigInteger J = BigInteger.valueOf(j);
				b.setV(J);
				G.UINT128.mod(a, b, c);
				assert(c.v().equals(I.mod(J)));
			}			
		}
	}
}
