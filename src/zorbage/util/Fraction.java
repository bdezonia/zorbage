package zorbage.util;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Fraction {
	
	
	// 64 = 64/1: 8 8 byte doubles in an octionionfloat64
	// 4 = 4/1: 4 bytes in a float
	// 1/64 = 1 / 64: 1 bit in a long
	// using 0 in n or d is an error
	// unknown = -1
	// variable = -2
	
	public static Fraction UNKNOWN = new Fraction(-1);
	public static Fraction VARIABLE = new Fraction(-2);
	
	private long n;
	private long d;
	
	public Fraction(long n, long d) {
		this.n = n;
		this.d = d;
	}
	
	public Fraction(long n) {
		this.n = n;
		this.d = 1;
	}
	
	public long n() { return n; }
	
	public long d() { return d; }

}
