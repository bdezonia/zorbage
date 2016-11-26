package zorbage.type.data;

public class BooleanMember {
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
		if (strs[0] == "0") v = false;
		else if (strs[0] == "1") v = true;
		else v = java.lang.Boolean.parseBoolean(strs[0]);
	}

	public boolean value() { return v; }
	
	@Override
	public String toString() { return "" + v; }
}
