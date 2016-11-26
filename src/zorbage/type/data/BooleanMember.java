package zorbage.type.data;

public class BooleanMember {
	
	private static final String ZERO = "0";
	private static final String ONE = "1";

	boolean v;
	
	public BooleanMember() {
		v = false;
	}
	
	public BooleanMember(boolean value) {
		v = value;
	}
	
	public BooleanMember(BooleanMember value) {
		v = value.v;
	}
	
	public BooleanMember(String value) {
		String[] strs = value.trim().split("\\s+");
		if (strs[0] == ZERO) v = false;
		else if (strs[0] == ONE) v = true;
		else v = java.lang.Boolean.parseBoolean(strs[0]);
	}

	public boolean value() { return v; }
	
	@Override
	public String toString() { if (v) return ONE; else return ZERO; }
}
